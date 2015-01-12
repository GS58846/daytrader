package org.apache.geronimo.daytrader.javaee6.web;

import org.apache.geronimo.daytrader.javaee6.entities.ExternalAuthProvider;
import org.apache.geronimo.daytrader.javaee6.utils.Log;
import org.json.JSONObject;
import org.scribe.model.*;
import org.scribe.oauth.OAuthService;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.ByteArrayInputStream;
import java.io.IOException;

@WebServlet(urlPatterns = {"/oauth2callback"}, asyncSupported = true)
public class OAuth2CallbackServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {
        //Check if the user have rejected
        String error = req.getParameter("error");
        if ((null != error) && ("access_denied".equals(error.trim()))) {
            HttpSession sess = req.getSession();
            sess.invalidate();
            resp.sendRedirect(req.getContextPath());
            return;
        }


        //OK the user have consented so lets find out about the user
        AsyncContext ctx = req.startAsync();
        ctx.start(new GetUserInfo(req, resp, ctx));
    }

    static class GetUserInfo implements Runnable {
        private HttpServletRequest req;
        private HttpServletResponse resp;
        private AsyncContext asyncCtx;

        public GetUserInfo(HttpServletRequest req, HttpServletResponse resp, AsyncContext asyncCtx) {
            this.req = req;
            this.resp = resp;
            this.asyncCtx = asyncCtx;
        }

        @Override
        public void run() {
            HttpSession sess = req.getSession();
            OAuthService service = (OAuthService) sess.getAttribute("oauth2Service");
            //Get the all important authorization code
            String code = req.getParameter("code");
            //Construct the access token
            Token token = service.getAccessToken(null, new Verifier(code));
            //Save the token for the duration of the session
            sess.setAttribute("token", token);
            //Perform a proxy login
            try {
                req.login("fred", "fredfred");
            } catch (ServletException e) {
                //Handle error - should not happen
            }
            //Now do something with it - get the user's G+ profile
            OAuthRequest oReq = new OAuthRequest(Verb.GET,
                    "https://www.googleapis.com/oauth2/v2/userinfo");
            service.signRequest(token, oReq);
            Response oResp = oReq.send();

            //Read the result
            JSONObject profile = new JSONObject(oResp.getBody());
            //Save the user details somewhere or associate it with
            sess.setAttribute("name", profile.getString("name"));
            sess.setAttribute("email", profile.getString("email"));
            sess.setAttribute("id", profile.getString("id"));

            try {
                //req.getRequestDispatcher("app").forward(req, resp);
                resp.getWriter().println("<script>");
                resp.getWriter().println("window.opener.location.href = 'app?action="+req.getParameter("state")+"&provider="+ ExternalAuthProvider.GOOGLE.name() +"&token="+token.getToken()+"';");
                resp.getWriter().println("window.close();");
                resp.getWriter().println("</script>");
            } catch (IOException e) {
                Log.error("Error redirecting to welcome.", e);
            }
            asyncCtx.complete();
        }
    }
}
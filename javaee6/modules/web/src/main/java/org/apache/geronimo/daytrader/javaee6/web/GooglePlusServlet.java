package org.apache.geronimo.daytrader.javaee6.web;

import org.apache.geronimo.daytrader.javaee6.web.oauth.Google2Api;
import org.scribe.builder.ServiceBuilder;
import org.scribe.oauth.OAuthService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/googleplus")
public class GooglePlusServlet extends HttpServlet {
    private static final String CLIENT_ID = "1003102423058-93thuatnhj2m2qbmtt9575o3jsf9sngm.apps.googleusercontent.com";
    private static final String CLIENT_SECRET = "ovnFQkqRttkot_yw9Ks_3zKq";
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws IOException, ServletException {
        //Configure
        ServiceBuilder builder= new ServiceBuilder();
        OAuthService service = builder.provider(Google2Api.class)
                .apiKey(CLIENT_ID)
                .apiSecret(CLIENT_SECRET)
                .callback(req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + req.getContextPath() + "/oauth2callback")
                .scope("openid profile email " +
                        "https://www.googleapis.com/auth/plus.login " +
                        "https://www.googleapis.com/auth/plus.me")
                .debug()
                .build(); //Now build the call
        HttpSession sess = req.getSession();
        sess.setAttribute("oauth2Service", service);
        res.sendRedirect(service.getAuthorizationUrl(null));
    }
}

package org.apache.geronimo.samples.daytrader.web.prims.ejb3;

import org.apache.geronimo.samples.daytrader.ejb3.prims.LocalSFTestLocal;
import org.apache.geronimo.samples.daytrader.ejb3.prims.RemoteSFTestRemote;
import java.io.*;
import javax.ejb.EJB;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import javax.servlet.*;
import javax.servlet.http.*;

/**
 *
 * @author jstecher
 * @version
 */
//@EJB (beanInterface=LocalSFTestLocal.class, beanName="LocalSFTestBean", name="SFTestLocal")
public class PingSFSessionRemoteCreate extends HttpServlet {

    String payload = "JEE is cool";
    static String html1 = "<html><head><title>Servlet PingSFSessionLocalCreate</title><LINK href=\"prim.css\" rel=\"stylesheet\" type=\"text/css\" /></head><body><h1>Servlet PingSFSessionLocalCreate</h1>";
    static String html2 = "</body></html>";
    
    
    private RemoteSFTestRemote getSFBean(HttpServletRequest request, HttpServletResponse response) throws NamingException{
        HttpSession sess = request.getSession(true);
        RemoteSFTestRemote SFBean = (RemoteSFTestRemote)sess.getAttribute("SFSessionRef");
        if(SFBean==null){
            // Get the initial JNDI context.
            Context ctx = new InitialContext();
            SFBean = (RemoteSFTestRemote) ctx.lookup(RemoteSFTestRemote.class.getName());
            sess.setAttribute("SFSessionRef", SFBean);
        }
        return SFBean;
    }
    
    /** Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        RemoteSFTestRemote remoteSFTestBean;
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        out.println(html1);
        
        try{
            remoteSFTestBean = getSFBean(request, response);
            remoteSFTestBean.addToArrayList(payload);
            out.println(payload);
        }catch(Exception e){
            System.out.println(e.toString());
            out.println("Failure in Primitive call!\n\n" + e.toString());
        }    

        out.println(html2);
        out.close();
    }
    
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }
    
    /** Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }
    
    /** Returns a short description of the servlet.
     */
    public String getServletInfo() {
        return "Short description";
    }
    // </editor-fold>
}

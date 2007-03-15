package org.apache.geronimo.samples.daytrader.web.prims.ejb3;

import org.apache.geronimo.samples.daytrader.ejb3.prims.RemoteSFTestRemote;
import java.io.*;
import java.net.*;

import javax.servlet.*;
import javax.servlet.http.*;

/**
 *
 * @author jstecher
 * @version
 */
public class PingSFSessionRemoteRemove extends HttpServlet {
    
    public static String payload = "Stateful Session Removed";
    static String html1 = "<html><head><title>Servlet PingSFSessionLocalRemove</title><LINK href=\"prim.css\" rel=\"stylesheet\" type=\"text/css\" /></head><body><h1>Servlet PingSFSessionLocalRemove</h1>";
    static String html2 = "</body></html>";
    
    
    private void removeSFBean(HttpServletRequest request, HttpServletResponse response){
        HttpSession sess = request.getSession(false);
        RemoteSFTestRemote SFBean;
        if(sess!=null){
            SFBean = (RemoteSFTestRemote)sess.getAttribute("SFSessionRef");
            SFBean.remove();
            sess.invalidate();
        }
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
            removeSFBean(request, response);
            out.println(payload);
        }catch(Exception e){
            System.out.println(e.toString());
            out.println("Stateful Session Bean not created or Failure in Primitive call!\n\n" + e.toString());
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

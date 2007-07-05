
package org.apache.geronimo.samples.daytrader.web.prims.ejb3;

import org.apache.geronimo.samples.daytrader.ejb3.prims.LocalSLMDBTestRemote;
import java.io.*;
import java.net.*;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.jms.QueueConnectionFactory;
import javax.naming.InitialContext;

import javax.servlet.*;
import javax.servlet.http.*;



/**
 *
 * @version
 */
public class PingServlet2Session2MDB extends HttpServlet {
    
    @EJB
    private LocalSLMDBTestRemote mdbTestRemote;
    
    @Resource(name = "jms/QueueConnectionFactory")
    private QueueConnectionFactory queueConnectionFactory;
    
    static String html1 = "<html><head><title>Servlet PingServlet2Session2MDB</title><LINK href=\"prim.css\" rel=\"stylesheet\" type=\"text/css\" /></head><body><h1>Servlet PingSLSessionLocal</h1>";
    static String html2 = "</body></html>";
    
    /** Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println(html1);
        int iterations = StaticUtils.getIterations(request);
        String message = null;
        
        for (int i = 0; i < iterations; i++){
            message = mdbTestRemote.publishToTradeBrokerQueue().toString();
        }

        out.println(message);
        
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

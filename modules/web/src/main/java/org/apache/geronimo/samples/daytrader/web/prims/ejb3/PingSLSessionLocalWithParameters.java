package org.apache.geronimo.samples.daytrader.web.prims.ejb3;

import org.apache.geronimo.samples.daytrader.ejb3.prims.LocalSLTestLocal;
import java.io.*;
import java.math.BigDecimal;
import java.util.Vector;
import javax.ejb.EJB;

import javax.servlet.*;
import javax.servlet.http.*;

/**
 *
 * @author jstecher
 * @version
 */
public class PingSLSessionLocalWithParameters extends HttpServlet {

    @EJB
    private LocalSLTestLocal localSLTestBean;
    private Integer number = new Integer(6);
    private Vector vect = new Vector(10);
    private BigDecimal bd = new BigDecimal(0.1112); 
    private String payload = "This is a test string payload";
    static String html1 = "<html><head><title>Servlet PingSLSessionLocalWithParameters</title><LINK href=\"prim.css\" rel=\"stylesheet\" type=\"text/css\" /></head><body><h1>Servlet PingSLSessionLocalWithParameters</h1>";
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
        out.println(localSLTestBean.getPayloadWithParameters(payload, number, vect, bd));
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

package org.apache.geronimo.samples.daytrader.web.prims.ejb3;

import org.apache.geronimo.samples.daytrader.ejb3.prims.AccountFacadeLocal;
import org.apache.geronimo.samples.daytrader.AccountDataBean;
import java.io.*;
import java.net.*;
import java.util.List;
import javax.ejb.EJB;

import javax.servlet.*;
import javax.servlet.http.*;

/**
 *
 * @author jstecher
 * @version
 */
public class PingServlet2Session2Entity extends HttpServlet {

    @EJB
    private AccountFacadeLocal accountFacade; 
    
    private static String header = "<HTML><HEAD><TITLE>EJB3 Primitives - PingServlet2Session2Entity</TITLE><LINK href=\"prim.css\" rel=\"stylesheet\" type=\"text/css\" /></HEAD><BODY><H3>PingServlet2Session2Entity</H3><BR>\n<TABLE class=\"table_1\"><TR class=\"row_1\"><TD>Account ID</TD><TD>Balance</TD></TR>";
    private static String footer = "</TABLE>\n</BODY></HTML>";
    
    /** Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        PrintWriter out = response.getWriter();        
                response.setContentType("text/html;charset=UTF-8");

		out.println(header);
		int iterations = StaticUtils.getIterations(request);
		
		for (int i = 0; i < iterations; i++){			
                        AccountDataBean ae = accountFacade.find(new Integer(StaticUtils.getRandomAccountID()));
                                        
			out.println("<TR class=\"row_2\"><TD>" + ae.getAccountID() + "</TD><TD>" + ae.getBalance() + "</TD</TR>");
			
		}
		out.println(footer);
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

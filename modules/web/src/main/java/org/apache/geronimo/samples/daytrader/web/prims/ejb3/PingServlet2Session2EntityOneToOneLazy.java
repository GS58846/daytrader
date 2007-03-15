package org.apache.geronimo.samples.daytrader.web.prims.ejb3;

import org.apache.geronimo.samples.daytrader.ejb3.prims.AccountFacadeLocal;
import org.apache.geronimo.samples.daytrader.AccountDataBean;
import org.apache.geronimo.samples.daytrader.AccountProfileDataBean;
import org.apache.geronimo.samples.daytrader.HoldingDataBean;
import org.apache.geronimo.samples.daytrader.ejb3.prims.ResultHolder;
import java.io.*;
import java.net.*;
import java.util.Iterator;
import java.util.List;
import java.util.Collection;
import javax.annotation.Resource;
import javax.ejb.EJB;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;

/**
 *
 * @author jstecher
 * @version
 */
public class PingServlet2Session2EntityOneToOneLazy extends HttpServlet {

    @EJB
    private AccountFacadeLocal accountFacade; 
    
    
    @Resource
    private javax.transaction.UserTransaction transaction;
    
    private static String header = "<HTML><HEAD><TITLE>EJB3 Primitives - PingServlet2Session2EntityOneToOneLazy</TITLE><LINK href=\"prim.css\" rel=\"stylesheet\" type=\"text/css\" /></HEAD><BODY><H3>PingServlet2Session2EntityOneToOneLazy</H3><BR>\n<TABLE class=\"table_1\"><TR class=\"row_1\"><TD>Account ID</TD><TD>Balance</TD><TD>User ID</TD><TD>Full Name</TD></TR>";
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
        try {

            for (int i = 0; i < iterations; i++){
                    transaction.begin();
                    
                    AccountDataBean ae = accountFacade.findByAccountID(new Integer(StaticUtils.getRandomAccountID()), true);
                                            
                    out.println("<TR class=\"row_2\"><TD>" + ae.getAccountID() + "</TD><TD>" + ae.getBalance() + "</TD>");

                    AccountProfileDataBean ape = ae.getProfile();
                    out.println("<TD>" + ape.getUserID() + "</TD><TD>" + ape.getFullName() + "</TD></TR>");

                    transaction.commit();
            }
        } catch (Exception ex) {
            ServletException se = new ServletException();
            se.initCause(ex);
            throw se;
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

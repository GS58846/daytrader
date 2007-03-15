package org.apache.geronimo.samples.daytrader.web.prims.ejb3;

import org.apache.geronimo.samples.daytrader.ejb3.prims.RemoteSFTestRemote;
import java.io.*;
import java.util.Random;
import javax.ejb.EJB;

import javax.servlet.*;
import javax.servlet.http.*;

/**
 *
 * @author jstecher
 * @version
 */
public class PingSFSessionRemoteUpdate extends HttpServlet {

    @EJB
    private RemoteSFTestRemote remoteSFTestBean;
    Random rn = new Random();
    static String payload1 = "Update One:  JEE is extemely fun";
    static String payload2 = "Update Two:  JEE is truly fun";
    static String html1 = "<html><head><title>Servlet PingSFSessionLocalUpdate</title><LINK href=\"prim.css\" rel=\"stylesheet\" type=\"text/css\" /></head><body><h1>Servlet PingSFSessionLocalUpdate</h1>";
    static String html2 = "</body></html>";
    
    
    private RemoteSFTestRemote getSFBean(HttpServletRequest request, HttpServletResponse response){
        HttpSession sess = request.getSession(false);
        RemoteSFTestRemote SFBean;
        if(sess!=null){
            SFBean = (RemoteSFTestRemote)sess.getAttribute("SFSessionRef");
        }else{
            SFBean = null;
        }
        return SFBean;
    }
    
    /** Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        double randomNumber = rn.nextDouble();
        double randomNumberMessage = rn.nextDouble();
        
        out.println(html1);
        
        try{
            //Need to think about business logic here so that its add, remove evenly.
            remoteSFTestBean = getSFBean(request, response);
            if(remoteSFTestBean!=null){
                if(randomNumber <= 0.50d){
                    out.println((String)remoteSFTestBean.getFirstItemFromArrayList());
                }else{
                    if(randomNumberMessage <= 0.50d){
                        remoteSFTestBean.addToArrayList(payload1);
                        out.println(payload1);
                    }else{
                        remoteSFTestBean.addToArrayList(payload2);
                        out.println(payload2);
                    } 
                }
            }else{
                out.println("Stateful Session Bean was not created or an error occured and it was null");
            }
            
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

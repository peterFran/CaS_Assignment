/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import cart.Order;
import cart.ShoppingCart;
import dataAccess.OrderDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import user.User;

/**
 *
 * @author petermeckiffe
 */
public class PlaceOrder extends HttpServlet {

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            /* TODO output your page here. You may use following sample code. */
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet PlaceOrder</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet PlaceOrder at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        } finally {            
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Get current clients cart, if exists commit to DB and dispatch to newOrder page
        OrderDAO orderDao = new OrderDAO();
        HttpSession session = request.getSession();
        Map<String, ShoppingCart> mp= (Map<String, ShoppingCart>)session.getAttribute("shoppingCarts");
        String clientId = (String) request.getParameter("clientId");
        System.out.println(clientId);
        ShoppingCart cart = mp.get(clientId);
        
        Order order = orderDao.placeOrder(cart);
        if(order!=null){
            
            mp.remove(clientId);
            session.removeAttribute("currentClient");
            request.setAttribute("order", order);
            request.getRequestDispatcher(
            "WEB-INF/newOrder.jsp").forward(request,response);
        } else {
            request.getRequestDispatcher(
            "WEB-INF/failed.jsp").forward(request,response);
        }
        
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}

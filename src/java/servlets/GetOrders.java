/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import cart.Order;
import dataAccess.OrderDAO;
import dataAccess.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author petermeckiffe
 */
@WebServlet(name = "GetOrders", urlPatterns = {"/GetOrders"})
public class GetOrders extends HttpServlet {

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
            out.println("<title>Servlet GetOrders</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet GetOrders at " + request.getContextPath() + "</h1>");
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
        // If client ID is passed, get all orders for client. If not, get all orders
        String id = request.getParameter("client");
        OrderDAO dao = new OrderDAO();
        UserDAO userDao = new UserDAO();
        List<Order> orders = null;
        if (id != null) {
            int userID = Integer.parseInt(id);
            orders = dao.retrieveCustomerOrders(userID);
            
        } else{
            orders = dao.retrieveCustomerOrders();
        }
        // Set request attributes and dispatch request to orderOps page
        request.setAttribute("orders", orders);
        request.setAttribute("clients", userDao.getUsers());
        request.getRequestDispatcher(
                "WEB-INF/orderOps.jsp").forward(request, response);
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
        processRequest(request, response);
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

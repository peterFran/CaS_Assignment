/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import cart.Item;
import cart.ShoppingCart;
import dataAccess.ItemDAO;
import dataAccess.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
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
public class AddToCart extends HttpServlet {

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
            out.println("<title>Servlet AddToCart</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AddToCart at " + request.getContextPath() + "</h1>");
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
        // Gets session
        HttpSession session = request.getSession();
        
        // Creates database beans
        UserDAO userDao = new UserDAO();
        ItemDAO itemDao = new ItemDAO();
        
        // Gets current user & item ID and quantity from form
        User user = (User) session.getAttribute("currentClient");
        String itemId = request.getParameter("itemId");
        String quantityString = request.getParameter("quantity");
        
        // Gets cart object from session
        Map<String,ShoppingCart> carts = (Map<String,ShoppingCart>) session.getAttribute("shoppingCarts");
        String userID = Integer.toString(user.getID());
        
        // Gets users cart
        ShoppingCart cart = carts.get(userID);
        
        // Gets item from DB and checks quantity in cart
        Item item = itemDao.getItem(Integer.parseInt(itemId));
        int quantity = cart.getItemQuantity(item);

        // Can perform 3 tasks depending on button pressed else if handles which one
        if(request.getParameter("inc")!= null){ // increment quantity of item
            cart.addItem(item, quantity+1);
        }else if (request.getParameter("dec")!=null){ // decrement quantity of item
            if(quantity>0){
                cart.addItem(item, quantity-1);
            }
        }else if (quantityString!=null){ // set quantity of item
            cart.addItem(item, Integer.parseInt(quantityString));
        }
        
        // put the cart object back into the map
        carts.put(Integer.toString(user.getID()), cart);
        
        // set session attribute for carts & redirect back to shopping servlet
        session.setAttribute("shoppingCarts", carts);
        response.sendRedirect("Shopping");
        
        
        
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

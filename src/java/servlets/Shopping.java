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
public class Shopping extends HttpServlet {

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
            out.println("<title>Servlet Shopping</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Shopping at " + request.getContextPath() + "</h1>");
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
        // Gets session & current client
        // Sets a request attribute to the clients cart and lists all items in another
        // dispatches request to shoppingOps page
        HttpSession session = request.getSession();
        User client = (User) session.getAttribute("currentClient");
        if(client!=null){
            Map<String,ShoppingCart> mp = (Map<String,ShoppingCart>) session.getAttribute("shoppingCarts");
            request.setAttribute("cart", (ShoppingCart)mp.get(Integer.toString(client.getID())));
            System.out.println(mp.get(Integer.toString(client.getID())));
            request.setAttribute("items", new ItemDAO().getAllValidItems());
            
        }
        request.setAttribute("clients", new UserDAO().getUsers());
        request.getRequestDispatcher(
            "WEB-INF/shoppingOps.jsp").forward(request,response);
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
        UserDAO userDao = new UserDAO();
        HttpSession session = request.getSession();
        
        String clientId = request.getParameter("clientId");
        User user = userDao.getUser(Integer.parseInt(clientId));
        session.setAttribute("currentClient", user);
        
        Map<String,ShoppingCart> carts = (Map<String,ShoppingCart>) session.getAttribute("shoppingCarts");
        ShoppingCart cart;
        
        if(carts==null){
            carts = new HashMap<String,ShoppingCart>();
        }
        if(!carts.containsKey(clientId)){
            carts.put(clientId, new ShoppingCart(user));
        } 
        
        session.setAttribute("shoppingCarts", carts);                
        this.doGet(request, response);
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

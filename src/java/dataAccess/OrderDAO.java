/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dataAccess;

import cart.CustomerItem;
import cart.Item;
import cart.Order;
import cart.ShoppingCart;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;
import user.User;

/**
 *
 * @author petermeckiffe
 */
// Acts as connection bean for CaS_Orders database
public class OrderDAO extends DAO {

    private ItemDAO itemDao;
    private UserDAO userDao;

    public OrderDAO() {
        super("CaS_Orders");
        this.itemDao = new ItemDAO();
        this.userDao = new UserDAO();
    }

    public Order retrieveOrder(int orderID) {
        List<CustomerItem> items = new ArrayList<CustomerItem>();
        Statement state = null;
        ResultSet rs = null;
        try {
            state = this.conn.createStatement();
            rs = state.executeQuery("SELECT item_id, quantity, price_each from CaS_Orders.items_in_orders WHERE order_id='" + orderID + "'");
            while (rs.next()) {
                int id = rs.getInt("item_id");
                int q = rs.getInt("quantity");
                BigDecimal price = rs.getBigDecimal("price_each");
                Item item = this.itemDao.getItem(id);
                item.setPrice(price);
                items.add(new CustomerItem(item, q));
            }
            rs = state.executeQuery("SELECT user_id, item_hash, date_created ,total_cost from CaS_Orders.orders WHERE order_id='" + orderID + "'");
            if(rs.next()){
                User user = this.userDao.getUser(rs.getInt("user_id"));
                if(user==null){
                    user = new User(rs.getInt("user_id"),"-- User has been removed from system --","");
                }
                return new Order(orderID, user, items, rs.getInt("item_hash"), rs.getTimestamp("date_created"), rs.getBigDecimal("total_cost"));
            } else{
                return null;
                      
            }
        } catch (SQLException a) {
            System.out.println(a);
            return null;
        } finally {
            this.closeConns(state, rs, null);
        }
    }
    
    

    public List<Order> retrieveCustomerOrders(int userID) {
        List<Order> orders = new ArrayList<Order>();
        Statement state = null;
        ResultSet rs = null;
        try {
            state = this.conn.createStatement();
            rs = state.executeQuery("SELECT order_id from CaS_Orders.orders WHERE user_id='" + userID + "' order by order_id");
            while (rs.next()) {
                orders.add(this.retrieveOrder(rs.getInt(1)));
            }
            return orders;
        } catch (SQLException a) {
            System.out.println(a);
            return null;
        } finally {
            this.closeConns(state, rs, null);
        }
    }
    
    // Retrieves all orders for all customers
    public List<Order> retrieveCustomerOrders() {
        List<Order> orders = new ArrayList<Order>();
        Statement state = null;
        ResultSet rs = null;
        try {
            state = this.conn.createStatement();
            rs = state.executeQuery("SELECT order_id from CaS_Orders.orders order by order_id");
            while (rs.next()) {
                orders.add(this.retrieveOrder(rs.getInt("order_id")));
            }
            return orders;
        } catch (SQLException a) {
            System.out.println(a);
            return null;
        } finally {
            this.closeConns(state, rs, null);
        }
    }
    
    // Could be useful in further development of system
    public List<Order> retrieveCustomerOrdersWithContentsHash(int userID, int itemHash) {
        List<Order> orders = new ArrayList<Order>();
        Statement state = null;
        ResultSet rs = null;
        try {
            state = this.conn.createStatement();
            rs = state.executeQuery("SELECT order_id from CaS_Orders.orders WHERE user_id='" + userID + "' AND item_hash='" + itemHash + "' order by order_id");
            while (rs.next()) {
                orders.add(this.retrieveOrder(rs.getInt("order_id")));
            }
            return orders;
        } catch (SQLException a) {
            return null;
        } finally {
            this.closeConns(state, rs, null);
        }
    }
    
    // Gets an order placed by a user at a certain time.
    public int retrieveCustomerOrderIDWithTimestamp(int userID, Timestamp dateCreated) {
        List<Order> orders = new ArrayList<Order>();
        Statement state = null;
        ResultSet rs = null;
        try {
            state = this.conn.createStatement();
            rs = state.executeQuery("SELECT order_id from CaS_Orders.orders WHERE user_id='" + userID + "' AND date_created='" + dateCreated + "'");
            if (rs.next()) {
                return rs.getInt("order_id");
            }
            return -1;
        } catch (SQLException a) {
            System.out.println(a);
            return -1;
        } finally {
            this.closeConns(state, rs, null);
        }
    }
    
    // Places the order on the system
    public Order placeOrder(ShoppingCart cart) {
        Statement state = null;
        ResultSet rs = null;
        try {
            state = this.conn.createStatement();
            int hash = cart.getItemList().hashCode();
            
            int orderID = this.retrieveCustomerOrderIDWithTimestamp(cart.getUserID(), cart.getTimeCreated());
            if (orderID!=-1) {
                return this.retrieveOrder(orderID);
            }
            
            int code = state.executeUpdate("Insert into Cas_Orders.orders (user_id, total_cost,item_hash, date_created) values('" + cart.getUserID() + "','" + cart.getTotalCost() + "','" + hash + "','" + cart.getTimeCreated() + "')");
            orderID = this.retrieveCustomerOrderIDWithTimestamp(cart.getUserID(), cart.getTimeCreated());
            for (Iterator<CustomerItem> it = cart.getItemList().iterator(); it.hasNext();) {
                CustomerItem item = it.next();
                state.executeUpdate("Insert into Cas_Orders.items_in_orders (order_id, item_id, quantity, price_each) values('" + orderID + "','" + item.getID() + "','"+item.getQuantity()+"','"+item.getPrice()+"')");
            }
            
            return new Order(orderID, cart.getUser(), cart.getItemList(), hash, cart.getTimeCreated(), cart.getTotalCost());

        } catch (SQLException a) {
            System.out.println(a.toString());

            return null;
        } finally {
            this.closeConns(state, rs, null);
        }
    }
}

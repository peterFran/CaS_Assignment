/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dataAccess;

import cart.CustomerItem;
import cart.Item;
import cart.Order;
import cart.ShoppingCart;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author petermeckiffe
 */
public class OrderDAO extends DAO {

    private ItemDAO itemDao;

    public OrderDAO() {
        super("CaS_Orders");
        this.itemDao = new ItemDAO();
    }

    public Order retrieveOrder(int orderID) {
        List<CustomerItem> items = new ArrayList<CustomerItem>();
        Statement state = null;
        ResultSet rs = null;
        try {
            state = this.conn.createStatement();
            rs = state.executeQuery("SELECT item_id, quantity from CaS_Orders.items_in_orders WHERE order_id='" + orderID + "'");
            while (rs.next()) {
                int id = rs.getInt("item_id");
                int q = rs.getInt("quantity");
                System.out.println(id);
                System.out.println(q);
                items.add(new CustomerItem(this.itemDao.getItem(id), q));
            }
            rs = state.executeQuery("SELECT user_id, item_hash, date_created ,total_cost from CaS_Orders.orders WHERE order_id='" + orderID + "'");
//            if(rs.next() && (items.hashCode() != rs.getInt(3))){
//                System.out.println("Error: Hash Codes do not match");
//            } else {
//                System.out.println("They match");
//            }
            if(rs.next()){
                return new Order(orderID, rs.getInt("user_id"), items, rs.getInt("item_hash"), rs.getTimestamp("date_created"), rs.getBigDecimal("total_cost"));
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
            rs = state.executeQuery("SELECT order_id from CaS_Orders.orders WHERE user_id='" + userID + "'");
            while (rs.next()) {
                orders.add(this.retrieveOrder(rs.getInt(1)));
            }
            return orders;
        } catch (SQLException a) {
            System.out.println(a);
            System.out.println("it");
            return null;
        } finally {
            this.closeConns(state, rs, null);
        }
    }

    public List<Order> retrieveCustomerOrders() {
        List<Order> orders = new ArrayList<Order>();
        Statement state = null;
        ResultSet rs = null;
        try {
            state = this.conn.createStatement();
            rs = state.executeQuery("SELECT order_id from CaS_Orders.orders");
            while (rs.next()) {
                System.out.println(rs.getInt("order_id"));
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

    public List<Order> retrieveCustomerOrdersWithContentsHash(int userID, int itemHash) {
        List<Order> orders = new ArrayList<Order>();
        Statement state = null;
        ResultSet rs = null;
        try {
            state = this.conn.createStatement();
            rs = state.executeQuery("SELECT order_id from CaS_Orders.orders WHERE user_id='" + userID + "' AND item_hash='" + itemHash + "'");
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

    public int retrieveCustomerOrderIDWithTimestamp(int userID, Timestamp dateCreated) {
        List<Order> orders = new ArrayList<Order>();
        Statement state = null;
        ResultSet rs = null;
        try {
            state = this.conn.createStatement();
            System.out.println(userID);
            System.out.println(dateCreated);
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
            System.out.println(code);
            orderID = this.retrieveCustomerOrderIDWithTimestamp(cart.getUserID(), cart.getTimeCreated());
            System.out.println("ID:"+orderID);
            for (Iterator<CustomerItem> it = cart.getItemList().iterator(); it.hasNext();) {
                CustomerItem item = it.next();
                state.executeUpdate("Insert into Cas_Orders.items_in_orders (order_id, item_id, quantity) values('" + orderID + "','" + item.getID() + "','"+item.getQuantity()+"')");
            }
            
            return new Order(orderID, cart.getUserID(), cart.getItemList(), hash, cart.getTimeCreated(), cart.getTotalCost());
            //TODO CHANGE

        } catch (SQLException a) {
            System.out.println(a.toString());

            return null;
        } finally {
            this.closeConns(state, rs, null);
        }
    }
}

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
import java.sql.Date;
import java.util.List;


/**
 *
 * @author petermeckiffe
 */
public class OrderDAO extends DAO{

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
            rs = state.executeQuery("SELECT item_id, quantity from CaS_Orders.items_in_order WHERE order_id='" + orderID + "'");
            while (rs.next()) {
                items.add(new CustomerItem(this.itemDao.getItem(rs.getInt(1)),rs.getInt(2)));
            }
            
            rs = state.executeQuery("SELECT user_id, item_hash, date_created ,total_cost from CaS_Orders.orders WHERE order_id='" + orderID + "'");
            if(items.hashCode()!=rs.getInt(3)){
                System.out.println("Error: Hash Codes do not match");
            }
            return new Order(orderID, rs.getInt("user_id"), items, rs.getInt("item_hash"),rs.getDate("date_created"),rs.getBigDecimal("total_cost"));
        } catch (SQLException a) {
            return null;
        } finally{
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
            return null;
        } finally{
            this.closeConns(state, rs, null);
        }
    }
    public List<Order> retrieveCustomerOrdersWithContentsHash(int userID, int itemHash){
        List<Order> orders = new ArrayList<Order>();
        Statement state = null;
        ResultSet rs = null;
        try {
            state = this.conn.createStatement();
            rs = state.executeQuery("SELECT order_id from CaS_Orders.orders WHERE user_id='" + userID + "' AND item_hash='"+itemHash+"'");
            while (rs.next()) {
                orders.add(this.retrieveOrder(rs.getInt("order_id")));
            }
            return orders;
        } catch (SQLException a) {
            return null;
        }  finally{
            this.closeConns(state, rs, null);
        }
    }
    public Order retrieveCustomerOrdersWithContentsHash(int userID, Date dateCreated){
        List<Order> orders = new ArrayList<Order>();
        Statement state = null;
        ResultSet rs = null;
        try {
            state = this.conn.createStatement();
            rs = state.executeQuery("SELECT order_id from CaS_Orders.orders WHERE user_id='" + userID + "' AND date_created='"+dateCreated+"'");
            if (rs.next()) {
                return this.retrieveOrder(rs.getInt("order_id"));
            }
            return null;
        } catch (SQLException a) {
            return null;
        } finally{
            this.closeConns(state, rs, null);
        }
    }
    public Order placeOrder(ShoppingCart cart) {
        Statement state = null;
        ResultSet rs = null;
        try {
            state = this.conn.createStatement();
            int hash = cart.getItemList().hashCode();
            rs = state.executeQuery("Select order_id from Cas_Orders.orders WHERE user_id='"+cart.getUserID()+"' AND date_created='"+cart.getDateCreated()+"'");
            if(rs.next()){
                return this.retrieveOrder(rs.getInt("user_id"));
            }
            int code = state.executeUpdate("Insert into Cas_Orders.orders (user_id, total_cost,item_hash, date_created) values('" + cart.getUserID() + "','" +cart.getTotalCost()+"','"+hash+"','"+cart.getDateCreated()+"')");
            
            int orderID = rs.getInt(2);
            
            for(Item item:cart.getItemList()){
                state.executeQuery("Insert into Cas_Orders.items_in_orders (order_id, item_id) values('" + cart.getUserID() + "','"+item.getID()+"')");
            }
            return new Order(orderID,cart.getUserID(),cart.getItemList(),hash,cart.getDateCreated(),cart.getTotalCost());
            //TODO CHANGE

        } catch (SQLException a) {
            System.out.println(a.toString());
            
            return null;
        } finally{
            this.closeConns(state, rs, null);
        }
    }
    
}

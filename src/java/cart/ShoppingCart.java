/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cart;

import java.util.List;
import java.sql.*;
import java.util.ArrayList;
import transaction.Transaction;

/**
 *
 * @author petermeckiffe
 */
public class ShoppingCart {
    private List<Item> items;
    private double cost;
    private Connection conn;
    public ShoppingCart(Connection conn){
        this.conn = conn;
        items = new ArrayList<Item>();
    }
    public Transaction addItem(Item item){
        Transaction tran;
        return tran;
    }
    public Transaction removeItem(Item item){
        this.items.add(item);
        this.cost+=item
    }
    public List<Item> getItemList(){
        return this.items;
    }
    public double getTotalCost(){
        return cost;
    }
}

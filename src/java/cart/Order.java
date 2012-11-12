/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cart;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 *
 * @author petermeckiffe
 */
public class Order {
    private int orderID;
    private int userID;
    private List<CustomerItem> items;
    private Date datePlaced;
    private int itemHash;
    private BigDecimal price;
    public Order(int orderID, int userID, List<CustomerItem> items, int hash, Date date, BigDecimal price){
        this.orderID = orderID;
        this.items = items;
        this.userID = userID;
        this.datePlaced = date;
        this.price = price;
        this.itemHash = hash;
    }
    public int getItemHash(){
        return this.itemHash;
    }
    public int getOrderID(){
        return this.orderID;
    }
    public int getUserID(){
        return this.userID;
    }
    public List<CustomerItem> getItems(){
        return this.items;
    }
    public Date getDatePlaced(){
        return this.datePlaced;
    }
    public BigDecimal getPrice(){
        return this.price;
    }
    @Override
    public String toString(){
        String returnString = String.format("--- Order ---\nOrder ID: %d\nCustomer ID: %d\nTotal price: %d\nDate Placed: %s\n- Item list -\n", this.orderID,this.userID,this.price,this.datePlaced.toString());
        for(Item i:this.items){
            returnString += i.toString()+"\n";
        }
        returnString += "--- End of Order ---\n";
        return null;
        
    }
}

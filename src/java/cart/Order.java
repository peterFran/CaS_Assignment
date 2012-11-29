/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cart;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import user.User;

/**
 *
 * @author petermeckiffe
 */
// Represents an order AFTER it has been placed to the database
public class Order {
    private int orderID;
    private User user;
    // List is customer items
    private List<CustomerItem> items;
    private Date datePlaced;
    private int itemHash;
    private BigDecimal price;
    private Timestamp stamp;
    public Order(int orderID, User user, List<CustomerItem> items, int hash, Timestamp stamp, BigDecimal price){
        this.orderID = orderID;
        this.items = items;
        this.user = user;
        this.stamp = stamp;
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
        return this.user.getID();
    }
    public Timestamp getTimestamp(){
        return this.stamp;
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
    public String getCustomerName(){
        return this.user.getFirstName()+" "+this.user.getLastName();
    }
    @Override
    public String toString(){
        String returnString = String.format("--- Order ---\nOrder ID: %d\nCustomer ID: %d\nTotal price: %d\nDate Placed: %s\n- Item list -\n", this.orderID,this.user.getID(),this.price,this.datePlaced.toString());
        for(Item i:this.items){
            returnString += i.toString()+"\n";
        }
        returnString += "--- End of Order ---\n";
        return null;
        
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cart;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import user.User;

/**
 *
 * @author petermeckiffe
 */
public class ShoppingCart implements Serializable{
    private List<CustomerItem> items;
    private User user;
    private Date dateCreated;
    private boolean checked=false;
    private Timestamp stamp;
    public ShoppingCart(User user){
        this.dateCreated = new Date();
        this.stamp = new Timestamp(this.dateCreated.getTime());
        this.user = user;
        this.items = new ArrayList<CustomerItem>();
    }
    public User getUser(){
        return this.user;
    }
    public int getUserID(){
        return this.user.getID();
    }
    public String getUserName(){
        return this.user.getFirstName()+" "+this.user.getLastName();
    }
    public List<CustomerItem> getItems(){
        return this.items;
    }
    public ShoppingCart(User user, List<CustomerItem> items, Date date){
        this.dateCreated = date;
        this.user = user;
        this.items = items;
        this.checked = true;
    }
    public int[] getIDs(){
        int[] itemIds = new int[this.items.size()];
        int count = 0;
        for(Item item:this.items){
            itemIds[count]=item.getID();
            count++;
        }
        return itemIds;
    }
    public boolean getChecked(){
        return this.checked;
    }
    public void addItem(Item item, int quantity){
        System.out.println("got");
        CustomerItem custItem = this.getCustomerItem(item.getID());
        
        if(custItem == null){
            this.items.add(new CustomerItem(item, quantity));
        }else{
            custItem.setQuantity(quantity);
        }
    }
    public void addItem(Item item){
        CustomerItem custItem = this.getCustomerItem(item.getID());
        if(custItem == null){
            this.items.add(new CustomerItem(item));
        }else{
            custItem.increment();
        }
    }
    public void decrementItem(Item item){
        CustomerItem custItem = this.getCustomerItem(item.getID());
        if(custItem != null){
            custItem.decrement();
        }
    }
    private CustomerItem getCustomerItem(int itemID){
        for(CustomerItem custItem:this.items){
            if(custItem.getID()==itemID){
                return custItem;
            }
        }
        return null;
    }
    public void removeItem(Item item){
        CustomerItem custItem = this.getCustomerItem(item.getID());
        if(custItem != null){
            this.items.remove(custItem);
        } 
    }
    public Date getDateCreated(){
        return this.dateCreated;
    }
    public Timestamp getTimeCreated(){
        return this.stamp;
    }
    
    public List<CustomerItem> getItemList(){
        return this.items;
    }
    public BigDecimal getTotalCost(){
        BigDecimal cost = new BigDecimal(0);
        for(CustomerItem i:items){
            cost = cost.add(i.getPrice().multiply(new BigDecimal(i.getQuantity())));
        }
        return cost;
    }
    @Override
    public String toString(){
        String string = "";
        for(CustomerItem a:this.items){
            string += a;
        }
        return string;
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cart;

import java.io.Serializable;
import java.math.BigDecimal;


/**
 *
 * @author petermeckiffe
 */
// Represents item in temporary memory
public class Item implements Serializable{
    private int ID;
    private String name;
    // Must use BigDecimal in order to represent currency correctly
    private BigDecimal price;

    public Item(int ID, String name, BigDecimal price) {
        this.ID = ID;
        this.name = name;
        this.price = price;
        //Search for Item on DB
    }

    public String getName() {
        return this.name;
    }

    public int getID() {
        return this.ID;
    }

    public BigDecimal getPrice(){
        return this.price;
    }
    public void setPrice(BigDecimal price){
        if(price.compareTo(new BigDecimal(0))>0){
            this.price = price;
        }
    }
    @Override
    public String toString(){
       return "Item ID: "+this.getID()+"\nItem Name: "+this.getName()+"\nItem Price: "+this.getPrice()+"\n";
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cart;

/**
 *
 * @author petermeckiffe
 */
// Extension of Item object, which allows for quantity
public class CustomerItem extends Item{
    private int quantity;
    public CustomerItem(Item item){
        super(item.getID(),item.getName(),item.getPrice());
        this.quantity=1;
    }
    public CustomerItem(Item item, int quantity){
        super(item.getID(),item.getName(),item.getPrice());
        this.quantity=quantity;
    }
    public void increment(){
        this.quantity++;
    }
    public void decrement(){
        this.quantity--;
    }
    public void setQuantity(int quantity){
        if(quantity>0){
            this.quantity=quantity;
        }
    }
    public int getQuantity(){
        return this.quantity;
    }
    @Override
    public String toString(){
        
        return String.format("%sQuantity: %d\n",super.toString(),this.quantity);
    }
}

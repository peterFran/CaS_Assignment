/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package transaction;

/**
 *
 * @author petermeckiffe
 */
public class Transaction {
    private static int globalID;
    private int ID;
    private boolean success;
    private String description;
    public Transaction(Boolean success, String description){
        this.description = description;
        this.success = success;
        this.globalID++;
        this.ID = this.globalID;
    }
    public String show(){
        return "ID: "+Integer.toString(ID)+"\nSuccess: "+this.success+"\nDescription: "+this.description;
    }
    
}

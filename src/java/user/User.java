/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package user;


/**
 *
 * @author petermeckiffe
 */
public class User {
    private String firstName;
    private String lastName;
    private int ID;

    public User(int ID, String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.ID = ID;
    }
    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getID() {
        return ID;
    }
    
}

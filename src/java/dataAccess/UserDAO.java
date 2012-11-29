/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dataAccess;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import user.User;

/**
 *
 * @author petermeckiffe
 */
// Acts as connection bean for CaS_Users database
public class UserDAO extends DAO{

    public UserDAO() {
        super("CaS_Users");
    }
    // Creates a new user
    public User createUser(String firstName, String lastName){
        Statement state;
        ResultSet rs;
        try {
            state = this.conn.createStatement();
            int code = state.executeUpdate("Insert into Cas_Users.users (firstname,lastname) values('" + firstName + "','" + lastName + "')");
            if(code!=-1){
                return this.getUser(firstName, lastName);
            }
            return null;
        } catch (SQLException a) {
            return null;
        }
    }
    
    // Retrieves an User object with an ID
    public User getUser(int ID) {
        Statement state = null;
        ResultSet rs = null;
        try {
            state = this.conn.createStatement();
            rs = state.executeQuery("SELECT firstname,lastname from CaS_Users.users WHERE user_id='" + ID + "'");
            if(rs.next()){
                return new User(ID, rs.getString("firstname"), rs.getString("lastname"));
            } else{
                return null;
            }
        } catch (SQLException a) {
            return null;
        } finally{
            this.closeConns(state, rs, null);
        }
    }
    
    // Deletes an User from system
    public boolean removeUser(int ID) {
        Statement state = null;
        try {
            state = this.conn.createStatement();
            int code = state.executeUpdate("delete from CaS_Users.users WHERE user_id='" + ID + "'");
            return true;
        } catch (SQLException a) {
            System.out.println(a);
            return false;
        } finally{
            this.closeConns(state, null, null);
        }
    }
    
    // Returns a User object if present on system with name
    public User getUser(String firstname, String lastname) {
        Statement state =null;
        ResultSet rs = null;
        try {
            state = this.conn.createStatement();
            rs = state.executeQuery("SELECT user_id from CaS_Users.users WHERE firstname='" + firstname+"' AND lastname='"+lastname+"'");
            if(rs.next()){
                return new User(rs.getInt("user_id"), firstname, lastname);
            }
            return null;
        } catch (SQLException a) {
            return null;
        } finally{
            this.closeConns(state, rs, null);
        }
    }
    
    // Returns a List object of all users
    public List<User> getUsers() {
        List<User> users = new ArrayList<User>();
        Statement state = null;
        ResultSet rs = null;
        try {
            state = this.conn.createStatement();
            rs = state.executeQuery("SELECT user_id from CaS_Users.users");
            while (rs.next()) {
                users.add(this.getUser(rs.getInt("user_id")));
            }
            return users;
            
        } catch (SQLException a) {
            return null;
        } finally{
            this.closeConns(state, rs, null);
        }
    }
}

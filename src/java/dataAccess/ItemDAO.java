/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dataAccess;

import cart.Item;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author petermeckiffe
 */
public class ItemDAO extends DAO{

    public ItemDAO(){
        super("CaS_Items");
    }

    public Item createItem(String name, BigDecimal price) {
        Statement state = null;
        try {
            if (name.length() == 0 || price.equals(BigDecimal.ZERO)) {
                return null;
            }
            state = this.conn.createStatement();
            int itemID = this.getIDWithName(name);
            if (itemID != -1) {
                return this.getItem(itemID);
            }

            int error = state.executeUpdate("Insert into Cas_Items.items (name,price) values('" + name + "','" + price + "')");
            if (error != -1) {
                itemID = this.getIDWithName(name);
                return new Item(itemID, name, price);
            }

            return null;
        } catch (SQLException a) {
            System.out.println(a.toString());
            return null;
        } finally {
            this.closeConns(state, null, null);
        }
    }

    public List<Item> getAllItems() {
        List<Item> items = new ArrayList<Item>();
        Statement state = null;
        ResultSet rs = null;
        try {
            state = this.conn.createStatement();
            rs = state.executeQuery("SELECT item_id from CaS_Items.items order by item_id");
            while (rs.next()) {
                items.add(this.getItem(rs.getInt("item_id")));
            }
            return items;
        } catch (SQLException a) {
            
            System.out.println(a.toString());
            return null;
        } finally{
            this.closeConns(state, rs, null);
        }
    }

    public Item getItem(int ID) {
        Statement state = null;
        ResultSet rs = null;
        try {
            state = this.conn.createStatement();
            rs = state.executeQuery("SELECT name,price from items WHERE item_id='" + ID + "'");
            if (rs.next()) {
                return new Item(ID, rs.getString(1), rs.getBigDecimal(2));
            }
            return null;

        } catch (SQLException a) {
            return null;
        } finally{
            this.closeConns(state, rs, null);
        }
    }

    public int getIDWithName(String name) {
        Statement state = null;
        ResultSet rs = null;
        try {
            state = this.conn.createStatement();
            rs = state.executeQuery("SELECT item_id from items WHERE name='" + name + "'");
            if (rs.next()) {
                return rs.getInt(1);
            }
            return -1;
        } catch (SQLException a) {
            return -1;
        } finally{
            this.closeConns(state, rs, null);
        }

    }

    public String getNameWithID(int ID) {
        Statement state = null;
        ResultSet rs = null;
        try {
            state = this.conn.createStatement();
            rs = state.executeQuery("SELECT name from items WHERE item_id='" + ID + "'");
            if (rs.next()) {
                return rs.getString(1);
            }
            return null;
        } catch (SQLException a) {
            return null;
        } finally{
            this.closeConns(state, rs, null);
        }
    }

    public boolean commitItem(Item item) {
        Statement state = null;
        try {
            state = this.conn.createStatement();
            int code = state.executeUpdate("update Cas_Items.items set price='" + item.getPrice() + "' where item_id='" + item.getID() + "'");
            return code != -1;
        } catch (SQLException a) {
            System.out.println(a);
            return false;
        } finally{
            this.closeConns(state, null, null);
        }
    }

    public Item refreshItem(Item item) {
        Statement state = null;
        ResultSet rs = null;
        try {
            state = this.conn.createStatement();
            rs = state.executeQuery("SELECT price from CaS_Items.items WHERE item_id='" + item.getID() + "'");
            if (rs.next()) {
                item.setPrice(rs.getBigDecimal(1));
            }
            return item;
        } catch (SQLException a) {
            return item;
        } finally{
            this.closeConns(state, rs, null);
        }
    }
}

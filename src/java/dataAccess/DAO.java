/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dataAccess;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 *
 * @author petermeckiffe
 */
public class DAO {
    protected Connection conn = null;
    public DAO(String dbName){
        try {
            System.out.println(new InitialContext().getEnvironment());
            Context envCtx = (Context) new InitialContext().lookup("java:comp/env");

            DataSource datasource = (DataSource) envCtx.lookup("jdbc/"+dbName);

            this.conn = datasource.getConnection();

        } catch (NamingException a) {
            System.out.println(a.toString());

        } catch (SQLException a) {
            System.out.println(a.toString());
        }
    }
    
    protected void closeConns(Statement s, ResultSet r, Connection c) {
        try {
            if (null != s) {
                s.close();
            }
        } catch (SQLException a) {
            //do nothing
        }
        try {
            if (null != r) {
                r.close();
            }
        } catch (SQLException a) {
            //do nothing
        }try {
            if (null != c) {
                c.close();
            }
        } catch (SQLException a) {
            //do nothing
        }
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        this.closeConns(null, null, conn);
    }
}

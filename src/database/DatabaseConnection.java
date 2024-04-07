/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author maharshigor
 */
public class DatabaseConnection {

    private final String host;
    private final String username;
    private final String password;
    private String db = "";
    private Statement st;
    
    private final HashMap<String, Connection> connections = new HashMap<>();

    public DatabaseConnection(String host, String username, String password) {
        this.host = host;
        this.username = username;
        this.password = password;
    }

    private Connection initializeConnection() {
        Connection con = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(
                    "jdbc:mysql://" + this.host + "/" + this.db,
                    this.username,
                    this.password);

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(DatabaseConnection.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
        return con;
    }
    
    public ResultSet executeQuery(String sql, String db) {
       
        ResultSet rs = null;
        try {
            Connection con = this.getConnection(db);
            this.st = con.createStatement();
            rs = st.executeQuery(sql);
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseConnection.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
        
        return rs;
    }
    
    private Connection getConnection(String db) {
        Connection con = null;
        this.setDb(db);
        try {
            if (!this.connections.containsKey(db) || this.connections.get(db).isClosed()) {
                con = this.initializeConnection();
                this.connections.put(db, con);
            } else {
                con = this.connections.get(db);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }        
        return con;
    }
    
    public void closeConnection(String db) {
        try {
            st.close();
            this.getConnection(db).close();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseConnection.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }
    
    public int executeUpdate(String sql, String db) {
        
        int rs = -1;
        try {
            Connection con = this.getConnection(db);
            st = con.createStatement();
            rs = st.executeUpdate(sql);
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseConnection.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
        return rs;
    }

    public String getHost() {
        return host;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getDb() {
        return db;
    }

    public void setDb(String db) {
        if (db == null) {
            this.db = "";
            return;
        }
        this.db = db;
    }

}

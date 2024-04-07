/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package database;

import helper.CLIExecute;
import helper.IO;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;

/**
 *
 * @author maharshigor
 */
public class DatabaseUtils {
    
    public void dumpDatabase(
            String username,
            String password,
            String fromDB,
            String toDB,
            JLabel label) {
        try {
            
            CLIExecute cli = new CLIExecute();
            cli.setCommand("mysqldump -u " + username + " --password=" + password + " " + fromDB + " > database-schema.sql");
            Thread t = new Thread(cli);
            t.start();
            IO.print("Generating dump : " + fromDB, label);
            t.join();
            IO.print("Generated dump : " + fromDB, label);

            cli.setCommand("mysql -u " + username + " --password=" + password + " " + toDB + " < database-schema.sql");
            t = new Thread(cli);
            t.start();
            IO.print("Retriving dump : " + toDB, label);
            t.join();
            IO.print("Retrived dump : " + toDB, label);
            
            cli = new CLIExecute();
            cli.setCommand("rm database-schema.sql");
            t = new Thread(cli);
            t.start();
            t.join();
            IO.print("Deleted dump!", label);
            
        } catch (InterruptedException ex) {
            Logger.getLogger(DatabaseUtils.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }
    
    public void truncateTables(String db, DatabaseConnection dbc) {
        dbc.executeUpdate("TRUNCATE TABLE credit_debit", db);
        dbc.executeUpdate("TRUNCATE TABLE payment_bill", db);
        dbc.executeUpdate("TRUNCATE TABLE payments", db);
        dbc.executeUpdate("TRUNCATE TABLE purchase_contact", db);
        dbc.executeUpdate("TRUNCATE TABLE purchase_product", db);
        dbc.executeUpdate("TRUNCATE TABLE purchase_serial", db);
        dbc.executeUpdate("TRUNCATE TABLE sale_contact", db);
        dbc.executeUpdate("TRUNCATE TABLE sale_product", db);
        dbc.executeUpdate("TRUNCATE TABLE sale_fscheme", db);
        dbc.executeUpdate("TRUNCATE TABLE sale_serial", db);
        dbc.closeConnection(db);
    }
    
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package helper;

import database.DatabaseConnection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.IntStream;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.SwingWorker;
import models.Contact;

/**
 *
 * @author maharshigor
 */
public class TransferWorker extends SwingWorker {
    
    private final String fromDB;
    private final String toDB;
    private final List<Contact> contacts;
    private final DatabaseConnection dbc;
    private final JLabel lStatus;
    private final JProgressBar pbTransferProgress;
    private final JButton btAddYear;
    

    @Override
    protected String doInBackground() throws Exception { 

        IntStream.range(0, contacts.size())
        .forEach(idx -> {
            calculateOpeningBalance(contacts.get(idx), fromDB, toDB);
            publish(idx);
        });
        String res = "Finished Transfering"; 
        return res;
    } 

    // Method 
    @Override 
    protected void process(List chunks) 
    { 
        // define what the event dispatch thread 
        // will do with the intermediate results 
        // received while the thread is executing 
        int val = (int) chunks.get(chunks.size() - 1); 
        pbTransferProgress.setValue(val);
        IO.print("Transferring balances for : " + contacts.get(val).getName(), lStatus);
    } 

    // Method 
    @Override 
    protected void done() {
        try {
            String res = (String) get();
            IO.print(res, lStatus);
            pbTransferProgress.setValue(contacts.size());
            dbc.closeConnection(fromDB);
            dbc.closeConnection(toDB);
            btAddYear.setEnabled(true);
        } catch (InterruptedException | ExecutionException ex) {
            Logger.getLogger(TransferWorker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void calculateOpeningBalance(Contact contact, String fromDB, String toDB) {
        double balance = contact.getOb();
        ResultSet rs;
        try {
            // Credit
            rs = dbc.executeQuery(
                "SELECT sum(amount) FROM credit_debit WHERE type = 'CREDIT' AND ptcode = " + contact.getIdContact(), fromDB);
            while (rs.next()) {
                balance -= rs.getDouble("sum(amount)");
            }
            
            // Debit
            rs = dbc.executeQuery(
                "SELECT sum(amount) FROM credit_debit WHERE type = 'DEBIT' AND ptcode = " + contact.getIdContact(), fromDB);
            while (rs.next()) {
                balance += rs.getDouble("sum(amount)");
            }
            
            // Receipt
            rs = dbc.executeQuery(
                "SELECT sum(amount) FROM payments WHERE type LIKE '% RECEIPT' AND ptcode = " + contact.getIdContact(), fromDB);
            while (rs.next()) {
                balance -= rs.getDouble("sum(amount)");
            }
            
            // Payments
            rs = dbc.executeQuery(
                "SELECT sum(amount) FROM payments WHERE type LIKE '% PAYMENT' AND ptcode = " + contact.getIdContact(), fromDB);
            while (rs.next()) {
                balance += rs.getDouble("sum(amount)");
            }
            
            // Purchase
            rs = dbc.executeQuery(
                "SELECT sum(total) FROM purchase_contact WHERE pcode = " + contact.getIdContact(), fromDB);
            while (rs.next()) {
                balance -= rs.getDouble("sum(total)");
            }
            
            // Sale
            rs = dbc.executeQuery(
                "SELECT sum(total) FROM sale_contact WHERE pcode = " + contact.getIdContact(), fromDB);
            while (rs.next()) {
                balance += rs.getDouble("sum(total)");
            }

            dbc.executeUpdate("UPDATE contact SET ob = " + balance + " WHERE idcontact = " + contact.getIdContact(), toDB);
        } catch (SQLException ex) {
            Logger.getLogger(TransferWorker.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    public TransferWorker(String fromDB, String toDB, List<Contact> contacts, DatabaseConnection dbc, JLabel lStatus, JProgressBar pbTransferProgress, JButton btAddYear) {
        this.fromDB = fromDB;
        this.toDB = toDB;
        this.contacts = contacts;
        this.dbc = dbc;
        this.lStatus = lStatus;
        this.pbTransferProgress = pbTransferProgress;
        this.btAddYear = btAddYear;
    }
    
}

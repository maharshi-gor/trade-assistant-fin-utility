/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package helper;

import javax.swing.JLabel;

/**
 *
 * @author maharshigor
 */
public class Validator {
    public static boolean validateDBName(String db, JLabel label) {
        if (db == null) {
            IO.print("Textfield not initialized", label);
        } else if (db.equals("")) {
            IO.print("Database name cannot be empty", label);
        } else if (db.contains(" ")) {
            IO.print("Database name should not contain spaces", label);
        } else {
            return true;
        }
        
        return false;
    }
}

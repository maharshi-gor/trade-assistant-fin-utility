package helper;


import javax.swing.JLabel;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author maharshigor
 */
public class IO {
    
    public static void print(String s, JLabel label) {
        System.out.println(s);
        label.setText(s);
    }
    
}

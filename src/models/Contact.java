package models;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author maharshigor
 */
public class Contact {
    private int idContact;
    private String name;
    private double ob;

    public Contact(int idContact, String name, double ob) {
        this.idContact = idContact;
        this.name = name;
        this.ob = ob;
    }

    public double getOb() {
        return ob;
    }

    public void setOb(double ob) {
        this.ob = ob;
    }

    public int getIdContact() {
        return idContact;
    }

    public void setIdContact(int idContact) {
        this.idContact = idContact;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    
}

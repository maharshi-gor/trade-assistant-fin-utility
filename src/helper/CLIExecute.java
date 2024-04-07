package helper;


import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author maharshigor
 */
public class CLIExecute implements Runnable {
    
    private String command;

    @Override
    public void run() {
        try {
            Runtime rt = Runtime.getRuntime();
            String os = System.getProperty("os.name");
            Process pr;
            if (os.contains("Windows")) {
                pr = rt.exec(new String[]{"cmd.exe", "/c", this.command});
            } else {
                pr = rt.exec(new String[]{"/bin/sh", "-c", this.command});
            }
            
            while (pr.isAlive()) {}
        } catch (IOException ex) {
            Logger.getLogger(CLIExecute.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }
    
    
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsonconverter.GUI.util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author Samuel
 */
public class HostName {

    String hostname = "Unknown";
    String userName = System.getProperty("user.name");

    public void takeUserInfo() {
        TimeZone tz = TimeZone.getTimeZone("Europe/Copenhagen");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(tz);

        try {
            InetAddress addr;
            addr = InetAddress.getLocalHost();
            hostname = addr.getHostName();
        } catch (UnknownHostException ex) {
            System.out.println("Hostname can not be resolved");
        }

        System.out.println("Host name: " + hostname);
        System.out.println("User name: " + userName);
        System.out.println("Time: " + sdf.format(new Date()));
        try {
            changeLookAndFeel();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(HostName.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void changeLookAndFeel() throws ClassNotFoundException {
        String LNF = UIManager.getSystemLookAndFeelClassName();
        try {
            UIManager.setLookAndFeel(LNF);
        } catch (InstantiationException ex) {
            Logger.getLogger(HostName.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(HostName.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(HostName.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

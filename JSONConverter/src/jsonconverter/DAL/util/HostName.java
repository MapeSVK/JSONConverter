/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsonconverter.DAL.util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import javafx.scene.control.Alert;

/**
 *
 * @author Samuel
 */
public class HostName {

    private String hostname;
    public String userName = System.getProperty("user.name");
    private static int onlyOnce = -1;

    public HostName() {
        takeUserInfo();
    }
    
    /* gets information about localHost */
    private void takeUserInfo() {
    
        if (onlyOnce == -1) {
            try {
                InetAddress addr;
                addr = InetAddress.getLocalHost();
                hostname = addr.getHostName();
                onlyOnce = 1;
            } catch (UnknownHostException ex) {
                Alert("Cannot get username", "System could not get user name from your computer. "
                        + "To solve this problem please write e-mail to jsonconverter@jsonconverter.com. "
                        + "Please send us name of the computer you are working on");
            }
        }
    }
  
    /* returns host name */
    public String getHostname()
    {
        return hostname;
    }

    /* reurns user name */
    public String getUserName() {
        return userName;
    }

    /* creates pop up alert window */
    public void Alert(String title, String text) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(text);
        alert.showAndWait();
    }
}


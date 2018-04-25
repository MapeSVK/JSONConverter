/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsonconverter;

import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 *
 * @author Mape
 */
public class MainFXMLController implements Initializable {
    
    @FXML
    private Label label;
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        try {
                    String hostname="Unknown";
                    String username =System.getProperty("user.name");
                    
                    TimeZone tz =  TimeZone.getTimeZone("Europe/Copenhagen");
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    sdf.setTimeZone(tz);
                    
                    InetAddress addr;
                    addr = InetAddress.getLocalHost();
                    hostname =addr.getHostName();
                    
                    System.out.println("host name : "+ hostname);
                    System.out.println("user name : "+ username);
                    System.out.println("time : "+ sdf.format(new Date()));
                            } catch (UnknownHostException ex) {
                    Logger.getLogger(MainFXMLController.class.getName()).log(Level.SEVERE, null, ex);
                }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}

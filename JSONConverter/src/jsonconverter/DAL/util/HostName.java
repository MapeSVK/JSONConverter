/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsonconverter.DAL.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 *
 * @author Samuel
 */
public class HostName {

    
    private String hostname;
    private String userName = System.getProperty("user.name");
    private int onlyOnce=-1;

    public HostName() {
        takeUserInfo();
    }
    /* gets information about localHost */
    private void takeUserInfo() {
      //  TimeZone tz = TimeZone.getTimeZone("Europe/Copenhagen");
     //   SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    //    sdf.setTimeZone(tz);
if(onlyOnce==-1)
{
        try {
            InetAddress addr;
            addr = InetAddress.getLocalHost();
            hostname = addr.getHostName();
            onlyOnce=0;
            System.out.println("DOtarlem");
        } catch (UnknownHostException ex) {
            System.out.println("Hostname can not be resolved");
        }
    }
    }

    public String getHostname() {
        return hostname;
    }

    public String getUserName() {
        return userName;
    }
    
}

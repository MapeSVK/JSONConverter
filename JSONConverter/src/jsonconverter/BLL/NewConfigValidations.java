/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsonconverter.BLL;

import java.util.List;
import jsonconverter.BE.Config;
import jsonconverter.BE.TaskInOurProgram;

/**
 *
 * @author Pepe15224
 */
public class NewConfigValidations {
    
    public boolean checkIfConfigExists(Config config,List<Config> configList)
    {
        for(Config configInList : configList)
        {
            System.out.println(configInList.getConfigName());
            if(configInList.getConfigName().equals(config.getConfigName()))
            {
                System.out.println("jest");
               return false;
               
            }
        }
        return true;
    }
  
}

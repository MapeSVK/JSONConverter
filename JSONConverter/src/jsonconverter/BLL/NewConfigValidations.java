/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsonconverter.BLL;

import java.util.List;
import jsonconverter.BE.Config;

/**
 *
 * @author Pepe15224
 */
public class NewConfigValidations {

    /* checks if config with this name aleready exists */
    public boolean checkIfConfigExists(Config config, List<Config> configList) {
        for (Config configInList : configList) {
            if (configInList.getConfigName().equals(config.getConfigName())) {
                return false;
            }
        }
        return true;
    }

}

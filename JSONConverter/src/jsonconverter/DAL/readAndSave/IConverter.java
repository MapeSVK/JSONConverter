/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsonconverter.DAL.readAndSave;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Pepe15224
 */
public interface IConverter {
    
    HashMap<String, Integer> getCSVHeaders();
    
    ArrayList<String> getCSVValues();
}

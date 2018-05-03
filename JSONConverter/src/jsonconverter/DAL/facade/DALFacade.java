/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsonconverter.DAL.facade;

import java.util.ArrayList;
import java.util.HashMap;
import jsonconverter.DAL.readAndSave.IConverter;

/**
 *
 * @author Pepe15224
 */
public class DALFacade {

    public HashMap<String, Integer> getCSVHeaders(IConverter converter)
    {
        return converter.getCSVHeaders();
    }
    
    public ArrayList<String> getCSVValues(IConverter converter)
    {
        return converter.getCSVValues();
    }
}

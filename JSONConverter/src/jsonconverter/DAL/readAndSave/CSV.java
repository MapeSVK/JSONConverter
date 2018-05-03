/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsonconverter.DAL.readAndSave;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Pepe15224
 */
public class CSV implements IConverter {

    String path;
    List<String> allLinesAsString;
    public CSV(String path) {
        this.path = path;
    }

    private List<String> getAllLinesAsString() 
    {
        try {
            java.nio.file.Path filePath = Paths.get(path);
            List<String> allLinesAsStrings = Files.readAllLines(filePath);
            return allLinesAsStrings;
        } catch (IOException ex) {
            Logger.getLogger(CSV.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public HashMap<String, Integer> getCSVHeaders() {
            HashMap<String,Integer> headersMap = new HashMap<>();
         String[] headers = getAllLinesAsString().get(0).split(";");
         
         for(int i=0;i<headers.length;i++)
         {
             if(headersMap.containsKey(headers[i]))
             {
                 String keyString = headers[i];
                 int orderNumber=1;
            while(headersMap.containsKey(keyString))
            {   keyString= headers[i];
                orderNumber++;
                keyString=keyString+orderNumber;
            }
            headersMap.put(keyString, i);
             }
             else
             headersMap.put(headers[i], i);
         }
         return headersMap;
    }

    @Override
    public ArrayList<String> getCSVValues() {
        ArrayList<String> CSVValuesList = new ArrayList();
        CSVValuesList.addAll(getAllLinesAsString());
        CSVValuesList.remove(0);
        return CSVValuesList;         
    }
    
}

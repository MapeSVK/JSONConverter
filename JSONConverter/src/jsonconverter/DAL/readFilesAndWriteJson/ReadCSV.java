/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsonconverter.DAL.readFilesAndWriteJson;

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
public class ReadCSV implements IConverter {

    private List<String> allLinesAsString;
    private ArrayList<String> CSVValuesList = new ArrayList();
    private List<String> headers = new ArrayList();
    HashMap<String, Integer> headersMap = new HashMap<>();
    public ReadCSV(String path) {
        getAllLinesAsString(path);
    }

    /* gets all lines from ReadCSV file and saves them inside of the list */
    private void getAllLinesAsString(String path) {
        try {
            java.nio.file.Path filePath = Paths.get(path);
             allLinesAsString = Files.readAllLines(filePath);
        } catch (IOException ex) {
            Logger.getLogger(ReadCSV.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /* splits the first line from ReadCSV file and then saves this line as a headers inside of the hashMap */
    @Override
    public HashMap<String, Integer> getFileHeaders() {
        if(headersMap.isEmpty())
        {
        String[] headers = allLinesAsString.get(0).split(";");

        for (int i = 0; i < headers.length; i++) {
            if (headersMap.containsKey(headers[i])) {
                String keyString = headers[i];
                int orderNumber = 1;
                while (headersMap.containsKey(keyString)) {
                    keyString = headers[i];
                    orderNumber++;
                    keyString = keyString + orderNumber;
                }
                headersMap.put(keyString, i);
            } else {
                if(i==0)
                    headersMap.put(headers[i].substring(1),i);
                else             
                headersMap.put(headers[i], i);
            }
        }
        }        
        return headersMap;
    }

    /* returns lines with values from ReadCSV value except for the first line */
    @Override
    public ArrayList<String> getFileValues() {
        if(CSVValuesList.isEmpty())
        {
        CSVValuesList.addAll(allLinesAsString);
        CSVValuesList.remove(0);
        }
        return CSVValuesList;
    }

    /*returns list of Headers from the file */
    @Override
    public List<String> getOnlyFileHeaders() {
        if(headers.isEmpty())
        {
        headers.clear();
        String[] headersString = allLinesAsString.get(0).split(";");
        for (int i = 0; i < headersString.length; i++) {
            if (headers.contains(headersString[i])) {
                String keyString = headersString[i];
                int orderNumber = 1;
                while (headers.contains(keyString)) {
                    keyString = headersString[i];
                    orderNumber++;
                    keyString = keyString + orderNumber;
                }
                headers.add(keyString);
            } else if(!headers.contains(headersString[i])) {
                if(i==0)
                    headers.add(headersString[i].substring(1));
                else
                headers.add(headersString[i]);
            }
        }     
        }
        return headers;
    }
}

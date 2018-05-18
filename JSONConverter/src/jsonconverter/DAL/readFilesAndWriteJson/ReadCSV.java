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

    private String path;
    private List<String> allLinesAsString;

    public ReadCSV(String path) {
        this.path = path;
    }

    /* gets all lines from ReadCSV file and saves them inside of the list */
    private List<String> getAllLinesAsString() {
        try {
            java.nio.file.Path filePath = Paths.get(path);
            List<String> allLinesAsStrings = Files.readAllLines(filePath);
            return allLinesAsStrings;
        } catch (IOException ex) {
            Logger.getLogger(ReadCSV.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /* splits the first line from ReadCSV file and then saves this line as a headers inside of the hashMap */
    @Override
    public HashMap<String, Integer> getFileHeaders() {
        HashMap<String, Integer> headersMap = new HashMap<>();
        String[] headers = getAllLinesAsString().get(0).split(";");

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
                headersMap.put(headers[i], i);
            }
        }
        return headersMap;
    }

    /* returns lines with values from ReadCSV value except for the first line */
    @Override
    public ArrayList<String> getFileValues() {
        ArrayList<String> CSVValuesList = new ArrayList();
        CSVValuesList.addAll(getAllLinesAsString());
        CSVValuesList.remove(0);
        return CSVValuesList;
    }

    /*returns list of Headers from the file */
    @Override
    public List<String> getOnlyFileHeaders() {
        List<String> headers = new ArrayList();
        headers.clear();
        String[] headersString = getAllLinesAsString().get(0).split(";");
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
            } else {
                headers.add(headersString[i]);
            }
        }
        return headers;
    }
}

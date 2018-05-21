/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsonconverter.DAL.readFilesAndWriteJson;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import jsonconverter.BE.JSONObject;

/**
 *
 * @author Pepe15224
 */
public class WriteJSON {

    /* creates json file from JSONObject list */
    public void createJsonFile(String fileName, File filePath, List<JSONObject> jsonList) {
        try {
            File fii = new File(filePath, fileName + ".json");
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            mapper.writeValue(fii, jsonList);
            System.out.println("JSON file created");
        } catch (IOException ex) {
            Logger.getLogger(WriteJSON.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

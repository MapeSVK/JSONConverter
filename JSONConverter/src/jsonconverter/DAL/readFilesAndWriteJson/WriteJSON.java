/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsonconverter.DAL.readFilesAndWriteJson;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import jsonconverter.BE.History;
import jsonconverter.BE.JSONObject;



public class WriteJSON {
    boolean success = false;

    /* creates json file from JSONObject list */
    public void createJsonFile(String fileName, File filePath, List<JSONObject> jsonList){
        try {
            File fii = new File(filePath, fileName + ".json");
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            mapper.writeValue(fii, jsonList);
            System.out.println("JSON file created");
            success = true;
            
        } catch (FileNotFoundException ex) {
            
            success = false;
            
        } catch (IOException e) {
            success = false;
        }       
    } 

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
    
    /* creates pop up alert window */
    public void Alert(String title, String text) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(text);
        alert.showAndWait();
    }

}

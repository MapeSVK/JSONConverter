/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsonconverter.DAL.facade;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import jsonconverter.BE.Config;
import jsonconverter.BE.JSONObject;
import jsonconverter.DAL.manager.SuperFakeDb;
import jsonconverter.DAL.readFilesAndWriteJson.IConverter;
import jsonconverter.DAL.readFilesAndWriteJson.WriteJSON;

/**
 *
 * @author Pepe15224
 */
public class DALFacade {

    private WriteJSON createJson = new WriteJSON();
    private SuperFakeDb fake = new SuperFakeDb();  // <-------------SUPER FAKE DB

    /* returns hashMap of headers from file (Headers are keys and numbers are values) */
    public HashMap<String, Integer> getFileHeaders(IConverter converter) {
        return converter.getFileHeaders();
    }

    /* returns values from the selected file */
    public ArrayList<String> getFileValues(IConverter converter) {
        return converter.getFileValues();
    }

    /* creates json file from JSONObject list */
    public void createJsonFile(String fileName, String filePath, List<JSONObject> jsonList) {
        createJson.createJsonFile(fileName, filePath, jsonList);
    }

    /*returns list of Headers from the file */
    public List<String> getOnlyFileHeaders(IConverter converter) {
        return converter.getOnlyFileHeaders();
    }
    
    //----------------------------------------------------------------SUPERFAKE DB------------------------------------------------------------------------------------------------
    public List<Config> getFakeConfigDatabase() {
        return fake.getFakeConfigDatabase();
    }
    
    public void addToFakeConfigDatabase(Config config) {
        fake.addToFakeConfigDatabase(config);
    }
}

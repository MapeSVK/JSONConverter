/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsonconverter.BLL;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import jsonconverter.BE.Config;
import jsonconverter.BE.JSONObject;
import jsonconverter.DAL.facade.DALFacade;
import jsonconverter.DAL.readFilesAndWriteJson.IConverter;

/**
 *
 * @author Samuel
 */
public class BLLManager {

    private DALFacade manager = new DALFacade();

    /* returns hashMap of headers from file (Headers are keys and numbers are values) */
    public HashMap<String, Integer> getFileHeaders(IConverter converter) {
        return manager.getFileHeaders(converter);
    }

    /* returns values from the selected file */
    public ArrayList<String> getFileValues(IConverter converter) {
        return manager.getFileValues(converter);
    }

    /* creates json file from JSONObject list */
    public void createJsonFile(String fileName, String filePath, List<JSONObject> jsonList) {
        manager.createJsonFile(fileName, filePath, jsonList);
    }
    
    /*returns list of Headers from the file */
    public List<String> getOnlyFileHeaders(IConverter converter)
    {
        return manager.getOnlyFileHeaders(converter);
    }
    
     //----------------------------------------------------------------SUPERFAKE DB------------------------------------------------------------------------------------------------
    public List<Config> getFakeConfigDatabase() {
        return manager.getFakeConfigDatabase();
    }
    
    public void addToFakeConfigDatabase(Config config) {
        manager.addToFakeConfigDatabase(config);
    }
}

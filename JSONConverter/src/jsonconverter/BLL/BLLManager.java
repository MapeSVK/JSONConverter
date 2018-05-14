/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsonconverter.BLL;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import jsonconverter.BE.Config;
import jsonconverter.BE.History;
import jsonconverter.BE.TaskInOurProgram;
import jsonconverter.DAL.facade.DALFacade;

/**
 *
 * @author Samuel
 */
public class BLLManager {

    private DALFacade manager = new DALFacade();
    private Converter convertJason = new Converter();
    private NewConfigValidations configValidations= new NewConfigValidations();

     /* returns hashMap of headers from file (Headers are keys and numbers are values) */
    public HashMap<String, Integer> getFileHeaders() {
        return manager.getFileHeaders();
    }

    /* returns values from the selected file */
    public ArrayList<String> getFileValues() {
        return manager.getFileValues();
    }

    /* creates json file from JSONObject list */
    public void createJsonFile(String fileName, File filePath, TaskInOurProgram currentTask) throws InterruptedException {    
      manager.createJsonFile(fileName, filePath, convertJason.returnJasonObjects(currentTask));
    }

    /*returns list of Headers from the file */
    public List<String> getOnlyFileHeaders() {
        return manager.getOnlyFileHeaders();
    }
    
public void getConverter(TaskInOurProgram currentTask) {
         manager.getConverter(currentTask);
         
    }

    public void setConverter(String fileType,String filePath) {
        manager.setConverter(fileType, filePath);
    }
    
    
    public void saveConfigToDatabase(Config config){
        manager.saveConfigToDatabase(config);
    }
    //----------------------------------------------------------------SUPERFAKE DB------------------------------------------------------------------------------------------------
    public List<Config> getFakeConfigDatabase() {
        return manager.getFakeConfigDatabase();
    }

    public void addToFakeConfigDatabase(Config config) {
        manager.addToFakeConfigDatabase(config);
    }
    
    public boolean checkIfConfigExists(Config config)
    {
        return configValidations.checkIfConfigExists(config,getFakeConfigDatabase());
    }
    
    /* HISTORY */
    
    public List<History> getAllHistory() {
        return manager.getAllHistory();
    }
}

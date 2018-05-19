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
    private NewConfigValidations configValidations = new NewConfigValidations();

    //- - - - - - - - - - - - - - - - - - - - CREATE JASON - - - - - - - - - - - - - - - - - - - -
    /* creates json file from JSONObject list */
    public void createJsonFile(String fileName, File filePath, TaskInOurProgram currentTask) throws InterruptedException {
        manager.createJsonFile(fileName, filePath, convertJason.returnJasonObjects(currentTask));
    }

    //- - - - - - - - - - - - - - - - - - - - CONVERTER - - - - - - - - - - - - - - - - - - - -
    /* returns hashMap of headers from file (Headers are keys and numbers are values) */
    public HashMap<String, Integer> getFileHeaders() {
        return manager.getFileHeaders();
    }

    /* returns values from the selected file */
    public ArrayList<String> getFileValues() {
        return manager.getFileValues();
    }

    /*returns list of Headers from the file */
    public List<String> getOnlyFileHeaders() {
        return manager.getOnlyFileHeaders();
    }
 /* gets proper converter for current task */
    public void getConverter(TaskInOurProgram currentTask) {
        manager.getConverter(currentTask);

    }
/* sets proper converter for current task */
    public void setConverter(String fileType, String filePath) {
        manager.setConverter(fileType, filePath);
    }

    //- - - - - - - - - - - - - - - - - - - - CONFIG - - - - - - - - - - - - - - - - - - - -
/* saves config to the database */
    public void saveConfigToDatabase(Config config, boolean isEditMode) {
        manager.saveConfigToDatabase(config, isEditMode);
    }
    
    /* removes config from the database */
     public void removeConfigFromDatabase(Config config) {
        manager.removeConfigFromDatabase(config);
    }

    /* gets all available configs for current user */
    public List<Config> getAllConfigs() {
        return manager.getAllConfigs();
    }

    //- - - - - - - - - - - - - - - - - - - - HOSTNAME - - - - - - - - - - - - - - - - - - - -
    /* returns local Hostname */
    public String getHostname() {
        return manager.getHostname();
    }

    /* returns local Username */
    public String getUserName() {
        return manager.getUserName();
    }

    //- - - - - - - - - - - - - - - - - - - - HISTORY - - - - - - - - - - - - - - - - - - - -
    /* getting HISTORY */
    public List<History> getAllHistory() {
        return manager.getAllHistory();
    }
}

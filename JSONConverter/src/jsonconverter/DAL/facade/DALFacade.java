/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsonconverter.DAL.facade;

import java.io.File;
import java.util.List;
import jsonconverter.BE.Config;
import jsonconverter.BE.History;
import jsonconverter.BE.JSONObject;
import jsonconverter.BE.TaskInOurProgram;
import jsonconverter.DAL.connection.DALConfig;
import jsonconverter.DAL.connection.DALHistory;
import jsonconverter.DAL.readFilesAndWriteJson.IConverter;
import jsonconverter.DAL.readFilesAndWriteJson.ReadCSV;
import jsonconverter.DAL.readFilesAndWriteJson.ReadEXEL;
import jsonconverter.DAL.readFilesAndWriteJson.ReadXML;
import jsonconverter.DAL.readFilesAndWriteJson.WriteJSON;
import jsonconverter.DAL.util.HostName;

/**
 *
 * @author Pepe15224
 */
public class DALFacade {

    private IConverter converter;
    private WriteJSON createJson = new WriteJSON();
    private DALHistory DALhistory = new DALHistory();
    private DALConfig config = new DALConfig();
    private HostName hostName = new HostName();

    //- - - - - - - - - - - - - - - - - - - - CREATE JASON - - - - - - - - - - - - - - - - - - - -
    /* creates json file from JSONObject list */
    public boolean createJsonFile(String fileName, File filePath, List<JSONObject> jsonList) {
        return createJson.createJsonFile(fileName, filePath, jsonList);
    }

    //- - - - - - - - - - - - - - - - - - - - CONVERTER - - - - - - - - - - - - - - - - - - - -
    /*returns list of Headers from the file */
    public List<String> getOnlyFileHeaders() {
        return converter.getOnlyFileHeaders();
    }

    /* gets proper converter for current task */
    public void getConverter(TaskInOurProgram currentTask) {
        currentTask.setConverter(converter);
    }

    /* sets proper converter for current task */
    public void setConverter(String fileType, String filePath) {
        if (fileType.equals(".csv")) {
            converter = new ReadCSV(filePath);
        } else if (fileType.equals(".xlsx")) {
            converter = new ReadEXEL(filePath);
        } else if (fileType.equals(".xml")) {
            converter = new ReadXML(filePath);
        }
    }

    //- - - - - - - - - - - - - - - - - - - - HISTORY - - - - - - - - - - - - - - - - - - - -
    /* getting HISTORY */
    public List<History> getAllHistory() {
        
        return DALhistory.getAllHistory();
    }

    public void addNewHistoryToDatabase(History history) {
        DALhistory.addHistory(history);
    }

    //- - - - - - - - - - - - - - - - - - - - CONFIG - - - - - - - - - - - - - - - - - - - -
    /* saves config to the database */
    public void saveConfigToDatabase(Config newconfig) {
        config.saveConfigToDatabase(newconfig);
    }

    /* gets all available configs for current user */
    public List<Config> getAllAvailableConfigs() {
        return config.getAllAvailableConfigs(hostName.getUserName());
    }
    
    /* gets all configs for current user */
    public List<Config> getAllConfigs() {
        return config.getAllConfigs();
    }

    /* removes config from the database */
    public void removeConfigFromDatabase(Config removeConfig) {
        config.removeConfigFromDatabase(removeConfig);
    }
    /* updates selected config */
    public void editConfig(Config chosenConfig)
    {
        config.editConfig(chosenConfig);
    }

    //- - - - - - - - - - - - - - - - - - - - HOSTNAME - - - - - - - - - - - - - - - - - - - -
    /* returns local Hostname */
    public String getHostname() {
        return hostName.getHostname();
    }

    /* returns local Username */
    public String getUserName() {
        return hostName.getUserName();
    }
}

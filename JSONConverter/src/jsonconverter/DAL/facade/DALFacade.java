/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsonconverter.DAL.facade;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
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
    private DALHistory history = new DALHistory();
    private DALConfig config = new DALConfig();
    private HostName hostName = new HostName();

    /* returns hashMap of headers from file (Headers are keys and numbers are values) */
    public HashMap<String, Integer> getFileHeaders() {
        return converter.getFileHeaders();
    }

    /* returns values from the selected file */
    public ArrayList<String> getFileValues() {
        return converter.getFileValues();
    }

    /* creates json file from JSONObject list */
    public void createJsonFile(String fileName, File filePath, List<JSONObject> jsonList) {
        createJson.createJsonFile(fileName, filePath, jsonList);
    }

    /*returns list of Headers from the file */
    public List<String> getOnlyFileHeaders() {
        return converter.getOnlyFileHeaders();
    }

    public void removeConfigFromDatabase(Config removeConfig) {
        config.removeConfigFromDatabase(removeConfig);
    }

    public void getConverter(TaskInOurProgram currentTask) {
        currentTask.setConverter(converter);
    }

    public void setConverter(String fileType, String filePath) {
        if (fileType.equals(".csv")) {
            converter = new ReadCSV(filePath);
        } else if (fileType.equals(".xlsx")) {
            converter = new ReadEXEL(filePath);
        } else if (fileType.equals(".xml")) {
            converter = new ReadXML(filePath);
        }
    }

    /* getting HISTORY */
    public List<History> getAllHistory() {

        return history.getAllHistory();
    }

    public void saveConfigToDatabase(Config newConfig) {
        config.saveConfigToDatabase(newConfig);
    }

    public List<Config> getAllConfigs() {
        return config.getAllConfigs(hostName.getUserName());
    }

    public String getHostname() {
        return hostName.getHostname();
    }

    public String getUserName() {
        return hostName.getUserName();
    }
}

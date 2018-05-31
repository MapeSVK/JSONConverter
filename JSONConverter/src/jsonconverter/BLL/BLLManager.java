/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsonconverter.BLL;

import jsonconverter.BLL.Validations.Validations;
import java.io.File;
import java.util.List;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
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
    private Validations validations = new Validations();

    //- - - - - - - - - - - - - - - - - - - - CREATE JASON - - - - - - - - - - - - - - - - - - - -
    /* creates json file from JSONObject list */
    public boolean createJsonFile(String fileName, File filePath, TaskInOurProgram currentTask) throws InterruptedException {
            return manager.createJsonFile(fileName, filePath, convertJason.returnJasonObjects(currentTask));

        }
    
//    public boolean isFileSaved() {
//        return manager.isFileSaved();
//    }

    //- - - - - - - - - - - - - - - - - - - - CONVERTER - - - - - - - - - - - - - - - - - - - -
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
    public void saveConfigToDatabase(Config config) {
        manager.saveConfigToDatabase(config);
    }

    /* removes config from the database */
    public void removeConfigFromDatabase(Config config) {
        manager.removeConfigFromDatabase(config);
    }

    /* gets all configs for current user */
    public List<Config> getAllConfigs() {
        return manager.getAllConfigs();
    }

    /* gets all available configs for current user */
    public List<Config> getAllAvailableConfigs() {
        return manager.getAllAvailableConfigs();
    }
    
    /* updates selected config */
    public void editConfig(Config config)
    {
        manager.editConfig(config);
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

    /* adds history to the database */
    public void addNewHistoryToDatabase(History history) {
        manager.addNewHistoryToDatabase(history);
    }

    //- - - - - - - - - - - - - - - - - - - - VALIDATIONS - - - - - - - - - - - - - - - - - - - -
    /* checks if config with this name aleready exists */
    public boolean checkIfConfigExists(Config config, List<Config> configList) {
        return validations.checkIfConfigExists(config, configList);

    }

    public void changeColorIfWrong(Node node, String fieldText, List<String> headersList) {
        validations.changeColorIfWrong(node, fieldText, headersList);
    }

    public boolean wrongInputValidation(AnchorPane pane) {
        return validations.wrongInputValidation(pane);
    }
    
    /* checks if config matches file */
    public boolean checkIfFileMatchesConfig(Config config,List<String> fileHeaders)
    {
        return validations.checkIfFileMatchesConfig(config, fileHeaders);
    }
    
    /* removes configs that dont match chosen file */
    public List<Config> checkIfYouCanUseConfig(List<String> fileHeaders,List<Config> configs)
    {
        return validations.checkIfYouCanUseConfig(fileHeaders, configs);
    }
}

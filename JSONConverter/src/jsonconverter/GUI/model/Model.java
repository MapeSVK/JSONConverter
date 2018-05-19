package jsonconverter.GUI.model;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import jsonconverter.BE.Config;
import jsonconverter.BE.History;
import jsonconverter.BE.TaskInOurProgram;
import jsonconverter.BLL.BLLManager;

public class Model {

    private BLLManager manager = new BLLManager();

    /* contains configs from the database which can be chosen in the choiceBox */
    private ObservableList<Config> configChoiceBoxItems = FXCollections.observableArrayList();
    /* contains every task in tableview */
    private ObservableList<TaskInOurProgram> tasksInTheTableView = FXCollections.observableArrayList();
    private ObservableList<History> allHistoryObservableArrayList = FXCollections.observableArrayList();
    private ObservableList<Config> allConfigObservableArrayList = FXCollections.observableArrayList();


    /* returns hashMap of headers from file (Headers are keys and numbers are values) */
    public HashMap<String, Integer> getFileHeaders() {
        return manager.getFileHeaders();
    }

    /* adding tasks to the observableArrayList */
    public void addTask(TaskInOurProgram task) {
        tasksInTheTableView.add(task);
    }

    /* getting observableArrayList with the tasks */
    public ObservableList<TaskInOurProgram> getTasksInTheTableView() {
        return tasksInTheTableView;
    }

    /* returns values from the selected file */
    public ArrayList<String> getFileValues() {
        return manager.getFileValues();
    }

    /* creates json file from JSONObject list */
    public void createJsonFile(String fileName, File filePath, TaskInOurProgram cuttentTask) throws InterruptedException {
        manager.createJsonFile(fileName, filePath, cuttentTask);
    }

    /*returns list of Headers from the file */
    public List<String> getOnlyFileHeaders() {
        return manager.getOnlyFileHeaders();
    }

    public void getConverter(TaskInOurProgram currentTask) {
        manager.getConverter(currentTask);
    }

    public void setConverter(String fileType, String filePath) {
        manager.setConverter(fileType, filePath);
    }
    public void removeConfigToDatabase(Config config) {
        manager.removeConfigFromDatabase(config);
        allConfigObservableArrayList.remove(config);
    }

    public void saveConfigToDatabase(Config config) {
        manager.saveConfigToDatabase(config);
        allConfigObservableArrayList.add(config);
    }

//    public void loadConfigFromDatabase() {
//        allConfigObservableArrayList.clear();
//        allConfigObservableArrayList.addAll(manager.getAllConfigs());
//    }

    public ObservableList<Config> getAllConfigObservableArrayList() {
        return allConfigObservableArrayList;
    }

    /* HISTORY */

 /* add history to a database after some action is done */
    public void addHistoryToTheDatabase(History history) {
        allHistoryObservableArrayList.add(history);
        //manager.addNewHistoryToDatabase(history);
    }

    public ObservableList<History> getAllHistoryObservableArrayList() {
        return allHistoryObservableArrayList;
    }

    public void loadHistoryFromDatabase() {
        allHistoryObservableArrayList.clear();
        allHistoryObservableArrayList.addAll(manager.getAllHistory());
    }
  public String getHostname() {
        return manager.getHostname();
    }

    public String getUserName() {
        return manager.getUserName();
    } 
     /* gets all available configs for current user */
    public List<Config> getAllAvailableConfigs() {
        return manager.getAllAvailableConfigs();
    }
    
    /* gets all configs for current user */
    public List<Config> getAllConfigs() {
        return manager.getAllConfigs();
    }
    
    public void checkIfYouCanUseConfig()
    {
        allConfigObservableArrayList.clear();
        int checkForErrors=0;
      int i=0;
      for(Config config : getAllAvailableConfigs())
      {
          System.out.println(config.getConfigName());
          i=0;
          checkForErrors=0;
          while(i<15)
          {
            String configString = config.getAllGetters(i);
       
            if(configString.contains("&&") && !configString.equals(""))
            {     System.out.println(configString);
            
                String[] splitedConfig = configString.split("&&");
                if(!getOnlyFileHeaders().contains(splitedConfig[0]))
                {
                    System.out.println(splitedConfig[0]);
                checkForErrors++;
                }
                if(!getOnlyFileHeaders().contains(splitedConfig[1]))
                {
                     System.out.println(splitedConfig[1]);
                    checkForErrors++;   
                }
            }           
          else  if(!getOnlyFileHeaders().contains(configString) && !configString.equals(""))
                {
                    System.out.println(configString);
                  checkForErrors++;
                }
              i++;
          }
          if(checkForErrors==0)
          {
              System.out.println(config.getConfigName());
              allConfigObservableArrayList.add(config);
          }
      }
    }
    //- - - - - - - - - - - - - - - - - - - - VALIDATIONS - - - - - - - - - - - - - - - - - - - -
    public boolean checkIfConfigExists(Config config)
    {
        return manager.checkIfConfigExists(config, manager.getAllConfigs());
    }
}

package jsonconverter.GUI.model;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import jsonconverter.BE.Config;
import jsonconverter.BE.JSONObject;
import jsonconverter.BE.TaskInOurProgram;
import jsonconverter.BLL.BLLManager;
import jsonconverter.DAL.readFilesAndWriteJson.IConverter;

public class Model {

    private BLLManager manager = new BLLManager();

    /* contains configs from the database which can be chosen in the choiceBox */
    private ObservableList<String> configChoiceBoxItems = FXCollections.observableArrayList();
    /* contains every task in tableview */
    private ObservableList<TaskInOurProgram> tasksInTheTableView = FXCollections.observableArrayList();


    /* returns hashMap of headers from file (Headers are keys and numbers are values) */
    public HashMap<String, Integer> getFileHeaders(IConverter converter) {
        return manager.getFileHeaders(converter);
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
    public ArrayList<String> getFileValues(IConverter converter) {
        return manager.getFileValues(converter);
    }

    /* creates json file from JSONObject list */
    public void createJsonFile(String fileName, File filePath, IConverter converter, Config config) {
        manager.createJsonFile(fileName, filePath, converter, config);
        System.out.println("chuuuj Manager");
    }

    /*returns list of Headers from the file */
    public List<String> getOnlyFileHeaders(IConverter converter) {
        return manager.getOnlyFileHeaders(converter);
    }

    //----------------------------------------------------------------SUPERFAKE DB------------------------------------------------------------------------------------------------
    public List<Config> getFakeConfigDatabase() {
        return manager.getFakeConfigDatabase();
    }

    public void addToFakeConfigDatabase(Config config) {
        manager.addToFakeConfigDatabase(config);
        fakeConfig.add(config);
    }
    private ObservableList<Config> fakeConfig = FXCollections.observableArrayList();

    public ObservableList<Config> getFakeConfig() {
        addFake();
        return fakeConfig;
    }
    
    public boolean checkIfConfigExists(Config config)
    {
        return manager.checkIfConfigExists(config);
    }
    
    public void addFake() {
    fakeConfig.add(new Config(1, "Actual start", "Actual start", "Order Type", "Order", "System status", "User status", 
            "Created on", "Actual start", "Opr. short text","Priority", "Actual start", "Lat.finish date", "Earl.start date", "Latest start", 
            "Normal duration", "test config"));
    }
}

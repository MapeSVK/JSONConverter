package jsonconverter.GUI.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

    /* importing configs from the database and then adding them to the configChoiceBoxItems ObservableArrayList */
    public void addConfigsToConfigChoiceBox() {
        configChoiceBoxItems.addAll("First", "Second", "Third"); // !temporary
    }

    /* calling the method which is responsible for adding items to observableArrayList and then getting this observableArrayList*/
    public ObservableList<String> getConfigChoiceBoxItems() {
        addConfigsToConfigChoiceBox();
        return configChoiceBoxItems;
    }

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
    public void createJsonFile(String fileName, String filePath, List<JSONObject> jsonList) {
        manager.createJsonFile(fileName, filePath, jsonList);
    }
    
     /*returns list of Headers from the file */
    public List<String> getOnlyFileHeaders(IConverter converter)
    {
        return manager.getOnlyFileHeaders(converter);
    }
}

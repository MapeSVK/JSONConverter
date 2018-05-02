
package jsonconverter.GUI.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import jsonconverter.BE.TaskInOurProgram;


public class Model {
    
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

    /* adding tasks to the observableArrayList */
    public void addTask(TaskInOurProgram task){
        tasksInTheTableView.add(task);
    }
    
    /* getting observableArrayList with the tasks */
    public ObservableList<TaskInOurProgram> getTasksInTheTableView() {
        return tasksInTheTableView;
    }
    
    
    
    
    
    
    

    
}

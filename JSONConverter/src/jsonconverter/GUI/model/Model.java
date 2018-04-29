
package jsonconverter.GUI.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class Model {
    
    /* contains configs from the database which can be chosen in the choiceBox */
    private ObservableList<String> configChoiceBoxItems = FXCollections.observableArrayList();

    
    /* importing configs from the database and then adding them to the configChoiceBoxItems ObservableArrayList */
    public void addConfigsToConfigChoiceBox() {
        configChoiceBoxItems.addAll("First", "Second", "Third"); // !temporary
    }
   
    
    /* calling the method which is responsible for adding items to observableArrayList and then getting this observableArrayList*/
    public ObservableList<String> getConfigChoiceBoxItems() {
        addConfigsToConfigChoiceBox();
        return configChoiceBoxItems;
    }

    
    
    

    
}

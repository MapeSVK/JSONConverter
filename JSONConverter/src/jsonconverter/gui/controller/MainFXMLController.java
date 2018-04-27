/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsonconverter.GUI.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author Samuel
 */
public class MainFXMLController implements Initializable {

    @FXML
    private Button importFileButton;
    @FXML
    private TableColumn<?, ?> nameOfTheFileColumn;
    @FXML
    private TableColumn<?, ?> configNameColumn;
    @FXML
    private TableColumn<?, ?> progressBarColumn;
    @FXML
    private TableColumn<?, ?> stopButtonColumn;
    @FXML
    private TableColumn<?, ?> pauseButtonColumn;
    @FXML
    private ChoiceBox<?> configChoiceBox;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void importFileButtonClick(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter filtermP4 = new FileChooser.ExtensionFilter("select mp4","*.mp4");
            FileChooser.ExtensionFilter filterMpeg4 = new FileChooser.ExtensionFilter("select mpeg4","*.mpeg4");
            fileChooser.getExtensionFilters().addAll(filtermP4,filterMpeg4);
            File file = fileChooser.showOpenDialog(null);
            
            if (file!=null){ //if statement only to avoid nullPointException after pressing "cancel" in filechooser
            String filePath = file.toString();
            FileTextField.setText(filePath); //insert path of the file into the textField
            }
            else {
                System.out.println("File was not choosen.");
            }              
    }

    @FXML
    private void createNewConfigButtonClick(ActionEvent event) {
    }

    @FXML
    private void addTaskButtonClick(ActionEvent event) {
    }

    @FXML
    private void chooseDestinationButtonClick(ActionEvent event) {
    }

    @FXML
    private void convertTasksButtonClick(ActionEvent event) {
    }

    @FXML
    private void pauseTasksButtonClick(ActionEvent event) {
    }

    @FXML
    private void deleteTasksButtonClick(ActionEvent event) {
    }

    @FXML
    private void historyPageButtonClick(MouseEvent event) {
    }
    
    
}

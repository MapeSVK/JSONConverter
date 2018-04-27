/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsonconverter.GUI.controller;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

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
    private Button btn;
    @FXML
    private TextField textFieldFileImport;
    @FXML
    private Label labelFileExtension;
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
    private String filePath;
    private FileChooser fileChooser;
    /**
     * 
     * @param event When you click the button "Import". This method will load
     * the file chooser.
     */
    @FXML
    private void importFileButtonClick(ActionEvent event) {
        fileChooser = new FileChooser();
        fileChooserSettings();
        File file = fileChooser.showOpenDialog(null);

        if (file != null) { //if statement only to avoid nullPointException after pressing "cancel" in filechooser
            filePath = file.toString();
            textFieldFileImport.setText(filePath); //insert path of the file into the textField
            fileExtendionIdentifier();
        } else {
            System.out.println("File could not be choosen.");
        }
    }
    /**
     * This method manages the file chooser.
     * The "ALL" contains all the possibles file extensions
     * The other ones are dedicated for one in concrete
     */
    private void fileChooserSettings() {
        FileChooser.ExtensionFilter ALL = new FileChooser.ExtensionFilter("Import *.XXX", "*.csv", "*xlsx");
        FileChooser.ExtensionFilter CSV = new FileChooser.ExtensionFilter("Import csv", "*.csv");
        FileChooser.ExtensionFilter XLSX = new FileChooser.ExtensionFilter("Import xlsx", "*.xlsx");
        fileChooser.getExtensionFilters().addAll(ALL, CSV, XLSX);
    }

    /**
     * This method manages the label next to the text field where you can see
     * where extension did you load without surfing through the text
     */
    private void fileExtendionIdentifier() {
        if (filePath.endsWith(".csv")) {
            labelFileExtension.setText(".csv");
        } else if (filePath.endsWith("xlsx")) {
            labelFileExtension.setText(".xlsx");
        } else {
            labelFileExtension.setText("???");
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

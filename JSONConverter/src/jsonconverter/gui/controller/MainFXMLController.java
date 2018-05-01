/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsonconverter.GUI.controller;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javax.swing.JFileChooser;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import jsonconverter.BE.Task;
import jsonconverter.GUI.model.Model;
import jsonconverter.GUI.util.RingProgressIndicator;

/**
 * FXML Controller class
 *
 * @author Samuel
 */
public class MainFXMLController implements Initializable {

    @FXML
    private Button importFileButton;
    @FXML
    private TextField textFieldFileImport;
    @FXML
    private Label labelFileExtension;
    @FXML
    private TableColumn<Task, String> nameOfTheFileColumn;
    @FXML
    private TableColumn<Task, String> configNameColumn;
    @FXML
    private TableColumn<Task, Button> stopButtonColumn;
    @FXML
    private TableColumn<Task, Button> pauseButtonColumn;
    @FXML
    private TableColumn<Task, RingProgressIndicator> progressCircleColumn;
    @FXML
    private ChoiceBox<String> configChoiceBox;
    @FXML
    private TableView<Task> tasksTableView;

    private String filePath;
    private FileChooser fileChooser;
    private JFileChooser jfileChooser;
    Model model = new Model();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setTasksTableViewItems();
        setConfigChoiceBoxItems();

        tasksTableView.setItems(FXCollections.observableArrayList(new Task("name", "configName")));

    }

    /* set tableView columns */
    public void setTasksTableViewItems() {
        nameOfTheFileColumn.setCellValueFactory(new PropertyValueFactory("name"));
        configNameColumn.setCellValueFactory(new PropertyValueFactory("configName"));
        progressCircleColumn.setCellValueFactory(new PropertyValueFactory("ringProgressIndicator"));
        stopButtonColumn.setCellValueFactory(new PropertyValueFactory("stopTask"));
        pauseButtonColumn.setCellValueFactory(new PropertyValueFactory("closeTask"));
    }

    /* getting data from the model and setting this data in the choiceBox */
    public void setConfigChoiceBoxItems() {
        configChoiceBox.setItems(model.getConfigChoiceBoxItems());
    }

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
            System.out.println("ERROR: File could not be imported.");
        }
    }

    /**
     * This method manages the file chooser. "ALL" contains all the possibles
     * file extensions It is possible to choose specific extensions
     */
    private void fileChooserSettings() {
        FileChooser.ExtensionFilter ALL = new FileChooser.ExtensionFilter("Import *.XXX", "*.csv", "*xlsx");
        FileChooser.ExtensionFilter CSV = new FileChooser.ExtensionFilter("Import csv", "*.csv");
        FileChooser.ExtensionFilter XLSX = new FileChooser.ExtensionFilter("Import xlsx", "*.xlsx");
        fileChooser.getExtensionFilters().addAll(ALL, CSV, XLSX);

    }

    /**
     * Setting text of the label depending on the file extension
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
        jfileChooser = new JFileChooser();

        jfileChooser.setCurrentDirectory(new File(filePath));
        jfileChooser.setDialogTitle("Select a directory");
        jfileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        jfileChooser.setAcceptAllFileFilterUsed(false);
        if (jfileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            System.out.println("Get current directory: " + jfileChooser.getCurrentDirectory());
            System.out.println("Get current file: " + jfileChooser.getSelectedFile());
        } else {
            System.out.println("No Selection ");
        }

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

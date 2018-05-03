package jsonconverter.GUI.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javax.swing.JFileChooser;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.concurrent.Task;
import jsonconverter.BE.TaskInOurProgram;
import jsonconverter.DAL.readAndSave.CSV;
import jsonconverter.DAL.readAndSave.IConverter;
import jsonconverter.GUI.model.Model;
import jsonconverter.GUI.util.RingProgressIndicator;

public class MainFXMLController implements Initializable {

    @FXML
    private Label labelFileExtension;
    @FXML
    private TableColumn<TaskInOurProgram, String> nameOfTheFileColumn;
    @FXML
    private TableColumn<TaskInOurProgram, String> configNameColumn;
    @FXML
    private TableColumn<TaskInOurProgram, Button> stopButtonColumn;
    @FXML
    private TableColumn<TaskInOurProgram, Button> pauseButtonColumn;
    @FXML
    private TableColumn<TaskInOurProgram, RingProgressIndicator> progressCircleColumn;
    @FXML
    private ChoiceBox<String> configChoiceBox;
    @FXML
    private TableView<Task> tasksTableView;
    
    private IConverter converter;
    private String filePath;
    private String fileType;
    private String nameOfImportedFile;
    private FileChooser fileChooser;
    private JFileChooser jfileChooser;
    private File file;
    private File directoryPath;
    String newFileName = "Test";//Name needs to be indicate! It's just an example
    private final String newFileExtension = ".json";
    private String newFileInfo = newFileName + newFileExtension;

    Model model = new Model();

    @FXML
    private TableColumn<String, String> extensionColumn;
    @FXML
    private Label nameOfImportedFileLabel;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setTasksTableViewItems();
        setConfigChoiceBoxItems();

     //   tasksTableView.setItems(model.getTasksInTheTableView());



    }
    
    

    /* set tableView columns */
    public void setTasksTableViewItems() {
        extensionColumn.setCellValueFactory(new PropertyValueFactory("extension"));
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

   
    @FXML
    private void importFileButtonClick(ActionEvent event) {
        fileChooser = new FileChooser();
        fileChooserSettings();
        file = fileChooser.showOpenDialog(null);

        if (file != null) { //if statement only to avoid nullPointException after pressing "cancel" in filechooser
            filePath = file.toString();           
            nameOfImportedFile = gettingTheFileNameFromThePath(file);
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
        FileChooser.ExtensionFilter ALL = new FileChooser.ExtensionFilter("Import *.XXX", "*.csv", "*.xlsx");
        FileChooser.ExtensionFilter CSV = new FileChooser.ExtensionFilter("Import csv", "*.csv");
        FileChooser.ExtensionFilter XLSX = new FileChooser.ExtensionFilter("Import xlsx", "*.xlsx");
        fileChooser.getExtensionFilters().addAll(ALL, CSV, XLSX);

    }

    /**
     * Setting text of the label depending on the file extension
     */
    private void fileExtendionIdentifier() {
        if (filePath.endsWith(".csv")) {
            fileType=".csv";
            nameOfImportedFileLabel.setText(nameOfImportedFile+".csv");  //set text of the label to NAME of the imported file
            converter = new CSV(filePath);
        } else if (filePath.endsWith(".xlsx")) {
            fileType=".xlsx";
             nameOfImportedFileLabel.setText(nameOfImportedFile+".xlsx");  
        } else {
            nameOfImportedFileLabel.setText(nameOfImportedFile+".???");
        }
    }

    /**
     * getting the name of the file from the path
     */
    public String gettingTheFileNameFromThePath(File file) {
        String nameOfTheFile = file.getName();
        int pos = nameOfTheFile.lastIndexOf(".");
        if (pos > 0) {
            nameOfTheFile = nameOfTheFile.substring(0, pos);
        }
        return nameOfTheFile;
    }

    @FXML
    private void createNewConfigButtonClick(ActionEvent event) throws IOException {
       Parent root;
                  Stage stage = new Stage();
                  FXMLLoader loader = new FXMLLoader(getClass().getResource("/jsonconverter/GUI/view/ConfigFXML.fxml"));
                  root = loader.load();
                  ConfigFXMLController controller = loader.getController();
                  controller.setFileTypeAndConverter(fileType, converter);
                  stage.initModality(Modality.APPLICATION_MODAL);
                  stage.setScene(new Scene(root));
                  stage.showAndWait();
    }

    @FXML
    private void addTaskButtonClick(ActionEvent event) {
        /* CONDITIONS */
        boolean isRightNameOfTheFile = false;
        boolean isConfigSet = false;
        boolean isRightExtension = false;
        
        if (nameOfImportedFile != null && !nameOfImportedFile.equals("")) {
            isRightNameOfTheFile = true;
        }
        
        if (!configChoiceBox.getSelectionModel().getSelectedItem().equals("") && 
                configChoiceBox.getSelectionModel().getSelectedItem() != null) {
            isConfigSet = true;
        }
        
        if (!labelFileExtension.getText().equals("") && labelFileExtension.getText() != null ) {
            isRightExtension = true;
        }
        
        /* ADDING */
        if (isRightNameOfTheFile == true && isConfigSet == true && isRightExtension == true) {
//            TaskInOurProgram task = new TaskInOurProgram(nameOfImportedFile, configChoiceBox.getSelectionModel().getSelectedItem(), 
//                    labelFileExtension.getText());
//            model.addTask(task); 
        }
                
    }
    
    /*
    *   This method contains mainly the directory chooser interface.
     */
    @FXML
    private void chooseDirectoryButtonClick(ActionEvent event) {
        jfileChooser = new JFileChooser();
        jfileChooser.setCurrentDirectory(new java.io.File(".")); //It will set as directory the current folder project
        jfileChooser.setDialogTitle("Select a directory");
        jfileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        jfileChooser.setAcceptAllFileFilterUsed(false);
        if (jfileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            directoryPath = jfileChooser.getSelectedFile().getAbsoluteFile();
            System.out.println("Get current directory: " + directoryPath);
        } else {
            System.out.println("No Selection ");
        }
    }

    @FXML
    private void convertTasksButtonClick(ActionEvent event) throws IOException {

        Thread t;
        t = new Thread(() -> {
            try {
                System.out.println("Go 2 Sleep!");
                System.out.println("Try to use the program now! You have less than 8s!!");
                Thread.sleep(8000);
                System.out.println("Hello, I am a thread");
                File newfile = new File(directoryPath, newFileInfo);
                newfile.createNewFile();

            } catch (InterruptedException ex) {
                Logger.getLogger(MainFXMLController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(MainFXMLController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        t.start();
    }

    @FXML
    private void pauseTasksButtonClick(ActionEvent event) {

    }

    @FXML
    private void deleteTasksButtonClick(ActionEvent event) {
        for(String line : model.getCSVValues(converter))
        {
            System.out.println(line);
        }
    }

    @FXML
    private void historyPageButtonClick(MouseEvent event) {
    }

}

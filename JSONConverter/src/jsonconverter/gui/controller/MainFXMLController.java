package jsonconverter.GUI.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ProgressBarTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.DirectoryChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import jsonconverter.BE.Config;

import jsonconverter.BE.TaskInOurProgram;
import jsonconverter.DAL.readFilesAndWriteJson.ReadCSV;
import jsonconverter.DAL.readFilesAndWriteJson.IConverter;
import jsonconverter.GUI.model.Model;

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
    private TableColumn<TaskInOurProgram, Double> progressCircleColumn;
    @FXML
    private ChoiceBox<Config> configChoiceBox;
    @FXML
    private TableView<TaskInOurProgram> tasksTableView;
      @FXML
    private TableColumn<String, String> extensionColumn;
    @FXML
    private Label nameOfImportedFileLabel;

    ExecutorService executor;
    
    
    @FXML
    private Button buttonChooseDirectory; //Do not remove it
    private IConverter converter;
    private String filePath;
    private String fileType;
    private String nameOfImportedFile;
    private FileChooser fileChooser;
    private File fileChoosedByImport;
    private File directoryPath;
    private boolean directoryPathHasBeenSelected = false;
    private String newFileName = "Test";//Name needs to be indicate! It's just an example
    private final String newFileExtension = ".json";
    private String newFileInfo = newFileName + newFileExtension;
    private Model model = new Model();
    private boolean isTaskConverting;

    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setTasksTableViewItems();
        setConfigChoiceBoxItems();

        tasksTableView.setItems(model.getTasksInTheTableView());
        playPauseButtonActionInTableView();
    }

    /* set tableView columns */
    public void setTasksTableViewItems() {
        extensionColumn.setCellValueFactory(new PropertyValueFactory("extensionOfTheFile"));
        nameOfTheFileColumn.setCellValueFactory(new PropertyValueFactory("nameOfTheFile"));
        configNameColumn.setCellValueFactory(new PropertyValueFactory("configName"));

        stopButtonColumn.setCellValueFactory(new PropertyValueFactory("stopTask"));
        pauseButtonColumn.setCellValueFactory(new PropertyValueFactory("closeTask"));

        TableColumn<TaskInOurProgram, String> statusCol = new TableColumn("Status");
        statusCol.setCellValueFactory(new PropertyValueFactory<TaskInOurProgram, String>(
                "message"));
        statusCol.setPrefWidth(75);

        progressCircleColumn.setCellValueFactory(new PropertyValueFactory<TaskInOurProgram, Double>(
                "progress"));
        progressCircleColumn
                .setCellFactory(ProgressBarTableCell.<TaskInOurProgram>forTableColumn());

        tasksTableView.getColumns().addAll(statusCol);

    }

    /* getting data from the model and setting this data in the choiceBox */
    public void setConfigChoiceBoxItems() {
        configChoiceBox.setItems(model.getFakeConfig()); // <---------------------------------------FAKE DB
    }

    @FXML
    private void importFileButtonClick(ActionEvent event) {
        fileChooser = new FileChooser();
        fileChooserSettings();
        fileChoosedByImport = fileChooser.showOpenDialog(null);

        if (fileChoosedByImport != null) {
            filePath = fileChoosedByImport.toString();
            nameOfImportedFile = gettingTheFileNameFromThePath(fileChoosedByImport);
            fileExtendionIdentifier();
            nameOfImportedFileLabel.setText(nameOfImportedFile);

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
            fileType = ".csv";
            labelFileExtension.setText("csv");
            converter = new ReadCSV(filePath);
        } else if (filePath.endsWith(".xlsx")) {
            fileType = ".xlsx";
            labelFileExtension.setText("xlsx");
        } else {
            nameOfImportedFileLabel.setText(nameOfImportedFile + ".???");
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
        controller.getConverterandModel(converter, model);
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

        if (!configChoiceBox.getSelectionModel().getSelectedItem().equals("")
                && configChoiceBox.getSelectionModel().getSelectedItem() != null) {
            isConfigSet = true;
        }

        if (!labelFileExtension.getText().equals("") && labelFileExtension.getText() != null) {
            isRightExtension = true;
        }

        /* ADDING */
        System.out.println(nameOfImportedFile);

        if (isRightNameOfTheFile == true && isConfigSet == true && isRightExtension == true) {
            TaskInOurProgram task = new TaskInOurProgram(nameOfImportedFile, configChoiceBox.getSelectionModel().getSelectedItem().getConfigName(),
                    labelFileExtension.getText());
            task.setConverter(converter);
            task.setConfig(configChoiceBox.getValue());
            task.setFilePath(directoryPath);
            task.setFileName(nameOfImportedFile);
            model.addTask(task);
        }

    }

    /*
    *   This method contains mainly the directory chooser interface.
     */
    @FXML
    private void chooseDirectoryButtonClick(ActionEvent event) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog(buttonChooseDirectory.getScene().getWindow());
        directoryChooser.setTitle("Select a directory");
        if (selectedDirectory == null) {
            Alert("Directory problem", "You did not select any directory. Try again!");
        } else {
            System.out.println("Selected directory: " + selectedDirectory.getAbsolutePath());
            directoryPath = selectedDirectory.getAbsoluteFile();
            directoryPathHasBeenSelected = true;
        }
    }

    @FXML
    private void convertTasksButtonClick(ActionEvent event) throws IOException {
         TaskInOurProgram task = tasksTableView.getSelectionModel().getSelectedItem();
//        service = new Service() {
//            @Override
//            protected Task createTask() {
//                return task;
//            }
//        };
//        service.start();
executor = Executors.newFixedThreadPool(1);
        executor.submit(task);
        executor.shutdown();
    }
 boolean akurwa=false;
    @FXML
    private void pauseTasksButtonClick(ActionEvent event) throws InterruptedException {

    }
    
    public void playPauseButtonActionInTableView() {
        for (TaskInOurProgram task : model.getTasksInTheTableView()) {
            
                task.getPauseTask().clipProperty().addListener((observable) -> {

                    if (isTaskConverting == false) {
                        System.out.println("I am paused");
                        isTaskConverting = true;
                    } else {
                        System.out.println("I am converting");
                        isTaskConverting = false;
                    }
                    
                });
            
        }
    }

    
    @FXML
    private void deleteTasksButtonClick(ActionEvent event) {
        System.out.println("----------Start-------------");
 Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
 Thread[] threadArray = threadSet.toArray(new Thread[threadSet.size()]);
        for (int i = 0; i < threadArray.length; i++) {
            System.out.println(threadArray[i].getName());
            
            
        }
        System.out.println("---------Koniec-----------");
    }

    @FXML
    private void historyPageButtonClick(MouseEvent event) {
    }

    private void Alert(String title, String text) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(text);
        alert.showAndWait();
    }

}

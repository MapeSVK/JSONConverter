package jsonconverter.GUI.controller;

import com.jfoenix.controls.JFXDatePicker;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.DirectoryChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import jsonconverter.BE.Config;
import jsonconverter.BE.History;
import jsonconverter.BE.TaskInOurProgram;
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
    private ObservableList<Config> allConfigsSavedInDatabase = FXCollections.observableArrayList();
    private String filePath = "";
    private String fileType;
    private String nameOfImportedFile = "";
    private FileChooser fileChooser;
    private File fileChoosedByImport;
    private File directoryPath;
    private boolean directoryPathHasBeenSelected = false;
    private String newFileName = nameOfImportedFile;//Name needs to be indicate! It's just an example
    private final String newFileExtension = ".json";
    private String newFileInfo = newFileName + newFileExtension;
    private Model model = new Model();
    private TaskInOurProgram task;
    private ExecutorService executor = Executors.newFixedThreadPool(3);
    @FXML
    private Button chooseDirectoryButton;
    @FXML
    private JFXDatePicker startDateDatePicker;
    @FXML
    private JFXDatePicker endDateDatePicker;
    @FXML
    private TableColumn<History, Date> dateAndTimeColumn;
    @FXML
    private TableColumn<History, String> taskNameColumn;
    @FXML
    private TableColumn<History, String> userNameColumn;
    @FXML
    private TableColumn<History, Button> errorColumn;
    @FXML
    private TableView<History> historyTableView;
    @FXML
    private Button editButton;

    private String convertingOrPauseOrPlay;
    private final Image pauseImage = new Image("file:images/pauseImage.png");
    private final Image closeImage = new Image("file:images/close.png");
    private final Image playImage = new Image("file:images/playImage.png");

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setTasksTableViewColumns();
        setHistoryTableViewColumns();
        setConfigChoiceBoxItems();
        tasksTableView.setItems(model.getTasksInTheTableView());

        tasksTableView.setSelectionModel(null);
        historyTableView.setSelectionModel(null);

        model.loadConfigFromDatabase();
        /* set history tableView */
        model.loadHistoryFromDatabase();
        historyTableView.setItems(model.getAllHistoryObservableArrayList());
        gettingThePrivateConfigs();

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
            model.getConverter(task);
            task.setConfig(configChoiceBox.getValue());
            task.setFilePath(directoryPath);
            task.setFileName(nameOfImportedFile);
            model.addTask(task);
        }

        pauseConvertingClick();

    }

    @FXML
    private void convertTasksButtonClick(ActionEvent event) throws IOException {

    }

    @FXML
    private void pauseTasksButtonClick(ActionEvent event) throws InterruptedException {
        executor.shutdownNow();
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

    /* set tableView columns */
    public void setTasksTableViewColumns() {
        extensionColumn.setCellValueFactory(new PropertyValueFactory("extensionOfTheFile"));
        nameOfTheFileColumn.setCellValueFactory(new PropertyValueFactory("nameOfTheFile"));
        configNameColumn.setCellValueFactory(new PropertyValueFactory("configName"));

        stopButtonColumn.setCellValueFactory(new PropertyValueFactory("pauseTask"));
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
        //configChoiceBox.setItems(model.getFakeConfig()); // <---------------------------------------FAKE DB
        configChoiceBox.setItems(model.getAllConfigObservableArrayList());
    }

    /**
     * This method manages the file chooser. "ALL" contains all the possibles
     * file extensions It is possible to choose specific extensions
     */
    private void fileChooserSettings() {
        FileChooser.ExtensionFilter ALL = new FileChooser.ExtensionFilter("All (*.*)", "*.csv", "*.xlsx", "*.xml");
        FileChooser.ExtensionFilter CSV = new FileChooser.ExtensionFilter("CSV", "*.csv");
        FileChooser.ExtensionFilter XLSX = new FileChooser.ExtensionFilter("XLSX", "*.xlsx");
        FileChooser.ExtensionFilter XML = new FileChooser.ExtensionFilter("XML", "*.xml");
        fileChooser.getExtensionFilters().addAll(ALL, CSV, XLSX, XML);

    }

    /**
     * Setting text of the label depending on the file extension
     */
    private void fileExtendionIdentifier() {
        if (filePath.endsWith(".csv")) {
            fileType = ".csv";
            labelFileExtension.setText("csv");
            model.setConverter(fileType, filePath);
        } else if (filePath.endsWith(".xlsx")) {
            fileType = ".xlsx";
            labelFileExtension.setText("xlsx");
            model.setConverter(fileType, filePath);
        } else if (filePath.endsWith(".xml")) {
            fileType = ".xml";
            labelFileExtension.setText("xml");
            model.setConverter(fileType, filePath);
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
        if (filePath.equals("") || nameOfImportedFile.equals("")) {
            JOptionPane.showMessageDialog(null, "No file imported. Please, import one previously");
        } else {
            Parent root;
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/jsonconverter/GUI/view/ConfigFXML.fxml"));
            root = loader.load();
            ConfigFXMLController controller = loader.getController();
            controller.getModel(model);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.showAndWait();
        }
    }

    public void pauseConvertingClick() {

        for (TaskInOurProgram task : model.getTasksInTheTableView()) {
            task.getPauseTask().setGraphic(new ImageView(playImage));
            convertingOrPauseOrPlay = "firstStage";
            //task.pauseThis();

            task.getPauseTask().setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    if (convertingOrPauseOrPlay.equals("firstStage")) {

                        executor.submit(task);
                        convertingOrPauseOrPlay = "secondStage";
                        task.getPauseTask().setGraphic(new ImageView(pauseImage));

                    } else if (convertingOrPauseOrPlay.equals("secondStage")) {
                        task.pauseThis();
                        convertingOrPauseOrPlay = "thirdStage";
                        task.getPauseTask().setGraphic(new ImageView(playImage));
                    } else if (convertingOrPauseOrPlay.equals("thirdStage")) {
                        task.continueThis();
                        task.getPauseTask().setGraphic(new ImageView(pauseImage));
                        convertingOrPauseOrPlay = "firstStage";
                    }

//                    else if (convertingOrPauseOrPlay.equals("fourthStage")){
//                        task.getPauseTask().setGraphic(new ImageView(playImage));
//                        
//                        if (task.isIsConvertingDone() == true) {
//                            convertingOrPauseOrPlay = "firstStage";
//                        }
//                        
//                        else if (task.isIsConvertingDone() == false) {
//                            convertingOrPauseOrPlay = "secondStage";
//                        }
//                    }
                }
            });
        }
    }

    /*
    *   This method contains mainly the directory chooser interface.
     */
    @FXML
    private void chooseDirectoryButtonClick(ActionEvent event) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog(chooseDirectoryButton.getScene().getWindow());
        directoryChooser.setTitle("Select a directory");
        if (selectedDirectory == null) {
            JOptionPane.showMessageDialog(null, "You did not select any directory. Try again!");
        } else {
            System.out.println("Selected directory: " + selectedDirectory.getAbsolutePath());
            directoryPath = selectedDirectory.getAbsoluteFile();
            directoryPathHasBeenSelected = true;
        }
    }

    private void Alert(String title, String text) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(text);
        alert.showAndWait();

    }

    public void getStage(Stage stage) {
        stage.showingProperty().addListener(e -> {
            if (stage.isShowing() == false) {
                if (executor != null) {
                    executor.shutdownNow();
                    System.exit(0);
                }
            }
        });
    }

    /* HISTORY TAB */
    public void setHistoryTableViewColumns() {
        dateAndTimeColumn.setCellValueFactory(new PropertyValueFactory("dateAndTime"));
        taskNameColumn.setCellValueFactory(new PropertyValueFactory("fileName"));
        userNameColumn.setCellValueFactory(new PropertyValueFactory("username"));
        errorColumn.setCellValueFactory(new PropertyValueFactory("errorButton"));
    }

//    public void openErrorMessageAfterClickOnTheButtonInHistoryTableView() {
//        for (History history : model.getHistoryFromDatabase) {
//            if (history.isHasError() == true) {
//                history.getErrorButton().setGraphic(new ImageView());
//            } else {
//                history.getErrorButton().setText("NO ERROR");
//            }
//
//            history.getErrorButton().setOnAction(new EventHandler<ActionEvent>() {
//                @Override
//                public void handle(ActionEvent event) {
//                    Parent root = null;
//                    Stage stage = new Stage();
//                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/jsonconverter/GUI/view/ErrorMessageFXML.fxml"));
//                    try {
//                        root = loader.load();
//                    } catch (IOException ex) {
//                        Alert("Window opening error", "Window with error message could not been opened!");
//                    }
//                    ErrorMessageFXMLController controller = loader.getController();
//                    controller.getModel(model);
//                    stage.initModality(Modality.APPLICATION_MODAL);
//                    stage.setScene(new Scene(root));
//                    stage.showAndWait();
//                }
//            });
//
//        }
//    }
    private void gettingThePrivateConfigs() {
        for (Config config : model.getAllConfigObservableArrayList()) {
            allConfigsSavedInDatabase.add(config);
        }
    }

    @FXML
    private void editConfigButtonClick(ActionEvent event) throws IOException, ParseException {
        if (configChoiceBox.getSelectionModel().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please, choose a valid configuration");
        } else {
            Parent root;
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/jsonconverter/GUI/view/ConfigFXML.fxml"));
            root = loader.load();
            ConfigFXMLController controller = loader.getController();
            controller.setConfig(configChoiceBox.getValue());
            controller.getModel(model);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.showAndWait();
        }
    }
}

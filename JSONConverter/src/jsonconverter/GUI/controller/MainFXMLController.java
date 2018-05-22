package jsonconverter.GUI.controller;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import java.awt.MouseInfo;
import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.ProgressBarTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.stage.DirectoryChooser;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.StringConverter;
import jsonconverter.BE.Config;
import jsonconverter.BE.History;
import jsonconverter.BE.TaskInOurProgram;
import jsonconverter.GUI.model.Model;
import jsonconverter.GUI.util.HistoryRow;

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
    private TableColumn<TaskInOurProgram, Double> progressCircleColumn;
    @FXML
    private ChoiceBox<Config> configChoiceBox;
    @FXML
    private TableView<TaskInOurProgram> tasksTableView;
    @FXML
    private TableColumn<String, String> extensionColumn;
    @FXML
    private Label nameOfImportedFileLabel;
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
    private TableColumn<History, ImageView> errorColumn;
    @FXML
    private TableView<History> historyTableView;
    @FXML
    private Button editButton;
    @FXML
    private TableColumn<TaskInOurProgram, Button> closeButtonColumn;
    @FXML
    private Button importFileFromFolderButton;

    private String filePath = "";
    private String fileType;
    private String nameOfImportedFile = "";
    private FileChooser fileChooser;
    private File fileChoseByImport;
    private File directoryPath;
    private boolean directoryPathHasBeenSelected = false;
    private String newFileName = nameOfImportedFile;//Name needs to be indicate! It's just an example
    private final String newFileExtension = ".json";
    private String newFileInfo = newFileName + newFileExtension;
    private Model model = new Model();
    private TaskInOurProgram task;
    private ExecutorService executor = Executors.newFixedThreadPool(3);
    private Date fromDateInDatePicker;
    
    /*SMALL ICONS*/
    private final Image errorSmall = new Image("file:images/errorImage.png");
    private final Image pauseSmall = new Image("file:images/pauseSmall.png");
    private final Image stopSmall = new Image("file:images/stopSmall.png");
    private final Image playSmall = new Image("file:images/playSmall.png");
    
    /*BIG ICONS*/
    private final Image pauseBig = new Image("file:images/pauseBig.png");
    private final Image playBig = new Image("file:images/playBig.png");
    private final Image stopBig = new Image("file:images/stopBig.png");
   
    private Date fromDate;
    private Date toDate;
    private java.sql.Date currentDate = java.sql.Date.valueOf(LocalDate.now());

    /* Stage needed to display error message */
    Stage stage;
    @FXML
    private Button convertTasksButton;
    @FXML
    private Button pauseProcessButton;
    @FXML
    private Button deleteProcessButton;
    @FXML
    private Button addTaskButton;
    @FXML
    private Button createNewConfigButton;
    @FXML
    private Label typeOfImportedFileLabel;
    private Label nameOfImportedFileValueLabel;
    @FXML
    private Region firstRegion;
    @FXML
    private Region secondRegion;
    @FXML
    private Region thirdRegion;
    @FXML
    private Label nameOfImportedFileLabelLabel;
    @FXML
    private JFXTextField searchByUsernameField;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        /* set big icons instead of basic look of buttons */
        pauseProcessButton.setGraphic(new ImageView(pauseBig));
        convertTasksButton.setGraphic(new ImageView(playBig));
        deleteProcessButton.setGraphic(new ImageView(stopBig));
        pauseProcessButton.getStyleClass().add("pauseBigButton");
        convertTasksButton.getStyleClass().add("playBigButton");
        deleteProcessButton.getStyleClass().add("stopBigButton");

        setTasksTableViewColumns();
        setHistoryTableViewColumns();
        setConfigChoiceBoxItems();
        tasksTableView.setItems(model.getTasksInTheTableView());

        tasksTableView.setSelectionModel(null);
        historyTableView.setSelectionModel(null);
        
        model.loadAvailableConfig();

        /* set history tableView */
        model.loadHistoryFromDatabase();
        historyTableView.setItems(model.getSortedAllHistory());

        /* set datePickers in History tab */
        toDate = currentDate;
        setPromptDateInDatePickerToEuropeanStyle();
        activeDatePickersInHistoryTab();
        model.getAllHistoryObservableArrayList().setAll(model.getSortedAllHistory());
        searchHistoryByUsername();

        /* style history tableView and show error as a pop-up */
        openErrorMessageAfterHoveringOverRow();
    }

    /* imports the file into program */
    @FXML
    private void importFileButtonClick(ActionEvent event) {
        fileChooser = new FileChooser();
        fileChooserSettings();
        fileChoseByImport = fileChooser.showOpenDialog(null);
        if (fileChoseByImport != null) {
            configChoiceBox.setDisable(false);
            createNewConfigButton.setDisable(false);
            editButton.setDisable(true);
            /* name and type set to visible */
            nameOfImportedFileLabelLabel.setVisible(true);
            typeOfImportedFileLabel.setVisible(true);
            labelFileExtension.setVisible(true);
            nameOfImportedFileLabel.setVisible(true);
            
            firstRegion.setVisible(false);
            
            filePath = fileChoseByImport.toString();
            nameOfImportedFile = gettingTheFileNameFromThePath(fileChoseByImport);
            fileExtendionIdentifier();
            model.getAllConfigObservableArrayList().clear();
            model.getAllConfigObservableArrayList().setAll(model.checkIfYouCanUseConfig());
            nameOfImportedFileLabel.setText(nameOfImportedFile);
        } else {
            model.Alert("Error", "File could not be imported");
        }
    }

    /* importf files from file */
    @FXML
    private void importFileFromFolderAction(ActionEvent event) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog(importFileFromFolderButton.getScene().getWindow());
        if (selectedDirectory != null) {
            nameOfImportedFileLabel.setText(selectedDirectory.getName());
            labelFileExtension.setText("file");
            File[] listOfFiles = selectedDirectory.listFiles();
            filePath = "placki";
            nameOfImportedFile = "file";
            configChoiceBox.setDisable(false);
            createNewConfigButton.setDisable(true);
            editButton.setDisable(true);
            /* name and type set to visible */
            nameOfImportedFileLabelLabel.setVisible(true);
            typeOfImportedFileLabel.setVisible(true);
            labelFileExtension.setVisible(true);
            nameOfImportedFileLabel.setVisible(true);
            
            firstRegion.setVisible(false);
            
            model.getAllFilesInFolder().clear();
            model.loadAvailableConfig();
            for (int i = 0; i < listOfFiles.length; i++) {
                if (listOfFiles[i].isFile()) {
                    model.addFileFromTheFolder(listOfFiles[i]);
                }
            }
        }
    }

    /* pops up window so user can chose directory */
    @FXML
    private void chooseDirectoryButtonClick(ActionEvent event) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog(chooseDirectoryButton.getScene().getWindow());
        directoryChooser.setTitle("Select a directory");
        if (selectedDirectory == null) {
            model.Alert("Is that correct?", "You did not select directory");
        } else {
            directoryPath = selectedDirectory.getAbsoluteFile();
            directoryPathHasBeenSelected = true;
            addTaskButton.setDisable(false);
            thirdRegion.setVisible(false);
        }
    }

    /* converts all tasks in taskTable */
    @FXML
    private void convertTasksButtonClick(ActionEvent event) throws IOException {
        if (convertTasksButton.isFocused() && !directoryPathHasBeenSelected) {
            model.Alert("Error", "Choose a directory first");
        } else {
            for (TaskInOurProgram task : model.getTasksInTheTableView()) {

                if (task.isIsExecutedForFirstTime() == false && task.isPause() == false) {
                    executor.submit(task);
                    task.setIsExecutedForFirstTime(true);
                    task.getPauseTask().setGraphic(new ImageView(pauseSmall));

                } else if (task.isIsExecutedForFirstTime() == true && task.isPause() == true && task.isIfWasStarted() == true) {
                    task.getPauseTask().setGraphic(new ImageView(pauseSmall));
                    task.continueThis();
                }
            }
           
        }
    }

    /* pauses all running tasks in taskTable */
    @FXML
    private void pauseTasksButtonClick(ActionEvent event) throws InterruptedException {
        for (TaskInOurProgram task : model.getTasksInTheTableView()) {

            //set default image before clicking on the button
            //task.getPauseTask().setGraphic(new ImageView(playSmall));
            if (task.isIsExecutedForFirstTime() == true && task.isPause() == false && task.isIfWasStarted() == true) {
                task.getPauseTask().setGraphic(new ImageView(playSmall));
                task.pauseThis();
            }
        }

    }

    /* stops all tasks and removes them from taskTable */
    @FXML
    private void deleteTasksButtonClick(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Are you sure?");
        alert.setContentText("It will be removed permanently. Are you sure?");
        alert.showAndWait();
        if (alert.getResult() == ButtonType.OK) {
            for (TaskInOurProgram task : model.getTasksInTheTableView()) {
                if (task.isPause()) {
                    task.continueThis();
                }
                task.cancel();
            }
            model.getTasksInTheTableView().clear();
        }

    }

    /* pops up window where user can create new config */
    @FXML
    private void createNewConfigButtonClick(ActionEvent event) throws IOException {
        if (filePath.equals("") || nameOfImportedFile.equals("")) {
            model.Alert("Error", "No file imported. Please, import one previously");
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

    /* adds task to the taskTable */
    @FXML
    private void addTaskButtonClick(ActionEvent event) {
        /* CONDITIONS */
        boolean isRightNameOfTheFile = false;
        boolean isConfigSet = false;
        boolean isRightExtension = false;
        String failedToAdd = "";

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
        if (isRightNameOfTheFile == true && isConfigSet == true && isRightExtension == true && !labelFileExtension.getText().equals("file")) {
            TaskInOurProgram task = new TaskInOurProgram(nameOfImportedFile, configChoiceBox.getSelectionModel().getSelectedItem().getConfigName(),
                    labelFileExtension.getText());
            model.getConverter(task);
            task.setConfig(configChoiceBox.getValue());
            task.setFilePath(directoryPath);
            task.setFileName(nameOfImportedFile);
            model.addTask(task);

            /* if task is added to the tableView, you can use pause or close */
            pauseConvertingClick();
            closeTaskButtonClick();

            /* set buttons disable until task will be added */
            convertTasksButton.setDisable(false);
            pauseProcessButton.setDisable(false);
            deleteProcessButton.setDisable(false);

        } else if (isRightNameOfTheFile == true && isConfigSet == true && isRightExtension == true && labelFileExtension.getText().equals("file")) {
            for (File file : model.getAllFilesInFolder()) {
                fileInFolderExtension(file);
                nameOfImportedFile = gettingTheFileNameFromThePath(file);
                if (model.checkIfFileMatchesConfig(configChoiceBox.getValue())) {
                    TaskInOurProgram task = new TaskInOurProgram(nameOfImportedFile, configChoiceBox.getSelectionModel().getSelectedItem().getConfigName(),
                            fileType);
                    model.getConverter(task);
                    task.setConfig(configChoiceBox.getValue());
                    task.setFilePath(directoryPath);
                    task.setFileName(nameOfImportedFile);
                    model.addTask(task);

                    /* if conditions were met then add to the history */
                    //createHistoryForTask(task);

                    /* if task is added to the tableView, you can use pause or close */
                    pauseConvertingClick();
                    closeTaskButtonClick();

                    /* set buttons disable until task will be added */
                    convertTasksButton.setDisable(false);
                    pauseProcessButton.setDisable(false);
                    deleteProcessButton.setDisable(false);
                } else {
                    failedToAdd = failedToAdd + "," + file.getName();
                }
            }
            model.Alert("Files failed to add", failedToAdd + " were unable to add because they dont match chosen config");
        }
        createAlertIfFileExistsInReposytory(listOfFilesThatCanBeOverrided());
    }

    /* pops up window where user can edit chosen chonfig */
    @FXML
    private void editConfigButtonClick(ActionEvent event) throws IOException, ParseException {
        if (configChoiceBox.getSelectionModel().isEmpty()) {
            model.Alert("Error", "Choose a valid configuration");
        } else {
            if (configChoiceBox.getSelectionModel().isEmpty()) {
                model.Alert("Error", "Choose a valid configuration.");
            } else {
                Parent root;
                Stage stage = new Stage();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/jsonconverter/GUI/view/ConfigFXML.fxml"));
                root = loader.load();
                ConfigFXMLController controller = loader.getController();
                controller.setConfig(configChoiceBox.getValue());
                controller.getModel(model);
                controller.setToolTips();
                controller.setEditMode();
                controller.removeconfigButton.setOpacity(1);
                controller.removeconfigButton.setDisable(false);
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setScene(new Scene(root));
                stage.showAndWait();
            }
        }
    }

    /* set tableView columns */
    private void setTasksTableViewColumns() {
        extensionColumn.setCellValueFactory(new PropertyValueFactory("extensionOfTheFile"));
        nameOfTheFileColumn.setCellValueFactory(new PropertyValueFactory("nameOfTheFile"));
        configNameColumn.setCellValueFactory(new PropertyValueFactory("configName"));

        stopButtonColumn.setCellValueFactory(new PropertyValueFactory("pauseTask"));
        closeButtonColumn.setCellValueFactory(new PropertyValueFactory("closeTask"));
        progressCircleColumn.setCellValueFactory(new PropertyValueFactory<TaskInOurProgram, Double>(
                "progress"));
        progressCircleColumn
                .setCellFactory(ProgressBarTableCell.<TaskInOurProgram>forTableColumn());
        
        pauseConvertingClick();
    }

    /* getting data from the model and setting this data in the choiceBox */
    private void setConfigChoiceBoxItems() {
        configChoiceBox.setItems(model.getAllConfigObservableArrayList());
        configChoiceBox.valueProperty().addListener(e -> {
            if (configChoiceBox.getValue() != null) {
                chooseDirectoryButton.setDisable(false);
                editButton.setDisable(false);
                secondRegion.setVisible(false);
            } else {
                chooseDirectoryButton.setDisable(true);
                
            }
        });
    }

    /**
     * This method manages the file chooser. "ALL" contains all the possibles
     * file extensions It is possible to choose specific extensions
     */
    public void fileChooserSettings() {
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

    /* sets right converter for imported file from folder */
    private void fileInFolderExtension(File file) {
        if (file.getPath().endsWith(".csv")) {
            fileType = "csv";
            model.setConverter(".csv", file.getPath());
        } else if (file.getPath().endsWith(".xlsx")) {
            fileType = "xlsx";
            model.setConverter(".xlsx", file.getPath());
        } else if (file.getPath().endsWith(".xml")) {
            model.setConverter(".xml", file.getPath());
            fileType = "xml";
        }
    }

    /**
     * getting the name of the file from the path
     */
    private String gettingTheFileNameFromThePath(File file) {
        String nameOfTheFile = file.getName();
        int pos = nameOfTheFile.lastIndexOf(".");
        if (pos > 0) {
            nameOfTheFile = nameOfTheFile.substring(0, pos);
        }
        return nameOfTheFile;
    }

    /* shows in dataTable only history in chosen period of time */
    private void activeDatePickersInHistoryTab() {
        startDateDatePicker.valueProperty().addListener(e -> {

            LocalDate dateFromInLocalDateForm = startDateDatePicker.getValue(); //putting date from datePicker to local date form           
            Calendar c = Calendar.getInstance(); // creating calendar instance as a helper 
            c.set(dateFromInLocalDateForm.getYear(), dateFromInLocalDateForm.getMonthValue() - 1,
                    dateFromInLocalDateForm.getDayOfMonth());
            fromDate = c.getTime();

            historyTableView.setItems(model.getHistoryOfChosenPeriod(model.getSortedAllHistory(), fromDate, toDate));
            model.getAllHistoryObservableArrayList().setAll(model.getHistoryOfChosenPeriod(model.getSortedAllHistory(), fromDate, toDate));

        });

        endDateDatePicker.valueProperty().addListener(e -> {
            LocalDate dateEndInLocalDateForm = endDateDatePicker.getValue(); //putting date from datePicker to local date form
            Calendar c = Calendar.getInstance(); // creating calendar instance as a helper 
            c.set(dateEndInLocalDateForm.getYear(), dateEndInLocalDateForm.getMonthValue() - 1,
                    dateEndInLocalDateForm.getDayOfMonth());
            toDate = c.getTime();

            historyTableView.setItems(model.getHistoryOfChosenPeriod(model.getSortedAllHistory(), fromDate, toDate));
            model.getAllHistoryObservableArrayList().setAll(model.getHistoryOfChosenPeriod(model.getSortedAllHistory(), fromDate, toDate));
        });
    }

    /* set promt text in datePickers to European style - first dat, then month and then year
       so it looks better after date is chosen
     */
    private void setPromptDateInDatePickerToEuropeanStyle() {

        startDateDatePicker.setConverter(new StringConverter<LocalDate>() {
            String pattern = "dd.MM.yyyy";
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string) {
                return LocalDate.parse(string, dateFormatter);
            }
        });
        endDateDatePicker.setConverter(new StringConverter<LocalDate>() {
            String pattern = "dd.MM.yyyy";
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string) {
                return LocalDate.parse(string, dateFormatter);
            }
        });
    }

    /* pauses selected task */
    private void pauseConvertingClick() {

        for (TaskInOurProgram task : model.getTasksInTheTableView()) {

            //set default image before clicking on the button
            task.getPauseTask().setGraphic(new ImageView(playSmall));

            task.getPauseTask().setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {

                    if (task.isIsExecutedForFirstTime() == false && task.isPause() == false) {
                        executor.submit(task);
                        task.setIsExecutedForFirstTime(true);
                        task.getPauseTask().setGraphic(new ImageView(pauseSmall));
                        
                    } else if (task.isIsExecutedForFirstTime() == true && task.isPause() == false) {
                        task.getPauseTask().setGraphic(new ImageView(playSmall));
                        task.pauseThis();

                    } else if (task.isIsExecutedForFirstTime() == true && task.isPause() == true) {
                        task.getPauseTask().setGraphic(new ImageView(pauseSmall));
                        task.continueThis();

                    }
                }
            });
            
            
        }
    }

    /* removes selected task */
    private void closeTaskButtonClick() {

        for (TaskInOurProgram task : model.getTasksInTheTableView()) {
            task.getCloseTask().setGraphic(new ImageView(stopSmall));

            task.getCloseTask().setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Are you sure?");
                    alert.setContentText("It will be removed permanently. Are you sure?");
                    alert.showAndWait();
                    if (alert.getResult() == ButtonType.OK) {

                        if (task.isPause() == true) {
                            task.continueThis();
                            task.cancel();
                        }
                        task.cancel();
                        model.getTasksInTheTableView().remove(task);
                    } else {
                    }
                }
            }
            );
        }
    }

    /* HISTORY TAB */
    private void setHistoryTableViewColumns() {
        dateAndTimeColumn.setCellValueFactory(new PropertyValueFactory("dateAndTime"));
        taskNameColumn.setCellValueFactory(new PropertyValueFactory("fileName"));
        userNameColumn.setCellValueFactory(new PropertyValueFactory("username"));
        errorColumn.setCellValueFactory(new PropertyValueFactory("errorIcon"));
    }

    /* 1. show pop-up window after hovering over the row which has error
       2. change background color to red of rows with errors 
       3. show the image of the error in the end of the row
     */
    private void openErrorMessageAfterHoveringOverRow() {

        // calling the most powerful class in this universe
        historyTableView.setRowFactory(t -> new HistoryRow());

        for (History history : model.getAllHistoryObservableArrayList()) {
            if (history.isHasError() == true) {

                //show icon in the end of the row
                history.getErrorIcon().setImage(errorSmall);
            }
        }
    }

    /* creates new history for task */
    private void createHistoryForTask(TaskInOurProgram task, Boolean hasError, String errorMessage) {
        /* create new history after button is presset */
        History history = new History(model.getFormatedActualDateAndTimeAsString(), 1, model.getUserName(),
                "File " + task.getFileName() + " was converted", hasError, errorMessage);
        model.addHistoryToTheDatabase(history);
    }


    /* ends executor when the main window is closed */
    public void getStage(Stage stage) {
        stage.showingProperty().addListener(e -> {
            if (stage.isShowing() == false) {
                if (executor != null) {
                    try {
                        convertTasksButtonClick(new ActionEvent());
                    } catch (IOException ex) {
                        Logger.getLogger(MainFXMLController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    executor.shutdownNow();
                }
            }
        });
    }

    /* returns list of tasks that aleready exist in choser directory */
    private List<TaskInOurProgram> listOfFilesThatCanBeOverrided() {
        List<TaskInOurProgram> filesThatExist = new ArrayList();

        File[] files = directoryPath.listFiles();
        for (int i = 0; i < files.length; i++) {
            if (files[i].isFile()) {
             
                for (TaskInOurProgram task : model.getTasksInTheTableView()) {
                    if (files[i].getName().equals(task.getFileName()+".json") && task.isIsConvertingDone()==false
                          && task.getFilePath().toString().equals(directoryPath.getAbsolutePath().toString())) {
                        
                            filesThatExist.add(task);                          
                    }
                }
            }
        }
        return filesThatExist;
    }

    /* creates alert with overrite question for all tasks in list */
    private void createAlertIfFileExistsInReposytory(List<TaskInOurProgram> tasks) {
        for (TaskInOurProgram task : tasks) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("file exists");
            alert.setContentText(task.getFileName()+" aleready exists in chosen reposytory."
                    + " Do you want to override it?");
            alert.showAndWait();
            if (alert.getResult() == ButtonType.CANCEL) {
                model.getTasksInTheTableView().remove(task);
            }
        }
    }
    
    /* sorts history by username field */
    private void searchHistoryByUsername()
    {
        ObservableList<History> listOfAvailableHistory = FXCollections.observableArrayList();
        searchByUsernameField.textProperty().addListener(e ->{
            if(searchByUsernameField.getText().isEmpty())
            {
                historyTableView.setItems(model.getAllHistoryObservableArrayList());
            }
            else
            {
                listOfAvailableHistory.clear();
                for(History his : model.getAllHistoryObservableArrayList())
                {
                    if(his.getUsername().toLowerCase().startsWith(searchByUsernameField.getText().toLowerCase()))
                    listOfAvailableHistory.add(his);
                }
                historyTableView.setItems(listOfAvailableHistory);
            }
        });
    }
}

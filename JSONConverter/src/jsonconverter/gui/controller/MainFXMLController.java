package jsonconverter.GUI.controller;

import com.jfoenix.controls.JFXDatePicker;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
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
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.ProgressBarTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javax.swing.JOptionPane;
import jsonconverter.BE.Config;
import jsonconverter.BE.History;
import jsonconverter.BE.TaskInOurProgram;
import jsonconverter.GUI.model.Model;
import jsonconverter.GUI.util.HostName;

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
    private HostName hostName = new HostName();
    private TaskInOurProgram task;
    private ExecutorService executor = Executors.newFixedThreadPool(3);
    @FXML
    private Button chooseDirectoryButton;

    private Date fromDateInDatePicker;
    private final Image pauseImage = new Image("file:images/pauseImage.png");
    private final Image closeImage = new Image("file:images/close.png");
    private final Image playImage = new Image("file:images/playImage.png");
    private final Image errorImage = new Image("file:images/errorImage.png");
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
    Date fromDate;
    Date toDate;

    LocalDateTime nowLocalDateTime = LocalDateTime.now();
    java.sql.Date currentDate = java.sql.Date.valueOf(LocalDate.now());

    @FXML
    private Button editButton;

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

    public void activeDatePickersInHistoryTab() {
        startDateDatePicker.valueProperty().addListener(e -> {

            LocalDate dateFromInLocalDateForm = startDateDatePicker.getValue(); //putting date from datePicker to local date form           
            Calendar c = Calendar.getInstance(); // creating calendar instance as a helper 
            c.set(dateFromInLocalDateForm.getYear(), dateFromInLocalDateForm.getMonthValue() - 1,
                    dateFromInLocalDateForm.getDayOfMonth());
            fromDate = c.getTime();

            historyTableView.setItems(model.getHistoryOfChosenPeriod(model.getAllHistoryObservableArrayList(), fromDate, toDate));

        });

        endDateDatePicker.valueProperty().addListener(e -> {
            LocalDate dateEndInLocalDateForm = endDateDatePicker.getValue(); //putting date from datePicker to local date form
            Calendar c = Calendar.getInstance(); // creating calendar instance as a helper 
            c.set(dateEndInLocalDateForm.getYear(), dateEndInLocalDateForm.getMonthValue() - 1,
                    dateEndInLocalDateForm.getDayOfMonth());
            toDate = c.getTime();

            historyTableView.setItems(model.getHistoryOfChosenPeriod(model.getAllHistoryObservableArrayList(), fromDate, toDate));
        });
    }

    /* set promt text in datePickers to European style - first dat, then month and then year
       so it looks better after date is chosen
     */
    public void setPromptDateInDatePickerToEuropeanStyle() {

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

    public void pauseConvertingClick() {

        for (TaskInOurProgram task : model.getTasksInTheTableView()) {

            //set default image before clicking on the button
            task.getPauseTask().setGraphic(new ImageView(playImage));

            task.getPauseTask().setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {

                    if (task.isIsExecutedForFirstTime() == false && task.isPause() == false) {
                        executor.submit(task);
                        task.setIsExecutedForFirstTime(true);
                        task.getPauseTask().setGraphic(new ImageView(pauseImage));

                    } else if (task.isIsExecutedForFirstTime() == true && task.isPause() == false) {
                        task.getPauseTask().setGraphic(new ImageView(playImage));
                        task.pauseThis();

                    } else if (task.isIsExecutedForFirstTime() == true && task.isPause() == true) {
                        task.getPauseTask().setGraphic(new ImageView(pauseImage));
                        task.continueThis();

                    }
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

    @FXML
    private void convertTasksButtonClick(ActionEvent event) throws IOException {

        createHistoryOfWholeAction();

    }

    @FXML
    private void pauseTasksButtonClick(ActionEvent event) throws InterruptedException {
        executor.shutdownNow();
    }

    @FXML
    private void deleteTasksButtonClick(ActionEvent event) {
    }

    @FXML
    private void historyPageButtonClick(MouseEvent event) {
    }

    /* HISTORY TAB */
    public void setHistoryTableViewColumns() {
        dateAndTimeColumn.setCellValueFactory(new PropertyValueFactory("dateAndTime"));
        taskNameColumn.setCellValueFactory(new PropertyValueFactory("fileName"));
        userNameColumn.setCellValueFactory(new PropertyValueFactory("username"));
        errorColumn.setCellValueFactory(new PropertyValueFactory("errorIcon"));
    }


    /* 1. show pop-up window after hovering over the row which has error
       2. change background color to red of rows with errors 
       3. show the image of the error in the end of the row
     */
    public void openErrorMessageAfterHoveringOverRow() {
        historyTableView.setRowFactory(tableView -> {
            final TableRow<History> row = new TableRow<>();

            //for each loop to set image and color only
            for (History history : model.getAllHistoryObservableArrayList()) {
                if (history.isHasError() == true) {
                    //set style of this "error" history
                    row.getStyleClass().clear();
                    row.getStyleClass().add("errorHistoryRow");

                    //show icon in the end of the row
                    history.getErrorIcon().setImage(errorImage);
                }
            }

            Popup popup = new Popup();
            Stage stage = new Stage();

            row.hoverProperty().addListener((observable) -> {
                for (History his : model.getAllHistoryObservableArrayList()) {
                    final History historyRow = row.getItem();

                    if (row.isHover() && his == historyRow) {
                        //creation of the popup
                        popup.setX(300);
                        popup.setY(200);
                        TextArea ta = new TextArea();
                        ta.setText(his.getErrorMessage());
                        popup.getContent().addAll(ta);

                        //creation of the stage
                        HBox layout = new HBox(10);
                        stage.setScene(new Scene(layout));

                        stage.show();
                        popup.show(stage);
                    } else {

                    }
                }
            });
            popup.hide();
            stage.close();

            return row;
        });
    }

    /* HELPER METHODS */
    public String getFormatedActualDateAndTimeAsString() {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        String dateAndTimeString = df.format(nowLocalDateTime);
        return dateAndTimeString;
    }

    public void createHistoryForTask(TaskInOurProgram task) {
        /* create new history after button is presset */
        History history = new History(getFormatedActualDateAndTimeAsString(), 1, hostName.getUserName(),
                task.getFileName(), true, "Error");
        model.addHistoryToTheDatabase(history);
    }

    public void createHistoryOfWholeAction() {
        /* create new history after button is presset */
        History history = new History(getFormatedActualDateAndTimeAsString(), 1, hostName.getUserName(),
                "Multiple Conversion (" + tasksTableView.getItems().size() + " files)", true, "Error");
        model.addHistoryToTheDatabase(history);
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
                }
            }
        });

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

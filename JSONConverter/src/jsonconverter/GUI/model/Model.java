package jsonconverter.GUI.model;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import static java.time.temporal.ChronoField.MONTH_OF_YEAR;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import jsonconverter.BE.Config;
import jsonconverter.BE.History;
import jsonconverter.BE.TaskInOurProgram;
import jsonconverter.BLL.BLLManager;

public class Model {

    private BLLManager manager = new BLLManager();
    private LocalDateTime nowLocalDateTime = LocalDateTime.now();

    /* contains each task in tableview */
    private ObservableList<TaskInOurProgram> tasksInTheTableView = FXCollections.observableArrayList();
    /* contains each history from database */
    private ObservableList<History> allHistoryObservableArrayList = FXCollections.observableArrayList();
    /* contains history date based on chosen dates */
    private ObservableList<History> historyDatasBasedOnChosenTimeList = FXCollections.observableArrayList();
    /* sorted lists for all history, so new items will be on the top. This sorted list is used everywhere then */
    SortedList<History> sortedAllHistory = new SortedList<>(allHistoryObservableArrayList, Comparator.comparing(History::getDateAndTime).reversed());
    /* contains configs from the database  */
    private ObservableList<Config> allConfigObservableArrayList = FXCollections.observableArrayList();
    /* contains all files inside chosen folder */
    private List<File> allFilesInFolder = new ArrayList();

    //- - - - - - - - - - - - - - - - - - - - CREATE JASON - - - - - - - - - - - - - - - - - - - -
    /* creates json file from JSONObject list */
    public void createJsonFile(String fileName, File filePath, TaskInOurProgram cuttentTask) throws InterruptedException {
        manager.createJsonFile(fileName, filePath, cuttentTask);
    }

    //- - - - - - - - - - - - - - - - - - - - TASK - - - - - - - - - - - - - - - - - - - -
    /* adding tasks to the observableArrayList */
    public void addTask(TaskInOurProgram task) {
        tasksInTheTableView.add(task);
    }

    /* getting observableArrayList with the tasks */
    public ObservableList<TaskInOurProgram> getTasksInTheTableView() {
        return tasksInTheTableView;
    }

    //- - - - - - - - - - - - - - - - - - - - CONVERTER - - - - - - - - - - - - - - - - - - - -
    /*returns list of Headers from the file */
    public List<String> getOnlyFileHeaders() {
        return manager.getOnlyFileHeaders();
    }

    /* gets converter from current task */
    public void getConverter(TaskInOurProgram currentTask) {
        manager.getConverter(currentTask);
    }

    /* sets converter for chosen file */
    public void setConverter(String fileType, String filePath) {
        manager.setConverter(fileType, filePath);
    }

    /* returns hashMap of headers from file (Headers are keys and numbers are values) */
    public HashMap<String, Integer> getFileHeaders() {
        return manager.getFileHeaders();
    }

    /* returns values from the selected file */
    public ArrayList<String> getFileValues() {
        return manager.getFileValues();
    }

    //- - - - - - - - - - - - - - - - - - - - CONFIG - - - - - - - - - - - - - - - - - - - -
    /* gets all available configs for current user */
    public List<Config> getAllAvailableConfigs() {
        return manager.getAllAvailableConfigs();
    }

    /* gets all configs for current user */
    public List<Config> getAllConfigs() {
        return manager.getAllConfigs();
    }

    /* removes config from the database */
    public void removeConfigToDatabase(Config config) {
        manager.removeConfigFromDatabase(config);
        allConfigObservableArrayList.remove(config);
    }

    /* saves new config in the databast */
    public void saveConfigToDatabase(Config config, boolean isEditMode) {
        manager.saveConfigToDatabase(config, isEditMode);
        allConfigObservableArrayList.add(config);
    }

    /* returns the list of all configs */
    public ObservableList<Config> getAllConfigObservableArrayList() {
        return allConfigObservableArrayList;
    }

    /* loads available configs */
    public void loadAvailableConfig() {
        allConfigObservableArrayList.clear();
        allConfigObservableArrayList.setAll(getAllAvailableConfigs());
    }

    /* removes configs that dont match chosen file */
    public List<Config> checkIfYouCanUseConfig() {
        List<Config> superList = new ArrayList();
        int checkForErrors = 0;
        int i = 0;
        for (Config config : getAllAvailableConfigs()) {
            i = 0;
            checkForErrors = 0;
            while (i < 15) {
                String configString = config.getAllGetters(i);

                if (configString.contains("&&") && !configString.equals("")) {
                    String[] splitedConfig = configString.split("&&");
                    if (!getOnlyFileHeaders().contains(splitedConfig[0])) {
                        checkForErrors++;
                    }
                    if (!getOnlyFileHeaders().contains(splitedConfig[1])) {
                        checkForErrors++;
                    }
                } else if (!getOnlyFileHeaders().contains(configString) && !configString.equals("")) {
                    checkForErrors++;
                }
                i++;
            }
            if (checkForErrors == 0) {
                superList.add(config);
            }
        }
        return superList;
    }

    /* checks if config matches file */
    public boolean checkIfFileMatchesConfig(Config config) {
        int checkForErrors = 0;
        int i = 0;
        i = 0;
        checkForErrors = 0;
        while (i < 15) {
            String configString = config.getAllGetters(i);

            if (configString.contains("&&") && !configString.equals("")) {
                String[] splitedConfig = configString.split("&&");
                if (!getOnlyFileHeaders().contains(splitedConfig[0])) {
                    checkForErrors++;
                }
                if (!getOnlyFileHeaders().contains(splitedConfig[1])) {
                    checkForErrors++;
                }
            } else if (!getOnlyFileHeaders().contains(configString) && !configString.equals("")) {
                checkForErrors++;
            }
            i++;
        }
        if (checkForErrors == 0) {
            return true;
        }
        return false;
    }

    //- - - - - - - - - - - - - - - - - - - - HISTORY - - - - - - - - - - - - - - - - - - - -
    /* gets list of history */
    public ObservableList<History> getAllHistoryObservableArrayList() {
        return allHistoryObservableArrayList;
    }

    /* loads history from the database */
    public void loadHistoryFromDatabase() {
        allHistoryObservableArrayList.clear();
        allHistoryObservableArrayList.addAll(manager.getAllHistory());
    }

    /* gets history in chosen period of time */
    public ObservableList<History> getHistoryOfChosenPeriod(ObservableList<History> allHistory, Date from, Date to) {
        historyDatasBasedOnChosenTimeList.clear();
        for (History history : allHistory) {

            DateTimeFormatter f = new DateTimeFormatterBuilder()
                    .appendPattern("dd.MM.yyyy HH:mm")
                    .parseDefaulting(MONTH_OF_YEAR, 1)
                    .toFormatter();

            LocalDate dateFromLocalDateForm = LocalDate.parse(history.getDateAndTime(), f);
            LocalDate dateToLocalDateForm = LocalDate.parse(history.getDateAndTime(), f);

            Date dateFrom = Date.from(dateFromLocalDateForm.atStartOfDay(ZoneId.systemDefault()).toInstant());
            Date dateTo = Date.from(dateToLocalDateForm.atStartOfDay(ZoneId.systemDefault()).toInstant());

            if (!dateFrom.before((java.util.Date) from) && !dateTo.after((java.util.Date) to)) {
                historyDatasBasedOnChosenTimeList.add(history);
            }
        }
        return historyDatasBasedOnChosenTimeList;
    }

    /* add history to a database after some action is done */
    public void addHistoryToTheDatabase(History history) {
        allHistoryObservableArrayList.add(history);
        manager.addNewHistoryToDatabase(history);
    }

    /* returns sorted history list */
    public SortedList<History> getSortedAllHistory() {
        return sortedAllHistory;
    }

    //- - - - - - - - - - - - - - - - - - - - HOSTNAME - - - - - - - - - - - - - - - - - - - -
    /* returns local Hostname */
    public String getHostname() {
        return manager.getHostname();
    }

    /* returns local Username */
    public String getUserName() {
        return manager.getUserName();
    }

    //- - - - - - - - - - - - - - - - - - - - FILES IN FOLDER - - - - - - - - - - - - - - - - - - - -
    /* gets all files in list */
    public List<File> getAllFilesInFolder() {
        return allFilesInFolder;
    }

    /* adds files from the folder to the list */
    public void addFileFromTheFolder(File newFile) {
        allFilesInFolder.add(newFile);
    }

    //- - - - - - - - - - - - - - - - - - - - VALIDATIONS - - - - - - - - - - - - - - - - - - - -
    /* checks if config with this name aleready exists */
    public boolean checkIfConfigExists(Config config) {
        return manager.checkIfConfigExists(config, manager.getAllConfigs());
    }

    /* makes background of field red when it is filled incorrectly */
    public void changeColorIfWrong(Node node, String fieldText, List<String> headersList) {
        manager.changeColorIfWrong(node, fieldText, headersList);
    }

    /* checks if fields have red background */
    public boolean wrongInputValidation(AnchorPane pane) {
        return manager.wrongInputValidation(pane);
    }
    
    //- - - - - - - - - - - - - - - - - - - - OTHERS - - - - - - - - - - - - - - - - - - - -
    /* sets right format of the date */
    public String getFormatedActualDateAndTimeAsString() {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        String dateAndTimeString = df.format(nowLocalDateTime);
        return dateAndTimeString;
    }
    
    /* creates pop up alert window */
    public void Alert(String title, String text) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(text);
        alert.showAndWait();
    }
    
    /* closes window */
    public void closeWindow(Button button) {
        Stage stage = (Stage) button.getScene().getWindow();
        stage.close();
    }
    
}

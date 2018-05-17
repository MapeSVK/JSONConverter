/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsonconverter.GUI.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import impl.org.controlsfx.autocompletion.SuggestionProvider;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import jsonconverter.BE.Config;
import jsonconverter.GUI.model.Model;
import org.controlsfx.control.textfield.TextFields;

/**
 * FXML Controller class
 *
 * @author Pepe15224
 */
public class ConfigFXMLController implements Initializable {

    private Model model;

    @FXML
    private JFXTextField siteNameField;
    @FXML
    private JFXTextField assetSerialNumberField;
    @FXML
    private JFXTextField typeField;
    @FXML
    private JFXTextField externalWorkOrderIdField;
    @FXML
    private JFXTextField userStatusField;
    @FXML
    private JFXTextField createdOnField;
    @FXML
    private JFXTextField createdByField;
    @FXML
    private JFXTextField nameField;
    @FXML
    private JFXTextField priorityField;
    @FXML
    private JFXTextField statusField;
    @FXML
    private JFXTextField systemStatusField;
    @FXML
    private JFXTextField latestFinishDateField;
    @FXML
    private JFXTextField earliestStartDateField;
    @FXML
    private JFXTextField latestStartDateField;
    @FXML
    private JFXTextField estimatedTimeField;
    private String username;
    boolean isValid;
    @FXML
    private JFXButton saveConfigButton;
    @FXML
    private CheckBox checkBoxPrivacy;
    private boolean privacyBoolean;
    @FXML
    private JFXTextField headerNameField;
    @FXML
    private AnchorPane configFieldsPane;
    private SuggestionProvider<String> suggest;
    private ArrayList<String> headersList = new ArrayList<>();
    private int fieldsCounter = 0;
    private boolean isEditMode;
    private Config choosenConfig;

    @FXML
    private JFXButton removeconfigButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void saveButtonOnAction(ActionEvent event) throws ParseException {
        if (privacyBoolean) {
            username = model.getUserName();
        } else {
            username = "Unkown";
        }
     //   if (model.checkIfConfigExists(createConfig())) {
            model.saveConfigToDatabase(createConfig());
            closeWindow();
     //   } else {
      //      Alert("Config already exists", "Config with this name already exists!");
    //    }
    }

    protected Config getActualConfig(Config config) {
        choosenConfig = config;
        return config;
    }
        
    @FXML
    private void removeButtonOnAction(ActionEvent event) {
        model.removeConfigToDatabase(getActualConfig(choosenConfig));
    }

    /* binds textfields with autocompletion */
    private void addAutoCompletionToFields() {
        for (Node node : configFieldsPane.getChildren()) {
            if (node instanceof JFXTextField) {
                // clear

                TextFields.bindAutoCompletion(((JFXTextField) node), suggest);
            }
        }
    }

    /* gets converter of imported file */
    protected void getModel(Model model) {
        this.model = model;
        headersList.addAll(model.getOnlyFileHeaders());
        suggest = SuggestionProvider.create(headersList);
        addAutoCompletionToFields();
        checkTextProperty();
    }

    protected void setConfig(Config choosenConfig) throws ParseException {
        siteNameField.setText(choosenConfig.getSiteName());
        assetSerialNumberField.setText(choosenConfig.getAssetSerialNumber());
        createdOnField.setText(choosenConfig.getCreatedOn());
        createdByField.setText(choosenConfig.getCreatedBy());
        statusField.setText(choosenConfig.getStatus());
        estimatedTimeField.setText(choosenConfig.getEstimatedTime());
        typeField.setText(choosenConfig.getType());
        externalWorkOrderIdField.setText(choosenConfig.getExternalWorkOrderId());
        systemStatusField.setText(choosenConfig.getSystemStatus());
        userStatusField.setText(choosenConfig.getUserStatus());
        nameField.setText(choosenConfig.getName());
        priorityField.setText(choosenConfig.getPriority());
        latestFinishDateField.setText(choosenConfig.getLatestFinishDate());
        earliestStartDateField.setText(choosenConfig.getEarliestStartDate());
        latestStartDateField.setText(choosenConfig.getLatestStartDate());
        headerNameField.setText(choosenConfig.getConfigName());
        checkBoxPrivacy.setSelected(choosenConfig.isPrivacy());
        username = choosenConfig.getCreatorName();
    }

    /* creates config based on users texFields and saves it in the database */
    private Config createConfig() throws ParseException {
        Config newConfig = new Config();

        if (!siteNameField.getText().isEmpty()) {
            newConfig.setSiteName(siteNameField.getText());
        }
        if (!assetSerialNumberField.getText().isEmpty()) {
            newConfig.setAssetSerialNumber(assetSerialNumberField.getText());
        }
        if (!createdOnField.getText().isEmpty()) {
            newConfig.setCreatedOn(createdOnField.getText());
        }
        if (!createdByField.getText().isEmpty()) {
            newConfig.setCreatedBy(createdByField.getText());
        }
        if (!statusField.getText().isEmpty()) {
            newConfig.setStatus(statusField.getText());
        }
        if (!estimatedTimeField.getText().isEmpty()) {
            newConfig.setEstimatedTime(estimatedTimeField.getText());
        }
        newConfig.setType(typeField.getText());
        newConfig.setExternalWorkOrderId(externalWorkOrderIdField.getText());
        newConfig.setSystemStatus(systemStatusField.getText());
        newConfig.setUserStatus(userStatusField.getText());
        newConfig.setName(nameField.getText());
        newConfig.setPriority(priorityField.getText());
        newConfig.setLatestFinishDate(latestFinishDateField.getText());
        newConfig.setEarliestStartDate(earliestStartDateField.getText());
        newConfig.setLatestStartDate(latestStartDateField.getText());
        newConfig.setConfigName(headerNameField.getText());
        newConfig.setPrivacy(checkBoxPrivacy.isSelected());
        newConfig.setCreatorName(username);
        return newConfig;
    }

    private void Alert(String title, String text) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(text);
        alert.showAndWait();
    }

    private void closeWindow() {
        Stage stage = (Stage) saveConfigButton.getScene().getWindow();
        stage.close();
    }

    private void checkTextProperty() {
        for (Node node : configFieldsPane.getChildren()) {
            if (node instanceof JFXTextField) {
                ((JFXTextField) node).textProperty().addListener(e -> {
                    addAndRemoveHeadersFromBinding();
                });
            }
        }
    }

    private void addAndRemoveHeadersFromBinding() {

        for (String string : model.getOnlyFileHeaders()) {
            fieldsCounter = 0;
            for (Node node : configFieldsPane.getChildren()) {
                if (node instanceof JFXTextField) {
                    if (((JFXTextField) node).getText().equals(string)) {
                        headersList.remove(string);
                        suggest.clearSuggestions();
                        suggest.addPossibleSuggestions(headersList);
                    } else if (!((JFXTextField) node).getText().equals(string) && !headersList.contains(string)) {
                        fieldsCounter++;
                        if (fieldsCounter == 15) {
                            fieldsCounter = 0;
                            headersList.add(string);
                            suggest.clearSuggestions();
                            suggest.addPossibleSuggestions(headersList);
                        }
                    }

                }
            }
        }
    }
}

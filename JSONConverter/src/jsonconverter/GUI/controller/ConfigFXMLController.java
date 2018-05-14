/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsonconverter.GUI.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.stage.Stage;
import jsonconverter.BE.Config;
import jsonconverter.DAL.readFilesAndWriteJson.IConverter;
import jsonconverter.GUI.model.Model;
import jsonconverter.GUI.util.HostName;
import org.controlsfx.control.textfield.TextFields;

/**
 * FXML Controller class
 *
 * @author Pepe15224
 */
public class ConfigFXMLController implements Initializable {

    private Model model;
    private IConverter converter;

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
    private HostName hostNameClass;
    ArrayList<JFXTextField> arrayListWithTextFields = new ArrayList<JFXTextField>();
    boolean isValid;
    @FXML
    private JFXButton saveConfigButton;
    @FXML
    private CheckBox checkBoxPrivacy;
    private boolean privacyBoolean;
    @FXML
    private JFXTextField headerNameField;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    /* gets converter of imported file */
    public void getConverterandModel(IConverter converter, Model model) {
        this.converter = converter;
        this.model = model;
        addAutoCompletionToFields();
    }

    @FXML
    private void saveButtonOnAction(ActionEvent event) {
        if (privacyBoolean) {
            hostNameClass = new HostName();
            username = hostNameClass.userName;
        } else {
            username = "Unkown";
        }
        if (model.checkIfConfigExists(createAnd())) {
            //model.addToFakeConfigDatabase(createAnd()); //<---------------------------FAKE CONFIG
            model.saveConfigToDatabase(createAnd());
            closeWindow();
        } else {
            Alert("Config already exists", "Config with this name already exists!");
        }
    }

    /* binds textfields with autocompletion */
    private void addAutoCompletionToFields() {
        TextFields.bindAutoCompletion(siteNameField, model.getOnlyFileHeaders(converter));
        TextFields.bindAutoCompletion(assetSerialNumberField, model.getOnlyFileHeaders(converter));
        TextFields.bindAutoCompletion(typeField, model.getOnlyFileHeaders(converter));
        TextFields.bindAutoCompletion(externalWorkOrderIdField, model.getOnlyFileHeaders(converter));
        TextFields.bindAutoCompletion(systemStatusField, model.getOnlyFileHeaders(converter));
        TextFields.bindAutoCompletion(userStatusField, model.getOnlyFileHeaders(converter));
        TextFields.bindAutoCompletion(createdOnField, model.getOnlyFileHeaders(converter));
        TextFields.bindAutoCompletion(createdByField, model.getOnlyFileHeaders(converter));
        TextFields.bindAutoCompletion(nameField, model.getOnlyFileHeaders(converter));
        TextFields.bindAutoCompletion(priorityField, model.getOnlyFileHeaders(converter));
        TextFields.bindAutoCompletion(statusField, model.getOnlyFileHeaders(converter));
        TextFields.bindAutoCompletion(latestFinishDateField, model.getOnlyFileHeaders(converter));
        TextFields.bindAutoCompletion(earliestStartDateField, model.getOnlyFileHeaders(converter));
        TextFields.bindAutoCompletion(latestStartDateField, model.getOnlyFileHeaders(converter));
        TextFields.bindAutoCompletion(estimatedTimeField, model.getOnlyFileHeaders(converter));
        
    }


    /* creates config based on users texFields and saves it in the database */
    private Config createAnd() {
        Config newConfig = new Config(
                1,
                siteNameField.getText(),
                assetSerialNumberField.getText(),
                typeField.getText(),
                externalWorkOrderIdField.getText(),
                systemStatusField.getText(),
                userStatusField.getText(),
                createdOnField.getText(),
                createdByField.getText(),
                nameField.getText(),
                priorityField.getText(),
                statusField.getText(),
                latestFinishDateField.getText(),
                earliestStartDateField.getText(),
                latestStartDateField.getText(),
                estimatedTimeField.getText(),
                headerNameField.getText(),
                privacyBoolean,
                username);

        return newConfig;
    }

    private void arrayCreation() {
        arrayListWithTextFields.add(siteNameField);
        arrayListWithTextFields.add(assetSerialNumberField);
        arrayListWithTextFields.add(typeField);
        arrayListWithTextFields.add(externalWorkOrderIdField);
        arrayListWithTextFields.add(userStatusField);
        arrayListWithTextFields.add(createdOnField);
        arrayListWithTextFields.add(createdByField);
        arrayListWithTextFields.add(nameField);
        arrayListWithTextFields.add(priorityField);
        arrayListWithTextFields.add(statusField);
        arrayListWithTextFields.add(systemStatusField);
        arrayListWithTextFields.add(latestFinishDateField);
        arrayListWithTextFields.add(earliestStartDateField);
        arrayListWithTextFields.add(latestStartDateField);
        arrayListWithTextFields.add(estimatedTimeField);

    }

    /* VALIDATION */
    private void validation() {
        for (String header : model.getOnlyFileHeaders(converter)) {
            for (JFXTextField textField : arrayListWithTextFields) {
                if (textField.getText().equals(header)) {
                    isValid = true;
                } else {
                    Alert("Error", "Text imputs are not good! Check each text and then try it again!");
                }
            }   
        }
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

    @FXML
    private void checkBoxPrivacyOnAction(ActionEvent event) {
        privacyBoolean = checkBoxPrivacy.isSelected();
    }
}

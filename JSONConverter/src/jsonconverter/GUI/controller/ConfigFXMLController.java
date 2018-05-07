/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsonconverter.GUI.controller;

import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import jsonconverter.BE.Config;
import jsonconverter.DAL.readFilesAndWriteJson.IConverter;
import jsonconverter.GUI.model.Model;
import org.controlsfx.control.textfield.TextFields;

/**
 * FXML Controller class
 *
 * @author Pepe15224
 */
public class ConfigFXMLController implements Initializable {

    private Model model = new Model();
    private IConverter converter;

    @FXML
    private JFXTextField siteNameField;
    @FXML
    private JFXTextField assetSerialNumberField;
    @FXML
    private JFXTextField typeField;
    @FXML
    private JFXTextField externalWorkOrderIdField;
    private JFXTextField systemStatus;
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
    private JFXTextField latestFinishDate;
    private JFXTextField earliestStartDate;
    private JFXTextField latestStartDate;
    private JFXTextField estimatedTime;
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
    @FXML
    private JFXTextField headerNameField;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    /* gets converter of imported file */
    public void getConverter(IConverter converter) {
        this.converter = converter;
        addAutoCompletionToFields();
    }

    @FXML
    private void convert(ActionEvent event) {
        createAndSaveConfig();
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
    private void createAndSaveConfig() {
        Config newConfig = new Config(-1, siteNameField.getText(),
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
                estimatedTimeField.getText());
    }
}

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
import java.text.ParseException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import jsonconverter.BE.Config;
import jsonconverter.DAL.util.HostName;
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
    @FXML
    private JFXTextField headerNameField;
    @FXML
    private AnchorPane configFieldsPane;
    private SuggestionProvider<String> suggest;
    private ArrayList<String> headersList = new ArrayList<>();
    private int fieldsCounter = 0;
    private boolean isEditMode;
    private Config choosenConfig;
    private HostName HN = new HostName();

    @FXML
    protected JFXButton removeconfigButton;
    @FXML
    private JFXTextField siteNameFieldEmpty;
    @FXML
    private JFXTextField assetSerialNumberFieldEmpty;
    @FXML
    private JFXTextField typeFieldEmpty;
    @FXML
    private JFXTextField externalWorkOrderIdFieldEmpty;
    @FXML
    private JFXTextField systemStatusFieldEmpty;
    @FXML
    private JFXTextField userStatusFieldEmpty;
    @FXML
    private JFXTextField createdOnFieldEmpty;
    @FXML
    private JFXTextField createdByFieldEmpty;
    @FXML
    private JFXTextField priorityFieldEmpty;
    @FXML
    private JFXTextField nameFieldEmpty;
    @FXML
    private JFXTextField statusFieldEmpty;
    @FXML
    private JFXTextField latestFinishDateFieldEmpty;
    @FXML
    private JFXTextField earliestStartDateFieldEmpty;
    @FXML
    private JFXTextField latestStartDateFieldEmpty;
    @FXML
    private JFXTextField estimatedTimeFieldEmpty;

    private Tooltip tooltip;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (isEditMode) {
            setToolTips();
        }
    }

    private void setToolTips() {
        siteNameField.setTooltip(new Tooltip("Site Name"));
        assetSerialNumberField.setTooltip(new Tooltip("Asset Serial Number"));
        typeField.setTooltip(new Tooltip("Type"));
        externalWorkOrderIdField.setTooltip(new Tooltip("External Work Order ID"));
        systemStatusField.setTooltip(new Tooltip("System Status"));
        userStatusField.setTooltip(new Tooltip("User Status"));
        createdOnField.setTooltip(new Tooltip("Created on"));
        createdByField.setTooltip(new Tooltip("Created by"));
        priorityField.setTooltip(new Tooltip("Priority"));
        nameField.setTooltip(new Tooltip("Name"));
        statusField.setTooltip(new Tooltip("Status"));
        latestFinishDateField.setTooltip(new Tooltip("Latest Finish Date"));
        earliestStartDateField.setTooltip(new Tooltip("Earliest Start Date"));
        latestStartDateField.setTooltip(new Tooltip("Latest Start Date"));
        estimatedTimeField.setTooltip(new Tooltip("Estimated Time"));
    }

    @FXML
    private void saveButtonOnAction(ActionEvent event) throws ParseException {
        if (checkBoxPrivacy.isSelected()) {
            username = HN.userName;
        } else {
            username = "Unknown";
        }
        if (headerNameField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please, insert a valid name");
        } else {
            //   if (model.checkIfConfigExists(createConfig())) {
            model = new Model();
            model.saveConfigToDatabase(createConfig(), isEditMode);
            closeWindow();
        }
        //   } else {
        //      Alert("Config already exists", "Config with this name already exists!");
        //    }
    }

    protected boolean setEditMode() {
        return isEditMode = true;
    }

    @FXML
    private void removeButtonOnAction(ActionEvent event) {
        int selectedOption = JOptionPane.showConfirmDialog(null,
                "It will be removed permanently. Are you sure?",
                "Are you sure?",
                JOptionPane.YES_NO_OPTION);
        if (selectedOption == JOptionPane.YES_OPTION) {
            model = new Model();
            model.removeConfigToDatabase(choosenConfig);
            closeWindow();
        }
    }

    /* binds textfields with autocompletion */
    private void addAutoCompletionToFields() {
        for (Node node : configFieldsPane.getChildren()) {
            if (node instanceof JFXTextField) {
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

    protected Config setConfig(Config choosenConfig) throws ParseException {
        headerNameField.setText(choosenConfig.getConfigName());
        checkBoxPrivacy.setSelected(choosenConfig.isPrivacy());
        username = choosenConfig.getCreatorName();

        fillIfEmptyEdit(choosenConfig);

        removeconfigButton.setDisable(false);
        this.choosenConfig = choosenConfig;
        return choosenConfig;
    }

    /* creates config based on users texFields and saves it in the database */
    private Config createConfig() {
        Config newConfig = new Config();
        
        if (!siteNameField.getText().isEmpty() && siteNameFieldEmpty.isDisable() == false && !siteNameFieldEmpty.getText().isEmpty()) {
            newConfig.setSiteName(siteNameField.getText() + "&&" + siteNameFieldEmpty.getText());
        } else if (siteNameFieldEmpty.isDisable() == true) {
            newConfig.setSiteName(siteNameField.getText());
        }

        if (!assetSerialNumberField.getText().isEmpty() && assetSerialNumberFieldEmpty.isDisable() == false && !assetSerialNumberFieldEmpty.getText().isEmpty()) {
            newConfig.setAssetSerialNumber(siteNameField.getText() + "&&" + assetSerialNumberFieldEmpty.getText());
        } else if (assetSerialNumberFieldEmpty.isDisable() == true) {
            newConfig.setAssetSerialNumber(assetSerialNumberField.getText());
        }

        if (!createdOnField.getText().isEmpty() && createdOnFieldEmpty.isDisable() == false && !createdOnFieldEmpty.getText().isEmpty()) {
            newConfig.setCreatedOn(createdOnField.getText() + "&&" + createdOnFieldEmpty.getText());
        } else if (createdOnFieldEmpty.isDisable() == true) {
            newConfig.setCreatedOn(createdOnField.getText());
        }

        if (!createdByField.getText().isEmpty() && createdByFieldEmpty.isDisable() == false && !createdByFieldEmpty.getText().isEmpty()) {
            newConfig.setCreatedBy(createdByField.getText() + "&&" + createdByFieldEmpty.getText());
        } else if (createdByFieldEmpty.isDisable() == true) {
            newConfig.setCreatedBy(createdByField.getText());
        }

        if (!statusField.getText().isEmpty() && statusFieldEmpty.isDisable() == false && !statusFieldEmpty.getText().isEmpty()) {
            newConfig.setStatus(statusField.getText() + "&&" + statusFieldEmpty.getText());
        } else if (statusFieldEmpty.isDisable() == true) {
            newConfig.setStatus(statusField.getText());
        }

        if (!estimatedTimeField.getText().isEmpty() && estimatedTimeFieldEmpty.isDisable() == false && !estimatedTimeFieldEmpty.getText().isEmpty()) {
            newConfig.setEstimatedTime(estimatedTimeField.getText() + "&&" + estimatedTimeFieldEmpty.getText());
        } else if (statusFieldEmpty.isDisable() == true) {
            newConfig.setEstimatedTime(estimatedTimeField.getText());
        }

        if (!typeField.getText().isEmpty() && typeFieldEmpty.isDisable() == false && !typeFieldEmpty.getText().isEmpty()) {
            newConfig.setType(typeField.getText() + "&&" + typeFieldEmpty.getText());
        } else if (typeFieldEmpty.isDisable() == true) {
            newConfig.setType(typeField.getText());
        }

        if (!externalWorkOrderIdField.getText().isEmpty() && externalWorkOrderIdFieldEmpty.isDisable() == false && !externalWorkOrderIdFieldEmpty.getText().isEmpty()) {
            newConfig.setExternalWorkOrderId(externalWorkOrderIdField.getText() + "&&" + externalWorkOrderIdFieldEmpty.getText());
        } else if (externalWorkOrderIdFieldEmpty.isDisable() == true) {
            newConfig.setExternalWorkOrderId(externalWorkOrderIdField.getText());
        }

        if (!systemStatusField.getText().isEmpty() && systemStatusFieldEmpty.isDisable() == false && !systemStatusFieldEmpty.getText().isEmpty()) {
            newConfig.setSystemStatus(systemStatusField.getText() + "&&" + systemStatusFieldEmpty.getText());
        } else if (systemStatusFieldEmpty.isDisable() == true) {
            newConfig.setSystemStatus(systemStatusField.getText());
        }

        if (!userStatusField.getText().isEmpty() && userStatusFieldEmpty.isDisable() == false && !userStatusFieldEmpty.getText().isEmpty()) {
            newConfig.setUserStatus(userStatusField.getText() + "&&" + userStatusFieldEmpty.getText());
        } else if (userStatusFieldEmpty.isDisable() == true) {
            newConfig.setUserStatus(userStatusField.getText());
        }

        if (!nameField.getText().isEmpty() && nameFieldEmpty.isDisable() == false && !nameFieldEmpty.getText().isEmpty()) {
            newConfig.setName(nameField.getText() + "&&" + nameFieldEmpty.getText());
        } else if (nameFieldEmpty.isDisable() == true) {
            newConfig.setName(nameField.getText());
        }

        if (!priorityField.getText().isEmpty() && priorityFieldEmpty.isDisable() == false && !priorityFieldEmpty.getText().isEmpty()) {
            newConfig.setPriority(priorityField.getText() + "&&" + priorityFieldEmpty.getText());
        } else if (priorityFieldEmpty.isDisable() == true) {
            newConfig.setPriority(priorityField.getText());
        }

        if (!latestFinishDateField.getText().isEmpty() && latestFinishDateFieldEmpty.isDisable() == false && !latestFinishDateFieldEmpty.getText().isEmpty()) {
            newConfig.setLatestFinishDate(latestFinishDateField.getText() + "&&" + latestFinishDateFieldEmpty.getText());
        } else if (latestFinishDateFieldEmpty.isDisable() == true) {
            newConfig.setLatestFinishDate(latestFinishDateField.getText());
        }

        if (!earliestStartDateField.getText().isEmpty() && earliestStartDateFieldEmpty.isDisable() == false && !earliestStartDateFieldEmpty.getText().isEmpty()) {
            newConfig.setEarliestStartDate(earliestStartDateField.getText() + "&&" + earliestStartDateFieldEmpty.getText());
        } else if (earliestStartDateFieldEmpty.isDisable() == true) {
            newConfig.setEarliestStartDate(earliestStartDateField.getText());
        }

        if (!latestStartDateField.getText().isEmpty() && latestStartDateFieldEmpty.isDisable() == false && !latestStartDateFieldEmpty.getText().isEmpty()) {
            newConfig.setLatestStartDate(latestStartDateField.getText() + "&&" + latestStartDateFieldEmpty.getText());
        } else if (latestStartDateFieldEmpty.isDisable() == true) {
            newConfig.setLatestStartDate(latestStartDateField.getText());
        }

        newConfig.setConfigName(headerNameField.getText());
        newConfig.setPrivacy(checkBoxPrivacy.isSelected());
        newConfig.setCreatorName(username);
        newConfig.setCinfig_id(choosenConfig.getCinfig_id());

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
                        ifEmpyField(((JFXTextField) node).getId(), false);
                        headersList.remove(string);
                        suggest.clearSuggestions();
                        suggest.addPossibleSuggestions(headersList);
                    } else if (!((JFXTextField) node).getText().equals(string) && !headersList.contains(string)) {
                        fieldsCounter++;
                        if (fieldsCounter == 30) {
                            fieldsCounter = 0;
                            headersList.add(string);
                            suggest.clearSuggestions();
                            suggest.addPossibleSuggestions(headersList);
                        }
                    } else if (!model.getOnlyFileHeaders().contains(((JFXTextField) node).getText())) {
                        ifEmpyField(((JFXTextField) node).getId(), true);
                    }
                }
            }
        }
    }

    private void ifEmpyField(String originalField, boolean disable) {
        for (Node node : configFieldsPane.getChildren()) {
            if (node instanceof JFXTextField) {
                if (((JFXTextField) node).getId().equals(originalField + "Empty")) {
                    ((JFXTextField) node).setDisable(disable);
                }

            }
        }
    }

    private void fillIfEmptyEdit(Config choosenConfig) {
        int di = 0;
        for (Node node : configFieldsPane.getChildren()) {
            if (node instanceof JFXTextField) {

                if (di < 15) {
                    JFXTextField mainField = (JFXTextField) configFieldsPane.getChildren().get(di);
                    JFXTextField secondaryField = (JFXTextField) configFieldsPane.getChildren().get(di + 15);

                    if (choosenConfig.getAllGetters(di).contains("&&")) {
                        String[] splited = choosenConfig.getAllGetters(di).split("&&");

                        mainField.setText(splited[0]);
                        secondaryField.setDisable(false);
                        secondaryField.setText(splited[1]);
                    }
                    if (!choosenConfig.getAllGetters(di).contains("&&")) {
                        mainField.setText(choosenConfig.getAllGetters(di));
                    }
                    di++;
                }
            }
        }
    }
}

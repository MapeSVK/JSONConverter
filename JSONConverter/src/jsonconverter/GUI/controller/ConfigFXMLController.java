
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import jsonconverter.BE.Config;
import jsonconverter.BE.History;
import jsonconverter.GUI.model.Model;
import org.controlsfx.control.textfield.TextFields;


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
    @FXML
    private JFXButton saveConfigButton;
    @FXML
    private CheckBox checkBoxPrivacy;
    @FXML
    private JFXTextField headerNameField;
    @FXML
    public JFXButton removeconfigButton;
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
    @FXML
    private AnchorPane configFieldsPane;

    private SuggestionProvider<String> suggest;
    private ArrayList<String> headersList = new ArrayList<>();
    private int fieldsCounter = 0;
    private boolean isEditMode;
    private Config chosenConfig;
    boolean isValid;
    //private Tooltip tooltip;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    protected void setToolTips() {
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

    protected boolean setEditMode() {
        return isEditMode = true;
    }

    /* saves new config to the datbase*/
    @FXML
    private void saveButtonOnAction(ActionEvent event) throws ParseException {

        if (headerNameField.getText().isEmpty()) {
            model.Alert("Error", "Please, insert a valid name");
        } else {
            if (model.wrongInputValidation(configFieldsPane)) {
                if (model.checkIfConfigExists(createConfig(new Config())) && isEditMode==false){
                    model.saveConfigToDatabase(createConfig(new Config()));
                    createHistoryAfterAddingNewConfig(headerNameField.getText());             
                    model.closeWindow(saveConfigButton);
                    
                } else if(isEditMode==true)
                {
                    model.editConfig(createConfig(chosenConfig));
                    model.getAllConfigObservableArrayList().remove(chosenConfig);
                    model.getAllConfigObservableArrayList().add(chosenConfig);
                    model.closeWindow(saveConfigButton);
                    createHistoryAfterEditConfig(headerNameField.getText());
                }
                 else {
                    model.Alert("Configuration already exists", "Configuration with this name already exists!");
                }
            } else {
                model.Alert("Fields filled incorrectly", "Fill fields correctly");
            }
        }
    }

    /*removes config from database */
    @FXML
    private void removeButtonOnAction(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Are you sure?");
        alert.setContentText("It will be removed permanently. Are you sure?");
        alert.showAndWait();
        if (alert.getResult() == ButtonType.OK) {
        model.removeConfigToDatabase(chosenConfig);      
        createHistoryAfterDeletingNewConfig(chosenConfig); //create history row after removing
        model.closeWindow(saveConfigButton);
        }
    }

    /* gets converter of imported file */
    public void getModel(Model model) {
        this.model = model;
        headersList.addAll(model.getOnlyFileHeaders());
        suggest = SuggestionProvider.create(headersList);
        addAutoCompletionToFields();
        checkTextProperty();
    }

    /* binds textfields with autocompletion */
    private void addAutoCompletionToFields() {
        for (Node node : configFieldsPane.getChildren()) {
            if (node instanceof JFXTextField) {
                TextFields.bindAutoCompletion(((JFXTextField) node), suggest);
                model.changeColorIfWrong(node, ((JFXTextField) node).getText(), model.getOnlyFileHeaders());
            }
        }
    }

    public Config setConfig(Config choosenConfig) throws ParseException {
        headerNameField.setText(choosenConfig.getConfigName());
        checkBoxPrivacy.setSelected(choosenConfig.isPrivacy());
        fillIfEmptyEdit(choosenConfig);
        removeconfigButton.setDisable(false);
        this.chosenConfig = choosenConfig;
        return choosenConfig;
    }

    /* creates config based on users text fields and saves it in the database */
    private Config createConfig(Config newConfig) {    

        if (!siteNameField.getText().isEmpty() && siteNameFieldEmpty.isDisable() == false && !siteNameFieldEmpty.getText().isEmpty()) {
            newConfig.setSiteName(siteNameField.getText() + "&&" + siteNameFieldEmpty.getText());
        } else {
            newConfig.setSiteName(siteNameField.getText());
        }

        if (!assetSerialNumberField.getText().isEmpty() && assetSerialNumberFieldEmpty.isDisable() == false && !assetSerialNumberFieldEmpty.getText().isEmpty()) {
            newConfig.setAssetSerialNumber(siteNameField.getText() + "&&" + assetSerialNumberFieldEmpty.getText());
        } else {
            newConfig.setAssetSerialNumber(assetSerialNumberField.getText());
        }

        if (!createdOnField.getText().isEmpty() && createdOnFieldEmpty.isDisable() == false && !createdOnFieldEmpty.getText().isEmpty()) {
            newConfig.setCreatedOn(createdOnField.getText() + "&&" + createdOnFieldEmpty.getText());
        } else {
            newConfig.setCreatedOn(createdOnField.getText());
        }

        if (!createdByField.getText().isEmpty() && createdByFieldEmpty.isDisable() == false && !createdByFieldEmpty.getText().isEmpty()) {
            newConfig.setCreatedBy(createdByField.getText() + "&&" + createdByFieldEmpty.getText());
        } else {
            newConfig.setCreatedBy(createdByField.getText());
        }

        if (statusFieldEmpty.isDisable() == false && !statusFieldEmpty.getText().isEmpty()) {
            newConfig.setStatus(statusField.getText() + "&&" + statusFieldEmpty.getText());
        } else {
            newConfig.setStatus(statusField.getText());
        }

        if (estimatedTimeFieldEmpty.isDisable() == false && !estimatedTimeFieldEmpty.getText().isEmpty()) {
            newConfig.setEstimatedTime(estimatedTimeField.getText() + "&&" + estimatedTimeFieldEmpty.getText());
        } else {
            newConfig.setEstimatedTime(estimatedTimeField.getText());
        }

        if (typeFieldEmpty.isDisable() == false && !typeFieldEmpty.getText().isEmpty()) {
            newConfig.setType(typeField.getText() + "&&" + typeFieldEmpty.getText());
        } else if (!typeField.getText().isEmpty()) {
            newConfig.setType(typeField.getText());
        }

        if (externalWorkOrderIdFieldEmpty.isDisable() == false && !externalWorkOrderIdFieldEmpty.getText().isEmpty()) {
            newConfig.setExternalWorkOrderId(externalWorkOrderIdField.getText() + "&&" + externalWorkOrderIdFieldEmpty.getText());
        } else if (!externalWorkOrderIdField.getText().isEmpty()) {
            newConfig.setExternalWorkOrderId(externalWorkOrderIdField.getText());
        }

        if (systemStatusFieldEmpty.isDisable() == false && !systemStatusFieldEmpty.getText().isEmpty()) {
            newConfig.setSystemStatus(systemStatusField.getText() + "&&" + systemStatusFieldEmpty.getText());
        } else if (!systemStatusField.getText().isEmpty()) {
            newConfig.setSystemStatus(systemStatusField.getText());
        }

        if (userStatusFieldEmpty.isDisable() == false && !userStatusFieldEmpty.getText().isEmpty()) {
            newConfig.setUserStatus(userStatusField.getText() + "&&" + userStatusFieldEmpty.getText());
        } else if (!userStatusField.getText().isEmpty()) {
            newConfig.setUserStatus(userStatusField.getText());
        }

        if (nameFieldEmpty.isDisable() == false && !nameFieldEmpty.getText().isEmpty()) {
            newConfig.setName(nameField.getText() + "&&" + nameFieldEmpty.getText());
        } else if (!nameField.getText().isEmpty()) {
            newConfig.setName(nameField.getText());
        }

        if (priorityFieldEmpty.isDisable() == false && !priorityFieldEmpty.getText().isEmpty()) {
            newConfig.setPriority(priorityField.getText() + "&&" + priorityFieldEmpty.getText());
        } else if (!priorityField.getText().isEmpty()) {
            newConfig.setPriority(priorityField.getText());
        }

        if (latestFinishDateFieldEmpty.isDisable() == false && !latestFinishDateFieldEmpty.getText().isEmpty()) {
            newConfig.setLatestFinishDate(latestFinishDateField.getText() + "&&" + latestFinishDateFieldEmpty.getText());
        } else if (!latestFinishDateField.getText().isEmpty()) {
            newConfig.setLatestFinishDate(latestFinishDateField.getText());
        }

        if (earliestStartDateFieldEmpty.isDisable() == false && !earliestStartDateFieldEmpty.getText().isEmpty()) {
            newConfig.setEarliestStartDate(earliestStartDateField.getText() + "&&" + earliestStartDateFieldEmpty.getText());
        } else if (!earliestStartDateField.getText().isEmpty()) {
            newConfig.setEarliestStartDate(earliestStartDateField.getText());
        }

        if (latestStartDateFieldEmpty.isDisable() == false && !latestStartDateFieldEmpty.getText().isEmpty()) {
            newConfig.setLatestStartDate(latestStartDateField.getText() + "&&" + latestStartDateFieldEmpty.getText());
        } else if (!latestStartDateField.getText().isEmpty()) {
            newConfig.setLatestStartDate(latestStartDateField.getText());
        }

        newConfig.setConfigName(headerNameField.getText());
        newConfig.setPrivacy(checkBoxPrivacy.isSelected());
        newConfig.setCreatorName(model.getUserName());

        return newConfig;
    }

    /* updates helpers */
    private void checkTextProperty() {
        for (Node node : configFieldsPane.getChildren()) {
            if (node instanceof JFXTextField) {
                ((JFXTextField) node).textProperty().addListener(e -> {
                    addAndRemoveHeadersFromBinding();
                    model.changeColorIfWrong(node, ((JFXTextField) node).getText(), model.getOnlyFileHeaders());
                });
            }
        }
    }

    /* removes helper if it is in use */
    private void addAndRemoveHeadersFromBinding() {

        for (String string : model.getOnlyFileHeaders()) {
            fieldsCounter = 0;
            for (Node node : configFieldsPane.getChildren()) {
                if (node instanceof JFXTextField) {
                    if (((JFXTextField) node).getText().equals(string)) {
                        DisableSecondaryField(((JFXTextField) node).getId(), false);
                        headersList.remove(string);
                        suggest.clearSuggestions();
                        suggest.addPossibleSuggestions(headersList);
                    } else if (!((JFXTextField) node).getText().equals(string) && !headersList.contains(string)) {
                        fieldsCounter++;
                        if (fieldsCounter == 45) {
                            fieldsCounter = 0;
                            headersList.add(string);
                            suggest.clearSuggestions();
                            suggest.addPossibleSuggestions(headersList);
                        }
                    } else if (!model.getOnlyFileHeaders().contains(((JFXTextField) node).getText())) {
                        DisableSecondaryField(((JFXTextField) node).getId(), true);
                    }
                }
            }
        }
    }

    /* disables secondary field if the main field hasnt got proper value */
    private void DisableSecondaryField(String originalField, boolean disable) {
        for (Node node : configFieldsPane.getChildren()) {
            if (node instanceof JFXTextField) {
                if (((JFXTextField) node).getId().equals(originalField + "Empty")) {
                    ((JFXTextField) node).setDisable(disable);
                }

            }
        }
    }

    /* fills sedondary and main fields when confid is on edited */
    private void fillIfEmptyEdit(Config choosenConfig) {
        int di = 0;
        for (Node node : configFieldsPane.getChildren()) {
            if (node instanceof JFXTextField) {

                if (di < 15) {
                    JFXTextField mainField = (JFXTextField) configFieldsPane.getChildren().get(di+35);
                    JFXTextField secondaryField = (JFXTextField) configFieldsPane.getChildren().get(di + 15+35);

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
    
    /* ADDING TO THE HISTORY  */
    
    /* add config */
    private void createHistoryAfterAddingNewConfig(String nameOfTheConfig) {
        /* create new history after button is presset */
        History history = new History(model.getFormatedActualDateAndTimeAsString(), 1, model.getUserName(),
                nameOfTheConfig + " configuration was created", false, "");
        model.addHistoryToTheDatabase(history);
    }

    /* edit config */
    private void createHistoryAfterEditConfig(String nameOfTheConfig) {
        /* create new history after button is presset */
        History history = new History(model.getFormatedActualDateAndTimeAsString(), 1, model.getUserName(),
                nameOfTheConfig+ " configuration was eddited", false, "");
        model.addHistoryToTheDatabase(history);
    }
    
    /* delete config */
    private void createHistoryAfterDeletingNewConfig(Config config) {
        /* create new history after button is presset */
        History history = new History(model.getFormatedActualDateAndTimeAsString(), 1, model.getUserName(),
                config.getConfigName() + " configuration was deleted", false, "");
        model.addHistoryToTheDatabase(history);
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsonconverter.GUI.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import jsonconverter.BE.CsvObject;
import jsonconverter.BE.Header;
import jsonconverter.BE.Planning;
import jsonconverter.DAL.readAndSave.IConverter;
import jsonconverter.GUI.model.Model;

/**
 * FXML Controller class
 *
 * @author Pepe15224
 */
public class ConfigFXMLController implements Initializable {

    private String fileType;
    private IConverter converter;
    Header head;
    Model model = new Model();
    Integer[] saveNumbers = new Integer[16];
    
    public void setFileTypeAndConverter(String fileType, IConverter converter) {
        this.fileType = fileType;
        this.converter = converter;
    }
    
    @FXML
    private JFXTextField siteNameField;
    @FXML
    private JFXTextField assetSerialNumberField;
    @FXML
    private JFXTextField typeField;
    @FXML
    private JFXTextField externalWorkOrderIdField;
    @FXML
    private JFXTextField systemStatus;
    @FXML
    private JFXTextField userStatusField;
    @FXML
    private JFXTextField createdOnField;
    @FXML
    private JFXPasswordField createdByField;
    @FXML
    private JFXPasswordField nameField;
    @FXML
    private JFXPasswordField priorityField;
    @FXML
    private JFXTextField statusField;
    @FXML
    private JFXTextField latestFinishDate;
    @FXML
    private JFXTextField earliestStartDate;
    @FXML
    private JFXPasswordField latestStartDate;
    @FXML
    private JFXPasswordField estimatedTime;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void convert(ActionEvent event) throws IOException {
        head = new Header(-1,
               siteNameField.getText(),
               assetSerialNumberField.getText(),
                typeField.getText(),
                externalWorkOrderIdField.getText(),
                systemStatus.getText(),
                userStatusField.getText(),
                createdOnField.getText(),
                createdByField.getText(),
                nameField.getText(),
                priorityField.getText(),
                statusField.getText(),
                latestFinishDate.getText(),
                earliestStartDate.getText(),
                latestStartDate.getText(),
                estimatedTime.getText());
        testJasonfile();
    }
    
    private void testJasonfile() throws IOException
    {
       ObjectMapper mapper = new ObjectMapper(); 
mapper.enable(SerializationFeature.INDENT_OUTPUT); 
mapper.writeValue(System.out, testGetValue());
    }
    private List<CsvObject> testGetValue()
    {
         List<CsvObject> cobjectList = new ArrayList();
        for(String line : model.getCSVValues(converter))
        {
            String[] fields = line.split(";");
            CsvObject aaa = new CsvObject(fields[model.getCSVHeaders(converter).get(head.getSiteName())],
                    fields[model.getCSVHeaders(converter).get(head.getAssetSerialNumber())],
                    fields[model.getCSVHeaders(converter).get(head.getType())],
                   fields[model.getCSVHeaders(converter).get(head.getExternalWorkOrderId())],
                   fields[model.getCSVHeaders(converter).get(head.getSystemStatus())],
                    fields[model.getCSVHeaders(converter).get(head.getUserStatus())], 
                    fields[model.getCSVHeaders(converter).get(head.getCreatedOn())], 
                    fields[model.getCSVHeaders(converter).get(head.getCreatedBy())],
                    fields[model.getCSVHeaders(converter).get(head.getName())],
                   fields[model.getCSVHeaders(converter).get(head.getPriority())],
                    fields[model.getCSVHeaders(converter).get(head.getStatus())], 
                    new Planning(fields[model.getCSVHeaders(converter).get(head.getLatestFinishDate())],
                            fields[model.getCSVHeaders(converter).get(head.getEarliestStartDate())],
                            fields[model.getCSVHeaders(converter).get(head.getLatestStartDate())], 
                           fields[model.getCSVHeaders(converter).get(head.getEstimatedTime())]));
            cobjectList.add(aaa);
        }
        return cobjectList;
    }
}

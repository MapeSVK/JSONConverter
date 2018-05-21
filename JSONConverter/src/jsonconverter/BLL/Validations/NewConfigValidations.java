/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsonconverter.BLL.Validations;

import com.jfoenix.controls.JFXTextField;
import java.util.List;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import jsonconverter.BE.Config;

/**
 *
 * @author Pepe15224
 */
public class NewConfigValidations {

    /* checks if config with this name aleready exists */
    public boolean checkIfConfigExists(Config config, List<Config> configList) {
        for (Config configInList : configList) {
            if (configInList.getConfigName().equals(config.getConfigName())) {
                return false;
            }
        }
        return true;
    }

    public void changeColorIfWrong(Node node, String fieldText, List<String> headersList) {
        if (((JFXTextField) node).getId().equals("siteNameField")
                || ((JFXTextField) node).getId().equals("assetSerialNumberField")
                || ((JFXTextField) node).getId().equals("createdOnField")
                || ((JFXTextField) node).getId().equals("createdByField")
                || ((JFXTextField) node).getId().equals("statusField")
                || ((JFXTextField) node).getId().equals("estimatedTimeField")
                || ((JFXTextField) node).getId().contains("Empty")) {
            if (!headersList.contains(fieldText) && !fieldText.equals("")) {
                ((JFXTextField) node).setStyle("-fx-background-color : red");
            } else {
                ((JFXTextField) node).setStyle("-fx-background-color :");
            }
        } else {
            if (fieldText.isEmpty() || !headersList.contains(fieldText)) {
                ((JFXTextField) node).setStyle("-fx-background-color : red");
            } else {
                ((JFXTextField) node).setStyle("-fx-background-color :");
            }
        }
    }

    public boolean wrongInputValidation(AnchorPane pane) {
        for (Node node : pane.getChildren()) {
            if (node instanceof JFXTextField) {

                if (((JFXTextField) node).getStyle().equals("-fx-background-color : red")) {
                    return false;
                }
            }
        }
        return true;
    }
}

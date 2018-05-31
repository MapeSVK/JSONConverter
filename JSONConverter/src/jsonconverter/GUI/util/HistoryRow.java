/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsonconverter.GUI.util;

import java.awt.MouseInfo;
import java.awt.Point;
import javafx.css.PseudoClass;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableRow;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import jsonconverter.BE.History;

/**
 *
 * @author Mape
 */
public class HistoryRow extends TableRow<History> {

    private static final PseudoClass ERROR = PseudoClass.getPseudoClass("error");
    Stage stage;

    public HistoryRow() {
        hoverProperty().addListener((o, oldValue, newValue) -> {

            Point p = MouseInfo.getPointerInfo().getLocation();
            int x = p.x;
            int y = p.y;

            Popup popup = new Popup();
            popup.setX(x - 300);
            popup.setY(y - 200);

            // error label
            Label errorLabel = new Label();
            errorLabel.setStyle("-fx-text-fill: #FE807F; -fx-font: 12px Arial; -fx-font-weight: bold;-fx-padding: 20 20 20 20");
            errorLabel.setMaxWidth(300);
            errorLabel.setAlignment(Pos.CENTER);
            errorLabel.setWrapText(true);
            
            //anchor pane
            AnchorPane background = new AnchorPane();
            background.setStyle("-fx-background-color: #000;-fx-background-radius:8px");
            background.setPrefHeight(100);
            background.setPrefWidth(400);
            
            //add label to the anchor pane
            background.getChildren().add(errorLabel);
            
            //create layout and scene
            AnchorPane layout = new AnchorPane();
            Scene scene = new Scene(layout);
            
            //put this scene to the stage
            stageSingleton().setScene(scene);
            
            if (newValue) {
                History historyRow = getItem();
                
                if (historyRow != null && historyRow.isHasError()) {
                    errorLabel.setText(historyRow.getErrorMessage());
                    
                    popup.getContent().addAll(background);
                    stageSingleton().show();
                    popup.show(stageSingleton());
                }
            } else {
                popup.hide();
                stageSingleton().close();
            }
        });
    }

    @Override
    protected void updateItem(History history, boolean empty) {
        super.updateItem(history, empty);

        pseudoClassStateChanged(ERROR, !empty && history != null && history.isHasError());

    }

    public Stage stageSingleton() {

        if (stage == null) {
            stage = new Stage();
            stage.setWidth(1);
            stage.setHeight(1);
            stage.initStyle(StageStyle.UNDECORATED);
        }
        return stage;

    }

}

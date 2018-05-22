/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsonconverter.GUI.util;

import java.awt.MouseInfo;
import java.awt.Point;
import javafx.css.PseudoClass;
import javafx.scene.Scene;
import javafx.scene.control.TableRow;
import javafx.scene.control.TextArea;
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
            TextArea ta = new TextArea();
            ta.getStyleClass().add("errorTextArea");
          
            AnchorPane layout = new AnchorPane();
            Scene scene = new Scene(layout);
            stageSingleton().setScene(scene);
            
            if (newValue) {
                History historyRow = getItem();
                
                if (historyRow != null && historyRow.isHasError()) {
                    ta.setText(historyRow.getErrorMessage());
                    popup.getContent().addAll(ta);
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

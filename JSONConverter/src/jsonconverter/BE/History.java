/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsonconverter.BE;

import java.util.Date;
import javafx.scene.control.Button;

/**
 *
 * @author Samuel
 */
public class History {

    private int id;
    private String username;
    private String fileName;
    private boolean hasError;
    private String errorMessage;
    private Button errorButton;
    private Date dateAndTime;
//    private DateFormat df = new SimpleDateFormat("dd.MM.yyyy'  'HH:mm");
//    private String dateAndTimeString = df.format(dateAndTime);

    public History(Date dateAndTime, int id, String username, String fileName, boolean hasError, String errorMessage) {
        this.dateAndTime = dateAndTime;
        this.id = id;
        this.username = username;
        this.fileName = fileName;
        this.hasError = hasError;
        this.errorMessage = errorMessage;
        this.errorButton = new Button("");
    }

    public Button getErrorButton() {
        return errorButton;
    }

    public void setErrorButton(Button errorButton) {
        this.errorButton = errorButton;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public boolean isHasError() {
        return hasError;
    }

    public void setHasError(boolean hasError) {
        this.hasError = hasError;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTime(Date dateAndTime) {
        this.dateAndTime = dateAndTime;
    }

    @Override
    public String toString() {
        return "History{" + "dateAndTime=" + dateAndTime + ", id=" + id + ", username=" + username + ", fileName=" + fileName + ", hasError=" + hasError + ", errorMessage=" + errorMessage + ", errorButton=" + errorButton + '}';
    }

}

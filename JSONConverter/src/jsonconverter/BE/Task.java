/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsonconverter.BE;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import jsonconverter.GUI.util.RingProgressIndicator;

/**
 *
 * @author Mape
 */
public class Task {
    
    private String nameOfTheFile;
    private String configName;
    ImageView closeTask;
    ImageView pauseTask;
    RingProgressIndicator ringProgressIndicator;
    private final Image pauseImage = new Image("file:images/pause.png");
    private final Image closeImage = new Image("file:images/close.png");
    private int progress = 0;


    public Task(String name, String configName) {
        this.nameOfTheFile = name;
        this.configName = configName;
        this.closeTask = new ImageView();
        closeTask.setImage(closeImage);
        this.pauseTask = new ImageView();
        pauseTask.setImage(pauseImage);
        this.ringProgressIndicator = new RingProgressIndicator();
        setRingProgressIndicator(ringProgressIndicator);
        
    }


    
    
    
    
    public void setRingProgressIndicator (RingProgressIndicator rpi) {
        rpi.setRingWidth(30); // requires changes in the CSS file (2 places)
        rpi.makeIndeterminate();
    }

    public String getConfigName() {
        return configName;
    }

    public void setConfigName(String configName) {
        this.configName = configName;
    }

    public String getNameOfTheFile() {
        return nameOfTheFile;
    }

    public void setNameOfTheFile(String nameOfTheFile) {
        this.nameOfTheFile = nameOfTheFile;
    }

    public ImageView getCloseTask() {
        return closeTask;
    }

    public void setCloseTask(ImageView closeTask) {
        this.closeTask = closeTask;
    }

    public ImageView getStopTask() {
        return pauseTask;
    }

    public void setStopTask(ImageView stopTask) {
        this.pauseTask = pauseTask;
    }
    
    public RingProgressIndicator getRingProgressIndicator() {
        return ringProgressIndicator;
    }
    
    
}

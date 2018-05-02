package jsonconverter.BE;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import jsonconverter.GUI.util.RingProgressIndicator;


public class TaskInOurProgram {
    
    private String extensionOfTheFile;
    private String nameOfTheFile;
    private String configName;
    ImageView closeTask;
    ImageView pauseTask;
    private final Image pauseImage = new Image("file:images/pause.png");
    private final Image closeImage = new Image("file:images/close.png");
  
    public TaskInOurProgram(String name, String configName, String extensionOfTheFile /*RingProgressIndicator rpi*/) {
        this.nameOfTheFile = name;
        this.configName = configName;
        this.extensionOfTheFile = extensionOfTheFile;
        this.closeTask = new ImageView();
        closeTask.setImage(closeImage);
        this.pauseTask = new ImageView();
        pauseTask.setImage(pauseImage);
       
        
    }

    public String getExtensionOfTheFile() {
        return extensionOfTheFile;
    }

    public void setExtensionOfTheFile(String extensionOfTheFile) {
        this.extensionOfTheFile = extensionOfTheFile;
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

}

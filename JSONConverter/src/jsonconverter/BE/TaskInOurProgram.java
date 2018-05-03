package jsonconverter.BE;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import jsonconverter.GUI.util.RingProgressIndicator;


public class TaskInOurProgram extends Task<Void> {
    
    private String extensionOfTheFile;
    private String nameOfTheFile;
    private String configName;
    ImageView closeTask;
    ImageView pauseTask;
    private final Image pauseImage = new Image("file:images/pause.png");
    private final Image closeImage = new Image("file:images/close.png");
    public static final int NUM_ITERATIONS = 100;
  
    public TaskInOurProgram(String name, String configName, String extensionOfTheFile) {
        this.nameOfTheFile = name;
        this.configName = configName;
        this.extensionOfTheFile = extensionOfTheFile;
        this.closeTask = new ImageView();
        closeTask.setImage(closeImage);
        this.pauseTask = new ImageView();
        pauseTask.setImage(pauseImage);
       
        
    }
 
    
    @Override
    protected Void call() throws Exception {
      this.updateProgress(ProgressIndicator.INDETERMINATE_PROGRESS, 1);
      this.updateMessage("Waiting...");
      
        System.out.println("tvoja mamka");
        
      this.updateMessage("Running...");
      for (int i = 0; i < NUM_ITERATIONS; i++) {
        updateProgress((1.0 * i) / NUM_ITERATIONS, 1);
        
        System.out.println("tvoj tatko");
        
      }
      this.updateMessage("Done");
      this.updateProgress(1, 1);
      return null;
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

package jsonconverter.BE;

import static impl.org.controlsfx.spreadsheet.RectangleSelection.SelectionRange.key;
import java.io.File;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import jsonconverter.DAL.readFilesAndWriteJson.IConverter;
import jsonconverter.GUI.model.Model;

public class TaskInOurProgram extends Task<Void> {

    private String extensionOfTheFile;
    private String nameOfTheFile;
    private String configName;
    private Config config;
    private IConverter converter;
    private Model model = new Model();
    private Object lock = this;
    private boolean pause = false;
    private String fileName;
    private File filePath;
    private boolean isConvertingDone = false;
    private Button pauseTask;
    private Button closeTask;
            
    public TaskInOurProgram(String name, String configName, String extensionOfTheFile) {
        this.nameOfTheFile = name;
        this.configName = configName;
        this.extensionOfTheFile = extensionOfTheFile;
        this.pauseTask = new Button("");
 //       this.closeTask = new Button("");       this needs to be added in second sprint
        
 
        this.pauseTask.getStyleClass().clear();
        this.pauseTask.getStyleClass().add("pauseButtons");
        
    }

    @Override
    public Void call() throws Exception {

        this.updateProgress(ProgressIndicator.INDETERMINATE_PROGRESS, 1);
        updateProgress(1, 3);
        TimeUnit.SECONDS.sleep(3);
        pauseThread();
        updateProgress(2, 3);
        TimeUnit.SECONDS.sleep(3);
        pauseThread();
        model.createJsonFile(fileName, filePath, converter, config);
        updateProgress(3, 3);
        isConvertingDone = true;
        System.out.println(isConvertingDone);
      return null;
    }

    public boolean isIsConvertingDone() {
        return isConvertingDone;
    }

    public void setIsConvertingDone(boolean isConvertingDone) {
        this.isConvertingDone = isConvertingDone;
    }
    
    public Button getPauseTask() {
        return pauseTask;
    }

    public void setPauseTask(Button pauseTask) {
        this.pauseTask = pauseTask;
    }

    public Button getCloseTask() {
        return closeTask;
    }

    public void setCloseTask(Button closeTask) {
        this.closeTask = closeTask;
    }
    
    

    public String getExtensionOfTheFile() {
        return extensionOfTheFile;
    }

    public void setExtensionOfTheFile(String extensionOfTheFile) {
        this.extensionOfTheFile = extensionOfTheFile;
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


    public Config getConfig() {
        return config;
    }

    public void setConfig(Config config) {
        this.config = config;
    }

    public IConverter getConverter() {
        return converter;
    }

    public void setConverter(IConverter converter) {
        this.converter = converter;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public void pauseThis() {
        pause = true;
    }

    public void continueThis() {
        pause = false;
        synchronized (lock) {
            lock.notifyAll();
        }
    }

    private void pauseThread() {
        synchronized (lock) {
            if (pause) {
                try {
                    lock.wait();
                } catch (InterruptedException ex) {
                    Logger.getLogger(TaskInOurProgram.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public File getFilePath() {
        return filePath;
    }

    public void setFilePath(File filePath) {
        this.filePath = filePath;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsonconverter.BLL;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import jsonconverter.BE.JSONObject;
import jsonconverter.BE.Planning;
import jsonconverter.BE.TaskInOurProgram;

/**
 *
 * @author Samuel
 */
public class Converter {

   private SimpleDateFormat dateTimeFormatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");  
    private SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy"); 

    public List<JSONObject> returnJasonObjects(TaskInOurProgram task) throws InterruptedException {
        ObservableList<JSONObject> jasonList = FXCollections.observableArrayList();
        double objectCounter=0;
        jasonList.clear();
        for (String line : task.getConverter().getFileValues()) {
            String[] fields = line.split(";");
            
            JSONObject newJson = new JSONObject(
                    checkConfig(fields, task.getConfig().getSiteName(), task),
                    checkConfig(fields, task.getConfig().getAssetSerialNumber(), task),
                    checkConfig(fields, task.getConfig().getType(), task),
                    checkConfig(fields, task.getConfig().getExternalWorkOrderId(), task),
                    checkConfig(fields, task.getConfig().getSystemStatus(), task),
                    checkConfig(fields, task.getConfig().getUserStatus(), task),
                    checkConfig(fields, task.getConfig().getCreatedOn(), task),
                    checkConfig(fields, task.getConfig().getCreatedBy(), task),
                    checkConfig(fields, task.getConfig().getName(), task),
                    checkConfig(fields, task.getConfig().getPriority(), task),
                    checkConfig(fields, task.getConfig().getStatus(), task),
                    getPlanning(task, fields));
                            
            jasonList.add(newJson);
            objectCounter++;
            task.update(objectCounter);
            task.pauseThread();
        }
        return jasonList;
    }
    private Planning getPlanning(TaskInOurProgram task,String[] fields)
    {
        Planning planning = new Planning();
       try {
           planning.setLatestFinishDate(dateTimeFormatter.format(dateFormatter.parse(checkConfig(fields, task.getConfig().getLatestFinishDate(), task))));
       } catch (ParseException ex) {
          planning.setLatestFinishDate(checkConfig(fields, task.getConfig().getLatestFinishDate(), task));
       }
        try {
           planning.setEarliestStartDate(dateTimeFormatter.format(dateFormatter.parse(checkConfig(fields, task.getConfig().getEarliestStartDate(), task))));
       } catch (ParseException ex) {
          planning.setEarliestStartDate(checkConfig(fields, task.getConfig().getEarliestStartDate(), task));
       }
        try {
           planning.setLatestStartDate(dateTimeFormatter.format(dateFormatter.parse(checkConfig(fields, task.getConfig().getLatestStartDate(), task))));
       } catch (ParseException ex) {
          planning.setLatestStartDate(checkConfig(fields, task.getConfig().getLatestStartDate(), task));
       }
        planning.setEstimatedTime(checkConfig(fields, task.getConfig().getEstimatedTime(), task));
        return planning;
    }
    
    private String checkConfig(String[] jason,String config,TaskInOurProgram task)
    {
        if(config.contains("&&"))
        {
            
            String[] ifEmpty = config.split("&&");          
            
            if(jason[task.getConverter().getFileHeaders().get(ifEmpty[0])].equals(""))
            {
                return jason[task.getConverter().getFileHeaders().get(ifEmpty[1])];
            }
            else
            {
                return jason[task.getConverter().getFileHeaders().get(ifEmpty[0])];
            }
            
        }
        else if(task.getConverter().getOnlyFileHeaders().contains(config))
        {
            return jason[task.getConverter().getFileHeaders().get(config)];
        }
        else
        {
            return config;
        }
    }
}
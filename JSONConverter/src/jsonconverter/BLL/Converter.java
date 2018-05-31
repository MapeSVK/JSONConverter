/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsonconverter.BLL;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
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

    private SimpleDateFormat dateXMLFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
    private SimpleDateFormat dateTimeFormatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    private SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
    private Date currentDate = new Date();

    /* converts values from imported file into JSON list basen on config */
    public List<JSONObject> returnJasonObjects(TaskInOurProgram task) throws InterruptedException {
        ObservableList<JSONObject> jasonList = FXCollections.observableArrayList();
        double objectCounter = 0;
        jasonList.clear();
        checkDefaultValues(task);
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

                TimeUnit.MILLISECONDS.sleep(60);

            jasonList.add(newJson);
            objectCounter++;
            task.update(objectCounter);
            task.pauseThread();
        }
        return jasonList;
    }

    /* checks if vslue inside date fields can be converted into the datetype. If yes it converts value if not it doesnt change value */
    private Planning getPlanning(TaskInOurProgram task, String[] fields) {
        Planning planning = new Planning();
        try {
            planning.setLatestFinishDate(dateTimeFormatter.format(dateFormatter.parse(checkConfig(fields, task.getConfig().getLatestFinishDate(), task))));
        } catch (ParseException ex) {
            try {
                planning.setLatestFinishDate(dateTimeFormatter.format(dateXMLFormatter.parse(checkConfig(fields, task.getConfig().getLatestFinishDate(), task))));
            } catch (ParseException ex1) {
                planning.setLatestFinishDate(checkConfig(fields, task.getConfig().getLatestFinishDate(), task));
            }

        }
        try {
            planning.setEarliestStartDate(dateTimeFormatter.format(dateFormatter.parse(checkConfig(fields, task.getConfig().getEarliestStartDate(), task))));
        } catch (ParseException ex) {
            try {
                planning.setEarliestStartDate(dateTimeFormatter.format(dateXMLFormatter.parse(checkConfig(fields, task.getConfig().getEarliestStartDate(), task))));
            } catch (ParseException ex1) {
                planning.setEarliestStartDate(checkConfig(fields, task.getConfig().getEarliestStartDate(), task));
            }

        }
        try {
            planning.setLatestStartDate(dateTimeFormatter.format(dateFormatter.parse(checkConfig(fields, task.getConfig().getLatestStartDate(), task))));
        } catch (ParseException ex) {
            try {
                planning.setLatestStartDate(dateTimeFormatter.format(dateXMLFormatter.parse(checkConfig(fields, task.getConfig().getLatestStartDate(), task))));
            } catch (ParseException ex1) {
                planning.setLatestStartDate(checkConfig(fields, task.getConfig().getLatestStartDate(), task));
            }
        }
        planning.setEstimatedTime(checkConfig(fields, task.getConfig().getEstimatedTime(), task));
        return planning;
    }

    /* checks if the field in config has "if empty" and also if the field in config is empty */
    private String checkConfig(String[] jason, String config, TaskInOurProgram task) {
        if (config.contains("&&")) {

            String[] ifEmpty = config.split("&&");

            if (jason[task.getConverter().getFileHeaders().get(ifEmpty[0])].equals("")) {
                return jason[task.getConverter().getFileHeaders().get(ifEmpty[1])];
            } else {
                return jason[task.getConverter().getFileHeaders().get(ifEmpty[0])];
            }

        } else if (task.getConverter().getOnlyFileHeaders().contains(config)) {
            return jason[task.getConverter().getFileHeaders().get(config)];
        } else {
            return config;
        }
    }

    /* adds default values if the fields are empty */
    private void checkDefaultValues(TaskInOurProgram task) {

        if (task.getConfig().getSiteName().equals("")) {
            task.getConfig().setSiteName("");
        }
        if (task.getConfig().getAssetSerialNumber().equals("")) {
            task.getConfig().setAssetSerialNumber("asset._id");
        }
        if (task.getConfig().getCreatedOn().equals("")) {
            task.getConfig().setCreatedOn(dateTimeFormatter.format(currentDate));
        }
        if (task.getConfig().getCreatedBy().equals("")) {
            task.getConfig().setCreatedBy("SAP");
        }
        if (task.getConfig().getStatus().equals("")) {
            task.getConfig().setStatus("NEW");
        }
        if (task.getConfig().getEstimatedTime().equals("")) {
            task.getConfig().setEstimatedTime("");
        }
    }
}

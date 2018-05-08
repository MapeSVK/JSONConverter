/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsonconverter.BLL;

import java.util.ArrayList;
import java.util.List;
import jsonconverter.BE.Config;
import jsonconverter.BE.JSONObject;
import jsonconverter.BE.Planning;
import jsonconverter.DAL.readFilesAndWriteJson.IConverter;

/**
 *
 * @author Samuel
 */
public class Converter {

    public List<JSONObject> getJasonObject(IConverter converter, Config config) {
        Integer[] numbersOfHeaders = new Integer[15];
        numbersOfHeaders[0] = converter.getFileHeaders().get(config.getSiteName());
        numbersOfHeaders[1] = converter.getFileHeaders().get(config.getAssetSerialNumber());
        numbersOfHeaders[2] = converter.getFileHeaders().get(config.getType());
        numbersOfHeaders[3] = converter.getFileHeaders().get(config.getExternalWorkOrderId());
        numbersOfHeaders[4] = converter.getFileHeaders().get(config.getSystemStatus());
        numbersOfHeaders[5] = converter.getFileHeaders().get(config.getUserStatus());
        numbersOfHeaders[6] = converter.getFileHeaders().get(config.getCreatedOn());
        numbersOfHeaders[7] = converter.getFileHeaders().get(config.getCreatedBy());
        numbersOfHeaders[8] = converter.getFileHeaders().get(config.getName());
        numbersOfHeaders[9] = converter.getFileHeaders().get(config.getPriority());
        numbersOfHeaders[10] = converter.getFileHeaders().get(config.getStatus());
        numbersOfHeaders[11] = converter.getFileHeaders().get(config.getLatestFinishDate());
        numbersOfHeaders[12] = converter.getFileHeaders().get(config.getEarliestStartDate());
        numbersOfHeaders[13] = converter.getFileHeaders().get(config.getLatestStartDate());
        numbersOfHeaders[14] = converter.getFileHeaders().get(config.getEstimatedTime());

        return returnJasonObjects(numbersOfHeaders, converter);
    }

    private List<JSONObject> returnJasonObjects(Integer[] numbersOfHeaders, IConverter converter) {
        List<JSONObject> jasonList = new ArrayList();
        jasonList.clear();
        for (String line : converter.getFileValues()) {
            String[] fields = line.split(";");
            JSONObject newJson = new JSONObject(fields[numbersOfHeaders[0]],
                    fields[numbersOfHeaders[1]],
                    fields[numbersOfHeaders[2]],
                    fields[numbersOfHeaders[3]],
                    fields[numbersOfHeaders[4]],
                    fields[numbersOfHeaders[5]],
                    fields[numbersOfHeaders[6]],
                    fields[numbersOfHeaders[7]],
                    fields[numbersOfHeaders[8]],
                    fields[numbersOfHeaders[9]],
                    fields[numbersOfHeaders[10]], new Planning(fields[numbersOfHeaders[11]],
                            fields[numbersOfHeaders[12]],
                            fields[numbersOfHeaders[13]],
                            fields[numbersOfHeaders[14]]));
            jasonList.add(newJson);
        }
        return jasonList;
    }
}

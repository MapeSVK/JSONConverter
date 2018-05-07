/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsonconverter.DAL.readFilesAndWriteJson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Pepe15224
 */
public interface IConverter {

    /* retursn headers from the file as a hashMap (Headers are keys and numbers are values) */
    HashMap<String, Integer> getFileHeaders();

    /* returns values from the file as a lines except for the first line */
    ArrayList<String> getFileValues();

    /*returns list of Headers from the file */
    List<String> getOnlyFileHeaders();
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsonconverter.BE;

import java.io.File;
import javafx.concurrent.Task;
import jsonconverter.DAL.readFilesAndWriteJson.IConverter;
import jsonconverter.GUI.model.Model;

/**
 *
 * @author Pepe15224
 */
public class NewTask extends Task {

    Model model = new Model();

    public NewTask(String fileName, File filePath, IConverter converter, Config config) {
        model.createJsonFile(fileName, filePath, converter, config);
    }

    @Override
    protected Object call() throws Exception {

        return null;
    }

}

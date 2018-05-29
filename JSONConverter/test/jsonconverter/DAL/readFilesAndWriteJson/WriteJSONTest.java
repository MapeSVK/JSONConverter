/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsonconverter.DAL.readFilesAndWriteJson;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import jsonconverter.BE.JSONObject;
import jsonconverter.BE.Planning;
import junit.framework.TestCase;

/**
 *
 * @author Pepe15224
 */
public class WriteJSONTest extends TestCase {
    
    public WriteJSONTest(String testName) {
        super(testName);
    }

    /**
     * Test of createJsonFile method, of class WriteJSON.
     */
    public void testCreateJsonFile() {
        System.out.println("ReadXMLTest : testFileHeaders");
        WriteJSON instance = new WriteJSON();
        
        List<JSONObject> jsonList = new ArrayList();  
        
        assertFalse(instance.createJsonFile("test/", new File("no"), jsonList));
        assertFalse(instance.createJsonFile("just no", new File("no number one"), jsonList));
        assertFalse(instance.createJsonFile("test/testFiles/", new File("no number two"), jsonList));
        assertFalse(instance.createJsonFile("/noah", new File("no"), jsonList));

    }
    

    /**
     * Test of Alert method, of class WriteJSON.
     */
    public void testAlert() {

    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsonconverter.DAL.readFilesAndWriteJson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import junit.framework.TestCase;

/**
 *
 * @author Pepe15224
 */
public class ReadCSVTest extends TestCase {

    public ReadCSVTest(String testName) {
        super(testName);
    }

    /**
     * Test of getFileHeaders method, of class ReadCSV.
     */
    public void testGetFileHeaders() {
        System.out.println("ReadCSVTest : testFileHeaders");
        ReadCSV instance = new ReadCSV("test/testFiles/testCSV.csv");
        HashMap<String, Integer> result = instance.getFileHeaders();
        
        testGetFileHeadersOutput(result);
    }

    public void testGetFileHeadersOutput(HashMap<String, Integer> result) {
        
        HashMap<String, Integer> expectedGood1 = new HashMap<>();
        expectedGood1.put("firstHeader", 0);
        expectedGood1.put("secondHeader", 1);
        expectedGood1.put("thirdHeader", 2);
        expectedGood1.put("secondHeader2", 3);
        
        assertEquals(expectedGood1, result);
        
        HashMap<String, Integer> expectedGood2 = new HashMap<>();
        expectedGood2.put("secondHeader", 1);
        expectedGood2.put("firstHeader", 0); 
        expectedGood2.put("secondHeader2", 3);
        expectedGood2.put("thirdHeader", 2);
        
        assertEquals(expectedGood2, result);
        
        HashMap<String, Integer> expectedBad1 = new HashMap<>();
        expectedBad1.put("secondHeader", 1);
        expectedBad1.put("firstHeader", 4); 
        expectedBad1.put("secondHeader2", 3);
        expectedBad1.put("thirdHeader", 2);
        
        assertNotSame(expectedBad1, result);
        
        HashMap<String, Integer> expectedBad2 = new HashMap<>();
        expectedBad2.put("secondHeader", 1);
        expectedBad2.put("firstHeader", 3); 
        expectedBad2.put("secondHeader2", 3);
        expectedBad2.put("thirdHeader", 2);
        
        assertNotSame(expectedBad2, result);
        
        HashMap<String, Integer> expectedBad3 = new HashMap<>();
        expectedBad3.put("secondHeader", 1);
        expectedBad3.put("firstHeader", 0); 
        expectedBad3.put("thirdHeader", 2);
        
        assertNotSame(expectedBad3, result);
        
        HashMap<String, Integer> expectedBad4 = new HashMap<>();
        expectedBad4.put("secondHeader", 1);
        expectedBad4.put("firstHeader", 3); 
        expectedBad4.put("secondHeader2", 2);
        expectedBad4.put("thirdHeader", 0);
        
        assertNotSame(expectedBad4, result);
             
    }

    /**
     * Test of getFileValues method, of class ReadCSV.
     */
    public void testGetFileValues() {
        System.out.println("ReadCSVTest : testGetFileValues");
        ReadCSV instance = new ReadCSV("test/testFiles/testCSV.csv");
        ArrayList<String> result = instance.getFileValues();
        testGetFileValuesOutput(result);
    }

    public void testGetFileValuesOutput(ArrayList<String> result) {
        ArrayList<String> expectedGood = new ArrayList();
        expectedGood.add("firstValue;secondValue;thirdValue;forthValue");
        expectedGood.add("fifthValue;sixthValue;seventhValue;fifthValue");

        assertEquals(expectedGood, result);

        ArrayList<String> expectedBad1 = new ArrayList();
        expectedBad1.add("firstValue;secondValue;thirdValue;forthValue");
        expectedBad1.add("fifthValue;seventhValue;fifthValue");

        assertNotSame(expectedBad1, result);

        ArrayList<String> expectedBad2 = new ArrayList();
        expectedBad2.add("firstValue;secondValue;thirdValue;forthValue");

        assertNotSame(expectedBad2, result);

        ArrayList<String> expectedBad3 = new ArrayList();
        expectedBad3.add("fifthVaalue;sixthValue;seventhValue;fifthValue");

        assertNotSame(expectedBad3, result);

        ArrayList<String> expectedBad4 = new ArrayList();
        expectedBad4.add("firstValue;secondValue;thirdValue;");
        expectedBad4.add("fifthValue;sixthValue;seventhValue;fifthValue");

        assertNotSame(expectedBad4, result);
    }

    /**
     * Test of getOnlyFileHeaders method, of class ReadCSV.
     */
    public void testGetOnlyFileHeaders() {
        System.out.println("ReadCSVTest : testGetOnlyFileHeaders");
        ReadCSV instance = new ReadCSV("test/testFiles/testCSV.csv");
        List<String> result = instance.getOnlyFileHeaders();
        testGetOnlyFileHeadersOutput(result);
    }
    
    public void testGetOnlyFileHeadersOutput(List<String> result) {
           
        List<String> expectedGood = new ArrayList();
        expectedGood.add("firstHeader");
        expectedGood.add("secondHeader");
        expectedGood.add("thirdHeader");
        expectedGood.add("secondHeader2");  
        
       assertEquals(expectedGood, result);
        
        List<String> expectedBad1 = new ArrayList();
        expectedBad1.add("firstHeader");
        expectedBad1.add("secondHeader");
        expectedBad1.add("thirdHeader");
        expectedBad1.add("secondHeader");
        
        assertNotSame(expectedBad1, result);
        
        List<String> expectedBad2 = new ArrayList();
        expectedBad2.add("firstHeader");
        expectedBad2.add("secondHeader");
        expectedBad2.add("secondHeader2");
        expectedBad2.add("secondHeader3");
        
        assertNotSame(expectedBad2, result);
        
        List<String> expectedBad3 = new ArrayList();
        expectedBad3.add("firstHeader");      
        expectedBad3.add("thirdHeader");
        expectedBad3.add("secondHeader2");
        
        assertNotSame(expectedBad3, result);
        
        List<String> expectedBad4 = new ArrayList();
        expectedBad4.add("firstHeader");
        expectedBad4.add("secondHeader2");
        expectedBad4.add("thirdHeader");
        expectedBad4.add("secondHeader");
        
        assertNotSame(expectedBad4, result);
    }

}

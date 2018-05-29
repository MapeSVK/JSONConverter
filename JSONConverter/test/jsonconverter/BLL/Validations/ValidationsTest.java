/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsonconverter.BLL.Validations;

import com.jfoenix.controls.JFXTextField;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import jsonconverter.BE.Config;
import junit.framework.TestCase;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

/**
 *
 * @author Pepe15224
 */
public class ValidationsTest extends TestCase {
    
    public ValidationsTest(String testName) {
        super(testName);

    }

    /**
     * Test of checkIfConfigExists method, of class Validations.
     */
    public void testCheckIfConfigExists() {  
        System.out.println("ValidationsTest : checkIfConfigExists");
        
        Validations instancne = new Validations();
        testCheckIfConfigExistsInput(instancne);
    }
    
    public void testCheckIfConfigExistsInput(Validations instancne) {
          
        Config config1 = new Config();
        config1.setConfigName("config1");
        Config config2 = new Config();
        config2.setConfigName("config2");
        Config config3 = new Config();
        config3.setConfigName("config3");
        Config config4 = new Config();
        config4.setConfigName("config4");
        
        List<Config> configList = new ArrayList();
        configList.add(config2);
        configList.add(config4);
        
        Validations instance = new Validations();
        assertFalse(instance.checkIfConfigExists(config2, configList));
        assertFalse(instance.checkIfConfigExists(config4, configList));
        assertTrue(instance.checkIfConfigExists(config1, configList));
        assertTrue(instance.checkIfConfigExists(config3, configList));
    }
    
    /**
     * Test of changeColorIfWrong method, of class Validations.
     */
    public void testChangeColorIfWrong() {
        System.out.println("ValidationsTest : testChangeColorIfWrong");
        
        List<String> fileHeaders = new ArrayList();
        fileHeaders.add("firstHeader");
        fileHeaders.add("secondHeader");
        fileHeaders.add("secondHeader2");
        fileHeaders.add("secondHeader3");
        
       // Node node = new JFXTextField(); //JFXTextFiled gives ExceptionInInitializerError
        String fieldText = "no list";
        Validations instance = new Validations();
      // instance.changeColorIfWrong(node, fieldText, fileHeaders);
      //  assertTrue(node.getStyle().equals("-fx-background-color : red"));

    }

    /**
     * Test of wrongInputValidation method, of class Validations.
     */
    public void testWrongInputValidation(){          
        System.out.println("ValidationsTest : testWrongInputValidation");
        
        AnchorPane pane = new AnchorPane();
        AnchorPane pane2 = new AnchorPane(pane);
        pane.setStyle("-fx-background-color : red");
        pane.getChildrenUnmodifiable();
         Validations instance = new Validations();
        boolean expResult = true;
        boolean result = instance.wrongInputValidation(pane2);
        boolean result2 = instance.wrongInputValidation(pane);
        assertEquals(expResult, result);
       assertEquals(expResult, result2);
    }

    /**
     * Test of checkIfFileMatchesConfig method, of class Validations.
     */
    public void testCheckIfFileMatchesConfig() {
        System.out.println("ValidationsTest : checkIfFileMatchesConfig");

        List<String> fileHeaders = new ArrayList();
        fileHeaders.add("firstHeader");
        fileHeaders.add("secondHeader");
        fileHeaders.add("secondHeader2");
        fileHeaders.add("secondHeader3");
        
        Validations instance = new Validations();
        
        Config config1 = new Config();
         config1.setSiteName("");
         config1.setAssetSerialNumber("");
         config1.setType("");
         config1.setExternalWorkOrderId("");
         config1.setSystemStatus("");
         config1.setUserStatus("");
         config1.setCreatedOn("");
         config1.setCreatedBy("");
         config1.setName("");
         config1.setPriority("");
         config1.setStatus("");
         config1.setLatestFinishDate("");
         config1.setEarliestStartDate("");
         config1.setLatestStartDate("");
         config1.setEstimatedTime("");
         
         Config config2 = new Config();
         config2.setSiteName("");
         config2.setAssetSerialNumber("");
         config2.setType("");
         config2.setExternalWorkOrderId("");
         config2.setSystemStatus("");
         config2.setUserStatus("");
         config2.setCreatedOn("");
         config2.setCreatedBy("aaaaa");
         config2.setName("");
         config2.setPriority("");
         config2.setStatus("");
         config2.setLatestFinishDate("");
         config2.setEarliestStartDate("");
         config2.setLatestStartDate("");
         config2.setEstimatedTime("");
        
        assertTrue(instance.checkIfFileMatchesConfig(config1, fileHeaders));
        assertFalse(instance.checkIfFileMatchesConfig(config2, fileHeaders));
    }

    /**
     * Test of checkIfYouCanUseConfig method, of class Validations.
     */
    public void testCheckIfYouCanUseConfig() {

        List<String> fileHeaders = new ArrayList();
        fileHeaders.add("firstHeader");
        fileHeaders.add("secondHeader");
        fileHeaders.add("secondHeader2");
        fileHeaders.add("secondHeader3");
        
        Config config2 = new Config();
         config2.setSiteName("");
         config2.setAssetSerialNumber("");
         config2.setType("");
         config2.setExternalWorkOrderId("");
         config2.setSystemStatus("");
         config2.setUserStatus("");
         config2.setCreatedOn("");
         config2.setCreatedBy("aaaaa");
         config2.setName("");
         config2.setPriority("");
         config2.setStatus("");
         config2.setLatestFinishDate("");
         config2.setEarliestStartDate("");
         config2.setLatestStartDate("");
         config2.setEstimatedTime("");
        
         Config config1 = new Config();
         config1.setSiteName("");
         config1.setAssetSerialNumber("");
         config1.setType("");
         config1.setExternalWorkOrderId("");
         config1.setSystemStatus("");
         config1.setUserStatus("");
         config1.setCreatedOn("");
         config1.setCreatedBy("");
         config1.setName("");
         config1.setPriority("");
         config1.setStatus("");
         config1.setLatestFinishDate("");
         config1.setEarliestStartDate("");
         config1.setLatestStartDate("");
         config1.setEstimatedTime("");
         
         List<Config> configs = new ArrayList();
                 configs.add(config2);
         List<Config> expResult = new ArrayList();
         
         List<Config> configs2 = new ArrayList();
                 configs2.add(config2);
                 configs2.add(config1);
         List<Config> expResult2 = new ArrayList();
         expResult2.add(config1);
         
         testCheckIfYouCanUseConfigOutput(expResult, fileHeaders, configs);
         testCheckIfYouCanUseConfigOutput(expResult, fileHeaders, configs);
    }
    
    public void testCheckIfYouCanUseConfigOutput(List<Config> expResult,List<String> fileHeaders,List<Config> configs) {
        System.out.println("ValidationsTest : testCheckIfYouCanUseConfig");

        Validations instance = new Validations();
        List<Config> result = instance.checkIfYouCanUseConfig(fileHeaders, configs);
        assertEquals(expResult, result);
    }
    
}

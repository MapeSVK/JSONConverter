/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsonconverter.DAL.readFilesAndWriteJson;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 *
 * @author Pepe15224
 */
public class ReadXML implements IConverter {

    private String filePath;
    private List<String> allLinesAsStrings = new ArrayList<>();

    public ReadXML(String filePath) {
        this.filePath = filePath;
        allLinesAsStrings.clear();
        putAndSplitCSVIntoList(createCSVString(filePath));
    }

    /* splits the first line from the list file and then saves this line as a headers inside of the hashMap */
    @Override
    public HashMap<String, Integer> getFileHeaders() {
        HashMap<String, Integer> headersMap = new HashMap<>();
        String[] headers = allLinesAsStrings.get(0).split(";");

        for (int i = 0; i < headers.length; i++) {
            if (headersMap.containsKey(headers[i])) {
                String keyString = headers[i];
                int orderNumber = 1;
                while (headersMap.containsKey(keyString)) {
                    keyString = headers[i];
                    orderNumber++;
                    keyString = keyString + orderNumber;
                }
                headersMap.put(keyString, i);
            } else {
                headersMap.put(headers[i], i);
            }
        }
        return headersMap;
    }

    /* returns lines with values from list value except for the first line */
    @Override
    public ArrayList<String> getFileValues() {
        ArrayList<String> CSVValuesList = new ArrayList();
        CSVValuesList.addAll(allLinesAsStrings);
        CSVValuesList.remove(0);
        return CSVValuesList;
    }

    /*returns list of Headers from the file */
    @Override
    public List<String> getOnlyFileHeaders() {
        List<String> headers = new ArrayList();
        headers.clear();
        String[] headersString = allLinesAsStrings.get(0).split(";");
        for (int i = 0; i < headersString.length; i++) {
            if (headers.contains(headersString[i])) {
                String keyString = headersString[i];
                int orderNumber = 1;
                while (headers.contains(keyString)) {
                    keyString = headersString[i];
                    orderNumber++;
                    keyString = keyString + orderNumber;
                }
                headers.add(keyString);
            } else {
                headers.add(headersString[i]);
            }
        }
        return headers;
    }

    /*gets pieses of information from XML file */
    private String createCSVString(String filepath) {
        try {
            File stylesheet = new File("style1.xsl");
            File xmlSource = new File(filepath);

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(xmlSource);

            StreamSource stylesource = new StreamSource(stylesheet);
            Transformer transformer = TransformerFactory.newInstance()
                    .newTransformer(stylesource);
            Source source = new DOMSource(document);
            StringWriter sw = new StringWriter();
            transformer.transform(source, new StreamResult(sw));
            return sw.toString();
        } catch (TransformerException | SAXException | IOException | ParserConfigurationException ex) {
            Logger.getLogger(ReadXML.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /*puts pieces of information from file into the list */
    private void putAndSplitCSVIntoList(String string) {

        String[] splitedString = string.split("NEXT");

        for (int i = 1; i < splitedString.length; i++) {
            allLinesAsStrings.add(splitedString[i].substring(0, splitedString[i].length() - 1));
        }
    }
}

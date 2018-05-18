/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsonconverter.DAL.readFilesAndWriteJson;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 *
 * @author Pepe15224
 */
public class ReadEXEL implements IConverter {

    private List<String> allLinesAsStrings = new ArrayList<>();
    private String filepath;
    private String exelRow = "";
    private SimpleDateFormat dateExelFormatterTimeFormatter = new SimpleDateFormat("dd-MMM-yyyy");
    private SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    public ReadEXEL(String filepath) {
        this.filepath = filepath;
        allLinesAsStrings.clear();
        readExelFile(filepath);
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

    /*reads EXEL file*/
    private void readExelFile(String filepath) {
        InputStream inp = null;
        try {
            inp = new FileInputStream(filepath);
            Workbook wb;
            try {
                wb = WorkbookFactory.create(inp);
                for (int i = 0; i < wb.getNumberOfSheets(); i++) {
                    echoAsCSV(wb.getSheetAt(i));
                }
            } catch (org.apache.poi.openxml4j.exceptions.InvalidFormatException ex) {
                Logger.getLogger(ReadEXEL.class.getName()).log(Level.SEVERE, null, ex);
            } catch (EncryptedDocumentException ex) {
                Logger.getLogger(ReadEXEL.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (InvalidFormatException ex) {
            Logger.getLogger(ReadEXEL.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ReadEXEL.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ReadEXEL.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                inp.close();
            } catch (IOException ex) {
                Logger.getLogger(ReadEXEL.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /*puts pieces of information from EXEL file into the list*/
    private void echoAsCSV(Sheet sheet) {
        Row row = null;
        for (int i = 0; i < sheet.getLastRowNum() + 1; i++) {
            row = sheet.getRow(i);
            exelRow = "";
            for (int j = 0; j < row.getLastCellNum(); j++) {
                try {
                    String goodDate = dateFormatter.format(dateExelFormatterTimeFormatter.parse("" + row.getCell(j)));
                    exelRow = exelRow + goodDate + ";";
                } catch (ParseException ex) {
                    exelRow = exelRow + row.getCell(j) + ";";
                }

            }
            allLinesAsStrings.add(exelRow);
        }
    }
}

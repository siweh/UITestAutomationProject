package com.softwaretestingboard.magento.utilities;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ExcelReader {
    public static final String testDataExcelFileName = "testdata.xlsx"; //Global test data excel file
    public static final String currentDir = System.getProperty("user.dir");  //Main Directory of the project
    private static XSSFWorkbook excelWBook; //Excel WorkBook
    private static XSSFSheet excelWSheet; //Excel Sheet
    private static XSSFCell cell; //Excel cell
    private static XSSFRow row; //Excel row

    // It creates FileInputStream and set excel file and excel sheet to excelWorkBook and excelWorkSheet variables.
    public void setExcelFileSheet(String sheetName) {
        try {
            //Create an object of File class to open xlsx file
            File file = new File(currentDir+"\\src\\test\\java\\com\\softwaretestingboard\\magento\\Data\\testdata.xlsx");
            // Open the Excel file
            FileInputStream ExcelFile = new FileInputStream(file);
            excelWBook = new XSSFWorkbook(ExcelFile);
            excelWSheet = excelWBook.getSheet(sheetName);
        }catch (Exception e){
            throw new RuntimeException(e);
        }

    }

    //This method reads the test data from the Excel cell.
    //We are passing row number and column number as parameters.
    public String getCellData(int RowNum, int ColNum) {
        cell = excelWSheet.getRow(RowNum).getCell(ColNum);
        DataFormatter formatter = new DataFormatter();
        return formatter.formatCellValue(cell);
    }
}

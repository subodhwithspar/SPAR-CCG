package CCGClassPackage;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

public class ExcelUtility {
    public Properties prop;
    FileInputStream fileInput;
    Map validationMessageMap=new HashMap();
    String Username="";
    String Password="";
    String ValidationMessage, TestCaseCode;
    public Properties configData(){
        File file = new File("D:\\Subodh\\AutomationFWdownload\\CCG\\src\\main\\resources\\LoginProperty.properties");
        fileInput = null;
        try {
            fileInput = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
         prop = new Properties();
        //load properties file
        try {
            prop.load(fileInput);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return prop;
    }
    public void readExcel(String filePath,String fileName,String sheetName) throws IOException{
        //Create an object of File class to open xlsx file
        File file =    new File(filePath+"\\"+fileName);
        //Create an object of FileInputStream class to read excel file
        FileInputStream inputStream = new FileInputStream(file);
        Workbook ExcelWorkbook = null;
        //Find the file extension by splitting file name in substring  and getting only extension name
        String fileExtensionName = fileName.substring(fileName.indexOf("."));
        //Check condition if the file is xlsx file
        if(fileExtensionName.equals(".xlsx")){
            //If it is xlsx file then create object of XSSFWorkbook class
           ExcelWorkbook = new XSSFWorkbook(inputStream);
        }
        //Check condition if the file is xls file
        else if(fileExtensionName.equals(".xls")){
            //If it is xls file then create object of HSSFWorkbook class
            ExcelWorkbook = new HSSFWorkbook(inputStream);
        }
        //Read sheet inside the workbook by its name
        Sheet excelSheet = ExcelWorkbook.getSheet(sheetName);
        //Find number of rows in excel file
        int rowCount = excelSheet.getLastRowNum()-excelSheet.getFirstRowNum();
        //Create a loop over all the rows of excel file to read it
        for (int i = 0; i < rowCount+1; i++) {
            Row row = excelSheet.getRow(i);
            //Create a loop to print cell values in a row
            for (int j = 0; j < row.getLastCellNum(); j++) {
                //Print Excel data in console
                System.out.print(row.getCell(j).getStringCellValue()+"|| ");
            }
            System.out.println();
        }
    }

    public LinkedHashMap readLoginSheet(String filePath, String fileName, String sheetName) throws IOException, NullPointerException,InterruptedException {

        //Create an object of File class to open xlsx file
        LinkedHashMap loginData=new LinkedHashMap<String, String>();//LinkedHashMap used because it's giving same sequence as we inserted data.
        //Map loginData=new HashMap<String, String>();
        Map validationMessageMap=new HashMap();
        File file =    new File(filePath+"\\"+fileName);
        //Create an object of FileInputStream class to read excel file
        FileInputStream inputStream = new FileInputStream(file);
        Workbook ExcelWorkbook = null;
        //Find the file extension by splitting file name in substring  and getting only extension name
        String fileExtensionName = fileName.substring(fileName.indexOf("."));
        //Check condition if the file is xlsx file
        if(fileExtensionName.equals(".xlsx")){
            //If it is xlsx file then create object of XSSFWorkbook class
            ExcelWorkbook = new XSSFWorkbook(inputStream);
        }
        //Check condition if the file is xls file
        else if(fileExtensionName.equals(".xls")){
            //If it is xls file then create object of HSSFWorkbook class
            ExcelWorkbook = new HSSFWorkbook(inputStream);
        }
        //Read sheet inside the workbook by its name
        Sheet excelSheet = ExcelWorkbook.getSheet(sheetName);
        //Find number of rows in excel file
        int rowCount = excelSheet.getLastRowNum()-excelSheet.getFirstRowNum();
        //Create a loop over all the rows of excel file to read it
        for (int i = 1; i < rowCount+1; i++) {
            int j=0;
            Row row = excelSheet.getRow(i);
            Username=row.getCell(j).getStringCellValue();
            Password=row.getCell(j+1).getStringCellValue();
            /*TestCaseCode=row.getCell(j+2).getStringCellValue();
            ValidationMessage=row.getCell(j+3).getStringCellValue();*/
            loginData.put(Username,Password);
            //validationMessageMap.put(TestCaseCode,ValidationMessage);
        }

            return loginData;
        }

        public void updateExcelSheet(String filePath, String fileName, String sheetName, int rowNumber, int cellNumber, String cellValue, String testComments ) throws IOException {
            File file =    new File(filePath+"\\"+fileName);
            //Create an object of FileInputStream class to read excel file
            FileInputStream inputStream = new FileInputStream(file);
            Workbook ExcelWorkbook = null;
            String fileExtensionName = fileName.substring(fileName.indexOf("."));
            //Check condition if the file is xlsx file
            if(fileExtensionName.equals(".xlsx")){
                //If it is xlsx file then create object of XSSFWorkbook class
                ExcelWorkbook = new XSSFWorkbook(inputStream);
            }
            //Check condition if the file is xls file
            else if(fileExtensionName.equals(".xls")){
                //If it is xls file then create object of HSSFWorkbook class
                ExcelWorkbook = new HSSFWorkbook(inputStream);
            }
            //Read sheet inside the workbook by its name
              Sheet  excelSheet = ExcelWorkbook.getSheet(sheetName);
            //Update the value of cell
            Cell cell1=excelSheet.getRow(rowNumber).getCell(cellNumber);
            Cell cell2=excelSheet.getRow(rowNumber).getCell(cellNumber+1);
            if (cell1 == null||cell2==null)
                cell1 = excelSheet.getRow(rowNumber).createCell(cellNumber);
                cell2 = excelSheet.getRow(rowNumber).createCell(cellNumber+1);

           cell1.setCellValue(cellValue);
           cell2.setCellValue(testComments);
           inputStream.close();
           FileOutputStream fos =new FileOutputStream(new File(filePath+"\\"+fileName));
           ExcelWorkbook.write(fos);
           fos.close();
        }
}

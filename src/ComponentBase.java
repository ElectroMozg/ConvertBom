import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import java.io.*;
import java.util.Scanner;
import org.apache.poi.ss.usermodel.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class ComponentBase {

    static class footprint{
        String name;
        String typePad;
        int numPad;

        footprint(String setName,String setTypePad, int setNumPad){
            name = setName;
            typePad = setTypePad;
            numPad  =   setNumPad;
        }
    }

    InputStream inp;
    static Workbook tableFile;
    Sheet tableSheet;
    static String nameTableFile;
    static int numLastRow;
    static int numCellNameFootprint = 0;
    static int numCellTypePad = 1;
    static int numCellNumPad = 2;

    public ComponentBase(String nameExlFile)throws  IOException
    {
        nameTableFile   =   nameExlFile;
        inp         = new FileInputStream(nameTableFile);
        tableFile   = WorkbookFactory.create(inp);
        tableSheet  = tableFile.getSheetAt(0);
        numLastRow  = tableSheet.getLastRowNum();
    }

    boolean findPackage(String namePackage){

        for(int i=0;i<tableSheet.getLastRowNum();i++){
            if(tableSheet.getRow(i).getCell(numCellNameFootprint).toString().equals(namePackage)){
                return true;
            }
        }
        return false;
    }

    footprint readFootprint(String name){
        for(int i=0;i<tableSheet.getLastRowNum();i++){
            if(tableSheet.getRow(i).getCell(numCellNameFootprint).toString().equals(name)){
                String type     =   tableSheet.getRow(i).getCell(numCellTypePad).toString();
                int    numPad   =   (int)tableSheet.getRow(i).getCell(numCellNumPad).getNumericCellValue();
                return new footprint(name,type,numPad);
            }
        }
        return null;
    }

    void wrNewFootprint(footprint wrFootprint)throws FileNotFoundException, IOException{

        tableSheet.createRow(++numLastRow);
        tableSheet.getRow(numLastRow).createCell(numCellNameFootprint).setCellValue(wrFootprint.name);
        tableSheet.getRow(numLastRow).createCell(numCellTypePad).setCellValue(wrFootprint.typePad);
        tableSheet.getRow(numLastRow).createCell(numCellNumPad).setCellValue(wrFootprint.numPad);

        FileOutputStream fileOut = new FileOutputStream(nameTableFile);
        tableFile.write(fileOut);
    }

    footprint enterFootprint(){

         Scanner in = new Scanner(System.in);
         System.out.print("Ведите имя Footprint: ");
         String name    =   in.nextLine();
         System.out.print("Введите тип пайки: ");
         String type =  in.nextLine();
         //TODO Надо добавить проверку ввода типа. Может быть только SMD или PTH
         System.out.print("Введите количество контактов: ");
         int numPad = in.nextInt();

         return new footprint(name,type,numPad);
    }

}

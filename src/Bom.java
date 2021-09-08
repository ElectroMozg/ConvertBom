import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class Bom {

    InputStream inp;
    static Workbook fileBom;
    Sheet sheetBom;
    static String nameFile;
    int numLastRow;

    int numColmRef;
    int numColmFootprint;
    int numColmLayer;
    int numColmValue;

    public Bom(String nameExlFile) throws FileNotFoundException, IOException {
        nameFile = nameExlFile;
        inp = new FileInputStream(nameFile);
        fileBom = WorkbookFactory.create(inp);
        sheetBom = fileBom.getSheetAt(0);
        numLastRow = sheetBom.getLastRowNum();

        numColmRef = 0;
        numColmFootprint = 1;
        numColmLayer = 4;
        numColmValue = 6;
    }


}

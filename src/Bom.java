import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class Bom {

    static Workbook file;
    Sheet sheet;
    static String nameFile;
    int numLastRow;

    DataBom[] readComponent;

    public Bom(String nameExlFile) throws FileNotFoundException, IOException {
        nameFile = nameExlFile;
        file = WorkbookFactory.create(new FileInputStream(nameFile));
        sheet = file.getSheetAt(0);
        numLastRow = sheet.getLastRowNum();

        readComponent = new DataBom[numLastRow];
    }

    DataBom[] readAllComponents() throws IOException {
        DataBom[] readComponent = new DataBom[numLastRow];

        for (int i = 0; i < numLastRow; i++){
            readComponent[i]    =   new DataBom(sheet,i+1);
            System.out.println("строка "+(i+1));
        }
        return readComponent;

    }

    void sortByLayer(DataBom[] dataBom) {
        boolean needIteration = true;
        while (needIteration) {
            needIteration = false;
            for (int i = 1; i < dataBom.length; i++) {
                if (dataBom[i].sortWeightLayer < dataBom[i - 1].sortWeightLayer) {
                    swap(dataBom, i, i-1);
                    needIteration = true;
                }
            }
        }
    }

    void sortByValue(DataBom[] dataBom) {
        boolean needIteration = true;
        while (needIteration) {
            needIteration = false;
            for (int i = 1; i < dataBom.length; i++) {
                if (dataBom[i].sortWeightValue < dataBom[i - 1].sortWeightValue) {
                    swap(dataBom, i, i-1);
                    needIteration = true;
                }
            }
        }
    }

    void sortByType(DataBom[] dataBom) {
        boolean needIteration = true;
        while (needIteration) {
            needIteration = false;
            for (int i = 1; i < dataBom.length; i++) {
                if (dataBom[i].sortWeightType < dataBom[i - 1].sortWeightType) {
                    swap(dataBom, i, i-1);
                    needIteration = true;
                }
            }
        }
    }

    void sortByFootprint(DataBom[] dataBom) {

        boolean needIteration = true;
        while (needIteration) {
            needIteration = false;
            for (int i = 1; i < dataBom.length; i++) {
                if (dataBom[i].sortWeightFootprint < dataBom[i - 1].sortWeightFootprint) {
                    swap(dataBom, i, i-1);
                    needIteration = true;
                }
            }
        }
    }

    private void swap(DataBom[] array,int ind1,int ind2){
        DataBom saveDataBom =   array[ind1];
        array[ind1] =   array[ind2];
        array[ind2] =   saveDataBom;
    }


}

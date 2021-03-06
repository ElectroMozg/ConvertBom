
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Bom {

    static Workbook file;
    Sheet sheet;
    static String nameFile;
    int numLastRow;

    DataBom[] readComponent;

    public Bom(String nameExlFile) throws  IOException {
        nameFile = nameExlFile;
        file = WorkbookFactory.create(new FileInputStream(nameFile));
        sheet = file.getSheetAt(0);
        numLastRow = sheet.getLastRowNum();

        readComponent = new DataBom[numLastRow];
    }

    DataBom[] readAllComponents() throws IOException {
        DataBom[] readComponent = new DataBom[numLastRow];

        for (int i = 0; i < numLastRow; i++) {
            readComponent[i] = new DataBom(sheet, i + 1);
            System.out.println("строка " + (i + 1));
        }
        return readComponent;

    }

    void saveAllComponents(DataBom[] saveBom) throws IOException {

        String nameExlFile ="Sort_BOM.xlsx";

        Workbook saveFile = WorkbookFactory.create(new FileInputStream(nameExlFile));
        Sheet saveSheet;
        saveSheet = saveFile.getSheetAt(0);

        for (int i = 0; i < saveBom.length; i++) {

            if (saveBom[i] == null) {
                break;
            }
            if (saveSheet.getRow(i) == null) {
                saveSheet.createRow(i);
            }

            if (saveSheet.getRow(i).getCell(0) == null) {
                saveSheet.getRow(i).createCell(0);
            }
            saveSheet.getRow(i).getCell(0).setCellValue(saveBom[i].designator);

            if (saveSheet.getRow(i).getCell(1) == null) {
                saveSheet.getRow(i).createCell(1);
            }
            saveSheet.getRow(i).getCell(1).setCellValue(saveBom[i].value);

            if (saveSheet.getRow(i).getCell(2) == null) {
                saveSheet.getRow(i).createCell(2);
            }
            saveSheet.getRow(i).getCell(2).setCellValue(saveBom[i].footprint);

            if (saveSheet.getRow(i).getCell(3) == null) {
                saveSheet.getRow(i).createCell(3);
            }
            saveSheet.getRow(i).getCell(3).setCellValue(saveBom[i].type);

            if (saveSheet.getRow(i).getCell(4) == null) {
                saveSheet.getRow(i).createCell(4);
            }
            saveSheet.getRow(i).getCell(4).setCellValue(saveBom[i].layer);
        }
        FileOutputStream fileOut = new FileOutputStream(nameExlFile);
        saveFile.write(fileOut);
    }


    void sortBom(DataBom[] dataBom) {

        boolean needIteration = true;
        while (needIteration) {
            needIteration = false;
            for (int i = 1; i < dataBom.length; i++) {
                if (dataBom[i].sortWeightLayer < dataBom[i - 1].sortWeightLayer) {
                    swap(dataBom, i, i - 1);
                    needIteration = true;
                } else if (dataBom[i].sortWeightLayer == dataBom[i - 1].sortWeightLayer) {
                    if (dataBom[i].sortWeightType < dataBom[i - 1].sortWeightType) {
                        swap(dataBom, i, i - 1);
                        needIteration = true;
                    } else if (dataBom[i].sortWeightType == dataBom[i - 1].sortWeightType) {
                        if (dataBom[i].sortWeightFootprint < dataBom[i - 1].sortWeightFootprint) {
                            swap(dataBom, i, i - 1);
                            needIteration = true;
                        } else if (dataBom[i].sortWeightFootprint == dataBom[i - 1].sortWeightFootprint) {
                            if (dataBom[i].sortWeightValue < dataBom[i - 1].sortWeightValue) {
                                swap(dataBom, i, i - 1);
                                needIteration = true;
                            }
                        }
                    }
                }
            }
        }
    }

    DataBom[] contEqualsRows(DataBom[] readBom) {

        ArrayList<DataBom> listBom = new ArrayList<>(Arrays.asList(readBom));

        for (int i = 0; i < listBom.size(); i++) {

            for (int j = i+1; j < listBom.size(); j++) {
                if (listBom.get(i).value.equals(listBom.get(j).value) &&
                        listBom.get(i).layer.equals(listBom.get(j).layer) &&
                        listBom.get(i).footprint.equals(listBom.get(j).footprint)) {
                    listBom.get(i).designator = listBom.get(i).designator +" , "+ listBom.get(j).designator;
                    listBom.remove(j);
                    listBom.get(i).quantity++;
                    j--;
                }
            }
            System.out.println(listBom.get(i).designator +" "+ listBom.get(i).value);
        }
        //TODO Разобраться почему такая запись[]::new
        return listBom.toArray(DataBom[]::new);
    }


    private void swap(DataBom[] array, int ind1, int ind2) {
        DataBom saveDataBom = array[ind1];
        array[ind1] = array[ind2];
        array[ind2] = saveDataBom;
    }


}

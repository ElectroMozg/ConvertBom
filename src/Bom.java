import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class Bom {

    static Workbook file;
    Sheet sheet;
    static String nameFile;
    int numLastRow;
    ComponentBase componentBase;
    DataBom[] readComponent;

    public Bom(String nameExlFile) throws FileNotFoundException, IOException {
        nameFile = nameExlFile;
        file = WorkbookFactory.create(new FileInputStream(nameFile));
        sheet = file.getSheetAt(0);
        numLastRow = sheet.getLastRowNum();
        componentBase = new ComponentBase("ComponentBase.xlsx");
        readComponent = new DataBom[numLastRow];
    }

    DataBom[] readAllComponents() throws IOException {
        DataBom[] readComponent = new DataBom[numLastRow];

        for (int i = 0; i < numLastRow - 1; i++) {
            //Прочитать Строку
            if (sheet.getRow(i + 1) == null) {
                sheet.createRow(i + 1);
            }
            Row readRow = sheet.getRow(i);
            readComponent[i].designator = readRow.getCell(0).toString();
            readComponent[i].footprint = readRow.getCell(1).toString();
            readComponent[i].layer = readRow.getCell(4).toString();
            readComponent[i].value = readRow.getCell(6).toString();
            if (!componentBase.findValue(readComponent[i].value)) {
                do {
                    System.out.println("Неизвестное значение");
                    System.out.println("Необходимо внести значение в базу");
                    componentBase.enterFromTerminal();
                }
                while (!componentBase.findValue(readComponent[i - 1].value));
            }
            readComponent[i].type = componentBase.getType(readComponent[i].value);
            if (!componentBase.footprint.findFootprint(readComponent[i].footprint)) {
                do {
                    System.out.println("Неизвестный Footprint");
                    System.out.println("Необходимо внести Footprint в базу");
                    componentBase.footprint.enterFromTerminal();
                }
                while (!componentBase.footprint.findFootprint(readComponent[i].footprint));
            }
            readComponent[i].solderType = componentBase.footprint.getSolderType(readComponent[i].footprint);
            readComponent[i].amountPad = componentBase.footprint.getAmountPad(readComponent[i].footprint);
            readComponent[i].numValue = convertValue(readComponent[i].value, readComponent[i].type);
        }
        return readComponent;
    }

    DataBom[] sortByType(DataBom[] dataBom) {
        DataBom[] sortBom = new DataBom[dataBom.length];
        String[] tableType = new String[6];
        tableType[0] = "Resistor";
        tableType[1] = "Capacitor";
        tableType[2] = "Microchip";
        tableType[3] = "Diode";
        tableType[4] = "Transistor";
        tableType[5] = "Other";
        int countSortBom = 0;
        for (int i = 0; i < tableType.length; i++) {
            for (int j = 0; j < dataBom.length; j++) {
                if (dataBom[j].type == tableType[i]) {
                    sortBom[countSortBom] = dataBom[j];
                    countSortBom++;
                }
            }

        }
        return sortBom;
    }

    DataBom[] sortByFootprint(DataBom[] dataBom) {
        DataBom[] sortBom = new DataBom[dataBom.length];

        for (int i = 0; i < dataBom.length; i++) {
            if(dataBom[i].type.equals("Resistor");
        }


        return sortBom;
    }

    double convertValue(String value, String type) {
        double doubleValue = 0;

        if (type.equals("Resistor")) {
            if (value.contains("R")) {
                doubleValue = Double.parseDouble(value.substring(0, value.indexOf("R")));
                if (value.length() - 1 > value.indexOf("R")) {

                    double tentsDoubleValue = Double.parseDouble(value.substring(value.indexOf("R") + 1)) / 10;
                    doubleValue = doubleValue + tentsDoubleValue;

                }
            } else if (value.contains("K")) {
                doubleValue = Double.parseDouble(value.substring(0, value.indexOf("K")));
                if (value.length() > value.indexOf("K")) {

                    double tentsDoubleValue = Double.parseDouble(value.substring(value.indexOf("K") + 1)) / 10;
                    doubleValue = (doubleValue + tentsDoubleValue) * 1000;

                }
            } else if (value.contains("M")) {
                doubleValue = Double.parseDouble(value.substring(0, value.indexOf("M")));
                if (value.length() > value.indexOf("M")) {

                    double tentsDoubleValue = Double.parseDouble(value.substring(value.indexOf("M") + 1)) / 10;
                    doubleValue = (doubleValue + tentsDoubleValue) * 1000000;

                }
            } else {
                System.out.println("Неизвестный префикс резистора");
            }
        } else if (type.equals("Capacitor")) {
            if (value.contains("u")) {
                doubleValue = Double.parseDouble(value.substring(0, value.indexOf("u"))) * 1E-6;

            } else if (value.contains("n")) {
                doubleValue = Double.parseDouble(value.substring(0, value.indexOf("n"))) * 1E-9;

            } else if (value.contains("p")) {
                doubleValue = Double.parseDouble(value.substring(0, value.indexOf("p"))) * 1E-12;

            } else {
                System.out.println("Неизвестный префикс резистора");
            }
        } else {
            System.out.println("не ковертируемый тип данных");
        }

        return doubleValue;
    }


}

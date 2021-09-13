import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.IOException;

public class DataBom {


    public ComponentBase componentBase;
    public Sheet sheetReadBom;

    String designator;
    String footprint;
    String layer;
    String value;
    String type;
    String solderType;
    int amountPad;
    double sortWeightValue;
    int sortWeightLayer;
    int sortWeightFootprint;
    int sortWeightType;

    public DataBom(Sheet sheet,int numRow ) throws IOException {

        componentBase = new ComponentBase("ComponentBase.xlsx");
        //Прочитать Строку
        sheetReadBom    =   sheet;
        if (sheetReadBom.getRow(numRow + 1) == null) {
            sheetReadBom.createRow(numRow + 1);
        }
        Row readRow = sheetReadBom.getRow(numRow);
        designator = readRow.getCell(0).toString();
        footprint = readRow.getCell(1).toString();
        layer = readRow.getCell(4).toString();
        value = readRow.getCell(6).toString();
        type = componentBase.getType(value);
        solderType = componentBase.footprint.getSolderType(footprint);
        amountPad = componentBase.footprint.getAmountPad(footprint);

        setSortWeightValue();
        setSortWeightFootprint();
        setSortWeightType();
        setSortWeightLayer();


    }



    private void setSortWeightValue() {

        if (type.equals("Resistor")) {
            if (value.contains("R")) {
                sortWeightValue = Double.parseDouble(value.substring(0, value.indexOf("R")));
                if (Double.parseDouble(value.substring(value.indexOf("R") + 1))>0) {

                    double tentsDoubleValue = Double.parseDouble(value.substring(value.indexOf("R") + 1)) / 10;
                    sortWeightValue = sortWeightValue + tentsDoubleValue;

                }
            } else if (value.contains("K")) {
                sortWeightValue = Double.parseDouble(value.substring(0, value.indexOf("K")));
                if (value.length() > value.indexOf("K")) {

                    double tentsDoubleValue = Double.parseDouble(value.substring(value.indexOf("K") + 1)) / 10;
                    sortWeightValue = (sortWeightValue + tentsDoubleValue) * 1000;

                }
            } else if (value.contains("M")) {
                sortWeightValue = Double.parseDouble(value.substring(0, value.indexOf("M")));
                if (value.length() > value.indexOf("M")) {

                    double tentsDoubleValue = Double.parseDouble(value.substring(value.indexOf("M") + 1)) / 10;
                    sortWeightValue = (sortWeightValue + tentsDoubleValue) * 1000000;

                }
            } else {
                System.out.println("Неизвестный префикс резистора");
            }
        } else if (type.equals("Capacitor")) {
            if (value.contains("u")) {
                sortWeightValue = Double.parseDouble(value.substring(0, value.indexOf("u"))) * 1E-6;

            } else if (value.contains("n")) {
                sortWeightValue = Double.parseDouble(value.substring(0, value.indexOf("n"))) * 1E-9;

            } else if (value.contains("p")) {
                sortWeightValue = Double.parseDouble(value.substring(0, value.indexOf("p"))) * 1E-12;

            } else {
                sortWeightValue = Double.parseDouble(value);
                System.out.println("Неизвестный префикс резистора");
            }
        } else {
            System.out.println("не ковертируемый тип данных"+type);
            sortWeightValue = 0;
        }


    }

    private void setSortWeightFootprint() {
        String[] tableFootprint = new String[14];
        tableFootprint[0] = "C0402";
        tableFootprint[1] = "C0603";
        tableFootprint[2] = "C0805";
        tableFootprint[3] = "C1206";
        tableFootprint[4] = "CT3528";
        tableFootprint[5] = "R0402 ";
        tableFootprint[6] = "R0603";
        tableFootprint[7] = "R0805";
        tableFootprint[8] = "R1206";
        tableFootprint[9] = "R2515";
        tableFootprint[10] = "R0402";
        tableFootprint[11] = "R0402";
        tableFootprint[12] = "R0402";
        tableFootprint[13] = "No Footprint";

        sortWeightFootprint = tableFootprint.length-1;

        for (int i = 0; i < tableFootprint.length; i++) {
            if (footprint.equals(tableFootprint[i])) {
                sortWeightFootprint = i;
            }
        }

    }

    public void setSortWeightType(){
        String[] tableType = new String[7];
        tableType[0] = "Resistor";
        tableType[1] = "Capacitor";
        tableType[2] = "Microchip";
        tableType[3] = "Diode";
        tableType[4] = "Transistor";
        tableType[5] = "Other";
        tableType[6] = "No Type";


        sortWeightType  =   tableType.length-1;
        for (int i = 0; i < tableType.length; i++) {
            if(type.equals(tableType[i])){
                sortWeightType  =   i;
            }
        }
    }

    public void setSortWeightLayer(){
        String[] tableType = new String[6];
        tableType[0] = "T";
        tableType[1] = "B";
        tableType[2] = "No Type";

        sortWeightLayer  =   tableType.length-1;
        for (int i = 0; i < tableType.length; i++) {
            if(layer.equals(tableType[i])){
                sortWeightLayer  =   i;
            }
        }
    }

}






import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.IOException;
import java.util.ArrayList;

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
    String comment;
    double sortWeightValue;
    int sortWeightLayer;
    int sortWeightFootprint;
    int sortWeightType;
    int quantity;



    public DataBom(Sheet sheet, int numRow) throws IOException {

        componentBase = new ComponentBase("ComponentBase.xlsx");
        //Прочитать Строку
        sheetReadBom = sheet;
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
        quantity = 1;

        setSortWeightValue();
        setSortWeightFootprint();
        setSortWeightType();
        setSortWeightLayer();

    }

    public void convertValueCapacitor(String convertValue) {

        //Необходимо вырезать символ F из значения
        if (convertValue.contains("F")) {
            int i = convertValue.indexOf("F");
            convertValue = convertValue.substring(0, i) + convertValue.substring(i + 1);
        }

        String[] splitValue = convertValue.split(" ");
        String capValue = "";
        String commentValue = "";
        String[] splitOmValue;


        for (int i = 0; i < splitValue.length; i++) {
            //Первый член всегда значение сопротивления
            if (i == 0) {
                capValue = splitValue[i];
            } else {
                commentValue = commentValue + "," + splitValue[i];
                comment =   commentValue;
            }
        }


        if (capValue.contains("u")) {
            splitOmValue = capValue.split("u");
            //R являеться разделителем дробной части
            if (splitOmValue.length >= 2) {
                sortWeightValue = Double.parseDouble(splitOmValue[0]) + (Double.parseDouble(splitOmValue[1]) / 10);
                sortWeightValue *= 1E-6;
                //Точка являеться разделителем дробной части
            } else if (capValue.contains(".")) {
                //Нужно отбросить префикс
                capValue = capValue.substring(0, capValue.indexOf("u"));
                sortWeightValue = Double.parseDouble(capValue);
                sortWeightValue *= 1E-6;
                //Не нашли дробную часть
            } else {
                sortWeightValue = Double.parseDouble(splitOmValue[0]);
                sortWeightValue *= 1E-6;
            }
        } else if (capValue.contains("n")) {
            splitOmValue = capValue.split("n");
            //R являеться разделителем дробной части
            if (splitOmValue.length >= 2) {
                sortWeightValue = Double.parseDouble(splitOmValue[0]) + (Double.parseDouble(splitOmValue[1]) / 10);
                sortWeightValue *= 1E-9;
                //Точка являеться разделителем дробной части
            } else if (capValue.contains(".")) {
                //Нужно отбросить префикс
                capValue = capValue.substring(0, capValue.indexOf("n"));
                sortWeightValue = Double.parseDouble(capValue);
                sortWeightValue *= 1E-9;
                //Не нашли дробную часть
            } else {
                sortWeightValue = Double.parseDouble(splitOmValue[0]);
                sortWeightValue *= 1E-9;
            }
        } else if (capValue.contains("p")) {
            splitOmValue = capValue.split("p");
            //R являеться разделителем дробной части
            if (splitOmValue.length >= 2) {
                sortWeightValue = Double.parseDouble(splitOmValue[0]) + (Double.parseDouble(splitOmValue[1]) / 10);
                sortWeightValue *= 1E-12;
                //Точка являеться разделителем дробной части
            } else if (capValue.contains(".")) {
                //Нужно отбросить префикс
                capValue = capValue.substring(0, capValue.indexOf("p"));
                sortWeightValue = Double.parseDouble(capValue);
                sortWeightValue *= 1E-12;
                //Не нашли дробную часть
            } else {
                sortWeightValue = Double.parseDouble(splitOmValue[0]);
                sortWeightValue *= 1E-12;
            }
        } else if (capValue.contains(".")) {
            sortWeightValue = Double.parseDouble(capValue);
        } else {
            System.out.println("Ошибка convertValueResistor");
        }

    }

    public void convertValueResistor(String convertValue) {
        String[] splitValue = convertValue.split(" ");
        String omValue = "";
        String commentValue = "";
        String[] splitOmValue;


        for (int i = 0; i < splitValue.length; i++) {
            //Первый член всегда значение сопротивления
            if (i == 0) {
                omValue = splitValue[i];
            } else {
                commentValue = commentValue + "," + splitValue[i];
                comment = commentValue;
            }
        }

        if (omValue.contains("R")) {
            splitOmValue = omValue.split("R");
            //R являеться разделителем дробной части
            if (splitOmValue.length >= 2) {
                sortWeightValue = Double.parseDouble(splitOmValue[0]) + (Double.parseDouble(splitOmValue[1]) / 10);
                //Точка являеться разделителем дробной части
            } else if (omValue.contains(".")) {
                //Нужно отбросить префикс
                omValue = omValue.substring(0, omValue.indexOf("R"));
                sortWeightValue = Double.parseDouble(omValue);
                //Не нашли дробную часть
            } else {
                sortWeightValue = Double.parseDouble(splitOmValue[0]);
            }
        } else if (omValue.contains("k")) {
            splitOmValue = omValue.split("k");
            //R являеться разделителем дробной части
            if (splitOmValue.length >= 2) {
                sortWeightValue = Double.parseDouble(splitOmValue[0]) + (Double.parseDouble(splitOmValue[1]) / 10);
                sortWeightValue *= 1E3;
                //Точка являеться разделителем дробной части
            } else if (omValue.contains(".")) {
                //Нужно отбросить префикс
                omValue = omValue.substring(0, omValue.indexOf("k"));
                sortWeightValue = Double.parseDouble(omValue);
                sortWeightValue *= 1E3;
                //Не нашли дробную часть
            } else {
                sortWeightValue = Double.parseDouble(splitOmValue[0]);
                sortWeightValue *= 1E3;
            }
        } else if (omValue.contains("M")) {
            splitOmValue = omValue.split("M");
            //R являеться разделителем дробной части
            if (splitOmValue.length >= 2) {
                sortWeightValue = Double.parseDouble(splitOmValue[0]) + (Double.parseDouble(splitOmValue[1]) / 10);
                sortWeightValue *= 1E6;
                //Точка являеться разделителем дробной части
            } else if (omValue.contains(".")) {
                //Нужно отбросить префикс
                omValue = omValue.substring(0, omValue.indexOf("M"));
                sortWeightValue = Double.parseDouble(omValue);
                sortWeightValue *= 1E6;
                //Не нашли дробную часть
            } else {
                sortWeightValue = Double.parseDouble(splitOmValue[0]);
                sortWeightValue *= 1E6;
            }
        } else if (omValue.contains(".")) {
            sortWeightValue = Double.parseDouble(omValue);
        } else {
            System.out.println("Ошибка convertValueResistor");
        }
    }

    private void setSortWeightValue() {

        if (type.equals("Resistor")){
            convertValueResistor(value);
        }  else if (type.equals("Capacitor")) {
            convertValueCapacitor(value);
        } else {
            System.out.println("не ковертируемый тип данных" + type);
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

        sortWeightFootprint = tableFootprint.length - 1;

        for (int i = 0; i < tableFootprint.length; i++) {
            if (footprint.equals(tableFootprint[i])) {
                sortWeightFootprint = i;
            }
        }

    }

    public void setSortWeightType() {
        ArrayList<String> ListType = new ArrayList<>();
        //Записанны в порядке очереди
        ListType.add("Capacitor");
        ListType.add("Resistor");
        ListType.add("Transistor");
        ListType.add("LedDiode");
        ListType.add("Optocoupler");
        ListType.add("Diode");
        ListType.add("Microchip");
        ListType.add("Relay");
        ListType.add("Connectors");
        ListType.add("Other");
        ListType.add("Not install");
        ListType.add("No type");

        for (int i = 0; i < ListType.size(); i++) {
            if (type.equals(ListType.get(i))) {
                sortWeightType = i;
            }else{
                sortWeightType = ListType.indexOf("No type");
            }
        }
    }

    public void setSortWeightLayer() {
        String[] tableType = new String[6];
        tableType[0] = "T";
        tableType[1] = "B";
        tableType[2] = "No Type";

        sortWeightLayer = tableType.length - 1;
        for (int i = 0; i < tableType.length; i++) {
            if (layer.equals(tableType[i])) {
                sortWeightLayer = i;
            }
        }
    }

}






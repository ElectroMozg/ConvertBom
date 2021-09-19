import org.apache.poi.ss.usermodel.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;


public class PatternSant {

    static class Component {
        private String value;
        private String designator;
        private String footprint;
        private String comment;
        private int quantity;
        private int quantityPin;
        private String typePin;
        private String note;
        private String layer;
        private String type;

        public void setValue(String designator) {
            this.designator = designator;
        }

        public void setDesignator(String designator) {
            this.designator = designator;
        }

        public void setFootprint(String footprint) {
            this.footprint = footprint;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public void setQuantityPin(int quantityPin) {
            this.quantityPin = quantityPin;
        }

        public void setTypePin(String quantitySolder) {
            this.typePin = quantitySolder;
        }

        public void setNote(String note) {
            this.note = note;
        }
    }

    private class AddressRowComponentsData {

        private int rowForCapacitors;
        private int rowForResists;
        private int rowForTransistors;
        private int rowForLedDiodes;
        private int rowForOptocouplers;
        private int rowForDiode;
        private int rowForMicrochip;
        private int rowForRelays;
        private int rowForConnectors;
        private int rowForOther;
        private int rowForNC;

        public AddressRowComponentsData(int startRow, Sheet sheetBom) {
            rowForCapacitors = 1 + findRowWith(startRow, "Конденсаторы", sheetBom);
            rowForResists = 1 + findRowWith(startRow, "Резисторы", sheetBom);
            rowForTransistors = 1 + findRowWith(startRow, "Транзисторы", sheetBom);
            rowForLedDiodes = 1 + findRowWith(startRow, "Светодиоды", sheetBom);
            rowForOptocouplers = 1 + findRowWith(startRow, "Оптопары", sheetBom);
            rowForDiode = 1 + findRowWith(startRow, "Диоды", sheetBom);
            rowForMicrochip = 1 + findRowWith(startRow, "Микросхемы", sheetBom);
            rowForRelays = 1 + findRowWith(startRow, "Реле", sheetBom);
            rowForConnectors = 1 + findRowWith(startRow, "Разъемы", sheetBom);
            rowForOther = 1 + findRowWith(startRow, "Прочее", sheetBom);
            rowForNC = 1 + findRowWith(startRow, "Не устанавливать", sheetBom);
        }

    }

    final String nameBoard;
    private int quantityBord;
    Workbook file;
    Sheet sheet;
    ArrayList<Component> tableBom = new ArrayList<>();
    //Адреса ячеек параметров в шаблоне САНТ
    final int rowNameBoard;
    final int cellNameBoard;
    final int rowQuantityBoard;
    final int cellQuantityBoard;
    final int cellValue;
    final int cellDesignator;
    final int cellFootprint;
    final int cellComments;
    final int cellQuantity;
    final int cellQuantityPin;
    final int cellQuantitySolder;
    final int cellNote;
    AddressRowComponentsData rowTopSmd;
    AddressRowComponentsData rowTopPht;
    AddressRowComponentsData rowBotSmd;
    AddressRowComponentsData rowBotPht;

    public PatternSant(DataBom[] sortBom) throws IOException {

        nameBoard = "Pattern BOM Sant";
        file = WorkbookFactory.create(new FileInputStream(nameBoard));
        sheet = file.getSheetAt(0);
        rowNameBoard = 1;
        cellNameBoard = 4;
        rowQuantityBoard = 1;
        cellQuantityBoard = 6;
        cellValue = 0;
        cellDesignator = 1;
        cellFootprint = 2;
        cellComments = 3;
        cellQuantity = 4;
        cellQuantityPin = 5;
        cellQuantitySolder = 6;
        cellNote = 7;
        rowTopSmd = new AddressRowComponentsData(findRowWith(0, "Компоненты для монтажа на поверхность   TOP", sheet), sheet);
        rowTopPht = new AddressRowComponentsData(findRowWith(0, "Компоненты для монтажа в отверстия   TOP", sheet), sheet);
        rowBotSmd = new AddressRowComponentsData(findRowWith(0, "Компоненты для монтажа на поверхность   BOT", sheet), sheet);
        rowBotPht = new AddressRowComponentsData(findRowWith(0, "Компоненты для монтажа в отверстия   BOT", sheet), sheet);

        tableBom    =   setTableBom(sortBom);
    }

    public void writeQuantityBoard() {
        writeCell(quantityBord, rowQuantityBoard, cellQuantityBoard);
    }

    public void writeNameBoard() {
        writeCell(nameBoard, rowNameBoard, cellNameBoard);
    }

    public void write(Component writeComponent) {

        if (writeComponent.layer.equals("TOP")) {

            if (writeComponent.typePin.equals("SMD")) {
                switch (writeComponent.type) {
                    case "Capacitor" -> {
                        writeParameters(writeComponent, rowTopSmd.rowForCapacitors);
                        rowTopSmd.rowForCapacitors++;
                    }
                    case "Resistor" -> {
                        writeParameters(writeComponent, rowTopSmd.rowForResists);
                        rowTopSmd.rowForResists++;
                    }
                    case "Transistor" -> {
                        writeParameters(writeComponent, rowTopSmd.rowForTransistors);
                        rowTopSmd.rowForTransistors++;
                    }
                    case "LedDiode" -> {
                        writeParameters(writeComponent, rowTopSmd.rowForLedDiodes);
                        rowTopSmd.rowForLedDiodes++;
                    }
                    case "Optocoupler" -> {
                        writeParameters(writeComponent, rowTopSmd.rowForOptocouplers);
                        rowTopSmd.rowForOptocouplers++;
                    }
                    case "Diode" -> {
                        writeParameters(writeComponent, rowTopSmd.rowForDiode);
                        rowTopSmd.rowForDiode++;
                    }
                    case "Microchip" -> {
                        writeParameters(writeComponent, rowTopSmd.rowForMicrochip);
                        rowTopSmd.rowForMicrochip++;
                    }
                    case "Relay" -> {
                        writeParameters(writeComponent, rowTopSmd.rowForRelays);
                        rowTopSmd.rowForRelays++;
                    }
                    case "Connectors" -> {
                        writeParameters(writeComponent, rowTopSmd.rowForConnectors);
                        rowTopSmd.rowForConnectors++;
                    }
                    case "Other" -> {
                        writeParameters(writeComponent, rowTopSmd.rowForOther);
                        rowTopSmd.rowForOther++;
                    }
                    case "Not install" -> {
                        writeParameters(writeComponent, rowTopSmd.rowForNC);
                        rowTopSmd.rowForNC++;
                    }
                }

            } else if (writeComponent.typePin.equals("PHT")) {

                switch (writeComponent.type) {
                    case "Capacitor" -> {
                        writeParameters(writeComponent, rowTopPht.rowForCapacitors);
                        rowTopSmd.rowForCapacitors++;
                    }
                    case "Resistor" -> {
                        writeParameters(writeComponent, rowTopPht.rowForResists);
                        rowTopSmd.rowForResists++;
                    }
                    case "Transistor" -> {
                        writeParameters(writeComponent, rowTopPht.rowForTransistors);
                        rowTopSmd.rowForTransistors++;
                    }
                    case "LedDiode" -> {
                        writeParameters(writeComponent, rowTopPht.rowForLedDiodes);
                        rowTopSmd.rowForLedDiodes++;
                    }
                    case "Optocoupler" -> {
                        writeParameters(writeComponent, rowTopPht.rowForOptocouplers);
                        rowTopSmd.rowForOptocouplers++;
                    }
                    case "Diode" -> {
                        writeParameters(writeComponent, rowTopPht.rowForDiode);
                        rowTopSmd.rowForDiode++;
                    }
                    case "Microchip" -> {
                        writeParameters(writeComponent, rowTopPht.rowForMicrochip);
                        rowTopSmd.rowForMicrochip++;
                    }
                    case "Relay" -> {
                        writeParameters(writeComponent, rowTopPht.rowForRelays);
                        rowTopSmd.rowForRelays++;
                    }
                    case "Connectors" -> {
                        writeParameters(writeComponent, rowTopPht.rowForConnectors);
                        rowTopSmd.rowForConnectors++;
                    }
                    case "Other" -> {
                        writeParameters(writeComponent, rowTopPht.rowForOther);
                        rowTopSmd.rowForOther++;
                    }
                    case "Not install" -> {
                        writeParameters(writeComponent, rowTopPht.rowForNC);
                        rowTopSmd.rowForNC++;
                    }
                }
            } else {
                System.out.println("Error:if writeComponent.typePin");
            }
        } else if (writeComponent.layer.equals("BOT")) {

            if (writeComponent.typePin.equals("SMD")) {

                switch (writeComponent.type) {
                    case "Capacitor" -> {
                        writeParameters(writeComponent, rowBotSmd.rowForCapacitors);
                        rowTopSmd.rowForCapacitors++;
                    }
                    case "Resistor" -> {
                        writeParameters(writeComponent, rowBotSmd.rowForResists);
                        rowTopSmd.rowForResists++;
                    }
                    case "Transistor" -> {
                        writeParameters(writeComponent, rowBotSmd.rowForTransistors);
                        rowTopSmd.rowForTransistors++;
                    }
                    case "LedDiode" -> {
                        writeParameters(writeComponent, rowBotSmd.rowForLedDiodes);
                        rowTopSmd.rowForLedDiodes++;
                    }
                    case "Optocoupler" -> {
                        writeParameters(writeComponent, rowBotSmd.rowForOptocouplers);
                        rowTopSmd.rowForOptocouplers++;
                    }
                    case "Diode" -> {
                        writeParameters(writeComponent, rowBotSmd.rowForDiode);
                        rowTopSmd.rowForDiode++;
                    }
                    case "Microchip" -> {
                        writeParameters(writeComponent, rowBotSmd.rowForMicrochip);
                        rowTopSmd.rowForMicrochip++;
                    }
                    case "Relay" -> {
                        writeParameters(writeComponent, rowBotSmd.rowForRelays);
                        rowTopSmd.rowForRelays++;
                    }
                    case "Connectors" -> {
                        writeParameters(writeComponent, rowBotSmd.rowForConnectors);
                        rowTopSmd.rowForConnectors++;
                    }
                    case "Other" -> {
                        writeParameters(writeComponent, rowBotSmd.rowForOther);
                        rowTopSmd.rowForOther++;
                    }
                    case "Not install" -> {
                        writeParameters(writeComponent, rowBotSmd.rowForNC);
                        rowTopSmd.rowForNC++;
                    }
                }


            } else if (writeComponent.typePin.equals("PHT")) {

                switch (writeComponent.type) {
                    case "Capacitor" -> {
                        writeParameters(writeComponent, rowBotPht.rowForCapacitors);
                        rowTopSmd.rowForCapacitors++;
                    }
                    case "Resistor" -> {
                        writeParameters(writeComponent, rowBotPht.rowForResists);
                        rowTopSmd.rowForResists++;
                    }
                    case "Transistor" -> {
                        writeParameters(writeComponent, rowBotPht.rowForTransistors);
                        rowTopSmd.rowForTransistors++;
                    }
                    case "LedDiode" -> {
                        writeParameters(writeComponent, rowBotPht.rowForLedDiodes);
                        rowTopSmd.rowForLedDiodes++;
                    }
                    case "Optocoupler" -> {
                        writeParameters(writeComponent, rowBotPht.rowForOptocouplers);
                        rowTopSmd.rowForOptocouplers++;
                    }
                    case "Diode" -> {
                        writeParameters(writeComponent, rowBotPht.rowForDiode);
                        rowTopSmd.rowForDiode++;
                    }
                    case "Microchip" -> {
                        writeParameters(writeComponent, rowBotPht.rowForMicrochip);
                        rowTopSmd.rowForMicrochip++;
                    }
                    case "Relay" -> {
                        writeParameters(writeComponent, rowBotPht.rowForRelays);
                        rowTopSmd.rowForRelays++;
                    }
                    case "Connectors" -> {
                        writeParameters(writeComponent, rowBotPht.rowForConnectors);
                        rowTopSmd.rowForConnectors++;
                    }
                    case "Other" -> {
                        writeParameters(writeComponent, rowBotPht.rowForOther);
                        rowTopSmd.rowForOther++;
                    }
                    case "Not install" -> {
                        writeParameters(writeComponent, rowBotPht.rowForNC);
                        rowTopSmd.rowForNC++;
                    }
                }
            } else {
                System.out.println("Error:if writeComponent.typePin");
            }

        } else {
            System.out.println("Error: writeComponent.layer.equals");
        }
    }

    public void save() throws IOException {
        FileOutputStream fileOut = new FileOutputStream(nameBoard);
        file.write(fileOut);
    }



    private  ArrayList<Component> setTableBom(DataBom[] sortBom) {

        ArrayList<Component> setTable = new ArrayList<>();

        for (DataBom sortRow :
                sortBom) {
            Component component = new Component();

            component.setValue(sortRow.value);
            component.setDesignator(sortRow.designator);
            component.setFootprint(sortRow.footprint);
            component.setComment(sortRow.comment);
            component.setQuantity(sortRow.quantity);
            component.setQuantityPin(sortRow.amountPad);
            component.setTypePin(sortRow.solderType);
            component.setNote(" ");
            setTable.add(component);
        }
        return setTable;
    }

    private int findRowWith(int startRow, String cell, Sheet sheetBom) {
        int findRow = 0;

        for (int i = startRow; i < sheetBom.getLastRowNum(); i++) {
            if (sheetBom.getRow(i) == null) {
                sheetBom.createRow(i);
            }
            if (sheetBom.getRow(i).getCell(0) == null) {
                sheetBom.getRow(i).createCell(0);
            }

            if (sheetBom.getRow(i).getCell(0).toString().equals(cell)) {
                findRow = i;
            }
        }
        return findRow;
    }

    private void writeParameters(Component writeComponent, int writeRow) {
        writeCell(writeComponent.value, writeRow, cellValue);
        writeCell(writeComponent.designator, writeRow, cellDesignator);
        writeCell(writeComponent.footprint, writeRow, cellFootprint);
        writeCell(writeComponent.comment, writeRow, cellComments);
        writeCell(writeComponent.quantity, writeRow, cellQuantity);
        writeCell(writeComponent.quantityPin, writeRow, cellQuantityPin);
        writeCell(writeComponent.quantity * writeComponent.quantityPin, writeRow, cellQuantitySolder);
        writeCell(writeComponent.note, writeRow, cellNote);
    }

    private void writeCell(String value, int row, int cell) {

        if (sheet.getRow(row) == null) {
            sheet.createRow(row);
        }
        if (sheet.getRow(row).getCell(cell) == null) {
            sheet.getRow(row).createCell(cell);
        }
        sheet.getRow(row).getCell(cell).setCellValue(value);
    }

    private void writeCell(int value, int row, int cell) {

        if (sheet.getRow(row) == null) {
            sheet.createRow(row);
        }
        if (sheet.getRow(row).getCell(cell) == null) {
            sheet.getRow(row).createCell(cell);
        }
        sheet.getRow(row).getCell(cell).setCellValue(value);
    }

}

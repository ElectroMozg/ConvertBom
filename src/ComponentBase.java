import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.*;
import java.util.Scanner;

import org.apache.poi.ss.usermodel.*;

import java.io.FileInputStream;
import java.io.IOException;


public class ComponentBase {
    static Workbook tableFile;
    static String nameFile;

    Sheet sheet;
    Footprint footprint;
    int numLastRow;
    int numCellValue = 0;
    int numCellType = 1;

    public ComponentBase(String nameExlFile) throws IOException {
        nameFile = nameExlFile;
        tableFile = WorkbookFactory.create(new FileInputStream(nameFile));
        footprint = new Footprint();
        sheet = tableFile.getSheetAt(0);
        numLastRow = sheet.getLastRowNum();
    }

    void enterFromTerminal() throws IOException {
        Scanner in = new Scanner(System.in);
        System.out.print("Ведите значение компонента: ");
        String value = in.nextLine();
        System.out.print("Введите тип компонента: ");
        String type = in.nextLine();
        newComponent(value, type);
    }

    boolean findValue(String value) {
        for (int i = 0; i < sheet.getLastRowNum()+1; i++) {
            String baseValue = sheet.getRow(i).getCell(numCellValue).toString();
            if (baseValue.equals(value)) {
                return true;
            }
        }
        return false;
    }

    void newComponent(String value, String type) throws IOException {
        sheet.createRow(++numLastRow);
        sheet.getRow(numLastRow).createCell(numCellValue).setCellValue(value);
        sheet.getRow(numLastRow).createCell(numCellType).setCellValue(type);

        FileOutputStream fileOut = new FileOutputStream(nameFile);
        tableFile.write(fileOut);
    }

    void setType(String value, String type) {
        String readType;

        for (int i = 0; i < sheet.getLastRowNum(); i++) {
            if (sheet.getRow(i).getCell(numCellValue).toString().equals(value)) {
                if (sheet.getRow(i).getCell(numCellType) == null) {
                    sheet.getRow(i).createCell(numCellType);
                }
                sheet.getRow(i).getCell(numCellType).setCellValue(type);
            }
        }
    }

    String getType(String value) throws IOException {

        if (!findValue(value)) {
            do {
                System.out.println("Неизвестное значение" + value);
                System.out.println("Необходимо внести значение в базу");
                enterFromTerminal();
            }
            while (!findValue(value));
        }
        for (int i = 0; i < sheet.getLastRowNum() + 1; i++) {
            if (sheet.getRow(i).getCell(numCellValue).toString().equals(value)) {
                String readType = sheet.getRow(i).getCell(numCellType).toString();
                return readType;
            }
        }
        return null;
    }

    static class Footprint {
        Sheet sheet;
        int numRow;
        int numCellName;
        int numCellTypePad;
        int numCellAmountPad;

        Footprint() {
            sheet = tableFile.getSheetAt(1);
            numRow = sheet.getLastRowNum();
            numCellName = 0;
            numCellTypePad = 1;
            numCellAmountPad = 2;

        }

        void newFootprint(String setName, String setTypePad, int setNumPad) throws IOException {
            sheet.createRow(++numRow);
            sheet.getRow(numRow).createCell(numCellName).setCellValue(setName);
            sheet.getRow(numRow).createCell(numCellTypePad).setCellValue(setTypePad);
            sheet.getRow(numRow).createCell(numCellAmountPad).setCellValue(setNumPad);

            FileOutputStream fileOut = new FileOutputStream(nameFile);
            tableFile.write(fileOut);
        }

        boolean findFootprint(String nameFootprint) {
            for (int i = 0; i < sheet.getLastRowNum() + 1; i++) {
                String readFootprint = sheet.getRow(i).getCell(numCellName).toString();
                if (readFootprint.equals(nameFootprint)) {
                    return true;
                }
            }
            return false;
        }

        String getSolderType(String nameFootprint) throws IOException {

            if (!findFootprint(nameFootprint)) {
                do {
                    System.out.println("getSolderType Неизвестный Footprint" + nameFootprint);
                    System.out.println("Необходимо внести Footprint в базу");
                    enterFromTerminal();
                }
                while (!findFootprint(nameFootprint));
            }

            String SolderType = null;
            for (int i = 0; i < sheet.getLastRowNum(); i++) {
                if (sheet.getRow(i).getCell(numCellName).toString().equals(nameFootprint)) {
                    SolderType = sheet.getRow(i).getCell(numCellTypePad).toString();
                }
            }
            return SolderType;
        }

        int getAmountPad(String nameFootprint) throws IOException {

            if (!findFootprint(nameFootprint)) {
                do {
                    System.out.println("getAmountPad Неизвестный Footprint " + nameFootprint);
                    System.out.println("Необходимо внести Footprint в базу");
                    enterFromTerminal();
                }
                while (!findFootprint(nameFootprint));
            }

            int AmountPad = 0;
            for (int i = 0; i < sheet.getLastRowNum(); i++) {
                if (sheet.getRow(i).getCell(numCellName).toString().equals(nameFootprint)) {
                    AmountPad = (int) sheet.getRow(i).getCell(numCellAmountPad).getNumericCellValue();
                }
            }
            return AmountPad;
        }

        void setSolderType(String nameFootprint, String SolderType) {
            for (int i = 0; i < sheet.getLastRowNum(); i++) {
                if (sheet.getRow(i).getCell(numCellName).toString().equals(nameFootprint)) {
                    if (sheet.getRow(i).getCell(numCellTypePad) == null) {
                        sheet.getRow(i).createCell(numCellTypePad);
                    }
                    sheet.getRow(i).getCell(numCellTypePad).setCellValue(SolderType);
                }
            }
        }

        void setAmountPad(String nameFootprint, int amountPad) {
            for (int i = 0; i < sheet.getLastRowNum(); i++) {
                if (sheet.getRow(i).getCell(numCellName).toString().equals(nameFootprint)) {
                    if (sheet.getRow(i).getCell(numCellAmountPad) == null) {
                        sheet.getRow(i).createCell(numCellAmountPad);
                    }
                    sheet.getRow(i).getCell(numCellAmountPad).setCellValue(amountPad);
                }
            }
        }

        void enterFromTerminal() throws IOException {
            Scanner in = new Scanner(System.in);
            System.out.print("Ведите имя Footprint: ");
            String name = in.nextLine();
            if (findFootprint(name)) {
                System.out.println("Footprint уже существует");
                return;
            }
            System.out.print("Введите тип пайки: ");
            String typeSolder = in.nextLine();
            //TODO Надо добавить проверку ввода типа. Может быть только SMD или PTH
            System.out.print("Введите количество контактов: ");
            int amountPad = in.nextInt();
            System.out.println("Создан Footprint " + name + " " + typeSolder + "количество контактов: " + amountPad);
            newFootprint(name, typeSolder, amountPad);
        }

    }


}

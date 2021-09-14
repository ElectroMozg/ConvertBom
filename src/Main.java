import org.apache.poi.EncryptedDocumentException;

import java.io.IOException;


public class Main {


    public static void main(String[] args) throws EncryptedDocumentException, IOException {


        Bom bom =   new Bom("Test_BOM.xlsx");

        bom.readComponent   = bom.readAllComponents();

        bom.sortBom(bom.readComponent);

        bom.saveAllComponents("Sort_BOM.xlsx",bom.readComponent);


        System.out.println("End");
        System.out.println("ENd");


    }


}

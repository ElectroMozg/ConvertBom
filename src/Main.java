import org.apache.poi.EncryptedDocumentException;

import java.io.IOException;


public class Main {


    public static void main(String[] args) throws EncryptedDocumentException, IOException {

        Bom bom =   new Bom("Test_BOM.xlsx");

        bom.readComponent   = bom.readAllComponents();

        bom.sortByValue(bom.readComponent);
        bom.sortByFootprint(bom.readComponent);
        bom.sortByType(bom.readComponent);
        bom.sortByLayer(bom.readComponent);


        System.out.println("End");
        System.out.println("ENd");
    }


}

import org.apache.poi.EncryptedDocumentException;

import java.io.IOException;


public class Main {


    public static void main(String[] args) throws EncryptedDocumentException, IOException {


        Bom bom = new Bom("Test_BOM.xlsx");

        bom.readComponent = bom.readAllComponents();
        bom.sortBom(bom.readComponent);
        bom.readComponent = bom.contEqualsRows(bom.readComponent);
        bom.saveAllComponents(bom.readComponent);

        PatternSant patternSant = new PatternSant(bom.readComponent);

        System.out.println("End");
        System.out.println("ENd");


    }


}

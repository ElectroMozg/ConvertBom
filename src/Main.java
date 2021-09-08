import org.apache.poi.EncryptedDocumentException;

import java.io.IOException;

public class Main {



    public static void main(String[] args) throws EncryptedDocumentException, IOException {
        ComponentBase fpTable = new ComponentBase("Text.xlsx");

        fpTable.findPackage("0603");
        fpTable.findPackage("0605");
        fpTable.findPackage("1206");

        //footprint wrFootprint   =   new footprint("1206","SMD",2);
        //fpTable.wrNewFootprint(fpTable.enterFootprint());
    }

}

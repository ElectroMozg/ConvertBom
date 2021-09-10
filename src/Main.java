import org.apache.poi.EncryptedDocumentException;

import java.io.IOException;

public class Main {



    public static void main(String[] args) throws EncryptedDocumentException, IOException {
        ComponentBase componentBase = new ComponentBase("Test.xlsx");

        componentBase.footprint.enterFromTerminal();
        //componentBase.enterFromTerminal();
        System.out.println( "0R Type: " + componentBase.getType("0R"));

        //footprint wrFootprint   =   new footprint("1206","SMD",2);
        //fpTable.wrNewFootprint(fpTable.enterFootprint());
    }

}

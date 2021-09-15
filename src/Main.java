import org.apache.poi.EncryptedDocumentException;

import java.io.IOException;


public class Main {


    public static void main(String[] args) throws EncryptedDocumentException, IOException {


        DataBom test  =   new DataBom();

        test.convertValueCapacitor("1.2nF");

//        String people = "8k25 10%";
//
//        String[] peopleArray = people.split(" ");
//        for (String human : peopleArray) {
//            System.out.println(human);
//            if (human.contains("k")){
//                String[] testArray = human.split("k");
//                for (String test : testArray) {
//                    System.out.println(test);
//
//                }
//            }
//
//
//        }
//        System.out.println(peopleArray.length);

      Bom bom =   new Bom("Test_BOM.xlsx");




        bom.readComponent   = bom.readAllComponents();

        bom.sortBom(bom.readComponent);

       // bom.readComponent   = bom.contEqualsRows(bom.readComponent);

        bom.saveAllComponents("Sort_BOM.xlsx",bom.readComponent);


        System.out.println("End");
        System.out.println("ENd");


    }


}

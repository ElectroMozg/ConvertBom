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


        public String getValue() {
            return designator;
        }

        public void setValue(String designator) {
            this.designator = designator;
        }

        public String getDesignator() {
            return designator;
        }

        public void setDesignator(String designator) {
            this.designator = designator;
        }

        public String getFootprint() {
            return footprint;
        }

        public void setFootprint(String footprint) {
            this.footprint = footprint;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public int getQuantityPin() {
            return quantityPin;
        }

        public void setQuantityPin(int quantityPin) {
            this.quantityPin = quantityPin;
        }

        public String getTypePin() {
            return typePin;
        }

        public void setTypePin(String quantitySolder) {
            this.typePin = quantitySolder;
        }

        public String getNote() {
            return note;
        }

        public void setNote(String note) {
            this.note = note;
        }
    }


    private String nameBoard;
    private int numBord;
    ArrayList<Component> tableBom = new ArrayList<>();


    public String getNameBoard() {
        return nameBoard;
    }

    public void setNameBoard(String nameBoard) {
        this.nameBoard = nameBoard;
    }

    public int getNumBord() {
        return numBord;
    }

    public void setNumBord(int numBord) {
        this.numBord = numBord;
    }

    public ArrayList<Component> getTableBom() {
        return tableBom;
    }

    public void setTableBom(ArrayList<Component> tableBom) {
        this.tableBom = tableBom;
    }

    public void setTableBom(DataBom[] sortBom) {

        for (DataBom sortRow :
                sortBom) {
            Component component =   new Component();

            component.setValue(sortRow.value);
            component.setDesignator(sortRow.designator);
            component.setFootprint(sortRow.footprint);
            component.setComment(sortRow.comment);
            component.setQuantity(sortRow.quantity);
            component.setQuantityPin(sortRow.amountPad);
            component.setTypePin(sortRow.solderType);
            component.setNote(" ");

        }

        this.tableBom = tableBom;
    }


}

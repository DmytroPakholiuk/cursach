package com.cursach.dmytropakholiuk;

public class CellFactory {

    public static final int CELLTYPE_WBC = 1;
    public static final int CELLTYPE_RBC = 2;
    public static final int CELLTYPE_INACTIVEPLAS = 3;
    public static final int CELLTYPE_PLASVIVAX = 4;
    public static final int CELLTYPE_HIVPLAS = 5;

    /**
     * @throws Exception
     * @param type - the type of cell, defined in class constants
     * @return Cell
     */
    public static Cell createCell (int type) throws Exception {
        switch (type){
            case CELLTYPE_WBC:
                return new WhiteBloodCell();
            default:
                throw new Exception("Unsupported cell type");
        }

    }
}

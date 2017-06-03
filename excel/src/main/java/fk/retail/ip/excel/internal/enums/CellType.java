package fk.retail.ip.excel.internal.enums;

/**
 * Created by agarwal.vaibhav on 23/05/17.
 */
public enum CellType {
    NUMERIC(0),
    STRING(1),
    FORMULA(2);

    private int type;

    CellType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }
}

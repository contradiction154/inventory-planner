package fk.retail.ip.excel.internal.command.Parser;

/**
 * Created by agarwal.vaibhav on 18/05/17.
 */
public enum  ColumnType {
    INTEGER("integer"),
    DOUBLE("double"),
    STRING("string");

    String type;

    private ColumnType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return type;
    }

}
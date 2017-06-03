package fk.retail.ip.requirement.internal;

import fk.retail.ip.excel.internal.Excel.Excel;
import fk.retail.ip.excel.internal.enums.CellType;
import fk.retail.ip.excel.internal.model.Column;
import fk.retail.ip.excel.internal.validation.IsInteger;
import fk.retail.ip.excel.internal.validation.IsNonEmpty;
import fk.retail.ip.excel.internal.validation.IsPositive;
import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by agarwal.vaibhav on 17/05/17.
 */
@Data
public class RequirementExcel implements Excel {

    private List<Column> columnList;

    public RequirementExcel(String state) {
        columnList = new ArrayList<>();
        IsInteger isInteger = new IsInteger();
        IsNonEmpty isNonEmpty = new IsNonEmpty();
        IsPositive isPositive = new IsPositive();
        switch (state) {
            case "verified":
                columnList.add(new Column("Requirement Id", CellType.NUMERIC, null));
                columnList.add(new Column("FSN", CellType.NUMERIC, null));
                columnList.add(new Column("Vertical", CellType.NUMERIC, null));
                columnList.add(new Column("Category", CellType.NUMERIC, null));
                columnList.add(new Column("Super Category", CellType.NUMERIC, null));
                columnList.add(new Column("Title", CellType.NUMERIC, null));
                columnList.add(new Column("Brand", CellType.NUMERIC, null));
                columnList.add(new Column("PV Band", CellType.NUMERIC, null));
                columnList.add(new Column("Sales Band", CellType.NUMERIC, null));
                columnList.add(new Column("Flipkart Selling Price", CellType.NUMERIC, null));
                columnList.add(new Column("Sales bucket-0", CellType.NUMERIC, null));
                columnList.add(new Column("Sales bucket-1", CellType.NUMERIC, null));
                columnList.add(new Column("Sales bucket-2", CellType.NUMERIC, null));
                columnList.add(new Column("Sales bucket-3", CellType.NUMERIC, null));
                columnList.add(new Column("Sales bucket-4", CellType.NUMERIC, null));
                columnList.add(new Column("Sales bucket-5", CellType.NUMERIC, null));
                columnList.add(new Column("Sales bucket-6", CellType.NUMERIC, null));
                columnList.add(new Column("Sales bucket-7", CellType.NUMERIC, null));
                columnList.add(new Column("Inventory", CellType.NUMERIC, null));
                columnList.add(new Column("Intransit", CellType.NUMERIC, null));
                columnList.add(new Column("Forecast", CellType.NUMERIC, null));
                columnList.add(new Column("QOH", CellType.NUMERIC, null));
                columnList.add(new Column("Procurement Type", CellType.NUMERIC, null));
                columnList.add(new Column("Currency", CellType.NUMERIC, null));
                columnList.add(new Column("Purchase Price", CellType.NUMERIC, null));
                columnList.add(new Column("MRP", CellType.NUMERIC, null));
                columnList.add(new Column("SLA", CellType.NUMERIC, null));
                columnList.add(new Column("Total Value", CellType.NUMERIC, null));
                columnList.add(new Column("Supplier", CellType.NUMERIC, null));
                columnList.add(new Column("Quantity", CellType.NUMERIC,
                        new ArrayList<>(Arrays.asList(isInteger, isPositive, isNonEmpty))));
                break;

            case "proposed":
                columnList.add(new Column("Requirement Id", CellType.NUMERIC, null));
                columnList.add(new Column("FSN", CellType.NUMERIC, null));
                columnList.add(new Column("Vertical", CellType.NUMERIC, null));
                columnList.add(new Column("Category", CellType.NUMERIC, null));
                columnList.add(new Column("Super Category", CellType.NUMERIC, null));
                columnList.add(new Column("Title", CellType.NUMERIC, null));
                columnList.add(new Column("Brand", CellType.NUMERIC, null));
                columnList.add(new Column("PV Band", CellType.NUMERIC, null));
                columnList.add(new Column("Sales Band", CellType.NUMERIC, null));
                columnList.add(new Column("Flipkart Selling Price", CellType.NUMERIC, null));
                columnList.add(new Column("Sales bucket-0", CellType.NUMERIC, null));
                columnList.add(new Column("Sales bucket-1", CellType.NUMERIC, null));
                columnList.add(new Column("Sales bucket-2", CellType.NUMERIC, null));
                columnList.add(new Column("Sales bucket-3", CellType.NUMERIC, null));
                columnList.add(new Column("Sales bucket-4", CellType.NUMERIC, null));
                columnList.add(new Column("Sales bucket-5", CellType.NUMERIC, null));
                columnList.add(new Column("Sales bucket-6", CellType.NUMERIC, null));
                columnList.add(new Column("Sales bucket-7", CellType.NUMERIC, null));
                columnList.add(new Column("Inventory", CellType.NUMERIC, null));
                columnList.add(new Column("Intransit", CellType.NUMERIC, null));
                columnList.add(new Column("Forecast", CellType.NUMERIC, null));
                columnList.add(new Column("QOH", CellType.NUMERIC, null));
                columnList.add(new Column("Procurement Type", CellType.NUMERIC, null));
                columnList.add(new Column("Currency", CellType.NUMERIC, null));
                columnList.add(new Column("Purchase Price", CellType.NUMERIC, null));
                columnList.add(new Column("MRP", CellType.NUMERIC, null));
                columnList.add(new Column("SLA", CellType.NUMERIC, null));
                columnList.add(new Column("Total Value", CellType.NUMERIC, null));
                columnList.add(new Column("Supplier", CellType.NUMERIC, null));
                columnList.add(new Column("Quantity", CellType.NUMERIC,
                        new ArrayList<>(Arrays.asList(isInteger, isPositive, isNonEmpty))));
                break;


            default:
                Column quantity = new Column("quantity", CellType.NUMERIC, null);
                columnList.add(quantity);
                columnList.add(new Column("app", CellType.NUMERIC, null));
                columnList.add(new Column("Requirement Id", CellType.STRING, null));
                columnList.add(new Column("asd", CellType.NUMERIC, null));


        }

    }

}

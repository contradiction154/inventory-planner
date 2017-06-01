package fk.retail.ip.requirement.internal;

import fk.retail.ip.excel.internal.command.Excel.Excel;
import fk.retail.ip.excel.internal.command.enums.CellType;
import fk.retail.ip.excel.internal.command.model.Column;
import fk.retail.ip.excel.internal.command.validation.IsInteger;
import fk.retail.ip.excel.internal.command.validation.Validation;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by agarwal.vaibhav on 17/05/17.
 */
@Data
public class RequirementExcel implements Excel {

    private List<Column> columnList;

    public RequirementExcel(String state) {
        columnList = new ArrayList<>();
        switch (state) {
            case "verified":
                List<Validation> validations = new ArrayList<>();
                IsInteger isInteger = new IsInteger();
                validations.add(isInteger);
                Column quantity = new Column("quantity", CellType.NUMERIC, validations);
                columnList.add(quantity);
                columnList.add(new Column("app", CellType.NUMERIC, validations));
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
                List<Validation> validationList = new ArrayList<>();
                IsInteger isInteger1 = new IsInteger();
                validationList.add(isInteger1);

                columnList.add(new Column("Quantity", CellType.NUMERIC, validationList));
                break;


            default:
                quantity = new Column("quantity", CellType.NUMERIC, null);
                columnList.add(quantity);
                columnList.add(new Column("app", CellType.NUMERIC, null));
                columnList.add(new Column("Requirement Id", CellType.STRING, null));
                columnList.add(new Column("asd", CellType.NUMERIC, null));


        }

    }

}

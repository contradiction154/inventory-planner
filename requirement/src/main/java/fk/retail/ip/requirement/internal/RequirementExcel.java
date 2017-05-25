package fk.retail.ip.requirement.internal;

import fk.retail.ip.excel.internal.command.Excel.Excel;
import fk.retail.ip.excel.internal.command.enums.CellType;
import fk.retail.ip.excel.internal.command.validation.IsInteger;
import fk.retail.ip.excel.internal.command.validation.Validation;
import fk.retail.ip.excel.internal.command.model.Column;
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
        }

    }

}

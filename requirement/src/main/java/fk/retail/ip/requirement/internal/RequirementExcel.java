package fk.retail.ip.requirement.internal;

import fk.retail.ip.excel.internal.command.Excel.Excel;
import fk.retail.ip.excel.internal.command.validation.IsInteger;
import fk.retail.ip.excel.internal.command.validation.Validation;
import fk.retail.ip.excel.internal.command.model.Column;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by agarwal.vaibhav on 17/05/17.
 */
public class RequirementExcel extends Excel {

    public RequirementExcel(String state) {
        switch (state) {
            case "verified":
                List<Validation> validations = new ArrayList<>();
                IsInteger isInteger = new IsInteger();
                validations.add(isInteger);
                columnList.add(new Column("app", "Double", validations));
                columnList.add(new Column("qty", "Integer", validations));
        }

    }

}

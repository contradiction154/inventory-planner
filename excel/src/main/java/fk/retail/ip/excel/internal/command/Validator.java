package fk.retail.ip.excel.internal.command;

import fk.retail.ip.excel.internal.command.model.Column;
import fk.retail.ip.excel.internal.command.validation.Validation;

import java.util.List;
import java.util.Map;

/**
 * Created by agarwal.vaibhav on 17/05/17.
 */
public class Validator {
    public void validate(List<Column> columnList, List<Map<String, Object>> rows) {
        for (Map<String, Object> row : rows) {
            columnList.forEach(column -> {
                List<Validation> validations = column.getPredicates();
                validations.forEach(validation -> {
                    validation.execute();
                });
            });
        }

    }
}

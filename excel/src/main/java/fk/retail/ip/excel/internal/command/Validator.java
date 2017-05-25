package fk.retail.ip.excel.internal.command;

import fk.retail.ip.excel.internal.command.model.Column;
import fk.retail.ip.excel.internal.command.validation.Validation;

import java.util.List;
import java.util.Map;

/**
 * Created by agarwal.vaibhav on 17/05/17.
 */
public interface Validator {
    default void validate(List<Column> columnList, List<Map<String, String>> rows) {
        for (Map<String, String> row : rows) {
            columnList.forEach(column -> {
                List<Validation> validations = column.getPredicates();
                validations.forEach(validation -> {
                    validation.execute();
                });
            });
        }

    }
}

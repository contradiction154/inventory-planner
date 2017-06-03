package fk.retail.ip.excel.internal.command;

import fk.retail.ip.excel.internal.exceptions.IsEmptyException;
import fk.retail.ip.excel.internal.exceptions.NonIntegerException;
import fk.retail.ip.excel.internal.exceptions.NonPostiveException;
import fk.retail.ip.excel.internal.model.Column;
import fk.retail.ip.excel.internal.validation.Validation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by agarwal.vaibhav on 17/05/17.
 */
public interface Validator {
    default Map<Integer, Map<String, List<String>>> validate(List<Column> columnList, List<Map<String, Object>> rows) {
        /*This is a map of column and its list of errors*/
        Map<Integer, Map<String, List<String>>> rowErrorsMap = new HashMap<>();
        int rowNum = 2;
        for (Map<String, Object> row : rows) {
            Map<String, List<String>> errorMap = new HashMap<>();
            columnList.forEach(column -> {
                List<Validation> validations = column.getPredicates();
                if (validations != null) {
                    String columnName = column.getName();
                    List<String> columnErrorList = new ArrayList<String>();
                    validations.forEach(validation -> {
                        try {
                            validation.execute(row.get(columnName));
                        } catch (NonIntegerException nex) {
                            columnErrorList.add(nex.getMessage());
                        } catch (IsEmptyException iex) {
                            columnErrorList.add(iex.getMessage());
                        } catch (NonPostiveException npx) {
                            columnErrorList.add(npx.getMessage());
                        }
                    });
                    if (!columnErrorList.isEmpty()) {
                        errorMap.put(columnName, columnErrorList);
                    }
                }
            });
            rowErrorsMap.put(rowNum, errorMap);
            rowNum++;
        }

        return rowErrorsMap;
    }
}

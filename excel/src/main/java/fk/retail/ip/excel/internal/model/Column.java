package fk.retail.ip.excel.internal.model;

import fk.retail.ip.excel.internal.enums.CellType;
import fk.retail.ip.excel.internal.validation.Validation;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * Created by agarwal.vaibhav on 17/05/17.
 */
@Data
@AllArgsConstructor
/*This class is used to define the schema of a column in excel*/
public class Column {
    String name;
    CellType datatype;
    List<Validation> predicates;
}

package fk.retail.ip.excel.internal.validation;

import fk.retail.ip.excel.internal.exceptions.IsEmptyException;
import fk.retail.ip.excel.internal.exceptions.NonIntegerException;
import fk.retail.ip.excel.internal.exceptions.NonPostiveException;

/**
 * Created by agarwal.vaibhav on 17/05/17.
 */
public abstract class Validation<T> {
    abstract public <T> boolean execute(T t) throws NonIntegerException, IsEmptyException, NonPostiveException;
}

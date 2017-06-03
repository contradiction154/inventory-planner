package fk.retail.ip.excel.internal.validation;

import fk.retail.ip.excel.internal.exceptions.IsEmptyException;

import java.util.function.Predicate;

/**
 * Created by agarwal.vaibhav on 01/06/17.
 */
public class IsNonEmpty <T> extends Validation<T> {
    @Override
    public <T1> boolean execute(T1 t1) throws IsEmptyException {
        String value = (String) t1;
        Predicate<T1> isInteger = T1 -> value.isEmpty() ;
        if (isInteger.test(t1)) {
            throw new IsEmptyException("dasdsa");
        } else {
            return true;
        }
    }
}

package fk.retail.ip.excel.internal.command.validation;

import java.util.function.Predicate;

/**
 * Created by agarwal.vaibhav on 01/06/17.
 */
public class IsNonEmpty <T> extends Validation<T> {
    @Override
    public <T1> boolean execute(T1 t1) {
        String value = (String) t1;
        Predicate<T1> isInteger = T1 -> value.isEmpty() ;
        if (isInteger.test(t1)) {
            return false;
        } else {
            return true;
        }
    }
}

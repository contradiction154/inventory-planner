package fk.retail.ip.excel.internal.validation;

import java.util.function.Predicate;

/**
 * Created by agarwal.vaibhav on 17/05/17.
 */
public class IsString<T> extends Validation<T> {

    @Override
    public <T> boolean execute(T t) {
        Predicate<T> positive = T -> T instanceof String;
        if (positive.test(t)) {
            return true;
        } else {
            return false;
        }
    }
}

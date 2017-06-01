package fk.retail.ip.excel.internal.command.validation;


import java.util.function.Predicate;

/**
 * Created by agarwal.vaibhav on 17/05/17.
 */
public class IsInteger<T> extends Validation<T> {

    @Override
    public <T> boolean execute(T t) {
        Predicate<T> isInteger = T -> T instanceof Integer;
        if (isInteger.test(t)) {
            return true;
        } else {
            return false;
        }

    }
}

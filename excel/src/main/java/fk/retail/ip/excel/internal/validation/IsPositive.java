package fk.retail.ip.excel.internal.validation;

import fk.retail.ip.excel.internal.exceptions.NonPostiveException;

import java.util.function.Predicate;

/**
 * Created by agarwal.vaibhav on 30/05/17.
 */
public class IsPositive<T> extends Validation<T> {
    @Override
    public <T> boolean execute(T t) throws NonPostiveException {
        Predicate<T> positive = T -> (Double)t > 0;
        if (positive.test(t)) {
            return true;
        } else {
            throw new NonPostiveException("asdsa");
        }
    }
}

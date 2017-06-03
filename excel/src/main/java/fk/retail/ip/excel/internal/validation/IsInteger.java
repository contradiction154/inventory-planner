package fk.retail.ip.excel.internal.validation;


import fk.retail.ip.excel.internal.exceptions.NonIntegerException;

import java.util.function.Predicate;

/**
 * Created by agarwal.vaibhav on 17/05/17.
 */
public class IsInteger<T> extends Validation<T>  {

    @Override
    public <T> boolean execute(T t) throws NonIntegerException {
        Predicate<T> isInteger = T -> T instanceof Integer;
        if (isInteger.test(t)) {
            return true;
        } else {
            throw new NonIntegerException("dasd");
        }

    }
}

package fk.retail.ip.excel.internal.command.validation;

/**
 * Created by agarwal.vaibhav on 17/05/17.
 */
public abstract class Validation<T> {
    abstract public <T> boolean execute(T t);
}

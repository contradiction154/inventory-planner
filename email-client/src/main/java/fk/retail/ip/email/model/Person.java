package fk.retail.ip.email.model;

import lombok.Data;

/**
 * Created by agarwal.vaibhav on 18/05/17.
 */
@Data
public class Person {
    private String name;
    private String address;

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof Person && (((Person) other).getAddress().equals(address));
    }
}


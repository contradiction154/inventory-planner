package fk.retail.ip.requirement.model;

import fk.retail.ip.email.model.Person;
import lombok.Data;

import java.util.List;

/**
 * Created by agarwal.vaibhav on 16/05/17.
 */
@Data
public class EmailIdList {
    List<Person> list;
}

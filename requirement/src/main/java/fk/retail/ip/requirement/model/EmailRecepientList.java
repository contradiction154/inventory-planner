package fk.retail.ip.requirement.model;

import lombok.Data;

import java.util.List;

/**
 * Created by agarwal.vaibhav on 16/05/17.
 */
@Data
public class EmailRecepientList {
    List<String> to;
    List<String> cc;
}

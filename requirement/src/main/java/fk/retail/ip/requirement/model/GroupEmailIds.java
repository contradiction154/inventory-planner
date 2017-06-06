package fk.retail.ip.requirement.model;

import lombok.Data;

import javax.persistence.*;

/**
 * Created by agarwal.vaibhav on 16/05/17.
 */
@Data
@Entity
@Table(name="user_groups")
public class GroupEmailIds {

    @Id
    protected Long id;
    private String group;
    private String emailId;
}

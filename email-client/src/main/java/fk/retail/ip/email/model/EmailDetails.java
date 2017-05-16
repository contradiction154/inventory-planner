package fk.retail.ip.email.model;

import lombok.Data;

import javax.persistence.*;

/**
 * Created by agarwal.vaibhav on 09/05/17.
 */
@Data
@Entity
@Table(name="user_groups")
public class EmailDetails {

    @Id
    @Access(AccessType.PROPERTY)
    protected Long id;
    String group;
    String email_list;
}

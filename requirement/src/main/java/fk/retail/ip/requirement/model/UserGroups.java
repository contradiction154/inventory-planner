package fk.retail.ip.requirement.model;

import lombok.Data;

import java.util.Map;

/**
 * Created by agarwal.vaibhav on 15/05/17.
 */
@Data
public class UserGroups {
    Map<String, Map<String, EmailingList>> userGroupConfig;
}

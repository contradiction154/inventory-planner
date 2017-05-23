package fk.retail.ip.requirement.internal.enums;

import fk.retail.ip.email.internal.enums.EmailParams;

/**
 * Created by agarwal.vaibhav on 14/05/17.
 */
public enum ApprovalEmailParams implements EmailParams {
    USERNAME("userName"),
    USER("user"),
    GROUPNAME("groupName"),
    TIMESTAMP("timestamp"),
    LINK("link"),
    CREATIONDATE("creationDate");

    public String param;

    private ApprovalEmailParams(String param) {
        this.param = param;
    }

    @Override
    public String toString() {
        return param;
    }
}

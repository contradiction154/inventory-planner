package fk.retail.ip.requirement.model;

import fk.retail.ip.email.model.ChannelDataModel;
import lombok.Data;

/**
 * Created by agarwal.vaibhav on 14/05/17.
 */

@Data
public class ApprovalChannelDataModel implements ChannelDataModel {
    String groupName;
    String userName;
    String timestamp;
    String link;
}

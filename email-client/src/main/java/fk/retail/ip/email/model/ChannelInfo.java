package fk.retail.ip.email.model;

import lombok.Data;

import java.util.List;

/**
 * Created by agarwal.vaibhav on 08/05/17.
 */
@Data
public class ChannelInfo {
    String type;
    List<String> to;
    String from;
    List<String> cc;
}

package fk.retail.ip.requirement.internal.command.emailHelper;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import fk.retail.ip.email.client.ConnektClient;
import fk.retail.ip.email.internal.Constants;
import fk.retail.ip.email.internal.enums.EmailParams;
import fk.retail.ip.email.model.ChannelInfo;
import fk.retail.ip.email.model.ConnektPayload;
import fk.retail.ip.email.model.EmailDetails;
import fk.retail.ip.requirement.internal.enums.ApprovalEmailParams;
import fk.retail.ip.requirement.internal.repository.EmailDetailsRepository;
import fk.retail.ip.requirement.model.ApprovalChannelDataModel;
import fk.retail.ip.requirement.model.EmailingList;
import fk.retail.ip.requirement.model.StencilConfigModel;
import fk.retail.ip.requirement.model.UserGroups;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by agarwal.vaibhav on 12/05/17.
 */
@Slf4j
public class ApprovalEmailHelper extends SendEmail {

    private StencilConfigModel stencilConfigModel;
    private UserGroups userGroups;

    @Inject
    public ApprovalEmailHelper(ConnektClient connektClient, EmailDetailsRepository emailDetailsRepository) {
        super(emailDetailsRepository, connektClient);
        try {
            String stencilConfigFile = "/stencil-configurations.json";
            InputStreamReader inputStreamReader = new InputStreamReader(getClass().getResourceAsStream(stencilConfigFile));
            ObjectMapper objectMapper = new ObjectMapper();
            stencilConfigModel = objectMapper.readValue(inputStreamReader, StencilConfigModel.class);

            String userGroupConfigFile = "/ApprovalStatesEmailingListConfigurations.json";
            inputStreamReader = new InputStreamReader(getClass().getResourceAsStream(userGroupConfigFile));
            userGroups = objectMapper.readValue(inputStreamReader, UserGroups.class);

        } catch(IOException ex) {
            log.debug(ex.getMessage());
        }

    }

    @Override
    public void send(Map<EmailParams, String> params, String state, boolean forward){

        if (params.get(ApprovalEmailParams.GROUPNAME).isEmpty()) {
            log.info("no group chosen hence not sending email");
            return;
        }

        String actionDirection = forward ? "forward" : "backward";

        ConnektPayload connektPayload = new ConnektPayload();

        ApprovalChannelDataModel approvalChannelDataModel = new ApprovalChannelDataModel();
        approvalChannelDataModel.setUserName(params.get(ApprovalEmailParams.USERNAME));
        approvalChannelDataModel.setTimestamp(params.get(ApprovalEmailParams.TIMESTAMP));
        approvalChannelDataModel.setGroupName(params.get(ApprovalEmailParams.GROUPNAME));
        approvalChannelDataModel.setLink(params.get(ApprovalEmailParams.LINK));
        connektPayload.setChannelDataModel(approvalChannelDataModel);

        String emailType = getEmailType(state, actionDirection);
        String stencilId = getStencilId(emailType);
        connektPayload.setStencilId(stencilId);

        List<String> groupNames = new ArrayList<>();

        String toUserGroupName = getToUserType(state, actionDirection) + "_" + params.get(ApprovalEmailParams.GROUPNAME);
        String ccUserGroupName = getCCUserType(state, actionDirection) + "_" + params.get(ApprovalEmailParams.GROUPNAME);
        toUserGroupName = "IPC_PowerBanks";
        ccUserGroupName = "CDO_PowerBanks";

        groupNames.add(toUserGroupName);
        groupNames.add(ccUserGroupName);

        List<EmailDetails> emailDetailsList = getEmailDetails(groupNames);
        if (emailDetailsList == null) {
            log.info("no emailing list found for given stencilId and group");
            return;
        }

        Map<String, String> emailList = new HashMap<>();
        emailDetailsList.forEach(item -> {
            emailList.put(item.getGroup(), item.getEmail_list());
        });

        ChannelInfo channelInfo = new ChannelInfo();
        channelInfo.setType(Constants.APPROVAL_CHANNEL_INFO_TYPE);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            /*Parse the json emailing list fetched from db*/
            EmailingList toList = objectMapper.readValue(emailList.get(toUserGroupName), EmailingList.class);
            EmailingList ccList = objectMapper.readValue(emailList.get(ccUserGroupName), EmailingList.class);
            channelInfo.setTo(toList.getList());
            channelInfo.setCc(ccList.getList());
            channelInfo.setFrom(fromEmailAddress);
        } catch(IOException ex) {
            log.info("unable to parse the json value of emailing list");
            return;
        }

//        channelInfo.setCc(emailList.get(ccUserGroupName));
        connektPayload.setChannelInfo(channelInfo);
        connektPayload.setContextId(Constants.APPROVAL_CONTEXT_ID);
        connektClient.sendEmail(connektPayload);

    }

    private String getStencilId(String emailType) {
        return stencilConfigModel.getEmailTypeStencilMapping().get(emailType);
    }

    private String getEmailType(String state, String actionDirection) {
        return state + "_" + actionDirection;
    }

    //Here i have to parse the approval states emaiiling list config to get the group name
    private String getToUserType(String state, String actionDirection) {
        //return userGroups.getUserGroupConfig().get(state).get(actionDirection).getList();
        return null;

    }

    private String getCCUserType(String state, String actionDirection) {
        return null;
    }

}

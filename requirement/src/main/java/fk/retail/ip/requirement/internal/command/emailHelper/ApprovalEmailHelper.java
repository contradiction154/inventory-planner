package fk.retail.ip.requirement.internal.command.emailHelper;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import fk.retail.ip.email.client.ConnektClient;
import fk.retail.ip.email.internal.enums.EmailParams;
import fk.retail.ip.email.model.ChannelInfo;
import fk.retail.ip.email.model.ConnektPayload;
import fk.retail.ip.email.model.Person;
import fk.retail.ip.requirement.internal.Constants;
import fk.retail.ip.requirement.internal.enums.ApprovalEmailParams;
import fk.retail.ip.requirement.internal.repository.EmailDetailsRepository;
import fk.retail.ip.requirement.model.*;
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
            inputStreamReader.close();

            String userGroupConfigFile = "/ApprovalStatesEmailingListConfigurations.json";
            inputStreamReader = new InputStreamReader(getClass().getResourceAsStream(userGroupConfigFile));
            userGroups = objectMapper.readValue(inputStreamReader, UserGroups.class);
            inputStreamReader.close();
        } catch(IOException ex) {
            log.debug(ex.getMessage());
        }

    }

    @Override
    public void send(Map<EmailParams, String> params, String state, boolean forward){

        String groupName = params.get(ApprovalEmailParams.GROUPNAME);

        if (groupName.isEmpty() || groupName == null) {
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
        approvalChannelDataModel.setCreationDate(params.get(ApprovalEmailParams.CREATIONDATE));
        connektPayload.setChannelDataModel(approvalChannelDataModel);

        String emailType = getEmailType(state, actionDirection);
        String stencilId = getStencilId(emailType);
        connektPayload.setStencilId(stencilId);

        List<String> groupNames = new ArrayList<>();

        List<String> toUserGroupNames = getToUserType(state, actionDirection, groupName);
        List<String> ccUserGroupNames = getCCUserType(state, actionDirection, groupName);

        Map<String, String> recepientUserMapping = new HashMap<>();
        toUserGroupNames.forEach(user -> {
            groupNames.add(user);
            recepientUserMapping.put(user, "to");
        });

        ccUserGroupNames.forEach(user -> {
            groupNames.add(user);
            recepientUserMapping.put(user, "cc");
        });

        if (groupNames.isEmpty()) {
            return;
        }

        List<GroupEmailIds> groupEmailIdsList = getEmailDetails(groupNames);
        if (groupEmailIdsList == null) {
            log.info("no emailing list found for given stencilId and group");
            return;
        }

        ChannelInfo channelInfo = new ChannelInfo();
        channelInfo.setType(Constants.APPROVAL_CHANNEL_INFO_TYPE);
        ObjectMapper objectMapper = new ObjectMapper();

        List<Person> toList = new ArrayList<>();
        List<Person> ccList = new ArrayList<>();
        groupEmailIdsList.forEach(group -> {
            try {
                /*Parse the json emailing list fetched from db*/
                EmailIdList emailIdList = objectMapper.readValue(group.getEmailId(), EmailIdList.class);
                if (recepientUserMapping.get(group.getGroup()).equals("to")) {
                    toList.addAll(emailIdList.getList());
                } else {
                    ccList.addAll(emailIdList.getList());
                }
            } catch (IOException ex) {
                log.debug("unable to parse json");
            }
        });

        getUniqueRecepients(toList, ccList);

        channelInfo.setCc(ccList);
        channelInfo.setTo(toList);

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

    private List<String> getToUserType(String state, String actionDirection, String groupName) {
        if (isUserGroupPresent(userGroups, actionDirection, state)) {
            List<String> toUsers =  userGroups.getStateUserGroups().get(state).get(actionDirection).getTo();
            return createUserGroupList(toUsers, groupName);
        } else {
            return new ArrayList<>();
        }

    }

    private List<String> getCCUserType(String state, String actionDirection, String groupName) {
        if (isUserGroupPresent(userGroups, actionDirection, state)) {
            List<String> ccUsers = userGroups.getStateUserGroups().get(state).get(actionDirection).getCc();
            return createUserGroupList(ccUsers, groupName);
        } else {
            return new ArrayList<>();
        }

    }

    private List<String> createUserGroupList(List<String> userList, String groupName) {
        List<String> userGroupList = new ArrayList<>();
        userList.forEach(user -> {
            userGroupList.add(user + "_" + groupName);
        });
        return userGroupList;
    }

    private void getUniqueRecepients(List<Person> toList, List<Person> ccList) {
        List<Person> commonPerson = new ArrayList<>(toList);
        commonPerson.retainAll(ccList);
        ccList.removeAll(toList);
    }

    private boolean isUserGroupPresent(UserGroups userGroups, String actionDirection, String state) {
        try {
            if (userGroups.getStateUserGroups().get(state).get(actionDirection) != null) {
                return true;
            } else {
                return false;
            }
        } catch (NullPointerException ex) {
            return false;
        }
    }

}

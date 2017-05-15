package fk.retail.ip.requirement.internal.command.emailHelper;


import com.fasterxml.jackson.core.type.TypeReference;
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
import fk.retail.ip.requirement.model.StencilConfigModel;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

/**
 * Created by agarwal.vaibhav on 12/05/17.
 */
@Slf4j
public class ApprovalEmailHelper extends SendEmail {

    private StencilConfigModel stencilConfigModel;

    @Inject
    public ApprovalEmailHelper(ConnektClient connektClient, EmailDetailsRepository emailDetailsRepository) {
        super(emailDetailsRepository, connektClient);
        try {
            String stencilConfigFile = "/stencil-configurations.json";
            InputStreamReader inputStreamReader = new InputStreamReader(getClass().getResourceAsStream(stencilConfigFile));
            ObjectMapper objectMapper = new ObjectMapper();
            stencilConfigModel = objectMapper.readValue(inputStreamReader, StencilConfigModel.class);
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

        ConnektPayload connektPayload = new ConnektPayload();
        String stencilId = getStencilId(state, forward);
        connektPayload.setStencilId(stencilId);

        ApprovalChannelDataModel approvalChannelDataModel = new ApprovalChannelDataModel();
        approvalChannelDataModel.setUserName(params.get(ApprovalEmailParams.USERNAME));
        approvalChannelDataModel.setTimestamp(params.get(ApprovalEmailParams.TIMESTAMP));
        approvalChannelDataModel.setGroupName(params.get(ApprovalEmailParams.GROUPNAME));
        approvalChannelDataModel.setLink(params.get(ApprovalEmailParams.LINK));
        connektPayload.setChannelDataModel(approvalChannelDataModel);

        String emailType = forward ? getEmailType(state, ApprovalEmailParams.GROUPNAME.toString(), "forward") :
                getEmailType(state, ApprovalEmailParams.GROUPNAME.toString(), "backward");

        EmailDetails emailDetailsList = getEmailDetails(stencilId, emailType);
        if (emailDetailsList == null) {
            log.info("no emailing list found for given stencilId and group");
            return;
        }

        ChannelInfo channelInfo = new ChannelInfo();
        channelInfo.setType(Constants.APPROVAL_CHANNEL_INFO_TYPE);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            /*Parse the json emailing list fetched from db*/
            List<String> toList = objectMapper.readValue(emailDetailsList.getToList(),
                    new TypeReference<List<String>>(){});
            channelInfo.setTo(toList);
            channelInfo.setFrom(fromEmailAddress);
        } catch(IOException ex) {
            log.info("unable to parse the json value of emailing list");
            return;
        }

        channelInfo.setCc(emailDetailsList.getCcList());

        connektPayload.setChannelInfo(channelInfo);
        connektPayload.setContextId(Constants.APPROVAL_CONTEXT_ID);
        connektClient.sendEmail(connektPayload);

    }

    private String getStencilId(String state, boolean forward) {
        String stencilId;
        if (forward) {
            stencilId = stencilConfigModel.getStateStencilMapping().get(state).get("forward");
        } else {
            stencilId = stencilConfigModel.getStateStencilMapping().get(state).get("backward");
        }
        return stencilId;

    }

    private String getEmailType(String state, String group, String type) {
        return state + "_" + group + "_" + type;
    }
}

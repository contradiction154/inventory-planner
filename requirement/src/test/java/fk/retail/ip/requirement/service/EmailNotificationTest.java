package fk.retail.ip.requirement.service;

import fk.retail.ip.email.client.ConnektClient;
import fk.retail.ip.email.internal.enums.EmailParams;
import fk.retail.ip.email.model.ConnektPayload;
import fk.retail.ip.requirement.config.TestDbModule;
import fk.retail.ip.requirement.internal.command.emailHelper.ApprovalEmailHelper;
import fk.retail.ip.requirement.internal.enums.ApprovalEmailParams;
import fk.retail.ip.requirement.internal.enums.RequirementApprovalState;
import fk.retail.ip.requirement.internal.repository.EmailDetailsRepository;
import fk.retail.ip.requirement.model.GroupEmailIds;
import org.jukito.JukitoRunner;
import org.jukito.UseModules;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by agarwal.vaibhav on 26/05/17.
 */
@RunWith(JukitoRunner.class)
@UseModules(TestDbModule.class)
public class EmailNotificationTest {

    @Mock
    EmailDetailsRepository emailDetailsRepository;

    @Captor
    private ArgumentCaptor<ConnektPayload> connektArgumentCaptor;

    @InjectMocks
    private ApprovalEmailHelper approvalEmailHelper;

    @Mock
    ConnektClient connektClient;


    @Test
    public void testEmailNotification() {
        String fromState = RequirementApprovalState.PROPOSED.toString();
        boolean forward = true;
        Mockito.when(emailDetailsRepository.getEmailDetails(Mockito.anyList())).thenReturn(getGroupEmailids());
        approvalEmailHelper.send(getEmailParams(), fromState, forward);
        Mockito.verify(connektClient).sendEmail(connektArgumentCaptor.capture());
        Assert.assertEquals("STNVNFI9I", connektArgumentCaptor.getValue().getStencilId());
        Assert.assertEquals("abc", connektArgumentCaptor.getValue().getChannelInfo().getTo().get(0).getAddress());
        Assert.assertEquals(0, connektArgumentCaptor.getValue().getChannelInfo().getCc().size());
    }

    private List<GroupEmailIds> getGroupEmailids() {
        List<GroupEmailIds> groupEmailIdsList = new ArrayList<>();
        GroupEmailIds ipcPowerbankGroup = new GroupEmailIds();
        ipcPowerbankGroup.setEmailId("{\"list\": [{\"address\":\"abc\", \"name\": \"abc\"}]}");
        ipcPowerbankGroup.setGroup("IPC_PowerBanks");
        GroupEmailIds cdoPowerbankGroup = new GroupEmailIds();
        cdoPowerbankGroup.setEmailId("{\"list\": [{\"address\":\"abc\", \"name\": \"abc\"}]}");
        cdoPowerbankGroup.setGroup("CDO_PowerBanks");
        GroupEmailIds bizfinPowerbankGroup = new GroupEmailIds();
        bizfinPowerbankGroup.setEmailId("{\"list\": [{\"address\":\"abc\", \"name\": \"abc\"}]}");
        bizfinPowerbankGroup.setGroup("BIZFIN_PowerBanks");
        groupEmailIdsList.add(ipcPowerbankGroup);
        groupEmailIdsList.add(cdoPowerbankGroup);
        groupEmailIdsList.add(bizfinPowerbankGroup);
        return groupEmailIdsList;
    }

    private Map<EmailParams, String> getEmailParams() {
        Map<EmailParams, String> emailParams = new HashMap<>();
        emailParams.put(ApprovalEmailParams.GROUPNAME, "PowerBanks");
        emailParams.put(ApprovalEmailParams.USERNAME, "testuser");
        emailParams.put(ApprovalEmailParams.CREATIONDATE, "123");
        return emailParams;
    }

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }
}

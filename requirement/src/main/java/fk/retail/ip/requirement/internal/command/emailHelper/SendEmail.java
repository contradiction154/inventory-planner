package fk.retail.ip.requirement.internal.command.emailHelper;

import fk.retail.ip.email.client.ConnektClient;
import fk.retail.ip.email.internal.enums.EmailParams;
import fk.retail.ip.email.model.EmailDetails;
import fk.retail.ip.requirement.internal.Constants;
import fk.retail.ip.requirement.internal.repository.EmailDetailsRepository;

import java.util.List;
import java.util.Map;

/**
 * Created by agarwal.vaibhav on 12/05/17.
 */
public abstract class SendEmail {
    private EmailDetailsRepository emailDetailsRepository;
    protected ConnektClient connektClient;

    public SendEmail(EmailDetailsRepository emailDetailsRepository, ConnektClient connektClient) {
        this.emailDetailsRepository = emailDetailsRepository;
        this.connektClient = connektClient;
    }

    protected String fromEmailAddress = Constants.FROM_EMAIL_ADDRESS;

    abstract void send(Map<EmailParams, String> params, String state, boolean forward);

    public List<EmailDetails> getEmailDetails(List<String> groupNames) {
        List<EmailDetails> emailDetailsList = emailDetailsRepository.getEmailDetails(groupNames);
        return emailDetailsList;
    }
}
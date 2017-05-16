package fk.retail.ip.requirement.internal.repository;

import fk.retail.ip.email.model.EmailDetails;
import fk.sp.common.extensions.jpa.JpaGenericRepository;

import java.util.List;

/**
 * Created by agarwal.vaibhav on 14/05/17.
 */
public interface EmailDetailsRepository extends JpaGenericRepository<EmailDetails, Long> {
    List<EmailDetails> getEmailDetails(List<String> groupNames);
}

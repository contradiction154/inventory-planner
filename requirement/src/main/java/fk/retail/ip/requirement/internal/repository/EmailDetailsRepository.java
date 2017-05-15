package fk.retail.ip.requirement.internal.repository;

import fk.retail.ip.email.model.EmailDetails;
import fk.sp.common.extensions.jpa.JpaGenericRepository;

/**
 * Created by agarwal.vaibhav on 14/05/17.
 */
public interface EmailDetailsRepository extends JpaGenericRepository<EmailDetails, Long> {
    EmailDetails getEmailDetails(String stencilId, String emailType);
}

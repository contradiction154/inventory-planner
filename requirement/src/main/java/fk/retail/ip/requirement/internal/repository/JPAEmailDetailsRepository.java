package fk.retail.ip.requirement.internal.repository;

import com.google.inject.Inject;
import fk.retail.ip.email.model.EmailDetails;
import fk.sp.common.extensions.jpa.SimpleJpaGenericRepository;

import javax.inject.Provider;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by agarwal.vaibhav on 14/05/17.
 */
public class JPAEmailDetailsRepository extends SimpleJpaGenericRepository<EmailDetails, Long> implements EmailDetailsRepository {

    @Inject
    public JPAEmailDetailsRepository(Provider<EntityManager> entityManagerProvider) {
        super(entityManagerProvider);
    }

    public List<EmailDetails> getEmailDetails(List<String> groupNames) {
        TypedQuery<EmailDetails> query = getEntityManager().createNamedQuery("findEmailDetailsByStencilId", EmailDetails.class);
        query.setParameter("group", groupNames);
        List<EmailDetails> emailDetailsList = query.getResultList();
        return emailDetailsList;
    }
}

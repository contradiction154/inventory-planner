package fk.retail.ip.requirement.internal.repository;

import com.google.inject.Inject;
import fk.retail.ip.requirement.model.GroupEmailIds;
import fk.sp.common.extensions.jpa.SimpleJpaGenericRepository;

import javax.inject.Provider;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by agarwal.vaibhav on 14/05/17.
 */
public class JPAEmailDetailsRepository extends SimpleJpaGenericRepository<GroupEmailIds, Long> implements EmailDetailsRepository {

    @Inject
    public JPAEmailDetailsRepository(Provider<EntityManager> entityManagerProvider) {
        super(entityManagerProvider);
    }

    public List<GroupEmailIds> getEmailDetails(List<String> groupNames) {
        TypedQuery<GroupEmailIds> query = getEntityManager().createNamedQuery("findEmailDetailsByStencilId", GroupEmailIds.class);
        query.setParameter("group", groupNames);
        List<GroupEmailIds> groupEmailIdsList = query.getResultList();
        return groupEmailIdsList;
    }
}

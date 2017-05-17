package fk.retail.ip.requirement.internal.repository;

import fk.retail.ip.requirement.model.GroupEmailIds;
import fk.sp.common.extensions.jpa.JpaGenericRepository;

import java.util.List;

/**
 * Created by agarwal.vaibhav on 14/05/17.
 */
public interface EmailDetailsRepository extends JpaGenericRepository<GroupEmailIds, Long> {
    List<GroupEmailIds> getEmailDetails(List<String> groupNames);
}

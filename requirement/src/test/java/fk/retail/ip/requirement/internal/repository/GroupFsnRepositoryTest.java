package fk.retail.ip.requirement.internal.repository;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import fk.retail.ip.requirement.config.TestModule;
import fk.retail.ip.requirement.internal.entities.Group;
import fk.retail.ip.requirement.internal.entities.GroupFsn;
import fk.sp.common.extensions.jpa.TransactionalJpaRepositoryTest;
import java.util.List;
import org.jukito.JukitoRunner;
import org.jukito.UseModules;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(JukitoRunner.class)
@UseModules(TestModule.class)
public class GroupFsnRepositoryTest extends TransactionalJpaRepositoryTest {

    @Inject
    GroupFsnRepository groupFsnRepository;

    @Test
    public void testFindByFsns() {
        Group group = TestHelper.getGroup("Test Group");
        GroupFsn groupFsn = TestHelper.getGroupFsn("fsn1", group);
        groupFsnRepository.persist(groupFsn);
        List<GroupFsn> groupFsns = groupFsnRepository.findByFsns(Lists.newArrayList("fsn1"));
        Assert.assertEquals(1, groupFsns.size());
        Assert.assertEquals(groupFsn, groupFsns.get(0));
        Assert.assertEquals(group, groupFsns.get(0).getGroup());
    }
}

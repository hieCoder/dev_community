package com.shsoftvina.community.modules.admin.group_noti;

import com.shsoftvina.community.domain.GroupNoti;
import com.shsoftvina.community.domain.enumration.EGroupNoti;
import com.shsoftvina.community.modules.root.group_noti.GroupNotiRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GroupNotiAdminRepository extends GroupNotiRepository {

    @Query("select g from GroupNoti g where g.code in :group")
    List<GroupNoti> findByCodeIn(List<EGroupNoti> group);
}

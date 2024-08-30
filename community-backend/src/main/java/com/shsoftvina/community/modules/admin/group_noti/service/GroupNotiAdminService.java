package com.shsoftvina.community.modules.admin.group_noti.service;

import com.shsoftvina.community.domain.enumration.EGroupNoti;
import com.shsoftvina.community.modules.root.group_noti.service.GroupNotiService;

import java.util.List;

public interface GroupNotiAdminService extends GroupNotiService {

    void updateGroup(List<EGroupNoti> groupNoti);
}

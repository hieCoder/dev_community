package com.shsoftvina.community.modules.root.group_noti.service;

import com.shsoftvina.community.domain.GroupNoti;
import com.shsoftvina.community.domain.enumration.EGroupNoti;

import java.util.List;

public interface GroupNotiEntityService {

    GroupNoti findByCode(EGroupNoti code);
    List<GroupNoti> findByCodes(List<EGroupNoti> codes);
}

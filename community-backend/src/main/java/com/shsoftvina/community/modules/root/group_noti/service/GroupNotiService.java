package com.shsoftvina.community.modules.root.group_noti.service;

import com.shsoftvina.community.domain.enumration.EGroupNoti;

import java.util.List;

public interface GroupNotiService extends GroupNotiEntityService{

    List<EGroupNoti> findAllByUserCurrent();
    boolean isUserHasGroupAccountActivity(String username);
    boolean isUserHasGroupSocical(String username);
}

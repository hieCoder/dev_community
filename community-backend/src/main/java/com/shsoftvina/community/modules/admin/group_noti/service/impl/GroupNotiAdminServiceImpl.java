package com.shsoftvina.community.modules.admin.group_noti.service.impl;

import com.shsoftvina.community.domain.User;
import com.shsoftvina.community.domain.enumration.EGroupNoti;
import com.shsoftvina.community.modules.admin.group_noti.GroupNotiAdminRepository;
import com.shsoftvina.community.modules.admin.group_noti.service.GroupNotiAdminService;
import com.shsoftvina.community.modules.admin.user.service.UserAdminService;
import com.shsoftvina.community.modules.root.group_noti.service.impl.GroupNotiServiceImpl;
import com.shsoftvina.community.security.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class GroupNotiAdminServiceImpl extends GroupNotiServiceImpl implements GroupNotiAdminService {

    @Autowired
    private GroupNotiAdminRepository groupNotiAdminRepository;

    @Autowired
    private UserAdminService userAdminService;

    @Override
    public void updateGroup(List<EGroupNoti> groupNoti) {
        User userCurrent = userAdminService.findByUsername(SecurityUtil.getUsernameCurrent());
        userCurrent.setGroups(groupNotiAdminRepository.findByCodeIn(groupNoti));
        userAdminService.save(userCurrent);
    }
}
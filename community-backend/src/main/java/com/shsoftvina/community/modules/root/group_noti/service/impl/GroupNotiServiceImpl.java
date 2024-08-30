package com.shsoftvina.community.modules.root.group_noti.service.impl;

import com.shsoftvina.community.domain.GroupNoti;
import com.shsoftvina.community.domain.enumration.EGroupNoti;
import com.shsoftvina.community.modules.root.group_noti.GroupNotiRepository;
import com.shsoftvina.community.modules.root.group_noti.service.GroupNotiService;
import com.shsoftvina.community.security.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@Primary
public class GroupNotiServiceImpl extends GroupNotiEntityServiceImpl implements GroupNotiService {

    @Autowired
    private GroupNotiRepository groupNotiRepository;

    @Override
    public List<EGroupNoti> findAllByUserCurrent() {
        return groupNotiRepository.findAllByUser(SecurityUtil.getUsernameCurrent())
                .stream().map(GroupNoti::getCode).toList();
    }

    protected List<EGroupNoti> findAllByUser(String username) {
        return groupNotiRepository.findAllByUser(username)
                .stream().map(GroupNoti::getCode).toList();
    }

    @Override
    public boolean isUserHasGroupAccountActivity(String username) {
        return this.findAllByUser(username).stream().anyMatch(g -> g.equals(EGroupNoti.ACCOUNT_ACTIVITY));
    }

    @Override
    public boolean isUserHasGroupSocical(String username) {
        return this.findAllByUser(username).stream().anyMatch(g -> g.equals(EGroupNoti.SOCIAL_INTERACTIONS));
    }
}

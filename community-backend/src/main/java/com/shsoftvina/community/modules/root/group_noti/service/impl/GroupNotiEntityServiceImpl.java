package com.shsoftvina.community.modules.root.group_noti.service.impl;

import com.shsoftvina.community.domain.GroupNoti;
import com.shsoftvina.community.domain.enumration.EGroupNoti;
import com.shsoftvina.community.exception.BadRequestAlertException;
import com.shsoftvina.community.exception.ErrorEnum;
import com.shsoftvina.community.modules.root.group_noti.GroupNotiRepository;
import com.shsoftvina.community.modules.root.group_noti.service.GroupNotiEntityService;
import com.shsoftvina.community.modules.root.group_noti.service.GroupNotiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class GroupNotiEntityServiceImpl implements GroupNotiEntityService {

    @Autowired
    private GroupNotiRepository groupNotiRepository;

    @Override
    public GroupNoti findByCode(EGroupNoti code) {
        return groupNotiRepository.findByCode(code).orElseThrow(() -> new BadRequestAlertException(ErrorEnum.GROUP_NOTI_NOT_FOUND));
    }

    @Override
    public List<GroupNoti> findByCodes(List<EGroupNoti> codes) {
        return groupNotiRepository.findByCodes(codes);
    }
}

package com.shsoftvina.community.modules.changelog.service.impl;

import com.shsoftvina.community.modules.changelog.ChangelogDevRepository;
import com.shsoftvina.community.modules.changelog.mapper.ChangelogDevResMapper;
import com.shsoftvina.community.modules.changelog.service.ChangelogDevService;
import com.shsoftvina.community.modules.changelog.model.res.ChangelogDevRes;
import com.shsoftvina.community.modules.root.changelog.service.impl.ChangelogServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ChangelogDevServiceImpl extends ChangelogServiceImpl implements ChangelogDevService {

    @Autowired
    private ChangelogDevRepository changelogDevRepository;

    @Autowired
    private ChangelogDevResMapper changelogDevResMapper;

    @Override
    public List<ChangelogDevRes> getChangelog(Long componentId) {
        return changelogDevResMapper.toDto(changelogDevRepository.findAllByComponentId(componentId));
    }
}

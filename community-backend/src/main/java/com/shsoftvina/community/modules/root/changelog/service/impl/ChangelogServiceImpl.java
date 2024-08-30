package com.shsoftvina.community.modules.root.changelog.service.impl;

import com.shsoftvina.community.domain.Changelog;
import com.shsoftvina.community.modules.root.changelog.ChangelogRepository;
import com.shsoftvina.community.modules.root.changelog.service.ChangelogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Primary
public class ChangelogServiceImpl implements ChangelogService {

    @Autowired
    private ChangelogRepository changelogRepository;

    @Override
    public void save(Changelog changelog) {
        changelogRepository.save(changelog);
    }
}

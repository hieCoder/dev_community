package com.shsoftvina.community.modules.changelog.service;

import com.shsoftvina.community.modules.changelog.model.res.ChangelogDevRes;
import com.shsoftvina.community.modules.root.changelog.service.ChangelogService;

import java.util.List;

public interface ChangelogDevService extends ChangelogService {
    List<ChangelogDevRes> getChangelog(Long componentId);
}

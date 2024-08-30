package com.shsoftvina.community.modules.admin.changelog.service;

import com.shsoftvina.community.modules.admin.example.model.req.EditExampleAdminReq;
import com.shsoftvina.community.modules.root.changelog.service.ChangelogService;

import java.util.List;

public interface ChangelogAdminService extends ChangelogService {

    void createChangelog(Long componentId, List<EditExampleAdminReq> examplesReq);
}

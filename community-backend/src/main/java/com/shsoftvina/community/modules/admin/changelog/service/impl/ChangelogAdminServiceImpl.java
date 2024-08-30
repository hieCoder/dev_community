package com.shsoftvina.community.modules.admin.changelog.service.impl;

import com.shsoftvina.community.domain.Changelog;
import com.shsoftvina.community.domain.ExampleChangelog;
import com.shsoftvina.community.modules.admin.changelog.service.ChangelogAdminService;
import com.shsoftvina.community.modules.admin.example.model.req.EditExampleAdminReq;
import com.shsoftvina.community.modules.root.changelog.service.impl.ChangelogServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ChangelogAdminServiceImpl extends ChangelogServiceImpl implements ChangelogAdminService {

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public void createChangelog(Long componentId, List<EditExampleAdminReq> examplesReq) {
        Changelog changelog = new Changelog();
        changelog.setCode(generateCodeChangelog());
        changelog.setComponentId(componentId);

        List<ExampleChangelog> exampleChangelogs = new ArrayList<>();
        for(EditExampleAdminReq req: examplesReq){
            if(!StringUtils.isBlank(req.getCommit())){
                ExampleChangelog exampleChangelog = modelMapper.map(req, ExampleChangelog.class);
                exampleChangelog.setId(null);
                exampleChangelog.setChangelog(changelog);
                exampleChangelogs.add(exampleChangelog);
            }
        }
        changelog.setExamples(exampleChangelogs);
        super.save(changelog);
    }

    private String generateCodeChangelog(){
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyMMdd.HH.mm.ss"));
    }
}
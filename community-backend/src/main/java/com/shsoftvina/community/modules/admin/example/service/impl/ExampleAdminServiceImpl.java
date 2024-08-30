package com.shsoftvina.community.modules.admin.example.service.impl;

import com.shsoftvina.community.domain.Component;
import com.shsoftvina.community.domain.Example;
import com.shsoftvina.community.domain.enumration.EStatus;
import com.shsoftvina.community.exception.BadRequestAlertException;
import com.shsoftvina.community.exception.ErrorEnum;
import com.shsoftvina.community.modules.admin.changelog.service.ChangelogAdminService;
import com.shsoftvina.community.modules.admin.example.mapper.CreateExampleAdminReqMapper;
import com.shsoftvina.community.modules.admin.example.mapper.EditExampleAdminReqMapper;
import com.shsoftvina.community.modules.admin.example.mapper.ExampleAdminResMapper;
import com.shsoftvina.community.modules.admin.example.model.req.CreateExampleAdminReq;
import com.shsoftvina.community.modules.admin.example.model.req.EditExampleAdminReq;
import com.shsoftvina.community.modules.admin.example.model.res.ExampleAdminRes;
import com.shsoftvina.community.modules.admin.example.service.ExampleAdminService;
import com.shsoftvina.community.modules.root.example.service.impl.ExampleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class ExampleAdminServiceImpl extends ExampleServiceImpl implements ExampleAdminService {

    private final String NO_KEYWORD = "";

    @Autowired
    private ExampleAdminResMapper exampleAdminResMapper;

    @Autowired
    private CreateExampleAdminReqMapper createExampleAdminReqMapper;

    @Autowired
    private EditExampleAdminReqMapper editExampleAdminReqMapper;

    @Autowired
    private ChangelogAdminService changelogAdminService;

    @Override
    public List<ExampleAdminRes> findAllByCreatePost(String keyword) {
        return exampleAdminResMapper.toDto(super.findAll(keyword));
    }

    @Override
    public void createExamples(List<CreateExampleAdminReq> req, Component component) {
        List<Example> examples = createExampleAdminReqMapper.toEntity(req);
        examples.stream().peek(e -> {
            e.setComponent(component);
            e.setStatus(EStatus.ACTIVATED);
        }).toList();
        super.saveAll(examples);
    }

    @Override
    public void updateExamples(List<EditExampleAdminReq> examplesReq, Component component) {

        List<Example> examplesEntity = component.getExamples();
        Long componentId = component.getId();

        // handle create example new
        List<EditExampleAdminReq> exampleNews = examplesReq.stream().filter(e -> e.getId() == null).toList();
        if(!exampleNews.isEmpty()) {
            this.updateExampleListNewForEditComponent(exampleNews, component);
        }

        // handle example current
        List<EditExampleAdminReq> exampleCurrents = examplesReq.stream().filter(e -> e.getId() != null).toList();
        if(!exampleCurrents.isEmpty()){

            this.updateExampleListCurrentForEditComponent(componentId, exampleCurrents);

            List<Long> exampleIdsReq = exampleCurrents.stream().map(EditExampleAdminReq::getId).toList();
            List<Long> exampleIdsEntity = examplesEntity.stream().map(Example::getId).toList();
            List<Long> idsInEntityNotInReq = exampleIdsEntity.stream()
                    .filter(id -> !exampleIdsReq.contains(id))
                    .toList();
            if(!idsInEntityNotInReq.isEmpty()){
                this.deleteExampleListCurrentForEditComponent(super.findByIdIn(idsInEntityNotInReq));
            }
        } else {
            this.deleteExampleListCurrentForEditComponent(examplesEntity);
        }
    }

    @Override
    public void checkNewExample(List<CreateExampleAdminReq> req) {
        List<String> newExampleTitles = req.stream().map(CreateExampleAdminReq::getTitle).toList();

        Set<String> allTitleInDB = new HashSet<>(super.findAll(NO_KEYWORD).stream().map(Example::getTitle).toList());
        for (String title : newExampleTitles) {
            if (!allTitleInDB.add(title)) {
                throw new BadRequestAlertException(ErrorEnum.DUPLICATE_DATA);
            }
        }
    }

    @Override
    public List<String> getAllName() {
        return super.findAll(NO_KEYWORD).stream()
                .map(Example::getTitle).toList();
    }

    @Async
    public void deleteExampleListCurrentForEditComponent(List<Example> examples) {
        super.deleteAll(examples);
    }

    @Async
    public void updateExampleListCurrentForEditComponent(Long componentId, List<EditExampleAdminReq> exampleCurrents) {
        List<Long> exampleIdsCurrent = exampleCurrents.stream().map(EditExampleAdminReq::getId).toList();
        List<Example> oldExamples = super.findByIdIn(exampleIdsCurrent);

        oldExamples.forEach(example ->
                exampleCurrents.stream()
                        .filter(e -> e.getId().equals(example.getId()))
                        .findFirst()
                        .ifPresent(editReq -> editExampleAdminReqMapper.partialUpdate(example, editReq))
        );
        super.saveAll(oldExamples);

        this.createChangelog(componentId, exampleCurrents);
    }

    @Async
    public void createChangelog(Long componentId, List<EditExampleAdminReq> examplesReq) {
        examplesReq = examplesReq.stream().filter(e -> e.getCommit()!=null).collect(Collectors.toList());
        if(!examplesReq.isEmpty()){
            changelogAdminService.createChangelog(componentId, examplesReq);
        }
    }

    @Async
    public void updateExampleListNewForEditComponent(List<EditExampleAdminReq> exampleNews, Component component) {
        List<Example> examples = editExampleAdminReqMapper.toEntity(exampleNews);

        examples.forEach(e -> {
            e.setComponent(component);
            e.setStatus(EStatus.ACTIVATED);
        });

        super.saveAll(examples);
    }
}

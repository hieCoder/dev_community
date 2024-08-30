package com.shsoftvina.community.modules.admin.example.service;

import com.shsoftvina.community.domain.Component;
import com.shsoftvina.community.domain.Example;
import com.shsoftvina.community.modules.admin.example.model.req.CreateExampleAdminReq;
import com.shsoftvina.community.modules.admin.example.model.req.EditExampleAdminReq;
import com.shsoftvina.community.modules.admin.example.model.res.ExampleAdminRes;
import com.shsoftvina.community.modules.root.example.service.ExampleService;

import java.util.List;

public interface ExampleAdminService extends ExampleService {

    List<ExampleAdminRes> findAllByCreatePost(String keyword);
    void createExamples(List<CreateExampleAdminReq> req, Component component);
    void updateExamples(List<EditExampleAdminReq> examples, Component component);
    void checkNewExample(List<CreateExampleAdminReq> req);
    List<String> getAllName();
}

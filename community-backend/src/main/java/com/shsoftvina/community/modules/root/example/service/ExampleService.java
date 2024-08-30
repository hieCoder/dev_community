package com.shsoftvina.community.modules.root.example.service;

import com.shsoftvina.community.domain.Example;

import java.util.List;

public interface ExampleService extends ExampleEntityService{

    void deleteAll(List<Example> examples);
}

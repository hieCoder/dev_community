package com.shsoftvina.community.modules.root.example.service;

import com.shsoftvina.community.domain.Example;

import java.util.List;

public interface ExampleEntityService {

    List<Example> findAll(String keyword);
    List<Example> findByIdIn(List<Long> ids);
    Example findById(Long id);
    Example save(Example example);
    List<Example> saveAll(List<Example> exampleList);
}

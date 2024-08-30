package com.shsoftvina.community.modules.root.example.service.impl;

import com.shsoftvina.community.domain.Example;
import com.shsoftvina.community.exception.BadRequestAlertException;
import com.shsoftvina.community.exception.ErrorEnum;
import com.shsoftvina.community.modules.root.example.ExampleRepository;
import com.shsoftvina.community.modules.root.example.service.ExampleEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ExampleEntityServiceImpl implements ExampleEntityService {

    @Autowired
    private ExampleRepository exampleRepository;

    @Override
    public List<Example> findAll(String keyword) {
        return exampleRepository.findAll(keyword);
    }

    @Override
    public List<Example> findByIdIn(List<Long> ids) {
        return exampleRepository.findByIdIn(ids);
    }

    @Override
    public Example findById(Long id) {
        return exampleRepository.findById(id).orElseThrow(() -> new BadRequestAlertException(ErrorEnum.EXAMPLE_NOT_FOUND));
    }

    @Override
    public Example save(Example example) {
        return exampleRepository.save(example);
    }

    @Override
    public List<Example> saveAll(List<Example> exampleList) {
        return exampleRepository.saveAll(exampleList);
    }
}

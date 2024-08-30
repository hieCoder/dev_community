package com.shsoftvina.community.modules.root.component_category.service.impl;

import com.shsoftvina.community.domain.ComponentCategory;
import com.shsoftvina.community.exception.BadRequestAlertException;
import com.shsoftvina.community.exception.ErrorEnum;
import com.shsoftvina.community.modules.root.component_category.ComponentCategoryRepository;
import com.shsoftvina.community.modules.root.component_category.service.ComponentCategoryEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ComponentCategoryEntityServiceImpl implements ComponentCategoryEntityService {

    @Autowired
    private ComponentCategoryRepository componentCategoryRepository;

    @Override
    public ComponentCategory findById(Long id) {
        return componentCategoryRepository.findById(id).orElseThrow(() -> new BadRequestAlertException(ErrorEnum.COMPONENT_CATEGORY_NOT_FOUND));
    }

    @Override
    public ComponentCategory save(ComponentCategory category) {
        return componentCategoryRepository.save(category);
    }

    @Override
    public List<ComponentCategory> findAllByRole(String username) {
        return componentCategoryRepository.findAllByRole(username);
    }
}
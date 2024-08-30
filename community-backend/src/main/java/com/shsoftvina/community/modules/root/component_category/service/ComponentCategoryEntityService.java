package com.shsoftvina.community.modules.root.component_category.service;

import com.shsoftvina.community.domain.ComponentCategory;

import java.util.List;

public interface ComponentCategoryEntityService {

    ComponentCategory findById(Long id);
    ComponentCategory save(ComponentCategory category);
    List<ComponentCategory> findAllByRole(String username);
}

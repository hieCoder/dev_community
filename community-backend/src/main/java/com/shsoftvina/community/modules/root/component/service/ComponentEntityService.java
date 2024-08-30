package com.shsoftvina.community.modules.root.component.service;

import com.shsoftvina.community.domain.Component;

import java.util.List;

public interface ComponentEntityService {

    List<Component> findAll(String keyword);
    Component save(Component component);
    Component findById(Long id);
}

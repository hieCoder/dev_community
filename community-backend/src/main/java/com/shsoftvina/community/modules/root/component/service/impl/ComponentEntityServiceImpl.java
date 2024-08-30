package com.shsoftvina.community.modules.root.component.service.impl;

import com.shsoftvina.community.domain.Component;
import com.shsoftvina.community.exception.BadRequestAlertException;
import com.shsoftvina.community.exception.ErrorEnum;
import com.shsoftvina.community.modules.root.component.ComponentRepository;
import com.shsoftvina.community.modules.root.component.service.ComponentEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ComponentEntityServiceImpl implements ComponentEntityService {

    @Autowired
    private ComponentRepository componentRepository;

    @Override
    public List<Component> findAll(String keyword) {
        return componentRepository.findAll(keyword);
    }

    @Override
    public Component save(Component component) {
        return componentRepository.save(component);
    }

    @Override
    public Component findById(Long id) {
        return componentRepository.findById(id).orElseThrow(() -> new BadRequestAlertException(ErrorEnum.COMPONENT_NOT_FOUND));
    }
}

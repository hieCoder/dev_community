package com.shsoftvina.community.mapper.search;

import com.shsoftvina.community.domain.Component;
import com.shsoftvina.community.mapper.EntityMapper;
import com.shsoftvina.community.model.search.res.ComponentSearchRes;
import com.shsoftvina.community.model.search.res.PostSearchRes;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ComponentSearchResMapper extends EntityMapper<ComponentSearchRes, Component> {

    @AfterMapping
    default void enrichResWithSearch(Component component, @MappingTarget PostSearchRes res) {
        res.setHref("{uri}/"+ component.getId());
    }
}
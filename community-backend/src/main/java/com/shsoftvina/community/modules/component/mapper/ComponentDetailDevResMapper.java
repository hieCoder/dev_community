package com.shsoftvina.community.modules.component.mapper;

import com.shsoftvina.community.domain.Component;
import com.shsoftvina.community.mapper.EntityMapper;
import com.shsoftvina.community.modules.component.model.res.ComponentDetailDevRes;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ComponentDetailDevResMapper extends EntityMapper<ComponentDetailDevRes, Component> {
}

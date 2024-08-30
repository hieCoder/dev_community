package com.shsoftvina.community.modules.admin.component.mapper;

import com.shsoftvina.community.domain.Component;
import com.shsoftvina.community.mapper.EntityMapper;
import com.shsoftvina.community.modules.admin.component.model.res.ComponentDetailAdminRes;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ComponentDetailAdminResMapper extends EntityMapper<ComponentDetailAdminRes, Component> {
}

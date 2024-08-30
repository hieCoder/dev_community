package com.shsoftvina.community.modules.admin.component.mapper;

import com.shsoftvina.community.domain.Component;
import com.shsoftvina.community.domain.enumration.EStatus;
import com.shsoftvina.community.mapper.EntityMapper;
import com.shsoftvina.community.modules.admin.component.model.req.CreateComponentAdminReq;
import com.shsoftvina.community.modules.admin.component.model.req.EditComponentAdminReq;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface EditComponentAdminMapper extends EntityMapper<EditComponentAdminReq, Component> {

}
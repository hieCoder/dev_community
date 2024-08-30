package com.shsoftvina.community.modules.admin.component_category.mapper;

import com.shsoftvina.community.domain.ComponentCategory;
import com.shsoftvina.community.mapper.EntityMapper;
import com.shsoftvina.community.modules.admin.component_category.model.req.CategoryAddAdminReq;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryAddAdminReqMapper extends EntityMapper<CategoryAddAdminReq, ComponentCategory> {
}

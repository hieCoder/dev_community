package com.shsoftvina.community.modules.admin.component_category.mapper;

import com.shsoftvina.community.domain.ComponentCategory;
import com.shsoftvina.community.mapper.EntityMapper;
import com.shsoftvina.community.modules.admin.component_category.model.req.CategoryAddAdminReq;
import com.shsoftvina.community.modules.admin.component_category.model.req.CategoryEditAdminReq;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryEditAdminReqMapper extends EntityMapper<CategoryEditAdminReq, ComponentCategory> {
}

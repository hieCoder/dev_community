package com.shsoftvina.community.modules.admin.component_category.mapper;

import com.shsoftvina.community.domain.ComponentCategory;
import com.shsoftvina.community.mapper.EntityMapper;
import com.shsoftvina.community.modules.admin.component_category.model.res.CategoryAdminRes;
import com.shsoftvina.community.modules.admin.component_category.model.res.CategoryDetailAdminRes;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryAdminResMapper extends EntityMapper<CategoryAdminRes, ComponentCategory> {
}

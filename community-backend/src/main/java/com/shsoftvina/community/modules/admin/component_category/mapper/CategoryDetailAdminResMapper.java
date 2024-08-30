package com.shsoftvina.community.modules.admin.component_category.mapper;

import com.shsoftvina.community.domain.ComponentCategory;
import com.shsoftvina.community.domain.Post;
import com.shsoftvina.community.mapper.EntityMapper;
import com.shsoftvina.community.modules.admin.component_category.model.res.CategoryDetailAdminRes;
import com.shsoftvina.community.modules.admin.post.model.res.PostDetailAdminRes;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryDetailAdminResMapper extends EntityMapper<CategoryDetailAdminRes, ComponentCategory> {
}

package com.shsoftvina.community.modules.admin.post.mapper;

import com.shsoftvina.community.domain.Post;
import com.shsoftvina.community.mapper.EntityMapper;
import com.shsoftvina.community.modules.admin.post.model.res.PostAdminRes;
import com.shsoftvina.community.modules.post.model.PostRes;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PostResAdminMapper extends EntityMapper<PostAdminRes, Post> {
}

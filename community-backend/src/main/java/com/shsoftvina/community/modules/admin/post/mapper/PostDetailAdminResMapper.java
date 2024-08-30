package com.shsoftvina.community.modules.admin.post.mapper;

import com.shsoftvina.community.domain.Post;
import com.shsoftvina.community.mapper.EntityMapper;
import com.shsoftvina.community.modules.admin.post.model.res.PostAdminRes;
import com.shsoftvina.community.modules.admin.post.model.res.PostDetailAdminRes;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PostDetailAdminResMapper extends EntityMapper<PostDetailAdminRes, Post> {
}

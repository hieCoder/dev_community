package com.shsoftvina.community.modules.post.mapper;

import com.shsoftvina.community.domain.Post;
import com.shsoftvina.community.mapper.EntityMapper;
import com.shsoftvina.community.modules.post.model.PostDetailRes;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PostDetailResMapper extends EntityMapper<PostDetailRes, Post> {
}

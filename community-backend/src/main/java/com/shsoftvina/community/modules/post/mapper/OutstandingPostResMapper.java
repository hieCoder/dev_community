package com.shsoftvina.community.modules.post.mapper;

import com.shsoftvina.community.domain.Post;
import com.shsoftvina.community.mapper.EntityMapper;
import com.shsoftvina.community.modules.post.model.OutstandingPostRes;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OutstandingPostResMapper extends EntityMapper<OutstandingPostRes, Post> {
}

package com.shsoftvina.community.mapper.search;

import com.shsoftvina.community.domain.Post;
import com.shsoftvina.community.mapper.EntityMapper;
import com.shsoftvina.community.model.search.res.PostSearchRes;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PostSearchResMapper extends EntityMapper<PostSearchRes, Post> {

    @AfterMapping
    default void enrichResWithSearch(Post post, @MappingTarget PostSearchRes res) {
        res.setDescription(post.getContent());
        res.setHref("{uri}/"+ post.getId());
    }
}

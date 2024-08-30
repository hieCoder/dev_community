package com.shsoftvina.community.modules.comment.mapper;


import com.shsoftvina.community.domain.Comment;
import com.shsoftvina.community.mapper.EntityMapper;
import com.shsoftvina.community.modules.comment.model.CommentDevRes;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {})
public interface CommentResMapper extends EntityMapper<CommentDevRes, Comment> {

	@Mapping(source = "parent.id", target = "parentId")
    CommentDevRes toDto(Comment entity);
}

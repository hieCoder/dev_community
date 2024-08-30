package com.shsoftvina.community.modules.comment.mapper;


import com.shsoftvina.community.domain.Comment;
import com.shsoftvina.community.mapper.EntityMapper;
import com.shsoftvina.community.modules.comment.model.CreateCommentDevReq;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface CreateCommentReqMapper extends EntityMapper<CreateCommentDevReq, Comment> {
}

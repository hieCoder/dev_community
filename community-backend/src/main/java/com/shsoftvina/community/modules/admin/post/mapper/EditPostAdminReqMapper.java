package com.shsoftvina.community.modules.admin.post.mapper;

import com.shsoftvina.community.domain.Post;
import com.shsoftvina.community.mapper.EntityMapper;
import com.shsoftvina.community.modules.admin.post.model.req.CreatePostAdminReq;
import com.shsoftvina.community.modules.admin.post.model.req.EditPostAdminReq;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EditPostAdminReqMapper extends EntityMapper<EditPostAdminReq, Post> {
}
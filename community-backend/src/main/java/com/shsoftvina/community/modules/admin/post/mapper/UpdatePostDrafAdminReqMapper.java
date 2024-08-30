package com.shsoftvina.community.modules.admin.post.mapper;

import com.shsoftvina.community.domain.Post;
import com.shsoftvina.community.mapper.EntityMapper;
import com.shsoftvina.community.modules.admin.post.model.req.UpdateDrafPostAdminReq;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UpdatePostDrafAdminReqMapper extends EntityMapper<UpdateDrafPostAdminReq, Post> {
}
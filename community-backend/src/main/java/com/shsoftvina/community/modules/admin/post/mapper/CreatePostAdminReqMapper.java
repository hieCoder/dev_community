package com.shsoftvina.community.modules.admin.post.mapper;

import com.shsoftvina.community.domain.Post;
import com.shsoftvina.community.domain.enumration.EStatus;
import com.shsoftvina.community.mapper.EntityMapper;
import com.shsoftvina.community.modules.admin.post.model.req.CreatePostAdminReq;
import com.shsoftvina.community.utils.DateUtils;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;

@Mapper(componentModel = "spring")
public interface CreatePostAdminReqMapper extends EntityMapper<CreatePostAdminReq, Post> {
}
package com.shsoftvina.community.modules.admin.user.mapper;

import com.shsoftvina.community.domain.User;
import com.shsoftvina.community.mapper.EntityMapper;
import com.shsoftvina.community.modules.admin.user.model.req.EditUserAdminReq;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EditUserAdminReqMapper extends EntityMapper<EditUserAdminReq, User> {
}
package com.shsoftvina.community.modules.admin.user.mapper;

import com.shsoftvina.community.domain.User;
import com.shsoftvina.community.mapper.EntityMapper;
import com.shsoftvina.community.modules.admin.user.model.res.UserAdminRes;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserAdminResMapper extends EntityMapper<UserAdminRes, User> {
}

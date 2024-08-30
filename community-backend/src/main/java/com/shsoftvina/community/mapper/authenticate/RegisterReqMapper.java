package com.shsoftvina.community.mapper.authenticate;

import com.shsoftvina.community.domain.User;
import com.shsoftvina.community.mapper.EntityMapper;
import com.shsoftvina.community.model.authenticate.register.RegisterReq;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RegisterReqMapper extends EntityMapper<RegisterReq, User> {
}
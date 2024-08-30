package com.shsoftvina.community.modules.admin.example.mapper;

import com.shsoftvina.community.domain.Example;
import com.shsoftvina.community.domain.enumration.EStatus;
import com.shsoftvina.community.mapper.EntityMapper;
import com.shsoftvina.community.modules.admin.example.model.req.CreateExampleAdminReq;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CreateExampleAdminReqMapper extends EntityMapper<CreateExampleAdminReq, Example> {
}
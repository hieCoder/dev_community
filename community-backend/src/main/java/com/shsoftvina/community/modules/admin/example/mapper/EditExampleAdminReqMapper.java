package com.shsoftvina.community.modules.admin.example.mapper;

import com.shsoftvina.community.domain.Example;
import com.shsoftvina.community.mapper.EntityMapper;
import com.shsoftvina.community.modules.admin.example.model.req.CreateExampleAdminReq;
import com.shsoftvina.community.modules.admin.example.model.req.EditExampleAdminReq;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EditExampleAdminReqMapper extends EntityMapper<EditExampleAdminReq, Example> {
}
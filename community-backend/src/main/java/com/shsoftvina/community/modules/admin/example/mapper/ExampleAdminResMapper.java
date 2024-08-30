package com.shsoftvina.community.modules.admin.example.mapper;

import com.shsoftvina.community.domain.Example;
import com.shsoftvina.community.mapper.EntityMapper;
import com.shsoftvina.community.modules.admin.example.model.res.ExampleAdminRes;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ExampleAdminResMapper extends EntityMapper<ExampleAdminRes, Example>{
}
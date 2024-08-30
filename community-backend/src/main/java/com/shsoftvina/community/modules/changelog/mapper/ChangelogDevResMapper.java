package com.shsoftvina.community.modules.changelog.mapper;

import com.shsoftvina.community.domain.Changelog;
import com.shsoftvina.community.mapper.EntityMapper;
import com.shsoftvina.community.modules.changelog.model.res.ChangelogDevRes;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ChangelogDevResMapper extends EntityMapper<ChangelogDevRes, Changelog> {
}

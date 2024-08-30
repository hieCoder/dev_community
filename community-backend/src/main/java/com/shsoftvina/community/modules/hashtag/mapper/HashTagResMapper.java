package com.shsoftvina.community.modules.hashtag.mapper;

import com.shsoftvina.community.domain.HashTag;
import com.shsoftvina.community.mapper.EntityMapper;
import com.shsoftvina.community.modules.hashtag.model.HashTagRes;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface HashTagResMapper extends EntityMapper<HashTagRes, HashTag> {
}
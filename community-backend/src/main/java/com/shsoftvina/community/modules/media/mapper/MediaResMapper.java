package com.shsoftvina.community.modules.media.mapper;


import com.shsoftvina.community.domain.Media;
import com.shsoftvina.community.mapper.EntityMapper;
import com.shsoftvina.community.modules.media.model.MediaRes;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface MediaResMapper extends EntityMapper<MediaRes, Media> {
}

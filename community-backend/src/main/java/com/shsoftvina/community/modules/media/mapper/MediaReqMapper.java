package com.shsoftvina.community.modules.media.mapper;


import com.shsoftvina.community.domain.Media;
import com.shsoftvina.community.mapper.EntityMapper;
import com.shsoftvina.community.modules.media.model.MediaReq;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface MediaReqMapper extends EntityMapper<MediaReq, Media> {
}

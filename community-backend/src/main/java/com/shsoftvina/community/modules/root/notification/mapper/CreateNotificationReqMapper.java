package com.shsoftvina.community.modules.root.notification.mapper;


import com.shsoftvina.community.domain.Notification;
import com.shsoftvina.community.mapper.EntityMapper;
import com.shsoftvina.community.modules.root.notification.model.req.CreateNotificationReq;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface CreateNotificationReqMapper extends EntityMapper<CreateNotificationReq, Notification> {
}

package com.shsoftvina.community.modules.admin.notification.mapper;

import com.shsoftvina.community.domain.Notification;
import com.shsoftvina.community.mapper.EntityMapper;
import com.shsoftvina.community.modules.admin.notification.model.res.NotificationAdminRes;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NotificationAdminResMapper extends EntityMapper<NotificationAdminRes, Notification> {
}

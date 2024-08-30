package com.shsoftvina.community.modules.root.notification.service;

import com.shsoftvina.community.domain.Notification;

public interface NotificationEntityService{

    Notification save(Notification notification);
    Notification findById(Long id);
}

package com.shsoftvina.community.modules.root.notification.service;

import com.shsoftvina.community.modules.root.notification.model.req.CreateNotificationReq;

public interface NotificationService extends NotificationEntityService{

    void createNotification(CreateNotificationReq req);
}

package com.shsoftvina.community.modules.admin.notification.service;

import com.shsoftvina.community.modules.admin.notification.model.res.NotificationAdminRes;
import com.shsoftvina.community.modules.root.notification.service.NotificationService;

import java.util.List;

public interface NotificationAdminService extends NotificationService {
    List<NotificationAdminRes> getAllNotification();
    long getCountAllNotificationUnread();
    void updateReadAll();
    void updateUnread(Long id);
}

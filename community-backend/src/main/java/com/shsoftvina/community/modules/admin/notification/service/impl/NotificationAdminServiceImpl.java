package com.shsoftvina.community.modules.admin.notification.service.impl;

import com.shsoftvina.community.domain.Notification;
import com.shsoftvina.community.modules.admin.notification.NotificationAdminRepository;
import com.shsoftvina.community.modules.admin.notification.mapper.NotificationAdminResMapper;
import com.shsoftvina.community.modules.admin.notification.model.res.NotificationAdminRes;
import com.shsoftvina.community.modules.admin.notification.service.NotificationAdminService;
import com.shsoftvina.community.modules.root.notification.service.impl.NotificationServiceImpl;
import com.shsoftvina.community.security.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class NotificationAdminServiceImpl extends NotificationServiceImpl implements NotificationAdminService {

    @Autowired
    private NotificationAdminRepository notificationAdminRepository;

    @Autowired
    private NotificationAdminResMapper notificationAdminResMapper;

    @Override
    public List<NotificationAdminRes> getAllNotification() {
        String username = SecurityUtil.getUsernameCurrent();
        return notificationAdminResMapper.toDto(notificationAdminRepository.getAllNotification(username));
    }

    @Override
    public long getCountAllNotificationUnread() {
        String username = SecurityUtil.getUsernameCurrent();
        return notificationAdminRepository.getCountAllNotificationUnread(username);
    }

    @Override
    public void updateReadAll() {
        String username = SecurityUtil.getUsernameCurrent();
        List<Notification> notifications = notificationAdminRepository.getAllNotification(username);
        notifications.forEach(n -> n.setRead(true));
        notificationAdminRepository.saveAll(notifications);
    }

    @Override
    public void updateUnread(Long id) {

        Notification notification = super.findById(id);
        notification.setRead(true);
        super.save(notification);
    }
}

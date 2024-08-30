package com.shsoftvina.community.modules.root.notification.service.impl;

import com.shsoftvina.community.domain.Notification;
import com.shsoftvina.community.exception.BadRequestAlertException;
import com.shsoftvina.community.exception.ErrorEnum;
import com.shsoftvina.community.modules.root.notification.NotificationRepository;
import com.shsoftvina.community.modules.root.notification.service.NotificationEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class NotificationEntityServiceImpl implements NotificationEntityService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Override
    public Notification save(Notification notification) {
        return notificationRepository.save(notification);
    }

    @Override
    public Notification findById(Long id) {
        return notificationRepository.findById(id).orElseThrow(() -> new BadRequestAlertException(ErrorEnum.NOTIFICATION_NOT_FOUND));
    }
}

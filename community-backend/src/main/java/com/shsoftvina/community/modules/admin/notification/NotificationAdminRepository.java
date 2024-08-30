package com.shsoftvina.community.modules.admin.notification;

import com.shsoftvina.community.domain.Notification;
import com.shsoftvina.community.modules.root.notification.NotificationRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NotificationAdminRepository extends NotificationRepository {

    @Query("SELECT n FROM Notification n WHERE n.user.username = :username order by n.id desc")
    List<Notification> getAllNotification(String username);

    @Query("SELECT COUNT(n) FROM Notification n WHERE n.user.username = :username and n.isRead <> true")
    long getCountAllNotificationUnread(String username);
}

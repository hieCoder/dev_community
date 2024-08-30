package com.shsoftvina.community.modules.admin.notification;

import com.shsoftvina.community.modules.admin.notification.model.res.NotificationAdminRes;
import com.shsoftvina.community.modules.admin.notification.service.NotificationAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/notifications")
public class NotificationAdminApi {

    @Autowired
    private NotificationAdminService notificationAdminService;

    @GetMapping
    public ResponseEntity<List<NotificationAdminRes>> getAllNotification(){
        return ResponseEntity.ok(notificationAdminService.getAllNotification());
    }

    @GetMapping("/count/unread")
    public ResponseEntity<Long> getCountAllNotificationUnread(){
        return ResponseEntity.ok(notificationAdminService.getCountAllNotificationUnread());
    }

    @PutMapping("/update-read-all")
    public ResponseEntity<Void> updateReadAll() {
        notificationAdminService.updateReadAll();
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/update-unread/{id}")
    public ResponseEntity<Void> updateUnread(@PathVariable Long id) {
        notificationAdminService.updateUnread(id);
        return ResponseEntity.noContent().build();
    }
}

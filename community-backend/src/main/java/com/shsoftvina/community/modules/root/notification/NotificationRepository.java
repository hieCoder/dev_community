package com.shsoftvina.community.modules.root.notification;

import com.shsoftvina.community.domain.Notification;
import com.shsoftvina.community.domain.enumration.ENotificationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    @Query("SELECT n FROM Notification n WHERE n.id = :id")
    Optional<Notification> findById(Long id);

    @Query("SELECT n FROM Notification n WHERE n.eventId = :eventId and n.notificationType = :notificationType")
    List<Notification> findAllInEvent(Long eventId, ENotificationType notificationType);
}

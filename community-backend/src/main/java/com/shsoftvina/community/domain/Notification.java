package com.shsoftvina.community.domain;

import com.shsoftvina.community.domain.enumration.EActionNotification;
import com.shsoftvina.community.domain.enumration.ENotificationType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "notification")
@Setter
@Getter
public class Notification extends AbstractAuditingEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "event_id")
    private Long eventId;

    @Column(name = "event_type")
    @Enumerated(EnumType.STRING)
    private ENotificationType notificationType;

    @Enumerated(EnumType.STRING)
    @Column(name = "action")
    private EActionNotification action;

    @Column(name = "href")
    private String href;

    @Column(name = "is_read")
    private boolean isRead;

    @Column(name = "metadata")
    private String metadata;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne
    private GroupNoti group;
}
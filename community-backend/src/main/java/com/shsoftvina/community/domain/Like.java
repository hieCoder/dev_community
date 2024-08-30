package com.shsoftvina.community.domain;

import com.shsoftvina.community.domain.enumration.ELikeType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "like")
@Getter
@Setter
public class Like extends AbstractAuditingEntity<Long> {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "event_id", nullable = false)
	private Long eventId;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "event_type")
	private ELikeType eventType;

	@ManyToOne(fetch = FetchType.LAZY)
	private Ip ip;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private User user;
}
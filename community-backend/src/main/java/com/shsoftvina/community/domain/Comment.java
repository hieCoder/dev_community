package com.shsoftvina.community.domain;

import com.shsoftvina.community.domain.enumration.ECommentType;
import com.shsoftvina.community.domain.enumration.EStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "comment")
@Getter
@Setter
public class Comment extends AbstractAuditingEntity<Long> {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "event_id", nullable = false)
	private Long eventId;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "event_type")
	private ECommentType eventType;
	
	@Column(name = "content", nullable = false)
	private String content;

	@Column(name = "nickName")
	private String nickName;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "status")
	private EStatus status;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Comment parent;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private User user;
}

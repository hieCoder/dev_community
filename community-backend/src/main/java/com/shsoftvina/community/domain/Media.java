package com.shsoftvina.community.domain;

import com.shsoftvina.community.domain.enumration.EMediaType;
import com.shsoftvina.community.domain.enumration.EStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;


@Entity
@Table(name = "media")
@Data
public class Media extends AbstractAuditingEntity<Long> {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "path")
	private String path;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "status")
	private EStatus status;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "type_media")
	private EMediaType mediaType;
	
	@Column(name = "extension")
	private String extension;
	
	@Column(name = "version")
	private Double version;
	
	@Column(name = "original_name")
	private String originalName;
	
	@Column(name = "file_size", precision = 21, scale = 2)
	private BigDecimal fileSize;
}

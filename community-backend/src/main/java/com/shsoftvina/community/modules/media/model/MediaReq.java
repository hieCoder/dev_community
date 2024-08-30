package com.shsoftvina.community.modules.media.model;

import com.shsoftvina.community.domain.enumration.EMediaType;
import com.shsoftvina.community.domain.enumration.EStatus;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class MediaReq {
	

	private Long id;

	private String name;

	private String path;

	private EStatus status;

	private EMediaType mediaType;

	private String extension;

	private Double version;

	private String originalName;

	private BigDecimal fileSize;

}

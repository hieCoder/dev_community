package com.shsoftvina.community.modules.media.model;

import com.shsoftvina.community.domain.enumration.EMediaType;
import lombok.Data;

@Data
public class MediaRes {
	
	private String path;
	
	private EMediaType mediaType;
	
	private String originalName;
}

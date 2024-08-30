package com.shsoftvina.community.modules.media.service;

import com.shsoftvina.community.modules.media.model.MediaRes;
import com.shsoftvina.community.modules.media.model.ResourceSerializable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MediaService {
	
	List<MediaRes> uploadFileResponse(MultipartFile[] uploadingFiles);
    ResourceSerializable getFile(String folder, String mediaType, String imageName, Integer width);
}

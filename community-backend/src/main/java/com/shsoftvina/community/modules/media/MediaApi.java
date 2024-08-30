package com.shsoftvina.community.modules.media;

import com.shsoftvina.community.modules.media.model.MediaRes;
import com.shsoftvina.community.modules.media.model.ResourceSerializable;
import com.shsoftvina.community.modules.media.service.MediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/media")
public class MediaApi {

	@Autowired
	private MediaService mediaService;
	
	@PostMapping("/upload")
	public ResponseEntity<List<MediaRes>> upload(
			@RequestPart(name = "files") MultipartFile[] uploadingFiles) {
		List<MediaRes> list = mediaService.uploadFileResponse(uploadingFiles);
		return ResponseEntity.ok().body(list);
	}

	@GetMapping(value = "/{folder}/{mediaType}/{imageName}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public ResponseEntity<ResourceSerializable> getFile(@PathVariable(name = "folder") String folder,
														@PathVariable(name = "mediaType") String mediaType,
														@PathVariable(name = "imageName") String imageName,
														@RequestParam(name = "width", required = false) Integer width) {
		ResourceSerializable resource = mediaService.getFile(folder, mediaType, imageName, width);
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);
	}
}

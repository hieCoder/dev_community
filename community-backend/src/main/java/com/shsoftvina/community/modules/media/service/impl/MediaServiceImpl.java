package com.shsoftvina.community.modules.media.service.impl;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.MetadataException;
import com.drew.metadata.exif.ExifDirectoryBase;
import com.drew.metadata.exif.ExifIFD0Directory;
import com.shsoftvina.community.domain.Media;
import com.shsoftvina.community.domain.enumration.EMediaType;
import com.shsoftvina.community.domain.enumration.EStatus;
import com.shsoftvina.community.exception.BadRequestAlertException;
import com.shsoftvina.community.exception.ErrorEnum;
import com.shsoftvina.community.modules.media.MediaConstant;
import com.shsoftvina.community.modules.media.MediaRepository;
import com.shsoftvina.community.modules.media.service.MediaService;
import com.shsoftvina.community.modules.media.mapper.MediaReqMapper;
import com.shsoftvina.community.modules.media.mapper.MediaResMapper;
import com.shsoftvina.community.modules.media.model.MediaReq;
import com.shsoftvina.community.modules.media.model.MediaRes;
import com.shsoftvina.community.modules.media.model.ResourceSerializable;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
public class MediaServiceImpl implements MediaService {

	@Value("${application.path.upload-dir}")
	private String uploadDir;

	@Autowired
	private MediaResMapper mediaResMapper;

	@Autowired
	private MediaReqMapper mediaReqMapper;

	@Autowired
	private MediaRepository mediaRepository;

	public static final Integer MAX_WIDTH = 1280;
	public static final Integer MIN_WIDTH = 50;

	@Override
	public List<MediaRes> uploadFileResponse(MultipartFile[] uploadingFiles) {
		log.debug("upload files");
		return this.uploadFiles(uploadingFiles).stream().map(mediaResMapper::toDto)
				.collect(Collectors.toList());
	}

	@Override
	public ResourceSerializable getFile(String folder, String mediaType, String fileName, Integer width) {
		log.debug("Read file on server");
		try {
			File file = null;
			file = new File(uploadDir + folder + File.separator + mediaType, fileName);
			if (file.exists()) {
				String extension = this.getFileExtension(fileName);
				if (MediaConstant.IMAGE_TYPE_ALLOW.contains(extension.toLowerCase())) {
					return this.findImage(folder,mediaType, fileName, width);
				} else {
					return new ResourceSerializable(file.toURI());
				}
			} else {
				return new ResourceSerializable(
						new File(uploadDir + "/init/noImage.png").toURI());
			}
		} catch (IOException e) {
			log.debug("Load file error: ", e.getMessage());
		}
		return null;
	}

	private ResourceSerializable findImage(String folder,String mediaType , String imageName, Integer width) {
		log.debug("Read file on server");
		try {
			File file = new File(uploadDir + folder + File.separator + mediaType, imageName);
			if (file != null && file.exists()) {
				if (width != null) {
					Float rWidth = Float.valueOf(width) / 100;
					int scaleWith = (Math.round(rWidth) * 100);
					if (scaleWith == 0) {
						scaleWith = 100;
					} else if (scaleWith > MAX_WIDTH) {
						scaleWith = MAX_WIDTH;
					} else if (scaleWith < MIN_WIDTH) {
						scaleWith = MIN_WIDTH;
					}
					File resize = new File(uploadDir + folder + "/" + scaleWith, imageName);
					if (resize.exists()) {
						log.debug("exist image path: " + resize.toURI());
						return new ResourceSerializable(resize.toURI());
					} else {
						Path path = this.resize(file.getAbsolutePath(), resize.getAbsolutePath(), scaleWith);
						return new ResourceSerializable(path.toUri());
					}
				} else {
					return new ResourceSerializable(file.toURI());
				}
			} else {
				return new ResourceSerializable(new File(uploadDir + "/init/noImage.png").toURI());
			}
		} catch (IOException | ImageProcessingException | MetadataException e) {
			log.debug("Load Image error: ", e.getMessage());
		}
		return null;
	}

	private Path resize(String inputImagePath, String outputImagePath, int scaledWidth)
			throws IOException, ImageProcessingException, MetadataException {
		BufferedImage inputImage = ImageIO.read(new File(inputImagePath));
		if (inputImage == null) return Path.of(inputImagePath);
		Metadata metadata = ImageMetadataReader.readMetadata(new File(inputImagePath));
		int orientation = 1;
		for (Directory directory : metadata.getDirectoriesOfType(ExifIFD0Directory.class)) {
			if (directory.containsTag(ExifDirectoryBase.TAG_ORIENTATION)) {
				orientation = directory.getInt(ExifDirectoryBase.TAG_ORIENTATION);
				break;
			}
		}
		inputImage = this.rotateImage(inputImage, orientation);
		BufferedImage scaledImage = Scalr.resize(inputImage, Scalr.Method.AUTOMATIC, Scalr.Mode.FIT_TO_WIDTH, scaledWidth);
		File outputFile = new File(outputImagePath);
		FileUtils.forceMkdirParent(outputFile);
		ImageIO.write(scaledImage, "png", outputFile);
		return outputFile.toPath();
	}

	private BufferedImage rotateImage(BufferedImage image, int orientation) {
		BufferedImage rotatedImage = image;
		switch (orientation) {
			case 3:
				rotatedImage = Scalr.rotate(image, Scalr.Rotation.CW_180);
				break;
			case 6:
				rotatedImage = Scalr.rotate(image, Scalr.Rotation.CW_90);
				break;
			case 8:
				rotatedImage = Scalr.rotate(image, Scalr.Rotation.CW_270);
				break;
			default:
				return rotatedImage;
		}
		return rotatedImage;
	}

	private List<Media> uploadFiles(MultipartFile[] uploadingFiles) {
		log.debug("Upload files by uploadingFiles");
		List<Media> list = new ArrayList<>();
		for (MultipartFile uploadedFile : uploadingFiles) {
			log.debug("Upload files by uploadedFile= {}", uploadedFile);
			String fileName = uploadedFile.getOriginalFilename();
			Media media = null;
			String extension = this.getFileExtension(fileName);
			if (MediaConstant.IMAGE_TYPE_ALLOW.contains(extension.toLowerCase())) {
				media = this.uploadFile(uploadedFile, EMediaType.IMAGE);
			} else if (MediaConstant.DOCUMENT_TYPE_ALLOW.contains(extension.toLowerCase())) {
				media = this.uploadFile(uploadedFile, EMediaType.DOCUMENT);
			} else if (MediaConstant.VIDEO_TYPE_ALLOW.contains(extension.toLowerCase())) {
				media = this.uploadFile(uploadedFile, EMediaType.VIDEO);
			} else if (MediaConstant.AUDIO_TYPE_ALLOW.contains(extension.toLowerCase())) {
				media = this.uploadFile(uploadedFile, EMediaType.AUDIO);
			} else if (MediaConstant.FILE_TYPE_ALLOW.contains(extension.toLowerCase())) {
				media = this.uploadFile(uploadedFile, EMediaType.FILE);
			} else
				throw new BadRequestAlertException(ErrorEnum.MEDIA_TYPE_NOT_SUPPORT);
			if (media != null)
				list.add(media);
		}
		return list;
	}

	private String getFileExtension(String name) {
		log.debug("Get files extension by uploadedFile= {}", name);
		return FilenameUtils.getExtension(name);
	}

	private Media uploadFile(MultipartFile uploadedFile, EMediaType mediaType) {
		String originalName = uploadedFile.getOriginalFilename();
		log.debug("Upload files by uploadedFile= {}", originalName);
		SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd");
		String uuid = UUID.randomUUID().toString();
		String extension = getFileExtension(originalName);
		String fileName = uuid + "." + extension;
		log.debug("Generate file name: {}", fileName);
		BigDecimal fileSize = BigDecimal.valueOf(uploadedFile.getSize());
		log.debug("File size: {}", fileSize);
		String filePath = fm.format(new Date()) + File.separator + mediaType.toString().toLowerCase() + File.separator
				+ fileName;
		log.debug("Upload file to path: {}", filePath);
		log.debug("Root folder: {}", uploadDir);
		String filName = uploadDir + filePath;
		log.debug("File path on server: {}", filName);
		try {
			FileUtils.copyInputStreamToFile(uploadedFile.getInputStream(), new File(filName));
		} catch (IOException e) {
			log.error(e.getMessage());
		}
		return this.insertInfo(filePath, fileName, originalName, mediaType, extension, fileSize);
	}

	private Media insertInfo(String filePath, String name, String originalName, EMediaType mediaType, String extension,
							 BigDecimal fileSize) {
		log.debug("Upload files by uploadedFile= {}", filePath);
		MediaReq file = new MediaReq();
		file.setName(name);
		file.setPath(filePath);
		file.setExtension(extension);
		file.setOriginalName(originalName);
		file.setFileSize(fileSize);
		file.setStatus(EStatus.ACTIVATED);
		file.setMediaType(mediaType);
		log.debug("Upload success: !");
		return this.saveFile(file);
	}

	private Media saveFile(MediaReq mediaReq) {
		log.debug("Request to save Media : {}", mediaReq);
		Media media = mediaReqMapper.toEntity(mediaReq);
		media = mediaRepository.save(media);
		return media;
	}
}

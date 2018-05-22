/*
 * Copyright 2005-2015 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package com.huitu.sjclub.service.impl;

import com.huitu.sjclub.FileType;
import com.huitu.sjclub.Setting;
import com.huitu.sjclub.plugin.StoragePlugin;
import com.huitu.sjclub.service.FileService;
import com.huitu.sjclub.service.PluginService;
import com.huitu.sjclub.util.FreeMarkerUtils;
import com.huitu.sjclub.util.SystemUtils;
import freemarker.template.TemplateException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service("fileServiceImpl")
public class FileServiceImpl implements FileService, ServletContextAware {

	private ServletContext servletContext;

	@Resource(name = "taskExecutor")
	private TaskExecutor taskExecutor;
	@Resource(name = "pluginServiceImpl")
	private PluginService pluginService;

	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	private void addUploadTask(final StoragePlugin storagePlugin, final String path, final File sjclub, final String contentType) {
		taskExecutor.execute(new Runnable() {
			public void run() {
				upload(storagePlugin, path, sjclub, contentType);
			}
		});
	}

	private void upload(StoragePlugin storagePlugin, String path, File sjclub, String contentType) {
		Assert.notNull(storagePlugin);
		Assert.hasText(path);
		Assert.notNull(sjclub);
		Assert.hasText(contentType);

		try {
			storagePlugin.upload(path, sjclub, contentType);
		} finally {
			FileUtils.deleteQuietly(sjclub);
		}
	}

	public boolean isValid(FileType fileType, MultipartFile multipartFile) {
		Assert.notNull(fileType);
		Assert.notNull(multipartFile);
		Assert.state(!multipartFile.isEmpty());

		Setting setting = SystemUtils.getSetting();
//		if (setting.getUploadMaxSize() != null && setting.getUploadMaxSize() != 0 && multipartFile.getSize() > setting.getUploadMaxSize() * 1024L * 1024L) {
//			return false;
//		}
		String[] uploadExtensions;
		switch (fileType) {
		case media:
			uploadExtensions = setting.getUploadMediaExtensions();
			break;
		case file:
			uploadExtensions = setting.getUploadFileExtensions();
			break;
		default:
			uploadExtensions = setting.getUploadImageExtensions();
			break;
		}
		if (ArrayUtils.isNotEmpty(uploadExtensions)) {
			return FilenameUtils.isExtension(multipartFile.getOriginalFilename(), uploadExtensions);
		}
		return false;
	}

	public String upload(FileType fileType, MultipartFile multipartFile, boolean async) {
		Assert.notNull(fileType);
		Assert.notNull(multipartFile);
		Assert.state(!multipartFile.isEmpty());

		Setting setting = SystemUtils.getSetting();
		String uploadPath;
		switch (fileType) {
		case media:
			uploadPath = setting.getMediaUploadPath();
			break;
		case file:
			uploadPath = setting.getFileUploadPath();
			break;
		default:
			uploadPath = setting.getImageUploadPath();
			break;
		}
		try {
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("uuid", UUID.randomUUID().toString());
			String path = FreeMarkerUtils.process(uploadPath, model);
			String destPath = path + UUID.randomUUID() + "." + FilenameUtils.getExtension(multipartFile.getOriginalFilename());
			for (StoragePlugin storagePlugin : pluginService.getStoragePlugins(true)) {
				File tempFile = new File(FileUtils.getTempDirectory(), UUID.randomUUID() + ".tmp");
				multipartFile.transferTo(tempFile);
				String contentType = multipartFile.getContentType();
				if (async) {
					addUploadTask(storagePlugin, destPath, tempFile, contentType);
				} else {
					upload(storagePlugin, destPath, tempFile, contentType);
				}
				return storagePlugin.getUrl(destPath);
			}
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		} catch (TemplateException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		return null;
	}

	public String upload(FileType sjclubType, MultipartFile multipartFile) {
		Assert.notNull(sjclubType);
		Assert.notNull(multipartFile);
		Assert.state(!multipartFile.isEmpty());

		return upload(sjclubType, multipartFile, true);
	}

	public String uploadLocal(FileType sjclubType, MultipartFile multipartFile) {
		Assert.notNull(sjclubType);
		Assert.notNull(multipartFile);
		Assert.state(!multipartFile.isEmpty());

		Setting setting = SystemUtils.getSetting();
		String uploadPath;
		switch (sjclubType) {
		case media:
			uploadPath = setting.getMediaUploadPath();
			break;
		case file:
			uploadPath = setting.getFileUploadPath();
			break;
		default:
			uploadPath = setting.getImageUploadPath();
			break;
		}
		try {
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("uuid", UUID.randomUUID().toString());
			String path = FreeMarkerUtils.process(uploadPath, model);
			String destPath = path + UUID.randomUUID() + "." + FilenameUtils.getExtension(multipartFile.getOriginalFilename());
			File destFile = new File(servletContext.getRealPath(destPath));
			new File(path).mkdirs();
			multipartFile.transferTo(destFile);
			return destPath;
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		} catch (TemplateException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

}
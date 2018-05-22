/*
 * Copyright 2005-2015 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package com.huitu.sjclub.service;

import com.huitu.sjclub.FileType;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {

	boolean isValid(FileType fileType, MultipartFile multipartFile);

	String upload(FileType sjclubType, MultipartFile multipartFile, boolean async);

	String upload(FileType sjclubType, MultipartFile multipartFile);

	String uploadLocal(FileType sjclubType, MultipartFile multipartFile);

}
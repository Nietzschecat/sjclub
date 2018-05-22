/*
 * Copyright 2005-2015 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package com.huitu.sjclub.controller.common;

import com.huitu.sjclub.FileType;
import com.huitu.sjclub.Message;
import com.huitu.sjclub.controller.admin.BaseController;
import com.huitu.sjclub.service.FileService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Controller("adminFileController")
@RequestMapping("/admin/file")
public class FileController extends BaseController {

	@Resource(name = "fileServiceImpl")
	private FileService fileService;

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public @ResponseBody
    Map<String, Object> upload( MultipartFile file) {
		Map<String, Object> data = new HashMap<String, Object>();
		String url = fileService.upload(FileType.file, file, false);
		if (StringUtils.isEmpty(url)) {
			data.put("message", Message.warn("admin.upload.error"));
			data.put("state", message("admin.upload.error"));
			return data;
		}
		data.put("message", SUCCESS_MESSAGE);
		data.put("state", "SUCCESS");
		data.put("url", url);
		data.put("code", 200);
		data.put("name", file.getOriginalFilename());
		return data;
	}

}
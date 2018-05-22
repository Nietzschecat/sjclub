/*
 * Copyright 2005-2015 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package com.huitu.sjclub.service.impl;

import com.huitu.sjclub.Setting;
import com.huitu.sjclub.service.CaptchaService;
import com.huitu.sjclub.util.SystemUtils;
import com.octo.captcha.service.CaptchaServiceException;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.awt.image.BufferedImage;

@Service("captchaServiceImpl")
public class CaptchaServiceImpl implements CaptchaService {

	@Resource(name = "imageCaptchaService")
	private com.octo.captcha.service.CaptchaService imageCaptchaService;

	public BufferedImage buildImage(String captchaId) {
		Assert.hasText(captchaId);

		return (BufferedImage) imageCaptchaService.getChallengeForID(captchaId);
	}

	public boolean isValid(Setting.CaptchaType captchaType, String captchaId, String captcha) {
		Assert.notNull(captchaType);

		Setting setting = SystemUtils.getSetting();
		if (!ArrayUtils.contains(setting.getCaptchaTypes(), captchaType)) {
			return true;
		}
		if (StringUtils.isEmpty(captchaId) || StringUtils.isEmpty(captcha)) {
			return false;
		}
		try {
			return imageCaptchaService.validateResponseForID(captchaId, captcha.toUpperCase());
		} catch (CaptchaServiceException e) {
			return false;
		}
	}

}
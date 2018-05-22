/*
 * Copyright 2005-2015 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package com.huitu.sjclub.service;

import com.huitu.sjclub.Setting;

import java.awt.image.BufferedImage;

public interface CaptchaService {

	BufferedImage buildImage(String captchaId);

	boolean isValid(Setting.CaptchaType captchaType, String captchaId, String captcha);

}
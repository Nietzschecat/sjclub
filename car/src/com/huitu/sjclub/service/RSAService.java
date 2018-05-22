/*
 * Copyright 2005-2015 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package com.huitu.sjclub.service;

import javax.servlet.http.HttpServletRequest;
import java.security.interfaces.RSAPublicKey;

public interface RSAService {

	RSAPublicKey generateKey(HttpServletRequest request);

	void removePrivateKey(HttpServletRequest request);

	String decryptParameter(String name, HttpServletRequest request);

}
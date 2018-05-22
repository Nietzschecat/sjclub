/*
 * Copyright 2005-2015 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package com.huitu.sjclub.controller.admin;

import com.huitu.sjclub.Message;
import com.huitu.sjclub.Setting;
import com.huitu.sjclub.entity.User;
import com.huitu.sjclub.service.RSAService;
import com.huitu.sjclub.util.SystemUtils;
import com.huitu.sjclub.service.UserService;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.security.interfaces.RSAPublicKey;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Controller("adminLoginController")
@RequestMapping("/admin/login")
public class LoginController extends BaseController {

	@Resource(name = "rsaServiceImpl")
	private RSAService rsaService;
	@Resource(name = "userServiceImpl")
	private UserService userService;

	@RequestMapping
	public @ResponseBody Map<String,String>
	index(HttpServletRequest request, ModelMap model) {
		Map<String,String> map=new HashMap<String, String>();
		if (userService.isAuthenticated()) {
			map.put("code","ok");
			return map;
		}
		map.put("code","pasIsError");
		return map;
	}

}
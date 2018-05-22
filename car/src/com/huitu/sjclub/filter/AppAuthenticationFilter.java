/*
 * Copyright 2005-2015 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package com.huitu.sjclub.filter;

import com.huitu.sjclub.service.UserService;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


public class AppAuthenticationFilter extends FormAuthenticationFilter {




	@Resource(name = "userServiceImpl")
	private UserService userService;
	

	@Override
	protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		
		System.out.println(request.getParameter("username"));
		System.out.println(request.getParameter("password"));
		
		
		/*if (StringUtils.equalsIgnoreCase(request.getHeader("X-Requested-With"), "XMLHttpRequest")) {
			response.addHeader("loginStatus", "accessDenied");
			response.sendError(HttpServletResponse.SC_FORBIDDEN);
			return false;
		}
		String loginToken = WebUtils.getCookie(request, Admin.LOGIN_TOKEN_COOKIE_NAME);
		if (!StringUtils.equalsIgnoreCase(loginToken, adminService.getLoginToken())) {
			WebUtils.issueRedirect(request, response, "/");
			return false;
		}*/
		return super.onAccessDenied(request, response);
	}

	@Override
	protected boolean onLoginSuccess(org.apache.shiro.authc.AuthenticationToken token, Subject subject, ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
		Session session = subject.getSession();
	
		Map<Object, Object> attributes = new HashMap<Object, Object>();
		Collection<Object> keys = session.getAttributeKeys();
		for (Object key : keys) {
			attributes.put(key, session.getAttribute(key));
		}
		session.stop();
		session = subject.getSession();
		for (Map.Entry<Object, Object> entry : attributes.entrySet()) {
			session.setAttribute(entry.getKey(), entry.getValue());
		}
		
		
		
		
		
		
		return super.onLoginSuccess(token, subject, servletRequest, servletResponse);
	}


}
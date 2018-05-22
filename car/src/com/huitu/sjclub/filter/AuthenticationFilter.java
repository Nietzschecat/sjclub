/*
 * Copyright 2005-2015 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package com.huitu.sjclub.filter;

import com.huitu.sjclub.AuthenticationToken;
import com.huitu.sjclub.service.RSAService;
import com.huitu.sjclub.service.UserService;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class AuthenticationFilter extends FormAuthenticationFilter {

	private static final String DEFAULT_EN_PASSWORD_PARAM = "enPassword";

	private static final String DEFAULT_CAPTCHA_ID_PARAM = "captchaId";

	private static final String DEFAULT_CAPTCHA_PARAM = "captcha";

	private String enPasswordParam = DEFAULT_EN_PASSWORD_PARAM;

	private String captchaIdParam = DEFAULT_CAPTCHA_ID_PARAM;

	private String captchaParam = DEFAULT_CAPTCHA_PARAM;

	@Resource(name = "userServiceImpl")
	private UserService userService;
	@Resource(name = "rsaServiceImpl")
	private RSAService rsaService;

	@Override
	protected org.apache.shiro.authc.AuthenticationToken createToken(ServletRequest servletRequest, ServletResponse servletResponse) {
	
		
		String username = getUsername(servletRequest);
		String password = getPassword(servletRequest);
		String captchaId = getCaptchaId(servletRequest);
		String captcha = getCaptcha(servletRequest);
		boolean rememberMe = isRememberMe(servletRequest);
		String host = getHost(servletRequest);
		
		
		
				
			
		
		return new AuthenticationToken(username, password, captchaId, captcha, rememberMe, host);
	}

	@Override
	protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		
		System.out.println("nanme:"+request.getParameter("username"));
		System.out.println("password:"+request.getParameter("password"));
		
		
	/*	if (StringUtils.equalsIgnoreCase(request.getHeader("X-Requested-With"), "XMLHttpRequest")) {
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


	
	
	/*@Override
	protected String getPassword(ServletRequest servletRequest) {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		
		String password = rsaService.decryptParameter(enPasswordParam, request);
		rsaService.removePrivateKey(request);
		
		return password;
		
	}*/

	protected String getCaptchaId(ServletRequest servletRequest) {
		String captchaId = WebUtils.getCleanParam(servletRequest, captchaIdParam);
		if (captchaId == null) {
			captchaId = ((HttpServletRequest) servletRequest).getSession().getId();
		}
		return captchaId;
	}

	protected String getCaptcha(ServletRequest servletRequest) {
		return WebUtils.getCleanParam(servletRequest, captchaParam);
	}

	public String getEnPasswordParam() {
		return enPasswordParam;
	}

	public void setEnPasswordParam(String enPasswordParam) {
		this.enPasswordParam = enPasswordParam;
	}

	public String getCaptchaIdParam() {
		return captchaIdParam;
	}

	public void setCaptchaIdParam(String captchaIdParam) {
		this.captchaIdParam = captchaIdParam;
	}

	public String getCaptchaParam() {
		return captchaParam;
	}

	public void setCaptchaParam(String captchaParam) {
		this.captchaParam = captchaParam;
	}

}
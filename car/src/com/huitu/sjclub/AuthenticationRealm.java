/*
 * Copyright 2005-2015 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package com.huitu.sjclub;

import com.huitu.sjclub.entity.User;
import com.huitu.sjclub.service.CaptchaService;
import com.huitu.sjclub.service.UserService;
import com.huitu.sjclub.util.SystemUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Set;

public class AuthenticationRealm extends AuthorizingRealm {

	@Resource(name = "captchaServiceImpl")
	private CaptchaService captchaService;
	@Resource(name = "userServiceImpl")
	private UserService userService;

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(org.apache.shiro.authc.AuthenticationToken token) {
		
		String username=null;
		String password =null;
		String captchaId=null;
		String captcha =null;
		String ip=null;
		
		//mobile login
		if(token.getClass()== UsernamePasswordToken.class ){
			UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
			username = usernamePasswordToken.getUsername();
			password = new String(usernamePasswordToken.getPassword());
			
		}
		
		if(token.getClass()== AuthenticationToken.class){
			AuthenticationToken authenticationToken = (AuthenticationToken) token;
			username = authenticationToken.getUsername();
			password = new String(authenticationToken.getPassword());
			captchaId = authenticationToken.getCaptchaId();
			captcha = authenticationToken.getCaptcha();
			ip = authenticationToken.getHost();

			/*if (!captchaService.isValid(Setting.CaptchaType.adminLogin, captchaId, captcha)) {
				throw new IncorrectCaptchaException();
			}*/
			
		}
			

		
		if (username != null && password != null) {
			User user = userService.findByUsername(username);
			if (user == null) {
				throw new UnknownAccountException();
			}
			/*if (!user.getIsEnabled()) {
				throw new DisabledAccountException();
			}*/
			Setting setting = SystemUtils.getSetting();

			if (!password.equals(user.getPassword())) {
				int loginFailureCount = user.getLoginFailureCount() + 1;

				user.setLoginFailureCount(loginFailureCount);
				userService.update(user);
				throw new IncorrectCredentialsException();
			}
			user.setLoginIp(ip);
			user.setLoginDate(new Date());
			user.setLoginFailureCount(0);
			userService.update(user);
			return new SimpleAuthenticationInfo(new Principal(user.getId(), username), password, getName());
		}
		throw new UnknownAccountException();
	}

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
		Principal principal = (Principal) principalCollection.fromRealm(getName()).iterator().next();
		
		if (principal != null) {
			
			SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();		
			
			List<String> authorities = userService.findAuthorities(principal.getId());
			Set<String> roles = userService.findRoles(principal.getId());
			
			if (authorities != null) {
				authorizationInfo.addStringPermissions(authorities);
	
			}
			if (roles != null) {
				authorizationInfo.addRoles(roles);
			}
			
			return authorizationInfo;
		}
		return null;
	}

}
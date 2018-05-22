/*
 * Copyright 2005-2015 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package com.huitu.sjclub.service;

import com.huitu.sjclub.entity.User;

import java.util.List;
import java.util.Set;

public interface UserService extends BaseService<User, Long> {

	boolean usernameExists(String username);

	User findByUsername(String username);

	List<String> findAuthorities(Long id);

	boolean isAuthenticated();

	User getCurrent();

	String getCurrentUsername();

	String getLoginToken();

	Set<String> findRoles(Long id);

	void setDefaultUser(String userName,String password);

	boolean isSystem(User user);

}
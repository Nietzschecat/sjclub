/*
 * Copyright 2005-2015 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package com.huitu.sjclub.service.impl;

import com.huitu.sjclub.Principal;
import com.huitu.sjclub.dao.UserDao;
import com.huitu.sjclub.entity.Department;
import com.huitu.sjclub.entity.PluginConfig;
import com.huitu.sjclub.entity.User;
import com.huitu.sjclub.entity.Role;
import com.huitu.sjclub.service.*;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

@Service("userServiceImpl")
public class UserServiceImpl extends BaseServiceImpl<User, Long> implements UserService {

	@Resource(name = "userDaoImpl")
	private UserDao userDao;

	@Resource(name = "departmentServiceImpl")
	private DepartmentService departmentService;

	@Resource(name = "pluginConfigServiceImpl")
	private PluginConfigService pluginConfigService;

	@Resource(name = "roleServiceImpl")
	private RoleService roleService;

	@Transactional(readOnly = true)
	public boolean usernameExists(String username) {
		return userDao.usernameExists(username);
	}

	@Transactional(readOnly = true)
	public User findByUsername(String username) {
		return userDao.findByUsername(username);
	}

	@Transactional(readOnly = true)
	public List<String> findAuthorities(Long id) {
		List<String> authorities = new ArrayList<String>();
		User admin = userDao.find(id);
		if (admin != null && admin.getRoles() != null) {
			for (Role role : admin.getRoles()) {
				if (role != null && role.getAuthorities() != null) {
					authorities.addAll(role.getAuthorities());
				}
			}
		}
		return authorities;
	}

	@Transactional(readOnly = true)
	public boolean isAuthenticated() {
		Subject subject = SecurityUtils.getSubject();
		if (subject != null) {
			return subject.isAuthenticated();
		}
		return false;
	}

	@Transactional(readOnly = true)
	public User getCurrent() {
		Subject subject = SecurityUtils.getSubject();
		if (subject != null) {
			Principal principal = (Principal) subject.getPrincipal();
			if (principal != null) {
				return userDao.find(principal.getId());
			}
		}
		return null;
	}

	@Transactional(readOnly = true)
	public String getCurrentUsername() {
		Subject subject = SecurityUtils.getSubject();
		if (subject != null) {
			Principal principal = (Principal) subject.getPrincipal();
			if (principal != null) {
				return principal.getUsername();
			}
		}
		return null;
	}

	@Transactional(readOnly = true)
	@Cacheable(value = "loginToken")
	public String getLoginToken() {
		return DigestUtils.md5Hex(UUID.randomUUID() + RandomStringUtils.randomAlphabetic(30));
	}

	@Override
	@Transactional
	@CacheEvict(value = "authorization", allEntries = true)
	public User save(User user) {
		return super.save(user);
	}

	@Override
	@Transactional
	@CacheEvict(value = "authorization", allEntries = true)
	public User update(User admin) {
		return super.update(admin);
	}

	@Override
	@Transactional
	@CacheEvict(value = "authorization", allEntries = true)
	public User update(User admin, String... ignoreProperties) {
		return super.update(admin, ignoreProperties);
	}

	@Override
	@Transactional
	@CacheEvict(value = "authorization", allEntries = true)
	public void delete(Long id) {
		super.delete(id);
	}

	@Override
	@Transactional
	@CacheEvict(value = "authorization", allEntries = true)
	public void delete(Long... ids) {
		super.delete(ids);
	}

	@Override
	@Transactional
	@CacheEvict(value = "authorization", allEntries = true)
	public void delete(User admin) {
		super.delete(admin);
	}

	@Override
	public Set<String> findRoles(Long id) {
		User admin = userDao.find(id);
		if (admin != null && admin.getRoles() != null) {
			Set<String> roles = new HashSet<String>();
			for (Role role : admin.getRoles()) {
				if (role != null ) {
					roles.add(role.getName());
				}
			}
			return roles;
			
		}
		return null;
	}

	//是否是系统的
	@Override
	public boolean isSystem(User user) {
		Set<Role> roles=user.getRoles();
		for(Role role:roles){
			if(role.getIsSystem()){
				return true;
			}
		}
		return false;
	}

	//设置初始化管理员账号
	@Override
	@Transactional
	public void setDefaultUser(String loginName,String password) {
		/*Department department=new Department();
		department.setIntroduction("管理员的部门，非正常部门");
		department.setName("非正常部门");
		departmentService.save(department);*/

		String adminAuthorities="sjclub:admin";//管理员
		Role role=new Role();
		role.setName("超级管理员");
		List<String> authorities=new ArrayList<String>();
		authorities.add(adminAuthorities);
		role.setAuthorities(authorities);
		role.setDescription("超级管理员");
		role.setIsSystem(true);
		roleService.save(role);



		Set<Role> roles=new HashSet<Role>();
		roles.add(role);
		User user=new User();
		user.setUserName("超级管理员");
		user.setLoginName(loginName);
		user.setPassword(password);
		user.setBirthDate(new Date());
		user.setClassName("班级");
		user.setLoginDate(new Date());
		user.setLoginFailureCount(0);
		user.setLoginIp("127.0.0.1");
		user.setSchool("学校");
		user.setSex("0");
		user.setSg("1.80米");
		user.setRoles(roles);
		userDao.persist(user);
		//保存文件上传插件
		PluginConfig pluginConfig=new PluginConfig();
		pluginConfig.setIsEnabled(true);
		pluginConfig.setPluginId("localStoragePlugin");
		pluginConfigService.save(pluginConfig);
		//设置默认的角色（社团管理员,社团普通成员，非社团成员）

		String userAuthorities="sjclub:user";//
		String visitorsAuthorities="sjclub:visitors";//
		String managerAuthoritie="sjclub:manager";//


		Role userRole=new Role();
		List<String> userauthorities=new ArrayList<String>();
		userauthorities.add(userAuthorities);
		userRole.setAuthorities(userauthorities);
		userRole.setName("普通成员");
		userRole.setDescription("普通成员");
		userRole.setIsSystem(false);
		roleService.save(userRole);


		Role managerRole=new Role();
		List<String> managerAuthorities=new ArrayList<String>();
		managerAuthorities.add(managerAuthoritie);
		managerRole.setAuthorities(managerAuthorities);
		managerRole.setName("社团管理员");
		managerRole.setDescription("社团管理员");
		managerRole.setIsSystem(false);
		roleService.save(managerRole);

		Role visitorsRole=new Role();
		List<String> visitorsauthorities=new ArrayList<String>();
		visitorsauthorities.add(visitorsAuthorities);
		visitorsRole.setAuthorities(visitorsauthorities);
		visitorsRole.setDescription("未加入社团成员");
		visitorsRole.setIsSystem(false);
		visitorsRole.setName("非社团成员");
		roleService.save(visitorsRole);

	}




}
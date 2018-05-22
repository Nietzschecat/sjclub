/*
 * Copyright 2005-2015 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package com.huitu.sjclub.service.impl;

import com.huitu.sjclub.dao.LogDao;
import com.huitu.sjclub.entity.Log;
import com.huitu.sjclub.service.LogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("logServiceImpl")
public class LogServiceImpl extends BaseServiceImpl<Log, Long> implements LogService {

	@Resource(name = "logDaoImpl")
	private LogDao logDao;

	public void clear() {
		logDao.removeAll();
	}

}
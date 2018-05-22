/*
 * Copyright 2005-2015 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package com.huitu.sjclub.dao;

import com.huitu.sjclub.entity.Log;

public interface LogDao extends BaseDao<Log, Long> {

	void removeAll();

}
/*
 * Copyright 2005-2015 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package com.huitu.sjclub.service;

public interface CacheService {

	String getDiskStorePath();

	int getCacheSize();

	void clear();

}
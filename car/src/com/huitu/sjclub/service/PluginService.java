/*
 * Copyright 2005-2015 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package com.huitu.sjclub.service;

import com.huitu.sjclub.plugin.StoragePlugin;

import java.util.List;

public interface PluginService {

	List<StoragePlugin> getStoragePlugins();

	List<StoragePlugin> getStoragePlugins(boolean isEnabled);

	StoragePlugin getStoragePlugin(String id);


}
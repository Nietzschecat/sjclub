/*
 * Copyright 2005-2015 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package com.huitu.sjclub.service.impl;

import com.huitu.sjclub.plugin.StoragePlugin;
import com.huitu.sjclub.service.PluginService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service("pluginServiceImpl")
public class PluginServiceImpl implements PluginService {

	@Resource
	private List<StoragePlugin> storagePlugins = new ArrayList<StoragePlugin>();

	@Resource
	private Map<String, StoragePlugin> storagePluginMap = new HashMap<String, StoragePlugin>();



	public List<StoragePlugin> getStoragePlugins() {
		Collections.sort(storagePlugins);
		return storagePlugins;
	}




	public List<StoragePlugin> getStoragePlugins(final boolean isEnabled) {
		List<StoragePlugin> result = new ArrayList<StoragePlugin>();
		CollectionUtils.select(storagePlugins, new Predicate() {
			public boolean evaluate(Object object) {
				StoragePlugin storagePlugin = (StoragePlugin) object;
				return storagePlugin.getIsEnabled() == isEnabled;
			}
		}, result);
		Collections.sort(result);
		return result;
	}



	public StoragePlugin getStoragePlugin(String id) {
		return storagePluginMap.get(id);
	}


}
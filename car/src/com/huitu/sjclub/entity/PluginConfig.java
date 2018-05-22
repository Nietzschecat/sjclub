/*
 * Copyright 2005-2015 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package com.huitu.sjclub.entity;

import com.huitu.sjclub.BaseAttributeConverter;
import org.apache.commons.lang.StringUtils;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "xx_plugin_config")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "seq_plugin_config")
public class PluginConfig extends OrderEntity<Long> {

	private static final long serialVersionUID = -1016006814132982656L;

	private String pluginId;

	private Boolean isEnabled;

	private Map<String, String> attributes = new HashMap<String, String>();

	@Column(nullable = false, updatable = false, unique = true)
	public String getPluginId() {
		return pluginId;
	}

	public void setPluginId(String pluginId) {
		this.pluginId = pluginId;
	}

	@Column(nullable = false)
	public Boolean getIsEnabled() {
		return isEnabled;
	}

	public void setIsEnabled(Boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	@Column(length = 4000)
	@Convert(converter = MapConverter.class)
	public Map<String, String> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<String, String> attributes) {
		this.attributes = attributes;
	}

	@Transient
	public String getAttribute(String name) {
		if (StringUtils.isEmpty(name)) {
			return null;
		}
		return getAttributes() != null ? getAttributes().get(name) : null;
	}

	@Converter
	public static class MapConverter extends BaseAttributeConverter<Map<String, String>> implements AttributeConverter<Object, String> {
	}

}

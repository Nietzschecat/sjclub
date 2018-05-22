/*
 * Copyright 2005-2015 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package com.huitu.sjclub.template.directive;

import com.huitu.sjclub.Filter;
import com.huitu.sjclub.Order;
import com.huitu.sjclub.util.FreeMarkerUtils;
import freemarker.core.Environment;
import freemarker.template.*;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class BaseDirective implements TemplateDirectiveModel {

	private static final String USE_CACHE_PARAMETER_NAME = "useCache";

	private static final String ID_PARAMETER_NAME = "id";

	private static final String COUNT_PARAMETER_NAME = "count";

	private static final String ORDER_BY_PARAMETER_NAME = "orderBy";

	private static final String ORDER_BY_ITEM_SEPARATOR = "\\s*,\\s*";

	private static final String ORDER_BY_FIELD_SEPARATOR = "\\s+";

	protected boolean useCache(Environment env, Map<String, TemplateModel> params) throws TemplateModelException {
		Boolean useCache = FreeMarkerUtils.getParameter(USE_CACHE_PARAMETER_NAME, Boolean.class, params);
		return useCache != null ? useCache : true;
	}

	protected Long getId(Map<String, TemplateModel> params) throws TemplateModelException {
		return FreeMarkerUtils.getParameter(ID_PARAMETER_NAME, Long.class, params);
	}

	protected Integer getCount(Map<String, TemplateModel> params) throws TemplateModelException {
		return FreeMarkerUtils.getParameter(COUNT_PARAMETER_NAME, Integer.class, params);
	}

	protected List<Filter> getFilters(Map<String, TemplateModel> params, Class<?> type, String... ignoreProperties) throws TemplateModelException {
		List<Filter> filters = new ArrayList<Filter>();
		PropertyDescriptor[] propertyDescriptors = PropertyUtils.getPropertyDescriptors(type);
		for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
			String propertyName = propertyDescriptor.getName();
			Class<?> propertyType = propertyDescriptor.getPropertyType();
			if (!ArrayUtils.contains(ignoreProperties, propertyName) && params.containsKey(propertyName)) {
				Object value = FreeMarkerUtils.getParameter(propertyName, propertyType, params);
				filters.add(Filter.eq(propertyName, value));
			}
		}
		return filters;
	}

	protected List<Order> getOrders(Map<String, TemplateModel> params, String... ignoreProperties) throws TemplateModelException {
		String orderBy = StringUtils.trim(FreeMarkerUtils.getParameter(ORDER_BY_PARAMETER_NAME, String.class, params));
		List<Order> orders = new ArrayList<Order>();
		if (StringUtils.isNotEmpty(orderBy)) {
			String[] orderByItems = orderBy.split(ORDER_BY_ITEM_SEPARATOR);
			for (String orderByItem : orderByItems) {
				if (StringUtils.isNotEmpty(orderByItem)) {
					String property = null;
					Order.Direction direction = null;
					String[] orderBys = orderByItem.split(ORDER_BY_FIELD_SEPARATOR);
					if (orderBys.length == 1) {
						property = orderBys[0];
					} else if (orderBys.length >= 2) {
						property = orderBys[0];
						try {
							direction = Order.Direction.valueOf(orderBys[1]);
						} catch (IllegalArgumentException e) {
							continue;
						}
					} else {
						continue;
					}
					if (!ArrayUtils.contains(ignoreProperties, property)) {
						orders.add(new Order(property, direction));
					}
				}
			}
		}
		return orders;
	}

	protected void setLocalVariable(String name, Object value, Environment env, TemplateDirectiveBody body) throws TemplateException, IOException {
		TemplateModel preVariable = FreeMarkerUtils.getVariable(name, env);
		try {
			FreeMarkerUtils.setVariable(name, value, env);
			body.render(env.getOut());
		} finally {
			FreeMarkerUtils.setVariable(name, preVariable, env);
		}
	}

	protected void setLocalVariables(Map<String, Object> variables, Environment env, TemplateDirectiveBody body) throws TemplateException, IOException {
		Map<String, Object> preVariables = new HashMap<String, Object>();
		for (String name : variables.keySet()) {
			TemplateModel preVariable = FreeMarkerUtils.getVariable(name, env);
			preVariables.put(name, preVariable);
		}
		try {
			FreeMarkerUtils.setVariables(variables, env);
			body.render(env.getOut());
		} finally {
			FreeMarkerUtils.setVariables(preVariables, env);
		}
	}

}
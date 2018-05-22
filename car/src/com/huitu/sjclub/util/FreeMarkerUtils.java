/*
 * Copyright 2005-2015 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package com.huitu.sjclub.util;

import com.huitu.sjclub.CommonAttributes;
import com.huitu.sjclub.EnumConverter;
import freemarker.core.Environment;
import freemarker.ext.beans.BeansWrapper;
import freemarker.ext.beans.BeansWrapperBuilder;
import freemarker.template.*;
import freemarker.template.utility.DeepUnwrap;
import org.apache.commons.beanutils.ConvertUtilsBean;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.beanutils.converters.ArrayConverter;
import org.apache.commons.beanutils.converters.DateConverter;
import org.springframework.context.ApplicationContext;
import org.springframework.util.Assert;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Date;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unchecked")
public final class FreeMarkerUtils {

	private static final ConvertUtilsBean convertUtils;

	private static final Configuration DEFAULT_CONFIGURATION = new Configuration(Configuration.VERSION_2_3_22);

	private static final BeansWrapper DEFAULT_BEANS_WRAPPER = new BeansWrapperBuilder(Configuration.VERSION_2_3_22).build();

	static {
		convertUtils = new ConvertUtilsBean() {
			@Override
			public String convert(Object value) {
				if (value != null) {
					Class<?> type = value.getClass();
					if (type.isEnum() && super.lookup(type) == null) {
						super.register(new EnumConverter(type), type);
					} else if (type.isArray() && type.getComponentType().isEnum()) {
						if (super.lookup(type) == null) {
							ArrayConverter arrayConverter = new ArrayConverter(type, new EnumConverter(type.getComponentType()), 0);
							arrayConverter.setOnlyFirstToString(false);
							super.register(arrayConverter, type);
						}
						Converter converter = super.lookup(type);
						return ((String) converter.convert(String.class, value));
					}
				}
				return super.convert(value);
			}

			@SuppressWarnings("rawtypes")
			@Override
			public Object convert(String value, Class clazz) {
				if (clazz.isEnum() && super.lookup(clazz) == null) {
					super.register(new EnumConverter(clazz), clazz);
				}
				return super.convert(value, clazz);
			}

			@SuppressWarnings("rawtypes")
			@Override
			public Object convert(String[] values, Class clazz) {
				if (clazz.isArray() && clazz.getComponentType().isEnum() && super.lookup(clazz.getComponentType()) == null) {
					super.register(new EnumConverter(clazz.getComponentType()), clazz.getComponentType());
				}
				return super.convert(values, clazz);
			}

			@SuppressWarnings("rawtypes")
			@Override
			public Object convert(Object value, Class targetType) {
				if (super.lookup(targetType) == null) {
					if (targetType.isEnum()) {
						super.register(new EnumConverter(targetType), targetType);
					} else if (targetType.isArray() && targetType.getComponentType().isEnum()) {
						ArrayConverter arrayConverter = new ArrayConverter(targetType, new EnumConverter(targetType.getComponentType()), 0);
						arrayConverter.setOnlyFirstToString(false);
						super.register(arrayConverter, targetType);
					}
				}
				return super.convert(value, targetType);
			}
		};

		DateConverter dateConverter = new DateConverter();
		dateConverter.setPatterns(CommonAttributes.DATE_PATTERNS);
		convertUtils.register(dateConverter, Date.class);
	}

	private FreeMarkerUtils() {
	}

	public static String process(String template) throws IOException, TemplateException {
		return process(template, null);
	}

	public static String process(String template, Map<String, ?> model) throws IOException, TemplateException {
		Configuration configuration = null;
		ApplicationContext applicationContext = SpringUtils.getApplicationContext();
		if (applicationContext != null) {
			FreeMarkerConfigurer freeMarkerConfigurer = SpringUtils.getBean("freeMarkerConfigurer", FreeMarkerConfigurer.class);
			if (freeMarkerConfigurer != null) {
				configuration = freeMarkerConfigurer.getConfiguration();
			}
		}
		return process(template, model, configuration);
	}

	public static String process(String template, Map<String, ?> model, Configuration configuration) throws IOException, TemplateException {
		if (template == null) {
			return null;
		}
		StringWriter out = new StringWriter();
		new Template("template", new StringReader(template), configuration != null ? configuration : DEFAULT_CONFIGURATION).process(model, out);
		return out.toString();
	}

	public static <T> T getParameter(String name, Class<T> type, Map<String, TemplateModel> params) throws TemplateModelException {
		Assert.hasText(name);
		Assert.notNull(type);
		Assert.notNull(params);

		TemplateModel templateModel = params.get(name);
		if (templateModel != null) {
			Object value = DeepUnwrap.unwrap(templateModel);
			if (value != null) {
				return (T) convertUtils.convert(value, type);
			}
		}
		return null;
	}

	public static <T> T getArgument(int index, Class<T> type, List<?> arguments) throws TemplateModelException {
		Assert.notNull(type);
		Assert.notNull(arguments);

		if (index >= 0 && index < arguments.size()) {
			Object argument = arguments.get(index);
			Object value;
			if (argument != null) {
				if (argument instanceof TemplateModel) {
					value = DeepUnwrap.unwrap((TemplateModel) argument);
				} else {
					value = argument;
				}
				if (value != null) {
					return (T) convertUtils.convert(value, type);
				}
			}
		}
		return null;
	}

	public static TemplateModel getVariable(String name, Environment env) throws TemplateModelException {
		Assert.hasText(name);
		Assert.notNull(env);

		return env.getVariable(name);
	}

	public static void setVariable(String name, Object value, Environment env) throws TemplateException {
		Assert.hasText(name);
		Assert.notNull(env);

		if (value instanceof TemplateModel) {
			env.setVariable(name, (TemplateModel) value);
		} else {
			env.setVariable(name, DEFAULT_BEANS_WRAPPER.wrap(value));
		}
	}

	public static void setVariables(Map<String, Object> variables, Environment env) throws TemplateException {
		Assert.notNull(variables);
		Assert.notNull(env);

		for (Map.Entry<String, Object> entry : variables.entrySet()) {
			String name = entry.getKey();
			Object value = entry.getValue();
			if (value instanceof TemplateModel) {
				env.setVariable(name, (TemplateModel) value);
			} else {
				env.setVariable(name, DEFAULT_BEANS_WRAPPER.wrap(value));
			}
		}
	}

}
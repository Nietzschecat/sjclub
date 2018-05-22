/*
 * Copyright 2005-2015 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package com.huitu.sjclub.controller.sjclub;

import com.huitu.sjclub.DateEditor;
import com.huitu.sjclub.Message;
import com.huitu.sjclub.Setting;
import com.huitu.sjclub.StringEditor;
import com.huitu.sjclub.entity.Log;
import com.huitu.sjclub.exception.IllegalLicenseException;
import com.huitu.sjclub.exception.ResourceNotFoundException;
import com.huitu.sjclub.template.directive.FlashMessageDirective;
import com.huitu.sjclub.util.SpringUtils;
import com.huitu.sjclub.util.SystemUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.ui.ModelMap;
import org.springframework.util.Assert;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.Set;

public class BaseController {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	protected static final String ERROR_VIEW = "/admin/common/error";

	protected static final Message ERROR_MESSAGE = Message.error("admin.message.error");

	protected static final Message SUCCESS_MESSAGE = Message.success("admin.message.success");

	private static final String CONSTRAINT_VIOLATIONS_ATTRIBUTE_NAME = "constraintViolations";

	@Resource(name = "validator")
	private Validator validator;

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
		binder.registerCustomEditor(Date.class, new DateEditor(true));
		binder.registerCustomEditor(String.class, "password", new StringEditor(true));
	}

	@ExceptionHandler
	public ModelAndView exceptionHandler(Exception exception, HttpServletResponse response) {
		if (exception instanceof ResourceNotFoundException) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return new ModelAndView("/admin/common/resource_not_found");
		} else if (exception instanceof IllegalLicenseException) {
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			return new ModelAndView("/admin/common/illegal_license");
		} else {
			logger.warn("Handler execution resulted in exception", exception);
			Setting setting = SystemUtils.getSetting();
			ModelMap model = new ModelMap();
			if (setting.getIsDevelopmentEnabled()) {
				model.addAttribute("exception", exception);
			}
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return new ModelAndView(ERROR_VIEW, model);
		}
	}

	protected boolean isValid(Object target, Class<?>... groups) {
		Assert.notNull(target);

		Set<ConstraintViolation<Object>> constraintViolations = validator.validate(target, groups);
		if (constraintViolations.isEmpty()) {
			return true;
		}
		RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
		requestAttributes.setAttribute(CONSTRAINT_VIOLATIONS_ATTRIBUTE_NAME, constraintViolations, RequestAttributes.SCOPE_REQUEST);
		return false;
	}

	protected boolean isValid(Collection<Object> targets, Class<?>... groups) {
		Assert.notEmpty(targets);

		for (Object target : targets) {
			if (!isValid(target, groups)) {
				return false;
			}
		}
		return true;
	}

	protected boolean isValid(Class<?> type, String property, Object value, Class<?>... groups) {
		Assert.notNull(type);
		Assert.hasText(property);

		Set<?> constraintViolations = validator.validateValue(type, property, value, groups);
		if (constraintViolations.isEmpty()) {
			return true;
		}
		RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
		requestAttributes.setAttribute(CONSTRAINT_VIOLATIONS_ATTRIBUTE_NAME, constraintViolations, RequestAttributes.SCOPE_REQUEST);
		return false;
	}

	protected boolean isValid(Class<?> type, Map<String, Object> properties, Class<?>... groups) {
		Assert.notNull(type);
		Assert.notEmpty(properties);

		for (Map.Entry<String, Object> entry : properties.entrySet()) {
			if (!isValid(type, entry.getKey(), entry.getValue(), groups)) {
				return false;
			}
		}
		return true;
	}

	protected String currency(BigDecimal amount, boolean showSign, boolean showUnit) {
		Setting setting = SystemUtils.getSetting();
		String price = setting.setScale(amount).toString();
		if (showSign) {
			price = setting.getCurrencySign() + price;
		}
		if (showUnit) {
			price += setting.getCurrencyUnit();
		}
		return price;
	}

	protected String message(String code, Object... args) {
		return SpringUtils.getMessage(code, args);
	}

	protected void addFlashMessage(RedirectAttributes redirectAttributes, Message message) {
		if (redirectAttributes != null && message != null) {
			redirectAttributes.addFlashAttribute(FlashMessageDirective.FLASH_MESSAGE_ATTRIBUTE_NAME, message);
		}
	}

	protected void addLog(String content) {
		if (content != null) {
			RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
			requestAttributes.setAttribute(Log.LOG_CONTENT_ATTRIBUTE_NAME, content, RequestAttributes.SCOPE_REQUEST);
		}
	}

}
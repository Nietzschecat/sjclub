/*
 * Copyright 2005-2015 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package com.huitu.sjclub;

import com.huitu.sjclub.util.SpringUtils;

public class Message {

	public enum Type {

		success,

		warn,

		error,
		//需重新登陆
		requiredlogined,
		//没权限
		notauthorized,
		//发生异常
		exception,
		//没找到页面
		resourcenotfound,

	}

	private Message.Type type;

	private String content;

	public Message() {

	}

	public Message(Message.Type type, String content) {
		this.type = type;
		this.content = content;
	}

	public Message(Message.Type type, String content, Object... args) {
		this.type = type;
		this.content = SpringUtils.getMessage(content, args);
	}

	public static Message success(String content, Object... args) {
		return new Message(Message.Type.success, content, args);
	}

	public static Message exception(String content, Object... args) {
		return new Message(Message.Type.exception, content, args);
	}

	public static Message requiredlogined(String content, Object... args) {
		return new Message(Message.Type.requiredlogined, content, args);
	}

	public static Message resourcenotfound(String content, Object... args) {
		return new Message(Message.Type.resourcenotfound, content, args);
	}

	public static Message notauthorized(String content, Object... args) {
		return new Message(Message.Type.notauthorized, content, args);
	}

	public static Message warn(String content, Object... args) {
		return new Message(Message.Type.warn, content, args);
	}


	public static Message error(String content, Object... args) {
		return new Message(Message.Type.error, content, args);
	}

	public Message.Type getType() {
		return type;
	}

	public void setType(Message.Type type) {
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return SpringUtils.getMessage(content);
	}

}
/*
 * Copyright 2005-2015 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package com.huitu.sjclub.entity;

import javax.persistence.*;

@Entity
@Table(name = "xx_log")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "seq_log")
public class Log extends BaseEntity<Long> {

	private static final long serialVersionUID = -7135158896704721662L;

	public static final String LOG_CONTENT_ATTRIBUTE_NAME = Log.class.getName() + ".CONTENT";

	private String operation;

	private String operator;

	private String content;

	private String parameter;

	private String ip;

	@Column(nullable = false, updatable = false)
	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	@Column(updatable = false)
	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	@Column(updatable = false, length = 4000)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Lob
	@Column(updatable = false)
	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	@Column(nullable = false, updatable = false)
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	@Transient
	public void setOperator(User operator) {
		setOperator(operator != null ? operator.getUserName() : null);
	}

}

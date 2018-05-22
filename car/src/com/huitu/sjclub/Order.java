/*
 * Copyright 2005-2015 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package com.huitu.sjclub;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.io.Serializable;

public class Order implements Serializable {

	private static final long serialVersionUID = -3078342809727773232L;

	public enum Direction {

		asc,

		desc
	}

	private static final Order.Direction DEFAULT_DIRECTION = Order.Direction.desc;

	private String property;

	private Order.Direction direction = DEFAULT_DIRECTION;

	public Order() {
	}

	public Order(String property, Order.Direction direction) {
		this.property = property;
		this.direction = direction;
	}

	public static Order asc(String property) {
		return new Order(property, Order.Direction.asc);
	}

	public static Order desc(String property) {
		return new Order(property, Order.Direction.desc);
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public Order.Direction getDirection() {
		return direction;
	}

	public void setDirection(Order.Direction direction) {
		this.direction = direction;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		Order other = (Order) obj;
		return new EqualsBuilder().append(getProperty(), other.getProperty()).append(getDirection(), other.getDirection()).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37).append(getProperty()).append(getDirection()).toHashCode();
	}

}
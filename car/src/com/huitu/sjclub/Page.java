/*
 * Copyright 2005-2015 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package com.huitu.sjclub;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Page<T> implements Serializable {

	private static final long serialVersionUID = -2053800594583879853L;

	private final List<T> content = new ArrayList<T>();

	private final long total;

	private final Pageable pageable;

	public Page() {
		this.total = 0L;
		this.pageable = new Pageable();
	}

	public Page(List<T> content, long total, Pageable pageable) {
		this.content.addAll(content);
		this.total = total;
		this.pageable = pageable;
	}

	public int getPageNumber() {
		return pageable.getPageNumber();
	}

	public int getPageSize() {
		return pageable.getPageSize();
	}

	public String getSearchProperty() {
		return pageable.getSearchProperty();
	}

	public String getSearchValue() {
		return pageable.getSearchValue();
	}

	public String getOrderProperty() {
		return pageable.getOrderProperty();
	}

	public Order.Direction getOrderDirection() {
		return pageable.getOrderDirection();
	}

	public List<Order> getOrders() {
		return pageable.getOrders();
	}

	public List<Filter> getFilters() {
		return pageable.getFilters();
	}

	public int getTotalPages() {
		return (int) Math.ceil((double) getTotal() / (double) getPageSize());
	}

	public List<T> getContent() {
		return content;
	}

	public long getTotal() {
		return total;
	}

	public Pageable getPageable() {
		return pageable;
	}

	public static final <T> Page<T> emptyPage(Pageable pageable) {
		return new Page<T>(Collections.<T> emptyList(), 0L, pageable);
	}

}
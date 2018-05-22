/*
 * Copyright 2005-2015 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package com.huitu.sjclub.dao;

import com.huitu.sjclub.Filter;
import com.huitu.sjclub.Order;
import com.huitu.sjclub.Page;
import com.huitu.sjclub.Pageable;
import com.huitu.sjclub.entity.BaseEntity;

import javax.persistence.LockModeType;
import java.io.Serializable;
import java.util.List;

public interface BaseDao<T extends BaseEntity<ID>, ID extends Serializable> {

	T find(ID id);

	T find(ID id, LockModeType lockModeType);

	List<T> findList(Integer first, Integer count, List<Filter> filters, List<Order> orders);

	Page<T> findPage(Pageable pageable);

	long count(Filter... filters);

	void persist(T entity);

	T merge(T entity);

	void remove(T entity);

	void refresh(T entity);

	void refresh(T entity, LockModeType lockModeType);

	ID getIdentifier(T entity);

	boolean isLoaded(T entity);

	boolean isLoaded(T entity, String attributeName);

	boolean isManaged(T entity);

	void detach(T entity);

	LockModeType getLockMode(T entity);

	void lock(T entity, LockModeType lockModeType);

	void clear();

	void flush();

}
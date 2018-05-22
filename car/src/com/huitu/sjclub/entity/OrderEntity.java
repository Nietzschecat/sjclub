/*
 * Copyright 2005-2015 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package com.huitu.sjclub.entity;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.hibernate.search.annotations.*;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.Min;
import java.io.Serializable;

@MappedSuperclass
public abstract class OrderEntity<ID extends Serializable> extends BaseEntity<ID> implements Comparable<OrderEntity<ID>> {

	private static final long serialVersionUID = 4390476354094749284L;

	public static final String ORDER_PROPERTY_NAME = "order";

	private Integer order;

	@Field(store = Store.YES, index = Index.YES, analyze = Analyze.NO)
	@NumericField
	@Min(0)
	@Column(name = "orders")
	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public int compareTo(OrderEntity<ID> orderEntity) {
		if (orderEntity == null) {
			return 1;
		}
		return new CompareToBuilder().append(getOrder(), orderEntity.getOrder()).append(getId(), orderEntity.getId()).toComparison();
	}

}

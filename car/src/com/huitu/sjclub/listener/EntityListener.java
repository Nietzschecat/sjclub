/*
 * Copyright 2005-2015 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package com.huitu.sjclub.listener;

import com.huitu.sjclub.entity.BaseEntity;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.util.Date;

public class EntityListener {

	@PrePersist
	public void prePersist(BaseEntity<?> entity) {
		entity.setCreateDate(new Date());
		entity.setModifyDate(new Date());
		entity.setVersion(null);
	}

	@PreUpdate
	public void preUpdate(BaseEntity<?> entity) {
		entity.setModifyDate(new Date());
	}

}
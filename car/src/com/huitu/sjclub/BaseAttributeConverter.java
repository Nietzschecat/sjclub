/*
 * Copyright 2005-2015 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package com.huitu.sjclub;

import com.fasterxml.jackson.databind.JavaType;
import com.huitu.sjclub.util.JsonUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.core.ResolvableType;

import javax.persistence.AttributeConverter;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class BaseAttributeConverter<T> implements AttributeConverter<Object, String> {

	private JavaType javaType;

	public BaseAttributeConverter() {
		ResolvableType resolvableType = ResolvableType.forClass(getClass());
		Type type = resolvableType.getSuperType().getGeneric().getType();
		javaType = JsonUtils.constructType(type);
	}

	public String convertToDatabaseColumn(Object attribute) {
		if (attribute == null) {
			return null;
		}

		return JsonUtils.toJson(attribute);
	}

	public Object convertToEntityAttribute(String dbData) {
		if (StringUtils.isEmpty(dbData)) {
			if (List.class.isAssignableFrom(javaType.getRawClass())) {
				return Collections.EMPTY_LIST;
			} else if (Set.class.isAssignableFrom(javaType.getRawClass())) {
				return Collections.EMPTY_SET;
			} else if (Map.class.isAssignableFrom(javaType.getRawClass())) {
				return Collections.EMPTY_MAP;
			} else {
				return null;
			}
		}

		return JsonUtils.toObject(dbData, javaType);
	}

}
package com.wanfin.fpd.core.filter;

import com.wanfin.fpd.common.persistence.ApiEntity;

/**
 * 容器属性过滤器
 * @author Chenh
 * @param <T>
 */
public interface CollectionResFilter<T> {
	public void filter(T t, Integer offset, Integer limit);
	public void filterByFields(T c, ApiEntity<?> entity);
}

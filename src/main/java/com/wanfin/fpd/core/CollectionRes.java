/*
 * Copyright (C) 2012 Stormpath, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.wanfin.fpd.core;

import java.util.Collection;
import java.util.Collections;

import javax.ws.rs.core.UriInfo;

import com.wanfin.fpd.common.persistence.ApiEntity;
import com.wanfin.fpd.core.filter.CollectionResFilter;

@SuppressWarnings({ "unchecked", "serial", "rawtypes" })
public class CollectionRes extends Link implements CollectionResFilter<Collection>{
	public CollectionRes(String subPath, Collection c) {
        this(subPath, c, ApiEntity.DEFAULT_OFFSET, getLimit(c));
    }

	public CollectionRes(String subPath, Collection c, Integer offset, Integer limit) {
        super(subPath);
        filter(c, offset, limit);
    }

	public CollectionRes(String subPath, Collection c, ApiEntity entity) {
		super(subPath);
		filterByFields(c, entity);
	}
	
	public CollectionRes(UriInfo info, String subPath, Collection c) {
        this(info, subPath, c, ApiEntity.DEFAULT_OFFSET, getLimit(c));
    }

	public CollectionRes(UriInfo info, String subPath, Collection c, Integer offset, Integer limit) {
        super(info, subPath);
        filter(c, offset, limit);
    }

	public CollectionRes(UriInfo info, String subPath, Collection c, ApiEntity entity) {
		super(info, subPath);
		filterByFields(c, entity);
	}

	private Collection getItems(Collection c) {
		return c != null ? c : Collections.emptyList();
	}

    private static Integer getLimit(Collection c) {
        return getLimit(c != null ? c.size() : ApiEntity.DEFAULT_OFFSET);
    }

    private static Integer getLimit(Integer limit) {
        return Math.max(ApiEntity.DEFAULT_LIMIT, limit);
    }
    

    @Override
	public void filter(Collection c, Integer offset, Integer limit) {
        put(ResKey.ITEMS.getKey(), getItems(c));
		put(ResKey.OFFSET.getKey(), offset);
        put(ResKey.LIMIT.getKey(), getLimit(limit));
	}

    @Override
    public void filterByFields(Collection c, ApiEntity entity) {
		put(ResKey.ITEMS.getKey(), getItems(c));
		put(ResKey.OFFSET.getKey(), entity.getOffset());
		put(ResKey.LIMIT.getKey(), getLimit(entity.getLimit()));
		
		if(entity.getFields() != null){
			put(ResKey.FIELDS.getKey(), entity.getFields());
		}
		if(entity.getSorts() != null){
			put(ResKey.SORTS.getKey(), entity.getSorts());
		}
		if((entity.getSortby() != null) && (entity.getOrderby() != null)){
			put(ResKey.SORTS.getKey(), entity.getSorts());
			put(ResKey.SORTBY.getKey(), entity.getSortby());
			put(ResKey.ORDERBY.getKey(), entity.getOrderby());
		}
		put(ResKey.CURPAGE.getKey(), entity.getCurPage());
		put(ResKey.PERPAGE.getKey(), entity.getPerPage());
	}
}
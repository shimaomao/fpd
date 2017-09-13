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

import java.util.LinkedHashMap;

import javax.ws.rs.core.UriInfo;

import com.wanfin.fpd.common.persistence.ApiEntity;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class Link extends LinkedHashMap {
	private static final long serialVersionUID = -8597773189300091501L;

	/** BASE ***********************************************************************************/
	public static final String SWAGGER_PATH = "/swagger";
	public static final String API_PATH = "${api.baseUri}/";
//	public static final String API_PATH = "${api.baseUri}/${api.version}/";
	public static final String PATH_SEPARATOR = "/";

    /** LinkKey ***********************************************************************************/
    public class LinkKey{
        public static final String User = PATH_SEPARATOR + "users";
        public static final String Role = PATH_SEPARATOR + "roles";
        public static final String Office = PATH_SEPARATOR + "offices";
        public static final String Dict = PATH_SEPARATOR + "dicts";

        public static final String TProduct = PATH_SEPARATOR + "tProducts";
        public static final String TCompany = PATH_SEPARATOR + "tCompanys";
        public static final String TEmployee = PATH_SEPARATOR + "tEmplyoees";
        
        public static final String RepayRecord = PATH_SEPARATOR + "repayRecords";
        public static final String TRepayPlan = PATH_SEPARATOR + "tRepayPlans";
        public static final String TLoanContract = PATH_SEPARATOR + "tLoanContracts";
        public static final String WOrder = PATH_SEPARATOR + "wOrders";
	}
    
    /** ResKey ***********************************************************************************/
	public enum ResKey{
		HREF("href", "链接地址"),
		OFFSET("offset", "设置显示起点"),
		LIMIT("limit", "限制最多显示行数"),
		ITEMS("items", "列表项"),
		FIELDS("fields", "显示列"),
		SORTS("sorts", "排序KEY/VAL"),
		SORTBY("sortby", "排序KEY"),
		ORDERBY("orderby", "排序VAL"),
		CURPAGE("curPage", "当前页"),
		PERPAGE("perPage", "每页");
		
		private String key;
		private String remark;
		private ResKey(String key, String remark) {
			this.key = key;
			this.remark = remark;
		}
		public String getKey() {
			return key;
		}
		public String getRemark() {
			return remark;
		}
		public void setKey(String key) {
			this.key = key;
		}
		public void setRemark(String remark) {
			this.remark = remark;
		}
	}
    
    /** 构造方法 ***********************************************************************************/
	public Link() {
		super();
	}

	public Link(ApiEntity entity) {
		this(getFullyQualifiedContextPath(), entity);
	}
	
    public Link(String subPath) {
        this(getFullyQualifiedContextPath(), subPath);
    }

    public Link(String fqBasePath, ApiEntity entity) {
        String href = createHref(fqBasePath, entity);
        put(ResKey.HREF.getKey(), href);
    }

    public Link(String fqBasePath, String subPath) {
        String href = fqBasePath + subPath;
        put(ResKey.HREF.getKey(), href);
    }

	public Link(UriInfo info, ApiEntity entity) {
		this(getFullyQualifiedContextPath(info), entity);
	}
	
    public Link(UriInfo info, String subPath) {
        this(getFullyQualifiedContextPath(info), subPath);
    }

    protected static String getFullyQualifiedContextPath() {
        return getFullyQualifiedContextPath(null);
    }

    protected static String getFullyQualifiedContextPath(UriInfo info) {
    	if(info == null){
    		info = new ApiUriInfo();
    	}
    	String fq = info.getBaseUri().toString();
    	if (fq.endsWith(PATH_SEPARATOR)) {
    		return fq.substring(0, fq.length()-1);
    	}
    	return fq;
    }

    protected String createHref(String fqBasePath, ApiEntity entity) {
        StringBuilder sb = new StringBuilder(fqBasePath);
        LinkResPath path = LinkResPath.forClass(entity.getClass());
        sb.append(path.getPath()).append(PATH_SEPARATOR).append(entity.getId());
        return sb.toString();
    }

    public String getHref() {
        return (String)get(ResKey.HREF.getKey());
    }
}

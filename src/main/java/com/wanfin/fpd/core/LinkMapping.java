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

import com.wanfin.fpd.core.ApiSval.JKey;
import com.wanfin.fpd.core.Link.LinkKey;


public class LinkMapping {
	/**********************************************************************************************************
	 * 默认操作
	 **********************************************************************************************************/
	//查询操作
	public static final String GET_ALL = "";
	public static final String GET_BY_ID = "/{"+JKey.ID+"}";

	//修改操作
	public static final String SAVE = "";
	public static final String UPDATE = "/{"+JKey.ID+"}";
	public static final String DELETE_BY_ID = "/{"+JKey.ID+"}";

	/**********************************************************************************************************
	 * 批量操作
	 **********************************************************************************************************/
//	public static final String PL_SAVE = "";
//	public static final String PL_UPDATE = "";
//	public static final String PL_DELETE_BY_ID = "/{"+JKey.ID+"}";
//
//	//物理操作
//	public static final String WL_SAVE = "";
//	public static final String WL_UPDATE_WL = "";
//	public static final String WL_DELETE_BY_ID = "/{"+JKey.ID+"}";
//	
//	//物理批量操作
//	public static final String WLPL_SAVE = "";
//	public static final String WLPL_UPDATE = "";
//	public static final String WLPL_DELETE_BY_ID = "/{"+JKey.ID+"}";
	
	/**********************************************************************************************************
	 * 关联操作
	 **********************************************************************************************************/
	//关联用户操作
	public static class M_User{
		public static final String GET_ALL = "/{"+JKey.RID+"}"+LinkKey.User;
		public static final String GET_BY_ID = "/{"+JKey.RID+"}"+LinkKey.User+"/{"+JKey.ID+"}/";
		public static final String SAVE = "/{"+JKey.RID+"}"+LinkKey.User;
		public static final String GET_ALL_ADMIN_ROLE_DB = "/dbzadmin"+LinkKey.User;
		public static final String GET_ALL_ADMIN_ROLE_XD = "/xdzadmin"+LinkKey.User;
		public static final String SAVE_ADMIN_ROLE_DB = "/dbzadmin"+LinkKey.User;
		public static final String SAVE_ADMIN_ROLE_XD = "/xdzadmin"+LinkKey.User;
		
		public static final String HAS_USER = "/hasUser";
		
//		public static final String UPDATE = "/{"+JKey.RID+"}/"+LinkKey.User+"/{"+JKey.ID+"}";
//		public static final String DELETE_BY_ID = "/{"+JKey.RID+"}/"+LinkKey.User+"/{"+JKey.ID+"}";
	}
	//关联角色操作
	public static class M_Role{
		public static final String GET_ALL = "/{"+JKey.RID+"}/"+LinkKey.Role;
		public static final String GET_BY_ID = "/{"+JKey.RID+"}/"+LinkKey.Role+"/{"+JKey.ID+"}/";
	}
	//关联贷款合同操作
	public static class M_TLoanContract{
		public static final String GET_ALL = "/{"+JKey.RID+"}/"+LinkKey.TLoanContract;
		public static final String GET_BY_ID = "/{"+JKey.RID+"}/"+LinkKey.TLoanContract+"/{"+JKey.ID+"}/";
	}
	//关联还款计划操作
	public static class M_TRepayPlan{
		public static final String GET_ALL = "/{"+JKey.RID+"}/"+LinkKey.TRepayPlan;
		public static final String GET_BY_ID = "/{"+JKey.RID+"}/"+LinkKey.TRepayPlan+"/{"+JKey.ID+"}/";
	}
}
/**  
 * @Project fpd 
 * @Title ApiSval.java
 * @Package com.wanfin.fpd.core
 * @Description [[_xxx_]]文件 
 * @author Chenh
 * @date 2016年6月6日 上午11:04:13 
 * @version V1.0   
 */ 
package com.wanfin.fpd.core;

/**
 * @Description [[_xxx_]] ApiSval类
 * @author Chenh
 * @date 2016年6月6日 上午11:04:13 
 */
public class ApiSval {
	/** KEY ***********************************************************************************/
	/**
	 * @Description 模块
	 * @author Chenh 
	 * @date 2016年6月6日 上午11:06:15  
	 */
	public static class JKey{
		//链接
		public static final String HREF = "href";
		//ID
		public static final String ID = "id";
		//关联ID
		public static final String RID = "rid";
		//结果消息
		public static final String MSG = "msg";
	}

	/**
	 * @Description 模块
	 * @author Chenh 
	 * @date 2016年6月6日 上午11:06:15  
	 */
	public static class Gconfig{
		public static class GcCategory{
			public static final String API = "api";
			public static final String API_DAO = "api_dao";
			public static final String API_DOC_TEST = "api_doc_test";

			public static final String CURD = "curd";
			public static final String CURD_MANY = "curd_many";
			public static final String DAO = "dao";
			public static final String TREE_TABLE = "treeTable";
			public static final String TREE_TABLE_AND_LIST = "treeTableAndList";
		}
	}
}
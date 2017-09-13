package com.wanfin.fpd.common.config;

public class SvalBase {
	/**
	 *	JSON常用Key类型
	 */
	public static class JsonKey{
		//消息
		public static final String KEY_MSG = "msg";    
		//状态：true/false
	    public static final String KEY_IS_TRUE = "istrue";
	    //状态码：200/201/404
	    public static final String KEY_STATUS = "status";
		//参数
		public static final String KEY_PARAMS = "params";
		//列表
		public static final String KEY_LISTS = "lists";
		
		//结果-内容(大文本、HTML)
		public static final String KEY_CONTENT = "content";
		//结果-数据(数据值)
		public static final String KEY_DATA = "data";
		//结果-数据(数据值JSON)
		public static final String KEY_RESULT = "result";

		//长度
		public static final String KEY_SIZE = "size";
		//长度
		public static final String KEY_LENGTH = "length";
		//总长度
		public static final String KEY_TOTAL = "total";

		//总金额
		public static final String KEY_TOTAL_AMOUNT = "totalAmount";
		//剩余金额
		public static final String KEY_SY_AMOUNT = "syAmount";
		
		public static final String KEY = "key";
		public static final String ENTITYS = "entitys";
		public static final String VALUE = "value";
		public static final String TOKEN = "token";
	}
	
	
}

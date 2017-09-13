/**  
 * @Project fpd 
 * @Title DFPlugin.java
 * @Package com.wanfin.fpd.modules.form.builder.df
 * @Description [[_自定义表单插件_]]文件 
 * @author Chenh
 * @date 2016年4月28日 下午12:28:37 
 * @version V1.0   
 */ 
package com.wanfin.fpd.modules.form.builder.df;

/**
 * @Description [[_自定义表单插件_]] DFPlugin类
 * @author Chenh
 * @date 2016年4月28日 下午12:28:37 
 */
public interface DFPlugin {
	public enum DFPType{
		TAB("tab", "切换面板", "tab切换面板");
//		INPUT("input", "文本框", "input文本框"),
//		SELECT("select", "下拉框", "select下拉框"),
//		RADIO("radio", "单选按钮", "radio单选按钮"),
//		CHECKBOX("checkBox", "选择框", "checkBox选择框"),
//		TEXTAREA("textarea", "文本域", "textarea文本域"),
//		TEXTAREA_SPLIT("textarea-split", "文本域编辑", "textarea-split文本域编辑");
		
		private String key;
		private String value;
		private String remark;
		
		/** 构造方法 ***********************************************************************************/
		/**
		 * @Description [[_自定义表单插件_]]构造器
		 * @author Chenh
		 * @date 2015-1-15 下午3:39:14  
		 */
		private DFPType(String key, String value, String remark) {
			this.key = key;
			this.value = value;
			this.remark = remark;
		}

		public static DFPType getByKey(String key){
			DFPType[] types = DFPType.values();
			for (DFPType dfpType : types) {
				if((key).equals(dfpType.getKey())){
					return dfpType;
				}
			}
			return null;
		}
		
		public String getKey() {
			return key;
		}

		public void setKey(String key) {
			this.key = key;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

		public String getRemark() {
			return remark;
		}

		public void setRemark(String remark) {
			this.remark = remark;
		}
	}
	
	/**
	 * @Description 自定义表单插件类型
	 * @return String
	 * @author Chenh 
	 * @date 2016年4月28日 下午1:10:37
	 */
	public String getType();
}

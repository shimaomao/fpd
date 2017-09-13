/**  
 * @Project fpd 
 * @Title DFFGroup.java
 * @Package com.wanfin.fpd.modules.form.builder.df.group
 * @Description [[_自定义表单组_]]文件 
 * @author Chenh
 * @date 2016年4月28日 下午2:32:32 
 * @version V1.0   
 */ 
package com.wanfin.fpd.modules.form.builder.df.group;

import java.io.Serializable;


/**
 * @Description [[_自定义表单组_]] DFFGroup类
 * @author Chenh
 * @date 2016年4月28日 下午2:32:32 
 */
public abstract class DFFGroup implements Serializable{
	private static final long serialVersionUID = 1L;

	/**
	 * @Description [[_表单组件类型_]] DFTypeCol类
	 * @author Chenh
	 * @date 2016年4月28日 下午4:45:03
	 */
	public enum DFCtype{
		SYS("0", "textinput", "sys", "系统生成"),
		INPUT("100", "textinput", "Text Input", "文本框"),
		INPUT_PSW("101", "passwordinput", "Password Input", "密码框"),
		INPUT_SEARCH("102", "search", "Search Input", "查询框"),
		INPUT_PREPENDED("110", "prependedtext", "Prepended Text", "前缀文本框"),
		INPUT_APPENDED("111", "appendedtext", "Appended Text", "后缀文本框"),
		INPUT_PREPENDED_CHECKBOX("120", "prependedcheckbox", "Prepended Checkbox", "前缀选项文本框"),
		INPUT_APPENDED_CHECKBOX("121", "appendedcheckbox", "Appended Checkbox", "后缀选项文本框"),
		INPUT_BUTTON("130", "input", "Button Drop Down", "按钮文本框"),
		
		SELECT("200", "selectbasic", "Select Basic", "下拉框"),
		SELECT_URL("201", "selecturl", "Select URL", "URL下拉框"),
		SELECTM("202", "selectmultiple", "Select Multiple", "多选下拉框"),
		
		TEXTAREA("300", "textarea", "Textarea", "文本域"),
		TEXTAREASPLIT("301", "textarea-split", "TextArea Split", "文本域"),

		RADIO("400", "multipleradiosinline", "Inline Radios", "单选按钮"),
		RADIOM("401", "multipleradios", "Multiple Radios", "多行单选按钮"),
	
		CHECKBOX("500", "multiplecheckboxesinline", "Inline Checkboxes", "单选框"),
		CHECKBOXM("501", "multiplecheckboxes", "Multiple Checkboxes", "多行单选框"),
		
		FILE("600", "filebutton", "File Button", "文件"),
		BUTTON("601", "singlebutton", "Single Button", "按钮"),
		BUTTON_DOUBLE("602", "doublebutton", "Double Button", "双按钮"),
		BUTTON_DROPDOWN("603", "buttondropdown", "Button Dropdown", "下拉按钮"),
		
		DATE("700", "date", "date", "date日期控件"),
		TREESELECT("701", "treeselect", "treeselect", "树状选项弹窗");
		
		private String val;
		private String type;
		private String ename;
		private String name;
		
		/** 构造方法 ***********************************************************************************/
		/**
		 * @Description [[_xxx_]]构造器
		 * @author Chenh
		 * @date 2015-1-15 下午3:39:14  
		 */
		private DFCtype(String val, String type, String ename, String name) {
			this.val = val;
			this.type = type;
			this.ename = ename;
			this.name = name;
		}
		public String getVal() {
			return val;
		}
		public void setVal(String val) {
			this.val = val;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getEname() {
			return ename;
		}
		public void setEname(String ename) {
			this.ename = ename;
		}
	}
}

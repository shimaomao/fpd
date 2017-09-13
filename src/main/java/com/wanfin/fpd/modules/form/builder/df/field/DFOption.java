/**  
 * @Project fpd 
 * @Title DFOption.java
 * @Package com.wanfin.fpd.modules.form.builder.df.field
 * @Description [[_自定义表单组件-下拉框选项_]]文件 
 * @author Chenh
 * @date 2016年4月28日 下午2:12:25 
 * @version V1.0   
 */ 
package com.wanfin.fpd.modules.form.builder.df.field;

import java.io.Serializable;

/**
 * @Description [[_自定义表单组件-下拉框选项_]] DFOption类
 * @author Chenh
 * @date 2016年4月28日 下午2:12:25 
 */
public class DFOption implements Serializable{
	private static final long serialVersionUID = 1L;
	private String value;
	private String label;
	private Boolean selected;
	
	/** 构造方法 ***********************************************************************************/
	/**
	 * @Description [[_xxx_]]构造器
	 * @author Chenh
	 * @date 2015-1-15 下午3:39:14  
	 */
	public DFOption(String value, String label, Boolean selected) {
		super();
		this.value = value;
		this.label = label;
		this.selected = selected;
	}

	/**
	 * @Description [[_xxx_]]构造器
	 * @author Chenh
	 * @date 2015-1-15 下午3:39:14  
	 */
	public DFOption() {
		super();
	}

	/**
	 * @Description [[_xxx_]]构造器
	 * @author Chenh
	 * @date 2015-1-15 下午3:39:14  
	 */
	public DFOption(String value) {
		super();
		this.value = value;
	}

	public String getValue() {
		if(this.value == null){
			this.value = "";
		}
		return value;
	}
	public String getLabel() {
		if(this.label == null){
			this.label = "";
		}
		return label;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public Boolean getSelected() {
		if(this.selected == null){
			this.selected = false;
		}
		return selected;
	}
	public void setSelected(Boolean selected) {
		this.selected = selected;
	}
}
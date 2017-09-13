/**  
 * @Project fpd 
 * @Title DFF.java
 * @Package com.wanfin.fpd.modules.form.builder.df.field
 * @Description [[_自定义表单组件_]]文件 
 * @author Chenh
 * @date 2016年4月28日 下午1:15:13 
 * @version V1.0   
 */ 
package com.wanfin.fpd.modules.form.builder.df.field;

import java.io.Serializable;


/**
 * @Description [[_自定义表单组件_]] DFF类
 * @author Chenh
 * @date 2016年4月28日 下午1:15:13 
 */
public abstract class DFF<T extends Object> implements Serializable{
	private static final long serialVersionUID = 1L;
	private String name;
	private String label;
	private String type;
	
	
	/** 构造方法 ***********************************************************************************/
	/**
	 * @Description [[_xxx_]]构造器
	 * @author Chenh
	 * @date 2015-1-15 下午3:39:14  
	 */
	public DFF(String name, String label, String type) {
		super();
		this.name = name;
		this.label = label;
		this.type = type;
	}
	/**
	 * @Description [[_xxx_]]构造器
	 * @author Chenh
	 * @date 2015-1-15 下午3:39:14  
	 */
	public DFF(String label, String type) {
		super();
		this.label = label;
		this.type = type;
	}



	/**
	 * @Description [[_xxx_]]构造器
	 * @author Chenh
	 * @date 2015-1-15 下午3:39:14  
	 */
	public DFF() {
		super();
	}
	public String getName() {
		if(this.name == null){
			this.name = getType();
		}
		return name;
	}
	public String getLabel() {
		if(this.label == null){
			this.label = "label";
		}
		return label;
	}
	public String getType() {
		if(this.type == null){
			this.type = "input";
		}
		return type;
	}
	
	public void setLabel(String label) {
		this.label = label;
	}
	public void setType(String type) {
		this.type = type;
	}
	public void setName(String name) {
		this.name = name;
	}
	public abstract T getValue();
	public abstract void setValue(T value);
}

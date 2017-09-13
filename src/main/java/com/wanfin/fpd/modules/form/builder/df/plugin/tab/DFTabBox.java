/**  
 * @Project fpd 
 * @Title DFTabBox.java
 * @Package com.wanfin.fpd.modules.form.builder.df.plugin.tab
 * @Description [[_自定义表单-组件块_]]文件 
 * @author Chenh
 * @date 2016年4月29日 下午1:44:18 
 * @version V1.0   
 */ 
package com.wanfin.fpd.modules.form.builder.df.plugin.tab;

import java.io.Serializable;

import com.wanfin.fpd.modules.form.builder.df.group.DFFGroup;

/**
 * @Description [[_自定义表单-组件块_]] DFTabBox类
 * @author Chenh
 * @date 2016年4月29日 下午1:44:18 
 */
public class DFTabBox implements Serializable{
	private static final long serialVersionUID = 1L;
	private String title;
	private String itype;
	private DFFGroup fields;
	
	/** 构造方法 ***********************************************************************************/
	/**
	 * @Description [[_xxx_]]构造器
	 * @author Chenh
	 * @date 2015-1-15 下午3:39:14  
	 */
	public DFTabBox() {
		super();
	}
	/**
	 * @Description [[_xxx_]]构造器
	 * @author Chenh
	 * @date 2015-1-15 下午3:39:14  
	 */
	public DFTabBox(String title, DFFGroup fields) {
		super();
		this.title = title;
		this.fields = fields;
	}
	/**
	 * @Description [[_xxx_]]构造器
	 * @author Chenh
	 * @date 2015-1-15 下午3:39:14  
	 */
	public DFTabBox(String title, String itype, DFFGroup fields) {
		super();
		this.title = title;
		this.itype = itype;
		this.fields = fields;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public DFFGroup getFields() {
		return fields;
	}
	public void setFields(DFFGroup fields) {
		this.fields = fields;
	}
	public String getItype() {
		return itype;
	}
	public void setItype(String itype) {
		this.itype = itype;
	}
}

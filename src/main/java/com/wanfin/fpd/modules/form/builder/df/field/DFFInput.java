/**  
 * @Project fpd 
 * @Title DFFInput.java
 * @Package com.wanfin.fpd.modules.form.builder.df.field
 * @Description [[_自定义表单组件-文本框_]]文件 
 * @author Chenh
 * @date 2016年4月28日 下午1:21:47 
 * @version V1.0   
 */ 
package com.wanfin.fpd.modules.form.builder.df.field;

import java.io.Serializable;

import com.wanfin.fpd.common.utils.StringUtils;
import com.wanfin.fpd.modules.form.builder.df.group.DFFGroup;


/**
 * @Description [[_自定义表单组件-文本框_]] DFFInput类
 * @author Chenh
 * @date 2016年4月28日 下午1:21:47 
 */
public class DFFInput extends DFF<String> implements Serializable{
	private static final long serialVersionUID = 1L;
	private String value;
	
	
	/** 构造方法 ***********************************************************************************/
	/**
	 * @Description [[_xxx_]]构造器
	 * @author Chenh
	 * @date 2015-1-15 下午3:39:14  
	 */
	public DFFInput() {
		super();
	}
	/**
	 * @Description [[_xxx_]]构造器
	 * @author Chenh
	 * @date 2015-1-15 下午3:39:14  
	 */
	public DFFInput(String name, String label, String type, String value) {
		super(name, label, type);
		this.value = value;
	}
	
	/**
	 * @Description [[_xxx_]]构造器
	 * @author Chenh
	 * @date 2015-1-15 下午3:39:14  
	 */
	public DFFInput(String label, String type, String value) {
		super(label, type);
		this.value = value;
	}
	
	@Override
	public String getValue() {
		if(this.value == null){
			this.value = "";
		}
		return this.value;
	}

	@Override
	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String getType() {
		if (!StringUtils.isNotEmpty(super.getType())) {
			return DFFGroup.DFCtype.INPUT.getType();
		}
		return super.getType();
	}
}
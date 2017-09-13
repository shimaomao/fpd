/**  
 * @Project fpd 
 * @Title DFFTextArea.java
 * @Package com.wanfin.fpd.modules.form.builder.df.field
 * @Description [[_自定义表单组件-文本域_]]文件 
 * @author Chenh
 * @date 2016年4月28日 下午2:07:30 
 * @version V1.0   
 */ 
package com.wanfin.fpd.modules.form.builder.df.field;

import java.io.Serializable;

import com.wanfin.fpd.common.utils.StringUtils;
import com.wanfin.fpd.modules.form.builder.df.group.DFFGroup;

/**
 * @Description [[_自定义表单组件-文本域_]] DFFTextArea类
 * @author Chenh
 * @date 2016年4月28日 下午2:07:30 
 */
public class DFFTextArea extends DFF<String> implements Serializable{
	private static final long serialVersionUID = 1L;
	private String value;

	/** 构造方法 ***********************************************************************************/
	/**
	 * @Description [[_xxx_]]构造器
	 * @author Chenh
	 * @date 2015-1-15 下午3:39:14  
	 */
	public DFFTextArea(String name, String label, String type, String value) {
		super(name, label, type);
		this.value = value;
	}
	/**
	 * @Description [[_xxx_]]构造器
	 * @author Chenh
	 * @date 2015-1-15 下午3:39:14  
	 */
	public DFFTextArea() {
		super();
	}
	/**
	 * @Description [[_xxx_]]构造器
	 * @author Chenh
	 * @date 2015-1-15 下午3:39:14  
	 */
	public DFFTextArea(String label, String type, String value) {
		super(label, type);
		this.value = value;
	}
	
	/** 
	 * @Description TODO
	 * @Title: getValue
	 * @see com.wanfin.fpd.modules.form.builder.df.field.DFF#getValue()
	 * @return
	 * @author Chenh 
	 * @date 2016年4月28日 下午2:07:30  
	 */
	@Override
	public String getValue() {
		if(this.value == null){
			this.value = "";
		}
		return this.value;
	}

	/** 
	 * @Description TODO
	 * @Title: setValue
	 * @see com.wanfin.fpd.modules.form.builder.df.field.DFF#setValue(java.lang.Object)
	 * @param value
	 * @author Chenh 
	 * @date 2016年4月28日 下午2:07:30  
	 */
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

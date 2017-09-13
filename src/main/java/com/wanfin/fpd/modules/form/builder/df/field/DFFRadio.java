/**  
 * @Project fpd 
 * @Title DFFRadio.java
 * @Package com.wanfin.fpd.modules.form.builder.df.field
 * @Description [[_自定义表单组件-单选按钮_]]文件 
 * @author Chenh
 * @date 2016年4月28日 下午1:42:37 
 * @version V1.0   
 */ 
package com.wanfin.fpd.modules.form.builder.df.field;

import java.io.Serializable;

import com.wanfin.fpd.common.utils.StringUtils;
import com.wanfin.fpd.modules.form.builder.df.group.DFFGroup;

/**
 * @Description [[_自定义表单组件-单选按钮_]] DFFRadio类
 * @author Chenh
 * @date 2016年4月28日 下午1:42:37 
 */
public class DFFRadio extends DFF<Boolean> implements Serializable{
	private static final long serialVersionUID = 1L;
	private Boolean value;
	
	/** 构造方法 ***********************************************************************************/
	/**
	 * @Description [[_xxx_]]构造器
	 * @author Chenh
	 * @date 2015-1-15 下午3:39:14  
	 */
	public DFFRadio() {
		super();
	}

	/**
	 * @Description [[_xxx_]]构造器
	 * @author Chenh
	 * @date 2015-1-15 下午3:39:14  
	 */
	public DFFRadio(String label, String type) {
		super(label, type);
	}

	/**
	 * @Description [[_xxx_]]构造器
	 * @author Chenh
	 * @date 2015-1-15 下午3:39:14  
	 */
	public DFFRadio(String label, String type, Boolean value) {
		super(label, type);
		this.value = value;
	}
	/**
	 * @Description [[_xxx_]]构造器
	 * @author Chenh
	 * @date 2015-1-15 下午3:39:14  
	 */
	public DFFRadio(String name, String label, String type, Boolean value) {
		super(name, label, type);
		this.value = value;
	}

	/** 
	 * @Description TODO
	 * @Title: getValue
	 * @see com.wanfin.fpd.modules.form.builder.df.field.DFF#getValue()
	 * @return
	 * @author Chenh 
	 * @date 2016年4月28日 下午1:42:37  
	 */
	@Override
	public Boolean getValue() {
		if(this.value == null){
			this.value = false;
		}
		return value;
	}
	
	/** 
	 * @Description TODO
	 * @Title: setValue
	 * @see com.wanfin.fpd.modules.form.builder.df.field.DFF#setValue(java.lang.Object)
	 * @param value
	 * @author Chenh 
	 * @date 2016年4月28日 下午1:42:37  
	 */
	@Override
	public void setValue(Boolean value) {
		this.value = value;
	}
	
	@Override
	public String getType() {
		if (!StringUtils.isNotEmpty(super.getType())) {
			return DFFGroup.DFCtype.RADIO.getType();
		}
		return super.getType();
	}
}

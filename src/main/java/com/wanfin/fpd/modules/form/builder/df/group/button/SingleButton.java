/**  
 * @Project fpd 
 * @Title SingleButton.java
 * @Package com.wanfin.fpd.modules.form.builder.df.group.button
 * @Description [[_自定义表单-单按钮_]]文件 
 * @author Chenh
 * @date 2016年4月28日 下午5:25:51 
 * @version V1.0   
 */ 
package com.wanfin.fpd.modules.form.builder.df.group.button;

import java.io.Serializable;

import com.wanfin.fpd.modules.form.builder.df.field.DFFInput;
import com.wanfin.fpd.modules.form.builder.df.field.DFFSelect;
import com.wanfin.fpd.modules.form.builder.df.group.DFFGroup;

/**
 * @Description [[_自定义表单-单按钮_]] SingleButton类
 * @author Chenh
 * @date 2016年4月28日 下午5:25:51 
 */
public class SingleButton extends DFFGroup implements Serializable{
	private static final long serialVersionUID = 1L;
	public DFFInput id;
	public DFFInput label;
	public DFFInput buttonlabel;
	public DFFSelect buttontype;
	public DFFInput getId() {
		return id;
	}
	public void setId(DFFInput id) {
		this.id = id;
	}
	public DFFInput getLabel() {
		return label;
	}
	public void setLabel(DFFInput label) {
		this.label = label;
	}
	public DFFInput getButtonlabel() {
		return buttonlabel;
	}
	public void setButtonlabel(DFFInput buttonlabel) {
		this.buttonlabel = buttonlabel;
	}
	public DFFSelect getButtontype() {
		return buttontype;
	}
	public void setButtontype(DFFSelect buttontype) {
		this.buttontype = buttontype;
	}
}

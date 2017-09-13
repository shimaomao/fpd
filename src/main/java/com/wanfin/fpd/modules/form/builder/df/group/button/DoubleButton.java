/**  
 * @Project fpd 
 * @Title DoubleButton.java
 * @Package com.wanfin.fpd.modules.form.builder.df.group.button
 * @Description [[_自定义表单-双按钮_]]文件 
 * @author Chenh
 * @date 2016年4月28日 下午5:28:42 
 * @version V1.0   
 */ 
package com.wanfin.fpd.modules.form.builder.df.group.button;

import java.io.Serializable;

import com.wanfin.fpd.modules.form.builder.df.field.DFFInput;
import com.wanfin.fpd.modules.form.builder.df.field.DFFSelect;

/**
 * @Description [[_自定义表单-双按钮_]] DoubleButton类
 * @author Chenh
 * @date 2016年4月28日 下午5:28:42 
 */
public class DoubleButton implements Serializable{
	private static final long serialVersionUID = 1L;
	public DFFInput id;
	public DFFInput label;
	public DFFInput buttonlabel;
	public DFFSelect buttontype;
	public DFFInput id2;
	public DFFInput button2label;
	public DFFSelect button2type;
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
	public DFFInput getId2() {
		return id2;
	}
	public void setId2(DFFInput id2) {
		this.id2 = id2;
	}
	public DFFInput getButton2label() {
		return button2label;
	}
	public void setButton2label(DFFInput button2label) {
		this.button2label = button2label;
	}
	public DFFSelect getButton2type() {
		return button2type;
	}
	public void setButton2type(DFFSelect button2type) {
		this.button2type = button2type;
	}
}
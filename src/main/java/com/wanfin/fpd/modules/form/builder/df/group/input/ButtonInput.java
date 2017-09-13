/**  
 * @Project fpd 
 * @Title ButtonInput.java
 * @Package com.wanfin.fpd.modules.form.builder.df.group.input
 * @Description [[_自定义表单-按钮文本框_]]文件 
 * @author Chenh
 * @date 2016年4月29日 上午11:08:25 
 * @version V1.0   
 */ 
package com.wanfin.fpd.modules.form.builder.df.group.input;

import java.io.Serializable;

import com.wanfin.fpd.modules.form.builder.df.field.DFFCheckBox;
import com.wanfin.fpd.modules.form.builder.df.field.DFFInput;
import com.wanfin.fpd.modules.form.builder.df.field.DFFSelect;
import com.wanfin.fpd.modules.form.builder.df.field.DFFTextAreaSplit;
import com.wanfin.fpd.modules.form.builder.df.group.DFFGroup;

/**
 * @Description [[_自定义表单-按钮文本框_]] ButtonInput类
 * @author Chenh
 * @date 2016年4月29日 上午11:08:25 
 */
public class ButtonInput extends DFFGroup implements Serializable{
	private static final long serialVersionUID = 1L;
	public DFFInput id;
	public DFFInput label;
	public DFFInput placeholder;
	public DFFInput buttontext;
	public DFFTextAreaSplit buttonoptions;
	public DFFCheckBox required;
	public DFFSelect inputsize;
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
	public DFFInput getPlaceholder() {
		return placeholder;
	}
	public void setPlaceholder(DFFInput placeholder) {
		this.placeholder = placeholder;
	}
	public DFFInput getButtontext() {
		return buttontext;
	}
	public void setButtontext(DFFInput buttontext) {
		this.buttontext = buttontext;
	}
	public DFFTextAreaSplit getButtonoptions() {
		return buttonoptions;
	}
	public void setButtonoptions(DFFTextAreaSplit buttonoptions) {
		this.buttonoptions = buttonoptions;
	}
	public DFFCheckBox getRequired() {
		return required;
	}
	public void setRequired(DFFCheckBox required) {
		this.required = required;
	}
	public DFFSelect getInputsize() {
		return inputsize;
	}
	public void setInputsize(DFFSelect inputsize) {
		this.inputsize = inputsize;
	}
}

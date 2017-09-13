/**  
 * @Project fpd 
 * @Title TextInput.java
 * @Package com.wanfin.fpd.modules.form.builder.df.group.input
 * @Description [[_自定义表单-文本框_]]文件 
 * @author Chenh
 * @date 2016年4月28日 下午5:54:20 
 * @version V1.0   
 */ 
package com.wanfin.fpd.modules.form.builder.df.group.input;

import java.io.Serializable;

import com.wanfin.fpd.modules.form.builder.df.field.DFFCheckBox;
import com.wanfin.fpd.modules.form.builder.df.field.DFFInput;
import com.wanfin.fpd.modules.form.builder.df.field.DFFSelect;
import com.wanfin.fpd.modules.form.builder.df.group.DFFGroup;

/**
 * @Description [[_自定义表单-日期控件_]] WdatePicker类
 * @author zzm
 * @date 2016-5-10 下午2:38:38 
 */
public class WdatePicker extends DFFGroup implements Serializable{
	private static final long serialVersionUID = 1L;
	public DFFInput id;
	public DFFInput label;
	public DFFInput formate;
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
	public DFFInput getFormate() {
		return formate;
	}
	public void setFormate(DFFInput formate) {
		this.formate = formate;
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

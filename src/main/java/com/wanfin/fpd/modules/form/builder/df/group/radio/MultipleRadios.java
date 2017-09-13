/**  
 * @Project fpd 
 * @Title MultipleRadios.java
 * @Package com.wanfin.fpd.modules.form.builder.df.group.radio
 * @Description [[_自定义表单-多行单选按钮_]]文件 
 * @author Chenh
 * @date 2016年4月29日 上午11:14:23 
 * @version V1.0   
 */ 
package com.wanfin.fpd.modules.form.builder.df.group.radio;

import java.io.Serializable;

import com.wanfin.fpd.modules.form.builder.df.field.DFFCheckBox;
import com.wanfin.fpd.modules.form.builder.df.field.DFFInput;
import com.wanfin.fpd.modules.form.builder.df.field.DFFTextAreaSplit;
import com.wanfin.fpd.modules.form.builder.df.group.DFFGroup;

/**
 * @Description [[_自定义表单-多行单选按钮_]] MultipleRadios类
 * @author Chenh
 * @date 2016年4月29日 上午11:14:23 
 */
public class MultipleRadios extends DFFGroup implements Serializable{
	private static final long serialVersionUID = 1L;
	public DFFInput id;
	public DFFInput label;
	public DFFCheckBox required;
	public DFFTextAreaSplit radios;
	public DFFInput url;
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
	public DFFCheckBox getRequired() {
		return required;
	}
	public void setRequired(DFFCheckBox required) {
		this.required = required;
	}
	public DFFTextAreaSplit getRadios() {
		return radios;
	}
	public void setRadios(DFFTextAreaSplit radios) {
		this.radios = radios;
	}
	public DFFInput getUrl() {
		return url;
	}
	public void setUrl(DFFInput url) {
		this.url = url;
	}
}
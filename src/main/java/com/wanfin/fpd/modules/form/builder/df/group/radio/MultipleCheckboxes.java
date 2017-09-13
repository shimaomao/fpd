/**  
 * @Project fpd 
 * @Title MultipleCheckboxes.java
 * @Package com.wanfin.fpd.modules.form.builder.df.group.radio
 * @Description [[_自定义表单-多行选择框_]]文件 
 * @author Chenh
 * @date 2016年4月29日 上午11:18:55 
 * @version V1.0   
 */ 
package com.wanfin.fpd.modules.form.builder.df.group.radio;

import java.io.Serializable;

import com.wanfin.fpd.modules.form.builder.df.field.DFFCheckBox;
import com.wanfin.fpd.modules.form.builder.df.field.DFFInput;
import com.wanfin.fpd.modules.form.builder.df.field.DFFTextAreaSplit;
import com.wanfin.fpd.modules.form.builder.df.group.DFFGroup;

/**
 * @Description [[_自定义表单-多行选择框_]] MultipleCheckboxes类
 * @author Chenh
 * @date 2016年4月29日 上午11:18:55 
 */
public class MultipleCheckboxes extends DFFGroup  implements Serializable{
	private static final long serialVersionUID = 1L;
	public DFFInput id;
	public DFFInput label;
	public DFFCheckBox required;
	public DFFTextAreaSplit checkboxes;
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
	public DFFTextAreaSplit getCheckboxes() {
		return checkboxes;
	}
	public void setCheckboxes(DFFTextAreaSplit checkboxes) {
		this.checkboxes = checkboxes;
	}
	public DFFInput getUrl() {
		return url;
	}
	public void setUrl(DFFInput url) {
		this.url = url;
	}
}
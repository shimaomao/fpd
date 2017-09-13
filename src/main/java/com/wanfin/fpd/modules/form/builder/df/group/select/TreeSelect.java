/**  
 * @Project fpd 
 * @Title Select.java
 * @Package com.wanfin.fpd.modules.form.builder.df.group.select
 * @Description [[_自定义表单-下拉框_]]文件 
 * @author Chenh
 * @date 2016年4月29日 上午11:21:55 
 * @version V1.0   
 */ 
package com.wanfin.fpd.modules.form.builder.df.group.select;

import java.io.Serializable;

import com.wanfin.fpd.modules.form.builder.df.field.DFFCheckBox;
import com.wanfin.fpd.modules.form.builder.df.field.DFFInput;
import com.wanfin.fpd.modules.form.builder.df.field.DFFSelect;
import com.wanfin.fpd.modules.form.builder.df.group.DFFGroup;

/**
 * @Description [[_自定义表单-树状选择框_]] TreeSelect类
 * @author zzm
 * @date 2016-5-17 下午5:13:13 
 */
public class TreeSelect extends DFFGroup  implements Serializable{
	private static final long serialVersionUID = 1L;
	private DFFInput id;
	private DFFInput label;
	private DFFInput name;
	private DFFInput value;
	private DFFInput labelName;
	private DFFInput labelValue;
	private DFFInput url;
	private DFFCheckBox required;
	private DFFSelect inputsize;
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
	public DFFSelect getInputsize() {
		return inputsize;
	}
	public void setInputsize(DFFSelect inputsize) {
		this.inputsize = inputsize;
	}
	public DFFInput getUrl() {
		return url;
	}
	public void setUrl(DFFInput url) {
		this.url = url;
	}
	public DFFInput getName() {
		return name;
	}
	public void setName(DFFInput name) {
		this.name = name;
	}
	public DFFInput getValue() {
		return value;
	}
	public void setValue(DFFInput value) {
		this.value = value;
	}
	public DFFInput getLabelName() {
		return labelName;
	}
	public void setLabelName(DFFInput labelName) {
		this.labelName = labelName;
	}
	public DFFInput getLabelValue() {
		return labelValue;
	}
	public void setLabelValue(DFFInput labelValue) {
		this.labelValue = labelValue;
	}
	public DFFCheckBox getRequired() {
		return required;
	}
	public void setRequired(DFFCheckBox required) {
		this.required = required;
	}
	
}
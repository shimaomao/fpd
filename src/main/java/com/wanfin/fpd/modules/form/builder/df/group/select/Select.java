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

import com.wanfin.fpd.modules.form.builder.df.field.DFFInput;
import com.wanfin.fpd.modules.form.builder.df.field.DFFSelect;
import com.wanfin.fpd.modules.form.builder.df.field.DFFTextAreaSplit;
import com.wanfin.fpd.modules.form.builder.df.group.DFFGroup;

/**
 * @Description [[_自定义表单-下拉框_]] Select类
 * @author Chenh
 * @date 2016年4月29日 上午11:21:55 
 */
public class Select extends DFFGroup  implements Serializable{
	private static final long serialVersionUID = 1L;
	public DFFInput id;
	public DFFInput label;
	public DFFInput url;
	public DFFTextAreaSplit options;
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
	public DFFTextAreaSplit getOptions() {
		return options;
	}
	public void setOptions(DFFTextAreaSplit options) {
		this.options = options;
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
}
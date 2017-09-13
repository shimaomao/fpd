/**  
 * @Project fpd 
 * @Title TextArea.java
 * @Package com.wanfin.fpd.modules.form.builder.df.group.input
 * @Description [[_自定义表单-文本域_]]文件 
 * @author Chenh
 * @date 2016年4月29日 上午11:12:09 
 * @version V1.0   
 */ 
package com.wanfin.fpd.modules.form.builder.df.group.input;

import java.io.Serializable;

import com.wanfin.fpd.modules.form.builder.df.field.DFFInput;
import com.wanfin.fpd.modules.form.builder.df.field.DFFTextArea;
import com.wanfin.fpd.modules.form.builder.df.group.DFFGroup;

/**
 * @Description [[_自定义表单-文本域_]] TextArea类
 * @author Chenh
 * @date 2016年4月29日 上午11:12:09 
 */
public class TextArea extends DFFGroup implements Serializable{
	private static final long serialVersionUID = 1L;
	public DFFInput id;
	public DFFInput label;
	public DFFTextArea textarea;
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
	public DFFTextArea getTextarea() {
		return textarea;
	}
	public void setTextarea(DFFTextArea textarea) {
		this.textarea = textarea;
	}
}

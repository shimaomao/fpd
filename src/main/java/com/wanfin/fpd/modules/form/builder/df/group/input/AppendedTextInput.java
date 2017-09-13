/**  
 * @Project fpd 
 * @Title AppendedTextInput.java
 * @Package com.wanfin.fpd.modules.form.builder.df.group.input
 * @Description [[_自定义表单-后缀文本框_]]文件 
 * @author Chenh
 * @date 2016年4月29日 上午11:03:30 
 * @version V1.0   
 */ 
package com.wanfin.fpd.modules.form.builder.df.group.input;

import java.io.Serializable;

import com.wanfin.fpd.modules.form.builder.df.field.DFFInput;

/**
 * @Description [[_自定义表单-后缀文本框_]] AppendedTextInput类
 * @author Chenh
 * @date 2016年4月29日 上午11:03:30 
 */
public class AppendedTextInput extends TextInput implements Serializable{
	private static final long serialVersionUID = 1L;
	public DFFInput append;

	public DFFInput getAppend() {
		return append;
	}

	public void setAppend(DFFInput append) {
		this.append = append;
	}
}

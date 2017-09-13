/**  
 * @Project fpd 
 * @Title PrependedTextInput.java
 * @Package com.wanfin.fpd.modules.form.builder.df.group.input
 * @Description [[_自定义表单-前缀文本框_]]文件 
 * @author Chenh
 * @date 2016年4月29日 上午11:01:35 
 * @version V1.0   
 */ 
package com.wanfin.fpd.modules.form.builder.df.group.input;

import java.io.Serializable;

import com.wanfin.fpd.modules.form.builder.df.field.DFFInput;

/**
 * @Description [[_自定义表单-前缀文本框_]] PrependedTextInput类
 * @author Chenh
 * @date 2016年4月29日 上午11:01:35 
 */
public class PrependedTextInput extends TextInput implements Serializable{
	private static final long serialVersionUID = 1L;
	public DFFInput prepend;

	public DFFInput getPrepend() {
		return prepend;
	}

	public void setPrepend(DFFInput prepend) {
		this.prepend = prepend;
	}
}
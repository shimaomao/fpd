/**  
 * @Project fpd 
 * @Title PrependedCheckboxInput.java
 * @Package com.wanfin.fpd.modules.form.builder.df.group.input
 * @Description [[_自定义表单-前缀选择框_]]文件 
 * @author Chenh
 * @date 2016年4月29日 上午11:05:26 
 * @version V1.0   
 */ 
package com.wanfin.fpd.modules.form.builder.df.group.input;

import java.io.Serializable;

import com.wanfin.fpd.modules.form.builder.df.field.DFFCheckBox;

/**
 * @Description [[_自定义表单-前缀选择框_]] PrependedCheckboxInput类
 * @author Chenh
 * @date 2016年4月29日 上午11:05:26 
 */
public class PrependedCheckboxInput extends PrependedTextInput implements Serializable{
	private static final long serialVersionUID = 1L;
	public DFFCheckBox checked;

	public DFFCheckBox getChecked() {
		return checked;
	}

	public void setChecked(DFFCheckBox checked) {
		this.checked = checked;
	}
}

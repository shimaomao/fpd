/**  
 * @Project fpd 
 * @Title AppendedCheckboxInput.java
 * @Package com.wanfin.fpd.modules.form.builder.df.group.input
 * @Description [[_自定义表单-后缀选择框_]]文件 
 * @author Chenh
 * @date 2016年4月29日 上午11:07:00 
 * @version V1.0   
 */ 
package com.wanfin.fpd.modules.form.builder.df.group.input;

import java.io.Serializable;

import com.wanfin.fpd.modules.form.builder.df.field.DFFCheckBox;

/**
 * @Description [[_自定义表单-后缀选择框_]] AppendedCheckboxInput类
 * @author Chenh
 * @date 2016年4月29日 上午11:07:00 
 */
public class AppendedCheckboxInput extends AppendedTextInput implements Serializable{
	private static final long serialVersionUID = 1L;
	public DFFCheckBox checked;

	public DFFCheckBox getChecked() {
		return checked;
	}

	public void setChecked(DFFCheckBox checked) {
		this.checked = checked;
	}
}

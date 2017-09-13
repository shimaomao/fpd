/**  
 * @Project fpd 
 * @Title FileButton.java
 * @Package com.wanfin.fpd.modules.form.builder.df.group.button
 * @Description [[_自定义表单-文件框_]]文件 
 * @author Chenh
 * @date 2016年4月28日 下午5:21:36 
 * @version V1.0   
 */ 
package com.wanfin.fpd.modules.form.builder.df.group.button;

import java.io.Serializable;

import com.wanfin.fpd.modules.form.builder.df.field.DFFInput;
import com.wanfin.fpd.modules.form.builder.df.group.DFFGroup;

/**
 * @Description [[_自定义表单-文件框_]] FileButton类
 * @author Chenh
 * @date 2016年4月28日 下午5:21:36 
 */
public class FileButton extends DFFGroup implements Serializable{
	private static final long serialVersionUID = 1L;
	public DFFInput id;
	public DFFInput label;
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
}

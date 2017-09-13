/**  
 * @Project fpd 
 * @Title DFTabItem.java
 * @Package com.wanfin.fpd.modules.form.builder.df.plugin.tab
 * @Description [[_自定义表单Tab组件列_]]文件 
 * @author Chenh
 * @date 2016年4月28日 下午2:43:47 
 * @version V1.0   
 */ 
package com.wanfin.fpd.modules.form.builder.df.plugin.tab;

import java.io.Serializable;
import java.util.List;

/**
 * @Description [[_自定义表单Tab组件列_]] DFTabItem类
 * @author Chenh
 * @date 2016年4月28日 下午2:43:47 
 */
public class DFTabItem implements Serializable{
	private static final long serialVersionUID = 1L;
	private String title;
	private List<DFTabBox> boxs;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<DFTabBox> getBoxs() {
		return boxs;
	}

	public void setBoxs(List<DFTabBox> boxs) {
		this.boxs = boxs;
	}
}
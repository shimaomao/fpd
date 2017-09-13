/**  
 * @Project fpd 
 * @Title DFTab.java
 * @Package com.wanfin.fpd.modules.form.builder.df.plugin.tab
 * @Description [[_自定义表单Tab组件_]]文件 
 * @author Chenh
 * @date 2016年4月28日 下午2:43:20 
 * @version V1.0   
 */ 
package com.wanfin.fpd.modules.form.builder.df.plugin.tab;

import java.io.Serializable;
import java.util.List;

import com.wanfin.fpd.modules.form.builder.df.DFPlugin;

/**
 * @Description [[_自定义表单Tab组件_]] DFTab类
 * @author Chenh
 * @date 2016年4月28日 下午2:43:20 
 */
public class DFTab implements DFPlugin, Serializable{
	private static final long serialVersionUID = 1L;
	private List<DFTabItem> tabItems;

	@Override
	public String getType() {
		return DFPlugin.DFPType.TAB.getKey();
	}

	public List<DFTabItem> getTabItems() {
		return tabItems;
	}

	public void setTabItems(List<DFTabItem> tabItems) {
		this.tabItems = tabItems;
	}
}
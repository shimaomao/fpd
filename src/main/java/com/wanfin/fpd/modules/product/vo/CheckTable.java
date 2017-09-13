/**  
 * @Project fpd 
 * @Title CheckTable.java
 * @Package com.wanfin.fpd.modules.product.vo
 * @Description [[_xxx_]]文件 
 * @author Chenh
 * @date 2016年5月23日 下午3:55:16 
 * @version V1.0   
 */ 
package com.wanfin.fpd.modules.product.vo;

import java.util.List;
import java.util.Map;

/**
 * @Description [[_xxx_]] CheckTable类
 * @author Chenh
 * @date 2016年5月23日 下午3:55:16 
 */
public class CheckTable {
	private String selector;
	private String title;
	private String thead;
	private Map<String, List<CheckTableGroups>> tbody;
	private String tfoot;
	public String getSelector() {
		return selector;
	}
	public String getTitle() {
		return title;
	}
	public String getThead() {
		return thead;
	}
	public Map<String, List<CheckTableGroups>> getTbody() {
		return tbody;
	}
	public String getTfoot() {
		return tfoot;
	}
	public void setSelector(String selector) {
		this.selector = selector;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setThead(String thead) {
		this.thead = thead;
	}
	public void setTbody(Map<String, List<CheckTableGroups>> tbody) {
		this.tbody = tbody;
	}
	public void setTfoot(String tfoot) {
		this.tfoot = tfoot;
	}
}
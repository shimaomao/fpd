/**  
 * @Project fpd 
 * @Title CMap.java
 * @Package com.wanfin.fpd.modules.wind.adapter
 * @Description [[_xxx_]]文件 
 * @author Chenh
 * @date 2016年6月2日 下午6:05:13 
 * @version V1.0   
 */ 
package com.wanfin.fpd.modules.wind.adapter;

/**
 * @Description [[_xxx_]] CMap类
 * @author Chenh
 * @date 2016年6月2日 下午6:05:13 
 */
public class CMap{ 
	private String key;
	private Object val;
	private String remark;
	/** 构造方法 ***********************************************************************************/
	/**
	 * @Description [[_xxx_]]构造器
	 * @author Chenh
	 * @date 2015-1-15 下午3:39:14  
	 */
	public CMap(String key, Object val, String remark) {
		this.key = key;
		this.val = val;
		this.remark = remark;
	}
	public String getKey() {
		return key;
	}
	public Object getVal() {
		return val;
	}
	public String getRemark() {
		return remark;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public void setVal(Object val) {
		this.val = val;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
}
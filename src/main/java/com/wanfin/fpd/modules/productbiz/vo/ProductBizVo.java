/**  
 * @Project fpd 
 * @Title TProductBizVo.java
 * @Package com.wanfin.fpd.modules.productbiz.vo
 * @Description [[_xxx_]]文件 
 * @author Chenh
 * @date 2016年5月24日 下午1:10:11 
 * @version V1.0   
 */ 
package com.wanfin.fpd.modules.productbiz.vo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wanfin.fpd.common.config.Global;
import com.wanfin.fpd.modules.productbiz.entity.TProductBiz;

/**
 * @Description [[_xxx_]] TProductBizVo类
 * @author Chenh
 * @date 2016年5月24日 下午1:10:11 
 */
public class ProductBizVo extends TProductBiz{
	/**
	 * @Description serialVersionUID属性
	 * @Fields long serialVersionUID
	 * @author Chenh
	 * @date 2016年5月24日 下午1:12:26 
	 */
	private static final long serialVersionUID = 1L;
	
	private String cfgId;
	private String productId;
	private String procDefId;
	private String procDefName;
	private String riskId;
	public String getCfgId() {
		return cfgId;
	}
	public String getProductId() {
		return productId;
	}
	public String getProcDefId() {
		return procDefId;
	}
	public String getRiskId() {
		return riskId;
	}
	public void setCfgId(String cfgId) {
		this.cfgId = cfgId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public void setProcDefId(String procDefId) {
		this.procDefId = procDefId;
	}
	public void setRiskId(String riskId) {
		this.riskId = riskId;
	}
	
	@JsonIgnore
	public static void sortList2(List<ProductBizVo> list, List<ProductBizVo> sourcelist, String parentId, boolean cascade){
		for (int i=0; i<sourcelist.size(); i++){
			ProductBizVo e = sourcelist.get(i);
			if (e.getParent()!=null && e.getParent().getId()!=null
					&& e.getParent().getId().equals(parentId)){
				list.add(e);
				if (cascade){
					// 判断是否还有子节点, 有则继续获取子节点
					for (int j=0; j<sourcelist.size(); j++){
						TProductBiz child = sourcelist.get(j);
						if (child.getParent()!=null && child.getParent().getId()!=null && child.getParent().getId().equals(e.getId())){
							sortList2(list, sourcelist, e.getId(), true);
							break;
						}
					}
				}
			}
		}
	}
	
	public String getProcDefName() {
		return procDefName;
	}
	public void setProcDefName(String procDefName) {
		this.procDefName = procDefName;
	}
	@JsonIgnore
	public static String getRootId(){
		return Global.ROOT_ID;
	}
}

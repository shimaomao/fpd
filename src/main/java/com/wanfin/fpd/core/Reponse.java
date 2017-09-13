/**    
* @Project: fpd 
* @Title: YYYYY 
* @Package com.wanfin.fpd.core 
* @Description [[_XXXXX_]]文件
* @author Chenh 
* @date 2016年8月24日 上午10:41:52   
* @version V1.0.0   */ 
package com.wanfin.fpd.core;

import java.util.HashMap;

import com.wanfin.fpd.common.config.SvalBase;

/**   
 * @author Chenh  
 * @date 2016年8月24日 上午10:41:52 
 * @Description [[_XXXXX_]] YYYYY类
 * TODO 
 *   
 */
public class Reponse {
	private String status; 
	private String statusType; 
	private String entityType; 
	private HashMap<String, Object> entity;

	public Reponse() {
		super();
	}
	public Reponse(String status, String statusType, String entityType,
			HashMap<String, Object> entity) {
		super();
		this.status = status;
		this.statusType = statusType;
		this.entityType = entityType;
		this.entity = entity;
		entity.put(SvalBase.JsonKey.KEY_IS_TRUE, true);
		entity.put(SvalBase.JsonKey.KEY_MSG, "Http请求成功！");
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStatusType() {
		return statusType;
	}
	public void setStatusType(String statusType) {
		this.statusType = statusType;
	}
	public String getEntityType() {
		return entityType;
	}
	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}
	public HashMap<String, Object> getEntity() {
		return entity;
	}
	public void setEntity(HashMap<String, Object> entity) {
		this.entity = entity;
	}
}

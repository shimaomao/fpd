/**  
 * @Project fpd 
 * @Title WindVo.java
 * @Package com.wanfin.fpd.modules.wind.util
 * @Description [[_xxx_]]文件 
 * @author Chenh
 * @date 2016年5月27日 下午5:21:01 
 * @version V1.0   
 */ 
package com.wanfin.fpd.modules.wind.adapter;

import org.json.JSONObject;

/**
 * @Description [[_xxx_]] WindVo类
 * @author Chenh
 * @date 2016年5月27日 下午5:21:01 
 */
public class WindVo{
	private String relId;
	private String returnCode;
	private String message;
	private JSONObject result;
	public String getReturnCode() {
		return returnCode;
	}
	public String getMessage() {
		return message;
	}
	public JSONObject getResult() {
		return result;
	}
	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public void setResult(JSONObject result) {
		this.result = result;
	}
	public String getRelId() {
		return relId;
	}
	public void setRelId(String relId) {
		this.relId = relId;
	}
}

/**    
* @Project: fpd 
* @Title: YYYYY 
* @Package com.wanfin.fpd.modules.auto.vo 
* @Description [[_返回结果类_]]文件
* @author Chenh 
* @date 2016年9月8日 下午1:12:02   
* @version V1.0.0   */ 
package com.wanfin.fpd.modules.auto.vo;

import org.restlet.data.Status;

import com.alibaba.fastjson.JSONObject;

/**   
 * @author Chenh  
 * @date 2016年9月8日 下午1:12:02 
 * @Description [[_返回结果类_]] RtResult类
 * TODO 
 *   
 */
public class RtResult {
	private boolean istrue;
	private String msg;
	private Status status;
	private JSONObject result;
	
	public RtResult() {
		super();
	}
	public RtResult(boolean istrue) {
		super();
		this.istrue = istrue;
		if(this.istrue){
			this.status = Status.SUCCESS_OK;
		}
	}
	public RtResult(boolean istrue, Status status, JSONObject result) {
		super();
		this.istrue = istrue;
		this.result = result;
	}
	public RtResult(boolean istrue, String msg, Status status, JSONObject result) {
		super();
		this.istrue = istrue;
		this.msg = msg;
		this.status = status;
		this.result = result;
	}
	public boolean isIstrue() {
		return istrue;
	}
	public void setIstrue(boolean istrue) {
		this.istrue = istrue;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public JSONObject getResult() {
		return result;
	}
	public void setResult(JSONObject result) {
		this.result = result;
	}
}

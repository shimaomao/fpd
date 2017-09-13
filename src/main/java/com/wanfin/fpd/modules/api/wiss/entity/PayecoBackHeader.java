package com.wanfin.fpd.modules.api.wiss.entity;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.wanfin.fpd.common.utils.DateUtils;

public class PayecoBackHeader implements Serializable{
	private static final long serialVersionUID = 1L;
	private String tradeId;// 交易码
	private String pvdId;//商户编号  易联分配给商户的编号
	private Date reqTime;//请求时间戳
	private Date resTime;//响应时间戳
	private String resCode;//返回码 0000:表示成功，其他为失败。
	private String resMsg;//返回信息
	public String getTradeId() {
		return tradeId;
	}
	public void setTradeId(String tradeId) {
		this.tradeId = tradeId;
	}
	public String getPvdId() {
		return pvdId;
	}
	public void setPvdId(String pvdId) {
		this.pvdId = pvdId;
	}
	public Date getReqTime() {
		return reqTime;
	}
	public void setReqTime(Date reqTime) {
		this.reqTime = reqTime;
	}
	public void setReqTime(String reqTime) {
		if(StringUtils.isNotBlank(reqTime)){
			this.reqTime = DateUtils.stringToDate(reqTime, "yyyyMMddHHmmss");
		}
	}
	public Date getResTime() {
		return resTime;
	}
	public void setResTime(Date resTime) {
		this.resTime = resTime;
	}
	public void setResTime(String resTime) {
		if(StringUtils.isNotBlank(resTime)){
			this.resTime = DateUtils.stringToDate(resTime, "yyyyMMddHHmmss");
		}
	}
	public String getResCode() {
		return resCode;
	}
	public void setResCode(String resCode) {
		this.resCode = resCode;
	}
	public String getResMsg() {
		return resMsg;
	}
	public void setResMsg(String resMsg) {
		this.resMsg = resMsg;
	}
	@Override
	public String toString() {
		return "PayecoBackHeader [tradeId=" + tradeId + ", pvdId=" + pvdId + ", reqTime=" + reqTime + ", resTime="
				+ resTime + ", resCode=" + resCode + ", resMsg=" + resMsg + "]";
	}
}

package com.wanfin.fpd.modules.api.wiss.entity;

import java.io.Serializable;

import com.wanfin.fpd.common.utils.StringUtils;

public class ReceiveParams implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String tradeId;//接口码。易联根据接口码识别交易请求. regNotify&
	private String version;//接口版本号。填写：1.0. 1.0&
	private String pvdId;//贷款业务提供商接入编号. wz&
	private String data;//json格式的业务报文。需要对json发送包进行UTF8编码的UrlEncode. {"header": {"tradeId": "regNotify", "pvdID": "WZ","reqTime": "20160215122334"}, "body": { "uid":"","pvdUid":"","regStatus":"","reqOrdersNo":""}}&
	private String sign;//商户使用私钥对data的值进行签名
	public String getTradeId() {
		return tradeId;
	}
	public void setTradeId(String tradeId) {
		this.tradeId = tradeId;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getPvdId() {
		return pvdId;
	}
	public void setPvdId(String pvdId) {
		this.pvdId = pvdId;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		if(StringUtils.isNotBlank(data)){
			data = StringUtils.stringEscape(data);
		}
		this.data = data;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	
}

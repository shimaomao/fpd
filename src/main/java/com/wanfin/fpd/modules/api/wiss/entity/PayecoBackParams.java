package com.wanfin.fpd.modules.api.wiss.entity;

import java.io.Serializable;

import net.sf.json.JSONObject;

public class PayecoBackParams implements Serializable{
	private static final long serialVersionUID = 1L;
	private String data;//json格式的业务报文。需要对json发送包进行UTF8编码的UrlEncode. {"header": {"tradeId": "regNotify", "pvdID": "WZ","reqTime": "20160215122334"}, "body": { "uid":"","pvdUid":"","regStatus":"","reqOrdersNo":""}}&
	private String sign;//商户使用私钥对data的值进行签名
	private JSONObject jsonHeader;//data的json格式
	private JSONObject jsonBody;//data的json格式
	private PayecoBackHeader header;
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public JSONObject getJsonHeader() {
		return jsonHeader;
	}
	public void setJsonHeader(JSONObject jsonHeader) {
		this.jsonHeader = jsonHeader;
	}
	public JSONObject getJsonBody() {
		return jsonBody;
	}
	public void setJsonBody(JSONObject jsonBody) {
		this.jsonBody = jsonBody;
	}
	public PayecoBackHeader getHeader() {
		return header;
	}
	public void setHeader(PayecoBackHeader header) {
		this.header = header;
	}
	
	@Override
	public String toString() {
		return "PayecoBackParams [data=" + data + ", sign=" + sign + ", jsonHeader=" + jsonHeader + ", jsonBody="
				+ jsonBody + ", header=" + header + "]";
	}
}

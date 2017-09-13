/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.http.credit;

import org.hibernate.validator.constraints.Length;

import com.wanfin.fpd.modules.sys.entity.Office;

/**
 * 接口查询Entity
 * @author srf
 * @version 2016-07-18
 */
public class SearchParams{
	
	private static final long serialVersionUID = 1L;
	private String token;		// 用户token
	private Boolean hasDatas;	//是否获取到数据
	private String resId;		// pingan_res_struct主键
	private String searchCode;		// 查询接口类型代码
	private String searchMethod;		// 查询方式
	private String idNo;		// 证件号码
	private String idType;		// 证件类型
	private String name;		// 主体名称
	private String address;		// 联系地址
	private String mobileNo;		// 手机号码
	private String moblieNos;		// 手机号码集
	private String accountNo;		// 银行账号
	private String areaCode;		// 行政区代码
	private String busiDesc;		// 业务描述
	private String carFrameNum;		// 车架号
	private String carNo;		// 车牌号
	private String refName;		// 联系人
	private String refMobileNo;		// 联系人手机号码
	private String cardNo;		// 银行卡号
	private String cardNos;		// 银行卡号集
	private String authType;	//鉴权类型
	private String entityAuthCode;	//卡主授权代码
	private String entityAuthDate;	//卡主授权时间
	private String expiresYear;	//有效期年
	private String expiresMonth;	//有效期月
	private String creditCardCVN;	//信用卡cvn码
	private String reuseDays;	//复用天数
	private String clientId;		// 客户端ID
	private String company;		// 工作单位
	private String courtNoticeId;		// 法院公告ID
	private String judgeDocId;		// 文书ID
	private String searchTransNo;		// 检索交易流水号
	private String driviliceNo;		// 驾驶证号
	private String eductionBckgrd;		// 学历
	private String email;		// 邮箱
	private String engineNo;		// 发动机号
	private String ip;		// IP地址
	private String ips;		// IP集
	private String needeQryDrvStatus;		// 是否查询行驶证状态
	private String photo64;		// 相片
	private String queryId;		// 查询交易ID
	private String reasonCode;		// 查询原因
	private String qqNo;		// qq号
	private String amazonNo;		// 亚马逊帐号
	private String taobaoNo;		// 淘宝帐号
	private String jdno;		// 京东帐号
	private String transId;		// 交易ID
	private String weiboNo;		// 微博号
	private String weixinNo;		// 微信号
	private String yhdNo;		// 1号店
	private Office office;		// 部门ID
	
	public SearchParams() {
		super();
	}

	@Length(min=0, max=64, message="用户token长度必须介于 0 和 64 之间")
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	@Length(min=0, max=64, message="pingan_res_struct主键长度必须介于 0 和 64 之间")
	public String getResId() {
		return resId;
	}

	public void setResId(String resId) {
		this.resId = resId;
	}
	
	@Length(min=0, max=32, message="查询接口类型代码长度必须介于 0 和 32 之间")
	public String getSearchCode() {
		return searchCode;
	}

	public void setSearchCode(String searchCode) {
		this.searchCode = searchCode;
	}
	
	@Length(min=0, max=12, message="查询方式长度必须介于 0 和 12 之间")
	public String getSearchMethod() {
		return searchMethod;
	}

	public void setSearchMethod(String searchMethod) {
		this.searchMethod = searchMethod;
	}
	
	@Length(min=0, max=64, message="证件号码长度必须介于 0 和 64 之间")
	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
	
	@Length(min=0, max=2, message="证件类型长度必须介于 0 和 2 之间")
	public String getIdType() {
		return idType;
	}

	public void setIdType(String idType) {
		this.idType = idType;
	}
	
	@Length(min=0, max=128, message="主体名称长度必须介于 0 和 128 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=128, message="联系地址长度必须介于 0 和 128 之间")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	@Length(min=0, max=24, message="手机号码长度必须介于 0 和 24 之间")
	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	
	@Length(min=0, max=128, message="手机号码集长度必须介于 0 和 128 之间")
	public String getMoblieNos() {
		return moblieNos;
	}

	public void setMoblieNos(String moblieNos) {
		this.moblieNos = moblieNos;
	}
	
	@Length(min=0, max=32, message="银行账号长度必须介于 0 和 32 之间")
	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	
	@Length(min=0, max=6, message="行政区代码长度必须介于 0 和 6 之间")
	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	
	@Length(min=0, max=64, message="业务描述长度必须介于 0 和 64 之间")
	public String getBusiDesc() {
		return busiDesc;
	}

	public void setBusiDesc(String busiDesc) {
		this.busiDesc = busiDesc;
	}
	
	@Length(min=0, max=64, message="车架号长度必须介于 0 和 64 之间")
	public String getCarFrameNum() {
		return carFrameNum;
	}

	public void setCarFrameNum(String carFrameNum) {
		this.carFrameNum = carFrameNum;
	}
	
	@Length(min=0, max=24, message="车牌号长度必须介于 0 和 24 之间")
	public String getCarNo() {
		return carNo;
	}

	public void setCarNo(String carNo) {
		this.carNo = carNo;
	}
	
	@Length(min=0, max=64, message="银行卡号长度必须介于 0 和 64 之间")
	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	
	@Length(min=0, max=128, message="银行卡号集长度必须介于 0 和 128 之间")
	public String getCardNos() {
		return cardNos;
	}

	public void setCardNos(String cardNos) {
		this.cardNos = cardNos;
	}
	
	@Length(min=0, max=48, message="客户端ID长度必须介于 0 和 48 之间")
	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	
	@Length(min=0, max=64, message="工作单位长度必须介于 0 和 64 之间")
	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}
	
	@Length(min=0, max=64, message="法院公告ID长度必须介于 0 和 64 之间")
	public String getCourtNoticeId() {
		return courtNoticeId;
	}

	public void setCourtNoticeId(String courtNoticeId) {
		this.courtNoticeId = courtNoticeId;
	}
	
	@Length(min=0, max=64, message="文书ID长度必须介于 0 和 64 之间")
	public String getJudgeDocId() {
		return judgeDocId;
	}

	public void setJudgeDocId(String judgeDocId) {
		this.judgeDocId = judgeDocId;
	}
	
	@Length(min=0, max=24, message="检索交易流水号长度必须介于 0 和 24 之间")
	public String getSearchTransNo() {
		return searchTransNo;
	}

	public void setSearchTransNo(String searchTransNo) {
		this.searchTransNo = searchTransNo;
	}
	
	@Length(min=0, max=64, message="驾驶证号长度必须介于 0 和 64 之间")
	public String getDriviliceNo() {
		return driviliceNo;
	}

	public void setDriviliceNo(String driviliceNo) {
		this.driviliceNo = driviliceNo;
	}
	
	@Length(min=0, max=8, message="学历长度必须介于 0 和 8 之间")
	public String getEductionBckgrd() {
		return eductionBckgrd;
	}

	public void setEductionBckgrd(String eductionBckgrd) {
		this.eductionBckgrd = eductionBckgrd;
	}
	
	@Length(min=0, max=128, message="邮箱长度必须介于 0 和 128 之间")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	@Length(min=0, max=64, message="发动机号长度必须介于 0 和 64 之间")
	public String getEngineNo() {
		return engineNo;
	}

	public void setEngineNo(String engineNo) {
		this.engineNo = engineNo;
	}
	
	@Length(min=0, max=24, message="IP地址长度必须介于 0 和 24 之间")
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
	
	@Length(min=0, max=128, message="IP集长度必须介于 0 和 128 之间")
	public String getIps() {
		return ips;
	}

	public void setIps(String ips) {
		this.ips = ips;
	}
	
	@Length(min=0, max=2, message="是否查询行驶证状态长度必须介于 0 和 2 之间")
	public String getNeedeQryDrvStatus() {
		return needeQryDrvStatus;
	}

	public void setNeedeQryDrvStatus(String needeQryDrvStatus) {
		this.needeQryDrvStatus = needeQryDrvStatus;
	}
	
	public String getPhoto64() {
		return photo64;
	}

	public void setPhoto64(String photo64) {
		this.photo64 = photo64;
	}
	
	@Length(min=0, max=64, message="查询交易ID长度必须介于 0 和 64 之间")
	public String getQueryId() {
		return queryId;
	}

	public void setQueryId(String queryId) {
		this.queryId = queryId;
	}
	
	@Length(min=0, max=2, message="查询原因长度必须介于 0 和 2 之间")
	public String getReasonCode() {
		return reasonCode;
	}

	public void setReasonCode(String reasonCode) {
		this.reasonCode = reasonCode;
	}
	
	@Length(min=0, max=64, message="qq号长度必须介于 0 和 64 之间")
	public String getQqNo() {
		return qqNo;
	}

	public void setQqNo(String qqNo) {
		this.qqNo = qqNo;
	}
	
	@Length(min=0, max=128, message="亚马逊帐号长度必须介于 0 和 128 之间")
	public String getAmazonNo() {
		return amazonNo;
	}

	public void setAmazonNo(String amazonNo) {
		this.amazonNo = amazonNo;
	}
	
	@Length(min=0, max=64, message="淘宝帐号长度必须介于 0 和 64 之间")
	public String getTaobaoNo() {
		return taobaoNo;
	}

	public void setTaobaoNo(String taobaoNo) {
		this.taobaoNo = taobaoNo;
	}
	
	@Length(min=0, max=64, message="京东帐号长度必须介于 0 和 64 之间")
	public String getJdno() {
		return jdno;
	}

	public void setJdno(String jdno) {
		this.jdno = jdno;
	}
	
	@Length(min=0, max=48, message="交易ID长度必须介于 0 和 48 之间")
	public String getTransId() {
		return transId;
	}

	public void setTransId(String transId) {
		this.transId = transId;
	}
	
	@Length(min=0, max=128, message="微博号长度必须介于 0 和 128 之间")
	public String getWeiboNo() {
		return weiboNo;
	}

	public void setWeiboNo(String weiboNo) {
		this.weiboNo = weiboNo;
	}
	
	@Length(min=0, max=64, message="微信号长度必须介于 0 和 64 之间")
	public String getWeixinNo() {
		return weixinNo;
	}

	public void setWeixinNo(String weixinNo) {
		this.weixinNo = weixinNo;
	}
	
	@Length(min=0, max=128, message="1号店长度必须介于 0 和 128 之间")
	public String getYhdNo() {
		return yhdNo;
	}

	public void setYhdNo(String yhdNo) {
		this.yhdNo = yhdNo;
	}
	
	public Office getOffice() {
		if(office == null){
			office = new Office();
		}
		return office;
	}

	public void setOffice(Office office) {
		this.office = office;
	}

	public Boolean isHasDatas() {
		return hasDatas;
	}

	public void setHasDatas(Boolean hasDatas) {
		this.hasDatas = hasDatas;
	}

	public String getRefName() {
		return refName;
	}

	public void setRefName(String refName) {
		this.refName = refName;
	}

	public String getRefMobileNo() {
		return refMobileNo;
	}

	public void setRefMobileNo(String refMobileNo) {
		this.refMobileNo = refMobileNo;
	}

	public String getAuthType() {
		return authType;
	}

	public void setAuthType(String authType) {
		this.authType = authType;
	}

	public String getEntityAuthCode() {
		return entityAuthCode;
	}

	public void setEntityAuthCode(String entityAuthCode) {
		this.entityAuthCode = entityAuthCode;
	}

	public String getEntityAuthDate() {
		return entityAuthDate;
	}

	public void setEntityAuthDate(String entityAuthDate) {
		this.entityAuthDate = entityAuthDate;
	}

	public String getExpiresYear() {
		return expiresYear;
	}

	public void setExpiresYear(String expiresYear) {
		this.expiresYear = expiresYear;
	}

	public String getExpiresMonth() {
		return expiresMonth;
	}

	public void setExpiresMonth(String expiresMonth) {
		this.expiresMonth = expiresMonth;
	}

	public String getCreditCardCVN() {
		return creditCardCVN;
	}

	public void setCreditCardCVN(String creditCardCVN) {
		this.creditCardCVN = creditCardCVN;
	}

	public String getReuseDays() {
		return reuseDays;
	}

	public void setReuseDays(String reuseDays) {
		this.reuseDays = reuseDays;
	}

	@Override
	public String toString() {
		return "SearchParams [token=" + token + ", hasDatas=" + hasDatas + ", resId=" + resId + ", searchCode="
				+ searchCode + ", searchMethod=" + searchMethod + ", idNo=" + idNo + ", idType=" + idType + ", name="
				+ name + ", address=" + address + ", mobileNo=" + mobileNo + ", moblieNos=" + moblieNos + ", accountNo="
				+ accountNo + ", areaCode=" + areaCode + ", busiDesc=" + busiDesc + ", carFrameNum=" + carFrameNum
				+ ", carNo=" + carNo + ", refName=" + refName + ", refMobileNo=" + refMobileNo + ", cardNo=" + cardNo
				+ ", cardNos=" + cardNos + ", authType=" + authType + ", entityAuthCode=" + entityAuthCode
				+ ", entityAuthDate=" + entityAuthDate + ", expiresYear=" + expiresYear + ", expiresMonth="
				+ expiresMonth + ", creditCardCVN=" + creditCardCVN + ", reuseDays=" + reuseDays + ", clientId="
				+ clientId + ", company=" + company + ", courtNoticeId=" + courtNoticeId + ", judgeDocId=" + judgeDocId
				+ ", searchTransNo=" + searchTransNo + ", driviliceNo=" + driviliceNo + ", eductionBckgrd="
				+ eductionBckgrd + ", email=" + email + ", engineNo=" + engineNo + ", ip=" + ip + ", ips=" + ips
				+ ", needeQryDrvStatus=" + needeQryDrvStatus + ", photo64=" + photo64 + ", queryId=" + queryId
				+ ", reasonCode=" + reasonCode + ", qqNo=" + qqNo + ", amazonNo=" + amazonNo + ", taobaoNo=" + taobaoNo
				+ ", jdno=" + jdno + ", transId=" + transId + ", weiboNo=" + weiboNo + ", weixinNo=" + weixinNo
				+ ", yhdNo=" + yhdNo + ", office=" + office + "]";
	}

}
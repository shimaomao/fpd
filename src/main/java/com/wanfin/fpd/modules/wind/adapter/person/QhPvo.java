/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.wind.adapter.person;

import java.io.Serializable;

import com.wanfin.fpd.common.utils.StringUtils;
import com.wanfin.fpd.modules.employee.entity.TEmployee;
import com.wanfin.fpd.modules.sys.entity.Office;
import com.wanfin.fpd.modules.wind.adapter.IeAdapter;
import com.wanfin.fpd.modules.wind.adapter.WindHttp;

/**
 * 前海征信个人查询输入参数Entity
 * @author srf
 * @version 2016-06-12
 */
public class QhPvo implements Serializable{
	/**   
	* @Fields REASONCODE : 请求原因码 
	*/  
	public static final String REASONCODE = "99";

	/**   
	* @Fields ID_TYPE : 请求ID类型：0身份证 
	*/  
	public static final String ID_TYPE = "0";
	
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

	
	public QhPvo() {
		super();
	}

	public QhPvo(TEmployee employee, IeAdapter ieAdapter) {
		super();
		if(employee != null){
			if((ieAdapter).equals(IeAdapter.CCA_QH_PERSON_VALIDATE_IDENTITY)){
				this.idType = ID_TYPE;//默认身份证
				this.idNo = employee.getCardNum();//身份证号码
				this.name = employee.getName();//名称
				this.mobileNo = employee.getMobile();//手机号
				this.reasonCode = REASONCODE;
			}else if((ieAdapter).equals(IeAdapter.CCA_QH_PERSON_VALIDATE_ADDRESS)){
				this.idType = ID_TYPE;//默认身份证
				this.idNo = employee.getCardNum();//身份证号码
				this.name = employee.getName();//名称
				this.mobileNo = employee.getMobile();//手机号
				this.address = employee.getHouseholdRegAddr();
				this.reasonCode = REASONCODE;
			}else if((ieAdapter).equals(IeAdapter.CCA_QH_PERSON_VALIDATE_WORK)){
				this.idType = ID_TYPE;//默认身份证
				this.idNo = employee.getCardNum();//身份证号码
				this.name = employee.getName();//名称
				this.mobileNo = employee.getMobile();//手机号
				this.reasonCode = REASONCODE;
			}else if((ieAdapter).equals(IeAdapter.CCA_QH_PERSON_VALIDATE_WORK)){
				this.idType = ID_TYPE;//默认身份证
				this.idNo = employee.getCardNum();//身份证号码
				this.name = employee.getName();//名称
				this.mobileNo = employee.getMobile();//手机号
				this.reasonCode = REASONCODE;
			}
			
//			this.token = token;
//			this.hasDatas = hasDatas;
//			this.idType = 0;//默认身份证
//			this.searchCode = searchCode;
//			this.searchMethod = searchMethod;
//			this.resId = employee.getId();
//			this.idNo = employee.getCardNum();
//			this.name = employee.getName();
//			this.address = employee.getHouseholdRegAddr();//地址
//			this.mobileNo = employee.getMobile();
//			this.moblieNos = employee.getMobile();
//			this.accountNo = employee.get;
//			this.areaCode = areaCode;
//			this.busiDesc = busiDesc;
//			this.carFrameNum = employee.getC;
//			this.carNo = carNo;
//			this.refName = refName;
//			this.refMobileNo = refMobileNo;
//			this.cardNo = cardNo;
//			this.cardNos = cardNos;
//			this.authType = authType;
//			this.entityAuthCode = entityAuthCode;
//			this.entityAuthDate = entityAuthDate;
//			this.expiresYear = expiresYear;
//			this.expiresMonth = expiresMonth;
//			this.creditCardCVN = creditCardCVN;
//			this.reuseDays = reuseDays;
//			this.clientId = clientId;
//			this.company = company;
//			this.courtNoticeId = courtNoticeId;
//			this.judgeDocId = judgeDocId;
//			this.searchTransNo = searchTransNo;
//			this.driviliceNo = driviliceNo;
//			this.eductionBckgrd = eductionBckgrd;
//			this.email = email;
//			this.engineNo = engineNo;
//			this.ip = ip;
//			this.ips = ips;
//			this.needeQryDrvStatus = needeQryDrvStatus;
//			this.photo64 = photo64;
//			this.queryId = queryId;
//			this.reasonCode = reasonCode;
//			this.qqNo = qqNo;
//			this.amazonNo = amazonNo;
//			this.taobaoNo = taobaoNo;
//			this.jdno = jdno;
//			this.transId = transId;
//			this.weiboNo = weiboNo;
//			this.weixinNo = weixinNo;
//			this.yhdNo = yhdNo;
//			this.office = office;
		}
	}

	public String getResId() {
		return resId;
	}

	public void setResId(String resId) {
		this.resId = resId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Boolean getHasDatas() {
		return hasDatas;
	}

	public void setHasDatas(Boolean hasDatas) {
		this.hasDatas = hasDatas;
	}

	public String getSearchCode() {
		return searchCode;
	}

	public void setSearchCode(String searchCode) {
		this.searchCode = searchCode;
	}

	public String getSearchMethod() {
		return searchMethod;
	}

	public void setSearchMethod(String searchMethod) {
		this.searchMethod = searchMethod;
	}

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	public String getIdType() {
		return idType;
	}

	public void setIdType(String idType) {
		this.idType = idType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getMoblieNos() {
		return moblieNos;
	}

	public void setMoblieNos(String moblieNos) {
		this.moblieNos = moblieNos;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getBusiDesc() {
		return busiDesc;
	}

	public void setBusiDesc(String busiDesc) {
		this.busiDesc = busiDesc;
	}

	public String getCarFrameNum() {
		return carFrameNum;
	}

	public void setCarFrameNum(String carFrameNum) {
		this.carFrameNum = carFrameNum;
	}

	public String getCarNo() {
		return carNo;
	}

	public void setCarNo(String carNo) {
		this.carNo = carNo;
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

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getCardNos() {
		return cardNos;
	}

	public void setCardNos(String cardNos) {
		this.cardNos = cardNos;
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

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getCourtNoticeId() {
		return courtNoticeId;
	}

	public void setCourtNoticeId(String courtNoticeId) {
		this.courtNoticeId = courtNoticeId;
	}

	public String getJudgeDocId() {
		return judgeDocId;
	}

	public void setJudgeDocId(String judgeDocId) {
		this.judgeDocId = judgeDocId;
	}

	public String getSearchTransNo() {
		return searchTransNo;
	}

	public void setSearchTransNo(String searchTransNo) {
		this.searchTransNo = searchTransNo;
	}

	public String getDriviliceNo() {
		return driviliceNo;
	}

	public void setDriviliceNo(String driviliceNo) {
		this.driviliceNo = driviliceNo;
	}

	public String getEductionBckgrd() {
		return eductionBckgrd;
	}

	public void setEductionBckgrd(String eductionBckgrd) {
		this.eductionBckgrd = eductionBckgrd;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEngineNo() {
		return engineNo;
	}

	public void setEngineNo(String engineNo) {
		this.engineNo = engineNo;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getIps() {
		return ips;
	}

	public void setIps(String ips) {
		this.ips = ips;
	}

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

	public String getQueryId() {
		return queryId;
	}

	public void setQueryId(String queryId) {
		this.queryId = queryId;
	}

	public String getReasonCode() {
		return reasonCode;
	}

	public void setReasonCode(String reasonCode) {
		this.reasonCode = reasonCode;
	}

	public String getQqNo() {
		return qqNo;
	}

	public void setQqNo(String qqNo) {
		this.qqNo = qqNo;
	}

	public String getAmazonNo() {
		return amazonNo;
	}

	public void setAmazonNo(String amazonNo) {
		this.amazonNo = amazonNo;
	}

	public String getTaobaoNo() {
		return taobaoNo;
	}

	public void setTaobaoNo(String taobaoNo) {
		this.taobaoNo = taobaoNo;
	}

	public String getJdno() {
		return jdno;
	}

	public void setJdno(String jdno) {
		this.jdno = jdno;
	}

	public String getTransId() {
		return transId;
	}

	public void setTransId(String transId) {
		this.transId = transId;
	}

	public String getWeiboNo() {
		return weiboNo;
	}

	public void setWeiboNo(String weiboNo) {
		this.weiboNo = weiboNo;
	}

	public String getWeixinNo() {
		return weixinNo;
	}

	public void setWeixinNo(String weixinNo) {
		this.weixinNo = weixinNo;
	}

	public String getYhdNo() {
		return yhdNo;
	}

	public void setYhdNo(String yhdNo) {
		this.yhdNo = yhdNo;
	}

	public Office getOffice() {
		return office;
	}

	public void setOffice(Office office) {
		this.office = office;
	}
}
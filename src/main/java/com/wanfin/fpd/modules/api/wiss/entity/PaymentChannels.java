package com.wanfin.fpd.modules.api.wiss.entity;

import com.wanfin.fpd.common.persistence.DataEntity;
import com.wanfin.fpd.common.utils.StringUtils;

/**
 * 易联锁定支付通道
 * @author user
 *
 */
public class PaymentChannels  extends DataEntity<PaymentChannels>{
	private static final long serialVersionUID = 1L;
	private String userId;	//卖家在易联的编号
	private String pvdUid;	//贷款供应商用户ID
	private String loanBusinessId;	//借款业务ID
	private String accountStatus;	//支付通道状态 0:未锁定；1:已锁定；2:已解锁；9:其他
	//private String cashBackStatus;	//回款状态 0:未回款；1:已回款；2:已收款；4:回款异常；5:收款异常；9:其他
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPvdUid() {
		return pvdUid;
	}
	public void setPvdUid(String pvdUid) {
		this.pvdUid = pvdUid;
	}
	public String getLoanBusinessId() {
		return loanBusinessId;
	}
	public void setLoanBusinessId(String loanBusinessId) {
		this.loanBusinessId = loanBusinessId;
	}
	public String getAccountStatus() {
		return accountStatus;
	}
	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}
//	public String getCashBackStatus() {
//		return cashBackStatus;
//	}
//	public void setCashBackStatus(String cashBackStatus) {
//		this.cashBackStatus = cashBackStatus;
//	}
	/**
	 * 参数简单校验
	 * @return
	 */
	public boolean verifyDatas(){
		if(StringUtils.isBlank(userId)){
			return false;
		}else if(StringUtils.isBlank(loanBusinessId)){
			return false;
		//}if((StringUtils.isBlank(accountStatus) || !",0,1,2,9,".contains(accountStatus)) && (StringUtils.isBlank(cashBackStatus) || !",0,1,2,4,5,9,".contains(cashBackStatus))){
		}else if(StringUtils.isBlank(accountStatus) || !",0,1,2,9,".contains(accountStatus)){
			return false;
		}
		
		return true;
	}
}

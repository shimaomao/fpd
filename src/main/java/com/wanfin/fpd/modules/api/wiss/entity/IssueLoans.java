package com.wanfin.fpd.modules.api.wiss.entity;

import java.io.Serializable;

import com.wanfin.fpd.common.utils.StringUtils;

/**
 * 7.3 放款通知(fkNotify)
 * @author user
 *
 */
public class IssueLoans  implements Serializable{
	private static final long serialVersionUID = 1L;
	private String userId;	//卖家在易联的编号
	private String pvdUid;	//贷款供应商用户ID
	private String loanBusinessId;	//借款业务ID
	private String loanStatus;//借款业务状态  1:新申请;2:审核中;3:审核通过（未放款;4:审核不通过（已终止）;5:已放款;6:已逾期
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
	public String getLoanStatus() {
		return loanStatus;
	}
	public void setLoanStatus(String loanStatus) {
		this.loanStatus = loanStatus;
	}
	
	/**
	 * 参数简单校验
	 * @return
	 */
	public boolean verifyDatas(){
		if(StringUtils.isBlank(pvdUid)){
			return false;
		}else if(StringUtils.isBlank(loanBusinessId)){
			return false;
		//}if((StringUtils.isBlank(accountStatus) || !",0,1,2,9,".contains(accountStatus)) && (StringUtils.isBlank(cashBackStatus) || !",0,1,2,4,5,9,".contains(cashBackStatus))){
		}else if(StringUtils.isBlank(loanStatus) || !",1,2,4,5,6,7,8,9,".contains(loanStatus)){
			return false;
		}
		return true;
	}
}

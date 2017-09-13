/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.wish.order.entity;

import org.hibernate.validator.constraints.Length;
import com.wanfin.fpd.modules.sys.entity.User;

import com.wanfin.fpd.common.persistence.DataEntity;

/**
 * 扣款明细表Entity
 * @author lzj
 * @version 2017-08-29
 */
public class TReturnedMsg extends DataEntity<TReturnedMsg> {
	
	private static final long serialVersionUID = 1L;
	private String loanContractId;		// 借款业务id
	private String returnedMoneyId;		// 回款id
	private String money;		// 本金
	private String fourfee;		// 4%利息
	private String eightfee;		// 8%利息
	private String userId;		// wish用户id
	private String channel;		// 通道
	private Integer periods;     //第几次还款
	
	public TReturnedMsg() {
		super();
	}

	public TReturnedMsg(String id){
		super(id);
	}

	@Length(min=0, max=64, message="借款业务id长度必须介于 0 和 64 之间")
	public String getLoanContractId() {
		return loanContractId;
	}

	public void setLoanContractId(String loanContractId) {
		this.loanContractId = loanContractId;
	}
	
	@Length(min=0, max=64, message="回款id长度必须介于 0 和 64 之间")
	public String getReturnedMoneyId() {
		return returnedMoneyId;
	}

	public void setReturnedMoneyId(String returnedMoneyId) {
		this.returnedMoneyId = returnedMoneyId;
	}
	
	@Length(min=0, max=255, message="本金长度必须介于 0 和 255 之间")
	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}
	
	@Length(min=0, max=255, message="4%利息长度必须介于 0 和 255 之间")
	public String getFourfee() {
		return fourfee;
	}

	public void setFourfee(String fourfee) {
		this.fourfee = fourfee;
	}
	
	@Length(min=0, max=255, message="8%利息长度必须介于 0 和 255 之间")
	public String getEightfee() {
		return eightfee;
	}

	public void setEightfee(String eightfee) {
		this.eightfee = eightfee;
	}
	
	
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Length(min=0, max=255, message="通道长度必须介于 0 和 255 之间")
	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public Integer getPeriods() {
		return periods;
	}

	public void setPeriods(Integer periods) {
		this.periods = periods;
	}

	
	
	
}
package com.wanfin.fpd.common.utils;

import java.math.BigDecimal;
/**
 * 资金账户操作Vo
 * @author lzj
 *
 */
public class AccountVo {
    private String authId;//认证用户ID
    private BigDecimal money;//操作金额
    private String marks;//备注
    
	public String getAuthId() {
		return authId;
	}
	public void setAuthId(String authId) {
		this.authId = authId;
	}
	public BigDecimal getMoney() {
		return money;
	}
	public void setMoney(BigDecimal money) {
		this.money = money;
	}
	public String getMarks() {
		return marks;
	}
	public void setMarks(String marks) {
		this.marks = marks;
	}
    
}

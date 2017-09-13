/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.wish.order.entity;

import org.hibernate.validator.constraints.Length;
import com.wanfin.fpd.modules.sys.entity.User;

import com.wanfin.fpd.common.persistence.DataEntity;

/**
 * 逾期金额Entity
 *
 * @author cjp
 * @version 2017-08-22
 */
public class WishOverdue extends DataEntity<WishOverdue> {

    private static final long serialVersionUID = 1L;
    private String organId;        // organ_id
    private String userId;        // 易联用户id
    private String money;        // 逾期金额
    private String loanContractId;        // 贷款订单id
    private String status;        // 逾期订单金额状态。0逾期，1已结清

    public WishOverdue() {
        super();
    }

    public WishOverdue(String id) {
        super(id);
    }

    @Length(min = 0, max = 255, message = "organ_id长度必须介于 0 和 255 之间")
    public String getOrganId() {
        return organId;
    }

    public void setOrganId(String organId) {
        this.organId = organId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Length(min = 0, max = 255, message = "逾期金额长度必须介于 0 和 255 之间")
    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    @Length(min = 0, max = 255, message = "贷款订单id长度必须介于 0 和 255 之间")
    public String getLoanContractId() {
        return loanContractId;
    }

    public void setLoanContractId(String loanContractId) {
        this.loanContractId = loanContractId;
    }

    @Length(min = 0, max = 255, message = "逾期订单金额状态。0逾期，1已结清长度必须介于 0 和 255 之间")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
package com.wanfin.fpd.modules.wish.beitone.entity;

import com.wanfin.fpd.common.persistence.DataEntity;
import com.wanfin.fpd.modules.wish.merchant.entity.Merchant;

/**
 * 推送p2p平台信息表
 * Created by qiao on 2017/8/21.
 */
public class ProductPush extends DataEntity<ProductPush> {

    private String type; //哪家p2p
    private String name; //p2p名称
    private String status; //是否推送p2p成功   0失败  1 成功
    private String loanContractId; //贷款订单id



    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLoanContractId() {
        return loanContractId;
    }

    public void setLoanContractId(String loanContractId) {
        this.loanContractId = loanContractId;
    }
}


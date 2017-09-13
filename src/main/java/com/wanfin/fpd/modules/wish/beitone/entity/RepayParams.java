package com.wanfin.fpd.modules.wish.beitone.entity;

/**
 * Created by qiao on 2017/8/30.
 */
public class RepayParams {
    private String periods; //周期
    private String principal; //本金
    private String interest; //利息

    public String getPeriods() {
        return periods;
    }

    public void setPeriods(String periods) {
        this.periods = periods;
    }

    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }
}

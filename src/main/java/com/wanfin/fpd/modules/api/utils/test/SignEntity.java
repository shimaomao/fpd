package com.wanfin.fpd.modules.api.utils.test;

import java.util.Map;

public class SignEntity {
    private String corpId;
    private String amount;
    private String bankowner;
    private String bankno;
    private String idSeq;
    private String yearConversion;
    private String limitTime;
    private String secretKey;
    private Map resources;
    private String batchid;
    private Map<String, Object> repayinfo;
    private String imgUrl;

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Map<String, Object> getRepayinfo() {
        return repayinfo;
    }

    public void setRepayinfo(Map<String, Object> repayinfo) {
        this.repayinfo = repayinfo;
    }

    public String getCorpId() {
        return corpId;
    }

    public void setCorpId(String corpId) {
        this.corpId = corpId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getBankowner() {
        return bankowner;
    }

    public void setBankowner(String bankowner) {
        this.bankowner = bankowner;
    }

    public String getBankno() {
        return bankno;
    }

    public void setBankno(String bankno) {
        this.bankno = bankno;
    }

    public String getIdSeq() {
        return idSeq;
    }

    public void setIdSeq(String idSeq) {
        this.idSeq = idSeq;
    }

    public String getYearConversion() {
        return yearConversion;
    }

    public void setYearConversion(String yearConversion) {
        this.yearConversion = yearConversion;
    }

    public String getLimitTime() {
        return limitTime;
    }

    public void setLimitTime(String limitTime) {
        this.limitTime = limitTime;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }


    public void setResources(Map resources) {
        this.resources = resources;
    }

    public Map getResources() {
        return resources;
    }

    public String getBatchid() {
        return batchid;
    }

    public void setBatchid(String batchid) {
        this.batchid = batchid;
    }

//	@Override
//	public String toString() {
//		return "SignEntity [corpId=" + corpId + ", amount=" + amount + ", bankowner=" + bankowner + ", bankno=" + bankno
//				+ ", idSeq=" + idSeq + ", yearConversion=" + yearConversion + ", limitTime=" + limitTime
//				+ ", secretKey=" + secretKey + "]";
//	}
}

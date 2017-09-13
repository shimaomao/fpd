package com.wanfin.fpd.modules.wish.contract.vo;

import com.wanfin.fpd.common.persistence.ApiEntity;
import com.wanfin.fpd.common.utils.excel.annotation.ExcelField;

public class TLendingVo extends ApiEntity<TLendingVo> {

    @ExcelField(title = "借款人id", align = 2, sort = 1)
    private String customerId;        // 借款人id
    @ExcelField(title = "借款业务id", align = 2, sort = 2)
    private String loanContractId;        // 借款业务id
    @ExcelField(title = "借款人姓名", align = 2, sort = 3)
    private String customerName;        // 借款人姓名
    @ExcelField(title = "借款人身份证号", align = 2, sort = 4)
    private String gatheringNumber;        // 借款人身份证号

    @ExcelField(title = "借款金额", align = 2, sort = 5)
    private String sumLoanAmount;//借款金额

    @ExcelField(title = "手续费", align = 2, sort = 6)
    private String sumCharge;//手续费
    @ExcelField(title = "放款时间", align = 2, sort = 7)
    private String lendTime;//放款时间
    @ExcelField(title = "最迟还款日", align = 2, sort = 8)
    private String payPrincipalDate;//最迟还款日


    private String starttime;
    private String endtime;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getLoanContractId() {
        return loanContractId;
    }

    public void setLoanContractId(String loanContractId) {
        this.loanContractId = loanContractId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getGatheringNumber() {
        return gatheringNumber;
    }

    public void setGatheringNumber(String gatheringNumber) {
        this.gatheringNumber = gatheringNumber;
    }

    public String getLendTime() {
        return lendTime;
    }

    public void setLendTime(String lendTime) {
        this.lendTime = lendTime;
    }

    public String getPayPrincipalDate() {
        return payPrincipalDate;
    }

    public void setPayPrincipalDate(String payPrincipalDate) {
        this.payPrincipalDate = payPrincipalDate;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public String getSumLoanAmount() {
        return sumLoanAmount;
    }

    public void setSumLoanAmount(String sumLoanAmount) {
        this.sumLoanAmount = sumLoanAmount;
    }

    public String getSumCharge() {
        return sumCharge;
    }

    public void setSumCharge(String sumCharge) {
        this.sumCharge = sumCharge;
    }


}

package com.wanfin.fpd.modules.wish.beitone.entity;

import com.wanfin.fpd.modules.wish.order.entity.WishOverdue;

import java.util.List;

/**
 * 贝通通知状态参数
 * Created by qiao on 2017/8/22.
 */
public class StatusInform {
    private String idSeq;  //贷款订单id
    private String status;  // 通知状态  1 待审核   2审核通过    3审核失败     4已放款     5还款已确认    6还款完成     7逾期    8逾期结清

    private RepayParams data;

    public String getIdSeq() {
        return idSeq;
    }

    public void setIdSeq(String idSeq) {
        this.idSeq = idSeq;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public RepayParams getData() {
        return data;
    }

    public void setData(RepayParams data) {
        this.data = data;
    }
}

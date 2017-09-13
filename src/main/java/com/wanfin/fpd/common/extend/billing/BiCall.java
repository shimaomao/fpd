package com.wanfin.fpd.common.extend.billing;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;

import com.wanfin.fpd.common.config.Cons;
import com.wanfin.fpd.modules.billing.entity.collect.BiCollect;
import com.wanfin.fpd.modules.sys.entity.User;
import com.wanfin.fpd.modules.sys.utils.UserUtils;
import com.wanfin.fpd.modules.trading.service.TradingRecordService;

public class BiCall {
	@Autowired
	private TradingRecordService tradingRecordService;
	
	public void updateAccount(BiCollect biCollect){
		//开通时的交易金额变更
		User organ = UserUtils.get(biCollect.getOrganId());//租户
		BigDecimal amount = BigDecimal.valueOf(biCollect.getTotalPrice()).setScale(2,BigDecimal.ROUND_HALF_UP);
		//租户扣除开通费用
		tradingRecordService.addTradingRecord("万众金融", "平台账户交易", Cons.TradingType.FEE_BIZ_PAY, organ.getLoginName(), amount.negate(), biCollect.getId(), "开通付费业务");//扣费
		//万众平台资产增加开通费用
		tradingRecordService.addTradingRecord(organ.getLoginName(), "平台账户交易", Cons.TradingType.FEE_BIZ_PAY, UserUtils.getAdmin().getLoginName(), amount, biCollect.getId(), "开通付费业务");
	}
}
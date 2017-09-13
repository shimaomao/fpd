/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.trading.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wanfin.fpd.common.config.Cons;
import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.service.CrudService;
import com.wanfin.fpd.common.utils.IdGen;
import com.wanfin.fpd.modules.asset.service.AssetService;
import com.wanfin.fpd.modules.trading.dao.TradingRecordDao;
import com.wanfin.fpd.modules.trading.entity.TradingRecord;

/**
 * 交易记录Service
 * @author zzm
 * @version 2016-06-14
 */
@Service
@Transactional(readOnly = true)
public class TradingRecordService extends CrudService<TradingRecordDao, TradingRecord> {

	@Autowired
	AssetService assetService;
	
	public TradingRecord get(String id) {
		return super.get(id);
	}
	
	public List<TradingRecord> findList(TradingRecord tradingRecord) {
		return super.findList(tradingRecord);
	}
	
	public Page<TradingRecord> findPage(Page<TradingRecord> page, TradingRecord tradingRecord) {
		return super.findPage(page, tradingRecord);
	}
	
	/** 
	 * @Description 添加交易记录，同时根据交易记录更新账户资产
	 * @Title: save
	 * @param tradingRecord
	 * @author Administrator 
	 * @date 2016-6-20 下午3:16:46  
	 */
	@Transactional(readOnly = false)
	public void save(TradingRecord tradingRecord) {
		super.save(tradingRecord);
		//更新账户资产
		assetService.addAsset(tradingRecord.getLoginName(), tradingRecord.getAmount());
	}
	
	/**
	 * @Description 添加交易记录，同时根据交易记录更新账户资产
	 * @param trader		交易对象
	 * @param tradingWay	交易方式
	 * @param tradingType	交易类型
	 * @param loginName		账户
	 * @param amount		交易金额
	 * @author zzm
	 * @date 2016-6-20 下午4:14:36  
	 */
	@Transactional(readOnly = false)
	public TradingRecord addTradingRecord(String trader, String tradingWay, String tradingType, String loginName, BigDecimal amount) {
		return addTradingRecord(trader, tradingWay, tradingType, loginName, amount, null, null);
	}
	
	/**
	 * @Description 添加交易记录，同时根据交易记录更新账户资产
	 * @param trader		交易对象
	 * @param tradingWay	交易方式
	 * @param tradingType	交易类型
	 * @param loginName		账户
	 * @param amount		交易金额
	 * @param businessId	交易业务id
	 * @param businessName	交易业务名称
	 * @author zzm
	 * @date 2016-6-20 下午4:14:36  
	 */
	@Transactional(readOnly = false)
	public TradingRecord addTradingRecord(String trader, String tradingWay, String tradingType, String loginName, BigDecimal amount, String businessId, String businessName) {
		return addTradingRecord(trader, tradingWay, tradingType, loginName, amount, businessId, businessName, String.valueOf(IdGen.randomLong()));
	}
	
	/**
	 * @Description 添加交易记录，同时根据交易记录更新账户资产
	 * @param trader		交易对象
	 * @param tradingWay	交易方式
	 * @param tradingType	交易类型
	 * @param loginName		账户
	 * @param amount		交易金额
	 * @param businessId	交易业务id
	 * @param businessName	交易业务名称
	 * @param seqNo			交易流水号
	 * @author zzm
	 * @date 2016-6-20 下午4:14:36  
	 */
	@Transactional(readOnly = false)
	public TradingRecord addTradingRecord(String trader, String tradingWay, String tradingType, String loginName, BigDecimal amount, String businessId, String businessName, String seqNo) {
		TradingRecord tradingRecord = new TradingRecord();
		tradingRecord.setTrader(trader);
		tradingRecord.setTradingTime(new Date());
		tradingRecord.setTradingWay(tradingWay);
		tradingRecord.setTradingType(tradingType);
		tradingRecord.setLoginName(loginName);
		tradingRecord.setSeqNo(seqNo);
		tradingRecord.setAmount(amount);
		tradingRecord.setBusinessId(businessId);
		tradingRecord.setBusinessName(businessName);
		this.save(tradingRecord);
		return tradingRecord;
	}
	
	
	@Transactional(readOnly = false)
	public void delete(TradingRecord tradingRecord) {
		super.delete(tradingRecord);
	}
	
}
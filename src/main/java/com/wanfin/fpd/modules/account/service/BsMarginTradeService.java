/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.account.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.service.CrudService;
import com.wanfin.fpd.common.utils.StringUtils;
import com.wanfin.fpd.modules.account.dao.BsMarginTradeDao;
import com.wanfin.fpd.modules.account.entity.BsAccount;
import com.wanfin.fpd.modules.account.entity.BsMarginTrade;

/**
 * 保证金记录表Service
 * @author srf
 * @version 2017-01-03
 */
@Service
@Transactional(readOnly = true)
public class BsMarginTradeService extends CrudService<BsMarginTradeDao, BsMarginTrade> {
	@Autowired
	private BsAccountService bsAccountService;//资金账户信息
	
	public BsMarginTrade get(String id) {
		return super.get(id);
	}
	
	public BsMarginTrade getEntityId(BsMarginTrade bsMarginTrade) {
		return dao.getEntityId(bsMarginTrade);
	}
	
	public List<BsMarginTrade> findList(BsMarginTrade bsMarginTrade) {
		return super.findList(bsMarginTrade);
	}
	
	public Page<BsMarginTrade> findPage(Page<BsMarginTrade> page, BsMarginTrade bsMarginTrade) {
		return super.findPage(page, bsMarginTrade);
	}
	
	/**
	 * 判断保证金是否充足
	 * @param bsMarginTrade:认证用户ID和保证金必须
	 * @return true保证金充足
	 */
	public boolean judgeDeposit(BsMarginTrade bsMarginTrade){
		BigDecimal zero = new BigDecimal(0);
		//参数校验
		if(bsMarginTrade == null || StringUtils.isBlank(bsMarginTrade.getAuthUserId()) || bsMarginTrade.getMarginMoney() == null || bsMarginTrade.getMarginMoney().compareTo(zero)<1){
			return false;
		}
		
		//资金账户检查
		BsAccount bsAccount = bsAccountService.getByAuthUser(bsMarginTrade.getAuthUserId());
		if(bsAccount == null || bsAccount.getAvailableFunds() == null){
			return false;
		}
		if(bsAccount.getAvailableFunds().compareTo(bsMarginTrade.getMarginMoney())>=0){
			return true;
		}else{
			return false;
		}
	}
	/**
	 * 扣除保证金
	 * @param bsMarginTrade
	 * @return -1:参数错误(含保证金为0)；1:成功；2:资金账户余额不足；12:资金账户存在问题；
	 */
	@Transactional(readOnly = false)
	public int deductDeposit(BsMarginTrade bsMarginTrade){
		BigDecimal zero = new BigDecimal(0);
		//参数校验
		if(bsMarginTrade == null || StringUtils.isBlank(bsMarginTrade.getAuthUserId()) || bsMarginTrade.getMarginMoney() == null || bsMarginTrade.getMarginMoney().compareTo(zero)<1){
			return -1;
		}
		
		//资金账户检查
		BsAccount bsAccount = bsAccountService.getByAuthUser(bsMarginTrade.getAuthUserId());
		if(bsAccount == null || bsAccount.getAvailableFunds() == null){
			return 12;
		}
		if(bsAccount.getAvailableFunds().compareTo(bsMarginTrade.getMarginMoney())<0){
			return 2;
		}
		//处理保存数据
		bsMarginTrade.setStatus(1);
		super.save(bsMarginTrade);
		if(bsAccount.getMarginAmount()==null){
			bsAccount.setMarginAmount(zero);
		}
		bsAccount.setMarginAmount(bsAccount.getMarginAmount().add(bsMarginTrade.getMarginMoney()));
		bsAccount.setAvailableFunds(bsAccount.getAvailableFunds().subtract(bsMarginTrade.getMarginMoney()));
		bsAccountService.save(bsAccount);
		
		return 1;
	}
	
	/**
	 * 归还保证金，之前已经扣过保证金且用户认证ID、对应类型ID、类型不能为空
	 * @param bsMarginTrade
	 * @return -1:参数错误(含保证金为0)；1:成功；2:资金账户余额不足；3:扣除记录不存在；4:保证金未在押；12:资金账户存在问题；
	 */
	@Transactional(readOnly = false)
	public int returnDeposit(BsMarginTrade bsMarginTrade){
		//参数校验
		if(bsMarginTrade == null || StringUtils.isAnyBlank(bsMarginTrade.getAuthUserId())){
			return -1;
		}
		//之前的扣除记录是否存在
		BsMarginTrade record = dao.getEntityId(bsMarginTrade);
		if(record == null){
			return 3;
		}else if(bsMarginTrade.getStatus()==1){
			return 4;
		}
		//资金账户检查
		BsAccount bsAccount = bsAccountService.getByAuthUser(record.getAuthUserId());
		if(bsAccount == null || bsAccount.getMarginAmount() == null || bsAccount.getAvailableFunds() == null){
			return 12;
		}
		if(bsAccount.getMarginAmount().compareTo(record.getMarginMoney())<0){
			return 2;
		}
		//处理保存数据
		record.setStatus(2);
		super.save(record);
		
		bsAccount.setMarginAmount(bsAccount.getMarginAmount().subtract(record.getMarginMoney()));
		bsAccount.setAvailableFunds(bsAccount.getAvailableFunds().add(record.getMarginMoney()));
		bsAccountService.save(bsAccount);
		
		return 1;
	}

	
	@Transactional(readOnly = false)
	public void save(BsMarginTrade bsMarginTrade) {
		super.save(bsMarginTrade);
	}
	
	@Transactional(readOnly = false)
	public void delete(BsMarginTrade bsMarginTrade) {
		super.delete(bsMarginTrade);
	}
	
}
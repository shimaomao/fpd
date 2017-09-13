/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.wish.order.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.service.CrudService;
import com.wanfin.fpd.modules.wish.order.dao.TReturnedMsgDao;
import com.wanfin.fpd.modules.wish.order.entity.TReturnedMsg;


/**
 * 扣款明细表Service
 *
 * @author lzj
 * @version 2017-08-29
 */
@Service
@Transactional(readOnly = true)
public class TReturnedMsgService extends CrudService<TReturnedMsgDao, TReturnedMsg> {

	public TReturnedMsg get(String id) {
		return super.get(id);
	}
	
	public List<TReturnedMsg> findList(TReturnedMsg tReturnedMsg) {
		return super.findList(tReturnedMsg);
	}
	
	public Page<TReturnedMsg> findPage(Page<TReturnedMsg> page, TReturnedMsg tReturnedMsg) {
		return super.findPage(page, tReturnedMsg);
	}
	
	@Transactional(readOnly = false)
	public void save(TReturnedMsg tReturnedMsg) {
		super.save(tReturnedMsg);
	}
	
	@Transactional(readOnly = false)
	public void delete(TReturnedMsg tReturnedMsg) {
		super.delete(tReturnedMsg);
	}

	public List<TReturnedMsg> findListByReturnMoneyIds(TReturnedMsg rm) {
		return dao.findListByReturnMoneyIds(rm);
	}

	public Integer getMaxPeriods(String loanContractId) {
		return dao.getMaxPeriods(loanContractId);
	}
	

}
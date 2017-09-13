/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.lending.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jfinal.plugin.activerecord.Db;
import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.service.CrudService;
import com.wanfin.fpd.modules.contract.entity.TLoanContract;
import com.wanfin.fpd.modules.lending.dao.TLendingDao;
import com.wanfin.fpd.modules.lending.entity.TLending;
import com.wanfin.fpd.modules.wish.contract.vo.TLendingVo;

/**
 * 放款记录Service
 * @author chenh
 * @version 2016-03-25
 */
@Service
@Transactional(readOnly = true)
public class TLendingService extends CrudService<TLendingDao, TLending> {

	public TLending get(String id) {
		return super.get(id);
	}
	
	public List<TLending> findList(TLending tLending) {
		return super.findList(tLending);
	}
	
	public Page<TLending> findPage(Page<TLending> page, TLending tLending) {
		return super.findPage(page, tLending);
	}
	
	@Transactional(readOnly = false)
	public void save(TLending tLending) {
		super.save(tLending);
	}
	
	@Transactional(readOnly = false)
	public void delete(TLending tLending) {
		super.delete(tLending);
	}
	
	
	public Page<TLending> findPageByLoanContract(Page<TLending> page, TLending tLending) {
		tLending.setPage(page);
		return page.setList(super.dao.findPageByLoanContract(tLending));
	}
	
	public Page<TLending> findPageByLoanContract(Page<TLending> page, TLending tLending, TLoanContract tLoanContract) {
		tLending.setContract(tLoanContract);
		return findPageByLoanContract(page, tLending);
	}
	
	/**
	 * @description 统计总放款金额
	 * @auth DC
	 * @date 2016年3月29日 上午8:57:27
	 * @param tLoanContract
	 * @return
	 */
	public BigDecimal countAmount(TLoanContract tLoanContract) {
		List<TLending> tLendings = findList(new TLending(tLoanContract));
		BigDecimal totalAmount = new BigDecimal(0.0);
		for (TLending tLendingTemp : tLendings) {
			tLendingTemp.setContract(tLoanContract);
			//totalAmount += Double.parseDouble(tLendingTemp.getAmount());
			totalAmount = totalAmount.add(new BigDecimal(tLendingTemp.getAmount()));
		}
		return totalAmount;
	}
	
	public List<?> getLendRecord(String contractId){
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT DATE_FORMAT(l.time,'%Y-%m-%d')AS time,l.amount,u.`name` FROM t_lending l LEFT JOIN sys_user u ON l.create_by = u.id WHERE l.contract_id = '")
		.append(contractId).append("'");
		return Db.query(sb.toString());
	}
	
	public TLending getTLending(TLending tl) {
		return dao.getTLending(tl);
	}

	public Page<TLendingVo> findTLendingVoPage(Page<TLendingVo> page,
			TLendingVo tLendingVo) {
		tLendingVo.setPage(page);
		page.setList(dao.findTLendingVoPage(tLendingVo));
		return page;
	}

	public List<TLendingVo> findTLendingVoList(TLendingVo tLendingVo) {
		return dao.findTLendingVoPage(tLendingVo);
	}

	public List<TLendingVo> findTLendingVoPage(TLendingVo tLendingVo) {
		return dao.findTLendingVoPage(tLendingVo);
	}
}
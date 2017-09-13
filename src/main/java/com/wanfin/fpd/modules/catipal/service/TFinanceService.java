/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.catipal.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wanfin.fpd.common.config.Cons;
import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.service.CrudService;
import com.wanfin.fpd.modules.catipal.dao.TCapitalDao;
import com.wanfin.fpd.modules.catipal.dao.TFinanceDao;
import com.wanfin.fpd.modules.catipal.entity.TCapital;
import com.wanfin.fpd.modules.catipal.entity.TFinance;

/**
 * 融资列表Service
 * @author lx
 * @version 2016-05-09
 */
@Service
@Transactional(readOnly = true)
public class TFinanceService extends CrudService<TFinanceDao, TFinance> {
	
	@Autowired
	private TCapitalDao capitaldao;
	
	public TFinance get(String id) {
		return super.get(id);
	}
	
	public List<TFinance> findList(TFinance tFinance) {
		return super.findList(tFinance);
	}
	
	public Page<TFinance> findPage(Page<TFinance> page, TFinance tFinance) {
		return super.findPage(page, tFinance);
	}
	
	@Transactional(readOnly = false)
	public void save(TFinance tFinance,TCapital capital) {
		BigDecimal b1 = new BigDecimal(capital.getRongziamount()) ;//资本信息融资金额
		BigDecimal b2 = new BigDecimal(capital.getLimitamount()) ; //贷款上限金额

		BigDecimal b3 = new BigDecimal(tFinance.getRongzimoney()) ;//本次融资金额
		if(tFinance.getRongzitype()!=null){
			if(tFinance.getRongzitype().equals(Cons.FinanceStatus.FINANCE_ADD)){//增加
				capital.setLimitamount(b2.add(b3)+"");
				capital.setRongziamount(b1.add(b3)+"");
			}else if(tFinance.getRongzitype().equals(Cons.FinanceStatus.FINANCE_REMOVE)){
				capital.setLimitamount(b2.subtract(b3)+"");
				capital.setRongziamount(b1.subtract(b3)+"");
			}
		}
		capitaldao.update(capital);
		super.save(tFinance);
	}
	
	@Transactional(readOnly = false)
	public void delete(TFinance tFinance) {
		super.delete(tFinance);
	}
	
}
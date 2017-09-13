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
import com.wanfin.fpd.modules.catipal.entity.TCapital;
import com.wanfin.fpd.modules.catipal.entity.TProfit;
import com.wanfin.fpd.modules.sys.utils.UserUtils;
import com.wanfin.fpd.modules.catipal.dao.TCapitalDao;
import com.wanfin.fpd.modules.catipal.dao.TProfitDao;

/**
 * 利润列表Service
 * @author lx
 * @version 2016-05-09
 */
@Service
@Transactional(readOnly = true)
public class TProfitService extends CrudService<TProfitDao, TProfit> {

	@Autowired
	private TCapitalDao capitaldao;
	
	public TProfit get(String id) {
		return super.get(id);
	}
	
	public List<TProfit> findList(TProfit tProfit) {
		return super.findList(tProfit);
	}
	
	public Page<TProfit> findPage(Page<TProfit> page, TProfit tProfit) {
		tProfit.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser().getCurrentUser(), "o", "u"));
		return super.findPage(page, tProfit);
	}
	
	@Transactional(readOnly = false)
	public void save(TProfit tProfit,TCapital capital) {
		BigDecimal b1 = new BigDecimal(capital.getProfitamount()) ;//资本信息未分配利润
		BigDecimal b2 = new BigDecimal(capital.getLimitamount()) ; //贷款上限金额

		BigDecimal b3 = new BigDecimal(tProfit.getProfitmoney()) ;//本次利润金额
		if(tProfit.getProfittype()!=null){
			if(tProfit.getProfittype().equals(Cons.ProfitStatus.NO_ASSIGN)){//未分配利润用来借贷（已获批未分配利润>减去相应的“利润金额”）
				capital.setLimitamount(b2.subtract(b3)+"");
				capital.setProfitamount(b1.subtract(b3)+"");
			}else if(tProfit.getProfittype().equals(Cons.ProfitStatus.YES_ASSIGN)){//分配利润用来借贷（已获批未分配利润>增加相应的“利润金额”）
				capital.setLimitamount(b2.add(b3)+"");
				capital.setProfitamount(b1.add(b3)+"");
			}
		}
		capitaldao.update(capital);
		
		super.save(tProfit);
	}
	
	@Transactional(readOnly = false)
	public void delete(TProfit tProfit) {
		super.delete(tProfit);
	}
}
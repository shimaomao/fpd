/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.statistics.service.normal;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.service.CrudService;
import com.wanfin.fpd.modules.statistics.entity.LoanContractVo;
import com.wanfin.fpd.modules.statistics.entity.normal.TongjiNormal;
import com.wanfin.fpd.modules.statistics.dao.normal.TongjiNormalDao;

/**
 * 正常结清期末兑现Service
 * @author 虎
 * @version 2017-04-20
 */
@Service
@Transactional(readOnly = true)
public class TongjiNormalService extends CrudService<TongjiNormalDao, TongjiNormal> {

	public TongjiNormal get(String id) {
		return super.get(id);
	}
	
	public List<TongjiNormal> findList(TongjiNormal tongjiNormal) {
		return super.findList(tongjiNormal);
	}
	
	public Page<TongjiNormal> findPage(Page<TongjiNormal> page, TongjiNormal tongjiNormal) {
		//return super.findPage(page, tongjiNormal);
		tongjiNormal.setPage(page);
		page.setList(tongjiDatail(tongjiNormal));
		return page;
	}
	
	private List<TongjiNormal> tongjiDatail(TongjiNormal tongjiNormal) {
		if(tongjiNormal == null){
			return null;
		}
		if(tongjiNormal.getCurrentMonth() == null){
			tongjiNormal.setCurrentMonth(new Date());
		}
		List<LoanContractVo> listOr = dao.findLoanDetailList(tongjiNormal);
		return dealTongjiDatail(listOr);
	}

	private List<TongjiNormal> dealTongjiDatail(List<LoanContractVo> listOr) {
		// TODO 自动生成的方法存根
		return null;
	}

	@Transactional(readOnly = false)
	public void save(TongjiNormal tongjiNormal) {
		super.save(tongjiNormal);
	}
	
	@Transactional(readOnly = false)
	public void delete(TongjiNormal tongjiNormal) {
		super.delete(tongjiNormal);
	}
	
}
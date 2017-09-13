/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.wish.excel.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wanfin.fpd.common.config.Cons;
import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.service.CrudService;
import com.wanfin.fpd.common.utils.DateUtils;
import com.wanfin.fpd.common.utils.StringUtils;
import com.wanfin.fpd.modules.contract.dao.TLoanContractDao;
import com.wanfin.fpd.modules.wish.excel.dao.WishContractVoDao;
import com.wanfin.fpd.modules.wish.excel.vo.WishContractVo;

/**
 * 业务信息Service，只作为统计用
 * @author srf
 * @version 2017-03-07
 */
@Service
@Transactional(readOnly = true)
public class WishExcelService extends CrudService<WishContractVoDao, WishContractVo> {
    
	
	public WishContractVo get(String id) {
		return super.get(id);
	}
	
	public List<WishContractVo> findList(WishContractVo loanContract) {
		return dao.findLoanDetailList(loanContract);
	}
	
	public Page<WishContractVo> findPage(Page<WishContractVo> page, WishContractVo loanContract) {
		//return super.findPage(page, loanContract);
		loanContract.setPage(page);
		page.setList( loanDatail(loanContract) );
		return page;
	}
	
	//贷款明细信息查询
	public List<WishContractVo> loanDatail(WishContractVo loanContract) {
		if(loanContract == null){
			return null;
		}
		List<WishContractVo> listOr = dao.findLoanDetailList(loanContract);
		return listOr;
	}
	
	/**
	 * @description	贷款明细处理
	 * @param listOr		List<WishContractVo> loanDatail(WishContractVo loanContract) 方法返回的结果
	 * @return
	 */
	
	
//	@Transactional(readOnly = false)
//	public void save(WishContractVo loanContract) {
//		super.save(loanContract);
//	}
	
//	@Transactional(readOnly = false)
//	public void delete(WishContractVo loanContract) {
//		super.delete(loanContract);
//	}
	
}
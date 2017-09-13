/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.tfinancialproduct.dao;

import java.util.List;
import java.util.Map;

import com.wanfin.fpd.common.persistence.CrudDao;
import com.wanfin.fpd.common.persistence.annotation.MyBatisDao;
import com.wanfin.fpd.modules.productbiz.entity.TProductBiz;
import com.wanfin.fpd.modules.tfinancialproduct.entity.TFinancialProduct;

/**
 * 理财产品DAO接口
 * @author lx
 * @version 2016-11-14
 */
@MyBatisDao
public interface TFinancialProductDao extends CrudDao<TFinancialProduct> {
	
	
	public int insertFinaproductLoan(TFinancialProduct tFinancialProduct);
	
	public int deleteFinaproductLoan(TFinancialProduct tFinancialProduct);
	
	
	public List<String> findLoanContractIds(TFinancialProduct tFinancialProduct);
	
	public List<String> getLoanContractIds();


}
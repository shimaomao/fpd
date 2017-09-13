/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.statistics.dao;

import java.util.List;

import com.wanfin.fpd.common.persistence.CrudDao;
import com.wanfin.fpd.common.persistence.annotation.MyBatisDao;
import com.wanfin.fpd.modules.statistics.entity.LoanContractVo;

/**
 * 业务信息DAO接口
 * @author srf
 * @version 2017-03-07
 */
@MyBatisDao
public interface LoanContractDao extends CrudDao<LoanContractVo> {

	List<LoanContractVo> findLoanDetailList(LoanContractVo loanContract);
	
}
/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.preloaninvestigate.dao;

import com.wanfin.fpd.common.persistence.CrudDao;
import com.wanfin.fpd.common.persistence.annotation.MyBatisDao;
import com.wanfin.fpd.modules.contract.entity.TLoanContract;
import com.wanfin.fpd.modules.preloaninvestigate.entity.PreLoanInvestigate;

/**
 * 立项调查DAO接口
 * @author zzm
 * @version 2016-06-13
 */
@MyBatisDao
public interface PreLoanInvestigateDao extends CrudDao<PreLoanInvestigate> {
	
	/**
	 * 更新状态
	 * @param id 合同ID
	 * @param status 更新的状态
	 * @return
	 */
	public int updateStatus(PreLoanInvestigate preLoanInvestigate);
	
}
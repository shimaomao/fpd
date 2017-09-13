/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.contract.dao;

import java.util.List;
import java.util.Map;

import com.wanfin.fpd.common.persistence.CrudDao;
import com.wanfin.fpd.common.persistence.annotation.MyBatisDao;
import com.wanfin.fpd.modules.contract.entity.TLoanContractBak;

/**
 * 业务办理DAO接口
 * @author lx
 * @version 2016-03-18
 */
@MyBatisDao
public interface TLoanContractBakDao extends CrudDao<TLoanContractBak> {
	/**
	 * 更新状态
	 * @param id 合同ID
	 * @param status 更新的状态
	 * @return
	 */
	public int updateStatus(TLoanContractBak tLoanContract);
	
	/**
	 * 获取
	 */
	public List<TLoanContractBak> findRefundList(TLoanContractBak tLoanContract);
	
	/**
	 * 更新对应的业务ID
	 * @author srf
	 * @date 20160413
	 * @param map
	 * @return
	 */
	public int updateBusinessId(Map<String, Object> map);

	/**
	 * @Description 获取逾期业务
	 * @param tLoanContract
	 * @return
	 * @author zzm 
	 * @date 2016-6-8 上午11:01:30  
	 */
	public List<TLoanContractBak> findOverdueList(TLoanContractBak tLoanContract);
	
	/**
	 * @Description 获取未贷前调查的合同
	 * @param tLoanContract
	 * @return
	 * @author zzm
	 * @date 2016-6-13 下午3:11:30  
	 */
	public List<TLoanContractBak> findPreLoanIList(TLoanContractBak tLoanContract);


	/**
	 * @Description 根据订单号获取合同
	 * @param wtypeId
	 * @return
	 */
	public TLoanContractBak getByWtypeId(String wtypeId);
}
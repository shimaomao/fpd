/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.statistics.dao.normal;

import java.util.List;

import com.wanfin.fpd.common.persistence.CrudDao;
import com.wanfin.fpd.common.persistence.annotation.MyBatisDao;
import com.wanfin.fpd.modules.statistics.entity.LoanContractVo;
import com.wanfin.fpd.modules.statistics.entity.normal.TongjiNormal;

/**
 * 正常结清期末兑现DAO接口
 * @author 虎
 * @version 2017-04-20
 */
@MyBatisDao
public interface TongjiNormalDao extends CrudDao<TongjiNormal> {

	List<LoanContractVo> findLoanDetailList(TongjiNormal tongjiNormal);
	
}
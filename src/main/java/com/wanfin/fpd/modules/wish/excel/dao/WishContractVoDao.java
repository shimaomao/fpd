/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.wish.excel.dao;

import java.util.List;

import com.wanfin.fpd.common.persistence.CrudDao;
import com.wanfin.fpd.common.persistence.annotation.MyBatisDao;
import com.wanfin.fpd.modules.statistics.entity.LoanContractVo;
import com.wanfin.fpd.modules.wish.excel.vo.WishContractVo;

/**
 * 业务信息DAO接口
 * @author srf
 * @version 2017-03-07
 */
@MyBatisDao
public interface WishContractVoDao extends CrudDao<WishContractVo> {

	List<WishContractVo> findLoanDetailList(WishContractVo wishContractVo);

	
}
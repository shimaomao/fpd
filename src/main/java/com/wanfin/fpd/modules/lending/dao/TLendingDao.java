/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.lending.dao;

import java.util.List;

import com.wanfin.fpd.common.persistence.CrudDao;
import com.wanfin.fpd.common.persistence.annotation.MyBatisDao;
import com.wanfin.fpd.modules.lending.entity.TLending;
import com.wanfin.fpd.modules.wish.contract.vo.TLendingVo;

/**
 * 放款记录DAO接口
 * @author chenh
 * @version 2016-03-25
 */
@MyBatisDao
public interface TLendingDao extends CrudDao<TLending> {

	/**
	 * @description 根据合同查询放款记录
	 * @auth chenh
	 * @date 2016年3月25日 下午4:21:07
	 * @param page
	 * @param tLending
	 * @return
	 */
	public List<TLending> findPageByLoanContract(TLending tLending);

	public TLending getTLending(TLending tl);

	public List<TLendingVo> findTLendingVoPage(TLendingVo tLendingVo);
}
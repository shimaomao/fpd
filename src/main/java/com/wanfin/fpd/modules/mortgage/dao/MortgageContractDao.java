/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.mortgage.dao;

import java.util.List;

import com.wanfin.fpd.common.persistence.CrudDao;
import com.wanfin.fpd.common.persistence.annotation.MyBatisDao;
import com.wanfin.fpd.modules.mortgage.entity.MortgageContract;

/**
 * 抵押物品DAO接口
 * @author zzm
 * @version 2016-03-27
 */
@MyBatisDao
public interface MortgageContractDao extends CrudDao<MortgageContract> {
	
	
	List<MortgageContract> findListByScanFlag(MortgageContract mortgageContract);
	
	List<MortgageContract> findListByScanFlagByPushStatus(MortgageContract mortgageContract);
	
	/**
	 * 根据推送状态更新记录
	 * @param entity
	 * @return
	 */
    int updateByPushStatus(MortgageContract entity);
}
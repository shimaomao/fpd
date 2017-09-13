/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.pledge.dao;

import java.util.List;

import com.wanfin.fpd.common.persistence.CrudDao;
import com.wanfin.fpd.common.persistence.annotation.MyBatisDao;
import com.wanfin.fpd.modules.pledge.entity.PledgeContract;

/**
 * 质押信息DAO接口
 * @author zzm
 * @version 2016-03-28
 */
@MyBatisDao
public interface PledgeContractDao extends CrudDao<PledgeContract> {
	
	List<PledgeContract> findListByScanFlag(PledgeContract pledgeContract);
	
	List<PledgeContract> findListByScanFlagByPushStatus(PledgeContract pledgeContract);
	
	void updateScanFlag(PledgeContract pledgeContract);
	
	int updateByPushStatus(PledgeContract entity);
}
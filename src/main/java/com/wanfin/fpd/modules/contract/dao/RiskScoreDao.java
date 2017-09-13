/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.contract.dao;

import com.wanfin.fpd.common.persistence.CrudDao;
import com.wanfin.fpd.common.persistence.annotation.MyBatisDao;
import com.wanfin.fpd.modules.contract.entity.RiskScore;

/**
 * 风险控制评分DAO接口
 * @author zzm
 * @version 2016-06-17
 */
@MyBatisDao
public interface RiskScoreDao extends CrudDao<RiskScore> {
	
}
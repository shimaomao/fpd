/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.jrj.dao;

import java.util.List;

import com.wanfin.fpd.common.persistence.CrudDao;
import com.wanfin.fpd.common.persistence.annotation.MyBatisDao;
import com.wanfin.fpd.modules.balancesheep.entity.TBalanceSheep;
import com.wanfin.fpd.modules.jrj.entity.JrjBalanceSheep;
import com.wanfin.fpd.modules.jrj.entity.JrjBusinessSituationOne;
import com.wanfin.fpd.modules.jrj.entity.JrjBusinessSituationTwo;
import com.wanfin.fpd.modules.jrj.entity.JrjProfit;

/**
 * 经营情况月报表二DAO接口
 * @author xzt
 * @version 2016-11-24
 */
@MyBatisDao
public interface JrjBusinessSituationTwoDao extends CrudDao<JrjBusinessSituationTwo> {
	
	List<JrjBusinessSituationTwo> findListByScanFlag(JrjBusinessSituationTwo entity);
	
	int updateByPushStatus(JrjBusinessSituationTwo entity);
}
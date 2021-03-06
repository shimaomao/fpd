/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.jrj.dao;

import java.util.List;

import com.wanfin.fpd.common.persistence.CrudDao;
import com.wanfin.fpd.common.persistence.annotation.MyBatisDao;
import com.wanfin.fpd.modules.balancesheep.entity.TBalanceSheep;
import com.wanfin.fpd.modules.jrj.entity.JrjBalanceSheep;
import com.wanfin.fpd.modules.jrj.entity.JrjCashFlow;
import com.wanfin.fpd.modules.jrj.entity.JrjOldCashFlow;
import com.wanfin.fpd.modules.jrj.entity.JrjProfit;

/**
 * 现金流量DAO接口
 * @author xzt
 * @version 2016-11-13
 */
@MyBatisDao
public interface JrjOldCashFlowDao extends CrudDao<JrjOldCashFlow> {
	
	List<JrjOldCashFlow> findListByScanFlag(JrjOldCashFlow jrjCashFlow);
	
	int updateByPushStatus(JrjOldCashFlow entity);
	
	List<JrjOldCashFlow> findListBySubmitDate(JrjOldCashFlow jrjCashFlow);
}
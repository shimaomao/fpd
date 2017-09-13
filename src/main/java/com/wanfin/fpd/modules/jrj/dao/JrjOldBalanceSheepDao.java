/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.jrj.dao;

import java.util.List;

import com.wanfin.fpd.common.persistence.CrudDao;
import com.wanfin.fpd.common.persistence.annotation.MyBatisDao;
import com.wanfin.fpd.modules.balancesheep.entity.TBalanceSheep;
import com.wanfin.fpd.modules.jrj.entity.JrjBalanceSheep;
import com.wanfin.fpd.modules.jrj.entity.JrjOldBalanceSheep;

/**
 * 资产负债表DAO接口
 * @author xzt
 * @version 2016-05-17
 */
@MyBatisDao
public interface JrjOldBalanceSheepDao extends CrudDao<JrjOldBalanceSheep> {
	
	List<JrjOldBalanceSheep> findListByScanFlag(JrjOldBalanceSheep jrjBalanceSheep);
	
	int updateByPushStatus(JrjOldBalanceSheep entity);
	
	List<JrjOldBalanceSheep> findListBySubmitDate(JrjOldBalanceSheep jrjBalanceSheep);
}
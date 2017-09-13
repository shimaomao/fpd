/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.jrj.dao;

import java.util.List;

import com.wanfin.fpd.common.persistence.CrudDao;
import com.wanfin.fpd.common.persistence.annotation.MyBatisDao;
import com.wanfin.fpd.modules.balancesheep.entity.TBalanceSheep;
import com.wanfin.fpd.modules.jrj.entity.JrjBalanceSheep;
import com.wanfin.fpd.modules.jrj.entity.JrjBusinessStatus;
import com.wanfin.fpd.modules.jrj.entity.JrjProceeds;

/**
 * 担保业务状况DAO接口
 * @author xzt
 * @version 2016-10-29
 */
@MyBatisDao
public interface JrjBusinessStatusDao extends CrudDao<JrjBusinessStatus> {
	
	List<JrjBusinessStatus> findListOrderByCreateDate(JrjBusinessStatus jrjBusinessStatus);
	
	List<JrjBusinessStatus> findListByCreateDate(JrjBusinessStatus jrjBusinessStatus);
	
	List<JrjBusinessStatus> findListByScanFlag(JrjBusinessStatus jrjBusinessStatus);
}
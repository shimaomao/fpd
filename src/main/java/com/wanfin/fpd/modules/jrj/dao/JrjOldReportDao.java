/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.jrj.dao;

import java.util.List;

import com.wanfin.fpd.common.persistence.CrudDao;
import com.wanfin.fpd.common.persistence.annotation.MyBatisDao;
import com.wanfin.fpd.modules.balancesheep.entity.TBalanceSheep;
import com.wanfin.fpd.modules.jrj.entity.JrjBalanceSheep;
import com.wanfin.fpd.modules.jrj.entity.JrjOldReport;

/**
 * 资产负债表DAO接口
 * @author lx
 * @version 2016-05-17
 */
@MyBatisDao
public interface JrjOldReportDao extends CrudDao<JrjOldReport> {
	
	List<JrjOldReport> findListByScanFlag(JrjOldReport jrjOldReport);
	
	int updateByPushStatus(JrjOldReport entity);
	
	List<JrjOldReport> findListBySubmitDate(JrjOldReport jrjOldReport);
}
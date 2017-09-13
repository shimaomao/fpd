/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.jrj.dao;

import java.util.List;

import com.wanfin.fpd.common.persistence.CrudDao;
import com.wanfin.fpd.common.persistence.annotation.MyBatisDao;
import com.wanfin.fpd.modules.jrj.entity.JrjRiskIndicator;

/**
 * 风险指标DAO接口
 * @author xzt
 * @version 2016-10-29
 */
@MyBatisDao
public interface JrjRiskIndicatorDao extends CrudDao<JrjRiskIndicator> {
	
	List<JrjRiskIndicator> findListByscanFlag(JrjRiskIndicator jrjRiskIndicator);
	
}
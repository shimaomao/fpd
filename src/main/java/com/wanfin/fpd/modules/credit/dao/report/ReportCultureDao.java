/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.credit.dao.report;

import org.apache.ibatis.annotations.Param;

import com.wanfin.fpd.common.persistence.CrudDao;
import com.wanfin.fpd.common.persistence.annotation.MyBatisDao;
import com.wanfin.fpd.modules.credit.entity.report.ReportCulture;

/**
 * 贷款项目调查报告养殖贷DAO接口
 * @author srf
 * @version 2017-01-16
 */
@MyBatisDao
public interface ReportCultureDao extends CrudDao<ReportCulture> {

	ReportCulture getByCreditApply(@Param("creditApplyId")String creditApplyId);
	
}
/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.credit.dao.report;

import org.apache.ibatis.annotations.Param;

import com.wanfin.fpd.common.persistence.CrudDao;
import com.wanfin.fpd.common.persistence.annotation.MyBatisDao;
import com.wanfin.fpd.modules.credit.entity.report.ReportEmployee;

/**
 * 贷款项目调查报告职工信用贷DAO接口
 * @author srf
 * @version 2017-01-23
 */
@MyBatisDao
public interface ReportEmployeeDao extends CrudDao<ReportEmployee> {
	ReportEmployee getByCreditApply(@Param("creditApplyId")String creditApplyId);
}
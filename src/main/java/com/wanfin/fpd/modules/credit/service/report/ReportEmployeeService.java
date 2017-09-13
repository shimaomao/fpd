/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.credit.service.report;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.service.CrudService;
import com.wanfin.fpd.modules.credit.entity.report.ReportEmployee;
import com.wanfin.fpd.modules.credit.dao.report.ReportEmployeeDao;

/**
 * 贷款项目调查报告职工信用贷Service
 * @author srf
 * @version 2017-01-23
 */
@Service
@Transactional(readOnly = true)
public class ReportEmployeeService extends CrudService<ReportEmployeeDao, ReportEmployee> {

	public ReportEmployee get(String id) {
		return super.get(id);
	}
	
	public ReportEmployee getByCreditApply(String creditApplyId) {
		return dao.getByCreditApply(creditApplyId);
	}
	
	public List<ReportEmployee> findList(ReportEmployee reportEmployee) {
		return super.findList(reportEmployee);
	}
	
	public Page<ReportEmployee> findPage(Page<ReportEmployee> page, ReportEmployee reportEmployee) {
		return super.findPage(page, reportEmployee);
	}
	
	@Transactional(readOnly = false)
	public void save(ReportEmployee reportEmployee) {
		super.save(reportEmployee);
	}
	
	@Transactional(readOnly = false)
	public void delete(ReportEmployee reportEmployee) {
		super.delete(reportEmployee);
	}
	
}
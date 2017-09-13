/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.credit.service.report;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.service.CrudService;
import com.wanfin.fpd.modules.credit.entity.report.ReportCompany;
import com.wanfin.fpd.modules.credit.dao.report.ReportCompanyDao;

/**
 * 贷款项目调查报告企业贷Service
 * @author srf
 * @version 2017-01-23
 */
@Service
@Transactional(readOnly = true)
public class ReportCompanyService extends CrudService<ReportCompanyDao, ReportCompany> {

	public ReportCompany get(String id) {
		return super.get(id);
	}
	
	public ReportCompany getByCreditApply(String creditApplyId) {
		return dao.getByCreditApply(creditApplyId);
	}
	
	public List<ReportCompany> findList(ReportCompany reportCompany) {
		return super.findList(reportCompany);
	}
	
	public Page<ReportCompany> findPage(Page<ReportCompany> page, ReportCompany reportCompany) {
		return super.findPage(page, reportCompany);
	}
	
	@Transactional(readOnly = false)
	public void save(ReportCompany reportCompany) {
		super.save(reportCompany);
	}
	
	@Transactional(readOnly = false)
	public void delete(ReportCompany reportCompany) {
		super.delete(reportCompany);
	}
	
}
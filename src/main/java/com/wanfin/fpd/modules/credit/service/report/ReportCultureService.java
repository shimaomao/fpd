/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.credit.service.report;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.service.CrudService;
import com.wanfin.fpd.modules.credit.entity.report.ReportCulture;
import com.wanfin.fpd.modules.credit.dao.report.ReportCultureDao;

/**
 * 贷款项目调查报告养殖贷Service
 * @author srf
 * @version 2017-01-16
 */
@Service
@Transactional(readOnly = true)
public class ReportCultureService extends CrudService<ReportCultureDao, ReportCulture> {

	public ReportCulture get(String id) {
		return super.get(id);
	}
	
	public ReportCulture getByCreditApply(String creditApplyId) {
		return dao.getByCreditApply(creditApplyId);
	}
	
	public List<ReportCulture> findList(ReportCulture reportCulture) {
		return super.findList(reportCulture);
	}
	
	public Page<ReportCulture> findPage(Page<ReportCulture> page, ReportCulture reportCulture) {
		return super.findPage(page, reportCulture);
	}
	
	@Transactional(readOnly = false)
	public void save(ReportCulture reportCulture) {
		super.save(reportCulture);
	}
	
	@Transactional(readOnly = false)
	public void delete(ReportCulture reportCulture) {
		super.delete(reportCulture);
	}
	
}
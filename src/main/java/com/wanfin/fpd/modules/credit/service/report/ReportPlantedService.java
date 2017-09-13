/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.credit.service.report;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.service.CrudService;
import com.wanfin.fpd.modules.credit.entity.report.ReportPlanted;
import com.wanfin.fpd.modules.credit.dao.report.ReportPlantedDao;

/**
 * 贷款项目调查报告种殖贷Service
 * @author srf
 * @version 2017-01-16
 */
@Service
@Transactional(readOnly = true)
public class ReportPlantedService extends CrudService<ReportPlantedDao, ReportPlanted> {

	public ReportPlanted get(String id) {
		return super.get(id);
	}
	
	public ReportPlanted getByCreditApply(String creditApplyId) {
		return dao.getByCreditApply(creditApplyId);
	}
	
	public List<ReportPlanted> findList(ReportPlanted reportPlanted) {
		return super.findList(reportPlanted);
	}
	
	public Page<ReportPlanted> findPage(Page<ReportPlanted> page, ReportPlanted reportPlanted) {
		return super.findPage(page, reportPlanted);
	}
	
	@Transactional(readOnly = false)
	public void save(ReportPlanted reportPlanted) {
		super.save(reportPlanted);
	}
	
	@Transactional(readOnly = false)
	public void delete(ReportPlanted reportPlanted) {
		super.delete(reportPlanted);
	}
	
}
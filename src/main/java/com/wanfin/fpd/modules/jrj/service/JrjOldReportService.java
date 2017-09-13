/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.jrj.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.service.CrudService;
import com.wanfin.fpd.modules.balancesheep.entity.TBalanceSheep;
import com.wanfin.fpd.modules.contract.entity.TLoanContract;
import com.wanfin.fpd.modules.jrj.dao.JrjBalanceSheepDao;
import com.wanfin.fpd.modules.jrj.dao.JrjOldReportDao;
import com.wanfin.fpd.modules.jrj.entity.JrjBalanceSheep;
import com.wanfin.fpd.modules.jrj.entity.JrjOldReport;
import com.wanfin.fpd.modules.balancesheep.dao.TBalanceSheepDao;

/**
 * 资产负债表Service
 * @author lx
 * @version 2016-05-17
 */
@Service("jrjOldReportService")
@Transactional(readOnly = true)
public class JrjOldReportService extends CrudService<JrjOldReportDao, JrjOldReport> {
	
	@Autowired
	JrjOldReportDao jrjOldReportDao;

	public JrjOldReport get(String id) {
		return super.get(id);
	}
	
	public List<JrjOldReport> findList(JrjOldReport jrjOldReport) {
		return super.findList(jrjOldReport);
	}
	
	public Page<JrjOldReport> findPage(Page<JrjOldReport> page, JrjOldReport jrjOldReport) {
		return super.findPage(page, jrjOldReport);
	}
	
	@Transactional(readOnly = false)
	public void save(JrjOldReport jrjOldReport) {
		super.save(jrjOldReport);
	}
	
	@Transactional(readOnly = false)
	public void delete(JrjOldReport jrjOldReport) {
		super.delete(jrjOldReport);
	}
	
	public List<JrjOldReport> findListByScanFlag(JrjOldReport jrjOldReport){
		return jrjOldReportDao.findListByScanFlag(jrjOldReport);
	}
	
	public List<JrjOldReport> findListBySubmitDate(JrjOldReport jrjOldReport){
		return jrjOldReportDao.findListBySubmitDate(jrjOldReport);
	}
	
	public int updateByPushStatus(JrjOldReport entity){
		return jrjOldReportDao.updateByPushStatus(entity);
	}
	
	
	@Transactional(readOnly = false)
	public StringBuffer updateGetListByscanFlagData(String fileName) {
		StringBuffer data = new StringBuffer();
		JrjOldReport queryEntity = new JrjOldReport();
		queryEntity.setScanFlag("0");
		List<JrjOldReport> list = jrjOldReportDao.findListByScanFlag(queryEntity);
		if (list != null && list.size() > 0) {			
			// 获取数据
			for (JrjOldReport temp : list) {
				data.append(temp.senData());
				data.append("\r\n");
			}
			// 更改已经做了扫描处理的标示
			for (JrjOldReport temp : list) {
				temp.setScanFlag("1");
				jrjOldReportDao.update(temp);
			}
			for (JrjOldReport temp : list) {
				temp.setScanFlag("1");
				temp.setPushStatus("1");
				jrjOldReportDao.updateByPushStatus(temp);
			}
		}
		return data;
	}	
	
}
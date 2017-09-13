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
import com.wanfin.fpd.modules.jrj.dao.JrjBalanceSheepDao;
import com.wanfin.fpd.modules.jrj.dao.JrjCashFlowDao;
import com.wanfin.fpd.modules.jrj.dao.JrjOldCashFlowDao;
import com.wanfin.fpd.modules.jrj.dao.JrjProfitDao;
import com.wanfin.fpd.modules.jrj.entity.JrjBalanceSheep;
import com.wanfin.fpd.modules.jrj.entity.JrjCashFlow;
import com.wanfin.fpd.modules.jrj.entity.JrjOldCashFlow;
import com.wanfin.fpd.modules.jrj.entity.JrjProfit;
import com.wanfin.fpd.modules.balancesheep.dao.TBalanceSheepDao;

/**
 * 利润表Service
 * @author zxt
 * @version 2016-11-13
 */
@Service("jrjOldCashFlowService")
@Transactional(readOnly = true)
public class JrjOldCashFlowService extends CrudService<JrjOldCashFlowDao, JrjOldCashFlow> {
	
	@Autowired
	JrjOldCashFlowDao jrjCashFlowDao;

	public JrjOldCashFlow get(String id) {
		return super.get(id);
	}
	
	public List<JrjOldCashFlow> findList(JrjOldCashFlow jrjCashFlow) {
		return super.findList(jrjCashFlow);
	}
	
	public Page<JrjOldCashFlow> findPage(Page<JrjOldCashFlow> page, JrjOldCashFlow jrjCashFlow) {
		return super.findPage(page, jrjCashFlow);
	}
	
	@Transactional(readOnly = false)
	public void save(JrjOldCashFlow jrjCashFlow) {
		super.save(jrjCashFlow);
	}
	
	@Transactional(readOnly = false)
	public void delete(JrjOldCashFlow jrjCashFlow) {
		super.delete(jrjCashFlow);
	}
	
	public List<JrjOldCashFlow> findListByScanFlag(JrjOldCashFlow jrjCashFlow){
		return jrjCashFlowDao.findListByScanFlag(jrjCashFlow);
	}
	
	public List<JrjOldCashFlow> findListBySubmitDate(JrjOldCashFlow jrjCashFlow){
		return jrjCashFlowDao.findListBySubmitDate(jrjCashFlow);
	}
	
	
	@Transactional(readOnly = false)
	public StringBuffer updateGetListByscanFlagData() {
		StringBuffer data = new StringBuffer();
		JrjOldCashFlow queryEntity = new JrjOldCashFlow();
		queryEntity.setScanFlag("0");
		List<JrjOldCashFlow> list = jrjCashFlowDao.findListByScanFlag(queryEntity);
		if (list != null && list.size() > 0) {			
			// 获取数据
			for (JrjOldCashFlow temp : list) {
				data.append(temp.senData());
				data.append("\r\n");
			}
			// 更改已经做了扫描处理的标示
			for (JrjOldCashFlow temp : list) {
				temp.setScanFlag("1");
				jrjCashFlowDao.update(temp);
			}
			for (JrjOldCashFlow temp : list) {
				temp.setScanFlag("1");
				temp.setPushStatus("1");
				jrjCashFlowDao.updateByPushStatus(temp);
			}
		}
		return data;
	}	
}
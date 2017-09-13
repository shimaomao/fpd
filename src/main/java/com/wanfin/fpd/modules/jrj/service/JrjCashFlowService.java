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
import com.wanfin.fpd.modules.jrj.dao.JrjProfitDao;
import com.wanfin.fpd.modules.jrj.entity.JrjBalanceSheep;
import com.wanfin.fpd.modules.jrj.entity.JrjCashFlow;
import com.wanfin.fpd.modules.jrj.entity.JrjProfit;
import com.wanfin.fpd.modules.balancesheep.dao.TBalanceSheepDao;

/**
 * 利润表Service
 * @author zxt
 * @version 2016-11-13
 */
@Service("jrjCashFlowService")
@Transactional(readOnly = true)
public class JrjCashFlowService extends CrudService<JrjCashFlowDao, JrjCashFlow> {
	
	@Autowired
	JrjCashFlowDao jrjCashFlowDao;

	public JrjCashFlow get(String id) {
		return super.get(id);
	}
	
	public List<JrjCashFlow> findList(JrjCashFlow jrjCashFlow) {
		return super.findList(jrjCashFlow);
	}
	
	public Page<JrjCashFlow> findPage(Page<JrjCashFlow> page, JrjCashFlow jrjCashFlow) {
		return super.findPage(page, jrjCashFlow);
	}
	
	@Transactional(readOnly = false)
	public void save(JrjCashFlow jrjCashFlow) {
		super.save(jrjCashFlow);
	}
	
	@Transactional(readOnly = false)
	public void delete(JrjCashFlow jrjCashFlow) {
		super.delete(jrjCashFlow);
	}
	
	public List<JrjCashFlow> findListByScanFlag(JrjCashFlow jrjCashFlow){
		return jrjCashFlowDao.findListByScanFlag(jrjCashFlow);
	}
	
	public List<JrjCashFlow> findListBySubmitDate(JrjCashFlow jrjCashFlow){
		return jrjCashFlowDao.findListBySubmitDate(jrjCashFlow);
	}
	
	
	@Transactional(readOnly = false)
	public StringBuffer updateGetListByscanFlagData() {
		StringBuffer data = new StringBuffer();
		JrjCashFlow queryEntity = new JrjCashFlow();
		queryEntity.setScanFlag("0");
		List<JrjCashFlow> list = jrjCashFlowDao.findListByScanFlag(queryEntity);
		if (list != null && list.size() > 0) {			
			// 获取数据
			for (JrjCashFlow temp : list) {
				data.append(temp.senData());
				data.append("\r\n");
			}
			// 更改已经做了扫描处理的标示
			/*for (JrjCashFlow temp : list) {
				temp.setScanFlag("1");
				jrjCashFlowDao.update(temp);
			}*/
			for (JrjCashFlow temp : list) {
				temp.setScanFlag("1");
				temp.setPushStatus("1");
				jrjCashFlowDao.updateByPushStatus(temp);
			}
		}
		return data;
	}	
}
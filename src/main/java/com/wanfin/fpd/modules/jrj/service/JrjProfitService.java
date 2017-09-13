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
import com.wanfin.fpd.modules.jrj.dao.JrjProfitDao;
import com.wanfin.fpd.modules.jrj.entity.JrjBalanceSheep;
import com.wanfin.fpd.modules.jrj.entity.JrjProfit;
import com.wanfin.fpd.modules.balancesheep.dao.TBalanceSheepDao;

/**
 * 利润表Service
 * @author zxt
 * @version 2016-11-13
 */
@Service("jrjProfitService")
@Transactional(readOnly = true)
public class JrjProfitService extends CrudService<JrjProfitDao, JrjProfit> {
	
	@Autowired
	JrjProfitDao jrjProfitDao;

	public JrjProfit get(String id) {
		return super.get(id);
	}
	
	public List<JrjProfit> findList(JrjProfit jrjProfit) {
		return super.findList(jrjProfit);
	}
	
	public Page<JrjProfit> findPage(Page<JrjProfit> page, JrjProfit jrjProfit) {
		return super.findPage(page, jrjProfit);
	}
	
	@Transactional(readOnly = false)
	public void save(JrjProfit jrjProfit) {
		super.save(jrjProfit);
	}
	
	@Transactional(readOnly = false)
	public void delete(JrjProfit jrjProfit) {
		super.delete(jrjProfit);
	}
	
	public List<JrjProfit> findListByScanFlag(JrjProfit jrjProfit){
		return jrjProfitDao.findListByScanFlag(jrjProfit);
	}
	
	public List<JrjProfit> findListBySubmitDate(JrjProfit jrjProfit){
		return jrjProfitDao.findListBySubmitDate(jrjProfit);
	}
	
	
	@Transactional(readOnly = false)
	public StringBuffer updateGetListByscanFlagData() {
		StringBuffer data = new StringBuffer();
		JrjProfit queryEntity = new JrjProfit();
		queryEntity.setScanFlag("0");
		List<JrjProfit> list = jrjProfitDao.findListByScanFlag(queryEntity);
		if (list != null && list.size() > 0) {			
			// 获取数据
			for (JrjProfit temp : list) {
				data.append(temp.senData());
				data.append("\r\n");
			}
			// 更改已经做了扫描处理的标示
			/*for (JrjProfit temp : list) {
				temp.setScanFlag("1");
				jrjProfitDao.update(temp);
			}*/
			for (JrjProfit temp : list) {
				temp.setScanFlag("1");
				temp.setPushStatus("1");
				jrjProfitDao.updateByPushStatus(temp);
			}
		}
		return data;
	}	
	
}
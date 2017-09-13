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
import com.wanfin.fpd.modules.jrj.dao.JrjOldProfitDao;
import com.wanfin.fpd.modules.jrj.dao.JrjProfitDao;
import com.wanfin.fpd.modules.jrj.entity.JrjBalanceSheep;
import com.wanfin.fpd.modules.jrj.entity.JrjOldProfit;
import com.wanfin.fpd.modules.jrj.entity.JrjProfit;
import com.wanfin.fpd.modules.balancesheep.dao.TBalanceSheepDao;

/**
 * 旧利润表Service
 * @author zxt
 * @version 2016-11-13
 */
@Service("jrjOldProfitService")
@Transactional(readOnly = true)
public class JrjOldProfitService extends CrudService<JrjOldProfitDao, JrjOldProfit> {
	
	@Autowired
	JrjOldProfitDao jrjProfitDao;

	public JrjOldProfit get(String id) {
		return super.get(id);
	}
	
	public List<JrjOldProfit> findList(JrjOldProfit jrjProfit) {
		return super.findList(jrjProfit);
	}
	
	public Page<JrjOldProfit> findPage(Page<JrjOldProfit> page, JrjOldProfit jrjProfit) {
		return super.findPage(page, jrjProfit);
	}
	
	@Transactional(readOnly = false)
	public void save(JrjOldProfit jrjProfit) {
		super.save(jrjProfit);
	}
	
	@Transactional(readOnly = false)
	public void delete(JrjOldProfit jrjProfit) {
		super.delete(jrjProfit);
	}
	
	public List<JrjOldProfit> findListByScanFlag(JrjOldProfit jrjProfit){
		return jrjProfitDao.findListByScanFlag(jrjProfit);
	}
	
	public List<JrjOldProfit> findListBySubmitDate(JrjOldProfit jrjProfit){
		return jrjProfitDao.findListBySubmitDate(jrjProfit);
	}
	
	
	@Transactional(readOnly = false)
	public StringBuffer updateGetListByscanFlagData() {
		StringBuffer data = new StringBuffer();
		JrjOldProfit queryEntity = new JrjOldProfit();
		queryEntity.setScanFlag("0");
		List<JrjOldProfit> list = jrjProfitDao.findListByScanFlag(queryEntity);
		if (list != null && list.size() > 0) {			
			// 获取数据
			for (JrjOldProfit temp : list) {
				data.append(temp.senData());
				data.append("\r\n");
			}
			// 更改已经做了扫描处理的标示
			for (JrjOldProfit temp : list) {
				temp.setScanFlag("1");
				jrjProfitDao.update(temp);
			}
			for (JrjOldProfit temp : list) {
				temp.setScanFlag("1");
				temp.setPushStatus("1");
				jrjProfitDao.updateByPushStatus(temp);
			}
		}
		return data;
	}	
	
}
/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.statistics.service.achievements;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.service.CrudService;
import com.wanfin.fpd.modules.statistics.entity.LoanContractVo;
import com.wanfin.fpd.modules.statistics.entity.achievements.TongjiAchievements;
import com.wanfin.fpd.modules.statistics.dao.achievements.TongjiAchievementsDao;

/**
 * 绩效所需项目Service
 * @author lxh
 * @version 2017-04-20
 */
@Service
@Transactional(readOnly = true)
public class TongjiAchievementsService extends CrudService<TongjiAchievementsDao, TongjiAchievements> {

	public TongjiAchievements get(String id) {
		return super.get(id);
	}
	
	public List<TongjiAchievements> findList(TongjiAchievements tongjiAchievements) {
		return super.findList(tongjiAchievements);
	}
	
	public Page<TongjiAchievements> findPage(Page<TongjiAchievements> page, TongjiAchievements tongjiAchievements) {
		//return super.findPage(page, tongjiAchievements);
		tongjiAchievements.setPage(page);
		page.setList( tongjiDatail(tongjiAchievements) );
		return page;
	}
	
	private List<TongjiAchievements> tongjiDatail(TongjiAchievements tongjiAchievements) {
		if(tongjiAchievements == null){
			return null;
		}
		if(tongjiAchievements.getCurrentMonth() == null){
			tongjiAchievements.setCurrentMonth(new Date());
		}
		List<LoanContractVo> listOr = dao.findLoanDetailList(tongjiAchievements);
		return dealTongjiDatail(listOr);
	}

	private List<TongjiAchievements> dealTongjiDatail(
			List<LoanContractVo> listOr) {
		// TODO 自动生成的方法存根
		return null;
	}

	@Transactional(readOnly = false)
	public void save(TongjiAchievements tongjiAchievements) {
		super.save(tongjiAchievements);
	}
	
	@Transactional(readOnly = false)
	public void delete(TongjiAchievements tongjiAchievements) {
		super.delete(tongjiAchievements);
	}
	
}
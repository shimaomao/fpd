/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.catipal.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.service.CrudService;
import com.wanfin.fpd.common.utils.StringUtils;
import com.wanfin.fpd.modules.catipal.entity.TCapital;
import com.wanfin.fpd.modules.sys.utils.UserUtils;
import com.wanfin.fpd.modules.catipal.dao.TCapitalDao;

/**
 * 资本信息Service
 * @author lx
 * @version 2016-05-09
 */
@Service
@Transactional(readOnly = true)
public class TCapitalService extends CrudService<TCapitalDao, TCapital> {

	public TCapital get(String id) {
		return super.get(id);
	}
	
	public List<TCapital> findList(TCapital tCapital) {
		return super.findList(tCapital);
	}
	
	public Page<TCapital> findPage(Page<TCapital> page, TCapital tCapital) {
		tCapital.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser().getCurrentUser(), "o", "u"));
		return super.findPage(page, tCapital);
	}
	
	@Transactional(readOnly = false)
	public void save(TCapital tCapital) {
		if (StringUtils.isBlank(tCapital.getId())){
			tCapital.preInsert();
			dao.insert(tCapital);
		}else{
			// 更新数据
			tCapital.preUpdate();
			dao.update(tCapital);
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(TCapital tCapital) {
		super.delete(tCapital);
	}

	public TCapital getByOrganId(String organId) {
		return dao.getByOrganId(organId);
	}
	
}
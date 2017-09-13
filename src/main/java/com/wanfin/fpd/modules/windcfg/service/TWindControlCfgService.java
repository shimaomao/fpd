/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.windcfg.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.service.CrudService;
import com.wanfin.fpd.common.utils.StringUtils;
import com.wanfin.fpd.modules.windcfg.dao.TWindControlCfgDao;
import com.wanfin.fpd.modules.windcfg.entity.TWindControlCfg;

/**
 * 风控配置Service
 * @author chenh
 * @version 2016-05-26
 */
@Service
@Transactional(readOnly = true)
public class TWindControlCfgService extends CrudService<TWindControlCfgDao, TWindControlCfg> {

	public TWindControlCfg get(String id) {
		return super.get(id);
	}
	
	public List<TWindControlCfg> findList(TWindControlCfg tWindControlCfg) {
		return super.findList(tWindControlCfg);
	}
	
	public Page<TWindControlCfg> findPage(Page<TWindControlCfg> page, TWindControlCfg tWindControlCfg) {
		return super.findPage(page, tWindControlCfg);
	}
	
	@Transactional(readOnly = false)
	public void save(TWindControlCfg tWindControlCfg) {
		super.save(tWindControlCfg);
	}
	
	@Transactional(readOnly = false)
	public void delete(TWindControlCfg tWindControlCfg) {
		super.delete(tWindControlCfg);
	}

	/**
	 * @Description TODO
	 * @param ids
	 * @author Chenh 
	 * @date 2016年5月27日 上午11:26:20  
	 */
	@Transactional(readOnly = false)
	public void deleteWLByRelId(List<String> ids) {
		super.dao.deleteWLByRelId(StringUtils.sqlInByList(ids));
	}
	/**
	 * @Description TODO
	 * @param ids
	 * @author Chenh 
	 * @date 2016年5月27日 上午11:26:20  
	 */
	@Transactional(readOnly = false)
	public void deleteWLByRelIdList(List<String> ids, String productId) {
		super.dao.deleteWLByRelIdList(ids, productId);
	}
}
/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.wind.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wanfin.fpd.common.config.Cons;
import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.service.CrudService;
import com.wanfin.fpd.common.utils.StringUtils;
import com.wanfin.fpd.modules.sys.entity.FeeBiz;
import com.wanfin.fpd.modules.sys.service.OpenBizService;
import com.wanfin.fpd.modules.sys.utils.UserUtils;
import com.wanfin.fpd.modules.wind.dao.TWindControlDao;
import com.wanfin.fpd.modules.wind.entity.TWindControl;

/**
 * 风控模型Service
 * @author lx
 * @version 2016-05-03
 */
@Service
@Transactional(readOnly = true)
public class TWindControlService extends CrudService<TWindControlDao, TWindControl> {
	
	@Autowired
	private OpenBizService openBizService;

	public TWindControl get(String id) {
		return super.get(id);
	}
	
	public List<TWindControl> findList(TWindControl tWindControl) {
		return super.findList(tWindControl);
	}
	
	public List<TWindControl> findListByids(String id) {
		return dao.findListByids(id);
	}
	
	public Page<TWindControl> findPage(Page<TWindControl> page, TWindControl tWindControl) {
		return super.findPage(page, tWindControl);
	}
	
	@Transactional(readOnly = false)
	public void save(TWindControl tWindControl) {
		super.save(tWindControl);
	}
	
	@Transactional(readOnly = false)
	public void delete(TWindControl tWindControl) {
		super.delete(tWindControl);
	}

	/**
	 * @Description 获取当前租户开通的风控模型
	 * @return
	 * @author zzm
	 * @date 2016-6-22 上午9:41:10  
	 */
	public List<TWindControl> findOrganList() {
		FeeBiz feeBiz = new FeeBiz();
		feeBiz.setCategory(Cons.FeeBizCategory.RISKTIP);
		feeBiz.setOrganId(UserUtils.getUser().getCompany().getId());
		List<FeeBiz> feeBizList = openBizService.findFeeBizList(feeBiz);
		StringBuffer sb = new StringBuffer("''");
		for(FeeBiz f : feeBizList){
			if(StringUtils.equals(f.getIsSelect() ,"1")){
				sb.append(",'"+f.getBizCode()+"'");
			}
		}
		return findListByids(sb.toString());
	}
	
}
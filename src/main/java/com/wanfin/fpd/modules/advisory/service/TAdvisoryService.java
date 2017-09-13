/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.advisory.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wanfin.fpd.common.config.Cons;
import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.service.CrudService;
import com.wanfin.fpd.common.utils.StringUtils;
import com.wanfin.fpd.modules.advisory.dao.TAdvisoryDao;
import com.wanfin.fpd.modules.advisory.entity.TAdvisory;
import com.wanfin.fpd.modules.product.entity.TProduct;
import com.wanfin.fpd.modules.sys.utils.UserUtils;

/**
 * 业务受理咨询Service
 * @author cdy
 * @version 2016-03-15
 */
@Service
@Transactional(readOnly = true)
public class TAdvisoryService extends CrudService<TAdvisoryDao, TAdvisory> {

	public TAdvisory get(String id) {
		return super.get(id);
	}
	
	public List<TAdvisory> findList(TAdvisory tAdvisory) {
		return super.findList(tAdvisory);
	}
	
	public Page<TAdvisory> findPage(Page<TAdvisory> page, TAdvisory tAdvisory) {
		// 生成数据权限过滤条件（dsf为dataScopeFilter的简写，在xml中使用 ${sqlMap.dsf}调用权限SQL）
		tAdvisory.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser().getCurrentUser(), "o", "u"));
		return super.findPage(page, tAdvisory);
	}
	
	@Transactional(readOnly = false)
	public void save(TAdvisory tAdvisory) {
		if(StringUtils.isNotBlank(tAdvisory.getId()) && tAdvisory.getId().startsWith("tmp_")){
			tAdvisory.setId(null);
		}
		if(tAdvisory.getProduct() == null)
			tAdvisory.setProduct(new TProduct((String)UserUtils.getCache(UserUtils.CACHE_SYSCODE)));
		super.save(tAdvisory);
	}
	
	@Transactional(readOnly = false)
	public void delete(TAdvisory tAdvisory) {
		super.delete(tAdvisory);
	}

}
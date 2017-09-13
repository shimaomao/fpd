/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.billing.service.price;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.service.CrudService;
import com.wanfin.fpd.common.utils.StringUtils;
import com.wanfin.fpd.modules.billing.dao.price.BiPriceDao;
import com.wanfin.fpd.modules.billing.entity.price.BiPrice;
import com.wanfin.fpd.modules.sys.utils.UserUtils;

/**
 * 收费单价Service
 * @author chenh
 * @version 2016-07-01
 */
@Service
@Transactional(readOnly = true)
public class BiPriceService extends CrudService<BiPriceDao, BiPrice> {

	public BiPrice get(String id) {
		return super.get(id);
	}
	
	public List<BiPrice> findList(BiPrice biPrice) {
		return super.findList(biPrice);
	}
	
	public Page<BiPrice> findPage(Page<BiPrice> page, BiPrice biPrice) {
		return super.findPage(page, biPrice);
	}
	
	@Transactional(readOnly = false)
	public void save(BiPrice biPrice) {
		if(StringUtils.isEmpty(biPrice.getOrganId())){
			biPrice.setOrganId(UserUtils.getUser().getCompany().getId());
		}
		biPrice.setUnitVal((long) 1);
		super.save(biPrice);
	}
	
	@Transactional(readOnly = false)
	public void delete(BiPrice biPrice) {
		super.delete(biPrice);
	}
	
}
/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.api.wiss.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.service.CrudService;
import com.wanfin.fpd.common.utils.StringUtils;
import com.wanfin.fpd.modules.api.wiss.entity.YlExchangeRates;
import com.wanfin.fpd.modules.api.wiss.dao.YlExchangeRatesDao;

/**
 * 汇率-易联Service
 * @author srf
 * @version 2017-07-05
 */
@Service
@Transactional(readOnly = true)
public class YlExchangeRatesService extends CrudService<YlExchangeRatesDao, YlExchangeRates> {

	public YlExchangeRates get(String id) {
		return super.get(id);
	}
	
	/**
	 * 通过原币种、兑换币种、所属日期获取汇率
	 * @param ylExchangeRates
	 * @return
	 */
	public YlExchangeRates getRate(YlExchangeRates ylExchangeRates) {
		if(ylExchangeRates == null || StringUtils.isAnyBlank(ylExchangeRates.getOrigCurrency(), ylExchangeRates.getExchCurrency()) || ylExchangeRates.getDate() == null){
			return null;
		}
		return dao.getRate(ylExchangeRates);
	}
	
	public List<YlExchangeRates> findList(YlExchangeRates ylExchangeRates) {
		return super.findList(ylExchangeRates);
	}
	
	public Page<YlExchangeRates> findPage(Page<YlExchangeRates> page, YlExchangeRates ylExchangeRates) {
		return super.findPage(page, ylExchangeRates);
	}
	
	@Transactional(readOnly = false)
	public void save(YlExchangeRates ylExchangeRates) {
		super.save(ylExchangeRates);
	}
	
	@Transactional(readOnly = false)
	public void delete(YlExchangeRates ylExchangeRates) {
		super.delete(ylExchangeRates);
	}
	
}
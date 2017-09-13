/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.billing.service.rule;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.service.CrudService;
import com.wanfin.fpd.common.utils.StringUtils;
import com.wanfin.fpd.core.billing.BiSval;
import com.wanfin.fpd.core.billing.BiTime;
import com.wanfin.fpd.modules.billing.dao.rule.BiRuleDao;
import com.wanfin.fpd.modules.billing.entity.price.BiPrice;
import com.wanfin.fpd.modules.billing.entity.rule.BiRule;
import com.wanfin.fpd.modules.billing.service.price.BiPriceService;
import com.wanfin.fpd.modules.sys.utils.UserUtils;

/**
 * 收费规则Service
 * @author chenh
 * @version 2016-07-01
 */
@Service
@Transactional(readOnly = true)
public class BiRuleService extends CrudService<BiRuleDao, BiRule> {
	@Autowired
	private BiPriceService biPriceService;
	
	public BiRule get(String id) {
		return super.get(id);
	}
	
	public List<BiRule> findList(BiRule biRule) {
		return super.findList(biRule);
	}
	
	public Page<BiRule> findPage(Page<BiRule> page, BiRule biRule) {
		return super.findPage(page, biRule);
	}
	
	@Transactional(readOnly = false)
	public void save(BiRule biRule) {
		biRule = init(biRule);
		if(biRule != null){
			if(StringUtils.isEmpty(biRule.getOrganId())){
				biRule.setOrganId(UserUtils.getUser().getCompany().getId());
			}
			super.save(biRule);
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(BiRule biRule) {
		super.delete(biRule);
	}

	/**
	 * 初始化平均数、总额、总时间、计费类型
	 * @param biRule
	 */
	public BiRule init(BiRule biRule) {
		if(biRule.getPrice() != null){
			BiPrice price = biRule.getPrice();
			if((price.getUnitVal() == null) || (price.getPrice() == null) || (price.getType() == null)){
				if(StringUtils.isNotEmpty(price.getId())){
					price = biPriceService.get(price.getId());
				}else{
					return null;
				}
			}

			/**
			 * 单价数量=(单位数量*单位转换比例)
			 * 提醒数量=(单位数量*单位转换比例/10)
			 * 
			 * 总价格=单位价格*单价数量*优惠率
			 * 
			 * 平均价格=总价格/单价数量*单价单位数量
			 * 
			 * 总时间=(单价数量*1小时)=(单位数量*1单位)
			 */
			biRule.setType(price.getType());
			if((biRule.getType()).equals(BiSval.BiType.TIME)){
				biRule.setNumber((long) BiTime.calculateHours(biRule));
				biRule.setTotalTime(biRule.getNumber()*price.getUnitVal());
			}else if((biRule.getType()).equals(BiSval.BiType.NUM)){
				biRule.setNumber((long) biRule.getUnitVal());
				biRule.setTotalTime(Long.MAX_VALUE);
			}
			biRule.setWaringNumber(biRule.getNumber()/BiSval.WARING_NUMBER);
			
			biRule.setTotalPrice(price.getPrice()*biRule.getNumber()*biRule.getRate());
			
			biRule.setAveragePrice(biRule.getTotalPrice()/(biRule.getNumber()*price.getUnitVal()));
			
			return biRule;
		}
		return null;
	}
}
package com.wanfin.fpd.core.billing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wanfin.fpd.modules.billing.service.BiService;
import com.wanfin.fpd.modules.billing.service.collect.BiCollectService;
import com.wanfin.fpd.modules.billing.service.element.BiElementService;
import com.wanfin.fpd.modules.billing.service.price.BiPriceService;
import com.wanfin.fpd.modules.billing.service.rule.BiRuleService;

@Service
@Transactional(readOnly = true)
public class BillingEngine {
	@Autowired
	private BiService bi;
	@Autowired
	private BiElementService element;
	@Autowired
	private BiCollectService collect;
	@Autowired
	private BiPriceService price;
	@Autowired
	private BiRuleService rule;
	
	public BiElementService element() {
		return element;
	}
	public BiRuleService rule() {
		return rule;
	}
	public BiCollectService collect() {
		return collect;
	}
	public BiPriceService price() {
		return price;
	}
	public BiService bi() {
		return bi;
	}
	public void setBi(BiService bi) {
		this.bi = bi;
	}
	public void setElement(BiElementService element) {
		this.element = element;
	}
	public void setCollect(BiCollectService collect) {
		this.collect = collect;
	}
	public void setPrice(BiPriceService price) {
		this.price = price;
	}
	public void setRule(BiRuleService rule) {
		this.rule = rule;
	}
}
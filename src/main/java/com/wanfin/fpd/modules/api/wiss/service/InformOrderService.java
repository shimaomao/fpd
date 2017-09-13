/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.api.wiss.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.service.CrudService;
import com.wanfin.fpd.modules.api.wiss.entity.InformOrder;
import com.wanfin.fpd.modules.api.wiss.dao.InformOrderDao;

/**
 * 通知获取订单信息Service
 * @author srf
 * @version 2017-06-30
 */
@Service("informOrderService")
@Transactional(readOnly = true)
public class InformOrderService extends CrudService<InformOrderDao, InformOrder> {

	public InformOrder get(String id) {
		return super.get(id);
	}
	
	public InformOrder getByCondition(InformOrder informOrder){
		return dao.getByCondition(informOrder);
	}
	
	public List<InformOrder> findList(InformOrder informOrder) {
		return super.findList(informOrder);
	}
	
	public Page<InformOrder> findPage(Page<InformOrder> page, InformOrder informOrder) {
		return super.findPage(page, informOrder);
	}
	
	@Transactional(readOnly = false)
	public void save(InformOrder informOrder) {
		super.save(informOrder);
	}
	
	@Transactional(readOnly = false)
	public void delete(InformOrder informOrder) {
		super.delete(informOrder);
	}
	
}
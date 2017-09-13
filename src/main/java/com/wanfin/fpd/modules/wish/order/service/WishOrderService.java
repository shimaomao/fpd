/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.wish.order.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.service.CrudService;
import com.wanfin.fpd.modules.wish.order.entity.WishOrder;
import com.wanfin.fpd.modules.wish.order.dao.WishOrderDao;

/**
 * 原始订单数据Service
 * @author cjp
 * @version 2017-06-27
 */
@Service
@Transactional(readOnly = true)
public class WishOrderService extends CrudService<WishOrderDao, WishOrder> {

	public WishOrder get(String id) {
		return super.get(id);
	}
	
	public List<WishOrder> findList(WishOrder wishOrder) {
		return super.findList(wishOrder);
	}
	
	public Page<WishOrder> findPage(Page<WishOrder> page, WishOrder wishOrder) {
		return super.findPage(page, wishOrder);
	}
	
	@Transactional(readOnly = false)
	public void save(WishOrder wishOrder) {
		super.save(wishOrder);
	}
	
	@Transactional(readOnly = false)
	public void updateOrInsert(WishOrder wishOrder){
		wishOrder.preUpdate();
		int numb = dao.updateOldDate(wishOrder);
		if(numb < 1){
			wishOrder.preInsert();
			dao.insert(wishOrder);
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(WishOrder wishOrder) {
		super.delete(wishOrder);
	}

	public Double getSumAmount(WishOrder wishOrder) {
		return dao.getSumAmount(wishOrder);
	}

	public WishOrder getByOrderId(String merchantOrderId) {
		return dao.getByOrderId(merchantOrderId);
	}

//	@Transactional(readOnly = false)
//	public void delOldDatas(WishOrder wishOrder) {
//		dao.delOldDatas(wishOrder);
//	}

	public void unlock(WishOrder wishOrder) {
		dao.unlock(wishOrder);
	}
	
}
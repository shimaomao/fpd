/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.wish.order.service;

import java.util.List;

import com.wanfin.fpd.modules.wish.order.dao.WishOverdueDao;
import com.wanfin.fpd.modules.wish.order.entity.WishOverdue;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.service.CrudService;


/**
 * 逾期金额Service
 * @author cjp
 * @version 2017-08-22
 */
@Service
@Transactional(readOnly = true)
public class WishOverdueService extends CrudService<WishOverdueDao, WishOverdue> {

	public WishOverdue get(String id) {
		return super.get(id);
	}
	
	public List<WishOverdue> findList(WishOverdue wishOverdue) {
		return super.findList(wishOverdue);
	}
	
	public Page<WishOverdue> findPage(Page<WishOverdue> page, WishOverdue wishOverdue) {
		return super.findPage(page, wishOverdue);
	}
	
	@Transactional(readOnly = false)
	public void save(WishOverdue wishOverdue) {
		super.save(wishOverdue);
	}
	
	@Transactional(readOnly = false)
	public void delete(WishOverdue wishOverdue) {
		super.delete(wishOverdue);
	}
	
}
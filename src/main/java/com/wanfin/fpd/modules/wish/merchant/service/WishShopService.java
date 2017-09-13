/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.wish.merchant.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.service.CrudService;
import com.wanfin.fpd.modules.wish.merchant.entity.WishShop;
import com.wanfin.fpd.modules.wish.merchant.dao.WishShopDao;

/**
 * 商户店铺信息Service
 * @author cjp
 * @version 2017-07-11
 */
@Service
@Transactional(readOnly = true)
public class WishShopService extends CrudService<WishShopDao, WishShop> {

	public WishShop get(String id) {
		return super.get(id);
	}
	
	public List<WishShop> findList(WishShop wishShop) {
		return super.findList(wishShop);
	}
	
	public Page<WishShop> findPage(Page<WishShop> page, WishShop wishShop) {
		return super.findPage(page, wishShop);
	}
	
	@Transactional(readOnly = false)
	public void save(WishShop wishShop) {
		super.save(wishShop);
	}
	
	@Transactional(readOnly = false)
	public void delete(WishShop wishShop) {
		super.delete(wishShop);
	}
	
}
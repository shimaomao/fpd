/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.bank.service.account;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.service.CrudService;
import com.wanfin.fpd.modules.bank.entity.account.TAccount;
import com.wanfin.fpd.modules.bank.dao.account.TAccountDao;

/**
 * 银行账户Service
 * @author chenh
 * @version 2016-03-29
 */
@Service
@Transactional(readOnly = true)
public class TAccountService extends CrudService<TAccountDao, TAccount> {

	public TAccount get(String id) {
		return super.get(id);
	}
	
	public List<TAccount> findList(TAccount tAccount) {
		return super.findList(tAccount);
	}
	
	public Page<TAccount> findPage(Page<TAccount> page, TAccount tAccount) {
		return super.findPage(page, tAccount);
	}
	
	@Transactional(readOnly = false)
	public void save(TAccount tAccount) {
		super.save(tAccount);
	}
	
	@Transactional(readOnly = false)
	public void delete(TAccount tAccount) {
		super.delete(tAccount);
	}
	
}
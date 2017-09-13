/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.account.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.service.CrudService;
import com.wanfin.fpd.modules.account.entity.BsAccount;
import com.wanfin.fpd.modules.account.dao.BsAccountDao;

/**
 * 资金账户信息Service
 * @author srf
 * @version 2017-01-03
 */
@Service("bsAccountService")
@Transactional(readOnly = true)
public class BsAccountService extends CrudService<BsAccountDao, BsAccount> {

	public BsAccount get(String id) {
		return super.get(id);
	}
	
	public BsAccount getByAuthUser(String authUserId) {
		return dao.getByAuthUser(authUserId);
	}
	
	public BsAccount getByAuthUser(BsAccount bsAccount) {
		return dao.getByAuthUser(bsAccount);
	}
	
	public List<BsAccount> findList(BsAccount bsAccount) {
		return super.findList(bsAccount);
	}
	
	public Page<BsAccount> findPage(Page<BsAccount> page, BsAccount bsAccount) {
		return super.findPage(page, bsAccount);
	}
	
	@Transactional(readOnly = false)
	public void save(BsAccount bsAccount) {
		super.save(bsAccount);
	}
	
	@Transactional(readOnly = false)
	public void delete(BsAccount bsAccount) {
		super.delete(bsAccount);
	}
    /**
     * //通过机构ID获取资金账户：机构ID--->管理员ID--->认证ID--->资金账户
     * @param id
     * @return
     */
	public BsAccount getbyOrganId(String organId) {
		return dao.getbyOrganId(organId);
	}

	public void update(BsAccount sellAccount) {
		dao.update(sellAccount);
	}
	
}
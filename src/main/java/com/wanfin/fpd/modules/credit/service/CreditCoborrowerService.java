/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.credit.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.service.CrudService;
import com.wanfin.fpd.modules.credit.entity.CreditCoborrower;
import com.wanfin.fpd.modules.credit.dao.CreditCoborrowerDao;

/**
 * 客户授信_共同借款人Service
 * @author srf
 * @version 2017-03-29
 */
@Service
@Transactional(readOnly = true)
public class CreditCoborrowerService extends CrudService<CreditCoborrowerDao, CreditCoborrower> {

	public CreditCoborrower get(String id) {
		return super.get(id);
	}
	
	public List<CreditCoborrower> findList(CreditCoborrower creditCoborrower) {
		return super.findList(creditCoborrower);
	}
	
	public Page<CreditCoborrower> findPage(Page<CreditCoborrower> page, CreditCoborrower creditCoborrower) {
		return super.findPage(page, creditCoborrower);
	}
	
	@Transactional(readOnly = false)
	public void save(CreditCoborrower creditCoborrower) {
		super.save(creditCoborrower);
	}
	
	@Transactional(readOnly = false)
	public void delete(CreditCoborrower creditCoborrower) {
		super.delete(creditCoborrower);
	}

	public List<CreditCoborrower> findAllList(CreditCoborrower creditCoborrower) {
		if(creditCoborrower == null || StringUtils.isBlank(creditCoborrower.getCreditApplyId())){
			return null;
		}
		return super.findAllList(creditCoborrower);
	}
	/**
	 * 授信ID和客户ID必须存在
	 * @param creditCoborrower
	 * @return
	 */
	public boolean checkCreditCoborrower(CreditCoborrower creditCoborrower) {
		//CreditCoborrower tmp = dao.checkCreditCoborrower(creditCoborrower);
		CreditCoborrower tmp = super.get(creditCoborrower.getId());
		if(tmp != null){
			return true;
		}
		return false;
	}

	/**
	 * 
	 * @param oldId 更新前id
	 * @param newId 更新后id
	 */
	@Transactional(readOnly = false)
	public void updateCoborrowerId(String oldCreditApplyIdId, String newCreditApplyIdId) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("newCreditApplyIdId", newCreditApplyIdId);
		map.put("oldCreditApplyIdId", oldCreditApplyIdId);
		dao.updateCoborrowerId(map);
	}
	
}
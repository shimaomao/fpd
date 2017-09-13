/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.contract.service.tpl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.service.CrudService;
import com.wanfin.fpd.modules.contract.entity.tpl.TContractTpl;
import com.wanfin.fpd.modules.contract.dao.tpl.TContractTplDao;

/**
 * 合同模板Service
 * @author chenh
 * @version 2016-03-30
 */
@Service
@Transactional(readOnly = true)
public class TContractTplService extends CrudService<TContractTplDao, TContractTpl> {

	public TContractTpl get(String id) {
		return super.get(id);
	}
	
	public List<TContractTpl> findList(TContractTpl tContractTpl) {
		return super.findList(tContractTpl);
	}
	
	public Page<TContractTpl> findPage(Page<TContractTpl> page, TContractTpl tContractTpl) {
		return super.findPage(page, tContractTpl);
	}
	
	@Transactional(readOnly = false)
	public void save(TContractTpl tContractTpl) {
		super.save(tContractTpl);
	}
	
	@Transactional(readOnly = false)
	public void delete(TContractTpl tContractTpl) {
		super.delete(tContractTpl);
	}
	
}
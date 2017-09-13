/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.lettertpl.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.service.CrudService;
import com.wanfin.fpd.modules.lettertpl.entity.LetterTpl;
import com.wanfin.fpd.modules.lettertpl.dao.LetterTplDao;

/**
 * 函件模板Service
 * @author zzm
 * @version 2016-06-08
 */
@Service
@Transactional(readOnly = true)
public class LetterTplService extends CrudService<LetterTplDao, LetterTpl> {

	public LetterTpl get(String id) {
		return super.get(id);
	}
	
	public List<LetterTpl> findList(LetterTpl letterTpl) {
		return super.findList(letterTpl);
	}
	
	public Page<LetterTpl> findPage(Page<LetterTpl> page, LetterTpl letterTpl) {
		return super.findPage(page, letterTpl);
	}
	
	@Transactional(readOnly = false)
	public void save(LetterTpl letterTpl) {
		super.save(letterTpl);
	}
	
	@Transactional(readOnly = false)
	public void delete(LetterTpl letterTpl) {
		super.delete(letterTpl);
	}
	
}
/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.billing.service.element;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.service.CrudService;
import com.wanfin.fpd.common.utils.SerialUtils;
import com.wanfin.fpd.common.utils.StringUtils;
import com.wanfin.fpd.core.billing.BiSval;
import com.wanfin.fpd.modules.billing.dao.element.BiElementDao;
import com.wanfin.fpd.modules.billing.entity.element.BiElement;
import com.wanfin.fpd.modules.sys.entity.Dict;
import com.wanfin.fpd.modules.sys.service.DictService;
import com.wanfin.fpd.modules.sys.utils.UserUtils;

/**
 * 收费项Service
 * @author chenh
 * @version 2016-07-01
 */
@Service
@Transactional(readOnly = true)
public class BiElementService extends CrudService<BiElementDao, BiElement> {
	@Autowired
	private DictService dictService;
	
	public BiElement get(String id) {
		return super.get(id);
	}
	
	public List<BiElement> findList(BiElement biElement) {
		return super.findList(biElement);
	}
	
	public Page<BiElement> findPage(Page<BiElement> page, BiElement biElement) {
		return super.findPage(page, biElement);
	}
	
	@Transactional(readOnly = false)
	public void save(BiElement biElement) {
		Boolean isNew = biElement.getIsNewRecord();
		if(StringUtils.isEmpty(biElement.getOrganId())){
			biElement.setOrganId(UserUtils.getUser().getCompany().getId());
		}
		if(StringUtils.isEmpty(biElement.getOrganId())){
			biElement.setOrganId(UserUtils.getUser().getCompany().getId());
		}
		if((biElement.getElement() == null) || StringUtils.isEmpty(biElement.getElement().getId())){
			biElement.setElement(new BiElement(BiElement.getRootId()));
		}
		if(isNew){
			super.save(biElement);
			biElement.setTypeId(SerialUtils.getOrderNo());
			Dict dict = initDict(biElement);
			dictService.save(dict);
			super.save(biElement);
		}else{
			Dict dict = new Dict();
			dict.setType(BiSval.BiDicKey.BILING_ELEMENT_KEY);
			dict.setValue(biElement.getTypeId());
			dict = dictService.getByCode(dict);
			if(dict == null){
				dict = initDict(biElement);
			}
			dict.setLabel(biElement.getName());
			dictService.save(dict);
			super.save(biElement);
		}
	}

	private Dict initDict(BiElement biElement) {
		Dict dict = new Dict();
		dict.setValue(biElement.getTypeId());
		dict.setType(BiSval.BiDicKey.BILING_ELEMENT_KEY);
		dict.setLabel(biElement.getName());
		dict.setSort(100);
		dict.setRemarks(biElement.getRemarks());
		dict.setDescription(biElement.getRemarks());
		return dict;
	}
	
	@Transactional(readOnly = false)
	public void delete(BiElement biElement) {
		Dict dict = new Dict();
		dict.setType(BiSval.BiDicKey.BILING_ELEMENT_KEY);
		dict.setValue(biElement.getTypeId());
		dict = dictService.getByCode(dict);
		dict.setLabel(biElement.getName());
		dictService.delete(dict);
		super.delete(biElement);
	}
	
}
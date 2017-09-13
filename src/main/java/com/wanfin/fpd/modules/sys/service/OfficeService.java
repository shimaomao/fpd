/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.sys.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wanfin.fpd.common.config.Cons;
import com.wanfin.fpd.common.service.BaseService;
import com.wanfin.fpd.common.service.TreeService;
import com.wanfin.fpd.common.utils.SerialUtils;
import com.wanfin.fpd.common.utils.StringUtils;
import com.wanfin.fpd.modules.sys.dao.OfficeDao;
import com.wanfin.fpd.modules.sys.entity.Office;
import com.wanfin.fpd.modules.sys.entity.User;
import com.wanfin.fpd.modules.sys.utils.UserUtils;

/**
 * 机构Service
 * @author ThinkGem
 * @version 2014-05-16
 */
@Service
@Transactional(readOnly = true)
public class OfficeService extends TreeService<OfficeDao, Office> {
	
    @Autowired
	private OfficeDao officeDao;
    
	public List<Office> findAll(){
		return UserUtils.getOfficeList();
	} 
 
	public List<Office> findList(Boolean isAll){
		if (isAll != null && isAll){
			return UserUtils.getOfficeAllList();
		}else{
			return UserUtils.getOfficeList();
		}
	}
	
	@Transactional(readOnly = true)
	public List<Office> getParentZHB(Office office){
		return dao.getParentZHB(office);
	}
	
	@Transactional(readOnly = true)
	public List<Office> findByParentIdsLike(Office office){
		return dao.findByParentIdsLike(office);
	}
	
	@Transactional(readOnly = true)
	public List<Office> findList(Office office){
		if(office != null){
			return dao.findByParentIdsLike(office);
		}
		return  new ArrayList<Office>();
	}
	
	@Transactional(readOnly = false)
	public void save(Office office) {
		if (StringUtils.isEmpty(office.getUniqueNumber())) {
			office.setUniqueNumber(SerialUtils.getOrderNo("ORG"));
		}
		if (StringUtils.isEmpty(office.getCode())) {
			office.setCode(office.getUniqueNumber());
		}
		if (StringUtils.isEmpty(office.getContractNumber())) {
			office.setContractNumber(office.getName()+"year年第number号");
		}
		
		if((office.getParent() != null)){
			if (StringUtils.isEmpty(office.getPrimaryPerson())) {
				if(!(office.getParent().getId()).equals(Cons.SysDF.DF_SUPER_COMPANY)){
					office.setPrimaryPerson(office.getParent().getPrimaryPerson());
				}
			}
			Office parent = office.getParent();
			if(StringUtils.isEmpty(parent.getGrade())){
				parent = super.get(parent);
			}
			if(parent != null){
				office.setGrade(String.valueOf(Integer.valueOf(parent.getGrade())+1));
			}else{
				if (StringUtils.isEmpty(office.getGrade())) {
					office.setGrade(Cons.SysDF.DF_SUPER_OFFICE_GRADEORG);
				}
			}
		}else{
			office.setGrade(Cons.SysDF.DF_SUPER_OFFICE_GRADEORG);
		}
		
		super.save(office);
		UserUtils.removeCache(UserUtils.CACHE_OFFICE_LIST);
	}
	
	@Transactional(readOnly = false)
	public void delete(Office office) {
		super.delete(office);
		UserUtils.removeCache(UserUtils.CACHE_OFFICE_LIST);
	}

	@Transactional(readOnly = false)
	public Office getBywtypeId(String wtypeId) {
		return dao.getBywtypeId(wtypeId);
	}
	
	
	public Office getOffiParam(String name) {
		return dao.getFindParam(name);
	}

	public List<Office> findOfficeByCompanyId(String id) {
		Office of=new Office();
		of.setId(id);
		return dao.findByParentIdsLike(of);
	}

}

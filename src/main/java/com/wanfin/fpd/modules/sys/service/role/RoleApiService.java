/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.sys.service.role;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.service.CrudService;
import com.wanfin.fpd.modules.sys.dao.RoleDao;
import com.wanfin.fpd.modules.sys.entity.Role;

/**
 * 角色Service
 * @author Chenh
 * @version 2016-06-16
 */
@Service
@Transactional(readOnly = true)
public class RoleApiService extends CrudService<RoleDao, Role> {

	public Role get(String id) {
		return super.get(id);
	}
	
	public List<Role> findList(Role role) {
		return super.findList(role);
	}
	
	public Page<Role> findPage(Page<Role> page, Role role) {
		return super.findPage(page, role);
	}
	
	@Transactional(readOnly = false)
	public void save(Role role) {
		super.save(role);
	}
	
	@Transactional(readOnly = false)
	public void delete(Role role) {
		super.delete(role);
	}
	
}
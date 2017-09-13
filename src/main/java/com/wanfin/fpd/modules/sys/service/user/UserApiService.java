/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.sys.service.user;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.service.CrudService;
import com.wanfin.fpd.modules.sys.dao.UserDao;
import com.wanfin.fpd.modules.sys.entity.User;

/**
 * 用户Service
 * @author Chenh
 * @version 2016-06-16
 */
@Service
@Transactional(readOnly = true)
public class UserApiService extends CrudService<UserDao, User> {

	public User get(String id) {
		return super.get(id);
	}
	
	public User getBywtypeId(String wtypeId) {
		return dao.getBywtypeId(wtypeId);
	}
	
	public List<User> findList(User user) {
		return super.findList(user);
	}
	
	public Page<User> findPage(Page<User> page, User user) {
		return super.findPage(page, user);
	}
	
	@Transactional(readOnly = false)
	public void save(User user) {
		super.save(user);
	}
	
	@Transactional(readOnly = false)
	public void delete(User user) {
		super.delete(user);
	}

	@Transactional(readOnly = false)
	public int insertUserRole(User user){
		return super.dao.insertUserRole(user);
	}

	public User getByLoginName(String loginName) {
		return dao.getByLoginName(new User(null, loginName));
	}
	
}
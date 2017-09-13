/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.wanfin.fpd.common.persistence.CrudDao;
import com.wanfin.fpd.common.persistence.annotation.MyBatisDao;
import com.wanfin.fpd.modules.sys.entity.User;

/**
 * 用户DAO接口
 * @author ThinkGem
 * @version 2014-05-16
 */
@MyBatisDao
public interface UserDao extends CrudDao<User> {
	
	/**
	 * 根据登录名称查询用户
	 * @param loginName
	 * @return
	 */
	public User getByLoginName(User user);

	/**
	 * 通过OfficeId获取用户列表，仅返回用户id和name（树查询用户时用）
	 * @param user
	 * @return
	 */
	public List<User> findUserByOfficeId(User user);
	
	/**
	 * 查询全部用户数目
	 * @return
	 */
	public long findAllCount(User user);
	
	/**
	 * 更新用户密码
	 * @param user
	 * @return
	 */
	public int updatePasswordById(User user);
	
	/**
	 * 更新登录信息，如：登录IP、登录时间
	 * @param user
	 * @return
	 */
	public int updateLoginInfo(User user);

	/**
	 * 删除用户角色关联数据
	 * @param user
	 * @return
	 */
	public int deleteUserRole(User user);
	
	/**
	 * 插入用户角色关联数据
	 * @param user
	 * @return
	 */
	public int insertUserRole(User user);
	
	/**
	 * 更新用户信息
	 * @param user
	 * @return
	 */
	public int updateUserInfo(User user);

	/**
	 * 根据wtypeId查询用户
	 * @param user
	 * @return
	 */
	public User getBywtypeId(String wtypeId);
	/**
	 * 根据公司查询用户
	 * @param user
	 * @return
	 */
	public List findUserByCompanyId(User user);
	/**
	 * 根据机构ID查zadmin
	 * @param roleId
	 * @param companyId
	 * @return
	 */
	public User getLoginNamebyOrganId(@Param("roleId") String roleId, @Param("companyId") String companyId, @Param("delFlag") String delFlag);
	/**
	 * 根据机构ID查小贷或担保租户管理员
	 * @param roleId
	 * @param companyId
	 * @return
	 */
	public User getAdmin(@Param("companyId")String companyId,@Param("dfAdminRoleXd") String dfAdminRoleXd,
			@Param("dfAdminRoleDb")String dfAdminRoleDb);

	/**
	 * 通过姓名获取用户信息
	 * @param name
	 * @return
	 */
	public User getByName(@Param("name") String name);
}

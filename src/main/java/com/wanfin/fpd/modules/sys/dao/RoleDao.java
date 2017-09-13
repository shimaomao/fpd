/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.wanfin.fpd.common.persistence.CrudDao;
import com.wanfin.fpd.common.persistence.annotation.MyBatisDao;
import com.wanfin.fpd.modules.sys.entity.Role;
import com.wanfin.fpd.modules.sys.entity.User;

/**
 * 角色DAO接口
 * @author ThinkGem
 * @version 2013-12-05
 */
@MyBatisDao
public interface RoleDao extends CrudDao<Role> {

	/**
	 * 根据ID获取角色
	 * @param role 角色
	 * @return
	 */
	public Role findById(Role role);
	
	/**
	 * 根据Name获取角色列表
	 * @param role 角色
	 * @return
	 */
	public List<Role> getByName(Role role);
	/**
	 * 根据Name获取角色列表
	 * @param role 角色
	 * @param organId 租户ID
	 * @return
	 */
	public List<Role> getByNameAndOrganId(Role role);
	
	/**
	 * 获取Admin同等级的角色列表
	 * @param role
	 * @return
	 * @descript 根据要求进行处理add by shirf 20160914
	 */
	public List<Role> findOnlyAdminList(Role role);
	
	/**
	 * 根据Enname获取角色列表
	 * @param role 角色
	 * @return
	 */
	public List<Role> getByEnname(Role role);
	/**
	 * 根据Enname获取角色列表
	 * @param role 角色
	 * @param organId 租户ID
	 * @return
	 */
	public List<Role> getByEnnameAndOrganId(Role role);

	/**
	 * 维护角色与菜单权限关系
	 * @param role
	 * @return
	 */
	public int deleteRoleMenu(Role role);

	public int insertRoleMenu(Role role);
	
	/**
	 * 维护角色与公司部门关系
	 * @param role
	 * @return
	 */
	public int deleteRoleOffice(Role role);

	public int insertRoleOffice(Role role);
	 /** 
	  * 根据角色英文名查找属于当前角色的登录名---------流程审核跟着角色走
	  * @param roleName  下个流程节点角色英文名
	  * @author lzj
	  * */
	public List<String> getRolelist(@Param("userId")String userId,@Param("enname")String enname,@Param("type")String type);
	
	/**
	 * 初始化角色菜单
	 * @param dataList
	 * @return
	 */
	public int insertInitRoleMenu(@Param("roleId")String roleId,@Param("dataList")List<String> dataList);
	
	/**
	 * 根据用户ID查Role
	 * @param user
	 * @return
	 */
	public List<Role> getRoleByUser(@Param("userId")String userId);
}

/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.sys.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.springframework.http.MediaType;
import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.Group;
import org.apache.shiro.session.Session;
import org.json.JSONException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wanfin.fpd.common.config.Cons;
import com.wanfin.fpd.common.config.Global;
import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.security.Digests;
import com.wanfin.fpd.common.security.shiro.session.SessionDAO;
import com.wanfin.fpd.common.service.BaseService;
import com.wanfin.fpd.common.service.ServiceException;
import com.wanfin.fpd.common.utils.AccountVo;
import com.wanfin.fpd.common.utils.CacheUtils;
import com.wanfin.fpd.common.utils.Encodes;
import com.wanfin.fpd.common.utils.StringUtils;
import com.wanfin.fpd.common.web.Servlets;
import com.wanfin.fpd.modules.files.entity.FileBytesVo;
import com.wanfin.fpd.modules.files.entity.TContractFiles;
import com.wanfin.fpd.modules.product.entity.TProduct;
import com.wanfin.fpd.modules.productbiz.dao.TProductBizDao;
import com.wanfin.fpd.modules.sys.dao.AuthenticatorDao;
import com.wanfin.fpd.modules.sys.dao.AuthenticatorRelDao;
import com.wanfin.fpd.modules.sys.dao.MenuDao;
import com.wanfin.fpd.modules.sys.dao.RoleDao;
import com.wanfin.fpd.modules.sys.dao.UserDao;
import com.wanfin.fpd.modules.sys.entity.Menu;
import com.wanfin.fpd.modules.sys.entity.Office;
import com.wanfin.fpd.modules.sys.entity.Role;
import com.wanfin.fpd.modules.sys.entity.User;
import com.wanfin.fpd.modules.sys.security.SystemAuthorizingRealm;
import com.wanfin.fpd.modules.sys.utils.LogUtils;
import com.wanfin.fpd.modules.sys.utils.UserUtils;

import org.springframework.http.HttpHeaders;
/**
 * 系统管理，安全相关实体的管理类,包括用户、角色、菜单.
 * @author ThinkGem
 * @version 2013-12-05
 */
@Service
@Transactional(readOnly = true)
public class SystemService extends BaseService implements InitializingBean {
	
	public static final String HASH_ALGORITHM = "SHA-1";
	public static final int HASH_INTERATIONS = 1024;
	public static final int SALT_SIZE = 8;
	
	@Autowired
	private UserDao userDao;
	@Autowired
	private RoleDao roleDao;
	@Autowired
	private MenuDao menuDao;
	@Autowired
	private SessionDAO sessionDao;
	@Autowired
	private TProductBizDao tproductBizDao;
	@Autowired
	private AuthenticatorDao authenticatorDao;
	@Autowired
	private AuthenticatorRelDao authenticatorRelDao;
	
	
	@Autowired
	private SystemAuthorizingRealm systemRealm;
	
	public SessionDAO getSessionDao() {
		return sessionDao;
	}

	@Autowired
	private IdentityService identityService;

	//-- User Service --//
	
	
	
	/**
	 * 获取用户
	 * @param id
	 * @return
	 */
	public User getUser(String id) {
		return UserUtils.get(id);
	}
	
	/**
	 * 根据机构ID取登陆名
	 * @param companyId
	 * @return
	 */
	public User getZadmin(String roleId,String companyId) {
		return getZadmin(roleId, companyId, Cons.NO);
	}
	public User getZadmin(String roleId,String companyId, String delFlag) {
		return userDao.getLoginNamebyOrganId(roleId, companyId, delFlag);
	}
	
	/**
	 * 根据登录名获取用户
	 * @param loginName
	 * @return
	 */
	public User getUserByLoginName(String loginName) {
		return UserUtils.getByLoginName(loginName);
	}
	
	public Page<User> findUser(Page<User> page, User user) {
		// 生成数据权限过滤条件（dsf为dataScopeFilter的简写，在xml中使用 ${sqlMap.dsf}调用权限SQL）
		user.getSqlMap().put("dsf", dataScopeFilter(user.getCurrentUser(), "o", "a"));
		// 设置分页参数
		user.setPage(page);
		// 执行分页查询
		page.setList(userDao.findList(user));
		return page;
	}
	
	/**
	 * 无分页查询人员列表
	 * @param user
	 * @return
	 */
	public List<User> findUser(User user){
		// 生成数据权限过滤条件（dsf为dataScopeFilter的简写，在xml中使用 ${sqlMap.dsf}调用权限SQL）
		user.getSqlMap().put("dsf", dataScopeFilter(user.getCurrentUser(), "o", "a"));
		List<User> list = userDao.findList(user);
		return list;
	}

	/**
	 * 通过部门ID获取用户列表，仅返回用户id和name（树查询用户时用）
	 * @param user
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<User> findUserByOfficeId(String officeId) {
		List<User> list = (List<User>)CacheUtils.get(UserUtils.USER_CACHE, UserUtils.USER_CACHE_LIST_BY_OFFICE_ID_ + officeId);
		if (list == null || list.size()<1){
			User user = new User();
			user.setOffice(new Office(officeId));
			list = userDao.findUserByOfficeId(user);
			CacheUtils.put(UserUtils.USER_CACHE, UserUtils.USER_CACHE_LIST_BY_OFFICE_ID_ + officeId, list);
		}
		return list;
	}
	
	@Transactional(readOnly = false)
	public void saveUser(User user) {
		if (StringUtils.isBlank(user.getId())){
			user.preInsert();
			userDao.insert(user);
			/**
			 * 新增--同步信息到认证表
			 */
			/*AuthenticationUser authUser=new AuthenticationUser();
			authUser.preInsert();
			authUser.setUsername(user.getLoginName());
			authUser.setPassword(user.getPassword());
			authUser.setMobile(user.getPhone());
			authUser.setName(user.getName());
			authUser.setEmail(user.getEmail());
			authenticatorDao.insert(authUser);
			AuthenticationUserRel authRel=new AuthenticationUserRel();
			authRel.preInsert();
			authRel.setAnuthenuserId(authUser.getId());
			authRel.setBuserId(user.getId());
			authenticatorRelDao.insert(authRel);*/
			
		}else{
			// 清除原用户机构用户缓存
			User oldUser = userDao.get(user.getId());
			if (oldUser.getOffice() != null && oldUser.getOffice().getId() != null){
				CacheUtils.remove(UserUtils.USER_CACHE, UserUtils.USER_CACHE_LIST_BY_OFFICE_ID_ + oldUser.getOffice().getId());
			}
			// 更新用户数据
			user.preUpdate();
			userDao.update(user);
			/**
			 * 更新--同步信息到认证表
			 */
			/*AuthenticationUserRel authRel=authenticatorRelDao.getBid(user.getId());
			AuthenticationUser authUser=authenticatorDao.get(authRel.getAnuthenuserId());
			if(authUser!=null){
				authUser.setUsername(user.getLoginName());
				authUser.setPassword(user.getPassword());
				authUser.setMobile(user.getMobile());
				authUser.setName(user.getName());
				authUser.setEmail(user.getEmail());
				authenticatorDao.update(authUser);
			}*/
			
		}
		if (StringUtils.isNotBlank(user.getId())){
			// 更新用户与角色关联
			userDao.deleteUserRole(user);
			if (user.getRoleList() != null && user.getRoleList().size() > 0){
				userDao.insertUserRole(user);
			}else{
				throw new ServiceException(user.getLoginName() + "没有设置角色！");
			}
			// 将当前用户同步到Activiti
			saveActivitiUser(user);
			// 清除用户缓存
			UserUtils.clearCache(user);
//			// 清除权限缓存
//			systemRealm.clearAllCachedAuthorizationInfo();
		}
	}
	
	@Transactional(readOnly = false)
	public void updateUserInfo(User user) {
		user.preUpdate();
		userDao.updateUserInfo(user);
		// 清除用户缓存
		UserUtils.clearCache(user);
//		// 清除权限缓存
//		systemRealm.clearAllCachedAuthorizationInfo();
	}
	
	@Transactional(readOnly = false)
	public void deleteUser(User user) {
		userDao.delete(user);
		// 同步到Activiti
		deleteActivitiUser(user);
		// 清除用户缓存
		UserUtils.clearCache(user);
		//删除后将用户踢出系统
		UserUtils.kickOutUser(user.getLoginName());
//		// 清除权限缓存
//		systemRealm.clearAllCachedAuthorizationInfo();
	}
	
	@Transactional(readOnly = false)
	public void updatePasswordById(String id, String loginName, String newPassword) {
		User user = new User(id);
		user.setPassword(entryptPassword(newPassword));
		userDao.updatePasswordById(user);
		// 清除用户缓存
		user.setLoginName(loginName);
		UserUtils.clearCache(user);
//		// 清除权限缓存
//		systemRealm.clearAllCachedAuthorizationInfo();
	}
	
	@Transactional(readOnly = false)
	public void updateUserLoginInfo(User user) {
		// 保存上次登录信息
		user.setOldLoginIp(user.getLoginIp());
		user.setOldLoginDate(user.getLoginDate());
		// 更新本次登录信息
		user.setLoginIp(StringUtils.getRemoteAddr(Servlets.getRequest()));
		user.setLoginDate(new Date());
		userDao.updateLoginInfo(user);
	}
	
	/**
	 * 生成安全的密码，生成随机的16位salt并经过1024次 sha-1 hash
	 */
	public static String entryptPassword(String plainPassword) {
		String plain = Encodes.unescapeHtml(plainPassword);
		byte[] salt = Digests.generateSalt(SALT_SIZE);
		byte[] hashPassword = Digests.sha1(plain.getBytes(), salt, HASH_INTERATIONS);
		return Encodes.encodeHex(salt)+Encodes.encodeHex(hashPassword);
	}
	
	public static void main(String[] args) {
		String plainPassword = "123456";
		System.out.println("entryptPassword : " + entryptPassword(plainPassword));
		String plain = Encodes.unescapeHtml(plainPassword);
		byte[] salt = Digests.generateSalt(SALT_SIZE);
		byte[] hashPassword = Digests.sha1(plain.getBytes(), salt, HASH_INTERATIONS);
		System.out.println("plain:"+plain);
		System.out.println("salt:"+salt);
		System.out.println("hashPassword:"+hashPassword);
		System.out.println("hashSalt:"+Encodes.encodeHex(salt));
		System.out.println("hashPassword:"+Encodes.encodeHex(hashPassword));
		System.out.println("hashSaltPassword:"+Encodes.encodeHex(salt)+Encodes.encodeHex(hashPassword));
		System.out.println("hashPasswordDe:"+Encodes.decodeHex(Encodes.encodeHex(salt)+Encodes.encodeHex(hashPassword)));
				
	}
	
	/**
	 * 验证密码
	 * @param plainPassword 明文密码
	 * @param password 密文密码
	 * @return 验证成功返回true
	 */
	public static boolean validatePassword(String plainPassword, String password) {
		String plain = Encodes.unescapeHtml(plainPassword);
		byte[] salt = Encodes.decodeHex(password.substring(0,16));
		byte[] hashPassword = Digests.sha1(plain.getBytes(), salt, HASH_INTERATIONS);
		return password.equals(Encodes.encodeHex(salt)+Encodes.encodeHex(hashPassword));
	}
	
	/**
	 * 获得活动会话
	 * @return
	 */
	public Collection<Session> getActiveSessions(){
		return sessionDao.getActiveSessions(false);
	}
	
	//-- Role Service --//
	
	public Role getRole(String id) {
		return roleDao.get(id);
	}

	public Role findById(String id) {
		return roleDao.findById(new Role(id));
	}

//	public boolean checkName(String name){
//		return checkEnname(null, name);
//	}
//	
//	public boolean checkName(List<Role> roles, String name){
//		if(roles == null){
//			Role r = new Role();
//			r.setName(name);
//			roles = roleDao.getByName(r);
//			if((roles != null) && (roles.size() == 1)){
//				return true;
//			}
//		}
//		return false;
//	}
	
	public List<Role> findRole(Role role){
		return roleDao.findList(role);
	}
	
	/**
	 * 根据角色名称获取列表
	 * @param enname
	 * @return
	 */
	public List<Role> getRoleByName(String name) {
		Role r = new Role();
		r.setName(name);
		return roleDao.getByName(r);
	}

	/**
	 * 根据角色名称是否有效
	 * @param enname
	 * @return
	 */
	public boolean checkName(String enname){
		return checkNameAndOrganId(null, enname);
	}
	/**
	 * 根据租户验证角色名称是否有效
	 * @param organId
	 * @param enname
	 * @return
	 */
	public boolean checkName(String organId, String enname){
		return checkNameAndOrganId(organId, enname);
	}
	/**
	 * 根据租户验证角色名称是否有效
	 * @param organId
	 * @param roles
	 * @param enname
	 * @return
	 */
	public boolean checkNameAndOrganId(String organId,String name){
		List<Role> roles = new ArrayList<Role>();
		Role r = new Role();
		r.setName(name);
		if(StringUtils.isNotEmpty(organId)){
			r.setOrganId(organId);
			roles = roleDao.getByNameAndOrganId(r);
		}
		if(roles.size() == 0){
			return true;
		}
		return false;
	}
	
	
	/**
	 * 根据角色英文名获取列表
	 * @param enname
	 * @return
	 */
	public List<Role> getRoleByEnname(String enname) {
		Role r = new Role();
		r.setEnname(enname);
		return roleDao.getByEnname(r);
	}

	/**
	 * 根据角色英文名是否有效
	 * @param roles
	 * @param enname
	 * @return
	 */
	public boolean checkEnname(String enname){
		return checkEnnameAndOrganId(null, enname);
	}
	/**
	 * 根据租户验证角色英文名是否有效
	 * @param organId
	 * @param enname
	 * @return
	 */
	public boolean checkEnname(String organId, String enname){
		return checkEnnameAndOrganId(organId, enname);
	}
	/**
	 * 根据租户验证角色英文名是否有效
	 * @param organId
	 * @param roles
	 * @param enname
	 * @return
	 */
	public boolean checkEnnameAndOrganId(String organId, String enname){
		List<Role> roles = new ArrayList<Role>();
		Role r = new Role();
		r.setEnname(enname);
		if(StringUtils.isNotEmpty(organId)){
			r.setOrganId(organId);
			roles = roleDao.getByEnnameAndOrganId(r);
		}
		if(roles.size() == 0){
			return true;
		}
		return false;
	}
	
	public List<Role> findAllRole(){
		return UserUtils.getRoleList();
	}
	public List<Role> findAdminLevelRole(){
		return UserUtils.getAdminLevelList();
	}
	
	@Transactional(readOnly = false)
	public void saveRole(Role role) {
		if (StringUtils.isBlank(role.getId())){
			role.preInsert();
			roleDao.insert(role);
			// 同步到Activiti
			saveActivitiGroup(role);
		}else{
			role.preUpdate();
			roleDao.update(role);
		}
		// 更新角色与菜单关联
		roleDao.deleteRoleMenu(role);
		if (role.getMenuList().size() > 0){
			roleDao.insertRoleMenu(role);
		}
		// 更新角色与部门关联
		roleDao.deleteRoleOffice(role);
		if (role.getOfficeList().size() > 0){
			roleDao.insertRoleOffice(role);
		}
		// 同步到Activiti
		saveActivitiGroup(role);
		// 清除用户角色缓存
		UserUtils.removeCache(UserUtils.CACHE_ROLE_LIST);
//		// 清除权限缓存
//		systemRealm.clearAllCachedAuthorizationInfo();
	}

	@Transactional(readOnly = false)
	public void deleteRole(Role role) {
		roleDao.delete(role);
		// 同步到Activiti
		deleteActivitiGroup(role);
		// 清除用户角色缓存
		UserUtils.removeCache(UserUtils.CACHE_ROLE_LIST);
//		// 清除权限缓存
//		systemRealm.clearAllCachedAuthorizationInfo();
	}
	
	@Transactional(readOnly = false)
	public Boolean outUserInRole(Role role, User user) {
		List<Role> roles = user.getRoleList();
		for (Role e : roles){
			if (e.getId().equals(role.getId())){
				roles.remove(e);
				saveUser(user);
				return true;
			}
		}
		return false;
	}
	
	@Transactional(readOnly = false)
	public User assignUserToRole(Role role, User user) {
		if (user == null){
			return null;
		}
		List<String> roleIds = user.getRoleIdList();
		if (roleIds.contains(role.getId())) {
			return null;
		}
		user.getRoleList().add(role);
		saveUser(user);
		return user;
	}

	//-- Menu Service --//
	
	public Menu getMenu(String id) {
		return menuDao.get(id);
	}

	public List<Menu> findAllMenu(){
		return UserUtils.getMenuList();
	}

	public List<Menu> getMenuListByProduct(TProduct tProduct){
		return UserUtils.getMenuListByProduct(tProduct);
	}
	
	
	//产品配置时专用  --=只查询业务中心的数据
	public List<Menu> findBusinessMenu(){
		return UserUtils.getBusinessMenuList();
	}
	
	@Transactional(readOnly = false)
	public void saveMenu(Menu menu) {
		
		// 获取父节点实体
		menu.setParent(this.getMenu(menu.getParent().getId()));
		
		// 获取修改前的parentIds，用于更新子节点的parentIds
		String oldParentIds = menu.getParentIds(); 
		
		// 设置新的父节点串
		menu.setParentIds(menu.getParent().getParentIds()+menu.getParent().getId()+",");

		// 保存或更新实体
		if (StringUtils.isBlank(menu.getId())){
			menu.preInsert();
			menuDao.insert(menu);
		}else{
			menu.preUpdate();
			menuDao.update(menu);
		}
		
		// 更新子节点 parentIds
		Menu m = new Menu();
		m.setParentIds("%,"+menu.getId()+",%");
		List<Menu> list = menuDao.findByParentIdsLike(m);
		for (Menu e : list){
			e.setParentIds(e.getParentIds().replace(oldParentIds, menu.getParentIds()));
			menuDao.updateParentIds(e);
		}
		// 清除用户菜单缓存
		UserUtils.removeCache(UserUtils.CACHE_MENU_LIST);
//		// 清除权限缓存
//		systemRealm.clearAllCachedAuthorizationInfo();
		// 清除日志相关缓存
		CacheUtils.remove(LogUtils.CACHE_MENU_NAME_PATH_MAP);
	}

	@Transactional(readOnly = false)
	public void updateMenuSort(Menu menu) {
		menuDao.updateSort(menu);
		// 清除用户菜单缓存
		UserUtils.removeCache(UserUtils.CACHE_MENU_LIST);
//		// 清除权限缓存
//		systemRealm.clearAllCachedAuthorizationInfo();
		// 清除日志相关缓存
		CacheUtils.remove(LogUtils.CACHE_MENU_NAME_PATH_MAP);
	}

	@Transactional(readOnly = false)
	public void deleteMenu(Menu menu) {
		menuDao.delete(menu);
		// 清除用户菜单缓存
		UserUtils.removeCache(UserUtils.CACHE_MENU_LIST);
//		// 清除权限缓存
//		systemRealm.clearAllCachedAuthorizationInfo();
		// 清除日志相关缓存
		CacheUtils.remove(LogUtils.CACHE_MENU_NAME_PATH_MAP);
	}
	
	@Transactional(readOnly = false)
	public void deletePLWL(List<Menu> menus) {
		menuDao.deletePLWL(menus);
	}

	@Transactional(readOnly = false)
	public void deletePLWLByIds(List<String> ids) {
		menuDao.deletePLWLByIds(ids);
	}
	
	/**
	 * @Description  删除全部产品菜单
	 * @param sysCode	产品id
	 * @return
	 * @author zzm
	 * @date 2016-5-23 下午5:43:46  
	 */
	@Transactional(readOnly = false)
	public void deleteMenusBySysCode(String sysCode) {
		Menu menu = new Menu();
		menu.setSysCode(sysCode);
		
		//20190922       lingxing         删除角色菜单表数据
		List<String> sysCodeMenuList =tproductBizDao.findAllByMenuSysCode(sysCode);//当产品重新部署菜单时，要把角色菜单表里的数据清理，后面再重新添加
		if(sysCodeMenuList.size()>0){
			menuDao.deletePLRoleMenuByIds(sysCodeMenuList);
		}
		// =====================================================
		
		
		menuDao.deleteBySysCode(menu);
	}
	
	/**
	 * 获取Key加载信息
	 */
	public static boolean printKeyLoadMessage(){
		StringBuilder sb = new StringBuilder();
		sb.append("\r\n======================================================================\r\n");
		sb.append("\r\n    欢迎使用 "+Global.getConfig("productName")+"  - Powered By http://jeesite.com\r\n");
		sb.append("\r\n======================================================================\r\n");
		System.out.println(sb.toString());
		return true;
	}
	
	///////////////// Synchronized to the Activiti //////////////////
	
	// 已废弃，同步见：ActGroupEntityServiceFactory.java、ActUserEntityServiceFactory.java

	/**
	 * 是需要同步Activiti数据，如果从未同步过，则同步数据。
	 */
	private static boolean isSynActivitiIndetity = true;
	public void afterPropertiesSet() throws Exception {
		if (!Global.isSynActivitiIndetity()){
			return;
		}
		if (isSynActivitiIndetity){
			isSynActivitiIndetity = false;
	        // 同步角色数据
			List<Group> groupList = identityService.createGroupQuery().list();
			if (groupList.size() == 0){
			 	Iterator<Role> roles = roleDao.findAllList(new Role()).iterator();
			 	while(roles.hasNext()) {
			 		Role role = roles.next();
			 		saveActivitiGroup(role);
			 	}
			}
		 	// 同步用户数据
			List<org.activiti.engine.identity.User> userList = identityService.createUserQuery().list();
			if (userList.size() == 0){
			 	Iterator<User> users = userDao.findAllList(new User()).iterator();
			 	while(users.hasNext()) {
			 		saveActivitiUser(users.next());
			 	}
			}
		}
	}
	
	private void saveActivitiGroup(Role role) {
		if (!Global.isSynActivitiIndetity()){
			return;
		}
		String groupId = role.getEnname();
		
		// 如果修改了英文名，则删除原Activiti角色
		if (StringUtils.isNotBlank(role.getOldEnname()) && !role.getOldEnname().equals(role.getEnname())){
			identityService.deleteGroup(role.getOldEnname());
		}
		
		Group group = identityService.createGroupQuery().groupId(groupId).singleResult();
		if (group == null) {
			group = identityService.newGroup(groupId);
		}
		group.setName(role.getName());
		group.setType(role.getRoleType());
		identityService.saveGroup(group);
		
		// 删除用户与用户组关系
		List<org.activiti.engine.identity.User> activitiUserList = identityService.createUserQuery().memberOfGroup(groupId).list();
		for (org.activiti.engine.identity.User activitiUser : activitiUserList){
			identityService.deleteMembership(activitiUser.getId(), groupId);
		}

		// 创建用户与用户组关系
		List<User> userList = findUser(new User(new Role(role.getId())));
		for (User e : userList){
			String userId = e.getLoginName();//ObjectUtils.toString(user.getId());
			// 如果该用户不存在，则创建一个
			org.activiti.engine.identity.User activitiUser = identityService.createUserQuery().userId(userId).singleResult();
			if (activitiUser == null){
				activitiUser = identityService.newUser(userId);
				activitiUser.setFirstName(e.getName());
				activitiUser.setLastName(StringUtils.EMPTY);
				activitiUser.setEmail(e.getEmail());
				activitiUser.setPassword(StringUtils.EMPTY);
				identityService.saveUser(activitiUser);
			}
			identityService.createMembership(userId, groupId);
		}
	}

	public void deleteActivitiGroup(Role role) {
		if (!Global.isSynActivitiIndetity()){
			return;
		}
		if(role!=null) {
			String groupId = role.getEnname();
			identityService.deleteGroup(groupId);
		}
	}

	private void saveActivitiUser(User user) {
		if (!Global.isSynActivitiIndetity()){
			return;
		}
		String userId = user.getLoginName();//ObjectUtils.toString(user.getId());
		org.activiti.engine.identity.User activitiUser = identityService.createUserQuery().userId(userId).singleResult();
		if (activitiUser == null) {
			activitiUser = identityService.newUser(userId);
		}
		activitiUser.setFirstName(user.getName());
		activitiUser.setLastName(StringUtils.EMPTY);
		activitiUser.setEmail(user.getEmail());
		activitiUser.setPassword(StringUtils.EMPTY);
		identityService.saveUser(activitiUser);
		
		// 删除用户与用户组关系
		List<Group> activitiGroups = identityService.createGroupQuery().groupMember(userId).list();
		for (Group group : activitiGroups) {
			identityService.deleteMembership(userId, group.getId());
		}
		// 创建用户与用户组关系
		for (Role role : user.getRoleList()) {
	 		String groupId = role.getEnname();
	 		// 如果该用户组不存在，则创建一个
		 	Group group = identityService.createGroupQuery().groupId(groupId).singleResult();
            if(group == null) {
	            group = identityService.newGroup(groupId);
	            group.setName(role.getName());
	            group.setType(role.getRoleType());
	            identityService.saveGroup(group);
            }
			identityService.createMembership(userId, role.getEnname());
		}
	}

	private void deleteActivitiUser(User user) {
		if (!Global.isSynActivitiIndetity()){
			return;
		}
		if(user!=null) {
			String userId = user.getLoginName();//ObjectUtils.toString(user.getId());
			identityService.deleteUser(userId);
		}
	}
	/**
	    * 根据角色英文名查找属于当前角色的登录名的list---------流程审核跟着角色走
	    * @paramroleName  下个流程节点角色英文名
	    * @author lzj
	    * */
	public List<String> findStartLoginName(String enname) {//流程创建用户组
		 //根据角色英文名查找属于当前角色的登录名
		 List<String> candidateUsers=new ArrayList<String>();
		 candidateUsers.add(UserUtils.getUser().getLoginName());//客户经理只能签收自己的任务
		 //List<String> candidateUsers=roleDao.getRolelist( UserUtils.getUser().getId(),enname);
	     return candidateUsers;
	  }
	/**
	    * 根据角色英文名查找属于当前角色的登录名的list---------流程审核跟着角色走
	    * @paramroleName  下个流程节点角色英文名
	    * @author lzj
	    * */
	public List<String> findDealLoginName(String enname) {//流程处理用户组
		 //根据角色英文名查找属于当前角色的登录名(0做部门处理,1不做部门过滤)
		 //List<String> candidateUsers=roleDao.getRolelist(UserUtils.getUser().getId(),enname,"0");
		 List<String> candidateUsers=roleDao.getRolelist(UserUtils.getUser().getId(),enname,"1");
	     return candidateUsers;
	  }

	public List<User> findUserByCompanyId(String companyId) {
			User user = new User();
			user.setCompany(new Office(companyId));
			List list = userDao.findUserByCompanyId(user);	
		    return list;
	}
	
	///////////////// Synchronized to the Activiti end //////////////////
	@Transactional(readOnly = false)
	public int insertInitRoleMenu(String roleId,List<String> dataList){
		return roleDao.insertInitRoleMenu(roleId, dataList);
	}
	
	/**
	 * 根据用户ID查Role
	 * @param user
	 * @return
	 */
	public List<Role> getRoleByUser(User user){
		return roleDao.getRoleByUser(user.getId());
	}
    /**
     * 获取租户管理员
     * @param organId
     * @return
     */
	public User getAdmin(String companyId) {
		return userDao.getAdmin(companyId,Cons.SysDF.DF_ADMIN_ROLE_XD,Cons.SysDF.DF_ADMIN_ROLE_DB);
	}
	
	 /**
     * 提交参数到站内信接口
     * @param file
     * @return
     */
	public Map<String,Object> addLetter(Map<String,String> paramMap){
		Map<String,Object> map=new HashMap<String, Object>();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		String url = Cons.Ips.IP_WLETTER_SEND_PATH;
		RestTemplate restTemplate = new RestTemplate();
		HttpEntity<Map<String,String>> entity = new HttpEntity<Map<String,String>>(paramMap, headers);
		String result = restTemplate.postForObject(url, entity, String.class);
		System.out.println("result============================="+result);
		JSONObject resultJson = JSON.parseObject(result);
		if((boolean) resultJson.get("istrue")){//请求成功
			map.put("isTrue", true);
		}else{//请求失败
			map.put("msg", resultJson.get("msg").toString());
		}
		return map;
	}

	 /**
    * 提交参数到营销短信接口
    * @param file
    * @return
    */
	public Map<String, Object> addMarket(String mobile, String msgtext) {
		Map<String,String> paramMap=new HashMap<String, String>();
		paramMap.put("mobile", mobile);
		paramMap.put("content", msgtext);
		Map<String,Object> map=new HashMap<String, Object>();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		String url = Cons.Ips.IP_WMARKET_SEND_PATH;
		RestTemplate restTemplate = new RestTemplate();
		HttpEntity<Map<String,String>> entity = new HttpEntity<Map<String,String>>(paramMap, headers);
		String result = restTemplate.postForObject(url, entity, String.class);
		System.out.println("result============================="+result);
		JSONObject resultJson = JSON.parseObject(result);
		if((boolean) resultJson.get("istrue")){//请求成功
			map.put("isTrue", true);
		}else{//请求失败
			map.put("msg", resultJson.get("msg").toString());
		}
		return map;
	}
}
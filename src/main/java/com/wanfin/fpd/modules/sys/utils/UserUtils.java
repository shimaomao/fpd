/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.sys.utils;

import java.util.Collection;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.UnavailableSecurityManagerException;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.support.DefaultSubjectContext;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.wanfin.fpd.common.service.BaseService;
import com.wanfin.fpd.common.utils.CacheUtils;
import com.wanfin.fpd.common.utils.DateUtils;
import com.wanfin.fpd.common.utils.SpringContextHolder;
import com.wanfin.fpd.common.utils.StringUtils;
import com.wanfin.fpd.modules.product.entity.TProduct;
import com.wanfin.fpd.modules.sys.dao.AreaDao;
import com.wanfin.fpd.modules.sys.dao.MenuDao;
import com.wanfin.fpd.modules.sys.dao.OfficeDao;
import com.wanfin.fpd.modules.sys.dao.RoleDao;
import com.wanfin.fpd.modules.sys.dao.UserDao;
import com.wanfin.fpd.modules.sys.entity.Area;
import com.wanfin.fpd.modules.sys.entity.Menu;
import com.wanfin.fpd.modules.sys.entity.Office;
import com.wanfin.fpd.modules.sys.entity.Role;
import com.wanfin.fpd.modules.sys.entity.User;
import com.wanfin.fpd.modules.sys.security.SystemAuthorizingRealm.Principal;

/**
 * 用户工具类
 * @author ThinkGem
 * @version 2013-12-05
 */
public class UserUtils {

	/**
	 * @Description ADMIN_ID属性
	 * @Fields String ADMIN_ID
	 * @author Chenh
	 * @date 2016年6月1日 下午3:07:07 
	 */
	
	private static final String ADMIN_ID = "1";
	private static UserDao userDao = SpringContextHolder.getBean(UserDao.class);
	private static RoleDao roleDao = SpringContextHolder.getBean(RoleDao.class);
	private static MenuDao menuDao = SpringContextHolder.getBean(MenuDao.class);
	private static AreaDao areaDao = SpringContextHolder.getBean(AreaDao.class);
	private static OfficeDao officeDao = SpringContextHolder.getBean(OfficeDao.class);
	//@Autowired
    private static SessionDAO sessionDAO= SpringContextHolder.getBean(SessionDAO.class);
    
	public static final String USER_CACHE = "userCache";
	public static final String USER_CACHE_ID_ = "id_";
	public static final String USER_CACHE_LOGIN_NAME_ = "ln";
	public static final String USER_CACHE_LIST_BY_OFFICE_ID_ = "oid_";

	public static final String CACHE_SYSCODE = "syscode";
	public static final String CACHE_SYSNAME = "sysName";
	public static final String CACHE_ROLE_LIST = "roleList";
	public static final String CACHE_MENU_LIST = "menuList";
	public static final String CACHE_MENU_ROLE_LIST = "menuRoleList";//配置菜单专用
	public static final String CACHE_MENU_LIST_PRODUCT = "menuListProduct";
	public static final String CACHE_AREA_LIST = "areaList";
	public static final String CACHE_OFFICE_LIST = "officeList";
	public static final String CACHE_OFFICE_ALL_LIST = "officeAllList";
	
	public static final String CACHE_MODELMENU_ALL_LIST = "modelmenulist";

	
	/**
	 * 根据ID获取用户
	 * @param id
	 * @return 取不到返回null
	 */
	public static User get(String id){
		User user = (User)CacheUtils.get(USER_CACHE, USER_CACHE_ID_ + id);
		if (user ==  null){
			user = userDao.get(id);
			if (user == null){
				return null;
			}
			user.setRoleList(roleDao.findList(new Role(user)));
			CacheUtils.put(USER_CACHE, USER_CACHE_ID_ + user.getId(), user);
			CacheUtils.put(USER_CACHE, USER_CACHE_LOGIN_NAME_ + user.getLoginName(), user);
		}
		return user;
	}
	
	/**
	 * 根据登录名获取用户
	 * @param loginName
	 * @return 取不到返回null
	 */
	public static User getByLoginName(String loginName){
		User user = (User)CacheUtils.get(USER_CACHE, USER_CACHE_LOGIN_NAME_ + loginName);
		if (user == null){
			user = userDao.getByLoginName(new User(null, loginName));
			if (user == null){
				return null;
			}
			user.setRoleList(roleDao.findList(new Role(user)));
			CacheUtils.put(USER_CACHE, USER_CACHE_ID_ + user.getId(), user);
			CacheUtils.put(USER_CACHE, USER_CACHE_LOGIN_NAME_ + user.getLoginName(), user);
		}
		return user;
	}
	
	/**
	 * 清除当前用户缓存
	 */
	public static void clearCache(){
		removeCache(CACHE_ROLE_LIST);
		removeCache(CACHE_MENU_LIST);
		removeCache(CACHE_AREA_LIST);
		removeCache(CACHE_OFFICE_LIST);
		removeCache(CACHE_OFFICE_ALL_LIST);
		UserUtils.clearCache(getUser());
	}
	
	/**
	 * 清除指定用户缓存
	 * @param user
	 */
	public static void clearCache(User user){
		CacheUtils.remove(USER_CACHE, USER_CACHE_ID_ + user.getId());
		CacheUtils.remove(USER_CACHE, USER_CACHE_LOGIN_NAME_ + user.getLoginName());
		CacheUtils.remove(USER_CACHE, USER_CACHE_LOGIN_NAME_ + user.getOldLoginName());
		if (user.getOffice() != null && user.getOffice().getId() != null){
			CacheUtils.remove(USER_CACHE, USER_CACHE_LIST_BY_OFFICE_ID_ + user.getOffice().getId());
		}
	}
	
	/**
	 * 获取当前用户
	 * @return 取不到返回 new User()
	 */
	public static User getUser(){
		Principal principal = getPrincipal();
		if (principal!=null){
			User user = get(principal.getId());
			if (user != null){
				return user;
			}
			return new User();
		}
		// 如果没有登录，则返回实例化空的User对象。
		return new User();
	}
	
	
	/**
	 * 判断当前用户是否是租户管理员
	 * @param id
	 * 
	 */
	public static Boolean get2(String id){
		//if (id == null) {
		if (StringUtils.isBlank(id)) {
			return false;
		}
		User temp = new User();
		temp.setId(id);
		List<Role> tempRoleList = roleDao.findList(new Role(temp));
		return temp.isZadmin(tempRoleList);	
	}
	
	/** 
     *  
     * @return 
     */  
    public static String getSessionId() {  
        Session session = getSession();  
        if (null == session) {  
            return null;  
        }  
        return getSession().getId().toString();  
    }  
      
    /** 
     * @param username 
     * @return 
     */  
    public static Session getSessionByUsername(String username){  
        Collection<Session> sessions = sessionDAO.getActiveSessions();  
        for(Session session : sessions){  
        	 System.out.println(String.valueOf(session.getAttribute("loginName")));
            if(null != session && StringUtils.equals(String.valueOf(session.getAttribute("loginName")), username)){  
                return session;  
            }  
        }  
        return null;  
    }  
    /**
    **踢除用户 
    * @param username 
    */ 
   public static void kickOutUser(String username){  
       Session session = getSessionByUsername(username);  
       if(null != session && !StringUtils.equals(String.valueOf(session.getId()), UserUtils.getSessionId())){  
    	  /* System.out.println("登录ip:"+session.getHost());
		   System.out.println("登录用户"+session.getAttribute(session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY)));
		   System.out.println("最后操作日期:"+session.getLastAccessTime());*/
    	   session.setTimeout(0);//设置session立即失效，即将其踢出系统
       }  
   }
	/**
	 * 获取当前用户角色列表
	 * @return
	 */
	public static List<Role> getRoleList(){
		@SuppressWarnings("unchecked")
		List<Role> roleList = (List<Role>)getCache(CACHE_ROLE_LIST);
		if (roleList == null){
			User user = getUser();
			if (user.isAdmin()){
				roleList = roleDao.findList(new Role());
			}else
			{
				Role role = new Role();
				role.getSqlMap().put("dsf", BaseService.dataScopeFilter(user.getCurrentUser(), "o", "u"));
				roleList = roleDao.findList(role);
			}
			putCache(CACHE_ROLE_LIST, roleList);
		}
		return roleList;
	}
	
	/**
	 * 获取Admin同等级的角色列表
	 * @return
	 */
	public static List<Role> getAdminLevelList(){
		List<Role> roleList = null;
		User user = getUser();
		Role role = new Role();
		role.setOrganId(user.getCompany().getId());
		roleList = roleDao.findOnlyAdminList(role);
		return roleList;
	}
	
	/**
	 * 获取当前用户授权菜单
	 * @return
	 */
	public static List<Menu> getMenuList(){
		@SuppressWarnings("unchecked")
		List<Menu> menuList = (List<Menu>)getCache(CACHE_MENU_LIST);
		if (menuList == null){
			User user = getUser();
			String sysCode = (String) getCache(CACHE_SYSCODE);
			if (user.isAdmin()){
				//超级管理员
				menuList = menuDao.findByAdmin(new Menu());
			}else if(user.getUserType().equals(ADMIN_ID)){	//1、系统管理	2、部门经理	 3、普通用户
				//租户管理员
				Menu m = new Menu();
				m.setSysCode(sysCode);
				m.setUserId(user.getId());
				menuList = menuDao.findByUserId(m);
			}else{
				Menu m = new Menu();
				m.setSysCode(sysCode);
				m.setUserId(user.getId());
				menuList = menuDao.findByUserId(m);
			}
			putCache(CACHE_MENU_LIST, menuList);
		}
		return menuList;
	}
	
	public static List<Menu> findAllByNoSysCode(Menu menu){
		return menuDao.findAllByNoSysCode(menu);
	}
	/**
	 * 获取当前用户产品授权菜单
	 * @return
	 */
	public static List<Menu> getMenuListByProduct(TProduct product){
		@SuppressWarnings("unchecked")
		List<Menu> menuList = (List<Menu>)getCache(CACHE_MENU_LIST_PRODUCT);
		if (menuList == null){
			User user = getUser();
			String sysCode = null;
			if((product != null) && (StringUtils.isNotEmpty(product.getId()))){
				sysCode = product.getId();
			}else{
				sysCode = (String) getCache(CACHE_SYSCODE);
			}
			if(sysCode !=null){
				if (user.isAdmin()){
					//超级管理员
					menuList = menuDao.findByProAdmin(new Menu());
				}else if(user.getUserType().equals(ADMIN_ID)){	//1、系统管理	2、部门经理	 3、普通用户
					//租户管理员
					Menu m = new Menu();
					m.setSysCode(sysCode);
					m.setUserId(user.getId());
					menuList = menuDao.findByProAndUserId(m);
				}else{
					Menu m = new Menu();
					m.setSysCode(sysCode);
					m.setUserId(user.getId());
					menuList = menuDao.findByProAndUserId(m);
				}
				//putCache(CACHE_MENU_LIST_PRODUCT, menuList);
			}else{
				System.out.println("sysCode is null!");
			}
		}
		return menuList;
	}
	
	
	
	/**
	 * 获取业务中心菜单，产品配置专用
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<Menu> getBusinessMenuList(){
		List<Menu> menuList = (List<Menu>)getCache(CACHE_MODELMENU_ALL_LIST);
		if (menuList == null || menuList.size()<0){
			menuList = menuDao.findBybuiness();
			putCache(CACHE_MODELMENU_ALL_LIST, menuList);
		}
		return menuList;
	}
	/**
	 * 获取当前用户授权的区域
	 * @return
	 */
	public static List<Area> getAreaList(){
		@SuppressWarnings("unchecked")
		List<Area> areaList = (List<Area>)getCache(CACHE_AREA_LIST);
		if (areaList == null){
			areaList = areaDao.findAllList(new Area());
			putCache(CACHE_AREA_LIST, areaList);
		}
		return areaList;
	}
	
	/**
	 * 获取当前用户有权限访问的部门
	 * @return
	 */
	public static List<Office> getOfficeList(){
		@SuppressWarnings("unchecked")
		List<Office> officeList = (List<Office>)getCache(CACHE_OFFICE_LIST);
		officeList = null;
		if (officeList == null || officeList.size()<1){
			User user = getUser();
			if (user.isAdmin()){
				officeList = officeDao.findAllList(new Office());
			}else{
				Office office = new Office();
				office.getSqlMap().put("dsf", BaseService.dataScopeFilter(user, "a", ""));
				officeList = officeDao.findList(office);
			}
			putCache(CACHE_OFFICE_LIST, officeList);
		}
		return officeList;
	}

	/**
	 * 获取当前用户有权限访问的部门
	 * @return
	 */
	public static List<Office> getOfficeAllList(){
		@SuppressWarnings("unchecked")
		List<Office> officeList = (List<Office>)getCache(CACHE_OFFICE_ALL_LIST);
		if (officeList == null){
			officeList = officeDao.findAllList(new Office());
		}
		return officeList;
	}
	
	/**
	 * 获取机构对象
	 * @return
	 */
	public static Office getOfficeById(String id){
		return officeDao.get(id);
	}
	
	/**
	 * 获取授权主要对象
	 */
	public static Subject getSubject(){
		return SecurityUtils.getSubject();
	}
	
	/**
	 * 获取当前登录者对象
	 */
	public static Principal getPrincipal(){
		try{
			Subject subject = SecurityUtils.getSubject();
			Principal principal = (Principal)subject.getPrincipal();
			if (principal != null){
				return principal;
			}
//			subject.logout();
		}catch (UnavailableSecurityManagerException e) {
			
		}catch (InvalidSessionException e){
			
		}
		return null;
	}
	
	public static Session getSession(){
		try{
			Subject subject = SecurityUtils.getSubject();
			Session session = subject.getSession(false);
			if (session == null){
				session = subject.getSession();
			}
			if (session != null){
				return session;
			}
//			subject.logout();
		}catch (InvalidSessionException e){
			
		}
		return null;
	}
	
	// ============== User Cache ==============
	
	public static Object getCache(String key) {
		return getCache(key, null);
	}
	
	public static Object getCache(String key, Object defaultValue) {
//		Object obj = getCacheMap().get(key);
		Object obj = getSession().getAttribute(key);
		return obj==null?defaultValue:obj;
	}

	public static void putCache(String key, Object value) {
//		getCacheMap().put(key, value);
		getSession().setAttribute(key, value);
	}

	public static void removeCache(String key) {
//		getCacheMap().remove(key);
		getSession().removeAttribute(key);
	}

	/**
	 * @Description TODO
	 * @return
	 * @author Chenh 
	 * @date 2016年6月1日 下午3:02:12  
	 */
	public static User getAdmin() {
		return userDao.get(ADMIN_ID);
	}
	
//	public static Map<String, Object> getCacheMap(){
//		Principal principal = getPrincipal();
//		if(principal!=null){
//			return principal.getCacheMap();
//		}
//		return new HashMap<String, Object>();
//	}
	
	
	/**
	 * @Description 获取流程号 ， 注意：此方法调用一次，计数器就+1
	 * @param category	类型
	 * @return
	 * @author zzm
	 * @date 2016-7-14 上午10:52:55  
	 */
	public static String getSqeNo(String category){
		int num = getCount(category);
		String str = DateUtils.getDate("yyyyMMdd") + String.format("%06d", (num+1));//如：20160101000001
		Db.update("update t_count set count = IF(count < 999999, (count + 1), 1) where category = '"+category+"'");
		return str;
	}
	
	public static String getSqeNo(String category, String prefix){
		return prefix + getSqeNo(category);
	}
	
	/**
	 * @Description 获取计数
	 * @param category
	 * @return
	 * @author zzm
	 * @date 2016-7-14 上午10:59:30  
	 */
	public static int getCount(String category){
		Record record = Db.findFirst("select * from t_count where category = '"+category+"'");
		if(record == null){
			record = new Record();
			record.set("category", category);
			record.set("count", 0);
			Db.save("t_count", record);
		}
		return record.getInt("count");
	}
}

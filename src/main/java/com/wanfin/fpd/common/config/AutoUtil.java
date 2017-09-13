/**    
 * @Project: fpd 
 * @Title: AutoUtil 
 * @Package com.wanfin.fpd.common.config 
 * @Description [[_自动初始化资源类_]]文件
 * @author Chenh 
 * @date 2016年9月6日 上午10:29:29   
 * @version V1.0.0   */
package com.wanfin.fpd.common.config;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.alibaba.fastjson.JSON;
import com.wanfin.fpd.common.utils.FileUtils;
import com.wanfin.fpd.common.utils.StringUtils;
import com.wanfin.fpd.modules.form.entity.tpl.DfFormTpl;
import com.wanfin.fpd.modules.sys.entity.Office;
import com.wanfin.fpd.modules.sys.entity.Role;
import com.wanfin.fpd.modules.sys.entity.User;

/**
 * @author Chenh
 * @date 2016年9月6日 上午10:29:29
 * @Description [[_自动初始化资源类_]] AutoUtil类 自动初始化资源类
 * 1、所有的JSON文件使用UTF-8编码
 */
public class AutoUtil {
	/**自动初始化  -  机构-公司文件基本路径*/  
	private static final String AUTO_COMPANY_BASEPATH = Global.getAutoConfig("auto.company.basepath");
	/**自动初始化  -  机构-公司文件-Admin*/  
	private static final String AUTO_COMPANY_ADMIN = Global.getAutoConfig("auto.company.admin");
	/**自动初始化  -  机构-公司文件-租户*/  
	private static final String AUTO_COMPANY_ORG = Global.getAutoConfig("auto.company.org");

	/**自动初始化  -  机构-部门文件基本路径*/  
	private static final String AUTO_OFFICE_BASEPATH = Global.getAutoConfig("auto.office.basepath");
	/**自动初始化  -  机构-部门文件-Admin*/  
	private static final String AUTO_OFFICE_ADMIN = Global.getAutoConfig("auto.office.admin");
	/**自动初始化  -  机构-部门文件-租户*/  
	private static final String AUTO_OFFICE_ORG = Global.getAutoConfig("auto.office.org");
	
	/**自动初始化  -  角色文件基本路径*/  
	private static final String AUTO_ROLE_BASEPATH = Global.getAutoConfig("auto.role.basepath");
	/**自动初始化  -  角色文件-Admin*/  
	private static final String AUTO_ROLE_ADMIN = Global.getAutoConfig("auto.role.admin");
	/**自动初始化  -  角色文件-租户*/  
	private static final String AUTO_ROLE_ORG = Global.getAutoConfig("auto.role.org");
	
	/**自动初始化  -  用户文默认密码*/  
	public static final String AUTO_USER_PASSWORD = Global.getAutoConfig("auto.user.password");
	/**自动初始化  -  用户文件基本路径*/  
	private static final String AUTO_USER_BASEPATH = Global.getAutoConfig("auto.user.basepath");
	/**自动初始化  -  用户文件-Admin*/  
	private static final String AUTO_USER_ADMIN = Global.getAutoConfig("auto.user.admin");
	/**自动初始化  -  用户文件-租户管理员*/  
	private static final String AUTO_USER_ORG = Global.getAutoConfig("auto.user.org");
	
	/**自动初始化  -  流程文件基本路径*/
	public static final String AUTO_ACTIVIT_BASEPATH = Global.getAutoConfig("auto.activit.basepath");
	/**自动初始化  -  流程文件-租户担保*/
	public static final String AUTO_ACTIVIT_ORG_DB = Global.getAutoConfig("auto.activit.org.db");
	/**自动初始化  -  流程文件-租户小贷*/
	public static final String AUTO_ACTIVIT_ORG_XD = Global.getAutoConfig("auto.activit.org.xd");
	
	/**自动初始化  -  模板文件基本路径*/
	public static final String AUTO_FORMTPL_BASEPATH = Global.getAutoConfig("auto.formTpl.basepath");
	/**自动初始化  -  模板文件-ADMIN*/
	public static final String AUTO_FORMTPL_ADMIN = Global.getAutoConfig("auto.formTpl.admin");
	/**自动初始化  -  模板文件-贷款租户*/
	public static final String AUTO_FORMTPL_ORG_XD = Global.getAutoConfig("auto.formTpl.org.xd");
	/**自动初始化  -  模板文件-担保租户*/
	public static final String AUTO_FORMTPL_ORG_DB = Global.getAutoConfig("auto.formTpl.org.db");
	
	/**自动初始化  -  菜单文件基本路径*/
	public static final String AUTO_MENU_BASEPATH = Global.getAutoConfig("auto.menu.basepath");
	/**自动初始化  -  菜单文件-租户担保*/
	public static final String AUTO_MENU_ORG_DB = Global.getAutoConfig("auto.menu.org.db");
	/**自动初始化  -  菜单文件-租户小贷*/
	public static final String AUTO_MENU_ORG_XD = Global.getAutoConfig("auto.menu.org.xd");
	
	/******************************************************************************************/
	public static String getAbsClass() {
		String os = System.getProperty("os.name");		
		if(os.contains("Windows")){
			return AutoUtil.class.getResource("/").getPath().substring(1);
		}else{
			return AutoUtil.class.getResource("/").getPath();
		}
	}
	
	public static List<Office> getCompanysAdmin() {
		return getOffices(getAbsClass() + AUTO_COMPANY_BASEPATH + AUTO_COMPANY_ADMIN);
	}
	
	public static List<Office> getCompanysOrg() {
		return getOffices(getAbsClass() + AUTO_COMPANY_BASEPATH + AUTO_COMPANY_ORG);
	}
	
	public static List<Office> getOfficesAdmin() {
		return getOffices(getAbsClass() + AUTO_OFFICE_BASEPATH + AUTO_OFFICE_ADMIN);
	}
	
	public static List<Office> getOfficesOrg() {
		return getOffices(getAbsClass() + AUTO_OFFICE_BASEPATH + AUTO_OFFICE_ORG);
	}
	
	public static List<Role> getRolesAdmin() {
		return getRoles(getAbsClass() + AUTO_ROLE_BASEPATH + AUTO_ROLE_ADMIN);
	}
	
	public static List<Role> getRolesOrg() {
		return getRoles(getAbsClass() + AUTO_ROLE_BASEPATH + AUTO_ROLE_ORG);
	}

	public static List<User> getUsersAdmin() {
		return getUsers(getAbsClass() + AUTO_USER_BASEPATH + AUTO_USER_ADMIN);
	}
	
	public static List<User> getUsersOrg() {
		return getUsers(getAbsClass() + AUTO_USER_BASEPATH + AUTO_USER_ORG);
	}
	
	public static String getActivitOrgDb() {
		return getAbsClass() + AUTO_ACTIVIT_BASEPATH + AUTO_ACTIVIT_ORG_DB;
	}
	
	public static String getActivitOrgXd() {
		return getAbsClass() + AUTO_ACTIVIT_BASEPATH + AUTO_ACTIVIT_ORG_XD;
	}

	public static List<DfFormTpl> getFormTplOrgXd() {
		return getFormTpls(getAbsClass() + AUTO_FORMTPL_BASEPATH + AUTO_FORMTPL_ORG_XD);
	}

	public static List<DfFormTpl> getFormTplOrgDb() {
		return getFormTpls(getAbsClass() + AUTO_FORMTPL_BASEPATH + AUTO_FORMTPL_ORG_DB);
	}

	public static List<DfFormTpl> getFormTplAdmin() {
		return getFormTpls(getAbsClass() + AUTO_FORMTPL_BASEPATH + AUTO_FORMTPL_ADMIN);
	}
	
	public static boolean writeFormTplOrgXd(JSONArray json) {
		return writeFormTpls(getAbsClass() + AUTO_FORMTPL_BASEPATH + AUTO_FORMTPL_ORG_XD, json);
	}

	public static boolean writeFormTplOrgDb(JSONArray json) {
		return writeFormTpls(getAbsClass() + AUTO_FORMTPL_BASEPATH + AUTO_FORMTPL_ORG_DB, json);
	}

	public static boolean writeFormTplAdmin(JSONObject json) {
		return writeFormTpls(getAbsClass() + AUTO_FORMTPL_BASEPATH + AUTO_FORMTPL_ADMIN, json);
	}
	
	
	
	
	public static String getMenuOrgDb() {
		return getAbsClass() + AUTO_MENU_BASEPATH + AUTO_MENU_ORG_DB;
	}
	
	public static String getMenuOrgXd() {
		return getAbsClass() + AUTO_MENU_BASEPATH + AUTO_MENU_ORG_XD;
	}
	
	/******************************************************************************************
	 * @Description: 读取机构-租户列表
	 * @author Chenh
	 * @param path
	 * @return  
	 * @throws
	 */
	public static List<Office> getCompanys(String path) {
		return readBean(path, Office.class);
	}
	
	/**
	 * @Description: 读取机构-部门列表
	 * @author Chenh
	 * @param path
	 * @return  
	 * @throws
	 */
	public static List<Office> getOffices(String path) {
		return readBean(path, Office.class);
	}

	/**
	 * @Description: 读取角色列表
	 * @author Chenh
	 * @param path
	 * @return  
	 * @throws
	 */
	public static List<Role> getRoles(String path) {
		return readBean(path, Role.class);
	}

	/**
	* @Description: 读取用户列表
	* @author Chenh
	* @param path
	* @return  
	* @throws
	*/
	public static List<User> getUsers(String path) {
		return readBean(path, User.class);
	}
	
	/**
	 * @Description: 读取模板列表
	 * @author Chenh
	 * @param path
	 * @return  
	 * @throws
	 */
	public static List<DfFormTpl> getFormTpls(String path) {
		return readBean(path, DfFormTpl.class);
	}
	
	/**
	 * @Description: 写模板列表
	 * @author Chenh
	 * @param path
	 * @return  
	 * @throws
	 */
	public static Boolean writeFormTpls(String path, JSONObject json) {
		return writeBean(path, json);
	}
	
	/**
	 * @Description: 写模板列表
	 * @author Chenh
	 * @param path
	 * @return  
	 * @throws
	 */
	public static Boolean writeFormTpls(String path, JSONArray json) {
		return writeBean(path, json);
	}
	
	/******************************************************************************************
	 * @Description: 读取json文件 
	 * @author Chenh
	 * @param path
	 * @return  
	 * @throws
	 */
	public static String ReadFile(String path) {
		String result = null;
		try {
			File file = new File(path);
			result = FileUtils.readFileToString(file, "UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * @Description: 写json文件 
	 * @author Chenh
	 * @param path
	 * @return  
	 * @throws
	 */
	public static Boolean writeFile(String path, String content) {
		Boolean isTrue = false;
		try {
			File file = new File(path);
			FileUtils.writeStringToFile(file, content, "UTF-8");
			isTrue = true;
		} catch (IOException e) {
			System.out.println("保存文件失败！");
		}
		return isTrue;
	}
	
	/***
	 * 将JSON文本反序列化为主从关系的实体
	 * @param <T>
	 * @param 主实体类型
	 * @param jsonString
	 *            JSON文本
	 * @param mainClass
	 *            主实体类型
	 * @return
	 */
	public static <T> List<T> toBean(String jsonString, Class<T> mainClass) {
		return (ArrayList<T>) JSON.parseArray(jsonString, mainClass);
	}
	
	/***
	 * @Description: 读取json文件 将JSON文本反序列化为主从关系的实体
	 * @param <T>
	 * @param 主实体类型
	 * @param jsonString JSON文本
	 * @param mainClass 主实体类型
	 * @param path
	 * @return
	 */
	public static <T> List<T> readBean(String path, Class<T> mainClass) {
		return toBean(ReadFile(path), mainClass);
	}
	
	/***
	 * @Description: 写json文件 将对象序列化为JSon
	 * @param json JSON文本
	 * @param path
	 * @return
	 */
	public static boolean writeBean(String path, JSONObject json) {
		if((json != null) && StringUtils.isNotEmpty(path)){
			return writeFile(path, json.toString());
		}
		return false;
	}
	/***
	 * @Description: 写json文件 将对象序列化为JSon
	 * @param json JSON文本
	 * @param path
	 * @return
	 */
	public static boolean writeBean(String path, JSONArray json) {
		if((json != null) && StringUtils.isNotEmpty(path)){
			return writeFile(path, json.toString());
		}
		return false;
	}
}

/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.common.config;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.springframework.core.io.DefaultResourceLoader;

import com.ckfinder.connector.ServletContextFactory;
import com.google.common.collect.Maps;
import com.wanfin.fpd.common.utils.PropertiesLoader;
import com.wanfin.fpd.common.utils.StringUtils;

/**
 * 全局配置类
 * @author ThinkGem
 * @version 2014-06-25
 */
public class Global {
	/**
	 * 树形结构根路径
	 */
	public static final String ROOT_ID = "1";

	/**
	 * 当前对象实例
	 */
	private static Global global = new Global();
	
	/**
	 * 保存全局属性值
	 */
	private static Map<String, String> map = Maps.newHashMap();
	
	/**
	 * 属性文件加载对象
	 */
	private static PropertiesLoader loader = new PropertiesLoader("jeesite.properties");

	
	/**
	 * 短信邮件模板属性文件加载对象
	 */
	private static PropertiesLoader message = new PropertiesLoader("jprop-message.properties");
	
	/**
	 * 站内信模板
	 */
	private static PropertiesLoader letter = new PropertiesLoader("jprop-letter.properties");
	/**
	 * ID常量属性文件营销短信
	 */
	private static PropertiesLoader market = new PropertiesLoader("jprop-253message.properties");
	/**
	 * API接口属性文件加载对象
	 */
	private static PropertiesLoader apipro = new PropertiesLoader("jprop-api.properties");

	/**
	 * ID常量属性文件加载对象
	 */
	private static PropertiesLoader idspro = new PropertiesLoader("jprop-ids.properties");
	
	/**
	 * ID常量属性文件加载对象
	 */
	private static PropertiesLoader autopro = new PropertiesLoader("jprop-auto.properties");

	/**
	 * 临时用于配置业务部负责人及分管领导的配置文件 add 20170306
	 */
	private static PropertiesLoader roleTemp = new PropertiesLoader("rols_temp.properties");
	

	/**
	 * 显示/隐藏
	 */
	public static final String SHOW = "1";
	public static final String HIDE = "0";

	/**
	 * 是/否
	 */
	public static final String YES = "1";
	public static final String NO = "0";
	
	/**
	 * 对/错
	 */
	public static final String TRUE = "true";
	public static final String FALSE = "false";
	
	/**
	 * 上传文件基础虚拟路径
	 */
//	public static final String USERFILES_BASE_URL = "/userfiles/";
	public static final String USERFILES_BASE_URL = getConfig("userfiles.base.url");
	public static final String GROUP_BASE_URL = getConfig("group.base.url");
	public static final String INTRANET_URL = getConfig("intranet.url");
	
	
	/**
	 * 当支付完成之后，返回CallBack   servlet使用
	 */
	public static String BIRULE_ID = "1";

	/**
	 * 获取当前对象实例
	 */
	public static Global getInstance() {
		return global;
	}
	
	
	public static String getMarketConfig(String key) {
		String value = map.get(key);
		if (value == null){
			value = market.getProperty(key);
			map.put(key, value != null ? value : StringUtils.EMPTY);
		}
		return value;
	}
	
	/**
	 * 获取配置
	 * @see ${fns:getConfig('adminPath')}
	 */
	public static String getConfig(String key) {
		String value = map.get(key);
		if (value == null){
			value = loader.getProperty(key);
			map.put(key, value != null ? value : StringUtils.EMPTY);
		}
		return value;
	}
	
	/**
	 * 获取临时用于配置业务部负责人及分管领导的配置文件 add 20170306
	 * @see ${fns:getApiConfig('api.ips.wd')}
	 */
	public static String getRoleTempConfig(String key) {
		String value = map.get(key);
		if (value == null){
			value = roleTemp.getProperty(key);
			map.put(key, value != null ? value : StringUtils.EMPTY);
		}
		return value;
	}
	
	/**
	 * 获取配置短信和邮件的信息配置
	 * @see ${fns:getMessageConfig('adminPath')}
	 */
	public static String getMessageConfig(String key) {
		String value = map.get(key);
		if (value == null){
			value = message.getProperty(key);
			map.put(key, value != null ? value : StringUtils.EMPTY);
		}
		return value;
	}
	
	/**
	 * 获取配置短信和邮件的信息配置
	 * @see ${fns:getApiConfig('api.ips.wd')}
	 */
	public static String getApiConfig(String key) {
		String value = map.get(key);
		if (value == null){
			value = apipro.getProperty(key);
			map.put(key, value != null ? value : StringUtils.EMPTY);
		}
		return value;
	}
	/**
	 * 获取站内信模板配置
	 * @see ${fns:getApiConfig('auto.XXX.XXX')}
	 */
	public static String getLetterConfig(String key) {
		String value = map.get(key);
		if (value == null){
			value = letter.getProperty(key);
			map.put(key, value != null ? value : StringUtils.EMPTY);
		}
		return value;
	}
	/**
	 * 获取配置短信和邮件的信息配置
	 * @see ${fns:getApiConfig('ids.super.office')}
	 */
	public static String getIdsConfig(String key) {
		String value = map.get(key);
		if (value == null){
			value = idspro.getProperty(key);
			map.put(key, value != null ? value : StringUtils.EMPTY);
		}
		return value;
	}
	
	/**
	 * 获取自动初始化的配置
	 * @see ${getAutoConfig('auto.XXX.XXX')}
	 */
	public static String getAutoConfig(String key) {
		String value = map.get(key);
		if (value == null){
			value = autopro.getProperty(key);
			map.put(key, value != null ? value : StringUtils.EMPTY);
		}
		return value;
	}
	
	/**
	 * 获取管理端根路径
	 */
	public static String getAdminPath() {
		return getConfig("adminPath");
	}
	
	/**
	 * 获取Api根路径
	 */
	public static String getApiPath() {
		return getConfig("api.baseUri");
//		return getConfig("api.baseUri")+getConfig("api.version");
	}
	
	/**
	 * 获取前端根路径
	 */
	public static String getFrontPath() {
		return getConfig("frontPath");
	}
	
	/**
	 * 获取URL后缀
	 */
	public static String getUrlSuffix() {
		return getConfig("urlSuffix");
	}
	
	/**
	 * 是否是演示模式，演示模式下不能修改用户、角色、密码、菜单、授权
	 */
	public static Boolean isDemoMode() {
		String dm = getConfig("demoMode");
		return "true".equals(dm) || "1".equals(dm);
	}
	
	/**
	 * 在修改系统用户和角色时是否同步到Activiti
	 */
	public static Boolean isSynActivitiIndetity() {
		String dm = getConfig("activiti.isSynActivitiIndetity");
		return "true".equals(dm) || "1".equals(dm);
	}
    
	/**
	 * 页面获取常量
	 * @see ${fns:getConst('YES')}
	 */
	public static Object getConst(String field) {
		try {
			return Global.class.getField(field).get(null);
		} catch (Exception e) {
			// 异常代表无配置，这里什么也不做
		}
		return null;
	}

	/**
	 * 获取上传文件的根目录
	 * @return
	 */
	public static String getUserfilesBaseDir() {
		String dir = getConfig("userfiles.basedir");
		if (StringUtils.isBlank(dir)){
			try {
				dir = ServletContextFactory.getServletContext().getRealPath("/");
			} catch (Exception e) {
				return "";
			}
		}
		if(!dir.endsWith("/")) {
			dir += "/";
		}
//		System.out.println("userfiles.basedir: " + dir);
		return dir;
	}
	
    /**
     * 获取工程路径
     * @return
     */
    public static String getProjectPath(){
    	// 如果配置了工程路径，则直接返回，否则自动获取。
		String projectPath = Global.getConfig("projectPath");
		if (StringUtils.isNotBlank(projectPath)){
			return projectPath;
		}
		try {
			File file = new DefaultResourceLoader().getResource("").getFile();
			if (file != null){
				while(true){
					File f = new File(file.getPath() + File.separator + "src" + File.separator + "main");
					if (f == null || f.exists()){
						break;
					}
					if (file.getParentFile() != null){
						file = file.getParentFile();
					}else{
						break;
					}
				}
				projectPath = file.toString();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return projectPath;
    }
	
    
    public static Map<String, String> getMap() {
		return map;
	}

	public static void setMap(Map<String, String> map) {
		Global.map = map;
	}


	/**
	 * 获取管理端根路径
	 */
	public static String getMsg() {
		return getConfig("adminPath");
	}
	
	/**
	 * 微服务地址
	 */
	public static String getWfwHttp(){
		return getApiConfig("wfw.api.http");
	}

 
}

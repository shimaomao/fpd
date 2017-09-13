/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.sys.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.common.collect.Maps;
import com.mangofactory.swagger.annotations.ApiIgnore;
import com.wanfin.fpd.common.config.Cons;
import com.wanfin.fpd.common.config.Global;
import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.security.shiro.session.SessionDAO;
import com.wanfin.fpd.common.servlet.ValidateCodeServlet;
import com.wanfin.fpd.common.utils.CacheUtils;
import com.wanfin.fpd.common.utils.CookieUtils;
import com.wanfin.fpd.common.utils.IdGen;
import com.wanfin.fpd.common.utils.SpringContextHolder;
import com.wanfin.fpd.common.utils.StringUtils;
import com.wanfin.fpd.common.web.BaseController;
import com.wanfin.fpd.modules.product.entity.TProduct;
import com.wanfin.fpd.modules.product.service.TProductService;
import com.wanfin.fpd.modules.sys.entity.Role;
import com.wanfin.fpd.modules.sys.security.FormAuthenticationFilter;
import com.wanfin.fpd.modules.sys.security.SystemAuthorizingRealm;
import com.wanfin.fpd.modules.sys.security.SystemAuthorizingRealm.Principal;
import com.wanfin.fpd.modules.sys.service.SystemService;
import com.wanfin.fpd.modules.sys.utils.LogUtils;
import com.wanfin.fpd.modules.sys.utils.UserUtils;

/**
 * 登录Controller
 * @author ThinkGem
 * @version 2013-5-31
 */
@ApiIgnore
@Controller
public class LoginController extends BaseController{
	
	@Autowired
	private SessionDAO sessionDAO;
	
	@Autowired
	private TProductService tProductService;
	@Autowired
	private SystemService systemService;
	/**
	 * 管理登录
	 */
	@RequestMapping(value = "${adminPath}/login", method = RequestMethod.GET)
	public String login(HttpServletRequest request, HttpServletResponse response, Model model) {
		Principal principal = UserUtils.getPrincipal();

//		// 默认页签模式
		String tabmode = CookieUtils.getCookie(request, "tabmode");
		if (tabmode == null){
			CookieUtils.setCookie(response, "tabmode", "0");
		}
		
		if (logger.isDebugEnabled()){
			logger.debug("login, active session size: {}", sessionDAO.getActiveSessions(false).size());
		}
		
		// 如果已登录，再次访问主页，则退出原账号。
		if (Global.TRUE.equals(Global.getConfig("notAllowRefreshIndex"))){
			CookieUtils.setCookie(response, "LOGINED", "false");
		}
		
		// 如果已经登录，则跳转到管理首页
		if(principal != null && !principal.isMobileLogin()){
			return "redirect:" + adminPath;
		}
//		String view;
//		view = "/WEB-INF/views/modules/sys/sysLogin.jsp";
//		view = "classpath:";
//		view += "jar:file:/D:/GitHub/jeesite/src/main/webapp/WEB-INF/lib/jeesite.jar!";
//		view += "/"+getClass().getName().replaceAll("\\.", "/").replace(getClass().getSimpleName(), "")+"view/sysLogin";
//		view += ".jsp";
		return "modules/sys/sysLogin";
	}

	/**
	 * 登录失败，真正登录的POST请求由Filter完成
	 */
	@RequestMapping(value = "${adminPath}/login", method = RequestMethod.POST)
	public String loginFail(HttpServletRequest request, HttpServletResponse response, Model model) {
		Principal principal = UserUtils.getPrincipal();
		
		// 如果已经登录，则跳转到管理首页
		if(principal != null){
			return "redirect:" + adminPath;
		}

		String username = WebUtils.getCleanParam(request, FormAuthenticationFilter.DEFAULT_USERNAME_PARAM);
		boolean rememberMe = WebUtils.isTrue(request, FormAuthenticationFilter.DEFAULT_REMEMBER_ME_PARAM);
		boolean mobile = WebUtils.isTrue(request, FormAuthenticationFilter.DEFAULT_MOBILE_PARAM);
		String exception = (String)request.getAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
		String message = (String)request.getAttribute(FormAuthenticationFilter.DEFAULT_MESSAGE_PARAM);
		
		if (StringUtils.isBlank(message) || StringUtils.equals(message, "null")){
			message = "用户或密码错误, 请重试.";
		}

		model.addAttribute(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM, username);
		model.addAttribute(FormAuthenticationFilter.DEFAULT_REMEMBER_ME_PARAM, rememberMe);
		model.addAttribute(FormAuthenticationFilter.DEFAULT_MOBILE_PARAM, mobile);
		model.addAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME, exception);
		model.addAttribute(FormAuthenticationFilter.DEFAULT_MESSAGE_PARAM, message);
		
		if (logger.isDebugEnabled()){
			logger.debug("login fail, active session size: {}, message: {}, exception: {}", 
					sessionDAO.getActiveSessions(false).size(), message, exception);
		}
		
		// 非授权异常，登录失败，验证码加1。
		if (!UnauthorizedException.class.getName().equals(exception)){
			model.addAttribute("isValidateCodeLogin", isValidateCodeLogin(username, true, false));
		}
		
		// 验证失败清空验证码
		request.getSession().setAttribute(ValidateCodeServlet.VALIDATE_CODE, IdGen.uuid());
		
		// 如果是手机登录，则返回JSON字符串
		if (mobile){
	        return renderString(response, model);
		}
		
		return "modules/sys/sysLogin";
	}

	/**
	 * 登录成功，进入管理首页
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "${adminPath}")
	public String index(HttpServletRequest request, HttpServletResponse response) {
		Principal principal = UserUtils.getPrincipal();

		// 登录成功后，验证码计算器清零
		isValidateCodeLogin(principal.getLoginName(), false, true);
		
		if (logger.isDebugEnabled()){
			logger.debug("show index, active session size: {}", sessionDAO.getActiveSessions(false).size());
		}
		
		// 如果已登录，再次访问主页，则退出原账号。
		if (Global.TRUE.equals(Global.getConfig("notAllowRefreshIndex"))){
			String logined = CookieUtils.getCookie(request, "LOGINED");
			if (StringUtils.isBlank(logined) || "false".equals(logined)){
				CookieUtils.setCookie(response, "LOGINED", "true");
			}else if (StringUtils.equals(logined, "true")){
				UserUtils.getSubject().logout();
				return "redirect:" + adminPath + "/login";
			}
		}
		
		// 如果是手机登录，则返回JSON字符串
		if (principal.isMobileLogin()){
			if (request.getParameter("login") != null){
				return renderString(response, principal);
			}
			if (request.getParameter("index") != null){
				return toSysIndex(request, response, null);
			}
			return "redirect:" + adminPath + "/login";
		}
		// 登录成功后，将登陆名保存至session
		UserUtils.getSession().setAttribute("loginName",principal.getLoginName());
//		// 登录成功后，获取上次登录的当前站点ID
//		UserUtils.putCache("siteId", StringUtils.toLong(CookieUtils.getCookie(request, "siteId")));

//		System.out.println("==========================a");
//		try {
//			byte[] bytes = com.wanfin.fpd.common.utils.FileUtils.readFileToByteArray(
//					com.wanfin.fpd.common.utils.FileUtils.getFile("c:\\sxt.dmp"));
//			UserUtils.getSession().setAttribute("kkk", bytes);
//			UserUtils.getSession().setAttribute("kkk2", bytes);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
////		for (int i=0; i<1000000; i++){
////			//UserUtils.getSession().setAttribute("a", "a");
////			request.getSession().setAttribute("aaa", "aa");
////		}
//		System.out.println("==========================b");
//		// 默认页签模式
		String tabmode = CookieUtils.getCookie(request, "tabmode");
		if (tabmode == null){
			CookieUtils.setCookie(response, "tabmode", "0");
		}
		return toSysIndex(request, response, null);
	}

	private String toSysIndex(HttpServletRequest request, HttpServletResponse response, TProduct tProduct) {
		if(tProduct == null){
			tProduct = new TProduct();
		}
		tProduct.setStatus(Cons.ProductStatus.PUBLISHED);
//		tProduct.setReleasesWay("2");
		Page<TProduct> page = tProductService.findPage2(new Page<TProduct>(request, response), tProduct);
		//String roleName = systemService.getRoleByUser(UserUtils.getUser()).getName();
		String roleName = null;
		List<Role> listUsers = systemService.getRoleByUser(UserUtils.getUser());
		if(listUsers != null&&listUsers.size()>0){
			if(listUsers.size()==1){
				roleName = listUsers.get(0).getName();
			}else{
				roleName = listUsers.get(0).getName()+"等";
			}
		}
		request.setAttribute("page", page);
		request.setAttribute("tProduct", tProduct);
		request.setAttribute("roleName", roleName);//#3559、 #4383
		/**
		 * update  lxh   不用选取产品
		 */
		String istrue = request.getParameter("istrue");
		System.out.println("--------------------"+istrue+"--------------------");
		if(StringUtils.isNotEmpty(istrue) && (istrue).equals("1")){
			String reurl = request.getParameter("reurl");
			System.out.println("--------------------"+reurl+"--------------------");
			request.getSession().setAttribute("isReUrl", istrue);
			request.getSession().setAttribute("reurl", reurl);
//			return "redirect:"+ adminPath + "/" + reurl;
		}
		return "modules/sys/sysIndex";
	}
	
	/**
	 * 获取主题方案
	 */
	@RequestMapping(value = "/theme/{theme}")
	public String getThemeInCookie(@PathVariable String theme, HttpServletRequest request, HttpServletResponse response){
		if (StringUtils.isNotBlank(theme)){
			CookieUtils.setCookie(response, "theme", theme);
		}else{
			theme = CookieUtils.getCookie(request, "theme");
		}
		return "redirect:"+request.getParameter("url");
	}
	
	/**
	 * @Description 获取访问的产品（系统）
	 * @param syscode 产品标识
	 * @return
	 * @author zzm 
	 * @date 2016-4-22 下午5:39:22  
	 */
	@RequestMapping(value = "${adminPath}/syscode/{syscode}")
	public String getSysCodeInCache(@PathVariable String syscode, HttpServletRequest request, HttpServletResponse response){
		if (StringUtils.isNotBlank(syscode)){
			UserUtils.putCache(UserUtils.CACHE_SYSCODE, syscode);
			// 清除用户菜单缓存
			UserUtils.removeCache(UserUtils.CACHE_MENU_LIST);
			UserUtils.removeCache(UserUtils.CACHE_MENU_ROLE_LIST);//配置菜单用
			// 清除日志相关缓存
			CacheUtils.remove(LogUtils.CACHE_MENU_NAME_PATH_MAP);
			// 清除权限数据缓存
			SpringContextHolder.getBean(SystemAuthorizingRealm.class).clearCachedAuthorizationInfo(UserUtils.getPrincipal());
			
			TProduct tp = tProductService.get(syscode);
			UserUtils.putCache(UserUtils.CACHE_SYSNAME, tp.getName());
		}else{
			syscode = (String) UserUtils.getCache(UserUtils.CACHE_SYSCODE);
		}
		return toSysIndex(request, response, null);
	}
	
	/**
	 * 是否是验证码登录
	 * @param useruame 用户名
	 * @param isFail 计数加1
	 * @param clean 计数清零
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static boolean isValidateCodeLogin(String useruame, boolean isFail, boolean clean){
		Map<String, Integer> loginFailMap = (Map<String, Integer>)CacheUtils.get("loginFailMap");
		if (loginFailMap==null){
			loginFailMap = Maps.newHashMap();
			CacheUtils.put("loginFailMap", loginFailMap);
		}
		Integer loginFailNum = loginFailMap.get(useruame);
		if (loginFailNum==null){
			loginFailNum = 0;
		}
		if (isFail){
			loginFailNum++;
			loginFailMap.put(useruame, loginFailNum);
		}
		if (clean){
			loginFailMap.remove(useruame);
		}
		return loginFailNum >= 3;
	}
	
	
	
	/**
	 * 管理退出
	 */
	@RequestMapping(value = "${adminPath}/syscode/logout")
	public String logout(HttpServletRequest request, HttpServletResponse response, Model model) {
		request.getSession().invalidate();
		return "modules/sys/sysLogin";
	}
	
	
	
}

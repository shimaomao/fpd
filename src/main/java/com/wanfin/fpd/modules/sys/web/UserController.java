/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.sys.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.codehaus.jettison.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mangofactory.swagger.annotations.ApiIgnore;
import com.wanfin.fpd.common.beanvalidator.BeanValidators;
import com.wanfin.fpd.common.config.Cons;
import com.wanfin.fpd.common.config.Global;
import com.wanfin.fpd.common.config.SvalBase;
import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.utils.DateUtils;
import com.wanfin.fpd.common.utils.SmsSendUtil;
import com.wanfin.fpd.common.utils.StringUtils;
import com.wanfin.fpd.common.utils.excel.ExportExcel;
import com.wanfin.fpd.common.utils.excel.ImportExcel;
import com.wanfin.fpd.common.web.BaseController;
import com.wanfin.fpd.modules.sys.dao.RoleDao;
import com.wanfin.fpd.modules.sys.entity.Office;
import com.wanfin.fpd.modules.sys.entity.Role;
import com.wanfin.fpd.modules.sys.entity.User;
import com.wanfin.fpd.modules.sys.service.OfficeService;
import com.wanfin.fpd.modules.sys.service.SystemService;
import com.wanfin.fpd.modules.sys.utils.UserUtils;

/**
 * 用户Controller
 * @author ThinkGem
 * @version 2013-8-29
 */
@ApiIgnore
@Controller
@RequestMapping(value = "${adminPath}/sys/user")
public class UserController extends BaseController {

	@Autowired
	private SystemService systemService;
	@Autowired
	private OfficeService officeService;
	@Autowired
	private RoleDao roleDao;
	
	@ModelAttribute
	public User get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return systemService.getUser(id);
		}else{
			return new User();
		}
	}

	@RequiresPermissions("sys:user:view")
	@RequestMapping(value = {"index"})
	public String index(User user, Model model) {
		return "modules/sys/userIndex";
	}

	@RequiresPermissions("sys:user:view")
	@RequestMapping(value = {"list", ""})
	public String list(User user, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<User> page = systemService.findUser(new Page<User>(request, response), user);
		List list=page.getList();
		for(int i=0;i<list.size();i++){//Task #3342 添加角色
			User u=(User) list.get(i);
			u.setRoleList(roleDao.findList(new Role(u)));
		}
        model.addAttribute("page", page);
		return "modules/sys/userList";
	}
	
	@ResponseBody
	@RequiresPermissions("sys:user:view")
	@RequestMapping(value = {"listData"})
	public Page<User> listData(User user, HttpServletRequest request, HttpServletResponse response, Model model) {
		return systemService.findUser(new Page<User>(request, response), user);
	}

	@RequiresPermissions("sys:user:view")
	@RequestMapping(value = "form")
	public String form(User user, Model model) {
		if (user.getCompany()==null || user.getCompany().getId()==null){
			user.setCompany(UserUtils.getUser().getCompany());
		}
		if (user.getOffice()==null || user.getOffice().getId()==null){
			user.setOffice(UserUtils.getUser().getOffice());
		}
        if (user.isAdmin()){
        	List<Role> rolelist = getAdminRoles(); //#2377 独立出去
    		model.addAttribute("allRoles", rolelist);
		}else{
			/**
			 * Start By Chenh 2016-08-24 Start 
			 * #2377 admin读取租户角色不合理
			 */
			List<Role> rolelist = new ArrayList<Role>();
			if(UserUtils.getUser().isAdmin()){
				rolelist = getAdminRoles();
			}else{
				//当前登录用户 所有机构的角色 
				List<Role> newrolelist = systemService.findAllRole();
				//判断 修改的用户是否是租户管理员
				boolean flag = UserUtils.get2(user.getId());
				for(Role curRole :newrolelist){
					if (flag) {
						if(((curRole.getId()).equals(Cons.SysDF.DF_ADMIN_ROLE_DB) || (curRole.getId()).equals(Cons.SysDF.DF_ADMIN_ROLE_XD))){
							rolelist.add(curRole);
						}
					} else {
						if(!((curRole.getId()).equals(Cons.SysDF.DF_ADMIN_ROLE_DB) || (curRole.getId()).equals(Cons.SysDF.DF_ADMIN_ROLE_XD))){
							rolelist.add(curRole);
						}
					}					
				}
			}
			model.addAttribute("allRoles", rolelist);
			//old
			//model.addAttribute("allRoles", systemService.findAllRole());
			/**
			 * end By Chenh 2016-08-24 Start 
			 */
		}
        model.addAttribute("user", user);
		return "modules/sys/userForm";
	}

	//#2377 独立出来
	private List<Role> getAdminRoles() {
		String parent_id = UserUtils.getUser().getCompany().getId();//当前系统管理员登录的公司主键id
		List<Role> rolelist = new ArrayList<Role>();
		List<Role> list = systemService.findAllRole();
		for (int i = 0; i < list.size(); i++) {
			Role ro = list.get(i);
			if(ro.getOffice().getParent().getId()!=null&&
				!"".equals(ro.getOffice().getParent().getId())
				&&parent_id.equals(ro.getOffice().getParent().getId())){
				rolelist.add(ro);
			}
		}
		return rolelist;
	}

	@RequiresPermissions("sys:user:edit")
	@RequestMapping(value = "save")
	public String save(User user,String sendSms, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
		if(Global.isDemoMode()){
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + adminPath + "/sys/user/list?repage";
		}
		// 修正引用赋值问题，不知道为何，Company和Office引用的一个实例地址，修改了一个，另外一个跟着修改。
		user.setCompany(new Office(request.getParameter("company.id")));
		user.setOffice(new Office(request.getParameter("office.id")));
		// 如果新密码为空，则不更换密码
		if (StringUtils.isNotBlank(user.getNewPassword())) {
			user.setPassword(SystemService.entryptPassword(user.getNewPassword()));
		}
		if (!beanValidator(model, user)){
			return form(user, model);
		}
		if (!"true".equals(checkLoginName(user.getOldLoginName(), user.getLoginName()))){
			addMessage(model, "保存用户'" + user.getLoginName() + "'失败，登录名已存在");
			return form(user, model);
		}
		// 角色数据有效性验证，过滤不在授权内的角色
		List<Role> roleList = Lists.newArrayList();
		List<String> roleIdList = user.getRoleIdList();
		//排序
		Collections.sort(roleIdList);
		for (Role r : systemService.findAllRole()){
			if (roleIdList.contains(r.getId())){
				roleList.add(r);
			}
		}
		user.setRoleList(roleList);
		boolean isNew = user.getIsNewRecord();
		// 保存用户信息
		systemService.saveUser(user);
		
		//短信通知 #2309
		if (StringUtils.isNotBlank(sendSms) && Global.YES.equals(sendSms) && StringUtils.isNotBlank(user.getMobile()) && StringUtils.isNotBlank(user.getNewPassword())){
			if(isNew){//新插入
				String msgContent = Cons.Msg.MSG_ADD_USER;//Global.getMessageConfig("message.smsregister");
				//System.out.println(msgContent);
				msgContent = msgContent.replace("#uname#", user.getLoginName());
				msgContent = msgContent.replace("#passwd#", user.getNewPassword());
				//msgContent = msgContent.replace("#email#", user.getEmail());
				//System.out.println(msgContent);
				try {
					SmsSendUtil.sendSmsBatch(msgContent, user.getMobile(),"3");
				} catch (IOException e) {
					e.printStackTrace();
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}else{//密码修改
				String msgContent = Cons.Msg.MSG_UPDATE_PWS;//Global.getMessageConfig("message.smsupdatekey");
				//System.out.println(msgContent);
				msgContent = msgContent.replace("#passwd#",user.getNewPassword());
				//System.out.println(msgContent);
				try {
					SmsSendUtil.sendSmsBatch(msgContent, user.getMobile(),"4");
				} catch (IOException e) {
					e.printStackTrace();
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}
		
		// 清除当前用户缓存
		/*if (user.getLoginName().equals(UserUtils.getUser().getLoginName())){
			UserUtils.clearCache();
			//UserUtils.getCacheMap().clear();
		}*/
		if(!user.getLoginFlag().equals(Global.YES)){
			//UserUtils.clearCache(user);
			//UserUtils.sessionList(user.getLoginName());
			UserUtils.kickOutUser(user.getLoginName());
			
		}
		addMessage(redirectAttributes, "保存用户'" + user.getLoginName() + "'成功");
		return "redirect:" + adminPath + "/sys/user/list?repage";
	}
	
	@RequiresPermissions("sys:user:edit")
	@RequestMapping(value = "delete")
	public String delete(User user, RedirectAttributes redirectAttributes) {
		if(Global.isDemoMode()){
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + adminPath + "/sys/user/list?repage";
		}
		if (UserUtils.getUser().getId().equals(user.getId())){
			addMessage(redirectAttributes, "删除用户失败, 不允许删除当前用户");
		}else if (User.isAdmin(user.getId())){
			addMessage(redirectAttributes, "删除用户失败, 不允许删除超级管理员用户");
		}else{
			systemService.deleteUser(user);
			addMessage(redirectAttributes, "删除用户成功");
		}
		return "redirect:" + adminPath + "/sys/user/list?repage";
	}
	
	/**
	 * 导出用户数据
	 * @param user
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("sys:user:view")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(User user, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "用户数据"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<User> page = systemService.findUser(new Page<User>(request, response, -1), user);
    		new ExportExcel("用户数据", User.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出用户失败！失败信息："+e.getMessage());
		}
		return "redirect:" + adminPath + "/sys/user/list?repage";
    }

	/**
	 * 导入用户数据
	 * @param file
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("sys:user:edit")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		if(Global.isDemoMode()){
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + adminPath + "/sys/user/list?repage";
		}
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<User> list = ei.getDataList(User.class);
			for (User user : list){
				try{
					if ("true".equals(checkLoginName("", user.getLoginName()))){
						user.setPassword(SystemService.entryptPassword("123456"));
						BeanValidators.validateWithException(validator, user);
						systemService.saveUser(user);
						successNum++;
					}else{
						failureMsg.append("<br/>登录名 "+user.getLoginName()+" 已存在; ");
						failureNum++;
					}
				}catch(ConstraintViolationException ex){
					failureMsg.append("<br/>登录名 "+user.getLoginName()+" 导入失败：");
					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList){
						failureMsg.append(message+"; ");
						failureNum++;
					}
				}catch (Exception ex) {
					failureMsg.append("<br/>登录名 "+user.getLoginName()+" 导入失败："+ex.getMessage());
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条用户，导入信息如下：");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条用户"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入用户失败！失败信息："+e.getMessage());
		}
		return "redirect:" + adminPath + "/sys/user/list?repage";
    }
	
	/**
	 * 下载导入用户数据模板
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("sys:user:view")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "用户数据导入模板.xlsx";
    		List<User> list = Lists.newArrayList(); list.add(UserUtils.getUser());
    		new ExportExcel("用户数据", User.class, 2).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:" + adminPath + "/sys/user/list?repage";
    }

	/**
	 * 验证登录名是否有效
	 * @param oldLoginName
	 * @param loginName
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("sys:user:edit")
	@RequestMapping(value = "checkLoginName")
	public String checkLoginName(String oldLoginName, String loginName) {
		if (loginName !=null && loginName.equals(oldLoginName)) {
			return "true";
		} else if (loginName !=null && systemService.getUserByLoginName(loginName) == null) {
			return "true";
		}
		return "false";
	}

	/**
	 * 用户信息显示及保存
	 * @param user
	 * @param model
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "info")
	public String info(User user, HttpServletResponse response, Model model) {
		User currentUser = UserUtils.getUser();
		if (StringUtils.isNotBlank(user.getName())){
			if(Global.isDemoMode()){
				model.addAttribute("message", "演示模式，不允许操作！");
				return "modules/sys/userInfo";
			}
			currentUser.setEmail(user.getEmail());
			currentUser.setPhone(user.getPhone());
			currentUser.setMobile(user.getMobile());
			currentUser.setRemarks(user.getRemarks());
			currentUser.setPhoto(user.getPhoto());
			systemService.updateUserInfo(currentUser);
			model.addAttribute("message", "保存用户信息成功");
		}
		model.addAttribute("user", currentUser);
		model.addAttribute("Global", new Global());
		return "modules/sys/userInfo";
	}

	/**
	 * 返回用户信息
	 * @return
	 */
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "infoData")
	public User infoData() {
		return UserUtils.getUser();
	}
	
	/**
	 * 修改个人用户密码
	 * @param oldPassword
	 * @param newPassword
	 * @param model
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "modifyPwd")
	public String modifyPwd(String oldPassword, String newPassword, Model model) {
		User user = UserUtils.getUser();
		if (StringUtils.isNotBlank(oldPassword) && StringUtils.isNotBlank(newPassword)){
			if(Global.isDemoMode()){
				model.addAttribute("message", "演示模式，不允许操作！");
				return "modules/sys/userModifyPwd";
			}
			if (SystemService.validatePassword(oldPassword, user.getPassword())){
				systemService.updatePasswordById(user.getId(), user.getLoginName(), newPassword);
				model.addAttribute("message", "修改密码成功");
			}else{
				model.addAttribute("message", "修改密码失败，旧密码错误");
			}
		}
		model.addAttribute("user", user);
		return "modules/sys/userModifyPwd";
	}
	
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String officeId, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		//List<User> list = systemService.findUserByOfficeId(officeId);
		List<User> list = systemService.findUserByOfficeId(UserUtils.getUser().getCompany().getId());
		for (int i=0; i<list.size(); i++){
			User e = list.get(i);
			Map<String, Object> map = Maps.newHashMap();
			map.put("id", "u_"+e.getId());
			map.put("pId", e.getOffice().getId());
			map.put("name", StringUtils.replace(e.getName(), " ", ""));
			mapList.add(map);
		}
		return mapList;
	}
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeDataByCompany")
	public List<Map<String, Object>> treeDataByCompany(HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<User> list = systemService.findUserByCompanyId(UserUtils.getUser().getCompany().getId());
		List<Office> offlist = officeService.findOfficeByCompanyId(UserUtils.getUser().getCompany().getId());
		for (int i=0; i<offlist.size(); i++){
			    Office o = offlist.get(i);
			    int num= systemService.findUserByOfficeId(o.getId()).size();
			    if(!o.getParentId().equals("7b506be60942497db0b02b97e70dd21c")){//过滤掉顶级公司名称
			    	if(num>0){//过滤掉用户为空的机构
			    		Map<String, Object> map = Maps.newHashMap();
						map.put("id", o.getId());
						map.put("pId", null);
						map.put("name", o.getName());
						mapList.add(map);
			    	}
			    }
			}
		for (int i=0; i<list.size(); i++){
			User e = list.get(i);
			Role role = new Role(e);
			List<Role> roles = systemService.findRole(role);
			e.setRoleList(roles);
			if(!e.isZadmin()){//过滤掉租户管理员
				Map<String, Object> map = Maps.newHashMap();
				map.put("id", "u_"+e.getLoginName());
				map.put("pId", e.getOffice().getId());
				map.put("name", StringUtils.replace(e.getName(), " ", ""));
				mapList.add(map);
			}
		}
		return mapList;
	}
	
	
	
	
//	@InitBinder
//	public void initBinder(WebDataBinder b) {
//		b.registerCustomEditor(List.class, "roleList", new PropertyEditorSupport(){
//			@Autowired
//			private SystemService systemService;
//			@Override
//			public void setAsText(String text) throws IllegalArgumentException {
//				String[] ids = StringUtils.split(text, ",");
//				List<Role> roles = new ArrayList<Role>();
//				for (String id : ids) {
//					Role role = systemService.getRole(Long.valueOf(id));
//					roles.add(role);
//				}
//				setValue(roles);
//			}
//			@Override
//			public String getAsText() {
//				return Collections3.extractToString((List) getValue(), "id", ",");
//			}
//		});
//	}
	
	/*初始化租户数据*/
	@RequiresPermissions("sys:user:view")
	@RequestMapping(value = {"initData"})
	public String initData(User user, HttpServletRequest request, HttpServletResponse response, Model model, RedirectAttributes redirectAttributes) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put(SvalBase.JsonKey.KEY_IS_TRUE, false);
		if((user != null) && StringUtils.isNotEmpty(user.getId())){
			user = systemService.getUser(user.getId());
			Role role = new Role(user);
			List<Role> roles = systemService.findRole(role);
			user.setRoleList(roles);
			if(user.isZadmin()){
				result.put("user", user);
				result.put("roles", roles);
				result.put(SvalBase.JsonKey.KEY_IS_TRUE, true);
				request.setAttribute(SvalBase.JsonKey.KEY_RESULT, result);
				return "modules/sys/userInitData";
			}else{
				result.put(SvalBase.JsonKey.KEY_MSG, "非超超级管理员，无数据初始化！");
			}
		}else{
			result.put(SvalBase.JsonKey.KEY_MSG, "用户未找到，无法初始化！");
		}
		request.setAttribute(SvalBase.JsonKey.KEY_RESULT, result);
		return "modules/sys/userInitFailure";
	}
	
	/**
	 * 检查loginName是否存在
	 * @param loginName
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getLoginCheck")
	public String getLoginCheck(String loginName, Model model, RedirectAttributes redirectAttributes) {
		User checkUser = systemService.getUserByLoginName(loginName);
		if(null==checkUser){
			return "1";
		}else{
			return "0";
		}
	}
}

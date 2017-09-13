/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.sys.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mangofactory.swagger.annotations.ApiIgnore;
import com.wanfin.fpd.common.config.Cons;
import com.wanfin.fpd.common.config.Global;
import com.wanfin.fpd.common.utils.StringUtils;
import com.wanfin.fpd.common.web.BaseController;
import com.wanfin.fpd.modules.auto.entity.TTenant;
import com.wanfin.fpd.modules.auto.service.AutoApiService;
import com.wanfin.fpd.modules.auto.service.TTenantService;
import com.wanfin.fpd.modules.sys.entity.Office;
import com.wanfin.fpd.modules.sys.entity.SysOfficeDetail;
import com.wanfin.fpd.modules.sys.entity.User;
import com.wanfin.fpd.modules.sys.service.OfficeService;
import com.wanfin.fpd.modules.sys.service.SysOfficeDetailService;
import com.wanfin.fpd.modules.sys.utils.DictUtils;
import com.wanfin.fpd.modules.sys.utils.UserUtils;

/**
 * 机构Controller
 * @author ThinkGem
 * @version 2013-5-15
 */
@ApiIgnore
@Controller
@RequestMapping(value = "${adminPath}/sys/office")
public class OfficeController extends BaseController {

	@Autowired
	private OfficeService officeService;
	@Autowired
	private AutoApiService autoApiService;
	@Autowired
	private TTenantService tenantService;
	@Autowired
	private SysOfficeDetailService sysOfficeDetailService;
	
	@ModelAttribute("office")
	public Office get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return officeService.get(id);
		}else{
			return new Office();
		}
	}

	@RequiresPermissions("sys:office:view")
	@RequestMapping(value = {""})
	public String index(Office office, Model model) {
//        model.addAttribute("list", officeService.findAll());
		return "modules/sys/officeIndex";
	}

	@RequiresPermissions("sys:office:view")
	@RequestMapping(value = {"list"})
	public String list(Office office, Model model) {
		User curUser = UserUtils.getUser();
		Office curoffice = curUser.getCompany();
		if(StringUtils.isNoneBlank(office.getId())){
			SysOfficeDetail sysOfficeDetail = sysOfficeDetailService.getByPid(office.getId());
			model.addAttribute("sysOfficeDetail", sysOfficeDetail);
		}else{
			model.addAttribute("sysOfficeDetail", new SysOfficeDetail());
		}
		model.addAttribute("office",office);
		model.addAttribute("curUser", curUser);
		model.addAttribute("curoffice", curoffice);
        model.addAttribute("list", officeService.findList(office));
		return "modules/sys/officeList";
	}
	
	@RequiresPermissions("sys:office:view")
	@RequestMapping(value = "form")
	public String form(Office office, Model model) {
		User user = UserUtils.getUser();
		if (office.getParent()==null || office.getParent().getId()==null){
			office.setParent(user.getOffice());
		}
		office.setParent(officeService.get(office.getParent().getId()));
		if (office.getArea()==null){
			office.setArea(user.getOffice().getArea());
		}
		// 自动获取排序号
		if (StringUtils.isBlank(office.getId())&&office.getParent()!=null){
			int size = 0;
			List<Office> list = officeService.findAll();
			for (int i=0; i<list.size(); i++){
				Office e = list.get(i);
				if (e.getParent()!=null && e.getParent().getId()!=null
						&& e.getParent().getId().equals(office.getParent().getId())){
					size++;
				}
			}
			office.setCode(office.getParent().getCode() + StringUtils.leftPad(String.valueOf(size > 0 ? size+1 : 1), 3, "0"));
		}
		SysOfficeDetail sysOfficeDetail = sysOfficeDetailService.getByPid(user.getCompany().getId());
		model.addAttribute("sysOfficeDetail", sysOfficeDetail);
		model.addAttribute("office", office);
		model.addAttribute("user", user);
		model.addAttribute("curUser", user);
		return "modules/sys/officeForm";
	}
	
	@RequiresPermissions("sys:office:edit")
	@RequestMapping(value = "save")
	public String save(Office office, Model model, RedirectAttributes redirectAttributes,HttpServletResponse resp,HttpServletRequest req) {
		if(Global.isDemoMode()){
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + adminPath + "/sys/office/";
		}
		if (!beanValidator(model, office)){
			return form(office, model);
		}
		//3520
		if(office.getType().contains("2")||office.getType().contains("3")){
			Office parentOffice = officeService.get(office.getParentId());
			office.setPrimaryPerson(parentOffice.getPrimaryPerson());
		}
		boolean isNew = office.getIsNewRecord();
		officeService.save(office);
		if(office.getChildDeptList()!=null){
			Office childOffice = null;
			for(String id : office.getChildDeptList()){
				childOffice = new Office();
				childOffice.setName(DictUtils.getDictLabel(id, Cons.SysDF.SYS_OFFICE_COMMON, "未知"));
				childOffice.setParent(office);
				childOffice.setPrimaryPerson(office.getPrimaryPerson());
				childOffice.setArea(office.getArea());
				childOffice.setType(Cons.SysDF.DF_SUPER_OFFICE_TYPEDEPART);
				childOffice.setUseable(Global.YES);
				officeService.save(childOffice);
			}
		}
		
		/**
		 * 判断是否为新增！
		 * 		如果不为新增：不执行初始化操作
		 *  	如果为新增：执行初始化操作
		 *  		判断机构下是否存在综合部！
		 *  			如果有综合部：直接开始初始化机构的角色
		 *  			如果没有综合部：直接初始化机构的部门
		 */		
		if(isNew){
			/**公司*/
			if((Cons.SysDF.DF_SUPER_OFFICE_TYPEORG).equals(office.getType())){
				if((autoApiService.checkHasZHB(office) == null)){
					Map<String,String> userMap = new HashMap<String,String>();
					userMap.put("userName", req.getParameter("userName"));
					userMap.put("userLoginName", req.getParameter("userLoginName"));
					userMap.put("userPassword", req.getParameter("userPassword"));
					office.setUserMap(userMap);
					autoApiService.init(office);
				}else{
					autoApiService.init(office, true);
				}
			}
		}
		
		addMessage(redirectAttributes, "保存机构'" + office.getName() + "'成功");
		String id = "0".equals(office.getParentId()) ? "" : office.getParentId();
		if(UserUtils.getUser().isAdmin()){
			return "redirect:" + adminPath + "/sys/office/list?id="+id+"&parentIds="+office.getParentIds();
		}else{
			return "redirect:" + adminPath + "/sys/sysOfficeDetail/formForDetail?id="+office.getId();
		}
	}
	
	/**
	* @Description: 初始化青海租户数据 
	* 	机构和用户数据取值TTenant表
	* 	部门、角色、表单模板默认（使用auto文件夹的Json定义）
	* @author Chenh
	* @return  
	* @throws
	 */
	@ResponseBody
	@RequestMapping(value = "ajaxInitQH")
	public Boolean ajaxInitQH(String type, Model model) {
		TTenant tenant = new TTenant();
		if(StringUtils.isEmpty(type)){
			type = "1";
		}
		tenant.setSimpleName(type);
		List<TTenant> tenants = tenantService.findList(tenant);
		return autoApiService.initQHCompanys(tenants, null, null, null);
	}
	
	@RequiresPermissions("sys:office:edit")
	@RequestMapping(value = "delete")
	public String delete(Office office, RedirectAttributes redirectAttributes) {
		if(Global.isDemoMode()){
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + adminPath + "/sys/office/list";
		}
//		if (Office.isRoot(id)){
//			addMessage(redirectAttributes, "删除机构失败, 不允许删除顶级机构或编号空");
//		}else{
			officeService.delete(office);
			addMessage(redirectAttributes, "删除机构成功");
//		}
		return "redirect:" + adminPath + "/sys/office/list?id="+office.getParentId()+"&parentIds="+office.getParentIds();
	}

	/**
	 * 获取机构JSON数据。
	 * @param extId 排除的ID
	 * @param type	类型（1：公司；2：部门/小组/其它：3：用户）
	 * @param grade 显示级别
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String extId, @RequestParam(required=false) String type,
			@RequestParam(required=false) Long grade, @RequestParam(required=false) Boolean isAll, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<Office> list = officeService.findList(isAll);
		for (int i=0; i<list.size(); i++){
			Office e = list.get(i);
			if ((StringUtils.isBlank(extId) || (extId!=null && !extId.equals(e.getId()) && e.getParentIds().indexOf(","+extId+",")==-1))
					&& (type == null || (type != null && (type.equals("1") ? type.equals(e.getType()) : true)))
					&& (grade == null || (grade != null && Integer.parseInt(e.getGrade()) <= grade.intValue()))
					&& Global.YES.equals(e.getUseable())){
				Map<String, Object> map = Maps.newHashMap();
				map.put("id", e.getId());
				map.put("pId", e.getParentId());
				map.put("pIds", e.getParentIds());
				map.put("name", e.getName());
				if (type != null && "3".equals(type)){
					map.put("isParent", true);
				}
				mapList.add(map);
			}
		}
		return mapList;
	}
	
	
	/**
	 **获取机构级别
	 */
	@ResponseBody
	@RequestMapping(value = "getOfficeType")
	public String getOfficeType(String id, Model model, RedirectAttributes redirectAttributes) {
		return get(id).getType();
	}

}

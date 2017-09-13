/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.sys.web;

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
import com.wanfin.fpd.common.config.Global;
import com.wanfin.fpd.common.utils.StringUtils;
import com.wanfin.fpd.common.web.BaseController;
import com.wanfin.fpd.modules.product.entity.TProduct;
import com.wanfin.fpd.modules.sys.entity.Menu;
import com.wanfin.fpd.modules.sys.service.SystemService;
import com.wanfin.fpd.modules.sys.utils.UserUtils;

/**
 * 菜单Controller
 * @author ThinkGem
 * @version 2013-3-23
 */
@ApiIgnore
@Controller
@RequestMapping(value = "${adminPath}/sys/menu")
public class MenuController extends BaseController {

	@Autowired
	private SystemService systemService;
	
	@ModelAttribute("menu")
	public Menu get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return systemService.getMenu(id);
		}else{
			return new Menu();
		}
	}

	@RequiresPermissions("sys:menu:view")
	@RequestMapping(value = {"list", ""})
	public String list(Model model) {
		List<Menu> list = Lists.newArrayList();
		List<Menu> sourcelist = systemService.findAllMenu();
		Menu.sortList(list, sourcelist, Menu.getRootId(), true);
        model.addAttribute("list", list);
		return "modules/sys/menuList";
	}

	@RequiresPermissions("sys:menu:view")
	@RequestMapping(value = "form")
	public String form(HttpServletRequest request, HttpServletResponse response, Menu menu, Model model, String ftype, String relId) {
		toForm(menu, model);
		request.setAttribute("ftype", ftype);
		request.setAttribute("relId", relId);
		return checkModelType(ftype, new TProduct(relId), "modules/sys/menuForm");
	}

	private void toForm(Menu menu, Model model) {
		if (menu.getParent()==null||menu.getParent().getId()==null){
			menu.setParent(new Menu(Menu.getRootId()));
		}
		menu.setParent(systemService.getMenu(menu.getParent().getId()));
		// 获取排序号，最末节点排序号+30
		if (StringUtils.isBlank(menu.getId())){
			List<Menu> list = Lists.newArrayList();
			List<Menu> sourcelist = systemService.findAllMenu();
			Menu.sortList(list, sourcelist, menu.getParentId(), false);
			if (list.size() > 0){
				menu.setSort(list.get(list.size()-1).getSort() + 30);
			}
		}
		model.addAttribute("menu", menu);
	}
	
	@RequiresPermissions("sys:menu:edit")
	@RequestMapping(value = "save")
	public String save(HttpServletRequest request, HttpServletResponse response, Menu menu, Model model, RedirectAttributes redirectAttributes, String modeUrl) {
		if(!UserUtils.getUser().isAdmin() && !UserUtils.getUser().getUserType().equals("1")){
			addMessage(redirectAttributes, "越权操作，只有超级管理员或租户管理员才能添加或修改数据！");
			return "redirect:" + adminPath + "/sys/role/?repage";
		}
		if(Global.isDemoMode()){
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + adminPath + "/sys/menu/";
		}
		if (!beanValidator(model, menu)){
			return form(request, response, menu, model, null, null);
		}
		systemService.saveMenu(menu);
		addMessage(redirectAttributes, "保存菜单'" + menu.getName() + "'成功");
		if(StringUtils.isNotEmpty(modeUrl)){
			request.setAttribute("isClose", 1);
			request.setAttribute("menu", new Menu());
			return "modules/product/productConfigCDPZForm";
		}
		return "redirect:" + adminPath + "/sys/menu/";
	}
	
	@RequiresPermissions("sys:menu:edit")
	@RequestMapping(value = "delete")
	public String delete(Menu menu, RedirectAttributes redirectAttributes) {
		if(Global.isDemoMode()){
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + adminPath + "/sys/menu/";
		}
//		if (Menu.isRoot(id)){
//			addMessage(redirectAttributes, "删除菜单失败, 不允许删除顶级菜单或编号为空");
//		}else{
			systemService.deleteMenu(menu);
			addMessage(redirectAttributes, "删除菜单成功");
//		}
		return "redirect:" + adminPath + "/sys/menu/";
	}

	@RequiresPermissions("user")
	@RequestMapping(value = "tree")
	public String tree() {
		return "modules/sys/menuTree";
	}

	@RequiresPermissions("user")
	@RequestMapping(value = "treeselect")
	public String treeselect(String parentId, Model model) {
		model.addAttribute("parentId", parentId);
		return "modules/sys/menuTreeselect";
	}
	
	/**
	 * 批量修改菜单排序
	 */
	@RequiresPermissions("sys:menu:edit")
	@RequestMapping(value = "updateSort")
	public String updateSort(String[] ids, Integer[] sorts, RedirectAttributes redirectAttributes, String modelType, String ftype, String relId) {
		if(StringUtils.isNotEmpty(ftype) && StringUtils.isNotEmpty(relId)){
			toUpdateSort(ids, sorts, redirectAttributes);
			addMessage(redirectAttributes, "保存菜单排序成功!");
			if(("product").equals(ftype)){
//				return "redirect:"+Global.getAdminPath()+"/product/tProduct/productConfigCDPZ?id="+relId;
				return checkModelType(modelType, new TProduct(relId), "redirect:" + adminPath + "/sys/menu/");
			}else{
				return "redirect:" + adminPath + "/sys/menu/";
			}
		}else{
			if(Global.isDemoMode()){
				addMessage(redirectAttributes, "演示模式，不允许操作！");
				return "redirect:" + adminPath + "/sys/menu/";
			}
			toUpdateSort(ids, sorts, redirectAttributes);
			addMessage(redirectAttributes, "保存菜单排序成功!");
			return "redirect:" + adminPath + "/sys/menu/";
		}
	}
	
	/**
	 * @Description 根据modelType跳转新页面
	 * @param modelType
	 * @return
	 * @author Chenh 
	 * @date 2016年5月9日 下午2:42:40
	 */
	private String checkModelType(String modelType, TProduct tProduct, String dfUrl) {
		if (StringUtils.isNotEmpty(modelType)) {
			if(("product").equals(modelType)){
				if((modelType).equals("step1")){
					return "redirect:"+Global.getAdminPath()+"/product/tProduct/productConfigIndex?id="+tProduct.getId();
				}else if((modelType).equals("step2")){
					return "redirect:"+Global.getAdminPath()+"/product/tProduct/productConfigYWPZ?id="+tProduct.getId();
				}else if((modelType).equals("step3")){
					return "redirect:"+Global.getAdminPath()+"/product/tProduct/productConfigYWLC?id="+tProduct.getId();
				}else if((modelType).equals("step4")){
					return "redirect:"+Global.getAdminPath()+"/product/tProduct/productConfigQXPZ?id="+tProduct.getId();
				}else if((modelType).equals("step5")){
					return "redirect:"+Global.getAdminPath()+"/product/tProduct/productConfigCDPZ?id="+tProduct.getId();
				}else if((modelType).equals("step6")){
					return "redirect:"+Global.getAdminPath()+"/product/tProduct/productConfigFKPZ?id="+tProduct.getId();
				}else if((modelType).equals("step7")){
					return "redirect:"+Global.getAdminPath()+"/product/tProduct/productConfigSPPZ?id="+tProduct.getId();
				}else{
					return "modules/product/productConfigCDPZForm";
				}
			}
		}
		return dfUrl;
	}
	
	
	private void toUpdateSort(String[] ids, Integer[] sorts, RedirectAttributes redirectAttributes) {
		for (int i = 0; i < ids.length; i++) {
			Menu menu = new Menu(ids[i]);
			menu.setSort(sorts[i]);
			systemService.updateMenuSort(menu);
		}
	}
	
	/**
	 * isShowHide是否显示隐藏菜单
	 * @param extId
	 * @param isShowHidden
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String extId,@RequestParam(required=false) String isShowHide, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<Menu> list = systemService.findAllMenu();
		for (int i=0; i<list.size(); i++){
			Menu e = list.get(i);
			if (StringUtils.isBlank(extId) || (extId!=null && !extId.equals(e.getId()) && e.getParentIds().indexOf(","+extId+",")==-1)){
				if(isShowHide != null && isShowHide.equals("0") && e.getIsShow().equals("0")){
					continue;
				}
				Map<String, Object> map = Maps.newHashMap();
				map.put("id", e.getId());
				map.put("pId", e.getParentId());
				map.put("name", e.getName());
				mapList.add(map);
			}
		}
		return mapList;
	}
}

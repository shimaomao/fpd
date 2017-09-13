/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.productbiz.web;

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
import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.utils.StringUtils;
import com.wanfin.fpd.common.web.BaseController;
import com.wanfin.fpd.modules.form.builder.web.BuilderController;
import com.wanfin.fpd.modules.productbiz.entity.TProductBiz;
import com.wanfin.fpd.modules.productbiz.service.TProductBizService;
import com.wanfin.fpd.modules.sys.service.OfficeService;
import com.wanfin.fpd.modules.sys.utils.UserUtils;

/**
 * 业务功能Controller
 * @author lx
 * @version 2016-03-10
 */
@ApiIgnore
@Controller
@RequestMapping(value = "${adminPath}/productbiz/tProductBiz")
public class TProductBizController extends BaseController {

	String urltype = "tree";
	
	@Autowired
	private TProductBizService tProductBizService;
	
	
	@Autowired
	private OfficeService officeService;
	
	@ModelAttribute
	public TProductBiz get(@RequestParam(required=false) String id) {
		TProductBiz entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = tProductBizService.get(id);
		}
		if (entity == null){
			entity = new TProductBiz();
		}
		return entity;
	}
	
	@RequiresPermissions("productbiz:tProductBiz:view")
	@RequestMapping(value = {"list", ""})
	public String list(TProductBiz tProductBiz, HttpServletRequest request, HttpServletResponse response, Model model) {
		if(urltype.equals("tree")){
			return tree(tProductBiz, request, response, model);
		}else{
			Page<TProductBiz> page = tProductBizService.findPage(new Page<TProductBiz>(request, response), tProductBiz); 
	
			model.addAttribute("page", page);
			model.addAttribute("tProductBiz", tProductBiz);
		
			return "modules/productbiz/tProductBizList";
		}
	}
	
	@RequiresPermissions("productbiz:tProductBiz:view")
	@RequestMapping(value = {"tree", ""})
	public String tree(TProductBiz tProductBiz, HttpServletRequest request, HttpServletResponse response, Model model) {
		List<TProductBiz> sourcelist = tProductBizService.findList(tProductBiz); 
		
		List<TProductBiz> list = Lists.newArrayList();
		TProductBiz.sortList(list, sourcelist, TProductBiz.getRootId(), true,"0");
		model.addAttribute("list", list);
		model.addAttribute("tProductBiz", tProductBiz);
		return "modules/productbiz/tProductBizTree";
	}

	@RequiresPermissions("productbiz:tProductBiz:view")
	@RequestMapping(value = "form")
	public String form(TProductBiz tProductBiz, Model model, String parentId) {
		if(StringUtils.isNotEmpty(parentId)){
			TProductBiz parent = tProductBizService.get(parentId);
			tProductBiz.setParent(parent);
			tProductBiz.setBizCode(parent.getBizCode());
			tProductBiz.setType(parent.getType());
			tProductBiz.setIsShow(parent.getIsShow());
		}
		initForm(tProductBiz);
		tProductBiz.setMenuIdList(tProductBizService.getMenuIdsListByBizId(tProductBiz));
		model.addAttribute("tProductBiz", tProductBiz);
		model.addAttribute("categoryList",BuilderController.getCategoryList());
		model.addAttribute("menuList", UserUtils.getBusinessMenuList());
		return "modules/productbiz/tProductBizForm";
	}

	private void initForm(TProductBiz tProductBiz) {
		if(tProductBiz.getParent() == null){
			tProductBiz.setParent(new TProductBiz(TProductBiz.getRootId()));
		}
		if(StringUtils.isEmpty(tProductBiz.getParent().getId())){
			tProductBiz.getParent().setId(TProductBiz.getRootId());
		}
		if(StringUtils.isEmpty(tProductBiz.getBizCode())){
			tProductBiz.setBizCode("tpl");
		}
		if(StringUtils.isEmpty(tProductBiz.getType())){
			tProductBiz.setType("1");
		}
		if(StringUtils.isEmpty(tProductBiz.getStatus())){
			tProductBiz.setStatus("0");
		}
		if(StringUtils.isEmpty(tProductBiz.getIsShow())){
			tProductBiz.setIsShow("1");
		}
		if(StringUtils.isEmpty(tProductBiz.getIsFlow())){
			tProductBiz.setIsFlow("0");
		}
	}

	@RequiresPermissions("productbiz:tProductBiz:edit")
	@RequestMapping(value = "save")
	public String save(TProductBiz tProductBiz, Model model, HttpServletRequest request, RedirectAttributes redirectAttributes) {
		if(!UserUtils.getUser().isAdmin() && !UserUtils.getUser().getUserType().equals("1")){
			addMessage(redirectAttributes, "越权操作，只有超级管理员或租户管理员才能添加或修改数据！");
			return "redirect:"+Global.getAdminPath()+"/productbiz/tProductBiz/tree?repage";
		}
		if(Global.isDemoMode()){
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:"+Global.getAdminPath()+"/productbiz/tProductBiz/list?repage";
		}
		if (!beanValidator(model, tProductBiz)){
			return form(tProductBiz, model, null);
		}
		if((tProductBiz.getParent() == null)){
			tProductBiz.setParent(tProductBizService.get(TProductBiz.getRootId()));
		}
		if((StringUtils.isEmpty(tProductBiz.getParent().getId()))){
			tProductBiz.getParent().setId(TProductBiz.getRootId());;
		}
		tProductBizService.saveTProductBiz(tProductBiz);
		return "redirect:"+Global.getAdminPath()+"/productbiz/tProductBiz/tree?repage";
	}
	
	@RequiresPermissions("productbiz:tProductBiz:edit")
	@RequestMapping(value = "delete")
	public String delete(TProductBiz tProductBiz, RedirectAttributes redirectAttributes) {
		tProductBizService.delete(tProductBiz);
		addMessage(redirectAttributes, "删除业务功能成功");
		return "redirect:"+Global.getAdminPath()+"/productbiz/tProductBiz/tree?repage";
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
		List<TProductBiz> list = tProductBizService.findAllList(new TProductBiz());
		for (int i=0; i<list.size(); i++){
			TProductBiz e = list.get(i);
			if (StringUtils.isBlank(extId) || (extId!=null && !extId.equals(e.getId()) && e.getParentIds().indexOf(","+extId+",")==-1)){
				if(isShowHide != null && isShowHide.equals("0") && e.getIsShow().equals("0")){
					continue;
				}
				Map<String, Object> map = Maps.newHashMap();
				map.put("id", e.getId());
				map.put("pId", e.getParentId());
				map.put("name", e.getBizName());
				mapList.add(map);
			}
		}
		return mapList;
	}
	
	/**
	 * 批量修改菜单排序
	 */
	@RequiresPermissions("productbiz:tProductBiz:edit")
	@RequestMapping(value = "updateSort")
	public String updateSort(String[] ids, String[] sorts, RedirectAttributes redirectAttributes) {
		if(Global.isDemoMode()){
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + adminPath + "/productbiz/tProductBiz/tree";
		}
		toUpdateSort(ids, sorts, redirectAttributes);
		addMessage(redirectAttributes, "保存排序成功!");
		return "redirect:" + adminPath + "/productbiz/tProductBiz/tree";
	}
	private void toUpdateSort(String[] ids, String[] sorts, RedirectAttributes redirectAttributes) {
		for (int i = 0; i < ids.length; i++) {
			TProductBiz tProductBiz = new TProductBiz(ids[i]);
			tProductBiz.setSort(sorts[i]);
			tProductBizService.updateSort(tProductBiz);
		}
	}
}
/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.collateral.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mangofactory.swagger.annotations.ApiIgnore;
import com.wanfin.fpd.common.config.Global;
import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.utils.StringUtils;
import com.wanfin.fpd.common.web.BaseController;
import com.wanfin.fpd.modules.collateral.entity.Building;
import com.wanfin.fpd.modules.collateral.service.BuildingService;
import com.wanfin.fpd.modules.mortgage.entity.MortgageContract;

/**
 * 商铺写字楼Controller
 * @author srf
 * @version 2016-03-24
 */
@ApiIgnore
@Controller
@RequestMapping(value = "${adminPath}/collateral/building")
public class BuildingController extends BaseController {

	@Autowired
	private BuildingService buildingService;
	
	@ModelAttribute
	public Building get(@RequestParam(required=false) String id) {
		Building entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = buildingService.get(id);
		}
		if (entity == null){
			entity = new Building();
		}
		return entity;
	}
	
//	@ModelAttribute
//	public Building getBycollateral(@RequestParam(required=false) String id) {
//		Building entity = null;
//		if (StringUtils.isNotBlank(id)){
//			entity = buildingService.getBycollateral(id);
//		}
//		if (entity == null){
//			entity = new Building();
//		}
//		return entity;
//	}
	
	@RequiresPermissions("collateral:building:view")
	@RequestMapping(value = {"list", ""})
	public String list(Building building, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Building> page = buildingService.findPage(new Page<Building>(request, response), building); 
		model.addAttribute("page", page);
		model.addAttribute("building", building);
		return "modules/collateral/buildingList";
	}

	@RequiresPermissions("collateral:building:view")
	@RequestMapping(value = "form")
	public String form(Building building, Model model) {
		model.addAttribute("building", building);
		return "modules/collateral/buildingForm";
	}
	
	//add
	@RequestMapping(value = "add")
	public String add(@RequestParam(required=false) String mortgageid, Building building, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request ) {
		Building entity = null;
		if (StringUtils.isNotBlank(mortgageid)){
			entity = buildingService.getByPledge(mortgageid);
		}
		
		//从会话获取 如果 没有保存到数据库
		if (entity == null && StringUtils.isNotBlank(mortgageid)){
			HttpSession session = request.getSession();		
			List<MortgageContract> mortgageContractList = (List<MortgageContract>)session.getAttribute("mortgageContractList");	
		    if (mortgageContractList != null &&  mortgageContractList.size() > 0) {
		    	for(int i = 0; i < mortgageContractList.size(); i++){
					MortgageContract temp = mortgageContractList.get(i);
					if(mortgageid.equals(temp.getId())){		
						entity = temp.getBuilding();
					}
				}	
		    }	
		}
				
		if (entity != null){
			model.addAttribute("building", entity);
		}
		
		return "modules/collateral/buildingAdd";
	}

	@RequiresPermissions("collateral:building:edit")
	@RequestMapping(value = "save")
	public String save(Building building, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, building)){
			return form(building, model);
		}
		buildingService.save(building);
		addMessage(redirectAttributes, "保存抵押物-商铺写字楼成功");
		return "redirect:"+Global.getAdminPath()+"/collateral/building/?repage";
	}
	
	@RequiresPermissions("collateral:building:edit")
	@RequestMapping(value = "delete")
	public String delete(Building building, RedirectAttributes redirectAttributes) {
		buildingService.delete(building);
		addMessage(redirectAttributes, "删除抵押物-商铺写字楼成功");
		return "redirect:"+Global.getAdminPath()+"/collateral/building/?repage";
	}

}
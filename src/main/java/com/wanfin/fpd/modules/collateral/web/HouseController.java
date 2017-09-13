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
import com.wanfin.fpd.modules.collateral.entity.House;
import com.wanfin.fpd.modules.collateral.service.HouseService;
import com.wanfin.fpd.modules.mortgage.entity.MortgageContract;

/**
 * 住宅Controller
 * @author srf
 * @version 2016-03-24
 */
@ApiIgnore
@Controller
@RequestMapping(value = "${adminPath}/collateral/house")
public class HouseController extends BaseController {

	@Autowired
	private HouseService houseService;
	
	@ModelAttribute
	public House get(@RequestParam(required=false) String id) {
		House entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = houseService.get(id);
		}
		if (entity == null){
			entity = new House();
		}
		return entity;
	}
	
	@RequiresPermissions("collateral:house:view")
	@RequestMapping(value = {"list", ""})
	public String list(House house, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<House> page = houseService.findPage(new Page<House>(request, response), house); 
		model.addAttribute("page", page);
		model.addAttribute("house", house);
		return "modules/collateral/houseList";
	}

	@RequiresPermissions("collateral:house:view")
	@RequestMapping(value = "form")
	public String form(House house, Model model) {
		model.addAttribute("house", house);
		return "modules/collateral/houseForm";
	}

	@RequestMapping(value = "add")
	public String add(@RequestParam(required=false) String mortgageid, House house, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request ) {
		House entity = null;
		if (StringUtils.isNotBlank(mortgageid)){
			entity = houseService.getByPledge(mortgageid);
		}
		
		//从会话获取 如果 没有保存到数据库
		if (entity == null && StringUtils.isNotBlank(mortgageid)){
			HttpSession session = request.getSession();		
			List<MortgageContract> mortgageContractList = (List<MortgageContract>)session.getAttribute("mortgageContractList");	
		    if (mortgageContractList != null &&  mortgageContractList.size() > 0) {
		    	for(int i = 0; i < mortgageContractList.size(); i++){
					MortgageContract temp = mortgageContractList.get(i);
					if(mortgageid.equals(temp.getId())){		
						entity = temp.getHouse();
					}
				}	
		    }	
		}
				
		if (entity != null){
			model.addAttribute("house", entity);
		}
		
		return "modules/collateral/houseAdd";
	}
	
	@RequiresPermissions("collateral:house:edit")
	@RequestMapping(value = "save")
	public String save(House house, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, house)){
			return form(house, model);
		}
		houseService.save(house);
		addMessage(redirectAttributes, "保存抵押物-住宅成功");
		return "redirect:"+Global.getAdminPath()+"/collateral/house/?repage";
	}
	
	@RequiresPermissions("collateral:house:edit")
	@RequestMapping(value = "delete")
	public String delete(House house, RedirectAttributes redirectAttributes) {
		houseService.delete(house);
		addMessage(redirectAttributes, "删除抵押物-住宅成功");
		return "redirect:"+Global.getAdminPath()+"/collateral/house/?repage";
	}

}
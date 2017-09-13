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
import com.wanfin.fpd.modules.collateral.entity.TerraceVilla;
import com.wanfin.fpd.modules.collateral.service.TerraceVillaService;
import com.wanfin.fpd.modules.mortgage.entity.MortgageContract;

/**
 * 联排别墅Controller
 * @author srf
 * @version 2016-03-24
 */
@ApiIgnore
@Controller
@RequestMapping(value = "${adminPath}/collateral/terraceVilla")
public class TerraceVillaController extends BaseController {

	@Autowired
	private TerraceVillaService terraceVillaService;
	
	@ModelAttribute
	public TerraceVilla get(@RequestParam(required=false) String id) {
		TerraceVilla entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = terraceVillaService.get(id);
		}
		if (entity == null){
			entity = new TerraceVilla();
		}
		return entity;
	}
	
	@RequiresPermissions("collateral:terraceVilla:view")
	@RequestMapping(value = {"list", ""})
	public String list(TerraceVilla terraceVilla, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<TerraceVilla> page = terraceVillaService.findPage(new Page<TerraceVilla>(request, response), terraceVilla); 
		model.addAttribute("page", page);
		model.addAttribute("terraceVilla", terraceVilla);
		return "modules/collateral/terraceVillaList";
	}

	@RequiresPermissions("collateral:terraceVilla:view")
	@RequestMapping(value = "form")
	public String form(TerraceVilla terraceVilla, Model model) {
		model.addAttribute("terraceVilla", terraceVilla);
		return "modules/collateral/terraceVillaForm";
	}

	@RequestMapping(value = "add")
	public String add(@RequestParam(required=false) String mortgageid, TerraceVilla terraceVilla, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request ) {
		TerraceVilla entity = null;
		if (StringUtils.isNotBlank(mortgageid)){
			entity = terraceVillaService.getByPledge(mortgageid);
		}
		//从会话获取 如果 没有保存到数据库
		if (entity == null && StringUtils.isNotBlank(mortgageid)){
			HttpSession session = request.getSession();		
			List<MortgageContract> mortgageContractList = (List<MortgageContract>)session.getAttribute("mortgageContractList");	
		    if (mortgageContractList != null &&  mortgageContractList.size() > 0) {
		    	for(int i = 0; i < mortgageContractList.size(); i++){
					MortgageContract temp = mortgageContractList.get(i);
					if(mortgageid.equals(temp.getId())){		
						entity = temp.getTerraceVilla();
					}
				}	
		    }	
		}
		if (entity != null){
			model.addAttribute("terraceVilla", entity);
		}
		
		return "modules/collateral/terraceVillaAdd";
	}
	
	@RequiresPermissions("collateral:terraceVilla:edit")
	@RequestMapping(value = "save")
	public String save(TerraceVilla terraceVilla, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, terraceVilla)){
			return form(terraceVilla, model);
		}
		terraceVillaService.save(terraceVilla);
		addMessage(redirectAttributes, "保存抵押联排别墅成功");
		return "redirect:"+Global.getAdminPath()+"/collateral/terraceVilla/?repage";
	}
	
	@RequiresPermissions("collateral:terraceVilla:edit")
	@RequestMapping(value = "delete")
	public String delete(TerraceVilla terraceVilla, RedirectAttributes redirectAttributes) {
		terraceVillaService.delete(terraceVilla);
		addMessage(redirectAttributes, "删除抵押联排别墅成功");
		return "redirect:"+Global.getAdminPath()+"/collateral/terraceVilla/?repage";
	}

}
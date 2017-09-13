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
import com.wanfin.fpd.modules.collateral.entity.GongLand;
import com.wanfin.fpd.modules.collateral.service.GongLandService;
import com.wanfin.fpd.modules.mortgage.entity.MortgageContract;

/**
 * 工业用地信息Controller
 * @author srf
 * @version 2016-03-24
 */
@ApiIgnore
@Controller
@RequestMapping(value = "${adminPath}/collateral/gongLand")
public class GongLandController extends BaseController {

	@Autowired
	private GongLandService gongLandService;
	
	@ModelAttribute
	public GongLand get(@RequestParam(required=false) String id) {
		GongLand entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = gongLandService.get(id);
		}
		if (entity == null){
			entity = new GongLand();
		}
		return entity;
	}
	
	@RequiresPermissions("collateral:gongLand:view")
	@RequestMapping(value = {"list", ""})
	public String list(GongLand gongLand, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<GongLand> page = gongLandService.findPage(new Page<GongLand>(request, response), gongLand); 
		model.addAttribute("page", page);
		model.addAttribute("gongLand", gongLand);
		return "modules/collateral/gongLandList";
	}

	@RequiresPermissions("collateral:gongLand:view")
	@RequestMapping(value = "form")
	public String form(GongLand gongLand, Model model) {
		model.addAttribute("gongLand", gongLand);
		return "modules/collateral/gongLandForm";
	}

	@RequestMapping(value = "add")
	public String add(@RequestParam(required=false) String mortgageid, GongLand gongLand, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request ) {
		GongLand entity = null;
		if (StringUtils.isNotBlank(mortgageid)){
			entity = gongLandService.getByPledge(mortgageid);
		}
		//从会话获取 如果 没有保存到数据库
		if (entity == null && StringUtils.isNotBlank(mortgageid)){
			HttpSession session = request.getSession();		
			List<MortgageContract> mortgageContractList = (List<MortgageContract>)session.getAttribute("mortgageContractList");	
		    if (mortgageContractList != null &&  mortgageContractList.size() > 0) {
		    	for(int i = 0; i < mortgageContractList.size(); i++){
					MortgageContract temp = mortgageContractList.get(i);
					if(mortgageid.equals(temp.getId())){		
						entity = temp.getGongLand();
					}
				}	
		    }	
		}
		if (entity != null){
			model.addAttribute("gongLand", entity);
		}
		
		return "modules/collateral/gongLandAdd";
	}
	
	@RequiresPermissions("collateral:gongLand:edit")
	@RequestMapping(value = "save")
	public String save(GongLand gongLand, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, gongLand)){
			return form(gongLand, model);
		}
		gongLandService.save(gongLand);
		addMessage(redirectAttributes, "保存抵押-工业用地信息成功");
		return "redirect:"+Global.getAdminPath()+"/collateral/gongLand/?repage";
	}
	
	@RequiresPermissions("collateral:gongLand:edit")
	@RequestMapping(value = "delete")
	public String delete(GongLand gongLand, RedirectAttributes redirectAttributes) {
		gongLandService.delete(gongLand);
		addMessage(redirectAttributes, "删除抵押-工业用地信息成功");
		return "redirect:"+Global.getAdminPath()+"/collateral/gongLand/?repage";
	}

}
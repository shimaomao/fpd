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
import com.wanfin.fpd.modules.collateral.entity.Cunhuo;
import com.wanfin.fpd.modules.collateral.service.CunhuoService;
import com.wanfin.fpd.modules.pledge.entity.PledgeContract;

/**
 * 存货信息Controller
 * @author srf
 * @version 2016-03-24
 */
@ApiIgnore
@Controller
@RequestMapping(value = "${adminPath}/collateral/cunhuo")
public class CunhuoController extends BaseController {

	@Autowired
	private CunhuoService cunhuoService;
	
	@ModelAttribute
	public Cunhuo get(@RequestParam(required=false) String id) {
		Cunhuo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = cunhuoService.get(id);
		}
		if (entity == null){
			entity = new Cunhuo();
		}
		return entity;
	}
	
	@RequiresPermissions("collateral:cunhuo:view")
	@RequestMapping(value = {"list", ""})
	public String list(Cunhuo cunhuo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Cunhuo> page = cunhuoService.findPage(new Page<Cunhuo>(request, response), cunhuo); 
		model.addAttribute("page", page);
		model.addAttribute("cunhuo", cunhuo);
		return "modules/collateral/cunhuoList";
	}

	@RequiresPermissions("collateral:cunhuo:view")
	@RequestMapping(value = "form")
	public String form(Cunhuo cunhuo, Model model) {
		model.addAttribute("cunhuo", cunhuo);
		return "modules/collateral/cunhuoForm";
	}
	
	@RequestMapping(value = "add")
	public String add(@RequestParam(required=false) String dizhiContractId, Cunhuo cunhuo, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		Cunhuo entity = null;
		if (StringUtils.isNotBlank(dizhiContractId)){
			entity = cunhuoService.getByPledge(dizhiContractId);
		}
		//从会话获取 如果 没有保存到数据库
		if (entity == null && StringUtils.isNotBlank(dizhiContractId)){
			HttpSession session = request.getSession();		
			List<PledgeContract> pledgeContractList = (List<PledgeContract>)session.getAttribute("pledgeContractList");	
			if (pledgeContractList != null &&  pledgeContractList.size() > 0) {
		    	for(int i = 0; i < pledgeContractList.size(); i++){
					PledgeContract temp = pledgeContractList.get(i);
					if(dizhiContractId.equals(temp.getId())){		
						entity = temp.getCunhuo();
					}
				}	
		    }	
		}
		if (entity != null){
			model.addAttribute("cunhuo", entity);
		}
		
		return "modules/collateral/cunhuoAdd";
	}

	@RequiresPermissions("collateral:cunhuo:edit")
	@RequestMapping(value = "save")
	public String save(Cunhuo cunhuo, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, cunhuo)){
			return form(cunhuo, model);
		}
		cunhuoService.save(cunhuo);
		addMessage(redirectAttributes, "保存质押存货信息成功");
		return "redirect:"+Global.getAdminPath()+"/collateral/cunhuo/?repage";
	}
	
	@RequiresPermissions("collateral:cunhuo:edit")
	@RequestMapping(value = "delete")
	public String delete(Cunhuo cunhuo, RedirectAttributes redirectAttributes) {
		cunhuoService.delete(cunhuo);
		addMessage(redirectAttributes, "删除质押存货信息成功");
		return "redirect:"+Global.getAdminPath()+"/collateral/cunhuo/?repage";
	}

}
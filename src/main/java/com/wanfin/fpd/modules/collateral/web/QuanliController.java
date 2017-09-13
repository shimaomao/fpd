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
import com.wanfin.fpd.modules.collateral.entity.Quanli;
import com.wanfin.fpd.modules.collateral.service.QuanliService;
import com.wanfin.fpd.modules.mortgage.entity.MortgageContract;
import com.wanfin.fpd.modules.pledge.entity.PledgeContract;

/**
 * 无形权力Controller
 * @author srf
 * @version 2016-03-24
 */
@ApiIgnore
@Controller
@RequestMapping(value = "${adminPath}/collateral/quanli")
public class QuanliController extends BaseController {

	@Autowired
	private QuanliService quanliService;
	
	@ModelAttribute
	public Quanli get(@RequestParam(required=false) String id) {
		Quanli entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = quanliService.get(id);
		}
		if (entity == null){
			entity = new Quanli();
		}
		return entity;
	}
	
	@RequiresPermissions("collateral:quanli:view")
	@RequestMapping(value = {"list", ""})
	public String list(Quanli quanli, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Quanli> page = quanliService.findPage(new Page<Quanli>(request, response), quanli); 
		model.addAttribute("page", page);
		model.addAttribute("quanli", quanli);
		return "modules/collateral/quanliList";
	}

	@RequiresPermissions("collateral:quanli:view")
	@RequestMapping(value = "form")
	public String form(Quanli quanli, Model model) {
		model.addAttribute("quanli", quanli);
		return "modules/collateral/quanliForm";
	}

	@RequestMapping(value = "add")
	public String add(@RequestParam(required=false) String dizhiContractId, Quanli quanli, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		Quanli entity = null;
		if (StringUtils.isNotBlank(dizhiContractId)){
			entity = quanliService.getByPledge(dizhiContractId);
		}		
		
		//从会话获取 如果 没有保存到数据库
		if (entity == null && StringUtils.isNotBlank(dizhiContractId)){
			HttpSession session = request.getSession();		
			List<PledgeContract> pledgeContractList = (List<PledgeContract>)session.getAttribute("pledgeContractList");	
			if (pledgeContractList != null &&  pledgeContractList.size() > 0) {
		    	for(int i = 0; i < pledgeContractList.size(); i++){
					PledgeContract temp = pledgeContractList.get(i);
					if(dizhiContractId.equals(temp.getId())){		
						entity = temp.getQuanli();
					}
				}	
		    }	
		}
				
		if (entity != null){
			model.addAttribute("quanli", entity);
		}
		
		return "modules/collateral/quanliAdd";
	}
	
	
	@RequestMapping(value = "addForMC")
	public String addForMC(@RequestParam(required=false) String dizhiContractId, Quanli quanli, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		Quanli entity = null;
		if (StringUtils.isNotBlank(dizhiContractId)){
			entity = quanliService.getByPledge(dizhiContractId);
		}		
		
		//从会话获取 如果 没有保存到数据库
		if (entity == null && StringUtils.isNotBlank(dizhiContractId)){
			HttpSession session = request.getSession();		
			List<MortgageContract> mortgageContractList = (List<MortgageContract>)session.getAttribute("mortgageContractList");	
		    if (mortgageContractList != null &&  mortgageContractList.size() > 0) {
		    	for(int i = 0; i < mortgageContractList.size(); i++){
					MortgageContract temp = mortgageContractList.get(i);
					if(dizhiContractId.equals(temp.getId())){		
						entity = temp.getQuanli();
					}
				}	
		    }	
		}
				
		if (entity != null){
			model.addAttribute("quanli", entity);
		}
		
		return "modules/collateral/quanliAdd";
	}
	
	@RequiresPermissions("collateral:quanli:edit")
	@RequestMapping(value = "save")
	public String save(Quanli quanli, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, quanli)){
			return form(quanli, model);
		}
		quanliService.save(quanli);
		addMessage(redirectAttributes, "保存质押无形权力成功");
		return "redirect:"+Global.getAdminPath()+"/collateral/quanli/?repage";
	}
	
	@RequiresPermissions("collateral:quanli:edit")
	@RequestMapping(value = "delete")
	public String delete(Quanli quanli, RedirectAttributes redirectAttributes) {
		quanliService.delete(quanli);
		addMessage(redirectAttributes, "删除质押无形权力成功");
		return "redirect:"+Global.getAdminPath()+"/collateral/quanli/?repage";
	}

}
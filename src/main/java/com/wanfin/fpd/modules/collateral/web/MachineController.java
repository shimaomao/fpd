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
import com.wanfin.fpd.modules.collateral.entity.Machine;
import com.wanfin.fpd.modules.collateral.service.MachineService;
import com.wanfin.fpd.modules.mortgage.entity.MortgageContract;
import com.wanfin.fpd.modules.pledge.entity.PledgeContract;

/**
 * 机器设备Controller
 * @author srf
 * @version 2016-03-24
 */
@ApiIgnore
@Controller
@RequestMapping(value = "${adminPath}/collateral/machine")
public class MachineController extends BaseController {

	@Autowired
	private MachineService machineService;
	
	@ModelAttribute
	public Machine get(@RequestParam(required=false) String id) {
		Machine entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = machineService.get(id);
		}
		if (entity == null){
			entity = new Machine();
		}
		return entity;
	}
	
	@RequiresPermissions("collateral:machine:view")
	@RequestMapping(value = {"list", ""})
	public String list(Machine machine, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Machine> page = machineService.findPage(new Page<Machine>(request, response), machine); 
		model.addAttribute("page", page);
		model.addAttribute("machine", machine);
		return "modules/collateral/machineList";
	}

	@RequiresPermissions("collateral:machine:view")
	@RequestMapping(value = "form")
	public String form(Machine machine, Model model) {
		model.addAttribute("machine", machine);
		return "modules/collateral/machineForm";
	}

	@RequestMapping(value = "add")
	public String add(@RequestParam(required=false) String dizhiContractId, Machine machine, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		Machine entity = null;
		if (StringUtils.isNotBlank(dizhiContractId)){
			entity = machineService.getByPledge(dizhiContractId);
		}
		
		//从会话获取 如果 没有保存到数据库
		if (entity == null && StringUtils.isNotBlank(dizhiContractId)){
			HttpSession session = request.getSession();		
			List<PledgeContract> pledgeContractList = (List<PledgeContract>)session.getAttribute("pledgeContractList");	
			if (pledgeContractList != null &&  pledgeContractList.size() > 0) {
		    	for(int i = 0; i < pledgeContractList.size(); i++){
					PledgeContract temp = pledgeContractList.get(i);
					if(dizhiContractId.equals(temp.getId())){		
						entity = temp.getMachine();
					}
				}	
		    }	
		}
		
		if (entity != null){
			model.addAttribute("machine", entity);
		}
		
		return "modules/collateral/machineAdd";
	}
	
	@RequestMapping(value = "addForMC")
	public String addForMC(@RequestParam(required=false) String dizhiContractId, Machine machine, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		Machine entity = null;
		if (StringUtils.isNotBlank(dizhiContractId)){
			entity = machineService.getByPledge(dizhiContractId);
		}
		
		//从会话获取 如果 没有保存到数据库
		if (entity == null && StringUtils.isNotBlank(dizhiContractId)){
			HttpSession session = request.getSession();		
			List<MortgageContract> mortgageContractList = (List<MortgageContract>)session.getAttribute("mortgageContractList");	
		    if (mortgageContractList != null &&  mortgageContractList.size() > 0) {
		    	for(int i = 0; i < mortgageContractList.size(); i++){
					MortgageContract temp = mortgageContractList.get(i);
					if(dizhiContractId.equals(temp.getId())){		
						entity = temp.getMachine();
					}
				}	
		    }	
		}
		
		if (entity != null){
			model.addAttribute("machine", entity);
		}
		
		return "modules/collateral/machineAdd";
	}
	
	@RequiresPermissions("collateral:machine:edit")
	@RequestMapping(value = "save")
	public String save(Machine machine, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, machine)){
			return form(machine, model);
		}
		machineService.save(machine);
		addMessage(redirectAttributes, "保存质押机器设备成功");
		return "redirect:"+Global.getAdminPath()+"/collateral/machine/?repage";
	}
	
	@RequiresPermissions("collateral:machine:edit")
	@RequestMapping(value = "delete")
	public String delete(Machine machine, RedirectAttributes redirectAttributes) {
		machineService.delete(machine);
		addMessage(redirectAttributes, "删除质押机器设备成功");
		return "redirect:"+Global.getAdminPath()+"/collateral/machine/?repage";
	}

}
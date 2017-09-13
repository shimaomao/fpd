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
import com.wanfin.fpd.modules.collateral.entity.Guquan;
import com.wanfin.fpd.modules.collateral.service.GuquanService;
import com.wanfin.fpd.modules.pledge.entity.PledgeContract;

/**
 * 股权Controller
 * @author srf
 * @version 2016-03-24
 */
@ApiIgnore
@Controller
@RequestMapping(value = "${adminPath}/collateral/guquan")
public class GuquanController extends BaseController {

	@Autowired
	private GuquanService guquanService;
	
	@ModelAttribute
	public Guquan get(@RequestParam(required=false) String id) {
		Guquan entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = guquanService.get(id);
		}
		if (entity == null){
			entity = new Guquan();
		}
		return entity;
	}
	
	@RequiresPermissions("collateral:guquan:view")
	@RequestMapping(value = {"list", ""})
	public String list(Guquan guquan, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Guquan> page = guquanService.findPage(new Page<Guquan>(request, response), guquan); 
		model.addAttribute("page", page);
		model.addAttribute("guquan", guquan);
		return "modules/collateral/guquanList";
	}

	@RequiresPermissions("collateral:guquan:view")
	@RequestMapping(value = "form")
	public String form(Guquan guquan, Model model) {
		model.addAttribute("guquan", guquan);
		return "modules/collateral/guquanForm";
	}

	@RequestMapping(value = "add")
	public String add(@RequestParam(required=false) String dizhiContractId, Guquan guquan, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		Guquan entity = null;
		if (StringUtils.isNotBlank(dizhiContractId)){
			entity = guquanService.getByPledge(dizhiContractId);
		}
		
		//从会话获取 如果 没有保存到数据库
		if (entity == null && StringUtils.isNotBlank(dizhiContractId)){
			HttpSession session = request.getSession();		
			List<PledgeContract> pledgeContractList = (List<PledgeContract>)session.getAttribute("pledgeContractList");	
			if (pledgeContractList != null &&  pledgeContractList.size() > 0) {
		    	for(int i = 0; i < pledgeContractList.size(); i++){
					PledgeContract temp = pledgeContractList.get(i);
					if(dizhiContractId.equals(temp.getId())){		
						entity = temp.getGuquan();
					}
				}	
		    }	
		}
				
		if (entity != null){
			model.addAttribute("guquan", entity);
		}
		
		return "modules/collateral/guquanAdd";
	}
	
	@RequiresPermissions("collateral:guquan:edit")
	@RequestMapping(value = "save")
	public String save(Guquan guquan, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, guquan)){
			return form(guquan, model);
		}
		guquanService.save(guquan);
		addMessage(redirectAttributes, "保存质押股权成功");
		return "redirect:"+Global.getAdminPath()+"/collateral/guquan/?repage";
	}
	
	@RequiresPermissions("collateral:guquan:edit")
	@RequestMapping(value = "delete")
	public String delete(Guquan guquan, RedirectAttributes redirectAttributes) {
		guquanService.delete(guquan);
		addMessage(redirectAttributes, "删除质押股权成功");
		return "redirect:"+Global.getAdminPath()+"/collateral/guquan/?repage";
	}

}
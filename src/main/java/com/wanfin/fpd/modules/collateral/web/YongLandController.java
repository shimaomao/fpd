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
import com.wanfin.fpd.modules.collateral.entity.YongLand;
import com.wanfin.fpd.modules.collateral.service.YongLandService;
import com.wanfin.fpd.modules.pledge.entity.PledgeContract;

/**
 * 用地Controller
 * @author srf
 * @version 2016-03-24
 */
@ApiIgnore
@Controller
@RequestMapping(value = "${adminPath}/collateral/yongLand")
public class YongLandController extends BaseController {

	@Autowired
	private YongLandService yongLandService;
	
	@ModelAttribute
	public YongLand get(@RequestParam(required=false) String id) {
		YongLand entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = yongLandService.get(id);
		}
		if (entity == null){
			entity = new YongLand();
		}
		return entity;
	}
	
	@RequiresPermissions("collateral:yongLand:view")
	@RequestMapping(value = {"list", ""})
	public String list(YongLand yongLand, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<YongLand> page = yongLandService.findPage(new Page<YongLand>(request, response), yongLand); 
		model.addAttribute("page", page);
		model.addAttribute("yongLand", yongLand);
		return "modules/collateral/yongLandList";
	}

	@RequiresPermissions("collateral:yongLand:view")
	@RequestMapping(value = "form")
	public String form(YongLand yongLand, Model model) {
		model.addAttribute("yongLand", yongLand);
		return "modules/collateral/yongLandForm";
	}

	@RequestMapping(value = "add")
	public String add(@RequestParam(required=false) String dizhiContractId, YongLand yongLand, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		YongLand entity = null;
		if (StringUtils.isNotBlank(dizhiContractId)){
			entity = yongLandService.getByPledge(dizhiContractId);
		}
		
		//从会话获取 如果 没有保存到数据库
		if (entity == null && StringUtils.isNotBlank(dizhiContractId)){
			HttpSession session = request.getSession();		
			List<PledgeContract> pledgeContractList = (List<PledgeContract>)session.getAttribute("pledgeContractList");	
			if (pledgeContractList != null &&  pledgeContractList.size() > 0) {
		    	for(int i = 0; i < pledgeContractList.size(); i++){
					PledgeContract temp = pledgeContractList.get(i);
					if(dizhiContractId.equals(temp.getId())){		
						entity = temp.getYongLand();
					}
				}	
		    }	
		}
		
		if (entity != null){
			model.addAttribute("yongLand", entity);
		}
		
		return "modules/collateral/yongLandAdd";
	}
	
	@RequiresPermissions("collateral:yongLand:edit")
	@RequestMapping(value = "save")
	public String save(YongLand yongLand, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, yongLand)){
			return form(yongLand, model);
		}
		yongLandService.save(yongLand);
		addMessage(redirectAttributes, "保存质押用地成功");
		return "redirect:"+Global.getAdminPath()+"/collateral/yongLand/?repage";
	}
	
	@RequiresPermissions("collateral:yongLand:edit")
	@RequestMapping(value = "delete")
	public String delete(YongLand yongLand, RedirectAttributes redirectAttributes) {
		yongLandService.delete(yongLand);
		addMessage(redirectAttributes, "删除质押用地成功");
		return "redirect:"+Global.getAdminPath()+"/collateral/yongLand/?repage";
	}

}
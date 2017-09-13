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
import com.wanfin.fpd.modules.collateral.entity.Gongyu;
import com.wanfin.fpd.modules.collateral.service.GongyuService;
import com.wanfin.fpd.modules.mortgage.entity.MortgageContract;

/**
 * 公寓信息Controller
 * @author srf
 * @version 2016-03-24
 */
@ApiIgnore
@Controller
@RequestMapping(value = "${adminPath}/collateral/gongyu")
public class GongyuController extends BaseController {

	@Autowired
	private GongyuService gongyuService;
	
	@ModelAttribute
	public Gongyu get(@RequestParam(required=false) String id) {
		Gongyu entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = gongyuService.get(id);
		}
		if (entity == null){
			entity = new Gongyu();
		}
		return entity;
	}
	
	@RequiresPermissions("collateral:gongyu:view")
	@RequestMapping(value = {"list", ""})
	public String list(Gongyu gongyu, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Gongyu> page = gongyuService.findPage(new Page<Gongyu>(request, response), gongyu); 
		model.addAttribute("page", page);
		model.addAttribute("gongyu", gongyu);
		return "modules/collateral/gongyuList";
	}

	@RequiresPermissions("collateral:gongyu:view")
	@RequestMapping(value = "form")
	public String form(Gongyu gongyu, Model model) {
		model.addAttribute("gongyu", gongyu);
		return "modules/collateral/gongyuForm";
	}

	@RequestMapping(value = "add")
	public String add(@RequestParam(required=false) String mortgageid, Gongyu gongyu, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request ) {
		Gongyu entity = null;
		if (StringUtils.isNotBlank(mortgageid)){
			entity = gongyuService.getByPledge(mortgageid);
		}
		//从会话获取 如果 没有保存到数据库
		if (entity == null && StringUtils.isNotBlank(mortgageid)){
			HttpSession session = request.getSession();		
			List<MortgageContract> mortgageContractList = (List<MortgageContract>)session.getAttribute("mortgageContractList");	
		    if (mortgageContractList != null &&  mortgageContractList.size() > 0) {
		    	for(int i = 0; i < mortgageContractList.size(); i++){
					MortgageContract temp = mortgageContractList.get(i);
					if(mortgageid.equals(temp.getId())){		
						entity = temp.getGongyu();
					}
				}	
		    }	
		}
		if (entity != null){
			model.addAttribute("gongyu", entity);
		}
		
		return "modules/collateral/gongyuAdd";
	}
	
	@RequiresPermissions("collateral:gongyu:edit")
	@RequestMapping(value = "save")
	public String save(Gongyu gongyu, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, gongyu)){
			return form(gongyu, model);
		}
		gongyuService.save(gongyu);
		addMessage(redirectAttributes, "保存抵押公寓信息成功");
		return "redirect:"+Global.getAdminPath()+"/collateral/gongyu/?repage";
	}
	
	@RequiresPermissions("collateral:gongyu:edit")
	@RequestMapping(value = "delete")
	public String delete(Gongyu gongyu, RedirectAttributes redirectAttributes) {
		gongyuService.delete(gongyu);
		addMessage(redirectAttributes, "删除抵押公寓信息成功");
		return "redirect:"+Global.getAdminPath()+"/collateral/gongyu/?repage";
	}

}
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
import com.wanfin.fpd.modules.collateral.entity.SingleVilla;
import com.wanfin.fpd.modules.collateral.service.SingleVillaService;
import com.wanfin.fpd.modules.mortgage.entity.MortgageContract;

/**
 * 独栋别墅Controller
 * @author srf
 * @version 2016-03-24
 */
@ApiIgnore
@Controller
@RequestMapping(value = "${adminPath}/collateral/singleVilla")
public class SingleVillaController extends BaseController {

	@Autowired
	private SingleVillaService singleVillaService;
	
	@ModelAttribute
	public SingleVilla get(@RequestParam(required=false) String id) {
		SingleVilla entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = singleVillaService.get(id);
		}
		if (entity == null){
			entity = new SingleVilla();
		}
		return entity;
	}
	
	@RequiresPermissions("collateral:singleVilla:view")
	@RequestMapping(value = {"list", ""})
	public String list(SingleVilla singleVilla, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SingleVilla> page = singleVillaService.findPage(new Page<SingleVilla>(request, response), singleVilla); 
		model.addAttribute("page", page);
		model.addAttribute("singleVilla", singleVilla);
		return "modules/collateral/singleVillaList";
	}

	@RequiresPermissions("collateral:singleVilla:view")
	@RequestMapping(value = "form")
	public String form(SingleVilla singleVilla, Model model) {
		model.addAttribute("singleVilla", singleVilla);
		return "modules/collateral/singleVillaForm";
	}

	@RequestMapping(value = "add")
	public String add(@RequestParam(required=false) String mortgageid, SingleVilla singleVilla, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request ) {
		SingleVilla entity = null;
		if (StringUtils.isNotBlank(mortgageid)){
			entity = singleVillaService.getByPledge(mortgageid);
		}
		//从会话获取 如果 没有保存到数据库
		if (entity == null && StringUtils.isNotBlank(mortgageid)){
			HttpSession session = request.getSession();		
			List<MortgageContract> mortgageContractList = (List<MortgageContract>)session.getAttribute("mortgageContractList");	
		    if (mortgageContractList != null &&  mortgageContractList.size() > 0) {
		    	for(int i = 0; i < mortgageContractList.size(); i++){
					MortgageContract temp = mortgageContractList.get(i);
					if(mortgageid.equals(temp.getId())){		
						entity = temp.getSingleVilla();
					}
				}	
		    }	
		}
		if (entity != null){
			model.addAttribute("singleVilla", entity);
		}
		
		return "modules/collateral/singleVillaAdd";
	}
	
	@RequiresPermissions("collateral:singleVilla:edit")
	@RequestMapping(value = "save")
	public String save(SingleVilla singleVilla, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, singleVilla)){
			return form(singleVilla, model);
		}
		singleVillaService.save(singleVilla);
		addMessage(redirectAttributes, "保存抵押独栋别墅成功");
		return "redirect:"+Global.getAdminPath()+"/collateral/singleVilla/?repage";
	}
	
	@RequiresPermissions("collateral:singleVilla:edit")
	@RequestMapping(value = "delete")
	public String delete(SingleVilla singleVilla, RedirectAttributes redirectAttributes) {
		singleVillaService.delete(singleVilla);
		addMessage(redirectAttributes, "删除抵押独栋别墅成功");
		return "redirect:"+Global.getAdminPath()+"/collateral/singleVilla/?repage";
	}

}
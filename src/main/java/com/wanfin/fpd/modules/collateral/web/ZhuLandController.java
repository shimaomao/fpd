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
import com.wanfin.fpd.modules.collateral.entity.ZhuLand;
import com.wanfin.fpd.modules.collateral.service.ZhuLandService;
import com.wanfin.fpd.modules.mortgage.entity.MortgageContract;

/**
 * 商宅用地Controller
 * @author srf
 * @version 2016-03-24
 */
@ApiIgnore
@Controller
@RequestMapping(value = "${adminPath}/collateral/zhuLand")
public class ZhuLandController extends BaseController {

	@Autowired
	private ZhuLandService zhuLandService;
	
	@ModelAttribute
	public ZhuLand get(@RequestParam(required=false) String id) {
		ZhuLand entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = zhuLandService.get(id);
		}
		if (entity == null){
			entity = new ZhuLand();
		}
		return entity;
	}
	
	@RequiresPermissions("collateral:zhuLand:view")
	@RequestMapping(value = {"list", ""})
	public String list(ZhuLand zhuLand, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ZhuLand> page = zhuLandService.findPage(new Page<ZhuLand>(request, response), zhuLand); 
		model.addAttribute("page", page);
		model.addAttribute("zhuLand", zhuLand);
		return "modules/collateral/zhuLandList";
	}

	@RequiresPermissions("collateral:zhuLand:view")
	@RequestMapping(value = "form")
	public String form(ZhuLand zhuLand, Model model) {
		model.addAttribute("zhuLand", zhuLand);
		return "modules/collateral/zhuLandForm";
	}

	@RequestMapping(value = "add")
	public String add(@RequestParam(required=false) String mortgageid, ZhuLand zhuLand, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request ) {
		ZhuLand entity = null;
		if (StringUtils.isNotBlank(mortgageid)){
			entity = zhuLandService.getByPledge(mortgageid);
		}
		//从会话获取 如果 没有保存到数据库
		if (entity == null && StringUtils.isNotBlank(mortgageid)){
			HttpSession session = request.getSession();		
			List<MortgageContract> mortgageContractList = (List<MortgageContract>)session.getAttribute("mortgageContractList");	
		    if (mortgageContractList != null &&  mortgageContractList.size() > 0) {
		    	for(int i = 0; i < mortgageContractList.size(); i++){
					MortgageContract temp = mortgageContractList.get(i);
					if(mortgageid.equals(temp.getId())){		
						entity = temp.getZhuLand();
					}
				}	
		    }	
		}
		if (entity != null){
			model.addAttribute("zhuLand", entity);
		}
		
		return "modules/collateral/zhuLandAdd";
	}
	
	@RequiresPermissions("collateral:zhuLand:edit")
	@RequestMapping(value = "save")
	public String save(ZhuLand zhuLand, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, zhuLand)){
			return form(zhuLand, model);
		}
		zhuLandService.save(zhuLand);
		addMessage(redirectAttributes, "保存抵押商宅用地成功");
		return "redirect:"+Global.getAdminPath()+"/collateral/zhuLand/?repage";
	}
	
	@RequiresPermissions("collateral:zhuLand:edit")
	@RequestMapping(value = "delete")
	public String delete(ZhuLand zhuLand, RedirectAttributes redirectAttributes) {
		zhuLandService.delete(zhuLand);
		addMessage(redirectAttributes, "删除抵押商宅用地成功");
		return "redirect:"+Global.getAdminPath()+"/collateral/zhuLand/?repage";
	}

}
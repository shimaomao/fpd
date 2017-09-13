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
import com.wanfin.fpd.modules.collateral.entity.ZhuZhai;
import com.wanfin.fpd.modules.collateral.service.ZhuZhaiService;
import com.wanfin.fpd.modules.mortgage.entity.MortgageContract;

/**
 * 住宅信息Controller
 * @author srf
 * @version 2016-03-24
 */
@ApiIgnore
@Controller
@RequestMapping(value = "${adminPath}/collateral/zhuZhai")
public class ZhuZhaiController extends BaseController {

	@Autowired
	private ZhuZhaiService zhuZhaiService;
	
	@ModelAttribute
	public ZhuZhai get(@RequestParam(required=false) String id) {
		ZhuZhai entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = zhuZhaiService.get(id);
		}
		if (entity == null){
			entity = new ZhuZhai();
		}
		return entity;
	}
	
	@RequiresPermissions("collateral:zhuZhai:view")
	@RequestMapping(value = {"list", ""})
	public String list(ZhuZhai zhuZhai, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ZhuZhai> page = zhuZhaiService.findPage(new Page<ZhuZhai>(request, response), zhuZhai); 
		model.addAttribute("page", page);
		model.addAttribute("zhuZhai", zhuZhai);
		return "modules/collateral/zhuZhaiList";
	}

	@RequiresPermissions("collateral:zhuZhai:view")
	@RequestMapping(value = "form")
	public String form(ZhuZhai zhuZhai, Model model) {
		model.addAttribute("zhuZhai", zhuZhai);
		return "modules/collateral/zhuZhaiForm";
	}
	
	@RequestMapping(value = "add")
	public String add(@RequestParam(required=false) String mortgageid, ZhuZhai zhuZhai, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request ) {
		ZhuZhai entity = null;
		if (StringUtils.isNotBlank(mortgageid)){
			entity = zhuZhaiService.getByPledge(mortgageid);
		}
		//从会话获取 如果 没有保存到数据库
		if (entity == null && StringUtils.isNotBlank(mortgageid)){
			HttpSession session = request.getSession();		
			List<MortgageContract> mortgageContractList = (List<MortgageContract>)session.getAttribute("mortgageContractList");	
		    if (mortgageContractList != null &&  mortgageContractList.size() > 0) {
		    	for(int i = 0; i < mortgageContractList.size(); i++){
					MortgageContract temp = mortgageContractList.get(i);
					if(mortgageid.equals(temp.getId())){		
						entity = temp.getZhuZhai();
					}
				}	
		    }	
		}
		if (entity != null){
			model.addAttribute("zhuZhai", entity);
		}
		
		return "modules/collateral/zhuZhaiAdd";
	}

	@RequiresPermissions("collateral:zhuZhai:edit")
	@RequestMapping(value = "save")
	public String save(ZhuZhai zhuZhai, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, zhuZhai)){
			return form(zhuZhai, model);
		}
		zhuZhaiService.save(zhuZhai);
		addMessage(redirectAttributes, "保存抵押住宅信息成功");
		return "redirect:"+Global.getAdminPath()+"/collateral/zhuZhai/?repage";
	}
	
	@RequiresPermissions("collateral:zhuZhai:edit")
	@RequestMapping(value = "delete")
	public String delete(ZhuZhai zhuZhai, RedirectAttributes redirectAttributes) {
		zhuZhaiService.delete(zhuZhai);
		addMessage(redirectAttributes, "删除抵押住宅信息成功");
		return "redirect:"+Global.getAdminPath()+"/collateral/zhuZhai/?repage";
	}

}
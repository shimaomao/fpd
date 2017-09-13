/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.catipal.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.wanfin.fpd.modules.catipal.entity.TCapital;
import com.wanfin.fpd.modules.catipal.service.TCapitalService;
import com.wanfin.fpd.modules.sys.utils.UserUtils;

/**
 * 资本信息Controller
 * @author lx
 * @version 2016-05-09
 */
@ApiIgnore
@Controller
@RequestMapping(value = "${adminPath}/catipal/tCapital")
public class TCapitalController extends BaseController {

	@Autowired
	private TCapitalService tCapitalService;
	
	@ModelAttribute
	public TCapital get(@RequestParam(required=false) String id) {
		TCapital entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = tCapitalService.get(id);
		}
		if (entity == null){
			entity = new TCapital();
		}
		return entity;
	}
	
	@RequiresPermissions("catipal:tCapital:view")
	@RequestMapping(value = {"list", ""})
	public String list(TCapital tCapital, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<TCapital> page = tCapitalService.findPage(new Page<TCapital>(request, response), tCapital);
		model.addAttribute("page", page);
		model.addAttribute("tCapital", tCapital);
		model.addAttribute("count",page.getCount());
		return "modules/catipal/tCapitalList";
	}

	@RequiresPermissions("catipal:tCapital:view")
	@RequestMapping(value = "form")
	public String form(TCapital tCapital, Model model) {
		model.addAttribute("tCapital", tCapital);
		return "modules/catipal/tCapitalForm";
	}

	@RequiresPermissions("catipal:tCapital:edit")
	@RequestMapping(value = "save")
	public String save(TCapital tCapital, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, tCapital)){
			return form(tCapital, model);
		}
		tCapital.setOrganId(UserUtils.getUser().getCompany().getId());
		List<TCapital> list=tCapitalService.findAllList(tCapital);
		//Bug #3142 资本信息列表只能创建一条记录，并可对其中表单手动编辑（未分配利润&外部融资首次可手动编辑，后续均只能通过添加利润&融资数据进行增减金额）
		if(list.size()>0 && StringUtils.isBlank(tCapital.getId())){
			addMessage(redirectAttributes, "资本信息已存在");
		}else{
			tCapitalService.save(tCapital);
			addMessage(redirectAttributes, "保存资本信息成功");
		}
		return "redirect:"+Global.getAdminPath()+"/catipal/tCapital/?repage";
	}
	
	@RequiresPermissions("catipal:tCapital:edit")
	@RequestMapping(value = "delete")
	public String delete(TCapital tCapital, RedirectAttributes redirectAttributes) {
		tCapitalService.delete(tCapital);
		addMessage(redirectAttributes, "删除资本信息成功");
		return "redirect:"+Global.getAdminPath()+"/catipal/tCapital/?repage";
	}

}
/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.common.web.tpl;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.wanfin.fpd.common.config.Global;
import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.web.BaseController;
import com.wanfin.fpd.common.utils.StringUtils;
import com.google.common.collect.Maps;
import com.mangofactory.swagger.annotations.ApiIgnore;
import com.wanfin.fpd.modules.common.entity.tpl.TplPublic;
import com.wanfin.fpd.modules.common.service.tpl.TplPublicService;
import com.wanfin.fpd.modules.sys.utils.UserUtils;

/**
 * 公共模板库表Controller
 * @author srf
 * @version 2017-01-16
 */
@ApiIgnore
@Controller
@RequestMapping(value = "${adminPath}/common/tpl/tplPublic")
public class TplPublicController extends BaseController {

	@Autowired
	private TplPublicService tplPublicService;
	
	@ModelAttribute
	public TplPublic get(@RequestParam(required=false) String id) {
		TplPublic entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = tplPublicService.get(id);
		}
		if (entity == null){
			entity = new TplPublic();
		}
		return entity;
	}
	
	//获取某个特定的合同模板内容
	@ResponseBody
	@RequestMapping(value = "getTplPublic")
	public Map<String, Object> getTplPublic(@RequestParam("id")String id, @RequestParam("tplCode") String tplCode){
		Map<String, Object> result = Maps.newHashMap();
		TplPublic tplPublic = null;
		if(StringUtils.isNotBlank(id)){
			tplPublic = tplPublicService.get(id);
		}
		
		if(tplPublic != null){
			result.put("status", 1);
			result.put("message", "成功");
			result.put("tplPublic", tplPublic);
		}else{
			result.put("status", 2);
			result.put("message", "没有对应的模板");
		}
		return result;
	}
	
	@RequiresPermissions("common:tpl:tplPublic:view")
	@RequestMapping(value = {"list", ""})
	public String list(TplPublic tplPublic, HttpServletRequest request, HttpServletResponse response, Model model) {
		if(!UserUtils.getUser().getLoginName().equals("admin")){
			tplPublic.setOrganId(UserUtils.getUser().getCompany().getId());
		}
		Page<TplPublic> page = tplPublicService.findPage(new Page<TplPublic>(request, response), tplPublic); 
		model.addAttribute("page", page);
		model.addAttribute("tplPublic", tplPublic);
		return "modules/common/tpl/tplPublicList";
	}

	@RequiresPermissions("common:tpl:tplPublic:view")
	@RequestMapping(value = "form")
	public String form(TplPublic tplPublic, Model model) {
		model.addAttribute("tplPublic", tplPublic);
		return "modules/common/tpl/tplPublicForm";
	}

	@RequiresPermissions("common:tpl:tplPublic:edit")
	@RequestMapping(value = "save")
	public String save(TplPublic tplPublic, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, tplPublic)){
			return form(tplPublic, model);
		}
		if(StringUtils.isBlank(tplPublic.getTplStatus())){
			tplPublic.setTplStatus("0");
		}
		TplPublic old = tplPublicService.getByCode(tplPublic.getTplCode());
		if(StringUtils.isNotBlank(tplPublic.getId()) || old == null){
			tplPublic.setOrganId(UserUtils.getUser().getCompany().getId());
			tplPublicService.save(tplPublic);
			addMessage(redirectAttributes, "保存公共模板库表成功");
		}else{
			model.addAttribute("message", "该模板编码已经存在！");
			return form(tplPublic, model);
		}
		return "redirect:"+Global.getAdminPath()+"/common/tpl/tplPublic/?repage";
	}
	
	@RequiresPermissions("common:tpl:tplPublic:edit")
	@RequestMapping(value = "chageState")
	public String chageState(TplPublic tplPublic, Model model, RedirectAttributes redirectAttributes) {
		if(StringUtils.isAnyBlank(tplPublic.getId(),tplPublic.getTplStatus())){
			model.addAttribute("message", "参数异常");
		}
		int ifok = tplPublicService.chageState(tplPublic);
		System.out.println("修改状态："+ifok);
		addMessage(redirectAttributes, "状态修改成功");
		return "redirect:"+Global.getAdminPath()+"/common/tpl/tplPublic/?repage";
	}
	
	@RequiresPermissions("common:tpl:tplPublic:edit")
	@RequestMapping(value = "delete")
	public String delete(TplPublic tplPublic, RedirectAttributes redirectAttributes) {
		tplPublicService.delete(tplPublic);
		addMessage(redirectAttributes, "删除公共模板库表成功");
		return "redirect:"+Global.getAdminPath()+"/common/tpl/tplPublic/?repage";
	}

}
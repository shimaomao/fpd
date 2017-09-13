/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.windcfg.web;

import java.util.Date;
import java.util.HashMap;
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

import com.google.common.base.Splitter;
import com.mangofactory.swagger.annotations.ApiIgnore;
import com.wanfin.fpd.common.config.Global;
import com.wanfin.fpd.common.config.SvalBase;
import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.utils.StringUtils;
import com.wanfin.fpd.common.web.BaseController;
import com.wanfin.fpd.modules.product.entity.TProduct;
import com.wanfin.fpd.modules.sys.entity.User;
import com.wanfin.fpd.modules.sys.utils.UserUtils;
import com.wanfin.fpd.modules.wind.entity.TWindControl;
import com.wanfin.fpd.modules.windcfg.entity.TWindControlCfg;
import com.wanfin.fpd.modules.windcfg.service.TWindControlCfgService;

/**
 * 风控配置Controller
 * @author chenh
 * @version 2016-05-26
 */
@ApiIgnore
@Controller
@RequestMapping(value = "${adminPath}/windcfg/tWindControlCfg")
public class TWindControlCfgController extends BaseController {

	@Autowired
	private TWindControlCfgService tWindControlCfgService;
	
	@ModelAttribute
	public TWindControlCfg get(@RequestParam(required=false) String id) {
		TWindControlCfg entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = tWindControlCfgService.get(id);
		}
		if (entity == null){
			entity = new TWindControlCfg();
		}
		return entity;
	}
	
	@RequiresPermissions("windcfg:tWindControlCfg:view")
	@RequestMapping(value = {"list", ""})
	public String list(TWindControlCfg tWindControlCfg, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<TWindControlCfg> page = tWindControlCfgService.findPage(new Page<TWindControlCfg>(request, response), tWindControlCfg); 
		model.addAttribute("page", page);
		model.addAttribute("tWindControlCfg", tWindControlCfg);
		return "modules/windcfg/tWindControlCfgList";
	}

	@RequiresPermissions("windcfg:tWindControlCfg:view")
	@RequestMapping(value = "form")
	public String form(TWindControlCfg tWindControlCfg, Model model) {
		model.addAttribute("tWindControlCfg", tWindControlCfg);
		return "modules/windcfg/tWindControlCfgForm";
	}

	@RequiresPermissions("windcfg:tWindControlCfg:edit")
	@RequestMapping(value = "save")
	public String save(TWindControlCfg tWindControlCfg, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, tWindControlCfg)){
			return form(tWindControlCfg, model);
		}
		tWindControlCfgService.save(tWindControlCfg);
		addMessage(redirectAttributes, "保存风控配置成功");
		return "redirect:"+Global.getAdminPath()+"/windcfg/tWindControlCfg/?repage";
	}
	
	@ResponseBody
	@RequestMapping(value = "ajaxSave")
	public Map<String, Object> ajaxSave(TWindControlCfg tWindControlCfg, Model model) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put(SvalBase.JsonKey.KEY_STATUS, false);
		if(tWindControlCfg != null){
			if((tWindControlCfg.getProduct() != null) && (StringUtils.isNotEmpty(tWindControlCfg.getProduct().getId()))
			&& (tWindControlCfg.getWind() != null) && (StringUtils.isNotEmpty(tWindControlCfg.getWind().getId()))){
				tWindControlCfgService.deleteWLByRelIdList(tWindControlCfg.getWind().getIds(), tWindControlCfg.getProduct().getId());
				for (String wid : tWindControlCfg.getWind().getIds()){
					User currentUser = UserUtils.getUser();//当前登录人
					TWindControlCfg newWindControlCfg = new TWindControlCfg();
					newWindControlCfg.setWind(new TWindControl(wid));
					newWindControlCfg.setProduct(new TProduct(tWindControlCfg.getProduct().getId()));
					newWindControlCfg.setOrganId(currentUser.getOffice().getId()); 
					newWindControlCfg.setCreateDate(new Date());
					newWindControlCfg.setUpdateDate(new Date());
					tWindControlCfgService.save(newWindControlCfg);
				}
				
				result.put(SvalBase.JsonKey.KEY_MSG, "保存风控配置成功");
				result.put(SvalBase.JsonKey.KEY_STATUS, true);
				return result;
			}
		}
		result.put(SvalBase.JsonKey.KEY_MSG, "保存风控配置失败");
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value = "ajaxDelete")
	public Map<String, Object> ajaxDelete(TWindControlCfg tWindControlCfg, Model model) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put(SvalBase.JsonKey.KEY_STATUS, false);
		if(tWindControlCfg != null){
			if((tWindControlCfg.getProduct() != null) && (StringUtils.isNotEmpty(tWindControlCfg.getProduct().getId())) && (tWindControlCfg.getWind() != null) && (StringUtils.isNotEmpty(tWindControlCfg.getWind().getId()))){
				tWindControlCfgService.deleteWLByRelIdList(tWindControlCfg.getWind().getIds(), tWindControlCfg.getProduct().getId());
				result.put(SvalBase.JsonKey.KEY_MSG, "删除风控配置成功");
				result.put(SvalBase.JsonKey.KEY_STATUS, true);
				return result;
			}
		}
		result.put(SvalBase.JsonKey.KEY_MSG, "删除风控配置失败");
		return result;
	}
	
	@RequiresPermissions("windcfg:tWindControlCfg:edit")
	@RequestMapping(value = "delete")
	public String delete(TWindControlCfg tWindControlCfg, RedirectAttributes redirectAttributes) {
		tWindControlCfgService.delete(tWindControlCfg);
		addMessage(redirectAttributes, "删除风控配置成功");
		return "redirect:"+Global.getAdminPath()+"/windcfg/tWindControlCfg/?repage";
	}

	public static void main(String[] args) {
		Iterable<String> s = Splitter.on(",").trimResults().split("aaa,cccc,vvvsv,savsadas");
		System.out.println(s);
	}
}
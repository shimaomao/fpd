/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.sys.web;

import java.util.List;
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

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.mangofactory.swagger.annotations.ApiIgnore;
import com.wanfin.fpd.common.config.Cons;
import com.wanfin.fpd.common.config.Global;
import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.service.ServiceException;
import com.wanfin.fpd.common.utils.StringUtils;
import com.wanfin.fpd.common.web.BaseController;
import com.wanfin.fpd.modules.company.entity.TCompany;
import com.wanfin.fpd.modules.employee.entity.TEmployee;
import com.wanfin.fpd.modules.sys.entity.FeeBiz;
import com.wanfin.fpd.modules.sys.entity.OpenBiz;
import com.wanfin.fpd.modules.sys.service.OpenBizService;
import com.wanfin.fpd.modules.wind.entity.TWindControl;
import com.wanfin.fpd.modules.wind.service.TWindControlService;

/**
 * 开通业务Controller
 * 
 * @author zzm
 * @version 2016-06-06
 */
@ApiIgnore
@Controller
@RequestMapping(value = "${adminPath}/sys/openBiz")
public class OpenBizController extends BaseController {

	@Autowired
	private OpenBizService openBizService;
	
	@Autowired
	private TWindControlService tWindControlService;

	@ModelAttribute
	public OpenBiz get(@RequestParam(required = false) String id) {
		OpenBiz entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = openBizService.get(id);
		}
		if (entity == null) {
			entity = new OpenBiz();
		}
		return entity;
	}

	@RequiresPermissions("sys:openBiz:view")
	@RequestMapping(value = { "list", "" })
	public String list(OpenBiz openBiz, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		openBiz.setStatus(Cons.NEW);
		Page<OpenBiz> page = openBizService.findPage(new Page<OpenBiz>(request,
				response), openBiz);
		model.addAttribute("page", page);
		model.addAttribute("openBiz", openBiz);
		return "modules/sys/openBizList";
	}
	
	@RequiresPermissions("sys:openBiz:view")
	@RequestMapping(value = "listF")
	public String listF(OpenBiz openBiz, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		openBiz.setStatus(Cons.VALID+","+Cons.INVALID);
		Page<OpenBiz> page = openBizService.findPage(new Page<OpenBiz>(request,
				response), openBiz);
		model.addAttribute("page", page);
		model.addAttribute("openBiz", openBiz);
		return "modules/sys/openBizList";
	}

	@RequiresPermissions("sys:openBiz:view")
	@RequestMapping(value = "form")
	public String form(OpenBiz openBiz, Model model) {
		model.addAttribute("openBiz", openBiz);
		if(openBiz.getFeeBiz() != null && StringUtils.isNotBlank("1")){
			openBiz.setFeeBiz(openBizService.getFeeBiz(openBiz.getFeeBiz().getId()));
		}
		
		if(openBiz.getCount() == 0 )
			openBiz.setCount(1);
		
		return "modules/sys/openBizForm";
	}

	@RequiresPermissions("sys:openBiz:edit")
	@RequestMapping(value = "save")
	public String save(OpenBiz openBiz, Model model,
			RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, openBiz)) {
			return form(openBiz, model);
		}
		try {
			openBizService.save(openBiz);
			addMessage(redirectAttributes, "保存业务开通成功");
		} catch (ServiceException e) {
			addMessage(redirectAttributes, e.getMessage());
		}
		return "redirect:" + Global.getAdminPath() + "/sys/openBiz/?repage";
	}

	@RequiresPermissions("sys:openBiz:edit")
	@RequestMapping(value = "delete")
	public String delete(OpenBiz openBiz, RedirectAttributes redirectAttributes) {
		openBizService.delete(openBiz);
		addMessage(redirectAttributes, "删除业务开通成功");
		return "redirect:" + Global.getAdminPath() + "/sys/openBiz/?repage";
	}

	@RequiresPermissions("sys:openBiz:view")
	@RequestMapping(value = "feeBizForm")
	public String feeBizForm(FeeBiz feeBiz, Model model) {
		model.addAttribute("feeBiz", feeBiz);
		return "modules/sys/feeBizForm";
	}
	
	@RequiresPermissions("sys:feeBiz:edit")
	@RequestMapping(value = "saveFeeBiz")
	public String saveFeeBiz(FeeBiz feeBiz, Model model,
			RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, feeBiz)) {
			return feeBizForm(feeBiz, model);
		}
		try {
			openBizService.saveFeeBiz(feeBiz);
			addMessage(redirectAttributes, "保存付费业务成功");
		} catch (ServiceException e) {
			addMessage(redirectAttributes, e.getMessage());
		}
		return "redirect:" + Global.getAdminPath() + "/sys/openBiz/feeBizList?repage";
	}
	
	@RequiresPermissions("sys:openBiz:view")
	@RequestMapping(value = "feeBizList")
	public String feeBizList(FeeBiz feeBiz, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		List<FeeBiz> list = openBizService.findFeeBizList(feeBiz);

		model.addAttribute("list", list);
		model.addAttribute("feeBiz", feeBiz);
		return "modules/sys/feeBizList";
	}
	
	/**
	 * @Description 获取付费业务关联的业务源
	 * @param extId	排除的id
	 * @param category 付费业务类型
	 * @return 
	 * @author zzm
	 * @date 2016-6-22 上午9:56:03  
	 */
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "bizCodeTreeData")
	public List<Map<String, Object>> bizCodeTreeData(@RequestParam(required=false) String extId, @RequestParam(required=false) String category, 
			HttpServletResponse response,HttpServletRequest request) {
		
		List<Map<String, Object>> mapList = Lists.newArrayList();
		String sql = null;
		if(StringUtils.equals(category, Cons.FeeBizCategory.RISKTIP)){
			sql = "select id,name from t_wind_control where status = 1";
		}
		
		if(StringUtils.isBlank(sql)) return mapList;
		
		List<Record> list = Db.find(sql);
		if(list != null && list.size() > 0 ){
			for (int i=0; i<list.size(); i++){
				Record e = list.get(i);
				if (StringUtils.isBlank(extId) || (extId!=null && !extId.equals(e.getStr("id")))){
					Map<String, Object> map = Maps.newHashMap();
					map.put("id", e.getStr("id"));
					map.put("name", e.getStr("name"));
					mapList.add(map);
				}
			}
		}
		
		return mapList;
	}
	

	/**
	 * @Description 申请开通付费业务
	 * @param openBiz
	 * @param model
	 * @param redirectAttributes
	 * @return
	 * @author zzm
	 * @date 2016-6-20 下午4:45:01  
	 */
	@RequiresPermissions("sys:openBiz:edit")
	@RequestMapping(value = "apply")
	public String apply(OpenBiz openBiz, Model model,
			RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, openBiz)) {
			return form(openBiz, model);
		}
		try {
			openBizService.apply(openBiz);
			addMessage(redirectAttributes, "激活申请发送成功");
		} catch (ServiceException e) {
			addMessage(redirectAttributes, e.getMessage());
		}
		return "redirect:" + Global.getAdminPath()
				+ "/sys/openBiz/list?repage";
	}
	
	/**
	 * @Description 给租户激活付费业务
	 * @param openBiz
	 * @param model
	 * @param redirectAttributes
	 * @author zzm
	 * @date 2016-6-20 下午4:45:20  
	 */
	@RequiresPermissions("sys:openBiz:audit")
	@RequestMapping(value = "activite")
	public String activite(OpenBiz openBiz, Model model,
			RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, openBiz)) {
			return form(openBiz, model);
		}
		try {
			openBizService.activateOpenBiz(openBiz);
			addMessage(redirectAttributes, "激活处理成功");
		} catch (ServiceException e) {
			addMessage(redirectAttributes, e.getMessage());
		}
		return "redirect:" + Global.getAdminPath()
				+ "/sys/openBiz/feeBizList?repage";
	}
	
	/**
	 * @Description 不通过租户激活付费业务的申请
	 * @param openBiz
	 * @param model
	 * @param redirectAttributes
	 * @author zzm
	 * @date 2016-6-20 下午4:45:20  
	 */
	@RequiresPermissions("sys:openBiz:audit")
	@RequestMapping(value = "deactivite")
	public String deactivite(OpenBiz openBiz, Model model,
			RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, openBiz)) {
			return form(openBiz, model);
		}
		try {
			openBizService.deactivateOpenBiz(openBiz);
			addMessage(redirectAttributes, "不激活处理成功");
		} catch (ServiceException e) {
			addMessage(redirectAttributes, e.getMessage());
		}
		return "redirect:" + Global.getAdminPath()
				+ "/sys/openBiz/feeBizList?repage";
	}
}
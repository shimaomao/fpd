/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.accountbalance.web;

import java.text.SimpleDateFormat;
import java.util.Calendar;
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
import com.mangofactory.swagger.annotations.ApiIgnore;
import com.wanfin.fpd.common.config.Global;
import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.utils.StringUtils;
import com.wanfin.fpd.common.web.BaseController;
import com.wanfin.fpd.modules.accountbalance.entity.TAccountBalance;
import com.wanfin.fpd.modules.accountbalance.service.TAccountBalanceService;

/**
 * 科目余额Controller
 * @author lx
 * @version 2016-05-16
 */
@ApiIgnore
@Controller
@RequestMapping(value = "${adminPath}/accountbalance/tAccountBalance")
public class TAccountBalanceController extends BaseController {

	@Autowired
	private TAccountBalanceService tAccountBalanceService;
	
	@ModelAttribute
	public TAccountBalance get(@RequestParam(required=false) String id) {
		TAccountBalance entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = tAccountBalanceService.get(id);
		}
		if (entity == null){
			entity = new TAccountBalance();
		}
		return entity;
	}
	
	@RequiresPermissions("accountbalance:tAccountBalance:view")
	@RequestMapping(value = {"list", ""})
	public String list(TAccountBalance tAccountBalance, HttpServletRequest request, HttpServletResponse response, Model model) {
		String  first = "";
		if(tAccountBalance.getCreateTime()==null||tAccountBalance.getCreateTime().equals("")){
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM"); 
			 Calendar c = Calendar.getInstance();    
		     c.add(Calendar.MONTH, 0);
		     c.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天 
		      first= format.format(c.getTime());
		     tAccountBalance.setCreateTime(first+"-%");
		}else{
			tAccountBalance.setCreateTime(tAccountBalance.getCreateTime()+"-%");
		}
		Page<TAccountBalance> page = tAccountBalanceService.findPage(new Page<TAccountBalance>(request, response), tAccountBalance); 
		model.addAttribute("page", page);
		tAccountBalance.setCreateTime(tAccountBalance.getCreateTime().substring(0, 7));//不加后面的"-%",用于桌面查询框显示
		model.addAttribute("tAccountBalance", tAccountBalance);
		return "modules/accountbalance/tAccountBalanceList";
	}

	@RequiresPermissions("accountbalance:tAccountBalance:view")
	@RequestMapping(value = "form")
	public String form(TAccountBalance tAccountBalance, Model model) {
		model.addAttribute("tAccountBalance", tAccountBalance);
		return "modules/accountbalance/tAccountBalanceForm";
	}

	@RequiresPermissions("accountbalance:tAccountBalance:edit")
	@RequestMapping(value = "save")
	public String save(TAccountBalance tAccountBalance, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, tAccountBalance)){
			return form(tAccountBalance, model);
		}
		tAccountBalanceService.save(tAccountBalance);
		addMessage(redirectAttributes, "保存科目余额成功");
		return "redirect:"+Global.getAdminPath()+"/accountbalance/tAccountBalance/?repage";
	}
	
	@RequiresPermissions("accountbalance:tAccountBalance:edit")
	@RequestMapping(value = "delete")
	public String delete(TAccountBalance tAccountBalance, RedirectAttributes redirectAttributes) {
		tAccountBalanceService.delete(tAccountBalance);
		addMessage(redirectAttributes, "删除科目余额成功");
		return "redirect:"+Global.getAdminPath()+"/accountbalance/tAccountBalance/?repage";
	}
	
	
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String extId, HttpServletResponse response,HttpServletRequest request) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<TAccountBalance> list = tAccountBalanceService.findList(new TAccountBalance());
		for (int i=0; i<list.size(); i++){
			TAccountBalance e = list.get(i);
			if (StringUtils.isBlank(extId) || (extId!=null && !extId.equals(e.getId()) && e.getParentIds().indexOf(","+extId+",")==-1)){
				Map<String, Object> map = Maps.newHashMap();
				map.put("id", e.getId());
				map.put("pId", e.getParent().getId());
				map.put("name", e.getSubjectName()+"(编号："+e.getSubjectNumber()+")");
				mapList.add(map);
			}
		}
		return mapList;
	}
	

}
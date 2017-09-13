/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.wind.web.creditchecking;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mangofactory.swagger.annotations.ApiIgnore;
import com.wanfin.fpd.common.config.Global;
import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.utils.StringUtils;
import com.wanfin.fpd.common.web.BaseController;
import com.wanfin.fpd.modules.employee.entity.TEmployee;
import com.wanfin.fpd.modules.employee.service.TEmployeeService;
import com.wanfin.fpd.modules.wind.adapter.IeAdapter;
import com.wanfin.fpd.modules.wind.adapter.IeAdapterDB;
import com.wanfin.fpd.modules.wind.adapter.IeAdapterType;
import com.wanfin.fpd.modules.wind.adapter.impl.CCheckingAdapter;
import com.wanfin.fpd.modules.wind.entity.creditchecking.TCreditChecking;
import com.wanfin.fpd.modules.wind.service.creditchecking.TCreditCheckingService;

/**
 * 征信Controller
 * @author chenh
 * @version 2016-05-30
 */
@ApiIgnore
@Controller
@RequestMapping(value = "${adminPath}/wind/creditchecking/tCreditChecking")
public class TCreditCheckingController extends BaseController {

	@Autowired
	private TCreditCheckingService tCreditCheckingService;
	
	@Autowired
	private TEmployeeService tEmployeeSysService;
	
	@ModelAttribute
	public TCreditChecking get(@RequestParam(required=false) String id) {
		TCreditChecking entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = tCreditCheckingService.get(id);
		}
		if (entity == null){
			entity = new TCreditChecking();
		}
		return entity;
	}
	
	@RequestMapping(value = {"list", ""})
	public String list(TCreditChecking tCreditChecking, HttpServletRequest request, HttpServletResponse response, Model model) {
		List<IeAdapter> ieAdapters = new ArrayList<IeAdapter>();
		List<IeAdapterDB> ieAdapterDBs = new ArrayList<IeAdapterDB>();
		List<IeAdapterType> ieAdapterTypes = new ArrayList<IeAdapterType>();
		if(tCreditChecking == null){
			tCreditChecking = new TCreditChecking();
		}
		if(StringUtils.isEmpty(tCreditChecking.getDb())){
			tCreditChecking.setDb(IeAdapterDB.DB_WZ.getKey());
		}
		
		if(StringUtils.isNotEmpty(tCreditChecking.getType())){
			ieAdapterTypes = IeAdapterType.getIeAdapterTypeByKey(tCreditChecking.getType());
			for (IeAdapterType ieAdapterType : ieAdapterTypes) {
				ieAdapterDBs.add(ieAdapterType.getDb());
			}
			ieAdapters = IeAdapter.getIeAdapterByType(IeAdapterType.getIeAdapterTypeByDBKey(IeAdapterDB.getIeAdapterDBByKey(tCreditChecking.getDb()), tCreditChecking.getType()));
		}
		
		Page<TCreditChecking> page = tCreditCheckingService.findPageByRelId(new Page<TCreditChecking>(request, response), tCreditChecking); 
		
		model.addAttribute("page", page);
		model.addAttribute("ieAdapters", ieAdapters);
		model.addAttribute("ieAdapterDBs", ieAdapterDBs);
		model.addAttribute("ieAdapterTypes", ieAdapterTypes);
		model.addAttribute("tCreditChecking", tCreditChecking);
		return "modules/wind/creditchecking/tCreditCheckingList";
	}

	@RequestMapping(value = {"listWZ", ""})
	public String listWZ(TCreditChecking tCreditChecking, HttpServletRequest request, HttpServletResponse response, Model model) {
		tCreditChecking.setDb(IeAdapterDB.DB_PY.getKey());
		Page<TCreditChecking> page = tCreditCheckingService.findPageByRelId(new Page<TCreditChecking>(request, response), tCreditChecking); 
		
		model.addAttribute("page", page);
		model.addAttribute("tCreditChecking", tCreditChecking);
		return "modules/wind/creditchecking/tCreditCheckingList";
	}
	
	@RequestMapping(value = {"listPY", ""})
	public String listPY(TCreditChecking tCreditChecking, HttpServletRequest request, HttpServletResponse response, Model model) {
		tCreditChecking.setDb(IeAdapterDB.DB_PY.getKey());
		Page<TCreditChecking> page = tCreditCheckingService.findPageByRelId(new Page<TCreditChecking>(request, response), tCreditChecking); 
		
		model.addAttribute("page", page);
		model.addAttribute("tCreditChecking", tCreditChecking);
		return "modules/wind/creditchecking/tCreditCheckingList";
	}

	@RequestMapping(value = {"listQH", ""})
	public String listQH(TCreditChecking tCreditChecking, HttpServletRequest request, HttpServletResponse response, Model model) {
		tCreditChecking.setDb(IeAdapterDB.DB_QH.getKey());
		Page<TCreditChecking> page = tCreditCheckingService.findPageByRelId(new Page<TCreditChecking>(request, response), tCreditChecking); 
		
		model.addAttribute("page", page);
		model.addAttribute("tCreditChecking", tCreditChecking);
		return "modules/wind/creditchecking/tCreditCheckingList";
	}
	
	@RequiresPermissions("wind:creditchecking:tCreditChecking:view")
	@RequestMapping(value = "form")
	public String form(TCreditChecking tCreditChecking, Model model) {
		model.addAttribute("tCreditChecking", tCreditChecking);
		return "modules/wind/creditchecking/tCreditCheckingForm";
	}

	@SuppressWarnings({ "rawtypes" })
	@RequestMapping(value = "view")
	public String view(TCreditChecking tCreditChecking, Model model, RedirectAttributes redirectAttributes) {
		if(tCreditChecking != null){
			TCreditChecking curTCreditChecking = null;
			if(StringUtils.isNotEmpty(tCreditChecking.getId())){
				curTCreditChecking = tCreditCheckingService.get(tCreditChecking.getId());
				tCreditChecking.setType(curTCreditChecking.getType());
				tCreditChecking.setTypeId(curTCreditChecking.getTypeId());
			}
			
			if(StringUtils.isNotEmpty(tCreditChecking.getDb()) && StringUtils.isNotEmpty(tCreditChecking.getType()) && StringUtils.isNotEmpty(tCreditChecking.getTypeId())){
				List<TCreditChecking> tCreditCheckings = tCreditCheckingService.findListByRelId(tCreditChecking); 
				
				CCheckingAdapter ccheckingAdapter = new CCheckingAdapter();
				List<CCheckingAdapter> ccheckingAdapters = new ArrayList<CCheckingAdapter>();
				if(curTCreditChecking != null){
					tCreditChecking = curTCreditChecking;
				}

				if(tCreditChecking != null){
					ccheckingAdapter = new CCheckingAdapter(tCreditChecking);
				}
				
				if((tCreditCheckings != null) && (tCreditCheckings.size() > 0)){
					ccheckingAdapters = CCheckingAdapter.convertToCCheckingAdapter(tCreditCheckings);
				}else{
					addMessage(redirectAttributes, "没有查询结果！");
				}
				model.addAttribute("ccheckingAdapter", ccheckingAdapter);
				model.addAttribute("ccheckingAdapters", ccheckingAdapters);
				
				model.addAttribute("tCreditChecking", tCreditChecking);
				model.addAttribute("tCreditCheckings", tCreditCheckings);
				return "modules/wind/creditchecking/tCreditCheckingView";
			}
		}
		addMessage(redirectAttributes, "参数有误！");
		return "redirect:"+Global.getAdminPath()+"/wind/creditchecking/tCreditChecking/?repage";
	}

	@SuppressWarnings({ "rawtypes" })
	@RequestMapping(value = "viewPersion")
	public String viewPersion(TEmployee tEmployee, Model model, RedirectAttributes redirectAttributes) {
		if(tEmployee != null){
			if(StringUtils.isNotEmpty(tEmployee.getId())){
				List<CCheckingAdapter> ccheckingAdapters = new ArrayList<CCheckingAdapter>();
				tEmployee = tEmployeeSysService.get(tEmployee.getId());
				if((tEmployee != null) && StringUtils.isNotEmpty(tEmployee.getCardNum())){
					TCreditChecking tCreditChecking = new TCreditChecking();
					tCreditChecking.setTypeId(tEmployee.getCardNum());
					List<TCreditChecking> tCreditCheckings = tCreditCheckingService.findListByRelId(tCreditChecking);
					
					if((tCreditCheckings != null) && (tCreditCheckings.size() > 0)){
						ccheckingAdapters = CCheckingAdapter.convertToCCheckingAdapter(tCreditCheckings);
					}else{
						addMessage(redirectAttributes, "没有查询结果！");
					}
				}else{
					addMessage(redirectAttributes, "没有查询结果！");
				}
				model.addAttribute("tEmployee", tEmployee);
				model.addAttribute("ccheckingAdapters", ccheckingAdapters);
				return "modules/wind/creditchecking/tCreditCheckingViewtPerson";
			}
		}
		addMessage(redirectAttributes, "参数有误！");
		return "redirect:"+Global.getAdminPath()+"/wind/creditchecking/tCreditChecking/?repage";
	}

	@RequiresPermissions("wind:creditchecking:tCreditChecking:edit")
	@RequestMapping(value = "save")
	public String save(TCreditChecking tCreditChecking, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, tCreditChecking)){
			return form(tCreditChecking, model);
		}
		tCreditCheckingService.save(tCreditChecking);
		addMessage(redirectAttributes, "保存征信成功");
		return "redirect:"+Global.getAdminPath()+"/wind/creditchecking/tCreditChecking/?repage";
	}
	
	@RequiresPermissions("wind:creditchecking:tCreditChecking:edit")
	@RequestMapping(value = "delete")
	public String delete(TCreditChecking tCreditChecking, RedirectAttributes redirectAttributes) {
		tCreditCheckingService.delete(tCreditChecking);
		addMessage(redirectAttributes, "删除征信成功");
		return "redirect:"+Global.getAdminPath()+"/wind/creditchecking/tCreditChecking/?repage";
	}
	
//	@ResponseBody
//	@RequestMapping(value = "ajaxInitDB")
//	public Boolean ajaxInitDB() {
//		return tCreditCheckingService.init();
//	}

}
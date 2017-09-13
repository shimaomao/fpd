/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.looploan.web;

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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mangofactory.swagger.annotations.ApiIgnore;
import com.wanfin.fpd.common.config.Cons.FModel;
import com.wanfin.fpd.common.config.Global;
import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.utils.IdGen;
import com.wanfin.fpd.common.utils.StringUtils;
import com.wanfin.fpd.common.web.BaseController;
import com.wanfin.fpd.modules.files.service.TContractFilesService;
import com.wanfin.fpd.modules.form.entity.tpl.DfFormTpl;
import com.wanfin.fpd.modules.form.service.tpl.DfFormTplService;
import com.wanfin.fpd.modules.looploan.entity.TLoopLoan;
import com.wanfin.fpd.modules.looploan.service.TLoopLoanService;

/**
 * 申请授信Controller
 * @author zzm
 * @version 2016-03-17c	
 */
@ApiIgnore
@Controller
@RequestMapping(value = "${adminPath}/looploan/tLoopLoan")
public class TLoopLoanController extends BaseController {

	@Autowired
	private TLoopLoanService tLoopLoanService;
	@Autowired
	private TContractFilesService tContractFilesService;
	
	@Autowired
	private DfFormTplService dfFormTplService;
	
	@ModelAttribute
	public TLoopLoan get(@RequestParam(required=false) String id) {
		TLoopLoan entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = tLoopLoanService.get(id);
		}
		if (entity == null){
			entity = new TLoopLoan();
		}
		return entity;
	}
	
	@RequiresPermissions("looploan:tLoopLoan:view")
	@RequestMapping(value = {"list", ""})
	public String list(TLoopLoan tLoopLoan, HttpServletRequest request, HttpServletResponse response, Model model) {
		/*tLoopLoan.setProduct(new TProduct((String)UserUtils.getCache(UserUtils.CACHE_SYSCODE)));  by zzm 20160520 授信暂定不放大产品里面*/
		Page<TLoopLoan> page = tLoopLoanService.findPage(new Page<TLoopLoan>(request, response), tLoopLoan); 
		model.addAttribute("tLoopLoan", tLoopLoan);
		model.addAttribute("page", page);
		return "modules/looploan/tLoopLoanList";
	}

	@RequiresPermissions("looploan:tLoopLoan:view")
	@RequestMapping(value = "form")
	public String form(TLoopLoan tLoopLoan, Model model) {
		if (StringUtils.isBlank(tLoopLoan.getId())){
			//新增是设置一个临时id, 以"new_"开头表示
			tLoopLoan.setId("new_"+IdGen.uuid());
		}
		
		FModel fm = FModel.M_LOOPLOAN;
		DfFormTpl dfFormTpl = dfFormTplService.get("c72b255fe1fb44e98ab6690c58cb74b3");
		model.addAttribute("action", fm.getAction());
		model.addAttribute("dfFormTpl", dfFormTpl);
		model.addAttribute("data", tLoopLoan);
		return "modules/looploan/tLoopLoanForm";
	}

	@RequiresPermissions("looploan:tLoopLoan:edit")
	@RequestMapping(value = "save")
	public String save(TLoopLoan tLoopLoan, Model model, String[] title,
			HttpServletRequest request, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, tLoopLoan)){
			return form(tLoopLoan, model);
		}
		tLoopLoanService.save(tLoopLoan);
		String message = "保存授信信息成功!";
			
		addMessage(redirectAttributes, message);
		return "redirect:"+Global.getAdminPath()+"/looploan/tLoopLoan/?repage";
	}
	
	@RequiresPermissions("looploan:tLoopLoan:edit")
	@RequestMapping(value = "delete")
	public String delete(TLoopLoan tLoopLoan, RedirectAttributes redirectAttributes) {
		tLoopLoanService.delete(tLoopLoan);
		addMessage(redirectAttributes, "删除授信信息成功");
		return "redirect:"+Global.getAdminPath()+"/looploan/tLoopLoan/?repage";
	}

	/**
	 * @Description 跳转到放款审批页面
	 * @param tLoanContract
	 * @param model
	 * @author zzm 
	 * @date 2016-4-8 上午9:25:13  
	 */
	@RequestMapping(value = "auditForm")
	public String auditForm(TLoopLoan tLoopLoan, Model model,HttpServletRequest request) {
		DfFormTpl dfFormTpl = dfFormTplService.get("c72b255fe1fb44e98ab6690c58cb74b3");
		model.addAttribute("dfFormTpl", dfFormTpl);
		model.addAttribute("data", tLoopLoan);
		model.addAttribute("act", tLoopLoan.getAct());
		return "modules/looploan/auditForm";
	}
	
	/**
	 * 工单执行（完成任务）
	 * @param tLoopLoan
	 * @param model
	 * @return
	 */
	@RequiresPermissions("looploan:tLoopLoan:edit")
	@RequestMapping(value = "complete")
	public String complete(TLoopLoan tLoopLoan, Model model, RedirectAttributes redirectAttributes) {
		if(StringUtils.isBlank(tLoopLoan.getProcInsId())){
			String procInsId = tLoopLoanService.startProcess(tLoopLoan);
			tLoopLoan.getAct().setProcInsId(procInsId);
			tLoopLoanService.auditSave(tLoopLoan);
			addMessage(redirectAttributes, "单据'" + tLoopLoan.getLoopNumber() + "'提交成功");
		}else{
			if (StringUtils.isBlank(tLoopLoan.getAct().getFlag())
					|| StringUtils.isBlank(tLoopLoan.getAct().getComment())){
				addMessage(model, "请填写审核意见。");
				return form(tLoopLoan, model);
			}
			tLoopLoanService.auditSave(tLoopLoan);
			addMessage(redirectAttributes, "单据'" + tLoopLoan.getLoopNumber() + "'审批成功");
		}
		return "redirect:" + adminPath + "/act/task/todo/";
	}

	/**
	 * 移除数据关联
	 * 功能：把表table里id在ids里的数据逻辑删除
	 * @param request
	 * @param ids
	 * @param table
	 * @param column
	 * @return
	 */
	@RequestMapping(value = "remmoveBanding", method = RequestMethod.POST)
	public @ResponseBody Map<String,Object> remmoveBanding(HttpServletRequest request,
			@RequestParam("ids[]") String[] ids,
			@RequestParam("table") String table) {
		Map<String,Object> map = new HashMap<String, Object>();
		try {
			tLoopLoanService.remmoveBanding(ids,table);
			map.put("status", 1);
			map.put("message", "删除成功");
		} catch (Exception e) {
			map.put("status", 0);
			map.put("message", e.getMessage());
			e.printStackTrace();
		}
		     
		return map;
	}
	
	/**
	 * 授信状态
	 * */
	@ResponseBody
	@RequiresPermissions("contract:tLoanContract:edit")
	@RequestMapping(value = "getStatus")
	public String getStatus(TLoopLoan tLoopLoan, Model model, RedirectAttributes redirectAttributes) {
		return tLoopLoan.getStatus();
	}
}
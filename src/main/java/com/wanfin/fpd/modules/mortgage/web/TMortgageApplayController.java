/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.mortgage.web;

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
import com.wanfin.fpd.common.config.Cons;
import com.wanfin.fpd.common.config.Cons.FModel;
import com.wanfin.fpd.common.config.Global;
import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.utils.StringUtils;
import com.wanfin.fpd.common.web.BaseController;
import com.wanfin.fpd.modules.act.entity.Act;
import com.wanfin.fpd.modules.form.entity.tpl.DfFormTpl;
import com.wanfin.fpd.modules.form.service.tpl.DfFormTplService;
import com.wanfin.fpd.modules.mortgage.entity.MortgageContract;
import com.wanfin.fpd.modules.mortgage.entity.TMortgageApplay;
import com.wanfin.fpd.modules.mortgage.service.MortgageContractService;
import com.wanfin.fpd.modules.mortgage.service.TMortgageApplayService;
import com.wanfin.fpd.modules.pledge.entity.PledgeContract;
import com.wanfin.fpd.modules.pledge.service.PledgeContractService;

/**
 * 押品借出审批Controller
 * 
 * @author lzj
 * @version 2016-09-23
 */
@ApiIgnore
@Controller
@RequestMapping(value = "${adminPath}/mortgage/tMortgageApplay")
public class TMortgageApplayController extends BaseController {

	@Autowired
	private TMortgageApplayService tMortgageApplayService;
	@Autowired
	private DfFormTplService dfFormTplService;
	@Autowired
	private MortgageContractService mortgageContractService;
	@Autowired
	private PledgeContractService pledgeContractService;

	@ModelAttribute
	public TMortgageApplay get(@RequestParam(required = false) String id) {
		TMortgageApplay entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = tMortgageApplayService.get(id);
		}
		if (entity == null) {
			entity = new TMortgageApplay();
		}
		return entity;
	}

	@RequiresPermissions("mortgage:tMortgageApplay:view")
	@RequestMapping(value = { "list", "" })
	public String list(TMortgageApplay tMortgageApplay, HttpServletRequest request, HttpServletResponse response,
			Model model) {
		Page<TMortgageApplay> page = tMortgageApplayService.findPage(new Page<TMortgageApplay>(request, response),
				tMortgageApplay);
		model.addAttribute("page", page);
		model.addAttribute("tMortgageApplay", tMortgageApplay);
		return "modules/mortgage/tMortgageApplayList";
	}

	// 在流程设置里面用到
	@RequestMapping(value = "mortgageAuditForm")
	public String mortgageAuditForm(TMortgageApplay tMortgageApplay, Model model) {
		String forward="modules/mortgage/mortgageAuditForm";
		FModel fm = null;
		DfFormTpl dfFormTpl = null;
		MortgageContract mc = null;
		Act act=tMortgageApplay.getAct();
		mc = mortgageContractService.get(tMortgageApplay.getId());
		if (mc != null) {// 抵押
			fm = FModel.M_MORTGAGE_CONTRACT;
			dfFormTpl = dfFormTplService.get(Cons.FormTplId.B_CONTRACT_MORTGAGE_ID);
			tMortgageApplay.setApplayType(Cons.MortgageApplayType.MORTGAGE);
			model.addAttribute("data", mc);
			model.addAttribute("type", "mortgage");
		} else {// 质押
			PledgeContract pc=pledgeContractService.get(tMortgageApplay.getId());
			fm = FModel.M_PLEDGE_CONTRACT;
			dfFormTpl = dfFormTplService.get(Cons.FormTplId.B_CONTRACT_PLEDGE_ID);
			tMortgageApplay.setApplayType(Cons.MortgageApplayType.PLEDGE);
			model.addAttribute("data", pc);
			model.addAttribute("type", "pledge");
			forward="modules/pledge/pledgeAuditForm";
		}
		if (StringUtils.isNotBlank(tMortgageApplay.getAct().getProcInsId())) {
			tMortgageApplay = tMortgageApplayService.getPojoByContract(tMortgageApplay.getId());// 根据质押品合同ID,最新的一条
			tMortgageApplay.setAct(act);
		}
		model.addAttribute("action", fm.getAction());
		model.addAttribute("dfFormTpl", dfFormTpl);
		model.addAttribute("tMortgageApplay", tMortgageApplay);
		return forward;
	}

	@RequiresPermissions("mortgage:tMortgageApplay:view")
	@RequestMapping(value = "form")
	public String form(TMortgageApplay tMortgageApplay, Model model) {
		model.addAttribute("tMortgageApplay", tMortgageApplay);
		return "modules/mortgage/tMortgageApplayForm";
	}

	@RequiresPermissions("mortgage:tMortgageApplay:edit")
	@RequestMapping(value = "save")
	public String save(TMortgageApplay tMortgageApplay, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, tMortgageApplay)) {
			return form(tMortgageApplay, model);
		}
		tMortgageApplayService.save(tMortgageApplay);
		addMessage(redirectAttributes, "保存押品借出审批成功");
		return "redirect:" + Global.getAdminPath() + "/mortgage/tMortgageApplay/?repage";
	}

	/**
	 * @Description 押品/质押品 审批
	 * @param id
	 * @param businessTable
	 * @return
	 * @author zlj
	 */
	/* @RequiresPermissions("mortgage:tMortgageApplay:view") */
	@RequestMapping(value = "saveAudit")
	@ResponseBody
	public String saveAudit(TMortgageApplay tMortgageApplay, Model model, RedirectAttributes redirectAttributes) {
		if (StringUtils.isBlank(tMortgageApplay.getAct().getFlag())
				|| StringUtils.isBlank(tMortgageApplay.getAct().getComment())) {
			addMessage(model, "请填写及审核意见。");
			return mortgageAuditForm(tMortgageApplay, model);
		} else {
			tMortgageApplayService.audit(tMortgageApplay);
		}
		addMessage(redirectAttributes, "处理成功");
		return "true";
	}

	@RequiresPermissions("mortgage:tMortgageApplay:edit")
	@RequestMapping(value = "delete")
	public String delete(TMortgageApplay tMortgageApplay, RedirectAttributes redirectAttributes) {
		tMortgageApplayService.delete(tMortgageApplay);
		addMessage(redirectAttributes, "删除押品借出审批成功");
		return "redirect:" + Global.getAdminPath() + "/mortgage/tMortgageApplay/?repage";
	}

}
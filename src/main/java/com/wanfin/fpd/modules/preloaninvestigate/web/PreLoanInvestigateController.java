/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.preloaninvestigate.web;

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
import com.wanfin.fpd.common.config.Global;
import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.service.ServiceException;
import com.wanfin.fpd.common.utils.StringUtils;
import com.wanfin.fpd.common.web.BaseController;
import com.wanfin.fpd.modules.act.service.ActTaskService;
import com.wanfin.fpd.modules.preloaninvestigate.entity.PreLoanInvestigate;
import com.wanfin.fpd.modules.preloaninvestigate.service.PreLoanInvestigateService;

/**
 * 立项调查Controller
 * @author zzm
 * @version 2016-06-13
 */
@ApiIgnore
@Controller
@RequestMapping(value = "${adminPath}/preloaninvestigate/preLoanInvestigate")
public class PreLoanInvestigateController extends BaseController {

	@Autowired
	private PreLoanInvestigateService preLoanInvestigateService;
	@Autowired 
	private ActTaskService actTaskService;
	
	
	
	@ModelAttribute
	public PreLoanInvestigate get(@RequestParam(required=false) String id) {
		PreLoanInvestigate entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = preLoanInvestigateService.get(id);
		}
		if (entity == null){
			entity = new PreLoanInvestigate();
		}
		return entity;
	}
	
	@RequiresPermissions("preloaninvestigate:preLoanInvestigate:view")
	@RequestMapping(value = {"list", ""})
	public String list(PreLoanInvestigate preLoanInvestigate, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<PreLoanInvestigate> page = preLoanInvestigateService.findPage(new Page<PreLoanInvestigate>(request, response), preLoanInvestigate); 
		model.addAttribute("page", page);
		model.addAttribute("preLoanInvestigate", preLoanInvestigate);
		return "modules/preloaninvestigate/preLoanInvestigateList";
	}

	@RequiresPermissions("preloaninvestigate:preLoanInvestigate:view")
	@RequestMapping(value = "form")
	public String form(PreLoanInvestigate preLoanInvestigate, Model model) {
		model.addAttribute("preLoanInvestigate", preLoanInvestigate);
		return "modules/preloaninvestigate/preLoanInvestigateForm";
	}

	
	//在流程设置里面用到
	@RequestMapping(value = "auditForm")
	public String auditForm(PreLoanInvestigate preLoanInvestigate, Model model,HttpServletRequest request) {
		//User currentUser = UserUtils.getUser();//当前登录人
		if(actTaskService.checkIsEnd(preLoanInvestigate.getAct())){//判断是否为最后节点
			preLoanInvestigate.getAct().setIsEnd("yes");
		}
		
		model.addAttribute("preLoanInvestigate",preLoanInvestigate);
		return "modules/preloaninvestigate/preLoanAuditForm";
	}
	
	
	
	@RequiresPermissions("preloaninvestigate:preLoanInvestigate:edit")
	@RequestMapping(value = "save")
	public String save(PreLoanInvestigate preLoanInvestigate, Model model, RedirectAttributes redirectAttributes) {
		if(preLoanInvestigate.getId()==null||"".equals(preLoanInvestigate.getId())){
			preLoanInvestigate.setStatus(Cons.PreLoanInvesStatus.TO_REVIEW);   
		}
		if (!beanValidator(model, preLoanInvestigate)){
			return form(preLoanInvestigate, model);
		}
		try {
			preLoanInvestigateService.save(preLoanInvestigate);
			addMessage(redirectAttributes, "保存立项调查成功");
		} catch (ServiceException e) {
			e.printStackTrace();
			addMessage(redirectAttributes, "保存失败："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/preloaninvestigate/preLoanInvestigate/?repage";
	}
	
	@RequiresPermissions("preloaninvestigate:preLoanInvestigate:edit")
	@RequestMapping(value = "delete")
	public String delete(PreLoanInvestigate preLoanInvestigate, RedirectAttributes redirectAttributes) {
		preLoanInvestigateService.delete(preLoanInvestigate);
		addMessage(redirectAttributes, "删除立项调查成功");
		return "redirect:"+Global.getAdminPath()+"/preloaninvestigate/preLoanInvestigate/?repage";
	}

	/**
	 * 立项调查申请时查看状态
	 * @param id
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions("preloaninvestigate:preLoanInvestigate:view")
	@RequestMapping(value = "getInvestiStatus")
	public String getInvestiStatus(String id, Model model, RedirectAttributes redirectAttributes) {
		return get(id).getStatus();
	}
	 
	
	
	/**
	 * @Description 工单执行（完成任务）
	 * @param tLoanContract
	 * @param model
	 * @param redirectAttributes
	 * @param request
	 * @return
	 * @author Chenh 
	 * @date 2016年3月30日 上午11:03:10
	 */
	@RequestMapping(value = "complete")
	public String complete(PreLoanInvestigate preLoanInvestigate, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		if(StringUtils.isBlank(preLoanInvestigate.getProcInsId())){
			preLoanInvestigateService.startProcess(preLoanInvestigate);
			addMessage(redirectAttributes, "立项申请'" + preLoanInvestigate.getInvestigateNumber() + "'提交成功");
		}else{
			if (StringUtils.isBlank(preLoanInvestigate.getAct().getFlag())
					|| StringUtils.isBlank(preLoanInvestigate.getAct().getComment())){
				addMessage(model, "请填写审核意见。");
				return form(preLoanInvestigate, model);
			}
			preLoanInvestigateService.auditSave(preLoanInvestigate);
			addMessage(redirectAttributes, "立项申请'" + preLoanInvestigate.getInvestigateNumber() + "'审批成功");
		}
		return "redirect:" + adminPath + "/act/task/todo/";
	}
	
	
}
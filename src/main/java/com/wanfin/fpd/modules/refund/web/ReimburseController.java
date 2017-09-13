/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.refund.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.SystemPropertyUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mangofactory.swagger.annotations.ApiIgnore;
import com.wanfin.fpd.common.config.Cons;
import com.wanfin.fpd.common.config.Global;
import com.wanfin.fpd.common.config.SvalBase;
import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.web.BaseController;
import com.wanfin.fpd.common.utils.StringUtils;
import com.wanfin.fpd.modules.contract.entity.TLoanContract;
import com.wanfin.fpd.modules.contract.service.TLoanContractService;
import com.wanfin.fpd.modules.customerintent.entity.TCustomerIntent;
import com.wanfin.fpd.modules.employee.entity.TEmployee;
import com.wanfin.fpd.modules.employee.service.TEmployeeService;
import com.wanfin.fpd.modules.preloaninvestigate.entity.PreLoanInvestigate;
import com.wanfin.fpd.modules.refund.entity.Reimburse;
import com.wanfin.fpd.modules.refund.service.ReimburseService;
import com.wanfin.fpd.modules.sys.utils.UserUtils;

/**
 * 申请退款Controller
 * 
 * @author srf
 * @version 2016-04-06
 */
@ApiIgnore
@Controller
@RequestMapping(value = "${adminPath}/refund/reimburse")
public class ReimburseController extends BaseController {

	//客户信息
	@Autowired 
	private TEmployeeService tEmployeeService;
	// 合同
	@Autowired
	private TLoanContractService tLoanContractService;
	//申请退款
	@Autowired
	private ReimburseService reimburseService;

	@ModelAttribute
	public Reimburse get(@RequestParam(required = false) String id) {
		Reimburse entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = reimburseService.get(id);
		}
		if (entity == null) {
			entity = new Reimburse();
		}
		return entity;
	}
    /**
     *    贷后管理-业务变更列表
     * @param tLoanContract
     * @param request
     * @param response
     * @param model
     * @return
     */
	@RequiresPermissions("refund:reimburse:view")
	@RequestMapping(value = { "list", "" })
	public String list(TLoanContract tLoanContract, HttpServletRequest request, HttpServletResponse response,
			Model model) {
		tLoanContract.setStatus(Cons.LoanContractStatus.UN_CLEARED);//CLEARED
		tLoanContract.setProductId((String)UserUtils.getCache(UserUtils.CACHE_SYSCODE));
		Page<TLoanContract> page = tLoanContractService.findRefundList(new Page<TLoanContract>(request, response),
				tLoanContract);
		model.addAttribute("page", page);
		model.addAttribute("loanContract", tLoanContract);

		return "modules/refund/reimburseLoanContList";
	}
	
	/**
	 *   贷后管理-退费管理列表
	 * @param request
	 * @param response
	 * @param model
	 * @param contractId
	 * @param reimburse
	 * @return
	 */
	@RequiresPermissions("refund:reimburse:view")
	@RequestMapping(value = "viewList")
	public String viewList(HttpServletRequest request, HttpServletResponse response, Model model, @RequestParam(required = false) String contractId, Reimburse reimburse){
		if(contractId != null && contractId.length()>0){
			reimburse.setLoanContractId(contractId);
		}
		Page<Reimburse> page = reimburseService.findPage(new Page<Reimburse>(request, response), reimburse);
		model.addAttribute("page", page);
		model.addAttribute("reimburse", reimburse);
		String isClose = request.getParameter("isClose");
		if(isClose!=null&&!"".equals(isClose)){
			model.addAttribute("isClose", true);
		}
		
		return "modules/refund/reimburseList";
	}
	
	/**
	 *  贷后管理-退费管理-查看详情
	 * @param request
	 * @param response
	 * @param model
	 * @param id
	 * @return
	 */
	@RequiresPermissions("refund:reimburse:view")
	@RequestMapping(value = "view")
	public String view(HttpServletRequest request, HttpServletResponse response, Model model, @RequestParam(required = false) String id){
		Reimburse entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = reimburseService.get(id);
		}
		if (entity == null) {
			entity = new Reimburse();
		}
		//合同信息 t_loan_contract
		TLoanContract loanContract = tLoanContractService.get(entity.getLoanContractId());
		model.addAttribute("loanContract", loanContract);
		model.addAttribute("reimburse", entity);
		
		return "modules/refund/reimburseView";
	}
	/**
	 *  贷后管理-业务变更、退费申请界面
	 * @param request
	 * @param response
	 * @param model
	 * @param contractId
	 * @param reimburse
	 * @return
	 */
	@RequiresPermissions("refund:reimburse:view_re")
	@RequestMapping(value = "form")
	public String form(HttpServletRequest request, HttpServletResponse response, Model model, @RequestParam(required = false) String contractId, Reimburse reimburse) {
		if(StringUtils.isBlank(reimburse.getCustomerName())){
			reimburse.setCustomerName(UserUtils.getUser().getName());
		}
		//合同信息 t_loan_contract
		TLoanContract loanContract = tLoanContractService.get(contractId);
		model.addAttribute("reimburse", reimburse);
		model.addAttribute("loanContract", loanContract);
		return "modules/refund/reimburseForm";
	}

	/**
	 *  贷后管理-业务变更、退费申请保存方法
	 * @param request
	 * @param response
	 * @param model
	 * @param contractId
	 * @param reimburse
	 * @return
	 */
	@RequiresPermissions("refund:reimburse:edit")
	@RequestMapping(value = "save")
	@ResponseBody
	public Map<String, Object> save(HttpServletRequest request, HttpServletResponse response, Model model, Reimburse reimburse) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put(SvalBase.JsonKey.KEY_STATUS, true);
		if (!beanValidator(model, reimburse)) {//数据校验失败
			result.put(SvalBase.JsonKey.KEY_MSG, "请正确填写内容！");
			return result;
		}
		//保存内容并启动流程
		reimburseService.save(reimburse);
		
		result.put(SvalBase.JsonKey.KEY_MSG, "保存成功！");
		return result;
	}
     /**
      * 退费删除
      * @param reimburse
      * @param redirectAttributes
      * @return
      */
	@RequiresPermissions("refund:reimburse:edit")
	@RequestMapping(value = "delete")
	public String delete(Reimburse reimburse, RedirectAttributes redirectAttributes) {
		reimburseService.delete(reimburse);
		addMessage(redirectAttributes, "删除申请退款成功");
		return "redirect:" + Global.getAdminPath() + "/refund/reimburse/?repage";
	}

	/**
	 * 查看状态
	 * @param id
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getReimburseStatus")
	public String getReimburseStatus(String id, Model model, RedirectAttributes redirectAttributes) {
		return get(id).getStatus();
	}
	
	//调整至审批界面
		@RequestMapping(value = "auditForm")
		public String auditForm(Reimburse reimburse, Model model,HttpServletRequest request) {
			TLoanContract loanContract = tLoanContractService.get(reimburse.getLoanContractId());
			model.addAttribute("reimburse", reimburse);
			model.addAttribute("loanContract", loanContract);
			return "modules/refund/reimburseAudit";
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
	public String complete(Reimburse reimburse, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		System.out.println(reimburse.getProcInsId());
		if(StringUtils.isBlank(reimburse.getProcInsId())){
			reimburseService.startProcess(reimburse);
			addMessage(redirectAttributes, "退费申请提交成功");
		}else{
			if (StringUtils.isBlank(reimburse.getAct().getFlag())
					|| StringUtils.isBlank(reimburse.getAct().getComment())){
				addMessage(model, "请填写审核意见。");
				return auditForm(reimburse, model,request);
			}
			reimburseService.auditSave(reimburse);
			addMessage(redirectAttributes, "立项申请审批成功");
		}
		return "redirect:" + adminPath + "/act/task/todo/";
	}
	
	
	//调至财务退款界面
	@RequestMapping(value = "refund")
	public String refund(Reimburse reimburse, Model model,HttpServletRequest request) {
		model.addAttribute("reimburse", reimburse);
		return "modules/refund/reimburserefund";
	}
	
	
	@RequestMapping(value = "saveRefund")
	public String saveRefund(Reimburse reimburse, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		reimburse.setStatus(Cons.ReimburseStatus.WITHDRAW);
		reimburseService.save(reimburse);
		addMessage(redirectAttributes, "退费成功");
		return "redirect:"+Global.getAdminPath()+"/refund/reimburse/viewList?repage&isClose=1";
	}
}
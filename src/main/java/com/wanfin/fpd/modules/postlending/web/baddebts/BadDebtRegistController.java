/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.postlending.web.baddebts;

import java.util.HashMap;
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

import com.mangofactory.swagger.annotations.ApiIgnore;
import com.wanfin.fpd.common.config.Cons;
import com.wanfin.fpd.common.config.Global;
import com.wanfin.fpd.common.config.SvalBase;
import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.utils.StringUtils;
import com.wanfin.fpd.common.web.BaseController;
import com.wanfin.fpd.modules.act.service.ActTaskService;
import com.wanfin.fpd.modules.contract.entity.TLoanContract;
import com.wanfin.fpd.modules.contract.service.TLoanContractService;
import com.wanfin.fpd.modules.postlending.entity.baddebts.BadDebtRegist;
import com.wanfin.fpd.modules.postlending.entity.fivelevel.FiveLevel;
import com.wanfin.fpd.modules.postlending.service.baddebts.BadDebtRegistService;
import com.wanfin.fpd.modules.product.entity.ProductBizCfg;
import com.wanfin.fpd.modules.product.service.ProductBizCfgService;
import com.wanfin.fpd.modules.product.service.TProductService;
import com.wanfin.fpd.modules.receivables.entity.RealIncome;
import com.wanfin.fpd.modules.receivables.entity.RepayRecord;
import com.wanfin.fpd.modules.receivables.service.RealIncomeService;
import com.wanfin.fpd.modules.receivables.service.RepayRecordService;
import com.wanfin.fpd.modules.refund.entity.Reimburse;
import com.wanfin.fpd.modules.refund.service.ReimburseService;
import com.wanfin.fpd.modules.repayplan.entity.TRepayPlan;
import com.wanfin.fpd.modules.repayplan.service.TRepayPlanService;
import com.wanfin.fpd.modules.sys.utils.UserUtils;

/**
 * 坏账处理Controller
 * 
 * @author srf
 * @version 2016-04-15
 */
@ApiIgnore
@Controller
@RequestMapping(value = "${adminPath}/postlending/baddebts/badDebtRegist")
public class BadDebtRegistController extends BaseController {
	@Autowired
	private ActTaskService actTaskService;

	// 合同
	@Autowired
	private TLoanContractService loanContractService;

	@Autowired
	private TProductService tProductService;

	@Autowired
	private BadDebtRegistService badDebtRegistService;

	@Autowired
	private TRepayPlanService tRepayPlanService;

	@Autowired
	private RepayRecordService repayRecordService;
	// 违约金，咨询费
	@Autowired
	private RealIncomeService realIncomeService;

	@Autowired
	private ReimburseService reimburseService;

	@Autowired
	private ProductBizCfgService productBizCfgService;

	@ModelAttribute
	public BadDebtRegist get(@RequestParam(required = false) String id) {
		BadDebtRegist entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = badDebtRegistService.get(id);
		}
		if (entity == null) {
			entity = new BadDebtRegist();
		}
		return entity;
	}

	@RequestMapping(value = "detail")
	public String getDetail(BadDebtRegist badDebtRegist, Model model, RedirectAttributes redirectAttributes) {
		badDebtRegist = badDebtRegistService.findList(badDebtRegist).get(0);
		// 合同信息
		TLoanContract loanContract = loanContractService.get(badDebtRegist.getLoanContractId());
		// 业务申请审批记录
		List<?> loanexaminelist = loanContractService.getExamineList(loanContract);
		// 还款计划表
		TRepayPlan tRepayPlan = new TRepayPlan();
		tRepayPlan.setLoanContractId(loanContract.getId());
		List<TRepayPlan> repayplanList = tRepayPlanService.findList(tRepayPlan);
		// 2-1 还款记录 t_repay_real
		Page<RepayRecord> pageRepayRecord = new Page<RepayRecord>();// 用于设置排序方式
		pageRepayRecord.setOrderBy("repay_date asc");
		RepayRecord repayRecord = new RepayRecord();
		repayRecord.setLoanContractId(loanContract.getId());
		repayRecord.setPage(pageRepayRecord);
		List<RepayRecord> repRecordList = repayRecordService.findList(repayRecord);
		// 2-2 逾期记录 t_repay_plan
		tRepayPlan.setIsYuQi(Cons.LoanContractStatus.OVERDUE);
		List<TRepayPlan> overdueList = tRepayPlanService.findList(tRepayPlan);
		// 3 违约记录 t_real_income
		Page<RealIncome> pageRealIncome = new Page<RealIncome>();
		pageRealIncome.setOrderBy(" pay_real_date asc");
		RealIncome realIncome = new RealIncome();
		realIncome.setPage(pageRealIncome);
		realIncome.setLoanContractId(loanContract.getId());
		List<RealIncome> realIncomeList = realIncomeService.findList(realIncome);
		// 退费记录
		Reimburse reimburse = new Reimburse();
		reimburse.setLoanContractId(loanContract.getId());
		List<Reimburse> reimburseList = reimburseService.findList(reimburse);

		// Map<String,String> map=new HashMap<String,String>();
		model.addAttribute("reimburseList", reimburseList);// 退费历史记录
		model.addAttribute("loanexaminelist", loanexaminelist);// 审批历史记录
		model.addAttribute("repayplanList", repayplanList);// 还款计划表
		model.addAttribute("repRecordList", repRecordList); // 2-1 还款记录
		model.addAttribute("overdueList", overdueList); // 2-2 逾期记录
		model.addAttribute("realIncomeList", realIncomeList); // 3 违约记录
		model.addAttribute("productName", tProductService.get(loanContract.getProductId()).getName());
		model.addAttribute("tLoanContract", loanContract);
		model.addAttribute("badDebtRegist", badDebtRegist);

		return "modules/postlending/baddebts/badDebtRegistDetail";
	}

	@RequiresPermissions("postlending:baddebts:badDebtRegist:view")
	@RequestMapping(value = { "list", "" })
	public String list(TLoanContract loanContract, HttpServletRequest request, HttpServletResponse response,
			Model model) {
		if (loanContract == null) {
			loanContract = new TLoanContract();
		}
		loanContract.setFiveLevel(Cons.FiveLevelStatus.LOSS_N);
		Page<TLoanContract> page = loanContractService.findPage(new Page<TLoanContract>(request, response),
				loanContract);
		model.addAttribute("page", page);
		model.addAttribute("loanContract", loanContract);
		return "modules/postlending/baddebts/badDebtRegistLoanList";
	}

	/*
	 *
	 * 坏账异步处理
	 */
	@ResponseBody
	@RequiresPermissions("postlending:baddebts:badDebtRegist:view")
	@RequestMapping(value = "ajaxUpdate")
	public Map<String, Object> ajaxUpdate(String id, Model model, RedirectAttributes redirectAttributes) {
		return badDebtRegistService.ajaxUpdate(id);
	}

	@RequiresPermissions("postlending:baddebts:badDebtRegist:view")
	@RequestMapping(value = "form")
	public String form(BadDebtRegist badDebtRegist, HttpServletRequest request, HttpServletResponse response,
			Model model, RedirectAttributes redirectAttributes) {
		String view = "badDebtRegistForm";
		if (StringUtils.isBlank(badDebtRegist.getId())) {
			ProductBizCfg productBizCfg = productBizCfgService
					.getByBizCode(Cons.FModel.M_BUSINESS_APPLICATION_HZCL.getKey());
			if (productBizCfg == null) {
				addMessage(redirectAttributes, "无此业务!");
				return "redirect:" + adminPath + "/postlending/baddebts/badDebtRegist/";
			}
			if (StringUtils.isBlank(productBizCfg.getProcDefId())) {
				addMessage(redirectAttributes, "此业务没配置流程!");
				return "redirect:" + adminPath + "/postlending/baddebts/badDebtRegist/";
			}
			// 设置配置的流程
			badDebtRegist.getAct().setProcDefId(productBizCfg.getProcDefId());
		}
		// 审批处理
		if (badDebtRegist.getAct().isFinishTask() && StringUtils.isNotBlank(badDebtRegist.getAct().getProcInsId())) {
			BadDebtRegist temp = badDebtRegist;
			badDebtRegist = badDebtRegistService.getByProcInsId(badDebtRegist.getAct().getProcInsId());
			badDebtRegist.setAct(temp.getAct());
			temp = null;
			view = "badDebtRegistView";
		} else if (StringUtils.isNotBlank(badDebtRegist.getAct().getTaskDefKey())) {
			view = "badDebtRegistAudit";
		} else if (StringUtils.isBlank(badDebtRegist.getRegistName())) {
			badDebtRegist.setRegistName(UserUtils.getUser().getName());// UserUtils.getPrincipal()
			badDebtRegist.setDepartment(UserUtils.getUser().getOffice().getName());
		}
		badDebtRegist.setLoanContract(loanContractService.get(badDebtRegist.getLoanContractId()));

		model.addAttribute("badDebtRegist", badDebtRegist);
		return "modules/postlending/baddebts/" + view;
	}

	@RequestMapping(value = "getAuditStatus")
	@ResponseBody
	public String getAuditStatus(String cid) {
		// 检查是否已经申请坏账处理
		//BadDebtRegist badDebtRegist=new BadDebtRegist();
		//badDebtRegist.setLoanContractId(cid);
		//badDebtRegist.setApprovalStatis(Cons.ContractStatus.PENDING);
		TLoanContract ct = loanContractService.get(cid);
		//List<BadDebtRegist> list = badDebtRegistService.findList(badDebtRegist);
		/*if (list.size() > 0) {// 已经申请过坏账处理
			return "false";
		}*/
		if(ct.getStatus().equals(Cons.LoanContractStatus.CANCEL)){
			return "false";//已核销
		}
		return "true";
	}

	@RequiresPermissions("postlending:baddebts:badDebtRegist:edit")
	@RequestMapping(value = "save")
	public String save(BadDebtRegist badDebtRegist, HttpServletRequest request, HttpServletResponse response,
			Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, badDebtRegist)) {
			addMessage(redirectAttributes, "填写的参数有误，请重新填写！");
			return form(badDebtRegist, request, response, model, redirectAttributes);
		}
		if (StringUtils.isBlank(badDebtRegist.getAct().getFlag())
				|| StringUtils.isBlank(badDebtRegist.getAct().getComment())) {
			addMessage(redirectAttributes, "请填写及审核意见。");
			return form(badDebtRegist, request, response, model, redirectAttributes);
		} else {
			badDebtRegistService.save(badDebtRegist);
		}
		addMessage(redirectAttributes, "提交坏账处理申请成功");
		return "redirect:" + Global.getAdminPath() + "/postlending/baddebts/badDebtRegist/?repage";
	}

	@RequiresPermissions("postlending:baddebts:badDebtRegist:edit")
	@RequestMapping(value = "saveAudit")
	public String saveAudit(BadDebtRegist badDebtRegist, HttpServletRequest request, HttpServletResponse response,
			Model model, RedirectAttributes redirectAttributes) {
		if (StringUtils.isBlank(badDebtRegist.getAct().getComment())) {
			addMessage(model, "请填写审核意见!");
			return form(badDebtRegist, request, response, model, redirectAttributes);
		}

		badDebtRegistService.saveAudit(badDebtRegist);

		return "redirect:" + adminPath + "/act/task/todo/";
	}

	@RequiresPermissions("postlending:baddebts:badDebtRegist:edit")
	@RequestMapping(value = "delete")
	public String delete(BadDebtRegist badDebtRegist, RedirectAttributes redirectAttributes) {
		badDebtRegistService.delete(badDebtRegist);
		addMessage(redirectAttributes, "删除坏账处理成功");
		return "redirect:" + Global.getAdminPath() + "/postlending/baddebts/badDebtRegist/?repage";
	}

}
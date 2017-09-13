/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.contract.web;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.activiti.engine.FormService;
import org.activiti.engine.form.FormProperty;
import org.activiti.engine.form.TaskFormData;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.joda.time.DateTime;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.HtmlUtils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mangofactory.swagger.annotations.ApiIgnore;
import com.wanfin.fpd.common.config.Cons;
import com.wanfin.fpd.common.config.Cons.FModel;
import com.wanfin.fpd.common.config.Cons.FileType;
import com.wanfin.fpd.common.config.Global;
import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.service.ServiceException;
import com.wanfin.fpd.common.utils.DateUtils;
import com.wanfin.fpd.common.utils.IdGen;
import com.wanfin.fpd.common.utils.InterfaceUtil;
import com.wanfin.fpd.common.utils.SpringContextHolder;
import com.wanfin.fpd.common.utils.StringUtils;
import com.wanfin.fpd.common.web.BaseController;
import com.wanfin.fpd.modules.act.entity.Act;
import com.wanfin.fpd.modules.act.service.ActTaskService;
import com.wanfin.fpd.modules.company.entity.Customer;
import com.wanfin.fpd.modules.company.entity.TCompany;
import com.wanfin.fpd.modules.company.service.TCompanyService;
import com.wanfin.fpd.modules.contract.entity.BusinessContract;
import com.wanfin.fpd.modules.contract.entity.ContractAudit;
import com.wanfin.fpd.modules.contract.entity.RiskScore;
import com.wanfin.fpd.modules.contract.entity.TLoanContract;
import com.wanfin.fpd.modules.contract.entity.TLoanContractBak;
import com.wanfin.fpd.modules.contract.entity.tpl.TContractTpl;
import com.wanfin.fpd.modules.contract.service.BusinessContractService;
import com.wanfin.fpd.modules.contract.service.ContractAuditService;
import com.wanfin.fpd.modules.contract.service.RiskScoreService;
import com.wanfin.fpd.modules.contract.service.TLoanContractService;
import com.wanfin.fpd.modules.contract.service.tpl.TContractTplService;
import com.wanfin.fpd.modules.contract.vo.CsPersonVo;
import com.wanfin.fpd.modules.credit.entity.CreditApply;
import com.wanfin.fpd.modules.credit.entity.CreditCoborrower;
import com.wanfin.fpd.modules.credit.entity.CustomerCredit;
import com.wanfin.fpd.modules.credit.service.CreditApplyService;
import com.wanfin.fpd.modules.credit.service.CreditCoborrowerService;
import com.wanfin.fpd.modules.credit.service.CustomerCreditService;
import com.wanfin.fpd.modules.employee.entity.TEmployee;
import com.wanfin.fpd.modules.employee.service.TEmployeeService;
import com.wanfin.fpd.modules.files.service.TContractFilesService;
import com.wanfin.fpd.modules.form.entity.tpl.DfFormTpl;
import com.wanfin.fpd.modules.form.service.tpl.DfFormTplService;
import com.wanfin.fpd.modules.guarantee.entity.TGuaranteeContract;
import com.wanfin.fpd.modules.guarantee.service.TGuaranteeContractService;
import com.wanfin.fpd.modules.lending.entity.TLending;
import com.wanfin.fpd.modules.lending.service.TLendingService;
import com.wanfin.fpd.modules.mortgage.entity.MortgageContract;
import com.wanfin.fpd.modules.mortgage.service.MortgageContractService;
import com.wanfin.fpd.modules.pledge.entity.PledgeContract;
import com.wanfin.fpd.modules.pledge.service.PledgeContractService;
import com.wanfin.fpd.modules.postlending.entity.advance.Advance;
import com.wanfin.fpd.modules.postlending.service.advance.AdvanceService;
import com.wanfin.fpd.modules.product.entity.ProductBizCfg;
import com.wanfin.fpd.modules.product.entity.TProduct;
import com.wanfin.fpd.modules.product.service.ProductBizCfgService;
import com.wanfin.fpd.modules.product.service.TProductService;
import com.wanfin.fpd.modules.receivables.entity.RealIncome;
import com.wanfin.fpd.modules.receivables.entity.RepayRecord;
import com.wanfin.fpd.modules.receivables.service.RealIncomeService;
import com.wanfin.fpd.modules.receivables.service.RepayRecordService;
import com.wanfin.fpd.modules.refund.entity.Reimburse;
import com.wanfin.fpd.modules.refund.service.ReimburseService;
import com.wanfin.fpd.modules.repayplan.dao.TRepayPlanBakDao;
import com.wanfin.fpd.modules.repayplan.entity.TRepayPlan;
import com.wanfin.fpd.modules.repayplan.entity.TRepayPlanBak;
import com.wanfin.fpd.modules.repayplan.service.TRepayPlanService;
import com.wanfin.fpd.modules.sys.entity.Office;
import com.wanfin.fpd.modules.sys.entity.SysOfficeDetail;
import com.wanfin.fpd.modules.sys.entity.User;
import com.wanfin.fpd.modules.sys.service.DictService;
import com.wanfin.fpd.modules.sys.service.OfficeService;
import com.wanfin.fpd.modules.sys.service.SysOfficeDetailService;
import com.wanfin.fpd.modules.sys.utils.UserUtils;

/**
 * 业务办理Controller
 * @author lx
 * @version 2016-03-18
 */
@ApiIgnore
@Controller
@RequestMapping(value = "${adminPath}/contract/tLoanContract")
public class TLoanContractController extends BaseController {
	@Autowired
    FormService formService;//获取流程表单内容
	@Autowired
	private TLoanContractService tLoanContractService;
	@Autowired
	private TContractFilesService tContractFilesService;
	@Autowired
	private TProductService tProductService;
	@Autowired
	private TRepayPlanService tRepayPlanService;
	@Autowired
	private TRepayPlanBakDao repayPlanBakDao;
	@Autowired
	private DictService dictService;
	@Autowired
	private RepayRecordService repayRecordService;
	// 违约金，咨询费
	@Autowired
	private RealIncomeService realIncomeService;
	@Autowired
	private ReimburseService reimburseService;
	@Autowired
	private ProductBizCfgService productBizCfgService;
	@Autowired
	private DfFormTplService dfFormTplService;
	@Autowired
	private RiskScoreService riskScoreService;
	@Autowired
	private CreditApplyService creditApplyService;
	@Autowired
	private TEmployeeService tEmployeeService;
	@Autowired
	private TCompanyService tCompanyService;
	@Autowired
	private AdvanceService advanceService;
	@Autowired
	private TLendingService tLendingService;
	@Autowired 
	private ActTaskService actTaskService;
	@Autowired
	private ContractAuditService contractAuditService;//合同审核流程
	@Autowired
	private TGuaranteeContractService tGuaranteeContractService;//担保合同
	@Autowired
	private TContractTplService tContractTplService;//合同模板
	@Autowired
	private BusinessContractService BusinessContractService;//业务申请相关合同
	@Autowired
	private PledgeContractService pledgeContractService;//质押信息
	@Autowired  
	private MortgageContractService mortgageContractService;//抵押物品
	@Autowired
	private TLoanContractService loanContractService;//合同信息
	@Autowired
	private CreditCoborrowerService creditCoborrowerService;//共同借款人
	@Autowired
	private OfficeService officeService;
	@Autowired
	private CustomerCreditService customerCreditService;

	
	
	@ModelAttribute
	public TLoanContract get(@RequestParam(required=false) String id) {
		TLoanContract entity = null;
		if (StringUtils.isNotBlank(id)){
			String[] ids=id.split(",");
			entity = tLoanContractService.get(ids[0]);
		}
		if (entity == null){
			entity = new TLoanContract();
		}
		return entity;
	}
	
	
	@RequiresPermissions("contract:tLoanContract:view")
	@RequestMapping(value = {"list", ""})
	public String list(TLoanContract tLoanContract, HttpServletRequest request, HttpServletResponse response, Model model) {
		tLoanContract.setStatus(Cons.LoanContractStatus.TO_REVIEW+","+
				Cons.LoanContractStatus.TO_REVIEW+","+
				Cons.LoanContractStatus.TO_SUSPENSION+","+
				Cons.LoanContractStatus.TO_APPROVE+","+
				Cons.LoanContractStatus.TO_SIGN+","+
				Cons.LoanContractStatus.CONTRACT_AUDIT+","+
				Cons.LoanContractStatus.TO_LOAN_APPROVAL+","+
				Cons.LoanContractStatus.IN_LOAN_APPROVAL+","+
				Cons.LoanContractStatus.DB_TO_CHARGE+","+
				Cons.LoanContractStatus.DB_TO_LOAN+","+
				Cons.LoanContractStatus.TO_LOAN+","+
				Cons.LoanContractStatus.ENDED+","
				);
		tLoanContract.setProductId((String)UserUtils.getCache(UserUtils.CACHE_SYSCODE));// by zzm 根据产品id过滤
		Page<TLoanContract> page = new Page<TLoanContract>(request, response);
		page.setOrderBy("a.contract_number desc");
		page = tLoanContractService.findPage(page, tLoanContract); 
		
		model.addAttribute("page", page);
		model.addAttribute("tLoanContract",tLoanContract);
		return "modules/contract/tLoanContractList";
	}
	
	
	
	
	/**
	 * 
	 * @param tLoanContract 客户客户业务记录，不良记录等
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("contract:tLoanContract:view")
	@RequestMapping(value = "clist")
	public String clist(TLoanContract tLoanContract, HttpServletRequest request, HttpServletResponse response, Model model) {
		String strURL = "modules/contract/tLoanContractList";
		String type = request.getParameter("type");
		if(type!=null&&!"".equals(type)){
			if("1".equals(type)){//个人客户业务记录
				strURL = "modules/employee/tLoanContractList";
				tLoanContract.setCustomerType("2");
			}else if("2".equals(type)){//个人客户不良记录
				strURL = "modules/employee/badtLoanContractList";
				tLoanContract.setCustomerType("2");
			}else if("3".equals(type)){//企业客户业务记录
				strURL = "modules/company/tLoanContractList";
				tLoanContract.setCustomerType("1");
			}else if("4".equals(type)){//企业客户不良记录
				strURL = "modules/company/badtLoanContractList";
				tLoanContract.setCustomerType("1");
			}
		}
		Page<TLoanContract> page = tLoanContractService.findPage(new Page<TLoanContract>(request, response), tLoanContract); 
		model.addAttribute("page", page);
		model.addAttribute("tLoanContract",tLoanContract);
		return strURL;
	}
	

//	@RequiresPermissions("contract:tLoanContract:view")
//	@RequestMapping(value = "form_bak")
//	public String form_bak(TLoanContract tLoanContract, Model model,HttpServletRequest request) {
//		//20160413 add by srf
//		//对于新增没有ID的先设置一个临时ID
//		if(StringUtils.isBlank(tLoanContract.getId())){
//			tLoanContract.setId("tmp_"+IdGen.uuid());
//		}
//		
//		//User currentUser = UserUtils.getUser();//当前登录人
//		model.addAttribute("tLoanContract", tLoanContract);
//
//		TRepayPlan repayPlan = new TRepayPlan();
//		//还款计划
//		repayPlan.setLoanContractId(tLoanContract.getId());
//		tLoanContract.setRepayPlanList(tRepayPlanService.findList(repayPlan));
//		
//		//产品集合e
//		List<TProduct>  productList = tProductService.findAllList();
//		model.addAttribute("productList", productList);
//		return "modules/contract/tLoanContractForm_bak";
//	}

	/*@RequiresPermissions("contract:tLoanContract:view")
	@RequestMapping(value = "form")
	public String form(TLoanContract tLoanContract, Model model,HttpServletRequest request) {
		if(StringUtils.isBlank(tLoanContract.getId())){
			tLoanContract.setId("tmp_"+IdGen.uuid());
			TProduct tProduct = tProductService.get((String)UserUtils.getCache(UserUtils.CACHE_SYSCODE));
			initByProduct(tLoanContract,tProduct);
		}else{
			if(tLoanContract.getArea()!=null){
				tLoanContract.setAreaIdT(tLoanContract.getArea().getId());
			}
		}
		FModel fm = FModel.M_BUSINESS_APPLICATION_YWSQ;
		DfFormTpl dfFormTpl = dfFormTplService.getByBizCode(fm.getKey());
		model.addAttribute("action", fm.getAction());
		model.addAttribute("dfFormTpl", dfFormTpl);
		model.addAttribute("data", tLoanContract);   
		return "modules/contract/tLoanContractForm";
	}*/
	
	
	@RequiresPermissions("contract:tLoanContract:view")
	@RequestMapping(value = "form")
	public String form(TLoanContract tLoanContract, Model model,HttpServletRequest request) {
		if(StringUtils.isBlank(tLoanContract.getId())){
			//添加  业务id
			tLoanContract.setId("tmp_"+IdGen.uuid());
			//获取产品信息  以前被注释  了  所以获取也注释
			//TProduct tProduct = tProductService.get((String)UserUtils.getCache(UserUtils.CACHE_SYSCODE));
			//initByProduct(tLoanContract,tProduct);//不需要初始化数据
		}else{
			if(tLoanContract.getArea()!=null){
				tLoanContract.setAreaIdT(tLoanContract.getArea().getId());  //获取地区 idT(估计是地区代号)
			}
		}

		
		/*//判断获取共同借款人
		if(StringUtils.isNotBlank(tLoanContract.getCreditApplyId())){
			CreditCoborrower creditCoborrower = new CreditCoborrower();
			creditCoborrower.setCreditApplyId(tLoanContract.getCreditApplyId());
			List<CreditCoborrower> listCoborrower = creditCoborrowerService.findAllList(creditCoborrower);
			if(listCoborrower != null && listCoborrower.size()>0){
				model.addAttribute("listCoborrower", listCoborrower);
			}
		}*/
		
		//业务申请
//		FModel fm = FModel.M_BUSINESS_APPLICATION_YWSQ;
//		DfFormTpl dfFormTpl = dfFormTplService.getByBizCode(fm.getKey());//考虑是否需要添加校验    是否存在模板
//		model.addAttribute("action", fm.getAction());
//		model.addAttribute("dfFormTpl", dfFormTpl);
//		model.addAttribute("data", tLoanContract);
		
		// 清除会话 				
		HttpSession session = request.getSession();
		//质押信息		
		session.setAttribute("pledgeContractList", null);
		//抵押信息		
		session.setAttribute("mortgageContractList", null);
		//保证信息		
		session.setAttribute("guaranteeContractList", null);
				

		tLoanContract.setLoanTypeItem(tLoanContract.getLoanTypeList());
		model.addAttribute("tLoanContract", tLoanContract);
		return "modules/contract/tLoanContractForm";
	}
	
	/**
	 * @Description 根据产品配置 合同
	 * @param contract
	 * @param product
	 * @author zzm
	 * @date 2016-6-3 上午10:20:41  
	 */
	private void initByProduct(TLoanContract contract, TProduct product){
		if(product != null ){
			contract.setLoanRate(product.getRate());
			contract.setLoanAmount(product.getAmountMin());
			contract.setServerFee(product.getServerFee());
			contract.setMangeFee(product.getMangeFee());
			contract.setAdvanceDamages(product.getBreakFee());
			contract.setLateFee(product.getLateFee());
			contract.setRateDiscont(product.getRateDiscount());
			contract.setIfInterestRelief(product.getIflixiRedu());
			contract.setGracePeriod(product.getGracePeriod());
			contract.setGracePeriodPenalty(product.getGraceFaxi());
			contract.setLatePenalty(product.getYuqiFaxi());
			contract.setLatePenaltyFee(product.getYuqiFee());
			contract.setIfAdvance(product.getAdvanceRepay());
			contract.setPayType(product.getPayType());
			contract.setLatePenalty(product.getYuqiFaxi()==null?"0.01":product.getYuqiFaxi());
			contract.setPeriodType("2");
			contract.setLoanRateType("年");
		}
	}
	
	//在流程设置里面用到
	/*@RequestMapping(value = "auditForm")
	public String auditForm(TLoanContract tLoanContract, Model model,HttpServletRequest request) {
		//User currentUser = UserUtils.getUser();//当前登录人
		model.addAttribute("tLoanContract", tLoanContract);
		
		FModel fm = FModel.M_BUSINESS_APPLICATION_YWSQ;
		DfFormTpl dfFormTpl = dfFormTplService.getByBizCode(fm.getKey());
		if(actTaskService.checkIsEnd(tLoanContract.getAct())){//判断是否为最后节点
			tLoanContract.getAct().setIsEnd("yes");
		}
		model.addAttribute("dfFormTpl", dfFormTpl);
		model.addAttribute("data", tLoanContract);
		return "modules/contract/tLoanContractAuditForm";
	}*/
	
	@RequestMapping(value = "auditForm")
	public String auditForm(TLoanContract tLoanContract, Model model,HttpServletRequest request) {

		//User currentUser = UserUtils.getUser();//当前登录人
		model.addAttribute("tLoanContract", tLoanContract);
		
		FModel fm = FModel.M_BUSINESS_APPLICATION_YWSQ;
		DfFormTpl dfFormTpl = dfFormTplService.getByBizCode(fm.getKey());
		if(actTaskService.checkIsEnd(tLoanContract.getAct())){//判断是否为最后节点
			tLoanContract.getAct().setIsEnd("yes");
		}
		//判断是是否是发起人，是则允许修改
		String taskId = tLoanContract.getAct().getTaskId();
		//System.out.println("任务ID="+taskId);
		if(StringUtils.isNotBlank(taskId)){
			TaskFormData taskFormData = formService.getTaskFormData(taskId);
			if(taskFormData != null){
				for (FormProperty p : taskFormData.getFormProperties()) {
					String getFormPropertyId = p.getId();
					String getFormPropertyname= p.getName();
					//String getFormPropertyvalue= p.getValue();
					//System.out.println("FormProperty Data:id="+getFormPropertyId+";name="+getFormPropertyname+";value="+getFormPropertyvalue);
					if("approved".equals(getFormPropertyId)){
						if("start".equals(getFormPropertyname)){//只能提交，可以修改金额和时间
							model.addAttribute("subType", "start");
						}
					}
				}
			}
		}
//		System.out.println("============================");
//		System.out.println(dfFormTpl);
//		System.out.println("============================");
		//========================#4340 start============================TODO
		String tab = request.getParameter("tab");
		//System.out.println("auditForm tab="+tab);
		if(StringUtils.isNotBlank(tab) && "ginfo".equals(tab) && StringUtils.isNotBlank(tLoanContract.getId())){
			//获取抵押内容
			MortgageContract mortgageContract = new MortgageContract();
			mortgageContract.setBusinessId(tLoanContract.getId());
			
			List<MortgageContract> mortgageList = mortgageContractService.findList(mortgageContract);
			if(mortgageList != null && mortgageList.size()>0){
				model.addAttribute("mortgageList", mortgageList);
			}
			//获取质押内容
			PledgeContract pledgeContract = new PledgeContract();
			pledgeContract.setBusinessId(tLoanContract.getId());
			
			List<PledgeContract> pledgeList = pledgeContractService.findList(pledgeContract);
			if(pledgeList != null && pledgeList.size()>0){
				model.addAttribute("pledgeList", pledgeList);
			}
			//获取保证内容
			TGuaranteeContract tGuaranteeContract = new TGuaranteeContract();
			tGuaranteeContract.setBusinessId(tLoanContract.getId());
			
			List<TGuaranteeContract> guaranteeList = tGuaranteeContractService.findList(tGuaranteeContract);
			if(guaranteeList != null && guaranteeList.size()>0){
				model.addAttribute("guaranteeList", guaranteeList);
			}
		}
		//========================#4340 end==============================
		//默认走还款流程
		if(StringUtils.isBlank(tLoanContract.getIsdirectLoan())){
			tLoanContract.setIsdirectLoan("0");
		}
		
		model.addAttribute("dfFormTpl", dfFormTpl);
		model.addAttribute("data", tLoanContract);
		return "modules/contract/tLoanContractAuditForm";
	
	}
	
	//在流程设置里面用到
	@RequestMapping(value = "toLoanChangeForm")
	public String toLoanChangeForm(TLoanContract tLoanContract, Model model,HttpServletRequest request) {
		///tLoanContract = tLoanContractService.get(tLoanContract.getId());
		if(tLoanContract.getArea()!=null){
			tLoanContract.setAreaIdT(tLoanContract.getArea().getId());
		}
		//Act act = tLoanContract.getAct();
		//tLoanContract.setAct(act);
		
		//User currentUser = UserUtils.getUser();//当前登录人
		model.addAttribute("tLoanContract", tLoanContract);		
		FModel fm = FModel.M_BUSINESS_APPLICATION_YWSQ;
		DfFormTpl dfFormTpl = dfFormTplService.getByBizCode(fm.getKey());
		model.addAttribute("dfFormTpl", dfFormTpl);
		model.addAttribute("data", tLoanContract);
		
		return "modules/contract/loanChangeForm";
	}
	
	//在流程设置里面用到
	@RequestMapping(value = "loanChangeAudit")
	public String loanChangeAudit(TLoanContract tLoanContract, Model model,HttpServletRequest request) {		
		//User currentUser = UserUtils.getUser();//当前登录人		
		FModel fm = FModel.M_BUSINESS_APPLICATION_YWSQ;
		DfFormTpl dfFormTpl = dfFormTplService.getByBizCode(fm.getKey());
		model.addAttribute("dfFormTpl", dfFormTpl);		
		model.addAttribute("tLoanContractModel", tLoanContract);
		TLoanContract tempTLoanContract = tLoanContractService.get(tLoanContract.getId());
		model.addAttribute("data", tempTLoanContract);		
		Act act = tLoanContract.getAct();
		if (act != null) {
			if (act.getTaskId() != null && !"".equals(act.getTaskId())) {
				TLoanContractBak contractBak = tLoanContractService.getContractBakByContractId(tempTLoanContract);
				//List<TRepayPlan> planList = tRepayPlanService.getPlanByContractId(tempTLoanContract.getId());
				TRepayPlanBak tempRpbQuery = new TRepayPlanBak();
				tempRpbQuery.setLoanContractId(contractBak.getId());				
				List<TRepayPlanBak> planListBak = repayPlanBakDao.findListCondition(tempRpbQuery);
				model.addAttribute("tLoanContract", tempTLoanContract);
				model.addAttribute("contractBak", contractBak);
				model.addAttribute("productName",tProductService.get(tLoanContract.getProductId()).getName());
				//model.addAttribute("planList", planList);
				model.addAttribute("planListBak", planListBak);
				return "modules/contract/loanChangeAudit";
			} else {
				return toLoanChangeForm(tLoanContract, model, request);
			}
				
		} else {
			return "";
		}
	}
	
		
	//在流程设置里面用到
	@RequestMapping(value = "saveChangeAudit")
	public String saveChangeAudit(TLoanContract tLoanContract, Model model,HttpServletRequest request) {
		tLoanContractService.saveAudit(tLoanContract);
		return "redirect:" + adminPath + "/act/task/todo/";
	}
	
	//在流程设置里面用到
	@RequestMapping(value = "loanChangeFormSave")
	public String loanChangeFormSave(TLoanContract tLoanContract, String amount, Model model,HttpServletRequest request,RedirectAttributes redirectAttributes) {
		User currentUser = UserUtils.getUser();//当前登录人
		//tLoanContract = tLoanContractService.get(tLoanContract.getId());
		//tLoanContract.setAct(act);
		/*model.addAttribute("tLoanContract", tLoanContract);
		
		FModel fm = FModel.M_BUSINESS_APPLICATION_YWSQ;
		DfFormTpl dfFormTpl = dfFormTplService.getByBizCode(fm.getKey());
		model.addAttribute("dfFormTpl", dfFormTpl);
		model.addAttribute("data", tLoanContract);*/
		if (StringUtils.isBlank(tLoanContract.getAct().getFlag())
				|| StringUtils.isBlank(tLoanContract.getAct().getComment()) || StringUtils.isBlank(amount)){
			addMessage(model, "请填写变更贷款金额及审核意见。");
			return loanChangeAudit(tLoanContract, model,request);
		}else{
			tLoanContractService.saveWork(tLoanContract, currentUser, amount);
		}
		
		return "redirect:"+Global.getAdminPath()+"/contract/tLoanContract/?repage";
		
	}
	
	/*
	 * 20160413 修改 by srf
	 * 
	 */
//	@RequiresPermissions("contract:tLoanContract:edit")
//	@RequestMapping(value = "save_bak")
//	public String save_bak(TRepayPlan tRepayPlan,TLoanContract tLoanContract,String counts,Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
//		
//		if(tLoanContract.getContractNumber()==null){//新增的时候，需要自动生成合同编号
//			tLoanContract.setContractNumber(tLoanContractService.buildContractNumber(tLoanContract));
//		}
//		if (!beanValidator(model, tLoanContract)){
//			return form(tLoanContract, model,request);
//		}
//		/*  //20160413 调整 by srf
//		tLoanContractService.save(tLoanContract);
//		//调整到tLoanContractService去处理tRepayPlanService.save
//		tRepayPlanService.save(tRepayPlan.getEndDate(), tRepayPlan.getPrincipal(), tRepayPlan.getInterest(), tRepayPlan.getStartDate(), tRepayPlan.getAccountDate(), counts,tLoanContract);
//		*/
//		tLoanContractService.save(tLoanContract, tRepayPlan, counts);
//		addMessage(redirectAttributes, "保存业务办理成功");
//		return "redirect:"+Global.getAdminPath()+"/contract/tLoanContract/?repage";
//	}
//	
	/**
	 * 通过自定义表单录入申请
	 */
	@RequiresPermissions("contract:tLoanContract:edit")
	@RequestMapping(value = "save")
	public String save(TLoanContract tLoanContract, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {

		//获取该用户的授信申请信息，查找最新的授信信息并关联
		/*if(StringUtils.isNotBlank(tLoanContract.getCreditApplyId())){
			CreditApply creditApply = creditApplyService.get(tLoanContract.getCreditApplyId());
			if(creditApply == null){
				tLoanContract.setCreditApplyId(null);
			}
		}
		if(StringUtils.isBlank(tLoanContract.getCreditApplyId())){
			CreditApply creditApply = creditApplyService.getNewestCredit(tLoanContract.getCustomerId());
			if(creditApply != null){
				tLoanContract.setCreditApplyId(creditApply.getId());
			}else{
				model.addAttribute("message", "该用户未查到有授信记录，请确认已经给该用户进行授信");
				return form(tLoanContract, model,request);
			}
		}*/
		//客户额度表查询 是否  有数据    有 就有 授信  无   则 没有授信
		CustomerCredit customerCredit = customerCreditService.getByCustorId(tLoanContract.getCustomerId());
		if(customerCredit == null){
			model.addAttribute("message", "该用户未查到有授信记录，请确认已经给该用户进行授信");
			return form(tLoanContract, model,request);
		}else if(customerCredit != null){
			if(new BigDecimal(tLoanContract.getLoanAmount()).compareTo(customerCredit.getBalance())>0){
				model.addAttribute("message", "该用户贷款金额不足,请重新授信");
				return form(tLoanContract, model,request);
			}
		}
		
	
		
		
		
		
		
		if(tLoanContract.getContractNumber()==null){//新增的时候，需要自动生成合同编号
			tLoanContract.setContractNumber(tLoanContractService.buildContractNumber(tLoanContract));
		}
		//保存时，如果以下字段可能没有值，保存会出差，需要手动加值--担保
		tLoanContract.setProductId((String)UserUtils.getCache(UserUtils.CACHE_SYSCODE));
		if(UserUtils.getUser().getCompany().getPrimaryPerson().equals(Cons.CompanyType.DAN_BAO)){
			if(tLoanContract.getLoanDate()==null||"".equals(tLoanContract.getLoanDate())){
				tLoanContract.setLoanDate(new Date());
			}
			if(tLoanContract.getPeriodType()==null||"".equals(tLoanContract.getPeriodType())){
				tLoanContract.setPeriodType("2");
			}
			tLoanContract.setGatheringBank("");
			tLoanContract.setGatheringName("");
			tLoanContract.setGatheringNumber("");
		}
		if(tLoanContract.getMoveOffice()!=null){
			if(StringUtils.isNotBlank(tLoanContract.getMoveOffice().getId())){
				tLoanContract.setMoveStatus(Cons.MoveStatus.AUDIT_ING);
			}
		}
		if (!beanValidator(model, tLoanContract)){
			return form(tLoanContract, model,request);
		}
		try {
			String[] ids=tLoanContract.getId().split(",");
			if(ids.length>1){
				tLoanContract.setId(ids[0]);
			}
			tLoanContract.setScanFlag("0");
			if(!tLoanContract.getId().startsWith("tmp_") &&  tLoanContract.getLoanTypeItem() !=null && tLoanContract.getLoanTypeItem().size()>0 ){
				tLoanContract.setLoanTypeList(tLoanContract.getLoanTypeItem());
			}
			tLoanContractService.save(tLoanContract);
			
			if (!StringUtils.isEmpty(tLoanContract.getId()) && !StringUtils.isEmpty(tLoanContract.getLoanType())) {
				HttpSession session = request.getSession();
				//保存 质押 
				if (tLoanContract.getLoanType().contains("1")) {
					List<PledgeContract> pledgeContractList = (List<PledgeContract>)session.getAttribute("pledgeContractList");
					if (pledgeContractList != null && pledgeContractList.size() > 0) {
						for (PledgeContract tempPc : pledgeContractList) {
							tempPc.setBusinessId(tLoanContract.getId());
							pledgeContractService.save(tempPc);
						}							
					}
				} 
				
				
				//保存 抵押
				if (tLoanContract.getLoanType().contains("2")) {
					List<MortgageContract> mortgageContractList = (List<MortgageContract>)session.getAttribute("mortgageContractList");
					if (mortgageContractList != null && mortgageContractList.size() > 0) {
						for (MortgageContract tempPc : mortgageContractList) {
							tempPc.setBusinessId(tLoanContract.getId());
							mortgageContractService.save(tempPc);
						}						
					}
				}
								
				//保存 保证
				if (tLoanContract.getLoanType().contains("4")) {
					List<TGuaranteeContract> tGuaranteeContractList = (List<TGuaranteeContract>)session.getAttribute("guaranteeContractList");
					if (tGuaranteeContractList != null && tGuaranteeContractList.size() > 0) {
						for (TGuaranteeContract tempPc : tGuaranteeContractList) {
							tempPc.setBusinessId(tLoanContract.getId());
							tGuaranteeContractService.save(tempPc);
						}
					}
				}
				
				
				//保存完之后  清除会话信息
				//质押 信息
				session.setAttribute("pledgeContractList", null);				
				// 抵押 信息
				session.setAttribute("mortgageContractList", null);				
				//保证 信息
				session.setAttribute("guaranteeContractList", null);
				
			}
			
			
			addMessage(redirectAttributes, "保存业务办理成功");
		} catch (ServiceException e) {
			e.printStackTrace();
			addMessage(redirectAttributes, "失败:"+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/contract/tLoanContract/?repage";
	}
	
	
	/**
	 * 获取对应的授信及共同借款人信息
	 * @param customerId 客户的ID
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("contract:tLoanContract:edit")
	@ResponseBody
	@RequestMapping(value = "getJsonCoborrower")
	public Map<String, Object> getJsonCoborrower(String customerId, Model model, RedirectAttributes redirectAttributes){
		Map<String, Object> result = Maps.newHashMap();
		
		if(StringUtils.isBlank(customerId)){
			result.put("status", -1);
			result.put("message", "获取失败");
		}else{
			CreditApply creditApply = creditApplyService.getNewestCredit(customerId);
			if(creditApply == null){
				result.put("status", 4);
				result.put("message", "没有该客户相关的授信记录！");
			}else{
				CreditCoborrower creditCoborrower = new CreditCoborrower();
				creditCoborrower.setCreditApplyId(creditApply.getId());
				List<CreditCoborrower> listCoborrower = creditCoborrowerService.findAllList(creditCoborrower);
				if(listCoborrower != null && listCoborrower.size()>0){
					result.put("status", 1);
					result.put("message", "成功");
					result.put("colist", listCoborrower);//共同借款人
				}else{
					result.put("status", 0);
					result.put("message", "无");
				}
			}
		}
		return result;
	}

	/**
	 * @Description 删除业务办理成功
	 * @param tLoanContract
	 * @param redirectAttributes
	 * @return
	 * @author Chenh 
	 * @date 2016年3月30日 上午11:04:06
	 */
	@RequiresPermissions("contract:tLoanContract:edit")
	@RequestMapping(value = "delete")
	public String delete(TLoanContract tLoanContract, RedirectAttributes redirectAttributes) {
		tLoanContract.setScanFlag("0");
		tLoanContractService.delete(tLoanContract);
		addMessage(redirectAttributes, "删除业务办理成功");
		return "redirect:"+Global.getAdminPath()+"/contract/tLoanContract/?repage";
	}
	
	
	/**
	 **放款申请
	 * */
	@ResponseBody
	@RequiresPermissions("contract:tLoanContract:edit")
	@RequestMapping(value = "getContractStatus")
	public String getContractStatus(String id, Model model, RedirectAttributes redirectAttributes) {
		
		return get(id).getStatus();
	}
	/**
	 **判断还款计划中是否存在未结清笔数
	 *@author lzj
	 * */
	@ResponseBody
	@RequiresPermissions("contract:tLoanContract:edit")
	@RequestMapping(value = "getNoPays")
	public String getNoPays(String id, Model model, RedirectAttributes redirectAttributes) {
		String forward="1";
		TRepayPlan repayPlan = new TRepayPlan();
		//原还款计划
		repayPlan.setLoanContractId(id);
		List<TRepayPlan> list = tRepayPlanService.findList(repayPlan);
		if(list != null && list.size()>=1 ){
			for(TRepayPlan p : list){
				 if(Cons.RepayStatus.IN_PAYMENT.equals(p.getStatus())){
					//model.addAttribute("message", "还款计划中存在未结清笔数！");
					model.addAttribute("isClose", Global.YES);
					forward="0";//原款计划中存在未结清笔数
					break;
				}
			}
		
	   }else {
		   return "-1";//原还款计划不存在
	   }
	   return forward;
	}
	
	/**
	 **检查是否已经提交放款申请了，Y代表已提交，N代表没提交,默认未提交
	 */
	@ResponseBody
	@RequiresPermissions("contract:tLoanContract:edit")
	@RequestMapping(value = "toCheckIfExistFksq")
	public String toCheckIfExistFksq(String id, Model model, RedirectAttributes redirectAttributes) {
		String retStr = "N";
		TLoanContract tLoanContract = get(id);
		
		List<?>  loanexaminelist = tLoanContractService.getExamineList(tLoanContract);
		for (int i = 0; i < loanexaminelist.size(); i++) {
			Object obj = loanexaminelist.get(i);
			Object[] os = (Object[])obj;//sql查询第一个位置是name，用来判断是否已经提交放款申请
			System.out.println(os[5].toString());
			if(os[5].toString().contains("fksq")){
				retStr = "Y";
			}
			if(os[1].toString().contains("驳回")){//如果为驳回的还是可以申请的
				retStr = "N";
			}
		}
		return retStr;
	}
	/** 
	 * lingxing  
	 * @param id
	 * @param type
	 * @param model
	 * @param redirectAttributes
	 * @return 
	 *  注意：勿改动，勿改动，勿改动，勿改动，勿改动，勿改动，勿改动，勿改动，
	 */
	@ResponseBody
	@RequestMapping(value = "getdizhiListStatus")
	public int getdizhiListStatus(String id,String type, Model model, RedirectAttributes redirectAttributes){
		TLoanContract entity = tLoanContractService.get(id);
		int count = 0;//为0直接过，
		if(entity.getLoanType().contains("1")||entity.getLoanType().contains("2")||entity.getLoanType().contains("4")){//检验抵押，质押，保证
			count = tLoanContractService.getdizhiListStatus(entity,type);
		}
		return count;
	}
	
	/** 
	 * lingxing  
	 * @param id
	 * @param type
	 * @param model
	 * @param redirectAttributes
	 * @return 
	 *  注意：勿改动，勿改动，勿改动，勿改动，勿改动，勿改动，勿改动，勿改动，
	 */
	@ResponseBody
	@RequestMapping(value = "getLoanType")
	public String getLoanType(String id, Model model, RedirectAttributes redirectAttributes){
		String tempStr = "请添加";
		TLoanContract entity = tLoanContractService.get(id);
		if (entity != null) {
			if (entity.getLoanType().contains("1")) {
				tempStr += "质押  ";
			} 
			if (entity.getLoanType().contains("2")) {
				tempStr += "抵押  ";
			} 
			if (entity.getLoanType().contains("4")) {
				tempStr += "保证 ";
			}
			tempStr += "信息 ";
		} else {
			tempStr = "找不到合同!";
		}
		
		return tempStr;
	}
	
	
	/**
	 *   解除担保
	 * @param tLoanContract
	 * @return
	 */
	@RequestMapping(value = "removeDanbao")
	@ResponseBody
	public Map<String, Object> removeDanbao(TLoanContract tLoanContract) {
		Map<String, Object> result = Maps.newHashMap();
		if(tLoanContract.getId()!=null){
		   if(tLoanContract.getStatus().equals(Cons.LoanContractStatus.DB_TO_CHECK)){
			   tLoanContract.setStatus(Cons.LoanContractStatus.DB_TO_REMOVE);
				tLoanContractService.updateStatus(tLoanContract);
				result.put("status", 1);
		   }else if(tLoanContract.getStatus().equals(Cons.LoanContractStatus.DB_TO_REMOVE)){
			   result.put("status", 0);
			   result.put("message","已经解除了的合同，无需再解除担保");
		   }else{
			   result.put("status", 0);
			   result.put("message","不是担保待检查的合同，不能解除担保");
		   }
			
		
		}
		return result;
	}
	
	
	
	/**
	 * @Description 跳转签订合同
	 * @param request
	 * @param response
	 * @param model
	 * @param tLoanContract
	 * @return
	 * @author Chenh 
	 * @date 2016年3月30日 上午11:02:34
	 */
	/*@RequiresPermissions("contract:tLoanContract:view")
	@RequestMapping(value = "toSign")
	public String toSign(HttpServletRequest request, HttpServletResponse response, Model model, TLoanContract tLoanContract) {
		FModel fm = FModel.M_BUSINESS_APPLICATION_YWSQ;
		DfFormTpl dfFormTpl = dfFormTplService.getByBizCode(fm.getKey());
		model.addAttribute("dfFormTpl", dfFormTpl);
		model.addAttribute("data", tLoanContract);
		return "/modules/contract/tLoanContractSign";
	}*/
	/**
	 * new cope
	 */
	@RequiresPermissions("contract:tLoanContract:view")
	@RequestMapping(value = "toSign")
	public String toSign(HttpServletRequest request, HttpServletResponse response, Model model, ContractAudit contractAudit) {	
		TLoanContract loanContract = null; 
		if(contractAudit == null){// || contractAudit.getLoanContract()==null
			return "redirect:"+Global.getAdminPath()+"/contract/tLoanContract/?repage";//返回合同列表
		}
		String ifEdit = "view";
		
		//编辑合同用
		BusinessContract businessContract = new BusinessContract();
		
		String tab = request.getParameter("tab");
		System.out.println("TLoanContractController toSign->tab:"+tab);
		if(tab == null || "info".equals(tab)){//基本信息
			
		}else if("files".equals(tab)){//合同及附件信息
			//有合同信息才行
			if(contractAudit.getLoanContract() != null && StringUtils.isNotBlank(contractAudit.getLoanContract().getId())){
				//获取所有贷款合同的模板ID及标题
				TContractTpl tContractTpl = new TContractTpl();
				tContractTpl.setType("1");
				tContractTpl.setFtlStatus("1");
				List<TContractTpl> contractTplList = tContractTplService.findList(tContractTpl);
				
				//businessContract.setContractAuditId(contractAudit.getId());
				businessContract.setLoanContract(contractAudit.getLoanContract());
				List<BusinessContract> businessContractList = BusinessContractService.findList(businessContract);
				
				model.addAttribute("contractTplList", contractTplList);
				model.addAttribute("businessContractList", businessContractList);
			}
		}else if("lc".equals(tab)){//流程审核相关信息
			
		}
		//StringUtils.isBlank(contractAudit.getId()) && 
		if(contractAudit.getLoanContract() != null && StringUtils.isNotBlank(contractAudit.getLoanContract().getId())){
			//获取合同信息
			loanContract = this.get(contractAudit.getLoanContract().getId());
			if(loanContract != null){
				contractAudit.setLoanContract(loanContract);
			}
			//获取流程信息
			ContractAudit tmp = contractAuditService.getByLoanContract(contractAudit);
			if(tmp != null){
				if(StringUtils.isBlank(contractAudit.getId())){
					contractAudit.setId(tmp.getId());
				}
				if(contractAudit.getAct() == null){
					contractAudit.setAct(new Act());
				}
				if(StringUtils.isNotBlank(tmp.getProcInsId()) && StringUtils.isBlank(contractAudit.getAct().getProcInsId())){
					contractAudit.getAct().setProcInsId(tmp.getProcInsId());
				}
				//contractAudit = tmp;//error
			}
		}
		//业务的模板：显示合同内容用
		FModel fm = FModel.M_BUSINESS_APPLICATION_YWSQ;
		DfFormTpl dfFormTpl = dfFormTplService.getByBizCode(fm.getKey());
		model.addAttribute("dfFormTpl", dfFormTpl);
		
		//获取对应合同审核的流程
		ProductBizCfg productBizCfg = productBizCfgService.getByBizCode(Cons.FModel.M_BUSINESS_APPLICATION_QDHT.getKey());
		if(productBizCfg != null && contractAudit.getAct() != null){
			contractAudit.getAct().setProcDefId(productBizCfg.getProcDefId());
		}
		
		if(StringUtils.isBlank(contractAudit.getId())){
			ifEdit = "edit";
		}
		if(contractAudit.getAct() != null && StringUtils.isNotBlank(contractAudit.getAct().getStatus()) && "finish".equals(contractAudit.getAct().getStatus())){
			ifEdit = "view";
		}
		
		String taskId = contractAudit.getAct().getTaskId();
		if(StringUtils.isNotBlank(taskId)){
			TaskFormData taskFormData = formService.getTaskFormData(taskId);
			if(taskFormData != null){
				for (FormProperty p : taskFormData.getFormProperties()) {
					String getFormPropertyId = p.getId();
					String getFormPropertyname= p.getName();
					if("approved".equals(getFormPropertyId) && StringUtils.isNotBlank(getFormPropertyname)){
						model.addAttribute("subType", getFormPropertyname);
						if(StringUtils.isBlank(getFormPropertyname)){
							
						}else if("start".equals(getFormPropertyname)){
							ifEdit = "edit";
						}else if("end".equals(getFormPropertyname)){
							ifEdit = "upload";
						}
					}
				}
			}
		}
		
		model.addAttribute("ifEdit", ifEdit);//调查报告是否可以编辑
		model.addAttribute("businessContract", businessContract);
		model.addAttribute("contractAudit", contractAudit);
		model.addAttribute("act", contractAudit.getAct());
		
		return "/modules/contract/tLoanContractSign";
	}
	
	/**
	 * @Description 签订合同
	 * @param request
	 * @param response
	 * @param model
	 * @param tLoanContract
	 * @param redirectAttributes
	 * @return
	 * @author Chenh 
	 * @date 2016年3月30日 上午11:02:53
	 */
/*	@RequiresPermissions("contract:tLoanContract:edit")
	@RequestMapping(value = "sign")
	public String sign(HttpServletRequest request, HttpServletResponse response, Model model, TLoanContract tLoanContract, RedirectAttributes redirectAttributes) {
		//检查添加附件#3630
		if(tContractFilesService.getByTaskId(tLoanContract.getId()).size()<1){
			model.addAttribute("message", "未添加附件");
			return toSign(request, response, model, tLoanContract);
		}
		
		*//**
		 * 签订合同
		 * *//*
		TLoanContract tLoanContractNew = tLoanContractService.get(tLoanContract.getId());
		*//**
		 * 签订合同默认状态：1-待评审-走评审制
		 * *//*
		//如果是担保，合同签订之后就是待收费状态
		if(UserUtils.getUser().getCompany().getPrimaryPerson().equals(Cons.CompanyType.DAN_BAO)){
		   tLoanContractNew.setStatus(Cons.LoanContractStatus.DB_TO_CHARGE);
		}else{
			if(tLoanContractNew.getIsdirectLoan()!=null && tLoanContractNew.getIsdirectLoan().equals("1")){
				tLoanContractNew.setStatus(Cons.LoanContractStatus.TO_LOAN);
			}else{
				tLoanContractNew.setStatus(Cons.LoanContractStatus.TO_LOAN_APPROVAL);
			}
			*//**
			 * update  改为走放款流程
			 *//*
			tLoanContractNew.setStatus(Cons.LoanContractStatus.TO_LOAN_APPROVAL);
		}
				
		tLoanContractService.save(tLoanContractNew);
		addMessage(redirectAttributes, "业务办理-签订合同成功");
		return "redirect:"+Global.getAdminPath()+"/contract/tLoanContract/?repage";
	}*/
	
	/**
	 * new  cope
	 * @param request
	 * @param response
	 * @param model
	 * @param contractAudit
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("contract:tLoanContract:edit")
	@RequestMapping(value = "sign")
	public String sign(HttpServletRequest request, HttpServletResponse response, Model model, ContractAudit contractAudit, RedirectAttributes redirectAttributes) {		
		if(contractAudit.getLoanContract() == null || StringUtils.isBlank(contractAudit.getLoanContract().getId())){
			model.addAttribute("message", "提交异常");
			return toSign(request, response, model, contractAudit);
		}
		//检查添加附件#3630
		if(tContractFilesService.getByTaskId(contractAudit.getLoanContract().getId(), FileType.FILE_TYPE_LOANCONTRACT_1_1).size()<1){
			model.addAttribute("message", "未添加附件（合同）");
			return toSign(request, response, model, contractAudit);
		}
		//保存，后台处理流程信息
		//contractAuditService.saveAudit(contractAudit);
		TLoanContract loanContract = loanContractService.get(contractAudit.getLoanContract().getId());
		loanContract.setStatus(Cons.LoanContractStatus.CONTRACT_AUDIT);//合同审核中
		tLoanContractService.save(loanContract);
		addMessage(redirectAttributes, "保存签订合同审核流程成功");
		
		return "redirect:"+Global.getAdminPath()+"/contract/tLoanContract/?repage";
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
	public String complete(TLoanContract tLoanContract, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		if(StringUtils.isBlank(tLoanContract.getProcInsId())){
			tLoanContractService.startProcess(tLoanContract);//新增
			addMessage(redirectAttributes, "合同'" + tLoanContract.getContractNumber() + "'提交成功");
		}else{
			if (StringUtils.isBlank(tLoanContract.getAct().getFlag())
					|| StringUtils.isBlank(tLoanContract.getAct().getComment())){
				addMessage(model, "请填写审核意见。");
				return form(tLoanContract, model,request);
			}
			tLoanContractService.auditSave(tLoanContract);
			addMessage(redirectAttributes, "合同'" + tLoanContract.getContractNumber() + "'审批成功");
		}
		return "redirect:" + adminPath + "/act/task/historic/";
	}
	/**
	 * @Description 放款（完成任务）
	 * @param tLoanContract
	 * @param model
	 * @param redirectAttributes
	 * @param request
	 * @return
	 * @author Chenh 
	 * @date 2016年3月30日 上午11:03:10
	 */
	@RequestMapping(value = "makeLoans")
	@ResponseBody
	public String makeLoans(TLoanContract tLoanContract, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		if(tLoanContract.getStatus().equals("4")){
			tLoanContractService.startLendProcess(tLoanContract);//放款待审批的流程可以开启
			addMessage(redirectAttributes, "合同'" + tLoanContract.getContractNumber() + "'提交成功");
		}else{
			if (StringUtils.isBlank(tLoanContract.getAct().getFlag())
					|| StringUtils.isBlank(tLoanContract.getAct().getComment())){
				addMessage(model, "请填写审核意见。");
				return form(tLoanContract, model,request);
			}
			tLoanContractService.makeLoansSave(tLoanContract);
			addMessage(redirectAttributes, "合同'" + tLoanContract.getContractNumber() + "'审批成功");
		}
		return "true";
	}
	/**
	 * @Description 跳转到放款审批页面
	 * @param tLoanContract
	 * @param model
	 * @author zzm 
	 * @date 2016-4-8 上午9:25:13  
	 */
	@RequestMapping(value = "loanAuditForm")
	public String loanAuditForm(TLoanContract tLoanContract, Model model,HttpServletRequest request) {

		FModel fm = FModel.M_BUSINESS_APPLICATION_YWSQ;
		DfFormTpl dfFormTpl = dfFormTplService.getByBizCode(fm.getKey());
		model.addAttribute("dfFormTpl", dfFormTpl);
		model.addAttribute("data", tLoanContract);
		tLoanContract.getAct().setTitle("放款申请【"+tLoanContract.getContractNumber()+"】");
		
		BusinessContract businessContract = new BusinessContract();
		businessContract.setLoanContract(tLoanContract);
		List<BusinessContract> businessContractList = BusinessContractService.findList(businessContract);		
		model.addAttribute("businessContractList", businessContractList);
		
		model.addAttribute("act", tLoanContract.getAct());
		model.addAttribute("tLoanContract", tLoanContract);//优化增加，不可删除
		return "modules/contract/loanAuditForm";
	}
	
	
	/**
	 * @Description 跳转到展期申请页面
	 * @param loanContract
	 * @param model
	 * @return
	 * @author zzm 
	 * @date 2016-4-6 上午10:50:32  
	 */
//	@RequiresPermissions("contract:tLoanContract:edit_extend")
	@RequestMapping(value = "extendForm")
	public String extendForm(TLoanContract loanContract, Model model) {
		
		if(loanContract.getParent() == null){
			model.addAttribute("message", "合同不存在！");
			model.addAttribute("isClose", Global.YES);
		}
	//	TLoanContract parent = new TLoanContract(loanContract.getParent().getId());
		TLoanContract parent = tLoanContractService.get(loanContract.getParent().getId());
		boolean isNew = StringUtils.isBlank(loanContract.getId());//是否新增
		if(isNew){
			Act act = loanContract.getAct();
			loanContract = tLoanContractService.get(parent.getId());
			if(act.getVars()!=null && act.getVars().getKeys()!=null && act.getVars().getKeys().equals("skip")){
				model.addAttribute("isClose", Global.YES);
			}else{
				if(!Cons.LoanContractStatus.UN_CLEARED.equals(loanContract.getStatus())){
					model.addAttribute("message", "未结清状态的合同才能展期！");
					model.addAttribute("isClose", Global.YES);
				 }
				if(Cons.LoanContractStatus.EXTENDED.equals(loanContract.getStatus()) || Cons.LoanContractStatus.ET_TO_APPROVE.equals(loanContract.getStatus())){
					model.addAttribute("message", "已展期、展期审批中的合同不能再展期！");
					model.addAttribute("isClose", Global.YES);
				}
			}
				
			
			//新增时设置一个临时id（以‘new_’开头），用于关联其它表的数据（不同步保存的，如：附件）
			loanContract.setId("new_"+IdGen.uuid());
			loanContract.setContractNumber(tLoanContractService.getExtendCodeByContractCode(loanContract.getContractNumber()));
			loanContract.setApplyDate(loanContract.getApplyDate());
			loanContract.setLoanPeriod("0");//默认展期期数0
			loanContract.setPeriodType(loanContract.getPeriodType());
			loanContract.setPayDay(loanContract.getPayDay());
			loanContract.setPayType(loanContract.getPayType());
			loanContract.setPayOptions(null);
			loanContract.setLoanRate(loanContract.getLoanRate());
			loanContract.setLoanRateType(loanContract.getLoanRateType());
			//loanContract.setPayPrincipalDate(loanContract.getPayPrincipalDate());
			loanContract.setAct(act);
		}
		//展期的贷款合同信息
		loanContract.setParent(parent);
		
		TRepayPlan repayPlan = new TRepayPlan();
		//展期计划
		repayPlan.setLoanContractId(loanContract.getParent().getId());
		loanContract.setRepayPlanList(tRepayPlanService.findList(repayPlan));
		//原还款计划
		repayPlan.setLoanContractId(parent.getId());
		List<TRepayPlan> list = tRepayPlanService.findList(repayPlan);
		parent.setRepayPlanList(list);
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");//小写的mm表示的是分钟  
		try{
			loanContract.setPayPrincipalDate(sdf.parse(list.get(list.size()-1).getEndDate()));
			//展期申请时间===============add===============================================
			String startDate="";
			Boolean isNever = true;//从未还款标记
			if(list!=null&&list.size()>0){
				startDate = list.get(0).getStartDate();//默认从未还款，第一条的开始时间
				for (TRepayPlan tRepayPlan : list) {
					if(Cons.RepayStatus.PAID.equals(tRepayPlan.getStatus())){
						startDate = tRepayPlan.getEndDate();//如果有已还清记得，取结束时间
						isNever = false;
					}
				}
			}
			if(isNever){
				loanContract.setApplyDate(sdf.parse(startDate));//从未还款时，时间不用加一天
			}else{
				loanContract.setApplyDate(new DateTime(sdf.parse(startDate)).plusDays(1).toDate());
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		int overdueCount = 0;//未还期数
		//展期申请时，计算未还期数，把未还的剩余本金作为展期金额
		if(isNew && list != null ){
			Double amount = 0.0;
			HashMap<Object, Object> numMap = Maps.newHashMap();
			for(TRepayPlan p : list){
				if(Cons.RepayStatus.NO_PAID.equals(p.getStatus())){
					//未还的本金
					amount+=Double.valueOf(p.getPrincipal());
					numMap.put(p.getNum(), p.getNum());
				}else if(Cons.RepayStatus.IN_PAYMENT.equals(p.getStatus())){
					model.addAttribute("message", "还款计划中存在未结清笔数不能展期！");
					model.addAttribute("isClose", Global.YES);
					break;
				}
			}
			if(parent.getPayType().equals("5")){//到期一次性还本付息,未还期数为原合同申请期数
				overdueCount =Integer.parseInt(parent.getLoanPeriod());
			}else{
				overdueCount = numMap.size();
			}
			//展期金额
			loanContract.setLoanAmount(amount+"");
		}
		
		model.addAttribute("tLoanContract", loanContract);
		model.addAttribute("overdueCount", overdueCount);
		
		return "modules/contract/extendForm";
	}
	
	@RequestMapping(value = "extendSave")
	public String extendSave(TLoanContract tLoanContract, Model model, RedirectAttributes redirectAttributes) {
		
		if (!beanValidator(model, tLoanContract)){
			return extendForm(tLoanContract, model);
		}
		tLoanContractService.extendSave(tLoanContract);
		addMessage(redirectAttributes, "展期合同提交成功");
		//model.addAttribute("isClose", Global.YES);
		//return "redirect:"+Global.getAdminPath()+"/refund/reimburse/?repage";
		//3597	B端	Bug
		redirectAttributes.addAttribute("isClose", Global.YES);			
		return "redirect:"+Global.getAdminPath()+"/contract/tLoanContract/extendForm?act.procDefId=''&act.vars.keys=skip&parent.id="  + tLoanContract.getParent().getId();
		
	}
	
	
	@RequestMapping(value = "detail")
	public String detail(TLoanContract tLoanContract, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		
		String tab = request.getParameter("tab");
		
		FModel fm = FModel.M_BUSINESS_APPLICATION_YWSQ;
		DfFormTpl dfFormTpl = dfFormTplService.getByBizCode(fm.getKey());
		//业务申请审批记录
		List<?>  loanexaminelist = tLoanContractService.getExamineList(tLoanContract);
		//还款计划表
		TRepayPlan tRepayPlan = new TRepayPlan();
		tRepayPlan.setLoanContractId(tLoanContract.getId());
		List<TRepayPlan> repayplanList = tRepayPlanService.findList(tRepayPlan);
		 // 2-1 还款记录 t_repay_real
		Page<RepayRecord> pageRepayRecord = new Page<RepayRecord>();// 用于设置排序方式
		pageRepayRecord.setOrderBy("repay_date asc");
		RepayRecord repayRecord = new RepayRecord();
		repayRecord.setLoanContractId(tLoanContract.getId());
		repayRecord.setPage(pageRepayRecord);
		List<RepayRecord> repRecordList = repayRecordService.findList(repayRecord);
		// 2-2 逾期记录 t_repay_plan
		tRepayPlan.setIsYuQi(Cons.LoanContractStatus.OVERDUE);
		tRepayPlan.setIsYuQi("1");
		List<TRepayPlan> overdueList = tRepayPlanService.findList(tRepayPlan);
		// 3 违约记录 t_real_income
		Page<RealIncome> pageRealIncome = new Page<RealIncome>();
		pageRealIncome.setOrderBy(" pay_real_date asc");
		RealIncome realIncome = new RealIncome();
		realIncome.setPage(pageRealIncome);
		realIncome.setLoanContractId(tLoanContract.getId());
		List<RealIncome> realIncomeList = realIncomeService.findList(realIncome);
		  //退费记录
		Reimburse reimburse = new Reimburse();
		reimburse.setLoanContractId(tLoanContract.getId());
		List<Reimburse>  reimburseList = reimburseService.findList(reimburse);
		
		//提前还款记录
		Advance advance = new Advance();
		advance.setLoanContractId(tLoanContract.getId());
		List<Advance>  advanceList = advanceService.findList(advance);
		
		//放款记录
		List<?> lendRecord = tLendingService.getLendRecord(tLoanContract.getId());
		
		//Map<String,String> map=new HashMap<String,String>();
		//========================#担保信息 start============================
		if(StringUtils.isNotBlank(tab) && "ginfo".equals(tab) && StringUtils.isNotBlank(tLoanContract.getId())){
			//获取抵押内容
			MortgageContract mortgageContract = new MortgageContract();
			mortgageContract.setBusinessId(tLoanContract.getId());
			
			List<MortgageContract> mortgageList = mortgageContractService.findList(mortgageContract);
			if(mortgageList != null && mortgageList.size()>0){
				model.addAttribute("mortgageList", mortgageList);
			}
			//获取质押内容
			PledgeContract pledgeContract = new PledgeContract();
			pledgeContract.setBusinessId(tLoanContract.getId());
			
			List<PledgeContract> pledgeList = pledgeContractService.findList(pledgeContract);
			if(pledgeList != null && pledgeList.size()>0){
				model.addAttribute("pledgeList", pledgeList);
			}
			//获取保证内容
			TGuaranteeContract tGuaranteeContract = new TGuaranteeContract();
			tGuaranteeContract.setBusinessId(tLoanContract.getId());
			
			List<TGuaranteeContract> guaranteeList = tGuaranteeContractService.findList(tGuaranteeContract);
			if(guaranteeList != null && guaranteeList.size()>0){
				model.addAttribute("guaranteeList", guaranteeList);
			}
		}
		//========================#担保信息 end==============================
		
		BusinessContract businessContract = new BusinessContract();
		businessContract.setLoanContract(tLoanContract);
		List<BusinessContract> businessContractList = BusinessContractService.findList(businessContract);		
		model.addAttribute("businessContractList", businessContractList);
		
		model.addAttribute("reimburseList",reimburseList);//退费历史记录
		model.addAttribute("loanexaminelist",loanexaminelist);//审批历史记录
		model.addAttribute("repayplanList",repayplanList);//还款计划表
		model.addAttribute("repRecordList", repRecordList); // 2-1 还款记录
		model.addAttribute("overdueList", overdueList); // 2-2 逾期记录
		model.addAttribute("realIncomeList", realIncomeList); // 3 违约记录
		model.addAttribute("advanceList", advanceList); // 4 提前还款记录
		model.addAttribute("lendRecord", lendRecord);//放款记录 #3639
		model.addAttribute("productName",tProductService.get(tLoanContract.getProductId()).getName());
		model.addAttribute("tLoanContract", tLoanContract);
		model.addAttribute("action", fm.getAction());
		model.addAttribute("dfFormTpl", dfFormTpl);		
		return "modules/contract/tLoanContractDetail";
	}
	
	
	
	
	/**
	 * @Description 展期签订合同  状态：待签订——》未结清
	 * @param id	合同id
	 * @return
	 * @author zzm 
	 * @date 2016-4-14 上午11:17:04  
	 */
	@RequestMapping(value = "extendSign")
	@ResponseBody
	public String extendSign(TLoanContract tLoanContract) {
		if(Cons.LoanContractStatus.ET_TO_SIGN.equals(tLoanContract.getStatus())){
			tLoanContract.setStatus(Cons.LoanContractStatus.UN_CLEARED);
			tLoanContractService.updateStatus(tLoanContract);
			
			//原合同改为已展期，无后续操作（后续操作在新的展期合同里进行）  20161010加----lingx
			tLoanContract.getParent().setStatus(Cons.LoanContractStatus.EXTENDED);
			tLoanContractService.updateStatus(tLoanContract.getParent());
			//--lingx
			
			return "true";
		}
		return "false";//adminPath + "/act/task";
	}
	
	/**
	 * @Description 异步获取贷款合同
	 * @param tLoanContract
	 * @return
	 * @author zzm
	 * @date 2016-6-14 上午10:36:22  
	 */
	@RequestMapping(value = "getLoanContracts")
	@ResponseBody
	public List<TLoanContract> getLoanContracts(HttpServletRequest request, Model model, TLoanContract tLoanContract) {
		tLoanContract.setProductId((String)UserUtils.getCache(UserUtils.CACHE_SYSCODE));
		if(StringUtils.isNotBlank(tLoanContract.getId()) || StringUtils.isNotBlank(tLoanContract.getContractNumber())){//有ID的情况下只返回ID对应的那条数据(修复查看详情没有关联业务的情况)
			List<TLoanContract> list = new ArrayList<TLoanContract>();
			list.add(tLoanContract);
			return list;
		}else{
			return tLoanContractService.findList(tLoanContract);
		}
	}
	
	
	/**
	 * @Description 跳转到风险控制页面
	 * @param riskScore
	 * @return
	 * @author Chenh 
	 * @date 2016年3月30日 上午11:02:34
	 */
	@RequiresPermissions("contract:tLoanContract:view")
	@RequestMapping(value = "toRiskControl")
	public String toRiskControl(HttpServletRequest request, HttpServletResponse response, Model model, RiskScore riskScore) {
		model.addAttribute("riskScore",riskScore);
		return "/modules/contract/riskControl";
	}
	
	/**
	 * @Description 获取风险控制评分
	 * @param riskScore
	 * @return
	 * @author zzm
	 * @date 2016-6-14 上午10:36:22  
	 */
	@RequestMapping(value = "getRiskScore")
	@ResponseBody
	public RiskScore getRiskScore(HttpServletRequest request, Model model, RiskScore riskScore) {
		return riskScoreService.getRiskScoreByB(riskScore);
	}
	
	
	/**
	 **申请人评分审核（调风控系统接口）
	 * */
	@ResponseBody
	@RequestMapping(value = "gradeExamina")
	public int gradeExamina(String id, Model model, RedirectAttributes redirectAttributes) {
		int result = 0;
		TLoanContract contract = get(id);
		//个人客户
		if(StringUtils.equals(contract.getCustomerType(), Cons.CustomerType.CUST_EMPLOYEE)){
			TEmployee employee = tEmployeeService.getFull(contract.getCustomerId());
			if(employee != null){
				CsPersonVo param = new CsPersonVo(employee);
				
				param.setOrganCode(UserUtils.getUser().getCompany().getCode());//组织机构编码
				param.setSeqNo(contract.getContractNumber());
				param.setAmount(new BigDecimal(contract.getLoanAmount()));
				creditApplyService.calculateGrade(param);
				result = param.getAudit();
			}
			
		}
		return result;
	}
	/**
	 * 
	 * @Description 债权转让列表
	 * @param tLoanContract
	 * @return
	 * @author zzm 
	 * @date 2016-7-29 下午4:29:29
	 */
	@RequiresPermissions("transfer")
	@RequestMapping(value = "loanTransferList")
	public String loanTransferList(TLoanContract tLoanContract, HttpServletRequest request, HttpServletResponse response, Model model) {
		tLoanContract.setStatus(Cons.LoanContractStatus.TO_LOAN+","+Cons.LoanContractStatus.UN_CLEARED);//待放款、未结清
		tLoanContract.setProductId((String)UserUtils.getCache(UserUtils.CACHE_SYSCODE));//根据产品id过滤
		Page<TLoanContract> page = tLoanContractService.findPage(new Page<TLoanContract>(request, response), tLoanContract); 
		model.addAttribute("page", page);
		model.addAttribute("tLoanContract",tLoanContract);
		return "modules/contract/loanTransferList";
	}
	
	/**
	 * 
	 * @Description 转理财表单
	 * @param tLoanContract
	 * @return
	 * @author zzm 
	 * @date 2016-7-29 下午4:29:29
	 */
	@RequiresPermissions("transfer")
	@RequestMapping(value = "toLoanTransfer")
	public String toLoanTransfer(String id,String cat_id,String installment,String use, HttpServletRequest request, Model model) {
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		try {
			String reulst = InterfaceUtil.sendPost(Cons.Ips.IP_W_GETCATID, "");
			JSONObject json = new JSONObject(reulst);
			JSONArray array = json.getJSONArray("data");
			for(int i=0;i<array.length();i++){
				Map<String,String> map = Maps.newHashMap();
				map.put("id", array.getJSONObject(i).getString("id"));
				map.put("name", array.getJSONObject(i).getString("cat_name"));
				list.add(map);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		if(StringUtils.isBlank(cat_id)){
			TLoanContract contract = get(id);
			if(StringUtils.equals(Cons.YES, contract.getIsTransfered())){
				model.addAttribute("isClose", Global.YES);
				model.addAttribute("message", "此业务已转理财！");
			}
		}
		
		model.addAttribute("id",id);
		model.addAttribute("cat_id",cat_id);
		model.addAttribute("installment",installment);
		model.addAttribute("use",use);
		model.addAttribute("list",list);
		return "modules/contract/loanTransferForm";
	}
	
	/**
	 * @Description 债权转让
	 * @param id
	 * @param cat_id
	 * @param installment
	 * @param use
	 * @author zzm
	 * @date 2016-8-4 下午4:53:10  
	 */
	@RequiresPermissions("transfer")
	@RequestMapping(value = "loanTransfer")
	public String loanTransfer(String id,String cat_id,String installment,String use, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) throws JSONException {
		TLoanContract contract = get(id);
		if(StringUtils.isBlank(id) || StringUtils.isBlank(cat_id) 
				|| StringUtils.isBlank(installment) || StringUtils.isBlank(use)) {
			model.addAttribute("message", "缺少必填数据！");
			model.addAttribute("isClose", Global.NO);
		}else{
			try {
				tLoanContractService.loanTransfer(contract,cat_id,installment,use);
				model.addAttribute("message", "转理财成功！");
				model.addAttribute("isClose", Global.YES);
			} catch (ServiceException e) {
				e.printStackTrace();
				model.addAttribute("message", e.getMessage());
				model.addAttribute("isClose", Global.NO);
			}
		}
		return toLoanTransfer(id,cat_id,installment,use,request,model);
	}
	
	
	
	/**
	 *      判断当前贷款金额是否超过产品的最高上限  最低下限
	 * @param customerCredit
	 * @param response
	 * @param model
	 * @return
	 * @throws ParseException
	 */
	
	@ResponseBody
	@RequestMapping(value = "getProductAmountLimit")
	public Map<String, Object> getProductAmountLimit(String amount, HttpServletResponse response, Model model) throws ParseException{
		Map<String, Object> result = Maps.newHashMap();
		TProduct product = tProductService.get((String)UserUtils.getCache(UserUtils.CACHE_SYSCODE));
		String amountMax = product.getAmountMax();//额度上限
		String amountMin = product.getAmountMin();//额度下限
		if(amountMax!=null && !"".equals(amountMax) && Float.parseFloat(amount)>Float.parseFloat(amountMax)){
			result.put("status", 0);
			result.put("message", "贷款金额大于产品上限金额,产品上限额为["+amountMax+"]！");
			result.put("amountlimit", amountMax);
		}else if(amountMin!=null && !"".equals(amountMin) && Float.parseFloat(amount)<Float.parseFloat(amountMin)){
			result.put("status", 0);
			result.put("message", "贷款金额小于产品下限金额,产品下限额为["+amountMin+"]！");
			result.put("amountlimit", amountMin);
		}else{
			result.put("status", 1);
			result.put("message", "save！");
		}
		return result;
	}
	
	/**

	 * @Description 异步生成贷款合同树形选择框
	 * @param tLoanContract
	 * @return
	 * @author srf
	 * @date 2017-01-10  
	 */
	@RequestMapping(value = "treeDataLoanContracts")
	@ResponseBody
	public List<Map<String, Object>> treeDataLoanContracts(HttpServletRequest request, Model model, TLoanContract tLoanContract) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		tLoanContract.setProductId((String)UserUtils.getCache(UserUtils.CACHE_SYSCODE));
		boolean addMpa = false;
		if(StringUtils.isNotBlank(tLoanContract.getStatus()) && "repaycheck".equals(tLoanContract.getStatus())){
			//选取可以确认的业务信息
			tLoanContract.setStatus(null);
			addMpa = false;
		}else{
			addMpa = true;
		}
		
		List<TLoanContract> list = tLoanContractService.findList(tLoanContract);
		if(addMpa){
			if(list != null && list.size()>0){
				Map<String, Object> map = Maps.newHashMap();
				map.put("id", "0");
				map.put("pId", "");
				map.put("name", "关联业务");
				mapList.add(map);
			}
		}
		for(int i=0; i<list.size(); i++){
			TLoanContract loanContract = list.get(i);
			if(StringUtils.isNotBlank(loanContract.getId()) && StringUtils.isNotBlank(loanContract.getContractNumber())){
				Map<String, Object> map = Maps.newHashMap();
				map.put("id", loanContract.getId());
				map.put("pId", "0");
				map.put("name", loanContract.getContractNumber());
				mapList.add(map);
			}
		}
		return mapList;
	}
	
	
     /**
	 **放款申请
	 * */
	@ResponseBody
	@RequestMapping(value = "updateMoveStatus")
	public Map<String, Object>  getContractStatus(String loanContractId,String moveStatus) {
		Map<String, Object> result = Maps.newHashMap();
		TLoanContract contract = get(loanContractId);
		if(contract!=null){
			contract.setMoveStatus(moveStatus);
			tLoanContractService.updateMonveStatus(contract);
			result.put("status", 1);
		}else{
			result.put("status", 0);
			result.put("message", "审核失败，合同不存在！");
		}
		return result;
	}
	
	@RequestMapping(value = "moveDetail")
	public String moveDetail(TLoanContract tLoanContract, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		FModel fm = FModel.M_BUSINESS_APPLICATION_YWSQ;
	//	DfFormTpl dfFormTpl = dfFormTplService.getByBizCode(fm.getKey());
		//业务申请审批记录
		List<?>  loanexaminelist = tLoanContractService.getExamineList(tLoanContract);
		//还款计划表
		TRepayPlan tRepayPlan = new TRepayPlan();
		tRepayPlan.setLoanContractId(tLoanContract.getId());
		List<TRepayPlan> repayplanList = tRepayPlanService.findList(tRepayPlan);
		 // 2-1 还款记录 t_repay_real
		Page<RepayRecord> pageRepayRecord = new Page<RepayRecord>();// 用于设置排序方式
		pageRepayRecord.setOrderBy("repay_date asc");
		RepayRecord repayRecord = new RepayRecord();
		repayRecord.setLoanContractId(tLoanContract.getId());
		repayRecord.setPage(pageRepayRecord);
		List<RepayRecord> repRecordList = repayRecordService.findList(repayRecord);
		// 2-2 逾期记录 t_repay_plan
		/*tRepayPlan.setIsYuQi(Cons.LoanContractStatus.OVERDUE);#3343*/
		tRepayPlan.setIsYuQi("1");
		List<TRepayPlan> overdueList = tRepayPlanService.findList(tRepayPlan);
		// 3 违约记录 t_real_income
		Page<RealIncome> pageRealIncome = new Page<RealIncome>();
		pageRealIncome.setOrderBy(" pay_real_date asc");
		RealIncome realIncome = new RealIncome();
		realIncome.setPage(pageRealIncome);
		realIncome.setLoanContractId(tLoanContract.getId());
		List<RealIncome> realIncomeList = realIncomeService.findList(realIncome);
		  //退费记录
		Reimburse reimburse = new Reimburse();
		reimburse.setLoanContractId(tLoanContract.getId());
		List<Reimburse>  reimburseList = reimburseService.findList(reimburse);
		
		//提前还款记录
		Advance advance = new Advance();
		advance.setLoanContractId(tLoanContract.getId());
		List<Advance>  advanceList = advanceService.findList(advance);
		
		//放款记录
		List<?> lendRecord = tLendingService.getLendRecord(tLoanContract.getId());
		
		//客户信息
		CsPersonVo csPersonVo = new CsPersonVo();
		if(StringUtils.equals(tLoanContract.getCustomerType(), Cons.CustomerType.CUST_EMPLOYEE)){
			TEmployee employee = tEmployeeService.get(tLoanContract.getCustomerId());
			if(employee != null){
				csPersonVo.setCardNo(employee.getCardNum());
				csPersonVo.setPhone(employee.getMobile());
				csPersonVo.setAddress(employee.getCurrentLiveAddress());
			}
			
		}else if(StringUtils.equals(tLoanContract.getCustomerType(), Cons.CustomerType.CUST_COMPANY_TXT)){
			TCompany company = tCompanyService.get(tLoanContract.getCustomerId());
			if(company!=null){
				csPersonVo.setCardNo(company.getCardNum());
				csPersonVo.setPhone(company.getPhone());
				csPersonVo.setAddress(company.getAddress());
			}
		}
		
		//Map<String,String> map=new HashMap<String,String>();
		model.addAttribute("reimburseList",reimburseList);//退费历史记录
		model.addAttribute("loanexaminelist",loanexaminelist);//审批历史记录
		model.addAttribute("repayplanList",repayplanList);//还款计划表
		model.addAttribute("repRecordList", repRecordList); // 2-1 还款记录
		model.addAttribute("overdueList", overdueList); // 2-2 逾期记录
		model.addAttribute("realIncomeList", realIncomeList); // 3 违约记录
		model.addAttribute("advanceList", advanceList); // 4 提前还款记录
		model.addAttribute("lendRecord", lendRecord);//放款记录 #3639
		model.addAttribute("productName",tProductService.get(tLoanContract.getProductId()).getName());
		model.addAttribute("tLoanContract", tLoanContract);
		model.addAttribute("action", fm.getAction());
		model.addAttribute("csPersonVo", csPersonVo);
	//	model.addAttribute("dfFormTpl", dfFormTpl);
		return "modules/contract/tLoanMoveContractDetail";
	}
	/**
	 * 订单转移页面
	 * @Description
	 * @param tLoanContract
	 * @return
	 * @author zzm 
	 * @date 2016-7-29 下午4:29:29
	 */
	@RequestMapping(value = "moveContract")
	public String moveContract(String loanContractId, String officeId,HttpServletRequest request, Model model) {
		Office office = officeService.get(officeId);
		model.addAttribute("office", office);
		model.addAttribute("loanContractId", loanContractId);
		model.addAttribute("officeId", officeId);
		return "modules/contract/moveOfficeForm";
	}
	/**
	 * 订单转移提交
	 * @Description
	 * @param tLoanContract
	 * @return
	 * @author zzm 
	 * @date 2016-7-29 下午4:29:29
	 */
	@ResponseBody
	@RequestMapping(value = "saveMove")
	public Map<String, Object>  saveMove(String loanContractId, String officeId,HttpServletRequest request, Model model) {
		Map<String, Object> result = Maps.newHashMap();
		Office office = officeService.get(officeId);
		if(office==null){
			result.put("status", 0);
			result.put("message", "机构不存在！");
			return result;
		}
		TLoanContract tLoanContract=tLoanContractService.get(loanContractId);
		tLoanContract.setMoveOffice(new Office(tLoanContract.getOrganId()));
		tLoanContract.setOrganId(officeId);
		tLoanContract.setMoveStatus(Cons.MoveStatus.AUDIT_ING);
		tLoanContractService.save(tLoanContract);
		result.put("status", 1);
		result.put("message", "订单推送成功！");
		return result;
	}
	/**
	 * 平台审核列表
	 * @param tLoanContract
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("contract:tLoanContract:view")
	@RequestMapping(value = {"moveContractlist"})
	public String moveContractlist(TLoanContract tLoanContract, HttpServletRequest request, HttpServletResponse response, Model model) {
		tLoanContract.setStatus(Cons.LoanContractStatus.TO_LOAN);//待放款
		//tLoanContract.setProductId((String)UserUtils.getCache(UserUtils.CACHE_SYSCODE));// by zzm 根据产品id过滤
		//tLoanContract.setMoveStatus(Cons.MoveStatus.AUDIT_ING);
		//String organId=UserUtils.getUser().getCompany().getId();
		//tLoanContract.setMoveOffice(new Office(organId));
		Page<TLoanContract> page = tLoanContractService.moveContractlist(new Page<TLoanContract>(request, response), tLoanContract); 
		model.addAttribute("page", page);
		model.addAttribute("tLoanContract",tLoanContract);
		model.addAttribute("admin",UserUtils.getUser().isAdmin());
		
		return "modules/contract/moveContractList";
	}
	/**
	 * 订单转移发起列表
	 * @param tLoanContract
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("contract:tLoanContract:view")
	@RequestMapping(value = {"pushMoveContractlist"})
	public String pushMoveContractlist(TLoanContract tLoanContract, HttpServletRequest request, HttpServletResponse response, Model model) {
		tLoanContract.setStatus(Cons.LoanContractStatus.TO_LOAN);//待放款
		//tLoanContract.setProductId((String)UserUtils.getCache(UserUtils.CACHE_SYSCODE));// by zzm 根据产品id过滤
		//tLoanContract.setMoveStatus(Cons.MoveStatus.AUDIT_ING);
		String organId=UserUtils.getUser().getCompany().getId();
		tLoanContract.setMoveOffice(new Office(organId));
		Page<TLoanContract> page = tLoanContractService.pushMoveContractlist(new Page<TLoanContract>(request, response), tLoanContract); 
		model.addAttribute("page", page);
		model.addAttribute("tLoanContract",tLoanContract);
		model.addAttribute("admin",UserUtils.getUser().isAdmin());
		
		return "modules/contract/moveContractList";
	}
	/**
	 * 订单转移接收列表
	 * @param tLoanContract
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("contract:tLoanContract:view")
	@RequestMapping(value = {"pullMoveContractlist"})
	public String pullMoveContractlist(TLoanContract tLoanContract, HttpServletRequest request, HttpServletResponse response, Model model) {
		tLoanContract.setStatus(Cons.LoanContractStatus.TO_LOAN);//待放款
		//tLoanContract.setProductId((String)UserUtils.getCache(UserUtils.CACHE_SYSCODE));// by zzm 根据产品id过滤
		//tLoanContract.setMoveStatus(Cons.MoveStatus.AUDIT_ING);
		String organId=UserUtils.getUser().getCompany().getId();
		tLoanContract.setOrganId(organId);
		Page<TLoanContract> page = tLoanContractService.pullMoveContractlist(new Page<TLoanContract>(request, response), tLoanContract); 
		model.addAttribute("page", page);
		model.addAttribute("tLoanContract",tLoanContract);
		model.addAttribute("admin",UserUtils.getUser().isAdmin());
		return "modules/contract/moveContractList";

	}
}
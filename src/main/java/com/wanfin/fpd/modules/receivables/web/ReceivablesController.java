package com.wanfin.fpd.modules.receivables.web;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mangofactory.swagger.annotations.ApiIgnore;
import com.wanfin.fpd.common.config.Cons;
import com.wanfin.fpd.common.config.Cons.FModel;
import com.wanfin.fpd.common.config.SvalBase;
import com.wanfin.fpd.common.mapper.JsonMapper;
import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.service.ServiceException;
import com.wanfin.fpd.common.utils.DateUtils;
import com.wanfin.fpd.common.utils.StringUtils;
import com.wanfin.fpd.common.web.BaseController;
import com.wanfin.fpd.modules.contract.entity.TLoanContract;
import com.wanfin.fpd.modules.contract.service.TLoanContractService;
import com.wanfin.fpd.modules.form.entity.tpl.DfFormTpl;
import com.wanfin.fpd.modules.form.service.tpl.DfFormTplService;
import com.wanfin.fpd.modules.postlending.entity.advance.Advance;
import com.wanfin.fpd.modules.postlending.service.advance.AdvanceService;
import com.wanfin.fpd.modules.product.entity.TProduct;
import com.wanfin.fpd.modules.product.service.TProductService;
import com.wanfin.fpd.modules.receivables.entity.RealIncome;
import com.wanfin.fpd.modules.receivables.entity.RepayRecord;
import com.wanfin.fpd.modules.receivables.service.RealIncomeService;
import com.wanfin.fpd.modules.receivables.service.RepayRecordService;
import com.wanfin.fpd.modules.repayplan.entity.TRepayPlan;
import com.wanfin.fpd.modules.repayplan.service.TRepayPlanService;
import com.wanfin.fpd.modules.repayplan.vo.PlanCreateParam;
import com.wanfin.fpd.modules.sys.utils.UserUtils;

/**
 * 还款计划Controller：收款业务所有内容控制
 * 
 * @author srf
 * @version 2016-03-29
 */
@ApiIgnore
@Controller
@RequestMapping(value = "${adminPath}/receivables/receivables")
public class ReceivablesController extends BaseController {
	// 合同
	@Autowired
	private TLoanContractService tLoanContractService;
	// 还款计划
	@Autowired
	private TRepayPlanService tRepayPlanService;
	// 真实还款记录
	@Autowired
	private RepayRecordService repayRecordService;
	// 合同对应产品类型
	@Autowired
	private TProductService tProductService;
	// 违约金，咨询费
	@Autowired
	private RealIncomeService realIncomeService;
	@Autowired
	private DfFormTplService dfFormTplService;
	@Autowired
	private AdvanceService advanceService;
	
	/**
	 * 获取具体贷款合同列表信息
	 */
	@RequiresPermissions("receivables:receivables:view")
	@RequestMapping(value = { "list", "" })
	public String list(TLoanContract tLoanContract, HttpServletRequest request, HttpServletResponse response,
			Model model) {
		tLoanContract.setStatus(Cons.LoanContractStatus.UN_CLEARED+","+
				Cons.LoanContractStatus.DB_TO_CHARGE);
		Page<TLoanContract> page = tLoanContractService.findPage(new Page<TLoanContract>(request, response),
				tLoanContract);
		model.addAttribute("page", page);
		model.addAttribute("loanContract", tLoanContract);

		return "modules/receivables/receivablesList";
	}

	/**
	 * 收款信息: 1、还款操作 2、还款计划 3、还款记录 4、逾期记录 5、违约记录 6、合同信息
	 * @throws UnsupportedEncodingException 
	 */
	@RequiresPermissions("receivables:receivables:view")
	@RequestMapping(value = "receView")
	/*old*/
	public String receView(HttpServletRequest request, HttpServletResponse response, Model model,
			@RequestParam(required = true) String contractId,@RequestParam(required = true) String message) throws UnsupportedEncodingException {
		// 1 显示还款计划列表 t_repay_plan
		TRepayPlan tRepayPlan = new TRepayPlan();
		tRepayPlan.setLoanContractId(contractId);
		List<TRepayPlan> repayPlanList = tRepayPlanService.findListCondition(tRepayPlan);
		
		// 2-1 还款记录 t_repay_real
		Page<RepayRecord> pageRepayRecord = new Page<RepayRecord>();// 用于设置排序方式
		pageRepayRecord.setOrderBy("update_date asc");
		RepayRecord repayRecord = new RepayRecord();
		repayRecord.setLoanContractId(contractId);
		repayRecord.setPage(pageRepayRecord);
		List<RepayRecord> repRecordList = repayRecordService.findList(repayRecord);
		// 2-2 逾期记录 t_repay_plan
		tRepayPlan.setIsYuQi("1");
		List<TRepayPlan> overdueList = tRepayPlanService.findList(tRepayPlan);

		//最新一期未还记录
		TRepayPlan trp = new TRepayPlan();
		trp.setLoanContractId(contractId);
		//tRepayPlan.setStatus(Cons.RepayStatus.NEED_PAY);// 只要未结清状态，修改状态值可能需要同时修改mybatis对应的xml配置文件
		List<TRepayPlan> trpList = tRepayPlanService.findByGroup(tRepayPlan);//根据未还款计划获取第一条未还期数
		
		int num=0;
		BigDecimal reInterest=new BigDecimal(0.0);
		BigDecimal rePrincipal=new BigDecimal(0.0);
		if(trpList.size()>0){
			TRepayPlan tmpRepayPlan = trpList.get(0);

			// 需要处理为空的情况
			BigDecimal interest = tmpRepayPlan.getInterest() == null ? new BigDecimal(0) : new BigDecimal(tmpRepayPlan.getInterest());// 利息
			BigDecimal interestReal = tmpRepayPlan.getInterestReal() == null ? new BigDecimal(0) : new BigDecimal(tmpRepayPlan.getInterestReal());// 真实利息
			BigDecimal principal = tmpRepayPlan.getPrincipal() == null ? new BigDecimal(0) : new BigDecimal(tmpRepayPlan.getPrincipal());// 本金
			BigDecimal principalReal = tmpRepayPlan.getPrincipalReal() == null ? new BigDecimal(0) : new BigDecimal(tmpRepayPlan.getPrincipalReal());// 真实本金
			
			reInterest = interest.subtract(interestReal).setScale(2, BigDecimal.ROUND_HALF_UP);// 需要还的利息
			rePrincipal = principal.subtract(principalReal).setScale(2, BigDecimal.ROUND_HALF_UP);// 需要还的本金
			num=trpList.get(0).getNum();//第一条未还期数
			
			if(reInterest.compareTo(new BigDecimal(0.0))==0 && rePrincipal.compareTo(new BigDecimal(0.0))==0){//本期不需要还  处理下一期(首期只有利息没有本金且利息已还会出现这种情况)
				 tmpRepayPlan = trpList.get(1);
				 // 需要处理为空的情况
				 interest = tmpRepayPlan.getInterest() == null ? new BigDecimal(0) : new BigDecimal(tmpRepayPlan.getInterest());// 利息
				 interestReal = tmpRepayPlan.getInterestReal() == null ? new BigDecimal(0) : new BigDecimal(tmpRepayPlan.getInterestReal());// 真实利息
				 principal = tmpRepayPlan.getPrincipal() == null ? new BigDecimal(0) : new BigDecimal(tmpRepayPlan.getPrincipal());// 本金
				 principalReal = tmpRepayPlan.getPrincipalReal() == null ? new BigDecimal(0) : new BigDecimal(tmpRepayPlan.getPrincipalReal());// 真实本金
				
				reInterest = interest.subtract(interestReal).setScale(2, BigDecimal.ROUND_HALF_UP);// 需要还的利息
				rePrincipal = principal.subtract(principalReal).setScale(2, BigDecimal.ROUND_HALF_UP);// 需要还的本金
				num=trpList.get(1).getNum();
			}
			
			
		}
	
		
		// 3 违约记录 t_real_income
		Page<RealIncome> pageRealIncome = new Page<RealIncome>();
		pageRealIncome.setOrderBy(" pay_real_date asc");
		RealIncome realIncome = new RealIncome();
		realIncome.setPage(pageRealIncome);
		realIncome.setLoanContractId(contractId);
		List<RealIncome> realIncomeList = realIncomeService.findList(realIncome);

		// 4 合同信息 t_loan_contract
		TLoanContract loanContract = tLoanContractService.get(contractId);
		
		
		// 5 合同对应的产品类型
		TProduct product = tProductService.get(loanContract.getProductId());
		
		if (repayPlanList == null || repayPlanList.size() <= 0) {
			//修改 	3555	
			this.createPl(loanContract);			
			repayPlanList = tRepayPlanService.findListCondition(tRepayPlan);
		}

		//提前还款记录
		Advance advance = new Advance();
		advance.setLoanContractId(contractId);
		List<Advance>  advanceList = advanceService.findList(advance);
		
		model.addAttribute("contractId", contractId); // 合同ID
		model.addAttribute("repayPlanList", repayPlanList); // 1 还款计划列表
		model.addAttribute("repRecordList", repRecordList); // 2-1 还款记录
		model.addAttribute("overdueList", overdueList); // 2-2 逾期记录
		model.addAttribute("realIncomeList", realIncomeList); // 3 违约记录
		model.addAttribute("advanceList", advanceList); //  提前还款记录
		model.addAttribute("loanContract", loanContract);// 4 合同信息
		model.addAttribute("product", product);// 5 合同对应的产品类型
		model.addAttribute("num", num);//最新未还期数
		model.addAttribute("reInterest", reInterest);//最新一期应还利息
		model.addAttribute("rePrincipal", rePrincipal);// 最新一期应还本金
		

		if("1".equals(message)){//message=1代表是从list里面点进去的，此时不存在已经还金额
			message = getmessage(tRepayPlan,loanContract,"0");
		}
		
		model.addAttribute("message", URLDecoder.decode(message, "UTF-8"));//从还款页面带过来的数据

		return "modules/receivables/receivablesView";
	}
	/**
	 * 收费信息: 1、还款操作 2、还款计划 3、还款记录 4、逾期记录 5、违约记录 6、合同信息
	 * @throws UnsupportedEncodingException 
	 */
	@RequiresPermissions("receivables:receivables:view")
	@RequestMapping(value = "freeView")
	/*old*/
	public String freeView(HttpServletRequest request, HttpServletResponse response, Model model,
			@RequestParam(required = true) String contractId,@RequestParam(required = true) String message) throws UnsupportedEncodingException {
		// 1 显示还款计划列表 t_repay_plan
		TRepayPlan tRepayPlan = new TRepayPlan();
		tRepayPlan.setLoanContractId(contractId);
		List<TRepayPlan> repayPlanList = tRepayPlanService.findListCondition(tRepayPlan);
		
		// 2-1 还款记录 t_repay_real
		Page<RepayRecord> pageRepayRecord = new Page<RepayRecord>();// 用于设置排序方式
		pageRepayRecord.setOrderBy("update_date asc");
		RepayRecord repayRecord = new RepayRecord();
		repayRecord.setLoanContractId(contractId);
		repayRecord.setPage(pageRepayRecord);
		List<RepayRecord> repRecordList = repayRecordService.findList(repayRecord);
		// 2-2 逾期记录 t_repay_plan
		tRepayPlan.setIsYuQi("1");
		List<TRepayPlan> overdueList = tRepayPlanService.findList(tRepayPlan);

		// 3 违约记录 t_real_income
		Page<RealIncome> pageRealIncome = new Page<RealIncome>();
		pageRealIncome.setOrderBy(" pay_real_date asc");
		RealIncome realIncome = new RealIncome();
		realIncome.setPage(pageRealIncome);
		realIncome.setLoanContractId(contractId);
		List<RealIncome> realIncomeList = realIncomeService.findList(realIncome);

		// 4 合同信息 t_loan_contract
		TLoanContract loanContract = tLoanContractService.get(contractId);
		
		
		// 5 合同对应的产品类型
		TProduct product = tProductService.get(loanContract.getProductId());
		
		if (repayPlanList == null || repayPlanList.size() <= 0) {
			//修改 	3555	
			this.createPl(loanContract);			
			repayPlanList = tRepayPlanService.findListCondition(tRepayPlan);
		}

		//提前还款记录
		Advance advance = new Advance();
		advance.setLoanContractId(contractId);
		List<Advance>  advanceList = advanceService.findList(advance);
		
		model.addAttribute("contractId", contractId); // 合同ID
		model.addAttribute("repayPlanList", repayPlanList); // 1 还款计划列表
		model.addAttribute("repRecordList", repRecordList); // 2-1 还款记录
		model.addAttribute("overdueList", overdueList); // 2-2 逾期记录
		model.addAttribute("realIncomeList", realIncomeList); // 3 违约记录
		model.addAttribute("advanceList", advanceList); //  提前还款记录
		model.addAttribute("loanContract", loanContract);// 4 合同信息
		model.addAttribute("product", product);// 5 合同对应的产品类型
		

		if("1".equals(message)){//message=1代表是从list里面点进去的，此时不存在已经还金额
			message = getmessage(tRepayPlan,loanContract,"0");
		}
		
		model.addAttribute("message", URLDecoder.decode(message, "UTF-8"));//从还款页面带过来的数据

		return "modules/receivables/freeView";
	}
	private void createPl(TLoanContract contract) {
		//登录人为贷款的，才需要保持还款计划
		if(UserUtils.getUser().getCompany().getPrimaryPerson()!=null&&"2".equals(UserUtils.getUser().getCompany().getPrimaryPerson())){
			//先清除旧的还款计划
			//TRepayPlan plan = new TRepayPlan();
			//plan.setLoanContractId(contract.getId());
			//tRepayPlanService.deletePLWL(plan);
			//JSONObject resultJson;
			try {
				PlanCreateParam param = new PlanCreateParam();
				param.setBusinessType("apply");
				//param.setAmount(Double.valueOf(entity.getLoanAmount()));//old #3121
				//param.setLoanRate(Float.valueOf(entity.getLoanRate()));//old #3121
				param.setAmount(new BigDecimal(contract.getLoanAmount()));//changed #3121
				param.setLoanRate(new BigDecimal(contract.getLoanRate()));//changed #3121
				
				if("年".equals(contract.getLoanRateType())){
					param.setLoanRateType("1");//利率类型   1年  2月  3日  
				}else if("月".equals(contract.getLoanRateType())){
					param.setLoanRateType("2");//利率类型   1年  2月  3日  
				}else if("日".equals(contract.getLoanRateType())){
					param.setLoanRateType("3");//利率类型   1年  2月  3日  
				}
				//param.setLoanRateType(entity.getLoanRateType());//
				param.setLoanPeriod(contract.getLoanPeriod());
				param.setLoanDate(DateUtils.formatDate(contract.getLoanDate()));
				param.setPayType(contract.getPayType());
				param.setPeriodType(contract.getPeriodType());
				param.setPayOptions(contract.getPayOptions());
				param.setPayDay(contract.getPayDay());
				if(StringUtils.isBlank(contract.getIfRealityDay())){
					contract.setIfRealityDay("0");
				}
				param.setIfRealityDay(contract.getIfRealityDay());
				JSONArray list = tRepayPlanService.createRepayPlans(param);
				if(list != null){
					String lotNum = tRepayPlanService.createLotNum();
					for(int i=0;i<list.length();i++){
						TRepayPlan repayPlan = (TRepayPlan) JsonMapper.fromJsonString(list.get(i).toString(), TRepayPlan.class);
						repayPlan.setLotNum(lotNum);
						repayPlan.setLoanContractId(contract.getId());
						repayPlan.setStatus(Cons.RepayStatus.NO_PAID);
						repayPlan.setIsValid("1");
						tRepayPlanService.save(repayPlan);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw new ServiceException("保存还款计划时出错！");
			}
		}
	}

	
	 
	/**
	 * 担保收费方法
	 * @throws UnsupportedEncodingException 
	 */
	@RequiresPermissions("receivables:receivables:view")
	@RequestMapping(value = "receViewDB")
	public String receViewDB(HttpServletRequest request, HttpServletResponse response, Model model,
			@RequestParam(required = true) String contractId,@RequestParam(required = true) String message) throws UnsupportedEncodingException {
		
		TLoanContract tLoanContract = tLoanContractService.get(contractId);
		
		if(tLoanContract.getArea()!=null){
			tLoanContract.setAreaIdT(tLoanContract.getArea().getId());
		}
		
		// 3 违约记录 t_real_income
		Page<RealIncome> pageRealIncome = new Page<RealIncome>();
		pageRealIncome.setOrderBy(" pay_real_date asc");
		RealIncome realIncome = new RealIncome();
		realIncome.setPage(pageRealIncome);
		realIncome.setLoanContractId(contractId);
		List<RealIncome> realIncomeList = realIncomeService.findList(realIncome);
		
		FModel fm = FModel.M_BUSINESS_APPLICATION_YWSQ;
		DfFormTpl dfFormTpl = dfFormTplService.getByBizCode(fm.getKey());
		model.addAttribute("action", fm.getAction());
		model.addAttribute("dfFormTpl", dfFormTpl);
		model.addAttribute("data", tLoanContract);   
		model.addAttribute("realIncomeList", realIncomeList); // 3 违约记录


		return "modules/receivables/receivablesViewDB";
	}

	 
	 
	/**
	 * 还款
	 * 
	 * @param contractId
	 *            对应合同ID
	 * @param contractDate
	 *            还款时间
	 * @param contractAmount
	 *            还款金额
	 * @return
	 */
	@RequiresPermissions("receivables:receivables:view")
	@RequestMapping(value = "receMoney")
	@ResponseBody
	public Map<String, Object> receMoney(HttpServletRequest request, HttpServletResponse response, Model model,
			@RequestParam(required = true) String contractId, @RequestParam(required = true) String contractDate,
			@RequestParam(required = true) String contractAmount) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put(SvalBase.JsonKey.KEY_STATUS, true);

		if (contractId == null || "".equals(contractId)) {
			result.put(SvalBase.JsonKey.KEY_MSG, "传入参数有误！");
			return result;
		}

		model.addAttribute("contractId", contractId);// 每次跳转都要传递

		TLoanContract contract = tLoanContractService.get(contractId);
		
		// 获取需要还款的数据
		TRepayPlan tRepayPlan = new TRepayPlan();
		tRepayPlan.setLoanContractId(contractId);
		HashMap<String, Double> map = tRepayPlanService.allRepayment(tRepayPlan);
		if (map == null) {
			result.put(SvalBase.JsonKey.KEY_MSG, "该合同没有相应的还款计划！");
			return result;
		}
		Double interest = map.get("interest") == null ? 0.0 : map.get("interest");// 利息
		Double interestReal = map.get("interest_real") == null ? 0.0 : map.get("interest_real");// 真实利息
		Double principal = map.get("principal") == null ? 0.0 : map.get("principal");// 本金
		Double principalReal = map.get("principal_real") == null ? 0.0 : map.get("principal_real");// 真实本金

		// 所需还款总额
		Double needRepay = BigDecimal.valueOf(interest + principal)
				.subtract(BigDecimal.valueOf(interestReal + principalReal))
				.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

		Double contAmount = Double.valueOf(contractAmount) == null ? 0.0 : Double.valueOf(contractAmount);
		RepayRecord repayRecord = new RepayRecord();
		repayRecord.setLoanContractId(contractId);
		repayRecord.setMoney(Double.valueOf(contractAmount));
		repayRecord.setRepayDate(DateUtils.parseDate(contractDate));
		String re_message = getmessage(tRepayPlan,contract,contractAmount);
		if (contAmount <= 0.0) {
			result.put(SvalBase.JsonKey.KEY_MSG, "还款金额不能为0或小于0！");
			result.put(SvalBase.JsonKey.KEY_CONTENT, contract.getId());
			result.put(SvalBase.JsonKey.KEY_RESULT,re_message);
			return result;
		} else if (contAmount > needRepay) {
			result.put(SvalBase.JsonKey.KEY_MSG, "还款金额不能超过所需还款总额！");
			result.put(SvalBase.JsonKey.KEY_CONTENT, contract.getId());
			result.put(SvalBase.JsonKey.KEY_RESULT,re_message);
			return result;
		}
		
		repayRecordService.save(repayRecord);// 内部处理还款计划表中对应的数据
		result.put(SvalBase.JsonKey.KEY_MSG, "还款成功！");
		result.put(SvalBase.JsonKey.KEY_RESULT,re_message);
		result.put(SvalBase.JsonKey.KEY_CONTENT, contract.getId());
		return result;
	}
	/**
	 * 单期还款
	 * 
	 * @param contractId
	 *            对应合同ID
	 * @param contractDate
	 *            还款时间
	 * @param contractAmount
	 *            还款金额
	 * @return
	 */
	@RequiresPermissions("receivables:receivables:view")
	@RequestMapping(value = "singleReceMoney")
	@ResponseBody 
	public Map<String, Object> singleReceMoney(HttpServletRequest request, HttpServletResponse response, Model model,
			@RequestParam(required = true) String contractId, @RequestParam(required = true) String contractDate,
			@RequestParam(required = true) Double rePrincipal,@RequestParam(required = true) Double reInterest,@RequestParam(required = true) int num,@RequestParam(required = true) String isFinsh) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put(SvalBase.JsonKey.KEY_STATUS, true);
		if (contractId == null || "".equals(contractId)) {
			result.put(SvalBase.JsonKey.KEY_MSG, "传入参数有误！");
			return result;
		}

		model.addAttribute("contractId", contractId);// 每次跳转都要传递

		TLoanContract contract = tLoanContractService.get(contractId);
		
		// 获取需要还款的数据
		TRepayPlan tRepayPlan = new TRepayPlan();
		tRepayPlan.setLoanContractId(contractId);
		HashMap<String, Double> map = tRepayPlanService.allRepayment(tRepayPlan);
		if (map == null) {
			result.put(SvalBase.JsonKey.KEY_MSG, "该合同没有相应的还款计划！");
			return result;
		}
		Double interest = map.get("interest") == null ? 0.0 : map.get("interest");// 利息
		Double interestReal = map.get("interest_real") == null ? 0.0 : map.get("interest_real");// 真实利息
		Double principal = map.get("principal") == null ? 0.0 : map.get("principal");// 本金
		Double principalReal = map.get("principal_real") == null ? 0.0 : map.get("principal_real");// 真实本金

		// 所需还款总额
		Double needRepay = BigDecimal.valueOf(interest + principal)
				.subtract(BigDecimal.valueOf(interestReal + principalReal))
				.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

		Double contAmount =  Double.valueOf(reInterest+rePrincipal);
		RepayRecord repayRecord = new RepayRecord();
		repayRecord.setLoanContractId(contractId);
		repayRecord.setMoney(Double.valueOf(reInterest+rePrincipal));
		repayRecord.setReInterest(reInterest);
		repayRecord.setRePrincipal(rePrincipal);
		repayRecord.setRepayDate(DateUtils.parseDate(contractDate));
		repayRecord.setNum(num);
		repayRecord.setIsFinsh(isFinsh);
		String re_message = getmessage(tRepayPlan,contract,String.valueOf(reInterest+rePrincipal));
		if (contAmount <= 0.0) {
			if(!isFinsh.equals("1")){
				result.put(SvalBase.JsonKey.KEY_MSG, "还款金额不能为0或小于0！");
				result.put(SvalBase.JsonKey.KEY_CONTENT, contract.getId());
				result.put(SvalBase.JsonKey.KEY_RESULT,re_message);
				return result;
			}
		} else if (contAmount > needRepay) {
			result.put(SvalBase.JsonKey.KEY_MSG, "还款金额不能超过所需还款总额！");
			result.put(SvalBase.JsonKey.KEY_CONTENT, contract.getId());
			result.put(SvalBase.JsonKey.KEY_RESULT,re_message);
			return result;
		}
		
		repayRecordService.singleRepayDeals(repayRecord);// 内部处理还款计划表中对应的数据
		result.put(SvalBase.JsonKey.KEY_MSG, "还款成功！");
		result.put(SvalBase.JsonKey.KEY_RESULT,re_message);
		result.put(SvalBase.JsonKey.KEY_CONTENT, contract.getId());
		return result;
	}
	
	public String getmessage(TRepayPlan tRepayPlan ,TLoanContract contract,String contractAmount){
		
		//List<TRepayPlan> repayPlanList = tRepayPlanService.findListCondition(tRepayPlan);
		Double jhMoney = 0.0;
		Double money = 0.0;//总计已还金额
		//float totallixi = 0;//总计需要还利息
		
		//修改Bug #3427
      /*  for (int i = 0; i < repayPlanList.size(); i++) {
			
        	TRepayPlan repayplan = repayPlanList.get(i);
        	
        	String interes = repayplan.getInterestReal();//真实已还款利息
			interes = (interes==null||"".equals(interes))?"0":interes;
			
			String Principal = repayplan.getPrincipalReal();//真实已经还款本金
			Principal = (Principal==null||"".equals(Principal))?"0":Principal;

			money += Float.parseFloat(interes)+Float.parseFloat(Principal);
			
			totallixi += Float.parseFloat(repayplan.getInterest());
		}*/
		HashMap<String, Double> map = tRepayPlanService.allRepayment(tRepayPlan);
		
		//map = map == null ? tRepayPlanService.allRepayment(tRepayPlan) : map;
		Double interest = map.get("interest") == null ? 0.0 : map.get("interest");// 利息
		Double interestReal = map.get("interest_real") == null ? 0.0 : map.get("interest_real");// 真实利息
		Double principal = map.get("principal") == null ? 0.0 : map.get("principal");// 本金
		Double principalReal = map.get("principal_real") == null ? 0.0 : map.get("principal_real");// 真实本金
		money += interestReal+principalReal;
		jhMoney += interest+principal;
		
       // float waitMoney =  Float.parseFloat(contract.getLoanAmount())+totallixi-money;//待还金额
        Double waitMoney =  jhMoney-money;//待还金额
        BigDecimal   b   =   new   BigDecimal(waitMoney); 
        waitMoney   =   b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();  //保留两位
        
        BigDecimal   b1   =   new   BigDecimal(money); //已还
        money   =   b1.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();  //保留两位
          
		String result = "";
        if(contractAmount.equals("0")){
        	result = "合同编号："+contract.getContractNumber()+"！总计已还金额："+money+"元，待还金额："+waitMoney+"元。";
        }else{
        	result = "合同编号："+contract.getContractNumber()+"，金额："+contractAmount+"元已经归还！总计已还金额："+money+"元，待还金额："+waitMoney+"元。";
        }
		return result;
	}
	
	
	/**
	 * 获取要修改的还款信息
	 * 
	 * @param contractId
	 *            对应合同ID
	 */
	@RequiresPermissions("receivables:receivables:view")
	@RequestMapping(value = "getReceRecord")
	public String getReceRecord(HttpServletRequest request, HttpServletResponse response, Model model,
			@RequestParam(required = true) String repayRecId) {
		RepayRecord repayRecord = repayRecordService.get(repayRecId);

		model.addAttribute("repayRecord", repayRecord);// 要修改的内容返回到页面
		return "modules/receivables/receivablesModify";
	}

	/**
	 * 修改付款金额
	 * 
	 * @return
	 */
	@RequiresPermissions("receivables:receivables:view")
	@RequestMapping(value = "upDateReceMoney")
	@ResponseBody
	public Map<String, Object> upDateReceMoney(RepayRecord repayRecord, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put(SvalBase.JsonKey.KEY_STATUS, true);

		if (repayRecord.getMoney() < 0) {// 修改金额不能为0
			result.put(SvalBase.JsonKey.KEY_MSG, "还款金额不能小于0！");
			return result;
		}

		// 先查对应的实际还款记录
		RepayRecord oldRepayRecord = repayRecordService.get(repayRecord.getId());

		// 获取需要还款的数据
		TRepayPlan tRepayPlan = new TRepayPlan();
		tRepayPlan.setLoanContractId(repayRecord.getLoanContractId());
		HashMap<String, Double> map = tRepayPlanService.allRepayment(tRepayPlan);
		if (map == null) {
			result.put(SvalBase.JsonKey.KEY_MSG, "该合同没有相应的还款计划！");
			return result;
		}
		Double interest = map.get("interest") == null ? 0.0 : map.get("interest");// 利息
		Double interestReal = map.get("interest_real") == null ? 0.0 : map.get("interest_real");// 真实利息
		Double principal = map.get("principal") == null ? 0.0 : map.get("principal");// 本金
		Double principalReal = map.get("principal_real") == null ? 0.0 : map.get("principal_real");// 真实本金

		// 所需还款总额
		Double needRepay = interest + principal - interestReal - principalReal;
		if (repayRecord.getMoney() > (needRepay + oldRepayRecord.getMoney())) {
			result.put(SvalBase.JsonKey.KEY_MSG, "还款金额不能超过所需还款总额！");
			return result;
		}

		repayRecordService.upDate(oldRepayRecord, repayRecord);

		model.addAttribute("contractId", repayRecord.getLoanContractId());

		result.put(SvalBase.JsonKey.KEY_MSG, "修改成功！");
		return result;
	}

	/**
	 * 处罚处理：收违约金、咨询费等
	 * 
	 * @return
	 */
	@RequiresPermissions("receivables:receivables:view")
	@RequestMapping(value = "punish")
	@ResponseBody
	public Map<String, Object> punish(Model model, HttpServletRequest request, HttpServletResponse response,
			@RequestParam(required = true) String loanContractId, @RequestParam(required = true) Date payRealDate,
			@RequestParam(required = true) Double consultancyAmount, @RequestParam(required = true) Double punishAmount,
			@RequestParam(required = true) Double interestPenalties,@RequestParam(required = true) Double guaranteeFee,
			@RequestParam(required = true) Double reviewFee) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put(SvalBase.JsonKey.KEY_STATUS, true);

		RealIncome realIncome = new RealIncome();
		realIncome.setLoanContractId(loanContractId);
		realIncome.setPayRealDate(payRealDate);
		realIncome.setConsultancyAmount(consultancyAmount == null ? 0 : consultancyAmount);
		realIncome.setPunishAmount(punishAmount == null ? 0 : punishAmount);
		realIncome.setInterestPenalties(interestPenalties == null ? 0 : interestPenalties);
		
		realIncome.setGuaranteeFee(guaranteeFee == null ? 0 : guaranteeFee);
		realIncome.setReviewFee(reviewFee == null ? 0 : reviewFee );

		if (payRealDate == null || "".equals(payRealDate)) {
			result.put(SvalBase.JsonKey.KEY_PARAMS, "paramNull");
			result.put(SvalBase.JsonKey.KEY_MSG, "收取的时间不能为空！");
		} else if (realIncome.getConsultancyAmount() < 0 || realIncome.getPunishAmount() < 0
				|| realIncome.getInterestPenalties() < 0 || realIncome.getGuaranteeFee()<0 || realIncome.getReviewFee()<0) {
			result.put(SvalBase.JsonKey.KEY_PARAMS, "paramNull");
			result.put(SvalBase.JsonKey.KEY_MSG, "输入金额不能小于0！");
		} 
//		else if (realIncome.getConsultancyAmount() == 0 && realIncome.getPunishAmount() == 0
//				&& realIncome.getInterestPenalties() == 0) {
//			result.put(SvalBase.JsonKey.KEY_PARAMS, "paramNull");
//			result.put(SvalBase.JsonKey.KEY_MSG, "输入金额不能小于0或都为0！");
//		}
		else {
			realIncomeService.save(realIncome);

			System.out.println(UserUtils.getUser().getCompany().getPrimaryPerson());
			if(UserUtils.getUser().getCompany().getPrimaryPerson()!=null&&"1".equals(UserUtils.getUser().getCompany().getPrimaryPerson())){
				TLoanContract contract  = tLoanContractService.get(loanContractId);
				contract.setStatus(Cons.LoanContractStatus.DB_TO_LOAN);//担保专用  待放款状态（担保公司不放款，等银行或者第三方贷款公司放款）
				tLoanContractService.updateStatus(contract);
			}
			result.put(SvalBase.JsonKey.KEY_MSG, "收取成功！");
		}
		return result;
	}

	/**
	 * 详细信息 1、客户信息 2、账户信息 3、客户档案 4、贷款合同信息 5、项目调查报告 6、合同附件 7、还款计划 8、还款记录 9、逾期记录
	 * 10、违约金、咨询费实收款记录 11、放款记录 12、退费信息 13、提前还款信息 14、借款借据+放款审批表
	 */
	@RequiresPermissions("receivables:receivables:view")
	@RequestMapping(value = "detail")
	public String detail(HttpServletRequest request, HttpServletResponse response, Model model,
			@RequestParam(required = true) String contractId) {
		// 1 显示还款计划列表 t_repay_plan
		TRepayPlan tRepayPlan = new TRepayPlan();
		tRepayPlan.setLoanContractId(contractId);
		List<TRepayPlan> repayPlanList = tRepayPlanService.findListCondition(tRepayPlan);
		// 2-1 还款记录 t_repay_real
		Page<RepayRecord> pageRepayRecord = new Page<RepayRecord>();// 用于设置排序方式
		pageRepayRecord.setOrderBy("repay_date asc");
		RepayRecord repayRecord = new RepayRecord();
		repayRecord.setLoanContractId(contractId);
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
		realIncome.setLoanContractId(contractId);
		List<RealIncome> realIncomeList = realIncomeService.findList(realIncome);

		// 4 合同信息 t_loan_contract
		TLoanContract loanContract = tLoanContractService.get(contractId);
		// 5 合同对应的产品类型
		TProduct product = tProductService.get(loanContract.getProductId());

		model.addAttribute("repayPlanList", repayPlanList); // 1 还款计划列表
		model.addAttribute("repRecordList", repRecordList); // 2-1 还款记录
		model.addAttribute("overdueList", overdueList); // 2-2 逾期记录
		model.addAttribute("realIncomeList", realIncomeList); // 3 违约记录
		model.addAttribute("loanContract", loanContract);// 4 合同信息
		model.addAttribute("product", product);// 5 合同对应的产品类型

		return "modules/receivables/receivablesDetail";
	}
	
	
	
	/**
	 * 详细信息 1、客户信息 2、账户信息 3、客户档案 4、贷款合同信息 5、项目调查报告 6、合同附件 7、还款计划 8、还款记录 9、逾期记录
	 * 10、违约金、咨询费实收款记录 11、放款记录 12、退费信息 13、提前还款信息 14、借款借据+放款审批表
	 */
	@RequiresPermissions("receivables:receivables:view")
	@RequestMapping(value = "pingzheng")
	public String pingzheng(HttpServletRequest request, HttpServletResponse response, Model model,@RequestParam(required = true) String contractId) {
		
		TRepayPlan tRepayPlan = new TRepayPlan();
		tRepayPlan.setLoanContractId(contractId);
		List<TRepayPlan> repayPlanList = tRepayPlanService.findListCondition(tRepayPlan);
		Integer repayreal = 0;
		for (int i = 0; i < repayPlanList.size(); i++) {
			TRepayPlan repayplan = repayPlanList.get(i);
			if(repayplan.getPrincipalReal()!=null&&!"".equals(repayplan.getPrincipalReal())){
				repayreal += Integer.parseInt(repayplan.getPrincipalReal());
			}
		}
		TLoanContract loanContract = tLoanContractService.get(contractId);
	
		model.addAttribute("repayreal", repayreal); // 已还本金额
		model.addAttribute("loanContract", loanContract);//合同信息
		model.addAttribute("nowDate", new Date());//当前时候
		model.addAttribute("total", Float.parseFloat(loanContract.getLoanAmount())-repayreal);//当前时候
		return "modules/receivables/pingzheng";
	}

	
	
	

	/**
	 * 业务冲正
	 */
	// @RequiresPermissions("receivables:receivables:view")
	// @RequestMapping(value = "correcting")
	// public String correcting(HttpServletRequest request, HttpServletResponse
	// response, Model model)
	// {
	// return "modules/receivables/ReceivablesList";
	// }
	
	
}

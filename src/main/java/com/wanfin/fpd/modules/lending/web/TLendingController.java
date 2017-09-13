package com.wanfin.fpd.modules.lending.web;

import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
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
import com.wanfin.fpd.common.config.SvalBase;
import com.wanfin.fpd.common.config.SvalBase.JsonKey;
import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.service.ServiceException;
import com.wanfin.fpd.common.utils.SmsSendUtil;
import com.wanfin.fpd.common.utils.StringUtils;
import com.wanfin.fpd.common.web.BaseController;
import com.wanfin.fpd.modules.catipal.entity.TCapital;
import com.wanfin.fpd.modules.catipal.service.TCapitalService;
import com.wanfin.fpd.modules.company.entity.TCompany;
import com.wanfin.fpd.modules.company.service.TCompanyService;
import com.wanfin.fpd.modules.contract.entity.TLoanContract;
import com.wanfin.fpd.modules.contract.service.TLoanContractService;
import com.wanfin.fpd.modules.contract.vo.CsPersonVo;
import com.wanfin.fpd.modules.employee.entity.TEmployee;
import com.wanfin.fpd.modules.employee.service.TEmployeeService;
import com.wanfin.fpd.modules.form.entity.tpl.DfFormTpl;
import com.wanfin.fpd.modules.form.service.tpl.DfFormTplService;
import com.wanfin.fpd.modules.lending.entity.TLending;
import com.wanfin.fpd.modules.lending.service.TLendingService;
import com.wanfin.fpd.modules.postlending.entity.advance.Advance;
import com.wanfin.fpd.modules.postlending.service.advance.AdvanceService;
import com.wanfin.fpd.modules.product.entity.TProduct;
import com.wanfin.fpd.modules.product.service.TProductService;
import com.wanfin.fpd.modules.receivables.entity.RealIncome;
import com.wanfin.fpd.modules.receivables.entity.RepayRecord;
import com.wanfin.fpd.modules.receivables.service.RealIncomeService;
import com.wanfin.fpd.modules.receivables.service.RepayRecordService;
import com.wanfin.fpd.modules.refund.entity.Reimburse;
import com.wanfin.fpd.modules.refund.service.ReimburseService;
import com.wanfin.fpd.modules.repayplan.entity.TRepayPlan;
import com.wanfin.fpd.modules.repayplan.service.TRepayPlanService;
import com.wanfin.fpd.modules.sys.entity.User;
import com.wanfin.fpd.modules.sys.utils.DictUtils;
import com.wanfin.fpd.modules.sys.utils.UserUtils;

/**
 * 放款记录Controller
 * @author chenh
 * @version 2016-03-25
 */
@SuppressWarnings("deprecation")
@ApiIgnore
@Controller
@RequestMapping(value = "${adminPath}/lending/tLending")
public class TLendingController extends BaseController {

	@Autowired
	private TLendingService tLendingService;
	
	@Autowired
	private TLoanContractService tLoanContractService;
	
	@Autowired
	private TProductService tProductService;
	
	@Autowired
	private TCompanyService tCompanyService;
	
	@Autowired
	private TEmployeeService tEmployeeService;
	
	@Autowired
	private TCapitalService capitalservice;
	
	@Autowired
	private DfFormTplService dfFormTplService;
	
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
	private AdvanceService advanceService;
	
	
	@ModelAttribute
	public TLending get(@RequestParam(required=false) String id) {
		TLending entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = tLendingService.get(id);
		}
		if (entity == null){
			entity = new TLending();
		}
		return entity;
	}
	
	/**
	 **放款列表
	 * */
	@RequiresPermissions("lending:tLending:view")
	@RequestMapping(value = {"list", ""})
	public String list(TLending tLending, HttpServletRequest request, HttpServletResponse response, Model model) {
		TLoanContract contract = null;
		if(tLending.getContract() == null){
			contract = new TLoanContract();
		}else{
			contract = tLending.getContract();
		}
		tLending.setContract(contract);
		Page<TLoanContract> page = tLoanContractService.findPage(new Page<TLoanContract>(request, response), contract);
		
		model.addAttribute("page", page);
		model.addAttribute("tLending", tLending);
		return "modules/lending/tLendingList";
	}
	
	@RequestMapping(value = "lendDetail")
	public String lendDetail(String tLoanContractId, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		TLoanContract tLoanContract=tLoanContractService.get(tLoanContractId);
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
		return "modules/lending/lendDetail";
	}
	
	
	
	@RequiresPermissions("lending:tLending:view")
	@RequestMapping(value = "form")
	public String form(TLending tLending, Model model) {
		model.addAttribute("tLending", tLending);
		return "modules/lending/tLendingForm";
	}

	@RequiresPermissions("lending:tLending:edit")
	@RequestMapping(value = "save")
	public String save(TLending tLending, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, tLending)){
			return form(tLending, model);
		}
		tLendingService.save(tLending);
		addMessage(redirectAttributes, "保存放款记录成功");
		return "redirect:"+Global.getAdminPath()+"/lending/tLending/list?repage";
	}
	
	@RequiresPermissions("lending:tLending:edit")
	@RequestMapping(value = "delete")
	public String delete(TLending tLending, RedirectAttributes redirectAttributes) {
		tLendingService.delete(tLending);
		addMessage(redirectAttributes, "删除放款记录成功");
		return "redirect:"+Global.getAdminPath()+"/lending/tLending/?repage";
	}

	/**
	 **放款申请
	 * */
	@ResponseBody
	@RequiresPermissions("lending:tLending:edit")
	@RequestMapping(value = "ajaxSave")
	public Map<String, Object> ajaxSave(String id, Model model, RedirectAttributes redirectAttributes) {
		return tLoanContractService.requireTlending(new TLoanContract(id));
	}
	
	/**
	 **放款待审列表
	 * */
	@RequiresPermissions("lending:tLending:view")
	@RequestMapping(value = {"listIng", ""})
	public String listIng(TLending tLending, HttpServletRequest request, HttpServletResponse response, Model model) {
		TLoanContract contract = null;
		if(tLending.getContract() == null){
			contract = new TLoanContract();
		}else{
			contract = tLending.getContract();
		}
		contract.setStatus(Cons.LoanContractStatus.TO_LOAN+","+Cons.LoanContractStatus.DB_TO_LOAN);
		tLending.setContract(contract);
		Page<TLoanContract> page = tLoanContractService.findPage(new Page<TLoanContract>(request, response), contract);
		
		model.addAttribute("page", page);
		model.addAttribute("tLending", tLending);
		return "modules/lending/tLendingListIng";
	}
	
	/**
	 **已放款列表
	 * */
	@RequiresPermissions("lending:tLending:view")
	@RequestMapping(value = {"listEd", ""})
	public String listEd(TLending tLending, HttpServletRequest request, HttpServletResponse response, Model model) {
		TLoanContract contract = null;
		if(tLending.getContract() == null){
			contract = new TLoanContract();
		}else{
			contract = tLending.getContract();
		}
		contract.setStatus(Cons.LoanContractStatus.UN_CLEARED);
		tLending.setContract(contract);
		Page<TLoanContract> page = tLoanContractService.findPage(new Page<TLoanContract>(request, response), contract);
		
		model.addAttribute("page", page);
		model.addAttribute("tLending", tLending);
		return "modules/lending/tLendingListEd";
	}
	
	@RequiresPermissions("lending:tLending:view")
	@RequestMapping(value = "toLend")
	public String toLend(HttpServletRequest request, HttpServletResponse response, Model model, TLending tLending) {
		/**
		 * 跳转放款页面
		 * */
		//User currentUser = UserUtils.getUser();//当前登录人
		Map<String, Object> result = new HashMap<String, Object>();
		TProduct tProduct = null;
		TCompany tCompany = null;
		TEmployee tEmployee = null;
		TLoanContract tLoanContract = null;
		if((tLending != null) && (tLending.getContract() != null)){
			if(StringUtils.isNotEmpty(tLending.getContract().getId())){
				tLoanContract = tLoanContractService.get(tLending.getContract().getId());
			}else{
				tLoanContract = new TLoanContract();
			}

			if(StringUtils.isNotEmpty(tLoanContract.getProductId())){
				tProduct = tProductService.get(tLoanContract.getProductId());
			}else{
				tProduct = new TProduct();
			}
			
			if(StringUtils.isNotEmpty(tLoanContract.getCustomerId())){
				if((tLoanContract.getCustomerType()).equals(Cons.CustomerType.CUST_COMPANY)){
					tCompany = tCompanyService.get(tLoanContract.getCustomerId());
				}
				
				if((tLoanContract.getCustomerType()).equals(Cons.CustomerType.CUST_EMPLOYEE)){
					tEmployee = tEmployeeService.get(tLoanContract.getCustomerId());
				}
			}else{
				tProduct = new TProduct();
			}
		}else{
			tProduct = new TProduct();
			tLoanContract = new TLoanContract();
		}
		
		//Bug #4067
		//Double totalAmount = tLendingService.countAmount(tLoanContract);
		BigDecimal totalAmount = tLendingService.countAmount(tLoanContract);
		result.put(SvalBase.JsonKey.KEY_TOTAL_AMOUNT, totalAmount);
		//可以一步步放款，还有多少款项没有放
		//result.put(SvalBase.JsonKey.KEY_SY_AMOUNT, (Double.parseDouble(tLoanContract.getLoanAmount()) -  totalAmount));
		result.put(SvalBase.JsonKey.KEY_SY_AMOUNT, (new BigDecimal(tLoanContract.getLoanAmount()).subtract(totalAmount)).setScale(2, BigDecimal.ROUND_HALF_UP));
		if((tLoanContract.getCustomerType()).equals(Cons.CustomerType.CUST_COMPANY)){
			model.addAttribute("tCompany", tCompany);
			tLoanContract.setGatheringBank(tCompany.getGatheringBank());
			tLoanContract.setGatheringName(tCompany.getGatheringName());
			tLoanContract.setGatheringNumber(tCompany.getGatheringNumber());
		}else if((tLoanContract.getCustomerType()).equals(Cons.CustomerType.CUST_EMPLOYEE)){
			model.addAttribute("tEmployee", tEmployee);
			tLoanContract.setGatheringBank(tEmployee.getGatheringBank());
			tLoanContract.setGatheringName(tEmployee.getGatheringName());
			tLoanContract.setGatheringNumber(tEmployee.getGatheringNumber());
		}
		
		tLending.setContract(tLoanContract);
		FModel fm = FModel.M_BUSINESS_APPLICATION_YWSQ;
		DfFormTpl dfFormTpl = dfFormTplService.getByBizCode(fm.getKey());
		model.addAttribute("dfFormTpl", dfFormTpl);
		model.addAttribute("tProduct", tProduct);
		model.addAttribute("tLending", tLending);
		model.addAttribute("tLend", result);
		model.addAttribute("action", "/lending/tLending/loan");
		return "/modules/lending/tLending";
	}
	
	@RequiresPermissions("lending:tLending:view")
	@RequestMapping(value = "toLoan")
	public String toLoan(HttpServletRequest request, HttpServletResponse response, Model model, TLending tLending) {
		/**
		 * 跳转放款页面
		 * */
		//User currentUser = UserUtils.getUser();//当前登录人
		Map<String, Object> result = new HashMap<String, Object>();
		TProduct tProduct = null;
		TCompany tCompany = null;
		TEmployee tEmployee = null;
		TLoanContract tLoanContract = null;
		if((tLending != null) && (tLending.getContract() != null)){
			if(StringUtils.isNotEmpty(tLending.getContract().getId())){
				tLoanContract = tLoanContractService.get(tLending.getContract().getId());
			}else{
				tLoanContract = new TLoanContract();
			}

			if(StringUtils.isNotEmpty(tLoanContract.getProductId())){
				tProduct = tProductService.get(tLoanContract.getProductId());
			}else{
				tProduct = new TProduct();
			}
			
			if(StringUtils.isNotEmpty(tLoanContract.getCustomerId())){
				if((tLoanContract.getCustomerType()).equals(Cons.CustomerType.CUST_COMPANY)){
					tCompany = tCompanyService.get(tLoanContract.getCustomerId());
				}
				
				if((tLoanContract.getCustomerType()).equals(Cons.CustomerType.CUST_EMPLOYEE)){
					tEmployee = tEmployeeService.get(tLoanContract.getCustomerId());
				}
			}else{
				tProduct = new TProduct();
			}
		}else{
			tProduct = new TProduct();
			tLoanContract = new TLoanContract();
		}
		
		//Bug #4067
		//Double totalAmount = tLendingService.countAmount(tLoanContract);
		BigDecimal totalAmount = tLendingService.countAmount(tLoanContract);
		result.put(SvalBase.JsonKey.KEY_TOTAL_AMOUNT, totalAmount);
		//可以一步步放款，还有多少款项没有放
		//result.put(SvalBase.JsonKey.KEY_SY_AMOUNT, (Double.parseDouble(tLoanContract.getLoanAmount()) -  totalAmount));
		result.put(SvalBase.JsonKey.KEY_SY_AMOUNT, (new BigDecimal(tLoanContract.getLoanAmount()).subtract(totalAmount)).setScale(2, BigDecimal.ROUND_HALF_UP));
		if((tLoanContract.getCustomerType()).equals(Cons.CustomerType.CUST_COMPANY)){
			model.addAttribute("tCompany", tCompany);
			tLoanContract.setGatheringBank(tCompany.getGatheringBank());
			tLoanContract.setGatheringName(tCompany.getGatheringName());
			tLoanContract.setGatheringNumber(tCompany.getGatheringNumber());
		}else if((tLoanContract.getCustomerType()).equals(Cons.CustomerType.CUST_EMPLOYEE)){
			model.addAttribute("tEmployee", tEmployee);
			tLoanContract.setGatheringBank(tEmployee.getGatheringBank());
			tLoanContract.setGatheringName(tEmployee.getGatheringName());
			tLoanContract.setGatheringNumber(tEmployee.getGatheringNumber());
		}
		
		tLending.setContract(tLoanContract);
		FModel fm = FModel.M_BUSINESS_APPLICATION_YWSQ;
		DfFormTpl dfFormTpl = dfFormTplService.getByBizCode(fm.getKey());
		tLoanContract.setLoanTypeItem(tLoanContract.getLoanTypeList());
		model.addAttribute("tLoanContract", tLoanContract);
		model.addAttribute("dfFormTpl", dfFormTpl);
		model.addAttribute("tProduct", tProduct);
		model.addAttribute("tLending", tLending);
		model.addAttribute("tLend", result);
		return "/modules/lending/tLendingLoan";
	}
	
	@RequiresPermissions("lending:tLending:edit")
	@RequestMapping(value = "loan")
	public String loan(TRepayPlan tRepayPlan, TLending tLending, Model model,TLoanContract contract, RedirectAttributes redirectAttributes, String counts) {
		if (!beanValidator(model, tLending)){
			return form(tLending, model);
		}
		
		if(StringUtils.isBlank(tLending.getAmount())){
			model.addAttribute("message", "放款金额为空");
			return form(tLending, model);
		}
		try{
			double dAmount = Double.parseDouble(tLending.getAmount());
			if(dAmount<0){
				model.addAttribute("message", "放款金额不能为负数");
				return form(tLending, model);
			}
		}catch(Exception e){
			model.addAttribute("message", "放款金额错误");
			return form(tLending, model);
		}
		
		if(contract == null || StringUtils.isAnyBlank(contract.getId(), contract.getType())){
			contract = tLoanContractService.get(tLending.getContract());
		}
		//1担保   2小贷           小贷才需要判断资本信息 #4668 W绕过可放贷资金
		if(contract.getType().equals(Cons.LoanOrderType.TYPE_B) && UserUtils.getUser().getCompany().getPrimaryPerson()!=null && "2".equals(UserUtils.getUser().getCompany().getPrimaryPerson())){
			
			TCapital capital = capitalservice.getByOrganId(UserUtils.getUser().getCompany().getId());//资本信息
			if(capital!=null){
				BigDecimal b1 = new BigDecimal(capital.getLoanamount()) ; //贷款注入金额
				BigDecimal b2 = new BigDecimal(tLending.getAmount()) ;//本次合同贷出资金
	        	if(b1.compareTo(b2)==1){
	        		if(tLending.getContract() == null){
	    				addMessage(redirectAttributes, "放款失败!");
	    				return "redirect:"+Global.getAdminPath()+"/lending/tLending/listIng/?repage";
	    			}else{
							try {
								Map<String, Object> result = tLoanContractService.finishedTlending(tLending, tRepayPlan, counts,capital);
								addMessage(redirectAttributes, result.get(JsonKey.KEY_MSG).toString());
							} catch (ServiceException e) {
								e.printStackTrace();
								addMessage(redirectAttributes, e.getMessage());
							}
						
	    				return "redirect:"+Global.getAdminPath()+"/lending/tLending/listIng/?repage";
	    			}
	        	}else{
	        		addMessage(redirectAttributes, "可放贷资金不足，不可放款!");
					return "redirect:"+Global.getAdminPath()+"/lending/tLending/listIng/?repage";
	        	}
	        }else{
	        	addMessage(redirectAttributes, "未初始化可放贷资金，不可放款!");
				return "redirect:"+Global.getAdminPath()+"/lending/tLending/listIng/?repage";
	        }
			
		}else{
			try {
				Map<String, Object> result = tLoanContractService.finishedTlending(tLending, tRepayPlan, counts,new TCapital());
				addMessage(redirectAttributes, result.get(JsonKey.KEY_MSG).toString());
			} catch (ServiceException e) {
				e.printStackTrace();
				addMessage(redirectAttributes, e.getMessage());
			}
		    return "redirect:"+Global.getAdminPath()+"/lending/tLending/listIng/?repage";
		}
	}
	

	/**
	 **台账记录
	 * */
	@RequestMapping(value = {"ledgerList", ""})
	public String ledgerList(TLending tLending, HttpServletRequest request, HttpServletResponse response, Model model) {
			TLoanContract contract = null;
			if(tLending.getContract() == null){
				contract = new TLoanContract();
			}else{
				contract = tLending.getContract(); 
			}
			String isNull = "0";
			if(contract.getSearchtime()!=null&&!contract.getSearchtime().equals("")){
				contract.setSearchtime(contract.getSearchtime()+"-%");
				isNull = "1";
			}
			tLending.setContract(contract);
			contract.setStatus("6,7,8");
			contract.setType(Cons.LoanOrderType.TYPE_B);//#4443
			Page<TLoanContract> page = tLoanContractService.findPage(new Page<TLoanContract>(request, response), contract);
			model.addAttribute("page", page);
			if(isNull.equals("1")){
				contract.setSearchtime(contract.getSearchtime().substring(0, 7));//不加后面的"-%",用于桌面查询框显示
				tLending.setContract(contract);
			}
			model.addAttribute("tLending", tLending);
		return "modules/lending/ledgerList";
	}
	 
	@RequestMapping(value = {"excelPlan", ""})
	public void excelPlan(TLending tLending, HttpServletRequest request, HttpServletResponse response, Model model) {
		
		//当前登录用户
		 User currentUser = UserUtils.getUser();//当前登录人
		
		 TLoanContract contract_new = new TLoanContract();
		 contract_new.setStatus("6,7,8");
		 
		 
		 Calendar ca = Calendar.getInstance();			  
	     int month=ca.get(Calendar.MONTH)+1;//获取月份
	     int years =ca.get(Calendar.YEAR);
		 //台账时间-年月格式
		 String taiZhangMonth  = request.getParameter("taiZhangYueFen");//如果没选月份，则默认当月
		 
		 if(!taiZhangMonth.equals("")){
			 contract_new.setSearchtime(taiZhangMonth+"-%");
			 int selectMonth = Integer.parseInt(taiZhangMonth.substring(5, 7));
			 month = selectMonth;
		 }
		 
		
		
		// 第一步，创建一个webbook，对应一个Excel文件  
		HSSFWorkbook wb = new HSSFWorkbook(); 
		HSSFSheet sheet = wb.createSheet(""+month+"月台账");  
		
		//第一行表头开始
		 HSSFFont headfont = wb.createFont();   
		 headfont.setFontName("黑体");   
		 headfont.setFontHeightInPoints((short) 22);// 字体大小   
		 headfont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 加粗   
				
		 HSSFCellStyle headstyle = wb.createCellStyle();   
		 headstyle.setFont(headfont);   
		 headstyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 左右居中   
		 headstyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 上下居中   
		 headstyle.setLocked(true);   
		 headstyle.setWrapText(false);// 自动换行  
		
		 
		 CellRangeAddress range = new CellRangeAddress(0, 0, 0, 15);   
	      sheet.addMergedRegion(range);   
		 HSSFRow row0 = sheet.createRow(0);   
		 
	      // 设置行高   
	     row0.setHeight((short) 600);   
	     HSSFCell cell0 = row0.createCell(0);   
	     cell0.setCellValue(new HSSFRichTextString(currentUser.getCompany().getName()+years+"年"+month+"月台账"));  
	     cell0.setCellStyle(headstyle);   
	     
		
		HSSFRow row = sheet.createRow((int) 1); 
		HSSFCellStyle style = wb.createCellStyle(); 
		style.setAlignment(HSSFCellStyle.ALIGN_LEFT); // 创建一个居中格式 
		
		HSSFFont headfont1 = wb.createFont();   
		headfont1.setFontName("黑体"); 
		headfont1.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 加粗   
		style.setFont(headfont1);
		
		HSSFCell cell = row.createCell((short) 0);	
		cell.setCellValue("序号");
		cell.setCellStyle(style);  
		
		cell = row.createCell((short) 1);		
		cell.setCellValue("合同编号");
		cell.setCellStyle(style);  
		
		cell = row.createCell((short) 2); 
		cell.setCellValue("客户名称");
		cell.setCellStyle(style);  
		
		cell = row.createCell((short) 3);		
		cell.setCellValue("客户类型");
		cell.setCellStyle(style);  
		
		cell = row.createCell((short) 4);		
		cell.setCellValue("贷款日期");
		cell.setCellStyle(style); 
		
		cell = row.createCell((short) 5);  
		cell.setCellValue("贷款金额");
		cell.setCellStyle(style); 
		
		cell = row.createCell((short) 6); 
		cell.setCellValue("贷款期限");
		cell.setCellStyle(style); 
		
		cell = row.createCell((short) 7); 
		cell.setCellValue("贷款利率");		
		cell.setCellStyle(style); 
		
		cell = row.createCell((short) 8);  
		cell.setCellValue("贷款到期日");
		cell.setCellStyle(style); 
		
		cell = row.createCell((short) 9);
		cell.setCellValue("法定代表人");
		cell.setCellStyle(style); 
		
		cell = row.createCell((short) 10);
		cell.setCellValue("联系电话");
		cell.setCellStyle(style); 
		
		cell = row.createCell((short) 11); 
		cell.setCellValue("已还金额");
		cell.setCellStyle(style); 
		
		cell = row.createCell((short) 12); 	
		cell.setCellValue("已还利息");
		cell.setCellStyle(style); 
		
		cell = row.createCell((short) 13); 	
		cell.setCellValue("最近还款日");
		cell.setCellStyle(style);
		
		cell = row.createCell((short) 14); 	
		cell.setCellValue("合同状态");
		cell.setCellStyle(style);
		
		cell = row.createCell((short) 15); 	
		cell.setCellValue("备注");
		cell.setCellStyle(style);
		
		
		sheet.setColumnWidth((short ) 0 , 10 * 256);
		sheet.setColumnWidth((short ) 1 , 25 * 256);
		sheet.setColumnWidth((short ) 2 , 25 * 256);
		sheet.setColumnWidth((short ) 3 , 18 * 256);
		sheet.setColumnWidth((short ) 4 , 25 * 256);
		sheet.setColumnWidth((short ) 5 , 15 * 256);
		sheet.setColumnWidth((short ) 6 , 15 * 256);
		sheet.setColumnWidth((short ) 7 , 20 * 256);
		sheet.setColumnWidth((short ) 8 , 15 * 256);
		sheet.setColumnWidth((short ) 9 , 25 * 256);
		sheet.setColumnWidth((short ) 10 , 15 * 256);
		sheet.setColumnWidth((short ) 11 , 25 * 256);
		sheet.setColumnWidth((short ) 12 , 15 * 256);
		sheet.setColumnWidth((short ) 13 , 15 * 256);
		sheet.setColumnWidth((short ) 14 , 15 * 256);
		sheet.setColumnWidth((short ) 15 , 15 * 256);
		
	     //List<TLoanContract>  loancontractlist = tLoanContractService.findList(contract_new);//old #3144
	     List<TLoanContract>  loancontractlist = tLoanContractService.findForExcelPlan(contract_new);//new #3144
   
   	     for(int j = 0;j<loancontractlist.size();j++){	    
				
		        
   	    	TLoanContract contract = loancontractlist.get(j);
   	    	 
				row = sheet.createRow((int) 2+ j); 
				row.createCell((short) 0).setCellValue(j+1);  
	        	row.createCell((short) 1).setCellValue(contract.getContractNumber()+"");  
	        	row.createCell((short) 2).setCellValue(contract.getCustomerName()+"");
	        	
				row.createCell((short) 4).setCellValue(new SimpleDateFormat("yyyy-MM-dd").format(contract.getApplyDate())+""); 
				row.createCell((short) 5).setCellValue(contract.getLoanAmount()+"");
				row.createCell((short) 6).setCellValue(contract.getLoanPeriod()+DictUtils.getDictLabel(contract.getPeriodType(), "period_type", "")+"");
				row.createCell((short) 7).setCellValue(contract.getLoanRate()+"("+contract.getLoanRateType()+")");
				row.createCell((short) 8).setCellValue(new SimpleDateFormat("yyyy-MM-dd").format(contract.getPayPrincipalDate())+"");
					
				if(contract.getCustomerType().equals("1")){//1:企业   2：个人
					row.createCell((short) 3).setCellValue("企业");
					row.createCell((short) 9).setCellValue(contract.getLegalPerson()+"");	
					row.createCell((short) 10).setCellValue(contract.getSuretyTelephone()+"");
				}else{
					row.createCell((short) 3).setCellValue("个人");
					row.createCell((short) 9).setCellValue(contract.getCustomerName()+"");	
					row.createCell((short) 10).setCellValue(contract.getLinkphone()+"");
				}
				
				row.createCell((short) 11).setCellValue(contract.getBackmoney()+"");
				row.createCell((short) 12).setCellValue(contract.getBacklixi()+"");
				//row.createCell((short) 13).setCellValue(contract.getBacktime()+"");//old #3144
				if(contract.getBacktime() != null){
					row.createCell((short) 13).setCellValue(new SimpleDateFormat("yyyy-MM-dd").format(contract.getBacktime())+"");//new #3144
				}else{
					row.createCell((short) 13).setCellValue("");
				}
				row.createCell((short) 14).setCellValue(DictUtils.getDictLabel(contract.getStatus(), "loan_contract_status", "")+"");
				row.createCell((short) 15).setCellValue(contract.getRemarks()+"");
				
				
           }
       try{ 
       	String fileName = currentUser.getCompany().getName()+years+"年"+month+"月台账.xls";
       	fileName=new String(fileName.getBytes("gb2312"), "iso-8859-1");
           response.addHeader("Content-Disposition", "attachment;filename="+ fileName);
       	OutputStream fout =new BufferedOutputStream(response.getOutputStream());  
       	response.setContentType("application/vnd.ms-excel;charset=gb2312");  
	        wb.write(fout);  
	        fout.flush();
	        fout.close();  
       }catch (Exception e){  
       	e.printStackTrace();  
       } 
	}
}
/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.act.web;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.codehaus.jettison.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mangofactory.swagger.annotations.ApiIgnore;
import com.wanfin.fpd.common.config.Cons;
import com.wanfin.fpd.common.config.Global;
import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.utils.CacheUtils;
import com.wanfin.fpd.common.utils.DateUtils;
import com.wanfin.fpd.common.utils.SmsSendUtil;
import com.wanfin.fpd.common.utils.SpringContextHolder;
import com.wanfin.fpd.common.utils.StringUtils;
import com.wanfin.fpd.common.web.BaseController;
import com.wanfin.fpd.modules.act.entity.Act;
import com.wanfin.fpd.modules.act.service.ActTaskService;
import com.wanfin.fpd.modules.company.entity.TCompany;
import com.wanfin.fpd.modules.company.service.TCompanyService;
import com.wanfin.fpd.modules.contract.entity.TLoanContract;
import com.wanfin.fpd.modules.contract.service.TLoanContractService;
import com.wanfin.fpd.modules.credit.entity.CreditApply;
import com.wanfin.fpd.modules.credit.service.CreditApplyService;
import com.wanfin.fpd.modules.employee.entity.TEmployee;
import com.wanfin.fpd.modules.employee.service.TEmployeeService;
import com.wanfin.fpd.modules.fonds.entity.TFiling;
import com.wanfin.fpd.modules.fonds.service.TFilingService;
import com.wanfin.fpd.modules.product.entity.TProduct;
import com.wanfin.fpd.modules.product.service.TProductService;
import com.wanfin.fpd.modules.repayplan.entity.TRepayPlan;
import com.wanfin.fpd.modules.repayplan.service.TRepayPlanService;
import com.wanfin.fpd.modules.sys.security.SystemAuthorizingRealm;
import com.wanfin.fpd.modules.sys.utils.LogUtils;
import com.wanfin.fpd.modules.sys.utils.UserUtils;

/**
 * 首页待办任务相关Controller
 * @author zzm
 * @version 2016-04-11
 */
@ApiIgnore
@Controller
@RequestMapping(value = "${adminPath}/index/todo")
public class TodoController extends BaseController {

	@Autowired
	private ActTaskService actTaskService;
	
	@Autowired
	private TLoanContractService tLoanContractService;
	
	@Autowired
	private TRepayPlanService tRepayPlanService;
	
	
	@Autowired
	private TCompanyService tCompanyService;
	
	@Autowired
	private TEmployeeService tEmployeeService;
	
	@Autowired
	private CreditApplyService creditApplyService;
	
	@Autowired
	private TFilingService tFilingService;
	
	@Autowired
	private TProductService tProductService;
	/**
	 * 待办业务
	 * @return
	 */
	@RequestMapping(value = {"todoList", ""})
	public String todoList(Act act,HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		
		//未选产品点击任务处理
		String isReUrl = (String) request.getSession().getAttribute("isReUrl");
		String reurl = (String) request.getSession().getAttribute("reurl");
		//System.out.println("isReUrl="+isReUrl);
		//System.out.println("reurl="+reurl);
		if(StringUtils.isNotBlank(isReUrl) && StringUtils.isNotBlank(reurl) && (isReUrl).equals("1")){
			request.getSession().removeAttribute("isReUrl");
			request.getSession().removeAttribute("reurl");
			return "redirect:"+ adminPath + "/" + reurl;
		}
		
		List<Act> actList = todoAct(act,response,model);
		List<TLoanContract> signList = todoSign(response,model);//业务待签订
		List<CreditApply> creditSignList = todoCreditSign(response,model);//授信待签订
		model.addAttribute("actList", actList);
		model.addAttribute("signList", signList);
		model.addAttribute("creditSignList", creditSignList);
		return "modules/desktop/todoList";
	}
	
	/**
	 * 获取已办任务
	 * @param page
	 * @param procDefKey 流程定义标识
	 * @return
	 */
	@RequestMapping(value = "historic")
	public String historicList(Act act, HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		Page<Act> page = new Page<Act>(request, response);
		page = actTaskService.historicList(page, act);
		model.addAttribute("page", page);
		if (UserUtils.getPrincipal().isMobileLogin()){
			return renderString(response, page);
		}
		return "modules/desktop/actTaskHistoricList";
	}
	
	
	/**
	 * 财务待办
	 * @return
	 */
	@RequestMapping(value = "financeTodoList")
	public String financeTodoList(HttpServletResponse response, Model model) throws Exception {
		List<TRepayPlan> planList = todoRepay(response,model);
		List<TLoanContract> loanList = todoLoan(response,model,Cons.LoanContractStatus.TO_LOAN);
		List<TLoanContract> db_to_chargeList = todoLoan(response,model,Cons.LoanContractStatus.DB_TO_CHARGE);
		
		model.addAttribute("planList", planList);
		model.addAttribute("loanList", loanList);
		model.addAttribute("db_to_chargeList", db_to_chargeList);
		return "modules/desktop/financeTodoList";
	}
	
	
	@RequestMapping(value ="productdeskindex")
	public String productdeskindex(TProduct tProduct, HttpServletRequest request, HttpServletResponse response, Model model)  throws Exception{
		tProduct.setStatus(Cons.ProductStatus.PUBLISHED);
//		tProduct.setReleasesWay("2");
		Page<TProduct> page = tProductService.findPage(new Page<TProduct>(request, response), tProduct); 
		List<Act> actList = todoAct(new Act(),response,model);
		
		String contract_ids = "";//得出当前需要自己审批的流程的业务id
		for (int i = 0; i < actList.size(); i++) {
			Act act = actList.get(i);
			if (act.getProcInsId() != null){
				act.setProcIns(actTaskService.getProcIns(act.getProcInsId()));
			}
            if(act.getBusinessTable()!=null && act.getBusinessTable().equals("t_loan_contract")){
            	contract_ids += act.getBusinessId()+",";
            }
		}
		if(contract_ids!=null && !contract_ids.equals("")){
			contract_ids = contract_ids.substring(0, contract_ids.length()-1);
		}
		List<TProduct> new_productlist = new ArrayList<TProduct>();//新建一个list，添加循环之后的产品
		List<TProduct> productlist = page.getList();
		TLoanContract tLoanContract = new TLoanContract();
		
		for (int i = 0; i < productlist.size(); i++) {
			TProduct product = productlist.get(i);
			tLoanContract.setProductId(product.getId());
			
			int exampleAmount = 0;
			if(contract_ids!=null && !contract_ids.equals("")){
				String[] cIds = contract_ids.split(",");
				for (int j = 0; j < cIds.length; j++) {
					TLoanContract contract = tLoanContractService.get(cIds[j]);
					if(contract.getProductId().equals(product.getId())){
						exampleAmount += 1;
					}
				}
			}
			 product.setExanple_amount(exampleAmount);//待审批业务数 
			
			 tLoanContract.setStatus(Cons.LoanContractStatus.TO_SIGN);
			 product.setSign_amount(tLoanContractService.findList(tLoanContract).size());//待签订业务数
			
			 tLoanContract.setStatus(Cons.LoanContractStatus.TO_LOAN);
			 product.setLoan_amount(tLoanContractService.findList(tLoanContract).size());//待签订业务数

			 new_productlist.add(product);
		   }
		
		model.addAttribute("productlist", new_productlist);
		model.addAttribute("tProduct", tProduct);
		return "modules/desktop/productdeskIndex";
	}
	
	@ResponseBody
	@RequestMapping(value = "toChageProduct")
	public String toChageProduct(String syscode, HttpServletRequest request, HttpServletResponse response){
		if (StringUtils.isNotBlank(syscode)){
			UserUtils.putCache(UserUtils.CACHE_SYSCODE, syscode);
			// 清除用户菜单缓存
			UserUtils.removeCache(UserUtils.CACHE_MENU_LIST);
			// 清除日志相关缓存
			CacheUtils.remove(LogUtils.CACHE_MENU_NAME_PATH_MAP);
			// 清除权限数据缓存
			SpringContextHolder.getBean(SystemAuthorizingRealm.class).clearCachedAuthorizationInfo(UserUtils.getPrincipal());
			
			TProduct tp = tProductService.get(syscode);
			UserUtils.putCache(UserUtils.CACHE_SYSNAME, tp.getName());
		}else{
			syscode = (String) UserUtils.getCache(UserUtils.CACHE_SYSCODE);
		}
		return "";
	}
	
	@ResponseBody
	@RequestMapping(value = "removeProductCache")
	public String removeProductCache(String syscode, HttpServletRequest request, HttpServletResponse response){
		
			UserUtils.removeCache(UserUtils.CACHE_SYSCODE);
			UserUtils.removeCache(UserUtils.CACHE_SYSNAME);
			UserUtils.removeCache(UserUtils.CACHE_MENU_LIST_PRODUCT);
			UserUtils.removeCache(UserUtils.CACHE_MODELMENU_ALL_LIST);
			// 清除用户菜单缓存
			UserUtils.removeCache(UserUtils.CACHE_MENU_LIST);
			// 清除日志相关缓存
			CacheUtils.remove(LogUtils.CACHE_MENU_NAME_PATH_MAP);
			// 清除权限数据缓存
			SpringContextHolder.getBean(SystemAuthorizingRealm.class).clearCachedAuthorizationInfo(UserUtils.getPrincipal());
		return "";
	}
	
	
	
	/**
	 * 桌面提醒
	 * @return
	 */
	@RequestMapping(value = "warnList")
	public String warnList(Act act,HttpServletResponse response, Model model) throws Exception {
		List<TLoanContract> new_warnloanlist = new ArrayList<TLoanContract>();
		
		TLoanContract tLoanContract = new TLoanContract();
		tLoanContract.setStatus(Cons.LoanContractStatus.CLEARED);//已结结清了的
		tLoanContract.setProductId((String)UserUtils.getCache(UserUtils.CACHE_SYSCODE));
		List<TLoanContract> loanlist  = tLoanContractService.findList(tLoanContract);
		for (int i = 0; i < loanlist.size(); i++) {
			TLoanContract contract = loanlist.get(i);
			TFiling tFiling = new TFiling();
			tFiling.setLoancontractId(contract.getId());
			 List<TFiling> filinglist =  tFilingService.findList(tFiling);
			 if(filinglist.size()==0){
				 new_warnloanlist.add(contract);
			 }
		}
		model.addAttribute("new_warnloanlist", new_warnloanlist);
		return "modules/desktop/warnList";
	}
	
	
	/**
	 * 跳转还款提醒列表
	 * @return
	 */
	@RequestMapping(value = "TodoRepayList")
	public String TodoRepayList(TRepayPlan tRepayPlan,HttpServletRequest request,HttpServletResponse response, Model model) throws Exception {
		//未还+未结清
		tRepayPlan.setStatus(Cons.RepayStatus.NO_PAID+","+Cons.RepayStatus.IN_PAYMENT);
		//时间范围
		Date beginAccountDate = DateUtils.parseDate(DateUtils.getDate());
		tRepayPlan.setBeginAccountDate(beginAccountDate);
		tRepayPlan.setEndAccountDate(DateUtils.addDays(beginAccountDate, 5));
		tRepayPlan.setProductid((String)UserUtils.getCache(UserUtils.CACHE_SYSCODE));
		Page<TRepayPlan> page =  tRepayPlanService.findPage(new Page<TRepayPlan>(request, response), tRepayPlan);
		model.addAttribute("page", page);
		model.addAttribute("tRepayPlan",tRepayPlan);
		return "modules/contract/todoRepayList";
	}
	
	
	@RequestMapping(value = "repaywarn")
	public String repaywarn(String id, Model model, RedirectAttributes redirectAttributes) {
		TLoanContract tLoanContract = tLoanContractService.get(id);
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd"); 
		TRepayPlan tRepayPlan = new TRepayPlan();
		//未还+未结清
		tRepayPlan.setStatus(Cons.RepayStatus.NO_PAID+","+Cons.RepayStatus.IN_PAYMENT);
		//时间范围
		Date beginAccountDate = DateUtils.parseDate(DateUtils.getDate());
		tRepayPlan.setBeginAccountDate(beginAccountDate);
		tRepayPlan.setEndAccountDate(DateUtils.addDays(beginAccountDate, 7));
		tRepayPlan.setProductid((String)UserUtils.getCache(UserUtils.CACHE_SYSCODE));
		tRepayPlan.setLoanContractId(id);
		List<TRepayPlan> repaylist  =  tRepayPlanService.findList(tRepayPlan);
		float money = 0;
		String accountDate = "";
		if(repaylist.size()>0){
			
			TRepayPlan repayplan = repaylist.get(0);
			String interes = repayplan.getInterestReal();
			interes = (interes==null||"".equals(interes))?"0":interes;
			
			String Principal = repayplan.getPrincipalReal();
			Principal = (Principal==null||"".equals(Principal))?"0":Principal;
			
			model.addAttribute("repayplan",repayplan);
			money = Float.parseFloat(repayplan.getInterest())+ Float.parseFloat(repayplan.getPrincipal())-Float.parseFloat(interes)-Float.parseFloat(Principal);
			accountDate = repayplan.getAccountDate();
		}
		if("1".equals(tLoanContract.getCustomerType())){
			TCompany company = tCompanyService.get(tLoanContract.getCustomerId());
			model.addAttribute("company",company);
		}else if("2".equals(tLoanContract.getCustomerType())){
			TEmployee employee = tEmployeeService.get(tLoanContract.getCustomerId());
			model.addAttribute("employee",employee);
		}
		model.addAttribute("tLoanContract",tLoanContract);
		//您合同编号#number#的借款本期应还人民币#money#元，还款到期为#date#。请提前准备，保证按时还款！[万众金融业务系统]
		String repaywarn = "";
		System.out.println(sdf.format(new Date())+"-------------------------------");
		 if(sdf.format(new Date()).equals(accountDate)){//如果是当天到期
			 repaywarn = Cons.Msg.MSG_ALERT_AHEAD;
			 if(repaywarn.contains("#number#") && repaywarn.contains("#money#")){
				 repaywarn = repaywarn.replace("#number#",tLoanContract.getContractNumber());
				 repaywarn = repaywarn.replace("#money#",money+"");
			 }
		 }else{
			 repaywarn = Cons.Msg.MSG_ALERT_NOWDATE;
			 if(repaywarn.contains("#number#") && repaywarn.contains("#money#") && repaywarn.contains("#date#")){
				 repaywarn = repaywarn.replace("#number#",tLoanContract.getContractNumber());
				 repaywarn = repaywarn.replace("#date#",accountDate);
				 repaywarn = repaywarn.replace("#money#",money+"");
			 }
		 }
		model.addAttribute("repaywarn",repaywarn);
		
		return "modules/contract/repaywarn";
	}
	
	
	@RequestMapping(value = "send",method=RequestMethod.POST)
	@ResponseBody
	public String send(String phone,String content,String type, HttpServletResponse response, Model model) throws IOException, JSONException {
		return SmsSendUtil.sendSmsBatch(content, phone,type);
	}
	
	
	
	/**
	 * 获取待办列表
	 * @return
	 */
	@RequestMapping(value = "act",method=RequestMethod.POST)
	@ResponseBody
	public List<Act> todoAct(Act act, HttpServletResponse response, Model model) {
		List<Act> list = actTaskService.todoList(act);
		return list;
	}
	
	/**
	 * 首页待办：待还款
	 * @return
	 */
	@RequestMapping(value = "repay")
	@ResponseBody
	public List<TRepayPlan> todoRepay(HttpServletResponse response, Model model) throws Exception {
		int num = 7;//在一周(7天)内还款的进行提醒
		List<TRepayPlan> list = tRepayPlanService.findToDoRepayPlans(num);
		return list;
	}
	
	/**
	 * 首页待办：待放款
	 * @return
	 */
	@RequestMapping(value = "loan")
	@ResponseBody
	public List<TLoanContract> todoLoan(HttpServletResponse response, Model model,String status) throws Exception {
		TLoanContract tLoanContract = new TLoanContract();
		tLoanContract.setStatus(status);
		return tLoanContractService.findListByStatus(tLoanContract);
	}
	/**
	 * 首页待办：待签订
	 * @return
	 */
	@RequestMapping(value = "sign")
	@ResponseBody
	public List<TLoanContract> todoSign(HttpServletResponse response, Model model) throws Exception {
		TLoanContract tLoanContract = new TLoanContract();
		//贷款合同待签订和展期合同待签订 ： TO_SIGN,ET_TO_SIGN
		tLoanContract.setStatus(Cons.LoanContractStatus.TO_SIGN+","+Cons.LoanContractStatus.ET_TO_SIGN);
		
		String productId =(String)UserUtils.getCache(UserUtils.CACHE_SYSCODE);
		if(productId!=null && !"".equals(productId)){
			tLoanContract.setProductId(productId);
		}
		
		return tLoanContractService.findList(tLoanContract);
	}
	/**
	 * 首页待办：授信申请待签订
	 * @return
	 */
	@RequestMapping(value = "creditSign")
	@ResponseBody
	public List<CreditApply> todoCreditSign(HttpServletResponse response, Model model) throws Exception {
		CreditApply creditApply = new CreditApply();
		//授信申请待签订 ： TO_SIGN
		creditApply.setStatus(Cons.CreditApplyStatus.TO_SIGN);
		return creditApplyService.findList(creditApply);
	}
	
	/**
	 * 首页待办：逾期业务
	 * @return
	 */
	@RequestMapping(value = "overdue")
	public String overduetodoList(HttpServletResponse response, Model model) throws Exception {
		//查询逾期还款计划
		TRepayPlan tRepayPlan = new TRepayPlan();
		tRepayPlan.setIsYuQi(Global.YES);
		List<TRepayPlan> list = tRepayPlanService.findList(tRepayPlan);
		if (UserUtils.getPrincipal().isMobileLogin()){
			return renderString(response, list);
		}
		model.addAttribute("list", list);
		return "modules/act/overdueTodoList";
	}

	public static void main(String[] args) {
		
//		 SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd"); 
//	     String da = sdf.format(new Date());
//	     System.out.println(da);
//	    System.out.println(da.equals("2016-07-29"));
		
	}
	
	
}

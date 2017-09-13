/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.wish.contract.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.wanfin.fpd.common.config.Cons;
import com.wanfin.fpd.common.config.Global;
import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.utils.DateUtils;
import com.wanfin.fpd.common.utils.StringUtils;
import com.wanfin.fpd.modules.company.entity.TCompany;
import com.wanfin.fpd.modules.company.service.TCompanyService;
import com.wanfin.fpd.modules.contract.entity.TLoanContract;
import com.wanfin.fpd.modules.contract.service.TLoanContractService;
import com.wanfin.fpd.modules.employee.entity.TEmployee;
import com.wanfin.fpd.modules.employee.service.TEmployeeService;
import com.wanfin.fpd.modules.files.entity.TContractFiles;
import com.wanfin.fpd.modules.files.service.TContractFilesService;
import com.wanfin.fpd.modules.repayplan.entity.TRepayPlan;
import com.wanfin.fpd.modules.repayplan.service.TRepayPlanService;
import com.wanfin.fpd.modules.sys.entity.User;
import com.wanfin.fpd.modules.sys.utils.UserUtils;
import com.wanfin.fpd.modules.wish.contract.service.WishContractService;
import com.wanfin.fpd.modules.wish.contract.vo.ContractDetailVo;
import com.wanfin.fpd.modules.wish.contract.vo.DeclareVo;
import com.wanfin.fpd.modules.wish.contract.vo.NumberToCN;
import com.wanfin.fpd.modules.wish.excel.service.WishExcelService;
import com.wanfin.fpd.modules.wish.merchant.entity.Merchant;
import com.wanfin.fpd.modules.wish.merchant.service.MerchantService;
import com.wanfin.fpd.modules.wish.order.entity.WishOrder;
import com.wanfin.fpd.modules.wish.order.service.WishOrderService;
import com.wanfin.fpd.modules.wish.utils.DoubleTwo;
import com.wanfin.fpd.modules.wish.utils.HtmlToPdf;
import com.wanfin.fpd.modules.wish.utils.PDFToImage;
import com.wanfin.fpd.modules.wish.utils.Rservice;
import com.wanfin.fpd.modules.wish.utils.WordToHtml;

/**
 * 业务办理Controller
 * 
 * @author lx
 * @version 2016-03-18
 */
@Controller
@RequestMapping(value = "/wish/contract/wishContract")
public class WebWishContractController {// extends BaseController

	@Autowired
	private TLoanContractService tLoanContractService;

	@Autowired
	private WishExcelService wishExcelService;
	
	@Autowired
	private TEmployeeService tEmployeeService;
	
	@Autowired
	private TRepayPlanService tRepayPlanService;
	
	@Autowired
	private WishContractService wishContractService;
	
	@Autowired
	private TCompanyService tCompanyService;
	@Autowired
	private MerchantService merchantService;
	@Autowired
	private WishOrderService wishOrderService;
	@Autowired
	private TContractFilesService tContractFilesService;
	
	
	private String wishUserId = "";
	private String wishUserName = "";

	/*private String loanStartDate = "";
	private String loanEndDate = "";
	private String mediumDate1 = "";
	private String mediumDate2 = "";
	
	private String loanAmount = "";*/
	private String tLoanContractId = "";

	/**
	 * 秒杀货款---放款待确认列表
	 * 
	 * @param tLoanContract
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"contractDetail" })
	public String contractDetail(String contractId,HttpServletRequest request, HttpServletResponse response,Model model) {
		ContractDetailVo vo=new ContractDetailVo();
		
		TLoanContract tLoanContract=tLoanContractService.get(contractId);
		List<TRepayPlan> planList=tRepayPlanService.getPlanByContractId(contractId);
		
		BigDecimal real=new BigDecimal("0");
		BigDecimal amount=StringUtils.isBlank(tLoanContract.getLoanAmount())?new BigDecimal("0"):new BigDecimal(tLoanContract.getLoanAmount());
		BigDecimal overAmount=new BigDecimal("0");
		BigDecimal overFee=new BigDecimal("0");
		BigDecimal rate=new BigDecimal(tLoanContract.getLoanRate()).divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP).divide(new BigDecimal(360), 2, BigDecimal.ROUND_HALF_UP);
		
		for(TRepayPlan plan:planList){
			if(plan.getNum()==1){
				BigDecimal principal=StringUtils.isBlank(plan.getPrincipal())?new BigDecimal("0"):new BigDecimal(plan.getPrincipal());
				BigDecimal principalReal=StringUtils.isBlank(plan.getPrincipalReal())?new BigDecimal("0"):new BigDecimal(plan.getPrincipalReal());
				
				vo.setFirstMoney(plan.getPrincipal());
				vo.setFirstAccountDate(plan.getAccountDate());
				vo.setRealFirstMoney(plan.getPrincipalReal());
				
				real=real.add(principalReal);
			}
			if(plan.getNum()==2){
				BigDecimal principal=StringUtils.isBlank(plan.getPrincipal())?new BigDecimal("0"):new BigDecimal(plan.getPrincipal());
				BigDecimal principalReal=StringUtils.isBlank(plan.getPrincipalReal())?new BigDecimal("0"):new BigDecimal(plan.getPrincipalReal());
				BigDecimal interest=StringUtils.isBlank(plan.getInterest())?new BigDecimal("0"):new BigDecimal(plan.getInterest());
				BigDecimal interestReal=StringUtils.isBlank(plan.getInterestReal())?new BigDecimal("0"):new BigDecimal(plan.getInterestReal());
				vo.setSecondMoney(principal.add(interest).toString());
				vo.setSecondAccountDate(plan.getAccountDate());
				vo.setRealSecondMoney(interestReal.add(principalReal).toString());
				real=real.add(principalReal).add(interestReal);
				if(plan.getIsYuQi().equals("1")){
					overAmount=principal.add(interest).subtract(principalReal).subtract(interestReal);
					double overdueDays =Double.parseDouble(plan.getYuQi());//逾期天数
                   //	（实际回款日期-实际第二回款日期）*逾期金额*年利率/360
                	overFee=new BigDecimal(overdueDays).multiply(overAmount).multiply(rate);
					
				}
			}
			
		}
		
		vo.setNumber(tLoanContract.getContractNumber());
		vo.setAppayDate(DateUtils.formatDate(tLoanContract.getApplyDate(), "yyyy-MM-dd"));
		vo.setAmount(tLoanContract.getLoanAmount());
		vo.setLoanDate(DateUtils.formatDate(tLoanContract.getLoanDate(), "yyyy-MM-dd"));
		vo.setDays(tLoanContract.getLoanPeriod());
		vo.setCharge(tLoanContract.getWishCharge());
		vo.setBankNum(tLoanContract.getGatheringNumber());
		vo.setDiffAmount(amount.subtract(real).toString());
		vo.setOverAmount(overAmount.toString());
		vo.setOverFee(overFee.toString());
		vo.setStatus(tLoanContract.getStatus());
		
		model.addAttribute("vo",vo);
		return "modules/wishNew/order/contractDetail";
	}

	
	
	/**
	 *初始化申请页面，调用R脚本处理数据
	 * 
	 * @param tLoanContract
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = { "submitApply" })
	public String submitApply(String amount,String wishCharge,String days,HttpServletRequest request,HttpServletResponse response, Model model) {
		wishUserId = (String) UserUtils.getSession().getAttribute("wishUserId");
		WishOrder wishOrder=new WishOrder();
		wishOrder.setUserId(wishUserId);
		wishOrder.setLoanOperation("0");
		Double sumAmount=wishOrderService.getSumAmount(wishOrder);//订单总额
		
	    BigDecimal numberOfMoney = new BigDecimal(amount);
	    String moneyCapital = NumberToCN.number2CNMontrayUnit(numberOfMoney);
	   
	    BigDecimal monthRate=new BigDecimal(Cons.wishMonthRate.WISH_MONTH_RATE);
	    
	    BigDecimal monthFee=new BigDecimal(amount).multiply(monthRate);
	    		
	    BigDecimal sumFee=new BigDecimal(wishCharge).add(monthFee);
	    BigDecimal realMoney=new BigDecimal(amount).subtract(sumFee);
		
		DeclareVo vo=new DeclareVo();
		vo.setMoney(amount);
		vo.setMoneyCapital(moneyCapital);
		vo.setMonthFee(monthFee.toString());
		vo.setMonthRate(monthRate.toString());
		vo.setOrderMoney(String.valueOf(sumAmount));
		vo.setRealMoney(realMoney.toString());
		vo.setSumFee(sumFee.toString());
		vo.setDays(days);
		vo.setWishCharge(wishCharge);
		model.addAttribute("vo", vo);
		return "modules/wishNew/merchant/login";// 跳转到开通申请页面
	}
	
	
	/**
	 * 秒杀货款业务保存-----由可收款订单页面跳转到申请页面
	 * 
	 * @param tLoanContract
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = { "saveWishContract" })
	public String saveWishContract(HttpServletRequest request,HttpServletResponse response, Model model) {
		String forward = "modules/wish/merchant/error";
		wishUserId = (String) UserUtils.getSession().getAttribute("wishUserId");
		//String  interest="0";
		String  quota="0";
		//String  quotaRate="0";
		if (StringUtils.isNotBlank(wishUserId)) {
			List<Record> query= Db.find("select * from result_records where user_id = ? and product_type='1' order by update_time desc",wishUserId);
			if(query.size()>0){
				org.json.JSONObject job;
				try {
					job = new org.json.JSONObject(query.get(0).toJson());
					//interest=job.get("interest")+"";
					quota=job.get("quota")+"";
					//quotaRate=job.get("quota_rate")+"";
				} catch (JSONException e) {
						e.printStackTrace();
				}
					
			}
			
			/*String nowDate=DateUtils.formatDate(new Date(), "yyyy-MM-dd");
			List<Record> rateQuery= Db.find("select rate from yl_exchange_rates where del_flag = '0' AND orig_currency = 'USD' AND exch_currency = 'CNY' AND valid_date =?",nowDate);
			String rate="";
			if(rateQuery.size()>0){
				org.json.JSONObject rateJob;
				try {
					rateJob = new org.json.JSONObject(rateQuery.get(0).toJson());
					rate=rateJob.get("rate")+"";
				} catch (JSONException e) {
						e.printStackTrace();
				}
			}*/
			
			//BigDecimal dollar=StringUtils.isNotBlank(sumAmount)?new BigDecimal(sumAmount):new BigDecimal(0);
			//BigDecimal bRate=StringUtils.isNotBlank(sumAmount)?new BigDecimal(sumAmount):new BigDecimal(0);
			//BigDecimal chianMoney=dollar.multiply(bRate);
			
			//BigDecimal interest=new BigDecimal(quota).multiply(new BigDecimal(Cons.wishMonthRate.WISH_MONTH_RATE).divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP));
			//BigDecimal quotaRate=new BigDecimal(quota).subtract(interest);
			
			if(new BigDecimal(quota).compareTo(BigDecimal.ZERO)==1){
				model.addAttribute("userId", wishUserId);
				model.addAttribute("quota", DoubleTwo.formatDown(Double.valueOf(quota)));//最大借款金额
			}else{
				model.addAttribute("userId", 0);
				model.addAttribute("quota", 0);//最大借款金额
			}
			model.addAttribute("interest", 0);//手续费
			//model.addAttribute("sumAmount",sumAmount);//可收订单总金额(美元)
			model.addAttribute("rate",0);//汇率
			//model.addAttribute("chianMoney",chianMoney);//转化后人民币
			
			forward = "modules/wishNew/order/applyAmount";// ------------借款申请页面
			
		} else {
			forward = "modules/wish/merchant/getServices";// 跳转到开通申请页面
		}
		return forward;

	}

	/**
	 * 确认申请收款-----调用脚本锁定订单信息
	 * 
	 * @param tLoanContract
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = { "sureWishContract" })
	public String sureWishContract(DeclareVo vo,HttpServletRequest request,HttpServletResponse response,Model model) {
		wishUserId = (String) UserUtils.getSession().getAttribute("wishUserId");
		wishUserName = (String) UserUtils.getSession().getAttribute("wishUserName");
		/*TEmployee employee=tEmployeeService.get(wishUserId);
		TCompany company=tCompanyService.get(wishUserId);*/
		Merchant merchant=merchantService.getByUserNum(wishUserId);
		
		if(StringUtils.isNotBlank(vo.getMoney()) && new BigDecimal(vo.getMoney()).compareTo(BigDecimal.ZERO)==1){
			if (StringUtils.isNotBlank(wishUserId) ) {
				TLoanContract tLoanContract = new TLoanContract();
				tLoanContract.setContractNumber("YLZF"+DateUtils.formatDate(new Date(), "yyyyMMddHHmmss"));
				tLoanContract.setApplyDate(new Date());
				tLoanContract.setCreateDate(new Date());
				tLoanContract.setUpdateDate(new Date());
				tLoanContract.setStatus(Cons.LoanContractStatus.TO_REVIEW);// 新增
				tLoanContract.setType("2");// 业务类型:秒杀货款
				tLoanContract.setLoanRateType("年");// 利率类型年
				tLoanContract.setPayType("5");// 还款方式---到期一次性还本付息
				tLoanContract.setLoanRate("12");// 贷款利率 12%
				tLoanContract.setLoanPeriod(vo.getDays());// 贷款期限 1
				tLoanContract.setPeriodType("3");// 还款周期 3----日
				tLoanContract.setType("2");// 业务类型 秒杀货款
				tLoanContract.setLoanDate(new Date());// 初始默认放款日期
				tLoanContract.setOrganId("wish");// 初始机构id
				tLoanContract.setGatheringName("0");// 初始银行账户信息
				tLoanContract.setGatheringNumber(StringUtils.isBlank(merchant.getAccountNo())?"1":merchant.getAccountNo());// 初始银行账户信息
				tLoanContract.setGatheringBank("0");// 初始银行账户信息
				/*if(employee!=null){
					tLoanContract.setGatheringName(StringUtils.isBlank(employee.getGatheringName())?"1":employee.getGatheringName() );// 初始银行账户信息
					tLoanContract.setGatheringNumber(StringUtils.isBlank(employee.getGatheringNumber())?"1":employee.getGatheringNumber());// 初始银行账户信息
					tLoanContract.setGatheringBank(StringUtils.isBlank(employee.getGatheringBank())?"1":employee.getGatheringBank());// 初始银行账户信息
				}else if(company!=null){
					tLoanContract.setGatheringName(StringUtils.isBlank(company.getGatheringName())?"1":company.getGatheringName() );// 初始银行账户信息
					tLoanContract.setGatheringNumber(StringUtils.isBlank(company.getGatheringNumber())?"1":company.getGatheringNumber());// 初始银行账户信息
					tLoanContract.setGatheringBank(StringUtils.isBlank(company.getGatheringBank())?"1":company.getGatheringBank());// 初始银行账户信息
				}*/
				tLoanContract.setProductId("1");
				tLoanContract.setLoanAmount((DoubleTwo.formatDown(Double.valueOf(vo.getMoney())))+"");// 贷款金额
				tLoanContract.setWishCharge((DoubleTwo.formatDoubleUp(Double.valueOf(vo.getWishCharge())))+"");// 手续费
				tLoanContract.setServerFee(vo.getMonthFee());//月息(服务费)
				tLoanContract.setCustomerType(Cons.CustomerType.CUST_EMPLOYEE);
				tLoanContract.setCustomerId(wishUserId);
				tLoanContract.setCustomerName(wishUserName);
				tLoanContract.setWishStatus("-1");
				//tLoanContract.setPayPrincipalDate(DateUtils.parseDate(expirationDate));//合同到期日--由页面上R语言计算结果得知
				tLoanContractService.saveWishContract(tLoanContract);
				tLoanContractId = tLoanContract.getId();
			
				List<String> jpgList=exportDeclarDoc(request,response,vo);
		        for(int i=0;i<jpgList.size();i++){
		        	TContractFiles tContractFiles=new TContractFiles();
		        	tContractFiles.setFilePath(jpgList.get(i));
		        	tContractFiles.setTaskId(tLoanContractId);
		        	tContractFiles.setType("wishApplay");
		        	tContractFiles.setCreateDate(new Date());
		        	tContractFiles.setCreateBy(new User());
		        	tContractFiles.setDelFlag("0");
		        	tContractFiles.setOrganId("wish");
		        	tContractFilesService.save(tContractFiles);
		        	
		        }
				//推送新增状态通知----脚本审核------推送脚本审核结果状态通知
				 new t3().start();
				 new t2().start();  
			     new t1().start();
				new Thread() {
					public void run(){
						wishContractService.singleInform(tLoanContractId);
				    }
				}.start();
		  }
		}
		return 	"modules/wishNew/merchant/applySuccess";

	}
	
	class t1 extends Thread {  
         @Override  
         public void run() {  
             System.out.println("thread1");  
             wishContractService.singleInform(tLoanContractId);
         }  
     };  

	
     class t2 extends Thread{  
         @Override  
         public void run() {  
             try {  
                 // 引用t1线程，等待t1线程执行完  
                 new t1().join();  
             } catch (InterruptedException e) {  
                 e.printStackTrace();  
             }  
             System.out.println("thread2");
             TLoanContract tc = tLoanContractService.get(tLoanContractId);
     		 System.out.println("userId=========================="+tc.getCustomerId());
     		 String loanAmount=tc.getLoanAmount();
     		 String  loanPeriod=tc.getLoanPeriod();
     		 String wishType="";
     		 //String oper="";
     		 if(("30").equals(loanPeriod)){
     			wishType="1";
     			//oper = Rservice.blockOrder(tc.getId(),loanAmount);// 这个R脚本----锁定订单
     		 }else if(("60").equals(loanPeriod)){
     			wishType="2";
     		 }else if(("90").equals(loanPeriod)){
     			wishType="3";
     		 }
     		 String oper = Rservice.blockOrder30_90(tc.getId(), loanAmount, wishType);// 这个R脚本----锁定订单
     		 System.out.println("oper=========================="+oper);
     		 String[] res = oper.split(",");
             if (res.length == 9) {
     		   if (res[8].equals("1")) {// 脚本审核通过
     		   Date date = new Date();// 两次回款计划创建时间统一 不然可能影响收款逻辑
     		   tc.setStatus(Cons.LoanContractStatus.TO_APPROVE);// 新增---至----审核中
     		   tc.setPayPrincipalDate(DateUtils.stringToDate(res[6],"yyyy-MM-dd"));
     		      if (new BigDecimal(res[4]).compareTo(BigDecimal.ZERO) >= 0) {// 保证数据完整性--始终存在两条回款计划
     							TRepayPlan rp1 = new TRepayPlan();
     							rp1.setNum(1);
     							rp1.setLoanContractId(tc.getId());
     							rp1.setPrincipal(DoubleTwo.formatDoubleUp(Double.valueOf(res[4]))+"");
     							rp1.setStartDate(res[2]);
     							rp1.setEndDate(res[3]);
     							rp1.setInterest("0");
     							rp1.setStatus("0");
     							rp1.setWishUserId(tc.getCustomerId());
     							rp1.setCreateDate(date);
     							rp1.setIsYuQi(Global.NO);
     							tRepayPlanService.save(rp1);
     						}
     						if (new BigDecimal(res[7]).compareTo(BigDecimal.ZERO) >= 0) {// 保证数据完整性--始终存在两条回款计划
     							TRepayPlan rp2 = new TRepayPlan();
     							rp2.setNum(2);
     							rp2.setLoanContractId(tc.getId());
     							rp2.setPrincipal(DoubleTwo.formatDoubleUp(Double.valueOf(res[7]))+"");
     							rp2.setStartDate(res[5]);
     							rp2.setEndDate(res[6]);
     							rp2.setInterest("0");
     							rp2.setStatus("0");
     							rp2.setWishUserId(tc.getCustomerId());
     							rp2.setCreateDate(date);
     							rp2.setIsYuQi(Global.NO);
     							tRepayPlanService.save(rp2);
     						}
     					} else if (res[8].equals("0")) {// 脚本审核不通过
     						tc.setStatus(Cons.LoanContractStatus.TO_SUSPENSION);// 新增---至----审核不通过
     						tc.setRemarks("风控脚本审核不通过！");
     					}
     					
     					//推送状态通知
     					tLoanContractId=tc.getId();
     			}else{
     					tc.setRemarks("非30天产品！");
     			}
                tLoanContractService.saveForWish(tc);
         }  
     };  
     
     
     class t3 extends Thread{  
         @Override  
         public void run() {  
             try {  
                 //引用t2线程，等待t2线程执行完  
                new t2().join();  
             } catch (InterruptedException e) {  
                 e.printStackTrace();  
             }  
             System.out.println("thread3");  
             wishContractService.singleInform(tLoanContractId);
         }  
     };  
	
	
	
	/**
	 *初始化申请页面，调用R脚本处理数据
	 * 
	 * @param tLoanContract
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	/*@ResponseBody
	@RequestMapping(value = { "getDate" })
	public Map<String,String> getDate(String amount, Model model) {
		Map<String,String> map=new HashMap<String, String>();
		wishUserId = (String) UserUtils.getSession().getAttribute("wishUserId");
		String quota="";
		String quotaRate="";
		String interest="";
		String loanMinDate="";
		String loanMaxDate="";
		String loanMediumDate1 ="";
		String loanMediumDate2 ="";
		String expirationDate="";
		String result=Rservice.getCharge(wishUserId,amount);//这个R脚本计算
		//String result="8,1000,990.0030328,9.9969672,2017-03-26,2017-06-19,2017-04-02,2017-06-08,2017-10-01";
	    String[] res=result.split(",");
		//用户id,贷款金额，实际到账，手续费，计算起始日，计算基数日，到账日
	    if(res.length>0 && res.length==9){
	    	quota=res[1];
	    	quotaRate=res[2];
			interest=res[3];
	    	loanMinDate=res[4];
	    	loanMaxDate=res[5];
	    	loanMediumDate1=res[6];//分割期1
	    	loanMediumDate2=res[7];//分割期2
	    	expirationDate=res[8];//到期日---合同到期日
	    }
	    
	    map.put("quota", quota);
	    map.put("quotaRate", quotaRate);
	    map.put("interest", interest);
	    map.put("loanMinDate", loanMinDate);
	    map.put("loanMaxDate", loanMaxDate);
	    map.put("loanMediumDate1", loanMediumDate1);
	    map.put("loanMediumDate2", loanMediumDate2);
	    map.put("expirationDate", expirationDate);
		return map;

	}*/
	/**
	 *初始化申请页面，调用R脚本处理数据
	 * 
	 * @param tLoanContract
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = { "applySuccess" })
	public String applySuccess(Model model) {
		return "modules/wish/merchant/appylSuccess";
	}
	
	@RequestMapping(value = {"contractList"})
	public String contractList(TLoanContract tLoanContract, HttpServletRequest request, HttpServletResponse response, Model model) {
		wishUserId = (String) UserUtils.getSession().getAttribute("wishUserId");
		tLoanContract.setCustomerId(wishUserId);
		int pageNo = tLoanContract.getPage().getPageNo(); // 当前页码
		int pageSize=tLoanContract.getPage().getPageSize();
		Page<TLoanContract> page = new Page<TLoanContract>(request, response);
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		page = tLoanContractService.wishContractlist(page, tLoanContract); 
		model.addAttribute("page", page);
		model.addAttribute("tLoanContract", tLoanContract);
		return "modules/wishNew/order/contractList";
	}
	
	
	//@RequestMapping(value = "/exportDeclarDoc", method = RequestMethod.GET)
    public List<String> exportDeclarDoc(HttpServletRequest request,HttpServletResponse response,DeclareVo vo){
    	wishUserId = (String) UserUtils.getSession().getAttribute("wishUserId");
    	List<String> jpgUrl=new ArrayList<String>();
    	Date date=new Date();
        try{
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("money", vo.getMoney());
        paramMap.put("moneyCapital", vo.getMoneyCapital());
        paramMap.put("monthFee", vo.getMonthFee());
        paramMap.put("monthRate", vo.getMonthRate());
        paramMap.put("orderMoney", vo.getOrderMoney());
        paramMap.put("realMoney", vo.getRealMoney());
        paramMap.put("sumFee", vo.getSumFee());
        paramMap.put("days", vo.getDays());
        paramMap.put("wishCharge", vo.getWishCharge());
        String path=request.getSession().getServletContext().getRealPath("/bat/model.doc");    
        FileInputStream is =  new FileInputStream(new File(path));
        POIFSFileSystem pfs = new POIFSFileSystem(is);  
        HWPFDocument hwpf = new HWPFDocument(pfs); 
       // hwpf.getr
        Range range = hwpf.getRange();    
        Set<String> keys = paramMap.keySet();
        for(String key: keys){
            Object value = paramMap.get(key);
            System.out.println("${"+key+"}"+"==="+paramMap.get(key));
            range.replaceText("${"+key+"}", value==null?"":String.valueOf(value));
        }
        String batPath=request.getSession().getServletContext().getRealPath("/bat/");
        String imgPath=request.getSession().getServletContext().getRealPath("/images/");
        String docPath=batPath+"/"+wishUserId+DateUtils.formatDate(date, "yyyyMMddHHmmss")+".doc";
        String htmlPath=batPath+"/"+wishUserId+DateUtils.formatDate(date, "yyyyMMddHHmmss")+".html";
        String pdfPath=batPath+"/"+wishUserId+DateUtils.formatDate(date, "yyyyMMddHHmmss")+".pdf";
        //System.out.println(request.getSession().getServletContext().getRealPath("/bat/")+"/"+docName);
        
        File newFile=new File(docPath);  
        if (newFile.exists())  
            newFile.delete();  
        newFile.createNewFile();
        FileOutputStream fopts = new FileOutputStream(newFile);
        hwpf.write(fopts);  
        // 输出字节流   
        fopts.close(); 
        
        WordToHtml.convert2Html(batPath,docPath,htmlPath);
        HtmlToPdf.convertHtmlToPdf(batPath, htmlPath, pdfPath);
       // jpgUrl=PDFToImage.pdftoIamge(1f,pdfPath, imgPath+"/"); 
        jpgUrl.add(pdfPath);
        File docFile = new File(docPath);
        File htmlFile = new File(htmlPath);
        File pdfFile = new File(pdfPath);
        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        if (docFile.exists() && docFile.isFile()) {
            if (docFile.delete()) {
                System.out.println("删除单个文件" + docPath + "成功！");
            } else {
                System.out.println("删除单个文件" + docPath + "失败！");
            }
        }
        if (htmlFile.exists() && htmlFile.isFile()) {
            if (htmlFile.delete()) {
                System.out.println("删除单个文件" + htmlPath + "成功！");
            } else {
                System.out.println("删除单个文件" + htmlPath + "失败！");
            }
        }
        /*if (pdfFile.exists() && pdfFile.isFile()) {
            if (pdfFile.delete()) {
                System.out.println("删除单个文件" + docPath + "成功！");
            } else {
                System.out.println("删除单个文件" + docPath + "失败！");
            }
        }*/
    } catch (Exception e) {
        e.printStackTrace();
    }
        return jpgUrl;
    }
    /**
	 *
	 * 获取借款时间差
	 * @param tLoanContract
	 * @param request
	 * @param response
	 * @param model
	 * @return
     * @throws ParseException 
	 */
	@ResponseBody
	@RequestMapping(value = { "getDiffTime" })
	public Map<String,String> getDiffTime() throws ParseException {
		Map<String,String> map=new HashMap<String, String>();
		wishUserId = (String) UserUtils.getSession().getAttribute("wishUserId");
		List<Record> query= Db.find("SELECT IFNULL(Max(tc.create_date),0) as maxDate from t_loan_contract tc where tc.customer_id=?",wishUserId);
		String maxDate="";
		if(query.size()>0){
			org.json.JSONObject job;
			try {
				job = new org.json.JSONObject(query.get(0).toJson());
				maxDate=job.get("maxDate")+"";
			} catch (JSONException e) {
					e.printStackTrace();
			}
				
		}
		//2017-08-28 15:10:17
		SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");//如2016-08-10 20:40  
		int minutes=10000;
		if(StringUtils.isNotBlank(maxDate) && !maxDate.equals("0")){
			String fromDate = simpleFormat.format(DateUtils.parseDate(maxDate,"yyyy-MM-dd hh:mm:ss"));  
			String toDate = simpleFormat.format(new Date());  
			long from = simpleFormat.parse(fromDate).getTime();  
			long to = simpleFormat.parse(toDate).getTime();  
			minutes= (int) ((to - from)/(1000 * 60)); 
		}
	
	    map.put("minutes", java.lang.Math.abs(minutes)+"");
		return map;

	}
	 /**
	 *
	 * 获取用户不同产品的额度
	 * @param tLoanContract
	 * @param request
	 * @param response
	 * @param model
	 * @return
    * @throws ParseException 
	 */
	@ResponseBody
	@RequestMapping(value = { "getDiffQuota" })
	public Map<String,String> getDiffQuota(String days) throws ParseException {
		Map<String,String> map=new HashMap<String, String>();
		wishUserId = (String) UserUtils.getSession().getAttribute("wishUserId");
		String  quota="0";
		String wishType="";
 		 if(("30").equals(days)){
 			wishType="1";
 		 }else if(("60").equals(days)){
 			wishType="2";
 		 }else if(("90").equals(days)){
 			wishType="3";
 		 }
		if (StringUtils.isNotBlank(wishUserId)) {
			List<Record> query= Db.find("select IFNULL(quota,0) AS quota from result_records where user_id = ? and product_type=? order by update_time desc",wishUserId,wishType);
			if(query.size()>0){
				org.json.JSONObject job;
				try {
					job = new org.json.JSONObject(query.get(0).toJson());
					quota=job.get("quota")+"";
				} catch (JSONException e) {
						e.printStackTrace();
				}
			}
		}
		map.put("userId", wishUserId+"");
	    map.put("quota", DoubleTwo.formatDown(Double.valueOf(quota))+"");
	    map.put("interest", 0+"");
	    map.put("rate", 0+"");
		return map;

	}
}
/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.wish.contract.web;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Maps;
import com.wanfin.fpd.common.config.Cons;
import com.wanfin.fpd.common.config.Global;
import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.utils.DateUtils;
import com.wanfin.fpd.common.utils.InterfaceUtil;
import com.wanfin.fpd.common.utils.StringUtils;
import com.wanfin.fpd.common.utils.excel.ExportExcel;
import com.wanfin.fpd.common.web.BaseController;
import com.wanfin.fpd.modules.api.wiss.entity.PayecoBackParams;
import com.wanfin.fpd.modules.api.wiss.service.InteractionService;
import com.wanfin.fpd.modules.contract.entity.TLoanContract;
import com.wanfin.fpd.modules.contract.service.TLoanContractService;
import com.wanfin.fpd.modules.lending.entity.TLending;
import com.wanfin.fpd.modules.lending.service.TLendingService;
import com.wanfin.fpd.modules.receivables.entity.RepayRecord;
import com.wanfin.fpd.modules.receivables.service.RepayRecordService;
import com.wanfin.fpd.modules.repayplan.entity.TRepayPlan;
import com.wanfin.fpd.modules.repayplan.service.TRepayPlanService;
import com.wanfin.fpd.modules.sys.entity.User;
import com.wanfin.fpd.modules.sys.utils.UserUtils;
import com.wanfin.fpd.modules.wish.beitone.service.ProductPushService;
import com.wanfin.fpd.modules.wish.contract.service.WishContractService;
import com.wanfin.fpd.modules.wish.contract.vo.RepayRecordVo;
import com.wanfin.fpd.modules.wish.contract.vo.TLendingVo;
import com.wanfin.fpd.modules.wish.excel.service.WishExcelService;
import com.wanfin.fpd.modules.wish.excel.vo.WishContractVo;
import com.wanfin.fpd.modules.wish.merchant.entity.Merchant;
import com.wanfin.fpd.modules.wish.merchant.service.MerchantService;
import com.wanfin.fpd.modules.wish.order.entity.ReturnedMoney;
import com.wanfin.fpd.modules.wish.order.entity.TReturnedMsg;
import com.wanfin.fpd.modules.wish.order.service.ReturnedMoneyService;
import com.wanfin.fpd.modules.wish.order.service.TReturnedMsgService;
import com.wanfin.fpd.modules.wish.utils.Rservice;

/**
 * 业务办理Controller
 * 
 * @author lx
 * @version 2016-03-18
 */
@Controller
@RequestMapping(value = "${adminPath}/wish/contract/wishContract")
public class WishContractController extends BaseController {//

	@Autowired
	private TLoanContractService tLoanContractService;

	@Autowired
	private WishExcelService wishExcelService;

	@Autowired
	private InteractionService interactionService;

	// 真实还款记录
	@Autowired
	private RepayRecordService repayRecordService;

	@Autowired
	private TRepayPlanService tRepayPlanService;

	@Autowired
	private TLendingService tLendingService;
	@Autowired
	private ReturnedMoneyService returnedMoneyService;

	@Autowired
	private WishContractService wishContractService;
	
	@Autowired
	private ProductPushService productPushService;
	
	@Autowired
	private TReturnedMsgService tReturnedMsgService;
	
	@Autowired
	private MerchantService merchantService;
	
	private String idstrs;

	private String tLoanContractId = "";
	
	@ModelAttribute
	public TLoanContract get(@RequestParam(required = false) String id) {
		TLoanContract entity = null;
		if (StringUtils.isNotBlank(id)) {
			String[] ids = id.split(",");
			entity = tLoanContractService.get(ids[0]);
		}
		if (entity == null) {
			entity = new TLoanContract();
		}
		return entity;
	}

	/**
	 * 秒杀货款---放款待确认列表
	 * 
	 * @param tLoanContract
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = { "test" })
	public void test(HttpServletRequest request, HttpServletResponse response,
			Model model) {
		System.out.println("111111111111111111111111");
		// return "modules/wish/contract/index";

	}

	@RequestMapping(value = "applySuccess")
	public String applySuccess() {
		return "modules/wish/merchant/applySuccess";
	}

	/**
	 * 秒杀货款---历史数据
	 * 
	 * @param tLoanContract
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("wish.contract:wishContract:view")
	@RequestMapping(value = { "wishContractlist" })
	public String wishContractlist(TLoanContract tLoanContract,
			HttpServletRequest request, HttpServletResponse response,
			Model model) {
		tLoanContract.setStatus(Cons.LoanContractStatus.CLEARED);
		tLoanContract.setType("2");// 业务类型:秒杀货款
		Page<TLoanContract> page = tLoanContractService.wishContractlist(
				new Page<TLoanContract>(request, response), tLoanContract);
		model.addAttribute("page", page);
		model.addAttribute("tLoanContract", tLoanContract);
		model.addAttribute("admin", UserUtils.getUser().isAdmin());
		return "modules/wish/contract/wishContractList";

	}
	
	
	
	/**
	 * 秒杀货款---历史数据
	 * 
	 * @param tLoanContract
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	/*@RequiresPermissions("wish.contract:wishContract:view")*/
	@RequestMapping(value = { "merchantList" })
	public String merchantList(Merchant merchant,
			HttpServletRequest request, HttpServletResponse response,
			Model model) {
	/*	tLoanContract.setStatus(Cons.LoanContractStatus.CLEARED);
		tLoanContract.setType("2");// 业务类型:秒杀货款
*/		Page<Merchant> page = merchantService.findPage(new Page<Merchant>(request, response), merchant);

		model.addAttribute("page", page);
		model.addAttribute("merchant", merchant);
		model.addAttribute("admin", UserUtils.getUser().isAdmin());
		return "modules/wish/contract/merchantList";

	}
	/**
	 * 秒杀货款---待回款报表
	 * 
	 * @param tLoanContract
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("wish.contract:wishContract:view")
	@RequestMapping(value = { "wishRepaylist" })
	public String wishRepaylist(TLoanContract tLoanContract,
			HttpServletRequest request, HttpServletResponse response,
			Model model) {
		tLoanContract.setStatus(Cons.LoanContractStatus.UN_CLEARED);
		tLoanContract.setType("2");// 业务类型:秒杀货款
		Page<TLoanContract> page = tLoanContractService.wishContractlist(
				new Page<TLoanContract>(request, response), tLoanContract);
		model.addAttribute("page", page);
		model.addAttribute("tLoanContract", tLoanContract);
		model.addAttribute("admin", UserUtils.getUser().isAdmin());
		return "modules/wish/contract/wishRepayList";

	}

	/**
	 * 秒杀货款---打款待确认列表
	 * 
	 * @param tLoanContract
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("wish.contract:wishContract:view")
	@RequestMapping(value = { "wishRetrunMoneylist" })
	public String wishRetrunMoneylist(ReturnedMoney returnedMoney,
			HttpServletRequest request, HttpServletResponse response,
			Model model) {
		returnedMoney.setIsDeal("1" + "," + "2");
		Page<ReturnedMoney> page = returnedMoneyService.findPage(
				new Page<ReturnedMoney>(request, response), returnedMoney);
		model.addAttribute("page", page);
		model.addAttribute("returnedMoney", returnedMoney);
		model.addAttribute("admin", UserUtils.getUser().isAdmin());
		return "modules/wish/contract/wishRetrunMoneylist";

	}

	/**
	 * 秒杀货款---待审核列表
	 * 
	 * @param tLoanContract
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("wish.contract:wishContract:view")
	@RequestMapping(value = { "wishAuidtList" })
	public String wishAuidtList(String status, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		// tLoanContract.setStatus(Cons.LoanContractStatus.TO_APPROVE);
		TLoanContract tLoanContract = new TLoanContract();
		tLoanContract.setStatus(status);
		// tLoanContract.setStatus(Cons.LoanContractStatus.TO_SUSPENSION+","+Cons.LoanContractStatus.TO_REVIEW+","+Cons.LoanContractStatus.TO_APPROVE);
		tLoanContract.setType("2");// 业务类型:秒杀货款
		Page<TLoanContract> page = tLoanContractService.wishContractlist(
				new Page<TLoanContract>(request, response), tLoanContract);
		model.addAttribute("page", page);
		model.addAttribute("tLoanContract", tLoanContract);
		model.addAttribute("admin", UserUtils.getUser().isAdmin());
		return "modules/wish/contract/wishAuidtList";

	}

	/**
	 * 秒杀货款---待放款列表
	 * 
	 * @param tLoanContract
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("wish.contract:wishContract:view")
	@RequestMapping(value = { "wishLendList" })
	public String wishLendList(TLoanContract tLoanContract,
			HttpServletRequest request, HttpServletResponse response,
			Model model) {
		Double count = 0.0;
		Double sumAmount = 0.0;
		Double sumCharge = 0.0;
		tLoanContract.setStatus(Cons.LoanContractStatus.TO_LOAN);
		tLoanContract.setType("2");// 业务类型:秒杀货款
		Page<TLoanContract> page = tLoanContractService.wishContractlist(
				new Page<TLoanContract>(request, response), tLoanContract);
		List<Double> coll = tLoanContractService.collectContract(tLoanContract);
		if (coll.size() == 3) {
			count = coll.get(0);
			sumAmount = coll.get(1);
			sumCharge = coll.get(2);
		}
		model.addAttribute("count", count);
		model.addAttribute("sumAmount", sumAmount);
		model.addAttribute("sumCharge", sumCharge);
		model.addAttribute("page", page);
		model.addAttribute("tLoanContract", tLoanContract);
		model.addAttribute("admin", UserUtils.getUser().isAdmin());
		return "modules/wish/contract/wishLendList";

	}

	/**
	 * 秒杀货款---还款月对账报表
	 * 
	 * @param tLoanContract
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("wish.contract:wishContract:view")
	@RequestMapping(value = { "wishRepayRecordlist" })
	public String wishRepayRecordlist(RepayRecordVo repayRecordVo,
			HttpServletRequest request, HttpServletResponse response,
			Model model) {
		Page<RepayRecordVo> page = repayRecordService.findRepayRecordVoPage(
				new Page<RepayRecordVo>(request, response), repayRecordVo);
		model.addAttribute("page", page);
		model.addAttribute("repayRecordVo", repayRecordVo);
		model.addAttribute("admin", UserUtils.getUser().isAdmin());
		return "modules/wish/contract/wishRepayRecordlist";

	}
	/**
	 * 还款月对账报表---导出报表
	 * 
	 * @param tLoanContract
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("wish.contract:wishContract:view")
	@RequestMapping(value = { "wishRepayRecordlistExcel" })
	public String wishRepayRecordlistExcel(RepayRecordVo repayRecordVo,
			HttpServletRequest request, HttpServletResponse response,
			Model model) {
		try {
			try {
				String title = "还款月对账报表";
				List<RepayRecordVo> teamList = repayRecordService.findRepayRecordVoPage(repayRecordVo);
				String fileName = title + DateUtils.getDate("yyyyMMddHHmmssS")+ ".xlsx";
				new ExportExcel(title, RepayRecordVo.class).setDataList(teamList).write(response, fileName).dispose();
				return null;
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 秒杀货款---放款日对账报表
	 * 
	 * @param tLoanContract
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("wish.contract:wishContract:view")
	@RequestMapping(value = { "wishLendRecordlist" })
	public String wishLendRecordlist(TLendingVo tLendingVo,
			HttpServletRequest request, HttpServletResponse response,
			Model model) {
		Page<TLendingVo> page = tLendingService.findTLendingVoPage(
				new Page<TLendingVo>(request, response), tLendingVo);
		model.addAttribute("page", page);
		model.addAttribute("tLendingVo", tLendingVo);
		model.addAttribute("admin", UserUtils.getUser().isAdmin());
		return "modules/wish/contract/wishLendRecordlist";

	}
	/**
	 * 放款日对账报表---导出报表
	 * 
	 * @param tLoanContract
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("wish.contract:wishContract:view")
	@RequestMapping(value = { "wishLendRecordlistExcel" })
	public String wishLendRecordlistExcel(TLendingVo tLendingVo,
			HttpServletRequest request, HttpServletResponse response,
			Model model) {
		try {
			try {
				String title = "放款日对账报表";
				List<TLendingVo> teamList = tLendingService.findTLendingVoList(tLendingVo);
				String fileName = title + DateUtils.getDate("yyyyMMddHHmmssS")+ ".xlsx";
				new ExportExcel(title, TLendingVo.class).setDataList(teamList).write(response, fileName).dispose();
				return null;
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 秒杀货款---待锁定列表
	 * 
	 * @param tLoanContract
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("wish.contract:wishContract:view")
	@RequestMapping(value = { "wishBlockList", "" })
	public String wishBlockList(TLoanContract tLoanContract,
			HttpServletRequest request, HttpServletResponse response,
			Model model) {
		tLoanContract.setStatus(Cons.LoanContractStatus.TO_LOAN_APPROVAL);
		tLoanContract.setType("2");// 业务类型:秒杀货款
		// String organId=UserUtils.getUser().getCompany().getId();
		// tLoanContract.setOrganId(organId);
		Page<TLoanContract> page = tLoanContractService.wishContractlist(
				new Page<TLoanContract>(request, response), tLoanContract);
		model.addAttribute("page", page);
		model.addAttribute("loanContract", tLoanContract);
		return "modules/wish/contract/wishBlockList";
	}

	/**
	 * 秒杀货款---导出报表
	 * 
	 * @param tLoanContract
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("wish.contract:wishContract:view")
	@RequestMapping(value = { "wishLendExcel" })
	public String wishLendExcel(WishContractVo wishContractVo,
			HttpServletRequest request, HttpServletResponse response,
			Model model) {
		try {
			try {
				String title = "";
				// wishContractVo.setStatus(Cons.LoanContractStatus.TO_LOAN_APPROVAL);
				wishContractVo.setType("2");// 业务类型:秒杀货款
				List<WishContractVo> teamList = wishExcelService
						.findList(wishContractVo);
				if (wishContractVo.getStatus().equals(
						Cons.LoanContractStatus.TO_LOAN_APPROVAL)) {
					title = "放款待审批数据";
				}
				if (wishContractVo.getStatus().equals(
						Cons.LoanContractStatus.UN_CLEARED)) {
					title = "待收款数据";
				}
				if (wishContractVo.getStatus().equals(
						Cons.LoanContractStatus.TO_LOAN_APPROVAL)) {
					title = "待锁定数据";
				}
				if (wishContractVo.getStatus().equals(
						Cons.LoanContractStatus.TO_LOAN)) {
					title = "待放款数据";
				}
				String fileName = title + DateUtils.getDate("yyyyMMddHHmmssS")
						+ ".xlsx";
				new ExportExcel(title, WishContractVo.class)
						.setDataList(teamList).write(response, fileName)
						.dispose();
				return null;
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 秒杀货款---B端平台审核
	 * 
	 * @param tLoanContract
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("wish.contract:wishContract:view")
	@ResponseBody
	@RequestMapping(value = { "auditWishContract" })
	public Map<String, String> auditWishContract(String contractId,String status,
			HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> map = new HashMap<String, String>();
		TLoanContract tc = tLoanContractService.get(contractId);
		map=getWishStatus(tc);
		if(map.get("status").equals("1")){
			if (StringUtils.isNotBlank(tc.getStatus())) {
				if (tc.getStatus().equals(Cons.LoanContractStatus.TO_APPROVE)) {
					
					if(status.equals("1")){
						tc.setStatus(Cons.LoanContractStatus.TO_LOAN_APPROVAL);
						//tc.setStatus(Cons.LoanContractStatus.TO_LOAN);
						tLoanContractService.updateStatus(tc);
						map.put("msg", "审核成功");
						tLoanContractId=tc.getId();
						/*new Thread() {
							public void run(){
								wishContractService.singleInform(tLoanContractId);
						    }
						}.start();*/
						new Thread() {
							public void run(){
								productPushService.releaseLoan(tLoanContractId);
						    }
						}.start();
					}else if(status.equals("0")){
						tc.setStatus(Cons.LoanContractStatus.TO_SUSPENSION);
						tc.setRemarks("平台审核不通过！");
						tLoanContractService.updateStatus(tc);
						wishContractService.unlockOrder(tLoanContractId);//解锁订单
						map.put("msg", "审核成功,业务状态中止不通过");
						
					}
					
				
				} else {
					map.put("msg", "不是待审批状态,无法进行审批操作！");
				}
			}
		}
		return map;
	}

	/**
	 * 秒杀货款---业务详情
	 * 
	 * @param tLoanContract
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("wish.contract:wishContract:view")
	@RequestMapping(value = { "wishLoanContractView" })
	public String wishLoanContractView(String contractId,
			HttpServletRequest request, HttpServletResponse response,
			Model model) {
		TLoanContract tLoanContract = tLoanContractService.get(contractId);
		// 2-1 还款记录 t_repay_real
		// Page<RepayRecord> pageRepayRecord = new Page<RepayRecord>();//
		// 用于设置排序方式
		// pageRepayRecord.setOrderBy("update_date asc");
		// RepayRecord repayRecord = new RepayRecord();
		// repayRecord.setLoanContractId(contractId);
		// repayRecord.setPage(pageRepayRecord);
		/*
		 * List<RepayRecord> repRecordList =
		 * repayRecordService.findList(repayRecord); BigDecimal noMoney=new
		 * BigDecimal("0"); for(RepayRecord rr:repRecordList){
		 * noMoney=noMoney.add(new BigDecimal(rr.getMoney())); }
		 */
		// noMoney=new
		// BigDecimal(tLoanContract.getLoanAmount()).subtract(noMoney);
		// model.addAttribute("noMoney", noMoney);
		List<TRepayPlan> planList = tRepayPlanService
				.getPlanByContractId(contractId);
		model.addAttribute("loanContract", tLoanContract);
		model.addAttribute("planList", planList);
		model.addAttribute("contractId", tLoanContract.getId());
		return "modules/wish/contract/wishLoanContractView";
	}

	/**
	 * 秒杀货款---收款页面
	 * 
	 * @param tLoanContract
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("wish.contract:wishContract:view")
	@RequestMapping(value = { "repayView" })
	public String repayView(String contractId, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		TLoanContract tLoanContract = tLoanContractService.get(contractId);
		// 2-1 还款记录 t_repay_real
		Page<RepayRecord> pageRepayRecord = new Page<RepayRecord>();// 用于设置排序方式
		pageRepayRecord.setOrderBy("update_date asc");
		RepayRecord repayRecord = new RepayRecord();
		repayRecord.setLoanContractId(contractId);
		repayRecord.setPage(pageRepayRecord);
		List<RepayRecord> repRecordList = repayRecordService
				.findList(repayRecord);
		BigDecimal noMoney = new BigDecimal("0");
		for (RepayRecord rr : repRecordList) {
			noMoney = noMoney.add(new BigDecimal(rr.getMoney()));
		}
		noMoney = new BigDecimal(tLoanContract.getLoanAmount())
				.subtract(noMoney);

		List<TRepayPlan> planList = tRepayPlanService
				.getPlanByContractId(contractId);

		model.addAttribute("noMoney", noMoney);
		model.addAttribute("loanContract", tLoanContract);
		model.addAttribute("repRecordList", repRecordList);
		model.addAttribute("planList", planList);

		model.addAttribute("contractId", tLoanContract.getId());
		return "modules/wish/contract/repayView";
	}

	/**
	 * 秒杀货款---收款
	 * 
	 * @param tLoanContract
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws ParseException
	 */
	@RequiresPermissions("wish.contract:wishContract:view")
	@ResponseBody
	@RequestMapping(value = { "repayMoney" })
	public Map<String, String> repayMoney(HttpServletRequest request,
			HttpServletResponse response, Model model) throws ParseException {
		/*
		 * Map<String,String> map=new HashMap<String, String>(); TLoanContract
		 * tLoanContract= tLoanContractService.get(contractId);
		 * if(contractAmount.compareTo(noMoney)==0){
		 * tLoanContract.setStatus(Cons.LoanContractStatus.CLEARED);
		 * tLoanContractService.updateStatus(tLoanContract); }
		 * 
		 * RepayRecord repayRecord = new RepayRecord();
		 * repayRecord.setId(IdGen.uuid());
		 * repayRecord.setLoanContractId(contractId);
		 * repayRecord.setMoney(contractAmount.doubleValue());
		 * repayRecord.setRepayDate(DateUtils.parseDate(contractDate));
		 * repayRecord.setIsYuQi("0"); repayRecord.setOrganId("1");
		 * repayRecordService.insert(repayRecord); map.put("msg", "还款成功");
		 * map.put("contractId", contractId);
		 */
		Map<String, String> map = returnedMoneyService.repayMoney();
		return map;
	}

	/**
	 * 秒杀货款---收款
	 * 
	 * @param tLoanContract
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws ParseException
	 */
	@RequiresPermissions("wish.contract:wishContract:view")
	@ResponseBody
	@RequestMapping(value = { "realRepayMoney" })
	public Map<String, String> realRepayMoney(String returnedMoneyIds,
			HttpServletRequest request, HttpServletResponse response,
			Model model) throws ParseException {
		// String moneyIds=returnedMoneyIds.substring(1,
		// returnedMoneyIds.length());
		/*String[] ids = returnedMoneyIds.split(",");
		for (int i = 0; i < ids.length; i++) {
			if (i == 0) {
				returnedMoneyIds = "'" + ids[i] + "'";
			} else {
				returnedMoneyIds = returnedMoneyIds + "," + "'" + ids[i] + "'";
			}
		}*/

		Map<String, String> map = returnedMoneyService
				.realRepayMoney(returnedMoneyIds);
		String rmUserIds = map.get("rmUserIds");
		String rmIds = map.get("rmIds");
		/*String[] userIds = rmUserIds.split(",");
		for (int i = 0; i < userIds.length; i++) {
			if (i == 0) {
				rmUserIds = "'" + userIds[i] + "'";
			} else {
				rmUserIds = rmUserIds + "," + "'" + userIds[i] + "'";
			}
		}*/
		//Map<String, String> map = new HashMap<String, String>();	
		
		TLoanContract tLoanContract=new TLoanContract();
		tLoanContract.setCustomerId(rmUserIds);
		List<TLoanContract> userList=tLoanContractService.getLoanMsgByCustomerId(tLoanContract);
		
		Map<Object, Object> partSettled = new HashMap<Object, Object>();// cashBackStatus=1
		Map<Object, Object> settled = new HashMap<Object, Object>();// cashBackStatus=2
		List dkList1 = new ArrayList();
		List dkList2 = new ArrayList();
		
		for(TLoanContract emp:userList){
			BigDecimal loanMoney=new BigDecimal("0");
			ReturnedMoney returnedMoney = new ReturnedMoney();
			returnedMoney.setIsDeal("2,3");
			returnedMoney.setUserId(emp.getCustomerId());
			List<ReturnedMoney> list = returnedMoneyService.findAllList(returnedMoney);
			for (ReturnedMoney rm : list) {
				BigDecimal rmLoanMoney=StringUtils.isNotBlank(rm.getRepayLoanMoney())?new BigDecimal(rm.getRepayLoanMoney()):new BigDecimal("0");
				
				loanMoney=loanMoney.add(rmLoanMoney);//已还
				//loanMoney = loanMoney.setScale(0, BigDecimal.ROUND_HALF_UP);
			}
			BigDecimal lastBackAmount=new BigDecimal(emp.getLoanAmount()).subtract(loanMoney);//剩余未还
			
			loanMoney=loanMoney.multiply(new BigDecimal(100));
			lastBackAmount=lastBackAmount.multiply(new BigDecimal(100));
			
			Map<String, String> mp = new HashMap<String, String>();
			mp.put("userId", emp.getCustomerId());
			mp.put("pvdUid",emp.getCustomerId());
			mp.put("backAmount",loanMoney.toString());
			mp.put("lastBackAmount",lastBackAmount.toString());
			if(lastBackAmount.compareTo(BigDecimal.ZERO)==0){
				dkList2.add(mp);
			}else{
				dkList1.add(mp);
			}
			
		}
		String tardeId = "dkNotify";
		JSONObject reqBody = new JSONObject();
		if(dkList1.size()>0){
			partSettled.put("cashBackStatus", "1");
			partSettled.put("dkList", dkList1);
			reqBody.put("partSettled", partSettled);
		}
		if(dkList2.size()>0){
			settled.put("cashBackStatus", "2");
			settled.put("dkList", dkList2);
			reqBody.put("settled", settled);
		}

		PayecoBackParams payecoBackParams = interactionService.getPayecoRequestByParams(tardeId, reqBody);
		if (payecoBackParams != null && payecoBackParams.getHeader() != null && payecoBackParams.getHeader().getResCode() != null && "0000".equals(payecoBackParams.getHeader().getResCode())) {
			if(StringUtils.isNotBlank(rmIds)){
				ReturnedMoney rmy = new ReturnedMoney();
				rmy.setId(rmIds);
				List<ReturnedMoney> rmylist = returnedMoneyService.findAllList(rmy);
				for (ReturnedMoney ry : rmylist) {
					ry.setIsDeal("3");
					returnedMoneyService.save(ry);
				}
			}
			map.put("msg", "通知成功");
		} else {
			map.put("msg", "通知失败");
			map.put("idstrs", idstrs);
		}

		return map;
		
	}
	/**
	 * 秒杀货款---重新发送收款通知
	 * 
	 * @param tLoanContract
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws ParseException
	 */
	@RequiresPermissions("wish.contract:wishContract:view")
	@ResponseBody
	@RequestMapping(value = { "sendMsg" })
	public Map<String, String> sendMsg(String returnedMoneyIds) {
		Map<String, String> map = new HashMap<String, String>();	
		/*String[] ids = returnedMoneyIds.split(",");
		for (int i = 0; i < ids.length; i++) {
			if (i == 0) {
				returnedMoneyIds = "'" + ids[i] + "'";
			} else {
				returnedMoneyIds = returnedMoneyIds + "," + "'" + ids[i] + "'";
			}
		}*/
		ReturnedMoney rmy = new ReturnedMoney();
		rmy.setIsDeal("2");
		rmy.setId(returnedMoneyIds);
		List<ReturnedMoney> rlist = returnedMoneyService.findListByIds(rmy);
		String rmUserIds="";
		for(ReturnedMoney rm:rlist){
			rmUserIds=rmUserIds+","+rm.getUserId();
		}
		rmUserIds="";
		TLoanContract tLoanContract=new TLoanContract();
		tLoanContract.setCustomerId(rmUserIds);
		List<TLoanContract> userList=tLoanContractService.getLoanMsgByCustomerId(tLoanContract);
		
		
		Map<Object, Object> partSettled = new HashMap<Object, Object>();// cashBackStatus=1
		Map<Object, Object> settled = new HashMap<Object, Object>();// cashBackStatus=2
		List dkList1 = new ArrayList();
		List dkList2 = new ArrayList();
		
		for(TLoanContract emp:userList){
			BigDecimal loanMoney=new BigDecimal("0");
			ReturnedMoney returnedMoney = new ReturnedMoney();
			returnedMoney.setIsDeal("2,3");
			returnedMoney.setUserId(emp.getCustomerId());
			List<ReturnedMoney> list = returnedMoneyService.findAllList(returnedMoney);
			for (ReturnedMoney rm : list) {
				BigDecimal rmLoanMoney=StringUtils.isNotBlank(rm.getRepayLoanMoney())?new BigDecimal(rm.getRepayLoanMoney()):new BigDecimal("0");
				
				loanMoney=loanMoney.add(rmLoanMoney);//已还
				//loanMoney = loanMoney.setScale(0, BigDecimal.ROUND_HALF_UP);
				//loanMoney = loanMoney;
			}
			BigDecimal lastBackAmount=new BigDecimal(emp.getLoanAmount()).subtract(loanMoney);//剩余未还
			
			lastBackAmount=lastBackAmount.multiply(new BigDecimal(100));
			loanMoney=loanMoney.multiply(new BigDecimal(100));
			
			Map<String, String> mp = new HashMap<String, String>();
			mp.put("userId", emp.getCustomerId());
			mp.put("pvdUid",emp.getCustomerId());
			mp.put("backAmount",loanMoney.toString());
			mp.put("lastBackAmount",lastBackAmount.toString());
			if(lastBackAmount.compareTo(BigDecimal.ZERO)==0){
				dkList2.add(mp);
			}else{
				dkList1.add(mp);
			}
			
		}
		String tardeId = "dkNotify";
		JSONObject reqBody = new JSONObject();
		if(dkList1.size()>0){
			partSettled.put("cashBackStatus", "1");
			partSettled.put("dkList", dkList1);
			reqBody.put("partSettled", partSettled);
		}
		if(dkList2.size()>0){
			settled.put("cashBackStatus", "2");
			settled.put("dkList", dkList2);
			reqBody.put("settled", settled);
		}

		PayecoBackParams payecoBackParams = interactionService.getPayecoRequestByParams(tardeId, reqBody);
		if (payecoBackParams != null && payecoBackParams.getHeader() != null && payecoBackParams.getHeader().getResCode() != null && "0000".equals(payecoBackParams.getHeader().getResCode())) {
			/*for (ReturnedMoney rm : list1) {
				rm.setIsDeal("3");
				returnedMoneyService.save(rm);
			}
			for (ReturnedMoney rm : list2) {
				rm.setIsDeal("3");
				returnedMoneyService.save(rm);
			}*/
			map.put("msg", "通知成功");
		} else {
			map.put("msg", "通知失败");
			map.put("idstrs", idstrs);
		}

		return map;
	}
	/**
	 * 秒杀货款---重新发送收款通知
	 * 
	 * @param tLoanContract
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws ParseException
	 *//*
	@RequiresPermissions("wish.contract:wishContract:view")
	@ResponseBody
	@RequestMapping(value = { "sendMsg" })
	public Map<String, String> sendMsg(HttpServletRequest request,
			HttpServletResponse response, Model model) throws ParseException {
		Map<String, String> map = new HashMap<String, String>();
		ReturnedMoney returnedMoney = new ReturnedMoney();
		returnedMoney.setIsDeal("2");
		returnedMoney.setCashBackStatus("1");
		List<ReturnedMoney> list1 = returnedMoneyService.findAllList(returnedMoney);
		
		
		returnedMoney.setCashBackStatus("2");
		List<ReturnedMoney> list2 = returnedMoneyService.findAllList(returnedMoney);
		Map<Object, Object> partSettled = new HashMap<Object, Object>();// cashBackStatus=1
		Map<Object, Object> settled = new HashMap<Object, Object>();// cashBackStatus=2
		List dkList1 = new ArrayList();
		List dkList2 = new ArrayList();
		for (ReturnedMoney rm : list1) {
			Map<String, String> mp = new HashMap<String, String>();
			BigDecimal loanMoney=StringUtils.isNotBlank(rm.getRepayLoanMoney())?new BigDecimal(rm.getRepayLoanMoney()):new BigDecimal("0");
			BigDecimal lastBackAmount=StringUtils.isNotBlank(rm.getLastBackAmount())?new BigDecimal(rm.getLastBackAmount()):new BigDecimal("0");
			loanMoney=loanMoney.multiply(new BigDecimal(100));
			loanMoney = loanMoney.setScale(0, BigDecimal.ROUND_HALF_UP);
			lastBackAmount=lastBackAmount.multiply(new BigDecimal(100));
			lastBackAmount = lastBackAmount.setScale(0, BigDecimal.ROUND_HALF_UP);
			
			mp.put("userId", rm.getUserId());
			mp.put("pvdUid", rm.getUserId());
			mp.put("backAmount",loanMoney.toString());
			mp.put("lastBackAmount",lastBackAmount.toString());
			dkList1.add(mp);
		}
		
		for (ReturnedMoney rm : list2) {
			Map<String, String> mp = new HashMap<String, String>();
			BigDecimal loanMoney=StringUtils.isNotBlank(rm.getRepayLoanMoney())?new BigDecimal(rm.getRepayLoanMoney()):new BigDecimal("0");
			BigDecimal lastBackAmount=StringUtils.isNotBlank(rm.getLastBackAmount())?new BigDecimal(rm.getLastBackAmount()):new BigDecimal("0");
			loanMoney=loanMoney.multiply(new BigDecimal(100));
			loanMoney = loanMoney.setScale(0, BigDecimal.ROUND_HALF_UP);
			lastBackAmount=lastBackAmount.multiply(new BigDecimal(100));
			lastBackAmount = lastBackAmount.setScale(0, BigDecimal.ROUND_HALF_UP);
			
			mp.put("userId", rm.getUserId());
			mp.put("pvdUid", rm.getUserId());
			mp.put("backAmount",loanMoney.multiply(new BigDecimal(100)).toString());
			mp.put("lastBackAmount", lastBackAmount.multiply(new BigDecimal(100)).toString());
			dkList2.add(mp);
		}
		
		String tardeId = "dkNotify";
		JSONObject reqBody = new JSONObject();
		if(dkList1.size()>0){
			partSettled.put("cashBackStatus", "1");
			partSettled.put("dkList", dkList1);
			reqBody.put("partSettled", partSettled);
		}
		if(dkList2.size()>0){
			settled.put("cashBackStatus", "2");
			settled.put("dkList", dkList2);
			reqBody.put("settled", settled);
		}

		PayecoBackParams payecoBackParams = interactionService
				.getPayecoRequestByParams(tardeId, reqBody);
		if (payecoBackParams != null && payecoBackParams.getHeader() != null
				&& payecoBackParams.getHeader().getResCode() != null
				&& "0000".equals(payecoBackParams.getHeader().getResCode())) {
			for (ReturnedMoney rm : list1) {
				rm.setIsDeal("3");
				returnedMoneyService.save(rm);
			}
			for (ReturnedMoney rm : list2) {
				rm.setIsDeal("3");
				returnedMoneyService.save(rm);
			}
			map.put("msg", "通知成功");
		} else {
			map.put("msg", "通知失败");
			map.put("idstrs", idstrs);
		}

		return map;
	}*/

	/**
	 * 秒杀货款---模拟锁定
	 * 
	 * @param tLoanContract
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("wish.contract:wishContract:view")
	@ResponseBody
	@RequestMapping(value = { "wishBlockContract" })
	public int wishBlockContract(String tLoanContractIds,
			HttpServletRequest request, HttpServletResponse response) {
		List<TLoanContract> list = tLoanContractService
				.getLoanListsByIds(tLoanContractIds);
		for (TLoanContract tc : list) {
			tc.setWishStatus("1");// 待放款业务已通知易联
			tc.setStatus(Cons.LoanContractStatus.UN_CLEARED);
			tLoanContractService.updateStatus(tc);
			tLoanContractService.updateWishStatus(tc);
		}
		return list.size();
	}

	/**
	 * 秒杀货款---模拟放款通知
	 * 
	 * @param tLoanContract
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("wish.contract:wishContract:view")
	@ResponseBody
	@RequestMapping(value = { "lendMsg" })
	public int lendMsg(String tLoanContractIds, HttpServletRequest request,
			HttpServletResponse response) {
		List<TLoanContract> list = tLoanContractService
				.getLoanListsByIds(tLoanContractIds);
		for (TLoanContract tc : list) {
			tc.setStatus("6");
			tLoanContractService.updateStatus(tc);

			TLending tLending = new TLending();
			tLending.setTime(new Date());
			tLending.setAmount(tc.getLoanAmount());
			tLending.setContract(tc);
			tLending.setCreateBy(new User("1"));
			tLending.setCreateDate(new Date());
			tLendingService.save(tLending);

		}
		return list.size();
	}

	
	
	/**
	 * 推送脚本审核
	 * 
	 * @param tLoanContract
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("wish.contract:wishContract:view")
	@ResponseBody
	@RequestMapping(value = { "operationMsg" })
	public Map<String, String> operationMsg(String contractId,HttpServletRequest request, HttpServletResponse response) {
		
		Map<String, String> map=new HashMap<String, String>();
		
		TLoanContract tc = tLoanContractService.get(contractId);
		
		System.out.println("userId=========================="+tc.getCustomerId());
		
		map=getWishStatus(tc);
		if(map.get("status").equals("1") && StringUtils.isNotBlank(tc.getCustomerId())){
			String loanAmount=tc.getLoanAmount();
			if(tc.getStatus().equals(Cons.LoanContractStatus.TO_REVIEW)){
				
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
				
				
				//String oper = Rservice.blockOrder(tc.getId(),loanAmount);// 这个R脚本----锁定订单
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
							rp1.setPrincipal(res[4]);
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
							rp2.setPrincipal(res[7]);
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
					}
					tLoanContractService.saveForWish(tc);
					//推送状态通知
					tLoanContractId=tc.getId();
					new Thread() {
						public void run(){
							wishContractService.singleInform(tLoanContractId);
					    }
					}.start();
					map.put("status", "1");
					map.put("msg", "获取回款计划成功！");
				}
			}else{
				map.put("status", "0");
				map.put("msg", "不是新增业务状态，无法获取回款计划！");
			}
		}
	
		return map;
	}
	

	/**
	 * 
	 * @Description 扣款明细
	 * @param tLoanContract
	 * @return
	 * @author lzj
	 */
	@RequestMapping(value = "sendReturnMsg")
	public String sendReturnMsg(String ids,HttpServletRequest request, Model model) {
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		String[] returnMoneyIds = ids.split(",");
		for (int i = 0; i < returnMoneyIds.length; i++) {
			if (i == 0) {
				ids = "'" + returnMoneyIds[i] + "'";
			} else {
				ids = ids + "," + "'" + returnMoneyIds[i] + "'";
			}
		}
		TReturnedMsg rm=new TReturnedMsg();
		rm.setReturnedMoneyId(ids);
		ReturnedMoney returnedMoney=returnedMoneyService.get(returnMoneyIds[0]);
		String fileId="";
		fileId=returnedMoney.getFileId();
		List<TReturnedMsg> array=tReturnedMsgService.findListByReturnMoneyIds(rm);
	    BigDecimal sum=new BigDecimal("0.0");

		for(int i=0;i<array.size();i++){
			Map<String,String> map = Maps.newHashMap();
			map.put("loanContractId", array.get(i).getLoanContractId());
			map.put("money", array.get(i).getMoney());
			list.add(map);
			BigDecimal money=StringUtils.isNotBlank(array.get(i).getMoney())?new BigDecimal(array.get(i).getMoney()):new BigDecimal("0");
			sum=sum.add(money);
		}

		model.addAttribute("list",list);
		model.addAttribute("sum",sum.toString());
		model.addAttribute("fileId",fileId);
		model.addAttribute("ids",ids);
		//http://127.0.0.1:8081/fpd/userfiles/admin/wish/2017-08-29/20170829183255629_125.jpg
		return "modules/wish/contract/returnMsglist";

	}
	
	
	
	/**
	 * 秒杀货款---判断通知状态
	*/
	public Map<String, String> getWishStatus(TLoanContract tc) {
		Map<String, String> map = new HashMap<String, String>();
		if(tc.getWishStatus().equals(tc.getStatus())){//已通知
			map.put("status", "1");
		}else{//未通知
			if(tc.getStatus().equals("1")){
				map.put("msg", "操作失败，新增业务状态(1)没有通知到易联接口");
			}else if(tc.getStatus().equals("2")){
				map.put("msg", "操作失败，审核中业务状态(2)没有通知到易联接口");
			}else if(tc.getStatus().equals("5")){
				map.put("msg", "操作失败，审核通过业务状态(3)没有通知到易联接口");
			}else if(tc.getStatus().equals("0")){
				map.put("msg", "操作失败，审核不通过业务状态(4)没有通知到易联接口");
			}else if(tc.getStatus().equals("6")){
				map.put("msg", "操作失败，已放款业务状态(5)没有通知到易联接口");
			}else if(tc.getStatus().equals("8")){
				map.put("msg", "操作失败，已逾期业务状态(6)没有通知到易联接口");
			}
			map.put("status", "0");
			
		}
		return map;
	}
}
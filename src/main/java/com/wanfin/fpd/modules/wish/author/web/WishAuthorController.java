/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.wish.author.web;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.wanfin.fpd.common.config.Global;
import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.utils.DateUtils;
import com.wanfin.fpd.common.utils.StringUtils;
import com.wanfin.fpd.common.web.BaseController;
import com.wanfin.fpd.modules.api.utils.KeystoreDeal;
import com.wanfin.fpd.modules.api.wiss.entity.PayecoBackParams;
import com.wanfin.fpd.modules.api.wiss.entity.ReceiveParams;
import com.wanfin.fpd.modules.api.wiss.service.InteractionService;
import com.wanfin.fpd.modules.contract.service.TLoanContractService;
import com.wanfin.fpd.modules.employee.entity.TEmployee;
import com.wanfin.fpd.modules.employee.service.TEmployeeService;
import com.wanfin.fpd.modules.sys.utils.UserUtils;
import com.wanfin.fpd.modules.wish.excel.service.WishExcelService;
import com.wanfin.fpd.modules.wish.merchant.entity.Merchant;
import com.wanfin.fpd.modules.wish.merchant.service.MerchantService;
import com.wanfin.fpd.modules.wish.order.entity.WishOrder;
import com.wanfin.fpd.modules.wish.order.service.WishOrderService;

/**
 * 开通权限控制
 * 
 * @author lx
 * @version 2016-03-18
 */
@Scope
@Controller
@RequestMapping(value = "/wish/author")
public class WishAuthorController extends BaseController {
	@Autowired
	private MerchantService merchantService;
	@Autowired
	private TEmployeeService tEmployeeService;

	@Autowired
	private TLoanContractService tLoanContractService;

	@Autowired
	private WishExcelService wishExcelService;

	@Autowired
	private WishOrderService wishOrderService;

	@Autowired
	private InteractionService interactionService;

	//@Autowired
	//private MerchantService merchantService;

	private String merchantId;//Merchant的ID
	private String userId;// 卖家在易联的编号
	private String cardNum;// 卖家身份证号
	private String name;//卖家名称
	private String tel;//卖家电话
	private String bankNum;//卖家银行卡号

	/**
	 * 由易联跳转到B页面
	 * @param tLoanContract
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	/*@RequestMapping(value = { "testGetAuthor" })
	public String test(String userId, String userName, String userType,HttpServletRequest request, HttpServletResponse response,Model model) {
		String forward = "";
		Merchant mb = merchantService.getByUserNum(userId);
		UserUtils.getSession().setAttribute("wishUserId", userId);
		UserUtils.getSession().setAttribute("wishUserName",userName);

		if (StringUtils.isNotBlank(userId) && StringUtils.isNotBlank(userName) && StringUtils.isNotBlank(userType)) {
			if (mb != null) {
				if (mb.getOpenStatus().equals("2")) {
					return "redirect:/wish/order/wishOrder/";// 已开通跳转到---收款信息页面
				} else if (mb.getOpenStatus().equals("1")) {
					addMessage(model, "开通申请中，请耐心等候审核结果！");
					forward = "modules/wishNew/merchant/submitApply";// ---审核中页面

				} else if (mb.getOpenStatus().equals("3")) {
					addMessage(model, "开通申请审核不通过！");
					forward = "modules/wishNew/merchant/unPass";// ---开通失败页面

				} else if (mb.getOpenStatus().equals("0")) {
					//forward = "modules/wishNew/merchant/getServices";// 开通申请页面
					forward = "modules/wishNew/merchant/login";// 跳转到开通申请页面
				}

			} else {
				//forward = "modules/wish/merchant/getServices";// 跳转到开通申请页面
				forward = "modules/wishNew/merchant/login";// 跳转到开通申请页面

			}
		}else{
			forward = "modules/wishNew/merchant/error";// 缺少参数，登录失败页面
		}
		model.addAttribute("userType", userType);
		model.addAttribute("userId", userId);
		return forward;
	}

*/
	@RequestMapping(value = { "testGetAuthor" })
	public String test(String userId, String userName, String userType,HttpServletRequest request, HttpServletResponse response,Model model) {
		String forward = "";
		Merchant mb = merchantService.getByUserNum(userId);
		UserUtils.getSession().setAttribute("wishUserId", userId);
		UserUtils.getSession().setAttribute("wishUserName",userName);

		if (StringUtils.isNotBlank(userId) && StringUtils.isNotBlank(userName) && StringUtils.isNotBlank(userType)) {
			if (mb != null) {
				return "redirect:/wish/contract/wishContract/saveWishContract";// 已开通跳转到---收款信息页面
			} else {
				addMessage(model, "开通申请中，请耐心等候审核结果！");
				forward = "modules/wishNew/merchant/submitApply";// ---审核中页面
			}
		}else{
			forward = "modules/wishNew/merchant/error";// 缺少参数，登录失败页面
		}
		//model.addAttribute("userType", userType);
		model.addAttribute("userId", userId);
		return forward;
	}



	/**
	 * 由易联跳转到B页面----类似登录
	 * 
	 * @param tLoanContract
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
/*	@RequestMapping(value = { "getAuthor" })
	public String getAuthor(ReceiveParams receiveParams,
			HttpServletRequest request, HttpServletResponse response,
			Model model) {
		String forward = "modules/wish/merchant/error";
		System.out.println("[服务端]接收的请求数据：" + receiveParams.getData());
		System.out.println("[服务端]接收的签名：" + receiveParams.getSign());
		//receiveParams.setData(StringUtils.stringEscape(receiveParams.getData()));
		//System.out.println("[Str处理后]接收的请求数据：" + receiveParams.getData());

		// 主结构参数判断
		if (judgeReceiveParams(receiveParams)) {
			if (KeystoreDeal.checkServiceSign(receiveParams.getData(),
					receiveParams.getSign())) {
				System.out.println("[服务端]验证签名成功！");
				// 解析接收的请求data
				JSONObject jsonData = JSONObject.fromObject(receiveParams.getData());
				// 解析接收的请求header
				JSONObject reqHeader = jsonData.getJSONObject("header");

				// 解析接收的请求的body
				JSONObject reqBody = jsonData.getJSONObject("body");

				String userId = reqBody.getString("userId");
				String userName = reqBody.getString("userName");
				String userType=reqBody.getString("userType");

				if (StringUtils.isNotBlank(userId) && StringUtils.isNotBlank(userName) && StringUtils.isNotBlank(userType)) {
					UserUtils.getSession().setAttribute("wishUserId", userId);
					UserUtils.getSession().setAttribute("wishUserName",userName);
					Merchant mb = merchantService.getByUserNum(userId);
					if (mb != null) {
						System.out.println("[服务端]OpenStatus="+mb.getOpenStatus());
						if (StringUtils.isBlank(mb.getOpenStatus()) || mb.getOpenStatus().equals("0")) {
							//forward = "modules/wishNew/merchant/getServices";// 开通申请页面
							forward = "modules/wishNew/merchant/login";// 跳转到开通申请页面
						}else if (mb.getOpenStatus().equals("2")) {
							return "redirect:/wish/order/wishOrder/";// 已开通跳转到---收款信息页面
						} else if (mb.getOpenStatus().equals("1")) {
							addMessage(model, "开通申请中，请耐心等候审核结果！");
							forward = "modules/wishNew/merchant/submitApply";// ---审核中页面

						} else if (mb.getOpenStatus().equals("3")) {
							addMessage(model, "开通申请审核不通过！");
							forward = "modules/wishNew/merchant/unPass";// ---开通失败页面

						}

					} else {
						//forward = "modules/wishNew/merchant/getServices";// 跳转到开通申请页面
						forward = "modules/wishNew/merchant/login";// 跳转到开通申请页面
					}
					model.addAttribute("userId", userId);//
					model.addAttribute("userType", userType);
				} else {
					forward = "modules/wishNew/merchant/error";// 缺少参数，登录失败页面
				}
			}
		}else{
			System.out.println("[服务端]参数验证失败！");
		}
		return forward;
	}*/
	@RequestMapping(value = { "getAuthor" })
	public String getAuthor(ReceiveParams receiveParams,
			HttpServletRequest request, HttpServletResponse response,
			Model model) {
		String forward = "modules/wishNew/merchant/error";
		System.out.println("[服务端]接收的请求数据：" + receiveParams.getData());
		System.out.println("[服务端]接收的签名：" + receiveParams.getSign());
		//receiveParams.setData(StringUtils.stringEscape(receiveParams.getData()));
		//System.out.println("[Str处理后]接收的请求数据：" + receiveParams.getData());

		// 主结构参数判断
		if (judgeReceiveParams(receiveParams)) {
			if (KeystoreDeal.checkApiSign(receiveParams.getData(),receiveParams.getSign())) {
				System.out.println("[服务端]验证签名成功！");
				// 解析接收的请求data
				JSONObject jsonData = JSONObject.fromObject(receiveParams.getData());
				// 解析接收的请求header
				JSONObject reqHeader = jsonData.getJSONObject("header");

				// 解析接收的请求的body
				JSONObject reqBody = jsonData.getJSONObject("body");

				String userId = reqBody.getString("userId");
				String userName = reqBody.getString("userName");
				String userType=reqBody.getString("userType");

				if (StringUtils.isNotBlank(userId) && StringUtils.isNotBlank(userName) && StringUtils.isNotBlank(userType)) {
					UserUtils.getSession().setAttribute("wishUserId", userId);
					UserUtils.getSession().setAttribute("wishUserName",userName);
					Merchant mb = merchantService.getByUserNum(userId);
					if (mb != null) {
						return "redirect:/wish/contract/wishContract/saveWishContract";// 已开通跳转到---收款信息页面
					} else {
						addMessage(model, "开通申请中，请耐心等候审核结果！");
						forward = "modules/wishNew/merchant/submitApply";// ---审核中页面
					}
					/*model.addAttribute("userId", userId);//
					model.addAttribute("userType", userType);*/
				} else {
					forward = "modules/wishNew/merchant/error";// 缺少参数，登录失败页面
				}
			}
		}else{
			System.out.println("[服务端]参数验证失败！");
		}
		return forward;
	}
	/**
	 * 申请开通
	 * 
	 * @param tLoanContract
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = { "applyOpen" }, method = RequestMethod.POST)
	public synchronized String applyOpen(String userName, String identityNum,String phone, String accountNum, String userType,HttpServletRequest request, HttpServletResponse response,Model model) {
		String forward = "modules/wishNew/merchant/error";
		userId = (String) UserUtils.getSession().getAttribute("wishUserId");
		Merchant mb = merchantService.getByUserNum(userId);// 从session中读取用户id

		if (StringUtils.isNotBlank(userId)) {
			if (mb != null) {
				if (mb.getOpenStatus().equals("2")) {
					WishOrder wishOrder=new WishOrder();
					wishOrder.setUserId(userId);
					wishOrder.setLoanOperation("0");
					Page<WishOrder> page = wishOrderService.findPage(new Page<WishOrder>(request, response), wishOrder);
					Double sumAmount=wishOrderService.getSumAmount(wishOrder);
					model.addAttribute("page", page);
					model.addAttribute("sumAmount", sumAmount);
					forward = "modules/wishNew/order/orderList";// 已开通跳转到---收款信息页面

				} else if (mb.getOpenStatus().equals("1")) {
					addMessage(model, "开通申请中，请耐心等候审核结果！");
					forward = "modules/wishNew/merchant/submitApply";// 审核信息页面---提示审核中

				} else if (mb.getOpenStatus().equals("3")) {
					addMessage(model, "开通申请审核不通过！");
					forward = "modules/wishNew/merchant/noPass";// 审核信息页面---提示开通失败

				} else if (mb.getOpenStatus().equals("0")) {
					mb.setOpenStatus("1");
					merchantService.save(mb);
					addMessage(model, "开通申请已提交，请耐心等候审核结果！");
					forward = "modules/wishNew/merchant/submitApply";// 审核信息页面---提示审核中

				}

			} else {// 新建商户--跳转到开通申请页面---状态：待审核-----线程调用
				mb = new Merchant();
				mb.setUserId(userId);
				mb.setUserName(userName);
				mb.setIdNumber(identityNum);
				mb.setPhone(phone);
				mb.setOpenStatus("1");
				merchantService.save(mb);

				TEmployee em=tEmployeeService.get(userId);
				if(em==null){
					TEmployee employee = new TEmployee();
					employee.setId(userId);// 此处一定要用这个userId，设计
					employee.setName(userName);
					employee.setCardNum(identityNum);
					employee.setGatheringNumber(accountNum);
					employee.setOrganId("wish");
					tEmployeeService.insert(employee);
				}
				//merchantId = em.getId();
				merchantId = userId;
				cardNum = identityNum;
				name = userName;
				tel = phone;
				bankNum = accountNum;
				/*
				new Thread() {
					public void run() {
						System.out.println("开通数据验证！");
						//--组织请求data body
						String date=DateUtils.formatDate(new Date(),"yyyyMMdd");
						String tardeId = "regNotify";
						JSONObject reqBody = new JSONObject();
						reqBody.put("userId", userId);
						reqBody.put("pvdUid", userId);
					  //reqBody.put("regStatus", "1");//1、开通成功 2、开通失败
						reqBody.put("reqOrdersNo", date+"_"+userId+"_init");
						reqBody.put("userName", name);//姓名
						reqBody.put("identityNum", cardNum);//身份证号
						reqBody.put("phone", tel);//手机号码
						reqBody.put("accountNum",bankNum);//银行账号
						PayecoBackParams payecoBackParams = interactionService.getPayecoRequestByParams(tardeId, reqBody);
						Merchant merchant = (Merchant) JSONObject.toBean(payecoBackParams.getJsonBody(), Merchant.class);
				        merchant.setShopCreateDate(merchant.getCreateDate());
				        merchant.setShopUpdateDate(merchant.getUpdateDate());
				        merchant.setCreateDate(null);
				        merchant.setUpdateDate(null);
				     
				        if(merchant.verifyDatas() && "1".equals(merchant.getRegStatus()) && "0".equals(merchant.getUserNameComp()) && "0".equals(merchant.getIdentityNumComp()) && "0".equals(merchant.getPhoneComp()) && "0".equals(merchant.getAccountNumComp())){
				        	merchant.setOpenStatus("2");// 成功
				        }else{
				        	merchant.setOpenStatus("3");// 失败
				        }
				        if(StringUtils.isNotBlank(merchant.getUserId())){
				           merchantService.updateByUserId(merchant);
				        }
				       
					}
				}.start();
				*/
				addMessage(model, "开通申请已提交，请耐心等候审核结果！");
				forward = "modules/wishNew/merchant/submitApply";// 审核信息页面---提示审核中
			}
		}
		model.addAttribute("userId", userId);//
		return forward;
	}

	/**
	 * 简单检查参数是否正确
	 * 
	 * @param receiveParams
	 * @return
	 */
	private boolean judgeReceiveParams(ReceiveParams receiveParams) {
		boolean checkResult = true;

		if (StringUtils.isBlank(receiveParams.getTradeId())) {// 交易码
			checkResult = false;
		} else if (StringUtils.isBlank(receiveParams.getPvdId())
				|| !Global.getApiConfig("winfin.pvdId").equals(
						receiveParams.getPvdId())) {// 贷款业务提供商接入编号
			checkResult = false;
		} else if (StringUtils.isBlank(receiveParams.getVersion())) {// 接口版本号
			checkResult = false;
		} else if (StringUtils.isBlank(receiveParams.getData())) {// 业务报文
			checkResult = false;
		} else if (StringUtils.isBlank(receiveParams.getSign())) {// 签名
			checkResult = false;
		}

		return checkResult;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getCardNum() {
		return cardNum;
	}
	public void setCardNum(String cardNum) {
		this.cardNum = cardNum;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getBankNum() {
		return bankNum;
	}
	public void setBankNum(String bankNum) {
		this.bankNum = bankNum;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

}
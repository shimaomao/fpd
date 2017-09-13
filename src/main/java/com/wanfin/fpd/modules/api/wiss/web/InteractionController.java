package com.wanfin.fpd.modules.api.wiss.web;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wanfin.fpd.common.config.Global;
import com.wanfin.fpd.common.utils.DateUtils;
import com.wanfin.fpd.common.utils.StringUtils;
import com.wanfin.fpd.modules.api.utils.KeystoreDeal;
import com.wanfin.fpd.modules.api.wiss.entity.HkInform;
import com.wanfin.fpd.modules.api.wiss.entity.InformOrder;
import com.wanfin.fpd.modules.api.wiss.entity.IssueLoans;
import com.wanfin.fpd.modules.api.wiss.entity.PayecoBackParams;
import com.wanfin.fpd.modules.api.wiss.entity.ReceiveParams;
import com.wanfin.fpd.modules.api.wiss.service.HkInformService;
import com.wanfin.fpd.modules.api.wiss.service.InformOrderService;
import com.wanfin.fpd.modules.api.wiss.service.InteractionService;
import com.wanfin.fpd.modules.company.entity.TCompany;
import com.wanfin.fpd.modules.company.service.TCompanyService;
import com.wanfin.fpd.modules.contract.entity.TLoanContract;
import com.wanfin.fpd.modules.contract.service.TLoanContractService;
import com.wanfin.fpd.modules.employee.entity.TEmployee;
import com.wanfin.fpd.modules.employee.service.TEmployeeService;
import com.wanfin.fpd.modules.lending.entity.TLending;
import com.wanfin.fpd.modules.lending.service.TLendingService;
import com.wanfin.fpd.modules.wish.contract.service.WishContractService;
import com.wanfin.fpd.modules.wish.merchant.entity.Merchant;
import com.wanfin.fpd.modules.wish.merchant.service.MerchantService;

import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

/**
 * 给到易联的接口控制类：
 * 所有给到易联的接口都通过该类来分流处理
 * @author user
 *
 */
@Controller
@RequestMapping(value = { "/api/wiseyl", "${adminPath}/api/wiseyl" })
public class InteractionController {
	@Autowired
	private InformOrderService informOrderService;
	@Autowired
	private TLoanContractService tLoanContractService;
	@Autowired
	private InteractionService interactionService;
	@Autowired
	private HkInformService hkInformService;
	@Autowired
	private MerchantService merchantService;
	@Autowired
	private TLendingService tLendingService;
	@Autowired
	private TEmployeeService tEmployeeService;

	@Autowired
	private TCompanyService tCompanyService;

	@Autowired
	private WishContractService wishContractService;

	private String tLoanContractId = "";

	@ResponseBody
	@RequestMapping(value = {""})
	public Map<String, Object> saveApply(ReceiveParams receiveParams, HttpServletRequest request, HttpServletResponse response, Model model){
		Map<String, Object> result = new HashMap<>();

		//请求时间戳
		String reqTime = DateUtils.getDate("yyyyMMddHHmmss");

		System.out.println("[服务端]接收的请求数据："+receiveParams.getData());
        System.out.println("[服务端]接收的签名："+receiveParams.getSign());

        //--组织响应的 data header
        JSONObject retHeader = new JSONObject();
        //--组织响应的 data body
        JSONObject retBody = new JSONObject();
        //主结构参数判断
        if(judgeReceiveParams(receiveParams)){
	        if(KeystoreDeal.checkApiSign(receiveParams.getData(), receiveParams.getSign())){
	        	System.out.println("[服务端]验证签名成功！");
        		//解析接收的请求data
        		JSONObject jsonData = JSONObject.fromObject(receiveParams.getData());

        		//解析接收的请求data
        		JSONObject reqHeader = jsonData.getJSONObject("header");

        		try{
	        		//传输过来的参数获取
	        		String paramReqTime = reqHeader.getString("reqTime");
	        		//String paramTradeId = reqHeader.getString("tradeId");
	        		if(StringUtils.isNotBlank(paramReqTime)){
	        			reqTime = paramReqTime;
	        		}
	        		boolean bodyParamsError = false;
	        		System.out.println("接口接收内容:"+receiveParams.getTradeId()+";json:"+jsonData.toString());
	        		//业务处理,根据不同内容来解析获取body中的数据
	        		if("regNotify".equals(receiveParams.getTradeId())){//4.2 开通通知(regNotify)
	        			//解析接收的请求的body
	            		JSONObject reqBody = jsonData.getJSONObject("body");
	            		//String[] dateFormats = new String[] {"yyyyMMdd" };//"yyyy-MM-dd HH:mm:ss"
	            		String[] dateFormats = new String[] {"yyyyMMddHHmmss","yyyyMMdd","yyyy-MM-dd" };
	            		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher(dateFormats));
	            		Merchant merchant = (Merchant)JSONObject.toBean(reqBody, Merchant.class);
	            		if(merchant != null && merchant.verifyDatas(true)){
	            			try{
	            				merchant.setPhone(StringUtils.dealPhone(merchant.getPhone()));//处理手机号码
	            				
	            				Merchant mer=merchantService.getByUserNum(merchant.getUserId());
	            				if(mer==null){
	            					if(merchant.getShopType().equals("1")){
		            					TEmployee employee=tEmployeeService.get(merchant.getUserId());
			            				if(employee == null){
			            					employee = new TEmployee();
			            					employee.setId(merchant.getUserId());// 此处一定要用这个userId，设计
			            					employee.setName(merchant.getAccountOwnerName());
			            					employee.setCardNum(merchant.getAccountOwnerNumber());
			            					employee.setGatheringNumber(merchant.getAccountNo());
			            					employee.setTelephone(merchant.getAccountRelatedPhone());
			            					employee.setOrganId("wish");
			            					tEmployeeService.insert(employee);
			            				}
		            				}else if(merchant.getShopType().equals("2")){
		            					TCompany company=tCompanyService.get(merchant.getUserId());
			            				if(company == null){
			            					company = new TCompany();
			            					company.setId(merchant.getUserId());// 此处一定要用这个userId，设计
			            					company.setName(merchant.getAccountOwnerName());
			            					company.setCardNum(merchant.getAccountOwnerNumber());
			            					company.setGatheringNumber(merchant.getAccountNo());
			            					company.setPhone(merchant.getAccountRelatedPhone());;
			            					company.setOrganId("wish");
			            					tCompanyService.insert(company);
			            				}
		            				}
		            				if("1".equals(merchant.getRegStatus())){
		            					merchant.setOpenStatus("2");// 成功
		            					merchantService.save(merchant);
		            				}else{//开通失败放日志库表
		            					merchant.setOpenStatus("3");// 失败
		            					merchantService.saveLog(merchant);
		            				}
	            				}else{
	            					if("1".equals(merchant.getRegStatus())){
	            						merchant.setId(mer.getId());
	            						merchant.setOpenStatus("2");// 更新成功
		            					merchantService.save(merchant);
		            					merchantService.saveLog(mer);
		            				}else{//开通失败放日志库表
		            					merchant.setId(mer.getId());
		            					merchant.setOpenStatus("3");// 更新失败
		            					merchantService.saveLog(merchant);
		            				}
	            				}
	            			}catch(Exception e){
	            				e.printStackTrace();
	            				retHeader.put("resCode", "1000");
		        		        retHeader.put("resMsg", "处理失败！");
	            			}
	            			retHeader.put("resCode", "0000");
	        		        retHeader.put("resMsg", "接收成功");
	            		}else{
	            			merchant.setId("new");//新开通的时候还没有ID
	            			merchantService.saveLog(merchant);
	            			bodyParamsError = true;
	            		}
	        		}else if("ordersNotify".equals(receiveParams.getTradeId())){//5.2 订单历史数据上传通知
	        			//解析接收的请求的body
	            		JSONObject reqBody = jsonData.getJSONObject("body");
	            		//String[] dateFormats = new String[] {"yyyyMMdd" };//"yyyy-MM-dd HH:mm:ss"
	            		String[] dateFormats = new String[] {"yyyyMMddHHmmss","yyyyMMdd","yyyy-MM-dd" };
	            		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher(dateFormats));
	            		InformOrder informOrder = (InformOrder) JSONObject.toBean(reqBody, InformOrder.class);
	        			if(informOrder != null && informOrder.verifyDatas()){
	        				//接收订单历史数据上传通知后处理 TODO
	        				//直接更新数据库字段状态
	        				InformOrder oldInformOrder = informOrderService.getByCondition(informOrder);
	        				informOrder.setBackStartDate(informOrder.getStartDate());
	        				informOrder.setBackEndDate(informOrder.getEndDate());
	        				if(oldInformOrder == null){//该通知不存在
	        				}else{
	        					informOrder.setId(oldInformOrder.getId());
	        					informOrder.setStartDate(oldInformOrder.getStartDate());
	        					informOrder.setEndDate(oldInformOrder.getEndDate());
	        				}
	        				if("0000".equals(informOrder.getDealStatus())){//成功
	        					informOrder.setDealStatus("2");
	        				}else if("0001".equals(informOrder.getDealStatus())){//无数据
	        					informOrder.setDealStatus("5");
	        				}else{
	        					informOrder.setDealStatus("4");
	        				}
	        				informOrderService.save(informOrder);

	        				retHeader.put("resCode", "0000");
	        		        retHeader.put("resMsg", "接收成功");
	        			}else{
	        				bodyParamsError = true;
	        			}
//	        		}else if("lockChannels".equals(receiveParams.getTradeId())){//7.2 锁定支付通道信息   该接口于2017-07-14由易联要求废弃
//	        			//解析接收的请求的body
//	        			//JSONArray reqBody = jsonData.getJSONArray("body");
//	        			JSONObject reqBody = jsonData.getJSONObject("body");
//	        			//解析data中的body内容
//	                    JSONArray lockListArray = reqBody.getJSONArray("lockList");
//	                    //List<PaymentChannels> paymentChannelsList = (List<PaymentChannels>) JSONArray.toCollection(reqBody, PaymentChannels.class);
//	        			@SuppressWarnings("unchecked")
//	        			List<PaymentChannels> paymentChannelsList = (List<PaymentChannels>) JSONArray.toCollection(lockListArray, PaymentChannels.class);
//	        			if(paymentChannelsList != null && paymentChannelsList.size()>0){
//	        				//参数校验
//	        				for(PaymentChannels paymentChannels:paymentChannelsList){
//	        					if(!paymentChannels.verifyDatas()){
//	        						bodyParamsError = true;
//	        						break;
//	        					}
//	        				}
//	        				if(!bodyParamsError){
//		        				//数据处理 TODO
//		        				for(PaymentChannels paymentChannels:paymentChannelsList){
//		        					//TLoanContract temp = new TLoanContract();
//		        					//temp.setId(paymentChannels.getLoanBusinessId());
//		        					//temp.setCustomerId(paymentChannels.getPvdUid());
//		        					
//		        					TLoanContract loanContract = tLoanContractService.get(paymentChannels.getLoanBusinessId());
//		        					if(loanContract != null){
//			        					//锁定支付通道信息
//			        					if(StringUtils.isNotBlank(paymentChannels.getAccountStatus())){
//			        						loanContract.setAccountStatus(paymentChannels.getAccountStatus());
//			        						if("4".equals(loanContract.getStatus()) && "1".equals(paymentChannels.getAccountStatus())){
//			        							loanContract.setStatus("5");
//			        						}
//			        					}
//	//		        					//回款通知
//	//		        					if(StringUtils.isNotBlank(paymentChannels.getCashBackStatus())){
//	//		        						loanContract.setCashBackStatus(paymentChannels.getCashBackStatus());
//	//		        					}
//			        					
//			        					//更新
//			        					tLoanContractService.saveForWish(loanContract);
//		        					}
//		        				}
//		        				retHeader.put("resCode", "0000");
//		        		        retHeader.put("resMsg", "接收成功");
//	        				}
//	        			}else{//body内容为空
//	        				retHeader.put("resCode", "0004");
//		                    retHeader.put("resMsg", "[服务端]body中数据为空！");
//	        			}
	        		}else if("fkNotify".equals(receiveParams.getTradeId())){//7.2 放款通知(fkNotify)
	        			//解析接收的请求的body
	            		JSONObject reqBody = jsonData.getJSONObject("body");
	            		//解析data中的body内容
	                    JSONArray fkListArray = reqBody.getJSONArray("fkList");
	                    @SuppressWarnings("unchecked")
	        			List<IssueLoans> issueLoansList = (List<IssueLoans>) JSONArray.toCollection(fkListArray, IssueLoans.class);
	                    if(issueLoansList != null && issueLoansList.size()>0){
	                    	//参数校验
	        				for(IssueLoans issueLoans:issueLoansList){
	        					if(!issueLoans.verifyDatas()){
	        						bodyParamsError = true;
	        						break;
	        					}
	        					if(",1,2,4,7,8,9,".equals(issueLoans.getLoanStatus())){
	        						bodyParamsError = true;
	        						break;
	        					}
	        				}
	        				if(!bodyParamsError){
	        					for(IssueLoans issueLoans:issueLoansList){

	        						TLoanContract loanContract = tLoanContractService.get(issueLoans.getLoanBusinessId());
	        						tLoanContractId=loanContract.getId();
	        						//if(loanContract != null && ( "4".equals(loanContract.getStatus()) || "5".equals(loanContract.getStatus()) || "6".equals(issueLoans.getLoanStatus()) ))
	        						//if(loanContract != null && ( "4".equals(loanContract.getStatus()) || "5".equals(loanContract.getStatus()) ))
	        						System.out.println("放款时候当前业务状态："+loanContract.getStatus());
	        						if(loanContract != null && "5".equals(loanContract.getStatus()))
	        						{

	        							if("5".equals(issueLoans.getLoanStatus())){
		        							loanContract.setStatus("6");
		        							tLoanContractService.saveForWish(loanContract);

		        							TLending tLending=new TLending();
		        							tLending.setTime(new Date());
		        							tLending.setAmount(loanContract.getLoanAmount());
		        							tLending.setContract(loanContract);
		        							tLendingService.save(tLending);

		        							//推送状态通知
		        							new Thread() {
		        								public void run(){
		        									wishContractService.singleInform(tLoanContractId);
		        							    }
		        							}.start();


	        							}else if("7".equals(issueLoans.getLoanStatus())){//审核通过，锁定失败:已终止
	        								loanContract.setStatus("0");
	        								loanContract.setWishStatus("0");//该状态对于易联还是传回7
	        								loanContract.setRemarks("审核通过，锁定失败:已终止！直接通知");
	        								tLoanContractService.saveForWish(loanContract);
	        								
	        								wishContractService.unlockOrder(loanContract.getId());//解锁订单
	        								//推送状态通知
	        								new Thread() {
	        									public void run(){
	        										wishContractService.informByStatus(tLoanContractId, "7");
	        								    }
	        								}.start();

	        							}else if("8".equals(issueLoans.getLoanStatus())){//放款失败:已终止(易联会持续放款)
//	        								loanContract.setStatus("0");
//	        								loanContract.setRemarks("放款失败:已终止！");
//	        								tLoanContractService.saveForWish(loanContract);
//
//	        								wishContractService.unlockOrder(loanContract.getId());//解锁订单
//	        								//推送状态通知
//	        								new Thread() {
//	        									public void run(){
//	        										wishContractService.singleInform(tLoanContractId);
//	        								    }
//	        								}.start();
	        								if(StringUtils.isBlank(loanContract.getRemarks()) || !loanContract.getRemarks().contains("放款失败")){
	        									loanContract.setRemarks(loanContract.getRemarks() + ":放款失败");
	        								}
	        								tLoanContractService.saveForWish(loanContract);
	        							}else if("4".equals(issueLoans.getLoanStatus())){//已终止
	        								loanContract.setStatus("0");
	        								loanContract.setRemarks("已终止！");
	        								tLoanContractService.saveForWish(loanContract);

	        								wishContractService.unlockOrder(loanContract.getId());//解锁订单
	        								//推送状态通知
	        								new Thread() {
	        									public void run(){
	        										wishContractService.singleInform(tLoanContractId);
	        								    }
	        								}.start();

	        							}



	        						}
	        					}
	        					retHeader.put("resCode", "0000");
		        		        retHeader.put("resMsg", "接收成功");
	        				}
	                    }else{//body内容为空
	        				retHeader.put("resCode", "0004");
		                    retHeader.put("resMsg", "[服务端]body中数据为空！");
	        			}

	        		}else if("hkNotify".equals(receiveParams.getTradeId())){//8.1 回款通知(hkNotify)
	        			//解析接收的请求的body
	            		JSONObject reqBody = jsonData.getJSONObject("body");
	            		//解析data中的body内容
	            		String filePath =  reqBody.getString("filePath");//回款文件路径（sftp约定目录+ filePath）
	            		System.out.println("回款文件路径:"+filePath);
	            		if(StringUtils.isNotBlank(filePath)){
	            			HkInform hkInform = new HkInform();
	            			hkInform.setFilePath(filePath);
	            			hkInform.setDealStatus("0");
	            			hkInformService.save(hkInform);
	            		}
	            		retHeader.put("resCode", "0000");
        		        retHeader.put("resMsg", "接收成功");
	        		}/*else if("updateMerchant".equals(receiveParams.getTradeId())){//4.3 更新店铺信息
	        			//解析接收的请求的body
	            		JSONObject reqBody = jsonData.getJSONObject("body");
	            		String[] dateFormats = new String[] {"yyyyMMddHHmmss","yyyyMMdd","yyyy-MM-dd" };
	            		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher(dateFormats));
	            		Merchant merchant = (Merchant) JSONObject.toBean(reqBody, Merchant.class);
	            		if(merchant.verifyDatas()){
	            			//先获取历史信息
	            			Merchant oldInfo = merchantService.getByUserNum(merchant.getUserId());
	            			if(oldInfo == null){//该用户不存在，则新增
	            				retHeader.put("resCode", "0010");
		        		        retHeader.put("resMsg", "该用户不存在");
	            			}else{
	            				merchant.setId(oldInfo.getId());
	            				if(merchant.getCreateDate() != null){
	            					merchant.setShopCreateDate(merchant.getCreateDate());
	            					merchant.setCreateDate(null);
	            				}
	            				if(merchant.getUpdateDate() != null){
	            					merchant.setShopUpdateDate(merchant.getUpdateDate());
	            					merchant.setUpdateDate(null);
	            				}
	            				merchantService.save(merchant);
	            				
	            				retHeader.put("resCode", "0000");
	            				retHeader.put("resMsg", "接收成功");
	            			}
	            		}else{
	            			bodyParamsError = true;
	            		}
	        		}*//*else if("".equals(receiveParams.getTradeId())){//
	        			
	        		}*/

	        		//body中参数错误
	        		if(bodyParamsError){
	        			System.out.println("[服务端]body中参数错误！");

	        			retHeader.put("resCode", "0003");
	                    retHeader.put("resMsg", "[服务端]body中参数错误！");
	        		}
        		}catch(Exception e){
        			e.printStackTrace();
        			System.out.println("[服务端]参数错误！");

        			retHeader.put("resCode", "0002");
                    retHeader.put("resMsg", "[服务端]header中参数错误！");
        		}
	        }else{
	        	System.out.println("[服务端]验证签名失败！");

	        	retHeader.put("resCode", "0005");
                retHeader.put("resMsg", "[服务端]验证签名失败！");
	        }
        }else{
        	System.out.println("[服务端]参数错误！");

        	retHeader.put("resCode", "0001");
            retHeader.put("resMsg", "[服务端]参数错误！");
        }
        //响应时间戳
      	String resTime = DateUtils.getDate("yyyyMMddHHmmss");
      	retHeader.put("tradeId", receiveParams.getTradeId());
        retHeader.put("pvdId", Global.getApiConfig("winfin.pvdId"));
        retHeader.put("reqTime", reqTime);
        retHeader.put("resTime", resTime);

        //--组织响应data
        JSONObject retData = new JSONObject();
        retData.put("header", retHeader);
        retData.put("body", retBody);

        //--获取响应data字符串
        String retDataStr = retData.toString();
        System.out.println("[服务端]组织响应data：" + retDataStr);

        //生成
        String retSign = KeystoreDeal.getPlatformSign(retDataStr);
        System.out.println("[服务端]生成签名：" + retSign);

        //组织响应数据
        result.put("data", retDataStr);
        result.put("sign", retSign);

		return result;
	}

	/**
	 * 简单检查参数是否正确
	 * @param receiveParams
	 * @return
	 */
	private boolean judgeReceiveParams(ReceiveParams receiveParams){
		boolean checkResult = true;

		if(StringUtils.isBlank(receiveParams.getTradeId())){//交易码
			System.out.println("交易码");
			checkResult = false;
		}else if(StringUtils.isBlank(receiveParams.getPvdId()) || !Global.getApiConfig("winfin.pvdId").toLowerCase().equals(receiveParams.getPvdId().toLowerCase())){//贷款业务提供商接入编号
			System.out.println("贷款业务提供商接入编号");
			checkResult = false;
		}else if(StringUtils.isBlank(receiveParams.getVersion())){//接口版本号
			System.out.println("接口版本号");
			checkResult = false;
		}else if(StringUtils.isBlank(receiveParams.getData())){//业务报文
			System.out.println("业务报文");
			checkResult = false;
		}else if(StringUtils.isBlank(receiveParams.getSign())){//签名
			System.out.println("签名");
			checkResult = false;
		}

		return checkResult;
	}

	/**
	 * 测试易联接口用
	 * @param receiveParams
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = {"testPayecoApi"})
	public Map<String, Object> testPayecoApi(String aipCode, HttpServletRequest request, HttpServletResponse response, Model model){
		Map<String, Object> result = new HashMap<>();
		System.out.println("接收到参数:"+aipCode+";");
		//--组织请求data body
        JSONObject reqBody = null;
		String tardeId = null;
		if(StringUtils.isBlank(aipCode) || "regNotify".equals(aipCode)){//4.2 开通通知(regNotify)
			tardeId = "regNotify";
			reqBody = new JSONObject();
			reqBody.put("userId", "111");
	        reqBody.put("pvdUid", "222");
	        reqBody.put("regStatus", "1");//1、开通成功 2、开通失败
	        reqBody.put("reqOrdersNo", "20170708_111_init");
	        reqBody.put("userName", "测试-张三");//姓名
	        reqBody.put("identityNum", "999888123412121234");//身份证号
	        reqBody.put("phone", "13100000000");//手机号码
	        reqBody.put("accountNum", "88888888");//银行账号
		}else if("getOrders".equals(aipCode)){//5.1 订单历史数据(getOrders)
			tardeId = aipCode;
			reqBody = new JSONObject();
			reqBody.put("userId", "222");
	        reqBody.put("startDate", "20170101");
	        reqBody.put("endDate",   "20170331");
	        reqBody.put("reqOrdersNo", "20170708_222_20170101_20170331");
		}else if("getRates".equals(aipCode)){//6.1 获取汇率(getRates)
			tardeId = aipCode;
			reqBody = new JSONObject();
//		}else if("informLoans".equals(aipCode)){//7.1 待资方审核放款列表
//			tardeId = aipCode;
//			reqBody = new JSONObject();
//			reqBody.put("userId", "111");
//	        reqBody.put("pvdUid", "222");
//	        reqBody.put("loanBusinessId", "111");//借款业务ID
//	        reqBody.put("loanStatus", "4");//借款业务状态
		}else if("busiResultNotify".equals(aipCode)){//7.1 借款业务状态变更通知(busiResultNotify)
			tardeId = aipCode;
			reqBody = new JSONObject();

			JSONArray list = new JSONArray();

			JSONObject busi = new JSONObject();
			busi.put("userId", "111");
			busi.put("pvdUid", "222");
			busi.put("loanBusinessId", "111");
			busi.put("loanStatus", "4");
			busi.put("busiSbTime", "20170701121133");
			busi.put("busiSbAmount", "111111");
			busi.put("fee", "1111");
			busi.put("busiAtAmount", "110000");
			busi.put("lastRepayDate", "20170701");

			list.add(busi);

			reqBody.element("busiList", list);
		}else if("dkNotify".equals(aipCode)){//8.2 打款通知(dkNotify)
			tardeId = aipCode;
			reqBody = new JSONObject();

			JSONArray list = new JSONArray();

			JSONObject dk = new JSONObject();
			dk.put("userId", "111");
			dk.put("pvdUid", "222");
			dk.put("loanBusinessId", "111");//借款业务ID
			dk.put("cashBackStatus", "222");//回款状态

			list.add(dk);

			reqBody.element("dkList", list);
		}

		if(tardeId != null && reqBody != null){
			PayecoBackParams payecoBackParams = interactionService.getPayecoRequestByParams(tardeId, reqBody);
			result.put("payecoBackParams", payecoBackParams);
		}
		result.put("code", "100");

		return result;
	}
}

package com.wanfin.fpd.common.quartz;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.wanfin.fpd.common.utils.DateUtils;
import com.wanfin.fpd.modules.api.wiss.entity.InformOrder;
import com.wanfin.fpd.modules.api.wiss.entity.PayecoBackParams;
import com.wanfin.fpd.modules.api.wiss.service.InformOrderService;
import com.wanfin.fpd.modules.api.wiss.service.InteractionService;
import com.wanfin.fpd.modules.wish.merchant.entity.Merchant;
import com.wanfin.fpd.modules.wish.merchant.service.MerchantService;

import net.sf.json.JSONObject;

/**
 * 5.1 订单历史数据(getOrders)   定时
 * 1.获取开通用户
 * 2.获取开通用户获取的订单信息
 * 3.分析获取哪个时段的订单信息
 * @author user
 *
 */
public class InformGetOrders {
	@Autowired
	private InteractionService interactionService;
	@Autowired
	private MerchantService merchantService;//获取商户信息
	@Autowired
	private InformOrderService informOrderService;
	
	/** 
     * 定时任务，执行方法 
     * */  
    public void execute(){
    	String tardeId = "getOrders";
    	String nowDate = DateUtils.getDate("yyyyMMdd");
    	String startDate = DateUtils.calculateDate(nowDate, 0, 0, -90, "yyyyMMdd");//前90天时间
    	String endDate = DateUtils.calculateDate(nowDate, 0, 0, -1, "yyyyMMdd");//获取当前前一天时间
    	System.out.println("nowDate:" + nowDate + "; startDate:" + startDate + " ;endDate:" + endDate +"");
    	
    	Merchant searchMerchant = new Merchant();
    	List<Merchant> merchantList = merchantService.findAllList(searchMerchant);
    	for(Merchant tmpMerchant:merchantList){
    		System.out.println(tmpMerchant.toString());
    		//获取历史信息请求号
    		StringBuilder strBuilder = new StringBuilder();
    		strBuilder.append(nowDate);
    		strBuilder.append("_");
    		strBuilder.append(tmpMerchant.getUserId());
    		strBuilder.append("_");
    		strBuilder.append(startDate);
    		strBuilder.append("_");
    		strBuilder.append(endDate);
    		strBuilder.append(".xlsx");
    		
    		JSONObject reqBody = new JSONObject();
			reqBody.put("userId", tmpMerchant.getUserId());
	        reqBody.put("startDate", startDate);
	        reqBody.put("endDate",   endDate);
	        reqBody.put("reqOrdersNo", strBuilder.toString());
	        
	        InformOrder informOrder = new InformOrder();
	        informOrder.setUserId(tmpMerchant.getUserId());
	        informOrder.setStartDate(DateUtils.stringToDate(startDate, "yyyyMMdd"));
	        informOrder.setEndDate(DateUtils.stringToDate(endDate, "yyyyMMdd"));
	        informOrder.setReqOrdersNo(strBuilder.toString());
	        
	        //PayecoBackParams payecoBackParams = null;
	        PayecoBackParams payecoBackParams = interactionService.getPayecoRequestByParams(tardeId, reqBody);
	        if(payecoBackParams != null && payecoBackParams.getHeader() != null && payecoBackParams.getHeader().getResCode() != null && "0000".equals(payecoBackParams.getHeader().getResCode())){
	        	informOrder.setDealStatus("1");
	        }else{
	        	informOrder.setDealStatus("0");
	        }
	        informOrderService.save(informOrder);
    	}
    }
}

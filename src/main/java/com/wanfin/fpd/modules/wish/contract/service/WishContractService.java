package com.wanfin.fpd.modules.wish.contract.service;

import java.math.BigDecimal;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wanfin.fpd.common.config.Cons;
import com.wanfin.fpd.common.utils.DateUtils;
import com.wanfin.fpd.modules.api.wiss.entity.PayecoBackParams;
import com.wanfin.fpd.modules.api.wiss.service.InteractionService;
import com.wanfin.fpd.modules.contract.entity.TLoanContract;
import com.wanfin.fpd.modules.contract.service.TLoanContractService;
import com.wanfin.fpd.modules.repayplan.entity.TRepayPlan;
import com.wanfin.fpd.modules.repayplan.service.TRepayPlanService;
import com.wanfin.fpd.modules.wish.order.entity.WishOrder;
import com.wanfin.fpd.modules.wish.order.service.WishOrderService;


@Service
@Transactional(readOnly = true)
public class WishContractService {

	@Autowired
	private TLoanContractService tLoanContractService;
	
	
	@Autowired
	private InteractionService interactionService;
	
	@Autowired
	private WishOrderService wishOrderService;
	
	@Autowired
	private TRepayPlanService tRepayPlanService;
	 /**
     * 通过业务ID来单独同步选定业务的状态给到易联
     * @param id 业务ID
     * @return -1:ID为空或异常；0:没有该业务；1:成功通知；2:已经通知易联
     */
	@Transactional(readOnly = false)
    public int singleInform(String id){
//    	int result = -1;
//    	if(StringUtils.isBlank(id)){
//    		return result;
//    	}
    	
    	//return result;
    	return inform(id, false);
    }
    
    
	 /**
     * 通过业务ID来单独同步选定业务的状态给到易联
     * @param id 业务ID
     * @param forceInform 是否强制通知？true:是；false:否
     * @return  -1:ID为空或异常；0:没有该业务；1:成功通知；2:已经通知易联；4:通知失败；5:该状态不需要通知到易联
     */
	@Transactional(readOnly = false)
    public int inform(String id, boolean forceInform){
    	int result = -1;
    	if(StringUtils.isBlank(id)){
    		return result;
    	}

    	System.out.println("tLoanContractService======================="+tLoanContractService);
    	TLoanContract loanContract = tLoanContractService.get(id);
    	if(loanContract != null && StringUtils.isNotBlank(loanContract.getStatus())){
    		boolean hasContent = false;
    		JSONObject reqBody = new JSONObject();
    		if(forceInform || StringUtils.isBlank(loanContract.getWishStatus()) || !loanContract.getStatus().equals(loanContract.getWishStatus())){
	    		if(Cons.LoanContractStatus.TO_REVIEW.equals(loanContract.getStatus())){//1新申请
	    			reqBody.put("newApply", singleContentToJson(loanContract, "1"));
	    			hasContent = true;
	    		}else if(Cons.LoanContractStatus.TO_APPROVE.equals(loanContract.getStatus())){//2审核中
	    			reqBody.put("applying", singleContentToJson(loanContract, "2"));
	    			hasContent = true;
	    		}else if(Cons.LoanContractStatus.TO_LOAN.equals(loanContract.getStatus())){//3审核通过（未放款
	    			reqBody.put("passed", singleContentToJson(loanContract, "3"));
	    			hasContent = true;
	    		}else if(Cons.LoanContractStatus.TO_SUSPENSION.equals(loanContract.getStatus())){//4审核不通过（已终止）
	    			reqBody.put("unpassed", singleContentToJson(loanContract, "4"));
	    			hasContent = true;
	    		}else if(Cons.LoanContractStatus.UN_CLEARED.equals(loanContract.getStatus())){//5已放款
	    			reqBody.put("loaned", singleContentToJson(loanContract, "5"));
	    			hasContent = true;
	    		}else if(Cons.LoanContractStatus.OVERDUE.equals(loanContract.getStatus())){//6已逾期
	    			reqBody.put("overdue", singleContentToJson(loanContract, "6"));
	    			hasContent = true;
	    		}
	    		if(hasContent){
	    			PayecoBackParams payecoBackParams = interactionService.getPayecoRequestByParams("busiResultNotify", reqBody);
	    			if(payecoBackParams != null && payecoBackParams.getHeader() != null && payecoBackParams.getHeader().getResCode() != null && "0000".equals(payecoBackParams.getHeader().getResCode())){
	    				loanContract.setWishStatus(loanContract.getStatus());
	        			tLoanContractService.updateWishStatus(loanContract);
	        			result = 1;
	    			}else{
	    				result = 4;
	    			}
	    		}else{
	    			result = 5;
	    		}
    		}else{
    			result = 2;
    		}
    	}else{
    		result = 0;
    	}
    	
    	return result;
    }
    
	/**
     * 通过业务ID来单独同步选定业务的状态给到易联
     * @param id 业务ID
     * @param type：通知状态
     * @return  -1:ID为空或异常；0:没有该业务；1:成功通知；2:已经通知易联；4:通知失败；5:该状态不需要通知到易联
     */
	@Transactional(readOnly = false)
    public int informByStatus(String id, String status){
    	int result = -1;
    	if(StringUtils.isAnyBlank(id, status)){
    		return result;
    	}

    	System.out.println("tLoanContractService=====" + status + "==============="+tLoanContractService);
    	TLoanContract loanContract = tLoanContractService.get(id);
    	if(loanContract != null && StringUtils.isNotBlank(loanContract.getStatus())){
    		boolean hasContent = false;
    		JSONObject reqBody = new JSONObject();
    		
    		if("1".equals(status)){//1新申请
    			reqBody.put("newApply", singleContentToJson(loanContract, "1"));
    			hasContent = true;
    		}else if("2".equals(status)){//2审核中
    			reqBody.put("applying", singleContentToJson(loanContract, "2"));
    			hasContent = true;
    		}else if("3".equals(status)){//3审核通过（未放款
    			reqBody.put("passed", singleContentToJson(loanContract, "3"));
    			hasContent = true;
    		}else if("4".equals(status)){//4审核不通过（已终止）
    			reqBody.put("unpassed", singleContentToJson(loanContract, "4"));
    			hasContent = true;
    		}else if("5".equals(status)){//5已放款
    			reqBody.put("loaned", singleContentToJson(loanContract, "5"));
    			hasContent = true;
    		}else if("6".equals(status)){//6已逾期
    			reqBody.put("overdue", singleContentToJson(loanContract, "6"));
    			hasContent = true;
    		}else if("7".equals(status)){//7审核通过锁定失败
    			reqBody.put("lockfail", singleContentToJson(loanContract, "7"));
    			hasContent = true;
    		}
    		if(hasContent){
    			PayecoBackParams payecoBackParams = interactionService.getPayecoRequestByParams("busiResultNotify", reqBody);
    			if(payecoBackParams != null && payecoBackParams.getHeader() != null && payecoBackParams.getHeader().getResCode() != null && "0000".equals(payecoBackParams.getHeader().getResCode())){
    				loanContract.setWishStatus(loanContract.getStatus());
        			tLoanContractService.updateWishStatus(loanContract);
        			result = 1;
    			}else{
    				result = 4;
    			}
    		}else{
    			result = 5;
    		}
    	}else{
    		result = 0;
    	}
    	
    	return result;
    }
    
    /**
     * 单独一个实体组装json中Body内各个状态值内容
     * @param loanContract
     * @param type
     * @return
     */
	@Transactional(readOnly = false)
    private JSONObject singleContentToJson(TLoanContract loanContract, String type){
    	if(loanContract == null || StringUtils.isBlank(type)){
    		return null;
    	}
    	JSONObject jsonContent = new JSONObject();
    	
    	JSONArray busiList=new JSONArray();
    	
		JSONObject ob=new JSONObject();
		BigDecimal hundred = new BigDecimal("100");
		ob.put("userId",loanContract.getCustomerId());
		ob.put("pvdUid",loanContract.getCustomerId());
		ob.put("loanBusinessId",loanContract.getId());
		//ob.put("loanStatus",tLt.getStatus());
		ob.put("busiSbTime",DateUtils.formatDate(loanContract.getApplyDate(), "yyyyMMddHHmmss"));
		//ob.put("busiSbAmount",loanContract.getLoanAmount());
		ob.put("busiSbAmount",new BigDecimal(loanContract.getLoanAmount()).multiply(hundred).setScale(0, BigDecimal.ROUND_HALF_UP));
		ob.put("fee",new BigDecimal(loanContract.getWishCharge()).multiply(hundred).setScale(0, BigDecimal.ROUND_HALF_UP));
		ob.put("bankCard",loanContract.getGatheringNumber());
		ob.put("bankName",loanContract.getGatheringName());
		BigDecimal busiAtAmount=new BigDecimal(loanContract.getLoanAmount()).subtract(new BigDecimal(loanContract.getWishCharge()));
		ob.put("busiAtAmount",busiAtAmount.multiply(hundred).setScale(0, BigDecimal.ROUND_HALF_UP));
		if(loanContract.getPayPrincipalDate() != null){
			String lastRepayDate = DateUtils.formatDate(loanContract.getPayPrincipalDate(),"yyyyMMdd");
			ob.put("lastRepayDate",lastRepayDate);
		}else{
			ob.put("lastRepayDate","");
		}
		busiList.add(ob);
    	
    	jsonContent.put("loanStatus", type);
    	jsonContent.put("busiList", busiList);
    	
    	return jsonContent;
    }
	
	 /**
     * 根据业务id  解锁订单------------业务审核不通过时调用
     * @param contractId 业务ID
     * @param 
     * @return  
     */
	@Transactional(readOnly = false)
    public void unlockOrder(String contractId){
    	TLoanContract loanContract = tLoanContractService.get(contractId);
    	if(loanContract != null && StringUtils.isNotBlank(loanContract.getStatus()) && loanContract.getStatus().equals(Cons.LoanContractStatus.TO_SUSPENSION)){
    		WishOrder wishOrder=new WishOrder();
    		wishOrder.setLoanContractId(loanContract.getId());
    		wishOrderService.unlock(wishOrder);//解锁订单
    		
    		TRepayPlan tRepayPlan=new TRepayPlan();//作废还款计划
    		tRepayPlan.setLoanContractId(loanContract.getId());
    		tRepayPlanService.inValidBatch(tRepayPlan);
    		
    	}
    }
    
}

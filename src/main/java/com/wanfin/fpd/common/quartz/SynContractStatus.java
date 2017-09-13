package com.wanfin.fpd.common.quartz;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.wanfin.fpd.common.config.Cons;
import com.wanfin.fpd.common.utils.DateUtils;
import com.wanfin.fpd.modules.api.wiss.entity.PayecoBackParams;
import com.wanfin.fpd.modules.api.wiss.service.InteractionService;
import com.wanfin.fpd.modules.contract.entity.TLoanContract;
import com.wanfin.fpd.modules.contract.service.TLoanContractService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 7.1 SynContractStatusTrigger   定时
 * 1、向wish发送审核结果，wish返回锁定确认
 * 2、易联向贷款供应商发送wish锁定确认
 * @author user
 *
 */
public class SynContractStatus {
	
	//1	新申请
	//2	审核中
	//4	待锁定(审核通过)
	//5	锁定待放款
	//6	未结清(已放款)
	//7	已结清
	//8	已逾期
	//9	已终止
	@Autowired
	private TLoanContractService tLoanContractService;
	
	
	@Autowired
	private InteractionService interactionService;
	/** 
     * 定时任务，执行方法 
     * */  
    public void execute(){
    	System.out.println("SynContractStatus=====start=============================");
    	
    	String tardeId = "busiResultNotify";
    	TLoanContract tLoanContract=new TLoanContract();
    	
    	JSONObject reqBody = new JSONObject();
    	
    	//tLoanContract.setStatus(Cons.LoanContractStatus.TO_LOAN);
		//List<TLoanContract> list=tLoanContractService.findBlockContractLists(tLoanContract);
	//	List<TLoanContract> list=tLoanContractService.findAllList(tLoanContract);
    	boolean hasContent = false;
    	
		//1新申请
		tLoanContract.setStatus(Cons.LoanContractStatus.TO_REVIEW);//1
		List<TLoanContract> listNew=tLoanContractService.findBlockContractLists(tLoanContract);
		
		JSONObject jsonNew = listContentToJson(listNew, "1");
		if(jsonNew != null){
			reqBody.put("newApply", jsonNew);
			hasContent = true;
		}
		
		//2审核中
		tLoanContract.setStatus(Cons.LoanContractStatus.TO_APPROVE);//2
		List<TLoanContract> listAudit=tLoanContractService.findBlockContractLists(tLoanContract);

		JSONObject jsonApplying = listContentToJson(listAudit, "2");
		if(jsonApplying != null){
			reqBody.put("applying", jsonApplying);
			hasContent = true;
		}
		
		//3审核通过（未放款
		tLoanContract.setStatus(Cons.LoanContractStatus.TO_LOAN);//5
		List<TLoanContract> listAuditPass=tLoanContractService.findBlockContractLists(tLoanContract);

		JSONObject jsonPassed = listContentToJson(listAuditPass, "3");
		if(jsonPassed != null){
			reqBody.put("passed", jsonPassed);
			hasContent = true;
		}
		
		//4审核不通过（已终止）
		tLoanContract.setStatus(Cons.LoanContractStatus.TO_SUSPENSION);//0
		List<TLoanContract> listNotPass=tLoanContractService.findBlockContractLists(tLoanContract);

		JSONObject jsonUnpass = listContentToJson(listNotPass, "4");
		if(jsonUnpass != null){
			reqBody.put("unpassed", jsonUnpass);
			hasContent = true;
		}
		
		//5已放款
		tLoanContract.setStatus(Cons.LoanContractStatus.UN_CLEARED);//6
		List<TLoanContract> listBeenLending=tLoanContractService.findBlockContractLists(tLoanContract);

		JSONObject jsonLoaned = listContentToJson(listBeenLending, "5");
		if(jsonLoaned != null){
			reqBody.put("loaned", jsonLoaned);
			hasContent = true;
		}
		
		//6已逾期
		tLoanContract.setStatus(Cons.LoanContractStatus.OVERDUE);//8
		List<TLoanContract> listOverDue=tLoanContractService.findBlockContractLists(tLoanContract);

		JSONObject jsonOverDue = listContentToJson(listOverDue, "6");
		if(jsonOverDue != null){
			reqBody.put("overdue", jsonOverDue);
			hasContent = true;
		}
		
		//7审核通过 锁定失败
		//tLoanContract.setStatus(Cons.LoanContractStatus.TO_LOAN);
		//List<TLoanContract> list=tLoanContractService.findBlockContractLists(tLoanContract);

		if(hasContent){
			PayecoBackParams payecoBackParams = interactionService.getPayecoRequestByParams(tardeId, reqBody);
			if(payecoBackParams != null && payecoBackParams.getHeader() != null && payecoBackParams.getHeader().getResCode() != null && "0000".equals(payecoBackParams.getHeader().getResCode())){
				updateLoanContractState(listNew);
				updateLoanContractState(listAudit);
				updateLoanContractState(listAuditPass);
				updateLoanContractState(listNotPass);
				updateLoanContractState(listBeenLending);
				updateLoanContractState(listOverDue);
			}
		}else{
			System.out.println("==>无状态信息同步！");
		}
		hasContent = false;
		
		/*
		JSONArray busiList=new JSONArray();
    	for(TLoanContract tLt:list){
    		JSONObject ob=new JSONObject();
    		ob.put("userId",tLt.getCustomerId());
    		ob.put("pvdUid",tLt.getCustomerId());
    		ob.put("loanBusinessId",tLt.getId());
    		ob.put("loanStatus",tLt.getStatus());
    		ob.put("busiSbTime",tLt.getApplyDate());
    		ob.put("busiSbAmount",tLt.getLoanAmount());
    		ob.put("fee",tLt.getWishCharge());
    		ob.put("bankCard",tLt.getGatheringNumber());
    		ob.put("bankName",tLt.getGatheringName());
			BigDecimal busiAtAmount=new BigDecimal(tLt.getLoanAmount()).subtract(new BigDecimal(tLt.getWishCharge()));
			ob.put("busiAtAmount",busiAtAmount);
			if(tLt.getPayPrincipalDate() != null){
				String lastRepayDate = DateUtils.formatDate(tLt.getPayPrincipalDate(),"yyyyMMdd");
				ob.put("lastRepayDate",lastRepayDate);
			}
			busiList.add(ob);
			
    	}
    	reqBody.put("busiList", busiList);
    	PayecoBackParams payecoBackParams = interactionService.getPayecoRequestByParams(tardeId, reqBody);
	    if(payecoBackParams != null && payecoBackParams.getHeader() != null && payecoBackParams.getHeader().getResCode() != null && "0000".equals(payecoBackParams.getHeader().getResCode())){
	    	for(TLoanContract tc:list){
	    		tc.setWishStatus("1");//待锁定业务已通知易联
	    		tLoanContractService.updateWishStatus(tc);
	    	}
	     }
	     */
	    
	    System.out.println("SynContractStatus=====end=============================");
    }
    
    /**
     * 通过业务ID来单独同步选定业务的状态给到易联
     * @param id 业务ID
     * @return -1:ID为空或异常；0:没有该业务；1:成功通知；2:已经通知易联
     */
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
     * 通知易联后更新通知状态
     * @param listLoanContract
     */
    private void updateLoanContractState(List<TLoanContract> listLoanContract){
    	if(listLoanContract != null && listLoanContract.size()>0){
    		for(TLoanContract loanContract:listLoanContract){
    			loanContract.setWishStatus(loanContract.getStatus());
    			tLoanContractService.updateWishStatus(loanContract);
    		}
    	}
    }
    /**
     * 组装json中Body内各个状态值内容
     * @param list
     * @param type
     * @return
     */
    private JSONObject listContentToJson(List<TLoanContract> list, String type){
    	if(list == null || list.size()<1){
    		return null;
    	}
    	JSONObject jsonContent = new JSONObject();
    	
    	JSONArray busiList=new JSONArray();
    	BigDecimal hundred = new BigDecimal("100");
    	for(TLoanContract tLt:list){
    		JSONObject ob=new JSONObject();
    		ob.put("userId",tLt.getCustomerId());
    		ob.put("pvdUid",tLt.getCustomerId());
    		ob.put("loanBusinessId",tLt.getId());
    		//ob.put("loanStatus",tLt.getStatus());
    		ob.put("busiSbTime",DateUtils.formatDate(tLt.getApplyDate(), "yyyyMMddHHmmss"));
    		ob.put("busiSbAmount",new BigDecimal(tLt.getLoanAmount()).multiply(hundred).setScale(0, BigDecimal.ROUND_HALF_UP));
    		ob.put("fee",new BigDecimal(tLt.getWishCharge()).multiply(hundred).setScale(0, BigDecimal.ROUND_HALF_UP));
    		ob.put("bankCard",tLt.getGatheringNumber());
    		ob.put("bankName",tLt.getGatheringName());
			BigDecimal busiAtAmount=new BigDecimal(tLt.getLoanAmount()).subtract(new BigDecimal(tLt.getWishCharge()));
			ob.put("busiAtAmount",busiAtAmount.multiply(hundred).setScale(0, BigDecimal.ROUND_HALF_UP));
			if(tLt.getPayPrincipalDate() != null){
				String lastRepayDate = DateUtils.formatDate(tLt.getPayPrincipalDate(),"yyyyMMdd");
				ob.put("lastRepayDate",lastRepayDate);
			}else{
				ob.put("lastRepayDate","");
			}
			busiList.add(ob);
    	}
    	jsonContent.put("loanStatus", type);
    	jsonContent.put("busiList", busiList);
    	
    	return jsonContent;
    }
    
    /**
     * 单独一个实体组装json中Body内各个状态值内容
     * @param loanContract
     * @param type
     * @return
     */
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
}

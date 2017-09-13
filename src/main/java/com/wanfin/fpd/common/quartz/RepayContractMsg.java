package com.wanfin.fpd.common.quartz;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONArray;
import com.wanfin.fpd.common.config.Cons;
import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.utils.DateUtils;
import com.wanfin.fpd.common.utils.StringUtils;
import com.wanfin.fpd.modules.api.wiss.entity.InformOrder;
import com.wanfin.fpd.modules.api.wiss.entity.PayecoBackParams;
import com.wanfin.fpd.modules.api.wiss.service.InformOrderService;
import com.wanfin.fpd.modules.api.wiss.service.InteractionService;
import com.wanfin.fpd.modules.contract.dao.TLoanContractDao;
import com.wanfin.fpd.modules.contract.entity.TLoanContract;
import com.wanfin.fpd.modules.contract.service.TLoanContractService;
import com.wanfin.fpd.modules.sys.utils.UserUtils;
import com.wanfin.fpd.modules.wish.merchant.entity.Merchant;
import com.wanfin.fpd.modules.wish.merchant.service.MerchantService;

import net.sf.json.JSONObject;

/**
 * 8.2 打款通知 定时  废弃
 * @author user
 *
 */
public class RepayContractMsg {
	
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
    	String tardeId = "dkNotify";
    	TLoanContract tLoanContract=new TLoanContract();
    	///tLoanContract.setStatus(Cons.LoanContractStatus.TO_LOAN_APPROVAL);
		List<TLoanContract> list=tLoanContractService.findRepayContractLists(tLoanContract);
		
		
		JSONObject reqBody = new JSONObject();
		JSONArray busiList=new JSONArray();
    	for(TLoanContract tLt:list){
    		JSONObject ob=new JSONObject();
    		ob.put("userId",tLt.getCustomerId());
    		ob.put("pvdUid",tLt.getCustomerId());
    		ob.put("loanBusinessId",tLt.getId());
    		if(tLt.getStatus().equals(Cons.LoanContractStatus.UN_CLEARED)){
    			ob.put("cashBackStatus","2");
    		}else if(tLt.getStatus().equals(Cons.LoanContractStatus.CLEARED)){
    			ob.put("cashBackStatus","3");
    		}
			busiList.add(ob);
    	}
    	reqBody.put("busiList", busiList);
    	PayecoBackParams payecoBackParams = interactionService.getPayecoRequestByParams(tardeId, reqBody);
	    if(payecoBackParams != null && payecoBackParams.getHeader() != null && payecoBackParams.getHeader().getResCode() != null && "0000".equals(payecoBackParams.getHeader().getResCode())){
	    	for(TLoanContract tc:list){
	    		tc.setWishStatus("2");//打款通知业务已通知易联
	    		tLoanContractService.updateWishStatus(tc);
	    	}
	     }
	    
    }
}

package com.wanfin.fpd.common.quartz;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;

import com.wanfin.fpd.common.config.Cons;
import com.wanfin.fpd.common.utils.StringUtils;
import com.wanfin.fpd.modules.api.wiss.entity.PayecoBackParams;
import com.wanfin.fpd.modules.api.wiss.service.InteractionService;
import com.wanfin.fpd.modules.company.entity.TCompany;
import com.wanfin.fpd.modules.company.service.TCompanyService;
import com.wanfin.fpd.modules.contract.entity.TLoanContract;
import com.wanfin.fpd.modules.contract.service.TLoanContractService;
import com.wanfin.fpd.modules.employee.entity.TEmployee;
import com.wanfin.fpd.modules.employee.service.TEmployeeService;
import com.wanfin.fpd.modules.wish.merchant.entity.Merchant;
import com.wanfin.fpd.modules.wish.merchant.service.MerchantService;

import net.sf.json.JSONObject;
/**
 * 4.3 更新商铺信息
 * @author asus
 *
 */
public class MerchantQuartz {
	@Autowired
	private MerchantService merchantService;
	@Autowired
	private InteractionService interactionService;
	@Autowired
	TEmployeeService tEmployeeService;
	@Autowired
	private TCompanyService tCompanyService;
	@Autowired
	private TLoanContractService tLoanContractService;
	
	public void execute(){
		String time = new SimpleDateFormat("MMM d，yyyy KK:mm:ss a",Locale.CHINESE).format(System.currentTimeMillis());  
    	System.out.println("————————————————————————————————————————————————————————————————————————————");
        System.out.println("time:"+time+">>更新商铺信息--开始"); 
        
        Merchant merchant = new Merchant();
        List<Merchant> list = merchantService.findList(merchant);
        for(Merchant tmpMerchant:list){
        	//--组织请求data body
        	String tardeId = "updateMerchant";
            JSONObject reqBody = new JSONObject();
            reqBody.put("userId", tmpMerchant.getUserId());
	        reqBody.put("merchantId", tmpMerchant.getMerchantId());
	        
	        try{
	            PayecoBackParams payecoBackParams = interactionService.getPayecoRequestByParams(tardeId, reqBody);
	            //解析data中的body内容
	            if(payecoBackParams != null && payecoBackParams.getJsonBody() != null && payecoBackParams.getHeader() != null && "0000".equals(payecoBackParams.getHeader().getResCode())){
	            	Merchant newMerchant = (Merchant) JSONObject.toBean(payecoBackParams.getJsonBody(), Merchant.class);
	            	System.out.println("旧数据:"+tmpMerchant.toString());
	            	System.out.println("新数据:"+newMerchant.toString());
	            	
	            	if(newMerchant.verifyDatas(false)){
		            	newMerchant.setId(tmpMerchant.getId());
//		            	if(StringUtils.isBlank(newMerchant.getUserId())){
//		            		newMerchant.setUserId(tmpMerchant.getUserId());
//		            	}
//		            	if(StringUtils.isBlank(newMerchant.getMerchantId())){
//		            		newMerchant.setMerchantId(tmpMerchant.getMerchantId());
//		            	}
		            	merchantService.saveLog(tmpMerchant);//旧数据完整备份
		            	//此处添加处理逻辑信息
		            	newMerchant.setPhone(StringUtils.dealPhone(newMerchant.getPhone()));//处理手机号码
		            	merchantService.save(newMerchant);
		            	
		            	if(StringUtils.isNotBlank(newMerchant.getUserId())){
		            		TEmployee y = new TEmployee();
		        			TCompany c = new TCompany();
		        			
		        			y.setId(newMerchant.getUserId());
		        			TEmployee employee=tEmployeeService.get(y);
		        			
		        			c.setId(newMerchant.getUserId());
		        			TCompany company=tCompanyService.get(c);
		        			
		        			TLoanContract tt=new TLoanContract();
		    				tt.setCustomerId(newMerchant.getUserId());
		    				tt.setStatus(Cons.LoanContractStatus.UN_CLEARED+","+Cons.LoanContractStatus.OVERDUE);
		    				List<TLoanContract> tctList=tLoanContractService.findAllList(tt);
		    				
		    				if(tctList.size()==0){
		    					if(employee!=null){
			        				employee.setName(newMerchant.getAccountOwnerName());
	            					employee.setCardNum(newMerchant.getAccountOwnerNumber());
	            					employee.setGatheringNumber(newMerchant.getAccountNo());
	            					employee.setTelephone(newMerchant.getAccountRelatedPhone());
	            					tEmployeeService.update(employee);
			        			}else if(company!=null){
			        				company.setName(newMerchant.getAccountOwnerName());
	            					company.setCardNum(newMerchant.getAccountOwnerNumber());
	            					company.setGatheringNumber(newMerchant.getAccountNo());
	            					company.setPhone(newMerchant.getAccountRelatedPhone());
	            					tCompanyService.update(company);
			        			}
		    				}
		            	}
		  
	            	}
	            }else{
	            	System.out.println("信息更新失败:" + tmpMerchant.getId() + "==>" + payecoBackParams.getHeader());
	            }
	        }catch(Exception e){
	        	
	        }
        }
        
        time = new SimpleDateFormat("MMM d，yyyy KK:mm:ss a",Locale.CHINESE).format(System.currentTimeMillis());  
        System.out.println("time:"+time+">>更新商铺信息--结束");
    	System.out.println("——————————————————————————————————————————————————————————————————————————1——");
	}
}

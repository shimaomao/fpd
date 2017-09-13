/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 * @Project fpd 
 * @Title TLoanContractApiResource.java
 * @Package com.wanfin.fpd.modules.contract.resource.loancontract
 * @Description [[_合同信息_]]文件 
 * @author Chenh
 * @date 2016年5月12日 下午3:08:57 
 * @version V1.0   
 */
package com.wanfin.fpd.modules.contract.resource.loancontract;

import java.util.List;

import javax.ws.rs.core.UriInfo;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMethod;

import com.wanfin.fpd.common.utils.DateUtils;
import com.wanfin.fpd.core.ApiUriInfo;
import com.wanfin.fpd.core.CollectionResTool;
import com.wanfin.fpd.core.Link;
import com.wanfin.fpd.core.filter.LinkFilter;
import com.wanfin.fpd.modules.contract.entity.TLoanContract;

/**
 * @Description [[_合同信息_]] TLoanContractApiResource资源类
 * @author CHenh
 * @version 2016-06-17
 * @date 2016年6月6日 下午7:24:32 
 */
@SuppressWarnings("unchecked")
public class TLoanContractApiResource extends CollectionResTool<TLoanContract> implements LinkFilter<TLoanContract> {
	private static final long serialVersionUID = 1L;
	
	public TLoanContractApiResource() {
		super();
	}

	public TLoanContractApiResource(TLoanContract entity) {
        super(entity);
    	filterDeal(entity);
    }
	
	public TLoanContractApiResource(UriInfo info, TLoanContract entity) {
		super(info, entity);
		filterDeal(entity);
	}
	
	public TLoanContractApiResource(TLoanContract entity, List<String> fields) {
		super(entity);
    	filterByFields(entity, fields);
	}
	
	public TLoanContractApiResource(UriInfo info, TLoanContract entity, List<String> fields) {
		super(info, entity);
		filterByFields(entity, fields);
	}

	@Override
	public void filter(TLoanContract entity) {
		put("id", entity.getId());
     
        put("remarks", entity.getRemarks());
        if(entity.getCreateBy() != null){
        	put("createBy", entity.getCreateBy().getName());
        }
        if(entity.getUpdateBy() != null){
        	put("updateBy", entity.getCreateBy().getName());
        }
        if(entity.getCreateDate() != null){
        	put("createDate", DateUtils.formatDateTime(entity.getCreateDate()));
        }
        if(entity.getUpdateDate() != null){
            put("updateDate", DateUtils.formatDateTime(entity.getUpdateDate()));
        }
	}
	
	@Override
	public void filterChange(TLoanContract entity) {
		filter(entity);
		
		put("contractNumber", entity.getContractNumber());
		put("customerName", entity.getCustomerName());
		put("isExtend", entity.getIsExtend());
		put("loanAmount", entity.getLoanAmount());
		put("loanDate", entity.getLoanDate());
		put("loanPeriod", entity.getLoanPeriod());
		put("loanRate", entity.getLoanRate());
		put("loanType", entity.getLoanRateType());
		put("periodType", entity.getPeriodType());
		put("payDay", entity.getPayDay());
		put("signDate", entity.getSignDate());
		put("productId", entity.getProductId());
		put("productname", entity.getProductname());
		put("wtypeId", entity.getWtypeId());
		put("status", entity.getStatus());
	}
	
	@Override
	public void filterQuery(TLoanContract entity) {
		filter(entity);
		
		put("contractNumber", entity.getContractNumber());
		put("customerName", entity.getCustomerName());
		put("isExtend", entity.getIsExtend());
		put("loanAmount", entity.getLoanAmount());
		put("loanDate", entity.getLoanDate());
		put("loanPeriod", entity.getLoanPeriod());
		put("loanRate", entity.getLoanRate());
		put("loanType", entity.getLoanRateType());
		put("periodType", entity.getPeriodType());
		put("payDay", entity.getPayDay());
		put("signDate", entity.getSignDate());
		put("productId", entity.getProductId());
		put("productname", entity.getProductname());
		put("wtypeId", entity.getWtypeId());
		put("status", entity.getStatus());
	}
	
	@Override
	public void filterByFields(TLoanContract entity, List<String> fields) {
		JSONObject jentity =  new JSONObject(entity);
    	for (String key : fields) {
            try {
            	put(key, jentity.get(key));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}
	
	/** 公共方法 ***********************************************************************************/
	@Override
	public Link filterApiResourceByFields(TLoanContract entity, TLoanContract enty) {
		if((entity != null) && (entity.getFields() != null) && (entity.getFields().size() > 0)){
			return new TLoanContractApiResource(enty, entity.getFields());
		}else{
			return new TLoanContractApiResource(enty);
		}
	}
	
	@Override
	public Link filterApiResourceByFields(ApiUriInfo info, TLoanContract entity, TLoanContract enty) {
		if((entity != null) && (entity.getFields() != null) && (entity.getFields().size() > 0)){
			return new TLoanContractApiResource(info, enty, entity.getFields());
		}else{
			return new TLoanContractApiResource(info, enty);
		}
	}

	@Override
	public void filterDeal(TLoanContract entity) {
		if((entity != null) && (entity.getOper() != null)){
        	if((entity.getOper().equals(RequestMethod.POST)) || (entity.getOper().equals(RequestMethod.PUT)) || (entity.getOper().equals(RequestMethod.PATCH))){
	        	filterChange(entity);
	        }else if((entity.getOper().equals(RequestMethod.GET))){
	        	filterQuery(entity);
	        }
        }else{
        	filter(entity);
        }
	}
}
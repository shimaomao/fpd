/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 * @Project fpd 
 * @Title TEmployeeApiResource.java
 * @Package com.wanfin.fpd.modules.employee.resource
 * @Description [[_个人客户_]]文件 
 * @author Chenh
 * @date 2016年5月12日 下午3:08:57 
 * @version V1.0   
 */
package com.wanfin.fpd.modules.employee.resource;

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
import com.wanfin.fpd.modules.employee.entity.TEmployee;

/**
 * @Description [[_个人客户_]] TEmployeeApiResource资源类
 * @author Chenh
 * @version 2016-06-24
 * @date 2016年6月6日 下午7:24:32 
 */
@SuppressWarnings("unchecked")
public class TEmployeeApiResource extends CollectionResTool<TEmployee> implements LinkFilter<TEmployee> {
	private static final long serialVersionUID = 1L;
	
	public TEmployeeApiResource() {
		super();
	}

	public TEmployeeApiResource(TEmployee entity) {
        super(entity);
	    filterDeal(entity);
    }
	
	public TEmployeeApiResource(UriInfo info, TEmployee entity) {
		super(info, entity);
    	filterDeal(entity);
	}
	
	public TEmployeeApiResource(TEmployee entity, List<String> fields) {
		super(entity);
    	filterByFields(entity, fields);
	}
	
	public TEmployeeApiResource(UriInfo info, TEmployee entity, List<String> fields) {
		super(info, entity);
		filterByFields(entity, fields);
	}

	@Override
	public void filter(TEmployee entity) {
		put("id", entity.getId());
		put("name",entity.getName());
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
        put("cardNum", entity.getCardNum());
        put("monthIncome",entity.getMonthIncome());
        put("yearsOfWorking",entity.getYearsOfWorking());
        put("evaluate",entity.getEvaluate());
        put("position",entity.getPosition());
	}
	
	@Override
	public void filterChange(TEmployee entity) {
		filter(entity);
	}
	
	@Override
	public void filterQuery(TEmployee entity) {
		filter(entity);
	}
	
	@Override
	public void filterByFields(TEmployee entity, List<String> fields) {
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
	public Link filterApiResourceByFields(TEmployee entity, TEmployee enty) {
		if((entity != null) && (entity.getFields() != null) && (entity.getFields().size() > 0)){
			return new TEmployeeApiResource(enty, entity.getFields());
		}else{
			return new TEmployeeApiResource(enty);
		}
	}
	
	@Override
	public Link filterApiResourceByFields(ApiUriInfo info, TEmployee entity, TEmployee enty) {
		if((entity != null) && (entity.getFields() != null) && (entity.getFields().size() > 0)){
			return new TEmployeeApiResource(info, enty, entity.getFields());
		}else{
			return new TEmployeeApiResource(info, enty);
		}
	}

	@Override
	public void filterDeal(TEmployee entity) {
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
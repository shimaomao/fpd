/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 * @Project fpd 
 * @Title TCompanyApiResource.java
 * @Package com.wanfin.fpd.modules.company.resource
 * @Description [[_企业客户_]]文件 
 * @author Chenh
 * @date 2016年5月12日 下午3:08:57 
 * @version V1.0   
 */
package com.wanfin.fpd.modules.company.resource;

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
import com.wanfin.fpd.modules.company.entity.TCompany;

/**
 * @Description [[_企业客户_]] TCompanyApiResource资源类
 * @author Chenh
 * @version 2016-06-23
 * @date 2016年6月6日 下午7:24:32 
 */
@SuppressWarnings("unchecked")
public class TCompanyApiResource extends CollectionResTool<TCompany> implements LinkFilter<TCompany> {
	private static final long serialVersionUID = 1L;
	
	public TCompanyApiResource() {
		super();
	}

	public TCompanyApiResource(TCompany entity) {
        super(entity);
        filterDeal(entity);
    }
	
	public TCompanyApiResource(UriInfo info, TCompany entity) {
		super(info, entity);
    	filterDeal(entity);
	}
	
	public TCompanyApiResource(TCompany entity, List<String> fields) {
		super(entity);
    	filterByFields(entity, fields);
	}
	
	public TCompanyApiResource(UriInfo info, TCompany entity, List<String> fields) {
		super(info, entity);
		filterByFields(entity, fields);
	}

	@Override
	public void filter(TCompany entity) {
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
	public void filterChange(TCompany entity) {
		filter(entity);
	}

	@Override
	public void filterQuery(TCompany entity) {
		filter(entity);
	}
	
	@Override
	public void filterByFields(TCompany entity, List<String> fields) {
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
	public Link filterApiResourceByFields(TCompany entity, TCompany enty) {
		if((entity != null) && (entity.getFields() != null) && (entity.getFields().size() > 0)){
			return new TCompanyApiResource(enty, entity.getFields());
		}else{
			return new TCompanyApiResource(enty);
		}
	}
	
	@Override
	public Link filterApiResourceByFields(ApiUriInfo info, TCompany entity, TCompany enty) {
		if((entity != null) && (entity.getFields() != null) && (entity.getFields().size() > 0)){
			return new TCompanyApiResource(info, enty, entity.getFields());
		}else{
			return new TCompanyApiResource(info, enty);
		}
	}

	@Override
	public void filterDeal(TCompany entity) {
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
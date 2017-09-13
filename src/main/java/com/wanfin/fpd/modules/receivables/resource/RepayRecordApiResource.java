/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 * @Project fpd 
 * @Title RepayRecordApiResource.java
 * @Package com.wanfin.fpd.modules.receivables.resource
 * @Description [[_还款记录_]]文件 
 * @author Chenh
 * @date 2016年5月12日 下午3:08:57 
 * @version V1.0   
 */
package com.wanfin.fpd.modules.receivables.resource;

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
import com.wanfin.fpd.modules.receivables.entity.RepayRecord;

/**
 * @Description [[_还款记录_]] RepayRecordApiResource资源类
 * @author Chenh
 * @version 2016-07-14
 * @date 2016年6月6日 下午7:24:32 
 */
@SuppressWarnings("unchecked")
public class RepayRecordApiResource extends CollectionResTool<RepayRecord> implements LinkFilter<RepayRecord> {
	private static final long serialVersionUID = 1L;
	
	public RepayRecordApiResource() {
		super();
	}

	public RepayRecordApiResource(RepayRecord entity) {
        super(entity);
        filterDeal(entity);
    }
	
	public RepayRecordApiResource(UriInfo info, RepayRecord entity) {
		super(info, entity);
		filterDeal(entity);
	}
	
	public RepayRecordApiResource(RepayRecord entity, List<String> fields) {
		super(entity);
    	filterByFields(entity, fields);
	}
	
	public RepayRecordApiResource(UriInfo info, RepayRecord entity, List<String> fields) {
		super(info, entity);
		filterByFields(entity, fields);
	}

	@Override
	public void filter(RepayRecord entity) {
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
	public void filterChange(RepayRecord entity) {
		filter(entity);
	}
	
	@Override
	public void filterQuery(RepayRecord entity) {
		filter(entity);
	}
	
	@Override
	public void filterByFields(RepayRecord entity, List<String> fields) {
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
	public Link filterApiResourceByFields(RepayRecord entity, RepayRecord enty) {
		if((entity != null) && (entity.getFields() != null) && (entity.getFields().size() > 0)){
			return new RepayRecordApiResource(enty, entity.getFields());
		}else{
			return new RepayRecordApiResource(enty);
		}
	}
	
	@Override
	public Link filterApiResourceByFields(ApiUriInfo info, RepayRecord entity, RepayRecord enty) {
		if((entity != null) && (entity.getFields() != null) && (entity.getFields().size() > 0)){
			return new RepayRecordApiResource(info, enty, entity.getFields());
		}else{
			return new RepayRecordApiResource(info, enty);
		}
	}

	@Override
	public void filterDeal(RepayRecord entity) {
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
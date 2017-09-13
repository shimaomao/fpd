/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 * @Project fpd 
 * @Title OfficeApiResource.java
 * @Package com.wanfin.fpd.modules.sys.resource
 * @Description [[_机构_]]文件 
 * @author Chenh
 * @date 2016年5月12日 下午3:08:57 
 * @version V1.0   
 */
package com.wanfin.fpd.modules.sys.resource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.UriInfo;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMethod;

import com.wanfin.fpd.common.utils.DateUtils;
import com.wanfin.fpd.core.ApiUriInfo;
import com.wanfin.fpd.core.CollectionResTool;
import com.wanfin.fpd.core.Link;
import com.wanfin.fpd.core.filter.LinkFilter;
import com.wanfin.fpd.modules.sys.entity.Area;
import com.wanfin.fpd.modules.sys.entity.Office;

/**
 * @Description [[_机构_]] OfficeApiResource资源类
 * @author Chenh
 * @version 2016-06-24
 * @date 2016年6月6日 下午7:24:32 
 */
@SuppressWarnings("unchecked")
public class OfficeApiResource extends CollectionResTool<Office> implements LinkFilter<Office> {
	private static final long serialVersionUID = 1L;
	
	public OfficeApiResource() {
		super();
	}

	public OfficeApiResource(Office entity) {
        super(entity);
        filterDeal(entity);
    }
	
	public OfficeApiResource(UriInfo info, Office entity) {
		super(info, entity);
		filterDeal(entity);
	}
	
	public OfficeApiResource(Office entity, List<String> fields) {
		super(entity);
    	filterByFields(entity, fields);
	}
	
	public OfficeApiResource(UriInfo info, Office entity, List<String> fields) {
		super(info, entity);
		filterByFields(entity, fields);
	}

	@Override
	public void filter(Office entity) {
		put("id", entity.getId());
		
        put("name", entity.getName());
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
	public void filterChange(Office entity) {
        filter(entity);
        put("grade", entity.getGrade());
	}
	
	@Override
	public void filterQuery(Office entity) {
        filter(entity);
        
        put("email", entity.getEmail());
        put("grade", entity.getGrade());
        
		if(entity.getArea() != null){
			Area relEntity = entity.getArea();
			Map<String, Object> relMap = new HashMap<String, Object>();
			relMap.put("id", relEntity.getId());
			relMap.put("name", relEntity.getName());
			put("area", relMap);
		}
		
		if(entity.getParent() != null){
			Office relEntity = entity.getParent();
			Map<String, Object> relMap = new HashMap<String, Object>();
			relMap.put("id", relEntity.getId());
			relMap.put("name", relEntity.getName());
			put("parent", relMap);
		}
	}
	
	@Override
	public void filterByFields(Office entity, List<String> fields) {
		JSONObject jentity =  new JSONObject(entity);
		/**
		 * 过滤属性
		 */
		fields.removeAll(filterIgnore());
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
	public Link filterApiResourceByFields(Office entity, Office enty) {
		if((entity != null) && (entity.getFields() != null) && (entity.getFields().size() > 0)){
			return new OfficeApiResource(enty, entity.getFields());
		}else{
			return new OfficeApiResource(enty);
		}
	}
	
	@Override
	public Link filterApiResourceByFields(ApiUriInfo info, Office entity, Office enty) {
		if((entity != null) && (entity.getFields() != null) && (entity.getFields().size() > 0)){
			return new OfficeApiResource(info, enty, entity.getFields());
		}else{
			return new OfficeApiResource(info, enty);
		}
	}
	
	@Override
	public void filterDeal(Office entity) {
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
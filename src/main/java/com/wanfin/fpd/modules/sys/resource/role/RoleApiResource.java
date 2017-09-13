/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 * @Project fpd 
 * @Title RoleApiResource.java
 * @Package com.wanfin.fpd.modules.sys.resource.role
 * @Description [[_角色_]]文件 
 * @author Chenh
 * @date 2016年5月12日 下午3:08:57 
 * @version V1.0   
 */
package com.wanfin.fpd.modules.sys.resource.role;

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
import com.wanfin.fpd.modules.sys.entity.Office;
import com.wanfin.fpd.modules.sys.entity.Role;

/**
 * @Description [[_角色_]] RoleApiResource资源类
 * @author Chenh
 * @version 2016-06-16
 * @date 2016年6月6日 下午7:24:32 
 */
@SuppressWarnings("unchecked")
public class RoleApiResource extends CollectionResTool<Role> implements LinkFilter<Role> {
	private static final long serialVersionUID = 1L;
	
	public RoleApiResource() {
		super();
	}

	public RoleApiResource(Role entity) {
        super(entity);
		filterDeal(entity);
    }
	
	public RoleApiResource(UriInfo info, Role entity) {
		super(info, entity);
		filterDeal(entity);
	}
	
	public RoleApiResource(Role entity, List<String> fields) {
		super(entity);
    	filterByFields(entity, fields);
	}
	
	public RoleApiResource(UriInfo info, Role entity, List<String> fields) {
		super(info, entity);
		filterByFields(entity, fields);
	}

	@Override
	public void filter(Role entity) {
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
	public void filterChange(Role entity) {
        filter(entity);

        put("name", entity.getName());
		put("enname", entity.getEnname());
		put("roleType", entity.getRoleType());
		put("dataScope", entity.getDataScope());
		
		if(entity.getOffice() != null){
			Office relEntity = entity.getOffice();
			Map<String, Object> relMap = new HashMap<String, Object>();
			relMap.put("id", relEntity.getId());
			put("office", relMap);
		}
	}

	@Override
	public void filterQuery(Role entity) {
        filter(entity);
        
		put("name", entity.getName());
		put("enname", entity.getEnname());
		put("roleType", entity.getRoleType());
		put("dataScope", entity.getDataScope());
		
		if(entity.getOffice() != null){
			Office relEntity = entity.getOffice();
			Map<String, Object> relMap = new HashMap<String, Object>();
			relMap.put("id", relEntity.getId());
			put("office", relMap);
		}
	}
	
	@Override
	public void filterByFields(Role entity, List<String> fields) {
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
	public Link filterApiResourceByFields(Role entity, Role enty) {
		if((entity != null) && (entity.getFields() != null) && (entity.getFields().size() > 0)){
			return new RoleApiResource(enty, entity.getFields());
		}else{
			return new RoleApiResource(enty);
		}
	}
	
	@Override
	public Link filterApiResourceByFields(ApiUriInfo info, Role entity, Role enty) {
		if((entity != null) && (entity.getFields() != null) && (entity.getFields().size() > 0)){
			return new RoleApiResource(info, enty, entity.getFields());
		}else{
			return new RoleApiResource(info, enty);
		}
	}
	
	@Override
	public void filterDeal(Role entity) {
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
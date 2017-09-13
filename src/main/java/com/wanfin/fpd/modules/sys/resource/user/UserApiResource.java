/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 * @Project fpd 
 * @Title UserApiResource.java
 * @Package com.wanfin.fpd.modules.sys.resource.user
 * @Description [[_用户_]]文件 
 * @author Chenh
 * @date 2016年5月12日 下午3:08:57 
 * @version V1.0   
 */
package com.wanfin.fpd.modules.sys.resource.user;

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
import com.wanfin.fpd.modules.sys.entity.User;
import com.wanfin.fpd.modules.sys.resource.OfficeApiResource;

/**
 * @Description [[_用户_]] UserApiResource资源类
 * @author Chenh
 * @version 2016-06-16
 * @date 2016年6月6日 下午7:24:32 
 */
@SuppressWarnings("unchecked")
public class UserApiResource extends CollectionResTool<User> implements LinkFilter<User> {
	private static final long serialVersionUID = 1L;
	
	public UserApiResource() {
		super();
	}

	public UserApiResource(User entity) {
        super(entity);
        filterDeal(entity);
    }
	
	public UserApiResource(UriInfo info, User entity) {
		super(info, entity);
		filterDeal(entity);
	}
	
	public UserApiResource(User entity, List<String> fields) {
		super(entity);
    	filterByFields(entity, fields);
	}
	
	public UserApiResource(UriInfo info, User entity, List<String> fields) {
		super(info, entity);
		filterByFields(entity, fields);
	}

	@Override
	public void filter(User entity) {
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
	public void filterChange(User entity) {
        filter(entity);
        
		put("name", entity.getName());
//        put("loginName", entity.getLoginName());
//        put("password", entity.getPassword());
		put("no", entity.getNo());
		put("email", entity.getEmail());
		put("phone", entity.getPhone());
		put("mobile", entity.getMobile());
		put("userType", entity.getUserType());
		put("photo", entity.getPhoto());
		put("loginIp", entity.getLoginIp());
		put("loginDate", entity.getLoginDate());
		put("loginFlag", entity.getLoginFlag());
		
		if(entity.getOffice() != null){
			put("office", new OfficeApiResource(entity.getOffice()));
		}
		
		if(entity.getCompany() != null){
			put("company", new OfficeApiResource(entity.getCompany()));
		}
	}
	
	@Override
	public void filterQuery(User entity) {
        filter(entity);
        
		put("name", entity.getName());
//        put("loginName", entity.getLoginName());
//        put("password", entity.getPassword());
		put("no", entity.getNo());
		put("email", entity.getEmail());
		put("phone", entity.getPhone());
		put("mobile", entity.getMobile());
		put("userType", entity.getUserType());
		put("photo", entity.getPhoto());
		put("loginIp", entity.getLoginIp());
		put("loginDate", entity.getLoginDate());
		put("loginFlag", entity.getLoginFlag());
		
		if(entity.getOffice() != null){
			put("office", new OfficeApiResource(entity.getOffice()));
		}
		
		if(entity.getCompany() != null){
			put("company", new OfficeApiResource(entity.getCompany()));
		}
	}
	
	@Override
	public void filterByFields(User entity, List<String> fields) {
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
	public Link filterApiResourceByFields(User entity, User enty) {
		if((entity != null) && (entity.getFields() != null) && (entity.getFields().size() > 0)){
			return new UserApiResource(enty, entity.getFields());
		}else{
			return new UserApiResource(enty);
		}
	}
	
	@Override
	public Link filterApiResourceByFields(ApiUriInfo info, User entity, User enty) {
		if((entity != null) && (entity.getFields() != null) && (entity.getFields().size() > 0)){
			return new UserApiResource(info, enty, entity.getFields());
		}else{
			return new UserApiResource(info, enty);
		}
	}

	@Override
	public void filterDeal(User entity) {
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
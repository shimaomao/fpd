///**
// * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
// * @Project fpd 
// * @Title TRepayPlanApiResource.java
// * @Package com.wanfin.fpd.modules.repayplan.resource
// * @Description [[_还款计划_]]文件 
// * @author Chenh
// * @date 2016年5月12日 下午3:08:57 
// * @version V1.0   
// */
//package com.wanfin.fpd.modules.repayplan.resource;
//
//import java.util.List;
//
//import javax.ws.rs.core.UriInfo;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//import org.springframework.web.bind.annotation.RequestMethod;
//
//import com.wanfin.fpd.common.utils.DateUtils;
//import com.wanfin.fpd.core.ApiUriInfo;
//import com.wanfin.fpd.core.CollectionResTool;
//import com.wanfin.fpd.core.Link;
//import com.wanfin.fpd.core.filter.LinkFilter;
//import com.wanfin.fpd.modules.repayplan.entity.TRepayPlan;
//
///**
// * @Description [[_还款计划_]] TRepayPlanApiResource资源类
// * @author Chenh
// * @version 2016-06-17
// * @date 2016年6月6日 下午7:24:32 
// */
//@SuppressWarnings("unchecked")
//public class TRepayPlanApiResource extends CollectionResTool<TRepayPlan> implements LinkFilter<TRepayPlan> {
//	private static final long serialVersionUID = 1L;
//	
//	public TRepayPlanApiResource() {
//		super();
//	}
//
//	public TRepayPlanApiResource(TRepayPlan entity) {
//        super(entity);
//        filterDeal(entity);
//    }
//	
//	public TRepayPlanApiResource(UriInfo info, TRepayPlan entity) {
//		super(info, entity);
//		filterDeal(entity);
//	}
//	
//	public TRepayPlanApiResource(TRepayPlan entity, List<String> fields) {
//		super(entity);
//    	filterByFields(entity, fields);
//	}
//	
//	public TRepayPlanApiResource(UriInfo info, TRepayPlan entity, List<String> fields) {
//		super(info, entity);
//		filterByFields(entity, fields);
//	}
//
//	@Override
//	public void filter(TRepayPlan entity) {
//		put("id", entity.getId());
//        
//        put("remarks", entity.getRemarks());
//        if(entity.getCreateBy() != null){
//        	put("createBy", entity.getCreateBy().getName());
//        }
//        if(entity.getUpdateBy() != null){
//        	put("updateBy", entity.getCreateBy().getName());
//        }
//        if(entity.getCreateDate() != null){
//        	put("createDate", DateUtils.formatDateTime(entity.getCreateDate()));
//        }
//        if(entity.getUpdateDate() != null){
//            put("updateDate", DateUtils.formatDateTime(entity.getUpdateDate()));
//        }
//	}
//
//	@Override
//	public void filterChange(TRepayPlan entity) {
//		filter(entity);
//
//		put("num", entity.getNum());
//		put("interest", entity.getInterest());
//		put("isYuQi", entity.getIsYuQi());
//		put("status", entity.getStatus());
//		put("principal", entity.getPrincipal());
//		put("yuQi", entity.getYuQi());
//		put("startDate", entity.getStartDate());
//		put("endDate", entity.getEndDate());
//		put("accountDate", entity.getAccountDate());
//		put("isValid", entity.getIsValid());
//	}
//	
//	@Override
//	public void filterQuery(TRepayPlan entity) {
//		filter(entity);
//		
//		put("num", entity.getNum());
//		put("interest", entity.getInterest());
//		put("isYuQi", entity.getIsYuQi());
//		put("status", entity.getStatus());
//		put("principal", entity.getPrincipal());
//		put("yuQi", entity.getYuQi());
//		put("startDate", entity.getStartDate());
//		put("endDate", entity.getEndDate());
//		put("accountDate", entity.getAccountDate());
//		put("isValid", entity.getIsValid());
//	}
//	
//	@Override
//	public void filterByFields(TRepayPlan entity, List<String> fields) {
//		JSONObject jentity =  new JSONObject(entity);
//    	for (String key : fields) {
//            try {
//            	put(key, jentity.get(key));
//			} catch (JSONException e) {
//				e.printStackTrace();
//			}
//		}
//	}
//	
//	/** 公共方法 ***********************************************************************************/
//	@Override
//	public Link filterApiResourceByFields(TRepayPlan entity, TRepayPlan enty) {
//		if((entity != null) && (entity.getFields() != null) && (entity.getFields().size() > 0)){
//			return new TRepayPlanApiResource(enty, entity.getFields());
//		}else{
//			return new TRepayPlanApiResource(enty);
//		}
//	}
//	
//	@Override
//	public Link filterApiResourceByFields(ApiUriInfo info, TRepayPlan entity, TRepayPlan enty) {
//		if((entity != null) && (entity.getFields() != null) && (entity.getFields().size() > 0)){
//			return new TRepayPlanApiResource(info, enty, entity.getFields());
//		}else{
//			return new TRepayPlanApiResource(info, enty);
//		}
//	}
//
//	@Override
//	public void filterDeal(TRepayPlan entity) {
//		if((entity != null) && (entity.getOper() != null)){
//        	if((entity.getOper().equals(RequestMethod.POST)) || (entity.getOper().equals(RequestMethod.PUT)) || (entity.getOper().equals(RequestMethod.PATCH))){
//	        	filterChange(entity);
//	        }else if((entity.getOper().equals(RequestMethod.GET))){
//	        	filterQuery(entity);
//	        }
//        }else{
//        	filter(entity);
//        }
//	}
//}
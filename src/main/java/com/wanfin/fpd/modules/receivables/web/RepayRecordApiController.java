/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 * @Project fpd 
 * @Title RepayRecordApiController.java
 * @Package com.wanfin.fpd.modules.receivables.controller
 * @Description [[_xxx_]]文件 
 * @author Chenh
 * @date 2016年5月12日 下午3:08:57 
 * @version V1.0   
 */ 
package com.wanfin.fpd.modules.receivables.web;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mangofactory.swagger.annotations.ApiIgnore;
import com.wanfin.fpd.common.utils.StringUtils;
import com.wanfin.fpd.common.web.BaseController;
import com.wanfin.fpd.core.ApiSval.JKey;
import com.wanfin.fpd.core.Link;
import com.wanfin.fpd.core.Link.LinkKey;
import com.wanfin.fpd.core.LinkMapping;
import com.wanfin.fpd.modules.receivables.entity.RepayRecord;
import com.wanfin.fpd.modules.receivables.resource.RepayRecordApiResource;
import com.wanfin.fpd.modules.receivables.service.RepayRecordService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;


/**
 * @Description [[_还款记录_]] RepayRecordApiController控制器类
 * @author Chenh
 * @version 2016-07-14
 * @date 2016年5月12日 下午3:08:57 
 */
@Controller
@Api(value = LinkKey.RepayRecord, description = "还款记录")
@RequestMapping(value = Link.API_PATH + LinkKey.RepayRecord)
public class RepayRecordApiController extends BaseController{
	private RepayRecordApiResource apiResource = new RepayRecordApiResource();
	
	@Autowired
	private RepayRecordService entityService;
	
	/************************************************************************************************
	 * 【标准接口】
	 ************************************************************************************************/
	/**
	 * @Description  GET 获取[[_还款记录_]]列表(getAll)
	 * 返回所有[[_还款记录_]]的列表（数组）
	 * 
	 * @param entity [[_还款记录_]]参数
	 * @return
	 * @author Chenh 
	 * @date 2016年6月6日 下午12:57:08
	 */
	@ResponseBody
	@ApiResponses({
		@ApiResponse(code=200, message="成功！"),
		
		@ApiResponse(code=400, message="错误请求！"),
		@ApiResponse(code=401, message="未授权！"),
		@ApiResponse(code=403, message="禁止访问！"),
		@ApiResponse(code=404, message="服务不存在！"),

		@ApiResponse(code=500, message="服务器内部错误！")
	})
	@RequestMapping(value = {LinkMapping.GET_ALL}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
   	@ApiOperation(value = "根据条件获取还款记录列表", notes = "根据条件获取还款记录列表", position = 1, httpMethod = "GET", response = Response.class)
	public Response get(@ApiParam(name="entity", value="还款记录(entity)") RepayRecord entity) {
        Collection<RepayRecord> entitys = entityService.findList(entity);
        entity.setOper(RequestMethod.GET);
        return Response.ok(apiResource.getCollectionRes(entity, entitys, LinkKey.RepayRecord)).build();
	}
	
	/**
	 * @Description GET/{id} 获取[[_还款记录_]](getById)
	 * 返回单个资源[[_还款记录_]]
	 * 
	 * @param entity [[_还款记录_]]参数
	 * @param id  [[_还款记录_]]主键
	 * @return
	 * @author Chenh 
	 * @date 2016年6月6日 下午12:58:28
	 */
	@ResponseBody
	@ApiResponses({
		@ApiResponse(code=200, message="成功！"),
		
		@ApiResponse(code=400, message="错误请求！"),
		@ApiResponse(code=401, message="未授权！"),
		@ApiResponse(code=403, message="禁止访问！"),
		@ApiResponse(code=404, message="服务不存在！"),
		
		@ApiResponse(code=500, message="服务器内部错误！")
	})
	@RequestMapping(value = {LinkMapping.GET_BY_ID}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ApiOperation(value = "根据ID获取还款记录", notes = "根据用户名获取还款记录", position = 2, httpMethod = "GET", response = Response.class)
	public Response get(@ApiParam(name="entity", value="还款记录(entity)") RepayRecord entity, @PathVariable(JKey.ID) @ApiParam(name="id", value="主键(id)") String id) {
		Map<String, Object> result = new HashMap<String, Object>();
		if(StringUtils.isNotEmpty(id)){
			RepayRecord enty = entityService.get(id);
			if (enty == null) {
				return Response.noContent().build();
			}
			entity.setOper(RequestMethod.GET);
			return Response.ok(apiResource.getApiResourceByExpand(entity, enty)).build();
		}
		result.put(JKey.MSG, "错误请求！");
		return Response.status(Status.BAD_REQUEST).entity(result).build();
	}
	
	/**
	 * @Description POST 新增[[_还款记录_]](save)
	 * 创建某个指定[[_还款记录_]]的信息
	 * 返回新生成的资源[[_还款记录_]]
	 * 
	 * @param entity [[_还款记录_]]参数
	 * @return
	 * @author Chenh 
	 * @date 2016年6月6日 下午12:59:04
	 */
	@ResponseBody
	@ApiResponses({
		@ApiResponse(code=201, message="创建成功！"),
		
		@ApiResponse(code=400, message="错误请求！"),
		@ApiResponse(code=401, message="未授权！"),
		@ApiResponse(code=403, message="禁止访问！"),
		@ApiResponse(code=404, message="服务不存在！"),
		@ApiResponse(code=405, message="GET请求不支持！"),
		@ApiResponse(code=412, message="参数错误！"),

		@ApiResponse(code=500, message="服务器内部错误！")
	})
	@RequestMapping(value = {LinkMapping.SAVE}, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON)
    @ApiOperation(value = "创建还款记录", notes = "创建还款记录", position = 3, httpMethod = "POST", response = Response.class)
    public Response post(@ApiParam(name="entity", value="还款记录(entity)") @RequestBody RepayRecord entity) {
        Map<String, Object> result = new HashMap<String, Object>();
		if(entity != null){
	        if(entity.getRepayDate() == null){
				result.put(JKey.MSG, "repayDate参数错误！");
				return Response.status(Status.PRECONDITION_FAILED).entity(result).build();
	        }
	        if(StringUtils.isEmpty(entity.getId())){
		        entityService.save(entity);
		        entity.setOper(RequestMethod.POST);
		        return resCreated(new RepayRecordApiResource(entity));
	    	}
			result.put(JKey.MSG, "参数错误！");
			return Response.status(Status.PRECONDITION_FAILED).entity(result).build();
    	}
    	result.put(JKey.MSG, "错误请求！");
		return Response.status(Status.BAD_REQUEST).entity(result).build();
	}
	
	/**
	 * @Description PUT 修改[[_还款记录_]](update)
	 * 	更新某个指定[[_还款记录_]]的信息
	 * 	返回[[_还款记录_]]全部信息
	 * 
	 * @param entity [[_还款记录_]]参数
	 * @param id  [[_还款记录_]]主键
	 * @return
	 * @author Chenh 
	 * @date 2016年6月6日 下午12:58:28
	 */
	@ApiIgnore
	@ResponseBody
	@ApiResponses({
		@ApiResponse(code=200, message="修改成功！"),
		
		@ApiResponse(code=400, message="错误请求！"),
		@ApiResponse(code=401, message="未授权！"),
		@ApiResponse(code=403, message="禁止访问！"),
		@ApiResponse(code=404, message="服务不存在！"),
		@ApiResponse(code=412, message="参数错误！"),

		@ApiResponse(code=500, message="服务器内部错误！")
	})
	@RequestMapping(value = {LinkMapping.UPDATE}, method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON)
    @ApiOperation(value = "根据ID更新还款记录", notes = "根据ID更新还款记录", position = 4, httpMethod = "PUT", response = Response.class)
    public Response put(@ApiParam(name="entity", value="还款记录(entity)") @RequestBody RepayRecord entity, @PathVariable(JKey.ID) @ApiParam(name="id", value="主键(id)") String id) {
		Map<String, Object> result = new HashMap<String, Object>();
		if(entity != null){
			if(StringUtils.isNotEmpty(entity.getId())){
				entityService.save(entity);
				entity.setOper(RequestMethod.PUT);
				return Response.ok(new RepayRecordApiResource(entity)).build();
			}
			result.put(JKey.MSG, "参数错误！");
			return Response.status(Status.PRECONDITION_FAILED).entity(result).build();
		}
    	result.put(JKey.MSG, "错误请求！");
		return Response.status(Status.BAD_REQUEST).entity(result).build();
	}
	
	/**
	 * @Description PATCH 修改[[_还款记录_]](update)
	 * 	更新某个指定[[_还款记录_]]的信息
	 * 	返回[[_还款记录_]]部分信息
	 * 
	 * @param entity [[_还款记录_]]参数
	 * @param id  [[_还款记录_]]主键
	 * @return
	 * @author Chenh 
	 * @date 2016年6月6日 下午12:58:28
	 */
	@ApiIgnore
	@ResponseBody
	@ApiResponses({
		@ApiResponse(code=200, message="修改成功！"),
		
		@ApiResponse(code=400, message="错误请求！"),
		@ApiResponse(code=401, message="未授权！"),
		@ApiResponse(code=403, message="禁止访问！"),
		@ApiResponse(code=404, message="服务不存在！"),
		@ApiResponse(code=412, message="参数错误！"),

		@ApiResponse(code=500, message="服务器内部错误！")
	})
	@RequestMapping(value = {LinkMapping.UPDATE}, method = RequestMethod.PATCH, produces = MediaType.APPLICATION_JSON)
	@ApiOperation(value = "根据ID更新还款记录", notes = "根据ID更新还款记录", position = 5, httpMethod = "PATCH", response = Response.class)
   	public Response patch(@ApiParam(name="entity", value="还款记录(entity)") @RequestBody RepayRecord entity, @PathVariable(JKey.ID) @ApiParam(name="id", value="主键(id)") String id) {
		Map<String, Object> result = new HashMap<String, Object>();
		if(entity != null){
			if(StringUtils.isNotEmpty(entity.getId())){
				entityService.save(entity);
				entity.setOper(RequestMethod.PATCH);
				return Response.ok(new RepayRecordApiResource(entity)).build();
			}
			result.put(JKey.MSG, "参数错误！");
			return Response.status(Status.PRECONDITION_FAILED).entity(result).build();
		}
    	result.put(JKey.MSG, "错误请求！");
		return Response.status(Status.BAD_REQUEST).entity(result).build();
	}
	
	/**
	 * @Description DELETE 删除[[_还款记录_]](delete)
	 * 	删除某个指定[[_还款记录_]]的信息
	 * 	返回空信息
	 * 
	 * @param id  [[_还款记录_]]主键
	 * @return
	 * @author Chenh 
	 * @date 2016年6月6日 下午1:21:29
	 */
	@ApiIgnore
	@ResponseBody
	@ApiResponses({
		@ApiResponse(code=200, message="删除成功！"),
		
		@ApiResponse(code=400, message="错误请求！"),
		@ApiResponse(code=401, message="未授权！"),
		@ApiResponse(code=403, message="禁止访问！"),
		@ApiResponse(code=404, message="服务不存在！"),

		@ApiResponse(code=500, message="服务器内部错误！")
	})
	@RequestMapping(value = {LinkMapping.DELETE_BY_ID}, method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON)
	@ApiOperation(value = "根据ID删除还款记录", notes = "根据ID删除还款记录", position = 6, httpMethod = "PATCH", response = Response.class)
	public Response delete(@PathVariable(JKey.ID) @ApiParam(name="id", value="主键(id)") String id) {
		Map<String, Object> result = new HashMap<String, Object>();
		if(StringUtils.isNotEmpty(id)){
			entityService.delete(new RepayRecord(id));
			result.put(JKey.ID, id);
			result.put(JKey.MSG, "删除成功！");
			return Response.ok().entity(result).build();
		}
		result.put(JKey.MSG, "错误请求！");
		return Response.status(Status.BAD_REQUEST).entity(result).build();
	}
}
/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 * @Project fpd 
 * @Title OfficeApiController.java
 * @Package com.wanfin.fpd.modules.sys.controller
 * @Description [[_xxx_]]文件 
 * @author Chenh
 * @date 2016年5月12日 下午3:08:57 
 * @version V1.0   
 */ 
package com.wanfin.fpd.modules.sys.web.office;

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
import com.wanfin.fpd.common.config.Cons;
import com.wanfin.fpd.common.config.Global;
import com.wanfin.fpd.common.utils.StringUtils;
import com.wanfin.fpd.common.web.BaseController;
import com.wanfin.fpd.core.ApiSval.JKey;
import com.wanfin.fpd.core.Link;
import com.wanfin.fpd.core.Link.LinkKey;
import com.wanfin.fpd.core.LinkMapping;
import com.wanfin.fpd.modules.sys.entity.Area;
import com.wanfin.fpd.modules.sys.entity.Office;
import com.wanfin.fpd.modules.sys.entity.User;
import com.wanfin.fpd.modules.sys.resource.OfficeApiResource;
import com.wanfin.fpd.modules.sys.service.OfficeService;
import com.wanfin.fpd.modules.sys.service.user.UserApiService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;


/**
 * @Description [[_机构_]] OfficeApiController控制器类
 * @author Chenh
 * @version 2016-06-24
 * @date 2016年5月12日 下午3:08:57 
 */
@Controller
@Api(value = LinkKey.Office, description = "机构")
@RequestMapping(value = Link.API_PATH + LinkKey.Office)
public class OfficeApiController extends BaseController{
	private OfficeApiResource apiResource = new OfficeApiResource();
	
	@Autowired
	private OfficeService entityService;
	@Autowired
	private UserApiService userService;
	
	/************************************************************************************************
	 * 【标准接口】
	 ************************************************************************************************/
	/**
	 * @Description  GET 获取[[_机构_]]列表(getAll)
	 * 返回所有[[_机构_]]的列表（数组）
	 * 
	 * @param entity [[_机构_]]参数
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
   	@ApiOperation(value = "根据条件获取机构列表", notes = "根据条件获取机构列表", position = 1, httpMethod = "GET", response = Response.class)
	public Response get(@ApiParam(name="entity", value="机构(entity)") Office entity) {
        Collection<Office> entitys = entityService.findList(entity);
		entity.setOper(RequestMethod.GET);
        return Response.ok(apiResource.getCollectionRes(entity, entitys, LinkKey.Office)).build();
	}
	
	/**
	 * @Description GET/{id} 获取[[_机构_]](getById)
	 * 返回单个资源[[_机构_]]
	 * 
	 * @param entity [[_机构_]]参数
	 * @param id  [[_机构_]]主键
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
	@ApiOperation(value = "根据ID获取机构", notes = "根据用户名获取机构", position = 2, httpMethod = "GET", response = Response.class)
	public Response get(@ApiParam(name="entity", value="机构(entity)") Office entity, @PathVariable(JKey.ID) @ApiParam(name="id", value="主键(id)") String id) {
		Map<String, Object> result = new HashMap<String, Object>();
		if(StringUtils.isNotEmpty(id)){
			Office enty = entityService.get(id);
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
	 * @Description POST 新增[[_机构_]](save)
	 * 创建某个指定[[_机构_]]的信息
	 * 返回新生成的资源[[_机构_]]
	 * 
	 * @param entity [[_机构_]]参数
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
    @ApiOperation(value = "创建机构", notes = "创建机构", position = 3, httpMethod = "POST", response = Response.class)
    public Response post(@ApiParam(name="entity", value="机构(entity)") @RequestBody Office entity) {
        Map<String, Object> result = new HashMap<String, Object>();
		if(entity != null){
			if(StringUtils.isEmpty(entity.getName())){
	        	result.put(JKey.MSG, "机构名称(name)为空！");
	        	return Response.status(Status.PRECONDITION_FAILED).entity(result).build();
	        }
	        if((entity.getParent() == null)){
	        	entity.setParent(new Office());
	        }
	        if(StringUtils.isEmpty(entity.getParent().getId())){
	        	entity.getParent().setId(Cons.SysDF.DF_SUPER_OFFICE);
	        }
	        if(StringUtils.isEmpty(entity.getType())){
	        	result.put(JKey.MSG, "机构类型(type)为空！");
	        	return Response.status(Status.PRECONDITION_FAILED).entity(result).build();
	        }
	        if(StringUtils.isEmpty(entity.getGrade())){
	        	result.put(JKey.MSG, "机构等级(grade)为空！");
	        	return Response.status(Status.PRECONDITION_FAILED).entity(result).build();
	        }
			
        	if(StringUtils.isEmpty(entity.getId())){
	        	entity.setUseable(Global.YES);
	        	entity.setArea(new Area(Cons.SysDF.DF_AREA));
		        entityService.save(entity);
				entity.setOper(RequestMethod.POST);
		        return resCreated(new OfficeApiResource(entity));
	    	}else{
	    		if(entity.getUpdateBy() == null){
	    			if(StringUtils.isNotEmpty(entity.getOrganId())){
	    				User newUser = userService.getBywtypeId(entity.getOrganId());
	    				if(newUser != null){
	    					entity.setUpdateBy(newUser);
	    				}else{
	    					result.put(JKey.MSG, "租户ID有误！");
	    		        	return Response.status(Status.PRECONDITION_FAILED).entity(result).build();
	    				}
	    			}else{
    					result.put(JKey.MSG, "租户ID有误！");
    		        	return Response.status(Status.PRECONDITION_FAILED).entity(result).build();
    				}
	    		}
		        entityService.save(entity);
				result.put(JKey.MSG, "修改成功！");
				return Response.ok().entity(result).build();
	    	}
    	}
    	result.put(JKey.MSG, "错误请求！");
		return Response.status(Status.BAD_REQUEST).entity(result).build();
	}
	
	/**
	 * @Description PUT 修改[[_机构_]](update)
	 * 	更新某个指定[[_机构_]]的信息
	 * 	返回[[_机构_]]全部信息
	 * 
	 * @param entity [[_机构_]]参数
	 * @param id  [[_机构_]]主键
	 * @return
	 * @author Chenh 
	 * @date 2016年6月6日 下午12:58:28
	 */
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
    @ApiOperation(value = "根据ID更新机构", notes = "根据ID更新机构", position = 4, httpMethod = "PUT", response = Response.class)
    public Response put(@ApiParam(name="entity", value="机构(entity)") @RequestBody Office entity, @PathVariable(JKey.ID) @ApiParam(name="id", value="主键(id)") String id) {
		Map<String, Object> result = new HashMap<String, Object>();
		if(StringUtils.isNotEmpty(id)){
			entity.setId(id);
			return post(entity);
		}
    	result.put(JKey.MSG, "错误请求(id为空)！");
		return Response.status(Status.BAD_REQUEST).entity(result).build();
	}
	
	/**
	 * @Description PATCH 修改[[_机构_]](update)
	 * 	更新某个指定[[_机构_]]的信息
	 * 	返回[[_机构_]]部分信息
	 * 
	 * @param entity [[_机构_]]参数
	 * @param id  [[_机构_]]主键
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
	@ApiOperation(value = "根据ID更新机构", notes = "根据ID更新机构", position = 5, httpMethod = "PATCH", response = Response.class)
   	public Response patch(@ApiParam(name="entity", value="机构(entity)") @RequestBody Office entity, @PathVariable(JKey.ID) @ApiParam(name="id", value="主键(id)") String id) {
		Map<String, Object> result = new HashMap<String, Object>();
		if(entity != null){
			if(StringUtils.isNotEmpty(entity.getId())){
				entityService.save(entity);
				entity.setOper(RequestMethod.PATCH);
				return Response.ok(new OfficeApiResource(entity)).build();
			}
			result.put(JKey.MSG, "参数错误！");
			return Response.status(Status.PRECONDITION_FAILED).entity(result).build();
		}
    	result.put(JKey.MSG, "错误请求！");
		return Response.status(Status.BAD_REQUEST).entity(result).build();
	}
	
	/**
	 * @Description DELETE 删除[[_机构_]](delete)
	 * 	删除某个指定[[_机构_]]的信息
	 * 	返回空信息
	 * 
	 * @param id  [[_机构_]]主键
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
	@ApiOperation(value = "根据ID删除机构", notes = "根据ID删除机构", position = 6, httpMethod = "PATCH", response = Response.class)
	public Response delete(@PathVariable(JKey.ID) @ApiParam(name="id", value="主键(id)") String id) {
		Map<String, Object> result = new HashMap<String, Object>();
		if(StringUtils.isNotEmpty(id)){
			entityService.delete(new Office(id));
			result.put(JKey.ID, id);
			result.put(JKey.MSG, "删除成功！");
			return Response.ok().entity(result).build();
		}
		result.put(JKey.MSG, "错误请求！");
		return Response.status(Status.BAD_REQUEST).entity(result).build();
	}
}
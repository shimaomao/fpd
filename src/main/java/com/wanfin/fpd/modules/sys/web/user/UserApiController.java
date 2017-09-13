/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 * @Project fpd 
 * @Title UserApiController.java
 * @Package com.wanfin.fpd.modules.sys.controller.user
 * @Description [[_xxx_]]文件 
 * @author Chenh
 * @date 2016年5月12日 下午3:08:57 
 * @version V1.0   
 */ 
package com.wanfin.fpd.modules.sys.web.user;

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
import com.wanfin.fpd.common.utils.StringUtils;
import com.wanfin.fpd.common.web.BaseController;
import com.wanfin.fpd.core.ApiSval.JKey;
import com.wanfin.fpd.core.Link;
import com.wanfin.fpd.core.Link.LinkKey;
import com.wanfin.fpd.core.LinkMapping;
import com.wanfin.fpd.modules.sys.entity.Office;
import com.wanfin.fpd.modules.sys.entity.Role;
import com.wanfin.fpd.modules.sys.entity.User;
import com.wanfin.fpd.modules.sys.resource.role.RoleApiResource;
import com.wanfin.fpd.modules.sys.resource.user.UserApiResource;
import com.wanfin.fpd.modules.sys.service.role.RoleApiService;
import com.wanfin.fpd.modules.sys.service.user.UserApiService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;

/**
 * @Description [[_用户_]] UserApiController控制器类
 * @author Chenh
 * @version 2016-06-16
 * @date 2016年5月12日 下午3:08:57 
 */
@Controller
@Api(value = LinkKey.User, description = "用户")
@RequestMapping(value = Link.API_PATH + LinkKey.User)
public class UserApiController extends BaseController{
	private UserApiResource apiResource = new UserApiResource();
	private RoleApiResource roleApiResource = new RoleApiResource();
	
	@Autowired
	private UserApiService entityService;
	
	@Autowired
	private RoleApiService roleService;

	/**
	 * @Description  GET 获取[[_用户_]]列表(getAll)
	 * 返回所有[[_用户_]]的列表（数组）
	 * 
	 * @param entity [[_用户_]]参数
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
   	@ApiOperation(value = "根据条件获取用户列表", notes = "根据条件获取用户列表", position = 1, httpMethod = "GET", response = Response.class)
	public Response get(@ApiParam(name="entity", value="用户(entity)") User entity) {
        Collection<User> entitys = entityService.findList(entity);
		entity.setOper(RequestMethod.GET);
        return Response.ok(apiResource.getCollectionRes(entity, entitys, LinkKey.User)).build();
	}
	
	/**
	 * @Description GET/{id} 获取[[_用户_]](getById)
	 * 返回单个资源[[_用户_]]
	 * 
	 * @param entity [[_用户_]]参数
	 * @param id  [[_用户_]]主键
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
	@ApiOperation(value = "根据ID获取用户", notes = "根据用户名获取用户", position = 2, httpMethod = "GET", response = Response.class)
	public Response get(@ApiParam(name="entity", value="用户(entity)") User entity, @PathVariable(JKey.ID) @ApiParam(name="id", value="主键(id)") String id) {
		Map<String, Object> result = new HashMap<String, Object>();
		if(StringUtils.isNotEmpty(id)){
			User enty = entityService.get(id);
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
	 * @Description POST 新增[[_用户_]](save)
	 * 创建某个指定[[_用户_]]的信息
	 * 返回新生成的资源[[_用户_]]
	 * 
	 * @param entity [[_用户_]]参数
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
    @ApiOperation(value = "创建用户", notes = "创建用户", position = 3, httpMethod = "POST", response = Response.class)
    public Response post(@ApiParam(name="entity", value="用户(entity)") @RequestBody User entity) {
		Map<String, Object> result = new HashMap<String, Object>();
		if(entity != null){
			if((entity.getCompany() == null)){
				entity.setCompany(new Office());
	    	}
			if(StringUtils.isEmpty(entity.getCompany().getId())){
				entity.getCompany().setId(Cons.SysDF.DF_SUPER_OFFICE);
			}
			if((entity.getOffice() == null) || StringUtils.isEmpty(entity.getOffice().getId())){
				result.put(JKey.MSG, "office或office.id参数错误！");
				return Response.status(Status.PRECONDITION_FAILED).entity(result).build();
			}
			if(StringUtils.isEmpty(entity.getId())){
				entityService.save(entity);
				entity.setOper(RequestMethod.POST);
				return resCreated(new UserApiResource(entity));
			}
			result.put(JKey.MSG, "参数错误！");
			return Response.status(Status.PRECONDITION_FAILED).entity(result).build();
		}
    	result.put(JKey.MSG, "错误请求！");
		return Response.status(Status.BAD_REQUEST).entity(result).build();
	}
	
	/**
	 * @Description PUT 修改[[_用户_]](update)
	 * 	更新某个指定[[_用户_]]的信息
	 * 	返回[[_用户_]]全部信息
	 * 
	 * @param entity [[_用户_]]参数
	 * @param id  [[_用户_]]主键
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
    @ApiOperation(value = "根据ID更新用户", notes = "根据ID更新用户", position = 4, httpMethod = "PUT", response = Response.class)
    public Response put(@ApiParam(name="entity", value="用户(entity)") @RequestBody User entity, @PathVariable(JKey.ID) @ApiParam(name="id", value="主键(id)") String id) {
		Map<String, Object> result = new HashMap<String, Object>();
		if(entity != null){
			if(StringUtils.isNotEmpty(entity.getId())){
				entityService.save(entity);
				entity.setOper(RequestMethod.PUT);
				return Response.ok(new UserApiResource(entity)).build();
			}
			result.put(JKey.MSG, "参数错误！");
			return Response.status(Status.PRECONDITION_FAILED).entity(result).build();
		}
    	result.put(JKey.MSG, "错误请求！");
		return Response.status(Status.BAD_REQUEST).entity(result).build();
	}
	
	/**
	 * @Description PATCH 修改[[_用户_]](update)
	 * 	更新某个指定[[_用户_]]的信息
	 * 	返回[[_用户_]]部分信息
	 * 
	 * @param entity [[_用户_]]参数
	 * @param id  [[_用户_]]主键
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
	@ApiOperation(value = "根据ID更新用户", notes = "根据ID更新用户", position = 5, httpMethod = "PATCH", response = Response.class)
   	public Response patch(@ApiParam(name="entity", value="用户(entity)") @RequestBody User entity, @PathVariable(JKey.ID) @ApiParam(name="id", value="主键(id)") String id) {
		Map<String, Object> result = new HashMap<String, Object>();
		if(entity != null){
			if(StringUtils.isNotEmpty(entity.getId())){
				entityService.save(entity);
				entity.setOper(RequestMethod.PATCH);
				return Response.ok(new UserApiResource(entity)).build();
			}
			result.put(JKey.MSG, "参数错误！");
			return Response.status(Status.PRECONDITION_FAILED).entity(result).build();
		}
    	result.put(JKey.MSG, "错误请求！");
		return Response.status(Status.BAD_REQUEST).entity(result).build();
	}
	
	/**
	 * @Description DELETE 删除[[_用户_]](delete)
	 * 	删除某个指定[[_用户_]]的信息
	 * 	返回空信息
	 * 
	 * @param id  [[_用户_]]主键
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
	@ApiOperation(value = "根据ID删除用户", notes = "根据ID删除用户", position = 6, httpMethod = "PATCH", response = Response.class)
	public Response delete(@PathVariable(JKey.ID) @ApiParam(name="id", value="主键(id)") String id) {
		Map<String, Object> result = new HashMap<String, Object>();
		if(StringUtils.isNotEmpty(id)){
			entityService.delete(new User(id));
			result.put(JKey.ID, id);
			result.put(JKey.MSG, "删除成功！");
			return Response.ok().entity(result).build();
		}
		result.put(JKey.MSG, "错误请求！");
		return Response.status(Status.BAD_REQUEST).entity(result).build();
	}
	

	/************************************************************************************************
	 * 【标准关联接口】
	 * Role 角色
	 ************************************************************************************************/
	/**
	 * @Description  GET/hasUser 根据登录名判断用户是否存在
	 * 返回用户
	 * 
	 * @param info API链接[[_角色_]]
	 * @param entity [[_角色_]]参数
	 * @param rid [[_用户_]]主键
	 * @return
	 * @author Chenh 
	 * @date 2016年6月6日 下午12:57:08
	 */
	@ResponseBody
	@RequestMapping(value = {LinkMapping.M_User.HAS_USER}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
   	@ApiOperation(value = "根据登录名判断用户是否存在", notes = "根据登录名判断用户是否存在", position = 1, httpMethod = "GET", response = Response.class)
	public Response hasUser(@ApiParam(name="entity", value="用户(entity)") User entity) {
		Map<String, Object> result = new HashMap<String, Object>();
		if(entity == null){
			result.put(JKey.MSG, "参数错误！");
			return Response.status(Status.PRECONDITION_FAILED).entity(result).build();
		}
		if(StringUtils.isEmpty(entity.getLoginName())){
			result.put(JKey.MSG, "登录名(loginName)参数不能为空！");
			return Response.status(Status.PRECONDITION_FAILED).entity(result).build();
		}
		
		entity = entityService.getByLoginName(entity.getLoginName());
		if(entity == null){
//			result.put(JKey.ID, entity.getId());
			result.put(JKey.MSG, "账户不存在或已注销！");
//			result.put(JKey.ID, id);
			result.put(JKey.MSG, "删除成功！");
		}else{
			result.put(JKey.ID, entity.getId());
			result.put(JKey.MSG, "账户不存在或已注销！");
		}
		entity.setOper(RequestMethod.GET);
		return Response.ok().entity(result).build();
	}
	
	/**
	 * @Description  GET/{rid}/roles 根据用户获取[[_角色_]]列表(Role_GET_ALL)
	 * 返回所有[[_角色_]]的列表（数组）
	 * 
	 * @param info API链接[[_角色_]]
	 * @param entity [[_角色_]]参数
	 * @param rid [[_用户_]]主键
	 * @return
	 * @author Chenh 
	 * @date 2016年6月6日 下午12:57:08
	 */
	@ResponseBody
	@RequestMapping(value = {LinkMapping.M_Role.GET_ALL}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ApiOperation(value = "根据条件获取角色列表", notes = "根据条件名获取角色列表", position = 1, httpMethod = "GET", response = Response.class)
	public Response getRoles(@ApiParam(name="entity", value="角色(entity)") Role entity, @PathVariable(JKey.RID) @ApiParam(name="rid", value="用户(rid)") String rid) {
		Map<String, Object> result = new HashMap<String, Object>();
		if(entity == null || StringUtils.isEmpty(rid)){
			result.put(JKey.MSG, "参数错误！");
			return Response.status(Status.PRECONDITION_FAILED).entity(result).build();
		}
		entity.setUser(new User(rid));
		if(entity == null || StringUtils.isEmpty(entity.getUser().getId())){
			result.put(JKey.MSG, "参数错误！");
			return Response.status(Status.PRECONDITION_FAILED).entity(result).build();
		}
		Collection<Role> entitys = roleService.findList(entity);
		entity.setOper(RequestMethod.GET);
		return Response.ok(roleApiResource.getCollectionRes(entity, entitys, LinkKey.Role)).build();
	}
}
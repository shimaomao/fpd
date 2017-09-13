/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 * @Project fpd 
 * @Title RoleApiController.java
 * @Package com.wanfin.fpd.modules.sys.controller.role
 * @Description [[_xxx_]]文件 
 * @author Chenh
 * @date 2016年5月12日 下午3:08:57 
 * @version V1.0   
 */ 
package com.wanfin.fpd.modules.sys.web.role;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
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
import com.wanfin.fpd.modules.sys.entity.Role;
import com.wanfin.fpd.modules.sys.entity.User;
import com.wanfin.fpd.modules.sys.resource.role.RoleApiResource;
import com.wanfin.fpd.modules.sys.resource.user.UserApiResource;
import com.wanfin.fpd.modules.sys.service.OfficeService;
import com.wanfin.fpd.modules.sys.service.SystemService;
import com.wanfin.fpd.modules.sys.service.role.RoleApiService;
import com.wanfin.fpd.modules.sys.service.user.UserApiService;
import com.wanfin.fpd.modules.sys.utils.UserUtils;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;

/**
 * @Description [[_角色_]] RoleApiController控制器类
 * @author Chenh	
 * @version 2016-06-16
 * @date 2016年5月12日 下午3:08:57 
 */
@Controller
@Api(value = LinkKey.Role, description = "角色")
@RequestMapping(value = Link.API_PATH + LinkKey.Role)
public class RoleApiController extends BaseController{
	private RoleApiResource apiResource = new RoleApiResource();
	
	@Autowired
	private RoleApiService entityService;
	@Autowired
	private UserApiService userService;
	@Autowired
	private OfficeService officeService;

	/**
	 * @Description  GET 获取[[_角色_]]列表(getAll)
	 * 返回所有[[_角色_]]的列表（数组）
	 * 
	 * @param entity [[_角色_]]参数
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
   	@ApiOperation(value = "根据条件获取角色列表", notes = "根据条件获取角色列表", position = 1, httpMethod = "GET", response = Response.class)
	public Response get(@ApiParam(name="entity", value="角色(entity)") Role entity) {
        Collection<Role> entitys = entityService.findList(entity);
		entity.setOper(RequestMethod.GET);
        return Response.ok(apiResource.getCollectionRes(entity, entitys, LinkKey.Role)).build();
	}
	
	/**
	 * @Description GET/{id} 获取[[_角色_]](getById)
	 * 返回单个资源[[_角色_]]
	 * 
	 * @param entity [[_角色_]]参数
	 * @param id  [[_角色_]]主键
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
		@ApiResponse(code=412, message="参数错误！"),

		@ApiResponse(code=500, message="服务器内部错误！")
	})
	@RequestMapping(value = {LinkMapping.GET_BY_ID}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ApiOperation(value = "根据ID获取角色", notes = "根据用户名获取角色", position = 2, httpMethod = "GET", response = Response.class)
	public Response get(@ApiParam(name="entity", value="角色(entity)") Role entity, @PathVariable(JKey.ID) @ApiParam(name="id", value="主键(id)") String id) {
		Map<String, Object> result = new HashMap<String, Object>();
		if(StringUtils.isNotEmpty(id)){
			Role enty = entityService.get(id);
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
	 * @Description POST 新增[[_角色_]](save)
	 * 创建某个指定[[_角色_]]的信息
	 * 返回新生成的资源[[_角色_]]
	 * 
	 * @param entity [[_角色_]]参数
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
    @ApiOperation(value = "创建角色", notes = "创建角色", position = 3, httpMethod = "POST", response = Response.class)
    public Response post(@ApiParam(name="entity", value="角色(entity)") @RequestBody Role entity) {
		Map<String, Object> result = new HashMap<String, Object>();
		if(entity != null){
			if(StringUtils.isEmpty(entity.getId())){
		        entityService.save(entity);
		        entity.setOper(RequestMethod.POST);
		        return resCreated(new RoleApiResource(entity));
	    	}
			result.put(JKey.MSG, "参数错误！");
			return Response.status(Status.PRECONDITION_FAILED).entity(result).build();
		}
    	result.put(JKey.MSG, "错误请求！");
		return Response.status(Status.BAD_REQUEST).entity(result).build();
	}
	
	/**
	 * @Description PUT 修改[[_角色_]](update)
	 * 	更新某个指定[[_角色_]]的信息
	 * 	返回[[_角色_]]全部信息
	 * 
	 * @param entity [[_角色_]]参数
	 * @param id  [[_角色_]]主键
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
    @ApiOperation(value = "根据ID更新角色", notes = "根据ID更新角色", position = 4, httpMethod = "PUT", response = Response.class)
    public Response put(@ApiParam(name="entity", value="角色(entity)") @RequestBody Role entity, @PathVariable(JKey.ID) @ApiParam(name="id", value="主键(id)") String id) {
		Map<String, Object> result = new HashMap<String, Object>();
		if(entity != null){
			if(StringUtils.isNotEmpty(entity.getId())){
				entityService.save(entity);
				entity.setOper(RequestMethod.PUT);
				return Response.ok(new RoleApiResource(entity)).build();
			}
			result.put(JKey.MSG, "参数错误！");
			return Response.status(Status.PRECONDITION_FAILED).entity(result).build();
		}
    	result.put(JKey.MSG, "错误请求！");
		return Response.status(Status.BAD_REQUEST).entity(result).build();
	}
	
	/**
	 * @Description PATCH 修改[[_角色_]](update)
	 * 	更新某个指定[[_角色_]]的信息
	 * 	返回[[_角色_]]部分信息
	 * 
	 * @param entity [[_角色_]]参数
	 * @param id  [[_角色_]]主键
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
	@ApiOperation(value = "根据ID更新角色", notes = "根据ID更新角色", position = 5, httpMethod = "PATCH", response = Response.class)
   	public Response patch(@ApiParam(name="entity", value="角色(entity)") @RequestBody Role entity, @PathVariable(JKey.ID) @ApiParam(name="id", value="主键(id)") String id) {
		Map<String, Object> result = new HashMap<String, Object>();
		if(entity != null){
			if(StringUtils.isNotEmpty(entity.getId())){
				entityService.save(entity);
				entity.setOper(RequestMethod.PATCH);
				return Response.ok(new RoleApiResource(entity)).build();
			}
			result.put(JKey.MSG, "参数错误！");
			return Response.status(Status.PRECONDITION_FAILED).entity(result).build();
		}
    	result.put(JKey.MSG, "错误请求！");
		return Response.status(Status.BAD_REQUEST).entity(result).build();
	}
	
	/**
	 * @Description DELETE 删除[[_角色_]](delete)
	 * 	删除某个指定[[_角色_]]的信息
	 * 	返回空信息
	 * 
	 * @param id  [[_角色_]]主键
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
	@ApiOperation(value = "根据ID删除角色", notes = "根据ID删除角色", position = 6, httpMethod = "PATCH", response = Response.class)
	public Response delete(@PathVariable(JKey.ID) @ApiParam(name="id", value="主键(id)") String id) {
		Map<String, Object> result = new HashMap<String, Object>();
		if(StringUtils.isNotEmpty(id)){
			entityService.delete(new Role(id));
			result.put(JKey.ID, id);
			result.put(JKey.MSG, "删除成功！");
			return Response.ok().entity(result).build();
		}
		result.put(JKey.MSG, "错误请求！");
		return Response.status(Status.BAD_REQUEST).entity(result).build();
	}
	

	/************************************************************************************************
	 * 【标准关联接口】
	 * User 用户
	 ************************************************************************************************/
	/**
	 * @Description POST 新增租户[[_用户_]](save)
	 * 创建某个指定小贷租户[[_用户_]]的信息
	 * 返回新生成的资源小贷租户[[_用户_]]
	 * 
	 * @param entity 小贷租户[[_用户_]]参数
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
	@RequestMapping(value = {LinkMapping.M_User.SAVE_ADMIN_ROLE_XD}, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON)
	@ApiOperation(value = "创建小贷租户用户", notes = "创建小贷租户用户", position = 3, httpMethod = "POST", response = Response.class)
	public Response postUserAdminRoleXD(@ApiParam(name="entity", value="用户(entity)") @RequestBody User entity) {
		return postUser(entity, Cons.SysDF.DF_ADMIN_ROLE_XD);
	}

	/**
	 * @Description POST 新增担保租户[[_用户_]](save)
	 * 创建某个指定担保租户[[_用户_]]的信息
	 * 返回新生成的资源担保租户[[_用户_]]
	 * 
	 * @param entity 担保租户[[_用户_]]参数
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
	@RequestMapping(value = {LinkMapping.M_User.SAVE_ADMIN_ROLE_DB}, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON)
	@ApiOperation(value = "创建担保租户用户", notes = "创建担保租户用户", position = 3, httpMethod = "POST", response = Response.class)
	public Response postUserAdminRoleDB(@ApiParam(name="entity", value="用户(entity)") @RequestBody User entity) {
		return postUser(entity, Cons.SysDF.DF_ADMIN_ROLE_DB);
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
	@RequestMapping(value = {LinkMapping.M_User.SAVE}, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON)
    @ApiOperation(value = "创建用户", notes = "创建用户", position = 3, httpMethod = "POST", response = Response.class)
	public Response postUser(@ApiParam(name="entity", value="用户(entity)") @RequestBody User entity, @PathVariable(JKey.RID) @ApiParam(name="rid", value="角色(rid)") String rid) {
		Map<String, Object> result = new HashMap<String, Object>();
		if(entity != null){
			if(StringUtils.isEmpty(entity.getId())){
				if((entity.getCompany() == null)){
					entity.setCompany(new Office());
		    	}
				if(StringUtils.isEmpty(entity.getCompany().getId())){
					result.put(JKey.MSG, "company.id参数错误！");
					return Response.status(Status.PRECONDITION_FAILED).entity(result).build();
				}
				
				if(StringUtils.isEmpty(entity.getWtypeId())){
					result.put(JKey.MSG, "WtypeId参数错误！");
					return Response.status(Status.PRECONDITION_FAILED).entity(result).build();
				}

				Office company = officeService.getBywtypeId(entity.getCompany().getId());
				if(company == null){
					company = new Office();
					company.setName("W机构-"+entity.getName());
					company.setParent(new Office(Cons.SysDF.DF_SUPER_OFFICE));
					company.setArea(new Area(Cons.SysDF.DF_AREA));
					company.setType(Cons.SysDF.DF_SUPER_OFFICE_TYPEORG);
					company.setGrade(String.valueOf(Integer.valueOf(Cons.SysDF.DF_SUPER_OFFICE_GRADEORG)+1));
					company.setUseable(Global.YES);
					company.setCreateBy(UserUtils.getAdmin());
					company.setUpdateBy(UserUtils.getAdmin());
					company.setUseable(Global.YES);
					company.setWtypeId(entity.getCompany().getId());
					company.setContractNumber(company.getName()+"year年第number号");
					officeService.save(company);
					entity.setCompany(company);
					
					Office office = new Office();
					office.setName("综合部");
					office.setParent(company);
					office.setArea(new Area(Cons.SysDF.DF_AREA));
					office.setType(Cons.SysDF.DF_SUPER_OFFICE_TYPEDEPART);
					office.setGrade(String.valueOf(Integer.valueOf(Cons.SysDF.DF_SUPER_OFFICE_GRADEORG)+2));
					office.setContractNumber(company.getName()+"year年第number号");
					company.setCreateBy(UserUtils.getAdmin());
					company.setUpdateBy(UserUtils.getAdmin());
					office.setUseable(Global.YES);
					office.setWtypeId(null);
					officeService.save(office);
					
					entity.setOffice(office);
				}

				if(StringUtils.isEmpty(entity.getLoginName())){
					result.put(JKey.MSG, "loginName参数错误！");
					return Response.status(Status.PRECONDITION_FAILED).entity(result).build();
				}
				
				User newLUser = userService.getByLoginName(entity.getLoginName());
				if(newLUser != null){
					result.put(JKey.MSG, "账户已注册！");
					return Response.ok().entity(result).build();
				}
				
				User newUser = userService.getBywtypeId(entity.getWtypeId());
				if(newUser == null){
					if(StringUtils.isEmpty(entity.getLoginName()) || (entity.getLoginName().length() < 5)){
						result.put(JKey.MSG, "loginName为空或长度小于5位！");
						return Response.status(Status.PRECONDITION_FAILED).entity(result).build();
					}
					if(StringUtils.isEmpty(entity.getLoginName())){
						result.put(JKey.MSG, "loginName参数错误！");
						return Response.status(Status.PRECONDITION_FAILED).entity(result).build();
					}
					if(StringUtils.isEmpty(entity.getPassword())){
						entity.setPassword(SystemService.entryptPassword(entity.getLoginName()+Cons.SysDF.W_PSW_PFIX));
					}
					if(StringUtils.isEmpty(entity.getPassword())){
						entity.setPassword(SystemService.entryptPassword(entity.getLoginName()+Cons.SysDF.W_PSW_PFIX));
					}
					userService.save(entity);
					List<Role> roleList = new ArrayList<Role>();
					roleList.add(new Role(rid));
					entity.setRoleList(roleList);
			        userService.insertUserRole(entity);
			        entity.setOper(RequestMethod.POST);
					return resCreated(new UserApiResource(entity));
				}else{
					result.put(JKey.MSG, "租户已存在！");
					return Response.ok().entity(result).build();
				}
			}
			result.put(JKey.MSG, "id参数应该为空！");
			return Response.status(Status.PRECONDITION_FAILED).entity(result).build();
		}
    	result.put(JKey.MSG, "错误请求，参数不存在！");
		return Response.status(Status.BAD_REQUEST).entity(result).build();
	}
}
/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.auto.web;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wanfin.fpd.common.config.AutoUtil;
import com.wanfin.fpd.common.utils.StringUtils;
import com.wanfin.fpd.common.web.BaseController;
import com.wanfin.fpd.modules.auto.service.AutoApiService;
import com.wanfin.fpd.modules.sys.entity.Office;
import com.wanfin.fpd.modules.sys.service.OfficeService;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;

/**
 * 流程定义相关Controller
 * @author ThinkGem
 * @version 2013-11-03
 */
@Controller
@RequestMapping(value = "${adminPath}/auto")
public class AutoApiController extends BaseController {
	@Autowired
	private AutoApiService actAutoApiService;
	@Autowired
	private OfficeService officeService;

	/**
	 * 自动初始化系统基础数据
	 * @return
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
	@RequestMapping(value = "/init", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
   	@ApiOperation(value = "根据条件获取订单列表", notes = "根据条件获取订单列表", position = 1, httpMethod = "GET", response = Response.class)
	public Boolean init() {
		return actAutoApiService.init(null);
	}

	/**
	 * 自动初始化系统基础数据
	 * @return
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
	@RequestMapping(value = "/initCompany", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
   	@ApiOperation(value = "根据条件获取订单列表", notes = "根据条件获取订单列表", position = 1, httpMethod = "GET", response = Response.class)
	public Boolean initCompany(Office company) {
		company = new Office("c2003a86e5c54fd3a799b67f3c5fe58e");
		if ((company != null) && (StringUtils.isNotEmpty(company.getId()))) {
			company = officeService.get(company);
			if (company != null) {
				return actAutoApiService.initOffices(company, AutoUtil.getOfficesAdmin());
			}
		}
		return false;
	}
}
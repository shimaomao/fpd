/**  
 * @Project fpd 
 * @Title AngularFormController.java
 * @Package com.wanfin.fpd.modules.form.angular.web
 * @Description [[_自定义表单_]]文件 
 * @author Chenh
 * @date 2016年4月22日 下午5:54:44 
 * @version V1.0   
 */ 
package com.wanfin.fpd.modules.form.angular.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mangofactory.swagger.annotations.ApiIgnore;
import com.wanfin.fpd.modules.lending.entity.TLending;

/**
 * @Description [[_自定义表单_]] AngularFormController类
 * @author Chenh
 * @date 2016年4月22日 下午5:54:44 
 */
@ApiIgnore
@Controller
@RequestMapping(value = "${adminPath}/form/angular/angularForm")
public class AngularFormController {
	
	/**
	 * @Description 跳转编辑
	 * @param tLending
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @author Chenh 
	 * @date 2016年4月22日 下午6:01:17
	 */
	@RequiresPermissions("form:angular:angularForm:view")
	@RequestMapping(value = {"toForm", ""})
	public String toForm(TLending tLending, HttpServletRequest request, HttpServletResponse response, Model model) {
		return "modules/form/angular/angularFormForm";
	}
	
	/**
	 * @Description 跳转列表
	 * @param tLending
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @author Chenh 
	 * @date 2016年4月22日 下午6:01:39
	 */
	@RequiresPermissions("form:angular:angularForm:view")
	@RequestMapping(value = {"toList", ""})
	public String toList(TLending tLending, HttpServletRequest request, HttpServletResponse response, Model model) {
		return "modules/form/angular/angularFormList";
	}
	
	/**
	 * @Description 异步提交
	 * @param tLending
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @author Chenh 
	 * @date 2016年4月22日 下午6:01:39
	 */
	@RequestMapping(value = {"ajaxSave", ""})
	public String ajaxSave(TLending tLending, HttpServletRequest request, HttpServletResponse response, Model model) {
		return "modules/form/angular/angularFormList";
	}
}

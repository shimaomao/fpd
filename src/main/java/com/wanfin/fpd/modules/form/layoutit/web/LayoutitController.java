/**  
 * @Project fpd 
 * @Title LayoutitController.java
 * @Package com.wanfin.fpd.modules.form.layoutit.web
 * @Description [[_自定义布局_]]文件 
 * @author Chenh
 * @date 2016年4月26日 上午9:34:38 
 * @version V1.0   
 */ 
package com.wanfin.fpd.modules.form.layoutit.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mangofactory.swagger.annotations.ApiIgnore;
import com.wanfin.fpd.modules.lending.entity.TLending;

/**
 * @Description [[_自定义布局_]] LayoutitController类
 * @author Chenh
 * @date 2016年4月26日 上午9:34:38 
 */
@ApiIgnore
@Controller
@RequestMapping(value = "${adminPath}/form/layoutit/layoutit")
public class LayoutitController {
	@RequiresPermissions("form:layoutit:layoutitForm:view")
	@RequestMapping(value = {"toForm", ""})
	public String toForm(TLending tLending, HttpServletRequest request, HttpServletResponse response, Model model) {
		return "modules/form/layoutit/layoutitForm";
	}

	@RequiresPermissions("form:layoutit:layoutitForm:view")
	@RequestMapping(value = {"toAngularForm", ""})
	public String toAngularForm(TLending tLending, HttpServletRequest request, HttpServletResponse response, Model model) {
		return "modules/form/layoutit/layoutitAngularForm";
	}
}

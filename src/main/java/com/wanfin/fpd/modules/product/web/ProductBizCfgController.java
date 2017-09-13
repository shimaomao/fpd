/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.product.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mangofactory.swagger.annotations.ApiIgnore;
import com.wanfin.fpd.common.config.Global;
import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.web.BaseController;
import com.wanfin.fpd.common.utils.StringUtils;
import com.wanfin.fpd.modules.product.entity.ProductBizCfg;
import com.wanfin.fpd.modules.product.service.ProductBizCfgService;

/**
 * 产品业务节点管理Controller
 * @author zzm
 * @version 2016-05-09
 */
@ApiIgnore
@Controller
@RequestMapping(value = "${adminPath}/product/productBizCfg")
public class ProductBizCfgController extends BaseController {

	@Autowired
	private ProductBizCfgService productBizCfgService;
	
	@ModelAttribute
	public ProductBizCfg get(@RequestParam(required=false) String id) {
		ProductBizCfg entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = productBizCfgService.get(id);
		}
		if (entity == null){
			entity = new ProductBizCfg();
		}
		return entity;
	}
	
	@RequiresPermissions("product:productBizCfg:view")
	@RequestMapping(value = {"list", ""})
	public String list(ProductBizCfg productBizCfg, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ProductBizCfg> page = productBizCfgService.findPage(new Page<ProductBizCfg>(request, response), productBizCfg); 
		model.addAttribute("page", page);
		model.addAttribute("productBizCfg", productBizCfg);
		return "modules/product/productBizCfgList";
	}

	@RequiresPermissions("product:productBizCfg:view")
	@RequestMapping(value = "form")
	public String form(ProductBizCfg productBizCfg, Model model) {
		model.addAttribute("productBizCfg", productBizCfg);
		return "modules/product/productBizCfgForm";
	}

	@RequiresPermissions("product:productBizCfg:edit")
	@RequestMapping(value = "save")
	public String save(ProductBizCfg productBizCfg, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, productBizCfg)){
			return form(productBizCfg, model);
		}
		productBizCfgService.save(productBizCfg);
		addMessage(redirectAttributes, "保存业务节点成功");
		return "redirect:"+Global.getAdminPath()+"/product/productBizCfg/?repage";
	}
	
	@RequiresPermissions("product:productBizCfg:edit")
	@RequestMapping(value = "delete")
	public String delete(ProductBizCfg productBizCfg, RedirectAttributes redirectAttributes) {
		productBizCfgService.delete(productBizCfg);
		addMessage(redirectAttributes, "删除业务节点成功");
		return "redirect:"+Global.getAdminPath()+"/product/productBizCfg/?repage";
	}

}
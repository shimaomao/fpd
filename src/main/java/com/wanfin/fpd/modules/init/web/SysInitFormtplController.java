/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.init.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mangofactory.swagger.annotations.ApiIgnore;
import com.wanfin.fpd.common.config.Cons;
import com.wanfin.fpd.common.config.Global;
import com.wanfin.fpd.common.config.SvalBase;
import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.utils.StringUtils;
import com.wanfin.fpd.common.web.BaseController;
import com.wanfin.fpd.modules.form.entity.tpl.DfFormTpl;
import com.wanfin.fpd.modules.init.entity.SysInitFormtpl;
import com.wanfin.fpd.modules.init.service.SysInitFormtplService;

/**
 * 模板初始化Controller
 * @author zxj
 * @version 2016-09-27
 */
@ApiIgnore
@Controller
@RequestMapping(value = "${adminPath}/init/sysInitFormtpl")
public class SysInitFormtplController extends BaseController {

	@Autowired
	private SysInitFormtplService sysInitFormtplService;
	
	@ModelAttribute
	public SysInitFormtpl get(@RequestParam(required=false) String id) {
		SysInitFormtpl entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = sysInitFormtplService.get(id);
		}
		if (entity == null){
			entity = new SysInitFormtpl();
		}
		return entity;
	}
	
	@RequiresPermissions("init:sysInitFormtpl:view")
	@RequestMapping(value = {"list", ""})
	public String list(SysInitFormtpl sysInitFormtpl, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SysInitFormtpl> page = sysInitFormtplService.findPage(new Page<SysInitFormtpl>(request, response), sysInitFormtpl); 
		model.addAttribute("page", page);
		model.addAttribute("sysInitFormtpl", sysInitFormtpl);
		return "modules/init/sysInitFormtplList";
	}

	/**
	* @Description: 异步更新本地Auto模板文件
	* @author Chenh
	* @param id 可选，如果为空，使用系统默认
	* @param type 小贷|担保
	* @param tplModel 产品|业务申请
	* @return  
	* @throws
	 */
	@ResponseBody
	@RequestMapping(value = "ajaxSaveAuto")
	public Map<String, Object> ajaxSaveAuto(String id, String type, String tplModels) {
		Map<String, Object> result = new HashMap<String, Object>();
		if(StringUtils.isEmpty(type) || StringUtils.isEmpty(tplModels)){
			result.put(SvalBase.JsonKey.KEY_IS_TRUE, false);
			result.put(SvalBase.JsonKey.KEY_MSG, "没有执行任何更新！请选择要更新的租户类型和模板类型，然后点击！");
		}else{
			String[] forms = tplModels.split(";");
			if((type).equals(Cons.CompanyType.DAN_BAO)){
				type = Cons.CompanyType.DAN_BAO;
			}else if((type).equals(Cons.CompanyType.DAI_KUAN)){
				type = Cons.CompanyType.DAI_KUAN;
			}else{
				result.put(SvalBase.JsonKey.KEY_IS_TRUE, false);
				result.put(SvalBase.JsonKey.KEY_MSG, "没有执行任何更新！您选择的租户类型未定义！");
				return result;
			}
			List<DfFormTpl> filerDftpls = null;
			List<String> msgs = new ArrayList<String>();
			for (int i = 0; i < forms.length; i++) {
				String tplModel = forms[i];
				if((tplModel).equals(Cons.FModel.M_BUSINESS_APPLICATION.getKey())){
					filerDftpls = sysInitFormtplService.writeFormTplsToYWSQ(id, type);
				}else if((tplModel).equals(Cons.FModel.M_PRODUCT.getKey())){
					filerDftpls = sysInitFormtplService.writeFormTplsToProduct(id, type);
				}else{
					result.put(SvalBase.JsonKey.KEY_IS_TRUE, false);
					result.put(SvalBase.JsonKey.KEY_MSG, "没有执行任何更新！您选择的模板类型未定义！");
					break;
				}
			}
			
			if((filerDftpls != null) && (filerDftpls.size() > 0)){
				for (DfFormTpl dftpl : filerDftpls) {
					msgs.add(dftpl.getModel()+":"+dftpl.getModsub()+":"+dftpl.getSname());
				}
				result.put(SvalBase.JsonKey.KEY_IS_TRUE, true);
				result.put(SvalBase.JsonKey.KEY_LISTS, msgs);
			}else{
				result.put(SvalBase.JsonKey.KEY_LISTS, "默认模板格式不对，或者没有处理任何更新！");
			}
		}
		return result;
	}

	/**
	* @Description: 
	* 操作步骤：
	* 1、更新本地Auto模板文件;
	* 2、选择机构去更新指定模板
	* @author Chenh
	* @param sysInitFormtpl
	* @param model
	* @return  
	* @throws
	 */
	@RequiresPermissions("init:sysInitFormtpl:view")
	@RequestMapping(value = "form")
	public String form(SysInitFormtpl sysInitFormtpl, Model model) {
//		model.addAttribute("adminFormTpls",dfFormtplService.getFormTpls());
		model.addAttribute("officeList", sysInitFormtplService.getOfficeList());
		model.addAttribute("formTpls",sysInitFormtplService.getFormTpls());
		model.addAttribute("sysInitFormtpl", sysInitFormtpl);
		return "modules/init/sysInitFormtplForm";
	}

	@RequiresPermissions("init:sysInitFormtpl:edit")
	@RequestMapping(value = "save")
	public String save(SysInitFormtpl sysInitFormtpl, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, sysInitFormtpl)){
			return form(sysInitFormtpl, model);
		}
		if(sysInitFormtplService.formUpdate(sysInitFormtpl)){
			addMessage(redirectAttributes, "保存模板初始化成功");
			return "redirect:"+Global.getAdminPath()+"/init/sysInitFormtpl/?repage";
		}else{
			addMessage(redirectAttributes, "保存模板初始化失败");
			return form(sysInitFormtpl, model);
		}
	}
	
	@RequiresPermissions("init:sysInitFormtpl:view")
	@RequestMapping(value = "relate")
	public String relate(SysInitFormtpl sysInitFormtpl, Model model,HttpServletRequest req,HttpServletResponse resp) {
		model.addAttribute("DfformTpl",sysInitFormtplService.getRelateDfFormTpl(sysInitFormtpl.getRelateId()));
		model.addAttribute("sysInitFormtpl", sysInitFormtpl);
		return "modules/init/sysInitFormtplRelate";
	}
	
	@RequiresPermissions("init:sysInitFormtpl:edit")
	@RequestMapping(value = "saveRelate")
	public String saveRelate(SysInitFormtpl sysInitFormtpl, Model model, RedirectAttributes redirectAttributes,HttpServletRequest req,HttpServletResponse resp) {
		String[] relateIds = req.getParameter("relate").split(";");
		if (!beanValidator(model, sysInitFormtpl)){
			return form(sysInitFormtpl, model);
		}
		if(sysInitFormtplService.updateRelate(sysInitFormtpl, relateIds)){
			addMessage(redirectAttributes, "初始化关联模板成功");
			return "redirect:"+Global.getAdminPath()+"/init/sysInitFormtpl/?repage";
		}else{
			addMessage(redirectAttributes, "初始化关联模板失败");
			model.addAttribute("DfformTpl",sysInitFormtplService.getRelateDfFormTpl(sysInitFormtpl.getRelateId()));
			return form(sysInitFormtpl, model);
		}
	}
	
	@RequiresPermissions("init:sysInitFormtpl:edit")
	@RequestMapping(value = "delete")
	public String delete(SysInitFormtpl sysInitFormtpl, RedirectAttributes redirectAttributes) {
		sysInitFormtplService.delete(sysInitFormtpl);
		addMessage(redirectAttributes, "删除模板初始化成功");
		return "redirect:"+Global.getAdminPath()+"/init/sysInitFormtpl/?repage";
	}

}
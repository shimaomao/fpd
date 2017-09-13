/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.form.web.tpl;

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

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mangofactory.swagger.annotations.ApiIgnore;
import com.wanfin.fpd.common.config.Cons.FModel;
import com.wanfin.fpd.common.config.AutoUtil;
import com.wanfin.fpd.common.config.Cons;
import com.wanfin.fpd.common.config.Global;
import com.wanfin.fpd.common.config.SvalBase;
import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.utils.StringUtils;
import com.wanfin.fpd.common.web.BaseController;
import com.wanfin.fpd.modules.form.entity.tpl.DfFormTpl;
import com.wanfin.fpd.modules.form.service.tpl.DfFormTplService;
import com.wanfin.fpd.modules.product.service.ProductBizCfgService;

/**
 * 自定义表单Controller
 * @author chenh
 * @version 2016-05-04
 */
@ApiIgnore
@Controller
@RequestMapping(value = "${adminPath}/form/tpl/dfFormTpl")
public class DfFormTplController extends BaseController {

	@Autowired
	private DfFormTplService dfFormTplService;
	@Autowired
	private ProductBizCfgService productBizCfgService;
	
	@ModelAttribute
	public DfFormTpl get(@RequestParam(required=false) String id) {
		DfFormTpl entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = dfFormTplService.get(id);
		}
		if (entity == null){
			entity = new DfFormTpl();
		}
		return entity;
	}
	
	@RequiresPermissions("form:tpl:dfFormTpl:view")
	@RequestMapping(value = {"list", ""})
	public String list(DfFormTpl dfFormTpl, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<DfFormTpl> page = dfFormTplService.findTplPage(new Page<DfFormTpl>(request, response), dfFormTpl); 
		model.addAttribute("page", page);
		model.addAttribute("dfFormTpl", dfFormTpl);
		return "modules/form/tpl/dfFormTplList";
	}

	@RequiresPermissions("form:tpl:dfFormTpl:view")
	@RequestMapping(value = {"rellist"})
	public String rellist(DfFormTpl dfFormTpl, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<DfFormTpl> page = dfFormTplService.findRelTplPage(new Page<DfFormTpl>(request, response), dfFormTpl); 
		model.addAttribute("page", page);
		model.addAttribute("dfFormTpl", dfFormTpl);
		return "modules/form/tpl/dfFormTplListRel";
	}

	@RequiresPermissions("form:tpl:dfFormTpl:view")
	@RequestMapping(value = {"syslist"})
	public String syslist(DfFormTpl dfFormTpl, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<DfFormTpl> page = dfFormTplService.findSysTplPage(new Page<DfFormTpl>(request, response), dfFormTpl); 
		model.addAttribute("page", page);
		model.addAttribute("dfFormTpl", dfFormTpl);
		return "modules/form/tpl/dfFormTplListSys";
	}

	@RequiresPermissions("form:tpl:dfFormTpl:view")
	@RequestMapping(value = "form")
	public String form(DfFormTpl dfFormTpl, Model model) {
		model.addAttribute("dfFormTpl", dfFormTpl);
		return "modules/form/tpl/dfFormTplForm";
	}
	
	@RequestMapping(value = "toFormTpl")
	public String toFormTpl(String bizCode, HttpServletRequest request, HttpServletResponse response, Model model) {
		DfFormTpl dfFormTpl = dfFormTplService.getByBizCode(bizCode);
		return "redirect:"+Global.getAdminPath()+"/form/tpl/dfFormTpl/viewData?id="+dfFormTpl.getId();
	}

	@RequiresPermissions("form:tpl:dfFormTpl:view")
	@RequestMapping(value = "view")
	public String view(DfFormTpl dfFormTpl, Model model) {
		dfFormTpl = dfFormTplService.get(dfFormTpl);
		model.addAttribute("dfFormTpl", dfFormTpl);
		return "modules/form/tpl/dfFormTplView";
	}
	
	/**
	* @Description:预览模板
	* 1、支持ID查看
	* 2、支持Json文件查看（传类型和模型类型） 
	* @author Chenh
	* @param id
	* @param type
	* @param tplModel
	* @param model
	* @return  
	* @throws
	 */
	@RequestMapping(value = "ajaxView")
	public String ajaxView(String id, String type, String tplModel, Model model) {
		if(StringUtils.isNotEmpty(id)){
			return view(new DfFormTpl(id), model);
		}else{
			DfFormTpl dfFormTpl = null;
			List<DfFormTpl> dfFormTpls = null;
			if((type).equals("1")){
				type = Cons.SysDF.DF_ADMIN_ROLE_DB;
				dfFormTpls = AutoUtil.getFormTplOrgDb();
			}else if((type).equals("2")){
				type = Cons.SysDF.DF_ADMIN_ROLE_XD;
				dfFormTpls = AutoUtil.getFormTplOrgXd();
			}
			
			for (DfFormTpl dffTpl : dfFormTpls) {
				if(dffTpl.getModel().equals(tplModel)){
					dfFormTpl = dffTpl;
					break;
				}
			}
			model.addAttribute("dfFormTpl", dfFormTpl);
			return "modules/form/tpl/dfFormTplView";
		}
	}
	
	@RequestMapping(value = "viewData")
	public String viewData(DfFormTpl dfFormTpl, Model model) {
		dfFormTpl = dfFormTplService.get(dfFormTpl);
		if(dfFormTpl != null){
			FModel fm = FModel.getFModelByKey(dfFormTpl.getModel());
			model.addAttribute("action", fm.getAction());
		}
		model.addAttribute("dfFormTpl", dfFormTpl);
		return "modules/form/tpl/dfFormTplViewData";
	}

	@RequiresPermissions("form:tpl:dfFormTpl:edit")
	@RequestMapping(value = "save")
	public String save(DfFormTpl dfFormTpl, Model model, RedirectAttributes redirectAttributes, String isNew) {
		if (!beanValidator(model, dfFormTpl)){
			return form(dfFormTpl, model);
		}
		/**
		 * 作为新模板保存
		 */
		if(StringUtils.isNotEmpty(isNew) && (dfFormTpl != null) && (StringUtils.isNotEmpty(dfFormTpl.getId()))){
			DfFormTpl newDfFormTpl = dfFormTplService.get(dfFormTpl.getId());
			newDfFormTpl.setId(null);
			newDfFormTpl.setName(null);
			newDfFormTpl.setSname(dfFormTpl.getSname());
			if(StringUtils.isNotEmpty(dfFormTpl.getModsub())){
				newDfFormTpl.setModsub(dfFormTpl.getModsub());
			}
			newDfFormTpl.setOrganId(null);
			newDfFormTpl.setRemarks(null);
			dfFormTplService.save(newDfFormTpl);
			model.addAttribute("dfFormTpl", newDfFormTpl);
			addMessage(redirectAttributes, "保存自定义表单成功");
			return "modules/form/tpl/dfFormTplForm";
		}else{
			dfFormTplService.save(dfFormTpl);
			addMessage(redirectAttributes, "保存自定义表单成功");
			return "redirect:"+Global.getAdminPath()+"/form/tpl/dfFormTpl/?repage";
		}
	}
	
	@RequiresPermissions("form:tpl:dfFormTpl:edit")
	@RequestMapping(value = "delete")
	public String delete(DfFormTpl dfFormTpl, RedirectAttributes redirectAttributes) {
		dfFormTplService.delete(dfFormTpl);
		addMessage(redirectAttributes, "删除自定义表单成功");
		return "redirect:"+Global.getAdminPath()+"/form/tpl/dfFormTpl/?repage";
	}
	

	/**
	 * @Description 获取模板JSON数据
	 * @param extId 排除的ID
	 * @param category 模板分类 （product：产品录入表单模板，business_application：业务申请表单，employee、company：客户信息录入表单）
	 * @return
	 * @author zzm 
	 * @date 2016-5-5 下午2:51:17  
	 */
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String extId, @RequestParam(required=false) String category) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		DfFormTpl dfFormTpl = new DfFormTpl();
		dfFormTpl.setModel(category);
		List<DfFormTpl> list = dfFormTplService.findTplList(dfFormTpl);
		for (int i=0; i<list.size(); i++){
			DfFormTpl e = list.get(i);
			if (StringUtils.isBlank(extId) || (!extId.equals(e.getId()) && 
					(e.getParent() !=null && !extId.equals(e.getParent().getId())))){
				Map<String, Object> map = Maps.newHashMap(); 
				map.put("id", e.getId());
				map.put("pId", (e.getParent() == null ? category : e.getParent().getId()));
				map.put("name", e.getSname());
				mapList.add(map);
			}
		}
		return mapList;
	}
	
	/**
	 * 系统自生成模板的新模板保存
	 * @param dfFormTpl
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("form:tpl:dfFormTpl:edit")
	@RequestMapping(value = "isNew")
	public String isNew(DfFormTpl dfFormTpl, Model model, RedirectAttributes redirectAttributes) {
		if((dfFormTpl != null) && (StringUtils.isNotEmpty(dfFormTpl.getId()))){
			DfFormTpl newDfFormTpl = dfFormTplService.get(dfFormTpl.getId());
			newDfFormTpl.setId(null);
			newDfFormTpl.setName(null);
			newDfFormTpl.setSname(dfFormTpl.getSname());
			if(StringUtils.isNotEmpty(dfFormTpl.getModsub())){
				newDfFormTpl.setModsub(dfFormTpl.getModsub());
			}
			newDfFormTpl.setOrganId(null);
			newDfFormTpl.setRemarks(null);
			dfFormTplService.save(newDfFormTpl);
			addMessage(redirectAttributes, "保存新模板成功");
		}else{
			addMessage(redirectAttributes, "保存新模板失败");
		}
		return "redirect:"+Global.getAdminPath()+"/form/tpl/dfFormTpl/?repage";
	}
}
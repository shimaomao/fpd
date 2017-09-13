/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.advisory.web;

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
import com.wanfin.fpd.common.config.Cons;
import com.wanfin.fpd.common.config.Cons.FModel;
import com.wanfin.fpd.common.config.Global;
import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.utils.IdGen;
import com.wanfin.fpd.common.utils.StringUtils;
import com.wanfin.fpd.common.web.BaseController;
import com.wanfin.fpd.modules.advisory.entity.TAdvisory;
import com.wanfin.fpd.modules.advisory.service.TAdvisoryService;
import com.wanfin.fpd.modules.form.entity.tpl.DfFormTpl;
import com.wanfin.fpd.modules.form.service.tpl.DfFormTplService;

/**
 * 业务受理咨询Controller
 * @author cdy
 * @version 2016-03-15
 */
@ApiIgnore
@Controller
@RequestMapping(value = "${adminPath}/advisory/tAdvisory")
public class TAdvisoryController extends BaseController {

	@Autowired
	private TAdvisoryService tAdvisoryService;
	
	@Autowired
	private DfFormTplService dfFormTplService;
	
	@ModelAttribute
	public TAdvisory get(@RequestParam(required=false) String id) {
		TAdvisory entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = tAdvisoryService.get(id);
		}
		if (entity == null){
			entity = new TAdvisory();
		}
		return entity;
	}
	
	@RequiresPermissions("advisory:tAdvisory:view")
	@RequestMapping(value = {"list", ""})
	public String list(TAdvisory tAdvisory, HttpServletRequest request, HttpServletResponse response, Model model) {
		/*tAdvisory.setProduct(new TProduct((String)UserUtils.getCache(UserUtils.CACHE_SYSCODE))); by zzm 20160520 授信暂定不放大产品里面*/
		Page<TAdvisory> page = tAdvisoryService.findPage(new Page<TAdvisory>(request, response), tAdvisory); 
		model.addAttribute("page", page);
		model.addAttribute("tAdvisory",tAdvisory);
		return "modules/advisory/tAdvisoryList";
	}

	@RequiresPermissions("advisory:tAdvisory:view")
	@RequestMapping(value = "form")
	public String form(TAdvisory tAdvisory, Model model) {
		FModel fm = FModel.M_ADVISORY;
		//更改为固定表单
		DfFormTpl dfFormTpl = dfFormTplService.get(Cons.FormTplId.B_CONTRACT_ADVISORY_ID);
//		DfFormTpl dfFormTpl = dfFormTplService.getByBizCode(fm.getKey());
		if(StringUtils.isBlank(tAdvisory.getId())){
			tAdvisory.setId("tmp_"+IdGen.uuid());
		}else{
			model.addAttribute("id", tAdvisory.getId());//解决修改数据时找不到id 会新增数据bug
		}
		
		model.addAttribute("action", fm.getAction());
		model.addAttribute("dfFormTpl", dfFormTpl);
		model.addAttribute("data", tAdvisory);
		return "modules/advisory/tAdvisoryForm";
	}

	@RequiresPermissions("advisory:tAdvisory:edit")
	@RequestMapping(value = "save")
	public String save(TAdvisory tAdvisory, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, tAdvisory)){
			return form(tAdvisory, model);
		}
		tAdvisoryService.save(tAdvisory);
		addMessage(redirectAttributes, "保存受理咨询成功");
		return "redirect:"+Global.getAdminPath()+"/advisory/tAdvisory/?repage";
	}
	
	@RequiresPermissions("advisory:tAdvisory:edit")
	@RequestMapping(value = "delete")
	public String delete(TAdvisory tAdvisory, RedirectAttributes redirectAttributes) {
		tAdvisoryService.delete(tAdvisory);
		addMessage(redirectAttributes, "删除受理咨询成功");
		return "redirect:"+Global.getAdminPath()+"/advisory/tAdvisory/?repage";
	}

}
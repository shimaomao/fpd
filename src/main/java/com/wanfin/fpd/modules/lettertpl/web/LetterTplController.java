/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.lettertpl.web;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.HtmlUtils;

import com.google.common.collect.Maps;
import com.mangofactory.swagger.annotations.ApiIgnore;
import com.wanfin.fpd.common.config.Cons;
import com.wanfin.fpd.common.config.Global;
import com.wanfin.fpd.common.config.SvalBase;
import com.wanfin.fpd.common.mapper.JsonMapper;
import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.utils.FreeMarkers;
import com.wanfin.fpd.common.utils.StringUtils;
import com.wanfin.fpd.common.utils.Tool;
import com.wanfin.fpd.common.web.BaseController;
import com.wanfin.fpd.modules.contract.entity.TLoanContract;
import com.wanfin.fpd.modules.contract.service.TLoanContractService;
import com.wanfin.fpd.modules.lettertpl.entity.LetterTpl;
import com.wanfin.fpd.modules.lettertpl.service.LetterTplService;

/**
 * 函件模板Controller
 * @author zzm
 * @version 2016-06-08
 */
@ApiIgnore
@Controller
@RequestMapping(value = "${adminPath}/lettertpl/letterTpl")
public class LetterTplController extends BaseController {

	@Autowired
	private LetterTplService letterTplService;
	@Autowired
	private TLoanContractService tLoanContractService;
	
	@ModelAttribute
	public LetterTpl get(@RequestParam(required=false) String id) {
		LetterTpl entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = letterTplService.get(id);
		}
		if (entity == null){
			entity = new LetterTpl();
		}
		return entity;
	}
	
	@RequiresPermissions("lettertpl:letterTpl:view")
	@RequestMapping(value = {"list", ""})
	public String list(LetterTpl letterTpl, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<LetterTpl> page = letterTplService.findPage(new Page<LetterTpl>(request, response), letterTpl); 
		model.addAttribute("page", page);
		model.addAttribute("letterTpl", letterTpl);
		return "modules/lettertpl/letterTplList";
	}

	@RequiresPermissions("lettertpl:letterTpl:view")
	@RequestMapping(value = "form")
	public String form(LetterTpl letterTpl, Model model) {
		model.addAttribute("letterTpl", letterTpl);
		return "modules/lettertpl/letterTplForm";
	}

	@RequiresPermissions("lettertpl:letterTpl:edit")
	@RequestMapping(value = "save")
	public String save(LetterTpl letterTpl, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, letterTpl)){
			return form(letterTpl, model);
		}
		letterTplService.save(letterTpl);
		addMessage(redirectAttributes, "保存函件模板成功");
		return "redirect:"+Global.getAdminPath()+"/lettertpl/letterTpl/?repage";
	}
	
	@RequiresPermissions("lettertpl:letterTpl:edit")
	@RequestMapping(value = "delete")
	public String delete(LetterTpl letterTpl, RedirectAttributes redirectAttributes) {
		letterTplService.delete(letterTpl);
		addMessage(redirectAttributes, "删除函件模板成功");
		return "redirect:"+Global.getAdminPath()+"/lettertpl/letterTpl/?repage";
	}
	
	/**
	 * @Description 跳转查看合同
	 * @param request
	 * @param response
	 * @param model
	 * @param tLoanContract
	 * @return
	 * @author Chenh 
	 * @date 2016年3月30日 上午11:02:10
	 */
	@RequestMapping(value = "toView")
	public String toView(HttpServletRequest request, Model model, String id, String ukey) {
		LetterTpl letterTpl = new LetterTpl();
		letterTpl.setType(ukey);
		List<LetterTpl> letterTpls = letterTplService.findList(letterTpl);
		model.addAttribute("letterTpls", letterTpls);
		model.addAttribute("id", id);
		model.addAttribute("ukey", ukey);
		return "/modules/lettertpl/letterTplListView";
	}
	
	/**
	 * @Description 跳转预览合同页面
	 * @param request
	 * @param response
	 * @param model
	 * @param tLoanContract
	 * @return
	 * @author Chenh 
	 * @date 2016年3月30日 下午4:13:14
	 */
	@RequestMapping(value = "view")
	public String view(LetterTpl letterTpl, String rid, RedirectAttributes redirectAttributes, Model model) {
		String content = "";
		StringBuffer msg = new StringBuffer();
		Map<String,Object> result = new HashMap<String, Object>();

		if(StringUtils.isBlank(letterTpl.getContent())){
			msg.append("函件模板有误!");
		}else if(StringUtils.isNotEmpty(rid) && StringUtils.isNotEmpty(letterTpl.getType())){
			Map<String,String> params = null;
			try {
				if(StringUtils.equals(Cons.LetterTplType.HASTEN_REPAY_RECORD, letterTpl.getType())){
					params = toMap(new JSONObject(JsonMapper.toJsonString(tLoanContractService.get(rid))));
				}else if(StringUtils.equals(Cons.LetterTplType.HASTEN_DANBAO_RECORD, letterTpl.getType())){
					TLoanContract contract = tLoanContractService.get(rid);
					contract.setCapitalsAmount(Tool.change(Double.parseDouble(contract.getLoanAmount())));
					params = toMap(new JSONObject(JsonMapper.toJsonString(contract)));
				}else{
					msg.append("函件类型标识未知!");
				}
				if(params != null ){
					try {
						content = FreeMarkers.renderString(HtmlUtils.htmlUnescape(StringUtils.trimToEmpty(letterTpl.getContent())), params);
					} catch (Exception e) {
						e.printStackTrace();
						msg.append("函件模板有误!");
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
				msg.append("数据获取异常!");
			}
			result.put(SvalBase.JsonKey.KEY_PARAMS, params);
		}else{
			msg.append("数据获取异常!");
		}

		if(msg.length() == 0){
			result.put(SvalBase.JsonKey.KEY_CONTENT, content);
			model.addAttribute(SvalBase.JsonKey.KEY_RESULT, result);
			return "/modules/lettertpl/letterTplView";
		}else{
			redirectAttributes.addAttribute("id", rid);
			redirectAttributes.addAttribute("ukey", letterTpl.getType());
			addMessage(redirectAttributes, msg.toString());
			return "redirect:"+Global.getAdminPath()+"/lettertpl/letterTpl/toView?repage";
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public Map<String,String> toMap(JSONObject jsonObject) throws JSONException{
		Map<String, String> result = Maps.newHashMap(); 
        Iterator<String> iterator = jsonObject.keys(); 
        String key = null; 
        String value = null; 
        while (iterator.hasNext()) 
        { 
            key = iterator.next(); 
            value = jsonObject.getString(key); 
            result.put(key, value); 
        } 
        return result; 
	}
}
/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.postlending.web.fivelevel;

import java.util.HashMap;
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
import com.wanfin.fpd.common.web.BaseController;
import com.wanfin.fpd.common.utils.IdGen;
import com.wanfin.fpd.common.utils.StringUtils;
import com.wanfin.fpd.modules.contract.entity.TLoanContract;
import com.wanfin.fpd.modules.contract.service.TLoanContractService;
import com.wanfin.fpd.modules.postlending.entity.fivelevel.FiveLevel;
import com.wanfin.fpd.modules.postlending.service.fivelevel.FiveLevelService;
import com.wanfin.fpd.modules.product.entity.ProductBizCfg;
import com.wanfin.fpd.modules.product.service.ProductBizCfgService;

/**
 * 五级分类Controller
 * @author srf
 * @version 2016-04-14
 */
@ApiIgnore
@Controller
@RequestMapping(value = "${adminPath}/postlending/fivelevel/fiveLevelBak")
public class FiveLevelControllerBak extends BaseController {
	// 合同
	@Autowired
	private TLoanContractService tLoanContractService;
		
	@Autowired
	private FiveLevelService fiveLevelService;

	@Autowired
	private ProductBizCfgService productBizCfgService;
	
	@ModelAttribute
	public FiveLevel get(@RequestParam(required=false) String id) {
		FiveLevel entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = fiveLevelService.get(id);
		}
		if (entity == null){
			entity = new FiveLevel();
		}
		return entity;
	}
	
	@RequiresPermissions("postlending:fivelevel:fiveLevel:view")
	@RequestMapping(value = {"list", ""})
	public String list(FiveLevel fiveLevel, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<FiveLevel> page = fiveLevelService.findPage(new Page<FiveLevel>(request, response), fiveLevel); 
		model.addAttribute("page", page);
		model.addAttribute("fiveLevel", fiveLevel);
		return "modules/postlending/fivelevel/fiveLevelList";
	}

	@RequiresPermissions("postlending:fivelevel:fiveLevel:view")
	@RequestMapping(value = "form")
	public String form(FiveLevel fiveLevel, Model model) {
		String view ="fiveLevelForm";
		ProductBizCfg productBizCfg = productBizCfgService.getByBizCode(Cons.FModel.M_BUSINESS_APPLICATION_WJFL.getKey());
		if(StringUtils.isBlank(fiveLevel.getId())){
			if(productBizCfg == null){
				addMessage(model, "无此业务!");
				return "redirect:" + adminPath + "/postlending/fivelevel/fiveLevel/";
			}
			if(StringUtils.isBlank(productBizCfg.getProcDefId())){
				addMessage(model, "此业务没配置流程!");
				return "redirect:" + adminPath + "/postlending/fivelevel/fiveLevel/";
			}
			//设置配置的流程
			fiveLevel.getAct().setProcDefId(productBizCfg.getProcDefId());
		}
		
		if(fiveLevel != null && fiveLevel.getLoanContractId() == null){
			//屏蔽直接从流程中启动
			if(fiveLevel.getAct().getProcInsId() == null || "".equals(fiveLevel.getAct().getProcInsId())){
				addMessage(model, "不允许从流程中直接启动!");
				return "redirect:" + adminPath + "/act/task/process/";
			}
		}
		//审批处理 
		if(fiveLevel != null){
		//if(fiveLevel != null){
			if(fiveLevel.getAct().isFinishTask() && StringUtils.isBlank(fiveLevel.getLoanContractId())){
				FiveLevel temp = fiveLevel;
				fiveLevel = fiveLevelService.getByProcInsId(fiveLevel.getAct().getProcInsId());
				fiveLevel.setAct(temp.getAct());
				temp = null;
				view ="fiveLevelView";
			}else if("audit".equals(fiveLevel.getAct().getTaskDefKey())){//审核处理
				view ="fiveLevelAudit";
			}
		}
		
		if(StringUtils.isBlank( fiveLevel.getId() )){
			//生成临时的ID
			fiveLevel.setId( "tmp_"+IdGen.uuid() );
		}
		
		//获取合同信息
		if(fiveLevel.getLoanContractId() != null && !"".equals(fiveLevel.getLoanContractId())){
			TLoanContract loanContract = tLoanContractService.get(fiveLevel.getLoanContractId());
			
			fiveLevel.setOldFiveLevel(loanContract.getFiveLevel());
			fiveLevel.setLoanContract(loanContract);
		}
		
		model.addAttribute("fiveLevel", fiveLevel);
		return "modules/postlending/fivelevel/"+view;
	}

	@RequiresPermissions("postlending:fivelevel:fiveLevel:edit")
	@RequestMapping(value = "save")
	@ResponseBody
	public Map<String, Object> save(FiveLevel fiveLevel, Model model, RedirectAttributes redirectAttributes) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put(SvalBase.JsonKey.KEY_STATUS, true);
		if (StringUtils.isBlank(fiveLevel.getRemarks())){
			result.put(SvalBase.JsonKey.KEY_PARAMS, "paramError");
			result.put(SvalBase.JsonKey.KEY_MSG, "请填写评估依据内容！");
			return result;
		}else if(StringUtils.isBlank(fiveLevel.getFiveLevel())){
			result.put(SvalBase.JsonKey.KEY_PARAMS, "paramError");
			result.put(SvalBase.JsonKey.KEY_MSG, "请选择分类！");
			return result;
		}
		fiveLevelService.save(fiveLevel);
		
		result.put(SvalBase.JsonKey.KEY_MSG, "提交五级分类信息成功！");
		return result;
	}
	
	@RequiresPermissions("postlending:fivelevel:fiveLevel:edit")
	@RequestMapping(value = "saveAudit")
	public String saveAudit(FiveLevel fiveLevel, Model model, RedirectAttributes redirectAttributes) {
		if (StringUtils.isBlank(fiveLevel.getAct().getComment())){
			addMessage(model, "请填写审核意见!");
			return form(fiveLevel, model);
		}
		fiveLevelService.auditSave(fiveLevel);
		
		return "redirect:" + adminPath + "/act/task/todo/";
	}
	
	@RequiresPermissions("postlending:fivelevel:fiveLevel:edit")
	@RequestMapping(value = "delete")
	public String delete(FiveLevel fiveLevel, RedirectAttributes redirectAttributes) {
		fiveLevelService.delete(fiveLevel);
		addMessage(redirectAttributes, "删除五级分类成功");
		return "redirect:"+Global.getAdminPath()+"/postlending/fivelevel/fiveLevel/?repage";
	}

}
/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.postlending.web.usetracking;

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

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.mangofactory.swagger.annotations.ApiIgnore;
import com.wanfin.fpd.common.config.Cons;
import com.wanfin.fpd.common.config.Global;
import com.wanfin.fpd.common.config.SvalBase;
import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.utils.IdGen;
import com.wanfin.fpd.common.utils.StringUtils;
import com.wanfin.fpd.common.web.BaseController;
import com.wanfin.fpd.modules.company.entity.TCompany;
import com.wanfin.fpd.modules.company.service.TCompanyService;
import com.wanfin.fpd.modules.contract.entity.TLoanContract;
import com.wanfin.fpd.modules.contract.service.TLoanContractService;
import com.wanfin.fpd.modules.employee.entity.TEmployee;
import com.wanfin.fpd.modules.employee.service.TEmployeeService;
import com.wanfin.fpd.modules.postlending.entity.usetracking.ActualPurpose;
import com.wanfin.fpd.modules.postlending.service.usetracking.ActualPurposeService;
import com.wanfin.fpd.modules.product.entity.ProductBizCfg;
import com.wanfin.fpd.modules.product.entity.TProduct;
import com.wanfin.fpd.modules.product.service.ProductBizCfgService;
import com.wanfin.fpd.modules.sys.utils.DictUtils;
import com.wanfin.fpd.modules.sys.utils.UserUtils;

/**
 * 用途跟踪Controller
 * @author srf
 * @version 2016-04-09
 */
@ApiIgnore
@Controller
@RequestMapping(value = "${adminPath}/postlending/usetracking/actualPurpose")
public class ActualPurposeController extends BaseController {

	// 合同
	@Autowired
	private TLoanContractService tLoanContractService;
	//用途跟踪
	@Autowired
	private ActualPurposeService actualPurposeService;

	@Autowired
	private ProductBizCfgService productBizCfgService;
	@Autowired
	private TEmployeeService tEmployeeService;
	@Autowired
	private TCompanyService tCompanyService;
	
	@ModelAttribute
	public ActualPurpose get(@RequestParam(required=false) String id) {
		ActualPurpose entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = actualPurposeService.get(id);
		}
		if (entity == null){
			entity = new ActualPurpose();
		}
		return entity;
	}
	
	@RequiresPermissions("postlending:usetracking:actualPurpose:view")
	@RequestMapping(value = {"list", ""})
	public String list(ActualPurpose actualPurpose, HttpServletRequest request, HttpServletResponse response, Model model) {
		actualPurpose.setProduct(new TProduct((String)UserUtils.getCache(UserUtils.CACHE_SYSCODE)));
		Page<ActualPurpose> page = actualPurposeService.findPage(new Page<ActualPurpose>(request, response), actualPurpose); 
		model.addAttribute("page", page);
		model.addAttribute("actualPurpose", actualPurpose);
		return "modules/postlending/usetracking/actualPurposeList";
	}

	@RequiresPermissions("postlending:usetracking:actualPurpose:view")
	@RequestMapping(value = "detail")
	public String detail(ActualPurpose actualPurpose, Model model) {
//		System.out.println("=="+actualPurpose.getId());
		actualPurpose = actualPurposeService.get(actualPurpose.getId());
		model.addAttribute("actualPurpose", actualPurpose);
		return "modules/postlending/usetracking/actualPurposeView2";
	}
	
	@RequiresPermissions("postlending:usetracking:actualPurpose:view")
	@RequestMapping(value = "form")
	public String form(ActualPurpose actualPurpose, Model model) {
		String view = "actualPurposeForm";
		if(StringUtils.isBlank(actualPurpose.getId())){
			ProductBizCfg productBizCfg = productBizCfgService.getByBizCode(Cons.FModel.M_BUSINESS_APPLICATION_DHJC.getKey());
			if(productBizCfg == null){
				addMessage(model, "无此业务!");
				return "redirect:" + adminPath + "/postlending/usetracking/actualPurpose/";
			}
			if(StringUtils.isBlank(productBizCfg.getProcDefId())){
				addMessage(model, "此业务没配置流程!");
				return "redirect:" + adminPath + "/postlending/usetracking/actualPurpose/";
			}
			//设置配置的流程
			actualPurpose.getAct().setProcDefId(productBizCfg.getProcDefId());
		}
		if(actualPurpose != null && actualPurpose.getLoanContractId() == null){
			//屏蔽直接从流程中启动
			if(actualPurpose.getAct().getProcInsId() == null || "".equals(actualPurpose.getAct().getProcInsId())){
				addMessage(model, "不允许从流程中直接启动!");
				return "redirect:" + adminPath + "/act/task/process/";
			}
		}
		//处理审批内容
		if(actualPurpose.getAct().isFinishTask() && StringUtils.isBlank(actualPurpose.getLoanContractId())){
			ActualPurpose temp = actualPurpose;
			actualPurpose = actualPurposeService.getByProcInsId(actualPurpose.getAct().getProcInsId());
			actualPurpose.setAct(temp.getAct());
			temp = null;
			
			view = "actualPurposeView";
		}else if(StringUtils.isNotBlank(actualPurpose.getAct().getProcInsId())){//流程已启动，进入审批界面
			view = "actualPurposeAudit";
		}
		
		if(StringUtils.isBlank(actualPurpose.getId())){
			//生成临时的ID
			actualPurpose.setId("tmp_"+IdGen.uuid());
		}
		//获取对应的合同信息，用户数据也从里边获取
		if( StringUtils.isNotBlank(actualPurpose.getLoanContractId())){
			//合同信息 t_loan_contract
			TLoanContract loanContract = tLoanContractService.get(actualPurpose.getLoanContractId());
			actualPurpose.setContractNumber(loanContract.getContractNumber());
			actualPurpose.setCustomerName(loanContract.getCustomerName());
			actualPurpose.setCustomerType(loanContract.getCustomerType());
			actualPurpose.setCustomerId(loanContract.getCustomerId());
			actualPurpose.setPurpose(DictUtils.getDictLabel(loanContract.getPurposeId(), "product_purpose", ""));
			if(StringUtils.isNotBlank(actualPurpose.getCustomerId())){
				Record record = Db.findFirst("SELECT count(*) num from t_loan_contract where if(is_extend is null,0,is_extend) != '1' and del_flag = '0' and customer_id = '"+actualPurpose.getCustomerId()+"'");
				actualPurpose.setLoanNum(record.getLong("num").intValue());
				TEmployee employee = tEmployeeService.get(loanContract.getCustomerId());
				if(employee != null){
					actualPurpose.setAddress(employee.getCurrentLiveAddress());
					actualPurpose.setCardNum(employee.getCardNum());	
					actualPurpose.setCardType("3");	
				}else{
					TCompany company = tCompanyService.get(loanContract.getCustomerId());
					if(company != null){
						actualPurpose.setAddress(company.getAddress());	
						actualPurpose.setCardNum(company.getCardNum());	
						actualPurpose.setCardType(company.getCardType());	
					}
				}
			}
			
		}
		
		model.addAttribute("actualPurpose", actualPurpose);
		return "modules/postlending/usetracking/"+view;
	}

	@RequiresPermissions("postlending:usetracking:actualPurpose:edit")
	@RequestMapping(value = "save")
	@ResponseBody
	public Map<String, Object> save(ActualPurpose actualPurpose, Model model, RedirectAttributes redirectAttributes) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put(SvalBase.JsonKey.KEY_STATUS, true);
		if (!beanValidator(model, actualPurpose)){
			result.put(SvalBase.JsonKey.KEY_MSG, "请正确填写内容！");
			return result;
		}
		
		//保存内容并启动流程
		actualPurposeService.save(actualPurpose);
		
		result.put(SvalBase.JsonKey.KEY_MSG, "提交用途跟踪成功！");
		return result;
	}
	
	@RequiresPermissions("postlending:usetracking:actualPurpose:edit")
	@RequestMapping(value = "saveAudit")
	public String saveAudit(HttpServletRequest request, HttpServletResponse response, Model model, ActualPurpose actualPurpose){
		if(StringUtils.isBlank(actualPurpose.getAct().getFlag()) || StringUtils.isBlank(actualPurpose.getAct().getComment())){//|| StringUtils.isBlank(actualPurpose.getAct().getComment())
			addMessage(model, "请填写负责人意见!");
			return form(actualPurpose, model);
		}
		actualPurposeService.auditSave(actualPurpose);
		
		return "redirect:" + adminPath + "/act/task/todo/";
	}
	
	@RequiresPermissions("postlending:usetracking:actualPurpose:edit")
	@RequestMapping(value = "delete")
	public String delete(ActualPurpose actualPurpose, RedirectAttributes redirectAttributes) {
		actualPurposeService.delete(actualPurpose);
		addMessage(redirectAttributes, "删除用途跟踪成功");
		return "redirect:"+Global.getAdminPath()+"/postlending/usetracking/actualPurpose/?repage";
	}

}
/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.contract.web;

import java.util.HashMap;
import java.util.List;

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

import com.google.common.collect.Maps;
import com.mangofactory.swagger.annotations.ApiIgnore;
import com.wanfin.fpd.common.config.Cons;
import com.wanfin.fpd.common.config.Global;
import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.utils.IdGen;
import com.wanfin.fpd.common.utils.StringUtils;
import com.wanfin.fpd.common.web.BaseController;
import com.wanfin.fpd.modules.contract.entity.ExtendContract;
import com.wanfin.fpd.modules.contract.entity.TLoanContract;
import com.wanfin.fpd.modules.contract.service.ExtendContractService;
import com.wanfin.fpd.modules.contract.service.TLoanContractService;
import com.wanfin.fpd.modules.repayplan.entity.TRepayPlan;
import com.wanfin.fpd.modules.repayplan.service.TRepayPlanService;

/**
 * 展期合同Controller
 * @author zzm
 * @version 2016-04-01
 */
@ApiIgnore
@Controller
@RequestMapping(value = "${adminPath}/contract/extendContract")
public class ExtendContractController extends BaseController {

	@Autowired
	private ExtendContractService extendContractService;
	@Autowired
	private TLoanContractService tLoanContractService;
	@Autowired
	private TRepayPlanService tRepayPlanService;
	
	@ModelAttribute
	public ExtendContract get(@RequestParam(required=false) String id) {
		ExtendContract entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = extendContractService.get(id);
		}
		if (entity == null){
			entity = new ExtendContract();
		}
		return entity;
	}
	
	@RequiresPermissions("contract:extendContract:view")
	@RequestMapping(value = {"list", ""})
	public String list(ExtendContract extendContract, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ExtendContract> page = extendContractService.findPage(new Page<ExtendContract>(request, response), extendContract); 
		model.addAttribute("page", page);
		model.addAttribute("extendContract", extendContract);
		return "modules/contract/extendContractList";
	}

	/**
	 * @Description 跳转到展期申请页面
	 * @param extendContract
	 * @param model
	 * @return
	 * @author zzm 
	 * @date 2016-4-6 上午10:50:32  
	 */
//	@RequiresPermissions("contract:extendContract:view")
	@RequestMapping(value = "form")
	public String form(ExtendContract extendContract, Model model) {
		if(StringUtils.isBlank(extendContract.getId()))
			//新增时设置一个临时id，用于关联其它表的数据（不同步保存的，如：附件）
			extendContract.setId("new_"+IdGen.uuid());
		int overdueCount = 0;//未还期数
		
		if(extendContract.getLoanContract()!= null 
				&& StringUtils.isNotBlank(extendContract.getLoanContract().getId())){
			//贷款合同信息
			extendContract.setLoanContract(tLoanContractService.get(extendContract.getLoanContract().getId()));
			//原还款计划
			TRepayPlan repayPlan = new TRepayPlan();
			repayPlan.setLoanContractId(extendContract.getLoanContract().getId());
			List<TRepayPlan> list = tRepayPlanService.findList(repayPlan);
			extendContract.getLoanContract().setRepayPlanList(list);
			if(list != null && extendContract.getAmount() == null ){
				Double amount = 0.0;
				HashMap<Object, Object> numMap = Maps.newHashMap();
				for(TRepayPlan p : list){
					if(Cons.RepayStatus.NO_PAID.equals(p.getStatus())){
						//未还的本金
						amount+=Double.valueOf(p.getPrincipal());
						numMap.put(p.getNum(), p.getNum());
					}
				}
			
				if(extendContract.getPayType().equals("5")){//到期一次性还本付息,未还期数等于贷款期数不可等于1，否则页面js还本金日期错误
					TLoanContract tct=tLoanContractService.get(extendContract.getLoanContract().getId());
					overdueCount =Integer.parseInt(tct.getLoanPeriod());
				}else{
					overdueCount = numMap.size();
				}
				extendContract.setAmount(amount);
			}
		}
		
		model.addAttribute("extendContract", extendContract);
		model.addAttribute("overdueCount", overdueCount);
		return "modules/contract/extendContractForm";
//		return "modules/contract/extendContractAudit";
	}

	/**
	 * @Description 提交展期申请
	 * @param extendContract
	 * @param model
	 * @param redirectAttributes
	 * @return
	 * @author zzm
	 * @date 2016-4-6 上午10:51:43  
	 */
	@RequiresPermissions("contract:extendContract:edit")
	@RequestMapping(value = "save")
	public String save(ExtendContract extendContract, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, extendContract)){
			return form(extendContract, model);
		}
		extendContractService.save(extendContract);
		addMessage(redirectAttributes, "保存展期申请成功");
		return "redirect:"+Global.getAdminPath()+"/contract/extendContract/form?repage";
	}
	
	@RequiresPermissions("contract:extendContract:edit")
	@RequestMapping(value = "delete")
	public String delete(ExtendContract extendContract, RedirectAttributes redirectAttributes) {
		extendContractService.delete(extendContract);
		addMessage(redirectAttributes, "删除展期申请成功");
		return "redirect:"+Global.getAdminPath()+"/contract/extendContract/?repage";
	}

}
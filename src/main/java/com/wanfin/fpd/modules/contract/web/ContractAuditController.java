/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.contract.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.FormService;
import org.activiti.engine.form.FormProperty;
import org.activiti.engine.form.TaskFormData;
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
import com.wanfin.fpd.common.config.Cons.FileType;
import com.wanfin.fpd.common.config.Global;
import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.utils.StringUtils;
import com.wanfin.fpd.common.web.BaseController;
import com.wanfin.fpd.modules.act.entity.Act;
import com.wanfin.fpd.modules.contract.entity.BusinessContract;
import com.wanfin.fpd.modules.contract.entity.ContractAudit;
import com.wanfin.fpd.modules.contract.entity.TLoanContract;
import com.wanfin.fpd.modules.contract.entity.tpl.TContractTpl;
import com.wanfin.fpd.modules.contract.service.BusinessContractService;
import com.wanfin.fpd.modules.contract.service.ContractAuditService;
import com.wanfin.fpd.modules.contract.service.TLoanContractService;
import com.wanfin.fpd.modules.contract.service.tpl.TContractTplService;
import com.wanfin.fpd.modules.files.service.TContractFilesService;
import com.wanfin.fpd.modules.form.entity.tpl.DfFormTpl;
import com.wanfin.fpd.modules.form.service.tpl.DfFormTplService;
import com.wanfin.fpd.modules.product.entity.ProductBizCfg;
import com.wanfin.fpd.modules.product.service.ProductBizCfgService;
import com.wanfin.fpd.modules.sys.utils.UserUtils;

/**
 * 签订合同审核流程Controller
 * @author srf
 * @version 2016-12-27
 */
@ApiIgnore
@Controller
@RequestMapping(value = "${adminPath}/contract/contractAudit")
public class ContractAuditController extends BaseController {
	@Autowired
	private ContractAuditService contractAuditService;//合同审核流程
	@Autowired
	private DfFormTplService dfFormTplService;//表单模板
	@Autowired
	private ProductBizCfgService productBizCfgService;//获取配置的流程信息
	@Autowired
	private FormService formService;//获取流程表单内容
	@Autowired
	private TContractTplService tContractTplService;//合同模板
	@Autowired
	private TLoanContractService loanContractService;//业务信息
	@Autowired
	private BusinessContractService BusinessContractService;//业务申请相关合同
	@Autowired
	private TContractFilesService contractFilesService;//附件
	@Autowired
	private TLoanContractService tLoanContractService;
	@Autowired
	private TContractFilesService tContractFilesService;
	
	@ModelAttribute
	public ContractAudit get(@RequestParam(required=false) String id) {
		ContractAudit entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = contractAuditService.get(id);
		}
		if (entity == null){
			entity = new ContractAudit();
		}
		return entity;
	}
	
	@RequiresPermissions("contract:contractAudit:view")
	@RequestMapping(value = {"list", ""})
	public String list(ContractAudit contractAudit, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ContractAudit> page = contractAuditService.findPage(new Page<ContractAudit>(request, response), contractAudit); 
		model.addAttribute("page", page);
		model.addAttribute("contractAudit", contractAudit);
		return "modules/contract/contractAuditList";
	}

	@RequiresPermissions("contract:contractAudit:view")
	@RequestMapping(value = "form")
	public String form(ContractAudit contractAudit, Model model) {
		model.addAttribute("contractAudit", contractAudit);
		return "modules/contract/contractAuditForm";
	}

	@RequiresPermissions("contract:contractAudit:edit")
	@RequestMapping(value = "save")
	public String save(ContractAudit contractAudit, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, contractAudit)){
			return form(contractAudit, model);
		}
		if(contractAudit.getLoanContract() == null || StringUtils.isBlank(contractAudit.getLoanContract().getId())){
			model.addAttribute("message", "对应合同不能为空");
			return form(contractAudit, model);
		}
		contractAuditService.save(contractAudit);
		addMessage(redirectAttributes, "保存签订合同审核流程成功");
		return "redirect:"+Global.getAdminPath()+"/contract/contractAudit/?repage";
	}
	
	@RequiresPermissions("contract:contractAudit:edit")
	@RequestMapping(value = "delete")
	public String delete(ContractAudit contractAudit, RedirectAttributes redirectAttributes) {
		contractAuditService.delete(contractAudit);
		addMessage(redirectAttributes, "删除签订合同审核流程成功");
		return "redirect:"+Global.getAdminPath()+"/contract/contractAudit/?repage";
	}
	
	//签订合同
	/**
	 * @Description 跳转签订合同
	 * @param request
	 * @param response
	 * @param model
	 * @param tLoanContract
	 * @return
	 * @author Chenh TODO
	 * @date 2016年3月30日 上午11:02:34
	 */
	@RequiresPermissions("contract:tLoanContract:view")
	@RequestMapping(value = "toSign")
	public String toSign(ContractAudit contractAudit, HttpServletRequest request, HttpServletResponse response, Model model) {
		TLoanContract loanContract = null; 
		if(contractAudit == null){// || contractAudit.getLoanContract()==null
			return "redirect:"+Global.getAdminPath()+"/contract/tLoanContract/?repage";//返回合同列表
		}
		String ifEdit = "view";
		
		//编辑合同用
		BusinessContract businessContract = new BusinessContract();
		
		String tab = request.getParameter("tab");
//		System.out.println("TLoanContractController toSign->tab:"+tab);
		if(tab == null || "info".equals(tab)){//基本信息
			
		}else if("files".equals(tab)){//合同及附件信息
			//有合同信息才行
			if(contractAudit.getLoanContract() != null && StringUtils.isNotBlank(contractAudit.getLoanContract().getId())){
				//获取所有贷款合同的模板ID及标题
				TContractTpl tContractTpl = new TContractTpl();
				tContractTpl.setOrganId(UserUtils.getUser().getCompany().getId());
				tContractTpl.setType("1");
				tContractTpl.setFtlStatus("1");
				List<TContractTpl> contractTplList = tContractTplService.findList(tContractTpl);
				
				//businessContract.setContractAuditId(contractAudit.getId());
				businessContract.setLoanContract(contractAudit.getLoanContract());
				List<BusinessContract> businessContractList = BusinessContractService.findList(businessContract);
				
				model.addAttribute("contractTplList", contractTplList);
				model.addAttribute("businessContractList", businessContractList);
				model.addAttribute("contractAudit", contractAudit);
			}
		}else if("lc".equals(tab)){//流程审核相关信息
			
		}
		//StringUtils.isBlank(contractAudit.getId()) && 
		if(contractAudit.getLoanContract() != null && StringUtils.isNotBlank(contractAudit.getLoanContract().getId())){
			//获取合同信息
			loanContract = loanContractService.get(contractAudit.getLoanContract().getId());
			if(loanContract != null){
				contractAudit.setLoanContract(loanContract);
			}
			//获取流程信息
			ContractAudit tmp = contractAuditService.getByLoanContract(contractAudit);
			if(tmp != null){
				if(StringUtils.isBlank(contractAudit.getId())){
					contractAudit.setId(tmp.getId());
				}
				if(contractAudit.getAct() == null){
					contractAudit.setAct(new Act());
				}
				if(StringUtils.isNotBlank(tmp.getProcInsId()) && StringUtils.isBlank(contractAudit.getAct().getProcInsId())){
					contractAudit.getAct().setProcInsId(tmp.getProcInsId());
				}
				//contractAudit = tmp;//error
			}
		}
		//业务的模板：显示合同内容用
		FModel fm = FModel.M_BUSINESS_APPLICATION_YWSQ;
		DfFormTpl dfFormTpl = dfFormTplService.getByBizCode(fm.getKey());
		model.addAttribute("dfFormTpl", dfFormTpl);
		
		//获取对应合同审核的流程
		ProductBizCfg productBizCfg = productBizCfgService.getByBizCode(Cons.FModel.M_BUSINESS_APPLICATION_QDHT.getKey());
		if(productBizCfg != null && contractAudit.getAct() != null){
			contractAudit.getAct().setProcDefId(productBizCfg.getProcDefId());
		}
		
		if(StringUtils.isBlank(contractAudit.getId())){
			ifEdit = "edit";
		}
		if(contractAudit.getAct() != null && StringUtils.isNotBlank(contractAudit.getAct().getStatus()) && "finish".equals(contractAudit.getAct().getStatus())){
			ifEdit = "view";
		}
		
		String taskId = contractAudit.getAct().getTaskId();
		if(StringUtils.isNotBlank(taskId)){
			TaskFormData taskFormData = formService.getTaskFormData(taskId);
			if(taskFormData != null){
				for (FormProperty p : taskFormData.getFormProperties()) {
					String getFormPropertyId = p.getId();
					String getFormPropertyname= p.getName();
					contractAudit.setSubType(getFormPropertyname);
					if("approved".equals(getFormPropertyId) && StringUtils.isNotBlank(getFormPropertyname)){
						model.addAttribute("subType", getFormPropertyname);
						if(StringUtils.isBlank(getFormPropertyname)){
							
						}else if("start".equals(getFormPropertyname)){
							ifEdit = "edit";
						}else if("end".equals(getFormPropertyname)){
							ifEdit = "upload";
						}
					}
				}
			}
		}
		
		model.addAttribute("ifEdit", ifEdit);//调查报告是否可以编辑
		model.addAttribute("businessContract", businessContract);
		model.addAttribute("contractAudit", contractAudit);
		model.addAttribute("loanContract", contractAudit.getLoanContract());
		model.addAttribute("act", contractAudit.getAct());
		
		return "/modules/contract/tLoanContractSign";
	}
	
	/**
	 * 保存业务合同内容
	 * @param request
	 * @param response
	 * @param model
	 * @param contractAudit
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("contract:tLoanContract:edit")
	@RequestMapping(value = "saveContract")
	public String saveContract(BusinessContract businessContract, HttpServletRequest request, HttpServletResponse response, Model model, RedirectAttributes redirectAttributes) {
		ContractAudit contractAudit = new ContractAudit();
		if(businessContract == null || businessContract.getLoanContract() == null || StringUtils.isAnyBlank(businessContract.getLoanContract().getId(), businessContract.getTplId(), businessContract.getContractName(), businessContract.getContractContent())){
			model.addAttribute("message", "请选定合同模板列表中的合同模板或已生成的合同，编辑后生成");
		}else{
			/*
			//通过业务ID和模板ID查询是否存在，如果已经存在则覆盖，否则插入(用于限制合同的生成 **屏蔽**)
			BusinessContract tempBusinessContract = BusinessContractService.getByCondition(businessContract);
			if(tempBusinessContract != null && StringUtils.isNotBlank(tempBusinessContract.getId())){
				businessContract.setId(tempBusinessContract.getId());
			}
			*/
			BusinessContractService.save(businessContract);
			contractAudit.setLoanContract(businessContract.getLoanContract());
			ContractAudit tmp = contractAuditService.getByLoanContract(contractAudit);
			if(tmp != null){
				contractAudit = contractAuditService.getByLoanContract(contractAudit);
			}
		}
		//contractAudit.setAct(businessContract.getAct());
		model.addAttribute("tab", "files");
		return toSign(contractAudit, request, response, model);
	}
	
	/**
	 * 删除业务合同内容
	 * @param contractAudit
	 * @param businessContract
	 * @param request
	 * @param response
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("contract:tLoanContract:edit")
	@RequestMapping(value = "delBusinessContract")
	public String delBusinessContract(@RequestParam("businessContractId") String businessContractId, HttpServletRequest request, HttpServletResponse response, Model model, RedirectAttributes redirectAttributes) {
		if(StringUtils.isBlank(businessContractId)){
			return "redirect:"+Global.getAdminPath()+"/contract/tLoanContract/?repage";
		}else{
			BusinessContract businessContract = BusinessContractService.get(businessContractId);
			if(businessContract == null){
				return "redirect:"+Global.getAdminPath()+"/contract/tLoanContract/?repage";
			}else{
				ContractAudit contractAudit = new ContractAudit();
				contractAudit.setLoanContract(businessContract.getLoanContract());
				BusinessContractService.delete(businessContract);
				
				return toSign(contractAudit, request, response, model);
			}
		}
	}
	
	/**
	 * @Description 签订合同提交审核
	 * @param request
	 * @param response
	 * @param model
	 * @param tLoanContract
	 * @param redirectAttributes
	 * @return
	 * @author Chenh 
	 * @date 2016年3月30日 上午11:02:53
	 */
	@RequiresPermissions("contract:tLoanContract:edit")
	@RequestMapping(value = "sign")
	public String sign(ContractAudit contractAudit, HttpServletRequest request, HttpServletResponse response, Model model, RedirectAttributes redirectAttributes) {
		if( StringUtils.isBlank(contractAudit.getLoanContract().getId()) 
				|| contractAudit.getLoanContract() == null ){
			model.addAttribute("message", "提交异常");
			return toSign(contractAudit, request, response, model);
		}	
		
		if(tContractFilesService.getByTaskId(contractAudit.getLoanContract().getId(), FileType.FILE_TYPE_LOANCONTRACT_1_1).size()<1){
			model.addAttribute("message", "未添加附件（合同）");
			return toSign(contractAudit, request, response, model);
		}
		
		
		TLoanContract loanContract = loanContractService.get(contractAudit.getLoanContract().getId());
		/**
		 * 签订合同默认状态：1-待评审-走评审制
		 * */
		//如果是担保，合同签订之后就是待收费状态
		if(UserUtils.getUser().getCompany().getPrimaryPerson().equals(Cons.CompanyType.DAN_BAO)){
			loanContract.setStatus(Cons.LoanContractStatus.DB_TO_CHARGE);
		}else{
			if(loanContract.getIsdirectLoan()!=null && loanContract.getIsdirectLoan().equals("1")){
				loanContract.setStatus(Cons.LoanContractStatus.TO_LOAN);
			}else{
				loanContract.setStatus(Cons.LoanContractStatus.TO_LOAN_APPROVAL);
			}
		}
		tLoanContractService.save(loanContract);
		addMessage(redirectAttributes, "保存签订合同成功");
		
		return "redirect:"+Global.getAdminPath()+"/contract/tLoanContract/?repage";
	}
}
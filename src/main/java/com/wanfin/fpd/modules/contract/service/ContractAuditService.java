/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.contract.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wanfin.fpd.common.config.Cons;
import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.service.CrudService;
import com.wanfin.fpd.common.service.ServiceException;
import com.wanfin.fpd.common.utils.StringUtils;
import com.wanfin.fpd.modules.contract.entity.ContractAudit;
import com.wanfin.fpd.modules.contract.entity.TLoanContract;
import com.wanfin.fpd.modules.sys.service.SystemService;
import com.wanfin.fpd.modules.sys.utils.UserUtils;
import com.wanfin.fpd.modules.act.service.ActTaskService;
import com.wanfin.fpd.modules.contract.dao.ContractAuditDao;

/**
 * 签订合同审核流程Service
 * @author srf
 * @version 2016-12-27
 */
@Service
@Transactional(readOnly = true)
public class ContractAuditService extends CrudService<ContractAuditDao, ContractAudit> {
	@Autowired
	private ActTaskService actTaskService;
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private SystemService systemService;//
	@Autowired
	private TLoanContractService loanContractService;//合同信息
	
	public ContractAudit get(String id) {
		return super.get(id);
	}
	
	public List<ContractAudit> findList(ContractAudit contractAudit) {
		return super.findList(contractAudit);
	}
	
	public Page<ContractAudit> findPage(Page<ContractAudit> page, ContractAudit contractAudit) {
		return super.findPage(page, contractAudit);
	}
	
	@Transactional(readOnly = false)
	public void save(ContractAudit contractAudit) {
		super.save(contractAudit);
	}
	
	@Transactional(readOnly = false)
	public void delete(ContractAudit contractAudit) {
		super.delete(contractAudit);
	}

	public ContractAudit getByLoanContract(ContractAudit contractAudit) {
		return dao.getByLoanContract(contractAudit);
	}

	@Transactional(readOnly = false)
	public void saveAudit(ContractAudit contractAudit) {
		super.save(contractAudit);
		TLoanContract loanContract = null;//同步更新合同状态
		if(StringUtils.isBlank(contractAudit.getProcInsId())){
			if (StringUtils.isNotBlank(contractAudit.getAct().getProcDefId())) {
				contractAudit.getAct()
						.setProcDef(actTaskService.getProcessByProcDefId(contractAudit.getAct().getProcDefId()));
			}

			if (StringUtils.isBlank(contractAudit.getAct().getProcDefKey())) {
				throw new ServiceException("没有指定流程");
			}
			
			if(StringUtils.isBlank(contractAudit.getLoanContract().getContractNumber())){
				//获取合同信息
				loanContract = loanContractService.get(contractAudit.getLoanContract().getId());
				if(loanContract != null){
					contractAudit.setLoanContract(loanContract);
				}
			}
			
			if(contractAudit.getAct() == null){
				System.out.println("Act() is null");
			}
			if(contractAudit.getAct().getVars() == null){
				System.out.println("Vars is null");
			}
			if(contractAudit.getAct().getVars().getVariableMap() == null){
				System.out.println("VariableMap is null");
			}
			
			//当前节点任务处理人
			//对于部分业务部在一个人不能同时属于多个部门的情况下先写死
			//营业务部分管领导
			/*List<String> khzjList = systemService.findDealLoginName(UserUtils.getUser().getId(), "khzj", true);
			///营业部负责人
			List<String> yybfzrList = systemService.findDealLoginName(UserUtils.getUser().getId(), "yybfzr", true);
			Map<String, Object> variables = new HashMap<String, Object>();//TODO
			variables.put("startUser", UserUtils.getUser().getLoginName());//发起人
*/			//variables.put("khzjUsers", khzjList);//客户总监 
		/*	variables.put("yybfzrUsers", yybfzrList);//营业部负责人yybfzrUsers
			variables.put("khzjUsers", khzjList);//营业务部分管领导 khzjUsers
*/			
			// 启动并提交流程
			String procInsId = actTaskService.startProcess(contractAudit.getAct().getProcDefKey(), "t_contract_audit",
					contractAudit.getId(), contractAudit.getLoanContract().getContractNumber()+"合同审核");//contractAudit.getAct().getVars().getVariableMap()
			
			//下一节点任务处理人
			//creditApply.getAct().getVars().getVariableMap().put("next", creditApply.getAct().getAssigneeName());
			actTaskService.completeFirstTask(procInsId, contractAudit.getAct().getComment(),
					contractAudit.getLoanContract().getContractNumber()+"合同审核", contractAudit.getAct().getVars().getVariableMap());
			//用于更新合同状态为：合同审核中
			loanContract = loanContractService.get(contractAudit.getLoanContract().getId());
			loanContract.setStatus(Cons.LoanContractStatus.CONTRACT_AUDIT);//合同审核中
			loanContractService.save(loanContract);//
		}else{//
			if(!contractAudit.getAct().getFlag().equals("-1")){//同意或驳回
				 // 设置意见
				String flag = contractAudit.getAct().getFlag();
			     String comment = "";
			     if("1".equals(flag)){
			    	 comment = "[同意]" + contractAudit.getAct().getComment();
			     }else if("-1".equals(flag)){
			    	 comment = "[拒绝(终止)]" + contractAudit.getAct().getComment();
			     }else if("0".equals(flag)){
			    	 comment = "[驳回]" + contractAudit.getAct().getComment();
			     }else if("9".equals(flag)){
			    	 comment = "[退回发起人]" + contractAudit.getAct().getComment();
			     }else{
			    	 comment = contractAudit.getAct().getComment();
			     }
			     
			     contractAudit.getAct().setComment(comment);
			     // 执行任务
			     actTaskService.complete(contractAudit.getAct().getTaskId(), contractAudit.getAct().getProcInsId(),
			    		 contractAudit.getAct().getComment(), contractAudit.getAct().getVars().getVariableMap());
			}else{//终止任务
				actTaskService.overTask(contractAudit.getAct().getTaskId(),contractAudit.getAct().getProcInsId(),contractAudit.getAct().getComment());
				
				contractAudit.setStatus(Cons.LoanContractStatus.ENDED);
				loanContract = loanContractService.get(contractAudit.getLoanContract().getId());
				loanContract.setStatus(Cons.LoanContractStatus.ENDED);//业务申请失败
				loanContractService.save(loanContract);//合同结束掉
			}
		}
		ProcessInstance pi = runtimeService.createProcessInstanceQuery()
				.processInstanceId(contractAudit.getAct().getProcInsId()).singleResult();
		if (pi != null || contractAudit.getAct().getFlag().equals("-1")) {
			super.save(contractAudit);
		}
	}
	
}
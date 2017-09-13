/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.postlending.service.baddebts;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Maps;
import com.jfinal.plugin.activerecord.Db;
import com.wanfin.fpd.common.config.Cons;
import com.wanfin.fpd.common.config.SvalBase.JsonKey;
import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.service.CrudService;
import com.wanfin.fpd.common.utils.StringUtils;
import com.wanfin.fpd.modules.act.service.ActTaskService;
import com.wanfin.fpd.modules.contract.entity.TLoanContract;
import com.wanfin.fpd.modules.contract.service.TLoanContractService;
import com.wanfin.fpd.modules.postlending.dao.baddebts.BadDebtRegistDao;
import com.wanfin.fpd.modules.postlending.entity.baddebts.BadDebtRegist;

/**
 * 坏账处理Service
 * @author srf
 * @version 2016-04-15
 */
@Service
@Transactional(readOnly = true)
public class BadDebtRegistService extends CrudService<BadDebtRegistDao, BadDebtRegist> {
	@Autowired
	private ActTaskService actTaskService;
	
	@Autowired
	private TLoanContractService loanContractService;
	
	@Autowired
	private TLoanContractService tLoanContractService;
	
	@Autowired
	private RuntimeService runtimeService;
	
	public BadDebtRegist get(String id) {
		return super.get(id);
	}
	
	public BadDebtRegist getByProcInsId(String  procInsId){
		return dao.getByProcInsId(procInsId);
	}
	
	public List<BadDebtRegist> findList(BadDebtRegist badDebtRegist) {
		return super.findList(badDebtRegist);
	}
	
	public Page<BadDebtRegist> findPage(Page<BadDebtRegist> page, BadDebtRegist badDebtRegist) {
		return super.findPage(page, badDebtRegist);
	}
	
	@Transactional(readOnly = false)
	public void save(BadDebtRegist badDebtRegist) {
		super.save(badDebtRegist);
		String procDefKey = badDebtRegist.getAct().getProcDefKey();
		String businessTable = "t_bad_debt_regist";
	    TLoanContract contract=tLoanContractService.get(badDebtRegist.getLoanContractId());
		String title = "【坏账核销】" + contract.getContractNumber();
		// 首次提交
		if (StringUtils.isBlank(badDebtRegist.getAct().getProcInsId())) {
			String procInsId = actTaskService.startProcess(procDefKey, businessTable, badDebtRegist.getId(), title);
			if (StringUtils.isNotBlank(procInsId))
				actTaskService.completeFirstTask(procInsId, badDebtRegist.getAct().getComment(), title,null);
			    badDebtRegist.getAct().setProcInsId(procInsId);
				this.save(badDebtRegist);
		}
		
	}
	
	@Transactional(readOnly = false)
	public void saveAudit(BadDebtRegist badDebtRegist){
		//设置意见
		badDebtRegist.getAct().setComment(("yes".equals(badDebtRegist.getAct().getFlag())?"[同意]":"[驳回]") + badDebtRegist.getAct().getComment());
		//更新合同状态
		Map<String, Object> vars = Maps.newHashMap();
		if("yes".equals(badDebtRegist.getAct().getFlag())){
		    vars.put("pass", "1");
		}else if("no".equals(badDebtRegist.getAct().getFlag())){
		    vars.put("pass", "0");
		}else if("stop".equals(badDebtRegist.getAct().getFlag())){
			 vars.put("pass", "-1");
		}
		//提交流程任务
		if(!"stop".equals(badDebtRegist.getAct().getFlag())){
			actTaskService.complete(badDebtRegist.getAct().getTaskId(), badDebtRegist.getAct().getProcInsId(), badDebtRegist.getAct().getComment(), vars);
		}else{
			badDebtRegist.setApprovalStatis(Cons.ContractStatus.NOT_PASS);
		}
		//判断流程是否结束
		ProcessInstance pi = runtimeService.createProcessInstanceQuery()
				.processInstanceId(badDebtRegist.getAct().getProcInsId()).singleResult();
	    if(pi==null){
	    	TLoanContract contract=tLoanContractService.get(badDebtRegist.getLoanContractId());
	    	contract.setStatus(Cons.LoanContractStatus.CANCEL);//核销
	    	tLoanContractService.save(contract);
	        System.out.println("流程已经结束");  
	    }  
	    else{  
	        System.out.println("流程没有结束");  
	    }  
		super.save(badDebtRegist);
		
	}
	
	@Transactional(readOnly = false)
	public void delete(BadDebtRegist badDebtRegist) {
		super.delete(badDebtRegist);
	}

	public Map<String, Object> ajaxUpdate(String id) {
		
		Map<String, Object> result = new HashMap<String, Object>();
		StringBuffer msg = new StringBuffer();
		Boolean status = false;
		
		TLoanContract tLoanContract = loanContractService.get(id);
	
		if (tLoanContract != null){
			if(tLoanContract.getFiveLevel().equals("5")){//五级分类为损失才可处理
				Db.update("update t_loan_contract set is_deal = '1'");
				status = true;
			}else{
				msg.append("该合同五级分类还未审批至损失。");
				status = false;
			}
		}else{
			status = false;
		}
		result.put(JsonKey.KEY_STATUS, status);
		result.put(JsonKey.KEY_MSG, msg);
		return result;
	}

}
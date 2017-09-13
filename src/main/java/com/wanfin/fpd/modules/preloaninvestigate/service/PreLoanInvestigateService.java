/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.preloaninvestigate.service;

import java.util.List;
import java.util.Map;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Maps;
import com.wanfin.fpd.common.config.Cons;
import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.service.CrudService;
import com.wanfin.fpd.common.service.ServiceException;
import com.wanfin.fpd.common.utils.StringUtils;
import com.wanfin.fpd.modules.act.service.ActTaskService;
import com.wanfin.fpd.modules.contract.entity.TLoanContract;
import com.wanfin.fpd.modules.preloaninvestigate.dao.PreLoanInvestigateDao;
import com.wanfin.fpd.modules.preloaninvestigate.entity.PreLoanInvestigate;
import com.wanfin.fpd.modules.sys.utils.UserUtils;

/**
 * 立项调查Service
 * @author zzm
 * @version 2016-06-13
 */
@Service
@Transactional(readOnly = true)
public class PreLoanInvestigateService extends CrudService<PreLoanInvestigateDao, PreLoanInvestigate> {

	
	@Autowired
	private ActTaskService actTaskService;
	@Autowired
	private RuntimeService runtimeService;
	
	public PreLoanInvestigate get(String id) {
		return super.get(id);
	}
	
	public List<PreLoanInvestigate> findList(PreLoanInvestigate preLoanInvestigate) {
		return super.findList(preLoanInvestigate);
	}
	
	public Page<PreLoanInvestigate> findPage(Page<PreLoanInvestigate> page, PreLoanInvestigate preLoanInvestigate) {
		// 生成数据权限过滤条件（dsf为dataScopeFilter的简写，在xml中使用 ${sqlMap.dsf}调用权限SQL）
		preLoanInvestigate.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser().getCurrentUser(), "o", "u"));
		return super.findPage(page, preLoanInvestigate);
	}
	
	@Transactional(readOnly = false)
	public void save(PreLoanInvestigate preLoanInvestigate) throws ServiceException {
//		PreLoanInvestigate searchItem = new PreLoanInvestigate();
//		searchItem.setLoanContract(new TLoanContract(preLoanInvestigate.getLoanContract().getId()));
//		if(preLoanInvestigate.getIsNewRecord()){
//			List<PreLoanInvestigate> list = findList(searchItem);
//			if(list != null && list.size() > 0 ){
//				throw new ServiceException("业务【"+preLoanInvestigate.getLoanContract().getContractNumber()+"】已有立项调查！");
//			}
//		}else{
//			PreLoanInvestigate oldObj = get(preLoanInvestigate.getId());
//			if(!StringUtils.equals(oldObj.getLoanContract().getId(), preLoanInvestigate.getLoanContract().getId())){
//				List<PreLoanInvestigate> list = findList(searchItem);
//				if(list != null && list.size() > 0 ){
//					throw new ServiceException("业务【"+preLoanInvestigate.getLoanContract().getContractNumber()+"】已有立项调查！");
//				}	
//			}
//		}
		super.save(preLoanInvestigate);
	}
	
	@Transactional(readOnly = false)
	public void delete(PreLoanInvestigate preLoanInvestigate) {
		super.delete(preLoanInvestigate);
	}
	
	
	
	@Transactional(readOnly = false)
	public void startProcess(PreLoanInvestigate preLoanInvestigate) {
		Map<String, Object> vars = Maps.newHashMap();
		preLoanInvestigate.getAct().setVars(vars);
		//当前节点任务处理人
		//preLoanInvestigate.getAct().getVars().getVariableMap().put("next", UserUtils.getUser().getLoginName());
		// 启动流程
		String procInsId = actTaskService.startProcess(preLoanInvestigate.getAct().getProcDefKey(), "t_pre_loan_investigate", preLoanInvestigate.getId(), preLoanInvestigate.getInvestigateNumber(),preLoanInvestigate.getAct().getVars().getVariableMap());
		if(StringUtils.isNotBlank(procInsId)){
			//下一节点任务处理人
			preLoanInvestigate.getAct().getVars().getVariableMap().put("next", preLoanInvestigate.getAct().getAssigneeName());
			actTaskService.completeFirstTask(procInsId, preLoanInvestigate.getAct().getComment(),preLoanInvestigate.getInvestigateNumber(), preLoanInvestigate.getAct().getVars().getVariableMap());
		}
		preLoanInvestigate.setStatus(Cons.PreLoanInvesStatus.TO_APPROVE);
		updateStatus(preLoanInvestigate);
	}           
	
	
	
	/**
	 * 更新合同状态
	 * add by shirf 20160406
	 * @param tLoanContract 合同
	 * @return
	 */
	@Transactional(readOnly = false)
	public int updateStatus(PreLoanInvestigate preLoanInvestigate){
		return super.dao.updateStatus(preLoanInvestigate);
	}
	
	/**
	 * 审核审批保存
	 * @param tLoopLoan
	 */
	@SuppressWarnings("unused")
	@Transactional(readOnly = false)
	public void auditSave(PreLoanInvestigate preLoanInvestigate) {
		
		// 设置意见
		preLoanInvestigate.getAct().setComment(("yes".equals(preLoanInvestigate.getAct().getFlag())?"[同意] ":"[驳回] ")+preLoanInvestigate.getAct().getComment());
		preLoanInvestigate.preUpdate();
		// 对不同环节的业务逻辑进行操作
		String taskDefKey = preLoanInvestigate.getAct().getTaskDefKey();
		// 提交流程任务
		Map<String, Object> vars = Maps.newHashMap();
		if("yes".equals(preLoanInvestigate.getAct().getFlag())){
			vars.put("pass", "1");
		}else if("no".equals(preLoanInvestigate.getAct().getFlag())){
			vars.put("pass", "0");
		}else if("stop".equals(preLoanInvestigate.getAct().getFlag())){
			vars.put("pass", "-1");
		}
		
		if(!"stop".equals(preLoanInvestigate.getAct().getFlag())){//非终止操作
			//下一节点任务处理人
			//vars.put("next", preLoanInvestigate.getAct().getAssigneeName());
			actTaskService.complete(preLoanInvestigate.getAct().getTaskId(), preLoanInvestigate.getAct().getProcInsId(), preLoanInvestigate.getAct().getComment(), vars);
		}else{
			 actTaskService.overTask(preLoanInvestigate.getAct().getTaskId(),preLoanInvestigate.getAct().getProcInsId(),preLoanInvestigate.getAct().getComment());
			 preLoanInvestigate.setStatus(Cons.PreLoanInvesStatus.TO_DISAGREE);//审核终止
			
		}	
		ProcessInstance pi = runtimeService.createProcessInstanceQuery()
				.processInstanceId(preLoanInvestigate.getAct().getProcInsId()).singleResult();
		if (pi != null || preLoanInvestigate.getAct().getFlag().equals("stop")) {
			this.updateStatus(preLoanInvestigate);
		}
	}
	
}
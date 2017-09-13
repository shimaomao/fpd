/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.refund.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.activiti.engine.runtime.ProcessInstance;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Maps;
import com.wanfin.fpd.common.config.Cons;
import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.service.CrudService;
import com.wanfin.fpd.modules.act.service.ActTaskService;
import com.wanfin.fpd.modules.contract.entity.TLoanContract;
import com.wanfin.fpd.modules.contract.service.TLoanContractService;
import com.wanfin.fpd.modules.refund.dao.ReimburseDao;
import com.wanfin.fpd.modules.refund.entity.Reimburse;
import com.wanfin.fpd.modules.sys.utils.UserUtils;

/**
 * 申请退款Service
 * @author srf
 * @version 2016-04-06
 */
@Service
@Transactional(readOnly = true)
public class ReimburseService extends CrudService<ReimburseDao, Reimburse> {

	@Autowired
	private ActTaskService actTaskService;
	@Autowired
	private TLoanContractService tLoanContractService;
	
	
	public Reimburse get(String id) {
		return super.get(id);
	}
	
	public List<Reimburse> getContition(Reimburse reimburse){
		return super.dao.getContition(reimburse);
	}
	
	public List<Reimburse> findList(Reimburse reimburse) {
		return super.findList(reimburse);
	}
	
	public Page<Reimburse> findPage(Page<Reimburse> page, Reimburse reimburse) {
		reimburse.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser().getCurrentUser(), "o", "u"));
		return super.findPage(page, reimburse);
	}
	
	/**
	 * 新增审核
	 */
	@Transactional(readOnly = false)
	public void save(Reimburse reimburse) {
		reimburse.setInsertTime( new Date() );
		
		//申请发起
		if(StringUtils.isBlank(reimburse.getId())){
			//状态：待审核
			reimburse.setStatus(Cons.ReimburseStatus.TO_REVIEW);
			
			reimburse.preInsert();
			dao.insert(reimburse);
			
		}
		//重新编辑申请
		else{
			reimburse.preUpdate();
			dao.update(reimburse);
			
		}
		
		//super.save(reimburse);
	}
	
	/**
	 * 审核批准
	 */
//	@Transactional(readOnly = false)
//	public void auditSave(Reimburse reimburse) {
//		//设置意见
//		reimburse.getAct().setComment(("yes".equals(reimburse.getAct().getFlag())?"[同意] ":"[驳回] ")+reimburse.getAct().getComment());
//		
//		//对不同环节的业务逻辑进行操作
//		String taskDefKey = reimburse.getAct().getTaskDefKey();
//		if("yes".equals(reimburse.getAct().getFlag())){
//			//审核环节 更新状态
//			if("audit1".equals(taskDefKey)){
//				reimburse.setStatus(Cons.ReimburseStatus.TO_SIGN);
//			}else if("audit2".equals(taskDefKey)){
//				reimburse.setStatus(Cons.ReimburseStatus.TO_LOAN);
//			}else if("audit3".equals(taskDefKey)){
//				reimburse.setStatus(Cons.ReimburseStatus.REFUND);
//			}
//			//未知环节，直接返回
//			else{
//			}
//		}else{
//			reimburse.setStatus(Cons.ReimburseStatus.WITHDRAW);
//		}
//		//保存新的状态
//		super.dao.updateStatus(reimburse);
//		
//		//提交流程任务
//		Map<String, Object> vars = Maps.newHashMap();
//		vars.put("pass", "yes".equals(reimburse.getAct().getFlag())? "0" : "1");
//		actTaskService.complete(reimburse.getAct().getTaskId(), reimburse.getAct().getProcInsId(), reimburse.getAct().getComment(), vars);
//		
//	}
	
	@Transactional(readOnly = false)
	public void delete(Reimburse reimburse) {
		super.delete(reimburse);
	}
	
	
	
	/**
	 * 更新合同状态
	 * @param 
	 * @return
	 */
	@Transactional(readOnly = false)
	public int updateStatus(Reimburse reimburse){
		return super.dao.updateStatus(reimburse);
	}
	
	
	@Transactional(readOnly = false)
	public void startProcess(Reimburse reimburse) {
		// 启动流程
		TLoanContract loanContract = tLoanContractService.get(reimburse.getLoanContractId());
		String title="【退费申请】" +loanContract.getContractNumber();
		//String procInsId = actTaskService.startProcess(reimburse.getAct().getProcDefKey(), "t_reimburse", reimburse.getId(), reimburse.getId());
		String procInsId = actTaskService.startProcess(reimburse.getAct().getProcDefKey(), "t_reimburse", reimburse.getId(),title);
		if(StringUtils.isNotBlank(procInsId))
		actTaskService.completeFirstTask(procInsId, reimburse.getAct().getComment(),title, null);
		reimburse.setStatus(Cons.ReimburseStatus.TO_LOAN);
		updateStatus(reimburse);
	}          
	
	
	/**
	 * 审核审批保存
	 * @param
	 */
	@SuppressWarnings("unused")
	@Transactional(readOnly = false)
	public void auditSave(Reimburse reimburse) {
		// 提交流程任务
		Map<String, Object> vars = Maps.newHashMap();
		if("yes".equals(reimburse.getAct().getFlag())){
			vars.put("pass", "1");
		}else if("no".equals(reimburse.getAct().getFlag())){
			vars.put("pass", "0");
		}else if("stop".equals(reimburse.getAct().getFlag())){
			vars.put("pass", "-1");
		}
		if(!"stop".equals(reimburse.getAct().getFlag())){
			// 设置意见
			reimburse.getAct().setComment(("yes".equals(reimburse.getAct().getFlag())?"[同意] ":"[驳回] ")+reimburse.getAct().getComment());
			reimburse.preUpdate();
			// 对不同环节的业务逻辑进行操作
			String taskDefKey = reimburse.getAct().getTaskDefKey();
			
			actTaskService.complete(reimburse.getAct().getTaskId(), reimburse.getAct().getProcInsId(), reimburse.getAct().getComment(), vars);
		}else{//终止任务
			  /*actTaskService.rollBackWorkFlow(creditApply.getAct().getTaskId());*/
			  actTaskService.overTask(reimburse.getAct().getTaskId(),reimburse.getAct().getProcInsId(),reimburse.getAct().getComment());
			  reimburse.setStatus(Cons.ReimburseStatus.TO_RETURN);//审核失败 
			  super.save(reimburse);
		  }
	}
}
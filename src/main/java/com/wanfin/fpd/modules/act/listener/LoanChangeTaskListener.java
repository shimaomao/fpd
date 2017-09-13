package com.wanfin.fpd.modules.act.listener;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.delegate.TaskListener;

import com.jfinal.plugin.activerecord.Db;
import com.wanfin.fpd.common.config.Cons;
import com.wanfin.fpd.common.utils.SpringContextHolder;
import com.wanfin.fpd.modules.contract.dao.TLoanContractDao;
import com.wanfin.fpd.modules.contract.entity.TLoanContract;
import com.wanfin.fpd.modules.contract.entity.TLoanContractBak;
import com.wanfin.fpd.modules.contract.service.TLoanContractService;
import com.wanfin.fpd.modules.repayplan.dao.TRepayPlanBakDao;
import com.wanfin.fpd.modules.repayplan.entity.TRepayPlan;
import com.wanfin.fpd.modules.repayplan.entity.TRepayPlanBak;
import com.wanfin.fpd.modules.repayplan.service.TRepayPlanService;

/**
 * 
 * @Description 贷前变更申请-任务监听事件，执行流程任务过程中的添加相应的业务逻辑
 * @author zzm
 * @date 2016-6-24 下午5:24:34 
 */
public class LoanChangeTaskListener implements Serializable, TaskListener {
	
	
	private static final long serialVersionUID = 5986247825158691156L;
	
	/**
	 * apply|approve
	 */
	private Expression option;

	public Expression getOption() {
		return option;
	}

	public void setOption(Expression option) {
		this.option = option;
	}

	/**
	 * 流程审核需要带参数pass（pass=1 通过，pass=2 不通过）
	 */
	@Override
	public void notify(DelegateTask delegateTask) {
		Map<String, Object> vars = delegateTask.getVariables();	
		if(vars.get("pass").equals("1")){
			agree(delegateTask);
		}else if(vars.get("pass").equals("-1")){//终止流程
			terminate(delegateTask);
		}
	}
	
	/**同意
	 *
	 */	
	public void agree(DelegateTask delegateTask) {
		//Map<String, Object> vars = delegateTask.getVariables();
		//String procInsId = delegateTask.getProcessInstanceId();
		//String procDefId = delegateTask.getProcessDefinitionId();
		//String procDefKey = procDefId.split(":")[0];	//流程定义标识
		String key = delegateTask.getExecution().getProcessBusinessKey();
		String[] keyArray = key.split(":");
		//int id = Integer.parseInt(vars.get("id").toString());
		TLoanContractService tLoanContractService = SpringContextHolder.getBean(TLoanContractService.class);
		TLoanContract loanContract = tLoanContractService.get(keyArray[1]);		
	
		//申请审批成功，待签订
		loanContract.setStatus(Cons.LoanContractStatus.TO_SIGN);
		
		tLoanContractService.save(loanContract);
		TRepayPlanService repayPlanService = (TRepayPlanService)SpringContextHolder.getBean(TRepayPlanService.class);
		TLoanContractBak contractBak = tLoanContractService.getContractBakByContractId(loanContract);
		//TLoanContractBak contractBak = tLoanContractService.getContractBakByContractId(null);
		List<TRepayPlan> planList = repayPlanService.getPlanByContractId(loanContract.getId());
		TRepayPlanBak tempTRepayPlanBak = new TRepayPlanBak();
		tempTRepayPlanBak.setLoanContractId(contractBak.getId());
		TRepayPlanBakDao repayPlanBakDao = (TRepayPlanBakDao)SpringContextHolder.getBean(TRepayPlanBakDao.class);
		List<TRepayPlanBak> planListBak = repayPlanBakDao.findListCondition(tempTRepayPlanBak);
		//List<TRepayPlanBak> planListBak = null;
		tLoanContractService.updateWork(loanContract, contractBak, loanContract.getUpdateBy(), planList, planListBak);	
		
	}
	
	/**不同意
	 * 
	 */
	
	public void terminate(DelegateTask delegateTask) {
		//Map<String, Object> vars = delegateTask.getVariables();
		//String procInsId = delegateTask.getProcessInstanceId();
		//String procDefId = delegateTask.getProcessDefinitionId();
		//String procDefKey = procDefId.split(":")[0];	//流程定义标识
		String key = delegateTask.getExecution().getProcessBusinessKey();
		String[] keyArray = key.split(":");
		//int id = Integer.parseInt(vars.get("id").toString());
		//int id = Integer.parseInt(vars.get("id").toString());
		TLoanContractService tLoanContractService = SpringContextHolder.getBean(TLoanContractService.class);
		TLoanContract loanContract = tLoanContractService.get(keyArray[1]);		
		TLoanContractBak contractBak = tLoanContractService.getContractBakByContractId(loanContract);
		//业务申请失败
		//loanContract.setStatus(Cons.LoanContractStatus.ENDED);
		//contractBak.setDataStatus("3");
		
		//Db.update("update t_loan_contract set status = ? where id = ?", Cons.LoanContractStatus.ENDED, loanContract.getId());
		Db.update("update t_loan_contract set status = ? where id = ?", Cons.LoanContractStatus.TO_LOAN, loanContract.getId());
		//审批不通过干掉已产生的变更还款计划
		Db.update("update t_loan_contract_bak set data_status = ? where id = ?", "3", contractBak.getId());
		
		//loanContractDao.update(loanContract);
		//loanContractBakDao.update(contractBak);	
		
	}

}

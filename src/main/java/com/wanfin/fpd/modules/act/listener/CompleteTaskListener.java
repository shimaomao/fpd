package com.wanfin.fpd.modules.act.listener;
import java.io.Serializable;
import java.util.Map;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.delegate.TaskListener;

import com.wanfin.fpd.common.config.Cons;
import com.wanfin.fpd.common.utils.SpringContextHolder;
import com.wanfin.fpd.modules.company.entity.TCompany;
import com.wanfin.fpd.modules.company.service.TCompanyService;
import com.wanfin.fpd.modules.contract.entity.TLoanContract;
import com.wanfin.fpd.modules.contract.service.TLoanContractService;
import com.wanfin.fpd.modules.employee.entity.TEmployee;
import com.wanfin.fpd.modules.employee.service.TEmployeeService;
import com.wanfin.fpd.modules.looploan.entity.TLoopLoan;
import com.wanfin.fpd.modules.looploan.service.TLoopLoanService;

/**
 * 
 * @Description 任务监听事件，执行流程任务过程中的添加相应的业务逻辑
 * @author zzm
 * @date 2016-3-31 下午12:49:32 
 */
public class CompleteTaskListener implements Serializable, TaskListener {
	
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

	@Override
	public void notify(DelegateTask delegateTask) {
		System.out.println("----任务监听   CompleteTaskListener---");
		Map<String, Object> vars = delegateTask.getVariables();
		String procInsId = delegateTask.getProcessInstanceId();
		String procDefId = delegateTask.getProcessDefinitionId();
		String procDefKey = procDefId.split(":")[0];	//流程定义标识
		String key = delegateTask.getExecution().getProcessBusinessKey();
		String[] keyArray = key.split(":");
		//System.out.println(vars+" "+key+" "+procInsId+" "+procDefKey+" "+option.getExpressionText());
		System.out.println(vars+" "+key+" "+procInsId+" "+procDefKey+" ");

		if(Cons.ProcDefKey.LOOP_LOAN.equals(procDefKey)){
			//授信申请流程结束
			TLoopLoanService tLoopLoanService = SpringContextHolder.getBean(TLoopLoanService.class);
			TLoopLoan looploan = tLoopLoanService.get(keyArray[1]);
			if("apply".equals(option.getExpressionText())){
				looploan.setStatus(Cons.LoopLoanStatus.TO_APPROVE);
			}
			else if(vars.get("pass").equals("1")){
				//授信审批成功
				looploan.setStatus(Cons.LoopLoanStatus.CREDIT_SUCCESS);
			}else{
				//授信审批失败
				looploan.setStatus(Cons.LoopLoanStatus.CREDIT_FAIL);
			}
			tLoopLoanService.save(looploan);
		}
		if(Cons.ProcDefKey.LEND_APPLY.equals(procDefKey)){
			//放款申请流程结束
			TLoanContractService tLoanContractService = SpringContextHolder.getBean(TLoanContractService.class);
			TLoanContract loanContract = tLoanContractService.get(keyArray[1]);
			if(vars.get("pass").equals("1")){
				//放款审批成功
				loanContract.setStatus(Cons.LoanContractStatus.TO_LOAN);
			}else{
				//放款审批失败
				loanContract.setStatus(Cons.LoanContractStatus.ENDED);
			}
			tLoanContractService.save(loanContract);
		}
		if(Cons.ProcDefKey.LOAN_APPLY.equals(procDefKey)){
			//业务发起申请流程结束
			TLoanContractService tLoanContractService = SpringContextHolder.getBean(TLoanContractService.class);
			TLoanContract loanContract = tLoanContractService.get(keyArray[1]);
			if(vars.get("pass").equals("1")){
				//申请审批成功，待签订
				loanContract.setStatus(Cons.LoanContractStatus.TO_SIGN);
			}else{
				//业务申请失败
				loanContract.setStatus(Cons.LoanContractStatus.ENDED);
			}
			tLoanContractService.save(loanContract);
		}
		if(Cons.ProcDefKey.EXTEND_APPLY.equals(procDefKey)){
			//业务发起申请流程结束
			TLoanContractService tLoanContractService = SpringContextHolder.getBean(TLoanContractService.class);
			TLoanContract loanContract = tLoanContractService.get(keyArray[1]);
			if(vars.get("pass").equals("1")){
				//展期申请审批成功，待签订
				loanContract.setStatus(Cons.LoanContractStatus.ET_TO_SIGN);
			}else{
				//展期申请失败
				loanContract.setStatus(Cons.LoanContractStatus.ENDED);
				//展期失败，原贷款合同从“已展期”变为“未结清”
				loanContract.getParent().setStatus(Cons.LoanContractStatus.UN_CLEARED);
				tLoanContractService.updateStatus(loanContract.getParent());
			}
			tLoanContractService.save(loanContract);
		}
		if(Cons.ProcDefKey.BLACK_APPLY.equals(procDefKey)){
			//加入黑名单流程结束
			TCompanyService tCompanyService = SpringContextHolder.getBean(TCompanyService.class);
			TEmployeeService tEmployeeService = SpringContextHolder.getBean(TEmployeeService.class);
			TCompany tCompany = tCompanyService.get(keyArray[1]);
			TEmployee tEmployee = tEmployeeService.get(keyArray[1]);
			if(vars.get("pass").equals("1")){
				//加入黑名单申请审批成功，状态：加入黑名审批中——》黑名
				if(tEmployee == null) {
					tCompany.setStatus(Cons.CustomerStatus.BLACK);
					tCompanyService.save(tCompany);
				}else{
					tEmployee.setStatus(Cons.CustomerStatus.BLACK);
					tEmployeeService.save(tEmployee);
				}
				
			}else{
				//加入黑名单申请失败，状态：解除黑名审批中——》正常
				if(tEmployee == null) {
					tCompany.setStatus(Cons.CustomerStatus.NORMAL);
					tCompany.setReason(null);
					tCompanyService.save(tCompany);
				}else{
					tEmployee.setStatus(Cons.CustomerStatus.NORMAL);
					tEmployee.setReason(null);
					tEmployeeService.save(tEmployee);
				} 
			}
		}
		if(Cons.ProcDefKey.BLACK_REMOVE.equals(procDefKey)){
			//解除黑名单流程结束
			TCompanyService tCompanyService = SpringContextHolder.getBean(TCompanyService.class);
			TEmployeeService tEmployeeService = SpringContextHolder.getBean(TEmployeeService.class);
			TCompany tCompany = tCompanyService.get(keyArray[1]);
			TEmployee tEmployee = tEmployeeService.get(keyArray[1]);
			if(vars.get("pass").equals("1")){
				//解除黑名单申请审批成功，状态：解除黑名审批中——》正常
				if(tEmployee == null) {
					tCompany.setStatus(Cons.CustomerStatus.NORMAL);
					tCompany.setReason(null);
					tCompanyService.save(tCompany);
				}else{
					tEmployee.setStatus(Cons.CustomerStatus.NORMAL);
					tEmployee.setReason(null);
					tEmployeeService.save(tEmployee);
				}
			}else{
				//解除黑名单申请失败，状态：解除黑名审批中——》黑名
				if(tEmployee == null) {
					tCompany.setStatus(Cons.CustomerStatus.BLACK);
					tCompanyService.save(tCompany);
				}else{
					tEmployee.setStatus(Cons.CustomerStatus.BLACK);
					tEmployeeService.save(tEmployee);
				} 
			}
		}
		
		
	}

}

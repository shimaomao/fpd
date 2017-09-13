package com.wanfin.fpd.modules.act.listener;

import java.io.Serializable;
import java.util.Map;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.delegate.Expression;

import com.wanfin.fpd.common.config.Cons;
import com.wanfin.fpd.common.utils.SpringContextHolder;
import com.wanfin.fpd.modules.looploan.entity.TLoopLoan;
import com.wanfin.fpd.modules.looploan.service.TLoopLoanService;

/**
 * @Description 流程实例化的流程监听类
 * @author zzm
 * @date 2016-3-31 下午12:53:27 
 */
public class StartExectuionListener implements Serializable, ExecutionListener{

	private static final long serialVersionUID = 5906513923272909666L;
	
	private Expression message;  
	  
    public Expression getMessage() {  
        return message;  
    }  
  
    public void setMessage(Expression message) {  
        this.message = message;  
    }
	
	@Override
	public void notify(DelegateExecution execution) throws Exception {
		System.out.println("----执行监听   StartExectuionListener---");
		Map<String, Object> vars = execution.getVariables();
		String procDefId = execution.getProcessDefinitionId();
		String procDefKey = procDefId.split(":")[0];	//流程定义标识
		String key = execution.getProcessBusinessKey();
		String[] keyArray = key.split(":");
		String procInsId = execution.getProcessInstanceId();
		System.out.println(vars+" "+procDefKey+" "+procInsId);
		if(Cons.ProcDefKey.LOOP_LOAN.equals(procDefKey)){
			//授信申请流程结束
			TLoopLoanService tLoopLoanService = SpringContextHolder.getBean(TLoopLoanService.class);
			TLoopLoan looploan = tLoopLoanService.get(keyArray[1]);
			looploan.setStatus(Cons.LoopLoanStatus.TO_APPROVE);
			tLoopLoanService.save(looploan);
		}
		if(Cons.ProcDefKey.LEND_APPLY.equals(procDefKey)){
			//放款申请流程结束
		}
	}

}

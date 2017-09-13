package com.wanfin.fpd.modules.act.listener;
import java.io.Serializable;
import java.util.Map;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.delegate.TaskListener;

import com.wanfin.fpd.common.config.Cons;
import com.wanfin.fpd.common.utils.SpringContextHolder;
import com.wanfin.fpd.modules.looploan.entity.TLoopLoan;
import com.wanfin.fpd.modules.looploan.service.TLoopLoanService;

/**
 * 
 * @Description 授信申请-任务监听事件，执行流程任务过程中的添加相应的业务逻辑
 * @author zzm
 * @date 2016-6-24 下午5:15:41 
 */
public class LoopLoanTaskListener implements Serializable, TaskListener {
	
	
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
		System.out.println("----任务监听   LoopLoanTaskListener---");
		Map<String, Object> vars = delegateTask.getVariables();
		String procInsId = delegateTask.getProcessInstanceId();
		String procDefId = delegateTask.getProcessDefinitionId();
		String procDefKey = procDefId.split(":")[0];	//流程定义标识
		String key = delegateTask.getExecution().getProcessBusinessKey();
		String[] keyArray = key.split(":");
		//System.out.println(vars+" "+key+" "+procInsId+" "+procDefKey+" "+option.getExpressionText());
		System.out.println(vars+" "+key+" "+procInsId+" "+procDefKey+" ");

		///授信申请流程结束
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

}

package com.wanfin.fpd.modules.act.listener;
import java.io.Serializable;
import java.util.Map;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.delegate.TaskListener;

import com.wanfin.fpd.common.utils.SpringContextHolder;
import com.wanfin.fpd.modules.looploan.service.TLoopLoanService;

/**
 * 
 * @Description 任务监听事件，可在流程启动时，添加相应的操作
 * @author zzm
 * @date 2016-3-31 下午12:49:32 
 */
public class CreateTaskListener implements Serializable, TaskListener {
	private static final long serialVersionUID = 3323852043551979023L;
	
	private Expression option;

	public Expression getOption() {
		return option;
	}

	public void setOption(Expression option) {
		this.option = option;
	}

	@SuppressWarnings("unused")
	@Override
	public void notify(DelegateTask delegateTask) {
		System.out.println("----任务监听   CreateTaskListener---");
		Map<String, Object> vars = delegateTask.getVariables();
		String instId = delegateTask.getProcessInstanceId();
		String code = delegateTask.getTaskDefinitionKey();
		String key = delegateTask.getExecution().getProcessBusinessKey();
		System.out.println(vars+" "+key+" "+instId+" "+code);
		TLoopLoanService tLoopLoanService = SpringContextHolder.getBean(TLoopLoanService.class);
	}

}

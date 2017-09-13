package com.wanfin.fpd.modules.act.listener;
import java.io.Serializable;
import java.util.Map;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.delegate.TaskListener;

import com.wanfin.fpd.common.config.Cons;
import com.wanfin.fpd.common.utils.SpringContextHolder;
import com.wanfin.fpd.modules.contract.entity.TLoanContract;
import com.wanfin.fpd.modules.contract.service.TLoanContractService;
import com.wanfin.fpd.modules.looploan.service.TLoopLoanService;
import com.wanfin.fpd.modules.preloaninvestigate.entity.PreLoanInvestigate;
import com.wanfin.fpd.modules.preloaninvestigate.service.PreLoanInvestigateService;
import com.wanfin.fpd.modules.refund.entity.Reimburse;
import com.wanfin.fpd.modules.refund.service.ReimburseService;

/**
 * 
 * @Description 任务监听事件，可在流程启动时，添加相应的操作
 * @author zzm
 * @date 2016-3-31 下午12:49:32 
 */
public class ReimbureseListener implements Serializable, TaskListener {
	private static final long serialVersionUID = 3323852043551979023L;
	
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
		System.out.println("----任务监听   ReimbureseListener---");
		Map<String, Object> vars = delegateTask.getVariables();
		String procInsId = delegateTask.getProcessInstanceId();
		String procDefId = delegateTask.getProcessDefinitionId();
		String procDefKey = procDefId.split(":")[0];	//流程定义标识
		String key = delegateTask.getExecution().getProcessBusinessKey();
		String[] keyArray = key.split(":");
		System.out.println(vars+" "+key+" "+procInsId+" "+procDefKey+" ");

		//业务发起申请流程结束
		ReimburseService reimburseService = SpringContextHolder.getBean(ReimburseService.class);
		Reimburse reimburse = reimburseService.get(keyArray[1]);
		if(vars.get("pass").equals("1")){
			//申请成功
			reimburse.setStatus(Cons.ReimburseStatus.REFUND);
		}else if(vars.get("pass").equals("-1")){
			//申请失败
			reimburse.setStatus(Cons.ReimburseStatus.TO_RETURN);
		}
		reimburseService.updateStatus(reimburse);
	}


}

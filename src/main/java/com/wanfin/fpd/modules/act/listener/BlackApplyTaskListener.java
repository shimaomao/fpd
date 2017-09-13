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
import com.wanfin.fpd.modules.employee.entity.TEmployee;
import com.wanfin.fpd.modules.employee.service.TEmployeeService;

/**
 * 
 * @Description 加入黑名单-任务监听事件，执行流程任务过程中的添加相应的业务逻辑
 * @author zzm
 * @date 2016-6-24 下午5:24:34 
 */
public class BlackApplyTaskListener implements Serializable, TaskListener {
	
	
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
		System.out.println("----任务监听   BlackApplyTaskListener---");
		Map<String, Object> vars = delegateTask.getVariables();
		String procInsId = delegateTask.getProcessInstanceId();
		String procDefId = delegateTask.getProcessDefinitionId();
		String procDefKey = procDefId.split(":")[0];	//流程定义标识
		String key = delegateTask.getExecution().getProcessBusinessKey();
		String[] keyArray = key.split(":");
		//System.out.println(vars+" "+key+" "+procInsId+" "+procDefKey+" "+option.getExpressionText());
		System.out.println(vars+" "+key+" "+procInsId+" "+procDefKey+" ");

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

}

package com.wanfin.fpd.modules.act.listener;
import java.io.Serializable;
import java.util.Map;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.delegate.TaskListener;
import org.json.JSONException;

import com.wanfin.fpd.common.utils.SpringContextHolder;
import com.wanfin.fpd.modules.postlending.entity.advance.Advance;
import com.wanfin.fpd.modules.postlending.service.advance.AdvanceService;

/**
 * 
 * @Description 提前还款-任务监听事件，执行流程任务过程中的添加相应的业务逻辑
 * @author zzm
 * @date 2016-6-2 上午11:59:20 
 */
public class AdvanceTaskListener implements Serializable, TaskListener {
	
	
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
		System.out.println("----任务监听   AdvanceTaskListener---");
		Map<String, Object> vars = delegateTask.getVariables();
		String procInsId = delegateTask.getProcessInstanceId();
		String procDefId = delegateTask.getProcessDefinitionId();
		String procDefKey = procDefId.split(":")[0];	//流程定义标识
		String key = delegateTask.getExecution().getProcessBusinessKey();
		String[] keyArray = key.split(":");
		//System.out.println(vars+" "+key+" "+procInsId+" "+procDefKey+" "+option.getExpressionText());
		System.out.println(vars+" "+key+" "+procInsId+" "+procDefKey+" ");

		//提前还款申请流程结束
		AdvanceService advanceService = SpringContextHolder.getBean(AdvanceService.class);
		Advance advance = advanceService.get(keyArray[1]);
		//申请成功
		try {
			if(vars.get("pass").equals("1")){
				advanceService.validAdvace(advance);
			}
			//申请失败
			else{
				advanceService.invalidAdvace(advance);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
	}

}

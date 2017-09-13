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
import com.wanfin.fpd.modules.contract.service.TLoanContractService;
import com.wanfin.fpd.modules.credit.entity.CreditApply;
import com.wanfin.fpd.modules.credit.service.CreditApplyService;
import com.wanfin.fpd.modules.employee.entity.TEmployee;
import com.wanfin.fpd.modules.employee.service.TEmployeeService;
import com.wanfin.fpd.modules.mortgage.entity.MortgageContract;
import com.wanfin.fpd.modules.mortgage.entity.TMortgageApplay;
import com.wanfin.fpd.modules.mortgage.service.MortgageContractService;
import com.wanfin.fpd.modules.mortgage.service.TMortgageApplayService;
import com.wanfin.fpd.modules.pledge.entity.PledgeContract;
import com.wanfin.fpd.modules.pledge.service.PledgeContractService;
import com.wanfin.fpd.modules.postlending.entity.fivelevel.FiveLevel;
import com.wanfin.fpd.modules.postlending.service.fivelevel.FiveLevelService;

/**
 * 
 * @Description  押品借出流程
 * @author lzj
 */
public class MortgageApplayTaskListener implements Serializable, TaskListener {
	
	
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
	 * 流程审核需要带参数pass（pass=1 通过，pass=-1不通过）
	 */
	@Override
	public void notify(DelegateTask delegateTask) {
		System.out.println("----任务监听   CreditApplyTaskListener---");
		Map<String, Object> vars = delegateTask.getVariables();
		String procInsId = delegateTask.getProcessInstanceId();
		String procDefId = delegateTask.getProcessDefinitionId();
		String procDefKey = procDefId.split(":")[0];	//流程定义标识
		String key = delegateTask.getExecution().getProcessBusinessKey();
		String[] keyArray = key.split(":");
		//System.out.println(vars+" "+key+" "+procInsId+" "+procDefKey+" "+option.getExpressionText());
		System.out.println(vars+" "+key+" "+procInsId+" "+procDefKey+" ");

		//押品借出流程结束
		TMortgageApplayService tMortgageApplayService = SpringContextHolder.getBean(TMortgageApplayService.class);
		MortgageContractService mortgageContractService = SpringContextHolder.getBean(MortgageContractService.class);
		PledgeContractService  pledgeContractService = SpringContextHolder.getBean(PledgeContractService.class);
		
		MortgageContract mc = mortgageContractService.get(keyArray[1]);
		PledgeContract pc = pledgeContractService.get(keyArray[1]);
		if(vars.get("pass").equals("1")){
			if (mc != null) {// 抵押品
				TMortgageApplay tMortgageApplay=tMortgageApplayService.getPojoByContract(mc.getId());
				if(tMortgageApplay.getAuditType().equals(Cons.MortgageAuditType.TAKE_IN)){//归还
					mc.setStruts(Integer.valueOf(Cons.StrutsStatus.TAKE_INTO));// 更新为收押状态
					mortgageContractService.updata(mc.getId(), Cons.StrutsStatus.TAKE_INTO);
				}else if(tMortgageApplay.getAuditType().equals(Cons.MortgageAuditType.BORROW_OUT)){//借出
					mc.setStruts(Integer.valueOf(Cons.StrutsStatus.BORROW_YES));// 更新为已借出
					mortgageContractService.updata(mc.getId(), Cons.StrutsStatus.BORROW_YES);
				}
				
			} else {// 质押品
				TMortgageApplay tMortgageApplay=tMortgageApplayService.getPojoByContract(pc.getId());
				if(tMortgageApplay.getAuditType().equals(Cons.MortgageAuditType.TAKE_IN)){//收押
					pc.setStruts(Integer.valueOf(Cons.StrutsStatus.TAKE_INTO));// 更新为收押状态
					pledgeContractService.updata(pc.getId(), Cons.StrutsStatus.TAKE_INTO);
				}else if(tMortgageApplay.getAuditType().equals(Cons.MortgageAuditType.BORROW_OUT)){//借出
					pc.setStruts(Integer.valueOf(Cons.StrutsStatus.BORROW_YES));// 更新为收押状态
					pledgeContractService.updata(pc.getId(), Cons.StrutsStatus.BORROW_YES);
				}
				
			}
			
		}else if(vars.get("pass").equals("-1")){
			if (mc != null) {// 抵押品
				TMortgageApplay tMortgageApplay=tMortgageApplayService.getPojoByContract(mc.getId());
				if(tMortgageApplay.getAuditType().equals(Cons.MortgageAuditType.TAKE_IN)){//归还
					mc.setStruts(Integer.valueOf(Cons.StrutsStatus.BORROW_YES));// 归还审批不通过 改为已借出状态
					mortgageContractService.updata(mc.getId(), Cons.StrutsStatus.BORROW_YES);
				}else if(tMortgageApplay.getAuditType().equals(Cons.MortgageAuditType.BORROW_OUT)){//借出
					mc.setStruts(Integer.valueOf(Cons.StrutsStatus.TAKE_INTO));// 恢复为已收押
					mortgageContractService.updata(mc.getId(), Cons.StrutsStatus.TAKE_INTO);
				}
			
			} else {// 质押品
				TMortgageApplay tMortgageApplay=tMortgageApplayService.getPojoByContract(pc.getId());
				if(tMortgageApplay.getAuditType().equals(Cons.MortgageAuditType.TAKE_IN)){//归还
					pc.setStruts(Integer.valueOf(Cons.StrutsStatus.BORROW_YES));// 借出审批不通过 改为新增状态
					pledgeContractService.updata(pc.getId(), Cons.StrutsStatus.BORROW_YES);
				}else if(tMortgageApplay.getAuditType().equals(Cons.MortgageAuditType.BORROW_OUT)){//借出
					pc.setStruts(Integer.valueOf(Cons.StrutsStatus.TAKE_INTO));// 恢复为已收押
					pledgeContractService.updata(pc.getId(), Cons.StrutsStatus.TAKE_INTO);
				}
				
			}
		}
		
	}
	
}

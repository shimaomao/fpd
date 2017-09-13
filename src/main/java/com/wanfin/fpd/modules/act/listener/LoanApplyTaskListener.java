package com.wanfin.fpd.modules.act.listener;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.delegate.TaskListener;
import org.springframework.beans.factory.annotation.Autowired;

import com.wanfin.fpd.common.config.Cons;
import com.wanfin.fpd.common.utils.DateUtils;
import com.wanfin.fpd.common.utils.FreeMarkers;
import com.wanfin.fpd.common.utils.SpringContextHolder;
import com.wanfin.fpd.common.utils.StringUtils;
import com.wanfin.fpd.modules.contract.entity.TLoanContract;
import com.wanfin.fpd.modules.contract.service.TLoanContractService;
import com.wanfin.fpd.modules.sms.entity.TMsgSwitch;
import com.wanfin.fpd.modules.sms.service.TMsgSwitchService;
import com.wanfin.fpd.modules.sys.dao.AuthenticatorDao;
import com.wanfin.fpd.modules.sys.entity.AuthenticationUser;
import com.wanfin.fpd.modules.sys.service.SystemService;

/**
 * 
 * @Description 贷款业务申请-任务监听事件，执行流程任务过程中的添加相应的业务逻辑
 * @author zzm
 * @date 2016-6-24 下午5:24:34 
 */
public class LoanApplyTaskListener implements Serializable, TaskListener {
	
	
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
		System.out.println("----任务监听   LoanApplyTaskListener---");
		Map<String, Object> vars = delegateTask.getVariables();
		String procInsId = delegateTask.getProcessInstanceId();
		String procDefId = delegateTask.getProcessDefinitionId();
		String procDefKey = procDefId.split(":")[0];	//流程定义标识
		String key = delegateTask.getExecution().getProcessBusinessKey();
		String[] keyArray = key.split(":");
		//System.out.println(vars+" "+key+" "+procInsId+" "+procDefKey+" "+option.getExpressionText());
		System.out.println(vars+" "+key+" "+procInsId+" "+procDefKey+" ");

		//业务发起申请流程结束
		TLoanContractService tLoanContractService = SpringContextHolder.getBean(TLoanContractService.class);
		SystemService systemService = SpringContextHolder.getBean(SystemService.class);
		TMsgSwitchService tMsgSwitchService = SpringContextHolder.getBean(TMsgSwitchService.class);
		AuthenticatorDao authenticatorDao = SpringContextHolder.getBean(AuthenticatorDao.class);
		
		TLoanContract loanContract = tLoanContractService.get(keyArray[1]);
		if(vars.get("pass").equals("1")){
			//申请审批成功，待签订
			loanContract.setStatus(Cons.LoanContractStatus.TO_SIGN);
			
			if(StringUtils.isNotBlank(loanContract.getType()) && "1".equals(loanContract.getType())){
				  TMsgSwitch tMsgSwitch=tMsgSwitchService.checkMsgSwitch(Cons.MsgBusinessType.MAKE_LOAN_SUCCESS);
				  if(tMsgSwitch!=null){
					  if(tMsgSwitch.getLetterStatus().equals("1")){
						     String title = Cons.letterDForm.LETTER_LOANAUDITSUCCESS_TITILE;
						     Map<String, String> model = com.google.common.collect.Maps.newHashMap();
						     model.put("time",DateUtils.formatDate(loanContract.getApplyDate()));
						     String content = FreeMarkers.renderString(Cons.letterDForm.LETTER_LOANAUDITSUCCESS_CONTENT, model);
						     Map<String, String> letterMap=new HashMap<String, String>();
						     letterMap.put("title", title);
						     letterMap.put("msgContent", content);
						     letterMap.put("sendUserId", loanContract.getAuthUserId());
						     letterMap.put("readUserId","'"+loanContract.getBuyAuthId()+"'");
						     letterMap.put("type",Cons.letterDForm.LETTER_LOANAUDITSUCCESS);
						     letterMap.put("name",title);
						     Map<String,Object> lmap=systemService.addLetter(letterMap);
						  }
						  if(tMsgSwitch.getMarketStatus().equals("1")){
							  AuthenticationUser buyaAuthUser=authenticatorDao.get(loanContract.getBuyAuthId());
								String mobile="";
				    			if(buyaAuthUser!=null){
				    				mobile=buyaAuthUser.getMobile();
				    			}
				    			if(StringUtils.isNotBlank(mobile)){
				    				Map<String, String> msgmodel = com.google.common.collect.Maps.newHashMap();
				        			msgmodel.put("time",DateUtils.formatDate(loanContract.getApplyDate()));
				        			String msgtext = FreeMarkers.renderString(Cons.messageDForm.MESSAGE_LOANAUDITSUCCESS_CONTENT, msgmodel);
				        			Map<String,Object> lmap=systemService.addMarket(mobile,msgtext);
									
				    			}
						  }
				  }
			 }	
		}else if(vars.get("pass").equals("-1")){
			//终止任务
			loanContract.setStatus(Cons.LoanContractStatus.ENDED);
		}
		tLoanContractService.save(loanContract);
		
	}

}

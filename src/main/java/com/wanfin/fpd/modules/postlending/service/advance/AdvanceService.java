/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.postlending.service.advance;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.h2.mvstore.DataUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.wanfin.fpd.common.config.Cons;
import com.wanfin.fpd.common.config.Global;
import com.wanfin.fpd.common.config.SvalBase.JsonKey;
import com.wanfin.fpd.common.mapper.JsonMapper;
import com.wanfin.fpd.common.persistence.BaseEntity;
import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.service.CrudService;
import com.wanfin.fpd.common.service.ServiceException;
import com.wanfin.fpd.common.utils.DateUtils;
import com.wanfin.fpd.common.utils.InterfaceUtil;
import com.wanfin.fpd.common.utils.StringUtils;
import com.wanfin.fpd.common.utils.TimeUtils;
import com.wanfin.fpd.modules.act.service.ActTaskService;
import com.wanfin.fpd.modules.catipal.entity.TCapital;
import com.wanfin.fpd.modules.catipal.service.TCapitalService;
import com.wanfin.fpd.modules.contract.entity.TLoanContract;
import com.wanfin.fpd.modules.contract.service.TLoanContractService;
import com.wanfin.fpd.modules.postlending.dao.advance.AdvanceDao;
import com.wanfin.fpd.modules.postlending.entity.advance.Advance;
import com.wanfin.fpd.modules.receivables.entity.RepayRecord;
import com.wanfin.fpd.modules.receivables.service.RepayRecordService;
import com.wanfin.fpd.modules.repayplan.entity.TRepayPlan;
import com.wanfin.fpd.modules.repayplan.service.TRepayPlanService;
import com.wanfin.fpd.modules.repayplan.vo.PlanCreateParam;
import com.wanfin.fpd.modules.sys.utils.UserUtils;

/**
 * 提前还款Service
 * @author srf
 * @version 2016-04-18
 */
@Service
@Transactional(readOnly = true)
public class AdvanceService extends CrudService<AdvanceDao, Advance> {
	
	@Autowired
	private TLoanContractService tLoanContractService;
	@Autowired
	private RuntimeService runtimeService;
	
	@Autowired
	private ActTaskService actTaskService;

	@Autowired
	private TRepayPlanService tRepayPlanService;
	
	@Autowired
	private RepayRecordService repayRecordService;
	@Autowired
	private TCapitalService capitalservice;
	
	public Advance get(String id) {
		return super.get(id);
	}
	
	public int updateStatus(String id,String status){
		return dao.updateStatus(id, status);
	}
	
	public List<Advance> findList(Advance advance) {
		return super.findList(advance);
	}
	
	public Page<Advance> findPage(Page<Advance> page, Advance advance) {
		return super.findPage(page, advance);
	}
	
	/** 
	 * @Description 提前还款单据保存、审核
	 * @Title: save
	 * @see com.wanfin.fpd.common.service.CrudService#save(com.wanfin.fpd.common.persistence.DataEntity)
	 * @param advance
	 * @throws ServiceException
	 * @author zzm
	 * @date 2016-6-2 上午11:04:23  
	 */
	@Transactional(readOnly = false)
	public void saveAndAudit(Advance advance) throws ServiceException{
		TLoanContract tLoanContract=tLoanContractService.get(advance.getLoanContractId());
		if(StringUtils.isBlank(advance.getId())){
			Advance item = new Advance();
			item.setLoanContractId(advance.getLoanContractId());
			item.setStatus(Cons.AdvanceStatus.TO_ADUIT);
			List<Advance> itemList = findList(item);
			if(itemList!=null && !itemList.isEmpty()){
				throw new ServiceException("此合同已有在审核中的提前还款单");
			}
			advance.setLoanContract(tLoanContract);
			advance.setStatus(Cons.AdvanceStatus.TO_ADUIT);		
			super.save(advance);
		}
		if(StringUtils.isBlank(advance.getProcInsId())){
			String title = "提前还款【"+tLoanContract.getContractNumber()+"】："+advance.getAdvanceamount();
			// 启动并提交流程
			String procInsId = actTaskService.startProcess(advance.getAct().getProcDefKey(), advance.getAct().getBusinessTable(), advance.getId(), title);
			if(StringUtils.isNotBlank(procInsId))
				actTaskService.completeFirstTask(procInsId, advance.getAct().getComment(), 
						title, advance.getAct().getVars().getVariableMap());
			advance.setProcInsId(procInsId);
			super.save(advance);
		}else{
			if(!"-1".equals(advance.getAct().getFlag())){//非终止操作
				// 设置意见
				advance.getAct().setComment(("1".equals(advance.getAct().getFlag())?"[同意] ":"[驳回] ")+advance.getAct().getComment());
				//执行任务
				actTaskService.complete(advance.getAct().getTaskId(), advance.getAct().getProcInsId(), 
						advance.getAct().getComment(), advance.getAct().getVars().getVariableMap());
			}else{
				 actTaskService.overTask(advance.getAct().getTaskId(),advance.getAct().getProcInsId(),advance.getAct().getComment());
				 advance.setStatus(Cons.AdvanceStatus.INVALID);//审批不通过
			}
			//判断流程是否结束
			ProcessInstance pi = runtimeService.createProcessInstanceQuery()
					.processInstanceId(advance.getAct().getProcInsId()).singleResult();
		    if(pi==null && !"-1".equals(advance.getAct().getFlag())){
				try {
					validAdvace(advance);//审批成功，处理还款计划
					//advance.setStatus(Cons.AdvanceStatus.VALID);//审批通过
			    	//super.save(advance);
				} catch (JSONException e) {
					e.printStackTrace();
				}
		        System.out.println("流程已经结束");  
		    }  
		    else{  
		        System.out.println("流程没有结束");  
		    }  
		}
		super.save(advance);
	}
	
	@Transactional(readOnly = false)
	public void delete(Advance advance) {
		super.delete(advance);
	}
	/**
	 * 提前还款生效
	 * @Description 只有状态为审批中的才会执行
	 * @author zzm 
	 * @throws JSONException 
	 * @date 2016-6-2 下午2:00:25  
	 */
	@Transactional(readOnly = false)
	public void validAdvace(Advance advance) throws JSONException{
		TLoanContract contract = tLoanContractService.get(advance.getLoanContractId());
		//计算还还有多少本金未还和需要的利息
		BigDecimal currentPrincipal = null;//当前本金
		// 获取需要还款的数据
		TRepayPlan rp = new TRepayPlan();
		rp.setLoanContractId(advance.getLoanContractId());
		HashMap<String, Double> map = tRepayPlanService.allRepayment(rp);
	    if (map != null) {//有还款计划
			BigDecimal principal = map.get("principal") == null ? new BigDecimal(0) : new BigDecimal(map.get("principal"));// 本金
			BigDecimal principalReal = map.get("principal_real") == null ? new BigDecimal(0) : new BigDecimal(map.get("principal_real"));// 真实本金
		    currentPrincipal = principal.subtract(principalReal);//当前本金
		}
		if((advance.getAdvanceamount().setScale(2,BigDecimal.ROUND_HALF_UP)).compareTo(currentPrincipal.setScale(2,BigDecimal.ROUND_HALF_UP))==0){//申请全部还清不需要生成新的还款计划
			TRepayPlan plan = new TRepayPlan();
			plan.setLoanContractId(advance.getLoanContractId());
			List<TRepayPlan> list = tRepayPlanService.findList(plan);//原还款计划
			for(TRepayPlan tp:list){
				tp.setStatus(Cons.RepayStatus.ADVANCE);
				tRepayPlanService.save(tp);//更新还款状态为提前还款
			}
			contract.setStatus(Cons.LoanContractStatus.CLEARED);
			tLoanContractService.updateStatus(contract);
			advance.setStatus(Cons.AdvanceStatus.VALID);
			super.save(advance);
			//资本信息
			//1担保   2小贷           小贷才需要判断资本信息
			if(UserUtils.getUser().getCompany().getPrimaryPerson()!=null&& "2".equals(UserUtils.getUser().getCompany().getPrimaryPerson())){
				TCapital capital = capitalservice.getByOrganId(UserUtils.getUser().getCompany().getId());
				if(capital!=null){
					BigDecimal b1 = new BigDecimal(capital.getLoanamount()) ; //贷款注入金额
					capital.setLoanamount((b1.add(advance.getAdvanceamount())).toString());
					capitalservice.save(capital);
		        }
			}
		}else{//生成新的还款计划
			if(StringUtils.equals(advance.getStatus(), Cons.AdvanceStatus.TO_ADUIT)){
				TRepayPlan plan = new TRepayPlan();
				plan.setLoanContractId(advance.getLoanContractId());
				//更新合同的还款计划
			
				JSONObject json = new JSONObject();
						
				json.put("token", Cons.Ips.IP_KIE_TOKEN);
				json.put("businessType", "earlyrepay");
				json.put("amount", contract.getLoanAmount());
				json.put("loanRate", contract.getLoanRate());
				if(contract.getLoanRateType().equals("年")){
					json.put("loanRateType", 1);
				}else if(contract.getLoanRateType().equals("月")){
					json.put("loanRateType", 2);
				}else if(contract.getLoanRateType().equals("日")){
					json.put("loanRateType", 3);
				}
				json.put("loanPeriod",contract.getLoanPeriod());
				json.put("loanDate", DateUtils.formatDate(contract.getLoanDate()));
				json.put("payType", contract.getPayType());
				json.put("periodType", contract.getPeriodType());
				json.put("payDay", contract.getPayDay());
				json.put("payOptions", contract.getPayOptions());
				json.put("payAmount", advance.getAdvanceamount().setScale(1,BigDecimal.ROUND_HALF_UP));
				json.put("payDate", DateUtils.formatDate(advance.getApplyDate()));
				json.put("payPrincipalDate", DateUtils.formatDate(contract.getPayPrincipalDate()));
				if(contract.getIfRealityDay()!=null && !contract.getIfRealityDay().equals("undefined")){
					if(contract.getIfRealityDay().equals("1")){
						json.put("ifRealityDay",true);
					}else{
						json.put("ifRealityDay",false);
					}
				}else{
					json.put("ifRealityDay",false);
				}
					
				List<TRepayPlan> list = tRepayPlanService.findList(plan);//原还款计划
				tRepayPlanService.inValidBatch(plan);//废弃旧的还款计划
				
				JSONArray array = new JSONArray();
				if(list != null ){
						for(int i=0;i<list.size();i++){
							JSONObject plan1 = new JSONObject();
							plan1.put("id", list.get(i).getId());
							plan1.put("num", list.get(i).getNum());
							plan1.put("interest", list.get(i).getInterest());
							plan1.put("interestReal", list.get(i).getInterestReal());
							plan1.put("principal", list.get(i).getPrincipal());
							plan1.put("principalReal", list.get(i).getPrincipalReal());
							plan1.put("accountDate", list.get(i).getAccountDate());
							plan1.put("startDate", list.get(i).getStartDate());
							plan1.put("endDate", list.get(i).getEndDate());
							plan1.put("status", list.get(i).getStatus());
							array.put(plan1);
							
					}
					json.put("list", array);
				}
				
				logger.info("生成还款计划 接口调用——》"+Cons.Ips.IP_KIE_CREATEPLANS+"?"+json.toString());
				String data = InterfaceUtil.sendPostJson(Cons.Ips.IP_KIE_CREATEPLANS, json.toString());
				JSONObject resultJson = new JSONObject(data);
				JSONArray plansJson = resultJson.getJSONArray("list");
				if(plansJson != null){
					String lotNum = tRepayPlanService.createLotNum();
					for(int i=0;i<plansJson.length();i++){
						TRepayPlan repayPlan = (TRepayPlan) JsonMapper.fromJsonString(plansJson.get(i).toString(), TRepayPlan.class);
						repayPlan.setId(null);
						repayPlan.setLotNum(lotNum);
						repayPlan.setLoanContractId(contract.getId());
						//repayPlan.setStatus(Cons.RepayStatus.NO_PAID);
						repayPlan.setIsValid("1");
						tRepayPlanService.save(repayPlan);
					}
				}		
				advance.setStatus(Cons.AdvanceStatus.VALID);
				super.save(advance);
				//资本信息
				//1担保   2小贷           小贷才需要判断资本信息
				if(UserUtils.getUser().getCompany().getPrimaryPerson()!=null&& "2".equals(UserUtils.getUser().getCompany().getPrimaryPerson())){
					TCapital capital = capitalservice.getByOrganId(UserUtils.getUser().getCompany().getId());
					if(capital!=null){
						BigDecimal b1 = new BigDecimal(capital.getLoanamount()) ; //贷款注入金额
						capital.setLoanamount((b1.add(advance.getAdvanceamount())).toString());
						capitalservice.save(capital);
			        }
				}
			}
			
		}
		
		
		
	}
	/**
	 * 提前还款生效
	 * @Description 只有状态为审批中的才会执行
	 * @author zzm 
	 * @throws JSONException 
	 * @date 2016-6-2 下午2:00:25  
	 *//*
	@Transactional(readOnly = false)
	public void validAdvace(Advance advance) throws JSONException{
		if(StringUtils.equals(advance.getStatus(), Cons.AdvanceStatus.TO_ADUIT)){
			
			//更新合同的还款计划
			TLoanContract contract = tLoanContractService.get(advance.getLoanContractId());
			JSONObject json = new JSONObject();
			
			json.put("token", Cons.Ips.IP_KIE_TOKEN);
			json.put("businessType", "apply");
			//json.put("amount", contract.getLoanAmount());---贷款金额应该减去已还的本金  下方处理  lzj
			json.put("loanRate", contract.getLoanRate());
		//	json.put("loanPeriod",contract.getLoanPeriod());//---贷款周期应该减去已还的周期  下方处理  lzj
			if(contract.getLoanRateType().equals("年")){
				json.put("loanRateType", 1);
			}else if(contract.getLoanRateType().equals("月")){
				json.put("loanRateType", 2);
			}else if(contract.getLoanRateType().equals("日")){
				json.put("loanRateType", 3);
			}
			json.put("periodUnit", contract.getPeriodType());
			 
			//json.put("loanDate",new SimpleDateFormat("yyyy-MM-dd").format(contract.getLoanDate()));
			json.put("loanDate",DateUtils.formatDate(advance.getApplyDate()));
			json.put("payType", contract.getPayType());
			json.put("periodType", contract.getPeriodType());
			json.put("periodType", contract.getPeriodType());
			
			json.put("payAmount", advance.getAdvanceamount().setScale(1,BigDecimal.ROUND_HALF_UP));
			
			json.put("payDate",DateUtils.formatDate(advance.getApplyDate()));
			json.put("payOptions", contract.getPayOptions());
		
			
			TRepayPlan plan = new TRepayPlan();
			plan.setLoanContractId(advance.getLoanContractId());
			//plan.setIsValid("1");
			List<TRepayPlan> list = tRepayPlanService.findList(plan);
			int loanPeriod=list.size();
			BigDecimal leftAmount=new BigDecimal(0.0);//剩余本金
			JSONArray array = new JSONArray();
			if(list != null ){
				for(int i=0;i<list.size();i++){
					if(list.get(i).getStatus().equals(Cons.RepayStatus.PAID)){
						loanPeriod--;//如果还款记录为已结清，则贷款周期减去1
					}else if(list.get(i).getStatus().equals(Cons.RepayStatus.NO_PAID)){//只考虑未还的，因为未结清无法申请提前还款\
						BigDecimal bd=new BigDecimal(0.0);
						if(list.get(i).getPrincipal()!=null){
							 bd= new BigDecimal(list.get(i).getPrincipal());//(未还每一期还的本金)
						}
						
						leftAmount=leftAmount.add(bd);
					}
				}
				leftAmount=leftAmount.subtract(advance.getAdvanceamount());
				if(contract.getPayType().equals("4")){
					loanPeriod=loanPeriod-1;
				}
				json.put("loanPeriod", loanPeriod);
				json.put("unPayPeriod", loanPeriod);
				json.put("amount", leftAmount);
				json.put("list", array);
			}
			tRepayPlanService.inValidBatch(plan);//废弃旧的还款计划
			
		
			logger.info("生成还款计划 接口调用——》"+Cons.Ips.IP_KIE_CREATEPLANS+"?"+json.toString());
			String data = InterfaceUtil.sendPostJson(Cons.Ips.IP_KIE_CREATEPLANS, json.toString());
			JSONObject resultJson = new JSONObject(data);
			JSONArray plansJson = resultJson.getJSONArray("list");
			if(plansJson != null){
				String lotNum = tRepayPlanService.createLotNum();
				
				for(int i=0;i<plansJson.length();i++){
					TRepayPlan repayPlan = (TRepayPlan) JsonMapper.fromJsonString(plansJson.get(i).toString(), TRepayPlan.class);
					repayPlan.setLotNum(lotNum);
					repayPlan.setLoanContractId(contract.getId());
					repayPlan.setStatus(Cons.RepayStatus.NO_PAID);
					tRepayPlanService.save(repayPlan);
				}
			}
			advance.setStatus(Cons.AdvanceStatus.VALID);
			super.save(advance);
			//资本信息
			//1担保   2小贷           小贷才需要判断资本信息
			if(UserUtils.getUser().getCompany().getPrimaryPerson()!=null&& "2".equals(UserUtils.getUser().getCompany().getPrimaryPerson())){
				TCapital capital = capitalservice.getByOrganId(UserUtils.getUser().getCompany().getId());
				if(capital!=null){
					BigDecimal b1 = new BigDecimal(capital.getLoanamount()) ; //贷款注入金额
					capital.setLoanamount((b1.add(advance.getAdvanceamount())).toString());
					capitalservice.save(capital);
		        }
			}
			 // 2-1 还款记录 t_repay_real
			Page<RepayRecord> pageRepayRecord = new Page<RepayRecord>();
			RepayRecord repayRecord = new RepayRecord();
			repayRecord.setLoanContractId(contract.getId());
			repayRecord.setPage(pageRepayRecord);
			List<RepayRecord> repRecordList = repayRecordService.findList(repayRecord);
			for(RepayRecord rrd:repRecordList){//将原还款记录的isYuQi更新，防止更改还款计划后财务页面修改还款记录
				rrd.setIsYuQi("newPlan");//为不改动表结构，临时添加isYuQi状态为newPlan
				repayRecordService.updateNewPlan(rrd);
			}
		}
	}*/
	
	/**
	 * 提前还款失效
	 * @Description 只有状态为审批中的才会执行
	 * @author zzm 
	 * @date 2016-6-2 下午2:00:25  
	 */
	@Transactional(readOnly = false)
	public void invalidAdvace(Advance advance){
		if(StringUtils.equals(advance.getStatus(), Cons.AdvanceStatus.TO_ADUIT)){
			advance.setStatus(Cons.AdvanceStatus.INVALID);
			super.save(advance);
		}
	}
	/**
	 * 判断是否为最后节点
	 * @Description 
	 * @author lzj
	 */
	public Boolean checkIsEnd(Advance temp) {
		return actTaskService.checkIsEnd(temp.getAct());
	}
	
	
}
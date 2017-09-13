/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.repayplan.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.joda.time.DateTime;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.drew.lang.StringUtil;
import com.wanfin.fpd.common.config.Cons;
import com.wanfin.fpd.common.mapper.JsonMapper;
import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.service.CrudService;
import com.wanfin.fpd.common.service.ServiceException;
import com.wanfin.fpd.common.utils.DateUtils;
import com.wanfin.fpd.common.utils.InterfaceUtil;
import com.wanfin.fpd.common.utils.StringUtils;
import com.wanfin.fpd.modules.catipal.dao.TCaiwuDao;
import com.wanfin.fpd.modules.catipal.entity.TCaiwu;
import com.wanfin.fpd.modules.contract.dao.TLoanContractDao;
import com.wanfin.fpd.modules.contract.entity.TLoanContract;
import com.wanfin.fpd.modules.contract.service.TLoanContractService;
import com.wanfin.fpd.modules.receivables.service.RepayRecordService;
import com.wanfin.fpd.modules.repayplan.dao.TRepayPlanDao;
import com.wanfin.fpd.modules.repayplan.entity.TRepayPlan;
import com.wanfin.fpd.modules.repayplan.vo.PlanCreateParam;
import com.wanfin.fpd.modules.sys.entity.User;
import com.wanfin.fpd.modules.sys.utils.UserUtils;

/**
 * 还款计划Service
 * @author lx
 * @version 2016-03-22
 */
@Service("tRepayPlanService")
@Transactional(readOnly = true)
public class TRepayPlanService extends CrudService<TRepayPlanDao, TRepayPlan> {
	
	@Autowired
	TRepayPlanDao tRepayPlanDao;
	
	@Autowired
	private TLoanContractDao tLoanContractDao;


	public TRepayPlan get(String id) {
		return super.get(id);
	}
	
	public List<TRepayPlan> findList(TRepayPlan tRepayPlan) {
		//tRepayPlan.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser().getCurrentUser(), "o", "u"));//#2763
		List<TRepayPlan> repayList = new ArrayList<TRepayPlan>();
		int yuqi = 0;
		for(TRepayPlan repay:super.findList(tRepayPlan)){
			  if(repay.getYuQi()!=null){
					yuqi = (int) Float.parseFloat(repay.getYuQi());
					repay.setYuQi(String.valueOf(yuqi));
			  }
			  repayList.add(repay);
		}
		return repayList;
	}
	
	public List<TRepayPlan> findList2(TRepayPlan tRepayPlan) {
		tRepayPlan.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser().getCurrentUser(), "o", "u"));//#3522
		List<TRepayPlan> repayList = new ArrayList<TRepayPlan>();
		int yuqi = 0;
		for(TRepayPlan repay:super.findList(tRepayPlan)){
			  if(repay.getYuQi()!=null){
					yuqi = (int) Float.parseFloat(repay.getYuQi());
					repay.setYuQi(String.valueOf(yuqi));
			  }
			  repayList.add(repay);
		}
		return repayList;
	}
	
	public List<TRepayPlan> getPlanByContractId(String id) {
		TRepayPlan temp = new TRepayPlan();
		temp.setLoanContractId(id.toString());
		return super.dao.findListCondition(temp);
	}
	
	public Page<TRepayPlan> findPage(Page<TRepayPlan> page, TRepayPlan tRepayPlan) {
		tRepayPlan.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser().getCurrentUser(), "o", "u"));
		return super.findPage(page, tRepayPlan);
	}
	
	// add by srf
	public List<TRepayPlan> findListCondition(TRepayPlan tRepayPlan){
		if(Cons.RepayStatus.NEED_PAY.equals(tRepayPlan.getStatus())){
			tRepayPlan.setStatus(Cons.RepayStatus.NO_PAID+","+Cons.RepayStatus.IN_PAYMENT);
		}
		return super.dao.findListCondition(tRepayPlan);
	}
	
	// add by srf
	public List<TRepayPlan> findListByScan(TRepayPlan tRepayPlan){
		if(Cons.RepayStatus.NEED_PAY.equals(tRepayPlan.getStatus())){
			tRepayPlan.setStatus(Cons.RepayStatus.NO_PAID+","+Cons.RepayStatus.IN_PAYMENT);
		}
		return super.dao.findListCondition(tRepayPlan);
	}
	
	@Transactional(readOnly = false)
	public StringBuffer updateGetListByscanFlagData(String fileName) {	
		TRepayPlan query = new TRepayPlan();
		query.setScanFlag("0");
		TLoanContract tempLc = new TLoanContract();
		tempLc.setContractNumber("NOT-ET");
		List<TRepayPlan> queryList = tRepayPlanDao.findListByscanFlag(query);
		List<TRepayPlan> successList = new ArrayList<TRepayPlan>();
		StringBuffer data = new StringBuffer();
		if (queryList != null && queryList.size() > 0) {			
			for (TRepayPlan temp : queryList) {
				List<TLoanContract> tempList = tLoanContractDao.getLoanListsByIds("'" + temp.getLoanContractId() + "'");
				if (tempList != null && tempList.size() > 0) {
					data.append(temp.toData(temp,tempList.get(0)));
					data.append("\r\n");
					successList.add(temp);
				}				
			}

			for (TRepayPlan temp : successList) {				
				temp.setScanFlag("1");	
				temp.setPushStatus("1");	
				temp.preUpdate();
				tRepayPlanDao.updateByscanFlag(temp);
			}
		}		
		return data;
	}
	
	@Transactional(readOnly = false)
	public void updateListByscanFlagData(List list) {
		TRepayPlan response = new TRepayPlan();
		for (int i = 0; i < list.size(); i++) {				
			String tmp = (String) list.get(i);
			String []msg = tmp.split("\\|");	
			if (msg[0] != null && !msg[0].trim().equals("")) {
				if("A".equals(msg[0])){
					response.setId(msg[2].trim());
					response.setScanFlag("0");	
					response.setPushStatus("0");	
					response.preUpdate();
					tRepayPlanDao.updateByscanFlag(response);
				} else {
					response.setId(msg[2].trim());
					response.setScanFlag("0");	
					response.setPushStatus("1");	
					response.preUpdate();
					tRepayPlanDao.updateByscanFlag(response);
				}					
			}				
		}				
	}
	
	
	
	@Transactional(readOnly = false)
	public StringBuffer updateGetETListByscanFlagData(String fileName) {	
		TRepayPlan query = new TRepayPlan();
		query.setScanFlag("0");
		TLoanContract tempLc = new TLoanContract();
		tempLc.setContractNumber("-ET");
		query.setScanFlag("0");
		//query.setInformFilingType(informFilingType);
		List<TRepayPlan> queryList = tRepayPlanDao.findListByscanFlag(query);
		List<TRepayPlan> successList = new ArrayList<TRepayPlan>();
		StringBuffer data = new StringBuffer();
		if (queryList != null && queryList.size() > 0) {			
			for (TRepayPlan temp : queryList) {
				List<TLoanContract> tempList = tLoanContractDao.getLoanListsByIds("'" + temp.getLoanContractId() + "'");
				if (tempList != null && tempList.size() > 0) {
					data.append(temp.toString(temp, tempList.get(0)));
					data.append("\r\n");
					successList.add(temp);
				}				
			}
			for (TRepayPlan temp : successList) {				
				temp.setScanFlag("1");	
				temp.setPushStatus("1");	
				temp.preUpdate();
				tRepayPlanDao.updateByscanFlag(temp);
			}
		}		
		return data;
	}
	
	
	@Transactional(readOnly = false)
	public void updateETListByscanFlagData(List list) {
		TRepayPlan response = new TRepayPlan();
		for (int i = 0; i < list.size(); i++) {				
			String tmp = (String) list.get(i);
			String []msg = tmp.split("\\|");	
			if (msg[0] != null && !msg[0].trim().equals("")) {
				if("A".equals(msg[0])){
					response.setId(msg[2].trim());
					response.setScanFlag("0");	
					response.setPushStatus("0");	
					response.preUpdate();
					tRepayPlanDao.updateByscanFlag(response);
				} else {
					response.setId(msg[2].trim());
					response.setScanFlag("0");	
					response.setPushStatus("1");	
					response.preUpdate();
					tRepayPlanDao.updateByscanFlag(response);
				}					
			}				
		}				
	}
		
		
	// add by shif 2016-03-31
	public HashMap<String, Double> allRepayment(TRepayPlan tRepayPlan){
		return super.dao.allRepayment(tRepayPlan);
	}
	
	public List<TRepayPlan> findByGroup(TRepayPlan tRepayPlan) {
		return tRepayPlanDao.findByGroup(tRepayPlan);
	}
	/**
	 * @Description 期限内待还款的还款计划
	 * @param num	多少天内到还款期限
	 * @return
	 * @author zzm
	 * @date 2016-4-12 上午9:56:52  
	 */
	public List<TRepayPlan> findToDoRepayPlans(int num){
		TRepayPlan tRepayPlan = new TRepayPlan();
		//未还+未结清
		tRepayPlan.setStatus(Cons.RepayStatus.NO_PAID+","+Cons.RepayStatus.IN_PAYMENT);
		//时间范围
		Date beginAccountDate = DateUtils.parseDate(DateUtils.getDate());
		tRepayPlan.setBeginAccountDate(beginAccountDate);
		tRepayPlan.setEndAccountDate(DateUtils.addDays(beginAccountDate, num));
		return findList2(tRepayPlan);
	}
	
	@Transactional(readOnly = false)
	public void save(String endDate,String principal,String interest,String startDate,String accountDate,String counts,TLoanContract tLoanContract) {
		if ((StringUtils.isNotBlank(endDate))) {
			String[] numArray = counts.split(",");
			String[] endDateArray = endDate.split(",");//还款截止日期
			String[] moneyArray = principal.split(",");//还款本金
			String[] lixiArray =interest.split(",");//还款利息
			String[] startDateArray =startDate.split(",");//计息开始日期
			String[] accountDateArray = accountDate.split(",");//计划到账日
			int onePay = 1;
			for(int i = 0; i < numArray.length; i++){
				if(Integer.parseInt(numArray[i].trim())==0){
					onePay = 0;
				}
			}
			User user = UserUtils.getUser();
			if((user == null) || (user.getCompany() == null)){
				user = UserUtils.getAdmin();
			}
			for (int i = 0; i < endDateArray.length; i++) {
				TRepayPlan rPlan = new TRepayPlan();
				rPlan.setNum(Integer.valueOf(numArray[i].trim()));
				rPlan.setEndDate(endDateArray[i].trim());
				rPlan.setPrincipal(moneyArray[i].trim());
				rPlan.setInterest(lixiArray[i].trim());
				rPlan.setStatus("1");
				rPlan.setLoanContractId(tLoanContract.getId());;
				rPlan.setCsNum(0);
				rPlan.setStartDate(startDateArray[i].trim());
				rPlan.setAccountDate(accountDateArray[i].trim());
				if(Integer.parseInt(numArray[i].trim())== onePay){
					rPlan.setPayInterestStatus("1");				
				}
				//if(rPlan.getId() == null || "".equals(rPlan.getId())){					
					rPlan.setReportName(user.getCompany().getName());
					//caiwu.setOrganId(currentUser.getCompany().getId()); 
					//caiwu.setCreateBy(currentUser);	
				//}	
				
				rPlan.setScanFlag("0");	
				super.save(rPlan);
			}
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(TRepayPlan tRepayPlan) {
		super.delete(tRepayPlan);
	}
	
	@Transactional(readOnly = false)
	public void deletePLWL(TRepayPlan tRepayPlan) {
		super.dao.deletePLWL(tRepayPlan);
	}
	
	/**
	 * @Description 批量废弃还款计划
	 * @param loanContractId	合同id（必需）
	 * @param lotNum	还款计划批号
	 * @author zzm
	 * @date 2016-6-2 下午2:52:46  
	 */
	@Transactional(readOnly = false)
	public void inValidBatch(TRepayPlan tRepayPlan) {
		super.dao.inValidBatch(tRepayPlan);
	}
	
	/**
	 * @Description 生成还款计划批号
	 * @return
	 * @author zzm
	 * @date 2016-6-2 下午3:23:27  
	 */
	public String createLotNum(){
		Date date = new Date();
		return DateUtils.formatDate(date,"yyyyMMdd") + "-" + date.getTime();
	}

	/**
	 * @Description 生成还款计划
	 * @param param
	 * @param   
	 * 		贷款:
	 * 			{
	 * 				businessType: 'apply'（必填）,
	 * 				amount: 贷款金额（必填）,
	 * 				loanRate: 利率（必填）,
	 * 				loanPeriod: 期限（必填）,
	 * 				loanDate: 放款日期（必填）,
	 * 				payType: 还款方式（必填）,
	 * 				periodType: 还款周期,
	 * 				payOptions: 还款选项
	 * 			}
	 * 		展期:
	 * 			{
	 * 				businessType: 'extend'（必填）,
	 * 				businessId: '业务id'（必填）,
	 * 				amount: 展期金额（必填）,
	 * 				loanRate: 展期利率（必填）,
	 * 				loanPeriod: 展期期限（必填）,
	 * 				unPayPeriod: 未还期数（必填）,
	 * 				loanDate: 展期申请日期（必填）,
	 * 				payType: 还款方式（必填）,
	 * 				periodType: 还款周期,
	 * 				payOptions: 还款选项
	 * 			}
	 * 		提前还款:
	 * 			{
	 * 				businessType: 'earlyrepay'（必填）,
	 * 				businessId: '业务id'（必填）,
	 * 				loanRate: 利率（必填）,
	 * 				loanPeriod: 期限（必填）,
	 * 				payType: 还款方式（必填）,
	 * 				payAmount: 提前还款金额（必填）,
	 * 				payDate: 提前还款时间（必填）,
	 * 				payOptions: 还款选项,
	 * 				list: [{
							num:期数,
							principal: 计划收入本金,
							interest: 计划收入利息,
							startDate: 计息开始日期,
							endDate: 计息结束日期
						}] （必填）
	 * 			}
	 * 
	 * @author zzm
	 * @return
	 * @throws ServiceException
	 * @throws JSONException 
	 */
	public JSONArray createRepayPlans(PlanCreateParam param) throws ServiceException, JSONException{
		logger.info("生成还款计划  >>");
		TLoanContract tctContract=tLoanContractDao.get(param.getBusinessId());
		JSONObject json = new JSONObject();
		json.put("token", Cons.Ips.IP_KIE_TOKEN);
		json.put("businessType", param.getBusinessType());
		json.put("amount", param.getAmount());
		json.put("loanRate", param.getLoanRate());
		json.put("loanPeriod", param.getLoanPeriod());//期限
		json.put("periodUnit", param.getPeriodType());
		json.put("loanDate", param.getLoanDate());//放款日期
		json.put("payType", param.getPayType());
		json.put("periodType", param.getPeriodType());
		json.put("unPayPeriod", param.getUnPayPeriod());//未还期数
		json.put("payAmount", param.getPayAmount());
		json.put("payDate", param.getPayDate());
		json.put("payOptions", param.getPayOptions());
		json.put("loanRateType", param.getLoanRateType());
		if(StringUtils.isBlank(param.getPayDay()) || "undefined".equals(param.getPayDay())){
			json.put("payDay", "0");
		}else{
			json.put("payDay", param.getPayDay());
		}
		json.put("payPrincipalDate", param.getPayPrincipalDate());
		
		if(param.getIfRealityDay()!=null && !param.getIfRealityDay().equals("undefined")){
			if(param.getIfRealityDay().equals("1")){
				json.put("ifRealityDay",true);
			}else{
				json.put("ifRealityDay",false);
			}
		}else{
			json.put("ifRealityDay",false);//默认false
		}
		
		
		if("apply".equals(param.getBusinessType())){//贷款业务申请
			//校验贷款必填项
			//if(param.getAmount() <= 0) //old #3121
			if(param.getAmount().compareTo(new BigDecimal(0)) < 1) //#3121
				throw new ServiceException("贷款金额必须大于0");
			//if(param.getLoanRate() <= 0)  //old #3121
			if(param.getLoanRate().compareTo(new BigDecimal(0)) < 1)  //#3121
				throw new ServiceException("利率必须大于0");
			if(Integer.parseInt(param.getLoanPeriod()) <= 0) 
				throw new ServiceException("贷款期限必须大于0");
			if(StringUtils.isBlank(param.getLoanDate())) 
				throw new ServiceException("缺少参数：放款日期");
			if(StringUtils.isBlank(param.getPayType())) 
				throw new ServiceException("缺少参数：还款方式");
		}else if("extend".equals(param.getBusinessType())){//展期申请业务
			//校验展期必填项
			//if(param.getAmount() <= 0) //old #3121
			if(param.getAmount().compareTo(new BigDecimal(0)) < 1)  //#3121
				throw new ServiceException("展期金额必须大于0");
			//if(param.getLoanRate() <= 0)  //old #3121
			if(param.getLoanRate().compareTo(new BigDecimal(0)) < 1)  //#3121
				throw new ServiceException("展期利率必须大于0");
			if(Integer.parseInt(param.getLoanPeriod()) < 0) 
				throw new ServiceException("展期期限必须大于等于0");
			if(StringUtils.isBlank(param.getLoanDate())) 
				throw new ServiceException("缺少参数：放款日期");
			if(StringUtils.isBlank(param.getPayType())) 
				throw new ServiceException("缺少参数：还款方式");
			if(param.getExtendDays()==null){
				json.put("extendDays", 0);
			}else{
				json.put("extendDays", param.getExtendDays());
			}
			if(tctContract!=null){
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				TRepayPlan ent=new TRepayPlan();
				ent.setLoanContractId(tctContract.getId());
				ent.setStatus(Cons.RepayStatus.NO_PAID);
				String startDate="";
				List<TRepayPlan> reList=tRepayPlanDao.findListCondition(ent);//根据未还款计划获取第一条未还计划的开始计息日期
				startDate=reList.get(0).getStartDate();//获取展期开始时间
				try {
					ent.setStatus(Cons.RepayStatus.PAID);
					ent.setLoanContractId(tctContract.getId());
					List<TRepayPlan> aList=tRepayPlanDao.findListCondition(ent);//查询已还清还款记录
					if(aList!=null&&aList.size()>0){
						startDate=aList.get(aList.size()-1).getEndDate();//最后一条还款记录的结束时间
						startDate =format.format(new DateTime(format.parse(startDate)).plusDays(1).toDate());
					}
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				json.put("loanDate", startDate);
				//json.put("payPrincipalDate",startDate);
				int minPeriod=reList.get(0).getNum();
				int maxPeriod=reList.get(reList.size()-1).getNum();
				int unPayPeriod=maxPeriod-minPeriod+1;//获取未还期数
				
				if(tctContract.getPayType().equals("5")){//一次性还本付息
					TRepayPlan tPlan=new TRepayPlan();
					tPlan.setLoanContractId(tctContract.getId());
					List<TRepayPlan> onecesList=tRepayPlanDao.findList(tPlan);
					if(onecesList.size()>0 && onecesList.get(0).getStatus().equals(Cons.RepayStatus.PAID)){//利息已结清
						//json.put("unPayPeriod",param.getLoanPeriod());//未还期数
						json.put("unPayPeriod",0);//未还期数
						//json.put("loanDate", onecesList.get(0).getEndDate());
						json.put("loanDate", format.format(new DateTime(onecesList.get(0).getEndDate()).plusDays(1).toDate()));
					}else{
						json.put("unPayPeriod",tctContract.getLoanPeriod());//未还期数
					}
				}else{
					json.put("unPayPeriod",unPayPeriod);//未还期数
				}
				
			}		
			
			
			
		}else if("earlyrepay".equals(param.getBusinessType())){//提前还款业务
			//校验提前还款必填项
			//if(param.getPayAmount() <= 0)   //old #3121
			if(param.getPayAmount().compareTo(new BigDecimal(0)) < 1)  //#3121
				throw new ServiceException("提前还款金额必须大于0");
			//if(param.getLoanRate() <= 0)   //old #3121
			if(param.getLoanRate().compareTo(new BigDecimal(0)) < 1)  //#3121
				throw new ServiceException("利率必须大于0");
			if(Integer.parseInt(param.getLoanPeriod()) <= 0) 
				throw new ServiceException("贷款期限必须大于0");
			if(StringUtils.isBlank(param.getPayDate())) 
				throw new ServiceException("缺少参数：提前还款时间");
			if(StringUtils.isBlank(param.getPayType())) 
				throw new ServiceException("缺少参数：还款方式");
			if(param.getList() == null || param.getList().size() == 0) 
				throw new ServiceException("缺少数据：原还款计划");
			
			List<TRepayPlan> list = param.getList();
			JSONArray array = new JSONArray();
			if(list != null ){
				for(int i=0;i<list.size();i++){
					JSONObject plan = new JSONObject();
					plan.put("id", list.get(i).getId());
					plan.put("num", list.get(i).getNum());
					plan.put("interest", list.get(i).getInterest());
					plan.put("interestReal", list.get(i).getInterestReal());
					plan.put("principal", list.get(i).getPrincipal());
					plan.put("principalReal", list.get(i).getPrincipalReal());
					plan.put("accountDate", list.get(i).getAccountDate());
					plan.put("startDate", list.get(i).getStartDate());
					plan.put("endDate", list.get(i).getEndDate());
					plan.put("status", list.get(i).getStatus());
					array.put(plan);
					
				}
			}
			
			json.put("list", array);
		}
		
		logger.info("生成还款计划 接口调用——》"+Cons.Ips.IP_KIE_CREATEPLANS+"? data = "+json.toString());
		String result = InterfaceUtil.sendPostJson(Cons.Ips.IP_KIE_CREATEPLANS, json.toString());
		logger.info("result="+result);
		try {
			JSONObject resultJson = new JSONObject(result);
			String code = resultJson.optString("code");
			if(code.equals("error")){
				throw new ServiceException(resultJson.getString("message"));
			}
			JSONArray list = resultJson.getJSONArray("list");
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("数据转换异常");
		}finally{
			logger.info("生成还款计划  结束 <<");
		}
		
		
	}
	
	
	@Transactional(readOnly = false)
	public List<TRepayPlan> upDateFullRepayPlans(PlanCreateParam param) throws ServiceException, JSONException{
		JSONArray repayPlanArray = null;
		if(StringUtils.isBlank(param.getLoanRateType())){
		}else if("年".equals(param.getLoanRateType())){
			param.setLoanRateType("1");//利率类型   1年  2月  3日  
		}else if("月".equals(param.getLoanRateType())){
			param.setLoanRateType("2");//利率类型   1年  2月  3日  
		}else if("日".equals(param.getLoanRateType())){
			param.setLoanRateType("3");//利率类型   1年  2月  3日  
		}
		
		if(StringUtils.isNotBlank(param.getBusinessId())){
			TLoanContract tempLc = tLoanContractDao.get(param.getBusinessId());
			if(tempLc!=null){
				tempLc.setLoanAmount(param.getAmount().toString());
				tempLc.setLoanRate(param.getLoanRate().toString());
				//tempLc.setLoanType (param.getLoanRateType());
				tempLc.setLoanPeriod(param.getLoanPeriod());
				tempLc.setLoanDate(DateUtils.parseDate(param.getLoanDate()));
				tempLc.setPayType(param.getPayType());
				tempLc.setPeriodType(param.getPeriodType());
				tempLc.setPayDay(param.getPayDay());
				tempLc.setPayOptions(param.getPayOptions());
				tempLc.setPayPrincipalDate(DateUtils.parseDate(param.getPayPrincipalDate()));
				tempLc.setIfRealityDay(param.getIfRealityDay());
				tLoanContractDao.update(tempLc);
			}
			
		}

		repayPlanArray = createRepayPlans(param);//从KIE获取还款计划
		if(repayPlanArray != null && repayPlanArray.length()>0){
			//删除旧的还款计划
			TRepayPlan repayPlan = new TRepayPlan();
			repayPlan.setLoanContractId(param.getBusinessId());
			List<TRepayPlan> repayPlanList = findList(repayPlan);
			if(repayPlanList != null && repayPlanList.size()>0){
				for(TRepayPlan delRp:repayPlanList){
					delete(delRp);
				}
			}
			
			List<TRepayPlan> rplist = new ArrayList<TRepayPlan>();
			String lotNum = createLotNum();
			
			for(int i=0;i<repayPlanArray.length();i++){
				TRepayPlan temp = (TRepayPlan) JsonMapper.fromJsonString(repayPlanArray.get(i).toString(), TRepayPlan.class);
				temp.setLotNum(lotNum);
				temp.setLoanContractId(param.getBusinessId());
				temp.setStatus(Cons.RepayStatus.NO_PAID);
				temp.setIsValid("1");
				
				//保存到数据库
				super.save(temp);
				
				rplist.add(temp);
			}
			
			if(rplist != null && rplist.size()>0){
				return rplist;
			}
		}
		
		return null;
	}

	public List<TRepayPlan> findListByNum(TRepayPlan tp) {
		if(Cons.RepayStatus.NEED_PAY.equals(tp.getStatus())){
			tp.setStatus(Cons.RepayStatus.NO_PAID+","+Cons.RepayStatus.IN_PAYMENT);
		}
		return super.dao.findListByNum(tp);
	}

	public List<TRepayPlan> findAllListByWish(TRepayPlan overPlan) {
		return super.dao.findAllListByWish(overPlan);
	}

	public String getFirstTime(String tLoanContractId) {
		return super.dao.getFirstTime(tLoanContractId);
	}

	
}
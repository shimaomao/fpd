/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.receivables.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.wanfin.fpd.common.config.Cons;
import com.wanfin.fpd.common.config.Global;
import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.service.CrudService;
import com.wanfin.fpd.common.utils.DateUtils;
import com.wanfin.fpd.common.utils.StringUtils;
import com.wanfin.fpd.modules.catipal.dao.TCapitalDao;
import com.wanfin.fpd.modules.catipal.entity.TCapital;
import com.wanfin.fpd.modules.contract.entity.TLoanContract;
import com.wanfin.fpd.modules.contract.service.TLoanContractService;
import com.wanfin.fpd.modules.credit.entity.CustomerCredit;
import com.wanfin.fpd.modules.credit.service.CustomerCreditService;
import com.wanfin.fpd.modules.receivables.dao.RepayRecordDao;
import com.wanfin.fpd.modules.receivables.entity.RepayRecord;
import com.wanfin.fpd.modules.repayplan.entity.TRepayPlan;
import com.wanfin.fpd.modules.repayplan.service.TRepayPlanService;
import com.wanfin.fpd.modules.sys.entity.User;
import com.wanfin.fpd.modules.sys.utils.UserUtils;
import com.wanfin.fpd.modules.wish.contract.vo.RepayRecordVo;

/**
 * 真实还款记录Service
 * 
 * @author srf
 * @version 2016-03-29
 */
@Service
@Transactional(readOnly = true)
public class RepayRecordService extends CrudService<RepayRecordDao, RepayRecord> {
	// 还款计划
	@Autowired
	private TRepayPlanService repayPlanService;
	@Autowired
	private TLoanContractService loanContractService;
	@Autowired
	private TLoanContractService tLoanContractService;
	
	@Autowired
	private CustomerCreditService customerCreditService;
	@Autowired
	private TCapitalDao tcapitaldao;
	@Autowired
	private RepayRecordDao repayRecordDao;

	public RepayRecord get(String id) {
		return super.get(id);
	}

	public List<RepayRecord> findList(RepayRecord repayRecord) {
		return super.findList(repayRecord);
	}

	public Page<RepayRecord> findPage(Page<RepayRecord> page, RepayRecord repayRecord) {
		return super.findPage(page, repayRecord);
	}

	/**
	 * 保存还款记录的同时还需要处理还款计划表中的数据 如果还有余额怎么处理？在先前先处理掉：不让还 TODO
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void save(RepayRecord repayRecord) {
		if(repayRecord != null){
			if(StringUtils.isEmpty(repayRecord.getOrganId())){
				repayRecord.setOrganId(UserUtils.getUser().getCompany().getId());
			}
		}
		repayDeals(repayRecord);

	}

	/**
	 * 修改还款记录的同时还需要处理还款计划表中的数据
	 *  @param oldRepayRecord 旧数据
	 *   @param newRepayRecord 新数据
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void upDate(RepayRecord oldRepayRecord, RepayRecord newRepayRecord) {
		// 先查对应的实际还款记录
//		RepayRecord oldRepayRecord = super.get(newRepayRecord.getId());

		// 回退原记录操作
		rollBackDeals(oldRepayRecord);

		// 新数据进行操作
		repayDeals(newRepayRecord);

	}

	@Transactional(readOnly = false)
	public void delete(RepayRecord repayRecord) {
		super.delete(repayRecord);
	}

	/**
	 * 进行各期的还款处理，先还最早的-----------本金利息一起还
	 * 
	 * @param repayRecord
	 */
	private void repayDeals(RepayRecord repayRecord) {
		TRepayPlan tRepayPlan = new TRepayPlan();
		tRepayPlan.setLoanContractId(repayRecord.getLoanContractId());// 对应的项目ID
		tRepayPlan.setStatus(Cons.RepayStatus.NEED_PAY);// 只要未结清状态，修改状态值可能需要同时修改mybatis对应的xml配置文件

		// 获取还款计划中需要还款的信息
		List<TRepayPlan> repayPlanList = repayPlanService.findListCondition(tRepayPlan);
		// 还款金额
		BigDecimal repayMoney = BigDecimal.valueOf(repayRecord.getMoney() == null ? 0.0 : repayRecord.getMoney()).setScale(2, BigDecimal.ROUND_HALF_UP);
		boolean ifOverdue = false;//本次还款是否逾期
		
		
		// 处理还款计划中的内容,考虑全部还完还有余额的情况
		for (int i = 0; i < repayPlanList.size() && repayMoney.doubleValue() >= 0; i++) {
			TRepayPlan tmpRepayPlan = repayPlanList.get(i);

			// 需要处理为空的情况
			BigDecimal interest =  StringUtils.isBlank(tmpRepayPlan.getInterest()) ? new BigDecimal(0) : new BigDecimal(tmpRepayPlan.getInterest());// 利息
			BigDecimal interestReal = StringUtils.isBlank(tmpRepayPlan.getInterestReal()) ? new BigDecimal(0) : new BigDecimal(tmpRepayPlan.getInterestReal());// 真实利息
			BigDecimal principal =  StringUtils.isBlank(tmpRepayPlan.getPrincipal())? new BigDecimal(0) : new BigDecimal(tmpRepayPlan.getPrincipal());// 本金
			BigDecimal principalReal =  StringUtils.isBlank(tmpRepayPlan.getPrincipalReal()) ? new BigDecimal(0) : new BigDecimal(tmpRepayPlan.getPrincipalReal());// 真实本金
			// 先还利息
			BigDecimal interDif = interest.subtract(interestReal).setScale(2, BigDecimal.ROUND_HALF_UP);// 需要还的利息
			BigDecimal princDif = principal.subtract(principalReal).setScale(2, BigDecimal.ROUND_HALF_UP);// 需要还的本金

			// 该项不需要还款
			if (interDif.doubleValue() <= 0 && princDif.doubleValue() <= 0) {
				tmpRepayPlan.setPayInterestStatus(Cons.RepayStatus.PAID);
				tmpRepayPlan.setStatus(Cons.RepayStatus.PAID);
				repayPlanService.save(tmpRepayPlan);
				continue;
			}
			
			if(interDif.doubleValue() > 0){
				if (repayMoney.compareTo(interDif) >= 0) {
					// 利息还清
					tmpRepayPlan.setInterestReal(tmpRepayPlan.getInterest());
					
					// 计算余额
					repayMoney = repayMoney.subtract(interDif).setScale(2, BigDecimal.ROUND_HALF_UP);
					
					// 付息状态--已结清
					tmpRepayPlan.setPayInterestStatus(Cons.RepayStatus.PAID);
					
					// 结清日期
					tmpRepayPlan.setOverDate(DateUtils.formatDate(repayRecord.getRepayDate()));//Bug #3332
					
					//还款状态---已结清
					tmpRepayPlan.setStatus(Cons.RepayStatus.PAID);
				} else {
					if(repayMoney.compareTo(BigDecimal.ZERO)>0){//Bug #4860
					// 不够还利息，全部用于还利息了
					tmpRepayPlan.setInterestReal(interestReal.add(repayMoney).toString());
					repayMoney = new BigDecimal(0);// 还剩余额
					
					// 付息状态---未结清
					tmpRepayPlan.setPayInterestStatus(Cons.RepayStatus.IN_PAYMENT);// TODO
					
					//还款状态----未结清
					tmpRepayPlan.setStatus(Cons.RepayStatus.IN_PAYMENT);
					}
				}
				// 付息时间
				tmpRepayPlan.setPayInterestDate(repayRecord.getRepayDate());
			}

			// --------------------------------------
			if(princDif.doubleValue() > 0){
				TLoanContract contract = tLoanContractService.get(repayRecord.getLoanContractId());
				//还款之后，授信可用额度加
				CustomerCredit credit = customerCreditService.getByCustomerId(contract.getCustomerId());
				if(credit!=null){//W端申请过来的业务没有授信记录
					credit.setBalance(new BigDecimal(Double.parseDouble(credit.getBalance()+"")+Double.parseDouble(repayMoney+"")));
					customerCreditService.save(credit);
				}
				
				
				// 再还本金
				if (repayMoney.compareTo(princDif) >= 0) {// 本次还完
					tmpRepayPlan.setPrincipalReal(tmpRepayPlan.getPrincipal());
				/*	-------------------Bug #3419---------------------------*/
					//2为小贷，为小贷才需要修改可放贷资金
					if(UserUtils.getUser().getCompany().getPrimaryPerson()!=null&&"2".equals(UserUtils.getUser().getCompany().getPrimaryPerson())){
						//修改可放贷资金
						TCapital capital = tcapitaldao.getByOrganId(UserUtils.getUser().getCompany().getId());
						BigDecimal loanamount = new BigDecimal(capital.getLoanamount()) ; //放贷注入资本（可贷款金额）
						capital.setLoanamount(loanamount.add(new BigDecimal(tmpRepayPlan.getPrincipal()))+"");//加上这次回收的本金
						tcapitaldao.update(capital);
					
					}
				/*	----------------------------------------------*/
					
					repayMoney = repayMoney.subtract(princDif).setScale(2, BigDecimal.ROUND_HALF_UP);// 还剩余额
	
					// 结清日期
					tmpRepayPlan.setOverDate(DateUtils.formatDate(repayRecord.getRepayDate()));
					// 到账时间 TODO
					//tmpRepayPlan.setAccountDate(tmpRepayPlan.getOverDate());// 同结清时间一直
					// 状态 结清
					tmpRepayPlan.setStatus(Cons.RepayStatus.PAID);
				} else {
					if(repayMoney.compareTo(BigDecimal.ZERO)>0){//Bug #4860
						tmpRepayPlan.setPrincipalReal(principalReal.add(repayMoney).toString());
						/*	---------------------Bug #3419-------------------------*/
						if(UserUtils.getUser().getCompany().getPrimaryPerson()!=null&&"2".equals(UserUtils.getUser().getCompany().getPrimaryPerson())){
							//修改可放贷资金
							TCapital capital = tcapitaldao.getByOrganId(UserUtils.getUser().getCompany().getId());
							BigDecimal loanamount = new BigDecimal(capital.getLoanamount()) ; //放贷注入资本（可贷款金额）
							capital.setLoanamount(loanamount.add(repayMoney)+"");//加上这次回收的本金
							tcapitaldao.update(capital);
						
						}
						/*	----------------------------------------------*/
						repayMoney = new BigDecimal(0);// 还剩余额
		
						// 状态 未结清
						tmpRepayPlan.setStatus(Cons.RepayStatus.IN_PAYMENT);
					}
					
				}
				
			}

			// 是否逾期、开始还款金额
			double overdueDays = DateUtils.getDistanceOfTwoDate(DateUtils.parseDate(tmpRepayPlan.getEndDate()),
					repayRecord.getRepayDate());
			if (overdueDays > 0) {//逾期
				tmpRepayPlan.setYuQi(String.valueOf(overdueDays));
				tmpRepayPlan.setIsYuQi(Global.YES);
				ifOverdue = true;
			}else{//未逾期
				tmpRepayPlan.setIsYuQi(Global.NO);
				tmpRepayPlan.setYuQi("0");
			}

			
			// 将数据保存回到数据库
			repayPlanService.save(tmpRepayPlan);
		}
		
		/*//2为小贷，为小贷才需要修改可放贷资金------------Bug #3419由于注入资本至处理本金所以转到上面代码处理
		if(UserUtils.getUser().getCompany().getPrimaryPerson()!=null&&"2".equals(UserUtils.getUser().getCompany().getPrimaryPerson())){
			//修改可放贷资金
			TCapital capital = tcapitaldao.getByOrganId(UserUtils.getUser().getCompany().getId());
			BigDecimal loanamount = new BigDecimal(capital.getLoanamount()) ; //放贷注入资本（可贷款金额）
			capital.setLoanamount(loanamount.add(new BigDecimal(repayRecord.getMoney()))+"");//加上这次回收金额
			tcapitaldao.update(capital);
		
		}*/
		if(ifOverdue){//逾期
			repayRecord.setIsYuQi(Global.YES);
		}else{
			repayRecord.setIsYuQi(Global.NO);
		}
		
		//检查对应合同的款项是否已经还完
		// 获取需要还款的数据
		HashMap<String, Double> map = repayPlanService.allRepayment(tRepayPlan);
		if(map != null && map.size()>0){
			Double interest = map.get("interest") == null ? 0.0 : map.get("interest");// 利息
			Double interestReal = map.get("interest_real") == null ? 0.0 : map.get("interest_real");// 真实利息
			Double principal = map.get("principal") == null ? 0.0 : map.get("principal");// 本金
			Double principalReal = map.get("principal_real") == null ? 0.0 : map.get("principal_real");// 真实本金
			// 所需还款总额
			Double needRepay = BigDecimal.valueOf(interest + principal).subtract(BigDecimal.valueOf(interestReal + principalReal)).doubleValue();
			if(needRepay == 0){//款项已经还清
				TLoanContract tLoanContract = new TLoanContract();
				tLoanContract.setId(repayRecord.getLoanContractId());
				tLoanContract.setStatus(Cons.LoanContractStatus.CLEARED);
				loanContractService.updateStatus(tLoanContract);
			}
		}
		
		
		User user = UserUtils.getUser();
		if((user == null) || (user.getCompany() == null)){
			user = UserUtils.getAdmin();
		}
		//if(repayRecord.getId() == null || "".equals(repayRecord.getId())){					
			repayRecord.setReportName(user.getCompany().getName());
			//caiwu.setOrganId(currentUser.getCompany().getId()); 
			//caiwu.setCreateBy(currentUser);	
		//}	
		
		repayRecord.setScanFlag("0");	
		super.save(repayRecord);
	}
	/**
	 * 单期还款-----------本金利息分开还
	 * 
	 * @param repayRecord
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void singleRepayDeals(RepayRecord repayRecord) {
		if(repayRecord != null){
			if(StringUtils.isEmpty(repayRecord.getOrganId())){
				repayRecord.setOrganId(UserUtils.getUser().getCompany().getId());
			}
		}
		TRepayPlan tRepayPlan = new TRepayPlan();
		tRepayPlan.setLoanContractId(repayRecord.getLoanContractId());// 对应的项目ID
		tRepayPlan.setStatus(Cons.RepayStatus.NEED_PAY);// 只要未结清状态，修改状态值可能需要同时修改mybatis对应的xml配置文件

		// 获取还款计划中需要还款的信息
		List<TRepayPlan> repayPlanList = repayPlanService.findListCondition(tRepayPlan);
		// 还款本金
		BigDecimal rePrincipal = BigDecimal.valueOf(repayRecord.getRePrincipal() == null ? 0.0 : repayRecord.getRePrincipal()).setScale(2, BigDecimal.ROUND_HALF_UP);
		// 还款利息
		BigDecimal reInterest = BigDecimal.valueOf(repayRecord.getReInterest() == null ? 0.0 : repayRecord.getReInterest()).setScale(2, BigDecimal.ROUND_HALF_UP);
		// 还款金额
		BigDecimal repayMoney= rePrincipal.add(reInterest);
		
		repayRecord.setMoney(repayMoney.doubleValue());
		boolean ifOverdue = false;//本次还款是否逾期
		
		
		// 处理还款计划中的内容,考虑全部还完还有余额的情况
		for (int i = 0; i < repayPlanList.size() && repayMoney.doubleValue() >= 0; i++) {
			TRepayPlan tmpRepayPlan = repayPlanList.get(i);
            if(tmpRepayPlan.getNum()==repayRecord.getNum()){
			// 需要处理为空的情况
			BigDecimal interest = tmpRepayPlan.getInterest() == null ? new BigDecimal(0) : new BigDecimal(tmpRepayPlan.getInterest());// 利息
			BigDecimal interestReal = tmpRepayPlan.getInterestReal() == null ? new BigDecimal(0) : new BigDecimal(tmpRepayPlan.getInterestReal());// 真实利息
			BigDecimal principal = tmpRepayPlan.getPrincipal() == null ? new BigDecimal(0) : new BigDecimal(tmpRepayPlan.getPrincipal());// 本金
			BigDecimal principalReal = tmpRepayPlan.getPrincipalReal() == null ? new BigDecimal(0) : new BigDecimal(tmpRepayPlan.getPrincipalReal());// 真实本金
			// 先还利息
			BigDecimal interDif = interest.subtract(interestReal).setScale(2, BigDecimal.ROUND_HALF_UP);// 需要还的利息
			BigDecimal princDif = principal.subtract(principalReal).setScale(2, BigDecimal.ROUND_HALF_UP);// 需要还的本金

			// 该项不需要还款
			if (interDif.doubleValue() <= 0 && princDif.doubleValue() <= 0) {
				tmpRepayPlan.setPayInterestStatus(Cons.RepayStatus.PAID);
				tmpRepayPlan.setStatus(Cons.RepayStatus.PAID);
				tmpRepayPlan.setOverDate(DateUtils.formatDate(repayRecord.getRepayDate()));
				repayPlanService.save(tmpRepayPlan);
				continue;
			}
			if(interDif.compareTo(reInterest) > 0){
				
				tmpRepayPlan.setInterestReal(interestReal.add(reInterest).toString());
				// 付息状态--未结清
				if(repayRecord.getIsFinsh().equals("1")){
					tmpRepayPlan.setPayInterestStatus(Cons.RepayStatus.PAID);
					tmpRepayPlan.setStatus(Cons.RepayStatus.PAID);
					// 结清日期
					tmpRepayPlan.setOverDate(DateUtils.formatDate(repayRecord.getRepayDate()));
				}else{
					tmpRepayPlan.setPayInterestStatus(Cons.RepayStatus.IN_PAYMENT);
					tmpRepayPlan.setStatus(Cons.RepayStatus.IN_PAYMENT);
					// 付息时间
					tmpRepayPlan.setPayInterestDate(repayRecord.getRepayDate());
				}
				
			}else if(interDif.compareTo(reInterest)==0){
				tmpRepayPlan.setInterestReal(interestReal.add(reInterest).toString());
				// 付息状态--已结清
				tmpRepayPlan.setPayInterestStatus(Cons.RepayStatus.PAID);
				tmpRepayPlan.setStatus(Cons.RepayStatus.PAID);
				// 付息时间
				tmpRepayPlan.setPayInterestDate(repayRecord.getRepayDate());
				// 结清日期
				tmpRepayPlan.setOverDate(DateUtils.formatDate(repayRecord.getRepayDate()));
			}
			
			if(princDif.compareTo(rePrincipal) > 0 && princDif.doubleValue()>0){//#5477
				tmpRepayPlan.setPrincipalReal(principalReal.add(rePrincipal).toString());
				// 付息状态--未结清
				if(repayRecord.getIsFinsh().equals("1")){
					tmpRepayPlan.setPayInterestStatus(Cons.RepayStatus.PAID);
					tmpRepayPlan.setStatus(Cons.RepayStatus.PAID);
					tmpRepayPlan.setOverDate(DateUtils.formatDate(repayRecord.getRepayDate()));
				}else{
					tmpRepayPlan.setPayInterestStatus(Cons.RepayStatus.IN_PAYMENT);
					tmpRepayPlan.setStatus(Cons.RepayStatus.IN_PAYMENT);
				}
				
			}else if(princDif.compareTo(rePrincipal) ==0){
				tmpRepayPlan.setPrincipalReal(principalReal.add(rePrincipal).toString());
				// 付息状态--已结清
				// 付息时间
				tmpRepayPlan.setOverDate(DateUtils.formatDate(repayRecord.getRepayDate()));
				
				tmpRepayPlan.setPayInterestStatus(Cons.RepayStatus.PAID);
				tmpRepayPlan.setStatus(Cons.RepayStatus.PAID);
			}
			
			//2为小贷，为小贷才需要修改可放贷资金
			if(UserUtils.getUser().getCompany().getPrimaryPerson()!=null&&"2".equals(UserUtils.getUser().getCompany().getPrimaryPerson())){
				//修改可放贷资金
				TCapital capital = tcapitaldao.getByOrganId(UserUtils.getUser().getCompany().getId());
				BigDecimal loanamount = new BigDecimal(capital.getLoanamount()) ; //放贷注入资本（可贷款金额）
				capital.setLoanamount(loanamount.add(rePrincipal)+"");//加上这次回收的本金
				tcapitaldao.update(capital);
			
			}
			
			TLoanContract contract = tLoanContractService.get(repayRecord.getLoanContractId());
			//还款之后，授信可用额度加
			CustomerCredit credit = customerCreditService.getByCustomerId(contract.getCustomerId());
			if(credit!=null){//W端申请过来的业务没有授信记录
				credit.setBalance(new BigDecimal(Double.parseDouble(credit.getBalance()+"")+Double.parseDouble(repayMoney+"")));
				customerCreditService.save(credit);
			}
			
			// -------------------------------------
			// 是否逾期、开始还款金额
			double overdueDays = DateUtils.getDistanceOfTwoDate(DateUtils.parseDate(tmpRepayPlan.getEndDate()),
					repayRecord.getRepayDate());
			if (overdueDays > 0) {//逾期
				tmpRepayPlan.setYuQi(String.valueOf(overdueDays));
				tmpRepayPlan.setIsYuQi(Global.YES);
				ifOverdue = true;
			}else{//未逾期
				tmpRepayPlan.setIsYuQi(Global.NO);
				tmpRepayPlan.setYuQi("0");
			}
			// 将数据保存回到数据库
			repayPlanService.save(tmpRepayPlan);
		 }
		}
		
		if(ifOverdue){//逾期
			repayRecord.setIsYuQi(Global.YES);
		}else{
			repayRecord.setIsYuQi(Global.NO);
		}
		
		//检查对应合同的款项是否已经还完
		// 获取需要还款的数据
		HashMap<String, Double> map = repayPlanService.allRepayment(tRepayPlan);
		if(map != null && map.size()>0){
			Double interest = map.get("interest") == null ? 0.0 : map.get("interest");// 利息
			Double interestReal = map.get("interest_real") == null ? 0.0 : map.get("interest_real");// 真实利息
			Double principal = map.get("principal") == null ? 0.0 : map.get("principal");// 本金
			Double principalReal = map.get("principal_real") == null ? 0.0 : map.get("principal_real");// 真实本金
			// 所需还款总额
			Double needRepay = BigDecimal.valueOf(interest + principal).subtract(BigDecimal.valueOf(interestReal + principalReal)).doubleValue();
			if(needRepay == 0){//款项已经还清
				TLoanContract tLoanContract = new TLoanContract();
				tLoanContract.setId(repayRecord.getLoanContractId());
				tLoanContract.setStatus(Cons.LoanContractStatus.CLEARED);
				loanContractService.updateStatus(tLoanContract);
			}
		}
		User user = UserUtils.getUser();
		if((user == null) || (user.getCompany() == null)){
			user = UserUtils.getAdmin();
		}
		//if(repayRecord.getId() == null || "".equals(repayRecord.getId())){					
			repayRecord.setReportName(user.getCompany().getName());
			//caiwu.setOrganId(currentUser.getCompany().getId()); 
			//caiwu.setCreateBy(currentUser);	
		//}	
		
		repayRecord.setScanFlag("0");	
		super.save(repayRecord);
	}

	/**
	 * 对上次错误的操作进行还原处理，先退近的记录,操作成功后对回滚的还款记录金额清零
	 * 
	 * @param repayRecord
	 */
	private void rollBackDeals(RepayRecord repayRecord) {
		Page<TRepayPlan> page = new Page<TRepayPlan>();// 用于设置排序方式
		page.setOrderBy("num desc");
		TRepayPlan tmpRepayPlan = new TRepayPlan();
		tmpRepayPlan.setLoanContractId(repayRecord.getLoanContractId());
		tmpRepayPlan.setPage(page);

		double rollbackMoney = repayRecord.getMoney();
		
		//获取取消该次还款记录前一次还款信息
		List<RepayRecord> oldList = super.dao.getOneByOther(repayRecord);
		RepayRecord oldRepayRecord=new RepayRecord();
		if(oldList.size()>1){
			oldRepayRecord=oldList.get(1);//如果有多次则取时间降序的第二条的还款时间
		}else{
			oldRepayRecord=oldList.get(0);//只有一次
		}
		
		List<TRepayPlan> repayPlanList = repayPlanService.findList(tmpRepayPlan);
		for (int i = 0; i < repayPlanList.size() && rollbackMoney > 0; i++) {
			tmpRepayPlan = repayPlanList.get(i);

			// 利息
			double interestReal = tmpRepayPlan.getInterestReal() == null ? 0 :Double.valueOf(tmpRepayPlan.getInterestReal());// 真实利息
			// 本金
			double principalReal = tmpRepayPlan.getPrincipalReal() == null ? 0 :Double.valueOf(tmpRepayPlan.getPrincipalReal());// 真实本金
		/*// 没有还款的记录不需要处理
			if (interestReal <= 0 && principalReal <= 0) {
				continue;
			}*/
			// 由上段注释代码片段更改如下------------Bug #3386
			if (interestReal <= 0 && principalReal <= 0) {//不必处理的还款计划直接退出
				tmpRepayPlan.setStatus(Cons.RepayStatus.NO_PAID);
				tmpRepayPlan.setYuQi("0");
				tmpRepayPlan.setIsYuQi(Global.NO);
				repayPlanService.save(tmpRepayPlan);
				continue;
			}
			// 先退本金
			if (rollbackMoney >= principalReal) {// 本金全退回
				tmpRepayPlan.setPrincipalReal("0");
				rollbackMoney -= principalReal;
				/*	-------------------Bug #3419---------------------------*/
				//2为小贷，为小贷才需要修改可放贷资金
				if(UserUtils.getUser().getCompany().getPrimaryPerson()!=null&&"2".equals(UserUtils.getUser().getCompany().getPrimaryPerson())){
					//修改可放贷资金
					TCapital capital = tcapitaldao.getByOrganId(UserUtils.getUser().getCompany().getId());
					BigDecimal loanamount = new BigDecimal(capital.getLoanamount()) ; //放贷注入资本（可贷款金额）
					capital.setLoanamount(loanamount.subtract(new BigDecimal(principalReal))+"");//减去这次回收的本金
					tcapitaldao.update(capital);
				
				}
				/*-----------------------------------*/
				// 状态 未还
				tmpRepayPlan.setStatus(Cons.RepayStatus.NO_PAID);
				// 到账时间
				//tmpRepayPlan.setAccountDate(null);// TODO
			} else {
				tmpRepayPlan.setPrincipalReal(String.valueOf(principalReal - rollbackMoney));
				/*	-------------------Bug #3419---------------------------*/
				//2为小贷，为小贷才需要修改可放贷资金
				if(UserUtils.getUser().getCompany().getPrimaryPerson()!=null&&"2".equals(UserUtils.getUser().getCompany().getPrimaryPerson())){
					//修改可放贷资金
					TCapital capital = tcapitaldao.getByOrganId(UserUtils.getUser().getCompany().getId());
					BigDecimal loanamount = new BigDecimal(capital.getLoanamount()) ; //放贷注入资本（可贷款金额）
					capital.setLoanamount(loanamount.subtract(new BigDecimal(rollbackMoney))+"");//减去这次回收的本金
					tcapitaldao.update(capital);
				
				}
				/*-----------------------------------*/
				rollbackMoney = 0;
				// 状态
				tmpRepayPlan.setStatus(Cons.RepayStatus.IN_PAYMENT);
				// 到账时间 还原到前一次还款状态的时间 
				/*if(oldRepayRecord != null){
					tmpRepayPlan.setAccountDate( DateUtils.formatDate(oldRepayRecord.getRepayDate()) );
				}*/
			}
			// 结清日期
			tmpRepayPlan.setOverDate("");
			// 再退利息
			if (rollbackMoney >= interestReal) {// 利息全退
				tmpRepayPlan.setInterestReal("0");
				rollbackMoney -= interestReal;

				// 付息状态
				tmpRepayPlan.setPayInterestStatus(Cons.RepayStatus.NO_PAID);
				// 付息时间
				tmpRepayPlan.setPayInterestDate(null);
			} else if (rollbackMoney > 0) {
				tmpRepayPlan.setInterestReal(String.valueOf(interestReal - rollbackMoney));
				rollbackMoney = 0;

				// 付息状态
				tmpRepayPlan.setPayInterestStatus(Cons.RepayStatus.IN_PAYMENT);
				// 付息时间 还原到之前还款的时间
				if(oldRepayRecord != null){
					tmpRepayPlan.setPayInterestDate( oldRepayRecord.getRepayDate() );
				}
			}

			// 是否逾期、开始还款金额
			double overdueDays = DateUtils.getDistanceOfTwoDate(DateUtils.parseDate(tmpRepayPlan.getEndDate()),
					oldRepayRecord.getRepayDate());
			if (overdueDays > 0) {//未逾期
				tmpRepayPlan.setYuQi(String.valueOf(overdueDays));
				tmpRepayPlan.setIsYuQi(Global.YES);
			}else{//未逾期
				tmpRepayPlan.setYuQi("0");
				tmpRepayPlan.setIsYuQi(Global.NO);
			}

			// 将数据保存回到数据库
			repayPlanService.save(tmpRepayPlan);
		}

		// 对回滚的还款记录金额清零
		repayRecord.setMoney(0.0);
		super.save(repayRecord);
		
		//回滚一下合同状态lzj-----否则全部结清后再修改合同状态仍是  已结清
		TLoanContract tLoanContract = new TLoanContract();
		tLoanContract.setId(repayRecord.getLoanContractId());
		tLoanContract.setStatus(Cons.LoanContractStatus.UN_CLEARED);
		loanContractService.updateStatus(tLoanContract);
	}
    //将原还款记录的isYuQi更新，防止更改还款计划后财务页面修改还款记录
	@Transactional(readOnly = false)
	public void updateNewPlan(RepayRecord rrd) {
		super.save(rrd);//更新
		
	}
	
	@Transactional(readOnly = false)
	public StringBuffer updateGetListByscanFlagData(String fileName) {	
		RepayRecord query = new RepayRecord();
		query.setScanFlag("0");
		query.setLoanContractId("NOT-ET");
		//query.setInformFilingType(informFilingType);
		List<RepayRecord> queryList = repayRecordDao.findListByScan(query);
		List<RepayRecord> successList = new ArrayList<RepayRecord>();
		StringBuffer data = new StringBuffer();
		if (queryList != null && queryList.size() > 0) {			
			for (RepayRecord temp : queryList) {	
				List<TLoanContract> tempList = loanContractService.getLoanListsByIds("'" + temp.getLoanContractId() + "'");
				if (tempList != null && tempList.size() > 0) {
					data.append(temp.toString(temp, tempList.get(0)));
					data.append("\r\n");
					successList.add(temp);
				}			
			}	
			
			for (RepayRecord temp : successList) {				
				temp.setScanFlag("1");	
				temp.setPushStatus("1");	
				temp.preUpdate();
				repayRecordDao.updateByscanFlag(temp);
			}
			
			
		}		
		return data;
	}
	
	
	@Transactional(readOnly = false)
	public void updateListByscanFlagData(List list) {
		RepayRecord response = new RepayRecord();
		for (int i = 0; i < list.size(); i++) {				
			String tmp = (String) list.get(i);
			String []msg = tmp.split("\\|");	
			if (msg[0] != null && !msg[0].trim().equals("")) {
				if("A".equals(msg[0])){
					response.setId(msg[2].trim());
					response.setScanFlag("0");	
					response.setPushStatus("0");	
					response.preUpdate();
					repayRecordDao.updateByscanFlag(response);
				} else {
					response.setId(msg[2].trim());
					response.setScanFlag("0");	
					response.setPushStatus("1");	
					response.preUpdate();
					repayRecordDao.updateByscanFlag(response);
				}					
			}				
		}				
	}
	
	
	
	
	@Transactional(readOnly = false)	
	public StringBuffer updateGetETListByscanFlagData(String fileName) {	
		RepayRecord query = new RepayRecord();
		query.setScanFlag("0");
		query.setLoanContractId("-ET");	
		List<RepayRecord> queryList = repayRecordDao.findList(query);
		List<RepayRecord> successList = new ArrayList<RepayRecord>();
		StringBuffer data = new StringBuffer();
		if (queryList != null && queryList.size() > 0) {			
			for (RepayRecord temp : queryList) {	
				List<TLoanContract> tempList = loanContractService.getLoanListsByIds("'" + temp.getLoanContractId() + "'");
				if (tempList != null && tempList.size() > 0) {
					data.append(temp.toETString(temp, tempList.get(0)));
					data.append("\r\n");
					successList.add(temp);
				}				
			}
			
			for (RepayRecord temp : successList) {				
				temp.setScanFlag("1");	
				temp.setPushStatus("1");	
				temp.preUpdate();
				repayRecordDao.updateByscanFlag(temp);
			}
		}		
		return data;
	}
	
	
	@Transactional(readOnly = false)
	public void updateETListByscanFlagData(List list) {
		RepayRecord response = new RepayRecord();
		for (int i = 0; i < list.size(); i++) {				
			String tmp = (String) list.get(i);
			String []msg = tmp.split("\\|");	
			if (msg[0] != null && !msg[0].trim().equals("")) {
				if("A".equals(msg[0])){
					response.setId(msg[2].trim());
					response.setScanFlag("0");	
					response.setPushStatus("0");	
					response.preUpdate();
					repayRecordDao.updateByscanFlag(response);
				} else {
					response.setId(msg[2].trim());
					response.setScanFlag("0");	
					response.setPushStatus("1");	
					response.preUpdate();
					repayRecordDao.updateByscanFlag(response);
				}					
			}				
		}				
	}

	@Transactional(readOnly = false)
	public void insert(RepayRecord repayRecord) {
		repayRecordDao.insert(repayRecord);
	}

	public Page<RepayRecordVo> findRepayRecordVoPage(Page<RepayRecordVo> page,RepayRecordVo repayRecordVo) {
		repayRecordVo.setPage(page);
		page.setList(repayRecordDao.findRepayRecordVoPage(repayRecordVo));
		return page;
	}

	public List<RepayRecordVo> findRepayRecordVoPage(RepayRecordVo repayRecordVo) {
		return repayRecordDao.findRepayRecordVoPage(repayRecordVo);
	}
}
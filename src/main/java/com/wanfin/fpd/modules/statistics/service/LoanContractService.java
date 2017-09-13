/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.statistics.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.service.CrudService;
import com.wanfin.fpd.common.utils.DateUtils;
import com.wanfin.fpd.common.utils.StringUtils;
import com.wanfin.fpd.modules.statistics.dao.LoanContractDao;
import com.wanfin.fpd.modules.statistics.entity.LoanContractVo;

/**
 * 业务信息Service，只作为统计用
 * @author srf
 * @version 2017-03-07
 */
@Service
@Transactional(readOnly = true)
public class LoanContractService extends CrudService<LoanContractDao, LoanContractVo> {

	public LoanContractVo get(String id) {
		return super.get(id);
	}
	
	public List<LoanContractVo> findList(LoanContractVo loanContract) {
		//return super.findList(loanContract);
		return loanDatail(loanContract);
	}
	
	public Page<LoanContractVo> findPage(Page<LoanContractVo> page, LoanContractVo loanContract) {
		//return super.findPage(page, loanContract);
		loanContract.setPage(page);
		page.setList( loanDatail(loanContract) );
		return page;
	}
	
	//贷款明细信息查询
	public List<LoanContractVo> loanDatail(LoanContractVo loanContract) {
		if(loanContract == null){
			return null;
		}
		if(loanContract.getBeginLastPayDate() == null){
			loanContract.setBeginLastPayDate(new Date());
		}
		//System.out.println("开始日期"+loanContract.getBeginLastPayDate());
		if(loanContract.getEndLastPayDate() == null){
			/*String year =new DateUtils().getYear();
			//new Date().getManth()+1    结果为 4+1   不是04+1   会出现问题
			int manth =Integer.parseInt(new DateUtils().getMonth())+1;
			String date = year+"-"+manth;
			//System.out.println(date);
			try {
				loanContract.setEndLastPayDate(new SimpleDateFormat("yyyy-MM").parse(date));
			} catch (ParseException e) {
				
				e.printStackTrace();
			}*/
			loanContract.setEndLastPayDate(new Date());
		}
		//System.out.println("结束日期"+loanContract.getEndLastPayDate());
		List<LoanContractVo> listOr = dao.findLoanDetailList(loanContract);
		return dealLoanDatail(listOr);
	}
	
	/**
	 * @description	贷款明细处理
	 * @param listOr		List<LoanContractVo> loanDatail(LoanContractVo loanContract) 方法返回的结果
	 * @return
	 */
	public List<LoanContractVo> dealLoanDatail(List<LoanContractVo> listOr) {
		if(listOr == null || listOr.size()<1){
			return null;
		}else{
			BigDecimal zero = new BigDecimal(0);
			List<LoanContractVo> listRe = new ArrayList<LoanContractVo>();
			int i=1;
			
			LoanContractVo totalInfo = new LoanContractVo();//合计的内容
			BigDecimal loanAmount = new BigDecimal(0);//贷款金额
			for(LoanContractVo tmp:listOr){
				//System.out.println("sql贷款明细处理实体类"+tmp);
					//    未结清   									已结清						已逾期		  		
				if(tmp.getStatus().equals("6") || tmp.getStatus().equals("7") || tmp.getStatus().equals("8") 
							//	展期签订											展期审批								已展期
						/*|| tmp.getStatus().equals("et_to_sign") */ || tmp.getStatus().equals("et_to_approve")/*  || tmp.getStatus().equals("extended")*/
						//      展期申请													展期审批不通过
						|| tmp.getStatus().equals("ba_zqsq") || tmp.getStatus().equals("extend_end")){
					tmp.setSerial(String.valueOf(i++));//导出xls用
					
					//利率处理
					/*BigDecimal loanRate = new BigDecimal(tmp.getLoanRate());
					if("年".equals(tmp.getLoanRateType())){
						loanRate = loanRate.divide(new BigDecimal(12), 2, BigDecimal.ROUND_HALF_UP);
					}else if("月".equals(tmp.getLoanRateType())){
						
					}else if("日".equals(tmp.getLoanRateType())){
						loanRate = loanRate.multiply(new BigDecimal(30));
					}
					tmp.setLoanRate( loanRate.toString() + "%" );*/
					
					/**
					 * update  月利率  改为 年利率
					 */
					BigDecimal loanRate = new BigDecimal(tmp.getLoanRate());
					if("年".equals(tmp.getLoanRateType())){
						
					}else if("月".equals(tmp.getLoanRateType())){
						loanRate = loanRate.multiply(new BigDecimal(12));
					}else if("日".equals(tmp.getLoanRateType())){
						loanRate = loanRate.multiply(new BigDecimal(360));
					}
					tmp.setLoanRate( loanRate.toString() + "%" );
					
					//当期未还利息金额							//当期应还利息金额	-	当期已还利息金额
					tmp.setCurrentNeedInterestMoney(tmp.getCurrentInterestMoney().subtract(tmp.getCurrentBackInterestMoney()));
					//当期未还本金金额							当期应还本金金额    -	当期已还本金金额
					tmp.setCurrentNeedPrincipalMoney(tmp.getCurrentPrincipalMoney().subtract(tmp.getCurrentBackPrincipalMoney()));
					//当期应收本息								当期应还利息金额  +  当期应还本金金额 
					tmp.setCurrentNeedIntePriMoney(tmp.getCurrentInterestMoney().add(tmp.getCurrentPrincipalMoney()));
									
					
					//未还利息金额，总的						本次总贷款利息金额    -   已还利息金额
					tmp.setNeedInterestMoney(tmp.getBackInterestTotal().subtract(tmp.getBackInterestMoney()));
					//未还本金金额，总的    jsp中的---本月期初贷款余额()
														//总的本金金额   -  已还本金金额  /*+总的利息金额-已还利息金额*/
					
					tmp.setNeedPrincipalMoney(tmp.getBackPrincipaTotal().subtract(tmp.getBackPrincipalMoney())/*.add(tmp.getBackInterestTotal()).subtract(tmp.getBackInterestMoney()) */);
					//本月末   未还贷款总数       					未还利息金额		/未还本金金额
					//tmp.setPreiodEndNeedRepay(	(tmp.getNeedInterestMoney().add(tmp.getNeedPrincipalMoney())).setScale(2,BigDecimal.ROUND_HALF_UP));
					/**
					 * 本月末   未还贷款总数         =   本月初贷款余额   - 本月提前还本金  - 本月应收本金
					 * update lxh  2017-4-24
					 */
					if (tmp.getAdviceBackPrincipalMoney() ==null) {
						tmp.setAdviceBackPrincipalMoney(new BigDecimal(0));
					}
					tmp.setPreiodEndNeedRepay(	(tmp.getNeedPrincipalMoney().subtract(tmp.getAdviceBackPrincipalMoney()).subtract(tmp.getCurrentPrincipalMoney())).setScale(2,BigDecimal.ROUND_HALF_UP));
					//到目前已收收入（利息+费用合计）		已还利息金额   +  已还本金金额
					tmp.setCurrentRepay(tmp.getBackInterestMoney().add(tmp.getBackPrincipalMoney()));
					//本次贷款放款金额 (缺少贷款放款金额)
					if(tmp.getLendAmount() != null && tmp.getLendAmount().doubleValue()>0){
						//BigDecimal hundred = new BigDecimal(100);
						//当期服务费
						if(StringUtils.isNotBlank(tmp.getServerFee())){
							try{
								BigDecimal rate = new BigDecimal(tmp.getServerFee());
								// 服务费  =  贷款金额  * 服务费率/100 
								tmp.setFeeService(tmp.getLendAmount().multiply(rate).divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP));
							}catch(Exception e){
								e.printStackTrace();
							}
						}else{
							tmp.setFeeService(zero);
						}
						//当期管理费
						if(StringUtils.isNotBlank(tmp.getMangeFee())){
							try{
								
								// 管理费  =  贷款金额  * 管理费率/100  
								tmp.setFeeManage(tmp.getLendAmount().multiply(new BigDecimal(tmp.getMangeFee())).divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP));
							}catch(Exception e){
								e.printStackTrace();
							}
						}else{
							tmp.setFeeManage(zero);
						}
						
					}else{
						tmp.setFeeService(zero);
						tmp.setFeeManage(zero);
					}
					//是否结清
					if("7".equals(tmp.getStatus())){
						tmp.setIsSquare("1");
						
						if(tmp.getPayPrincipalDate() != null){
							String lastPayDate = DateUtils.formatDate(tmp.getPayPrincipalDate(), "yyyy-MM-dd");
							//结清状态
							if(StringUtils.isNotBlank(tmp.getLastRepayDate()) || StringUtils.isNotBlank(lastPayDate)){
								try {
									int compare = DateUtils.compareTo(tmp.getLastRepayDate(), lastPayDate);
									if(compare == 0){//正常结清/提前结清/逾期结清
										tmp.setSquareType("正常结清");
									}else if(compare == -1){
										tmp.setSquareType("提前结清");
									}else if(compare == 1){
										tmp.setSquareType("逾期结清");
									}
								} catch (ParseException e) {
									e.printStackTrace();
								}
							}
						}
					}else{
						tmp.setIsSquare("0");
					}
	
					//贷款金额
					loanAmount = loanAmount.add( new BigDecimal(tmp.getLoanAmount()) );
					totalInfo.setNeedPrincipalMoney( totalInfo.getNeedPrincipalMoney().add(tmp.getNeedPrincipalMoney()));//13
					totalInfo.setCurrentBackPrincipalMoney( totalInfo.getCurrentBackPrincipalMoney().add(tmp.getCurrentBackPrincipalMoney()) );//14
					totalInfo.setCurrentPrincipalMoney( totalInfo.getCurrentPrincipalMoney().add(tmp.getCurrentPrincipalMoney()) );//15
					totalInfo.setCurrentInterestMoney( totalInfo.getCurrentInterestMoney().add(tmp.getCurrentInterestMoney()) );//16
					totalInfo.setFeeService( totalInfo.getFeeService().add(tmp.getFeeService()) );//17
					totalInfo.setFeeManage( totalInfo.getFeeManage().add(tmp.getFeeManage()) );//18
					//19
					//20
					totalInfo.setCurrentNeedIntePriMoney( totalInfo.getCurrentNeedIntePriMoney().add(tmp.getCurrentNeedIntePriMoney()) );//21
					totalInfo.setPreiodEndNeedRepay( totalInfo.getPreiodEndNeedRepay().add(tmp.getPreiodEndNeedRepay()) );//22
					totalInfo.setBackPrincipalMoney( totalInfo.getBackPrincipalMoney().add(tmp.getBackPrincipalMoney()) );//23
					totalInfo.setCurrentRepay( totalInfo.getCurrentRepay().add(tmp.getCurrentRepay()) );//24
					//判断是否已逾期
					if(tmp.getStatus().equals("8")){

						if(tmp.getLatePenalty() == null || tmp.getLatePenalty().equals("") ){
								tmp.setLatePenalty("条数据的罚息为空");
							}else{
								 //  未还罚息	  =逾期的本金*罚息率/360  * 逾期天数=()		
								/*tmp.setLatePenalty((tmp.getCurrentNeedIntePriMoney()
										.multiply(	new BigDecimal(tmp.getLatePenalty()).divide(new BigDecimal(360),2, BigDecimal.ROUND_HALF_UP))
										.multiply(new BigDecimal(30))).setScale(2,BigDecimal.ROUND_HALF_UP).toString());*/
								//  期间总罚息  =  未还罚息 +  期间已还罚息
								if(tmp.getRepInterestPenalties() == null){
									tmp.setRepInterestPenalties(new BigDecimal(0));
								}
								/*tmp.setLatePenalty(new BigDecimal(tmp.getLatePenalty()).add(tmp.getRepInterestPenalties()).setScale(2,BigDecimal.ROUND_HALF_UP).toString());*/
								//已还的罚息
								tmp.setLatePenalty(tmp.getRepInterestPenalties().setScale(2,BigDecimal.ROUND_HALF_UP).toString());
							}
						if(tmp.getLateFee() ==null ||  tmp.getLateFee().equals("")){
							tmp.setLateFee("条数据的滞纳金为空");
						}else{
								//	违约金   =逾期的本金*滞纳金率/360  * 逾期天数	
							/*tmp.setLateFee(	(tmp.getCurrentNeedIntePriMoney()
									.multiply(new BigDecimal(tmp.getLateFee()).divide(new BigDecimal(360),2, BigDecimal.ROUND_HALF_UP))
									.multiply(new BigDecimal(30))).setScale(2,BigDecimal.ROUND_HALF_UP).toString() );*/
							
							//  期间总违约金  =  未还违约金 +  期间已还违约金
							if(tmp.getRepPunishAmount() == null){
								tmp.setRepPunishAmount(new BigDecimal(0));
							}
							/*tmp.setLateFee(new BigDecimal(tmp.getLateFee()).add(tmp.getRepPunishAmount()).setScale(2,BigDecimal.ROUND_HALF_UP).toString());*/
							//已还的 违约金
							tmp.setLateFee(tmp.getRepPunishAmount().setScale(2,BigDecimal.ROUND_HALF_UP).toString());
						}

					}else {
						tmp.setLatePenalty("0");
						tmp.setLateFee("0");
					}
					listRe.add(tmp);
				}
					
			}
			totalInfo.setSerial("合计:");
			totalInfo.setLoanAmount(loanAmount.toString());
			listRe.add(totalInfo);//合计内容
			
			return listRe;
		}
	}
	
//	@Transactional(readOnly = false)
//	public void save(LoanContractVo loanContract) {
//		super.save(loanContract);
//	}
	
//	@Transactional(readOnly = false)
//	public void delete(LoanContractVo loanContract) {
//		super.delete(loanContract);
//	}
	
}
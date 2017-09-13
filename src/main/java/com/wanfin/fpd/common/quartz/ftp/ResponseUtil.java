/**
 * Project Name:dataExchange
 * File Name:ResponseUtil.java
 * Package Name:cn.jrjzx.dataExchange.common
 * Date:2016年6月28日下午3:15:08
 * Copyright (c) 2016, hengwei.xiao@jrjzx.cn All Rights Reserved.
 *
*/

package com.wanfin.fpd.common.quartz.ftp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.wanfin.fpd.common.utils.sftp.DownLoadFileFromFtp;
import com.wanfin.fpd.modules.catipal.entity.TCaiwu;
import com.wanfin.fpd.modules.catipal.service.TCaiwuService;
import com.wanfin.fpd.modules.company.entity.TCompany;
import com.wanfin.fpd.modules.company.service.TCompanyService;
import com.wanfin.fpd.modules.contract.entity.TLoanContract;
import com.wanfin.fpd.modules.contract.service.TLoanContractService;
import com.wanfin.fpd.modules.employee.entity.TEmployee;
import com.wanfin.fpd.modules.employee.service.TEmployeeService;
import com.wanfin.fpd.modules.guarantee.service.TGuaranteeContractService;
import com.wanfin.fpd.modules.jrj.entity.JrjBalanceSheep;
import com.wanfin.fpd.modules.jrj.entity.JrjBusinessStatus;
import com.wanfin.fpd.modules.jrj.entity.JrjProceeds;
import com.wanfin.fpd.modules.jrj.entity.JrjRiskIndicator;
import com.wanfin.fpd.modules.jrj.service.JrjBalanceSheepService;
import com.wanfin.fpd.modules.jrj.service.JrjBusinessStatusService;
import com.wanfin.fpd.modules.jrj.service.JrjProceedsService;
import com.wanfin.fpd.modules.jrj.service.JrjRiskIndicatorService;
import com.wanfin.fpd.modules.receivables.entity.RepayRecord;
import com.wanfin.fpd.modules.receivables.service.RepayRecordService;
import com.wanfin.fpd.modules.repayplan.entity.TRepayPlan;
import com.wanfin.fpd.modules.repayplan.service.TRepayPlanService;
import com.wanfin.fpd.modules.mortgage.entity.MortgageContract;
import com.wanfin.fpd.modules.mortgage.service.MortgageContractService;
import com.wanfin.fpd.modules.pledge.entity.PledgeContract;
import com.wanfin.fpd.modules.pledge.service.PledgeContractService;
import com.wanfin.fpd.modules.receivables.entity.RepayRecord;
import com.wanfin.fpd.modules.receivables.service.RepayRecordService;
import com.wanfin.fpd.modules.repayplan.entity.TRepayPlan;
import com.wanfin.fpd.modules.repayplan.service.TRepayPlanService;




/**
 * ClassName:ResponseUtil <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2016年6月28日 下午3:15:08 <br/>
 * @author   xiaohengwei
 * @version  
 * @since    JDK 1.7
 * @see 	 
 */
public class ResponseUtil {
	
	private static WebApplicationContext currentWebApplicationContext = ContextLoader.getCurrentWebApplicationContext();
	private static TCaiwuService caiwuService = (TCaiwuService)currentWebApplicationContext.getBean("tCaiwuService");
	private static TRepayPlanService tRepayPlanService = (TRepayPlanService)currentWebApplicationContext.getBean("tRepayPlanService");
	private static TGuaranteeContractService tGuaranteeContractService = (TGuaranteeContractService)currentWebApplicationContext.getBean("tGuaranteeContractService");
	
	private static RepayRecordService repayRecordService = (RepayRecordService)currentWebApplicationContext.getBean("repayRecordService");

	
	private static TLoanContractService loanService = (TLoanContractService)currentWebApplicationContext.getBean("tLoanContractService");
	private static TCompanyService companyService = (TCompanyService)currentWebApplicationContext.getBean("tCompanyService");
	private static TEmployeeService employeeService = (TEmployeeService)currentWebApplicationContext.getBean("tEmployeeService");
	private static MortgageContractService mortgageContractService = (MortgageContractService)currentWebApplicationContext.getBean("mortgageContractService");
	private static PledgeContractService pledgeContractService = (PledgeContractService)currentWebApplicationContext.getBean("pledgeContractService");
	private static JrjBalanceSheepService sheepService = (JrjBalanceSheepService)currentWebApplicationContext.getBean("jrjBalanceSheepService");
	private static JrjBusinessStatusService statusService = (JrjBusinessStatusService)currentWebApplicationContext.getBean("jrjBusinessStatusService");
	private static JrjProceedsService proceedsService = (JrjProceedsService)currentWebApplicationContext.getBean("jrjProceedsService");
	private static JrjRiskIndicatorService indicatorService = (JrjRiskIndicatorService)currentWebApplicationContext.getBean("jrjRiskIndicatorService");
	
	/**
	 * 
	 * errorResponse:解析
	 * @since JDK 1.7
	 */
	public static Map<String, List<Object>> resultResponse(DownLoadFileFromFtp loadFileFromFtp, String remoteDir, String ldaFileName, List list){
		
		Map<String, List<Object>> tempMap = new HashMap<String, List<Object>>();
		
		if(list==null){
			return tempMap;
		}else{				
				//放贷业务合同文件
				if (ldaFileName.toUpperCase().contains("CONXD")) { //小贷业务合同文件
					/*TLoanContract queryPersonSheep = new TLoanContract();
					queryPersonSheep.setScanFlag("1");
					queryPersonSheep.setPushStatus("0");
					//查询推送状态为0的记录
					List<TLoanContract> loanList = loanService.findListByScanFlagAndPushStatus(queryPersonSheep);
					
					//查询状态为1的记录
					queryPersonSheep.setPushStatus("1");
					List<TLoanContract> loanList1 = loanService.findListByScanFlagAndPushStatus(queryPersonSheep);
					
					List<String> strList = new ArrayList();
					//组装返回的ID
					for(int i=0;i<list.size();i++){	
						String tmp = (String) list.get(i);
						String []msg = tmp.split("\\|");
						if(!"".equals(msg[1])){
							strList.add(msg[1]);
						}						
					}						
					loanService.updateReceiptData(strList,loanList,loanList1);*/
					
					for(int i=0;i<list.size();i++){	
						String tmp = (String) list.get(i);
						String []msg = tmp.split("\\|");
						if(!"".equals(msg[2])){
							if("A".equals(msg[0])){
								TLoanContract loan = loanService.get(msg[2]);
								if(loan != null ){
									loan.setScanFlag("0");
									loan.setPushStatus("0");
									loanService.updateByPushStatus(loan);
								}								
							}else{
								TLoanContract loan = loanService.get(msg[2]);
								if(loan != null ){
									loan.setScanFlag("0");
									loan.setPushStatus("1");
									loanService.updateByPushStatus(loan);
								}
							}						
						}						
					}	
					
					
				} else if(ldaFileName.toUpperCase().contains("CONDB")){ //担保合同文件
					
					/*TLoanContract queryPersonSheep = new TLoanContract();
					queryPersonSheep.setScanFlag("1");
					queryPersonSheep.setPushStatus("0");
					//查询推送状态为0的记录
					List<TLoanContract> loanList = loanService.findListDbByScanFlagAndPushStatus(queryPersonSheep);
					
					//查询状态为1的记录
					queryPersonSheep.setPushStatus("1");
					List<TLoanContract> loanList1 = loanService.findListDbByScanFlagAndPushStatus(queryPersonSheep);
					
					List<String> strList = new ArrayList();
					//组装返回的ID
					for(int i=0;i<list.size();i++){	
						String tmp = (String) list.get(i);
						String []msg = tmp.split("\\|");
						if(!"".equals(msg[1])){
							strList.add(msg[1]);
						}						
					}						
					loanService.updateReceiptDbData(strList,loanList,loanList1);*/
					
					for(int i=0;i<list.size();i++){	
						String tmp = (String) list.get(i);
						String []msg = tmp.split("\\|");
						if(!"".equals(msg[2])){
							if("A".equals(msg[0])){
								TLoanContract loan = loanService.get(msg[2]);
								if(loan != null ){
									loan.setScanFlag("0");
									loan.setPushStatus("0");
									loanService.updateByPushStatus(loan);
								}								
							}else{
								TLoanContract loan = loanService.get(msg[2]);
								if(loan != null ){
									loan.setScanFlag("0");
									loan.setPushStatus("1");
									loanService.updateByPushStatus(loan);
								}
							}						
						}						
					}	
					
				} else if (ldaFileName.toUpperCase().contains("PLA")) {
					
					
				} else if (ldaFileName.toUpperCase().contains("PLE")) {  //抵质押文件回执
					
					//组装返回的ID						
					for(int i=0;i<list.size();i++){	
						String tmp = (String) list.get(i);
						String []msg = tmp.split("\\|");
						if(!"".equals(msg[2])){
							if("A".equals(msg[0])){
								MortgageContract mor = mortgageContractService.get(msg[2]);
								if(mor != null){
									mor.setPushStatus("0");
									mor.setScanFlag("0");
									mortgageContractService.updateByPushStatus(mor);
								}
							}else{
								MortgageContract mor = mortgageContractService.get(msg[2]);
								if(mor != null){
									mor.setPushStatus("0");
									mor.setScanFlag("1");
									mortgageContractService.updateByPushStatus(mor);
								}
							}						
						}						
					}
					
					for(int i=0;i<list.size();i++){	
						String tmp = (String) list.get(i);
						String []msg = tmp.split("\\|");
						if(!"".equals(msg[2])){
							if("A".equals(msg[0])){
								PledgeContract ple = pledgeContractService.get(msg[2]);
								if(ple != null){
									ple.setPushStatus("0");
									ple.setScanFlag("0");
									pledgeContractService.updateByPushStatus(ple);
								}
							}else{
								PledgeContract ple = pledgeContractService.get(msg[2]);
								if(ple != null){
									ple.setPushStatus("0");
									ple.setScanFlag("1");
									pledgeContractService.updateByPushStatus(ple);
								}
							}						
						}						
					}
					
					
					/*******  抵押   ******/
					/*MortgageContract queryMor = new MortgageContract();
					queryMor.setScanFlag("1");
					queryMor.setPushStatus("0");
					//查询推送状态为0的记录
					List<MortgageContract> morList = mortgageContractService.findListByScanFlagByPushStatus(queryMor);
					
					queryMor.setPushStatus("1");
					List<MortgageContract> morList1 = mortgageContractService.findListByScanFlagByPushStatus(queryMor);					
									
					mortgageContractService.updateReceiptDbData(strList, morList, morList1);
					*//*******  抵押  end******//*				
					
					
					*//*******  质押   ******//*
					PledgeContract queryPle = new PledgeContract();		
					queryPle.setScanFlag("1");
					queryPle.setPushStatus("0");
					//查询推送状态为0的记录
					List<PledgeContract> pleList = pledgeContractService.findListByScanFlagByPushStatus(queryPle);
					
					queryPle.setPushStatus("1");
					//查询推送状态为0的记录
					List<PledgeContract>  pleList1 = pledgeContractService.findListByScanFlagByPushStatus(queryPle);					
					pledgeContractService.updateReceiptDbData(strList, pleList, pleList1);
					*//*******  质押  end******/				
				} else if (ldaFileName.toUpperCase().contains("REP")) {				
				
				} else if (ldaFileName.toUpperCase().contains("EXT")) {
				
				} else if (ldaFileName.toUpperCase().contains("ERP")) {
					
				} else if (ldaFileName.toUpperCase().contains("ZCFZ")) {
					
				}  else if (ldaFileName.toUpperCase().contains("XDLR")) {
					
				}else if(ldaFileName.toUpperCase().contains("BUS")){ //企业客户文件
					/*TCompany queryPersonSheep = new TCompany();		
					queryPersonSheep.setScanFlag("1");
					queryPersonSheep.setPushStatus("0");
					//查询  推送状态为0的记录
					List<TCompany> personList = companyService.findListByScanTimeAndPushStatus(queryPersonSheep);
					
					//查询  推送状态为1的记录
 					List<TCompany> personList1 = companyService.findListByScanTimeAndPushStatus(queryPersonSheep);
					List<String> strList = new ArrayList();					
					companyService.updateReceiptData(strList,personList,personList1);*/			
					
					//组装返回的ID
					for(int i=0;i<list.size();i++){	
						String tmp = (String) list.get(i);
						String []msg = tmp.split("\\|");
						if(!"".equals(msg[2])){
							String[] str = msg[2].split(",");
							if("A".equals(msg[0])){
								TCompany company = companyService.get(str[1]);	
								if(company != null){
									company.setScanFlag("0");
									company.setPushStatus("0");
									companyService.updateByPushStatus(company);
								}								
							}else{
								TCompany company = companyService.get(str[1]);	
								if(company != null){
									company.setScanFlag("0");
									company.setScanFlag("1");
									companyService.updateByPushStatus(company);
								}
							}
							
						}						
					}	
					
				}else if(ldaFileName.toUpperCase().contains("PER")){					
					/*TEmployee queryPersonSheep = new TEmployee();		
					queryPersonSheep.setScanFlag("1");
					queryPersonSheep.setPushStatus("0");					
					//查询推送状态为1的记录
					List<TEmployee> personList = employeeService.findListByScanFlagAndPushStatus(queryPersonSheep);
					
					//查询推送状态为1
					queryPersonSheep.setPushStatus("1");
					List<TEmployee> personList1 = employeeService.findListByScanFlagAndPushStatus(queryPersonSheep);
					
					List<String> strList = new ArrayList();
					//组装返回的ID
					for(int i=0;i<list.size();i++){	
						String tmp = (String) list.get(i);
						String []msg = tmp.split("\\|");
						if(!"".equals(msg[1])){
							String[] str = msg[1].split(",");
							strList.add(str[1]);
						}						
					}	
					employeeService.updateReceiptData(strList,personList,personList1);*/
					
					for(int i=0;i<list.size();i++){	
						String tmp = (String) list.get(i);
						String []msg = tmp.split("\\|");
						if(!"".equals(msg[2])){
							String[] str = msg[2].split(",");							
							if("A".equals(msg[0])){						
								TEmployee employee = employeeService.get(str[1]);
								if(employee != null){
									employee.setScanFlag("0");
									employee.setPushStatus("0");
									employeeService.updateByPushStatus(employee);
								}	
								
							}else{
								TEmployee employee = employeeService.get(str[1]);
								if(employee != null){
									employee.setScanFlag("0");
									employee.setPushStatus("1");
									employeeService.updateByPushStatus(employee);
								}	
							}
						}						
					}	
					
					
				}else if(ldaFileName.toUpperCase().contains("BAL")){
					
					for(int i=0;i<list.size();i++){	
						//担保资产负债
						String tmp = (String) list.get(i);
						String []msg = tmp.split("\\|");
						JrjBalanceSheep response = sheepService.get(msg[1]);
						if(response != null){
							response.setScanFlag("0");							
							sheepService.save(response);
						}
				   }
				}else if(ldaFileName.toUpperCase().contains("BUIS")){
					
					for(int i=0;i<list.size();i++){	
						//担保业务状况
						String tmp = (String) list.get(i);
						String []msg = tmp.split("\\|");
						JrjBusinessStatus response = statusService.get(msg[1]);
						if(response != null){
							response.setScanFlag("0");							
							statusService.save(response);
						}
					}
				}else if(ldaFileName.toUpperCase().contains("PROC")){
					for(int i=0;i<list.size();i++){	
						//担保收益情况
						String tmp = (String) list.get(i);
						String []msg = tmp.split("\\|");
						JrjProceeds response = proceedsService.get(msg[1]);
						if(response != null){
							response.setScanFlag("0");							
							proceedsService.save(response);
						}
					}
				}else if(ldaFileName.toUpperCase().contains("RIS")){
					
					for(int i=0;i<list.size();i++){	
					//担保收益情况
					String tmp = (String) list.get(i);
					String []msg = tmp.split("\\|");
					JrjRiskIndicator response = indicatorService.get(msg[1]);
					if(response != null){
						response.setScanFlag("0");							
						indicatorService.save(response);
					}
				}				
			}			
		}	
		
		/*
		 * 处理回执文件
		 */
		if (ldaFileName.toUpperCase().contains("PLA")) {
			tRepayPlanService.updateListByscanFlagData(list);	
		} else if (ldaFileName.toUpperCase().contains("ENS")) {
			tGuaranteeContractService.updateListByscanFlagData(list);
		} else if (ldaFileName.toUpperCase().contains("REP")) {				
			repayRecordService.updateListByscanFlagData(list);				
		} else if (ldaFileName.toUpperCase().contains("EXT")) {
			tRepayPlanService.updateETListByscanFlagData(list);					
		} else if (ldaFileName.toUpperCase().contains("ERP")) {
			repayRecordService.updateETListByscanFlagData(list);					
		} else if (ldaFileName.toUpperCase().contains("ZCFZ")) {			
			caiwuService.updateListByscanFlagData(list);				
		} else if (ldaFileName.toUpperCase().contains("XDLR")) {
			caiwuService.updateListByscanFlagData(list);
		}			
		return tempMap;
	}
	
	 
	

	
	

	

	

	
	
	
	
	
	

	

	
	
}


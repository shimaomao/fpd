package com.wanfin.fpd.modules.excelUpload.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wanfin.fpd.common.utils.IdGen;
import com.wanfin.fpd.modules.company.entity.TCompany;
import com.wanfin.fpd.modules.company.service.TCompanyService;
import com.wanfin.fpd.modules.contract.entity.TLoanContract;
import com.wanfin.fpd.modules.contract.service.TLoanContractService;
import com.wanfin.fpd.modules.credit.service.CustomerCreditService;
import com.wanfin.fpd.modules.employee.entity.TEmployee;
import com.wanfin.fpd.modules.employee.service.TEmployeeService;
import com.wanfin.fpd.modules.excelUpload.entity.CompanyAndContract;
import com.wanfin.fpd.modules.excelUpload.entity.TEmployeeAndContract;
import com.wanfin.fpd.modules.lending.entity.TLending;
import com.wanfin.fpd.modules.lending.service.TLendingService;
import com.wanfin.fpd.modules.sys.dao.UserDao;
import com.wanfin.fpd.modules.sys.entity.User;
import com.wanfin.fpd.modules.sys.service.SystemService;

/**
 * 导入数据业务处理类:插入个人数据和企业数据
 * @author lzj
 * @version 2017-03-27
 * 
 * */
@Service
public class ReadExcelService {
	@Autowired
	SystemService systemService;//用户信息
	//注入个人客户的service类
	@Autowired
	private TEmployeeService tEmployeeService;
	//注入业务信息service类
	@Autowired
	private TLoanContractService tLoanService;
	//注入企业客户信息service类
	@Autowired
	private TCompanyService CompanyService;
	//注入授信service类
	@Autowired
	private CustomerCreditService credit;
	//注入还款记录service类
	@Autowired
	private TLendingService tLendingService;
	@Autowired
	private UserDao userDao;
//	//注入还款计划service类
//	@Autowired
//	private TRepayPlanService repay;
//	//注入放款记录service类
//	@Autowired
//	private TLendingService tlend;
//	//注入资本信息service类
//	@Autowired
//	private TCapitalService tcapital;
	
	
	//接收excel已解析数据，插入个人客户以及业务信息
	@Transactional(readOnly = false)
	public List<Map> excelByTEmployService(List<TEmployeeAndContract> list){//TODO
		//问题数据收集集合
		List<Map> listWentiZ=new ArrayList<Map>();

		//-------------------验证不够详细		
		for(TEmployeeAndContract temployeeandcontract:list){
			
			
			if(temployeeandcontract !=null){
				
						//问题  {姓名:{"姓名不为空",....},姓名:{"姓名不为空",....},...  }    list{(吴),}
						Map<String,List> map=new HashMap<String,List>();  //姓名
						List<String> listWentiG=new ArrayList<String>();  //问题
						map.put(temployeeandcontract.getCustomerName(), listWentiG);
						listWentiZ.add(map);
			
						if (tEmployeeService.judgeForTemployee(temployeeandcontract,listWentiG)    	//个人客户   非空验证
								&& tLoanService.judgeByContractTE(temployeeandcontract,listWentiG)){   //业务办理   非空验证
								//验证成功  类转换
							TEmployee emp = new TEmployee();
							TLoanContract loan = new TLoanContract();
							TLending tl = new TLending();
							//个人信息
							emp = temployeeandcontract.otTEmployee(emp);
							//个人业务信息
							loan = temployeeandcontract.otTLoanContract(loan);
							//还款计划
							tl=temployeeandcontract.toTLending(tl);
						
								//根据身份证号码查询出个人客户    (是否存在)---------------------------TEmployee 判断是否存在不改变   ------
								TEmployee temployee = tEmployeeService
										.byTEmployeeAndContract(temployeeandcontract);   				
								//根据业务编号，查询出客户业务信息
								TLoanContract tLoanContract = tLoanService
										.contractByNumber(temployeeandcontract);
								
								
								//获取客户经理信息
								User user = userDao.getByName(temployeeandcontract.getCustomerManager());
								
								if(user == null){
									listWentiG.add("---结果:该数据对应的客户经理有误，请检查数据业务编号和业务信息的准确性，完善并重新上传");
									continue;
								}
								
								
								//  更新    创建   时间			
								Date nowDate = new Date();
								/*temployeeandcontract.setCreateBy(user);
								temployeeandcontract.setCreateDate(nowDate);
								temployeeandcontract.setUpdateBy(user);
								temployeeandcontract.setUpdateDate(nowDate);*/
			 				    emp.setCreateBy(user);
								emp.setCreateDate(nowDate);
								emp.setUpdateBy(user);
								emp.setUpdateDate(nowDate);
								
								loan.setCreateBy(user);
								loan.setCreateDate(nowDate);
								loan.setUpdateBy(user);
								loan.setUpdateDate(nowDate);
								
								if(temployee != null){//设置客户ID
									emp.setId(temployee.getId());
								}/*else{  不能设置 id  要不然不添加
									emp.setId(IdGen.uuid());
								}*/
								
								if(tLoanContract != null){//设置业务ID
									loan.setId(tLoanContract.getId());
									tl.getContract().setId(tLoanContract.getId());
								}/*else{
									loan.setId(IdGen.uuid());
								}*/
								
								try {
									if(temployee != null && tLoanContract != null){
																							
										//更新客户信息---------------------TEmployee----------------------------------
										tEmployeeService.updateByExcel(emp);
																	
										//更新业务信息-----------------------------------------------------------------
										tLoanService.updateByExcel(loan);
										//生成还款计划--------------------------------是否 用原来的 还款计划
			/*						---*/tLoanService.repoyForTemploy(loan);
			
										//生成或更新  放款记录
										//查询     这条数据是否存在
										TLending tllLending =tLendingService.getTLending(tl);
										
										if(tllLending !=null &&  tllLending.getId() !=null ){
											tl.setId(tLendingService.getTLending(tl).getId());
										}
										tLendingService.save(tl);
										
										//更新授信
										/*credit.addCustomerCredit(temployeeandcontract.getId(),temployeeandcontract.getProductId()
												,new BigDecimal(temployeeandcontract.getLoanAmount()),null
												,temployeeandcontract.getPayPrincipalDate(),null
												,null);	*/
												/**
												 * @Description 客户授信
												 * @param customerId	客户id
												 * @param credit	授信额度	
												 * @param score		评分---------------null
												 * @param overDate	有效截止时间
												 * @param relId		此次授信关联id-------null
												 * @param creditWay	授信方式     ----------null
												 * @author zzm			------
												 * @date 2016-7-13 下午6:34:10  
												 * 
												 * addCustomerCredit(String customerId, String productId, BigDecimal credit, Double score, 
												 * 
												 * Date overDate, String relId, String creditWay)
												 */  
										//	有问题  如果以前没有授信过   												客户id					产品id				  授信额度						评分	
										/*credit.addCustomerCredit(tEmployeeService.selectId(emp).getId(), loan.getProductId(), new BigDecimal(loan.getLoanAmount()),null
												//     有效截止时间	       授信关联id   授信方式
												,loan.getPayPrincipalDate(),null,null);*/
										listWentiG.add("---结果:该客户数据和业务数据更新成功");   
									}else if(temployee != null && tLoanContract == null){
			
									//更新客户信息---------------------TEmployee--------------------------
									tEmployeeService.updateByExcel(emp);
									//插入业务信息------------------------------------------------
									tLoanService.insertByExcel(loan);
																
									//生成还款计划
			/*						---*/tLoanService.repoyForTemploy(loan);
									
									//生成或更新  放款记录
									tl.getContract().setId(loan.getId());
									//查询     这条数据是否存在
									TLending tllLending =tLendingService.getTLending(tl);
									
									if(tllLending !=null &&  tllLending.getId() !=null ){
										tl.setId(tLendingService.getTLending(tl).getId());
									}
									tLendingService.save(tl);
										
										//更新授信
										/*credit.addCustomerCredit(temployeeandcontract.getId(),temployeeandcontract.getProductId()
												,new BigDecimal(temployeeandcontract.getLoanAmount()),null
												,temployeeandcontract.getPayPrincipalDate(),null
												,null);*/
										//授信失败  导致 用原来的 授信(  客户id  为空   或者   产品主件 为空)
									/*credit.addCustomerCredit(tEmployeeService.selectId(emp).getId(), loan.getProductId(), new BigDecimal(loan.getLoanAmount()),null
											,loan.getPayPrincipalDate(),null
											,null);*/
										
										listWentiG.add("---结果:该客户数据业务数据更新成功，业务数据插入成功");
									}else if(temployee == null && tLoanContract != null){
										/**
										 *  客户不存在   但是 业务编号存在
										 */							
										listWentiG.add("---结果:该数据没有客户信息，请检查数据业务编号和业务信息的准确性，完善并重新上传");
									}else{
										//插入客户信息---------------------------------------------
										tEmployeeService.insertByExcel(emp);
										//会执行 presave() 方法  把  创建人  改为  当前用户  
										//tEmployeeService.save(emp);
												
										//插入业务信息--------------------------------------------
										tLoanService.insertByExcel(loan);
										
										//生成还款计划
										tLoanService.repoyForTemploy(loan);
										
										//生成或更新  放款记录
										tl.getContract().setId(loan.getId());
										//查询     这条数据是否存在
										TLending tllLending =tLendingService.getTLending(tl);
										
										if(tllLending !=null &&  tllLending.getId() !=null ){
											tl.setId(tLendingService.getTLending(tl).getId());
										}
										tLendingService.save(tl);
										
										//更新授信
										/*credit.addCustomerCredit(temployeeandcontract.getId(),temployeeandcontract.getProductId()
												,new BigDecimal(temployeeandcontract.getLoanAmount()),null
												,temployeeandcontract.getPayPrincipalDate(),null
												,null);*/
										//添加授信
										/*credit.addCustomerCredit(tEmployeeService.selectId(emp).getId(), loan.getProductId(), new BigDecimal(loan.getLoanAmount()),null
												,loan.getPayPrincipalDate(),null
												,null);*/
										
										listWentiG.add("---结果:该客户数据和业务数据插入成功");
									}
								} catch (Exception e) {
									e.printStackTrace();
									listWentiG.add("---结果:该数据插入或更新失败，请在excel表中检查该数据的格式是否有误，完善并重新上传");
								}
							}/*else {
								map.put(temployeeandcontract.getCardNum(), "该数据系统校验失败，请在excel表中检查该数据是否未填写，完善并重新上传");
							}*/
				}
			}
			System.out.println("****************判定完毕*******************");
		return listWentiZ;
	}
	
	
	
	/**
	 * 
	 * 接收excel已解析数据，插入企业以及业务信息
	 * */
	@Transactional(readOnly = false)
	public List<Map> excelByCompany(List<CompanyAndContract> list){//TODO
		
		//问题数据收集集合
		List<Map> listWentiZ=new ArrayList<Map>();
	
		
			for(CompanyAndContract company : list){
				if(company !=null){		
				
						Map<String,List> map=new HashMap<String,List>();
						List<String> listWentiG=new ArrayList<String>();
						map.put(company.getName(), listWentiG);
						listWentiZ.add(map);
				
						if(CompanyService.judgeForCompany(company,listWentiG)
										&& tLoanService.judgeByContractCO(company,listWentiG)){
										
										//验证成功  类转换
										TCompany com = new TCompany();
										TLoanContract loan = new TLoanContract();
										//还款计划
										TLending tl = new TLending();
										//企业信息
										com = company.otTCompany( com);
										//企业业务信息
										loan = company.otTLoanContract( loan);
										//还款计划
										tl=company.toTLending(tl);
										
										
										TCompany tcompany = CompanyService.byCompanyAndContract(company);
										TLoanContract companyByloan = tLoanService.companyByNumber(company);
										
										//获取客户经理信息
										User user = userDao.getByName(company.getCustomerManager());
										
										if(user == null){
											listWentiG.add("--结果为:该数据对应的客户经理有误，请在excel表中检查该数据的格式是否有误，完善并重新上传");
											continue;
										}
										Date nowDate = new Date();
										/*company.setCreateBy(user);
										company.setCreateDate(nowDate);
										company.setUpdateBy(user);
										company.setUpdateDate(nowDate);*/
										com.setCreateBy(user);
										com.setCreateDate(nowDate);
										com.setUpdateBy(user);
										com.setUpdateDate(nowDate);
										
										loan.setCreateBy(user);
										loan.setCreateDate(nowDate);
										loan.setUpdateBy(user);
										loan.setUpdateDate(nowDate);
										
										if(tcompany != null){//设置客户ID
											com.setId(tcompany.getId());
										}
										
										if(companyByloan != null){//设置业务ID
											loan.setId(companyByloan.getId());
											tl.getContract().setId(companyByloan.getId());
										}
										
										try {
											if(tcompany != null && companyByloan != null){
												//更新企业客户
												CompanyService.updateByExcel(com);
												/*CompanyService.save(com);*/
												//更新企业业务
												tLoanService.updateByExcel(loan);
												
												//生成还款计划
												tLoanService.repoyForTemploy(loan);
												
												//生成或更新  放款记录
												//查询     这条数据是否存在
												TLending tllLending =tLendingService.getTLending(tl);
												
												if(tllLending !=null &&  tllLending.getId() !=null ){
													tl.setId(tLendingService.getTLending(tl).getId());
												}
												tLendingService.save(tl);
				
												
												//更新授信
												/*credit.addCustomerCredit(company.getId(),company.getProductId()
														,new BigDecimal(company.getLoanAmount()),null
														,company.getPayPrincipalDate(),null
														,null);*/
												//产品主键
												/*credit.addCustomerCredit(com.getId(),loan.getProductId()
														,new BigDecimal(loan.getLoanAmount()),null
														,loan.getPayPrincipalDate(),null
														,null);*/
												listWentiG.add("--结果为:该客户数据和业务数据更新成功");
											}else if(tcompany != null && companyByloan == null){
												//更新企业客户
												CompanyService.updateByExcel(com);
												//插入企业业务
												tLoanService.insertByExcel(loan);
												
												//生成还款计划
										/*	不清楚还款计划	不改  但是没有生产还款计划--*/
												/*tLoanService.repoyForCompany(company);*/
												tLoanService.repoyForTemploy(loan);
												
												
												//生成或更新  放款记录
												tl.getContract().setId(loan.getId());
												//查询     这条数据是否存在
												TLending tllLending =tLendingService.getTLending(tl);
												
												if(tllLending !=null &&  tllLending.getId() !=null ){
													tl.setId(tLendingService.getTLending(tl).getId());
												}
												tLendingService.save(tl);
												
												//更新授信
												/*credit.addCustomerCredit(company.getId(),company.getProductId()
														,new BigDecimal(company.getLoanAmount()),null
														,company.getPayPrincipalDate(),null
														,null);*/
												/*credit.addCustomerCredit(com.getId(),loan.getProductId()
														,new BigDecimal(loan.getLoanAmount()),null
														,loan.getPayPrincipalDate(),null
														,null);*/
												listWentiG.add("--结果为:该客户数据更新成功,业务数据插入成功");
											}else if(tcompany == null && companyByloan != null){
												listWentiG.add("--结果为:该数据只有业务信息 没有客户信息，请检查数据业务编号和业务信息的准确性，完善并重新上传");
											}else{
												//插入企业客户
												CompanyService.insertByExcel(com);
												//插入企业业务
												tLoanService.insertByExcel(loan);
												
												//生成还款计划
												tLoanService.repoyForTemploy(loan);
												
												//生成或更新  放款记录
												tl.getContract().setId(loan.getId());
												//查询     这条数据是否存在
												TLending tllLending =tLendingService.getTLending(tl);
											
												if(tllLending !=null &&  tllLending.getId() !=null ){
													tl.setId(tLendingService.getTLending(tl).getId());
												}
												tLendingService.save(tl);
												
												//更新授信
												/*credit.addCustomerCredit(company.getId(),company.getProductId()
														,new BigDecimal(company.getLoanAmount()),null
														,company.getPayPrincipalDate(),null
														,null);*/
												/*credit.addCustomerCredit(com.getId(),loan.getProductId()
														,new BigDecimal(loan.getLoanAmount()),null
														,loan.getPayPrincipalDate(),null
														,null);*/
												listWentiG.add("--结果为:该客户数据和业务数据都插入成功");
											}
										} catch (Exception e) {
											e.printStackTrace();
											listWentiG.add("--结果为:该数据插入或更新失败，请在excel表中检查该数据的格式是否有误，完善并重新上传");
										}
										
									}/*else{
											listWentiG.add("--结果为:该数据系统校验失败，请在excel表中检查该数据是否未填写，完善并重新上传");
										}*/
						}			
			}		
		return listWentiZ;
	}
	
}

/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.contract.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Maps;
import com.jfinal.plugin.activerecord.Db;
import com.wanfin.fpd.common.config.Cons;
import com.wanfin.fpd.common.config.Global;
import com.wanfin.fpd.common.config.SvalBase.JsonKey;
import com.wanfin.fpd.common.mapper.JsonMapper;
import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.service.CrudService;
import com.wanfin.fpd.common.service.ServiceException;
import com.wanfin.fpd.common.utils.DateUtils;
import com.wanfin.fpd.common.utils.FreeMarkers;
import com.wanfin.fpd.common.utils.HttpPostJson;
import com.wanfin.fpd.common.utils.IdGen;
import com.wanfin.fpd.common.utils.InterfaceUtil;
import com.wanfin.fpd.common.utils.OrderNumPojo;
import com.wanfin.fpd.common.utils.PropertiesUtil;
import com.wanfin.fpd.common.utils.StringUtils;
import com.wanfin.fpd.modules.account.entity.AccountTradingRecord;
import com.wanfin.fpd.modules.account.entity.BsAccount;
import com.wanfin.fpd.modules.account.service.AccountTradingRecordService;
import com.wanfin.fpd.modules.account.service.BsAccountService;
import com.wanfin.fpd.modules.act.service.ActTaskService;
import com.wanfin.fpd.modules.bank.entity.account.TAccount;
import com.wanfin.fpd.modules.bank.service.account.TAccountService;
import com.wanfin.fpd.modules.catipal.dao.TCapitalDao;
import com.wanfin.fpd.modules.catipal.entity.TCaiwu;
import com.wanfin.fpd.modules.catipal.entity.TCapital;
import com.wanfin.fpd.modules.company.entity.TCompany;
import com.wanfin.fpd.modules.company.service.TCompanyService;
import com.wanfin.fpd.modules.contract.dao.TLoanContractBakDao;
import com.wanfin.fpd.modules.contract.dao.TLoanContractDao;
import com.wanfin.fpd.modules.contract.entity.TLoanContract;
import com.wanfin.fpd.modules.contract.entity.TLoanContractBak;
import com.wanfin.fpd.modules.credit.entity.CreditApply;
import com.wanfin.fpd.modules.credit.service.CreditApplyService;
import com.wanfin.fpd.modules.credit.service.CustomerCreditService;
import com.wanfin.fpd.modules.employee.entity.TEmployee;
import com.wanfin.fpd.modules.employee.service.TEmployeeService;
import com.wanfin.fpd.modules.excelUpload.entity.CompanyAndContract;
import com.wanfin.fpd.modules.excelUpload.entity.TEmployeeAndContract;
import com.wanfin.fpd.modules.files.service.TContractFilesService;
import com.wanfin.fpd.modules.guarantee.dao.TGuaranteeContractDao;
import com.wanfin.fpd.modules.guarantee.entity.TGuaranteeContract;
import com.wanfin.fpd.modules.guarantee.service.TGuaranteeContractService;
import com.wanfin.fpd.modules.lending.entity.TLending;
import com.wanfin.fpd.modules.lending.service.TLendingService;
import com.wanfin.fpd.modules.mortgage.entity.MortgageContract;
import com.wanfin.fpd.modules.mortgage.service.MortgageContractService;
import com.wanfin.fpd.modules.pledge.entity.PledgeContract;
import com.wanfin.fpd.modules.pledge.service.PledgeContractService;
import com.wanfin.fpd.modules.postlending.entity.fivelevel.FiveLevel;
import com.wanfin.fpd.modules.product.entity.TProduct;
import com.wanfin.fpd.modules.product.service.TProductService;
import com.wanfin.fpd.modules.repayplan.dao.TRepayPlanBakDao;
import com.wanfin.fpd.modules.repayplan.dao.TRepayPlanDao;
import com.wanfin.fpd.modules.repayplan.entity.TRepayPlan;
import com.wanfin.fpd.modules.repayplan.entity.TRepayPlanBak;
import com.wanfin.fpd.modules.repayplan.service.TRepayPlanService;
import com.wanfin.fpd.modules.repayplan.vo.PlanCreateParam;
import com.wanfin.fpd.modules.sms.entity.TMsgSwitch;
import com.wanfin.fpd.modules.sms.service.TMsgSwitchService;
import com.wanfin.fpd.modules.sys.dao.AuthenticatorDao;
import com.wanfin.fpd.modules.sys.entity.AuthenticationUser;
import com.wanfin.fpd.modules.sys.entity.AuthenticationUserRel;
import com.wanfin.fpd.modules.sys.entity.Office;
import com.wanfin.fpd.modules.sys.entity.User;
import com.wanfin.fpd.modules.sys.service.AuthenticatorService;
import com.wanfin.fpd.modules.sys.service.SystemService;
import com.wanfin.fpd.modules.sys.utils.DictUtils;
import com.wanfin.fpd.modules.sys.utils.UserUtils;

/**
 * 业务办理Service
 * @author lx
 * @version 2016-03-18
 */
@Service("tLoanContractService")
@Transactional(readOnly = true)
public class TLoanContractService extends CrudService<TLoanContractDao, TLoanContract> {

	@Autowired
	private TProductService tProductService;
	
	@Autowired
	private TLendingService tLendingService;
	
	@Autowired
	private TAccountService tAccountService;

	@Autowired
	private TRepayPlanService tRepayPlanService;
	
	@Autowired
	private RuntimeService runtimeService;
	
	@Autowired
	private TRepayPlanBakDao repayPlanBakDao;
	
	@Autowired
	private TRepayPlanDao repayPlanDao;
	@Autowired
	private SystemService systemService;
	/*@Autowired
	private AuthenticatorRelDao authenticatorRelDao;*/
	@Autowired
	private AuthenticatorDao authenticatorDao;
	@Autowired
	private ActTaskService actTaskService;
	@Autowired
	private TContractFilesService tContractFilesService;
	
	@Autowired
	private TLoanContractBakDao loanContractBakDao;
	
	@Autowired
	private TLoanContractDao loanContractDao;
	
	@Autowired
	private TCapitalDao capitaldao;
	
	@Autowired
	private TGuaranteeContractDao guaranteeContractDao;
	
	@Autowired
	private TCompanyService tCompanyService;
	
	@Autowired
	private TEmployeeService tEmployeeService;
	
	@Autowired
	private PledgeContractService pledgeContractService;
	
	@Autowired
	private MortgageContractService mortgageContractService;
	
	@Autowired
	private CustomerCreditService customerCreditService;
	
	@Autowired
	private	TGuaranteeContractService tGuaranteeContractService;
	@Autowired
	private CreditApplyService creditApplyService;//add by shirf 20161204
	@Autowired
	private BsAccountService bsAccountService;//资金账户信息
	@Autowired
	private AccountTradingRecordService accountTradingRecordService;
	@Autowired
	private TMsgSwitchService tMsgSwitchService;
	
	
	
	public TLoanContract get(String id) {
		return super.get(id);
	}
	
	/**
	 * 根据贷款合同ID查找贷款合同变更备份
	 */
	public TLoanContractBak getContractBakByContractId(TLoanContract souceContract) {
		TLoanContractBak query = new TLoanContractBak();
		query.setSouceContract(souceContract);
		query.setDataStatus("1");;
		//("from LoanContractBak where data_status=1 and loan_contract_id=?", id);
		List<?> list = loanContractBakDao.findList(query);
			
		TLoanContractBak contractBak = new TLoanContractBak();
		if (list != null && list.size() > 0) {
			contractBak = (TLoanContractBak) list.get(0);
		}
		return contractBak;
		//return null;
	}
	
	
	public List<TLoanContract> findList(TLoanContract tLoanContract) {

		tLoanContract.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser().getCurrentUser(), "o", "u"));

		tLoanContract.setCreateBy(UserUtils.getUser());//过滤只有创建人才能签订合同

		return super.findList(tLoanContract);
	}
	
	
	
	public List<TLoanContract> getLoanListsByIds(String id) {
		return loanContractDao.getLoanListsByIds(id);
	}
	
	
	/**
	 * add for #3144
	 */
	public List<TLoanContract> findForExcelPlan(TLoanContract tLoanContract) {

		tLoanContract.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser().getCurrentUser(), "o", "u"));

		return super.findList(tLoanContract);
	}
	
	public Page<TLoanContract> findPage(Page<TLoanContract> page, TLoanContract tLoanContract) {
		tLoanContract.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser().getCurrentUser(), "o", "u"));
		return super.findPage(page, tLoanContract);
	}
	
	
	
	public List<TLoanContract> findListByStatus(TLoanContract tLoanContract) {
		tLoanContract.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser().getCurrentUser(), "o", "u"));
		return super.findList(tLoanContract);
	}
	/**
	 * @Description 根据合同编号生成展期编号
	 * @param code	需要展期的合同的编号
	 * @return
	 * @author zzm 
	 * @date 2016-4-12 下午4:30:16  
	 */
	public String getExtendCodeByContractCode(String code){
		String extendCode = null;
		if(code.contains("-ET")){
			int index = code.indexOf("-ET");
			extendCode = code.substring(0,index)+"-ET"+(Integer.valueOf(code.substring(index+3)) + 1);
		}else{
			extendCode = code + "-ET1";
		}
		return extendCode; 
	}
	
	/**
	 * 为查找所有可以申请退款的内容
	 * @author shirf 
	 * @date 20160408
	 */
	public Page<TLoanContract> findRefundList(Page<TLoanContract> page, TLoanContract tLoanContract) {
		tLoanContract.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser().getCurrentUser(), "o", "u"));
		tLoanContract.setPage(page);
		page.setList(dao.findRefundList(tLoanContract));
		return page;
	}
	
	/**
	 * 更新合同状态
	 * add by shirf 20160406
	 * @param tLoanContract 合同
	 * @return
	 */
	@Transactional(readOnly = false)
	public int updateStatus(TLoanContract tLoanContract){
		//updatOrderStatus(tLoanContract);
		return super.dao.updateStatus(tLoanContract);
	}
	
	
	/**
	 * 更新订单状态
	* @Description: TODO 
	* @author ZXJ
	* @param tLoanContract  
	* @throws
	 */
	private void updatOrderStatus(TLoanContract tLoanContract) {
		if(tLoanContract.getStatus().contains("5")){
			String url = Global.getWfwHttp() + "orderLoan/updateLoanRecordStatus/";
			net.sf.json.JSONObject jo = new net.sf.json.JSONObject();
			jo.element("pid", tLoanContract.getWtypeId());
			jo.element("orderStatus", "5");
			HttpPostJson post = new HttpPostJson();
			String result = post.sendPostJson(url, jo.toString());
			logger.info(result);
		}
	}

	@Transactional(readOnly = false)
	public void save(TLoanContract tLoanContract) throws ServiceException{
		//判断是否是临时ID add by srf
		String tempID = null;
		if(StringUtils.isNotBlank(tLoanContract.getId()) && tLoanContract.getId().startsWith("tmp_")){
			tempID = tLoanContract.getId();
			tLoanContract.setId(null);
			tLoanContract.setFiveLevel(Cons.FiveLevelStatus.NORMAL_N);
		}
		
		//修改，先把还款计划删除，在重新添加。
		/*if(StringUtils.isNotBlank(tLoanContract.getId())){
			Db.update("delete from t_repay_plan where loan_contract_id = ?",tLoanContract.getId());
		}*/
		
		TCompany company = tCompanyService.get(tLoanContract.getCustomerId());
		TEmployee employee = tEmployeeService.get(tLoanContract.getCustomerId());
		if(company != null){
			tLoanContract.setCustomerType(Cons.CustomerType.CUST_COMPANY);
			tLoanContract.setCustomerName(company.getName());
		}else if(employee != null){
			tLoanContract.setCustomerType(Cons.CustomerType.CUST_EMPLOYEE);
			tLoanContract.setCustomerName(employee.getName());
		}
		
		TProduct product = tProductService.get(tLoanContract.getProductId());
		if(StringUtils.isBlank(tLoanContract.getId()) && StringUtils.isNotBlank(product.getReleasesObje()) && !product.getReleasesObje().contains(tLoanContract.getCustomerType())){
			throw new ServiceException("产品指定发行对象为["+product.getReleasesObje().replace(Cons.CustomerType.CUST_COMPANY, "企业").replace(Cons.CustomerType.CUST_EMPLOYEE, "个人")+"]");
		}
		
		if ((tLoanContract != null) && StringUtils.isBlank(tLoanContract.getStatus())){//新增的合同状态为空，插入数据库时设定为1：新增，为提交审核
			tLoanContract.setStatus(Cons.LoanContractStatus.TO_REVIEW);
		}
		//处理每期还款日1:固定某个日子，2：根据放款日期，获取日
		if(tLoanContract.getPayDayType() != null && "2".equals(tLoanContract.getPayDayType())){
			Calendar cal = Calendar.getInstance();
			cal.setTime(tLoanContract.getLoanDate());
			tLoanContract.setPayDay(cal.get(Calendar.DATE)+"");
		}
		if(tLoanContract.getType() == null){
			tLoanContract.setType(Cons.LoanOrderType.TYPE_B);
		}
		super.save(tLoanContract);
		
		//add by srf 更新对应表中的tempID
		if(StringUtils.isNotBlank(tempID)){
			//调整关联附件表
			tContractFilesService.updateFileTaskId(tempID, tLoanContract.getId());
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("businessId", tLoanContract.getId());
			map.put("id", tempID);
			
			//1、质押；2、抵押；3、信用；4、保证
			//调整关联抵押信息表
			map.put("table", "t_mortgage_contract");
			super.dao.updateBusinessId(map);
			
			//调整关联质押信息表
			map.put("table", "t_pledge_contract");
			super.dao.updateBusinessId(map);
			
			//调整保证表
			map.put("table", "t_guarantee_contract");
			super.dao.updateBusinessId(map);
		}
		
	}
	
	/**
	 * add 保存业务信息
	 * 20160413 srf 修改添加了临时ID内容，并把控制层中service内容移入
	 */
	@Transactional(readOnly = false)
	public void save(TLoanContract tLoanContract,TRepayPlan tRepayPlan,String counts) {
		//判断是否是临时ID add by srf
		String tempID = null;
		if(StringUtils.isNotBlank(tLoanContract.getId()) && tLoanContract.getId().startsWith("tmp_")){
			tempID = tLoanContract.getId();
			tLoanContract.setId(null);
		}
		
		String contractid = tLoanContract.getId();//如果id不为空，则表示是修改，先把还款计划删除，在重新添加。
		if(contractid!=null&&!"".equals(contractid)){
			Db.update("delete from t_repay_plan where loan_contract_id = ?",contractid);
		}
		if ((tLoanContract != null) && StringUtils.isBlank(tLoanContract.getStatus())){//新增的合同状态为空，插入数据库时设定为1：新增，为提交审核
			tLoanContract.setStatus(Cons.LoanContractStatus.TO_REVIEW);
		}
		//处理每期还款日1:固定某个日子，2：根据放款日期，获取日
		if(tLoanContract.getPayDayType() != null && "2".equals(tLoanContract.getPayDayType())){
			Calendar cal = Calendar.getInstance();
			cal.setTime(tLoanContract.getLoanDate());
			tLoanContract.setPayDay(cal.get(Calendar.DATE)+"");
		}
		
		if(tLoanContract.getWtypeId() == null){
			tLoanContract.setWtypeId(Cons.LoanOrderType.TYPE_B);
		}
		//保存业务信息，并获取ID备后边处理
		if(tLoanContract.getType() == null){
			tLoanContract.setType(Cons.LoanOrderType.TYPE_B);
		}
		super.save(tLoanContract);
		
		//tRepayPlanService.save(tRepayPlan.getEndDate(), tRepayPlan.getPrincipal(), tRepayPlan.getInterest(), tRepayPlan.getStartDate(), tRepayPlan.getAccountDate(), counts,tLoanContract);
		
		//保存还款计划
		List<TRepayPlan> planList = tLoanContract.getRepayPlanList();
		if(planList != null && planList.size() > 0){
			//先清除旧的还款计划
			TRepayPlan plan = new TRepayPlan();
			plan.setLoanContractId(tLoanContract.getId());
			tRepayPlanService.deletePLWL(plan);
			
			for(TRepayPlan repayPlan : planList){
				repayPlan.setLoanContractId(tLoanContract.getId());
				repayPlan.setStatus(Cons.RepayStatus.NO_PAID);
				tRepayPlanService.save(repayPlan);
			}
		}
		
		//add by srf 更新对应表中的tempID
		if(StringUtils.isNotBlank(tempID)){
			//调整关联附件表
			tContractFilesService.updateFileTaskId(tempID, tLoanContract.getId());
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("businessId", tLoanContract.getId());
			map.put("id", tempID);
			
			//1、质押；2、抵押；3、信用；4、保证
			//调整关联抵押信息表
			map.put("table", "t_mortgage_contract");
			super.dao.updateBusinessId(map);
			
			//调整关联质押信息表
			map.put("table", "t_pledge_contract");
			super.dao.updateBusinessId(map);
			
			//调整保证表
			map.put("table", "t_guarantee_contract");
			super.dao.updateBusinessId(map);
		}
		
	}
	
	@Transactional(readOnly = false)
	public void delete(TLoanContract tLoanContract) {
		//start
		//删除自动授信 add by shirf 20161204
		CreditApply creditApply = new CreditApply();
		creditApply.setCustomerId(tLoanContract.getCustomerId());
		creditApply.setRemarks("自动授信");
		creditApply.setOrganId(tLoanContract.getOrganId());
		creditApplyService.deleteByCondition(creditApply);
		//end
				
		super.delete(tLoanContract);
	}

	public Integer querymaxNumber(User currentUser) {
		return querymaxNumber(currentUser.getCompany().getId());
	}

	public Integer querymaxNumber(String organId) {
		return Db.queryInt("select max(max_number) from t_loan_contract where organ_id = ?", organId);
	}
	public Integer querymaxNumber(String organId,String year) {
		return Db.queryInt("select max(max_number) from t_loan_contract t where organ_id = ? AND date_format(t.apply_date,'%Y')=? and t.del_flag='0'", organId,year);
	}
	public String querycontractNumber(User currentUser) {
		return querycontractNumber(currentUser.getCompany().getId());
	}

	public String querycontractNumber(String organId) {
		return Db.queryStr("select contract_number from sys_office where id = ?", organId);
	}
	
	/**
	 * 根据扫描状态扫描为推送的记录
	 * @param tLoanContract
	 * @return
	 */
	public List<TLoanContract> findListByScanFlag(TLoanContract tLoanContract){
		return loanContractDao.findListByScanFlag(tLoanContract);
	}
	
	/**
	 * 根据扫描状态扫描为推送的记录
	 * @param tLoanContract
	 * @return
	 */
	public List<TLoanContract> findListByScanFlagAndPushStatus(TLoanContract tLoanContract){
		return loanContractDao.findListByScanFlagAndPushStatus(tLoanContract);
	}
	
	
	
	/**
	 * 根据扫描状态扫描为推送的记录 担保
	 * @param tLoanContract
	 * @return
	 */
	public List<TLoanContract> findListDbByScanFlagAndPushStatus(TLoanContract tLoanContract){
		return loanContractDao.findListDbByScanFlagAndPushStatus(tLoanContract);
	}
	
	
	/**
	 * 根据扫描状态扫描为推送的记录 担保
	 * @param tLoanContract
	 * @return
	 */
	public List<TLoanContract> findListDbByScanFlag(TLoanContract tLoanContract){
		return loanContractDao.findListDbByScanFlag(tLoanContract);
	}
	
	
	/**
	 * 根据扫描状态扫描为推送的记录 担保
	 * @param tLoanContract
	 * @return
	 */
	public int updateByPushStatus(TLoanContract tLoanContract){
		return loanContractDao.updateByPushStatus(tLoanContract);
	}
	
	
	
	
	/**
	 * @Description 请求放款操作
	 * @param tLoanContract 贷款合同
	 * @return
	 * @author Chenh 
	 * @date 2016年3月29日 下午4:34:13
	 */
	@Transactional(readOnly = false)
	public Map<String, Object> requireTlending(TLoanContract tLoanContract) {
		Map<String, Object> result = new HashMap<String, Object>();
		Boolean status = false;
		StringBuffer msg = new StringBuffer();
		
		TLoanContract entity = null;
		if ((tLoanContract != null) && StringUtils.isNotBlank(tLoanContract.getId())){
			entity = get(tLoanContract.getId());
		}
		
		if (entity != null){
			TLending tLending = new TLending();
			tLending.setContract(entity);
			List<TLending> tLendings = tLendingService.findList(tLending);
			if((tLendings != null) && (tLendings.size() > 0)){
				msg.append("贷款合同申请放款已经发布成功，请勿重复发布");
			}else{
				tLending.setAmount(entity.getLoanAmount());
				tLendingService.save(tLending);
				
				entity.setStatus(Cons.LoanContractStatus.TO_LOAN);
				save(entity);
				msg.append("贷款合同申请放款成功");
			}
			status = true;
		}else{
			status = false;
			msg.append("贷款合同不存在：[id]="+tLoanContract.getId());
		}
		
		result.put(JsonKey.KEY_STATUS, status);
		result.put(JsonKey.KEY_MSG, msg);
		return result;
	}
	
	/**
	 * @Description 放款生成还款计划
	 * @param tLending 放款记录
	 * @param endDate  计划到账日
	 * @param principal 计划收入本金
	 * @param interest 计划收入利息
	 * @param startDate 计息开始时间
	 * @param accountDate 计息结束时间
	 * @param counts 记录数
	 * @return
	 * @author Chenh
	 * @throws ServiceException
	 * @date 2016年3月29日 下午4:31:35
	 * 20161207 shirf 修改loanAmountSY == 0是放款走customerCreditService.useCustomerCredit方法，以防止放款金额大于可用额度的情况
	 */
	@Transactional(readOnly = false)
	public Map<String, Object> finishedTlending(TLending tLending, TRepayPlan tRepayPlan, String counts,TCapital capital) throws ServiceException {
		Map<String, Object> result = new HashMap<String, Object>();
		Boolean status = false;
		StringBuffer msg = new StringBuffer();
		
		TLoanContract newContract = tLending.getContract();
		TLoanContract entity = null;
		if ((tLending != null) && (tLending.getContract() != null) && StringUtils.isNotBlank(tLending.getContract().getId())){
			entity = get(tLending.getContract().getId());
		}
		BsAccount sellAccount=null;
		BsAccount buyAccount=null;
		//TProduct product=tProductService.get(entity.getProductId());//W端产品放款时更新产品信息
		if (entity != null){
			BigDecimal loanAmount = new BigDecimal(entity.getLoanAmount());//贷款金额
			BigDecimal loanAmountIng = new BigDecimal(tLending.getAmount());//当前放款金额
			BigDecimal loanAmountEd = tLendingService.countAmount(entity);//历史放款金额
			BigDecimal loanAmountSY = loanAmount.subtract(loanAmountEd).subtract(loanAmountIng);//剩余放款金额
			//金额对比处理节省处理时间 add by shirf 20170106
			if(loanAmount.compareTo(loanAmountIng)<0){
				result.put(JsonKey.KEY_STATUS, status);
				result.put(JsonKey.KEY_MSG, "放款金额错误：放款金额大于贷款金额");
				return result;
			}else if(loanAmount.compareTo(loanAmountIng)>0){
				//W端需要一次性放款
				if(StringUtils.isNotBlank(entity.getType()) && "1".equals(entity.getType())){
					result.put(JsonKey.KEY_STATUS, status);
					result.put(JsonKey.KEY_MSG, "放款金额错误：该笔贷款不允许多次放款，必须一次性放款");
					return result;
				}
			}
			
	        TProduct tProduct=tProductService.get(entity.getProductId());
	        
	        
			if(StringUtils.isNotBlank(entity.getMoveStatus()) && entity.getMoveStatus().equals(Cons.MoveStatus.AUDIT_SUCCESS)){
				String lastNo=dao.getLoanLastNo(entity.getProductId());
				entity.setContractNumber(OrderNumPojo.getOrderNum(tProduct.getName(), lastNo));
			}
			
			//是否需要授信
			boolean needCredit = true;
			if(UserUtils.getUser().getCompany().getPrimaryPerson()!=null&&"1".equals(UserUtils.getUser().getCompany().getPrimaryPerson())){
				needCredit = false;
			}
			if(StringUtils.isNotBlank(entity.getAuthUserId())){
				needCredit = false;
			}
			
			if(StringUtils.isNotBlank(tLending.getIfOnline()) && tLending.getIfOnline().equals("1")){//通过W线上资金账户----默认为空 不走资金账户
			if(StringUtils.isNotBlank(entity.getType()) && "1".equals(entity.getType())){
				needCredit = false;
				//通过机构ID获取资金账户：机构ID--->管理员ID--->认证ID--->资金账户
				sellAccount=bsAccountService.getbyOrganId(UserUtils.getUser().getCompany().getId());
				if(sellAccount==null){
					result.put(JsonKey.KEY_STATUS, status);
					result.put(JsonKey.KEY_MSG, "万众金融平台租户资金账户不存在！");//W端
					return result;
				}else{
					//买家资金账户
					if(StringUtils.isBlank(entity.getBuyAuthId())){
						result.put(JsonKey.KEY_STATUS, status);
						result.put(JsonKey.KEY_MSG, "万众金融平台用户认证异常！");//W端
						return result;
					}else{
						buyAccount=bsAccountService.getByAuthUser(entity.getBuyAuthId());
						if(buyAccount==null){
							result.put(JsonKey.KEY_STATUS, status);
							result.put(JsonKey.KEY_MSG, "万众金融平台客户资金账户不存在！");//W端
							return result;
						}else{
							 if(sellAccount.getAvailableFunds().compareTo(new BigDecimal(tLending.getAmount()))<0){
								  result.put(JsonKey.KEY_STATUS, status);
								  result.put(JsonKey.KEY_MSG, "万众金融平台可用余额不足，无法放款！！");//W端
								  return result;
						       }
						}
						
					}
					
				}
			}
			
			}
			
			BigDecimal zero = new BigDecimal(0);
			if(loanAmountSY.compareTo(zero)>0){//loanAmountSY > 0
				//if(!product.getReleasesWay().equals("1")){//不是W端的产品才走授信
				if(needCredit){//不是W端的产品才走授信
					try {
						String res=customerCreditService.useCustomerCredit(entity.getCustomerId(), new BigDecimal(tLending.getAmount()));
						if(res.equals("sucess")){
							status = true;
							msg.append("贷款合同已成功放款:"+(loanAmountEd.add(new BigDecimal(tLending.getAmount())).setScale(2, BigDecimal.ROUND_HALF_UP))+"元");
						}else{
							result.put(JsonKey.KEY_STATUS, false);
							result.put(JsonKey.KEY_MSG, res);//W端
							return result;
						}
					} catch (ServiceException e) {
						e.printStackTrace();
						msg.append(e.getMessage());
						status = false;
					}
				}
				tLendingService.save(tLending);
			}else if(loanAmountSY.compareTo(zero)==0){
				System.out.println(UserUtils.getUser().getCompany().getPrimaryPerson());
				if(UserUtils.getUser().getCompany().getPrimaryPerson()!=null&&"1".equals(UserUtils.getUser().getCompany().getPrimaryPerson())){
					entity.setStatus(Cons.LoanContractStatus.DB_TO_CHECK);//担保专用  已放款，进入保后管理
				}else{
					entity.setStatus(Cons.LoanContractStatus.UN_CLEARED);
				}
				save(entity);
				//更新orderLoan
				updateOrderLoan(entity);
				msg.append("贷款合同全部放款成功");
				status = true;
				if(entity.getMoveOffice()==null){//订单转移暂不处理授信问题------存在客户归属问题，由哪个机构授信
					if(status && needCredit){
					 //放款之后，授信可用额度减少 dealed by shirf 20161207
					 customerCreditService.useCustomerCredit(entity.getCustomerId(), loanAmountIng);
					}
				}
				tLendingService.save(tLending);
			}else{
				msg.append("放款异常！放款总金额大于贷款金额！");
				status = false;
			}
			/**
			 * 添加账户
			 */
			if(StringUtils.isNoneEmpty(newContract.getGatheringNumber())){
				TAccount tAccount = new TAccount();
				tAccount.setCustomerId(entity.getCustomerId());
				tAccount.setType(entity.getCustomerType());
				tAccount.setGatheringBank(newContract.getGatheringBank());
				tAccount.setGatheringName(newContract.getGatheringName());
				tAccount.setGatheringNumber(newContract.getGatheringNumber());
				tAccountService.save(tAccount);
			}
			
			/**
			 * 批量删除还款计划、新增还款计划
			 */
			/* by zzm @20160531
			 if((tRepayPlan != null) && (StringUtils.isNoneEmpty(tRepayPlan.getEndDate()))
					&& (StringUtils.isNoneEmpty(tRepayPlan.getPrincipal()))
					&& (StringUtils.isNoneEmpty(tRepayPlan.getInterest()))
					&& (StringUtils.isNoneEmpty(tRepayPlan.getStartDate()))
					&& (StringUtils.isNoneEmpty(tRepayPlan.getAccountDate()))){
				TRepayPlan newTRepayPlan = new TRepayPlan();
				tRepayPlan.setLoanContractId(entity.getId());
				tRepayPlanService.deletePLWL(newTRepayPlan);
				tRepayPlanService.save(tRepayPlan.getEndDate(), tRepayPlan.getPrincipal(), tRepayPlan.getInterest(), tRepayPlan.getStartDate(), tRepayPlan.getAccountDate(), counts, entity);
			}*/
			//登录人为贷款的，才需要保持还款计划
			if(UserUtils.getUser().getCompany().getPrimaryPerson()!=null&&"2".equals(UserUtils.getUser().getCompany().getPrimaryPerson())){
				//没有还款计划的需要生成还款计划，有还款计划的则不做处理
				List<TRepayPlan> repayPlanList = tRepayPlanService.getPlanByContractId(entity.getId());
				if(repayPlanList == null || repayPlanList.size()<1){
					//先清除旧的还款计划
					TRepayPlan plan = new TRepayPlan();
					plan.setLoanContractId(entity.getId());
					tRepayPlanService.deletePLWL(plan);
				}
				try {
					PlanCreateParam param = new PlanCreateParam();
					param.setBusinessType("apply");
					param.setAmount(new BigDecimal(entity.getLoanAmount()));//changed #3121
					param.setLoanRate(new BigDecimal(entity.getLoanRate()));//changed #3121
					
					if("年".equals(entity.getLoanRateType())){
						param.setLoanRateType("1");//利率类型   1年  2月  3日  
					}else if("月".equals(entity.getLoanRateType())){
						param.setLoanRateType("2");//利率类型   1年  2月  3日  
					}else if("日".equals(entity.getLoanRateType())){
						param.setLoanRateType("3");//利率类型   1年  2月  3日  
					}
					param.setLoanPeriod(entity.getLoanPeriod());
										
//					param.setLoanDate(DateUtils.formatDate(entity.getLoanDate()));
					param.setLoanDate(DateUtils.formatDate(tLending.getTime()));
					param.setPayType(entity.getPayType());
					param.setPeriodType(entity.getPeriodType());
					param.setPayOptions(entity.getPayOptions());
					param.setIfRealityDay(entity.getIfRealityDay());//是否按大小月计息，lzq修改
					param.setPayDay(entity.getPayDay());
					if(repayPlanList == null || repayPlanList.size()<1){
						JSONArray list = tRepayPlanService.createRepayPlans(param);
						if(list != null){
							String lotNum = tRepayPlanService.createLotNum();
							for(int i=0;i<list.length();i++){
								TRepayPlan repayPlan = (TRepayPlan) JsonMapper.fromJsonString(list.get(i).toString(), TRepayPlan.class);
								repayPlan.setLotNum(lotNum);
								repayPlan.setLoanContractId(entity.getId());
								repayPlan.setStatus(Cons.RepayStatus.NO_PAID);
								repayPlan.setIsValid("1");
								repayPlan.setRepayAuthId(entity.getBuyAuthId());
								if(needCredit){//不是W端的产品
									repayPlan.setType("0");
								}else{
									repayPlan.setType("1");
								}
								tRepayPlanService.save(repayPlan);
							}
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					throw new ServiceException("放款失败！保存还款计划时出错！");
				}
				//if(StringUtils.isNotBlank(product.getReleasesWay())){
				if(StringUtils.isNotBlank(tLending.getIfOnline()) && tLending.getIfOnline().equals("1")){
				if(StringUtils.isNotBlank(entity.getType()) && "1".equals(entity.getType())){
					      
					
					        sellAccount.setTotalFunds(sellAccount.getTotalFunds().subtract(new BigDecimal(tLending.getAmount())));
					        sellAccount.setAvailableFunds(sellAccount.getAvailableFunds().subtract(new BigDecimal(tLending.getAmount())));
					        bsAccountService.update(sellAccount);
					        AccountTradingRecord sellRecord=new AccountTradingRecord();
					    	/********卖家租户资金流水***********/
					        sellRecord.setSeqNo(entity.getContractNumber());// 编号(合同编号)---------------方便查看流水是关于哪比投资的
					        sellRecord.setTradingType(Cons.AccountTradingType.GO_LOAN);// 交易类型:放款
					        sellRecord.setTradingWay(Cons.ifOnLine.ON_LINE);		// 交易方式:交易方式(1，线上流水,2，线下流水)
					        sellRecord.setAmount(new BigDecimal(tLending.getAmount()));	// 交易金额
					        sellRecord.setTotalFunds(sellRecord.getTotalFunds());//交易后总资金
					        sellRecord.setFreezingFunds(sellRecord.getFreezingFunds()); //交易后冻结资金
					        sellRecord.setAvailableBalance(sellAccount.getAvailableFunds()); //交易后可用余额
					        sellRecord.setAccountId(sellAccount.getId());		// 账户ID
					        sellRecord.setAuhtUserId(sellAccount.getAuthUserId());
					        sellRecord.setRecordType("2");//出账
					        sellRecord.setTradingTime(new Date());		// 交易时间
					        sellRecord.setBusinessId(entity.getId());// 交易业务id
					        sellRecord.setBusinessName(tProduct.getName());// 交易业务名称(推荐交易类型与交易名称组合)
					        sellRecord.setCreateBy(new User("1"));
					        sellRecord.setCreateDate(new Date());
					        sellRecord.setUpdateBy(new User("1"));
					        sellRecord.setUpdateDate(new Date());
					        sellRecord.setDelFlag("0");
					        sellRecord.setRemarks("机构放款记录");
					        accountTradingRecordService.save(sellRecord);
					        
					        //买家资金账户
					        buyAccount.setTotalFunds(buyAccount.getTotalFunds().add(new BigDecimal(tLending.getAmount())));
					        buyAccount.setAvailableFunds(buyAccount.getAvailableFunds().add(new BigDecimal(tLending.getAmount())));
					        bsAccountService.update(buyAccount);
					        AccountTradingRecord buyRecord=new AccountTradingRecord();
					      
					        
					      
					        /********买家资金流水***********/
					        buyRecord.setSeqNo(entity.getContractNumber());// 编号(合同编号)---------------方便查看流水是关于哪比投资的
					        buyRecord.setTradingType(Cons.AccountTradingType.GO_LOAN);// 交易类型:放款
					        buyRecord.setTradingWay(Cons.ifOnLine.ON_LINE);		// 交易方式:交易方式(1，线上流水,2，线下流水)
					        buyRecord.setRecordType("1");//入账
					        buyRecord.setAmount(new BigDecimal(tLending.getAmount()));	// 交易金额
					        buyRecord.setTotalFunds(buyAccount.getTotalFunds());//交易后总资金
					        buyRecord.setAvailableBalance(buyAccount.getAvailableFunds()); //交易后可用余额
					        buyRecord.setFreezingFunds(buyAccount.getFreezingFunds()); //交易后冻结资金
					        buyRecord.setAccountId(buyAccount.getId());		// 账户ID
					        buyRecord.setAuhtUserId(buyAccount.getAuthUserId());
					        buyRecord.setTradingTime(new Date());		// 交易时间
					        buyRecord.setBusinessId(entity.getId());// 交易业务id
					        buyRecord.setBusinessName(tProduct.getName());// 交易业务名称(推荐交易类型与交易名称组合)
					        buyRecord.setCreateBy(new User("1"));
					        buyRecord.setCreateDate(new Date());
					        buyRecord.setUpdateBy(new User("1"));
					        buyRecord.setUpdateDate(new Date());
					        buyRecord.setDelFlag("0");
					        buyRecord.setRemarks("借款放款入账记录");
					        accountTradingRecordService.save(buyRecord);
					        
					        
							TMsgSwitch tMsgSwitch=tMsgSwitchService.checkMsgSwitch(Cons.MsgBusinessType.MAKE_LOAN_SUCCESS);
							if(tMsgSwitch!=null){
								if(tMsgSwitch.getLetterStatus().equals("1")){
									String title = Cons.letterDForm.LETTER_MAKELOANSUCCESS_TITILE;
									Map<String, String> model = com.google.common.collect.Maps.newHashMap();
									model.put("time", DateUtils.formatDate(entity.getApplyDate()));
									model.put("loanNum", entity.getContractNumber());
									model.put("money", tLending.getAmount());
									String content = FreeMarkers.renderString(Cons.letterDForm.LETTER_MAKELOANSUCCESS_CONTENT, model);
									Map<String, String> letterMap=new HashMap<String, String>();
									letterMap.put("title", title);
									letterMap.put("msgContent", content);
									letterMap.put("sendUserId", entity.getAuthUserId());
									letterMap.put("readUserId","'"+entity.getBuyAuthId()+"'");
									letterMap.put("type",Cons.letterDForm.LETTER_MAKELOANSUCCESS);
									letterMap.put("name",title);
									Map<String,Object> lmap=systemService.addLetter(letterMap);
									if(!(boolean) lmap.get("isTrue")){
										msg.append("站内信发送失败！");
									}
								}
								if(tMsgSwitch.getMarketStatus().equals("1")){
									AuthenticationUser buyaAuthUser=authenticatorDao.get(entity.getBuyAuthId());
									String mobile="";
					    			if(buyaAuthUser!=null){
					    				mobile=buyaAuthUser.getMobile();
					    			}
					    			if(StringUtils.isNotBlank(mobile)){
					    				Map<String, String> msgmodel = com.google.common.collect.Maps.newHashMap();
					        			msgmodel.put("time",DateUtils.formatDate(entity.getApplyDate()));
					        			msgmodel.put("loanNum",entity.getContractNumber());
					        			msgmodel.put("money",tLending.getAmount());
					        			String msgtext = FreeMarkers.renderString(Cons.messageDForm.MESSAGE_MAKELOANSUCCESS_CONTENT, msgmodel);
					        			Map<String,Object> lmap=systemService.addMarket(mobile,msgtext);
										if(!(boolean) lmap.get("isTrue")){
											msg.append("营销短信发送失败！");
										}
					    			}
									
								}
							}
							
							
					        
				    }
			    }
				
				//修改可放贷资金
				if(needCredit){//不是W端的产品才处理可放贷资金Feature #4449
				BigDecimal loanamount = new BigDecimal(capital.getLoanamount()) ; //放贷注入资本（可贷款金额）
				capital.setLoanamount(loanamount.subtract(new BigDecimal(tLending.getAmount()))+"");//减去这次放款金额
				capitaldao.update(capital);
				}
				
			}
			
			//放款短信提醒
			if(status){
				String phone = "";
				if(Cons.CustomerType.CUST_COMPANY.equals(entity.getCustomerType())){
					TCompany company = tCompanyService.get(entity.getCustomerId());
					phone = company.getSuretyMobile();
				}else if(Cons.CustomerType.CUST_EMPLOYEE.equals(entity.getCustomerType())){
					TEmployee employee = tEmployeeService.get(entity.getCustomerId());
					phone = employee.getMobile();
				}			
			}
		}else{
			status = false;
			msg.append("贷款合同不存在");
		}
		result.put(JsonKey.KEY_STATUS, status);
		result.put(JsonKey.KEY_MSG, msg);
		return result;
	}
	
	public void updateOrderLoan(TLoanContract entity){
 		dao.updateOrderLoan(entity);
	}
	
	@Transactional(readOnly = false)
	public void startProcess(TLoanContract tLoanContract) {
		// 启动流程
		String procInsId = actTaskService.startProcess(tLoanContract.getAct().getProcDefKey(), "t_loan_contract", tLoanContract.getId(), tLoanContract.getContractNumber());
		if(StringUtils.isNotBlank(procInsId))
			actTaskService.completeFirstTask(procInsId, tLoanContract.getAct().getComment(), tLoanContract.getContractNumber(), null);
		tLoanContract.setStatus(Cons.LoanContractStatus.TO_APPROVE);
		updateStatus(tLoanContract);
	}              
	/**
	 * 启动放款流程
	 * @param tLoanContract
	 */
	@Transactional(readOnly = false)
	public void startLendProcess(TLoanContract tLoanContract) {
		// 启动流程
		String procInsId = actTaskService.startProcess(tLoanContract.getAct().getProcDefKey(), "t_loan_contract", tLoanContract.getId(), tLoanContract.getContractNumber());
		if(StringUtils.isNotBlank(procInsId))
			actTaskService.completeFirstTask(procInsId, tLoanContract.getAct().getComment(), 
					tLoanContract.getContractNumber(), null);
		tLoanContract.setStatus(Cons.LoanContractStatus.TO_APPROVE);//放款审批中
		updateStatus(tLoanContract);
	}            

	/**
	 * 审核审批保存
	 * @param tLoopLoan
	 */
	@SuppressWarnings("unused")
	@Transactional(readOnly = false)
	public void auditSave(TLoanContract tLoanContract) {
		
		// 设置意见
		tLoanContract.getAct().setComment(("yes".equals(tLoanContract.getAct().getFlag())?"[同意] ":"[驳回] ")+tLoanContract.getAct().getComment());
		
		tLoanContract.preUpdate();
		
		// 对不同环节的业务逻辑进行操作
		String taskDefKey = tLoanContract.getAct().getTaskDefKey();
		
		// 提交流程任务
		Map<String, Object> vars = Maps.newHashMap();
		vars.put("pass", "yes".equals(tLoanContract.getAct().getFlag())? "1" : "0");
		if(!tLoanContract.getAct().getFlag().equals("stop")){
			actTaskService.complete(tLoanContract.getAct().getTaskId(), tLoanContract.getAct().getProcInsId(), tLoanContract.getAct().getComment(), vars);
			super.dao.updateIsdirectLoan(tLoanContract);
		}else{
			 actTaskService.overTask(tLoanContract.getAct().getTaskId(),tLoanContract.getAct().getProcInsId(),tLoanContract.getAct().getComment());
			 tLoanContract.setStatus(Cons.LoanContractStatus.ENDED);//业务申请失败 
			 
			 if(StringUtils.isNotBlank(tLoanContract.getType()) && "1".equals(tLoanContract.getType())){
			   TMsgSwitch tMsgSwitch=tMsgSwitchService.checkMsgSwitch(Cons.MsgBusinessType.MAKE_LOAN_SUCCESS);
			   if(tMsgSwitch!=null){
				   if(tMsgSwitch.getLetterStatus().equals("1")){
					   String title = Cons.letterDForm.LETTER_LOANAUDITFAIL_TITILE;
					   Map<String, String> model = com.google.common.collect.Maps.newHashMap();
					   model.put("time",DateUtils.formatDate(tLoanContract.getApplyDate()));
					   String content = FreeMarkers.renderString(Cons.letterDForm.LETTER_LOANAUDITFAIL_CONTENT, model);
					   Map<String, String> letterMap=new HashMap<String, String>();
					   letterMap.put("title", title);
					   letterMap.put("msgContent", content);
					   letterMap.put("sendUserId", tLoanContract.getAuthUserId());
					   letterMap.put("readUserId","'"+tLoanContract.getBuyAuthId()+"'");
					   letterMap.put("type",Cons.letterDForm.LETTER_MAKELOANSUCCESS);
					   letterMap.put("name",title);
					   Map<String,Object> lmap=systemService.addLetter(letterMap);
				   } 
				   if(tMsgSwitch.getMarketStatus().equals("1")){
					    AuthenticationUser buyaAuthUser=authenticatorDao.get(tLoanContract.getBuyAuthId());
						String mobile="";
		    			if(buyaAuthUser!=null){
		    				mobile=buyaAuthUser.getMobile();
		    			}
		    			if(StringUtils.isNotBlank(mobile)){
		    				Map<String, String> msgmodel = com.google.common.collect.Maps.newHashMap();
		        			msgmodel.put("time",DateUtils.formatDate(tLoanContract.getApplyDate()));
		        			String msgtext = FreeMarkers.renderString(Cons.messageDForm.MESSAGE_LOANAUDITFAIL_CONTENT, msgmodel);
		        			Map<String,Object> lmap=systemService.addMarket(mobile,msgtext);
		
		    			}
				   }
				 }
		 
			   }
			   		 
		}
		ProcessInstance pi = runtimeService.createProcessInstanceQuery()
				.processInstanceId(tLoanContract.getAct().getProcInsId()).singleResult();
		if (pi != null || tLoanContract.getAct().getFlag().equals("stop")) {
			this.save(tLoanContract);
		}
		
	}

	/** 
	 * @Description 保存展期合同，并启动流程、执行第一个任务（提交申请）
	 * @param tLoanContract	合同对象
	 * @author zzm 
	 * @date 2016-4-13 上午11:02:28  
	 */
	@Transactional(readOnly = false)
	public void extendSave(TLoanContract tLoanContract) {
		String tempId = null;//新增是临时设置的id
		if(StringUtils.startsWith(tLoanContract.getId(),"new_")){
			//新增，且有临时设置的id时
			tempId = tLoanContract.getId();
			tLoanContract.setId(null);
		}
		if(StringUtils.isBlank(tLoanContract.getId())){
			tLoanContract.setStatus(Cons.LoanContractStatus.ET_TO_APPROVE);
			if(tLoanContract.getType() == null){
				tLoanContract.setType(Cons.LoanOrderType.TYPE_B);
			}
			super.save(tLoanContract);
			//原合同状态改为“已展期”
			TLoanContract parent = tLoanContract.getParent();
			//parent.setStatus(Cons.LoanContractStatus.EXTENDED);
			parent.setStatus(Cons.LoanContractStatus.ET_TO_APPROVE);
			this.updateStatus(parent);
			
			if(tLoanContract.getLoanType().equals("1")){//1质押2抵押3信用4保证
				PledgeContract pc=new PledgeContract();
				pc.setBusinessId(parent.getId());
				List<PledgeContract> pList=pledgeContractService.findList(pc);
				for(PledgeContract p:pList){
					p.setBusinessId(tLoanContract.getId());
					pledgeContractService.update(p);
				}
				
			}else if(tLoanContract.getLoanType().equals("2")){
				MortgageContract mc=new MortgageContract();
				mc.setBusinessId(parent.getId());
				List<MortgageContract> pList=mortgageContractService.findList(mc);
				for(MortgageContract m:pList){
					m.setBusinessId(tLoanContract.getId());
					mortgageContractService.update(m);
				}
				
			}else if(tLoanContract.getLoanType().equals("4")){
				TGuaranteeContract gc=new TGuaranteeContract();
				gc.setBusinessId(parent.getId());
				List<TGuaranteeContract> gList=tGuaranteeContractService.findList(gc);
				for(TGuaranteeContract g:gList){
					g.setBusinessId(tLoanContract.getId());
					tGuaranteeContractService.update(g);
				}
			}
			
			
		}
		if(StringUtils.isBlank(tLoanContract.getProcInsId())){
			//保存展期还款计划
			List<TRepayPlan> planList = tLoanContract.getRepayPlanList();
			if(planList != null && planList.size() > 0){
				for(TRepayPlan repayPlan : planList){
					repayPlan.setLoanContractId(tLoanContract.getId());
					repayPlan.setStatus(Cons.RepayStatus.NO_PAID);
					tRepayPlanService.save(repayPlan);
				}
			}
			
			if(StringUtils.isNotBlank(tempId)){
				//关联附件
				tContractFilesService.updateFileTaskId(tempId,tLoanContract.getId());
			}
			// 启动并提交流程
			String procInsId = actTaskService.startProcess(tLoanContract.getAct().getProcDefKey(), "t_loan_contract", tLoanContract.getId(), tLoanContract.getContractNumber());
			if(StringUtils.isNotBlank(procInsId))
				actTaskService.completeFirstTask(procInsId, tLoanContract.getAct().getComment(), 
					tLoanContract.getContractNumber(), tLoanContract.getAct().getVars().getVariableMap());
		}else{
			if(!"-1".equals(tLoanContract.getAct().getFlag())){//非终止操作
				// 设置意见
				tLoanContract.getAct().setComment(("1".equals(tLoanContract.getAct().getFlag())?"[同意] ":"[驳回] ")+tLoanContract.getAct().getComment());
				//执行任务
				actTaskService.complete(tLoanContract.getAct().getTaskId(), tLoanContract.getAct().getProcInsId(), 
						tLoanContract.getAct().getComment(), tLoanContract.getAct().getVars().getVariableMap());
			}else{
				 actTaskService.overTask(tLoanContract.getAct().getTaskId(),tLoanContract.getAct().getProcInsId(),tLoanContract.getAct().getComment());
				 tLoanContract.setStatus(Cons.LoanContractStatus.EXTEND_END);
				 //展期失败，原贷款合同从“已展期”变为“未结清”
				 tLoanContract.getParent().setStatus(Cons.LoanContractStatus.UN_CLEARED);
				 this.updateStatus(tLoanContract.getParent());
			}
			
		}
		ProcessInstance pi = runtimeService.createProcessInstanceQuery()
				.processInstanceId(tLoanContract.getAct().getProcInsId()).singleResult();
		if (pi != null || tLoanContract.getAct().getFlag().equals("-1")) {
			super.save(tLoanContract);
		}
		
		
	}

	public List<?> getExamineList(TLoanContract tLoanContract) {
		
		List<?> PROC_INST_ID_s = Db.query("select PROC_INST_ID_ from ACT_HI_PROCINST where BUSINESS_KEY_ like '%"+tLoanContract.getId()+"'");
		String str_ = "0";
		if(PROC_INST_ID_s.size()>0){
			String str = PROC_INST_ID_s.toString();
			String str_1 = str.replaceAll("\\s*", "");
			 str_  = str_1.substring(1, str_1.length()-1).replace(",","','");
		}
		return Db.query("select p.NAME_,MESSAGE_,ASSIGNEE_,START_TIME_,END_TIME_,p.KEY_ from "
				+ "ACT_HI_COMMENT c LEFT JOIN "
				+ "ACT_HI_TASKINST t on c.TASK_ID_ = t.ID_ LEFT JOIN "
				+ "ACT_RE_PROCDEF p on t.PROC_DEF_ID_ = p.ID_ "
				+ "where c.PROC_INST_ID_ in ('"+str_+"') ORDER BY p.NAME_,TIME_;");
	
	}
	
	/**
	 * @Description 获取逾期业务
	 * @param page
	 * @param tLoanContract
	 * @return
	 * @author zzm
	 * @date 2016-6-8 下午2:19:03  
	 */
	public Page<TLoanContract> findOverduePage(Page<TLoanContract> page, TLoanContract tLoanContract) {
		tLoanContract.setStatus(Cons.LoanContractStatus.UN_CLEARED);//逾期业务的状态只可能是‘未结清’
		tLoanContract.setProductId((String)UserUtils.getCache(UserUtils.CACHE_SYSCODE));
		tLoanContract.setPage(page);
		tLoanContract.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser().getCurrentUser(), "o", "u"));
		page.setList(dao.findOverdueList(tLoanContract));
		return page;
	}

	/**
	 * @Description 获取未贷前调查的合同
	 * @param tLoanContract
	 * @return
	 * @author zzm
	 * @date 2016-6-13 下午3:16:54  
	 */
	public List<TLoanContract> findPreLoanIList(TLoanContract tLoanContract) {
		return this.dao.findPreLoanIList(tLoanContract);
	}
	

	public String buildContractNumber(TLoanContract tLoanContract) {
		if(StringUtils.isEmpty(tLoanContract.getOrganId())){
			User currentUser = UserUtils.getUser();//当前登录人
			return buildContractNumber(tLoanContract, currentUser.getCompany().getId());
		}
		return buildContractNumber(tLoanContract, tLoanContract.getOrganId());
	}
	public String buildContractNumber(TLoanContract tLoanContract, String organId) {
		Integer max_number =  null;
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy");  
		Date date=tLoanContract.getApplyDate();  
		String year=sdf.format(date);  
		
		if(StringUtils.isNotEmpty(organId)){
			//max_number =  querymaxNumber(organId);
			max_number =  querymaxNumber(organId,year);//#3346---lzj合同编号
		}else{
			User currentUser = UserUtils.getUser();//当前登录人
			max_number =  querymaxNumber(currentUser);
		}
		max_number = (max_number==null)?0:max_number;
		//tLoanContract.setMaxNumber((max_number+1)+"");//old #2899
		tLoanContract.setMaxNumber(String.format("%04d",(max_number+1)));//changed #2899
		
		String contractNumber = querycontractNumber(organId);
		if(contractNumber.contains("year")){
			//组装当前时间产生一个合同号
			//Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00")); //获取东八区时间
			//Integer year = c.get(Calendar.YEAR);    //获取年
			contractNumber = contractNumber.replace("year", year+""); //替换年份
			contractNumber = contractNumber.replace("number", tLoanContract.getMaxNumber()); 
		}else {
			contractNumber = contractNumber.replace("number",tLoanContract.getMaxNumber()); 
		}
		return contractNumber;
	}

	/**
	 * @Description 根据订单号获取合同
	 * @param wtypeId
	 * @return
	 */
	public TLoanContract getByWtypeId(String wtypeId) {
		return dao.getByWtypeId(wtypeId);
	}

	public int getdizhiListStatus(TLoanContract entity,String type) {
		
		    //count返回时为1 代表业务提交申请没添加抵质押保证信息，不让提交
		    //count返回时为2 代表贷款业务放款时抵质押物没收押，不让放款，担保业务时不让出担保函
		    //count返回时为3 代表担保业务不能解保
		
		    //type为1时代表业务提交时判断是否添加了抵质押保证信息
		    //type为2时代表业务提交时判断是否添加了抵质押保证信息
		    //type为3时代表担保业务解报时，还未解押抵质押物，不能解报
		
		    int count = 0;
			
			if(entity.getLoanType().contains("1")){//质押
				PledgeContract pledgeContract = new PledgeContract();
				pledgeContract.setBusinessTable("t_loan_contract");
				pledgeContract.setBusinessId(entity.getId());
				List<PledgeContract> pleglist = pledgeContractService.findList(pledgeContract);
				if(pleglist.size()==0 && type.equals("1")){
					count = 1;
				}
				if(pleglist.size()>0 && type.equals("2")){		
					  for (int i = 0; i < pleglist.size(); i++) {
						PledgeContract pleg = pleglist.get(i);
						if(pleg.getStruts()==1){//1是新增状态，还未收押
							count = 2;
						}
					 }
				}
				
				if(pleglist.size()>0 && type.equals("3")){		
					  for (int i = 0; i < pleglist.size(); i++) {
						PledgeContract pleg = pleglist.get(i);
						if(pleg.getStruts()!=6){//6是已解除状态 ,有一项未解除，都不能解除担保
							count = 3;
						}
					 }
				}
			}
			
			if(entity.getLoanType().contains("2")){//抵押
				MortgageContract mortgageContract = new MortgageContract();
				mortgageContract.setBusinessTable("t_loan_contract");
				mortgageContract.setBusinessId(entity.getId());
				List<MortgageContract> mortlist = mortgageContractService.findList(mortgageContract);
				if(mortlist.size()==0 && type.equals("1")){
					count = 1;
				}
				if(mortlist.size()>0 && type.equals("2")){
                    	for (int i = 0; i < mortlist.size(); i++) {
						MortgageContract mort = mortlist.get(i);
						if(mort.getStruts()==1){//1是新增状态，还未收押
							count = 2;
						}
					 }
				}
				if(mortlist.size()>0 && type.equals("3")){
                	for (int i = 0; i < mortlist.size(); i++) {
					MortgageContract mort = mortlist.get(i);
					if(mort.getStruts()!=6){//6是已解除状态 ,有一项未解除，都不能解除担保
						count = 3;
					}
				 }
			}
			}
			
			if(entity.getLoanType().contains("4")){//保证
				TGuaranteeContract tguaranteeContract = new TGuaranteeContract();
				tguaranteeContract.setBusinessTable("t_loan_contract");
				tguaranteeContract.setBusinessId(entity.getId());
				List<TGuaranteeContract> tgyrlist = tGuaranteeContractService.findList(tguaranteeContract);
				if(tgyrlist.size()==0 && type.equals("1")){
					count = 1;
				}
			}
			
			return count;
		}

	/**
	 * @Description 发布理财
	 * @param tLoanContract
	 * @param cat_id
	 * @param installment
	 * @param use 
	 * @throws ServiceException
	 * @author zzm
	 * @date 2016-8-4 下午4:12:44  
	 */
	@Transactional(readOnly = false)
	public void loanTransfer(TLoanContract tLoanContract, String cat_id, String installment, String use) throws ServiceException{
		if(StringUtils.equals(Cons.YES, tLoanContract.getIsTransfered())){
			throw new ServiceException("此业务已转理财！");
		}
		
		if(!StringUtils.equals(Cons.LoanContractStatus.TO_LOAN, tLoanContract.getStatus())
				&&!StringUtils.equals(Cons.LoanContractStatus.UN_CLEARED, tLoanContract.getStatus())){
			throw new ServiceException("只有待放款和未结清的业务才能转理财！");
		}
		JSONObject data = new JSONObject();
		try {
			//借款分类
			data.put("cat_id", cat_id);
			//分期
			data.put("installment", installment);
			
			//借款人
			if(StringUtils.equals(Cons.CustomerType.CUST_EMPLOYEE, tLoanContract.getCustomerType())){
				TEmployee customer = tEmployeeService.get(tLoanContract.getCustomerId());
				data.put("uid", customer.getWtypeId());
				data.put("user_type", "1");
				data.put("real_name", customer.getName());
				data.put("id_card", customer.getCardNum());
				data.put("mobile", customer.getMobile());
			}else{
				TCompany customer = tCompanyService.get(tLoanContract.getCustomerId());
				data.put("uid", customer.getWtypeId());
				data.put("user_type", "2");
				data.put("real_name", customer.getName());
				data.put("id_card", customer.getCardNum());
				data.put("mobile", customer.getSuretyMobile());
			}
			//贷款产品所属的机构id
			Office organ = UserUtils.getOfficeById(UserUtils.getUser().getCompany().getId());
			if(organ  == null || StringUtils.isBlank(organ.getWtypeId())){
				throw new ServiceException("机构不存在!");
			}
			data.put("agency_id", organ.getWtypeId());
			
			//借款产品id
			if(StringUtils.isNotBlank(tLoanContract.getProductId())){
				TProduct product = tProductService.get(tLoanContract.getProductId());
				data.put("loan_id", product.getWtypeId());
			}
			//合同id
			data.put("contract_id", tLoanContract.getWtypeId());
			//系统区间服务费
			data.put("system_fee", tLoanContract.getServerFee());
			//还款方式
			if(StringUtils.equals("2", tLoanContract.getPayType())){
				data.put("repay_way", "4");
			} else if(StringUtils.equals("3", tLoanContract.getPayType())){
				data.put("repay_way", "2");
			} else if(StringUtils.equals("4", tLoanContract.getPayType())){
				data.put("repay_way", "3");
			}else{
				data.put("repay_way", tLoanContract.getPayType());
			}
			//月总利率
			BigDecimal rate_yy = new BigDecimal(tLoanContract.getLoanRate());
			data.put("monthly_total_rate", rate_yy.divide(BigDecimal.valueOf(12), 2, BigDecimal.ROUND_HALF_UP));
			
			//申请借款金额
			if(StringUtils.equals(Cons.LoanContractStatus.TO_LOAN, tLoanContract.getStatus())){
				//待放款状态：借款金额=合同金额-已放款金额
				//Double totalAmount = tLendingService.countAmount(tLoanContract);
				//data.put("apply_amount", (Double.parseDouble(tLoanContract.getLoanAmount()) -  totalAmount));
				BigDecimal totalAmount = tLendingService.countAmount(tLoanContract);
				data.put("apply_amount", (new BigDecimal(tLoanContract.getLoanAmount()).subtract(totalAmount)) );
			} else if(StringUtils.equals(Cons.LoanContractStatus.UN_CLEARED, tLoanContract.getStatus())){
				//未结清状态：借款金额=需还款本金-已还本金
				TRepayPlan tRepayPlan = new TRepayPlan();
				tRepayPlan.setLoanContractId(tLoanContract.getId());
				double amount = 0;//待还本金
				List<TRepayPlan> planList = tRepayPlanService.findList(tRepayPlan);
				if(planList!=null && planList.size()>0){
					for(TRepayPlan p : planList){
						double principal = p.getPrincipal() == null ? 0 : Double.parseDouble(p.getPrincipal());
						double principalReal = p.getPrincipalReal() == null ? 0 : Double.parseDouble(p.getPrincipalReal());
						amount = amount + principal - principalReal;
					}
				}
				data.put("apply_amount", amount);
			}
			
			data.put("grace_period", tLoanContract.getGracePeriod());
			data.put("grace_period_rate", tLoanContract.getGracePeriodPenalty());
			data.put("overdue_rate", tLoanContract.getLatePenalty());
			data.put("use", use);
			Iterator iter = data.keys();
			StringBuffer sb = new StringBuffer();
			while(iter.hasNext()){
				if(sb.length() > 0){
					sb.append("&");
				}
				String key = (String) iter.next();
				sb.append(key+"="+data.get(key));
			}
			String result = InterfaceUtil.sendPost(Cons.Ips.IP_W_ADDLOANORDER, sb.toString());
			JSONObject j = new JSONObject(result);
			logger.info("result = "+result);
			if(j.optInt("code",1) == 0){
				tLoanContract.setTransferId(j.optString("data",""));
				tLoanContract.setIsTransfered(Global.YES);//设为已转理财
				super.save(tLoanContract);
			}else{
				throw new ServiceException("失败："+j.optString("msg",""));
			}
		} catch (JSONException e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
		
	}

	public Boolean checkWorkStatu(TEmployee tEmployee) {
		Boolean flag=true;
		TLoanContract tLoanContract=new TLoanContract();
		tLoanContract.setCustomerId(tEmployee.getId());
		List<TLoanContract> list=dao.findLoanStatusList(tLoanContract);
		if(list.size()>=1){
			flag=false;//存在业务
		}
		return flag;
	}

	public List<?> getFiveLevellist(FiveLevel fiveLevel) {
		List<?> PROC_INST_ID_s = Db.query("select PROC_INST_ID_ from ACT_HI_PROCINST where BUSINESS_KEY_ like '%"+fiveLevel.getId()+"'");
		String str_ = "0";
		if(PROC_INST_ID_s.size()>0){
			String str = PROC_INST_ID_s.toString();
			String str_1 = str.replaceAll("\\s*", "");
			 str_  = str_1.substring(1, str_1.length()-1).replace(",","','");
		}
		return Db.query("select p.NAME_,MESSAGE_,ASSIGNEE_,START_TIME_,END_TIME_,p.KEY_ from "
				+ "ACT_HI_COMMENT c LEFT JOIN "
				+ "ACT_HI_TASKINST t on c.TASK_ID_ = t.ID_ LEFT JOIN "
				+ "ACT_RE_PROCDEF p on t.PROC_DEF_ID_ = p.ID_ "
				+ "where c.PROC_INST_ID_ in ('"+str_+"') ORDER BY p.NAME_,TIME_;");
	}
	
	
	
	/**
	 * 保存贷前变更
	 * 
	 * @param contract
	 * @param repayPlan
	 * @param fromUser
	 * @param amount
	 * @param counts
	 * 
	 */
	@Transactional(readOnly = false)
	public void saveWork(TLoanContract tLoanContract, User fromUser, String amount) {
		TLoanContractBak lcbak = new TLoanContractBak();
		lcbak = (TLoanContractBak) PropertiesUtil.copyProperties(tLoanContract, lcbak, "loanContract,id,repayPlanList");
		// 设置原合同id
		lcbak.setSouceContract(tLoanContract);
		lcbak.setLoanAmount(amount);
		//lcbak.setPayType(payType);
		//lcbak.setInsertTime(new Date());
		//lcbak.setUser(fromUser);
		lcbak.setCustomerName(tLoanContract.getCustomerName());
		lcbak.setPayDay(tLoanContract.getPayDay());
		lcbak.setPayPrincipalDate(tLoanContract.getPayPrincipalDate());
		// status;//变更合同状态1待审批（当前申请变更的备份合同） 2通过 3未通过（历史变更合同）
		lcbak.setDataStatus("1");		

		// 保存业务变更到备份合同表
		lcbak.preInsert();
		loanContractBakDao.insert(lcbak);

	/*
		// 保存还款计划
		if (repayPlan != null) {
			String[] numArray = counts.split(",");
			String[] endDateArray = repayPlan.getEndDate().split(",");
			String[] moneyArray = repayPlan.getPrincipal().toString().split(",");
			String[] lixiArray = repayPlan.getInterest().toString().split(",");
			String[] startDateArray = repayPlan.getStartDate().toString().split(",");
			String[] accountDateArray = repayPlan.getAccountDate().toString().split(",");
			for (int i = 0; i < endDateArray.length; i++) {
				RepayPlanBak rPlan = new RepayPlanBak();
				rPlan.setNum(Integer.parseInt(numArray[i].trim()));				
				rPlan.setEndDate(endDateArray[i].trim());
				rPlan.setPrincipal(moneyArray[i].trim());
				rPlan.setInterest(lixiArray[i].trim());
				rPlan.setStatus(1);
				rPlan.setLoanContract(lcbak);
				rPlan.setCsNum(0);
				rPlan.setStartDate(startDateArray[i].trim());
				rPlan.setAccountDate(accountDateArray[i].trim());
				rPlan.setInsertTime(new Date());
				if (Integer.parseInt(numArray[i].trim()) == 1) {
					rPlan.setPayInterestStatus("1");
				}
				loanContractDao.save(rPlan);
			}
		}*/
		
		//保存还款计划
		List<TRepayPlan> planList = tLoanContract.getRepayPlanList();
		if(planList != null && planList.size() > 0){
			//先清除旧的还款计划
			/*TRepayPlan plan = new TRepayPlan();
			plan.setLoanContractId(tLoanContract.getId());
			tRepayPlanService.deletePLWL(plan);
			*/
			for(TRepayPlan rp : planList){
				TRepayPlanBak rpb = new TRepayPlanBak();
				PropertiesUtil.copyProperties(rp, rpb, "id");
				rpb.setLoanContractId(lcbak.getId());
				rpb.setStatus(Cons.RepayStatus.NO_PAID);
				rpb.preInsert();
				rpb.setId(IdGen.uuid());
				repayPlanBakDao.insert(rpb);
			}
		}

		/*// 改变原合同状态为业务变更待审批
		// 设置更新时间
		loanContractDao.executeUpdateByHQL("update TLoanContract t set t.status=17,t.insertTime=? where t.id=?",
				contract.getInsertTime(), contract.getId());*/
		// 启动流程(业务变更的启动流程和审核不在同意页面和方法)
		String procInsId = actTaskService.startProcess(tLoanContract.getAct().getProcDefKey(), "t_loan_contract", tLoanContract.getId(), tLoanContract.getContractNumber());
		if(StringUtils.isNotBlank(procInsId))
		actTaskService.completeFirstTask(procInsId, tLoanContract.getAct().getComment(), 
						tLoanContract.getContractNumber(), null);
		tLoanContract.setStatus(Cons.LoanContractStatus.TO_APPROVE);
		updateStatus(tLoanContract);

		
	}
	
	
	@Transactional(readOnly = false)
	public void saveAudit(TLoanContract tLoanContract) {
		// 设置意见
		tLoanContract.getAct().setComment(("1".equals(tLoanContract.getAct().getFlag())?"[同意] ":"[驳回] ")+tLoanContract.getAct().getComment());
	    if(!tLoanContract.getAct().getFlag().equals("-1")){
	    	Map<String, Object> vars = tLoanContract.getAct().getVars().getVariableMap();
			vars.put("pass",tLoanContract.getAct().getFlag());
			//执行任务
			actTaskService.complete(tLoanContract.getAct().getTaskId(), tLoanContract.getAct().getProcInsId(), 
					tLoanContract.getAct().getComment(), vars);
	    }else{//流程被终止
			actTaskService.overTask(tLoanContract.getAct().getTaskId(),tLoanContract.getAct().getProcInsId(),tLoanContract.getAct().getComment());
			tLoanContract.setStatus(Cons.LoanContractStatus.TO_LOAN);//贷前变更审批不通过，状态仍改为待放款
			//审批不通过干掉已产生的变更还款计划
			Db.update("update t_loan_contract set status = ? where id = ?", Cons.LoanContractStatus.TO_LOAN, tLoanContract.getId());
			TLoanContractBak contractBak = this.getContractBakByContractId(tLoanContract);
			Db.update("update t_loan_contract_bak set data_status = ? where id = ?", "3", contractBak.getId());
		} 
	    ProcessInstance pi = runtimeService.createProcessInstanceQuery()
				.processInstanceId(tLoanContract.getAct().getProcInsId()).singleResult();
		if (pi != null || tLoanContract.getAct().getFlag().equals("0")) {
			updateStatus(tLoanContract);
		}
		
	}
	
	
	/**
	 * 贷前变更审批通过，更新合同贷款金额、状态
	 */
	@Transactional(readOnly = false)
	public void updateWork(TLoanContract contract, TLoanContractBak contractBak, User user, List<TRepayPlan> planList,
			List<TRepayPlanBak> planListBak) {
		BigDecimal amount1 = new BigDecimal(contract.getLoanAmount());
		BigDecimal amount2 = new BigDecimal(contractBak.getLoanAmount());
		String temp = contractBak.getPayType();
		contractBak.setPayType(contract.getPayType());
		contract.setPayType(temp);
		contract.setLoanAmount(amount2.toString());
		contractBak.setLoanAmount(amount1.toString());
		contract.setStatus("5"); // 合同状态改为待放款
		contractBak.setDataStatus("2"); // 合同状态改为通过
		String payDay = contract.getPayDay();
		Date payPrincipalDate = contract.getPayPrincipalDate();
		String payDayBak = contractBak.getPayDay();
		Date payPrincipalDateBak = contractBak.getPayPrincipalDate();
		contract.setPayDay(payDayBak);
		contract.setPayPrincipalDate(payPrincipalDateBak);
		contractBak.setPayDay(payDay);
		contractBak.setPayPrincipalDate(payPrincipalDate);
		TRepayPlan tempRp = new TRepayPlan();
		tempRp.setLoanContractId(contract.getId());
		repayPlanDao.deletePLWL(tempRp);
		TRepayPlanBak tempRPB = new TRepayPlanBak();
		tempRPB.setLoanContractId(contractBak.getId());
		repayPlanBakDao.deletePLWL(tempRPB);
		for (int i = 0; i < planListBak.size(); i++) {
			TRepayPlanBak rPlanBak = planListBak.get(i);
			TRepayPlan rPlan = new TRepayPlan();
			PropertiesUtil.copyProperties(rPlanBak, rPlan, "id");
			rPlan.setLoanContract(contract);
			//rpb.setStatus(Cons.RepayStatus.NO_PAID);
			rPlan.preInsert();
			rPlan.setId(IdGen.uuid());
			//repayPlanBakDao.insert(rPlan);
		/*	rPlan.setNum(rPlanBak.getNum());
			rPlan.setCsNum(rPlanBak.getCsNum());
			rPlan.setEndDate(rPlanBak.getEndDate());
			rPlan.setStartDate(rPlanBak.getStartDate());
			rPlan.setAccountDate(rPlanBak.getAccountDate());
			rPlan.setPrincipal(rPlanBak.getPrincipal());
			rPlan.setInterest(rPlanBak.getInterest());
			rPlan.setStatus(Cons.RepayStatus.NO_PAID);
			rPlan.setPayInterestStatus(Cons.RepayStatus.NO_PAID);
			rPlan.setLoanContract(contract);*/
			tRepayPlanService.save(rPlan);
		}
		for (int i = 0; i < planList.size(); i++) {
			TRepayPlan rPlan = planList.get(i);
			TRepayPlanBak rPlanBak = new TRepayPlanBak();
			PropertiesUtil.copyProperties(rPlan, rPlanBak, "id");
			rPlanBak.setLoanContractBak(contractBak);
			rPlanBak.setLoanContractId(contractBak.getId());
			//rpb.setStatus(Cons.RepayStatus.NO_PAID);
			rPlanBak.preInsert();
			rPlanBak.setId(IdGen.uuid());
		/*	rPlanBak.setNum(rPlan.getNum());
			rPlanBak.setEndDate(rPlan.getEndDate());
			rPlanBak.setPrincipal(rPlan.getPrincipal());
			rPlanBak.setInterest(rPlan.getInterest());
			rPlanBak.setStatus(Cons.RepayStatus.NO_PAID);
			rPlanBak.setPayInterestStatus(Cons.RepayStatus.NO_PAID);
			rPlanBak.setLoanContractBak(contractBak);
			rPlanBak.setLoanContractId(contractBak.getId());*/
			repayPlanBakDao.insert(rPlanBak);
		}
		loanContractDao.update(contract);
		loanContractBakDao.update(contractBak);	
	}

	/**
	 * 放款审核审批保存
	 * @param tLoopLoan
	 */
	@SuppressWarnings("unused")
	@Transactional(readOnly = false)
	public void makeLoansSave(TLoanContract tLoanContract) {
		// 设置意见
		tLoanContract.getAct().setComment(("yes".equals(tLoanContract.getAct().getFlag())?"[同意] ":"[驳回] ")+tLoanContract.getAct().getComment());
		
		tLoanContract.preUpdate();
		
		// 对不同环节的业务逻辑进行操作
		String taskDefKey = tLoanContract.getAct().getTaskDefKey();
		
		// 提交流程任务
		Map<String, Object> vars = Maps.newHashMap();
		if(tLoanContract.getAct().getFlag().equals("yes")){
			vars.put("pass", "1");
		}else if(tLoanContract.getAct().getFlag().equals("no")){
			vars.put("pass", "0");
		}else if(tLoanContract.getAct().getFlag().equals("stop")){
			vars.put("pass", "-1");
		}
		if(!tLoanContract.getAct().getFlag().equals("stop")){//非终止操作
			actTaskService.complete(tLoanContract.getAct().getTaskId(), tLoanContract.getAct().getProcInsId(), tLoanContract.getAct().getComment(), vars);
		}else{//终止任务
			 actTaskService.overTask(tLoanContract.getAct().getTaskId(),tLoanContract.getAct().getProcInsId(),tLoanContract.getAct().getComment());
			 tLoanContract.setStatus(Cons.LoanContractStatus.ENDED);//放款审批不通过
		}
		ProcessInstance pi = runtimeService.createProcessInstanceQuery()
				.processInstanceId(tLoanContract.getAct().getProcInsId()).singleResult();
		if (pi != null || tLoanContract.getAct().getFlag().equals("stop")) {
			this.save(tLoanContract);
		}
		
	}
	
	
	/**
	 * 转换数据  规定的格式推送到监管系统
	 * @param tLoanContract
	 * @return
	 */
	public String sendData(TLoanContract tLoanContract){	
		List<TGuaranteeContract> list = new ArrayList<TGuaranteeContract>();
		String loanQiXian = tLoanContract.getPeriodType(); //贷款期限
		String rateType = ""; //利率类型
		String loanType = ""; //贷款类型
		String flag = "2";  //是否多户联合贷款
		String haikuanType = ""; //还款类型
		String guarantor = ""; //担保方
		if("年".equals(tLoanContract.getLoanRateType())){
			//loanQiXian = "1"; //月
			rateType = "1";
		}    		
		if("日".equals(tLoanContract.getLoanRateType())){
			rateType = "2";
			//loanQiXian = "2";
		}
		if("月".equals(tLoanContract.getLoanRateType())){
			rateType = "3";
			//loanQiXian = "1";
		}  		
		
		//判断贷款类型
		if(tLoanContract.getLoanType() != null ){			
		 List strList = new ArrayList();
		 if(tLoanContract.getLoanType().contains("3")){
			 strList.add("1");
		 }
		 if(tLoanContract.getLoanType().contains("4")){
			 strList.add("2");
			 flag = "1";
			 TGuaranteeContract con = new TGuaranteeContract();
			 con.setBusinessId(tLoanContract.getId());
			 list = guaranteeContractDao.findListBybusinessId(con);
			 if(list != null && list.size() > 0){				
				for(int i=0;i<list.size();i++){
					if(i != list.size()-1){
						guarantor += list.get(i).getGuarantorName()+";";
					}else{
						guarantor += list.get(i).getGuarantorName();
					}
				}
			 }
			 
		 }
		 if(tLoanContract.getLoanType().contains("2")){
			 strList.add("3");
		 }
		 if(tLoanContract.getLoanType().contains("1")){
			 strList.add("4");
		 }
		 if(strList !=null && strList.size() > 0){
			for(int i=0;i<strList.size();i++){
				if(i==0 || i == strList.size()-1){
					loanType =loanType + strList.get(i);
				}else{
					loanType =loanType+"," + strList.get(i)+",";
				}
			} 
		 }
	   }
		
		
		//还款方式	
		if("1".equals(tLoanContract.getPayType())){
			haikuanType = "1";
		}else if("2".equals(tLoanContract.getPayType())){
			haikuanType = "2";
		}else if("3".equals(tLoanContract.getPayType())){
			haikuanType= "4";
		}else if("4".equals(tLoanContract.getPayType())){
			haikuanType = "4";
		}else if("5".equals(tLoanContract.getPayType())){
			haikuanType = "4";
		}  			
		
		
		SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyy-MM-dd");
		String signDate = "";
		if(tLoanContract.getApplyDate() != null){
			signDate = formatter.format(tLoanContract.getApplyDate());
		}
		
		String payPrincipalDate = "";
		if(tLoanContract.getPayPrincipalDate() != null){
			payPrincipalDate = formatter.format(tLoanContract.getPayPrincipalDate());
		}	
		
		String customerId = "";
		if("1".equals(tLoanContract.getCustomerType())){
			customerId = "1,"+tLoanContract.getCustomerId();
		}else if("2".equals(tLoanContract.getCustomerType())){
			customerId = "2,"+tLoanContract.getCustomerId();
		}
		
    	if("1".equals(tLoanContract.getDelFlag())){ //删除数据    		
    		return "D" + "|"+tLoanContract.getCompanyName()+"|"+tLoanContract.getContractNumber()+"|"+tLoanContract.getContractNumber() + "|"
    				+ customerId + "|" +guarantor+"|"+tLoanContract.getLoanAmount() + "|" +loanQiXian+"|" +tLoanContract.getLoanPeriod() +"|" +signDate + "|" + payPrincipalDate +"|" + rateType +"|" + tLoanContract.getLoanRate() + "|" 
    				+ "3" + "|" + "13" + "|" + loanType + "|" + flag + "|" + haikuanType +"|" + signDate +"|" +"" + "|" + tLoanContract.getId();   		
    		
    	}else{ //新增修改数据    		
    		if("0".equals(tLoanContract.getPushStatus())){
    			return "A" + "|"+tLoanContract.getCompanyName()+"|"+tLoanContract.getContractNumber()+"|"+tLoanContract.getContractNumber() + "|"
        				+ customerId + "|" +guarantor+"|"+tLoanContract.getLoanAmount() + "|" +loanQiXian+"|" +tLoanContract.getLoanPeriod() +"|" +signDate + "|" + payPrincipalDate +"|" + rateType +"|" + tLoanContract.getLoanRate() + "|" 
        				+ "3" + "|" + "13" + "|" + loanType + "|" + flag + "|" + haikuanType +"|" + signDate +"|" +"" + "|" + tLoanContract.getId();    		  
    		}else{
    			return "U" + "|"+tLoanContract.getCompanyName()+"|"+tLoanContract.getContractNumber()+"|"+tLoanContract.getContractNumber() + "|"
        				+ customerId + "|" +guarantor+"|"+tLoanContract.getLoanAmount() + "|" +loanQiXian+"|" +tLoanContract.getLoanPeriod() +"|" +signDate + "|" + payPrincipalDate +"|" + rateType +"|" + tLoanContract.getLoanRate() + "|" 
        				+ "3" + "|" + "13" + "|" + loanType + "|" + flag + "|" + haikuanType +"|" + signDate +"|" +"" + "|" + tLoanContract.getId();    		
    		}
    	}    	
	}	
	
	
	
	/**
	 * 转换数据  规定的格式推送到监管系统
	 * @param tLoanContract
	 * @return
	 */
	public String sendDbData(TLoanContract tLoanContract){	
		//List<TGuaranteeContract> list = new ArrayList<TGuaranteeContract>();
		String rateType = ""; //费率类型
		if("年".equals(tLoanContract.getLoanRateType())){
			//loanQiXian = "1"; //月
			rateType = "1";
		}    		
		if("日".equals(tLoanContract.getLoanRateType())){
			rateType = "2";
			//loanQiXian = "2";
		}
		if("月".equals(tLoanContract.getLoanRateType())){
			rateType = "3";
			//loanQiXian = "1";
		}  		
		
		
		/*String loanQiXian = "";
	
	   
		
		//判断担保期限
		if(tLoanContract.getPeriodType() != null && "3".equals(tLoanContract.getPeriodType())){			
			if(tLoanContract.getLoanPeriod() != null && !"".equals(tLoanContract.getLoanPeriod())){
				int qixian = Integer.valueOf(tLoanContract.getLoanPeriod());
				if(qixian>=0 && qixian <=360){
					loanQiXian = "1年";
				}else if(qixian>360 && qixian<=720){
					loanQiXian = "2年";
				}else if(qixian>720){
					loanQiXian = "3年";
				}
			}
		}else if(tLoanContract.getPeriodType() != null && "2".equals(tLoanContract.getPeriodType())){			
			if(tLoanContract.getLoanPeriod() != null && !"".equals(tLoanContract.getLoanPeriod())){
				int qixian = Integer.valueOf(tLoanContract.getLoanPeriod());
				if(qixian>=0 && qixian <=12){
					loanQiXian = "1年";
				}else if(qixian>=12 && qixian<=24){
					loanQiXian = "2年";
				}else if(qixian>24){
					loanQiXian = "3年";
				}
			}
		}else if(tLoanContract.getPeriodType() != null && "1".equals(tLoanContract.getPeriodType())){			
			if(tLoanContract.getLoanPeriod() != null && !"".equals(tLoanContract.getLoanPeriod())){
				int qixian = Integer.valueOf(tLoanContract.getLoanPeriod());
				if(qixian>=0 && qixian <=1){
					loanQiXian = "1年";
				}else if(qixian>1 && qixian<=2){
					loanQiXian = "2年";
				}else if(qixian>2){
					loanQiXian = "3年";
				}
			}
		}*/
		
		SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyy-MM-dd");		
		String applyDate = "";
		if(tLoanContract.getApplyDate() != null && !"".equals(tLoanContract.getApplyDate())){
			applyDate = formatter.format(tLoanContract.getApplyDate());
		}	
		
		String customerId = "";
		if("1".equals(tLoanContract.getCustomerType())){
			customerId = "1,"+tLoanContract.getCustomerId();
		}else if("2".equals(tLoanContract.getCustomerType())){
			customerId = "2,"+tLoanContract.getCustomerId();
		}
		
		StringBuffer sbStringBuffer = new StringBuffer();
		if("1".equals(tLoanContract.getDelFlag())){ //删除数据    		
			sbStringBuffer.append("D" +"|");  	
    	}else{ //新增修改数据    		
    		if("0".equals(tLoanContract.getPushStatus())){
    			sbStringBuffer.append("A" +"|");       				
    		}else{
    			sbStringBuffer.append("U" +"|"); 
    		}
    	}    	
		
		/*
		 *  字段1	标识符
			字段2	担保公司唯一标示
			字段3	合同编号
			字段4	合同名称
			字段5	借款客户名称ID
			字段6	担保金额(元)
				
			字段7	担保期限类型
			字段8	担保期限
			字段9	费率类型
			字段10	费率（%）
			字段11	合同签订日期
			字段12	评审费(%)
			字段13	服务费(%)
			字段14	违约金(%)
			字段15	反担保方式
			字段16	解除担保时间
			字段17	第三方系统记录ID

说明：担保期限类型：1）月 2）日
      费率类型：    1）年 2）日  3)月
     反担保方式：  1）信用  2）保证  3）抵押   4）质押 
		 */
		
		sbStringBuffer.append(tLoanContract.getCompanyName()+"|" +tLoanContract.getContractNumber()+"|"+ tLoanContract.getContractNumber()+"|"+
				customerId+"|"+tLoanContract.getLoanAmount() +"|"
				+tLoanContract.getPeriodType() +"|"+tLoanContract.getLoanPeriod() +"|"
				+rateType +"|"+tLoanContract.getLoanRate() +"|"
				+applyDate +"|"+tLoanContract.getMangeFee() +"|"
				+tLoanContract.getServerFee() +"|"+tLoanContract.getLateFee() +"|"
				+tLoanContract.getLoanType() +"|"+"" +"|"
				+tLoanContract.getId());
    	return sbStringBuffer.toString();
	}	
	
	
	@Transactional(readOnly = false)
	public StringBuffer updateGetListByscanFlagData(String fileName, Long informFilingType) {
		StringBuffer sheepData = new StringBuffer();
		TLoanContract queryContract = new TLoanContract();
		queryContract.setScanFlag("0");
		List<TLoanContract> contractList = dao.findListByScanFlag(queryContract);
		List<TLoanContract> successList = new ArrayList<TLoanContract>();
		if (contractList != null && contractList.size() > 0) {			
			// 获取数据
			for (TLoanContract temp : contractList) {
				sheepData.append(this.sendData(temp));
				sheepData.append("\r\n");
				successList.add(temp);
			}
			// 更改已经做了扫描处理的标示
			/*for (TLoanContract temp : contractList) {
				temp.setScanFlag("1");
				dao.update(temp);
				
			}*/
			for (TLoanContract temp : successList) {
				temp.setScanFlag("1");
				temp.setPushStatus("1");
				temp.preUpdate();
				dao.updateByPushStatus(temp);
			}
		}
		return sheepData;
	}
	
	
	@Transactional(readOnly = false)
	public StringBuffer updateGetListDbByscanFlagData(String fileName, Long informFilingType) {
		StringBuffer sheepData = new StringBuffer();
		TLoanContract queryContract = new TLoanContract();
		queryContract.setScanFlag("0");
		List<TLoanContract> contractList = dao.findListDbByScanFlag(queryContract);
		List<TLoanContract> successList = new ArrayList<TLoanContract>();
		if (contractList != null && contractList.size() > 0) {			
			// 获取数据
			for (TLoanContract temp : contractList) {
				sheepData.append(this.sendDbData(temp));
				sheepData.append("\r\n");
				successList.add(temp);
			}
			// 更改已经做了扫描处理的标示
			/*for (TLoanContract temp : contractList) {
				temp.setScanFlag("1");
				dao.update(temp);
			}*/
			for (TLoanContract temp : successList) {
				temp.setScanFlag("1");
				temp.setPushStatus("1");
				temp.preUpdate();
				dao.updateByPushStatus(temp);
			}
		}
		return sheepData;
	}
	
	
	@Transactional(readOnly = false)
	public void updateReceiptData(List<String> dataList,List<TLoanContract> conList,List<TLoanContract> conList1) {
	
		//对推送状态为0的做处理
		if(conList != null && conList.size() > 0){
			for(TLoanContract temp:conList){				
				if(dataList != null && dataList.size() > 0){
					if(dataList.contains(temp.getId())){ //这条记录存在回执错误
						temp.setScanFlag("0");							
						dao.update(temp);
					}else{ //这条记录推成功						
						temp.setScanFlag("1");	
						temp.setPushStatus("1");
						dao.updateByPushStatus(temp);
					}
				}else{
					temp.setScanFlag("1");	
					temp.setPushStatus("1");
					dao.updateByPushStatus(temp);
				}				
			}
		}
		
		//对推送状态为1的做处理
		if(conList1 != null && conList1.size() > 0){
			for(TLoanContract temp:conList1){				
				if(dataList != null && dataList.size() > 0){
					if(dataList.contains(temp.getId())){ //这条记录存在回执错误
						temp.setScanFlag("0");							
						dao.update(temp);
					}else{ //这条记录推成功						
						temp.setScanFlag("1");	
						dao.updateByPushStatus(temp);
					}
				}else{
					temp.setScanFlag("1");	
					dao.updateByPushStatus(temp);
				}				
			}
		}		
		
	}
	
	@Transactional(readOnly = false)
	public void updateReceiptDbData(List<String> dataList,List<TLoanContract> conList,List<TLoanContract> conList1) {
		//对推送状态为0的做处理
		if(conList != null && conList.size() > 0){
			for(TLoanContract temp:conList){				
				if(dataList != null && dataList.size() > 0){
					if(dataList.contains(temp.getId())){ //这条记录存在回执错误
						temp.setScanFlag("0");							
						dao.update(temp);
					}else{ //这条记录推成功						
						temp.setScanFlag("1");	
						temp.setPushStatus("1");
						dao.updateByPushStatus(temp);
					}
				}else{
					temp.setScanFlag("1");	
					temp.setPushStatus("1");
					dao.updateByPushStatus(temp);
				}				
			}
		}
		
		//对推送状态为1的做处理
		if(conList1 != null && conList1.size() > 0){
			for(TLoanContract temp:conList1){				
				if(dataList != null && dataList.size() > 0){
					if(dataList.contains(temp.getId())){ //这条记录存在回执错误
						temp.setScanFlag("0");							
						dao.update(temp);
					}else{ //这条记录推成功						
						temp.setScanFlag("1");	
						dao.updateByPushStatus(temp);
					}
				}else{
					temp.setScanFlag("1");	
					dao.updateByPushStatus(temp);
				}				
			}
		}		
		
	}

	@Transactional(readOnly = false)
	public void updateMonveStatus(TLoanContract contract) {
		dao.updateMonveStatus(contract);
	}

	public Page<TLoanContract> pushMoveContractlist(Page<TLoanContract> page, TLoanContract tLoanContract) {
		//tLoanContract.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser().getCurrentUser(), "o", "u"));
		tLoanContract.setPage(page);
		page.setList(dao.pushMoveContractlist(tLoanContract));
		return page;
	}
	public Page<TLoanContract> pullMoveContractlist(Page<TLoanContract> page,
			TLoanContract tLoanContract) {
		tLoanContract.setPage(page);
		page.setList(dao.pullMoveContractlist(tLoanContract));
		return page;
	}

	public Page<TLoanContract> moveContractlist(Page<TLoanContract> page,
			TLoanContract tLoanContract) {
		tLoanContract.setPage(page);
		page.setList(dao.moveContractlist(tLoanContract));
		return page;
	}
	
	/**
	 * 判断历史上传的excel表业务的数据是否准确
	 * */
	/**
	 * update luoxiaohu 2017-4-11
	 */
	public boolean judgeByContractTE(TEmployeeAndContract tEmployee,List<String> list){
		boolean a = true ;
		if(StringUtils.isBlank(tEmployee.getCustomerType())){
			list.add("客户类型为空");
			a = false;
		}
		if(!tEmployee.getCustomerType().equals("1") && !tEmployee.getCustomerType().equals("2")){
			list.add("客户类型（1.企业/2.个人）");
			a = false;
		}
		if(StringUtils.isBlank(tEmployee.getBorrower())){
			list.add("借款主体为空");
			a = false;
		}
		
		if(!tEmployee.getBorrower().equals("1") && !tEmployee.getBorrower().equals("2") && 
				!tEmployee.getBorrower().equals("3")&& !tEmployee.getBorrower().equals("4")&& 
				!tEmployee.getBorrower().equals("5")&& !tEmployee.getBorrower().equals("6")&& 
				!tEmployee.getBorrower().equals("7")){
			list.add("借款主体(1 :个人贷款, 2 :个体工商户贷款 ,3: 农村专业合作组织贷款 ,4 :微型企业贷款 ,5 :小型企业贷款, 6 :中型及以上企业贷款,7: 其他组织贷款");
			a = false;
		}
		if(StringUtils.isBlank(tEmployee.getIndustryId())){
			list.add( "行业为空");
			a = false;
		}
		if(!"1".equals(tEmployee.getIndustryId()) && !"2".equals(tEmployee.getIndustryId()) && !"3".equals(tEmployee.getIndustryId())
				&& !"4".equals(tEmployee.getIndustryId())&& !"5".equals(tEmployee.getIndustryId())&& !"6".equals(tEmployee.getIndustryId())
				&& !"7".equals(tEmployee.getIndustryId())&& !"8".equals(tEmployee.getIndustryId())&& !"9".equals(tEmployee.getIndustryId())
				&& !"10".equals(tEmployee.getIndustryId())&& !"11".equals(tEmployee.getIndustryId())&& !"12".equals(tEmployee.getIndustryId())
				&& !"99".equals(tEmployee.getIndustryId())){
			list.add( "行业(1 农、林、牧、渔业贷款,2 采矿业贷款,3 制造业贷款,4 电力、燃气及水生产供应业贷款,"
					+ "5 建筑业贷款,6 交通运输、仓储和邮政业贷款,7 信息传输、计算机服务和软件业贷款,8 批发和零售业贷款,9 住宿和餐饮业贷款,"
					+ "10 房地产业贷款,11 租赁和商务服务业贷款,12 居民服务和其他服务业贷款,99 其他贷款");
			a = false;
		}
		/**	
		
		HashSet<Integer> set = new HashSet();
		set.add(1);
		set.add(2);
		set.add(3);
		set.add(4);
		set.add(5);
		set.add(6);
		set.add(7);
		set.add(8);
		set.add(9);
		set.add(10);
		set.add(11);
		set.add(12);
		set.add(99);
		if(!set.contains(tEmployee.getIndustryId())){
		list.add(tEmployee.getCustomerName(), "行业(1 农、林、牧、渔业贷款,2 采矿业贷款,3 制造业贷款,4 电力、燃气及水生产供应业贷款,"
				+ "5 建筑业贷款,6 交通运输、仓储和邮政业贷款,7 信息传输、计算机服务和软件业贷款,8 批发和零售业贷款,9 住宿和餐饮业贷款,"
				+ "10 房地产业贷款,11 租赁和商务服务业贷款,12 居民服务和其他服务业贷款,99 其他贷款");
		a = false;
		}*/
		/*HashSet<String> set01 = new HashSet();
		set01.add("1");
		set01.add("0");
		if(StringUtils.isBlank(tEmployee.getAgriculture())){
			list.add("涉农为空");
			a = false;
		}else if(!set01.contains(tEmployee.getAgriculture())){
			list.add( "是否涉农(是1/否0)");
			a = false;
		}*/
		
		HashSet<String> setDK = new HashSet();
		setDK.add("1");
		setDK.add("2");
		setDK.add("3");
		setDK.add("4");	
		if(StringUtils.isBlank(tEmployee.getLoanType())){
			list.add( "贷款方式为空");
			a = false;
		}
		  String[] chrstr=tEmployee.getLoanType().split(",");
		 //取两个数组的交集
		  boolean b = true;
		  for(int i=0;i<chrstr.length;i++){
			  //包含 1 2 3 4 就说明贷款方式  填写正确(不管  是否用的,还是.)
			  if(setDK.contains(chrstr[i])){
				  b=false;
			  }
			  if(b){
				list.add("贷款方式（1.抵押/2.质押/3.信用/4.保证）");
				a = false;
			}
		  }	
		if( StringUtils.isBlank(tEmployee.getLoanAmount())){
			list.add( "贷款金额为空");
			a = false;
		}
		if( StringUtils.isBlank(tEmployee.getLoanRate())){
			list.add("贷款利率为空");
			a = false;
		}
		if( StringUtils.isBlank(tEmployee.getLoanRateType())){
			list.add("利率类型为空");
			a = false;
		}else if(!tEmployee.getLoanRateType().equals("年") && !"月".equals(tEmployee.getLoanRateType()) 
				&& !"日".equals(tEmployee.getLoanRateType()) && !"季".equals(tEmployee.getLoanRateType())){
			list.add( "利率类型为年  月   日  季");
			a = false;
		}
		if( StringUtils.isBlank(tEmployee.getLoanPeriod())){
			list.add( "贷款期限为空");
			a = false;
		}else if(	(Integer.parseInt(tEmployee.getLoanPeriod()) % 1 )!= 0){
			list.add( "贷款期限只能为整数");
			a = false;
		}
		if( StringUtils.isBlank(tEmployee.getPurposeId())){
			list.add("贷款用途为空");
			a = false;
		}else if(!tEmployee.getPurposeId().equals("1") && !tEmployee.getPurposeId().equals("2") && 
				!tEmployee.getPurposeId().equals("3")&& !tEmployee.getPurposeId().equals("4")&& 
				!tEmployee.getPurposeId().equals("5")&& !tEmployee.getPurposeId().equals("6")&& 
				!tEmployee.getPurposeId().equals("7") && !tEmployee.getPurposeId().equals("8") 
				&& !tEmployee.getPurposeId().equals("99")){
			list.add( "贷款用途(1 个人-资金周转2 个人-个人经营3 个人-综合消费"
					+ "4 企业-固定资产贷款5 企业-流动资金贷款6 企业-并购贷款7 企业-房地产贷款8 企业-项目融资99 其他)");
			a = false;
		}
		if( StringUtils.isBlank(tEmployee.getPayType())){
			list.add("还款方式为空");
			a = false;
		}else if(!tEmployee.getPayType().equals("1") && !tEmployee.getPayType().equals("2") && 
				!tEmployee.getPayType().equals("3")&& !tEmployee.getPayType().equals("4")&& 
				!tEmployee.getPayType().equals("5")){
			list.add("还款方式（ 1.等额本息/2.等额本金/3.按月付息到期还款/4.按期付息到期还款/5.到期一次性还本付息）");
			a = false;
		}
		if( StringUtils.isBlank(tEmployee.getPeriodType())){
			list.add( "还款周期为空");
			a = false;
		}else if(!tEmployee.getPeriodType().equals("1") && !tEmployee.getPeriodType().equals("2") && 
				!tEmployee.getPeriodType().equals("3") && !tEmployee.getPeriodType().equals("4")){
			list.add("还款周期（1: 年,2: 月,3: 日,4: 季）");
			a = false;
		}
		if( StringUtils.isBlank(tEmployee.getIfAdvance())){
			list.add("提前还款为空");
			a = false;
		}else if(!tEmployee.getIfAdvance().equals("0") && !tEmployee.getIfAdvance().equals("1")){
			list.add("是否可提前还款（0.否/1.是）");
			a = false;
		}
		/**
		 * update  验证
		 */
		if( null==tEmployee.getPayPrincipalDate()){
			list.add( "还本金日期（最后一个还款日）为空");
			a = false;
		}
		/**
		 * old   验证租户id
		 */
		/*if( null==tEmployee.getOrganId){
			list.add("租户ID为空");
			a = false;
		}*/
		
		
		
		if( StringUtils.isBlank(tEmployee.getMangeFee())){
			list.add( "管理费为空");
			a = false;
		}if( StringUtils.isBlank(tEmployee.getGracePeriod())){
			list.add("宽限期为空");
			a = false;
		}if( StringUtils.isBlank(tEmployee.getServerFee())){
			list.add("前期服务费为空");
			a = false;
		}if( StringUtils.isBlank(tEmployee.getLateFee()) ){
			list.add( "违约金为空");
			a = false;
		}if( StringUtils.isBlank(tEmployee.getAdvanceDamages())){
			list.add("提前还款违约金比例为空");
			a = false;
		}
		return a;
	}
	
	
	/**
	 * 根据历史excel表，处理数据
	 * */
	//根据合同编号，查询合同信息
	public TLoanContract contractByNumber(TEmployeeAndContract tEmployeeAndContract){
		return dao.contractByNumber(tEmployeeAndContract);
	}
	
	public void updateByExcel(TLoanContract tLoanContract) {
		//判断是否是临时ID add by srf
		String tempID = null;
		if(StringUtils.isNotBlank(tLoanContract.getId()) && tLoanContract.getId().startsWith("tmp_")){
			tempID = tLoanContract.getId();
			tLoanContract.setId(null);
			tLoanContract.setFiveLevel(Cons.FiveLevelStatus.NORMAL_N);
		}
		
		//修改，先把还款计划删除，在重新添加。
		if(StringUtils.isNotBlank(tLoanContract.getId())){
			Db.update("delete from t_repay_plan where loan_contract_id = ?",tLoanContract.getId());
		}
		
		TCompany company = tCompanyService.get(tLoanContract.getCustomerId());
		TEmployee employee = tEmployeeService.get(tLoanContract.getCustomerId());
		if(company != null){
			tLoanContract.setCustomerType(Cons.CustomerType.CUST_COMPANY);
			tLoanContract.setCustomerName(company.getName());
		}else if(employee != null){
			tLoanContract.setCustomerType(Cons.CustomerType.CUST_EMPLOYEE);
			tLoanContract.setCustomerName(employee.getName());
		}
		
		TProduct product = tProductService.get(tLoanContract.getProductId());
		if(StringUtils.isBlank(tLoanContract.getId()) && StringUtils.isNotBlank(product.getReleasesObje()) && !product.getReleasesObje().contains(tLoanContract.getCustomerType())){
			throw new ServiceException("产品指定发行对象为["+product.getReleasesObje().replace(Cons.CustomerType.CUST_COMPANY, "企业").replace(Cons.CustomerType.CUST_EMPLOYEE, "个人")+"]");
		}
		
		if ((tLoanContract != null) && StringUtils.isBlank(tLoanContract.getStatus())){//新增的合同状态为空，插入数据库时设定为1：新增，为提交审核
			tLoanContract.setStatus(Cons.LoanContractStatus.TO_REVIEW);
		}
		//处理每期还款日1:固定某个日子，2：根据放款日期，获取日
		if(tLoanContract.getPayDayType() != null && "2".equals(tLoanContract.getPayDayType())){
			Calendar cal = Calendar.getInstance();
			cal.setTime(tLoanContract.getLoanDate());
			tLoanContract.setPayDay(cal.get(Calendar.DATE)+"");
		}
		if(tLoanContract.getType() == null){
			tLoanContract.setType(Cons.LoanOrderType.TYPE_B);
		}
		super.dao.update(tLoanContract);
		
		//add by srf 更新对应表中的tempID
		/*if(StringUtils.isNotBlank(tempID)){
			//调整关联附件表
			tContractFilesService.updateFileTaskId(tempID, tLoanContract.getId());
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("businessId", tLoanContract.getId());
			map.put("id", tempID);
			
			//1、质押；2、抵押；3、信用；4、保证
			//调整关联抵押信息表
			map.put("table", "t_mortgage_contract");
			super.dao.updateBusinessId(map);
			
			//调整关联质押信息表
			map.put("table", "t_pledge_contract");
			super.dao.updateBusinessId(map);
			
			//调整保证表
			map.put("table", "t_guarantee_contract");
			super.dao.updateBusinessId(map);
		}*/

}
	/**
	 * 根据excel历史上传记录生成用户的还款计划
	 * @throws Exception 
	 * */
	@Transactional(readOnly = false)
	public void repoyForTemploy(TLoanContract temploy) throws Exception {
		//先清除旧的还款计划
		TRepayPlan plan = new TRepayPlan();
		plan.setLoanContractId(temploy.getId());
		tRepayPlanService.deletePLWL(plan);
		
		try {
			
			//  生成还款计划需要的参数
			PlanCreateParam param = new PlanCreateParam();
			param.setBusinessType("apply");//业务默认售后
			param.setAmount(new BigDecimal(temploy.getLoanAmount()));
			param.setLoanRate(new BigDecimal(temploy.getLoanRate()));
			
			if("年".equals(temploy.getLoanRateType())){
				param.setLoanRateType("1");//利率类型   1年  2月  3日  
			}else if("月".equals(temploy.getLoanRateType())){
				param.setLoanRateType("2");//利率类型   1年  2月  3日  
			}else if("日".equals(temploy.getLoanRateType())){
				param.setLoanRateType("3");//利率类型   1年  2月  3日  
			}else{
				param.setLoanRateType(temploy.getLoanRateType());
			}
			param.setLoanPeriod(temploy.getLoanPeriod());
			
			param.setLoanDate(DateUtils.formatDate(temploy.getLoanDate()));
			param.setPayType(temploy.getPayType());
			param.setPeriodType(temploy.getPeriodType());
			param.setPayOptions(null);//还款选项 （payType=3时才会用到）	1|2|null = 前置付息|一次性付息|常规
			param.setIfRealityDay("true");//默认自然月
			String pay = "yyyy-MM-dd";
			SimpleDateFormat day = new SimpleDateFormat(pay);
			String payday = day.format(temploy.getLoanDate());
			String payday1=payday.substring(5, 7);
			param.setPayDay(payday1);//还款日默认为放款日
			
					//  生成还款计划列表
				JSONArray list = tRepayPlanService.createRepayPlans(param);
				if(list != null){
					//还款计划编号
					String lotNum = tRepayPlanService.createLotNum();
					for(int i=0;i<list.length();i++){
						//完善还款计划参数
						TRepayPlan repayPlan = (TRepayPlan) JsonMapper.fromJsonString(list.get(i).toString(), TRepayPlan.class);
						//批号
						repayPlan.setLotNum(lotNum);
						//合同主键
						repayPlan.setLoanContractId(temploy.getId());
						//还款状态
						repayPlan.setStatus(Cons.RepayStatus.NO_PAID);
						//是否有效
						repayPlan.setIsValid("1");
						tRepayPlanService.save(repayPlan);
						
					}
				}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void insertByExcel(TLoanContract tLoanContract) {

		tLoanContract.setId(IdGen.uuid());
		
		//根据客户id  判断是  个人  还是企业
		TCompany company = tCompanyService.get(tLoanContract.getCustomerId());
		TEmployee employee = tEmployeeService.get(tLoanContract.getCustomerId());
		if(company != null){
			//是企业客户  就保存  客户类型字段 1 和 客户姓名
			tLoanContract.setCustomerType(Cons.CustomerType.CUST_COMPANY);
			
			tLoanContract.setCustomerName(company.getName());
		}else if(employee != null){
			//是个人客户  就保存  客户类型字段 2  和 客户姓名
			tLoanContract.setCustomerType(Cons.CustomerType.CUST_EMPLOYEE);
			tLoanContract.setCustomerName(employee.getName());
		}
		//产品信息
		TProduct product = tProductService.get(tLoanContract.getProductId());
		//  合同为空         产品的发行对象为空
		if(StringUtils.isBlank(tLoanContract.getId()) && StringUtils.isNotBlank(product.getReleasesObje())
				 //产品的发行对象为空  不包含  客户类型
				&& !product.getReleasesObje().contains(tLoanContract.getCustomerType())){
			throw new ServiceException("产品指定发行对象为["+product.getReleasesObje().replace(Cons.CustomerType.CUST_COMPANY, "企业").replace(Cons.CustomerType.CUST_EMPLOYEE, "个人")+"]");
		}
		//新增的合同状态为空，插入数据库时设定为1：新增，为提交审核
		if ((tLoanContract != null) && StringUtils.isBlank(tLoanContract.getStatus())){
			tLoanContract.setStatus(Cons.LoanContractStatus.TO_REVIEW);
		}
		//处理每期还款日1:固定某个日子 2号，2：根据放款日期，获取日
		if(tLoanContract.getPayDayType() != null && "2".equals(tLoanContract.getPayDayType())){
			Calendar cal = Calendar.getInstance();
			cal.setTime(tLoanContract.getLoanDate());  //放款日期
			tLoanContract.setPayDay(cal.get(Calendar.DATE)+"");  //付息日
		}
		if(tLoanContract.getType() == null){
			//  设置默认的业务类型
			tLoanContract.setType(Cons.LoanOrderType.TYPE_B);
		}
		
		super.dao.insert(tLoanContract);
		


		
	}
	
	/**
	 * 判断历史上传excel表企业数据是否准确
	 * */
	/**--------------------------------------------------------------------------------------------------------------------------------------------
	 * update luoxiaohu 2017-4-11
	 */
	public boolean judgeByContractCO(CompanyAndContract tEmployee,List<String> list){
		boolean a = true ;
		if(StringUtils.isBlank(tEmployee.getCustomerType())){
			list.add("客户类型为空");
			a = false;
		}
		if(!tEmployee.getCustomerType().equals("1") && !tEmployee.getCustomerType().equals("2")){
			list.add("客户类型（1.企业/2.个人）");
			a = false;
		}
		if(StringUtils.isBlank(tEmployee.getBorrower())){
			list.add("借款主体为空");
			a = false;
		}else if(!tEmployee.getBorrower().equals("1") && !tEmployee.getBorrower().equals("2") && 
				!tEmployee.getBorrower().equals("3")&& !tEmployee.getBorrower().equals("4")&& 
				!tEmployee.getBorrower().equals("5")&& !tEmployee.getBorrower().equals("6")&& 
				!tEmployee.getBorrower().equals("7")){
			list.add( "借款主体(1 :个人贷款, 2 :个体工商户贷款 ,3: 农村专业合作组织贷款 ,4 :微型企业贷款 ,5 :小型企业贷款, 6 :中型及以上企业贷款,7: 其他组织贷款");
			a = false;
		}
		if(StringUtils.isBlank(tEmployee.getIndustryId())){
			list.add( "行业为空");
			a = false;
		}
		if(!"1".equals(tEmployee.getIndustryId()) && !"2".equals(tEmployee.getIndustryId()) && !"3".equals(tEmployee.getIndustryId())
				&& !"4".equals(tEmployee.getIndustryId())&& !"5".equals(tEmployee.getIndustryId())&& !"6".equals(tEmployee.getIndustryId())
				&& !"7".equals(tEmployee.getIndustryId())&& !"8".equals(tEmployee.getIndustryId())&& !"9".equals(tEmployee.getIndustryId())
				&& !"10".equals(tEmployee.getIndustryId())&& !"11".equals(tEmployee.getIndustryId())&& !"12".equals(tEmployee.getIndustryId())
				&& !"99".equals(tEmployee.getIndustryId())){
			list.add( "行业(1 农、林、牧、渔业贷款,2 采矿业贷款,3 制造业贷款,4 电力、燃气及水生产供应业贷款,"
					+ "5 建筑业贷款,6 交通运输、仓储和邮政业贷款,7 信息传输、计算机服务和软件业贷款,8 批发和零售业贷款,9 住宿和餐饮业贷款,"
					+ "10 房地产业贷款,11 租赁和商务服务业贷款,12 居民服务和其他服务业贷款,99 其他贷款");
			a = false;
		}
	
		
		/*HashSet<String> set = new HashSet();
		set.add("1");
		set.add("2");
		set.add(3);
		set.add(4);
		set.add(5);
		set.add(6);
		set.add(7);
		set.add(8);
		set.add(9);
		set.add(10);
		set.add(11);
		set.add(12);
		set.add(99);
		if(!set.contains(tEmployee.getIndustryId())){
		list.add("行业(1 农、林、牧、渔业贷款,2 采矿业贷款,3 制造业贷款,4 电力、燃气及水生产供应业贷款,"
				+ "5 建筑业贷款,6 交通运输、仓储和邮政业贷款,7 信息传输、计算机服务和软件业贷款,8 批发和零售业贷款,9 住宿和餐饮业贷款,"
				+ "10 房地产业贷款,11 租赁和商务服务业贷款,12 居民服务和其他服务业贷款,99 其他贷款");
		a = false;
		} */
		
		/*if(StringUtils.isBlank(tEmployee.getAgriculture())){
			list.add("涉农为空");
			a = false;
		}
		HashSet<String> set01 = new HashSet();
		set01.add("1");
		set01.add("0");
		if(!set01.contains(tEmployee.getAgriculture())){
			list.add("是否涉农(是1/否0)");
			a = false;
		}*/
		if(StringUtils.isBlank(tEmployee.getLoanType())){
			list.add( "贷款方式为空");
			a = false;
		}
		
		HashSet<String> setDK = new HashSet();
		setDK.add("1");
		setDK.add("2");
		setDK.add("3");
		setDK.add("4");		
		
		  String[] chrstr=tEmployee.getLoanType().split(",");
		 //取两个数组的交集
		  boolean b = true;
		  for(int i=0;i<chrstr.length;i++){
			  //包含 1 2 3 4 就说明贷款方式  填写正确(不管  是否用的,还是.)
			  if(setDK.contains(chrstr[i])){
				  b=false;
			  }
			  if(b){
				  list.add( "贷款方式（1.抵押/2.质押/3.信用/4.保证）");
				a = false;
			}
		  }
		 
		 
		  for(int i=0;i<chrstr.length;i++)
 		  {
			  if(!setDK.contains(chrstr[i])){
					list.add( "贷款方式（1.抵押/2.质押/3.信用/4.保证）");
					a = false;
				}
		  }
		
		if( StringUtils.isBlank(tEmployee.getLoanAmount())){
			list.add( "贷款金额为空");
			a = false;
		}
		if( StringUtils.isBlank(tEmployee.getLoanRate())){
			list.add("贷款利率为空");
			a = false;
		}
		if( StringUtils.isBlank(tEmployee.getLoanRateType())){
			list.add( "利率类型为空");
			a = false;
		}else if(!tEmployee.getLoanRateType().equals("年") && !"月".equals(tEmployee.getLoanRateType()) 
				&& !"日".equals(tEmployee.getLoanRateType()) && !"季".equals(tEmployee.getLoanRateType())){
			list.add( "利率类型为年  月   日  季");
			a = false;
		}
		if( StringUtils.isBlank(tEmployee.getLoanPeriod())){
			list.add("贷款期限为空");
			a = false;
		}else if(	(Integer.parseInt(tEmployee.getLoanPeriod())%1)!=0){
			list.add( "贷款期限只能为整数");
			a = false;
		}
		if( StringUtils.isBlank(tEmployee.getPurposeId())){
			list.add( "贷款用途为空");
			a = false;
		}else if(!tEmployee.getPurposeId().equals("1") && !tEmployee.getPurposeId().equals("2") && 
				!tEmployee.getPurposeId().equals("3")&& !tEmployee.getPurposeId().equals("4")&& 
				!tEmployee.getPurposeId().equals("5")&& !tEmployee.getPurposeId().equals("6")&& 
				!tEmployee.getPurposeId().equals("7") && !tEmployee.getPurposeId().equals("8") 
				&& !tEmployee.getPurposeId().equals("99")){
			list.add( "贷款用途(1 个人-资金周转2 个人-个人经营3 个人-综合消费"
					+ "4 企业-固定资产贷款5 企业-流动资金贷款6 企业-并购贷款7 企业-房地产贷款8 企业-项目融资99 其他)");
			a = false;
		}	
		if( StringUtils.isBlank(tEmployee.getPayType())){
			list.add( "还款方式为空");
			a = false;
		}else if(!tEmployee.getPayType().equals("1") && !tEmployee.getPayType().equals("2") && 
				!tEmployee.getPayType().equals("3") && 
				!tEmployee.getPayType().equals("4")&& 
				!tEmployee.getPayType().equals("5")){
			list.add("还款方式（ 1.等额本息/2.等额本金/3.按月付息到期还款/4.按期付息到期还款/5.到期一次性还本付息））");
			a = false;
		}
		if( StringUtils.isBlank(tEmployee.getPeriodType())){
			list.add( "还款周期为空");
			a = false;
		}else if(!tEmployee.getPeriodType().equals("1") && !tEmployee.getPeriodType().equals("2") && 
				!tEmployee.getPeriodType().equals("3") && !tEmployee.getPeriodType().equals("4")){
			list.add("还款周期（1: 年,2: 月,3: 日,4: 季）");
			a = false;
		}
		if( StringUtils.isBlank(tEmployee.getIfAdvance())){
			list.add( "提前还款为空");
			a = false;
		}else if(tEmployee.getIfAdvance().equals("是") || tEmployee.getIfAdvance().equals("不是")){
			list.add("是否可提前还款（0.否/1.是）");
			a = false;
		}
		if( null==tEmployee.getPayPrincipalDate()){
			list.add( "还本金日期（最后一个还款日）为空");
			a = false;
		}
		/**
		*old   验证租户id
		*/
		if( null==tEmployee.getOrganId()){
			list.add( "租户ID为空");
			a = false;
		}
		if( StringUtils.isBlank(tEmployee.getMangeFee())){
			list.add("管理费为空");
			a = false;
		}if( StringUtils.isBlank(tEmployee.getGracePeriod())){
			list.add( "宽限期为空");
			a = false;
		}if( StringUtils.isBlank(tEmployee.getServerFee())){
			list.add( "前期服务费为空");
			a = false;
		}if( StringUtils.isBlank(tEmployee.getLateFee()) ){
			list.add("违约金为空");
			a = false;
		}if( StringUtils.isBlank(tEmployee.getAdvanceDamages())){
			list.add( "提前还款违约金比例为空");
			a = false;
		}
		return a;
		
	}
	
	/**
	 * 根据合同标号查询企业业务信息
	 * */
	public TLoanContract companyByNumber(CompanyAndContract company){
		return super.dao.companyByNumber(company);
	}

	public Page<TLoanContract> wishContractlist(Page<TLoanContract> page,TLoanContract tLoanContract) {
		tLoanContract.setPage(page);
		page.setList(dao.wishContractlist(tLoanContract));
		return page;
	}
	@Transactional(readOnly = false)
	public void saveWishContract(TLoanContract tLoanContract) {
		tLoanContract.setId(IdGen.uuid());
	     dao.insert(tLoanContract);
	}
	@Transactional(readOnly = false)
	public void saveForWish(TLoanContract tLoanContract){
		super.save(tLoanContract);
	}

	/**
	 * 通知没有通知过的内容:wish_status=status表示通知过
	 * @param tLoanContract
	 * @return
	 */
	public List<TLoanContract> findBlockContractLists(
			TLoanContract tLoanContract) {
		return dao.findBlockContractLists(tLoanContract);
	}

	@Transactional(readOnly = false)
	public void updateWishStatus(TLoanContract tLt) {
		dao.updateWishStatus(tLt);
	}

	public List<TLoanContract> findRepayContractLists(
			TLoanContract tLoanContract) {
		return dao.findRepayContractLists(tLoanContract);
	}
    /**
     * 汇总信息
     * @param tLoanContract
     * @return
     */
	public List<Double> collectContract(TLoanContract tLoanContract) {
		return dao.collectContract(tLoanContract);
	}

	public List<TLoanContract> findWishAllList(TLoanContract tloanContract) {
		return dao.findWishAllList(tloanContract);
	}

	public List<TLoanContract> getLoanMsgByCustomerId(TLoanContract tLoanContract) {
		return dao.getLoanMsgByCustomerId(tLoanContract);
	}



	

}
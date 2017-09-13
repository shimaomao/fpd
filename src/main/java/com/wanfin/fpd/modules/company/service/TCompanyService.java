/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.company.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jfinal.plugin.activerecord.Db;
import com.wanfin.fpd.common.config.Cons;
import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.service.CrudService;
import com.wanfin.fpd.common.utils.IdGen;
import com.wanfin.fpd.common.utils.StringUtils;
import com.wanfin.fpd.modules.act.service.ActTaskService;
import com.wanfin.fpd.modules.catipal.entity.TCaiwu;
import com.wanfin.fpd.modules.company.dao.TCompanyDao;
import com.wanfin.fpd.modules.company.entity.Customer;
import com.wanfin.fpd.modules.company.entity.TCompany;
import com.wanfin.fpd.modules.employee.entity.TEmployee;
import com.wanfin.fpd.modules.employee.service.TEmployeeService;
import com.wanfin.fpd.modules.excelUpload.entity.CompanyAndContract;
import com.wanfin.fpd.modules.files.service.TContractFilesService;
import com.wanfin.fpd.modules.sys.utils.UserUtils;

/**
 * 企业客户Service
 * @author lx
 * @version 2016-03-14
 */
@Service("tCompanyService")
@Transactional(readOnly = true)
public class TCompanyService extends CrudService<TCompanyDao, TCompany> {
	
	@Autowired
	private TContractFilesService tContractFilesService;
	
	@Autowired
	private TEmployeeService tEmployeeService;
	
	@Autowired
	private ActTaskService actTaskService;
	
	@Autowired
	private TCompanyDao companydao;
	@Autowired
	private RuntimeService runtimeService;

	public TCompany get(String id) {
		return super.get(id);
	}
	
	public List<TCompany> findAllList(TCompany tCompany) {
		return super.findAllList(tCompany);
	}
	
	
	public List<TCompany> findList(TCompany tCompany) {
		tCompany.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser().getCurrentUser(), "o", "u"));
		return super.findList(tCompany);
	}
	
	public Page<TCompany> findPage(Page<TCompany> page, TCompany tCompany) {
		tCompany.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser().getCurrentUser(), "o", "u"));
		return super.findPage(page, tCompany);
	}
	
	@Transactional(readOnly = false)
	public void save(TCompany tCompany) {
		
		String card = tCompany.getSuretyCardnum();
		
		if(card!=null&&card.length()==18){
			tCompany.setSuretyBirthday(card.substring(6, 10)+"-"+card.substring(10, 12)+"-"+card.substring(12, 14));
			DateFormat fmt =new SimpleDateFormat("yyyy-MM-dd");
			Date date=null;
			try {
				date = fmt.parse(card.substring(6, 10)+"-"+card.substring(10, 12)+"-"+card.substring(12, 14));
			} catch (ParseException e) {
				e.printStackTrace();
			}
	        Calendar cal = Calendar.getInstance();  //获取当前系统时间
	        int yearNow = cal.get(Calendar.YEAR); 
	        int monthNow = cal.get(Calendar.MONTH); 
	        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH); 
	        cal.setTime(date); 
	        int yearBirth = cal.get(Calendar.YEAR); 
	        int monthBirth = cal.get(Calendar.MONTH); 
	        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH); 
	        int age = yearNow - yearBirth; 
	        if (monthNow <= monthBirth) { 
	            //如果月份相等，在比较日期，如果当前日，小于出生日，也减1，表示不满多少周岁
	            if (monthNow == monthBirth) { 
	                if (dayOfMonthNow < dayOfMonthBirth) age--; 
	            }else{ 
	                age--; 
	            } 
	        }
	        tCompany.setSuretyAge(age+"");
		}
		
		
		
		String tempId = null;
		if(StringUtils.isBlank(tCompany.getId()) || tCompany.getId().startsWith("new_")){
			//新增，且有临时id
			tempId = tCompany.getId();
			tCompany.setId(null);
			//tCompany.setProduct(new TProduct((String)UserUtils.getCache(UserUtils.CACHE_SYSCODE)));
			tCompany.setStatus(Cons.CustomerStatus.UNNORMAL);
		}
		
		if(StringUtils.isEmpty(tCompany.getId()) && StringUtils.isEmpty(tCompany.getStatus())){
			tCompany.setStatus(Cons.CustomerStatus.UNNORMAL);
		}
		super.save(tCompany);
		
		if(StringUtils.isNotBlank(tempId)){
			//关联附件
			tContractFilesService.updateFileTaskId(tempId,tCompany.getId());
		}	
	}
	
	@Transactional(readOnly = false)
	public void delete(TCompany tCompany) {
		super.delete(tCompany);
	}

	/**
	 * @Description 黑名单审核
	 * @param act
	 * @param id
	 * @author zzm 
	 * @date 2016-4-14 下午6:30:58  
	 */
	@Transactional(readOnly = false)
	public void saveAudit(TCompany tCompany) {
		String title = null;
		String procDefKey = Cons.ProcDefKey.BLACK_APPLY;//流程定义KEY
		String businessTable = tCompany.getAct().getBusinessTable();//业务表表名
		TEmployee tEmployee = tEmployeeService.get(tCompany.getId());
		if(tEmployee == null){
			procDefKey = tCompany.getAct().getProcDefKey();
		}else{
			procDefKey = tCompany.getAct().getProcDefKey();
		}
		if(StringUtils.isBlank(tCompany.getProcInsId())){
			//企业客户审核
			if(tEmployee == null){
				title = "【企业客户】"+tCompany.getName();
				procDefKey = tCompany.getAct().getProcDefKey();
				businessTable = "t_company";
				if(procDefKey.contains(Cons.ProcDefKey.BLACK_APPLY)){					
					//tCompany.setReason(tCompany.getAct().getComment());
					tCompany.setReason(tCompany.getReason());
					tCompany.setStatus(Cons.CustomerStatus.BLACK_AUDIT);
				} else if(procDefKey.contains(Cons.ProcDefKey.BLACK_REMOVE)){
					tCompany.setStatus(Cons.CustomerStatus.REMOVE_BLACK_AUDIT);
				}
				super.save(tCompany);
			}
			//个人客户审核
			else{
				title = "【个人客户】"+tEmployee.getName();
				procDefKey = tCompany.getAct().getProcDefKey();
				businessTable = "t_employee";
				if(procDefKey.contains(Cons.ProcDefKey.BLACK_APPLY)){
					//tEmployee.setReason(tCompany.getAct().getComment());
					tEmployee.setReason(tCompany.getReason());
					tEmployee.setStatus(Cons.CustomerStatus.BLACK_AUDIT);
				} else if(procDefKey.contains(Cons.ProcDefKey.BLACK_REMOVE)){
					tEmployee.setStatus(Cons.CustomerStatus.REMOVE_BLACK_AUDIT);
				}
				tEmployeeService.save(tEmployee);
			}
			// 启动并提交流程
			
			String procInsId = actTaskService.startProcess(procDefKey, businessTable, tCompany.getId(), title);
			if(StringUtils.isNotBlank(procInsId))
				actTaskService.completeFirstTask(procInsId, tCompany.getAct().getComment(), 
					title, tCompany.getAct().getVars().getVariableMap());
		}else{
			// 设置意见
			tCompany.getAct().setComment(("1".equals(tCompany.getAct().getFlag())?"[同意] ":"[驳回] ")+tCompany.getAct().getComment());
		    if(!"-1".equals(tCompany.getAct().getFlag())){//非终止操作
		    	//执行任务
				actTaskService.complete(tCompany.getAct().getTaskId(), tCompany.getAct().getProcInsId(), 
						tCompany.getAct().getComment(), tCompany.getAct().getVars().getVariableMap());
		    }else{
		    	 actTaskService.overTask(tCompany.getAct().getTaskId(),tCompany.getAct().getProcInsId(),tCompany.getAct().getComment());
		    	//加入黑名单申请失败，状态：解除黑名审批中——》正常
		    	 if(procDefKey.contains(Cons.ProcDefKey.BLACK_APPLY)){
		    		 if(tEmployee == null) {
							tCompany.setStatus(Cons.CustomerStatus.NORMAL);
							tCompany.setReason(null);
						}else{
							tEmployee.setStatus(Cons.CustomerStatus.NORMAL);
							tEmployee.setReason(null);
						}  
		    	 }else if(procDefKey.contains(Cons.ProcDefKey.BLACK_REMOVE)){
		    		//解除黑名单申请失败，状态：解除黑名审批中——》黑名
		 			if(tEmployee == null) {
		 				tCompany.setStatus(Cons.CustomerStatus.BLACK);
		 				
		 			}else{
		 				tEmployee.setStatus(Cons.CustomerStatus.BLACK);
		 			} 
		    	 }
					
		    }
			
		}
		ProcessInstance pi = runtimeService.createProcessInstanceQuery()
				.processInstanceId(tCompany.getAct().getProcInsId()).singleResult();
		if (pi != null || tCompany.getAct().getFlag().equals("-1")) {
			if(tEmployee == null) {
				this.save(tCompany);
			}else{
				tEmployeeService.save(tEmployee);
			} 
		}
		
	}
	
	/**
	 * @Description 客户查询  包含企业和个人
	 * @param page
	 * @param customer
	 * @return
	 * @author zzm
	 * @date 2016-4-18 下午3:35:35  
	 */
	public Page<Customer> findCustomByPage(Page<Customer> page, Customer customer){
		customer.setPage(page);
		page.setList(this.dao.findCustomerList(customer));
		return page;
	}

		
	/**
	 * @Description 获取客户信息
	 * @param id
	 * @return
	 * @author zzm
	 * @date 2016-6-3 下午2:40:16  
	 */
	public Customer getCustomer(String id){
		return this.dao.getCustomer(id);
	}
	
	/**
	 * @Description 获取客户与产品的关联
	 * @param productId	产品id
	 * @param customerId	客户id（个人/企业）
	 * @author zzm 
	 * @date 2016-6-7 上午10:38:07  
	 */
	public Map<String,String> getCustomerProductLink(String productId, String customerId){
		return this.dao.getCustomerProductLink(productId, customerId);
	}
	
	/**
	 * @Description 保存客户与产品的关联
	 * @param productId	产品id
	 * @param customerId	客户id（个人/企业）
	 * @param customerType	客户类型（1 企业，2 个人）
	 * @author zzm 
	 * @date 2016-6-7 上午10:38:07  
	 */
	@Transactional(readOnly = false)
	public void updateStatus(String customerId, String customerType,String status){
	   if("1".equals(customerType)  || "11".equals(customerType)){
		
		   Db.update("update t_company set status = ? where id = ?",status, customerId);
	   }else if("2".equals(customerType) || "22".equals(customerType)){
		   
		   Db.update("update t_employee set status = ? where id = ?",status, customerId);
	   }
		
	}
	


    /**
     * 
     * @param table表名  1企业     2个人
     * @param type 性别类型 1：男   2：女
     * @return
     */
//	public int findCustomerSexAmount(String tablename,String filedName,int tabletype,int type) {
//        String productid = "a5f0fdb7b8d9424ab320d2ef85c74f4b";//(String)UserUtils.getCache(UserUtils.CACHE_SYSCODE); 
//		return companydao.findCustomerSexAmount(productid,tablename,filedName,tabletype,type);
//	}

	
	/**
	 * @Description 根据W端企业客户ID获取B端企业客户
	 * @param wtypeId
	 * @return
	 */
	public TCompany getByWtypeId(String wtypeId){
		return dao.getByWtypeId(wtypeId);
	}

	/**
	 * @Description 获取所有个人客户身份证号
	 * @param wtypeId
	 * @return
	 */
	public List<String> findAllCardNumList(){
		return dao.findAllCardNumList();
	}

	public Page<TCompany> findCompanyPage(Page<TCompany> page, TCompany tCompany) {
		// 生成数据权限过滤条件（dsf为dataScopeFilter的简写，在xml中使用 ${sqlMap.dsf}调用权限SQL）
		tCompany.getSqlMap().put("dsf", dataScopeFilter(tCompany.getCurrentUser(), "o", "u"));
		// 设置分页参数
		tCompany.setPage(page);
		// 执行分页查询
		page.setList(dao.findList(tCompany));
		return page;
	}
	
	
	/**
	 * 根据扫描状态进行查询数据
	 * @param company
	 * @return
	 */
	public List<TCompany> findListByScanTimeAndPushStatus(TCompany company){
		return companydao.findListByScanTimeAndPushStatus(company);
	};
	
	/**
	 * 通过扫描状态查询记录
	 * @param tCompany
	 * @return
	 */
	public List<TCompany> findListByScanTime(TCompany tCompany) {		
		return companydao.findListByScanTime(tCompany);
	}
	
	/**
	 * 通过扫描状态查询记录
	 * @param tCompany
	 * @return
	 */
	public void updateByPushStatus(TCompany tCompany) {		
		 companydao.updateByPushStatus(tCompany);
	}
	
	@Transactional(readOnly = false)
	public StringBuffer updateGetListByscanFlagData(String fileName, Long informFilingType) {	
		TCompany queryCompanySheep = new TCompany();
		queryCompanySheep.setScanFlag("0");
		StringBuffer sheepData = new StringBuffer();
		List<TCompany> companyList = dao.findListByScanTime(queryCompanySheep);
		if (companyList != null && companyList.size() > 0) {			
			// 获取数据
			for (TCompany temp : companyList) {	
				sheepData.append(temp.senData());
				sheepData.append("\r\n");
			}
			// 更改已经做了扫描处理的标示
		/*	for (TCompany temp : companyList) {
			
				dao.update(temp);
			}*/
			
			for (TCompany temp : companyList) {
				temp.setPushStatus("1");
				temp.setScanFlag("1");
				temp.preUpdate();
				dao.updateByPushStatus(temp);
			}
		}
		return sheepData;
	}
	
	
	@Transactional(readOnly = false)
	public void updateReceiptData(List<String> dataList,List<TCompany> personList,List<TCompany> personList1) {
		
		//对推送状态为0的做处理
		if(personList != null && personList.size() > 0){
			for(TCompany temp:personList){	
				if(dataList != null && dataList.size() > 0){						
					if(dataList.contains(temp.getId())){ //这条记录存在回执错误						
						temp.setScanFlag("0");							
						dao.update(temp);
					}else{ //这条记录推成功						
						temp.setPushStatus("1");				
						temp.setScanFlag("1");							
						dao.updateByPushStatus(temp);
					}	
					
				}else {
					temp.setPushStatus("1");				
					temp.setScanFlag("1");							
					dao.updateByPushStatus(temp);
				}							
			}
		}		
		
		//对推送状态为1的做处理
		if(personList1 != null && personList1.size() > 0){
			for(TCompany temp:personList1){	
				if(dataList != null && dataList.size() > 0){						
					if(dataList.contains(temp.getId())){ //这条记录存在回执错误						
						temp.setScanFlag("0");							
						dao.update(temp);
					}else{ //这条记录推成功						
						temp.setPushStatus("1");				
						dao.updateByPushStatus(temp);
					}	
					
				}else {
					temp.setPushStatus("1");				
					dao.updateByPushStatus(temp);
				}							
			}
		}	
		
		
	}

	public List<TCompany> getByCondition(TCompany tCompany) {
		if(StringUtils.isNotBlank(tCompany.getId())){
			tCompany.getSqlMap().put("dsf", " AND a.id <> '"+tCompany.getId()+"' ");
		}
		return dao.getByCondition(tCompany);
	}
	
	/**
	 * 历史excel。判断接收的参数
	 * */
	/**
	 * update luoxiaohu  2017-4-11
	 * @param company
	 * @param map
	 * @return
	 */
public boolean judgeForCompany(CompanyAndContract company,List<String> list){
	/*
	StringUtils.isNotBlank(company.getName())
	&& StringUtils.isNotBlank(company.getCardNum())
	&& StringUtils.isNotBlank(company.getCardType())
	&& StringUtils.isNotBlank(company.getProperties())
	&& StringUtils.isNotBlank(company.getCaptial())
	&& StringUtils.isNotBlank(company.getAddress())
	&& StringUtils.isNotBlank(company.getPhone())
	&& StringUtils.isNotBlank(company.getSurety())
	&& StringUtils.isNotBlank(company.getSuretyCardnum())
	&& StringUtils.isNotBlank(company.getSuretySex())
	&& StringUtils.isNotBlank(company.getSuretyMobile())
	&& StringUtils.isNotBlank(company.getSuretyAddress())
	&& StringUtils.isNotBlank(company.getSuretyRegaddr())
	&& StringUtils.isNotBlank(company.getGatheringName())
	&& StringUtils.isNotBlank(company.getGatheringBank())
	&& StringUtils.isNotBlank(company.getGatheringNumber())
	&& StringUtils.isNotBlank(company.getCustomerManager())
	*/	
		boolean a=true ;
		if(	StringUtils.isBlank(company.getName())){
			list.add("没有填写企业名");
			a =false;
		}
		if(	StringUtils.isBlank(company.getCardNum())){
			list.add( "没有填写公司证件号码");
			a =false;
		}
		if(	StringUtils.isBlank(company.getCardType())){
			list.add("没有填写公司证件类型");
			a =false;
		}if(	StringUtils.isBlank(company.getProperties())){
			list.add( "没有填写企业性质");
			a =false;
		}if(	StringUtils.isBlank(company.getCaptial())){
			list.add( "没有填写注册资金");
			a =false;
		}if(	StringUtils.isBlank(company.getAddress())){
			list.add("没有填写公司地址");
			a =false;
		}if(	StringUtils.isBlank(company.getPhone())){
			list.add( "没有填写公司电话");
			a =false;
		}if(	StringUtils.isBlank(company.getSurety())){
			list.add("没有填写公司代表人");
			a =false;
		}if(	StringUtils.isBlank(company.getSuretyCardnum())){
			list.add( "没有填写法人身份证");
			a =false;
		}if(	StringUtils.isBlank(company.getSuretySex())){
			list.add("没有填写公司法人性别");
			a =false;
		}else if(	"女".equals(company.getSuretySex()) ||  "男".equals(company.getSuretySex()) ){
			list.add("性别 : 男为1,女为2");
			a =false;
		}
		if(	StringUtils.isBlank(company.getSuretyMobile())){
			list.add("没有填写公司法人手机号码");
			a =false;
		}if(	StringUtils.isBlank(company.getSuretyAddress())){
			list.add("没有填写公司法人住址");
			a =false;
		}if(	StringUtils.isBlank(company.getSuretyRegaddr())){
			list.add( "没有填写公司法人户籍");
			a =false;
		}if(	StringUtils.isBlank(company.getGatheringName())){
			list.add( "没有填写公司法人开户名");
			a =false;
		}if(	StringUtils.isBlank(company.getGatheringBank())){
			list.add("没有填写公司法人开户行");
			a =false;
		}if(	StringUtils.isBlank(company.getGatheringNumber())){
			list.add("没有填写公司法人开户账户");
			a =false;
		}if(	StringUtils.isBlank(company.getCustomerManager())){
			list.add("没有填写客户经理");
			a =false;
		}
		
		
		return a;
		
		
		
	}
	
	/**
	 * 历史记录excel操作
	 * */
	public TCompany byCompanyAndContract(CompanyAndContract company){
		return super.dao.byCompanyAndContract(company);
	}

	public void updateByExcel(TCompany com) {
		try {
			
			super.dao.update(com);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
	}
	
	
	//根据历史记录插入数据
		@Transactional(readOnly = false)
		public void insertByExcel (TCompany com) throws Exception{
			try {
				com.setId(IdGen.uuid());
				super.dao.insert(com);
			} catch (Exception e) {
				
				e.printStackTrace();
			}
		}

		@Transactional(readOnly = false)
		public void insert(TCompany company) {
			dao.insert(company);
		}

		@Transactional(readOnly = false)
		public void update(TCompany company) {
			dao.update(company);
		}
	
}
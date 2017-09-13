/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.employee.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.wanfin.fpd.common.config.Cons;
import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.service.CrudService;
import com.wanfin.fpd.common.utils.IdGen;
import com.wanfin.fpd.common.utils.StringUtils;
import com.wanfin.fpd.modules.company.entity.TCompany;
import com.wanfin.fpd.modules.employee.dao.TEmployeeDao;
import com.wanfin.fpd.modules.employee.entity.CsCar;
import com.wanfin.fpd.modules.employee.entity.CsContact;
import com.wanfin.fpd.modules.employee.entity.CsHouse;
import com.wanfin.fpd.modules.employee.entity.TEmployee;
import com.wanfin.fpd.modules.excelUpload.entity.TEmployeeAndContract;
import com.wanfin.fpd.modules.files.service.TContractFilesService;
import com.wanfin.fpd.modules.spouse.entity.TSpouse;
import com.wanfin.fpd.modules.spouse.service.TSpouseService;
import com.wanfin.fpd.modules.sys.utils.UserUtils;

/**
 * 客户个人Service
 * @author lx
 * @version 2016-03-12
 */
@Service("tEmployeeService")
@Transactional(readOnly = true)
public class TEmployeeService extends CrudService<TEmployeeDao, TEmployee> {
	
	@Autowired
	private TContractFilesService tContractFilesService;
	
	@Autowired
	private TSpouseService tspouseService;
	
	@Autowired
	private CsCarService csCarService;
	
	@Autowired
	private CsContactService csContactService;
	
	@Autowired
	private CsHouseService csHouseService;
	

	public TEmployee get(String id) {
		return super.get(id);
	}
	
	
	/** 
	 * @Description 客户全量信息
	 * @return
	 * @author zzm
	 * @date 2016-7-21 下午6:12:18  
	 */
	public TEmployee getFull(String id) {
		TEmployee employee = super.get(id);
		CsHouse house = new CsHouse();
		house.setCustomerId(employee.getId());
		CsCar car = new CsCar();
		car.setCustomerId(employee.getId());
		CsContact contact = new CsContact();
		contact.setCustomerId(employee.getId());
		employee.setHouses(csHouseService.findList(house));
		employee.setCars(csCarService.findList(car));
		employee.setContacts(csContactService.findList(contact));
		Record record = getSpouseId(id);
		if(record!=null && record.getStr("id") != null){
			employee.setMate(tspouseService.get(record.getStr("id")));
		}
		return employee;
	}
	
	public List<TEmployee> findList(TEmployee tEmployee) {
		tEmployee.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser().getCurrentUser(), "o", "u"));
		return super.findList(tEmployee);
	}
	
	public List<TEmployee> findAllList(TEmployee tEmployee) {
		return super.findAllList(tEmployee);
	}
	
	
	public Page<TEmployee> findPage(Page<TEmployee> page, TEmployee tEmployee) {
		tEmployee.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser().getCurrentUser(), "o", "u"));
		return super.findPage(page, tEmployee);
	}
	
	@Transactional(readOnly = false)
	public void save(TEmployee tEmployee) {
        String card = tEmployee.getCardNum();
		
		if(card!=null&&card.length()==18){
			tEmployee.setBirthday(card.substring(6, 10)+"-"+card.substring(10, 12)+"-"+card.substring(12, 14));
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
	        tEmployee.setAge(age+"");
		}
		
		
		String tempId = null;
		if(StringUtils.isNotBlank(tEmployee.getId()) && tEmployee.getId().startsWith("new_")){
			//新增，且有临时id
			tempId = tEmployee.getId();
			tEmployee.setId(null);
			//tEmployee.setProduct(new TProduct((String)UserUtils.getCache(UserUtils.CACHE_SYSCODE)));
			tEmployee.setStatus(Cons.CustomerStatus.UNNORMAL);
		}
		
		if(StringUtils.isEmpty(tEmployee.getId()) && StringUtils.isEmpty(tEmployee.getStatus())){
			tEmployee.setStatus(Cons.CustomerStatus.UNNORMAL);
		}
		
		super.save(tEmployee);
		
		if("2".equals(tEmployee.getMarriedInfo())&&!tEmployee.getId().startsWith("new_")){//不以new开头，说明是修改，根据客户信息查出其配偶信息主键id
			Record id  =  getSpouseId(tEmployee.getId());//Db.findFirst("select id from t_spouse where customer_type = ? and customer_id = ?", 2,tEmployee.getId());
			System.out.println(id);
			if(id==null||"".equals(id)){
				tEmployee.getMate().setId(null);
			}else{
				if(tEmployee.getMate()!=null){
					tEmployee.getMate().setId(id.getStr("id"));
				}
			}
			if(tEmployee.getMate()!=null){
				tEmployee.getMate().setCustomerType("2");
				tEmployee.getMate().setCustomerName(tEmployee.getName());
				tEmployee.getMate().setCustomerId(tEmployee.getId());
				tEmployee.getMate().setOrganId(tEmployee.getOrganId());
				
				tspouseService.save(tEmployee.getMate());
			}
			
		}
		
		//>>>>add by zzm @2016-07-26
		//车辆信息
		if(tEmployee.getCars() != null ){
			CsCar car = new CsCar();
			car.setCustomerId(tEmployee.getId());
			List<CsCar> carList = csCarService.findList(car);
			StringBuffer sb = new StringBuffer();
			for(CsCar c : tEmployee.getCars()){
				c.setCustomerId(tEmployee.getId());
				csCarService.save(c);
				sb.append(c.getId()+",");
			}
			if(carList!=null){
				for(CsCar c : carList){
					if(sb.indexOf(c.getId()) < 0)
					csCarService.delete(c);
				}
			}
		}
		//房产信息
		if(tEmployee.getHouses() != null ){
			CsHouse house = new CsHouse();
			house.setCustomerId(tEmployee.getId());
			List<CsHouse> houseList = csHouseService.findList(house);
			StringBuffer sb = new StringBuffer();
			for(CsHouse h : tEmployee.getHouses()){
				h.setCustomerId(tEmployee.getId());
				csHouseService.save(h);
				sb.append(h.getId()+",");
			}
			if(houseList!=null){
				for(CsHouse h : houseList){
					if(sb.indexOf(h.getId()) < 0)
						csHouseService.delete(h);
				}
			}
		}
		//联系人信息
		if(tEmployee.getContacts() != null ){
			CsContact contact = new CsContact();
			contact.setCustomerId(tEmployee.getId());
			List<CsContact> contactList = csContactService.findList(contact);
			StringBuffer sb = new StringBuffer();
			for(CsContact c : tEmployee.getContacts()){
				c.setCustomerId(tEmployee.getId());
				csContactService.save(c);
				sb.append(c.getId()+",");
			}
			if(contactList!=null){
				for(CsContact c : contactList){
					if(sb.indexOf(c.getId()) < 0)
						csContactService.delete(c);
				}
			}
		}
		//<<<<

		
		if(StringUtils.isNotBlank(tempId)){
			//关联附件
			tContractFilesService.updateFileTaskId(tempId,tEmployee.getId());
		}	
	}
	
	public Record getSpouseId(String id){
		return Db.findFirst("select id from t_spouse where customer_type = ? and customer_id = ?", 2,id);
	}
	
	
	@Transactional(readOnly = false)
	public void delete(TEmployee tEmployee) {
		super.delete(tEmployee);
	}

	/**
	 * @Description 根据W端个人客户ID获取B端个人客户
	 * @param wtypeId
	 * @return
	 */
	@Transactional(readOnly = true)
	public TEmployee getByWtypeId(String wtypeId) {
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
	
    /**
     * 根据扫描记录查询状态
     * @param tEmployee
     * @return
     */
	public List<TEmployee> findListByScanFlag(TEmployee tEmployee){
		return dao.findListByScanFlag(tEmployee);
	}
	
	
	
	public void updateByPushStatus(TEmployee tEmployee){
		 dao.updateByPushStatus(tEmployee);
	}
	
	
    /**
     * 根据扫描状态和推送状态查询记录
     * @param tEmployee
     * @return
     */
	public List<TEmployee> findListByScanFlagAndPushStatus(TEmployee tEmployee){
		return dao.findListByScanFlagAndPushStatus(tEmployee);
	}


	public Page<TEmployee> findEmployeePage(Page<TEmployee> page, TEmployee tEmployee) {
		// 生成数据权限过滤条件（dsf为dataScopeFilter的简写，在xml中使用 ${sqlMap.dsf}调用权限SQL）
		tEmployee.getSqlMap().put("dsf", dataScopeFilter(tEmployee.getCurrentUser(), "o", "u"));
	    // 设置分页参数
		tEmployee.setPage(page);
		// 执行分页查询
		page.setList(dao.findList(tEmployee));
		return page;
	}
	
	
	@Transactional(readOnly = false)
	public StringBuffer updateGetListByscanFlagData(String fileName, Long informFilingType) {
		StringBuffer sheepData = new StringBuffer();
		TEmployee queryPersonSheep = new TEmployee();		
		queryPersonSheep.setScanFlag("0");
		
		List<TEmployee> personList = dao.findListByScanFlag(queryPersonSheep);
		if (personList != null && personList.size() > 0) {			
			// 获取数据
			for (TEmployee temp : personList) {
				sheepData.append(temp.senData());
				sheepData.append("\r\n");
			}
			// 更改已经做了扫描处理的标示
			/*for (TEmployee temp : personList) {
				temp.setScanFlag("1");
				dao.update(temp);
			}*/
			for (TEmployee temp : personList) {
				temp.setScanFlag("1");
				temp.setPushStatus("1");
				temp.preUpdate();
				dao.updateByPushStatus(temp);
			}
		}
		return sheepData;
	}
	
	
	@Transactional(readOnly = false)
	public void updateReceiptData(List<String> dataList,List<TEmployee> personList,List<TEmployee> personList1) {
		
		if(personList != null && personList.size() > 0){
			for(TEmployee temp:personList){
				if(dataList != null && dataList.size() > 0){
					if(temp.getId().equals(temp.getId())){ //这条记录存在回执错误
						temp.setScanFlag("0");
						dao.update(temp);
					}else{ //这条记录推成功						
						temp.setPushStatus("1");
						temp.setScanFlag("1");							
						dao.updateByPushStatus(temp);
					}
				}else{
					temp.setPushStatus("1");
					temp.setScanFlag("1");							
					dao.updateByPushStatus(temp);
				}
				
			}
		}		
		
		//对推送状态为1 做处理
		if(personList1 != null && personList1.size() > 0){
			for(TEmployee temp:personList1){
				if(dataList != null && dataList.size() > 0){
					if(temp.getId().equals(temp.getId())){ //这条记录存在回执错误
						temp.setScanFlag("0");
						dao.update(temp);
					}else{ //这条记录推成功						
						temp.setPushStatus("1");
						dao.updateByPushStatus(temp);
					}
				}else{
					temp.setPushStatus("1");
					dao.updateByPushStatus(temp);
				}
				
			}
		}	
		
		
	}


	public List<TEmployee> getByCondition(TEmployee tEmployee) {
		if(StringUtils.isNotBlank(tEmployee.getId())){
			tEmployee.getSqlMap().put("dsf", " AND a.id <> '"+tEmployee.getId()+"' ");
		}
		return dao.getByCondition(tEmployee);
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
	 * 历史excel数据判断方法
	 * */
	/**
	 * new  luoxiaohu  2017-04-11
	 */
	public boolean judgeForTemployee(TEmployeeAndContract temployee,List<String> list){
		
		boolean a=true ;
		if(	StringUtils.isBlank(temployee.getCustomerName())){
			list.add("没有填写姓名");
			a =false;
		}
		if(	StringUtils.isBlank(temployee.getSex())){
			list.add( "没有填写性别");
			a =false;
		}
		else if(	"女".equals(temployee.getCustomerName()) ||  "男".equals(temployee.getCustomerName()) ){
			list.add( "性别 : 男为1,女为2");
			a =false;
		}
		if(StringUtils.isBlank(temployee.getCardNum()) ){
			list.add( "身份证为空");
			a =false;
		}else if(temployee.getCardNum().length() != 15 && temployee.getCardNum().length() != 18 ){
			list.add( "身份证号码为18位");
			a =false;
		}
		if(StringUtils.isBlank(temployee.getMobile()) ){
			list.add( "电话为空");
			a =false;
		}
		if(temployee.getCardNum().length() == 11){
			list.add( "电话号码为11位");
			a =false;
		}
		if(StringUtils.isBlank(temployee.getMarriedInfo()) ){
			list.add( "婚姻为空");
			a =false;
		}
		if(!temployee.getMarriedInfo().equals("1") && !temployee.getMarriedInfo().equals("2") && !temployee.getMarriedInfo().equals("3") && temployee.getMarriedInfo().equals("4")){
			list.add("婚姻状况（1.未婚/2.已婚/3.离婚/4.丧偶）");
			a =false;
		}
		if(StringUtils.isBlank(temployee.getCurrentLiveAddress()) ){
			list.add( "居住地址为空");
			a =false;
		}
		if(StringUtils.isBlank(temployee.getHouseholdRegAddr()) ){
			list.add( "户籍为空");
			a =false;
		}
		if(StringUtils.isBlank(temployee.getGatheringName()) ){
			list.add("开户名为空");
			a =false;
		}
		if(StringUtils.isBlank(temployee.getGatheringBank()) ){
			list.add( "开户行为空");
			a =false;
		}
		if(StringUtils.isBlank(temployee.getGatheringNumber()) ){
			list.add("开户账户为空");
			a =false;
		}
		if(StringUtils.isBlank(temployee.getCustomerManager()) ){
			list.add("客户经理为空");
			a =false;
		}
		
		return a;
		
		
		
	}

	/**
	 *历史上传数据业务处理方法
	 * 2017.3.28
	 * lej
	 * */
	public TEmployee byTEmployeeAndContract(TEmployeeAndContract temployeeandcontract){
		return super.dao.byTEmployeeAndContract(temployeeandcontract);
	}
	
	
	// 根据历史 记录  更新数据
	@Transactional(readOnly = false)
	public void updateByExcel (TEmployee tEmployee) throws Exception{
		try {
			super.dao.update(tEmployee);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//根据历史记录插入数据
		@Transactional(readOnly = false)
		public void insertByExcel (TEmployee tEmployee) throws Exception{
			try {
				tEmployee.setId(IdGen.uuid());
				super.dao.insert(tEmployee);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		@Transactional(readOnly = false)
		public void insert(TEmployee employee) {
			dao.insert(employee);
		}

		@Transactional(readOnly = false)
		public void update(TEmployee employee) {
			dao.update(employee);
		}

}
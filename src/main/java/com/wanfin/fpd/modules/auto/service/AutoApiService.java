/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.auto.service;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpUtils;

import net.sf.json.JSONObject;

import org.activiti.engine.impl.util.json.HTTP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jfinal.plugin.activerecord.Db;
import com.wanfin.fpd.common.config.AutoUtil;
import com.wanfin.fpd.common.config.Cons;
import com.wanfin.fpd.common.config.Global;
import com.wanfin.fpd.common.service.BaseService;
import com.wanfin.fpd.common.utils.HttpPostJson;
import com.wanfin.fpd.common.utils.SerialUtils;
import com.wanfin.fpd.common.utils.StringUtils;
import com.wanfin.fpd.modules.act.service.ActAutoService;
import com.wanfin.fpd.modules.auto.entity.TTenant;
import com.wanfin.fpd.modules.form.entity.tpl.DfFormTpl;
import com.wanfin.fpd.modules.form.service.tpl.DfFormTplService;
import com.wanfin.fpd.modules.product.entity.TProduct;
import com.wanfin.fpd.modules.product.service.TProductService;
import com.wanfin.fpd.modules.sys.entity.Area;
import com.wanfin.fpd.modules.sys.entity.Office;
import com.wanfin.fpd.modules.sys.entity.Role;
import com.wanfin.fpd.modules.sys.entity.User;
import com.wanfin.fpd.modules.sys.service.AreaService;
import com.wanfin.fpd.modules.sys.service.OfficeService;
import com.wanfin.fpd.modules.sys.service.SystemService;

/**
 * 流程定义相关Controller
 * @author ThinkGem
 * @version 2013-11-03
 */
@Service
@Transactional(readOnly = true)
public class AutoApiService extends BaseService {
	public static final String DAI_KUAN = "01";
	public static final String DAN_BAO = "02";
//	public static final String IS_TRUE_ROLES = "isTrueRoles";
//	public static final String IS_TRUE_USERS = "isTrueUsers";
//	public static final String IS_TRUE_DB_ACTIVITS = "isTrueDbActivits";
//	public static final String IS_TRUE_XD_ACTIVITS = "isTrueXdActivits";
//	public static final String IS_TRUE_FORM_TPLS = "isTrueFormTpls";
//	public static final String IS_TRUE_PRODUCTS = "isTrueProducts";
	@Autowired
	private DfFormTplService dfFormTplService;
	@Autowired
	private TProductService tProductService;
	@Autowired
	private AreaService areaService;
	@Autowired
	private OfficeService officeService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private ActAutoService actAutoService;
	
	/**
	* @Description: 判断当前新增机构是否为租户 
	* @author Chenh
	* @param office
	* @return  
	* @throws
	 */
	public boolean checkOfficeIsZH(Office office) {
		return (Cons.SysDF.DF_SUPER_OFFICE_TYPEORG).equals(office.getType()) && (Cons.SysDF.DF_SUPER_COMPANY).equals(office.getParentId());
	}
	
	/**
	* @Description: 检查机构下是否存在综合部 
	* @author Chenh
	* @param company
	* @return  
	* @throws
	 */
	public Office checkHasZHB(Office company) {
		Office office = new Office();
		office.setParent(company);
		office.setParentIds(company.getId());
		List<Office> offices = officeService.getParentZHB(office);
		
		if((offices != null) && (offices.size() > 0)){
			office = offices.get(0);
			
		}else{
			office = null;
		}
		return office;
	}
	
	/**
	 * 初始化系统基础数据
	 */
	@Transactional(readOnly = false)
	public Boolean init(Office company) {
		if(checkOfficeIsZH(company)){
			Boolean isTrueOffices = initOffices(company, AutoUtil.getOfficesOrg());
			init(company, isTrueOffices);
		}
		return true;
	}
	/**
	 * 初始化系统基础数据（前提条件机构-综合部已创建）
	 */
	@Transactional(readOnly = false)
	public void init(Office company, Boolean isTrueOffices) {
		Boolean isTrueRoles = false;
		Boolean isTrueUsers = false;
		Boolean isTrueDbActivits = false;
		Boolean isTrueDbFormTpls = false;
		Boolean isTrueXdActivits = false;
		Boolean isTrueXdFormTpls = false;
		Boolean isTrueProducts = false;
		
		if(isTrueOffices){
			logger.info("初始化租户机构成功！");
			
			isTrueRoles = initRoles(company, AutoUtil.getRolesOrg());
		}
		
		if(isTrueRoles){
			logger.info("初始化租户角色成功！");

			isTrueUsers = initUsers(company, AutoUtil.getUsersOrg());
		}
		
		//初始化租户账户
		if(isTrueUsers){
			String userId = Db.queryStr("SELECT id FROM sys_user WHERE company_id = ?", company.getId());
			initAccount(userId, "2");
		}
		
		if(isTrueUsers){
			logger.info("初始化租户用户成功！");
			
			//下面开始初始化流程
			if((company.getPrimaryPerson()).equals(Cons.CompanyType.DAN_BAO)){
				isTrueDbFormTpls = initFormTpls(company, AutoUtil.getFormTplOrgDb());
				isTrueDbActivits = initActivit(company, AutoUtil.getActivitOrgDb(), Cons.SysDF.DF_ADMIN_ROLE_DB);
				
				if(isTrueDbActivits){
					logger.info("初始化担保租户表单模板成功！");
				}
				if(isTrueDbActivits){
					logger.info("初始化担保租户流程成功！");
				}
			}else if((company.getPrimaryPerson()).equals(Cons.CompanyType.DAI_KUAN)){
				isTrueXdFormTpls = initFormTpls(company, AutoUtil.getFormTplOrgXd());
				isTrueXdActivits = initActivit(company, AutoUtil.getActivitOrgXd(), Cons.SysDF.DF_ADMIN_ROLE_XD);

				if(isTrueXdFormTpls){
					logger.info("初始化租户表单模板成功！");
				}
				if(isTrueXdActivits){
					logger.info("初始化贷款租户流程成功！");
				}
			}
		}
		

//		if((isTrueFormTpls && isTrueDbActivits)){
//			logger.info("执行开始产品初始化功能！");
////						isTrueProducts = autoApiService.initProduct(office, AutoUtil.getUsersOrg());
//			logger.info("执行完成产品初始化功能！");
//		}
		
		if(isTrueProducts){
			logger.info("执行产品初始化成功！");
		}
	}

	/**
	 * @Description: 根据json文件初始化Admin数据列表
	 * @author Chenh
	 * @return  
	 * @throws
	 */
	@Transactional(readOnly = false)
	public Boolean initQHAdmin() {
		return false;
	}
	
	/**
	 * @Description: 根据json文件初始化公司列表服务 ,使用默认流程模板
	 * @author Chenh
	 * @param tenants
	 * @param offices
	 * @param roles
	 * @param formTpls
	 * @return  
	 * @throws
	 */
	@Transactional(readOnly = false)
	public Boolean initQHCompanys(List<Office> offices, List<Role> roles, List<DfFormTpl> formTpls) {
		List<TTenant> tenants = new ArrayList<TTenant>();
		return initQHCompanys(tenants, offices, roles, null, null, null, null);
	}

	/**
	 * @Description: 根据租户表初始化公司列表服务 ,使用默认流程模板
	 * @author Chenh
	 * @param tenants
	 * @param offices
	 * @param roles
	 * @param formTpls
	 * @return  
	 * @throws
	 */
	@Transactional(readOnly = false)
	public Boolean initQHCompanys(List<TTenant> tenants, List<Office> offices, List<Role> roles, List<DfFormTpl> formTpls) {
		return initQHCompanys(tenants, offices, roles, null, null, null, null);
	}
	
	/**
	 * @Description: 根据租户表初始化公司列表服务 
	 * @author Chenh
	 * @param tenants
	 * @param offices
	 * @param roles
	 * @param formTpls
	 * @return  
	 * @throws
	 */
	@Transactional(readOnly = false)
	public Boolean initQHCompanys(List<TTenant> tenants, List<Office> offices, List<Role> roles, List<DfFormTpl> formTplsDb, List<DfFormTpl> formTplsXd, String activitOrgDb, String activitOrgXd) {
		for (TTenant tenant : tenants) {
			List<Office> companys = new ArrayList<Office>();
			List<User> users = new ArrayList<User>();
			Office company = new Office();
			company.setBsys(tenant.getBsys());
			company.setName(tenant.getTenantName());
			company.setRemarks(tenant.getDescription());
			if(StringUtils.isNotEmpty(tenant.getType())){
				if((tenant.getType()).equals(DAI_KUAN)){
					company.setPrimaryPerson(Cons.CompanyType.DAI_KUAN);
				}else if((tenant.getType()).equals(DAN_BAO)){
					company.setPrimaryPerson(Cons.CompanyType.DAN_BAO);
				}else{
					logger.error("不能确定机构为担保或者小贷！");
				}
			}
			companys.add(company);

			User user = new User();
			user.setLoginName(tenant.getAdminName());
			user.setName(user.getLoginName());
			users.add(user);
			initCompanys(companys, offices, roles, users, formTplsDb, formTplsXd, activitOrgDb, activitOrgXd);
		}
		return true;
	}
	
	/**
	* @Description: 初始化公司列表服务 
	* @author Chenh
	* @param companys
	* @param offices
	* @param roles
	* @param users
	* @param formTpls
	* @param activitOrgDb
	* @param activitOrgXd
	* @return  
	* @throws
	 */
	@Transactional(readOnly = false)
	public Boolean initCompanys(List<Office> companys, List<Office> offices, List<Role> roles, List<User> users, List<DfFormTpl> formTplsDb, List<DfFormTpl> formTplsXd, String activitOrgDb, String activitOrgXd) {
		/**顶级机构**/
		Office pcompany = officeService.get(Cons.SysDF.DF_SUPER_COMPANY);
		/**默认区域机构**/
		Area parea = areaService.get(Cons.SysDF.DF_AREA);
		if((companys != null) && (companys.size() > 0)){
			for(Office company : companys){
				Boolean isTrueOffices = false;
				Boolean isTrueRoles = false;
				Boolean isTrueUsers = false;
				Boolean isTrueDbActivits = false;
				Boolean isTrueXdActivits = false;
				Boolean isTrueDbFormTpls = false;
				Boolean isTrueXdFormTpls = false;
				Boolean isTrueProducts = false;
				
				Office curCompany = initCompany(company, pcompany, parea);
				

				if((offices == null) || (offices.size() < 0)){
					offices = AutoUtil.getOfficesOrg();
				}
				isTrueOffices = initOffices(curCompany, offices);
				
				if(isTrueOffices){
					logger.info("初始化租户机构成功！");
					
					if((roles == null) || (roles.size() < 0)){
						roles = AutoUtil.getRolesOrg();
					}
					isTrueRoles = initRoles(company, roles);
				}
				
				if(isTrueRoles){
					logger.info("初始化租户角色成功！");
					
					if((users == null) || (users.size() < 0)){
						users = AutoUtil.getUsersOrg();
					}
					isTrueUsers = initUsers(company, users);
				}
				
				//初始化租户账户
				if(isTrueUsers){
					if((users == null) || (users.size() < 0)){
						users = AutoUtil.getUsersOrg();
					}
					initAccount(users.get(0).getId(), "2");
				}
				
				if(isTrueUsers){
					logger.info("初始化租户用户成功！");
					
					//下面开始初始化流程
					if((company.getPrimaryPerson()).equals(Cons.CompanyType.DAN_BAO)){
						if(StringUtils.isEmpty(activitOrgDb)){
							activitOrgDb = AutoUtil.getActivitOrgDb();
						}
						if((formTplsDb == null) || (formTplsDb.size() < 0)){
							formTplsDb = AutoUtil.getFormTplOrgDb();
						}

						isTrueDbFormTpls = initFormTpls(company, formTplsDb);
						isTrueDbActivits = initActivit(company, activitOrgDb, Cons.SysDF.DF_ADMIN_ROLE_DB);

						if(isTrueDbFormTpls){
							logger.info("初始化担保租户表单模板成功！");
						}
						if(isTrueDbActivits){
							logger.info("初始化担保租户流程成功！");
						}
					}else if((company.getPrimaryPerson()).equals(Cons.CompanyType.DAI_KUAN)){
						if(StringUtils.isEmpty(activitOrgXd)){
							activitOrgXd = AutoUtil.getActivitOrgXd();
						}
						if((formTplsXd == null) || (formTplsXd.size() < 0)){
							formTplsXd = AutoUtil.getFormTplOrgXd();
						} 
						
						isTrueXdFormTpls = initFormTpls(company, formTplsXd);
						isTrueXdActivits = initActivit(company, activitOrgXd, Cons.SysDF.DF_ADMIN_ROLE_XD);

						if(isTrueXdFormTpls){
							logger.info("初始化贷款租户表单模板成功！");
						}
						if(isTrueXdActivits){
							logger.info("初始化贷款租户流程成功！");
						}
					}
				}
				
				if(isTrueProducts){
					logger.info("执行产品初始化成功！");
				}
			}
			return true;
		}
		return false;
	}

	/**
	 * 初始化公司服务
	 * 要求数据结构：
	 *  [
 	 *	 	{
 	 *		    "name": "广州创世跬科金融信息科技有限公司",
 	 *		    "remarks": "禁止删除：顶级机构",
	 *		    "type": "1",
	 *		    "primaryPerson":"1"//可选
	 *	    }
	 *	 ] 
	 * @author Chenh
	 * @param company
	 * @throws
	 */
	@Transactional(readOnly = false)
	public Office initCompany(Office company, Office pcompany, Area area) {
		if(pcompany != null){
			if(company.getArea() == null){
				company.setArea(pcompany.getArea());
			}
			company.setParent(pcompany);
			company.setGrade(String.valueOf(Integer.valueOf(pcompany.getGrade())+1));
		}
		
		if(company.getParent() == null){
			company = null;
		}

		if(company.getArea() == null){
			if(area != null){
				company.setArea(area);
			}else{
				logger.error("区域不能为空！");
				company = null;
			}
		}
		
		if (StringUtils.isEmpty(company.getPrimaryPerson())) {
			logger.error("公司类型不能为空！");
			company = null;
		}
		
		if (StringUtils.isEmpty(company.getGrade())) {
			logger.error("机构等级不能为空！");
			company = null;
		}

		if(company != null){
			company.setUseable(Global.YES);
			company.setDelFlag(Global.NO);
			company.setType("1");//公司为1
			officeService.save(company);
		}
		return company;
	}
	
	/**
	 * 初始化部门列表服务
	 */
	@Transactional(readOnly = false)
	public Boolean initOffices(Office company, List<Office> offices){
		if(StringUtils.isNotEmpty(company.getId())){
			if(company.getArea() != null){
				if((offices != null) && (offices.size() > 0)){
					for(Office office : offices){
						initOffice(company, office);
					}
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * 
	 * @Description: 初始化部门服务
	 * 要求数据结构：
	 *  [
 	 *	 	{
 	 *		    "name": "广州创世跬科金融信息科技有限公司",
 	 *		    "remarks": "禁止删除：顶级机构",
	 *		    "type": "1",
	 *		    "primaryPerson":"1"//可选
	 *	    }
	 *	 ] 
	 * @author Chenh
	 * @param company
	 * @param office  
	 * @throws
	 */
	@Transactional(readOnly = false)
	public Boolean initOffice(Office company, Office office) {
		if(office == null){
			office = new Office();
		}
		
		if(StringUtils.isNotEmpty(office.getType())){
			if(((Cons.SysDF.DF_SUPER_OFFICE_TYPEORG).equals(office.getType()))){
				office.setType(Cons.SysDF.DF_SUPER_OFFICE_TYPEORG);
			}else{
				office.setType(Cons.SysDF.DF_SUPER_OFFICE_TYPEDEPART);
			}
			
			if(StringUtils.isNotEmpty(office.getName())){
				office.setParent(company);
				office.setArea(company.getArea());
				office.setPrimaryPerson(company.getPrimaryPerson());
				office.setGrade(String.valueOf(Integer.valueOf(company.getGrade())+1));
				office.setUseable(Global.YES);
				office.setDelFlag(Global.NO);
				company.setType("2");//部门为2
				officeService.save(office);
				return true;
			}
		}
		return false;
	}
    
	/**
	 * @Description:初始化角色列表服务
	 * 要求数据结构：
	 *  [
 	 *	 	{
 	 *		    "remarks": "",
 	 *		    "name": "客户经理",
	 *		    "enname": "khjl",
	 *		    "dataScope": "8"
	 *	    }
	 *	 ] 
	 * @author Chenh
	 * @param company
	 * @param user  
	 * @throws
	 */
	@Transactional(readOnly = false)
	public Boolean initRoles(Office company, List<Role> roles){
		Office office = checkHasZHB(company);
		if(office != null){
			if(StringUtils.isNotEmpty(company.getId())){
				if((roles != null) && (roles.size() > 0)){
					for(Role role : roles){
						role.setOffice(office);
						initRole(company, role);
					}
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * 初始化角色服务
	 */
	@Transactional(readOnly = false)
	public void initRole(Office company, Role role) {
		if((role != null) && (role.getOffice() != null) && StringUtils.isNotEmpty(role.getOffice().getId())){
			role.setOrganId(company.getId());
			systemService.saveRole(role);
			//角色创建成功后，初始化对应菜单
			String message = initMenu(company, role);
			logger.info(message);
		}else{
			logger.warn("机构("+company.getName()+")下的角色("+role.getName()+")所属部门不能为空！");
		}
		
	}
	/**
	 * 初始化用户列表服务
	 */
	@Transactional(readOnly = false)
	public Boolean initUsers(Office company, List<User> users){
		Office office = new Office();
		office.setParent(company);
		office.setParentIds(company.getId());
		List<Office> offices = officeService.getParentZHB(office);
		
		Role dbrole = systemService.getRole(Cons.SysDF.DF_ADMIN_ROLE_DB);
		Role xdrole = systemService.getRole(Cons.SysDF.DF_ADMIN_ROLE_XD);
		List<Role> roles = new ArrayList<Role>();
		if((offices != null) && (offices.size() > 0)){
			office = offices.get(0);
			if(StringUtils.isNotEmpty(company.getId())){
				if((users != null) && (users.size() > 0)){
					for(User user : users){
						user.setCompany(company);
						if((company.getPrimaryPerson()).equals(Cons.CompanyType.DAI_KUAN)){
							user.setRole(xdrole);
							roles.add(xdrole);
							if(StringUtils.isEmpty(user.getLoginName())){
								//user.setLoginName(SerialUtils.getOrderNo("admin1", "yMdhms"));
								user.setLoginName(company.getUserMap().get("userLoginName"));
								user.setName(company.getUserMap().get("userName"));
								user.setPassword(company.getUserMap().get("userPassword"));
							}
						}else if((company.getPrimaryPerson()).equals(Cons.CompanyType.DAN_BAO)){
							user.setRole(dbrole);
							roles.add(dbrole);
							if(StringUtils.isEmpty(user.getLoginName())){
								//user.setLoginName(SerialUtils.getOrderNo("admin2", "yMdhms"));
								user.setLoginName(company.getUserMap().get("userLoginName"));
								user.setName(company.getUserMap().get("userName"));
								user.setPassword(company.getUserMap().get("userPassword"));
							}
						}else{
							logger.warn("机构("+company.getName()+")下的用户("+user.getName()+")的角色不能为空！");
						}
						user.setOffice(office);
						user.setRoleList(roles);
						initUser(company, user);
					}
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * @Description:初始化用户服务:
	 * 要求数据结构：
	 *  [
 	 *	 	{
 	 *		    "remarks": "自动初始化租户",
 	 *		    "name": "张三丰11",
	 *		    "loginName": "12345678",
	 *		    "password": ""
	 *	    }
	 *	 ] 
	 * @author Chenh
	 * @param company
	 * @param user  
	 * @throws
	 */
	@Transactional(readOnly = false)
	public void initUser(Office company, User user) {
		if((user != null) && (user.getOffice() != null) && StringUtils.isNotEmpty(user.getOffice().getId())){
			if((user.getCompany() != null) && (user.getRole() != null) && (user.getOffice() != null) && StringUtils.isNotEmpty(user.getName()) && StringUtils.isNotEmpty(user.getLoginName())){
				user.setOrganId(company.getId());

				/**生成序列号*/
				if(StringUtils.isEmpty(user.getNo())){
					user.setNo(SerialUtils.getOrderNo());
				}
				/**生成密码*/
				if(StringUtils.isEmpty(user.getPassword())){
					user.setPassword(AutoUtil.AUTO_USER_PASSWORD);
				}
				/**设置默认类型为1*/
				if(StringUtils.isEmpty(user.getUserType())){
					user.setUserType(Cons.SysDF.DF_ADMIN_USERTYPE_MANAGER);
				}
				user.setPassword(SystemService.entryptPassword(user.getPassword()));
				systemService.saveUser(user);
			}else{
				logger.warn("机构("+company.getName()+")下的用户("+user.getName()+")的公司、部门、角色、名称、登录名不能为空！");
			}
		}else{
			logger.warn("机构("+company.getName()+")下的用户("+user.getName()+")所属部门不能为空！");
		}
	}
	
	
	/**
	 * 初始化表单模板列表服务
	 */
	@Transactional(readOnly = false)
	public Boolean initFormTpls(Office company, List<DfFormTpl> dfFormTpls) {
		if(company != null){
			for (DfFormTpl dfFormTpl : dfFormTpls) {
				initFormTpl(company, dfFormTpl);
			}
			return true;
		}
		return false;
	}
	
	/**
	 * 初始化表单模板服务
	 */
	@Transactional(readOnly = false)
	public Boolean initFormTpl(Office company, DfFormTpl dfFormTpl) {
		if(dfFormTpl != null){
			dfFormTpl.setOrganId(company.getId());
			dfFormTpl.setParent(null);
			dfFormTpl.setJson(dfFormTpl.getJson());
			dfFormTpl.setOffice(company);
			dfFormTpl.setOriginalhtml(dfFormTpl.getOriginalhtml());
			dfFormTpl.setParsehtml(dfFormTpl.getParsehtml());
			dfFormTpl.setRemarks(dfFormTpl.getSname());
			dfFormTpl.setSname(dfFormTpl.getSname());
			dfFormTpl.setType(dfFormTpl.getType());

			dfFormTpl.setRelId(null);
			dfFormTpl.setProduct(dfFormTpl.getProduct());
			dfFormTpl.setModel(dfFormTpl.getModel());
			dfFormTpl.setModsub(dfFormTpl.getModsub());
			
			dfFormTpl.setName(null);
			dfFormTpl.setRemarks(null);
			dfFormTplService.save(dfFormTpl);
			return true;
		}
		return false;
	}
	
	/**
	 * 初始化产品服务
	 */
	@Transactional(readOnly = false)
	public void initProduct(TProduct tProduct){
		tProductService.save(tProduct);
	}
	
	/**
	 * 初始化流程服务
	 */
	@Transactional(readOnly = false)
	public Boolean initActivit(Office company, String actRoute, String roleId) {
		String category = company.getId();
		String message = activitAuto(category, actRoute, roleId);
		if(!message.equals("")){
			return true;
		}else{
			return false;
		}
	}
	

	/**
	 * 初始化流程 
	 * @param file
	 * @return
	 */
	@Transactional(readOnly = false)
	public String activitAuto(String category, String actRoute, String roleId) {
		String message = "";
		User curZadmin = systemService.getZadmin(roleId, category);
		if(curZadmin != null){
			String prefix = curZadmin.getLoginName();
			File activitFile = new File(actRoute);
			File[] fileList = activitFile.listFiles();
			for(File file : fileList){
				message = actAutoService.deploy(file, category, actRoute, prefix);
			}
		}else{
			message = "租户未找到！";
		}
		return message;
	}
	
	/**
	 * 初始化菜单
	 * @param role
	 * @param organType
	 * @return
	 */
	@Transactional(readOnly = false)
	public String initMenu(Office company,Role role){
		String message = "";
		String route = "";
		List<String> streamList;
		if((company.getPrimaryPerson()).equals(Cons.CompanyType.DAN_BAO)){
			route = AutoUtil.getMenuOrgDb() + role.getEnname() + ".txt";
		}else if((company.getPrimaryPerson()).equals(Cons.CompanyType.DAI_KUAN)){
			route = AutoUtil.getMenuOrgXd() + role.getEnname() + ".txt";
		}
		if(!route.equals("")){
			try {
				streamList = Files.readAllLines(Paths.get(route), StandardCharsets.UTF_8);
				List<String> dataList = new ArrayList<String>();
				for(String s : streamList){
					dataList.add(s.replaceAll("\n", ""));
				}
				systemService.insertInitRoleMenu(role.getId(), dataList);
				message = role.getEnname()+"菜单初始化成功";
			} catch (IOException e) {
				message = role.getEnname()+"菜单初始化失败";
			}
		}
		return message;
	}
	
	/**
	 * 初始化账户
	 * @param userId
	 * @param accountType
	 */
	public void initAccount(String userId,String accountType){
		String url = Global.getWfwHttp() + "initAccount/initAccountByW/";
		JSONObject jo = new JSONObject();
		jo.element("keyId", userId);
		jo.element("accountType", accountType);
		HttpPostJson post = new HttpPostJson();
		String result = post.sendPostJson(url, jo.toString());
		logger.info(result);
	}
}
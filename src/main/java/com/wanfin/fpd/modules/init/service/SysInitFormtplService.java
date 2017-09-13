/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.init.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wanfin.fpd.common.config.AutoUtil;
import com.wanfin.fpd.common.config.Cons;
import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.service.CrudService;
import com.wanfin.fpd.common.utils.IdGen;
import com.wanfin.fpd.common.utils.StringUtils;
import com.wanfin.fpd.modules.form.entity.tpl.DfFormTpl;
import com.wanfin.fpd.modules.form.service.tpl.DfFormTplService;
import com.wanfin.fpd.modules.init.dao.SysInitFormtplDao;
import com.wanfin.fpd.modules.init.entity.SysInitFormtpl;
import com.wanfin.fpd.modules.init.vo.SysInitFormtplVo;
import com.wanfin.fpd.modules.sys.entity.Office;
import com.wanfin.fpd.modules.sys.entity.User;
import com.wanfin.fpd.modules.sys.service.OfficeService;
import com.wanfin.fpd.modules.sys.service.SystemService;

/**
 * 模板初始化Service
 * @author zxj
 * @version 2016-09-27
 */
@Service
@Transactional(readOnly = true)
public class SysInitFormtplService extends CrudService<SysInitFormtplDao, SysInitFormtpl> {
	@Autowired
	OfficeService officeService;
	@Autowired
	SystemService systemService;
	@Autowired
	DfFormTplService dfFormTplService;
	
	public SysInitFormtpl get(String id) {
		return super.get(id);
	}
	
	public List<SysInitFormtpl> findList(SysInitFormtpl sysInitFormtpl) {
		return super.findList(sysInitFormtpl);
	}
	
	public Page<SysInitFormtpl> findPage(Page<SysInitFormtpl> page, SysInitFormtpl sysInitFormtpl) {
		return super.findPage(page, sysInitFormtpl);
	}
	
	@Transactional(readOnly = false)
	public void save(SysInitFormtpl sysInitFormtpl) {
		super.save(sysInitFormtpl);
	}
	
	@Transactional(readOnly = false)
	public void delete(SysInitFormtpl sysInitFormtpl) {
		super.delete(sysInitFormtpl);
	}
	
	/**
	 * 获取公司列表数据
	 * @return
	 */
	public Map<String, List<Office>> getOfficeList(){
		List<Office> dataList = officeService.findAll();
		List<Office> dbList = new ArrayList<Office>();
		List<Office> xdList = new ArrayList<Office>();
		for(Office office : dataList){
			if(office.getId().equals(Cons.SysDF.DF_SUPER_COMPANY)){
				dbList.add(office);
				xdList.add(office);
			}
			if(office.getType().equals("1")&&office.getParentId().equals(Cons.SysDF.DF_SUPER_COMPANY)&&office.getPrimaryPerson().equals(Cons.CompanyType.DAN_BAO)){
				dbList.add(office);
			}else if(office.getType().equals("1")&&office.getParentId().equals(Cons.SysDF.DF_SUPER_COMPANY)&&office.getPrimaryPerson().equals(Cons.CompanyType.DAI_KUAN)){
				xdList.add(office);
			}
		}
		Map<String,List<Office>> dataMap = new HashMap<String,List<Office>>();
		dataMap.put("dbList", dbList);
		dataMap.put("xdList", xdList);
		return dataMap;
	}
	
	/**
	 * 获取表单模板
	 * @return
	 */
	public Map<String,List<DfFormTpl>> getFormTpls(){
		Map<String,List<DfFormTpl>> formMap = new HashMap<String,List<DfFormTpl>>();
		formMap.put("dbForms", AutoUtil.getFormTplOrgDb());
		formMap.put("xdForms", AutoUtil.getFormTplOrgXd());
		return formMap;
	}
	
	/**
	* @Description: 更新指定模板文件 
	* 注意：模板必须满足特定格式才能更新，否则无法更新！
	* @author Chenh
	* @param id	指定更新原模板
	* @param type 指定业务类型
	* @param tplModel 指定模板类型
	* @return  
	* @throws
	 */
	public List<DfFormTpl> writeFormTpls(DfFormTpl srcTpl, String type, String tplModel){
		if((srcTpl == null) || StringUtils.isEmpty(type) || StringUtils.isEmpty(tplModel)){
			return null;
		}
		
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setIgnoreDefaultExcludes(false);
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		jsonConfig.setExcludes(new String[] { "id", "delFlag", "office", "parent", "product", "page", "updateDate", "dbName", "area", "currentUser" , "sqlMap", "global", "updateBy", "createDate", "relId", "isNewRecord", "createBy", "organId"});
		

		JSONArray jo = null;
		List<DfFormTpl> dftpls = new ArrayList<DfFormTpl>();
		List<DfFormTpl> filerDftpls = new ArrayList<DfFormTpl>();
		if((Cons.CompanyType.DAI_KUAN).equals(type)){
			dftpls = AutoUtil.getFormTplOrgXd();
			if((dftpls != null) && (dftpls.size() > 0)){
				for (DfFormTpl dfFormTpl : dftpls) {
					if((dfFormTpl.getModel()).equals(tplModel)){
						dfFormTpl.setJson(srcTpl.getJson());
						dfFormTpl.setOriginalhtml(srcTpl.getOriginalhtml());
						dfFormTpl.setParsehtml(srcTpl.getParsehtml());
						filerDftpls.add(dfFormTpl);
					}
				}

				jo = JSONArray.fromObject(dftpls, jsonConfig);
				AutoUtil.writeFormTplOrgXd(jo);
				System.out.println("OrgXd模板更新成功！");
			}else{
				System.out.println("------------------------------OrgXd模板更新失败------------------------------");
			}
		}else if((Cons.CompanyType.DAN_BAO).equals(type)){
			dftpls = AutoUtil.getFormTplOrgDb();
			if((dftpls != null) && (dftpls.size() > 0)){
				for (DfFormTpl dfFormTpl : dftpls) {
					if((dfFormTpl.getModel()).equals(tplModel)){
						dfFormTpl.setJson(srcTpl.getJson());
						dfFormTpl.setOriginalhtml(srcTpl.getOriginalhtml());
						dfFormTpl.setParsehtml(srcTpl.getParsehtml());
						filerDftpls.add(dfFormTpl);
					}
				}
				jo = JSONArray.fromObject(dftpls, jsonConfig);
				AutoUtil.writeFormTplOrgDb(jo);
				System.out.println("OrgDb模板更新成功！");
			}else{
				System.out.println("------------------------------OrgDb模板更新失败------------------------------");
			}
		}
		return filerDftpls;
	}
	
	/**
	* @Description: 更新产品模板文件 
	* 如果指定ID以指定模板更新，
	* 如果没有指定，使用超级管理员默认模板
	* @author Chenh
	* @param id	指定更新原模板
	* @param type 指定业务类型
	* @return  
	* @throws 
	 */
	public List<DfFormTpl> writeFormTplsToProduct(String id, String type){
		if(StringUtils.isEmpty(type)){
			return null;
		}
		if(StringUtils.isEmpty(id)){
			if(type.equals(Cons.CompanyType.DAI_KUAN)){
				id = Cons.FormTplId.WZH_PRODUCT_FORM_ID;
			}else if(type.equals(Cons.CompanyType.DAN_BAO)){
				id = Cons.FormTplId.WZH_PRODUCT_FORM_ID;
			}
		}
		DfFormTpl dfFormTpl = dfFormTplService.get(id);
		if(dfFormTpl == null){
			System.out.println("系统产品模板("+id+")在数据库中不存在！");
			return null;
		}
		return writeFormTpls(dfFormTpl, type, Cons.FModel.M_PRODUCT.getKey());
	}
	
	/**
	* @Description: 更新产品模板文件 
	* 如果指定ID以指定模板更新，
	* 如果没有指定，使用超级管理员默认模板
	* @author Chenh
	* @param id	指定更新原模板
	* @param type 指定业务类型
	* @return  
	* @throws
	 */
	public List<DfFormTpl> writeFormTplsToYWSQ(String id, String type){
		if(StringUtils.isEmpty(type)){
			return null;
		}
		if(StringUtils.isEmpty(id)){
			if(type.equals(Cons.CompanyType.DAI_KUAN)){
				id = Cons.FormTplId.WZH_XD_LOANCONTRACT_FORM_ID;
			}else if(type.equals(Cons.CompanyType.DAN_BAO)){
				id = Cons.FormTplId.WZH_DB_LOANCONTRACT_FORM_ID;
			}
		}
		DfFormTpl dfFormTpl = dfFormTplService.get(id);
		if(dfFormTpl == null){
			System.out.println("系统业务申请模板("+id+")在数据库中不存在！");
			return null;
		}
		return writeFormTpls(dfFormTpl, type, Cons.FModel.M_BUSINESS_APPLICATION.getKey());
	}
	
	/**
	 * 更新表单模板
	 * @param sysInitFormtpl
	 * @return
	 */
	@Transactional(readOnly = false)
	public boolean formUpdate(SysInitFormtpl sysInitFormtpl){
		String roleId = "";
		boolean updateCheck = false;
		boolean step = false;
		sysInitFormtpl.preInsert();
		String[] forms = sysInitFormtpl.getFormName().split(";");
		String[] officeIds = sysInitFormtpl.getOfficeName().split(";");
		List<DfFormTpl> formList = new ArrayList<DfFormTpl>();
		//更新模板
		if(sysInitFormtpl.getOfficeType().equals(Cons.CompanyType.DAN_BAO)){
			formList = AutoUtil.getFormTplOrgDb();
		}else if(sysInitFormtpl.getOfficeType().equals(Cons.CompanyType.DAI_KUAN)){
			formList = AutoUtil.getFormTplOrgXd();
		}
		out:for(String form : forms){
			for(DfFormTpl dfFormTpl : formList){
				if(dfFormTpl.getModel().equals(form)){
					dfFormTpl.setRemarks("初始化更新(1)");
					dfFormTpl.setUpdateDate(new Date());
					if(dfFormTpl.getOriginalhtml().contains("最多贷款期限")){
						System.out.println("---------------------------");
					}
					
					try {
						DfFormTpl filterTpl = new DfFormTpl();
						filterTpl.setModel(dfFormTpl.getModel());
						List<DfFormTpl> filterTpls = dfFormTplService.findInitTplListByOrgan(new SysInitFormtplVo(filterTpl, Arrays.asList(officeIds)));
						if((filterTpls != null) && (filterTpls.size() > 0)){
							dfFormTplService.updateByInit(dfFormTpl, officeIds);
							updateCheck = true;
						}else{
							System.out.println("--------------------------------------------------");
							System.out.println("没找到需要更新的模板");
							System.out.println("--------------------------------------------------");
							updateCheck = false;
							break out;
						}
					} catch (Exception e) {
						updateCheck = false;
						break out;
					}
				}
			}
		}
		
		//插入更新模板记录
		if(updateCheck){
			List<Map<String,String>> list = new ArrayList<Map<String,String>>();
			for(String officeId : officeIds){
				if(!officeId.equals(Cons.SysDF.DF_SUPER_COMPANY)){
					Office office = officeService.get(officeId);
					if(office.getPrimaryPerson().equals(Cons.CompanyType.DAN_BAO)){
						roleId= Cons.SysDF.DF_ADMIN_ROLE_DB;
					}else if(office.getPrimaryPerson().equals(Cons.CompanyType.DAI_KUAN)){
						roleId= Cons.SysDF.DF_ADMIN_ROLE_XD;
					}
					User curZadmin = null;
					try {
						curZadmin = systemService.getZadmin(roleId, officeId);
					} catch (Exception e) {
						System.out.println("租户获取异常-"+roleId+"|"+officeId);
					}
					if(curZadmin!=null){
						for(String form : forms){
							Map<String,String> map = new HashMap<String,String>();
							map.put("id", IdGen.uuid());
							map.put("officeName", office.getName());
							map.put("organId",curZadmin.getLoginName());
							map.put("form", form);
							map.put("officeId", office.getId());
							map.put("relateId", dfFormTplService.getFormTplId(form, office.getId()).getId());
							list.add(map);
						}
						step = true;
					}else{
						step = false;
						break;
					}
				}
			}
			if(step){
				this.dao.saveAllData(sysInitFormtpl,list);
				return true;
			}else{
				return false;
			}
		}else{
			return false;
		}
	}
	
	/**
	 * 通过parentId获取关联模板 
	 * @param relateId
	 * @return
	 */
	public List<DfFormTpl> getRelateDfFormTpl(String relateId){
		return dfFormTplService.getRelateDfFormTpl(relateId);
	} 
	
	/**
	 * 通过标准模板更新关联模板
	 * @param officeId
	 * @param model
	 * @param relateIds
	 */
	@Transactional(readOnly = false)
	public boolean updateRelate(SysInitFormtpl sysInitFormtpl,String[] relateIds){
		try {
			DfFormTpl dfFormTpl = dfFormTplService.get(sysInitFormtpl.getRelateId());
			dfFormTpl.setRemarks("初始化同步更新");
			dfFormTpl.setUpdateDate(new Date());
			dfFormTplService.updateRelate(dfFormTpl, relateIds);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
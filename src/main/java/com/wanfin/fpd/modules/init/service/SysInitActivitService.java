/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.init.service;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sun.mail.handlers.message_rfc822;
import com.wanfin.fpd.common.config.AutoUtil;
import com.wanfin.fpd.common.config.Cons;
import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.service.CrudService;
import com.wanfin.fpd.common.utils.IdGen;
import com.wanfin.fpd.modules.act.service.ActAutoService;
import com.wanfin.fpd.modules.init.entity.SysInitActivit;
import com.wanfin.fpd.modules.init.dao.SysInitActivitDao;
import com.wanfin.fpd.modules.sys.entity.Office;
import com.wanfin.fpd.modules.sys.entity.User;
import com.wanfin.fpd.modules.sys.service.OfficeService;
import com.wanfin.fpd.modules.sys.service.SystemService;

/**
 * 流程初始化Service
 * @author zxj
 * @version 2016-09-22
 */
@Service
@Transactional(readOnly = true)
public class SysInitActivitService extends CrudService<SysInitActivitDao, SysInitActivit> {
	@Autowired
	SystemService systemService;
	@Autowired
	ActAutoService actAutoService;
	@Autowired
	OfficeService officeService;
	
	public SysInitActivit get(String id) {
		return super.get(id);
	}
	
	public List<SysInitActivit> findList(SysInitActivit sysInitActivit) {
		return super.findList(sysInitActivit);
	}
	
	public Page<SysInitActivit> findPage(Page<SysInitActivit> page, SysInitActivit sysInitActivit) {
		return super.findPage(page, sysInitActivit);
	}
	
	@Transactional(readOnly = false)
	public void save(SysInitActivit sysInitActivit) {
		super.save(sysInitActivit);
	}
	
	@Transactional(readOnly = false)
	public void delete(SysInitActivit sysInitActivit) {
		super.delete(sysInitActivit);
	}
	
	/**
	 * 保存流程文件
	 * @param sysInitActivit
	 * @return
	 */
	@Transactional(readOnly = false)
	public boolean activitSave(SysInitActivit sysInitActivit,String[] officeIds){
		File actFile = new File("");
		String actRoute = "";
		String roleId = "";
		boolean step = false;
		sysInitActivit.preInsert();
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		for(String officeName : officeIds){
			if(!officeName.equals(Cons.SysDF.DF_SUPER_COMPANY)){
				Map<String,String> map = new HashMap<String,String>();
				Office office = officeService.get(officeName);
				if(office.getPrimaryPerson().equals("1")){
					roleId= Cons.SysDF.DF_ADMIN_ROLE_DB;
					actRoute = AutoUtil.getActivitOrgDb();
					actFile = new File(AutoUtil.getActivitOrgDb() + sysInitActivit.getFileName());
				}else if(office.getPrimaryPerson().equals("2")){
					roleId= Cons.SysDF.DF_ADMIN_ROLE_XD;
					actRoute = AutoUtil.getActivitOrgXd();
					actFile = new File(AutoUtil.getActivitOrgXd() + sysInitActivit.getFileName());
				}
				User curZadmin = systemService.getZadmin(roleId, officeName);
				String message = actAutoService.deploy(actFile, officeName, actRoute, curZadmin.getLoginName());
				if(message.contains("部署成功")){
					map.put("id", IdGen.uuid());
					map.put("officeName", office.getName());
					map.put("organId",curZadmin.getLoginName());
					list.add(map);
					step = true;
				}else{
					step = false;
					break;
				}
			}
		}
		if(step){
			this.dao.saveAllData(sysInitActivit,list);
			return true;
		}else{
			return false;
		}
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
			if(office.getType().equals("1")&&office.getParentId().equals(Cons.SysDF.DF_SUPER_COMPANY)&&office.getPrimaryPerson().equals("1")){
				dbList.add(office);
			}else if(office.getType().equals("1")&&office.getParentId().equals(Cons.SysDF.DF_SUPER_COMPANY)&&office.getPrimaryPerson().equals("2")){
				xdList.add(office);
			}
		}
		Map<String,List<Office>> dataMap = new HashMap<String,List<Office>>();
		dataMap.put("dbList", dbList);
		dataMap.put("xdList", xdList);
		return dataMap;
	}

}
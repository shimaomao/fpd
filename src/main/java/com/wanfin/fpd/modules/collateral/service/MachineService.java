/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.collateral.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.service.CrudService;
import com.wanfin.fpd.modules.collateral.dao.MachineDao;
import com.wanfin.fpd.modules.collateral.entity.Machine;

/**
 * 机器设备Service
 * @author srf
 * @version 2016-03-24
 */
@Service
@Transactional(readOnly = true)
public class MachineService extends CrudService<MachineDao, Machine> {

	@Autowired
	protected MachineDao machineDao;
	
	public Machine get(String id) {
		return super.get(id);
	}
	
	public Machine getByPledge(String id) {
		return machineDao.getByPledge(id);
	}
	
	public List<Machine> findList(Machine machine) {
		return super.findList(machine);
	}
	
	public Page<Machine> findPage(Page<Machine> page, Machine machine) {
		return super.findPage(page, machine);
	}
	
	@Transactional(readOnly = false)
	public void save(Machine machine) {
		super.save(machine);
	}
	
	@Transactional(readOnly = false)
	public void delete(Machine machine) {
		super.delete(machine);
	}
	
}
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
import com.wanfin.fpd.modules.collateral.dao.CarDao;
import com.wanfin.fpd.modules.collateral.entity.Car;

/**
 * 车辆信息Service
 * @author srf
 * @version 2016-03-24
 */
@Service
@Transactional(readOnly = true)
public class CarService extends CrudService<CarDao, Car> {

	@Autowired
	protected CarDao carDao;
	
	public Car get(String id) {
		return super.get(id);
	}
	
	public Car getByPledge(String id) {
		return carDao.getByPledge(id);
	}
	
	public List<Car> findList(Car car) {
		return super.findList(car);
	}
	
	public Page<Car> findPage(Page<Car> page, Car car) {
		return super.findPage(page, car);
	}
	
	@Transactional(readOnly = false)
	public void save(Car car) {
		super.save(car);
	}
	
	@Transactional(readOnly = false)
	public void delete(Car car) {
		super.delete(car);
	}
	
}
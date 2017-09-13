/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.credit.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.service.CrudService;
import com.wanfin.fpd.common.service.ServiceException;
import com.wanfin.fpd.common.utils.StringUtils;
import com.wanfin.fpd.modules.credit.dao.CustomerCreditDao;
import com.wanfin.fpd.modules.credit.entity.CustomerCredit;

/**
 * 客户授信额度Service
 * @author zzm
 * @version 2016-07-13
 */
@Service
@Transactional(readOnly = true)
public class CustomerCreditService extends CrudService<CustomerCreditDao, CustomerCredit> {

	public CustomerCredit get(String id) {
		return super.get(id);
	}
	
	public CustomerCredit getByCustorId(String customerId) {
		return dao.getByCustorId(customerId);
	}
	
	public List<CustomerCredit> findList(CustomerCredit customerCredit) {
		return super.findList(customerCredit);
	}
	
	public Page<CustomerCredit> findPage(Page<CustomerCredit> page, CustomerCredit customerCredit) {
		return super.findPage(page, customerCredit);
	}
	
	@Transactional(readOnly = false)
	public void save(CustomerCredit customerCredit) {
		super.save(customerCredit);
	}
	
	@Transactional(readOnly = false)
	public void delete(CustomerCredit customerCredit) {
		super.delete(customerCredit);
	}
	
	@Transactional(readOnly = false)
	public void deleteByCondition(CustomerCredit customerCredit) {
		dao.deleteByCondition(customerCredit);
	}
	
	/**
	 * @Description 客户授信
	 * @param customerId	客户id
	 * @param credit	授信额度	
	 * @param balance	剩余可用余额
	 * @param score		评分
	 * @param overDate	有效截止时间
	 * @param relId		此次授信关联id
	 * @author zzm
	 * @date 2016-7-13 下午6:34:10  
	 */
	@Transactional(readOnly = false)
	public void addCustomerCredit(String customerId, BigDecimal credit, BigDecimal balance, Double score, Date overDate, String relId) {
		CustomerCredit customerCredit = getByCustomerId(customerId);
		if(customerCredit==null){
			customerCredit = new CustomerCredit();
		}
		customerCredit.setCustomerId(customerId);
		customerCredit.setCredit(credit);
		customerCredit.setBalance(balance);
		customerCredit.setScore(score);
		customerCredit.setOverDate(overDate);
		customerCredit.setRelId(relId);
		customerCredit.setCreditDate(new Date());
		save(customerCredit);
	}
	
	/**
	 * 根据客户id和产品ID获取客户的授信额度记录
	 * @param customerId
	 * @param productId
	 * @return
	 */
	public CustomerCredit getByCustomerProductId(String customerId,String productId){
		if(StringUtils.isAnyBlank(customerId, productId)){
			throw new ServiceException("查询授信记录失败");
		}
		CustomerCredit credit = new CustomerCredit();
		credit.setCustomerId(customerId);
		credit.setProductId(productId);
		List<CustomerCredit> list = this.findList(credit);
		if(list != null && !list.isEmpty()){
			return list.get(0);
		}else{
			return null;
		}
	}
	
	/**
	 * @Description 根据客户id获取客户的授信额度记录
	 * @param customerId
	 * @return
	 * @author zzm
	 * @date 2016-7-13 下午6:08:03  
	 */
	public CustomerCredit getByCustomerId(String customerId){
		CustomerCredit credit = new CustomerCredit();
		credit.setCustomerId(customerId);
		List<CustomerCredit> list = this.findList(credit);
		if(list != null && !list.isEmpty()){
			return list.get(0);
		}else{
			return null;
		}
	}
	
	/**
	 * 使用授信额度
	 * @param customerId
	 * @param amount
	 * @throws ServiceException
	 */
	@Transactional(readOnly = false)
	public String useCustomerCredit(String customerId, BigDecimal amount) throws ServiceException{
		String res="sucess";
		CustomerCredit credit = this.getByCustomerId(customerId);
		if(credit == null || credit.getBalance() == null){
		            res="客户没有授信记录";
		}else if(credit.getBalance().compareTo(amount) < 0){
		            res="【"+amount+"】"+"超出了客户的可用授信额度【"+credit.getBalance()+"】";
		}
		
		if(res.equals("sucess")){
			credit.setBalance(credit.getBalance().subtract(amount));//subtract减去可用授信额度
			save(credit);
		}
		return res;
	}
}
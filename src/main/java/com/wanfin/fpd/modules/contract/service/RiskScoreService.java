/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.contract.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.service.CrudService;
import com.wanfin.fpd.modules.contract.dao.RiskScoreDao;
import com.wanfin.fpd.modules.contract.entity.RiskScore;
import com.wanfin.fpd.modules.contract.entity.TLoanContract;

/**
 * 风险控制评分Service
 * @author zzm
 * @version 2016-06-17
 */
@Service
@Transactional(readOnly = true)
public class RiskScoreService extends CrudService<RiskScoreDao, RiskScore> {
	
	@Autowired
	private TLoanContractService tLoanContractService;

	public RiskScore get(String id) {
		return super.get(id);
	}
	
	public List<RiskScore> findList(RiskScore riskScore) {
		return super.findList(riskScore);
	}
	
	public Page<RiskScore> findPage(Page<RiskScore> page, RiskScore riskScore) {
		return super.findPage(page, riskScore);
	}
	
	@Transactional(readOnly = false)
	public void save(RiskScore riskScore) {
		super.save(riskScore);
	}
	
	@Transactional(readOnly = false)
	public void delete(RiskScore riskScore) {
		super.delete(riskScore);
	}
	
	/**
	 * @Description 根据业务id获取风险控制评分
	 * 			先通过风控接口获取，如果风控获取不到；在从本地获取
	 * @param loanContractId
	 * @param category
	 * @return
	 * @author zzm
	 * @date 2016-6-17 下午4:15:17  
	 */
	@Transactional(readOnly = false)
	public RiskScore getRiskScoreByB(RiskScore riskScore){
		TLoanContract contract = tLoanContractService.get(riskScore.getLoanContractId());
		List<RiskScore> list = findList(riskScore);
		if(list != null && list.size() > 0){
			return list.get(0);
		}else{
			Double amount = Double.valueOf(contract.getLoanAmount());
			if(amount <= 5000){
				riskScore.setGrade((double)(400+100*(amount/5000)));
				riskScore.setRpn("D");
			}else if(amount <= 10000){
				riskScore.setGrade((double)(500+100*(amount/10000)));
				riskScore.setRpn("C");
			}else if(amount <= 50000){
				riskScore.setGrade((double)(600+100*(amount/50000)));
				riskScore.setRpn("B");
			}else if(amount <= 100000){
				riskScore.setGrade((double)(700+100*(amount/100000)));
				riskScore.setRpn("B+");
			}else {
				riskScore.setGrade((double)(800+200*(Double.valueOf(amount.toString().substring(0, 2))/100)));
				riskScore.setRpn("A+");
			}
			return riskScore;
		}
	}
	
}
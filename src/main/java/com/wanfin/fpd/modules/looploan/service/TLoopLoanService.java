/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.looploan.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Maps;
import com.wanfin.fpd.common.config.Cons;
import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.service.CrudService;
import com.wanfin.fpd.common.service.ServiceException;
import com.wanfin.fpd.common.utils.StringUtils;
import com.wanfin.fpd.modules.act.service.ActTaskService;
import com.wanfin.fpd.modules.files.service.TContractFilesService;
import com.wanfin.fpd.modules.looploan.dao.TLoopLoanDao;
import com.wanfin.fpd.modules.looploan.entity.TLoopLoan;
import com.wanfin.fpd.modules.product.entity.TProduct;
import com.wanfin.fpd.modules.sys.utils.UserUtils;

/**
 * 申请授信Service
 * @author zzm
 * @version 2016-03-17
 */
@Service
@Transactional(readOnly = true)
public class TLoopLoanService extends CrudService<TLoopLoanDao, TLoopLoan> {
	
	@Autowired
	private TLoopLoanDao tLoopLoanDao;
	@Autowired
	private ActTaskService actTaskService;
	@Autowired
	private TContractFilesService tContractFilesService;

	public TLoopLoan get(String id) {
		return super.get(id);
	}
	
	public List<TLoopLoan> findList(TLoopLoan tLoopLoan) {
		return super.findList(tLoopLoan);
	}
	
	public Page<TLoopLoan> findPage(Page<TLoopLoan> page, TLoopLoan tLoopLoan) {
		return super.findPage(page, tLoopLoan);
	}
	
	@Transactional(readOnly = false)
	public void save(TLoopLoan tLoopLoan) {
		String tempId = null;
		if(StringUtils.isNotBlank(tLoopLoan.getId()) && tLoopLoan.getId().startsWith("new_")){
			//新增，且有临时id
			tempId = tLoopLoan.getId();
			tLoopLoan.setId(null);
			tLoopLoan.setProduct(new TProduct((String)UserUtils.getCache(UserUtils.CACHE_SYSCODE)));
		}
		if(StringUtils.isBlank(tLoopLoan.getId()))
			tLoopLoan.setStatus(Cons.LoopLoanStatus.TO_APPLY);//新增时是待申请状态
		super.save(tLoopLoan);
		
		if(StringUtils.isNotBlank(tempId)){
			//关联附件
			tContractFilesService.updateFileTaskId(tempId,tLoopLoan.getId());
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("businessId", tLoopLoan.getId());
			map.put("id", tempId);
			//关联担保信息
			map.put("table", "t_guarantee_contract");
			tLoopLoanDao.updateBusinessId(map);
			//关联抵押信息
			map.put("table", "t_mortgage_contract");
			tLoopLoanDao.updateBusinessId(map);
			//关联质押信息
			map.put("table", "t_pledge_contract");
			tLoopLoanDao.updateBusinessId(map);
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(TLoopLoan tLoopLoan) {
		super.delete(tLoopLoan);
	}
	
	@Transactional(readOnly = false)
	public String startProcess(TLoopLoan tLoopLoan) {
		// 启动流程
		return actTaskService.startProcess(tLoopLoan.getAct().getProcDefKey(), "t_loop_loan", tLoopLoan.getId(), tLoopLoan.getLoopNumber());
	}
	
	
	/**
	 * 审核审批保存
	 * @param tLoopLoan
	 */
	@Transactional(readOnly = false)
	public void auditSave(TLoopLoan tLoopLoan) {
		
		// 设置意见
		tLoopLoan.getAct().setComment(("yes".equals(tLoopLoan.getAct().getFlag())?"[同意] ":"[驳回] ")+tLoopLoan.getAct().getComment());

		// 提交流程任务
		Map<String, Object> vars = Maps.newHashMap();
		vars.put("pass", "yes".equals(tLoopLoan.getAct().getFlag())? "1" : "0");
		actTaskService.complete(tLoopLoan.getAct().getTaskId(), tLoopLoan.getAct().getProcInsId(), tLoopLoan.getAct().getComment(), vars);
		
	}

	/**
	 * 移除数据关联
	 * 功能：把表table里id在ids里的business_table、business_id字段置为null
	 * @param ids
	 * @param table
	 * @param column
	 * @return
	 */
	@Transactional(readOnly = false)
	public int remmoveBanding(String[] ids, String table) {
		if(ids == null || ids.length == 0 || StringUtils.isBlank(table)){
			throw new ServiceException("数据不完整");
		}
		
		for(String id : ids){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("table", table);
			map.put("id", id);
			tLoopLoanDao.remmoveBanding(map);
		}
		
		return 0;
	}
	
}
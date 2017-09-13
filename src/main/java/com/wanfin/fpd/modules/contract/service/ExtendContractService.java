/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.contract.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Maps;
import com.wanfin.fpd.common.config.Cons;
import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.service.CrudService;
import com.wanfin.fpd.common.utils.StringUtils;
import com.wanfin.fpd.modules.act.service.ActTaskService;
import com.wanfin.fpd.modules.contract.dao.ExtendContractDao;
import com.wanfin.fpd.modules.contract.entity.ExtendContract;
import com.wanfin.fpd.modules.contract.entity.ExtendPlan;
import com.wanfin.fpd.modules.files.service.TContractFilesService;

/**
 * 展期合同Service
 * @author zzm
 * @version 2016-04-01
 */
@Service
@Transactional(readOnly = true)
public class ExtendContractService extends CrudService<ExtendContractDao, ExtendContract> {
	
	@Autowired
	private ExtendContractDao extendContractDao;

	@Autowired
	private TContractFilesService tContractFilesService;
	
	@Autowired
	private ExtendPlanService extendPlanService;
	
	@Autowired
	private ActTaskService actTaskService;
	
	public ExtendContract get(String id) {
		return super.get(id);
	}
	
	public List<ExtendContract> findList(ExtendContract extendContract) {
		return super.findList(extendContract);
	}
	
	public Page<ExtendContract> findPage(Page<ExtendContract> page, ExtendContract extendContract) {
		return super.findPage(page, extendContract);
	}
	
	/** 
	 * @Description 保存展期合同，并启动流程、执行第一个任务（提交申请）
	 * @see com.wanfin.fpd.common.service.CrudService#save(com.wanfin.fpd.common.persistence.DataEntity)
	 * @param extendContract	展期合同对象
	 * @author zzm 
	 * @date 2016-4-6 上午10:13:48  
	 */
	@Transactional(readOnly = false)
	public void save(ExtendContract extendContract) {
		String tempId = null;//新增是临时设置的id
		if(extendContract.getId().startsWith("new_")){
			//新增，且有临时设置的id时
			tempId = extendContract.getId();
			extendContract.setId(null);
		}
		if(StringUtils.isBlank(extendContract.getId())){
			//获取当前贷款合同最大展期次数；
			Integer maxNum = extendContractDao.getMaxNum(extendContract.getLoanContract().getId());
			extendContract.setNum( (maxNum!=null && maxNum > 0) ? (maxNum+1) : 1 );
			extendContract.setStatus(Cons.ExtendContractStatus.TO_REVIEW);
			super.save(extendContract);
		}
		
		//保存展期还款计划
		List<ExtendPlan> planList = extendContract.getExtendPlanList();
		if(planList != null && planList.size() > 0){
			for(ExtendPlan extendPlan : planList){
				extendPlan.setExtendContractId(extendContract.getId());
				extendPlan.setStatus(Cons.RepayStatus.NO_PAID);
				extendPlanService.save(extendPlan);
			}
		}
		
		if(StringUtils.isNotBlank(tempId)){
			//关联附件
			tContractFilesService.updateFileTaskId(tempId,extendContract.getId());
		}
		if(StringUtils.isBlank(extendContract.getProcInsId())){
			// 启动并提交流程
			//TODO @zzm 流程定义KEY、业务表表名要通过数据库配置，这里暂时写死  
			String procDefKey = "extend_contract";//流程定义KEY
			String businessTable = "t_extend_contract";//业务表表名
			String procInsId = actTaskService.startProcess(procDefKey, businessTable, extendContract.getId(), extendContract.getContractNumber());
			actTaskService.completeFirstTask(procInsId, extendContract.getAct().getComment(), 
					extendContract.getContractNumber(), extendContract.getAct().getVars().getVariableMap());
		}else{
			//执行任务
			// 设置意见
			extendContract.getAct().setComment(("1".equals(extendContract.getAct().getFlag())?"[同意] ":"[驳回] ")+extendContract.getAct().getComment());
			// 提交流程任务
			Map<String, Object> vars = Maps.newHashMap();
			vars.put("pass", extendContract.getAct().getFlag());
			actTaskService.complete(extendContract.getAct().getTaskId(), extendContract.getAct().getProcInsId(), extendContract.getAct().getComment(), vars);
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(ExtendContract extendContract) {
		super.delete(extendContract);
	}
	
}
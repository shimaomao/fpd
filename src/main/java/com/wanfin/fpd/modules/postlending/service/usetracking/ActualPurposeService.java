/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.postlending.service.usetracking;

import java.util.List;
import java.util.Map;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Maps;
import com.wanfin.fpd.common.config.Cons;
import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.service.CrudService;
import com.wanfin.fpd.modules.postlending.entity.usetracking.ActualPurpose;
import com.wanfin.fpd.modules.act.service.ActTaskService;
import com.wanfin.fpd.modules.files.service.TContractFilesService;
import com.wanfin.fpd.modules.postlending.dao.usetracking.ActualPurposeDao;
import com.wanfin.fpd.modules.product.entity.TProduct;
import com.wanfin.fpd.modules.sys.utils.UserUtils;

/**
 * 用途跟踪Service
 * @author srf
 * @version 2016-04-09
 */
@Service
@Transactional(readOnly = true)
public class ActualPurposeService extends CrudService<ActualPurposeDao, ActualPurpose> {

	@Autowired
	private ActTaskService actTaskService;
	@Autowired
	private TContractFilesService contractFilesService;
	@Autowired
	private RuntimeService runtimeService;
	
	
	public ActualPurpose get(String id) {
		return super.get(id);
	}
	
	public ActualPurpose getByProcInsId(String  procInsId){
		return dao.getByProcInsId(procInsId);
	}
	
	public List<ActualPurpose> findList(ActualPurpose actualPurpose) {
		return super.findList(actualPurpose);
	}
	
	public Page<ActualPurpose> findPage(Page<ActualPurpose> page, ActualPurpose actualPurpose) {
		return super.findPage(page, actualPurpose);
	}
	
	/**
	 * 新增并启动流程
	 */
	@Transactional(readOnly = false)
	public void save(ActualPurpose actualPurpose) {
		//判断是否为临时ID
		String tempID = null;
		if(StringUtils.isNotBlank(actualPurpose.getId()) && actualPurpose.getId().startsWith("tmp_")){
			tempID = actualPurpose.getId();
			actualPurpose.setId(null);
			actualPurpose.setProduct(new TProduct((String)UserUtils.getCache(UserUtils.CACHE_SYSCODE)));
		}

		//申请发起
		if(StringUtils.isBlank(actualPurpose.getId())){
			//状态：待审批
			actualPurpose.setStatus(Cons.ContractStatus.PENDING);
			
			actualPurpose.preInsert();
			dao.insert(actualPurpose);
			actualPurpose.getLoanContractId();
			//启动流程
			String procInsId = actTaskService.startProcess(actualPurpose.getAct().getProcDefKey(), "t_actual_purpose", actualPurpose.getId(), "贷后检查审核【"+actualPurpose.getTitle()+"】");
			if(StringUtils.isNotBlank(procInsId)){
				//启动第一个任务
				actTaskService.completeFirstTask(procInsId);
			}
		}
		
		super.save(actualPurpose);
		
		if(StringUtils.isNotBlank(tempID)){
			//调整关联附件表
			contractFilesService.updateFileTaskId(tempID, actualPurpose.getId());
		}
	}
	
	/**
	 * 审核
	 */
	@Transactional(readOnly = false)
	public void auditSave(ActualPurpose actualPurpose){
		//设置意见
		actualPurpose.getAct().setComment(("yes".equals(actualPurpose.getAct().getFlag())?"[同意] ":"[驳回] ")+actualPurpose.getAct().getComment());
/*		
		//对不同环节的业务逻辑进行操作
		//String taskDefKey = actualPurpose.getAct().getTaskDefKey();
		if("yes".equals(actualPurpose.getAct().getFlag())){
			actualPurpose.setStatus(Cons.ContractStatus.APPROVAL);
		}else{
			actualPurpose.setStatus(Cons.ContractStatus.NOT_PASS);
		}
		
		//保存新的状态
		//super.dao.updateStatus(actualPurpose);
		super.save(actualPurpose);*/
		
		//提交流程任务
		Map<String, Object> vars = Maps.newHashMap();
		if("yes".equals(actualPurpose.getAct().getFlag())){
			vars.put("pass", "1");
		}else if("no".equals(actualPurpose.getAct().getFlag())){
			vars.put("pass", "0");
		}else if("stop".equals(actualPurpose.getAct().getFlag())){
			vars.put("pass","-1");
		}
		if(!"stop".equals(actualPurpose.getAct().getFlag())){
			actTaskService.complete(actualPurpose.getAct().getTaskId(), actualPurpose.getAct().getProcInsId(), actualPurpose.getAct().getComment(), vars);
		}else{
			 actTaskService.overTask(actualPurpose.getAct().getTaskId(),actualPurpose.getAct().getProcInsId(),actualPurpose.getAct().getComment());
			 actualPurpose.setStatus(Cons.ContractStatus.NOT_PASS);
		}
		ProcessInstance pi = runtimeService.createProcessInstanceQuery()
				.processInstanceId(actualPurpose.getAct().getProcInsId()).singleResult();
	    if(pi==null && "yes".equals(actualPurpose.getAct().getFlag())){//判断流程是否结束,并且审核通过则更改状态
	    	actualPurpose.setStatus(Cons.ContractStatus.APPROVAL);
	        System.out.println("流程已经结束");  
	    }  
	    else{  
	        System.out.println("流程没有结束");  
	    }  
	    super.save(actualPurpose);
	}
	
	
	@Transactional(readOnly = false)
	public void delete(ActualPurpose actualPurpose) {
		super.delete(actualPurpose);
	}
	
}
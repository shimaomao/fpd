/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.postlending.service.fivelevel;

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
import com.wanfin.fpd.modules.act.service.ActTaskService;
import com.wanfin.fpd.modules.contract.service.TLoanContractService;
import com.wanfin.fpd.modules.files.service.TContractFilesService;
import com.wanfin.fpd.modules.postlending.dao.fivelevel.FiveLevelDao;
import com.wanfin.fpd.modules.postlending.entity.fivelevel.FiveLevel;
import com.wanfin.fpd.modules.sys.entity.Dict;
import com.wanfin.fpd.modules.sys.service.DictService;
import com.wanfin.fpd.modules.sys.utils.UserUtils;

/**
 * 五级分类Service
 * @author srf
 * @version 2016-04-14
 */
@Service
@Transactional(readOnly = true)
public class FiveLevelService extends CrudService<FiveLevelDao, FiveLevel> {
	@Autowired
	private ActTaskService actTaskService;
	
	@Autowired
	private RuntimeService runtimeService;
	
	@Autowired
	private TLoanContractService loanContractService;
	
	@Autowired
	private TContractFilesService contractFilesService;
	
	@Autowired
	private DictService dictService;
	
	public FiveLevel get(String id) {
		return super.get(id);
	}
	
	public FiveLevel getByProcInsId(String  procInsId){
		return dao.getByProcInsId(procInsId);
	}
	
	public List<FiveLevel> findList(FiveLevel fiveLevel) {
		return super.findList(fiveLevel);
	}
	
	public Page<FiveLevel> findPage(Page<FiveLevel> page, FiveLevel fiveLevel) {
		fiveLevel.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser().getCurrentUser(), "o", "u"));
		return super.findPage(page, fiveLevel);
	}
	
	@Transactional(readOnly = false)
	public void save(FiveLevel fiveLevel) {
		//判断是否为临时ID
		String tempID = null;
		if(StringUtils.isNotBlank(fiveLevel.getId()) && fiveLevel.getId().startsWith("tmp_")){
			tempID = fiveLevel.getId();
			fiveLevel.setId(null);
		}
		
		//发起申请
		if(StringUtils.isBlank(fiveLevel.getId())){
			fiveLevel.setStatus(Integer.parseInt(Cons.ContractStatus.PENDING));
			
			fiveLevel.preInsert();
			dao.insert(fiveLevel);
			
			//启动流程
			String procInsId = actTaskService.startProcess(fiveLevel.getAct().getProcDefKey(), "t_five_level", fiveLevel.getId(), "五级分类审核");
			if(StringUtils.isNotBlank(procInsId)){
				actTaskService.completeFirstTask(procInsId);
			}
			
		}else{
			super.save(fiveLevel);
		}
		
		if(StringUtils.isNotBlank(tempID)){
			//调整关联附件表
			contractFilesService.updateFileTaskId(tempID, fiveLevel.getId());
		}
	}
	
	@Transactional(readOnly = false)
	public void auditSave(FiveLevel fiveLevel) {
		//设置意见
		fiveLevel.getAct().setComment(("yes".equals(fiveLevel.getAct().getFlag())?"[同意] ":"[驳回]") + fiveLevel.getAct().getComment());
		
		if("yes".equals(fiveLevel.getAct().getFlag())){
			fiveLevel.setStatus(Integer.parseInt(Cons.ContractStatus.APPROVAL));
			//更新合同中对应状态
			if(fiveLevel.getLoanContractId() != null && !"".equals(fiveLevel.getLoanContractId())){
				fiveLevel.setLoanContract( loanContractService.get(fiveLevel.getLoanContractId()) );
				fiveLevel.getLoanContract().setFiveLevel(fiveLevel.getFiveLevel());
				//loanContractService.save(fiveLevel.getLoanContract());//提交申请时不更改五级分类的状态Bug #3147
			}
		}else{
			fiveLevel.setStatus(Integer.parseInt(Cons.ContractStatus.NOT_PASS));
		}
		Map<String, Object> vars = Maps.newHashMap();
		if("yes".equals(fiveLevel.getAct().getFlag())){
			vars.put("pass","1");
		}else if("no".equals(fiveLevel.getAct().getFlag())){
			vars.put("pass","0");
		}else if("stop".equals(fiveLevel.getAct().getFlag())){
			vars.put("pass","-1");
		}
		if(StringUtils.isBlank(fiveLevel.getProcInsId())){
            fiveLevel.setStatus(Integer.parseInt(Cons.ContractStatus.PENDING));
			fiveLevel.preInsert();
			dao.insert(fiveLevel);
			// 启动并提交流程
			String procInsId = actTaskService.startProcess(fiveLevel.getAct().getProcDefKey(), "t_five_level", fiveLevel.getId(), fiveLevel.getLoanContract().getContractNumber());
			if(StringUtils.isNotBlank(procInsId))
				actTaskService.completeFirstTask(procInsId, fiveLevel.getAct().getComment(), 
						fiveLevel.getLoanContract().getContractNumber(), vars);
			
		}else{
			if(!"stop".equals(fiveLevel.getAct().getFlag())){
				//执行任务
				actTaskService.complete(fiveLevel.getAct().getTaskId(), fiveLevel.getAct().getProcInsId(), 
						fiveLevel.getAct().getComment(),vars);
			}else{
				 actTaskService.overTask(fiveLevel.getAct().getTaskId(),fiveLevel.getAct().getProcInsId(),fiveLevel.getAct().getComment());
				 fiveLevel.setStatus(Integer.parseInt(Cons.ContractStatus.NOT_PASS));//审批不通过
			}
			
			//判断流程是否结束 ，如果审批结束才更新五级分类Bug #3147
			ProcessInstance pi = runtimeService.createProcessInstanceQuery()
					.processInstanceId(fiveLevel.getAct().getProcInsId()).singleResult();
		    if(pi==null){
		    	fiveLevel.setLoanContract(loanContractService.get(fiveLevel.getLoanContractId()));
		    	fiveLevel.getLoanContract().setFiveLevel(fiveLevel.getFiveLevel());//更新状态
		    	loanContractService.save(fiveLevel.getLoanContract());//提交申请时不更改五级分类的状态Bug #3147
		        System.out.println("流程已经结束");  
		    }  
		    else{  
		        System.out.println("流程没有结束");  
		    }  
		}
		super.save(fiveLevel);
		//提交流程任务
		/*Map<String, Object> vars = Maps.newHashMap();
		vars.put("pass", "yes".equals(fiveLevel.getAct().getFlag())?"0":"1");
		actTaskService.complete(fiveLevel.getAct().getTaskId(), fiveLevel.getAct().getProcInsId(), fiveLevel.getAct().getComment(), vars);*/
	}
	
	@Transactional(readOnly = false)
	public void delete(FiveLevel fiveLevel) {
		super.delete(fiveLevel);
	}

	public int findSizeByStatus(FiveLevel fiveLevel) {
		// TODO Auto-generated method stub
		return dao.findSizeByStatus(fiveLevel);
	}
	
}
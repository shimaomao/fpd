/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.mortgage.service;

import java.util.List;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wanfin.fpd.common.config.Cons;
import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.service.CrudService;
import com.wanfin.fpd.common.utils.StringUtils;
import com.wanfin.fpd.modules.act.service.ActTaskService;
import com.wanfin.fpd.modules.mortgage.dao.TMortgageApplayDao;
import com.wanfin.fpd.modules.mortgage.entity.MortgageContract;
import com.wanfin.fpd.modules.mortgage.entity.TMortgageApplay;
import com.wanfin.fpd.modules.pledge.entity.PledgeContract;
import com.wanfin.fpd.modules.pledge.service.PledgeContractService;

/**
 * 押品借出审批Service
 * 
 * @author lzj
 * @version 2016-09-23
 */
@Service
@Transactional(readOnly = true)
public class TMortgageApplayService extends CrudService<TMortgageApplayDao, TMortgageApplay> {
	@Autowired
	private MortgageContractService mortgageContractService;
	@Autowired
	private PledgeContractService pledgeContractService;
	@Autowired
	private ActTaskService actTaskService;
	@Autowired
	private RuntimeService runtimeService;

	public TMortgageApplay get(String id) {
		return super.get(id);
	}

	public List<TMortgageApplay> findList(TMortgageApplay tMortgageApplay) {
		return super.findList(tMortgageApplay);
	}

	public Page<TMortgageApplay> findPage(Page<TMortgageApplay> page, TMortgageApplay tMortgageApplay) {
		return super.findPage(page, tMortgageApplay);
	}

	@Transactional(readOnly = false)
	public void save(TMortgageApplay tMortgageApplay) {
		super.save(tMortgageApplay);
	}

	@Transactional(readOnly = false)
	public void delete(TMortgageApplay tMortgageApplay) {
		super.delete(tMortgageApplay);
	}

	/**
	 * @Description 押品借出|归还流程
	 * @param act
	 * @param id
	 * @author lzj
	 */

	@Transactional(readOnly = false)
	public void audit(TMortgageApplay tMortgageApplay) {
		MortgageContract mc = null;
		PledgeContract pc = null;
		String procDefKey = tMortgageApplay.getAct().getProcDefKey();
		String businessTable = "t_mortgage_applay";
		String title = "";
		// 首次提交
		if (StringUtils.isBlank(tMortgageApplay.getAct().getProcInsId())) {
			if (tMortgageApplay.getApplayType().equals(Cons.MortgageApplayType.MORTGAGE)) {// 抵押品
				mc = mortgageContractService.get(tMortgageApplay.getId());
				tMortgageApplay.setMortgageContractId(mc.getId());
			} else if (tMortgageApplay.getApplayType().equals(Cons.MortgageApplayType.PLEDGE)) {
				pc = pledgeContractService.get(tMortgageApplay.getId());// 质押品
				tMortgageApplay.setMortgageContractId(pc.getId());
			}
			if (mc != null) {// 抵押品
				if(mc.getStruts().equals(Integer.valueOf(Cons.StrutsStatus.TAKE_INTO))){//首次提交时为已收押 则本次申请表示借出
					tMortgageApplay.setAuditType(Cons.MortgageAuditType.BORROW_OUT);//借出
					title = "【抵押品-借出审批】" + mc.getContractNumber();
				}else if(mc.getStruts().equals(Integer.valueOf(Cons.StrutsStatus.BORROW_YES))){//首次提交时为已借出 则本次申请为归还
					tMortgageApplay.setAuditType(Cons.MortgageAuditType.TAKE_IN);//归还
					title = "【抵押品-归还审批】" + mc.getContractNumber();
				}
				mc.setStruts(Integer.valueOf(Cons.StrutsStatus.BORROW_AUDIT));// 审批中
				mortgageContractService.updata(tMortgageApplay.getId(), Cons.StrutsStatus.BORROW_AUDIT);
			} else {// 质押品
				if(pc.getStruts().equals(Integer.valueOf(Cons.StrutsStatus.TAKE_INTO))){//首次提交时为已收押 则本次申请表示借出
					tMortgageApplay.setAuditType(Cons.MortgageAuditType.BORROW_OUT);//借出
					title = "【质押品-借出审批】" + pc.getContractNumber();
				}else if(pc.getStruts().equals(Integer.valueOf(Cons.StrutsStatus.BORROW_YES))){//首次提交时为已借出 则本次申请为归还
					tMortgageApplay.setAuditType(Cons.MortgageAuditType.TAKE_IN);//归还
					title = "【质押品-归还审批】" + pc.getContractNumber();
				}
				pc.setStruts(Integer.valueOf(Cons.StrutsStatus.BORROW_AUDIT));// 审批中
				pledgeContractService.updata(tMortgageApplay.getId(), Cons.StrutsStatus.BORROW_AUDIT);
			}
			tMortgageApplay.setIsNewRecord(false);
			tMortgageApplay.preInsert();
			dao.insert(tMortgageApplay);
			// 启动并提交流程
			tMortgageApplay.getAct().setComment(("1".equals(tMortgageApplay.getAct().getFlag()) ? "[同意] " : "[驳回] ")
					+ tMortgageApplay.getAct().getComment());
			String procInsId = actTaskService.startProcess(procDefKey, businessTable, tMortgageApplay.getMortgageContractId(), title);
			if (StringUtils.isNotBlank(procInsId))
				actTaskService.completeFirstTask(procInsId, tMortgageApplay.getAct().getComment(), title,
						tMortgageApplay.getAct().getVars().getVariableMap());
			tMortgageApplay.getAct().setProcInsId(procInsId);
			this.save(tMortgageApplay);
			
		} else {
			// 后续审核
			if (tMortgageApplay.getApplayType().equals(Cons.MortgageApplayType.MORTGAGE)) {// 抵押品
				mc = mortgageContractService.get(tMortgageApplay.getMortgageContractId());
			} else if (tMortgageApplay.getApplayType().equals(Cons.MortgageApplayType.PLEDGE)) {
				pc = pledgeContractService.get(tMortgageApplay.getMortgageContractId());// 质押品
			}
			// 设置意见
			tMortgageApplay.getAct().setComment(("1".equals(tMortgageApplay.getAct().getFlag()) ? "[同意] " : "[驳回] ")
					+ tMortgageApplay.getAct().getComment());
			if (!"-1".equals(tMortgageApplay.getAct().getFlag())) {// 非终止操作
				// 执行任务
				actTaskService.complete(tMortgageApplay.getAct().getTaskId(), tMortgageApplay.getAct().getProcInsId(),
						tMortgageApplay.getAct().getComment(), tMortgageApplay.getAct().getVars().getVariableMap());
			} else {
				actTaskService.overTask(tMortgageApplay.getAct().getTaskId(), tMortgageApplay.getAct().getProcInsId(),
						tMortgageApplay.getAct().getComment());
				if (mc != null) {// 抵押品
					if(tMortgageApplay.getAuditType().equals(Cons.MortgageAuditType.BORROW_OUT)){
						mc.setStruts(Integer.valueOf(Cons.StrutsStatus.TAKE_INTO));// 借出审批不通过 改为已收押状态
						mortgageContractService.updata(mc.getId(), Cons.StrutsStatus.TAKE_INTO);
					}else if(tMortgageApplay.getAuditType().equals(Cons.MortgageAuditType.TAKE_IN)){
						mc.setStruts(Integer.valueOf(Cons.StrutsStatus.BORROW_YES));// 归还审批不通过 恢复为已借出状态
						mortgageContractService.updata(mc.getId(), Cons.StrutsStatus.BORROW_YES);
					}
					
				} else if(pc != null){// 质押品
					if(tMortgageApplay.getAuditType().equals(Cons.MortgageAuditType.BORROW_OUT)){
						pc.setStruts(Integer.valueOf(Cons.StrutsStatus.TAKE_INTO));// 借出审批不通过  改为已收押状态
						pledgeContractService.updata(pc.getId(), Cons.StrutsStatus.TAKE_INTO);
					}else if(tMortgageApplay.getAuditType().equals(Cons.MortgageAuditType.TAKE_IN)){
						pc.setStruts(Integer.valueOf(Cons.StrutsStatus.BORROW_YES));// 归还审批不通过 恢复为已借出状态
						pledgeContractService.updata(pc.getId(), Cons.StrutsStatus.BORROW_YES);
					}
				}

			}
		}
		
	}
    /**
     * 根据借出合同ID查询申请记录
     * @param mortgageContractId
     * @return
     */
	public TMortgageApplay getPojoByContract(String mortgageContractId) {
		return dao.getPojoByContract(mortgageContractId);
	}

}
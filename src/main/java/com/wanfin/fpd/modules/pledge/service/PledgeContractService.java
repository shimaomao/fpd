/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.pledge.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jfinal.plugin.activerecord.Db;
import com.wanfin.fpd.common.config.Cons;
import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.service.CrudService;
import com.wanfin.fpd.common.service.ServiceException;
import com.wanfin.fpd.common.utils.StringUtils;
import com.wanfin.fpd.modules.collateral.service.CarService;
import com.wanfin.fpd.modules.collateral.service.CunhuoService;
import com.wanfin.fpd.modules.collateral.service.GuquanService;
import com.wanfin.fpd.modules.collateral.service.MachineService;
import com.wanfin.fpd.modules.collateral.service.QuanliService;
import com.wanfin.fpd.modules.collateral.service.YongLandService;
import com.wanfin.fpd.modules.files.service.TContractFilesService;
import com.wanfin.fpd.modules.mortgage.entity.MortgageContract;
import com.wanfin.fpd.modules.pledge.dao.PledgeContractDao;
import com.wanfin.fpd.modules.pledge.entity.PledgeContract;
import com.wanfin.fpd.modules.product.entity.TProduct;
import com.wanfin.fpd.modules.sys.utils.UserUtils;


/**
 * 质押信息Service
 * 
 * @author zzm
 * @version 2016-03-27
 */
@Service
@Transactional(readOnly = true)
public class PledgeContractService extends CrudService<PledgeContractDao, PledgeContract> {

	@Autowired
	CarService carService;
	@Autowired
	CunhuoService cunhuoService;
	@Autowired
	GuquanService guquanService;
	@Autowired
	QuanliService quanliService;
	@Autowired
	MachineService machineService;
	@Autowired
	YongLandService yongLandService;
	@Autowired
	private TContractFilesService tContractFilesService;

	public PledgeContract get(String id) {
		return super.get(id);
	}

	public List<PledgeContract> findList(PledgeContract pledgeContract) {
		return super.findList(pledgeContract);
	}

	public Page<PledgeContract> findPage(Page<PledgeContract> page, PledgeContract pledgeContract) {
		pledgeContract.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser().getCurrentUser(), "o", "u"));
		return super.findPage(page, pledgeContract);
	}

	@Transactional(readOnly = false)
	public void save(PledgeContract pledgeContract) {
		
		//处理临时ID
		String tmpID = pledgeContract.getId();
		boolean needUpdate = false;//是否需要更新关联库表
		if(StringUtils.isNotBlank(tmpID) && tmpID.startsWith("temp_")){
			pledgeContract.setId(null);
			needUpdate = true;
		}
		
		if(pledgeContract.getIsNewRecord())
			pledgeContract.setProduct(new TProduct((String)UserUtils.getCache(UserUtils.CACHE_SYSCODE)));
		
		super.save(pledgeContract);

		// 保存对应得质押物品信息
		int pledgeType = pledgeContract.getPledgeType();
		
		switch (pledgeType) {
		case Cons.CollateralType.CAR :
			if (pledgeContract.getCar() != null) {
				pledgeContract.getCar().setDizhiContractId(pledgeContract.getId());
				carService.save(pledgeContract.getCar());
			}			
			break;
		case Cons.CollateralType.STOCK:
			if (pledgeContract.getCunhuo() != null) {
				pledgeContract.getCunhuo().setDizhiContractId(pledgeContract.getId());
				cunhuoService.save(pledgeContract.getCunhuo());
			}
			
			break;
		case Cons.CollateralType.STOCK_RIGHT:
			if (pledgeContract.getGuquan() != null) {
				pledgeContract.getGuquan().setDizhiContractId(pledgeContract.getId());
				guquanService.save(pledgeContract.getGuquan());
			}
			
			break;
		case Cons.CollateralType.MACHINE:
			if (pledgeContract.getMachine() != null) {
				pledgeContract.getMachine().setDizhiContractId(pledgeContract.getId());
				machineService.save(pledgeContract.getMachine());
			}
			
			break;
		case Cons.CollateralType.INVISIBLE_POWER:
			if (pledgeContract.getQuanli() != null) {
				pledgeContract.getQuanli().setDizhiContractId(pledgeContract.getId());
				quanliService.save(pledgeContract.getQuanli());
			}
			
			break;
		case Cons.CollateralType.UER_LAND:
			if (pledgeContract.getYongLand() != null) {
				pledgeContract.getYongLand().setDizhiContractId(pledgeContract.getId());//
				yongLandService.save(pledgeContract.getYongLand());
			}
			
		}
		
		if(needUpdate){
			//调整关联附件表
			tContractFilesService.updateFileTaskId(tmpID, pledgeContract.getId());
		}
		
		
	}

	@Transactional(readOnly = false)
	public void delete(PledgeContract pledgeContract) {
		super.delete(pledgeContract);
	}

	/**
	 * 质押关联合同
	 * 
	 * @param ids
	 * @param businessTable
	 * @param businessId
	 * @return 成功关联的质押数
	 */
	@Transactional(readOnly = false)
	public int relateContract(String[] ids, String businessTable, String businessId) {
		int count = 0;
		if (StringUtils.isBlank(businessTable) && StringUtils.isBlank(businessId) || ids == null || ids.length == 0) {
			throw new ServiceException("缺少必要数据");
		}
		for (String id : ids) {
			PledgeContract pledge = super.get(id);
			if (StringUtils.isNotBlank(pledge.getBusinessId())) {
				throw new ServiceException("质押合同【" + pledge.getName() + "】已经挂了业务，请刷新后操作");
			}

			pledge.setBusinessTable(businessTable);
			pledge.setBusinessId(businessId);
			super.save(pledge);
			count++;
		}
		return count;
	}

	@Transactional(readOnly = false)
	public Integer updata(String id,String status) {
	 return Db.update("update t_pledge_contract set struts = ? where id = ?",status,id);
	}
	
	
	public List<PledgeContract> findListByScanFlag(PledgeContract pledgeContract) {
		return super.findList(pledgeContract);
	}

	public void updateScanFlag(PledgeContract pledgeContract){
		dao.updateScanFlag(pledgeContract);
	}
	
	
	public List<PledgeContract> findListByScanFlagByPushStatus(PledgeContract pledgeContract){
		return dao.findListByScanFlagByPushStatus(pledgeContract);
	}
	
	public int updateByPushStatus(PledgeContract pledgeContract){
		return dao.updateByPushStatus(pledgeContract);
	}
	
	@Transactional(readOnly = false)
	public StringBuffer updateGetListByscanFlagData(String fileName, Long informFilingType,StringBuffer diZhiData) {
		PledgeContract pledgeContract = new PledgeContract();
		pledgeContract.setScanFlag("0");
		List<PledgeContract> pleContractList = dao.findListByScanFlag(pledgeContract);	
		
		if (pleContractList != null && pleContractList.size() > 0) {
			// 获取数据
			for (PledgeContract temp : pleContractList) {
				diZhiData.append(temp.sendData());
				diZhiData.append("\r\n");
			}
			
			// 更改已经做了扫描处理的标示
			/*for (PledgeContract temp : pleContractList) {
				temp.setScanFlag("1");
				dao.updateScanFlag(temp);
			}*/
			for (PledgeContract temp : pleContractList) {
				temp.setScanFlag("1");
				temp.setPushStatus("1");
				dao.updateByPushStatus(temp);
			}
		}
		return diZhiData;
		
	}
	
	
	@Transactional(readOnly = false)
	public void updateReceiptDbData(List<String> dataList,List<PledgeContract> pleList,List<PledgeContract> pleList1) {
		
		//对推送状态为0 做处理
		if(pleList != null && pleList.size() > 0){
			for(PledgeContract temp:pleList){					
				if(dataList != null && dataList.size() > 0){
					if(dataList.contains(temp.getId())){ //这条记录存在回执错误
						temp.setScanFlag("0");							
						dao.update(temp);
					}else{ //这条记录推成功						
						temp.setScanFlag("1");	
						temp.setPushStatus("1");
						dao.updateByPushStatus(temp);
					}
				}else{
					temp.setScanFlag("1");	
					temp.setPushStatus("1");
					dao.updateByPushStatus(temp);
				}
			}
		}			
		
		//对推送状态为1做处理
		if(pleList1 != null && pleList1.size() > 0){
			for(PledgeContract temp:pleList1){					
				if(dataList != null && dataList.size() > 0){
					if(dataList.contains(temp.getId())){ //这条记录存在回执错误
						temp.setScanFlag("0");							
						dao.update(temp);
					}else{ //这条记录推成功						
						temp.setScanFlag("1");	
						dao.updateByPushStatus(temp);
					}
				}else{
					temp.setScanFlag("1");	
					dao.updateByPushStatus(temp);
				}
			}
		}	
		
	}

	public void update(PledgeContract p) {
		dao.update(p);
	}
	
}
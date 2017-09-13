/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.mortgage.service;

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
import com.wanfin.fpd.modules.collateral.service.BuildingService;
import com.wanfin.fpd.modules.collateral.service.CarService;
import com.wanfin.fpd.modules.collateral.service.GongLandService;
import com.wanfin.fpd.modules.collateral.service.GongyuService;
import com.wanfin.fpd.modules.collateral.service.HouseService;
import com.wanfin.fpd.modules.collateral.service.MachineService;
import com.wanfin.fpd.modules.collateral.service.QuanliService;
import com.wanfin.fpd.modules.collateral.service.SingleVillaService;
import com.wanfin.fpd.modules.collateral.service.TerraceVillaService;
import com.wanfin.fpd.modules.collateral.service.ZhuLandService;
import com.wanfin.fpd.modules.collateral.service.ZhuZhaiService;
import com.wanfin.fpd.modules.contract.entity.TLoanContract;
import com.wanfin.fpd.modules.mortgage.dao.MortgageContractDao;
import com.wanfin.fpd.modules.mortgage.entity.MortgageContract;
import com.wanfin.fpd.modules.pledge.entity.PledgeContract;
import com.wanfin.fpd.modules.product.entity.TProduct;
import com.wanfin.fpd.modules.sys.utils.UserUtils;

/**
 * 抵押物品Service
 * 
 * @author zzm
 * @version 2016-03-27
 */
@Service
@Transactional(readOnly = true)
public class MortgageContractService extends CrudService<MortgageContractDao, MortgageContract> {
	@Autowired
	private BuildingService buildingService; // 1 商铺写字楼
	@Autowired
	private HouseService houseService; // 2住宅
	@Autowired
	private GongLandService gongLandService; // 3工业用地信息
	@Autowired
	private ZhuLandService zhuLandService; // 4商宅用地
	@Autowired
	private GongyuService gongyuService; // 5公寓信息
	@Autowired
	private SingleVillaService singleVillaService;// 6独栋别墅
	@Autowired
	private ZhuZhaiService zhuZhaiService; // 7住宅信息
	@Autowired
	private TerraceVillaService terraceVillaService;// 8联排别墅
	@Autowired
	CarService carService;// 9车辆
	@Autowired
	MachineService machineService;//10机器设备
	@Autowired
	private MortgageContractDao mortgageContractDao;
	
	@Autowired
	QuanliService quanliService;

	public MortgageContract get(String id) {
		return super.get(id);
	}

	public List<MortgageContract> findList(MortgageContract mortgageContract) {
		return super.findList(mortgageContract);
	}

	public Page<MortgageContract> findPage(Page<MortgageContract> page, MortgageContract mortgageContract) {
		mortgageContract.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser().getCurrentUser(), "o", "u"));
		return super.findPage(page, mortgageContract);
	}

	@Transactional(readOnly = false)
	public void save(MortgageContract mortgageContract) {
		if(mortgageContract == null){
			return;
		}
		
		String tmpID = mortgageContract.getId();
		//新增合同 清掉ID
		if(StringUtils.isNotBlank(tmpID) && tmpID.startsWith("temp_")){
			mortgageContract.setId(null);			
		}
			
		if(tmpID != null && tmpID.contains(",")){//自定义表单有时候会出现两个重复id，因此进行过滤
			String strid[] = tmpID.split(",");
			mortgageContract.setId(strid[0]);
		}
		
		if(mortgageContract.getIsNewRecord())
			mortgageContract.setProduct(new TProduct((String)UserUtils.getCache(UserUtils.CACHE_SYSCODE)));
		
		super.save(mortgageContract);
		
		int pledgeType = mortgageContract.getPledgeType();
		switch (pledgeType) {
			case Cons.CollateralType.OFFICE_BUILDING:
				if(mortgageContract.getBuilding()!=null){
					mortgageContract.getBuilding().setDizhiContractId(mortgageContract.getId());
					mortgageContract.getBuilding().setOrganId(mortgageContract.getOrganId());
					buildingService.save(mortgageContract.getBuilding());
				}
				break;
			case Cons.CollateralType.HOUSE:
				if(mortgageContract.getHouse()!=null){
					mortgageContract.getHouse().setDizhiContractId(mortgageContract.getId());
					mortgageContract.getHouse().setOrganId(mortgageContract.getOrganId());
					houseService.save(mortgageContract.getHouse());
				}
				break;
			case Cons.CollateralType.INDUSTRIAL_LAND:
				if(mortgageContract.getGongLand()!=null){
					mortgageContract.getGongLand().setDizhiContractId(mortgageContract.getId());
					mortgageContract.getGongLand().setOrganId(mortgageContract.getOrganId());
					gongLandService.save(mortgageContract.getGongLand());
				}
				break;
			case Cons.CollateralType.COMMERCIAL_LAND:
				if(mortgageContract.getZhuLand()!=null){
					mortgageContract.getZhuLand().setDizhiContractId(mortgageContract.getId());
					mortgageContract.getZhuLand().setOrganId(mortgageContract.getOrganId());
					zhuLandService.save(mortgageContract.getZhuLand());
				}
				break;
			case Cons.CollateralType.APARTMENT:
				if(mortgageContract.getGongyu()!=null){
					mortgageContract.getGongyu().setDizhiContractId(mortgageContract.getId());
					mortgageContract.getGongyu().setOrganId(mortgageContract.getOrganId());
					gongyuService.save(mortgageContract.getGongyu());
				}
				
				break;
			case Cons.CollateralType.SINGLE_VILLAS:
				if(mortgageContract.getSingleVilla()!=null){
				mortgageContract.getSingleVilla().setDizhiContractId(mortgageContract.getId());//
				mortgageContract.getSingleVilla().setOrganId(mortgageContract.getOrganId());
				singleVillaService.save(mortgageContract.getSingleVilla());
				}
				break;
			case Cons.CollateralType.RESIDENCE:
				if(mortgageContract.getZhuZhai()!=null){
				mortgageContract.getZhuZhai().setDizhiContractId(mortgageContract.getId());//
				mortgageContract.getZhuZhai().setOrganId(mortgageContract.getOrganId());
				zhuZhaiService.save(mortgageContract.getZhuZhai());
				}
				break;
			case Cons.CollateralType.ROW_VILLA:
				if(mortgageContract.getTerraceVilla()!=null){
					mortgageContract.getTerraceVilla().setDizhiContractId(mortgageContract.getId());//
					mortgageContract.getTerraceVilla().setOrganId(mortgageContract.getOrganId());
					terraceVillaService.save(mortgageContract.getTerraceVilla());
				}
				break;
			case Cons.CollateralType.MORT_CAR:
				if(mortgageContract.getCar()!=null){
					mortgageContract.getCar().setDizhiContractId(mortgageContract.getId());//
					mortgageContract.getCar().setOrganId(mortgageContract.getOrganId());
					carService.save(mortgageContract.getCar());
				}
				break;
			case Cons.CollateralType.MOR_MACHINE:
				if(mortgageContract.getMachine()!=null){
					mortgageContract.getMachine().setDizhiContractId(mortgageContract.getId());//
					mortgageContract.getMachine().setOrganId(mortgageContract.getOrganId());
					machineService.save(mortgageContract.getMachine());
				}
				break;
			case Cons.CollateralType.MOR_LINQUAN:
				if(mortgageContract.getQuanli()!=null){
					mortgageContract.getQuanli().setDizhiContractId(mortgageContract.getId());
					mortgageContract.getQuanli().setOrganId(mortgageContract.getOrganId());
					quanliService.save(mortgageContract.getQuanli());
				}
				break;
			case Cons.CollateralType.MOR_NONG:
				if(mortgageContract.getGongLand()!=null){
					mortgageContract.getGongLand().setDizhiContractId(mortgageContract.getId());
					mortgageContract.getGongLand().setOrganId(mortgageContract.getOrganId());
					gongLandService.save(mortgageContract.getGongLand());
				}
				break;
		}
	}

	@Transactional(readOnly = false)
	public void delete(MortgageContract mortgageContract) {
		super.delete(mortgageContract);
	}

	/**
	 * 抵押关联合同
	 * 
	 * @param ids
	 * @param businessTable
	 * @param businessId
	 * @return 成功关联的抵押数
	 */
	@Transactional(readOnly = false)
	public int relateContract(String[] ids, String businessTable, String businessId) {
		int count = 0;
		if (StringUtils.isBlank(businessTable) && StringUtils.isBlank(businessId) || ids == null || ids.length == 0) {
			throw new ServiceException("缺少必要数据");
		}
		for (String id : ids) {
			MortgageContract mortgage = super.get(id);
			if (StringUtils.isNotBlank(mortgage.getBusinessId())) {
				throw new ServiceException("抵押合同【" + mortgage.getName() + "】已经挂了业务，请刷新后操作");
			}

			mortgage.setBusinessTable(businessTable);
			mortgage.setBusinessId(businessId);
			super.save(mortgage);
			count++;
		}
		return count;
	}
	
	
	@Transactional(readOnly = false)
	public Integer updata(String id,String status) {
	 return Db.update("update t_mortgage_contract set struts = ? where id = ?",status,id);
	}
	
	/**
	 * 根据监管系统扫描标示进行查询记录
	 * @param mortgageContract
	 * @return
	 */
	public List<MortgageContract> findListByScanFlag(MortgageContract mortgageContract) {
		return mortgageContractDao.findListByScanFlag(mortgageContract);
	}
	
	/**
	 * 根据监管系统扫描标示进行查询记录
	 * @param mortgageContract
	 * @return
	 */
	public List<MortgageContract> findListByScanFlagByPushStatus(MortgageContract mortgageContract) {
		return mortgageContractDao.findListByScanFlagByPushStatus(mortgageContract);
	}
	

	public int updateByPushStatus(MortgageContract mortgageContract) {
		return mortgageContractDao.updateByPushStatus(mortgageContract);
	}

	
	

	@Transactional(readOnly = false)
	public StringBuffer updateGetListByscanFlagData(String fileName, Long informFilingType) {
		MortgageContract queryMorContract = new MortgageContract();
		queryMorContract.setScanFlag("0");
		StringBuffer diZhiData = new StringBuffer();
		List<MortgageContract> morContractList = dao.findListByScanFlag(queryMorContract);
		
		if (morContractList != null && morContractList.size() > 0) {
			// 获取数据
			for (MortgageContract temp : morContractList) {
				diZhiData.append(temp.sendData() );
				diZhiData.append("\r\n");
			}
			
			// 更改已经做了扫描处理的标示
		/*	for (MortgageContract temp : morContractList) {
				temp.setScanFlag("1");
				dao.update(temp);
			}*/
			//updateByPushStatus
			for (MortgageContract temp : morContractList) {
				temp.setScanFlag("1");
				temp.setPushStatus("1");
				dao.updateByPushStatus(temp);
			}
		}
		return diZhiData;		
	}
	
	@Transactional(readOnly = false)
	public void updateReceiptDbData(List<String> dataList,List<MortgageContract> morList,List<MortgageContract> morList1) {
		
		//推送状态为0做处理
		if(morList != null && morList.size() > 0){
			for(MortgageContract temp:morList){				
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
		
		//推送状态为1做处理
		if(morList1 != null && morList1.size() > 0){
			for(MortgageContract temp:morList1){				
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

	public void update(MortgageContract m) {
		dao.update(m);
	}
	

}
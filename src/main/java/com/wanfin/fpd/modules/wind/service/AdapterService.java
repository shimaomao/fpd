package com.wanfin.fpd.modules.wind.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wanfin.fpd.common.config.Cons;
import com.wanfin.fpd.common.config.Cons.Ips;
import com.wanfin.fpd.common.utils.InterfaceUtil;
import com.wanfin.fpd.modules.company.entity.TCompany;
import com.wanfin.fpd.modules.company.service.TCompanyService;
import com.wanfin.fpd.modules.employee.entity.TEmployee;
import com.wanfin.fpd.modules.employee.service.TEmployeeService;
import com.wanfin.fpd.modules.wind.adapter.IAdapterDb;
import com.wanfin.fpd.modules.wind.adapter.IeAdapter;
import com.wanfin.fpd.modules.wind.adapter.IeAdapterType;
import com.wanfin.fpd.modules.wind.adapter.WindHttp;
import com.wanfin.fpd.modules.wind.adapter.vo.IaTypeVo;
import com.wanfin.fpd.modules.wind.service.creditchecking.TCreditCheckingService;

@Service
@Transactional(readOnly = true)
public class AdapterService {
	public static final String WIND_SYS_IP = Ips.IP_FK;
	public static final String WIND_SYS_PROJECT = "riskcontrol";
	public static final String WIND_SYS_TOKEN_KEY = "userToken";
	public static final String WIND_SYS_TOKEN_VAL = Cons.Ips.IP_FK_TOKEN;
	
//	private List<IAdapterDb> iadapterDbs = new ArrayList<IAdapterDb>();
	
	@Autowired
	private TCompanyService tCompanyService;
	@Autowired
	private TEmployeeService tEmployeeService;
	@Autowired
	private TCreditCheckingService tCreditCheckingService;
	
//	public AdapterService() {
//		super();
//		this.iadapterDbs.add(new IAdapterDb(IeAdapterDB.DB_WZ, WIND_SYS_IP, WIND_SYS_TOKEN_KEY, WIND_SYS_TOKEN_VAL));
////		this.iadapterDbs.add(new IAdapterDb(IeAdapterDB.DB_PY, WIND_SYS_IP, SvalBase.JsonKey.TOKEN, WIND_SYS_TOKEN_VAL));
//		this.iadapterDbs.add(new IAdapterDb(IeAdapterDB.DB_QH, WIND_SYS_IP, SvalBase.JsonKey.TOKEN, WIND_SYS_TOKEN_VAL));
//	}
//	/********************************************************************************************************
//	 * 发送HTTP请求，获取批量数据
//	 * @Description 获取所有机构类型值
//	 * @return
//	 * @author Chenh 
//	 * @date 2016年5月27日 下午5:03:00
//	 */
//	public List<IaVo> initPLData(){
//		return initDataPL(this.iadapterDbs);
//	}
//	
//	public List<IaVo> initDataPL(List<IAdapterDb> iAdapterDbs){
//		List<String> personIds = tEmployeeService.findAllCardNumList();
//		List<String> corporationIds = tCompanyService.findAllCardNumList();
//		
//		List<IaVo> iaVos = new ArrayList<IaVo>();
//		for (IAdapterDb iAdapterDb : iAdapterDbs) {
//			List<IaTypeVo> iaTypeVos = new ArrayList<IaTypeVo>();
//
//			for (String id : personIds) {
//				iAdapterDb.setParams("&personId="+id+"&types=all");
//				iaTypeVos.add(initDataPLByGR(iAdapterDb, id));
//			}
//
//			for (String id : corporationIds) {
//				iAdapterDb.setParams("&corpId="+id+"&types=all");
//				iaTypeVos.add(initDataPLByQY(iAdapterDb, id));
//			}
//			iaVos.add(new IaVo(iAdapterDb.getDb(), iaTypeVos));
//		}
//		
//		return iaVos;
//	}
//	
//	public IaTypeVo initDataPLByQY(IAdapterDb iAdapterDb, String id) {
//		IeAdapterType ieAdapterType = IeAdapterType.getIeAdapterTypeByDBKey(iAdapterDb.getDb(), WindHttp.WT_CORPORATION);
//		iAdapterDb.setApi(ieAdapterType.getApi());
//		
//		if(StringUtils.isEmpty(iAdapterDb.getParams())){
//			List<String> subs = IeAdapter.getSubByType(ieAdapterType);
//			iAdapterDb.setParams("&corpId="+id+"&types="+StringUtils.listToString(subs));
//		}
//		return initData(iAdapterDb, id, WindHttp.WT_CORPORATION);
//	}
//	
//	public IaTypeVo initDataPLByGR(IAdapterDb iAdapterDb, String id) {
//		IeAdapterType ieAdapterType = IeAdapterType.getIeAdapterTypeByDBKey(iAdapterDb.getDb(), WindHttp.WT_PERSON);
//
//		if(StringUtils.isEmpty(iAdapterDb.getParams())){
//			List<String> subs = IeAdapter.getSubByType(ieAdapterType);
//			iAdapterDb.setParams("&personId="+id+"&types="+StringUtils.listToString(subs));
//		}
//		iAdapterDb.setApi(ieAdapterType.getApi());
//		return initData(iAdapterDb, id, WindHttp.WT_PERSON);
//	}
//
//	/************************************************************************************
//	 * @Description 获取所有类型值
//	 * @param iAdapterDbs
//	 * @return
//	 * @author Chenh 
//	 * @date 2016年5月27日 下午5:03:00
//	 */
//	public List<IaVo> initType(List<IAdapterDb> iAdapterDbs){
//		List<IaVo> iaVos = new ArrayList<IaVo>();
//		for (IAdapterDb iAdapterDb : iAdapterDbs) {
//			List<IaTypeVo> iaTypeVos = new ArrayList<IaTypeVo>();
//			iAdapterDb.setApi("person/queryTypes");
//			iaTypeVos.add(initTypeByQY(iAdapterDb));
//			iAdapterDb.setApi("corporations/queryTypes");
//			iaTypeVos.add(initTypeByGR(iAdapterDb));
//			iaVos.add(new IaVo(iAdapterDb.getDb(), iaTypeVos));
//		}
//		return iaVos;
//	}
//	public IaTypeVo initTypeByGR(IAdapterDb iAdapterDb) {
//		return initType(iAdapterDb, WindHttp.WT_PERSON);
//	}
//
//	public IaTypeVo initTypeByQY(IAdapterDb iAdapterDb) {
//		return initType(iAdapterDb, WindHttp.WT_CORPORATION);
//	}
//
//	public IaTypeVo initType(IAdapterDb iAdapterDb, String wt) {
//		IaTypeVo vo = new IaTypeVo();
//		vo.setType(IeAdapterType.getIeAdapterTypeByDBKey(iAdapterDb.getDb(), wt));
//		vo.setVo(WindHttp.dealHttp(InterfaceUtil.sendPost(iAdapterDb.getUrl(), iAdapterDb.getToken())));
//		return vo;
//	}
//
//	public List<IAdapterDb> getIadapterDbs() {
//		return iadapterDbs;
//	}
//
//	public void setIadapterDbs(List<IAdapterDb> iadapterDbs) {
//		this.iadapterDbs = iadapterDbs;
//	}
	
	
	/********************************************************************************************************
	* @Description: 发送HTTP请求，获取单个数据
	* @author Chenh
	* @param iAdapterDb
	* @param id
	* @param wt
	* @return  
	* @throws
	 */
	public IaTypeVo initData(IAdapterDb iAdapterDb, String id, String wt) {
		IaTypeVo vo = new IaTypeVo();
		vo.setType(IeAdapterType.getIeAdapterTypeByDBKey(iAdapterDb.getDb(), wt));
		vo.setVo(WindHttp.dealHttp(iAdapterDb, id, InterfaceUtil.sendPost(iAdapterDb.getUrl(), iAdapterDb.getTokenAndParams())));
		return vo;
	}
	public IaTypeVo initJsonData(IAdapterDb iAdapterDb, String id, String wt) {
		IaTypeVo vo = new IaTypeVo();
		vo.setType(IeAdapterType.getIeAdapterTypeByDBKey(iAdapterDb.getDb(), wt));
		vo.setVo(WindHttp.dealHttp(iAdapterDb, id, InterfaceUtil.sendPostJson(iAdapterDb.getUrl(), iAdapterDb.getParams())));
		return vo;
	}
	
	

	/********************************************************************************************************
	* @Description: 保存征信查询数据
	* @author Chenh
	* @param idNo
	* @param ccaQhPersonValidatesf
	* @param vo  
	* @throws
	*/
	public void saveCreaditChecking(String idNo, IeAdapter adapter, IaTypeVo vo) {
//		tCreditCheckingService.saveCreaditChecking(idNo, adapter, vo);
	}
	/**   
	* @Description: 根据ID获取个人客户
	* @author Chenh
	* @param id
	* @return  
	* @throws
	*/
	public TEmployee getTEmployee(String id) {
		return tEmployeeService.get(id);
	}
	
	/**   
	* @Description: 根据ID获取企业客户
	* @author Chenh
	* @param id
	* @return  
	* @throws
	*/
	public TCompany getTCompany(String id) {
		return tCompanyService.get(id);
	}
}

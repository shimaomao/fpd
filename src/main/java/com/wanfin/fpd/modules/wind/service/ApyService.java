package com.wanfin.fpd.modules.wind.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wanfin.fpd.common.config.SvalBase;
import com.wanfin.fpd.modules.wind.adapter.IAdapterDb;
import com.wanfin.fpd.modules.wind.adapter.IeAdapterDB;

@Service
@Transactional(readOnly = true)
public class ApyService {
	private IAdapterDb db;
	
	@Autowired
	private AdapterService service;
	
	public ApyService() {
		super();
		db = new IAdapterDb(IeAdapterDB.DB_PY, AdapterService.WIND_SYS_IP, SvalBase.JsonKey.TOKEN, AdapterService.WIND_SYS_TOKEN_VAL);
	}

	/**********************************************************************
	 * 个人信息
	 */
//	public void initGr() {
//		IeAdapterType ieAdapterType = IeAdapterType.getIeAdapterTypeByDBKey(db.getDb(), WindHttp.WT_PERSON);
//		db.setApi(ieAdapterType.getApi());
//	}
//	
//	/**
//	 * 获取个人信息
//	 * @return
//	 */
//	public IaTypeVo getGr(String id) {
//		initGr();
//		db.setParams("&personId="+id+"&types=all");
//		return service.initData(db, id, WindHttp.WT_PERSON);
//	}
//	
//	/**
//	 * 根据类型获取个人信息
//	 * @return
//	 */
//	public IaTypeVo getGrByAdapter(String id, IeAdapter adapter) {
//		initGr();
//		db.setParams("&personId="+id+"&types="+adapter.getSub());
//		return service.initData(db, id, WindHttp.WT_PERSON);
//	}
//
//	/*获取个人基本信息*/
//	public IaTypeVo getGrByPers(String id) {
//		return getGrByAdapter(id, IeAdapter.CCA_PERSON_PERS);
//	}
//	/*获取法院判决信息*/
//	public IaTypeVo getGrByCourt(String id) {
//		return getGrByAdapter(id, IeAdapter.CCA_PERSON_COURT);
//	}
//	/*用户证书信息*/
//	public IaTypeVo getGrByLicen(String id) {
//		return getGrByAdapter(id, IeAdapter.CCA_PERSON_LICEN);
//	}
//	/*个人持股信息*/
//	public IaTypeVo getGrByHsto(String id) {
//		return getGrByAdapter(id, IeAdapter.CCA_PERSON_HSTO);
//	}
//	/*关联用户信息*/
//	public IaTypeVo getGrByRelpe(String id) {
//		return getGrByAdapter(id, IeAdapter.CCA_PERSON_RELPE);
//	}
//	/*个人贷款信息*/
//	public IaTypeVo getGrByLoan(String id) {
//		return getGrByAdapter(id, IeAdapter.CCA_PERSON_LOAN);
//	}
//	/*个人行用卡信息*/
//	public IaTypeVo getGrByCred(String id) {
//		return getGrByAdapter(id, IeAdapter.CCA_PERSON_CRED);
//	}
//	/*个人附件信息*/
//	public IaTypeVo getGrByAnne(String id) {
//		return getGrByAdapter(id, IeAdapter.CCA_PERSON_ANNE);
//	}
	
	/**********************************************************************
	 * 企业信息
	 */
//	public void initQy() {
//		IeAdapterType ieAdapterType = IeAdapterType.getIeAdapterTypeByDBKey(db.getDb(), WindHttp.WT_CORPORATION);
//		db.setApi(ieAdapterType.getApi());
//	}
//	
//	/**
//	 * 获取企业信息
//	 * @return
//	 */
//	public IaTypeVo getQy(String id) {
//		initQy();
//		db.setParams("&corpId="+id+"&types=all");
//		return service.initData(db, id, WindHttp.WT_CORPORATION);
//	}
//	
//	/**
//	 * 根据类型获取企业信息
//	 * @return
//	 */
//	public IaTypeVo getQyByAdapter(String id, IeAdapter adapter) {
//		initGr();
//		db.setParams("&corpId="+id+"&types="+adapter.getSub());
//		return service.initData(db, id, WindHttp.WT_CORPORATION);
//	}
//	/*公司基本信息*/
//	public IaTypeVo getQyByCorp(String id) {
//		return getQyByAdapter(id, IeAdapter.CCA_CORPORATION_CORP);
//	}
//	/*公司贷款信息*/
//	public IaTypeVo getQyByLoan(String id) {
//		return getQyByAdapter(id, IeAdapter.CCA_CORPORATION_LOAN);
//	}
//	/*法院判决信息*/
//	public IaTypeVo getQyByCourt(String id) {
//		return getQyByAdapter(id, IeAdapter.CCA_CORPORATION_COURT);
//	}
//	/*公司执照信息*/
//	public IaTypeVo getQyByLicen(String id) {
//		return getQyByAdapter(id, IeAdapter.CCA_CORPORATION_LICEN);
//	}
//	/*公司公告信息*/
//	public IaTypeVo getQyByAnno(String id) {
//		return getQyByAdapter(id, IeAdapter.CCA_CORPORATION_ANNO);
//	}
//	/*工商处罚信息*/
//	public IaTypeVo getQyByPuni(String id) {
//		return getQyByAdapter(id, IeAdapter.CCA_CORPORATION_PUNI);
//	}
//	/*公司附件信息*/
//	public IaTypeVo getQyByAnne(String id) {
//		return getQyByAdapter(id, IeAdapter.CCA_CORPORATION_ANNE);
//	}
//	/*公司重要个人信息*/
//	public IaTypeVo getQyByPers(String id) {
//		return getQyByAdapter(id, IeAdapter.CCA_CORPORATION_PERS);
//	}
//	/*公司重要关联公司信息*/
//	public IaTypeVo getQyByRelco(String id) {
//		return getQyByAdapter(id, IeAdapter.CCA_CORPORATION_RELCO);
//	}
//	/*公司持股信息*/
//	public IaTypeVo getQyByHsto(String id) {
//		return getQyByAdapter(id, IeAdapter.CCA_CORPORATION_HSTO);
//	}
//	/*公司个人控股信息*/
//	public IaTypeVo getQyByPsto(String id) {
//		return getQyByAdapter(id, IeAdapter.CCA_CORPORATION_PSTO);
//	}
//	/*公司控股信息*/
//	public IaTypeVo getQyByCstr(String id) {
//		return getQyByAdapter(id, IeAdapter.CCA_CORPORATION_CSTR);
//	}
}

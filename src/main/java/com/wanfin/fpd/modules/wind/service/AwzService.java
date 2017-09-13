package com.wanfin.fpd.modules.wind.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wanfin.fpd.modules.company.entity.TCompany;
import com.wanfin.fpd.modules.employee.entity.TEmployee;
import com.wanfin.fpd.modules.wind.adapter.IAdapterDb;
import com.wanfin.fpd.modules.wind.adapter.IeAdapter;
import com.wanfin.fpd.modules.wind.adapter.IeAdapterDB;
import com.wanfin.fpd.modules.wind.adapter.IeAdapterType;
import com.wanfin.fpd.modules.wind.adapter.WindHttp;
import com.wanfin.fpd.modules.wind.adapter.WindVo;
import com.wanfin.fpd.modules.wind.adapter.vo.IaTypeVo;
/**
 * 万众征信数据服务
 * @author Chenh
 */

@Service
@Transactional(readOnly = true)
public class AwzService {
	private IAdapterDb db;
	
	@Autowired
	private AdapterService service;
	
	public AwzService() {
		super();
		db = new IAdapterDb(IeAdapterDB.DB_WZ, AdapterService.WIND_SYS_IP, AdapterService.WIND_SYS_TOKEN_KEY, AdapterService.WIND_SYS_TOKEN_VAL);
	}

	
	/**   
	* @Description: 获取个人信息 
	* @author Chenh
	* @param id
	* @param ieAdapter
	* @return  
	* @throws
	*/
	public IaTypeVo get(String id, IeAdapter ieAdapter) {
		IaTypeVo vo = null;
		if((ieAdapter.getType()).equals(IeAdapterType.QH_PERSON)){
			vo = getGr(id, ieAdapter);
		}else if((ieAdapter.getType()).equals(IeAdapterType.QH_CORPORATION)){
			vo = getQy(id, ieAdapter);
		}
		return vo;
	}
	
	/**********************************************************************
	 * 个人信息
	 */
	public void initGr() {
		IeAdapterType ieAdapterType = IeAdapterType.getIeAdapterTypeByDBKey(db.getDb(), WindHttp.WT_PERSON);
		db.setApi(ieAdapterType.getApi());
	}
	
	/**
	 * 获取个人所有信息
	 * @param cardNum 身份证号
	 * @return
	 */
	public IaTypeVo getGr(String cardNum) {
		initGr();
		db.setParams("&personId="+cardNum+"&types=all");
		return service.initData(db, cardNum, WindHttp.WT_PERSON);
	}
	/**
	 * 获取个人所有信息
	 * @param id 客户ID
	 * @return
	 */
	public IaTypeVo getGrById(String id) {
		TEmployee tEmployee = service.getTEmployee(id);
		return getGr(tEmployee.getCardNum());
	}
	
	/**
	 * 根据类型获取个人信息
	 * @return
	 */
	@SuppressWarnings("null")
	public IaTypeVo getGr(String id, IeAdapter adapter) {
		IaTypeVo vo = null;
		if((adapter.getType()).equals(IeAdapterType.QH_PERSON)){
			TEmployee tEmployee = service.getTEmployee(id);
			return getGrByAdapter(tEmployee.getCardNum(), adapter);
		}else{
			WindVo wvo = new WindVo();
			wvo.setMessage("该接口只能查询个人征信信息！请确定IeAdapterType无误！");
			vo.setVo(wvo);
		}
		return vo;
	}
	/**
	 * 根据类型获取个人信息
	 * @return
	 */
	public IaTypeVo getGrByAdapter(String cardNum, IeAdapter adapter) {
		initGr();
		db.setParams("&personId="+cardNum+"&types="+adapter.getSub());
		IaTypeVo vo = service.initData(db, cardNum, WindHttp.WT_PERSON);
		service.saveCreaditChecking(cardNum, adapter, vo);
		return vo;
	}

	/*获取个人基本信息*/
	public IaTypeVo getGrByPers(String id) {
		return getGrByAdapter(id, IeAdapter.CCA_WZ_PERSON_PERS);
	}
	/*获取法院判决信息*/
	public IaTypeVo getGrByCourt(String id) {
		return getGrByAdapter(id, IeAdapter.CCA_WZ_PERSON_COURT);
	}
	/*用户证书信息*/
	public IaTypeVo getGrByLicen(String id) {
		return getGrByAdapter(id, IeAdapter.CCA_WZ_PERSON_LICEN);
	}
	/*个人持股信息*/
	public IaTypeVo getGrByHsto(String id) {
		return getGrByAdapter(id, IeAdapter.CCA_WZ_PERSON_HSTO);
	}
	/*关联用户信息*/
	public IaTypeVo getGrByRelpe(String id) {
		return getGrByAdapter(id, IeAdapter.CCA_WZ_PERSON_RELPE);
	}
	/*个人贷款信息*/
	public IaTypeVo getGrByLoan(String id) {
		return getGrByAdapter(id, IeAdapter.CCA_WZ_PERSON_LOAN);
	}
	/*个人行用卡信息*/
	public IaTypeVo getGrByCred(String id) {
		return getGrByAdapter(id, IeAdapter.CCA_WZ_PERSON_CRED);
	}
	/*个人附件信息*/
	public IaTypeVo getGrByAnne(String id) {
		return getGrByAdapter(id, IeAdapter.CCA_WZ_PERSON_ANNE);
	}
	
	/**********************************************************************
	 * 企业信息
	 */
	public void initQy() {
		IeAdapterType ieAdapterType = IeAdapterType.getIeAdapterTypeByDBKey(db.getDb(), WindHttp.WT_CORPORATION);
		db.setApi(ieAdapterType.getApi());
	}
	/**
	 * 获取企业信息
	 * @param corpId 机构编码
	 * @return
	 */
	public IaTypeVo getQy(String corpId) {
		initQy();
		db.setParams("&corpId="+corpId+"&types=all");
		return service.initData(db, corpId, WindHttp.WT_CORPORATION);
	}
	
	/**
	 * 获取企业信息
	 * @param id 企业客户ID
	 * @return
	 */
	public IaTypeVo getQyById(String id) {
		TCompany tCompany = service.getTCompany(id);
		return getQy(tCompany.getCardNum());
	}
	
	/**
	 * 根据类型获取企业信息
	 * @return
	 */
	@SuppressWarnings("null")
	public IaTypeVo getQy(String id, IeAdapter adapter) {
		IaTypeVo vo = null;
		if((adapter.getType()).equals(IeAdapterType.QH_PERSON)){
			TCompany tCompany = service.getTCompany(id);
			return getQyByAdapter(tCompany.getCardNum(), adapter);
		}else{
			WindVo wvo = new WindVo();
			wvo.setMessage("该接口只能查询个人征信信息！请确定IeAdapterType无误！");
			vo.setVo(wvo);
		}
		return vo;
	}
	
	/**
	 * 根据类型获取企业信息
	 * @return
	 */
	public IaTypeVo getQyByAdapter(String cardNum, IeAdapter adapter) {
		initGr();
		db.setParams("&corpId="+cardNum+"&types="+adapter.getSub());
		IaTypeVo vo = service.initData(db, cardNum, WindHttp.WT_CORPORATION);
		service.saveCreaditChecking(cardNum, adapter, vo);
		return vo;
	}
	/*公司基本信息*/
	public IaTypeVo getQyByCorp(String id) {
		return getQyByAdapter(id, IeAdapter.CCA_WZ_CORPORATION_CORP);
	}
	/*公司贷款信息*/
	public IaTypeVo getQyByLoan(String id) {
		return getQyByAdapter(id, IeAdapter.CCA_WZ_CORPORATION_LOAN);
	}
	/*法院判决信息*/
	public IaTypeVo getQyByCourt(String id) {
		return getQyByAdapter(id, IeAdapter.CCA_WZ_CORPORATION_COURT);
	}
	/*公司执照信息*/
	public IaTypeVo getQyByLicen(String id) {
		return getQyByAdapter(id, IeAdapter.CCA_WZ_CORPORATION_LICEN);
	}
	/*公司公告信息*/
	public IaTypeVo getQyByAnno(String id) {
		return getQyByAdapter(id, IeAdapter.CCA_WZ_CORPORATION_ANNO);
	}
	/*工商处罚信息*/
	public IaTypeVo getQyByPuni(String id) {
		return getQyByAdapter(id, IeAdapter.CCA_WZ_CORPORATION_PUNI);
	}
	/*公司附件信息*/
	public IaTypeVo getQyByAnne(String id) {
		return getQyByAdapter(id, IeAdapter.CCA_WZ_CORPORATION_ANNE);
	}
	/*公司重要个人信息*/
	public IaTypeVo getQyByPers(String id) {
		return getQyByAdapter(id, IeAdapter.CCA_WZ_CORPORATION_PERS);
	}
	/*公司重要关联公司信息*/
	public IaTypeVo getQyByRelco(String id) {
		return getQyByAdapter(id, IeAdapter.CCA_WZ_CORPORATION_RELCO);
	}
	/*公司持股信息*/
	public IaTypeVo getQyByHsto(String id) {
		return getQyByAdapter(id, IeAdapter.CCA_WZ_CORPORATION_HSTO);
	}
	/*公司个人控股信息*/
	public IaTypeVo getQyByPsto(String id) {
		return getQyByAdapter(id, IeAdapter.CCA_WZ_CORPORATION_PSTO);
	}
	/*公司控股信息*/
	public IaTypeVo getQyByCstr(String id) {
		return getQyByAdapter(id, IeAdapter.CCA_WZ_CORPORATION_CSTR);
	}
}

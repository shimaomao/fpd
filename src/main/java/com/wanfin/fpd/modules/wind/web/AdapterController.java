package com.wanfin.fpd.modules.wind.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mangofactory.swagger.annotations.ApiIgnore;
import com.wanfin.fpd.common.config.SvalBase;
import com.wanfin.fpd.common.utils.StringUtils;
import com.wanfin.fpd.core.billing.BiSval;
import com.wanfin.fpd.core.billing.BillingEngine;
import com.wanfin.fpd.modules.billing.entity.collect.BiCollect;
import com.wanfin.fpd.modules.sys.utils.UserUtils;
import com.wanfin.fpd.modules.wind.adapter.IAdapterVo;
import com.wanfin.fpd.modules.wind.adapter.IeAdapter;
import com.wanfin.fpd.modules.wind.adapter.IeAdapterBi;
import com.wanfin.fpd.modules.wind.adapter.IeAdapterDB;
import com.wanfin.fpd.modules.wind.adapter.IeAdapterType;
import com.wanfin.fpd.modules.wind.adapter.WindHttp;
import com.wanfin.fpd.modules.wind.adapter.WindVo;
import com.wanfin.fpd.modules.wind.adapter.vo.DbGroupVo;
import com.wanfin.fpd.modules.wind.adapter.vo.DbItemVo;
import com.wanfin.fpd.modules.wind.adapter.vo.DbVo;
import com.wanfin.fpd.modules.wind.adapter.vo.IaTypeVo;
import com.wanfin.fpd.modules.wind.service.ApyService;
import com.wanfin.fpd.modules.wind.service.AqhService;
import com.wanfin.fpd.modules.wind.service.AwzService;
import com.wanfin.fpd.modules.wind.service.creditchecking.TCreditCheckingService;

@ApiIgnore
@Controller
@RequestMapping(value = "${adminPath}/adapter/")
public class AdapterController {
	private static final String MSG_WQQCG = "数据获取成功!";
	private static final String MSG_WQQSJ = "未请求到数据或请求数据为空（未计费）!";
	private static final String MSG_WKTFW = "未开通该服务!";
	@Autowired
	private BillingEngine engine; 
	@Autowired
	private AwzService awzService; 
	@Autowired
	private ApyService apyService; 
	@Autowired
	private AqhService aqhService; 
	@Autowired
	private TCreditCheckingService tCreditCheckingService;
	
	@ResponseBody
	@RequestMapping(value = "ajaxDB")
	public List<DbVo> ajaxDB() {
		List<DbVo> dbs = new ArrayList<DbVo>();
		IeAdapterDB[] dbArr = IeAdapterDB.values();

		for (int i = 0; i < dbArr.length; i++) {
			DbVo dbVo = new DbVo(dbArr[i]);
			dbVo.setStatus(false);
			
			dbVo = initDBStatus(dbArr, i, dbVo, IeAdapterDB.DB_WZ, BiSval.BiElementKey.BI_ZX_WZ);
			dbVo = initDBStatus(dbArr, i, dbVo, IeAdapterDB.DB_KL, BiSval.BiElementKey.BI_ZX_KL);
			dbVo = initDBStatus(dbArr, i, dbVo, IeAdapterDB.DB_PY, BiSval.BiElementKey.BI_ZX_PY);
			dbVo = initDBStatus(dbArr, i, dbVo, IeAdapterDB.DB_QH, BiSval.BiElementKey.BI_ZX_QH);
			
			if(dbVo.getStatus()){
				dbs.add(dbVo);
			}
		}
		return dbs;
	}
	
	/**
	 * 初始化数据源状态
	 * @param dbArr
	 * @param i
	 * @param dbVo
	 * @param filterAdapterDB
	 * @param biKey
	 * @return
	 */
	private DbVo initDBStatus(IeAdapterDB[] dbArr, int i, DbVo dbVo, IeAdapterDB filterAdapterDB, String biKey) {
		if((dbArr[i]).equals(filterAdapterDB)){
			List<BiCollect> qhBiCollects = engine.bi().filterList(engine.bi().getServeringByOranIdAndElement(UserUtils.getUser().getOffice().getId(), biKey));
			if((qhBiCollects != null) && (qhBiCollects.size() > 0)){
				dbVo.setStatus(true);
			}
			/****************************************************************************************/
			/**
			 * 测试代码
			 */
//			dbVo.setStatus(true);
			/****************************************************************************************/
		}
		return dbVo;
	}

	@ResponseBody
	@RequestMapping(value = "ajaxGroup")
	public DbVo ajaxGroup(String db, String type) {
		IeAdapterDB ieAdapterDB = IeAdapterDB.getIeAdapterDBByKey(db);

		List<IeAdapterType> curDBTypes = new ArrayList<IeAdapterType>();
		if(StringUtils.isNotEmpty(type)){
			curDBTypes.add(IeAdapterType.getIeAdapterTypeByDBKey(ieAdapterDB, type));
		}else{
			curDBTypes = IeAdapterType.getIeAdapterTypeByDb(ieAdapterDB);
		}
		List<DbGroupVo> groups = new ArrayList<DbGroupVo>();
		for (IeAdapterType curDBType : curDBTypes) {
			DbGroupVo groupVo = new DbGroupVo(curDBType);
			List<DbItemVo> items = new ArrayList<DbItemVo>();
			List<IeAdapter> curDBAdapters = IeAdapter.getIeAdapterByType(curDBType);
			for (IeAdapter curDBAdapter : curDBAdapters) {
				DbItemVo itemVo = new DbItemVo(curDBAdapter);
				items.add(itemVo);
			}
			groupVo.setStatus(false);
			
			groupVo = initGroupStatus(curDBType, groupVo, IeAdapterType.WZ_CORPORATION, BiSval.BiElementKey.BI_ZX_WZ_QY);
			groupVo = initGroupStatus(curDBType, groupVo, IeAdapterType.WZ_PERSON, BiSval.BiElementKey.BI_ZX_WZ_GR);
			groupVo = initGroupStatus(curDBType, groupVo, IeAdapterType.KL_CORPORATION, BiSval.BiElementKey.BI_ZX_KL_QY);
			groupVo = initGroupStatus(curDBType, groupVo, IeAdapterType.KL_PERSON, BiSval.BiElementKey.BI_ZX_KL_GR);
			groupVo = initGroupStatus(curDBType, groupVo, IeAdapterType.PY_CORPORATION, BiSval.BiElementKey.BI_ZX_PY_QY);
			groupVo = initGroupStatus(curDBType, groupVo, IeAdapterType.PY_PERSON, BiSval.BiElementKey.BI_ZX_PY_GR);
			groupVo = initGroupStatus(curDBType, groupVo, IeAdapterType.QH_CORPORATION, BiSval.BiElementKey.BI_ZX_QH_QY);
			groupVo = initGroupStatus(curDBType, groupVo, IeAdapterType.QH_PERSON, BiSval.BiElementKey.BI_ZX_QH_GR);
			
			groupVo.setPkey(db);
			groupVo.setItems(items);
			if(groupVo.getStatus()){
				groups.add(groupVo);
			}
		}
		return new DbVo(ieAdapterDB, groups);
	}
	/**
	 * 初始化组状态
	 * @param curDBType
	 * @param groupVo
	 * @param adapterType
	 * @param biKey
	 */
	private DbGroupVo initGroupStatus(IeAdapterType curDBType, DbGroupVo groupVo, IeAdapterType filterAdapterType, String biKey) {
		if((curDBType).equals(filterAdapterType)){
			List<BiCollect> qhGrBiCollects = engine.bi().filterList(engine.bi().getServeringByOranIdAndElement(UserUtils.getUser().getOffice().getId(), biKey));
			if((qhGrBiCollects != null) && (qhGrBiCollects.size() > 0)){
				groupVo.setStatus(true);
			}

			/****************************************************************************************/
			/**
			 * 测试代码
			 */
//			groupVo.setStatus(true);
			/****************************************************************************************/
		}
		return groupVo;
	}
	
	@ResponseBody
	@RequestMapping(value = "ajaxItem")
	public DbGroupVo ajaxItem(String db, String type) {
		List<DbItemVo> items = new ArrayList<DbItemVo>();
		IeAdapterType ieAdapterType = IeAdapterType.getIeAdapterTypeByDBKey(IeAdapterDB.getIeAdapterDBByKey(db), type);
		List<IeAdapter> curAdapters = IeAdapter.getIeAdapterByType(ieAdapterType);
		for (IeAdapter curAdapter : curAdapters) {
			DbItemVo itemVo = new DbItemVo(curAdapter);
			itemVo.setDb(db);
			itemVo.setPkey(type);
			itemVo.setStatus(false);

			/**********************************************************************
			 * WZ关联业务标识KEY
			 **********************************************************************/
			itemVo = initItemStatus(curAdapter, itemVo, IeAdapter.CCA_WZ_CORPORATION_ANNE, IeAdapterBi.WZ_CORPORATION_ANNE.getKey());
			itemVo = initItemStatus(curAdapter, itemVo, IeAdapter.CCA_WZ_CORPORATION_CORP, IeAdapterBi.WZ_CORPORATION_CORP.getKey());
			itemVo = initItemStatus(curAdapter, itemVo, IeAdapter.CCA_WZ_CORPORATION_HSTO, IeAdapterBi.WZ_CORPORATION_HSTO.getKey());
			itemVo = initItemStatus(curAdapter, itemVo, IeAdapter.CCA_WZ_CORPORATION_PUNI, IeAdapterBi.WZ_CORPORATION_PUNI.getKey());
			itemVo = initItemStatus(curAdapter, itemVo, IeAdapter.CCA_WZ_CORPORATION_RELCO, IeAdapterBi.WZ_CORPORATION_RELCO.getKey());
			itemVo = initItemStatus(curAdapter, itemVo, IeAdapter.CCA_WZ_CORPORATION_LICEN, IeAdapterBi.WZ_CORPORATION_LICEN.getKey());
			itemVo = initItemStatus(curAdapter, itemVo, IeAdapter.CCA_WZ_CORPORATION_COURT, IeAdapterBi.WZ_CORPORATION_COURT.getKey());
			itemVo = initItemStatus(curAdapter, itemVo, IeAdapter.CCA_WZ_CORPORATION_PSTO, IeAdapterBi.WZ_CORPORATION_PSTO.getKey());
			itemVo = initItemStatus(curAdapter, itemVo, IeAdapter.CCA_WZ_CORPORATION_ANNO, IeAdapterBi.WZ_CORPORATION_ANNO.getKey());
			itemVo = initItemStatus(curAdapter, itemVo, IeAdapter.CCA_WZ_CORPORATION_LOAN, IeAdapterBi.WZ_CORPORATION_LOAN.getKey());
			itemVo = initItemStatus(curAdapter, itemVo, IeAdapter.CCA_WZ_CORPORATION_PERS, IeAdapterBi.WZ_CORPORATION_PERS.getKey());
			itemVo = initItemStatus(curAdapter, itemVo, IeAdapter.CCA_WZ_CORPORATION_CSTR, IeAdapterBi.WZ_CORPORATION_CSTR.getKey());
			itemVo = initItemStatus(curAdapter, itemVo, IeAdapter.CCA_WZ_PERSON_PERS, IeAdapterBi.WZ_PERSON_PERS.getKey());
			itemVo = initItemStatus(curAdapter, itemVo, IeAdapter.CCA_WZ_PERSON_ANNE, IeAdapterBi.WZ_PERSON_ANNE.getKey());
			itemVo = initItemStatus(curAdapter, itemVo, IeAdapter.CCA_WZ_PERSON_LICEN, IeAdapterBi.WZ_PERSON_LICEN.getKey());
			itemVo = initItemStatus(curAdapter, itemVo, IeAdapter.CCA_WZ_PERSON_COURT, IeAdapterBi.WZ_PERSON_COURT.getKey());
			itemVo = initItemStatus(curAdapter, itemVo, IeAdapter.CCA_WZ_PERSON_RELPE, IeAdapterBi.WZ_PERSON_RELPE.getKey());
			itemVo = initItemStatus(curAdapter, itemVo, IeAdapter.CCA_WZ_PERSON_HSTO, IeAdapterBi.WZ_PERSON_HSTO.getKey());
			itemVo = initItemStatus(curAdapter, itemVo, IeAdapter.CCA_WZ_PERSON_CRED, IeAdapterBi.WZ_PERSON_CRED.getKey());
			itemVo = initItemStatus(curAdapter, itemVo, IeAdapter.CCA_WZ_PERSON_LOAN, IeAdapterBi.WZ_PERSON_LOAN.getKey());

			
			/**********************************************************************
			 * KL关联业务标识KEY
			 **********************************************************************/
//			itemVo = initItemStatus(curAdapter, itemVo, IeAdapter.CCA_PY_PERSON_CORP, IeAdapterBi.PY_CORPORATION_CORP.getKey());
//			itemVo = initItemStatus(curAdapter, itemVo, IeAdapter.CCA_PY_PERSON_MOBILE, IeAdapterBi.PY_PERSON_MOBILE.getKey());
//			itemVo = initItemStatus(curAdapter, itemVo, IeAdapter.CCA_PY_PERSON_PERSON, IeAdapterBi.PY_PERSON_PERSON.getKey());
//			itemVo = initItemStatus(curAdapter, itemVo, IeAdapter.CCA_PY_PERSON_RISK, IeAdapterBi.PY_PERSON_RISK.getKey());
			
			
			/**********************************************************************
			 * PY关联业务标识KEY
			 **********************************************************************/
			itemVo = initItemStatus(curAdapter, itemVo, IeAdapter.CCA_PY_PERSON_CORP, IeAdapterBi.PY_CORPORATION_CORP.getKey());
			itemVo = initItemStatus(curAdapter, itemVo, IeAdapter.CCA_PY_PERSON_MOBILE, IeAdapterBi.PY_PERSON_MOBILE.getKey());
			itemVo = initItemStatus(curAdapter, itemVo, IeAdapter.CCA_PY_PERSON_PERSON, IeAdapterBi.PY_PERSON_PERSON.getKey());
			itemVo = initItemStatus(curAdapter, itemVo, IeAdapter.CCA_PY_PERSON_RISK, IeAdapterBi.PY_PERSON_RISK.getKey());

			/**********************************************************************
			 * QH关联业务标识KEY
			 **********************************************************************/
			itemVo = initItemStatus(curAdapter, itemVo, IeAdapter.CCA_QH_PERSON_ADDRESS, IeAdapterBi.QH_PERSON_ADDRESS.getKey());
			itemVo = initItemStatus(curAdapter, itemVo, IeAdapter.CCA_QH_PERSON_BANK_CARD_INFO, IeAdapterBi.QH_PERSON_BANK_CARD_INFO.getKey());
			itemVo = initItemStatus(curAdapter, itemVo, IeAdapter.CCA_QH_PERSON_CHEAT_INFO, IeAdapterBi.QH_PERSON_CHEAT_INFO.getKey());
			itemVo = initItemStatus(curAdapter, itemVo, IeAdapter.CCA_QH_PERSON_CARD_VERIFY, IeAdapterBi.QH_PERSON_CARD_VERIFY.getKey());
			itemVo = initItemStatus(curAdapter, itemVo, IeAdapter.CCA_QH_PERSON_COUNT_ANNORETR_RESULTS, IeAdapterBi.QH_PERSON_COUNT_ANNORETR_RESULTS.getKey());
			itemVo = initItemStatus(curAdapter, itemVo, IeAdapter.CCA_QH_PERSON_COUNT_ANNOSEAR_NOTICE, IeAdapterBi.QH_PERSON_COUNT_ANNOSEAR_NOTICE.getKey());
			itemVo = initItemStatus(curAdapter, itemVo, IeAdapter.CCA_QH_PERSON_COUNT_ANNOUNCE_RETRIEVE, IeAdapterBi.QH_PERSON_COUNT_ANNOUNCE_RETRIEVE.getKey());
			itemVo = initItemStatus(curAdapter, itemVo, IeAdapter.CCA_QH_PERSON_COUNT_ANNOUNCE_SEARCH, IeAdapterBi.QH_PERSON_COUNT_ANNOUNCE_SEARCH.getKey());
			itemVo = initItemStatus(curAdapter, itemVo, IeAdapter.CCA_QH_PERSON_COUNT_BREAKWORD, IeAdapterBi.QH_PERSON_COUNT_BREAKWORDH.getKey());
			itemVo = initItemStatus(curAdapter, itemVo, IeAdapter.CCA_QH_PERSON_COUNT_BREWORDINFO, IeAdapterBi.QH_PERSON_COUNT_BREWORDINFO.getKey());
			itemVo = initItemStatus(curAdapter, itemVo, IeAdapter.CCA_QH_PERSON_COUNT_ENFORCEMENT, IeAdapterBi.QH_PERSON_COUNT_ENFORCEMENT.getKey());
			itemVo = initItemStatus(curAdapter, itemVo, IeAdapter.CCA_QH_PERSON_COUNT_ENFORINFO, IeAdapterBi.QH_PERSON_COUNT_ENFORINFO.getKey());
			itemVo = initItemStatus(curAdapter, itemVo, IeAdapter.CCA_QH_PERSON_COUNT_JUDGE_RETRIEVE, IeAdapterBi.QH_PERSON_COUNT_JUDGE_RETRIEVE.getKey());
			itemVo = initItemStatus(curAdapter, itemVo, IeAdapter.CCA_QH_PERSON_COUNT_JUDGE_SEARCH, IeAdapterBi.QH_PERSON_COUNT_JUDGE_SEARCH.getKey());
			itemVo = initItemStatus(curAdapter, itemVo, IeAdapter.CCA_QH_PERSON_COUNT_JUDGE_RESULTS, IeAdapterBi.QH_PERSON_COUNT_JUDGE_RESULTS.getKey());
			itemVo = initItemStatus(curAdapter, itemVo, IeAdapter.CCA_QH_PERSON_COUNT_JUDGSEAR_DOCS, IeAdapterBi.QH_PERSON_COUNT_JUDGSEAR_DOCS.getKey());
			itemVo = initItemStatus(curAdapter, itemVo, IeAdapter.CCA_QH_PERSON_CREDIT, IeAdapterBi.QH_PERSON_CREDIT.getKey());
			itemVo = initItemStatus(curAdapter, itemVo, IeAdapter.CCA_QH_PERSON_CREDIT_TRACK, IeAdapterBi.QH_PERSON_CREDIT_TRACK.getKey());
			itemVo = initItemStatus(curAdapter, itemVo, IeAdapter.CCA_QH_PERSON_DEIVE_DATAS, IeAdapterBi.QH_PERSON_DEIVE_DATAS.getKey());
			itemVo = initItemStatus(curAdapter, itemVo, IeAdapter.CCA_QH_PERSON_DEIVE_SCORE, IeAdapterBi.QH_PERSON_DEIVE_SCORE.getKey());
			itemVo = initItemStatus(curAdapter, itemVo, IeAdapter.CCA_QH_PERSON_DEIVE_STATE, IeAdapterBi.QH_PERSON_DEIVE_STATE.getKey());
			itemVo = initItemStatus(curAdapter, itemVo, IeAdapter.CCA_QH_PERSON_DEIVE_TASK, IeAdapterBi.QH_PERSON_DEIVE_TASK.getKey());
			itemVo = initItemStatus(curAdapter, itemVo, IeAdapter.CCA_QH_PERSON_MOBILE_CONSULT, IeAdapterBi.QH_PERSON_MOBILE_CONSULT.getKey());
			itemVo = initItemStatus(curAdapter, itemVo, IeAdapter.CCA_QH_PERSON_MOBILE_OVER_ALLINC, IeAdapterBi.QH_PERSON_MOBILE_OVER_ALLINC.getKey());
			itemVo = initItemStatus(curAdapter, itemVo, IeAdapter.CCA_QH_PERSON_PINGAN_RES_STRUCT, IeAdapterBi.QH_PERSON_PINGAN_RES_STRUCT.getKey());
			itemVo = initItemStatus(curAdapter, itemVo, IeAdapter.CCA_QH_PERSON_RENT_CAR, IeAdapterBi.QH_PERSON_RENT_CAR.getKey());
			itemVo = initItemStatus(curAdapter, itemVo, IeAdapter.CCA_QH_PERSON_RISK, IeAdapterBi.QH_PERSON_RISK.getKey());
			itemVo = initItemStatus(curAdapter, itemVo, IeAdapter.CCA_QH_PERSON_VALIDATE, IeAdapterBi.QH_PERSON_VALIDATE.getKey());
			itemVo = initItemStatus(curAdapter, itemVo, IeAdapter.CCA_QH_PERSON_VALIDATE_ADDRESS, IeAdapterBi.QH_PERSON_VALIDATEDZ.getKey());
			itemVo = initItemStatus(curAdapter, itemVo, IeAdapter.CCA_QH_PERSON_VALIDATE_IDENTITY, IeAdapterBi.QH_PERSON_VALIDATESF.getKey());
			itemVo = initItemStatus(curAdapter, itemVo, IeAdapter.CCA_QH_PERSON_VALIDATE_WORK, IeAdapterBi.QH_PERSON_VALIDATEWORK.getKey());

			if(itemVo.getStatus()){
				items.add(itemVo);
			}
		}
		return new DbGroupVo(ieAdapterType, items);
	}
	/**
	 * 初始化项状态
	 * @param curAdapter
	 * @param itemVo
	 * @param filterAdapter
	 * @param biKey
	 * @return
	 */
	private DbItemVo initItemStatus(IeAdapter curAdapter,DbItemVo itemVo, IeAdapter filterAdapter, String biKey) {
		if((curAdapter).equals(filterAdapter)){
			List<BiCollect> biCollects = engine.bi().filterList(engine.bi().getServeringByOranIdAndElement(UserUtils.getUser().getOffice().getId(), biKey));
			if((biCollects != null) && (biCollects.size() > 0)){
				itemVo.setStatus(true);
			}

			/****************************************************************************************/
			/**
			 * 测试代码
			 */
//			itemVo.setStatus(true);
			/****************************************************************************************/
		}
		return itemVo;
	}
	
	@ResponseBody
	@RequestMapping(value = "ajaxAdapter")
	public DbItemVo ajaxAdapter(String db, String type, String sub, String id) throws JSONException {
		IAdapterVo vo = new IAdapterVo();
		IeAdapterType ieAdapterType = IeAdapterType.getIeAdapterTypeByDBKey(IeAdapterDB.getIeAdapterDBByKey(db), type);
		IeAdapter ieAdapter = IeAdapter.getIeAdapterByTypeAndSub(ieAdapterType, sub);
		vo.setResult(initItemDatas(db, id, ieAdapter));
		return new DbItemVo(ieAdapter, vo);
	}

	/**
	* @Description: 初始化获取的数据
	* @author Chenh
	* @param db
	* @param id
	* @param ieAdapter
	* @return
	* @throws JSONException  
	* @throws
	 */
	public Map<String, Object> initItemDatas(String db, String id, IeAdapter ieAdapter) throws JSONException {
		WindVo vo = new WindVo();
		IaTypeVo iaTypeVo = new IaTypeVo();
		Map<String, Object> json = new HashMap<String, Object>();
		if((db).equals(IeAdapterDB.DB_WZ.getKey())){
//			iaTypeVo = awzService.get(id, ieAdapter);
			json.put(ieAdapter.getSub(), "{\"approveddate\":1467104386000,\"operatingend\":\"111111\",\"paidcurrencytype\":\"1\",\"businesslicensenumber\":\"440101000357140\",\"controltype\":\"1\",\"cooperationtime\":1467043200000,\"legalrepresentative\":\"111111\",\"ismaxstockholder\":\"1\",\"paidcapital\":\"111111\",\"mainbusiness\":\"111111\",\"registrationAuthority\":\"111111\",\"entitytype\":\"1\",\"registeredaddress\":\"111111\",\"phone\":\"111111\",\"companyname\":\"111111\",\"postalcode\":\"111111\",\"companytype\":\"111111\",\"builddate\":1467043200000,\"operatingstart\":\"111111\",\"id\":\"12f409fc18d74ee485bbb29bd939fadf\",\"businessaddress\":\"111111\",\"registeredcapital\":\"111111\",\"fax\":\"111111\",\"registeredcurrencytype\":\"1\"}");
		}else if((db).equals(IeAdapterDB.DB_PY.getKey())){
//			iaTypeVo = awzService.getGrByAdapterById(id, ieAdapter);
//			json.put(ieAdapter.getSub(), "{\"approveddate\":1467104386000,\"operatingend\":\"111111\",\"paidcurrencytype\":\"1\",\"businesslicensenumber\":\"440101000357140\",\"controltype\":\"1\",\"cooperationtime\":1467043200000,\"legalrepresentative\":\"111111\",\"ismaxstockholder\":\"1\",\"paidcapital\":\"111111\",\"mainbusiness\":\"111111\",\"registrationAuthority\":\"111111\",\"entitytype\":\"1\",\"registeredaddress\":\"111111\",\"phone\":\"111111\",\"companyname\":\"111111\",\"postalcode\":\"111111\",\"companytype\":\"111111\",\"builddate\":1467043200000,\"operatingstart\":\"111111\",\"id\":\"12f409fc18d74ee485bbb29bd939fadf\",\"businessaddress\":\"111111\",\"registeredcapital\":\"111111\",\"fax\":\"111111\",\"registeredcurrencytype\":\"1\"}");
		}else if((db).equals(IeAdapterDB.DB_QH.getKey())){
			iaTypeVo = aqhService.get(id, ieAdapter);
//			json.put(ieAdapter.getSub(), "{\"addresstype\":\"\",\"carchkresult\":\"\",\"idtype\":\"0\",\"graduatemajor\":\"\",\"graduateschool\":\"\",\"isexistrel\":\"\",\"graduateyear\":\"\",\"isasyned\":\"\",\"photocmpresult\":\"\",\"ishighestedubkg\":\"\",\"drvstatusqryid\":\"\",\"rellevel\":\"\",\"name\":\"卢院平\",\"appearlastdate\":\"\",\"ownermobilestatus\":\"\",\"isownermobile\":\"\",\"seqno\":\"10QHj-8U4lLaGnbQjfqNmy\",\"datadate\":\"\",\"appear1thdate\":\"\",\"cartypeinc\":\"\",\"carfactoryinc\":\"\",\"isrealcompany\":\"\",\"isrealidentity\":\"1\",\"mobileno\":\"18126098065\",\"housedatadate\":\"\",\"carprice\":\"\",\"photosimdeg\":\"\",\"housechkresult\":\"\",\"companysimdeg\":\"\",\"isvalidaddress\":\"\",\"idno\":\"430104196606273061\",\"usetimescore\":\"\"}");
		}
		vo.setRelId(id);
		vo.setResult(new JSONObject(json));
		vo.setReturnCode("200");
		iaTypeVo.setVo(vo);
		return dealItemData(ieAdapter, iaTypeVo);
	}

	/**
	* @Description: 解析Http获取的数据
	* @author Chenh
	* @param ieAdapter
	* @param iaTypeVo
	* @return
	* @throws JSONException  
	* @throws
	 */
	public Map<String, Object> dealItemData(IeAdapter ieAdapter, IaTypeVo iaTypeVo) throws JSONException {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put(SvalBase.JsonKey.KEY_STATUS, false);
		
		if((iaTypeVo != null) && (iaTypeVo.getVo() != null)){
			WindVo windVo = iaTypeVo.getVo();
			if(((WindHttp.WHCode.SUCCESS).equals(windVo.getReturnCode())) && (windVo.getResult() != null)){
				Boolean isTrue = engine.bi().reduceServerNumber(IeAdapterBi.getIeAdapterBiByIeAdapter(ieAdapter).getKey());
				/****************************************************************************************/
				/**
				 * 测试代码
				 */
//				isTrue = true;
				/****************************************************************************************/
				if(isTrue){
					result.put(SvalBase.JsonKey.KEY_STATUS, true);
					String subJson = windVo.getResult().optString(ieAdapter.getSub());
					if(StringUtils.isNotEmpty(subJson)){
						result.put(SvalBase.JsonKey.KEY_DATA, ieAdapter.getAdapter().init(subJson));
						result.put(SvalBase.JsonKey.KEY_MSG, MSG_WQQCG);
					}else{
						result.put(SvalBase.JsonKey.KEY_DATA, "");
						result.put(SvalBase.JsonKey.KEY_MSG, MSG_WQQSJ);
					}
				}else{
					result.put(SvalBase.JsonKey.KEY_MSG, MSG_WKTFW);
				}
			}else{
				result.put(SvalBase.JsonKey.KEY_MSG, MSG_WQQSJ);
			}
		}else{
			result.put(SvalBase.JsonKey.KEY_MSG, MSG_WQQSJ);
		}
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "ajaxTest")
	public Boolean ajaxTest(String db, String type, String sub, String id) throws JSONException {
		Boolean isTrue = engine.bi().reduceServerNumber(IeAdapterBi.getIeAdapterBiByIeAdapter(IeAdapter.CCA_WZ_CORPORATION_CORP).getKey());
		System.out.println(isTrue);
		return true;
	}
}
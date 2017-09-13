package com.wanfin.fpd.modules.wind.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.wanfin.fpd.modules.wind.adapter.WindHttp.WHCode;
import com.wanfin.fpd.modules.wind.adapter.corporations.CAnnex;
import com.wanfin.fpd.modules.wind.adapter.corporations.CAnno;
import com.wanfin.fpd.modules.wind.adapter.corporations.CCorp;
import com.wanfin.fpd.modules.wind.adapter.corporations.CCourt;
import com.wanfin.fpd.modules.wind.adapter.corporations.CHsto;
import com.wanfin.fpd.modules.wind.adapter.corporations.CLicense;
import com.wanfin.fpd.modules.wind.adapter.corporations.CLoan;
import com.wanfin.fpd.modules.wind.adapter.corporations.CPerson;
import com.wanfin.fpd.modules.wind.adapter.corporations.CPsto;
import com.wanfin.fpd.modules.wind.adapter.corporations.CPuni;
import com.wanfin.fpd.modules.wind.adapter.corporations.CRelco;
import com.wanfin.fpd.modules.wind.adapter.corporations.CStr;
import com.wanfin.fpd.modules.wind.adapter.person.PAnnex;
import com.wanfin.fpd.modules.wind.adapter.person.PCourt;
import com.wanfin.fpd.modules.wind.adapter.person.PCredit;
import com.wanfin.fpd.modules.wind.adapter.person.PLicense;
import com.wanfin.fpd.modules.wind.adapter.person.PLoan;
import com.wanfin.fpd.modules.wind.adapter.person.PPersion;
import com.wanfin.fpd.modules.wind.adapter.person.PRelPe;
import com.wanfin.fpd.modules.wind.adapter.person.PSto;
import com.wanfin.fpd.modules.wind.adapter.person.QhpAddress;
import com.wanfin.fpd.modules.wind.adapter.person.QhpBankCardInfo;
import com.wanfin.fpd.modules.wind.adapter.person.QhpBankScore;
import com.wanfin.fpd.modules.wind.adapter.person.QhpCardVerify;
import com.wanfin.fpd.modules.wind.adapter.person.QhpCheatInfo;
import com.wanfin.fpd.modules.wind.adapter.person.QhpCountAnnoretrResults;
import com.wanfin.fpd.modules.wind.adapter.person.QhpCountAnnosearNotice;
import com.wanfin.fpd.modules.wind.adapter.person.QhpCountAnnounceRetrieve;
import com.wanfin.fpd.modules.wind.adapter.person.QhpCountAnnounceSearch;
import com.wanfin.fpd.modules.wind.adapter.person.QhpCountBreakword;
import com.wanfin.fpd.modules.wind.adapter.person.QhpCountBrewordInfo;
import com.wanfin.fpd.modules.wind.adapter.person.QhpCountEnforInfo;
import com.wanfin.fpd.modules.wind.adapter.person.QhpCountEnforcement;
import com.wanfin.fpd.modules.wind.adapter.person.QhpCountJudgeRetrieve;
import com.wanfin.fpd.modules.wind.adapter.person.QhpCountJudgeSearch;
import com.wanfin.fpd.modules.wind.adapter.person.QhpCountJudgretrResults;
import com.wanfin.fpd.modules.wind.adapter.person.QhpCountJudgsearDocs;
import com.wanfin.fpd.modules.wind.adapter.person.QhpCredit;
import com.wanfin.fpd.modules.wind.adapter.person.QhpCreditTrack;
import com.wanfin.fpd.modules.wind.adapter.person.QhpDeiveDatas;
import com.wanfin.fpd.modules.wind.adapter.person.QhpDeiveScore;
import com.wanfin.fpd.modules.wind.adapter.person.QhpDeiveState;
import com.wanfin.fpd.modules.wind.adapter.person.QhpDeiveTask;
import com.wanfin.fpd.modules.wind.adapter.person.QhpLoan;
import com.wanfin.fpd.modules.wind.adapter.person.QhpMobileConsult;
import com.wanfin.fpd.modules.wind.adapter.person.QhpMobileOverAllInc;
import com.wanfin.fpd.modules.wind.adapter.person.QhpPinganResStruct;
import com.wanfin.fpd.modules.wind.adapter.person.QhpRentCar;
import com.wanfin.fpd.modules.wind.adapter.person.QhpRisk;
import com.wanfin.fpd.modules.wind.adapter.person.QhpValidate;
import com.wanfin.fpd.modules.wind.adapter.person.QhpValidateCar;
import com.wanfin.fpd.modules.wind.adapter.person.QhpValidateDZ;
import com.wanfin.fpd.modules.wind.adapter.person.QhpValidateEducation;
import com.wanfin.fpd.modules.wind.adapter.person.QhpValidateFace;
import com.wanfin.fpd.modules.wind.adapter.person.QhpValidateHouse;
import com.wanfin.fpd.modules.wind.adapter.person.QhpValidatePhone;
import com.wanfin.fpd.modules.wind.adapter.person.QhpValidateRelPerson;
import com.wanfin.fpd.modules.wind.adapter.person.QhpValidateSF;
import com.wanfin.fpd.modules.wind.adapter.person.QhpValidateWork;

public enum IeAdapter{
	/**********************************************************************
	 * 万众金融数据源
	 **********************************************************************/
	CCA_WZ_PERSON_LICEN(IeAdapterType.WZ_PERSON, "", "licen", "用户证书信息", new PLicense()),
	CCA_WZ_PERSON_COURT(IeAdapterType.WZ_PERSON, "", "court", "法院判决信息", new PCourt()),
	CCA_WZ_PERSON_ANNE(IeAdapterType.WZ_PERSON, "", "anne", "个人附件信息", new PAnnex()),
	CCA_WZ_PERSON_RELPE(IeAdapterType.WZ_PERSON, "", "relpe", "关联用户信息", new PRelPe()),
	CCA_WZ_PERSON_HSTO(IeAdapterType.WZ_PERSON, "", "hsto", "个人持股信息", new PSto()),
	CCA_WZ_PERSON_CRED(IeAdapterType.WZ_PERSON, "", "cred", "个人信用卡信息", new PCredit()),
	CCA_WZ_PERSON_PERS(IeAdapterType.WZ_PERSON, "", "pers", "个人基本信息", new PPersion()),
	CCA_WZ_PERSON_LOAN(IeAdapterType.WZ_PERSON, "", "loan", "个人贷款信息", new PLoan()),
	CCA_WZ_CORPORATION_HSTO(IeAdapterType.WZ_CORPORATION, "", "hsto", "公司持股信息", new CHsto()),
	CCA_WZ_CORPORATION_PUNI(IeAdapterType.WZ_CORPORATION, "", "puni", "工商处罚信息", new CPuni()),
	CCA_WZ_CORPORATION_RELCO(IeAdapterType.WZ_CORPORATION, "", "relco", "公司重要关联公司信息", new CRelco()),
	CCA_WZ_CORPORATION_LICEN(IeAdapterType.WZ_CORPORATION, "", "licen", "公司执照信息", new CLicense()),
	CCA_WZ_CORPORATION_COURT(IeAdapterType.WZ_CORPORATION, "", "court", "法院判决信息", new CCourt()),
	CCA_WZ_CORPORATION_PSTO(IeAdapterType.WZ_CORPORATION, "", "psto", "公司个人控股信息", new CPsto()),
	CCA_WZ_CORPORATION_ANNE(IeAdapterType.WZ_CORPORATION, "", "anne", "公司附件信息", new CAnnex()),
	CCA_WZ_CORPORATION_CORP(IeAdapterType.WZ_CORPORATION, "", "corp", "公司基本信息", new CCorp()),
	CCA_WZ_CORPORATION_ANNO(IeAdapterType.WZ_CORPORATION, "", "anno", "公司公告信息", new CAnno()),
	CCA_WZ_CORPORATION_LOAN(IeAdapterType.WZ_CORPORATION, "", "loan", "公司贷款信息", new CLoan()),
	CCA_WZ_CORPORATION_PERS(IeAdapterType.WZ_CORPORATION, "", "pers", "公司重要个人信息", new CPerson()),
	CCA_WZ_CORPORATION_CSTR(IeAdapterType.WZ_CORPORATION, "", "cstr", "公司控股信息", new CStr()),

	
	/**********************************************************************
	 * 鹏元征信数据源
	 **********************************************************************/
	CCA_PY_PERSON_MOBILE(IeAdapterType.PY_PERSON, "", "mobile", "手机号码信息", new CLoan()),
	CCA_PY_PERSON_RISK(IeAdapterType.PY_PERSON, "", "risk", "个人风险信息", new CPerson()),
	CCA_PY_PERSON_PERSON(IeAdapterType.PY_PERSON, "", "person", "个人信息", new CPerson()),
	CCA_PY_PERSON_CORP(IeAdapterType.PY_PERSON, "", "corp", "企业信息", new CHsto()),
	
	
	/**********************************************************************
	 * 前海征信数据源
	 **********************************************************************/
	CCA_QH_PERSON_VALIDATE(IeAdapterType.QH_PERSON, "", "validate", "好信一鉴通", new QhpValidate()),//*
	CCA_QH_PERSON_VALIDATE_ADDRESS(IeAdapterType.QH_PERSON, "WZ2016071810301249807", "validateDZ", "好信一鉴通地址验证", new QhpValidateDZ()),
	CCA_QH_PERSON_VALIDATE_IDENTITY(IeAdapterType.QH_PERSON, "WZ2016071810234452400", "validateSF", "好信一鉴通身份验证", new QhpValidateSF()),
	CCA_QH_PERSON_VALIDATE_WORK(IeAdapterType.QH_PERSON, "WZ2016071810283590505", "validateWork", "好信一鉴通工作单位验证", new QhpValidateWork()),
	CCA_QH_PERSON_VALIDATE_PHONE(IeAdapterType.QH_PERSON, "WZ201607181024544001", "validatePhone", "好信一鉴通手机验证", new QhpValidatePhone()),
	CCA_QH_PERSON_VALIDATE_RELPERSON(IeAdapterType.QH_PERSON, "WZ2016071810254988302", "validateRelPerson", "好信一鉴通关系人验证", new QhpValidateRelPerson()),
	CCA_QH_PERSON_VALIDATE_CAR(IeAdapterType.QH_PERSON, "WZ2016071810265950004", "validateCar", "好信一鉴通车辆验证", new QhpValidateCar()),
	CCA_QH_PERSON_VALIDATE_HOUSE(IeAdapterType.QH_PERSON, "WZ2016071810263472603", "validateHouse", "好信一鉴通房产验证", new QhpValidateHouse()),
	CCA_QH_PERSON_VALIDATE_FACE(IeAdapterType.QH_PERSON, "WZ2016071810290345306", "validateFace", "好信一鉴通人脸识别", new QhpValidateFace()),
	CCA_QH_PERSON_VALIDATE_EDUCATION(IeAdapterType.QH_PERSON, "WZ201607181031178408", "validateEducation", "好信一鉴通学历验证", new QhpValidateEducation()),
	CCA_QH_PERSON_CARD_VERIFY(IeAdapterType.QH_PERSON, "WZ2016072101593612700", "cardVerify", "好信一鉴通银行卡鉴权信息", new QhpCardVerify()),
	CCA_QH_PERSON_CREDIT(IeAdapterType.QH_PERSON, "WZ2016071806234699502", "credit", "好信度数据", new QhpCredit()),
	CCA_QH_PERSON_LOAN(IeAdapterType.QH_PERSON, "WZ2016071806283562504", "loan", "常贷客数据", new QhpLoan()),
	CCA_QH_PERSON_ADDRESS(IeAdapterType.QH_PERSON, "WZ2016071806335470406", "address", "好信地址通信息", new QhpAddress()),
	CCA_QH_PERSON_COUNT_ENFORCEMENT(IeAdapterType.QH_PERSON, "WZ2016071806383481616", "countEnforcement", "好信法院通-被执行人信息", new QhpCountEnforcement()),
	CCA_QH_PERSON_COUNT_BREAKWORD(IeAdapterType.QH_PERSON, "WZ2016071806433355718", "countBreakword", "好信法院通-失信被执行信息", new QhpCountBreakword()),
	CCA_QH_PERSON_COUNT_ANNOUNCE_RETRIEVE(IeAdapterType.QH_PERSON, "WZ2016071806440418219", "countAnnounceRetrieve", "好信法院通-法院公告信息检索", new QhpCountAnnounceRetrieve()),
	CCA_QH_PERSON_COUNT_ANNOUNCE_SEARCH(IeAdapterType.QH_PERSON, "WZ2016071806442938320", "countAnnounceSearch", "好信法院通-法院公告信息查询", new QhpCountAnnounceSearch()),
	CCA_QH_PERSON_COUNT_JUDGE_RETRIEVE(IeAdapterType.QH_PERSON, "WZ2016071806450521321", "countJudgeRetrieve", "好信法院通-裁判文书信息检索", new QhpCountJudgeRetrieve()),
	CCA_QH_PERSON_COUNT_JUDGE_SEARCH(IeAdapterType.QH_PERSON, "WZ2016071806454771022", "countJudgeSearch", "好信法院通-裁判文书信息查询", new QhpCountJudgeSearch()),
	CCA_QH_PERSON_CHEAT_INFO(IeAdapterType.QH_PERSON, "WZ2016071806314876005", "cheatInfo", "反欺诈风险度认证数据", new QhpCheatInfo()),
	CCA_QH_PERSON_BANK_CARD_INFO(IeAdapterType.QH_PERSON, "WZ2016071806371547913", "bankCardInfo", "好信银行卡资讯信息", new QhpBankCardInfo()),
	CCA_QH_PERSON_COUNT_ENFORINFO(IeAdapterType.QH_PERSON, "WZ2016071806344032507", "countEnforInfo", "好信信用轨迹信息", new QhpCountEnforInfo()),
	CCA_QH_PERSON_DEIVE_SCORE(IeAdapterType.QH_PERSON, "WZ2016071806353261809", "deiveScore", "好信驾驶分数据", new QhpDeiveScore()),
	CCA_QH_PERSON_RENT_CAR(IeAdapterType.QH_PERSON, "WZ2016071806350973608", "rentCar", "好信租车客信用评分数据", new QhpRentCar()),
	CCA_QH_PERSON_BANK_SCORE(IeAdapterType.QH_PERSON, "WZ2016071806374036014", "bankScore", "好信银行卡评分信息", new QhpBankScore()),
	CCA_QH_PERSON_MOBILE_CONSULT(IeAdapterType.QH_PERSON, "WZ2016071806364922312", "mobileConsult", "好信手机综合资讯", new QhpMobileConsult()),
	CCA_QH_PERSON_DEIVE_TASK(IeAdapterType.QH_PERSON, "WZ2016072601422991900", "deiveTask", "好信提交驾驶证比对任务数据", new QhpDeiveTask()),
	CCA_QH_PERSON_DEIVE_STATE(IeAdapterType.QH_PERSON, "WZ2016072601425382601", "deiveState", "好信提交驾驶证状态比对数据", new QhpDeiveState()),
	CCA_QH_PERSON_DEIVE_DATAS(IeAdapterType.QH_PERSON, "WZ2016072602005664100", "deiveDatas", "驾驶证比对查询数据", new QhpDeiveDatas()),
	
	
	/**未完成接口**/	
	CCA_QH_PERSON_COUNT_ANNORETR_RESULTS(IeAdapterType.QH_PERSON, "", "countAnnoretrResults", "好信法院通-法院公告信息检索查询结果", new QhpCountAnnoretrResults()),
	CCA_QH_PERSON_COUNT_ANNOSEAR_NOTICE(IeAdapterType.QH_PERSON, "", "countAnnosearNotice", "好信法院通-法院公告信息查询结果", new QhpCountAnnosearNotice()),
	CCA_QH_PERSON_COUNT_BREWORDINFO(IeAdapterType.QH_PERSON, "", "countBrewordInfo", "好信法院通-失信被执行人详情信息表", new QhpCountBrewordInfo()),
	CCA_QH_PERSON_COUNT_JUDGE_RESULTS(IeAdapterType.QH_PERSON, "", "countJudgretrResults", "好信法院通-裁判文书信息检索查询结果", new QhpCountJudgretrResults()),
	CCA_QH_PERSON_COUNT_JUDGSEAR_DOCS(IeAdapterType.QH_PERSON, "", "countJudgsearDocs", "好信法院通-裁判文书信息查询详情信息表", new QhpCountJudgsearDocs()),
	CCA_QH_PERSON_CREDIT_TRACK(IeAdapterType.QH_PERSON, "", "creditTrack", "好信信用轨迹信息", new QhpCreditTrack()),
	CCA_QH_PERSON_MOBILE_OVER_ALLINC(IeAdapterType.QH_PERSON, "", "mobileOverAllInc", "好信手机综合资讯附表", new QhpMobileOverAllInc()),
	CCA_QH_PERSON_PINGAN_RES_STRUCT(IeAdapterType.QH_PERSON, "", "pinganResStruct", "好信平安主体结构信息", new QhpPinganResStruct()),
	CCA_QH_PERSON_RISK(IeAdapterType.QH_PERSON, "WZ2016071806260260203", "risk", "风险度提示信息", new QhpRisk());
	

	public static final String C = "c_";
	private IeAdapterType type;//类别
	private String sub;//子分类
	private String remark;//子分类
	private IAdapter<?> adapter;//转换类
	private String apiKey;//唯一标识
	private IeAdapter(IeAdapterType type, String apiKey, String sub, String remark, IAdapter<?> adapter) {
		this.type = type;
		this.sub = sub;
		this.apiKey = apiKey;
		this.remark = remark;
		this.adapter = adapter;
	}
	/**
	 * 根据类型获取枚举
	 * @param db
	 * @return
	 */
	public static List<IeAdapter> getIeAdapterByType(IeAdapterType type) {
		List<IeAdapter>  adapters = new ArrayList<IeAdapter>();
		IeAdapter[] ccAdapters = IeAdapter.values();
		for (IeAdapter ccAdapter : ccAdapters) {
			if((ccAdapter.getType()).equals(type)){
				adapters.add(ccAdapter);
			}
		}
		return adapters;
	}

	/**
	 * 根据数据源获取枚举
	 * @param db
	 * @return
	 */
	public static List<IeAdapter> getIeAdapterByDb(IeAdapterDB db) {
		List<IeAdapter>  adapters = new ArrayList<IeAdapter>();
		List<IeAdapterType>  types = IeAdapterType.getIeAdapterTypeByDb(db);
		IeAdapter[] ccAdapters = IeAdapter.values();
		for (IeAdapterType type : types) {
			for (IeAdapter ccAdapter : ccAdapters) {
				if((ccAdapter.getType()).equals(type)){
					adapters.add(ccAdapter);
				}
			}
		}
		return adapters;
	}
	
	/**
	 * 根据类型获取子类型枚举
	 * @param db
	 * @return
	 */
	public static List<String> getSubByType(IeAdapterType type) {
		List<String>  adapters = new ArrayList<String>();
		IeAdapter[] ccAdapters = IeAdapter.values();
		for (IeAdapter ccAdapter : ccAdapters) {
			if((ccAdapter.getType()).equals(type)){
				adapters.add(ccAdapter.getSub());
			}
		}
		return adapters;
	}
	
	/**
	 * 根据类型和子类型获取枚举
	 * @param type
	 * @param sub
	 * @return
	 */
	public static IeAdapter getIeAdapterByTypeAndSub(IeAdapterType type, String sub) {
		IeAdapter[] credits = IeAdapter.values();
		for (IeAdapter credit : credits) {
			if(((credit.getType()).equals(type)) && ((credit.getSub()).equals(sub))){
				return credit;
			}
		}
		return null;
	}

	public static IeAdapter[] init(String type, List<Map<String, String>> vals) {
//		if(vals != null){
//			for (Map<String, String> val : vals) {
//				Set<String> vkeys = val.keySet();
//				for(String key : vkeys) {
//					if(IeAdapter.getCreditCheckingByTypeAndSub(type, key) == null){
//						EnumUtils.addEnum(IeAdapter.class, C+StringUtils.upperCase(type)+"_"+StringUtils.upperCase(key), new Class[]{String.class, String.class, String.class}, new Object[]{type, key, val.get(key)});
//					}
//				}
//			}
//		}
		return IeAdapter.values();
	}

	public static IeAdapter[] initByVo(String type, WindVo vo) {
		if((vo != null) && (vo.getReturnCode()).equals(WHCode.SUCCESS)){
			List<Map<String, String>> vals = new ArrayList<Map<String,String>>();
			JSONObject voResult = vo.getResult();
			Iterator<?> itkeys = vo.getResult().keys();
			try {
		        while(itkeys.hasNext()) {
		            String key = (String) itkeys.next();
		            String value = voResult.getString(key);//类型强转失败会异常
		            
		            Map<String, String> valMap = new HashMap<String, String>();
		            valMap.put(key, value);
		            vals.add(valMap);
		        }
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return init(type, vals);
		}
		return init(type, null);
	}

	public static IeAdapter[] initIaSubVoByVo(String type, WindVo vo) {
		if((vo != null) && (vo.getReturnCode()).equals(WHCode.SUCCESS)){
			List<Map<String, String>> vals = new ArrayList<Map<String,String>>();
			JSONObject voResult = vo.getResult();
			Iterator<?> itkeys = vo.getResult().keys();
			try {
				while(itkeys.hasNext()) {
					String key = (String) itkeys.next();
					String value = voResult.getString(key);//类型强转失败会异常
					
					Map<String, String> valMap = new HashMap<String, String>();
					valMap.put(key, value);
					vals.add(valMap);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return init(type, vals);
		}
		return init(type, null);
	}
	
//	public static List<IeAdapter> getIeAdapterByType(String type) {
//		List<IeAdapter>  ieAdapters = new ArrayList<IeAdapter>();
//		IeAdapter[] adapters = IeAdapter.values();
//		for (IeAdapter ieAdapter : adapters) {
//			if((ieAdapter.getType()).equals(type)){
//				ieAdapters.add(ieAdapter);
//			}
//		}
//		return ieAdapters;
//	}
//
//	public static IeAdapter getIeAdapterByTypeAndSub(String type, String sub) {
//		IeAdapter[] credits = IeAdapter.values();
//		for (IeAdapter credit : credits) {
//			if(((credit.getType()).equals(type)) && ((credit.getSub()).equals(sub))){
//				return credit;
//			}
//		}
//		return null;
//	}
	
	
	public IeAdapterType getType() {
		return type;
	}
	public String getSub() {
		return sub;
	}
	public String getRemark() {
		return remark;
	}
	public IAdapter<?> getAdapter() {
		return adapter;
	}
	public void setType(IeAdapterType type) {
		this.type = type;
	}
	public void setSub(String sub) {
		this.sub = sub;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public void setAdapter(IAdapter<?> adapter) {
		this.adapter = adapter;
	}
	public String getApiKey() {
		return apiKey;
	}
	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}
}

package com.wanfin.fpd.modules.wind.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wanfin.fpd.common.config.SvalBase;
import com.wanfin.fpd.common.utils.StringUtils;
import com.wanfin.fpd.modules.wind.adapter.IAdapterDb;
import com.wanfin.fpd.modules.wind.adapter.IeAdapter;
import com.wanfin.fpd.modules.wind.adapter.IeAdapterDB;
import com.wanfin.fpd.modules.wind.adapter.IeAdapterType;
import com.wanfin.fpd.modules.wind.adapter.WindHttp;
import com.wanfin.fpd.modules.wind.adapter.WindVo;
import com.wanfin.fpd.modules.wind.adapter.corporations.QhCvo;
import com.wanfin.fpd.modules.wind.adapter.person.QhPvo;
import com.wanfin.fpd.modules.wind.adapter.person.QhpCardVerify;
import com.wanfin.fpd.modules.wind.adapter.vo.IaTypeVo;
/**
 * 前海征信数据服务
 * @author Chenh
 * 
 * 	<br>1:风险度提示2.0(主体名称，证件号码，证件类型，查询原因) 
 * 	<br>2:好信度数据(主体名称，证件号码，证件类型，查询原因，手机号码，银行卡号)
 * 	<br>3:地址通数据(主体名称，证件号码，证件类型，查询原因，手机号码，地址) 
 * 	<br>4:常贷客数据(主体名称，证件号码，证件类型)
 */

@Service
@Transactional(readOnly = true)
public class AqhService {
	private IAdapterDb db;
	
	@Autowired
	private AdapterService service;

	public AqhService() {
		super();
		db = new IAdapterDb(IeAdapterDB.DB_QH, AdapterService.WIND_SYS_IP, SvalBase.JsonKey.TOKEN, AdapterService.WIND_SYS_TOKEN_VAL);
	}

	/**
	 * 根据ID标识获取征信信息
	 * @param id 个人身份证号/企业唯一标识
	 * @param IeAdapter 查询参数唯一标识
	 * @return
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
	 * 获取个人信息
	 * @param personId 身份证号
	 * @return
	 */
	@SuppressWarnings("null")
	public IaTypeVo getGr(String id, IeAdapter ieAdapter) {
		IaTypeVo vo = null;
		if((ieAdapter.getType()).equals(IeAdapterType.QH_PERSON)){
			initGr();
			QhPvo qhVo = new QhPvo(service.getTEmployee(id), ieAdapter);
			if((ieAdapter).equals(IeAdapter.CCA_QH_PERSON_VALIDATE)){
				vo = getGrValidate(qhVo);
			}else if((ieAdapter).equals(IeAdapter.CCA_QH_PERSON_VALIDATE_ADDRESS)){
				vo = getGrValidateDZ(qhVo);
			}else if((ieAdapter).equals(IeAdapter.CCA_QH_PERSON_VALIDATE_IDENTITY)){
				vo = getGrValidateSF(qhVo);
			}else if((ieAdapter).equals(IeAdapter.CCA_QH_PERSON_VALIDATE_WORK)){
				vo = getGrValidateWork(qhVo);
			}else if((ieAdapter).equals(IeAdapter.CCA_QH_PERSON_VALIDATE_PHONE)){
				vo = getGrValidatePhone(qhVo);
			}else if((ieAdapter).equals(IeAdapter.CCA_QH_PERSON_VALIDATE_RELPERSON)){
				vo = getGrValidateRelPerson(qhVo);
			}else if((ieAdapter).equals(IeAdapter.CCA_QH_PERSON_VALIDATE_CAR)){
				vo = getGrValidateCar(qhVo);
			}else if((ieAdapter).equals(IeAdapter.CCA_QH_PERSON_VALIDATE_HOUSE)){
				vo = getGrValidateHouse(qhVo);
			}else if((ieAdapter).equals(IeAdapter.CCA_QH_PERSON_VALIDATE_FACE)){
				vo = getGrValidateFace(qhVo);
			}else if((ieAdapter).equals(IeAdapter.CCA_QH_PERSON_VALIDATE_EDUCATION)){
				vo = getGrValidateEducation(qhVo);
			}else if((ieAdapter).equals(IeAdapter.CCA_QH_PERSON_CARD_VERIFY)){
				vo = getGrCardVerify(qhVo);
			}else if((ieAdapter).equals(IeAdapter.CCA_QH_PERSON_CREDIT)){
				vo = getGrCredit(qhVo);
			}else if((ieAdapter).equals(IeAdapter.CCA_QH_PERSON_LOAN)){
				vo = getGrLoan(qhVo);
			}else if((ieAdapter).equals(IeAdapter.CCA_QH_PERSON_ADDRESS)){
				vo = getGrAddress(qhVo);
			}else if((ieAdapter).equals(IeAdapter.CCA_QH_PERSON_COUNT_ENFORCEMENT)){
				vo = getGrCountEnforcement(qhVo);
			}else if((ieAdapter).equals(IeAdapter.CCA_QH_PERSON_COUNT_BREAKWORD)){
				vo = getGrCountBreakword(qhVo);
			}else if((ieAdapter).equals(IeAdapter.CCA_QH_PERSON_COUNT_ANNOUNCE_RETRIEVE)){
				vo = getGrCountAnnounceRetrieve(qhVo);
			}else if((ieAdapter).equals(IeAdapter.CCA_QH_PERSON_COUNT_ANNOUNCE_SEARCH)){
				vo = getGrCountAnnounceSearch(qhVo);
			}else if((ieAdapter).equals(IeAdapter.CCA_QH_PERSON_COUNT_JUDGE_RETRIEVE)){
				vo = getGrCountJudgeRetrieve(qhVo);
			}else if((ieAdapter).equals(IeAdapter.CCA_QH_PERSON_COUNT_JUDGE_SEARCH)){
				vo = getGrCountJudgeSearch(qhVo);
			}else if((ieAdapter).equals(IeAdapter.CCA_QH_PERSON_CHEAT_INFO)){
				vo = getGrCheatInfo(qhVo);
			}else if((ieAdapter).equals(IeAdapter.CCA_QH_PERSON_BANK_CARD_INFO)){
				vo = getGrBankCardInfo(qhVo);
			}else if((ieAdapter).equals(IeAdapter.CCA_QH_PERSON_COUNT_ENFORINFO)){
				vo = getGrCountEnforInfo(qhVo);
			}else if((ieAdapter).equals(IeAdapter.CCA_QH_PERSON_RENT_CAR)){
				vo = getGrRentCar(qhVo);
			}else if((ieAdapter).equals(IeAdapter.CCA_QH_PERSON_DEIVE_SCORE)){
				vo = getGrDeiveScore(qhVo);
			}else if((ieAdapter).equals(IeAdapter.CCA_QH_PERSON_BANK_SCORE)){
				vo = getGrBankScore(qhVo);
			}else if((ieAdapter).equals(IeAdapter.CCA_QH_PERSON_MOBILE_CONSULT)){
				vo = getGrMobileConsult(qhVo);
			}else if((ieAdapter).equals(IeAdapter.CCA_QH_PERSON_DEIVE_TASK)){
				vo = getGrDeiveTask(qhVo);
			}else if((ieAdapter).equals(IeAdapter.CCA_QH_PERSON_DEIVE_STATE)){
				vo = getGrDeiveState(qhVo);
			}else if((ieAdapter).equals(IeAdapter.CCA_QH_PERSON_DEIVE_DATAS)){
				vo = getGrDeiveDatas(qhVo);
			}
		}else{
			WindVo wvo = new WindVo();
			wvo.setMessage("该接口只能查询个人征信信息！请确定IeAdapterType无误！");
			vo.setVo(wvo);
		}
		return vo;
	}

	/**
	 * @Description: 好信一鉴通验证
	 * @author Chenh
	 * @param vo
	 * @return  
	 * @throws
	 */
	public IaTypeVo getGrValidate(QhPvo vo) {
		initGr();
		WindVo wvo = new WindVo();
		if(StringUtils.isEmpty(db.getToken(true))){
			wvo.setMessage("TOKEN不能为空!");
			wvo.setReturnCode(WindHttp.WHCode.ERR_TOKEN);
			return new IaTypeVo(wvo);
		}
		vo.setIdType(QhPvo.ID_TYPE);
		if(StringUtils.isEmpty(vo.getIdNo())){
			wvo.setMessage("证件号不能为空!");
			wvo.setReturnCode(WindHttp.WHCode.ERR_NUll);
			return new IaTypeVo(wvo);
		}
		if(StringUtils.isEmpty(vo.getName())){
			wvo.setMessage("主体名称不能为空!");
			wvo.setReturnCode(WindHttp.WHCode.ERR_NUll);
		}
		if(StringUtils.isEmpty(vo.getMobileNo())){
			wvo.setMessage("手机号不能为空!");
			wvo.setReturnCode(WindHttp.WHCode.ERR_NUll);
		}
		vo.setReasonCode(QhPvo.REASONCODE);
		
		if(StringUtils.isNotEmpty(wvo.getMessage())){
			return new IaTypeVo(wvo);
		}else{
			wvo.setReturnCode(null);
		}
		String json = "{"+db.getToken(true)+",\"idNo\":\""+vo.getIdNo()+"\",\"idType\":\""+vo.getIdType()+"\",\"name\":\""+vo.getName()+"\",\"mobileNo\":\""+vo.getMobileNo()+"\",\"busiDesc\":\""+vo.getBusiDesc()+"\",\"reasonCode\":\""+vo.getReasonCode()+"\"}";
		db.setParams(json);
		db.setApi(db.getApi()+IeAdapter.CCA_QH_PERSON_VALIDATE.getApiKey());
		IaTypeVo iaTypeVo = service.initJsonData(db, vo.getIdNo(), WindHttp.WT_PERSON);
		service.saveCreaditChecking(vo.getIdNo(), IeAdapter.CCA_QH_PERSON_VALIDATE, iaTypeVo);
		return iaTypeVo;
	}
	
	/**
	 * @Description: 好信一鉴通地址验证 
	 * @author Chenh
	 * @param vo
	 * @return  
	 * @throws
	 */
	public IaTypeVo getGrValidateDZ(QhPvo vo) {
		initGr();
		WindVo wvo = new WindVo();
		if(StringUtils.isEmpty(db.getToken(true))){
			wvo.setMessage("TOKEN不能为空!");
			wvo.setReturnCode(WindHttp.WHCode.ERR_TOKEN);
			return new IaTypeVo(wvo);
		}
		vo.setIdType(QhPvo.ID_TYPE);
		if(StringUtils.isEmpty(vo.getIdNo())){
			wvo.setMessage("证件号不能为空!");
			wvo.setReturnCode(WindHttp.WHCode.ERR_NUll);
			return new IaTypeVo(wvo);
		}
		if(StringUtils.isEmpty(vo.getName())){
			wvo.setMessage("主体名称不能为空!");
			wvo.setReturnCode(WindHttp.WHCode.ERR_NUll);
		}
		if(StringUtils.isEmpty(vo.getMobileNo())){
			wvo.setMessage("手机号不能为空!");
			wvo.setReturnCode(WindHttp.WHCode.ERR_NUll);
		}
		if(StringUtils.isEmpty(vo.getAddress())){
			wvo.setMessage("地址不能为空!");
			wvo.setReturnCode(WindHttp.WHCode.ERR_NUll);
		}
		vo.setReasonCode(QhPvo.REASONCODE);
		
		if(StringUtils.isNotEmpty(wvo.getMessage())){
			return new IaTypeVo(wvo);
		}else{
			wvo.setReturnCode(null);
		}
		String json = "{"+db.getToken(true)+",\"idNo\":\""+vo.getIdNo()+"\",\"idType\":\""+vo.getIdType()+"\",\"name\":\""+vo.getName()+"\",\"mobileNo\":\""+vo.getMobileNo()+"\",\"address\":\""+vo.getAddress()+"\",\"busiDesc\":\""+vo.getBusiDesc()+"\",\"reasonCode\":\""+vo.getReasonCode()+"\"}";
		db.setParams(json);
		db.setApi(db.getApi()+IeAdapter.CCA_QH_PERSON_VALIDATE_ADDRESS.getApiKey());
		IaTypeVo iaTypeVo = service.initJsonData(db, vo.getIdNo(), WindHttp.WT_PERSON);
		service.saveCreaditChecking(vo.getIdNo(), IeAdapter.CCA_QH_PERSON_VALIDATE_ADDRESS, iaTypeVo);
		return iaTypeVo;
	}
	
	/**
	 * @Description: 好信一鉴通身份验证 
	 * @author Chenh
	 * @param vo
	 * @return  
	 * @throws
	 */
	public IaTypeVo getGrValidateSF(QhPvo vo) {
		initGr();
		WindVo wvo = new WindVo();
		if(StringUtils.isEmpty(db.getToken(true))){
			wvo.setMessage("TOKEN不能为空!");
			wvo.setReturnCode(WindHttp.WHCode.ERR_TOKEN);
			return new IaTypeVo(wvo);
		}
		vo.setIdType(QhPvo.ID_TYPE);
		if(StringUtils.isEmpty(vo.getIdNo())){
			wvo.setMessage("证件号不能为空!");
			wvo.setReturnCode(WindHttp.WHCode.ERR_NUll);
			return new IaTypeVo(wvo);
		}
		if(StringUtils.isEmpty(vo.getName())){
			wvo.setMessage("主体名称不能为空!");
			wvo.setReturnCode(WindHttp.WHCode.ERR_NUll);
		}
		if(StringUtils.isEmpty(vo.getMobileNo())){
			wvo.setMessage("手机号不能为空!");
			wvo.setReturnCode(WindHttp.WHCode.ERR_NUll);
		}
		vo.setReasonCode(QhPvo.REASONCODE);
		
		if(StringUtils.isNotEmpty(wvo.getMessage())){
			return new IaTypeVo(wvo);
		}else{
			wvo.setReturnCode(null);
		}
		String json = "{"+db.getToken(true)+",\"idNo\":\""+vo.getIdNo()+"\",\"idType\":\""+vo.getIdType()+"\",\"name\":\""+vo.getName()+"\",\"mobileNo\":\""+vo.getMobileNo()+"\",\"busiDesc\":\""+vo.getBusiDesc()+"\",\"reasonCode\":\""+vo.getReasonCode()+"\"}";
		db.setParams(json);
		db.setApi(db.getApi()+IeAdapter.CCA_QH_PERSON_VALIDATE_IDENTITY.getApiKey());
		IaTypeVo iaTypeVo = service.initJsonData(db, vo.getIdNo(), WindHttp.WT_PERSON);
		service.saveCreaditChecking(vo.getIdNo(), IeAdapter.CCA_QH_PERSON_VALIDATE_IDENTITY, iaTypeVo);
		return iaTypeVo;
	}
	
	/**
	 * @Description: 好信好信一鉴通工作单位验证 
	 * @author Chenh
	 * @param vo
	 * @return  
	 * @throws
	 */
	public IaTypeVo getGrValidateWork(QhPvo vo) {
		initGr();
		WindVo wvo = new WindVo();
		if(StringUtils.isEmpty(db.getToken(true))){
			wvo.setMessage("TOKEN不能为空!");
			wvo.setReturnCode(WindHttp.WHCode.ERR_TOKEN);
			return new IaTypeVo(wvo);
		}
		vo.setIdType(QhPvo.ID_TYPE);
		if(StringUtils.isEmpty(vo.getIdNo())){
			wvo.setMessage("证件号不能为空!");
			wvo.setReturnCode(WindHttp.WHCode.ERR_NUll);
			return new IaTypeVo(wvo);
		}
		if(StringUtils.isEmpty(vo.getName())){
			wvo.setMessage("主体名称不能为空!");
			wvo.setReturnCode(WindHttp.WHCode.ERR_NUll);
		}
		if(StringUtils.isEmpty(vo.getMobileNo())){
			wvo.setMessage("手机号不能为空!");
			wvo.setReturnCode(WindHttp.WHCode.ERR_NUll);
		}
		if(StringUtils.isEmpty(vo.getCompany())){
			wvo.setMessage("工作单位不能为空!");
			wvo.setReturnCode(WindHttp.WHCode.ERR_NUll);
		}
		vo.setReasonCode(QhPvo.REASONCODE);
		
		if(StringUtils.isNotEmpty(wvo.getMessage())){
			return new IaTypeVo(wvo);
		}else{
			wvo.setReturnCode(null);
		}
		String json = "{"+db.getToken(true)+",\"idNo\":\""+vo.getIdNo()+"\",\"idType\":\""+vo.getIdType()+"\",\"name\":\""+vo.getName()+"\",\"mobileNo\":\""+vo.getMobileNo()+"\",\"company\":\""+vo.getCompany()+"\",\"busiDesc\":\""+vo.getBusiDesc()+"\",\"reasonCode\":\""+vo.getReasonCode()+"\"}";
		db.setParams(json);
		db.setApi(db.getApi()+IeAdapter.CCA_QH_PERSON_VALIDATE_WORK.getApiKey());
		IaTypeVo iaTypeVo = service.initJsonData(db, vo.getIdNo(), WindHttp.WT_PERSON);
		service.saveCreaditChecking(vo.getIdNo(), IeAdapter.CCA_QH_PERSON_VALIDATE_WORK, iaTypeVo);
		return iaTypeVo;
	}

	/**   
	* @Description: 好信一鉴通手机验证 
	* @author Chenh
	* @param vo
	* @return  
	* @throws
	*/
	private IaTypeVo getGrValidatePhone(QhPvo vo) {
		initGr();
		WindVo wvo = new WindVo();
		if(StringUtils.isEmpty(db.getToken(true))){
			wvo.setMessage("TOKEN不能为空!");
			wvo.setReturnCode(WindHttp.WHCode.ERR_TOKEN);
			return new IaTypeVo(wvo);
		}
		vo.setIdType(QhPvo.ID_TYPE);
		if(StringUtils.isEmpty(vo.getIdNo())){
			wvo.setMessage("证件号不能为空!");
			wvo.setReturnCode(WindHttp.WHCode.ERR_NUll);
			return new IaTypeVo(wvo);
		}
		if(StringUtils.isEmpty(vo.getName())){
			wvo.setMessage("主体名称不能为空!");
			wvo.setReturnCode(WindHttp.WHCode.ERR_NUll);
		}
		if(StringUtils.isEmpty(vo.getMobileNo())){
			wvo.setMessage("手机号不能为空!");
			wvo.setReturnCode(WindHttp.WHCode.ERR_NUll);
		}
		vo.setReasonCode(QhPvo.REASONCODE);
		
		if(StringUtils.isNotEmpty(wvo.getMessage())){
			return new IaTypeVo(wvo);
		}else{
			wvo.setReturnCode(null);
		}
		String json = "{"+db.getToken(true)+",\"idNo\":\""+vo.getIdNo()+"\",\"idType\":\""+vo.getIdType()+"\",\"name\":\""+vo.getName()+"\",\"mobileNo\":\""+vo.getMobileNo()+"\",\"busiDesc\":\""+vo.getBusiDesc()+"\",\"reasonCode\":\""+vo.getReasonCode()+"\"}";
		db.setParams(json);
		db.setApi(db.getApi()+IeAdapter.CCA_QH_PERSON_VALIDATE_PHONE.getApiKey());
		IaTypeVo iaTypeVo = service.initJsonData(db, vo.getIdNo(), WindHttp.WT_PERSON);
		service.saveCreaditChecking(vo.getIdNo(), IeAdapter.CCA_QH_PERSON_VALIDATE_PHONE, iaTypeVo);
		return iaTypeVo;
	}
	
	/**   
	 * @Description: 好信一鉴通关系人验证
	 * @author Chenh
	 * @param vo
	 * @return  
	 * @throws
	 */
	private IaTypeVo getGrValidateRelPerson(QhPvo vo) {
		initGr();
		WindVo wvo = new WindVo();
		if(StringUtils.isEmpty(db.getToken(true))){
			wvo.setMessage("TOKEN不能为空!");
			wvo.setReturnCode(WindHttp.WHCode.ERR_TOKEN);
			return new IaTypeVo(wvo);
		}
		vo.setIdType(QhPvo.ID_TYPE);
		if(StringUtils.isEmpty(vo.getIdNo())){
			wvo.setMessage("证件号不能为空!");
			wvo.setReturnCode(WindHttp.WHCode.ERR_NUll);
			return new IaTypeVo(wvo);
		}
		if(StringUtils.isEmpty(vo.getName())){
			wvo.setMessage("主体名称不能为空!");
			wvo.setReturnCode(WindHttp.WHCode.ERR_NUll);
		}
		if(StringUtils.isEmpty(vo.getMobileNo())){
			wvo.setMessage("手机号不能为空!");
			wvo.setReturnCode(WindHttp.WHCode.ERR_NUll);
		}
		if(StringUtils.isEmpty(vo.getRefName())){
			wvo.setMessage("联系人不能为空!");
			wvo.setReturnCode(WindHttp.WHCode.ERR_NUll);
		}
		if(StringUtils.isEmpty(vo.getRefMobileNo())){
			wvo.setMessage("联系人手机号码不能为空!");
			wvo.setReturnCode(WindHttp.WHCode.ERR_NUll);
		}
		vo.setReasonCode(QhPvo.REASONCODE);
		
		if(StringUtils.isNotEmpty(wvo.getMessage())){
			return new IaTypeVo(wvo);
		}else{
			wvo.setReturnCode(null);
		}
		String json = "{"+db.getToken(true)+",\"idNo\":\""+vo.getIdNo()+"\",\"idType\":\""+vo.getIdType()+"\",\"name\":\""+vo.getName()+"\",\"mobileNo\":\""+vo.getMobileNo()+"\",\"refName\":\""+vo.getRefName()+"\",\"refMobileNo\":\""+vo.getRefMobileNo()+"\",\"busiDesc\":\""+vo.getBusiDesc()+"\",\"reasonCode\":\""+vo.getReasonCode()+"\"}";
		db.setParams(json);
		db.setApi(db.getApi()+IeAdapter.CCA_QH_PERSON_VALIDATE_RELPERSON.getApiKey());
		IaTypeVo iaTypeVo = service.initJsonData(db, vo.getIdNo(), WindHttp.WT_PERSON);
		service.saveCreaditChecking(vo.getIdNo(), IeAdapter.CCA_QH_PERSON_VALIDATE_RELPERSON, iaTypeVo);
		return iaTypeVo;
	}
	
	/**   
	 * @Description: 好信一鉴通车辆验证
	 * @author Chenh
	 * @param vo
	 * @return  
	 * @throws
	 */
	private IaTypeVo getGrValidateCar(QhPvo vo) {
		initGr();
		WindVo wvo = new WindVo();
		if(StringUtils.isEmpty(db.getToken(true))){
			wvo.setMessage("TOKEN不能为空!");
			wvo.setReturnCode(WindHttp.WHCode.ERR_TOKEN);
			return new IaTypeVo(wvo);
		}
		vo.setIdType(QhPvo.ID_TYPE);
		if(StringUtils.isEmpty(vo.getIdNo())){
			wvo.setMessage("证件号不能为空!");
			wvo.setReturnCode(WindHttp.WHCode.ERR_NUll);
			return new IaTypeVo(wvo);
		}
		if(StringUtils.isEmpty(vo.getName())){
			wvo.setMessage("主体名称不能为空!");
			wvo.setReturnCode(WindHttp.WHCode.ERR_NUll);
		}
		if(StringUtils.isEmpty(vo.getMobileNo())){
			wvo.setMessage("手机号不能为空!");
			wvo.setReturnCode(WindHttp.WHCode.ERR_NUll);
		}
		if(StringUtils.isEmpty(vo.getRefName())){
			wvo.setMessage("联系人不能为空!");
			wvo.setReturnCode(WindHttp.WHCode.ERR_NUll);
		}
		if(StringUtils.isEmpty(vo.getRefMobileNo())){
			wvo.setMessage("联系人手机号码不能为空!");
			wvo.setReturnCode(WindHttp.WHCode.ERR_NUll);
		}
		vo.setReasonCode(QhPvo.REASONCODE);
		
		if(StringUtils.isNotEmpty(wvo.getMessage())){
			return new IaTypeVo(wvo);
		}else{
			wvo.setReturnCode(null);
		}
		String json = "{"+db.getToken(true)+",\"idNo\":\""+vo.getIdNo()+"\",\"idType\":\""+vo.getIdType()+"\",\"name\":\""+vo.getName()+"\",\"mobileNo\":\""+vo.getMobileNo()+"\",\"refName\":\""+vo.getRefName()+"\",\"refMobileNo\":\""+vo.getRefMobileNo()+"\",\"busiDesc\":\""+vo.getBusiDesc()+"\",\"reasonCode\":\""+vo.getReasonCode()+"\"}";
		db.setParams(json);
		db.setApi(db.getApi()+IeAdapter.CCA_QH_PERSON_VALIDATE_CAR.getApiKey());
		IaTypeVo iaTypeVo = service.initJsonData(db, vo.getIdNo(), WindHttp.WT_PERSON);
		service.saveCreaditChecking(vo.getIdNo(), IeAdapter.CCA_QH_PERSON_VALIDATE_CAR, iaTypeVo);
		return iaTypeVo;
	}
	
	/**   
	 * @Description: 好信一鉴通房产验证
	 * @author Chenh
	 * @param vo
	 * @return  
	 * @throws
	 */
	private IaTypeVo getGrValidateHouse(QhPvo vo) {
		initGr();
		WindVo wvo = new WindVo();
		if(StringUtils.isEmpty(db.getToken(true))){
			wvo.setMessage("TOKEN不能为空!");
			wvo.setReturnCode(WindHttp.WHCode.ERR_TOKEN);
			return new IaTypeVo(wvo);
		}
		vo.setIdType(QhPvo.ID_TYPE);
		if(StringUtils.isEmpty(vo.getIdNo())){
			wvo.setMessage("证件号不能为空!");
			wvo.setReturnCode(WindHttp.WHCode.ERR_NUll);
			return new IaTypeVo(wvo);
		}
		if(StringUtils.isEmpty(vo.getName())){
			wvo.setMessage("主体名称不能为空!");
			wvo.setReturnCode(WindHttp.WHCode.ERR_NUll);
		}
		if(StringUtils.isEmpty(vo.getMobileNo())){
			wvo.setMessage("手机号不能为空!");
			wvo.setReturnCode(WindHttp.WHCode.ERR_NUll);
		}
		vo.setReasonCode(QhPvo.REASONCODE);
		
		if(StringUtils.isNotEmpty(wvo.getMessage())){
			return new IaTypeVo(wvo);
		}else{
			wvo.setReturnCode(null);
		}
		String json = "{"+db.getToken(true)+",\"idNo\":\""+vo.getIdNo()+"\",\"idType\":\""+vo.getIdType()+"\",\"name\":\""+vo.getName()+"\",\"mobileNo\":\""+vo.getMobileNo()+"\",\"busiDesc\":\""+vo.getBusiDesc()+"\",\"reasonCode\":\""+vo.getReasonCode()+"\"}";
		db.setParams(json);
		db.setApi(db.getApi()+IeAdapter.CCA_QH_PERSON_VALIDATE_HOUSE.getApiKey());
		IaTypeVo iaTypeVo = service.initJsonData(db, vo.getIdNo(), WindHttp.WT_PERSON);
		service.saveCreaditChecking(vo.getIdNo(), IeAdapter.CCA_QH_PERSON_VALIDATE_HOUSE, iaTypeVo);
		return iaTypeVo;
	}
	
	/**   
	 * @Description: 好信一鉴通人脸识别
	 * @author Chenh
	 * @param vo
	 * @return  
	 * @throws
	 */
	private IaTypeVo getGrValidateFace(QhPvo vo) {
		initGr();
		WindVo wvo = new WindVo();
		if(StringUtils.isEmpty(db.getToken(true))){
			wvo.setMessage("TOKEN不能为空!");
			wvo.setReturnCode(WindHttp.WHCode.ERR_TOKEN);
			return new IaTypeVo(wvo);
		}
		vo.setIdType(QhPvo.ID_TYPE);
		if(StringUtils.isEmpty(vo.getIdNo())){
			wvo.setMessage("证件号不能为空!");
			wvo.setReturnCode(WindHttp.WHCode.ERR_NUll);
			return new IaTypeVo(wvo);
		}
		if(StringUtils.isEmpty(vo.getName())){
			wvo.setMessage("主体名称不能为空!");
			wvo.setReturnCode(WindHttp.WHCode.ERR_NUll);
		}
		if(StringUtils.isEmpty(vo.getMobileNo())){
			wvo.setMessage("手机号不能为空!");
			wvo.setReturnCode(WindHttp.WHCode.ERR_NUll);
		}
		if(StringUtils.isEmpty(vo.getPhoto64())){
			wvo.setMessage("相片不能为空!");
			wvo.setReturnCode(WindHttp.WHCode.ERR_NUll);
		}
		if(StringUtils.isEmpty(vo.getAreaCode())){
			wvo.setMessage("行政区代码不能为空!");
			wvo.setReturnCode(WindHttp.WHCode.ERR_NUll);
		}
		vo.setReasonCode(QhPvo.REASONCODE);
		
		if(StringUtils.isNotEmpty(wvo.getMessage())){
			return new IaTypeVo(wvo);
		}else{
			wvo.setReturnCode(null);
		}
		String json = "{"+db.getToken(true)+",\"idNo\":\""+vo.getIdNo()+"\",\"idType\":\""+vo.getIdType()+"\",\"name\":\""+vo.getName()+"\",\"mobileNo\":\""+vo.getMobileNo()+"\",\"photo64\":\""+vo.getPhoto64()+"\",\"areaCode\":\""+vo.getAreaCode()+"\",\"busiDesc\":\""+vo.getBusiDesc()+"\",\"reasonCode\":\""+vo.getReasonCode()+"\"}";
		db.setParams(json);
		db.setApi(db.getApi()+IeAdapter.CCA_QH_PERSON_VALIDATE_FACE.getApiKey());
		IaTypeVo iaTypeVo = service.initJsonData(db, vo.getIdNo(), WindHttp.WT_PERSON);
		service.saveCreaditChecking(vo.getIdNo(), IeAdapter.CCA_QH_PERSON_VALIDATE_FACE, iaTypeVo);
		return iaTypeVo;
	}
	
	/**   
	 * @Description: 好信一鉴通学历验证
	 * @author Chenh
	 * @param vo
	 * @return  
	 * @throws
	 */
	private IaTypeVo getGrValidateEducation(QhPvo vo) {
		initGr(); 
		WindVo wvo = new WindVo();
		if(StringUtils.isEmpty(db.getToken(true))){
			wvo.setMessage("TOKEN不能为空!");
			wvo.setReturnCode(WindHttp.WHCode.ERR_TOKEN);
			return new IaTypeVo(wvo);
		}
		vo.setIdType(QhPvo.ID_TYPE);
		if(StringUtils.isEmpty(vo.getIdNo())){
			wvo.setMessage("证件号不能为空!");
			wvo.setReturnCode(WindHttp.WHCode.ERR_NUll);
			return new IaTypeVo(wvo);
		}
		if(StringUtils.isEmpty(vo.getName())){
			wvo.setMessage("主体名称不能为空!");
			wvo.setReturnCode(WindHttp.WHCode.ERR_NUll);
		}
		if(StringUtils.isEmpty(vo.getMobileNo())){
			wvo.setMessage("手机号不能为空!");
			wvo.setReturnCode(WindHttp.WHCode.ERR_NUll);
		}
		if(StringUtils.isEmpty(vo.getEductionBckgrd())){
			wvo.setMessage("学历背景不能为空!");
			wvo.setReturnCode(WindHttp.WHCode.ERR_NUll);
		}
		vo.setReasonCode(QhPvo.REASONCODE);
		
		if(StringUtils.isNotEmpty(wvo.getMessage())){
			return new IaTypeVo(wvo);
		}else{
			wvo.setReturnCode(null);
		}
		String json = "{"+db.getToken(true)+",\"idNo\":\""+vo.getIdNo()+"\",\"idType\":\""+vo.getIdType()+"\",\"name\":\""+vo.getName()+"\",\"mobileNo\":\""+vo.getMobileNo()+"\",\"eductionBckgrd\":\""+vo.getEductionBckgrd ()+"\",\"busiDesc\":\""+vo.getBusiDesc()+"\",\"reasonCode\":\""+vo.getReasonCode()+"\"}";
		db.setParams(json);
		db.setApi(db.getApi()+IeAdapter.CCA_QH_PERSON_VALIDATE_EDUCATION.getApiKey());
		IaTypeVo iaTypeVo = service.initJsonData(db, vo.getIdNo(), WindHttp.WT_PERSON);
		service.saveCreaditChecking(vo.getIdNo(), IeAdapter.CCA_QH_PERSON_VALIDATE_EDUCATION, iaTypeVo);
		return iaTypeVo;
	}
	
	/**   
	* @Description: 一鉴通银行卡鉴权
	* IdType：开户人证件类型
	* 	I-居民身份证
	* 	P-外国护照
	* 	M-军官证
	* 	S-港澳通行证或台胞证
	* 	C-中国护照
	* 	L-中国临时身份证
	* 	H-户口本编号
	* 	B-士兵证
	* 	J-警官证
	* 	R-外国公民永久居留证
	* 	O-其他
	* AuthType:鉴权类型
	* 	A3：鉴权三要素（姓名、证件号、银行卡号）
	* 	A4：鉴权四要素（姓名、证件号、银行卡号、手机号码）
	* 	A6：鉴权六要素（姓名、证件号、银行卡号、手机号码、有效期（年、月）、cvn码）
	* @author Chenh
	* @param qhVo
	* @return  
	* @throws
	*/
	private IaTypeVo getGrCardVerify(QhPvo vo) {
		initGr();
		WindVo wvo = new WindVo();
		if(StringUtils.isEmpty(db.getToken(true))){
			wvo.setMessage("TOKEN不能为空!");
			wvo.setReturnCode(WindHttp.WHCode.ERR_TOKEN);
			return new IaTypeVo(wvo);
		}
		if(StringUtils.isEmpty(vo.getAuthType())){
			wvo.setMessage("鉴权类型不能为空!");
			wvo.setReturnCode(WindHttp.WHCode.ERR_NUll);
		}else{
			if((QhpCardVerify.CVauthType.A3).equals(vo.getAuthType())){
				
			}else if((QhpCardVerify.CVauthType.A4).equals(vo.getAuthType())){
				if(StringUtils.isEmpty(vo.getMobileNo())){
					wvo.setMessage("手机号不能为空!");
					wvo.setReturnCode(WindHttp.WHCode.ERR_NUll);
				}
			}else if((QhpCardVerify.CVauthType.A6).equals(vo.getAuthType())){
				if(StringUtils.isEmpty(vo.getMobileNo())){
					wvo.setMessage("手机号不能为空!");
					wvo.setReturnCode(WindHttp.WHCode.ERR_NUll);
				}
				if(StringUtils.isEmpty(vo.getExpiresYear())){
					wvo.setMessage("有效期年不能为空!");
					wvo.setReturnCode(WindHttp.WHCode.ERR_NUll);
				}
				if(StringUtils.isEmpty(vo.getExpiresMonth())){
					wvo.setMessage("有效期月不能为空!");
					wvo.setReturnCode(WindHttp.WHCode.ERR_NUll);
				}
				if(StringUtils.isEmpty(vo.getCreditCardCVN())){
					wvo.setMessage("信用卡cvn码不能为空!");
					wvo.setReturnCode(WindHttp.WHCode.ERR_NUll);
				}
			}

			if(StringUtils.isEmpty(vo.getIdType())){
				wvo.setMessage("开户人证件类型不能为空!");
				wvo.setReturnCode(WindHttp.WHCode.ERR_NUll);
				return new IaTypeVo(wvo);
			}
			if(StringUtils.isEmpty(vo.getIdNo())){
				wvo.setMessage("证件号不能为空!");
				wvo.setReturnCode(WindHttp.WHCode.ERR_NUll);
				return new IaTypeVo(wvo);
			}
			if(StringUtils.isEmpty(vo.getCardNo())){
				wvo.setMessage("银行卡号不能为空!");
				wvo.setReturnCode(WindHttp.WHCode.ERR_NUll);
			}
			if(StringUtils.isEmpty(vo.getName())){
				wvo.setMessage("主体名称不能为空!");
				wvo.setReturnCode(WindHttp.WHCode.ERR_NUll);
			}
		}
		vo.setReasonCode(QhPvo.REASONCODE);
		
		if(StringUtils.isNotEmpty(wvo.getMessage())){
			return new IaTypeVo(wvo);
		}else{
			wvo.setReturnCode(null);
		}
		String json = "{"+db.getToken(true)+",\"idNo\":\""+vo.getIdNo()+"\",\"idType\":\""+vo.getIdType()+"\",\"name\":\""+vo.getName()+"\",\"cardNo\":\""+vo.getCardNo()+"\",\"authType\":\""+vo.getAuthType()+"\",\"mobileNo\":\""+vo.getMobileNo()+"\",\"expiresYear\":\""+vo.getExpiresYear()+"\",\"expiresMonth\":\""+vo.getExpiresMonth()+"\",\"creditCardCVN\":\""+vo.getCreditCardCVN()+"\",\"busiDesc\":\""+vo.getBusiDesc()+"\",\"reasonCode\":\""+vo.getReasonCode()+"\"}";
		db.setParams(json);
		db.setApi(db.getApi()+IeAdapter.CCA_QH_PERSON_CARD_VERIFY.getApiKey());
		IaTypeVo iaTypeVo = service.initJsonData(db, vo.getIdNo(), WindHttp.WT_PERSON);
		service.saveCreaditChecking(vo.getIdNo(), IeAdapter.CCA_QH_PERSON_CARD_VERIFY, iaTypeVo);
		return iaTypeVo;
	}
	
	/**
	 * @Description: 风险度提示信息
	 * @author Chenh
	 * @param vo
	 * @return  
	 * @throws
	 */
	public IaTypeVo getGrRisk(QhPvo vo) {
		initGr();
		WindVo wvo = new WindVo();
		if(StringUtils.isEmpty(db.getToken(true))){
			wvo.setMessage("TOKEN不能为空!");
			wvo.setReturnCode(WindHttp.WHCode.ERR_TOKEN);
			return new IaTypeVo(wvo);
		}
		vo.setIdType(QhPvo.ID_TYPE);
		if(StringUtils.isEmpty(vo.getIdNo())){
			wvo.setMessage("证件号不能为空!");
			wvo.setReturnCode(WindHttp.WHCode.ERR_NUll);
			return new IaTypeVo(wvo);
		}
		if(StringUtils.isEmpty(vo.getName())){
			wvo.setMessage("主体名称不能为空!");
			wvo.setReturnCode(WindHttp.WHCode.ERR_NUll);
		}
//		if(StringUtils.isEmpty(vo.getMoblieNos())){
//			wvo.setMessage("手机号集不能为空!");
//			wvo.setReturnCode(WindHttp.WHCode.ERR_NUll);
//		}
//		if(StringUtils.isEmpty(vo.getIps())){
//			wvo.setMessage("IP集不能为空!");
//			wvo.setReturnCode(WindHttp.WHCode.ERR_NUll);
//		}
//		if(StringUtils.isEmpty(vo.getCardNos())){
//			wvo.setMessage("卡号集不能为空!");
//			wvo.setReturnCode(WindHttp.WHCode.ERR_NUll);
//		}
		vo.setReasonCode("2");//贷中管理
		
		if(StringUtils.isNotEmpty(wvo.getMessage())){
			return new IaTypeVo(wvo);
		}else{
			wvo.setReturnCode(null);
		}
		String json = "{"+db.getToken(true)+",\"idNo\":\""+vo.getIdNo()+"\",\"idType\":\""+vo.getIdType()+"\",\"name\":\""+vo.getName()+"\",\"moblieNos\":\""+vo.getMoblieNos()+"\",\"ips\":\""+vo.getIps()+"\",\"cardNos\":\""+vo.getCardNos()+"\",\"busiDesc\":\""+vo.getBusiDesc()+"\",\"reasonCode\":\""+vo.getReasonCode()+"\"}";
		db.setParams(json);
		db.setApi(db.getApi()+IeAdapter.CCA_QH_PERSON_RISK.getApiKey());
		IaTypeVo iaTypeVo = service.initJsonData(db, vo.getIdNo(), WindHttp.WT_PERSON);
		service.saveCreaditChecking(vo.getIdNo(), IeAdapter.CCA_QH_PERSON_RISK, iaTypeVo);
		return iaTypeVo;
	}
	
	/**   
	* @Description: 好信度数据
	* @author Chenh
	* @param vo
	* @return  
	* @throws
	*/
	private IaTypeVo getGrCredit(QhPvo vo) {
		initGr();
		WindVo wvo = new WindVo();
		if(StringUtils.isEmpty(db.getToken(true))){
			wvo.setMessage("TOKEN不能为空!");
			wvo.setReturnCode(WindHttp.WHCode.ERR_TOKEN);
			return new IaTypeVo(wvo);
		}
		vo.setIdType(QhPvo.ID_TYPE);
		if(StringUtils.isEmpty(vo.getIdNo())){
			wvo.setMessage("证件号不能为空!");
			wvo.setReturnCode(WindHttp.WHCode.ERR_NUll);
			return new IaTypeVo(wvo);
		}
		if(StringUtils.isEmpty(vo.getName())){
			wvo.setMessage("主体名称不能为空!");
			wvo.setReturnCode(WindHttp.WHCode.ERR_NUll);
		}
		if(StringUtils.isEmpty(vo.getMobileNo())){
			wvo.setMessage("手机号不能为空!");
			wvo.setReturnCode(WindHttp.WHCode.ERR_NUll);
		}
		if(StringUtils.isEmpty(vo.getCardNo())){
			wvo.setMessage("银行卡号不能为空!");
			wvo.setReturnCode(WindHttp.WHCode.ERR_NUll);
		}
		vo.setReasonCode(QhPvo.REASONCODE);
		
		if(StringUtils.isNotEmpty(wvo.getMessage())){
			return new IaTypeVo(wvo);
		}else{
			wvo.setReturnCode(null);
		}
		String json = "{"+db.getToken(true)+",\"idNo\":\""+vo.getIdNo()+"\",\"idType\":\""+vo.getIdType()+"\",\"name\":\""+vo.getName()+"\",\"mobileNo\":\""+vo.getMobileNo()+"\",\"cardNo\":\""+vo.getCardNo()+"\",\"email\":\""+vo.getEmail()+"\",\"weiboNo\":\""+vo.getWeiboNo()+"\",\"weixinNo\":\""+vo.getWeixinNo()+"\",\"qqNo\":\""+vo.getQqNo()+"\",\"taobaoNo\":\""+vo.getTaobaoNo()+"\",\"jdNo\":\""+vo.getJdno()+"\",\"amazonNo\":\""+vo.getAmazonNo()+"\",\"yhdNo\":\""+vo.getYhdNo()+"\",\"busiDesc\":\""+vo.getBusiDesc()+"\",\"reasonCode\":\""+vo.getReasonCode()+"\"}";
		db.setParams(json);
		db.setApi(db.getApi()+IeAdapter.CCA_QH_PERSON_CREDIT.getApiKey());
		IaTypeVo iaTypeVo = service.initJsonData(db, vo.getIdNo(), WindHttp.WT_PERSON);
		service.saveCreaditChecking(vo.getIdNo(), IeAdapter.CCA_QH_PERSON_CREDIT, iaTypeVo);
		return iaTypeVo;
	}

	/**   
	 * @Description: 常贷客数据
	 * @author Chenh
	 * @param vo
	 * @return  
	 * @throws
	 */
	private IaTypeVo getGrLoan(QhPvo vo) {
		initGr();
		WindVo wvo = new WindVo();
		if(StringUtils.isEmpty(db.getToken(true))){
			wvo.setMessage("TOKEN不能为空!");
			wvo.setReturnCode(WindHttp.WHCode.ERR_TOKEN);
			return new IaTypeVo(wvo);
		}
		vo.setIdType(QhPvo.ID_TYPE);
		if(StringUtils.isEmpty(vo.getIdNo())){
			wvo.setMessage("证件号不能为空!");
			wvo.setReturnCode(WindHttp.WHCode.ERR_NUll);
			return new IaTypeVo(wvo);
		}
		if(StringUtils.isEmpty(vo.getName())){
			wvo.setMessage("主体名称不能为空!");
			wvo.setReturnCode(WindHttp.WHCode.ERR_NUll);
		}
		vo.setReasonCode(QhPvo.REASONCODE);
		
		if(StringUtils.isNotEmpty(wvo.getMessage())){
			return new IaTypeVo(wvo);
		}else{
			wvo.setReturnCode(null);
		}
		String json = "{"+db.getToken(true)+",\"idNo\":\""+vo.getIdNo()+"\",\"idType\":\""+vo.getIdType()+"\",\"name\":\""+"\",\"busiDesc\":\""+vo.getBusiDesc()+"\",\"reasonCode\":\""+vo.getReasonCode()+"\"}";
		db.setParams(json);
		db.setApi(db.getApi()+IeAdapter.CCA_QH_PERSON_LOAN.getApiKey());
		IaTypeVo iaTypeVo = service.initJsonData(db, vo.getIdNo(), WindHttp.WT_PERSON);
		service.saveCreaditChecking(vo.getIdNo(), IeAdapter.CCA_QH_PERSON_LOAN, iaTypeVo);
		return iaTypeVo;
	}
	
	/**   
	 * @Description: 好信地址通信息
	 * @author Chenh
	 * @param vo
	 * @return  
	 * @throws
	 */
	private IaTypeVo getGrAddress(QhPvo vo) {
		initGr();
		WindVo wvo = new WindVo();
		if(StringUtils.isEmpty(db.getToken(true))){
			wvo.setMessage("TOKEN不能为空!");
			wvo.setReturnCode(WindHttp.WHCode.ERR_TOKEN);
			return new IaTypeVo(wvo);
		}
		vo.setIdType(QhPvo.ID_TYPE);
		if(StringUtils.isEmpty(vo.getIdNo())){
			wvo.setMessage("证件号不能为空!");
			wvo.setReturnCode(WindHttp.WHCode.ERR_NUll);
			return new IaTypeVo(wvo);
		}
		if(StringUtils.isEmpty(vo.getName())){
			wvo.setMessage("主体名称不能为空!");
			wvo.setReturnCode(WindHttp.WHCode.ERR_NUll);
		}
		if(StringUtils.isEmpty(vo.getMobileNo())){
			wvo.setMessage("手机号不能为空!");
			wvo.setReturnCode(WindHttp.WHCode.ERR_NUll);
		}
		if(StringUtils.isEmpty(vo.getAddress())){
			wvo.setMessage("地址不能为空!");
			wvo.setReturnCode(WindHttp.WHCode.ERR_NUll);
		}
		vo.setReasonCode(QhPvo.REASONCODE);
		
		if(StringUtils.isNotEmpty(wvo.getMessage())){
			return new IaTypeVo(wvo);
		}else{
			wvo.setReturnCode(null);
		}
		String json = "{"+db.getToken(true)+",\"idNo\":\""+vo.getIdNo()+"\",\"idType\":\""+vo.getIdType()+"\",\"name\":\""+vo.getName()+"\",\"mobileNo\":\""+vo.getMobileNo()+"\",\"address\":\""+vo.getAddress()+"\",\"email\":\""+vo.getEmail()+"\",\"qqNo\":\""+vo.getQqNo()+"\",\"busiDesc\":\""+vo.getBusiDesc()+"\",\"reasonCode\":\""+vo.getReasonCode()+"\"}";
		db.setParams(json);
		db.setApi(db.getApi()+IeAdapter.CCA_QH_PERSON_ADDRESS.getApiKey());
		IaTypeVo iaTypeVo = service.initJsonData(db, vo.getIdNo(), WindHttp.WT_PERSON);
		service.saveCreaditChecking(vo.getIdNo(), IeAdapter.CCA_QH_PERSON_ADDRESS, iaTypeVo);
		return iaTypeVo;
	}
	
	/**   
	 * @Description: 好信法院通-被执行人信息
	 * @author Chenh
	 * @param vo
	 * @return  
	 * @throws
	 */
	private IaTypeVo getGrCountEnforcement(QhPvo vo) {
		initGr();
		WindVo wvo = new WindVo();
		if(StringUtils.isEmpty(db.getToken(true))){
			wvo.setMessage("TOKEN不能为空!");
			wvo.setReturnCode(WindHttp.WHCode.ERR_TOKEN);
			return new IaTypeVo(wvo);
		}
		vo.setIdType(QhPvo.ID_TYPE);
		if(StringUtils.isEmpty(vo.getIdNo())){
			wvo.setMessage("证件号不能为空!");
			wvo.setReturnCode(WindHttp.WHCode.ERR_NUll);
			return new IaTypeVo(wvo);
		}
		if(StringUtils.isEmpty(vo.getName())){
			wvo.setMessage("主体名称不能为空!");
			wvo.setReturnCode(WindHttp.WHCode.ERR_NUll);
		}
		vo.setReasonCode(QhPvo.REASONCODE);
		
		if(StringUtils.isNotEmpty(wvo.getMessage())){
			return new IaTypeVo(wvo);
		}else{
			wvo.setReturnCode(null);
		}
		String json = "{"+db.getToken(true)+",\"idNo\":\""+vo.getIdNo()+"\",\"idType\":\""+vo.getIdType()+"\",\"name\":\""+vo.getName()+"\",\"busiDesc\":\""+vo.getBusiDesc()+"\",\"reasonCode\":\""+vo.getReasonCode()+"\"}";
		db.setParams(json);
		db.setApi(db.getApi()+IeAdapter.CCA_QH_PERSON_COUNT_ENFORCEMENT.getApiKey());
		IaTypeVo iaTypeVo = service.initJsonData(db, vo.getIdNo(), WindHttp.WT_PERSON);
		service.saveCreaditChecking(vo.getIdNo(), IeAdapter.CCA_QH_PERSON_COUNT_ENFORCEMENT, iaTypeVo);
		return iaTypeVo;
	}
	
	/**   
	 * @Description: 好信法院通-失信被执行信息
	 * @author Chenh
	 * @param vo
	 * @return  
	 * @throws
	 */
	private IaTypeVo getGrCountBreakword(QhPvo vo) {
		initGr();
		WindVo wvo = new WindVo();
		if(StringUtils.isEmpty(db.getToken(true))){
			wvo.setMessage("TOKEN不能为空!");
			wvo.setReturnCode(WindHttp.WHCode.ERR_TOKEN);
			return new IaTypeVo(wvo);
		}
		vo.setIdType(QhPvo.ID_TYPE);
		if(StringUtils.isEmpty(vo.getIdNo())){
			wvo.setMessage("证件号不能为空!");
			wvo.setReturnCode(WindHttp.WHCode.ERR_NUll);
			return new IaTypeVo(wvo);
		}
		if(StringUtils.isEmpty(vo.getName())){
			wvo.setMessage("主体名称不能为空!");
			wvo.setReturnCode(WindHttp.WHCode.ERR_NUll);
		}
		vo.setReasonCode(QhPvo.REASONCODE);
		
		if(StringUtils.isNotEmpty(wvo.getMessage())){
			return new IaTypeVo(wvo);
		}else{
			wvo.setReturnCode(null);
		}
		String json = "{"+db.getToken(true)+",\"idNo\":\""+vo.getIdNo()+"\",\"idType\":\""+vo.getIdType()+"\",\"name\":\""+vo.getName()+"\",\"busiDesc\":\""+vo.getBusiDesc()+"\",\"reasonCode\":\""+vo.getReasonCode()+"\"}";
		db.setParams(json);
		db.setApi(db.getApi()+IeAdapter.CCA_QH_PERSON_COUNT_BREAKWORD.getApiKey());
		IaTypeVo iaTypeVo = service.initJsonData(db, vo.getIdNo(), WindHttp.WT_PERSON);
		service.saveCreaditChecking(vo.getIdNo(), IeAdapter.CCA_QH_PERSON_COUNT_BREAKWORD, iaTypeVo);
		return iaTypeVo;
	}
	
	/**   
	 * @Description: 好信法院通-法院公告信息检索
	 * @author Chenh
	 * @param vo
	 * @return  
	 * @throws
	 */
	private IaTypeVo getGrCountAnnounceRetrieve(QhPvo vo) {
		initGr();
		WindVo wvo = new WindVo();
		if(StringUtils.isEmpty(db.getToken(true))){
			wvo.setMessage("TOKEN不能为空!");
			wvo.setReturnCode(WindHttp.WHCode.ERR_TOKEN);
			return new IaTypeVo(wvo);
		}
		vo.setIdType(QhPvo.ID_TYPE);
		if(StringUtils.isEmpty(vo.getIdNo())){
			wvo.setMessage("证件号不能为空!");
			wvo.setReturnCode(WindHttp.WHCode.ERR_NUll);
			return new IaTypeVo(wvo);
		}
		if(StringUtils.isEmpty(vo.getName())){
			wvo.setMessage("主体名称不能为空!");
			wvo.setReturnCode(WindHttp.WHCode.ERR_NUll);
		}
		if(StringUtils.isEmpty(vo.getMobileNo())){
			wvo.setMessage("手机号不能为空!");
			wvo.setReturnCode(WindHttp.WHCode.ERR_NUll);
		}
		vo.setReasonCode(QhPvo.REASONCODE);
		
		if(StringUtils.isNotEmpty(wvo.getMessage())){
			return new IaTypeVo(wvo);
		}else{
			wvo.setReturnCode(null);
		}
		String json = "{"+db.getToken(true)+",\"idNo\":\""+vo.getIdNo()+"\",\"idType\":\""+vo.getIdType()+"\",\"name\":\""+vo.getName()+"\",\"mobileNo\":\""+vo.getMobileNo()+"\",\"busiDesc\":\""+vo.getBusiDesc()+"\",\"reasonCode\":\""+vo.getReasonCode()+"\"}";
		db.setParams(json);
		db.setApi(db.getApi()+IeAdapter.CCA_QH_PERSON_COUNT_ANNOUNCE_RETRIEVE.getApiKey());
		IaTypeVo iaTypeVo = service.initJsonData(db, vo.getIdNo(), WindHttp.WT_PERSON);
		service.saveCreaditChecking(vo.getIdNo(), IeAdapter.CCA_QH_PERSON_COUNT_ANNOUNCE_RETRIEVE, iaTypeVo);
		return iaTypeVo;
	}
	
	/**   
	 * @Description: 好信法院通-法院公告信息查询
	 * @author Chenh
	 * @param vo
	 * @return  
	 * @throws
	 */
	private IaTypeVo getGrCountAnnounceSearch(QhPvo vo) {
		initGr();
		WindVo wvo = new WindVo();
		if(StringUtils.isEmpty(db.getToken(true))){
			wvo.setMessage("TOKEN不能为空!");
			wvo.setReturnCode(WindHttp.WHCode.ERR_TOKEN);
			return new IaTypeVo(wvo);
		}
		if(StringUtils.isEmpty(vo.getCourtNoticeId())){
			wvo.setMessage("法院公告ID不能为空!");
			wvo.setReturnCode(WindHttp.WHCode.ERR_NUll);
			return new IaTypeVo(wvo);
		}
		if(StringUtils.isEmpty(vo.getQueryId())){
			wvo.setMessage("查询交易ID不能为空!");
			wvo.setReturnCode(WindHttp.WHCode.ERR_NUll);
		}
		if(StringUtils.isEmpty(vo.getSearchTransNo())){
			wvo.setMessage("检索交易流水号不能为空!");
			wvo.setReturnCode(WindHttp.WHCode.ERR_NUll);
		}
		vo.setReasonCode(QhPvo.REASONCODE);
		
		if(StringUtils.isNotEmpty(wvo.getMessage())){
			return new IaTypeVo(wvo);
		}else{
			wvo.setReturnCode(null);
		}
		String json = "{"+db.getToken(true)+",\"courtNoticeId\":\""+vo.getCourtNoticeId()+"\",\"queryId\":\""+vo.getQueryId()+"\",\"searchTransNo\":\""+vo.getSearchTransNo()+"\",\"busiDesc\":\""+vo.getBusiDesc()+"\",\"reasonCode\":\""+vo.getReasonCode()+"\"}";
		db.setParams(json);
		db.setApi(db.getApi()+IeAdapter.CCA_QH_PERSON_COUNT_ANNOUNCE_SEARCH.getApiKey());
		IaTypeVo iaTypeVo = service.initJsonData(db, vo.getIdNo(), WindHttp.WT_PERSON);
		service.saveCreaditChecking(vo.getIdNo(), IeAdapter.CCA_QH_PERSON_COUNT_ANNOUNCE_SEARCH, iaTypeVo);
		return iaTypeVo;
	}
	
	/**   
	 * @Description: 好信法院通-裁判文书信息检索
	 * @author Chenh
	 * @param vo
	 * @return  
	 * @throws
	 */
	private IaTypeVo getGrCountJudgeRetrieve(QhPvo vo) {
		initGr();
		WindVo wvo = new WindVo();
		if(StringUtils.isEmpty(db.getToken(true))){
			wvo.setMessage("TOKEN不能为空!");
			wvo.setReturnCode(WindHttp.WHCode.ERR_TOKEN);
			return new IaTypeVo(wvo);
		}
		vo.setIdType(QhPvo.ID_TYPE);
		if(StringUtils.isEmpty(vo.getIdNo())){
			wvo.setMessage("证件号不能为空!");
			wvo.setReturnCode(WindHttp.WHCode.ERR_NUll);
			return new IaTypeVo(wvo);
		}
		if(StringUtils.isEmpty(vo.getName())){
			wvo.setMessage("主体名称不能为空!");
			wvo.setReturnCode(WindHttp.WHCode.ERR_NUll);
		}
		vo.setReasonCode(QhPvo.REASONCODE);
		
		if(StringUtils.isNotEmpty(wvo.getMessage())){
			return new IaTypeVo(wvo);
		}else{
			wvo.setReturnCode(null);
		}
		String json = "{"+db.getToken(true)+",\"idNo\":\""+vo.getIdNo()+"\",\"idType\":\""+vo.getIdType()+"\",\"name\":\""+vo.getName()+"\",\"busiDesc\":\""+vo.getBusiDesc()+"\",\"reasonCode\":\""+vo.getReasonCode()+"\"}";
		db.setParams(json);
		db.setApi(db.getApi()+IeAdapter.CCA_QH_PERSON_COUNT_JUDGE_RETRIEVE.getApiKey());
		IaTypeVo iaTypeVo = service.initJsonData(db, vo.getIdNo(), WindHttp.WT_PERSON);
		service.saveCreaditChecking(vo.getIdNo(), IeAdapter.CCA_QH_PERSON_COUNT_JUDGE_RETRIEVE, iaTypeVo);
		return iaTypeVo;
	}
	
	/**   
	 * @Description: 好信法院通-裁判文书信息查询
	 * @author Chenh
	 * @param vo
	 * @return  
	 * @throws
	 */
	private IaTypeVo getGrCountJudgeSearch(QhPvo vo) {
		initGr();
		WindVo wvo = new WindVo();
		if(StringUtils.isEmpty(db.getToken(true))){
			wvo.setMessage("TOKEN不能为空!");
			wvo.setReturnCode(WindHttp.WHCode.ERR_TOKEN);
			return new IaTypeVo(wvo);
		}
		if(StringUtils.isEmpty(vo.getJudgeDocId())){
			wvo.setMessage("文书ID不能为空!");
			wvo.setReturnCode(WindHttp.WHCode.ERR_NUll);
			return new IaTypeVo(wvo);
		}
		if(StringUtils.isEmpty(vo.getQueryId())){
			wvo.setMessage("查询交易ID不能为空!");
			wvo.setReturnCode(WindHttp.WHCode.ERR_NUll);
		}
		if(StringUtils.isEmpty(vo.getSearchTransNo())){
			wvo.setMessage("检索交易流水号不能为空!");
			wvo.setReturnCode(WindHttp.WHCode.ERR_NUll);
		}
		vo.setReasonCode(QhPvo.REASONCODE);
		
		if(StringUtils.isNotEmpty(wvo.getMessage())){
			return new IaTypeVo(wvo);
		}else{
			wvo.setReturnCode(null);
		}
		String json = "{"+db.getToken(true)+",\"judgeDocId\":\""+vo.getJudgeDocId()+"\",\"queryId\":\""+vo.getQueryId()+"\",\"searchTransNo\":\""+vo.getSearchTransNo()+"\",\"busiDesc\":\""+vo.getBusiDesc()+"\",\"reasonCode\":\""+vo.getReasonCode()+"\"}";
		db.setParams(json);
		db.setApi(db.getApi()+IeAdapter.CCA_QH_PERSON_COUNT_JUDGE_SEARCH.getApiKey());
		IaTypeVo iaTypeVo = service.initJsonData(db, vo.getIdNo(), WindHttp.WT_PERSON);
		service.saveCreaditChecking(vo.getIdNo(), IeAdapter.CCA_QH_PERSON_COUNT_JUDGE_SEARCH, iaTypeVo);
		return iaTypeVo;
	}
	
	/**   
	 * @Description: 反欺诈风险度认证数据
	 * @author Chenh
	 * @param vo
	 * @return  
	 * @throws
	 */
	private IaTypeVo getGrCheatInfo(QhPvo vo) {
		initGr();
		WindVo wvo = new WindVo();
		if(StringUtils.isEmpty(db.getToken(true))){
			wvo.setMessage("TOKEN不能为空!");
			wvo.setReturnCode(WindHttp.WHCode.ERR_TOKEN);
			return new IaTypeVo(wvo);
		}
		vo.setIdType(QhPvo.ID_TYPE);
		if(StringUtils.isEmpty(vo.getIdNo())){
			wvo.setMessage("证件号不能为空!");
			wvo.setReturnCode(WindHttp.WHCode.ERR_NUll);
			return new IaTypeVo(wvo);
		}
		if(StringUtils.isEmpty(vo.getName())){
			wvo.setMessage("主体名称不能为空!");
			wvo.setReturnCode(WindHttp.WHCode.ERR_NUll);
		}
		if(StringUtils.isEmpty(vo.getMobileNo())){
			wvo.setMessage("手机号码不能为空!");
			wvo.setReturnCode(WindHttp.WHCode.ERR_NUll);
		}
		if(StringUtils.isEmpty(vo.getIp())){
			wvo.setMessage("IP地址不能为空!");
			wvo.setReturnCode(WindHttp.WHCode.ERR_NUll);
		}
		vo.setReasonCode(QhPvo.REASONCODE);
		
		if(StringUtils.isNotEmpty(wvo.getMessage())){
			return new IaTypeVo(wvo);
		}else{
			wvo.setReturnCode(null);
		}
		String json = "{"+db.getToken(true)+",\"idNo\":\""+vo.getIdNo()+"\",\"idType\":\""+vo.getIdType()+"\",\"name\":\""+vo.getName()+"\",\"mobileNo\":\""+vo.getMobileNo()+"\",\"ip\":\""+vo.getIp()+"\",\"busiDesc\":\""+vo.getBusiDesc()+"\",\"reasonCode\":\""+vo.getReasonCode()+"\"}";
		db.setParams(json);
		db.setApi(db.getApi()+IeAdapter.CCA_QH_PERSON_CHEAT_INFO.getApiKey());
		IaTypeVo iaTypeVo = service.initJsonData(db, vo.getIdNo(), WindHttp.WT_PERSON);
		service.saveCreaditChecking(vo.getIdNo(), IeAdapter.CCA_QH_PERSON_CHEAT_INFO, iaTypeVo);
		return iaTypeVo;
	}
	
	/**   
	 * @Description: 好信银行卡资讯
	 * @author Chenh
	 * @param vo
	 * @return  
	 * @throws
	 */
	private IaTypeVo getGrBankCardInfo(QhPvo vo) {
		initGr();
		WindVo wvo = new WindVo();
		if(StringUtils.isEmpty(db.getToken(true))){
			wvo.setMessage("TOKEN不能为空!");
			wvo.setReturnCode(WindHttp.WHCode.ERR_TOKEN);
			return new IaTypeVo(wvo);
		}
		vo.setIdType(QhPvo.ID_TYPE);
		if(StringUtils.isEmpty(vo.getIdNo())){
			wvo.setMessage("证件号不能为空!");
			wvo.setReturnCode(WindHttp.WHCode.ERR_NUll);
			return new IaTypeVo(wvo);
		}
		if(StringUtils.isEmpty(vo.getName())){
			wvo.setMessage("主体名称不能为空!");
			wvo.setReturnCode(WindHttp.WHCode.ERR_NUll);
		}
		if(StringUtils.isEmpty(vo.getCardNo())){
			wvo.setMessage("银行卡卡号不能为空!");
			wvo.setReturnCode(WindHttp.WHCode.ERR_NUll);
		}
		vo.setReasonCode(QhPvo.REASONCODE);
		
		if(StringUtils.isNotEmpty(wvo.getMessage())){
			return new IaTypeVo(wvo);
		}else{
			wvo.setReturnCode(null);
		}
		String json = "{"+db.getToken(true)+",\"idNo\":\""+vo.getIdNo()+"\",\"idType\":\""+vo.getIdType()+"\",\"name\":\""+vo.getName()+"\",\"mobileNo\":\""+vo.getMobileNo()+"\",\"busiDesc\":\""+vo.getBusiDesc()+"\",\"reasonCode\":\""+vo.getReasonCode()+"\"}";
		db.setParams(json);
		db.setApi(db.getApi()+IeAdapter.CCA_QH_PERSON_BANK_CARD_INFO.getApiKey());
		IaTypeVo iaTypeVo = service.initJsonData(db, vo.getIdNo(), WindHttp.WT_PERSON);
		service.saveCreaditChecking(vo.getIdNo(), IeAdapter.CCA_QH_PERSON_BANK_CARD_INFO, iaTypeVo);
		return iaTypeVo;
	}

	/**   
	* @Description: 好信信用轨迹信息
	* @author Chenh
	* @param vo
	* @return  
	* @throws
	*/
	private IaTypeVo getGrCountEnforInfo(QhPvo vo) {
		initGr();
		WindVo wvo = new WindVo();
		if(StringUtils.isEmpty(db.getToken(true))){
			wvo.setMessage("TOKEN不能为空!");
			wvo.setReturnCode(WindHttp.WHCode.ERR_TOKEN);
			return new IaTypeVo(wvo);
		}
		vo.setIdType(QhPvo.ID_TYPE);
		if(StringUtils.isEmpty(vo.getIdNo())){
			wvo.setMessage("证件号不能为空!");
			wvo.setReturnCode(WindHttp.WHCode.ERR_NUll);
			return new IaTypeVo(wvo);
		}
		if(StringUtils.isEmpty(vo.getName())){
			wvo.setMessage("主体名称不能为空!");
			wvo.setReturnCode(WindHttp.WHCode.ERR_NUll);
		}
		if(StringUtils.isEmpty(vo.getMobileNo())){
			wvo.setMessage("手机号不能为空!");
			wvo.setReturnCode(WindHttp.WHCode.ERR_NUll);
		}
		vo.setReasonCode(QhPvo.REASONCODE);
		
		if(StringUtils.isNotEmpty(wvo.getMessage())){
			return new IaTypeVo(wvo);
		}else{
			wvo.setReturnCode(null);
		}
		String json = "{"+db.getToken(true)+",\"idNo\":\""+vo.getIdNo()+"\",\"idType\":\""+vo.getIdType()+"\",\"name\":\""+vo.getName()+"\",\"mobileNo\":\""+vo.getMobileNo()+"\",\"cardNo\":\""+vo.getCardNo()+"\",\"email\":\""+vo.getEmail()+"\",\"weiboNo\":\""+vo.getWeiboNo()+"\",\"weixinNo\":\""+vo.getWeixinNo()+"\",\"qqNo\":\""+vo.getQqNo()+"\",\"taobaoNo\":\""+vo.getTaobaoNo()+"\",\"jdNo\":\""+vo.getJdno()+"\",\"amazonNo\":\""+vo.getAmazonNo()+"\",\"yhdNo\":\""+vo.getYhdNo()+"\",\"busiDesc\":\""+vo.getBusiDesc()+"\",\"reasonCode\":\""+vo.getReasonCode()+"\"}";
		db.setParams(json);
		db.setApi(db.getApi()+IeAdapter.CCA_QH_PERSON_COUNT_ENFORINFO.getApiKey());
		IaTypeVo iaTypeVo = service.initJsonData(db, vo.getIdNo(), WindHttp.WT_PERSON);
		service.saveCreaditChecking(vo.getIdNo(), IeAdapter.CCA_QH_PERSON_COUNT_ENFORINFO, iaTypeVo);
		return iaTypeVo;
	}
	
	/**   
	 * @Description: 好信驾驶分数据
	 * @author Chenh
	 * @param vo
	 * @return  
	 * @throws
	 */
	private IaTypeVo getGrDeiveScore(QhPvo vo) {
		initGr();
		WindVo wvo = new WindVo();
		if(StringUtils.isEmpty(db.getToken(true))){
			wvo.setMessage("TOKEN不能为空!");
			wvo.setReturnCode(WindHttp.WHCode.ERR_TOKEN);
			return new IaTypeVo(wvo);
		}
		vo.setIdType(QhPvo.ID_TYPE);
		if(StringUtils.isEmpty(vo.getIdNo())){
			wvo.setMessage("证件号不能为空!");
			wvo.setReturnCode(WindHttp.WHCode.ERR_NUll);
			return new IaTypeVo(wvo);
		}
		if(StringUtils.isEmpty(vo.getName())){
			wvo.setMessage("主体名称不能为空!");
			wvo.setReturnCode(WindHttp.WHCode.ERR_NUll);
		}
		vo.setReasonCode(QhPvo.REASONCODE);
		
		if(StringUtils.isNotEmpty(wvo.getMessage())){
			return new IaTypeVo(wvo);
		}else{
			wvo.setReturnCode(null);
		}
		String json = "{"+db.getToken(true)+",\"idNo\":\""+vo.getIdNo()+"\",\"idType\":\""+vo.getIdType()+"\",\"name\":\""+vo.getName()+"\",\"cardNo\":\""+vo.getCardNo()+"\",\"busiDesc\":\""+vo.getBusiDesc()+"\",\"reasonCode\":\""+vo.getReasonCode()+"\"}";
		db.setParams(json);
		db.setApi(db.getApi()+IeAdapter.CCA_QH_PERSON_DEIVE_SCORE.getApiKey());
		IaTypeVo iaTypeVo = service.initJsonData(db, vo.getIdNo(), WindHttp.WT_PERSON);
		service.saveCreaditChecking(vo.getIdNo(), IeAdapter.CCA_QH_PERSON_DEIVE_SCORE, iaTypeVo);
		return iaTypeVo;
	}
	
	/**   
	 * @Description: 租车客信用评分数据
	 * @author Chenh
	 * @param vo
	 * @return  
	 * @throws
	 */
	private IaTypeVo getGrRentCar(QhPvo vo) {
		initGr();
		WindVo wvo = new WindVo();
		if(StringUtils.isEmpty(db.getToken(true))){
			wvo.setMessage("TOKEN不能为空!");
			wvo.setReturnCode(WindHttp.WHCode.ERR_TOKEN);
			return new IaTypeVo(wvo);
		}
		vo.setIdType(QhPvo.ID_TYPE);
		if(StringUtils.isEmpty(vo.getIdNo())){
			wvo.setMessage("证件号不能为空!");
			wvo.setReturnCode(WindHttp.WHCode.ERR_NUll);
			return new IaTypeVo(wvo);
		}
		if(StringUtils.isEmpty(vo.getName())){
			wvo.setMessage("主体名称不能为空!");
			wvo.setReturnCode(WindHttp.WHCode.ERR_NUll);
		}
		if(StringUtils.isEmpty(vo.getDriviliceNo())){
			wvo.setMessage("驾驶证号不能为空!");
			wvo.setReturnCode(WindHttp.WHCode.ERR_NUll);
		}
		vo.setReasonCode(QhPvo.REASONCODE);
		
		if(StringUtils.isNotEmpty(wvo.getMessage())){
			return new IaTypeVo(wvo);
		}else{
			wvo.setReturnCode(null);
		}
		String json = "{"+db.getToken(true)+",\"idNo\":\""+vo.getIdNo()+"\",\"idType\":\""+vo.getIdType()+"\",\"name\":\""+vo.getName()+"\",\"driviLiceNo\":\""+vo.getDriviliceNo()+"\",\"busiDesc\":\""+vo.getBusiDesc()+"\",\"reasonCode\":\""+vo.getReasonCode()+"\"}";
		db.setParams(json);
		db.setApi(db.getApi()+IeAdapter.CCA_QH_PERSON_RENT_CAR.getApiKey());
		IaTypeVo iaTypeVo = service.initJsonData(db, vo.getIdNo(), WindHttp.WT_PERSON);
		service.saveCreaditChecking(vo.getIdNo(), IeAdapter.CCA_QH_PERSON_RENT_CAR, iaTypeVo);
		return iaTypeVo;
	}
	
	/**   
	 * @Description: 租车客信用评分数据
	 * @author Chenh
	 * @param vo
	 * @return  
	 * @throws
	 */
	private IaTypeVo getGrBankScore(QhPvo vo) {
		initGr();
		WindVo wvo = new WindVo();
		if(StringUtils.isEmpty(db.getToken(true))){
			wvo.setMessage("TOKEN不能为空!");
			wvo.setReturnCode(WindHttp.WHCode.ERR_TOKEN);
			return new IaTypeVo(wvo);
		}
		vo.setIdType(QhPvo.ID_TYPE);
		if(StringUtils.isEmpty(vo.getIdNo())){
			wvo.setMessage("证件号不能为空!");
			wvo.setReturnCode(WindHttp.WHCode.ERR_NUll);
			return new IaTypeVo(wvo);
		}
		if(StringUtils.isEmpty(vo.getName())){
			wvo.setMessage("主体名称不能为空!");
			wvo.setReturnCode(WindHttp.WHCode.ERR_NUll);
		}
		if(StringUtils.isEmpty(vo.getMobileNo())){
			wvo.setMessage("手机号不能为空!");
			wvo.setReturnCode(WindHttp.WHCode.ERR_NUll);
		}
		if(StringUtils.isEmpty(vo.getAccountNo())){
			wvo.setMessage("银行账号不能为空!");
			wvo.setReturnCode(WindHttp.WHCode.ERR_NUll);
		}
		vo.setReasonCode(QhPvo.REASONCODE);
		
		if(StringUtils.isNotEmpty(wvo.getMessage())){
			return new IaTypeVo(wvo);
		}else{
			wvo.setReturnCode(null);
		}
		String json = "{"+db.getToken(true)+",\"idNo\":\""+vo.getIdNo()+"\",\"idType\":\""+vo.getIdType()+"\",\"name\":\""+vo.getName()+"\",\"mobileNo\":\""+vo.getMobileNo()+"\",\"accountNo\":\""+vo.getAccountNo()+"\",\"busiDesc\":\""+vo.getBusiDesc()+"\",\"reasonCode\":\""+vo.getReasonCode()+"\"}";
		db.setParams(json);
		db.setApi(db.getApi()+IeAdapter.CCA_QH_PERSON_BANK_SCORE.getApiKey());
		IaTypeVo iaTypeVo = service.initJsonData(db, vo.getIdNo(), WindHttp.WT_PERSON);
		service.saveCreaditChecking(vo.getIdNo(), IeAdapter.CCA_QH_PERSON_BANK_SCORE, iaTypeVo);
		return iaTypeVo;
	}

	/**   
	 * @Description: 好信手机综合资讯(仅支持电信号码)
	 * @author Chenh
	 * @param vo
	 * @return  
	 * @throws
	 */
	private IaTypeVo getGrMobileConsult(QhPvo vo) {
		initGr();
		WindVo wvo = new WindVo();
		if(StringUtils.isEmpty(db.getToken(true))){
			wvo.setMessage("TOKEN不能为空!");
			wvo.setReturnCode(WindHttp.WHCode.ERR_TOKEN);
			return new IaTypeVo(wvo);
		}
		vo.setIdType(QhPvo.ID_TYPE);
		if(StringUtils.isEmpty(vo.getIdNo())){
			wvo.setMessage("证件号不能为空!");
			wvo.setReturnCode(WindHttp.WHCode.ERR_NUll);
			return new IaTypeVo(wvo);
		}
		if(StringUtils.isEmpty(vo.getName())){
			wvo.setMessage("主体名称不能为空!");
			wvo.setReturnCode(WindHttp.WHCode.ERR_NUll);
		}
		if(StringUtils.isEmpty(vo.getMobileNo())){
			wvo.setMessage("手机号不能为空!");
			wvo.setReturnCode(WindHttp.WHCode.ERR_NUll);
		}
		vo.setReasonCode(QhPvo.REASONCODE);
		
		if(StringUtils.isNotEmpty(wvo.getMessage())){
			return new IaTypeVo(wvo);
		}else{
			wvo.setReturnCode(null);
		}
		String json = "{"+db.getToken(true)+",\"idNo\":\""+vo.getIdNo()+"\",\"idType\":\""+vo.getIdType()+"\",\"name\":\""+vo.getName()+"\",\"mobileNo\":\""+vo.getMobileNo()+"\",\"busiDesc\":\""+vo.getBusiDesc()+"\",\"reasonCode\":\""+vo.getReasonCode()+"\"}";
		db.setParams(json);
		db.setApi(db.getApi()+IeAdapter.CCA_QH_PERSON_MOBILE_CONSULT.getApiKey());
		IaTypeVo iaTypeVo = service.initJsonData(db, vo.getIdNo(), WindHttp.WT_PERSON);
		service.saveCreaditChecking(vo.getIdNo(), IeAdapter.CCA_QH_PERSON_MOBILE_CONSULT, iaTypeVo);
		return iaTypeVo;
	}

	/**   
	 * @Description: 提交驾驶证比对任务数据
	 * @author Chenh
	 * @param vo
	 * @return  
	 * @throws
	 */
	private IaTypeVo getGrDeiveTask(QhPvo vo) {
		initGr();
		WindVo wvo = new WindVo();
		if(StringUtils.isEmpty(db.getToken(true))){
			wvo.setMessage("TOKEN不能为空!");
			wvo.setReturnCode(WindHttp.WHCode.ERR_TOKEN);
			return new IaTypeVo(wvo);
		}
		vo.setIdType(QhPvo.ID_TYPE);
		if(StringUtils.isEmpty(vo.getIdNo())){
			wvo.setMessage("证件号不能为空!");
			wvo.setReturnCode(WindHttp.WHCode.ERR_NUll);
			return new IaTypeVo(wvo);
		}
		if(StringUtils.isEmpty(vo.getName())){
			wvo.setMessage("主体名称不能为空!");
			wvo.setReturnCode(WindHttp.WHCode.ERR_NUll);
		}
		if(StringUtils.isEmpty(vo.getDriviliceNo())){
			wvo.setMessage("驾驶证号不能为空!");
			wvo.setReturnCode(WindHttp.WHCode.ERR_NUll);
		}
		vo.setReasonCode(QhPvo.REASONCODE);
		
		if(StringUtils.isNotEmpty(wvo.getMessage())){
			return new IaTypeVo(wvo);
		}else{
			wvo.setReturnCode(null);
		}
		String json = "{"+db.getToken(true)+",\"idNo\":\""+vo.getIdNo()+"\",\"idType\":\""+vo.getIdType()+"\",\"name\":\""+vo.getName()+"\",\"driverNo\":\""+vo.getDriviliceNo()+"\",\"busiDesc\":\""+vo.getBusiDesc()+"\",\"reasonCode\":\""+vo.getReasonCode()+"\"}";
		db.setParams(json);
		db.setApi(db.getApi()+IeAdapter.CCA_QH_PERSON_DEIVE_TASK.getApiKey());
		IaTypeVo iaTypeVo = service.initJsonData(db, vo.getIdNo(), WindHttp.WT_PERSON);
		service.saveCreaditChecking(vo.getIdNo(), IeAdapter.CCA_QH_PERSON_DEIVE_TASK, iaTypeVo);
		return iaTypeVo;
	}
	
	/**   
	 * @Description: 提交驾驶证状态比对
	 * @author Chenh
	 * @param vo
	 * @return  
	 * @throws
	 */
	private IaTypeVo getGrDeiveState(QhPvo vo) {
		initGr();
		WindVo wvo = new WindVo();
		if(StringUtils.isEmpty(db.getToken(true))){
			wvo.setMessage("TOKEN不能为空!");
			wvo.setReturnCode(WindHttp.WHCode.ERR_TOKEN);
			return new IaTypeVo(wvo);
		}
		vo.setIdType(QhPvo.ID_TYPE);
		if(StringUtils.isEmpty(vo.getIdNo())){
			wvo.setMessage("证件号不能为空!");
			wvo.setReturnCode(WindHttp.WHCode.ERR_NUll);
			return new IaTypeVo(wvo);
		}
		if(StringUtils.isEmpty(vo.getName())){
			wvo.setMessage("主体名称不能为空!");
			wvo.setReturnCode(WindHttp.WHCode.ERR_NUll);
		}
		if(StringUtils.isEmpty(vo.getDriviliceNo())){
			wvo.setMessage("驾驶证号不能为空!");
			wvo.setReturnCode(WindHttp.WHCode.ERR_NUll);
		}
		vo.setReasonCode(QhPvo.REASONCODE);
		
		if(StringUtils.isNotEmpty(wvo.getMessage())){
			return new IaTypeVo(wvo);
		}else{
			wvo.setReturnCode(null);
		}
		String json = "{"+db.getToken(true)+",\"idNo\":\""+vo.getIdNo()+"\",\"idType\":\""+vo.getIdType()+"\",\"name\":\""+vo.getName()+"\",\"driverNo\":\""+vo.getDriviliceNo()+"\",\"busiDesc\":\""+vo.getBusiDesc()+"\",\"reasonCode\":\""+vo.getReasonCode()+"\"}";
		db.setParams(json);
		db.setApi(db.getApi()+IeAdapter.CCA_QH_PERSON_DEIVE_STATE.getApiKey());
		IaTypeVo iaTypeVo = service.initJsonData(db, vo.getIdNo(), WindHttp.WT_PERSON);
		service.saveCreaditChecking(vo.getIdNo(), IeAdapter.CCA_QH_PERSON_DEIVE_STATE, iaTypeVo);
		return iaTypeVo;
	}
	
	/**   
	 * @Description: 驾驶证比对查询数据
	 * @author Chenh
	 * @param vo
	 * @return  
	 * @throws
	 */
	private IaTypeVo getGrDeiveDatas(QhPvo vo) {
		initGr();
		WindVo wvo = new WindVo();
		if(StringUtils.isEmpty(db.getToken(true))){
			wvo.setMessage("TOKEN不能为空!");
			wvo.setReturnCode(WindHttp.WHCode.ERR_TOKEN);
			return new IaTypeVo(wvo);
		}
		if(StringUtils.isEmpty(vo.getQueryId())){
			wvo.setMessage("证任务ID不能为空!");
			wvo.setReturnCode(WindHttp.WHCode.ERR_NUll);
			return new IaTypeVo(wvo);
		}
		if(StringUtils.isEmpty(vo.getSearchTransNo())){
			wvo.setMessage("任务提交交易流水号不能为空!");
			wvo.setReturnCode(WindHttp.WHCode.ERR_NUll);
		}
		vo.setReasonCode(QhPvo.REASONCODE);
		
		if(StringUtils.isNotEmpty(wvo.getMessage())){
			return new IaTypeVo(wvo);
		}else{
			wvo.setReturnCode(null);
		}
		String json = "{"+db.getToken(true)+",\"queryId\":\""+vo.getQueryId()+"\",\"searchTransNo\":\""+vo.getSearchTransNo()+"\",\"busiDesc\":\""+vo.getBusiDesc()+"\",\"reasonCode\":\""+vo.getReasonCode()+"\"}";
		db.setParams(json);
		db.setApi(db.getApi()+IeAdapter.CCA_QH_PERSON_DEIVE_DATAS.getApiKey());
		IaTypeVo iaTypeVo = service.initJsonData(db, vo.getIdNo(), WindHttp.WT_PERSON);
		service.saveCreaditChecking(vo.getIdNo(), IeAdapter.CCA_QH_PERSON_DEIVE_DATAS, iaTypeVo);
		return iaTypeVo;
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
	 * @param id 企业唯一标识号
	 * @param IeAdapter 适配枚举类
	 * @return
	 */
	public IaTypeVo getQy(String id, IeAdapter ieAdapter) {
		IaTypeVo vo = null;
		if((ieAdapter.getType()).equals(IeAdapterType.QH_CORPORATION)){
			initQy();
			QhCvo qhVo = new QhCvo(service.getTCompany(id), ieAdapter);
//			if((ieAdapter).equals(IeAdapter.CCA_QH_CORPORATION)){
//				vo = getGrValidate(qhVo);
//			}else if((ieAdapter).equals(IeAdapter.CCA_QH_CORPORATION)){
//				vo = getGrValidateWork(qhVo);
//			}
		}else{
			WindVo wvo = new WindVo();
			wvo.setMessage("该接口只能查询企业征信信息！请确定IeAdapterType无误！");
			vo.setVo(wvo);
		}
		return vo;
	}
}
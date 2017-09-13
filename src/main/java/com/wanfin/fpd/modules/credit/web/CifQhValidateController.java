/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.credit.web;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mangofactory.swagger.annotations.ApiIgnore;
import com.wanfin.fpd.common.config.Global;
import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.utils.HttpPostJson;
import com.wanfin.fpd.common.utils.IdGen;
import com.wanfin.fpd.common.utils.StringUtils;
import com.wanfin.fpd.common.web.BaseController;
import com.wanfin.fpd.modules.credit.entity.CifQhValidate;
import com.wanfin.fpd.modules.credit.service.CifQhValidateService;
import com.wanfin.fpd.modules.sys.entity.User;
import com.wanfin.fpd.modules.sys.utils.UserUtils;

import net.sf.json.JSONObject;

/**
 * creditresultController
 * @author cjp
 * @version 2017-05-09
 */
@ApiIgnore
@Controller
@RequestMapping(value = "${adminPath}/credit/cifQhValidate")
public class CifQhValidateController extends BaseController {


	@Autowired
	private CifQhValidateService cifQhValidateService;

	@ModelAttribute
	public CifQhValidate get(@RequestParam(required=false) String id) {
		CifQhValidate entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = cifQhValidateService.get(id);
		}
		if (entity == null){
			entity = new CifQhValidate();
		}
		return entity;
	}

	@RequiresPermissions("credit:cifQhValidate:view")
	@RequestMapping(value = {"list", ""})
	public String list(CifQhValidate cifQhValidate, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CifQhValidate> page = cifQhValidateService.findPage(new Page<CifQhValidate>(request, response), cifQhValidate); 
		model.addAttribute("searchCode", request.getParameter("searchCode"));
		model.addAttribute("page", page);
		model.addAttribute("cifQhValidate", cifQhValidate);
		return "modules/credit/cifQhValidateList";
	}

	@RequiresPermissions("credit:cifQhValidate:view")
	@RequestMapping(value = "form")
	public String form(CifQhValidate cifQhValidate, Model model) {
		model.addAttribute("cifQhValidate", cifQhValidate);
		return "modules/credit/cifQhValidateForm";
	}

	@RequiresPermissions("credit:cifQhValidate:edit")
	@RequestMapping(value = "save")
	public String save(CifQhValidate cifQhValidate, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, cifQhValidate)){
			return form(cifQhValidate, model);
		}
		cifQhValidateService.save(cifQhValidate);
		addMessage(redirectAttributes, "保存creditResult成功");
		return "redirect:"+Global.getAdminPath()+"/credit/cifQhValidate/?repage";
	}

	@RequiresPermissions("credit:cifQhValidate:edit")
	@RequestMapping(value = "delete")
	public String delete(CifQhValidate cifQhValidate, RedirectAttributes redirectAttributes) {
		cifQhValidateService.delete(cifQhValidate);
		addMessage(redirectAttributes, "删除creditResult成功");
		return "redirect:"+Global.getAdminPath()+"/credit/cifQhValidate/?repage";
	}

	@RequiresPermissions("credit:cifQhValidate:edit")
	@RequestMapping(value = "search")
	public String search(CifQhValidate cifQhValidate, HttpServletRequest request, HttpServletResponse response, Model model, RedirectAttributes redirectAttributes){
		String searchCode = cifQhValidate.getSearchCode();

		model.addAttribute("cifQhValidate", cifQhValidate);
		model.addAttribute("code", cifQhValidate.getSearchCode());
		//身份证征信查询
		if ("WZ2016071810234452400".equals(searchCode)) {
			return "modules/credit/cifQhIdentityForm";
		}
		//地址验证
		if ("WZ2016071810301249807".equals(searchCode)) {
			return "modules/credit/cifQhAddressForm";
		}
		//工作单位验证
		if ("WZ2016071810283590505".equals(searchCode)) {
			return "modules/credit/cifQhCompanyForm";
		}
		//手机验证
		if ("WZ201607181024544001".equals(searchCode)) {
			return "modules/credit/cifQhMobileForm";
		}
		//关系人认证验证
		if ("WZ2016071810254988302".equals(searchCode)) {
			return "modules/credit/cifQhRelatedForm";
		}
		//车辆验证查询
		if ("WZ2016071810265950004".equals(searchCode)) {
			return "modules/credit/cifQhCarForm";
		}
		// 学历验证
		if ("WZ201607181031178408".equals(searchCode)) {
			return "modules/credit/cifQhEducationForm";
		}
		// 好信分度
		if ("WZ2016071806234699502".equals(searchCode)) {
			return "modules/credit/cifQhLoningForm";
		}
		//风险度提示
		if ("WZ2016071806260260203".equals(searchCode)) {
			return "modules/credit/cifQhRiskForm";
		}
		//常贷客数据
		if ("WZ2016071806283562504".equals(searchCode)) {
			return "modules/credit/cifQhLoanInfoForm";
		}
		//好信欺诈度提示
		if ("WZ2016071806314876005".equals(searchCode)) {
			return "modules/credit/cifQhCheatForm";
		}
		//好信地址通
		if ("WZ2016071806335470406".equals(searchCode)) {
			return "modules/credit/cifQhAddressInfoForm";
		}
		//好信信用轨迹
		if ("WZ2016071806344032507".equals(searchCode)) {
			return "modules/credit/cifQhCreditTrackForm";
		}
		//好信租车分
		if ("WZ2016071806350973608".equals(searchCode)) {
			return "modules/credit/cifQhRentCarForm";
		}
		//好信驾驶分
		if ("WZ2016071806353261809".equals(searchCode)) {
			return "modules/credit/cifQhDriveForm";
		}
//		//好信联络通
//		if ("WZ2016071806355920010".equals(searchCode)) {
//			return "modules/credit/cifQhContactForm";
//		}
		//好信手机综合资讯
		if ("WZ2016071806364922312".equals(searchCode)) {
			return "modules/credit/cifQhMobileForm";
		}
		//好信银行卡咨询
		if ("WZ2016071806371547913".equals(searchCode)) {
			return "modules/credit/cifQhBankCardForm";
		}
		//好信银行卡评分
		if ("WZ2016071806374036014".equals(searchCode)) {
			return "modules/credit/cifQhCardScoreForm";
		}
		//一鉴通银行卡鉴权
		if ("WZ2016072101593612700".equals(searchCode)) {
			return "modules/credit/cifQhOneBankCardForm";
		}
		//好信工商通
		if ("WZ2016071806381118715".equals(searchCode)) {
			return "modules/credit/cifQhIndustryCommerceForm";
		}
		//好信法院通-被执行人信息
		if ("WZ2016071806381118715".equals(searchCode)) {
			return "modules/credit/cifQhEnforComentForm";
		}
		return "modules/credit/cifQhIdentityForm";
	}

	@SuppressWarnings("finally")
	@RequiresPermissions("credit:cifQhValidate:edit")
	@RequestMapping(value = "searchCredit")
	public String searchCredit(CifQhValidate cifQhValidate, HttpServletRequest request, HttpServletResponse response, Model model, RedirectAttributes redirectAttributes){

		String searchCode = request.getParameter("code");
		if (StringUtils.isEmpty(searchCode)) {
			return "modules/credit/cifQhValidateList";
		}
		User user = UserUtils.getUser();
		Map map = new HashMap<>();
		map.put("name", cifQhValidate.getName());
		map.put("idNo", cifQhValidate.getIdNo());
		map.put("idType", cifQhValidate.getIdType());
		map.put("organId", user.getCompany().getId());
		map.put("searchCode", searchCode);
		map.put("mobileNo", cifQhValidate.getMobileNo());
		map.put("token", "test123");
		map.put("refName", cifQhValidate.getRefName());
		map.put("refMobileNo", cifQhValidate.getRefMobileNo());
		map.put("engineNo", cifQhValidate.getEngineNo());
		map.put("carNo", cifQhValidate.getCarNo());
		map.put("needeQryDrvStatus", cifQhValidate.getNeedeQryDrvStatus());
		map.put("carFrameNum", cifQhValidate.getCarFrameNum());
		map.put("eductionBckgrd", cifQhValidate.getEductionBckgrd());
		map.put("cardNo", cifQhValidate.getCardNo());
		map.put("driviliceNo", cifQhValidate.getDriviliceNo());
		map.put("accountNo", cifQhValidate.getAccountNo());
		map.put("expiresYear", cifQhValidate.getExpiresYear());
		map.put("reasonCode", cifQhValidate.getReasonCode());
		map.put("expiresYear", cifQhValidate.getExpiresYear());
		map.put("expiresMonth", cifQhValidate.getExpiresMonth());
		map.put("entityAuthCode", cifQhValidate.getEntityAuthCode());
		map.put("entityAuthDate", cifQhValidate.getEntityAuthDate());

		CifQhValidate validate = null;
		try {
			JSONObject json = JSONObject.fromObject(map);
			String str = HttpPostJson.sendPost("http://credit.wanfin.com/credit/credit/dealDatas", json.toString());
			System.out.println("---1111111------"+str);
			JSONObject jsonObject = JSONObject.fromObject(str);
			JSONObject entity = (JSONObject) jsonObject.get("entity");

			if (entity.getBoolean("istrue")) {
				JSONObject object = (JSONObject) entity.get("entity");
				validate = save(object, user);
				
			} else {
				validate = new CifQhValidate();
			}
			validate.setIdNo(cifQhValidate.getIdNo());
			validate.setIdType(cifQhValidate.getIdType());
			validate.setName(cifQhValidate.getName());
			validate.setAccountNo(cifQhValidate.getAccountNo());
			validate.setDriviliceNo(cifQhValidate.getDriviliceNo());
			validate.setExpiresMonth(cifQhValidate.getExpiresMonth());
			validate.setExpiresYear(cifQhValidate.getExpiresYear());
			validate.setEntityAuthCode(cifQhValidate.getEntityAuthCode());
			validate.setEntityAuthDate(cifQhValidate.getEntityAuthDate());
			
			
			if (StringUtils.isNotEmpty(cifQhValidate.getAddress())) {
				validate.setAddress(cifQhValidate.getAddress());
			}
			if (StringUtils.isNotEmpty(cifQhValidate.getCompany())) {
				validate.setCompany(cifQhValidate.getCompany());
			}
			if (StringUtils.isNotEmpty(cifQhValidate.getRefName())) {
				validate.setRefName(cifQhValidate.getRefName());
			}
			if (StringUtils.isNotEmpty(cifQhValidate.getRefMobileNo())) {
				validate.setRefMobileNo(cifQhValidate.getRefMobileNo());
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			model.addAttribute("cifQhValidate", validate);
			//身份证征信
			if ("WZ2016071810234452400".equals(searchCode)) {
				return "modules/credit/cifQhIdentityResult";
			}
			//手机验证
			if ("WZ201607181024544001".equals(searchCode)) {
				return "modules/credit/cifQhMobileResult";
			}
			//关系人验证
			if ("WZ2016071810254988302".equals(searchCode)) {
				return "modules/credit/cifQhRelatedResult";
			}
			//房产验证
			if ("WZ2016071810263472603".equals(searchCode)) {

			}
			//车辆验证
			if ("WZ201607181024544001".equals(searchCode)) {
				return "modules/credit/cifQhCarResult";
			}
			//工作单位验证
			if ("WZ2016071810283590505".equals(searchCode)) {
				return "modules/credit/cifQhCompanyResult";
			}
			//人脸识别
			if ("WZ2016071810290345306".equals(searchCode)) {

			}
			//地址验证
			if ("WZ2016071810301249807".equals(searchCode)) {
				return "modules/credit/cifQhAddressResult";
			}
			// 学历验证
			if ("WZ201607181031178408".equals(searchCode)) {
				return "modules/credit/cifQhEducationResult";
			}
			//好信度分
			if ("WZ2016071806234699502".equals(searchCode)) {
				return "modules/credit/cifQhLoningResult";
			}
			//风险度分
			if ("WZ2016071806260260203".equals(searchCode)) {
				return "modules/credit/cifQhRiskResult";
			}
			//常客贷数据
			if ("WZ2016071806283562504".equals(searchCode)) {
				return "modules/credit/cifQhLoanInfoResult";
			}
			//好信欺诈度提示
			if ("WZ2016071806314876005".equals(searchCode)) {
				return "modules/credit/cifQhCheatResult";
			}
			//好信地址通
			if ("WZ2016071806335470406".equals(searchCode)) {
				return "modules/credit/cifQhAddressInfoResult";
			}
			//好信信用轨迹
			if ("WZ2016071806344032507".equals(searchCode)) {
				return "modules/credit/cifQhCreditTrackResult";
			}
			//好信租车分
			if ("WZ2016071806350973608".equals(searchCode)) {
				return "modules/credit/cifQhRentCarResult";
			}
			//好信驾驶分
			if ("WZ2016071806353261809".equals(searchCode)) {
				return "modules/credit/cifQhDriveResult";
			}
//			//好信联络通
//			if ("WZ2016071806355920010".equals(searchCode)) {
//				return "modules/credit/cifQhContactResult";
//			}
			//好信手机综合资讯
			if ("WZ2016071806364922312".equals(searchCode)) {
				return "modules/credit/cifQhMobileResult";
			}
			//好信银行卡咨询
			if ("WZ2016071806371547913".equals(searchCode)) {
				return "modules/credit/cifQhBankCardResult";
			}
			//好信银行卡评分
			if ("WZ2016071806374036014".equals(searchCode)) {
				return "modules/credit/cifQhCardScoreResult";
			}
			//一鉴通银行卡鉴权
			if ("WZ2016072101593612700".equals(searchCode)) {
				return "modules/credit/cifQhOneBankCardResult";
			}
			//好信工商通
			if ("WZ2016071806381118715".equals(searchCode)) {
				return "modules/credit/cifQhIndustryCommerceResult";
			}
			//好信法院通-被执行人信息
			if ("WZ2016071806383481616".equals(searchCode)) {
				return "modules/credit/cifQhEnforComentResult";
			}
			if ("WZ2016071810301249807".equals(searchCode)) {

			}
			if ("WZ2016071810301249807".equals(searchCode)) {

			}
			if ("WZ2016071810301249807".equals(searchCode)) {

			}
			if ("WZ2016071810301249807".equals(searchCode)) {

			}
			if ("WZ2016071810301249807".equals(searchCode)) {

			}
			if ("WZ2016071810301249807".equals(searchCode)) {

			}
			if ("WZ2016071810301249807".equals(searchCode)) {

			}
			if ("WZ2016071810301249807".equals(searchCode)) {

			}
			if ("WZ2016071810301249807".equals(searchCode)) {

			}
			if ("WZ2016071810301249807".equals(searchCode)) {

			}
			return "modules/credit/cifQhIdentityResult";
		}
	}

	public CifQhValidate save(JSONObject entity, User user) {
		CifQhValidate cifQhValidate = new CifQhValidate();
		cifQhValidate.setId(IdGen.uuid());
		cifQhValidate.setCreateBy(user);
		cifQhValidate.setCreateDate(new Date());
		cifQhValidate.setCreateBy(user);
		cifQhValidate.setOrganId(user.getOrganId());
		if (!"null".equals(entity.get("resId"))) {
			cifQhValidate.setResId((String)entity.get("resId"));
		}
		if (!"null".equals(entity.get("idNo"))) {
			cifQhValidate.setIdNo((String)entity.get("idNo"));
		}
		if (!"null".equals(entity.get("idType"))) {
			cifQhValidate.setIdType((String)entity.get("idType"));
		}
		if (!"null".equals(entity.get("name"))) {
			cifQhValidate.setName((String)entity.get("name"));
		}
		if (!"null".equals(entity.get("mobileNo"))) {
			cifQhValidate.setMobileNo((String)entity.get("mobileNo"));
		}
		if (!"null".equals(entity.get("seqNo"))) {
			cifQhValidate.setSeqNo((String)entity.get("seqNo"));
		}
		if (!"null".equals(entity.get("isRealIdentity"))) {
			cifQhValidate.setIsRealIdentity((String)entity.get("isRealIdentity"));
		}
		if (!"null".equals(entity.get("isValidAddress"))) {
			cifQhValidate.setIsValidAddress((String)entity.get("isValidAddress"));
		}
		if (!"null".equals(entity.get("addressType"))) {
			cifQhValidate.setAddressType((String)entity.get("addressType"));
		}
		if (!"null".equals(entity.get("isRealCompany"))) {
			cifQhValidate.setIsRealCompany((String)entity.get("isRealCompany"));
		}
		if (!"null".equals(entity.get("companySimDeg"))) {
			cifQhValidate.setCompanySimDeg((String)entity.get("companySimDeg"));
		}
		if (!"null".equals(entity.get("appear1ThDate"))) {
			cifQhValidate.setAppear1ThDate((String)entity.get("appear1ThDate"));
		}
		if (!"null".equals(entity.get("appearLastDate"))) {
			cifQhValidate.setAppearLastDate((String)entity.get("appearLastDate"));
		}
		if (!"null".equals(entity.get("isOwnerMobile"))) {
			cifQhValidate.setIsOwnerMobile((String)entity.get("isOwnerMobile"));
		}
		if (!"null".equals(entity.get("ownerMobileStatus"))) {
			cifQhValidate.setOwnerMobileStatus((String)entity.get("ownerMobileStatus"));
		}
		if (!"null".equals(entity.get("useTimeScore"))) {
			cifQhValidate.setUseTimeScore((String)entity.get("useTimeScore"));
		}
		if (!"null".equals(entity.get("isExistRel"))) {
			cifQhValidate.setIsExistRel((String)entity.get("isExistRel"));
		}
		if (!"null".equals(entity.get("relLevel"))) {
			cifQhValidate.setRelLevel((String)entity.get("relLevel"));
		}
		if (!"null".equals(entity.get("carChkResult"))) {
			cifQhValidate.setCarChkResult((String)entity.get("carChkResult"));
		}
		if (!"null".equals(entity.get("isAsyned"))) {
			cifQhValidate.setIsAsyned((String)entity.get("isAsyned"));
		}
		if (!"null".equals(entity.get("carTypeInc"))) {
			cifQhValidate.setCarTypeInc((String)entity.get("carTypeInc"));
		}
		if (!"null".equals(entity.get("carFactoryInc"))) {
			cifQhValidate.setCarFactoryInc((String)entity.get("carFactoryInc"));
		}
		if (!"null".equals(entity.get("carPrice"))) {
			cifQhValidate.setCarPrice((String)entity.get("carPrice"));
		}
		if (!"null".equals(entity.get("drvStatusQryId"))) {
			cifQhValidate.setDrvStatusQryId((String)entity.get("drvStatusQryId"));
		}
		if (!"null".equals(entity.get("houseChkResult"))) {
			cifQhValidate.setHouseChkResult((String)entity.get("houseChkResult"));
		}
		if (!"null".equals(entity.get("houseDataDate"))) {
			cifQhValidate.setHouseDataDate((String)entity.get("houseDataDate"));
		}
		if (!"null".equals(entity.get("photoCmpResult"))) {
			cifQhValidate.setPhotoCmpResult((String)entity.get("photoCmpResult"));
		}
		if (!"null".equals(entity.get("photoSimDeg"))) {
			cifQhValidate.setPhotoSimDeg((String)entity.get("photoSimDeg"));
		}
		if (!"null".equals(entity.get("isHighestEduBkg"))) {
			cifQhValidate.setIsHighestEduBkg((String)entity.get("isHighestEduBkg"));
		}
		if (!"null".equals(entity.get("dataDate"))) {
			cifQhValidate.setDataDate((String)entity.get("dataDate"));
		}
		if (!"null".equals(entity.get("graduateSchool"))) {
			cifQhValidate.setGraduateSchool((String)entity.get("graduateSchool"));
		}
		if (!"null".equals(entity.get("graduateMajor"))) {
			cifQhValidate.setGraduateMajor((String)entity.get("graduateMajor"));
		}
		if (!"null".equals(entity.get("graduateYear"))) {
			cifQhValidate.setGraduateYear((String)entity.get("graduateYear"));
		}
		if (!"null".equals(entity.get("isOwnerMobileTwo"))) {
			cifQhValidate.setIsOwnerMobileTwo((String)entity.get("isOwnerMobileTwo"));
		}
		if (!"null".equals(entity.get("ownerMobileStatusTwo"))) {
			cifQhValidate.setOwnerMobileStatusTwo((String)entity.get("ownerMobileStatusTwo"));
		}
		if (!"null".equals(entity.get("useTimeScoreTwo"))) {
			cifQhValidate.setUseTimeScoreTwo((String)entity.get("useTimeScoreTwo"));
		}

		if (!"null".equals(entity.get("sourseId"))) {
			cifQhValidate.setSourseId((String)entity.get("sourseId"));
		}
		if (!"null".equals(entity.get("credooScore"))) {
			cifQhValidate.setCredooScore((String)entity.get("credooScore"));
		}
		if (!"null".equals(entity.get("dataBuildTime"))) {
			cifQhValidate.setDataBuildTime((String)entity.get("dataBuildTime"));
		}
		if (!"null".equals(entity.get("dataStatus"))) {
			cifQhValidate.setDataStatus((String)entity.get("dataStatus"));
		}
		if (!"null".equals(entity.get("erCode"))) {
			cifQhValidate.setErCode((String)entity.get("erCode"));
		}
		if (!"null".equals(entity.get("erMsg"))) {
			cifQhValidate.setErMsg((String)entity.get("erMsg"));
		}
		if (!"null".equals(entity.get("rskScore"))) {
			cifQhValidate.setRskScore((String)entity.get("rskScore"));
		}
		if (!"null".equals(entity.get("rskMark"))) {
			cifQhValidate.setRskMark((String)entity.get("rskMark"));
		}
		if (!"null".equals(entity.get("isMachdBlMakt"))) {
			cifQhValidate.setIsMachdBlMakt((String)entity.get("isMachdBlMakt"));
		}
		if (!"null".equals(entity.get("isMachCraCall"))) {
			cifQhValidate.setIsMachCraCall((String) entity.get("isMachCraCall"));
		}
		if (!"null".equals(entity.get("isMachFraud"))) {
			cifQhValidate.setIsMachFraud((String)entity.get("isMachFraud"));
		}
		if (!"null".equals(entity.get("isMachEmpty"))) {
			cifQhValidate.setIsMachEmpty((String)entity.get("isMachEmpty"));
		}
		if (!"null".equals(entity.get("isMachYZmobile"))) {
			cifQhValidate.setIsMachYZmobile((String)entity.get("isMachYZmobile"));
		}
		if (!"null".equals(entity.get("isMachSmallNo"))) {
			cifQhValidate.setIsMachSmallNo((String)entity.get("isMachSmallNo"));
		}
		if (!"null".equals(entity.get("isMachSzNo"))) {
			cifQhValidate.setIsMachSzNo((String)entity.get("isMachSzNo"));
		}
		if (!"null".equals(entity.get("iUpdateDate"))) {
			cifQhValidate.setiUpdateDate((String)entity.get("iUpdateDate"));
		}
		if (!"null".equals(entity.get("mUpdateDate"))) {
			cifQhValidate.setmUpdateDate((String)entity.get("mUpdateDate"));
		}
		if (!"null".equals(entity.get("iRskDesc"))) {
			cifQhValidate.setiRskDesc((String)entity.get("iRskDesc"));
		}
		if (!"null".equals(entity.get("mRskDesc"))) {
			cifQhValidate.setmRskDesc((String)entity.get("mRskDesc"));
		}
		if (!"null".equals(entity.get("province"))) {
			cifQhValidate.setIsMachEmpty((String)entity.get("province"));
		}
		if (!"null".equals(entity.get("cityInfo"))) {
			cifQhValidate.setIsMachYZmobile((String)entity.get("cityInfo"));
		}
		if (!"null".equals(entity.get("borough"))) {
			cifQhValidate.setIsMachSmallNo((String)entity.get("borough"));
		}
		if (!"null".equals(entity.get("fmtAddress"))) {
			cifQhValidate.setIsMachSzNo((String)entity.get("fmtAddress"));
		}
		if (!"null".equals(entity.get("longitude"))) {
			cifQhValidate.setiUpdateDate((String)entity.get("longitude"));
		}
		if (!"null".equals(entity.get("latitude"))) {
			cifQhValidate.setmUpdateDate((String)entity.get("latitude"));
		}
		if (!"null".equals(entity.get("buildingName"))) {
			cifQhValidate.setiRskDesc((String)entity.get("buildingName"));
		}
		if (!"null".equals(entity.get("buildingAddress"))) {
			cifQhValidate.setmRskDesc((String)entity.get("buildingAddress"));
		}
		if (!"null".equals(entity.get("houseArodAvgPrice"))) {
			cifQhValidate.setIsMachSzNo((String)entity.get("houseArodAvgPrice"));
		}
		if (!"null".equals(entity.get("houseAvgPrice"))) {
			cifQhValidate.setiUpdateDate((String)entity.get("houseAvgPrice"));
		}
		if (!"null".equals(entity.get("state"))) {
			cifQhValidate.setmUpdateDate((String)entity.get("state"));
		}
		if (!"null".equals(entity.get("buildingType"))) {
			cifQhValidate.setiRskDesc((String)entity.get("buildingType"));
		}
		if (!"null".equals(entity.get("isMatch"))) {
			cifQhValidate.setmRskDesc((String)entity.get("isMatch"));
		}
		if (!"null".equals(entity.get("addressPorperty"))) {
			cifQhValidate.setiRskDesc((String)entity.get("addressPorperty"));
		}
		if (!"null".equals(entity.get("baseDate"))) {
			cifQhValidate.setIsMachSzNo((String)entity.get("baseDate"));
		}
		if (!"null".equals(entity.get("credooScoreM0"))) {
			cifQhValidate.setIsMachSzNo((String)entity.get("credooScoreM0"));
		}
		if (!"null".equals(entity.get("credooScoreM1"))) {
			cifQhValidate.setiUpdateDate((String)entity.get("credooScoreM1"));
		}
		if (!"null".equals(entity.get("credooScoreM2"))) {
			cifQhValidate.setmUpdateDate((String)entity.get("credooScoreM2"));
		}
		if (!"null".equals(entity.get("credooScoreM3"))) {
			cifQhValidate.setiRskDesc((String)entity.get("credooScoreM3"));
		}
		if (!"null".equals(entity.get("credooScoreM4"))) {
			cifQhValidate.setmRskDesc((String)entity.get("credooScoreM4"));
		}
		if (!"null".equals(entity.get("credooScoreM5"))) {
			cifQhValidate.setiRskDesc((String)entity.get("credooScoreM5"));
		}
		if (!"null".equals(entity.get("score"))) {
			cifQhValidate.setiRskDesc((String)entity.get("score"));
		}
		if (!"null".equals(entity.get("label"))) {
			cifQhValidate.setiRskDesc((String)entity.get("label"));
		}
		if (!"null".equals(entity.get("userName"))) {
			cifQhValidate.setiRskDesc((String)entity.get("userName"));
		}
		if (!"null".equals(entity.get("transId"))) {
			cifQhValidate.setiRskDesc((String)entity.get("transId"));
		}
		if (!"null".equals(entity.get("clientId"))) {
			cifQhValidate.setiRskDesc((String)entity.get("clientId"));
		}
		if (!"null".equals(entity.get("records"))) {
			cifQhValidate.setiRskDesc((String)entity.get("records"));
		}
		
		if (!"null".equals(entity.get("accountNo"))) {
			cifQhValidate.setiRskDesc((String)entity.get("accountNo"));
		}
		if (!"null".equals(entity.get("dcFlag"))) {
			cifQhValidate.setiRskDesc((String)entity.get("dcFlag"));
		}
		if (!"null".equals(entity.get("summaryScore"))) {
			cifQhValidate.setiRskDesc((String)entity.get("summaryScore"));
		}
		if (!"null".equals(entity.get("cstScore"))) {
			cifQhValidate.setiRskDesc((String)entity.get("cstScore"));
		}
		if (!"null".equals(entity.get("cotScore"))) {
			cifQhValidate.setiRskDesc((String)entity.get("cotScore"));
		}
		if (!"null".equals(entity.get("cntScore"))) {
			cifQhValidate.setiRskDesc((String)entity.get("cntScore"));
		}
		if (!"null".equals(entity.get("cnaScore"))) {
			cifQhValidate.setiRskDesc((String)entity.get("cnaScore"));
		}
		if (!"null".equals(entity.get("chvScore"))) {
			cifQhValidate.setiRskDesc((String)entity.get("chvScore"));
		}
		if (!"null".equals(entity.get("dsiScore"))) {
			cifQhValidate.setiRskDesc((String)entity.get("dsiScore"));
		}
		if (!"null".equals(entity.get("wlpScore"))) {
			cifQhValidate.setiRskDesc((String)entity.get("wlpScore"));
		}
		if (!"null".equals(entity.get("cnpScore"))) {
			cifQhValidate.setiRskDesc((String)entity.get("cnpScore"));
		}
		if (!"null".equals(entity.get("cotCluster"))) {
			cifQhValidate.setiRskDesc((String)entity.get("cotCluster"));
		}
		if (!"null".equals(entity.get("dsiCluster"))) {
			cifQhValidate.setiRskDesc((String)entity.get("dsiCluster"));
		}
		if (!"null".equals(entity.get("rskCluster"))) {
			cifQhValidate.setiRskDesc((String)entity.get("rskCluster"));
		}
		if (!"null".equals(entity.get("crbScore"))) {
			cifQhValidate.setiRskDesc((String)entity.get("cotScore"));
		}
		if (!"null".equals(entity.get("crbCluster"))) {
			cifQhValidate.setiRskDesc((String)entity.get("crbCluster"));
		}
		
		cifQhValidate.setCreditCardCVN((String)entity.get("creditCardCVN"));
		cifQhValidate.setExpiresYear((String)entity.get("expiresYear"));
		cifQhValidate.setExpiresMonth((String)entity.get("expiresMonth"));
		cifQhValidate.setReasonCode((String)entity.get("reasonCode"));
		cifQhValidate.setEntityAuthCode((String) entity.get("entityAuthCode"));
		cifQhValidate.setEntityAuthDate((String) entity.get("entityAuthDate"));
		cifQhValidate.setAuthResult((String) entity.get("authResult"));
		cifQhValidate.setEntityName((String)entity.get("entityName"));
		cifQhValidate.setEntityId((String) entity.get("entityId"));
		cifQhValidate.setCaseStatus((String)entity.get("caseStatus"));
		cifQhValidate.setBuildDate((String)entity.get("buildDate"));
		cifQhValidate.setCourtName((String)entity.get("courtName"));
		cifQhValidate.setExecMoney((String)entity.get("execMoney"));
		cifQhValidate.setCaseCode((String)entity.get("caseCode"));
		return cifQhValidate;
	}

	public static void main(String[] args) {
		CifQhValidate validate = new CifQhValidate();
		Map map = new HashMap<>();
		map.put("name", "陈建鹏");
		map.put("idNo", "440981199203082836");
		map.put("idType", "0");
		map.put("organId", "111111");
		map.put("searchCode", "WZ2016071810234452400");
		map.put("token", "test123");
		map.put("mobileNo", "13256966203");
		JSONObject json = JSONObject.fromObject(map);
		System.out.println("11s"+json.toString());
		String str = HttpPostJson.sendPost("http://credit.wanfin.com/credit/credit/test", json.toString());
		//String str = "{"context":{"headers":{},"entity":{"status":"OK","istrue":true,"msg":"请求成功","entity":{"id":"bfdc5d33bea440d2a66a24262e6ebce6","remarks":null,"createBy":null,"createDate":null,"updateBy":null,"updateDate":null,"delFlag":"0","organId":"111111","resId":"d59cab80875e4ad297566a1ba3832223","idNo":"440981199203082836","idType":"0","name":"陈建鹏","mobileNo":"13256966203","seqNo":"90KwIAMhQU79T_MWj6RKSA","isRealIdentity":"1","isValidAddress":"","addressType":"","isRealCompany":"","companySimDeg":"","appear1ThDate":"","appearLastDate":"","isOwnerMobile":"","ownerMobileStatus":"","useTimeScore":"","isExistRel":"","relLevel":"","carChkResult":"","isAsyned":"","carTypeInc":"","carFactoryInc":"","carPrice":"","drvStatusQryId":"","houseChkResult":"","houseDataDate":"","photoCmpResult":"","photoSimDeg":"","isHighestEduBkg":"","dataDate":"2017-05-22 09:46:09","graduateSchool":"","graduateMajor":"","graduateYear":"","isOwnerMobileTwo":null,"ownerMobileStatusTwo":null,"useTimeScoreTwo":null}},"entityType":"www.wanfin.com.commons.config.HttpEntity","entityAnnotations":[],"entityStream":{"committed":false,"closed":false},"lastModified":null,"date":null,"committed":false,"responseCookies":{},"acceptableLanguages":["*"],"requestCookies":{},"lengthLong":-1,"entityTag":null,"links":[],"stringHeaders":{},"allowedMethods":[],"acceptableMediaTypes":[{"type":"*","subtype":"*","parameters":{},"quality":1000,"wildcardType":true,"wildcardSubtype":true}],"entityClass":"www.wanfin.com.commons.config.HttpEntity","mediaType":null,"length":-1,"language":null,"location":null},"status":200,"lastModified":null,"date":null,"cookies":{},"metadata":{},"statusInfo":"OK","entityTag":null,"links":[],"stringHeaders":{},"allowedMethods":[],"entity":{"status":"OK","istrue":true,"msg":"请求成功","entity":{"id":"bfdc5d33bea440d2a66a24262e6ebce6","remarks":null,"createBy":null,"createDate":null,"updateBy":null,"updateDate":null,"delFlag":"0","organId":"111111","resId":"d59cab80875e4ad297566a1ba3832223","idNo":"440981199203082836","idType":"0","name":"陈建鹏","mobileNo":"13256966203","seqNo":"90KwIAMhQU79T_MWj6RKSA","isRealIdentity":"1","isValidAddress":"","addressType":"","isRealCompany":"","companySimDeg":"","appear1ThDate":"","appearLastDate":"","isOwnerMobile":"","ownerMobileStatus":"","useTimeScore":"","isExistRel":"","relLevel":"","carChkResult":"","isAsyned":"","carTypeInc":"","carFactoryInc":"","carPrice":"","drvStatusQryId":"","houseChkResult":"","houseDataDate":"","photoCmpResult":"","photoSimDeg":"","isHighestEduBkg":"","dataDate":"2017-05-22 09:46:09","graduateSchool":"","graduateMajor":"","graduateYear":"","isOwnerMobileTwo":null,"ownerMobileStatusTwo":null,"useTimeScoreTwo":null}},"mediaType":null,"length":-1,"language":null,"location":null,"headers":{}}";
		System.out.println("---1111111------"+str);
		JSONObject jsonObject = JSONObject.fromObject(str);
		System.out.println("2222"+jsonObject);
		JSONObject entity = (JSONObject) jsonObject.get("entity");
		System.out.print("boolean:"+entity.get("istrue"));
		JSONObject object = (JSONObject) entity.get("entity");
		System.out.println("sssssssssss:"+object.get("resId"));
		System.out.println("----qqqq----"+object.get("resId").equals("null"));
		System.out.println("----wwww----"+object.get("resId") == null);
		if (!object.get("resId").equals("null")) {
			validate.setResId((String)object.get("resId"));
		}
		if (!object.get("idType").equals("null")) {
			validate.setIdType((String)object.get("idType"));
		}
		System.out.println("sssssssssss:"+object.get("id"));
		System.out.println("sssssssssss:"+object.get("idType"));
	}
}
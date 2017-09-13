package com.wanfin.fpd.modules.contract.web.loancontract;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wanfin.fpd.common.config.Cons;
import com.wanfin.fpd.common.config.SvalBase.JsonKey;
import com.wanfin.fpd.common.utils.StringUtils;
import com.wanfin.fpd.common.web.BaseController;
import com.wanfin.fpd.core.ApiSval.JKey;
import com.wanfin.fpd.core.Link;
import com.wanfin.fpd.core.Link.LinkKey;
import com.wanfin.fpd.core.LinkMapping;
import com.wanfin.fpd.modules.company.entity.TCompany;
import com.wanfin.fpd.modules.company.service.TCompanyService;
import com.wanfin.fpd.modules.contract.AutoVo;
import com.wanfin.fpd.modules.contract.entity.TLoanContract;
import com.wanfin.fpd.modules.contract.entity.WOrder;
import com.wanfin.fpd.modules.contract.resource.loancontract.TLoanContractApiResource;
import com.wanfin.fpd.modules.contract.service.TLoanContractService;
import com.wanfin.fpd.modules.contract.vo.CsPersonVo;
import com.wanfin.fpd.modules.credit.service.CreditApplyService;
import com.wanfin.fpd.modules.employee.entity.TEmployee;
import com.wanfin.fpd.modules.employee.service.TEmployeeService;
import com.wanfin.fpd.modules.form.entity.tpl.DfFormTpl;
import com.wanfin.fpd.modules.form.service.tpl.DfFormTplService;
import com.wanfin.fpd.modules.product.entity.TProduct;
import com.wanfin.fpd.modules.product.service.TProductService;
import com.wanfin.fpd.modules.sys.entity.Office;
import com.wanfin.fpd.modules.sys.service.OfficeService;
import com.wanfin.fpd.modules.wauto.entity.WAuto;
import com.wanfin.fpd.modules.wauto.service.WAutoService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;

@Controller
@Api(value = LinkKey.WOrder, description = "订单", authorizations = {})
@RequestMapping(value = Link.API_PATH + LinkKey.WOrder)
public class WOrderApiController extends BaseController{
	private TLoanContractApiResource apiResource = new TLoanContractApiResource();
	@Autowired
	private TLoanContractService entityService;
	@Autowired
	private DfFormTplService formTplService;
	@Autowired
	private OfficeService officeService;
	@Autowired
	private TCompanyService tCompanyService;
	@Autowired
	private TEmployeeService tEmployeeService;
	@Autowired
	private TProductService tProductService;
	@Autowired
	private WAutoService wAutoService;
	
	@Autowired
	private CreditApplyService creditApplyService;
	
	/************************************************************************************************
	 * 【标准接口】
	 ************************************************************************************************/
	/**
	 * @Description  GET 获取[[_订单_]]列表(getAll)
	 * 返回所有[[_订单_]]的列表（数组）
	 * 
	 * @param entity [[_订单_]]参数
	 * @return
	 * @author Chenh 
	 * @date 2016年6月6日 下午12:57:08
	 */
	@ResponseBody
	@ApiResponses({
		@ApiResponse(code=200, message="成功！"),
		
		@ApiResponse(code=400, message="错误请求！"),
		@ApiResponse(code=401, message="未授权！"),
		@ApiResponse(code=403, message="禁止访问！"),
		@ApiResponse(code=404, message="服务不存在！"),

		@ApiResponse(code=500, message="服务器内部错误！")
	})
	@RequestMapping(value = {LinkMapping.GET_ALL}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
   	@ApiOperation(value = "根据条件获取订单列表", notes = "根据条件获取订单列表", position = 1, httpMethod = "GET", response = Response.class)
	public Response get(@ApiParam(name="entity", value="订单(entity)") WOrder entity) {
		Collection<TLoanContract> entitys = entityService.findList(entity);
		entity.setOper(RequestMethod.GET);
        return Response.ok(apiResource.getCollectionRes(entity, entitys, LinkKey.TLoanContract)).build();
	}
	
	/**
	 * @Description GET/{id} 获取[[_订单信息_]](getById)
	 * 返回单个资源[[_订单信息_]]
	 * 
	 * @param info API链接[[_订单信息_]]
	 * @param entity [[_订单信息_]]参数
	 * @param id  [[_订单信息_]]主键
	 * @return
	 * @author Chenh 
	 * @date 2016年6月6日 下午12:58:28
	 */
	@ResponseBody
	@ApiResponses({
		@ApiResponse(code=200, message="成功！"),
		
		@ApiResponse(code=400, message="错误请求！"),
		@ApiResponse(code=401, message="未授权！"),
		@ApiResponse(code=403, message="禁止访问！"),
		@ApiResponse(code=404, message="服务不存在！"),
		@ApiResponse(code=405, message="GET请求不支持！"),
		@ApiResponse(code=412, message="参数错误！"),

		@ApiResponse(code=500, message="服务器内部错误！")
	})
	@RequestMapping(value = {LinkMapping.GET_BY_ID}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	@ApiOperation(value = "根据ID获取订单状态信息", notes = "根据ID获取订单状态信息", position = 2, httpMethod = "GET", response = Response.class)
	public Response get(@ApiParam(name="entity", value="订单信息(entity)") WOrder entity, @PathVariable(JKey.ID) @ApiParam(name="id", value="主键(id)") String id) {
		Map<String, Object> result = new HashMap<String, Object>();
		if(StringUtils.isNotEmpty(id)){
			TLoanContract enty = entityService.getByWtypeId(id);
			if (enty == null) {
				result.put(JKey.MSG, "订单不存在！");
				return Response.status(Status.PRECONDITION_FAILED).entity(result).build();
			}
			if (StringUtils.isEmpty(enty.getStatus())){
				result.put(JKey.MSG, "订单数据有误！");
				return Response.status(Status.INTERNAL_SERVER_ERROR).entity(result).build();
			}
			
			result.put(JKey.MSG, enty.getStatus());
			return Response.ok().entity(result).build();
		}
		result.put(JKey.MSG, "错误请求！");
		return Response.status(Status.BAD_REQUEST).entity(result).build();
	}
	
	/**
	 * @Description POST 新增[[_订单信息_]](save)
	 * 创建某个指定[[_订单信息_]]的信息
	 * 返回新生成的资源[[_订单信息_]]
	 * 
	 * @param entity [[_订单信息_]]参数
	 * @return
	 * @author Chenh 
	 * @date 2016年6月6日 下午12:59:04
	 */
	@ResponseBody
	@ApiResponses({
		@ApiResponse(code=201, message="成功！"),
		
		@ApiResponse(code=400, message="错误请求！"),
		@ApiResponse(code=401, message="未授权！"),
		@ApiResponse(code=403, message="禁止访问！"),
		@ApiResponse(code=404, message="服务不存在！"),
		@ApiResponse(code=409, message="记录已存在！"),
		@ApiResponse(code=412, message="参数错误！"),

		@ApiResponse(code=500, message="服务器内部错误！")
	})
	@RequestMapping(value = {LinkMapping.SAVE}, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON)
	@ApiOperation(value = "创建业务信息", notes = "创建业务信息", position = 3, httpMethod = "POST", response = Response.class)
    public Response post(@ApiParam(name="entity", value="业务订单信息(entity)") @RequestBody WOrder entity) {
        Map<String, Object> result = new HashMap<String, Object>();
		if(entity != null){
			AutoVo avo = isAutoYW(entity, result);
			TLoanContract tloanContractEntity = new TLoanContract(entity);
     		if(StringUtils.isNotEmpty(tloanContractEntity.getOrganId())){
    			//判断租户不为空
    			Office office = officeService.get(tloanContractEntity.getOrganId());
    			if(office == null){
    				result.put(JKey.MSG, "租户不存在！");
    				return Response.status(Status.PRECONDITION_FAILED).entity(result).build();
    			}else{
    				entity.setOrgan(office);
    			}

    			if(StringUtils.isEmpty(tloanContractEntity.getWtypeId())){
    				result.put(JKey.MSG, "订单号不存在！");
    				return Response.status(Status.PRECONDITION_FAILED).entity(result).build();
    			}

    			//判断订单不存在
    			TLoanContract newEntity = entityService.getByWtypeId(tloanContractEntity.getWtypeId());
    			if((newEntity != null)){
    				result.put(JKey.MSG, "订单已存在！");
    				return Response.status(Status.CONFLICT).entity(result).build();
    			}
    			
    			//判断产品不存在
    			if(entity.getProduct() != null){
    				if(StringUtils.isNotEmpty(entity.getProduct().getWtypeId())){
	        			TProduct newTProduct = tProductService.getByWtypeId(entity.getProduct().getWtypeId());
	    				if(newTProduct == null){
	    					newTProduct = entity.getProduct(); 
	    					if(StringUtils.isEmpty(newTProduct.getName())){
	    						result.put(JKey.MSG, "产品名不能为空，请检查产品数据完整性！");
	    	    				return Response.status(Status.PRECONDITION_FAILED).entity(result).build();
	    					}
	    					newTProduct.setId(null);
	    					newTProduct.setOrganId(entity.getAgencyId());
	    					
	    					newTProduct.setReleasesObje(entity.getUserType());
	    					Boolean isTrue = false;
            				if(!avo.isAuto()){
	    						tProductService.save(newTProduct);
		        				isTrue = initDfFormTpl(tloanContractEntity.getOrganId(), tloanContractEntity.getProductId(), Cons.FormTplId.W_PRODUCT_FORM_ID, newTProduct.getId(), newTProduct.getName(), Cons.FModel.M_PRODUCT.getKey());

		        				if(!isTrue){
	        						result.put(JKey.MSG, "服务器内部错误：产品模板关联失败("+Cons.FormTplId.W_PRODUCT_FORM_ID+")！");
	            					return Response.status(Status.INTERNAL_SERVER_ERROR).entity(result).build();
	        					}
            				}
	    				}else{
	    					entity.setProduct(newTProduct);
	    				}
        				tloanContractEntity.setProductId(newTProduct.getId());
    				}else{
    					result.put(JKey.MSG, "产品参数错误！");
    					return Response.status(Status.PRECONDITION_FAILED).entity(result).build();
    				}
    			}else{
    				result.put(JKey.MSG, "产品不存在！");
    				return Response.status(Status.PRECONDITION_FAILED).entity(result).build();
    			}

    			//判断客户不存在
    			if((entity.getUserType()).equals(Cons.CustomerType.CUST_EMPLOYEE)){
    				if(entity.getEmployee() != null){
    					if(StringUtils.isNotEmpty(entity.getEmployee().getWtypeId())){
	            			TEmployee newTEmployee = tEmployeeService.getByWtypeId(entity.getEmployee().getWtypeId());
	            			if(newTEmployee == null){
	            				if(StringUtils.isNotEmpty(entity.getGatheringBank()) && StringUtils.isNotEmpty(entity.getGatheringName()) && StringUtils.isNotEmpty(entity.getGatheringNumber())){
		            				newTEmployee = entity.getEmployee();
		            				if(StringUtils.isEmpty(newTEmployee.getName())){
			    						result.put(JKey.MSG, "个人客户名不能为空，请检查个人客户数据完整性！");
			    	    				return Response.status(Status.PRECONDITION_FAILED).entity(result).build();
			    					}
		            				newTEmployee.setId(null);
		            				newTEmployee.setOrganId(entity.getAgencyId());
		            				newTEmployee.setGatheringBank(entity.getGatheringBank());
		            				newTEmployee.setGatheringName(entity.getGatheringName());
		            				newTEmployee.setGatheringNumber(entity.getGatheringNumber());
		            				newTEmployee.setProduct(new TProduct(tloanContractEntity.getProductId()));
		            				
		            				Boolean isTrue = false;
		            				if(!avo.isAuto()){
			            				tEmployeeService.save(newTEmployee);
			            				isTrue = initDfFormTpl(tloanContractEntity.getOrganId(), tloanContractEntity.getProductId(), Cons.FormTplId.B_TEMPLOYEE_FORM_ID, newTEmployee.getId(), newTEmployee.getName(), Cons.FModel.M_CUSTOMER_EMPLOYEE.getKey());

			            				if(!isTrue){
		            						result.put(JKey.MSG, "服务器内部错误：客户<个人>模板关联失败("+Cons.FormTplId.B_TEMPLOYEE_FORM_ID+")！");
			            					return Response.status(Status.INTERNAL_SERVER_ERROR).entity(result).build();
		            					}
		            				}
	            				}else{
	            					result.put(JKey.MSG, "订单的银行卡信息不能为空（银行类型、开户行、开户人）！");
	            					return Response.status(Status.PRECONDITION_FAILED).entity(result).build();
	            				}
	            			}else{
		    					entity.setEmployee(newTEmployee);
		    				}
	        				tloanContractEntity.setCustomerId(newTEmployee.getId());
    					}else{
        					result.put(JKey.MSG, "个人客户(wtypeId)参数错误！");
        					return Response.status(Status.PRECONDITION_FAILED).entity(result).build();
        				}
        			}else{
        				result.put(JKey.MSG, "个人客户不存在！");
        				return Response.status(Status.PRECONDITION_FAILED).entity(result).build();
        			}
    			}else if((entity.getUserType()).equals(Cons.CustomerType.CUST_COMPANY)){
    				if(entity.getCompany() != null){
    					if(StringUtils.isNotEmpty(entity.getCompany().getWtypeId())){
	    					TCompany newTCompany = tCompanyService.getByWtypeId(entity.getCompany().getWtypeId());
	            			if(newTCompany == null){
	            				newTCompany = entity.getCompany();
	            				if(StringUtils.isEmpty(newTCompany.getName())){
		    						result.put(JKey.MSG, "企业客户名不能为空，请检查企业客户数据完整性！");
		    	    				return Response.status(Status.PRECONDITION_FAILED).entity(result).build();
		    					}
	            				newTCompany.setId(null);
	            				newTCompany.setOrganId(entity.getAgencyId());
	            				//newTCompany.setProduct(new TProduct(tloanContractEntity.getProductId()));
	            				
	            				Boolean isTrue = false;
	            				if(!avo.isAuto()){
		            				tCompanyService.save(entity.getCompany());
		            				isTrue = initDfFormTpl(tloanContractEntity.getOrganId(), tloanContractEntity.getProductId(), Cons.FormTplId.B_TCOMPANY_FORM_ID, newTCompany.getId(), newTCompany.getName(), Cons.FModel.M_CUSTOMER_COMPANY.getKey());

	            					if(!isTrue){
	            						result.put(JKey.MSG, "服务器内部错误：客户<机构>模板关联失败("+Cons.FormTplId.B_TCOMPANY_FORM_ID+")！");
		            					return Response.status(Status.INTERNAL_SERVER_ERROR).entity(result).build();
	            					}
	            				}
	            			}else{
		    					entity.setCompany(newTCompany);
		    				}
	        				tloanContractEntity.setCustomerId(newTCompany.getId());
    					}else{
        					result.put(JKey.MSG, "企业客户(wtypeId)参数错误！");
        					return Response.status(Status.PRECONDITION_FAILED).entity(result).build();
        				}
        			}else{
        				result.put(JKey.MSG, "企业客户不存在！");
        				return Response.status(Status.PRECONDITION_FAILED).entity(result).build();
        			}
	    		}else{
	    			result.put(JKey.MSG, "客户类型未知！");
					return Response.status(Status.PRECONDITION_FAILED).entity(result).build();
	    		}
    			
				
				if((tloanContractEntity.getLoanDate() != null) && StringUtils.isNotEmpty(tloanContractEntity.getLoanPeriod()) &&StringUtils.isNotEmpty(tloanContractEntity.getPeriodType())){
					try {
						tloanContractEntity.setContractNumber(entityService.buildContractNumber(tloanContractEntity, tloanContractEntity.getOrganId()));
					} catch (Exception e) {
						result.put(JKey.MSG, "服务器内错误：合同号生成失败！");
    					return Response.status(Status.INTERNAL_SERVER_ERROR).entity(result).build();
					}
					
					tloanContractEntity.setPayPrincipalDate(getPayPrincipalDate(tloanContractEntity.getLoanDate(), Integer.parseInt(tloanContractEntity.getLoanPeriod()), tloanContractEntity.getPeriodType()));
					
					if(StringUtils.isEmpty(entity.getApplyAmount())){
						result.put(JKey.MSG, "贷款金额不能为空！");
						return Response.ok().entity(result).build();
					}

					if(StringUtils.isEmpty(entity.getOrderSn())){
						result.put(JKey.MSG, "订单号不能为空！");
						return Response.ok().entity(result).build();
					}
					
    				Boolean isTrue = false;
    				if(!avo.isAuto()){
    					entityService.save(tloanContractEntity);
    					isTrue = initDfFormTpl(tloanContractEntity.getOrganId(), tloanContractEntity.getProductId(), Cons.FormTplId.W_LOANCONTRACT_FORM_ID, tloanContractEntity.getId(), tloanContractEntity.getContractNumber(), Cons.FModel.M_BUSINESS_APPLICATION_YWSQ.getKey());
    					
        				if(!isTrue){
    						result.put(JKey.MSG, "服务器内部错误：业务申请模板关联失败("+Cons.FormTplId.W_LOANCONTRACT_FORM_ID+")！");
        					return Response.status(Status.INTERNAL_SERVER_ERROR).entity(result).build();
    					}
    				}else{
    					return dealAutoYw(entity, result);
    				}
					entity.setOper(RequestMethod.POST);
					return resCreated(new TLoanContractApiResource(tloanContractEntity));
				}else{
					result.put(JKey.MSG, "订单的还款信息不能为空（放款日期、贷款期限、还款周期）！");
					return Response.status(Status.PRECONDITION_FAILED).entity(result).build();
				}
				
			}
			result.put(JKey.MSG, "租户不存在！");
			return Response.status(Status.PRECONDITION_FAILED).entity(result).build();
        }
		result.put(JKey.MSG, "错误请求！");
		return Response.status(Status.BAD_REQUEST).entity(result).build();
	}
	
	/**
	 * 判断是否自动业务
	 * @param entity
	 */
	private AutoVo isAutoYW(WOrder entity, Map<String, Object> result) {
		AutoVo vo = new AutoVo();
		if(StringUtils.isEmpty(entity.getIsAuto()) || ("0").equals(entity.getIsAuto())){
			vo.setAuto(false);
			vo.setResponse(null);
		}else if(entity.getIsAuto().equals("1")){//1代表自动化
			vo.setAuto(true);
			vo.setResponse(null);
		}else{
			vo.setAuto(false);
			result.put(JKey.MSG, "自动化属性不再指定范围（0、1、空）！");
			vo.setResponse(Response.status(Status.PRECONDITION_FAILED).entity(result).build());
		}
		return vo;
	}
	
	/**
	 * 自动业务处理
	 * @param entity
	 */
	private Response dealAutoYw(WOrder entity, Map<String, Object> result) {
		CsPersonVo param = new CsPersonVo(entity.getEmployee());
		
		param.setOrganCode(entity.getOrgan().getCode());//组织机构编码
		param.setSeqNo(entity.getId());
		param.setAmount(new BigDecimal(entity.getApplyAmount()));
		CsPersonVo cspVo = creditApplyService.calculateGrade(param);
		
		System.out.println(cspVo.getAudit()+"--"+cspVo.getScore()+"---"+cspVo.getAmount());
		
		if(cspVo.getAudit() == 1){
			WAuto wAuto = new WAuto(entity);
			wAuto.setIsAgree(cspVo.getAudit());
			wAutoService.save(wAuto);
			result.put(JKey.MSG, "恭喜你！风控审核已通过！");
			result.put(JsonKey.KEY_DATA, cspVo);
			return Response.ok().entity(result).build();
		}else if(cspVo.getAudit() == 0){
			WAuto wAuto = new WAuto(entity);
			wAuto.setIsAgree(cspVo.getAudit());
			wAutoService.save(wAuto);
			result.put(JKey.MSG, "Sorry！风控审核未通过！");
			result.put(JsonKey.KEY_DATA, cspVo);
			return Response.ok().entity(result).build();
		}else{
			result.put(JKey.MSG, "风控系统异常：原因Audit值异常！");
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(result).build();
		}
	}

	private Boolean initDfFormTpl(String organId, String productId, String formId, String relId, String relName, String relFModel) {
		DfFormTpl formTpl = formTplService.get(formId);
		if(formTpl == null){
			return false;
		}
		try{
			formTpl.setId(null);
			formTpl.setSname(relName);
			formTpl.setProduct(new TProduct(productId));
			formTpl.setRelId(relId);
			formTpl.setModel(relFModel);
			formTpl.setParent(null);
			formTpl.setName(null);
			formTpl.setRemarks(null);
			formTpl.setOrganId(organId);
			formTplService.save(formTpl);
		} catch (NullPointerException e) {
			System.err.print("保存表单发生空指针异常！");;
			return false;
		}
		return true;
	}
	
	/**
	 * @Description 还本金日期
	 * @param loanDate	放款日期
	 * @param periods 贷款期数 
	 * @param periodType 还款周期 : 1|2|3  = 年|月|日 
	 * @author zzm
	 * @date 2016-5-12 下午2:46:56  
	 */
	public static Date getPayPrincipalDate(Date loanDate, int periods, String periodType){
		Calendar c = Calendar.getInstance();
		c.setTime(loanDate);
		if ("3".equals(periodType)) {// 日
			c.add(Calendar.DATE, periods - 1);
		} else if ("2".equals(periodType)) {// 月
			c.add(Calendar.DATE, -1);
			c.add(Calendar.MONTH, periods);
		} else if ("1".equals(periodType)) {// 年
			c.add(Calendar.DATE, -1);
			c.add(Calendar.YEAR, periods);
		}
		return c.getTime();
	}
}

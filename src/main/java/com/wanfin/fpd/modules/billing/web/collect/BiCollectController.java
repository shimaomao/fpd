/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.billing.web.collect;

import java.awt.Desktop;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.util.EntityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mangofactory.swagger.annotations.ApiIgnore;
import com.wanfin.fpd.common.config.Global;
import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.utils.InterfaceUtil;
import com.wanfin.fpd.common.utils.MD5;
import com.wanfin.fpd.common.utils.StringUtils;
import com.wanfin.fpd.common.web.BaseController;
import com.wanfin.fpd.core.billing.BiSval;
import com.wanfin.fpd.core.billing.BillingEngine;
import com.wanfin.fpd.modules.billing.entity.collect.BiCollect;
import com.wanfin.fpd.modules.billing.entity.rule.BiRule;
import com.wanfin.fpd.modules.sys.entity.Dict;
import com.wanfin.fpd.modules.sys.service.DictService;
import com.wanfin.fpd.modules.sys.utils.UserUtils;

/**
 * 计费汇总Controller
 * @author chenh
 * @version 2016-07-02
 */
@ApiIgnore
@Controller
@RequestMapping(value = "${adminPath}/billing/collect/biCollect")
public class BiCollectController extends BaseController {
	@Autowired
	private DictService dictService;

	@Autowired
	private BillingEngine engine;
	
	@ModelAttribute
	public BiCollect get(@RequestParam(required=false) String id) {
		BiCollect entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = engine.collect().get(id);
		}
		if (entity == null){
			entity = new BiCollect();
		}
		return entity;
	}
	
	@RequiresPermissions("billing:collect:biCollect:view")
	@RequestMapping(value = {"list", ""})
	public String list(BiCollect biCollect, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<BiCollect> page = engine.collect().findPage(new Page<BiCollect>(request, response), biCollect); 
		List<BiRule> biRules = engine.rule().findList(new BiRule());
		model.addAttribute("page", page);
		model.addAttribute("biRules", biRules);
		model.addAttribute("biCollect", biCollect);
		return "modules/billing/collect/biCollectList";
	}

	@RequiresPermissions("billing:collect:biCollect:view")
	@RequestMapping(value = "form")
	public String form(BiCollect biCollect, Model model) {
		List<BiRule> biRules = engine.rule().findList(new BiRule());
		model.addAttribute("biRules", biRules);
		model.addAttribute("biCollect", biCollect);
		return "modules/billing/collect/biCollectForm";
	}

	@RequiresPermissions("billing:collect:biCollect:edit")
	@RequestMapping(value = "save")
	public String save(BiCollect biCollect, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, biCollect)){
			return form(biCollect, model);
		}
		engine.collect().save(biCollect);
		addMessage(redirectAttributes, "保存计费汇总成功");
		return "redirect:"+Global.getAdminPath()+"/billing/collect/biCollect/?repage";
	}
	
	@RequiresPermissions("billing:collect:biCollect:edit")
	@RequestMapping(value = "delete")
	public String delete(BiCollect biCollect, RedirectAttributes redirectAttributes) {
		engine.collect().delete(biCollect);
		addMessage(redirectAttributes, "删除计费汇总成功");
		return "redirect:"+Global.getAdminPath()+"/billing/collect/biCollect/?repage";
	}

	//@RequiresPermissions("billing:rule:biRule:view")
	@RequiresPermissions("billing:collect:biCollect:view")
	@RequestMapping(value = {"openServer"})
	public String openServer(BiRule biRule, HttpServletRequest request, HttpServletResponse response, Model model) {
		Dict dict = new Dict();
		dict.setType(BiSval.BiDicKey.BILING_ELEMENT_TYPE);
		List<Dict> dicts = dictService.findList(dict);
		List<BiRule> groups = new ArrayList<BiRule>();
		if(biRule == null){
			biRule = new BiRule();
		}
		if(biRule.getType() == null){
			biRule.setType(BiSval.BiType.NUM);
		}
		if(biRule.getType() != null){
			List<BiRule> products = engine.rule().findList(biRule);
			BiCollect biCollect = new BiCollect();
			biCollect.setStatus(1);//服务中
			/*List<BiCollect> biCollects = engine.collect().findListByOrganId(biCollect);*/

//			//方案1
			for (BiRule product : products) {
				/*for (BiCollect collect : biCollects) {
					if((product).equals(collect.getRule())){
						product.setHasBuy(true);
					}
				}*/
				
				if((product.getPrice().getType()).equals(product.getPrice().getType())){
					groups.add(product);
				}
			}
			
			
//			//方案2
//			for (BiCollect collect : biCollects) {
//				if(products.contains(collect.getRule())){
//					products.remove(collect.getRule());
//				}
//			}
//			
//			for (BiRule product : products) {
//				if((product.getPrice().getType()).equals(product.getPrice().getType())){
//					groups.add(product);
//				}
//			}
		}
		model.addAttribute("dicts", dicts);
		model.addAttribute("groups", groups);
		model.addAttribute("biRule", biRule);
		return "modules/billing/collect/openServerList";
	}
	
	@RequiresPermissions("billing:collect:biCollect:view")
	@RequestMapping(value = {"openedServer"})
	public String openedServer(BiCollect biCollect, HttpServletRequest request, HttpServletResponse response, Model model) {
		if(biCollect == null){
			biCollect = new BiCollect();
		}
		if(biCollect.getStatus() == null){
			biCollect.setStatus(BiSval.BiCollectStatus.SERVER_ING);
		}

		if(biCollect.getRule() == null){
			biCollect.setRule(new BiRule());
		}
		if(biCollect.getRule().getType() == null){
			biCollect.getRule().setType(BiSval.BiType.NUM);
		}
		Dict dict = new Dict();
		dict.setType(BiSval.BiDicKey.BILING_ELEMENT_TYPE);
		List<Dict> dicts = dictService.findList(dict);
		List<BiCollect> biCollects = engine.collect().findListByOrganId(biCollect);
		model.addAttribute("dicts", dicts);
		model.addAttribute("biCollects", biCollects);
		model.addAttribute("biCollect", biCollect);
		return "modules/billing/collect/openedServerList";
	}
	
	@ResponseBody
	@RequiresPermissions("billing:collect:biCollect:edit")
	@RequestMapping(value = "ajaxOpenServer")
	public Map<String, Object> openServer(String biRuleId, Model model, RedirectAttributes redirectAttributes) {
		Map<String, Object> result = new HashMap<String, Object>();
		StringBuffer msg = new StringBuffer();
		if(StringUtils.isNotEmpty(biRuleId)){
			/**
			 * 若产品为时间计价
			 * 需要更新用户下单时间对应的实际使用时间
			 */
			BiRule biRule = engine.rule().get(biRuleId);
			if((biRule.getType()).equals(BiSval.BiType.TIME)){
				biRule = engine.rule().init(biRule);
			}
			BiCollect biCollect = new BiCollect();
			biCollect.setRule(biRule);
			biCollect.setNumber(biRule.getNumber());
			biCollect.setWaringNumber(biRule.getWaringNumber());
			biCollect.setTotalTime(biRule.getTotalTime());
			biCollect.setTotalPrice(biRule.getTotalPrice()*biRule.getRate());
			biCollect.setElement(biRule.getPrice().getElement());
			biCollect.setOrganId(UserUtils.getUser().getCompany().getId());
			engine.collect().save(biCollect);
			result.put("status", true);
			msg.append("服务开通成功！");
		}else{
			result.put("status", false);
			msg.append("服务开通失败！");
		}
		result.put("msg", msg.toString());
		return result;
	}
	
	
	@RequiresPermissions("billing:collect:biCollect:edit")
	@RequestMapping(value = "ajaxOpenServer1")
	public String ajaxOpenServer1(String biRuleId, Model model, RedirectAttributes redirectAttributes) {
		
			BiRule biRule = engine.rule().get(biRuleId);
			model.addAttribute("biRule", biRule);
			return "modules/billing/collect/biruleDetail";
	}
	
	@SuppressWarnings("restriction")
	@ResponseBody
	@RequiresPermissions("billing:collect:biCollect:edit")
	@RequestMapping(value = "paymoney")
	public String paymoney(Model model, RedirectAttributes redirectAttributes,HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException, JSONException {
		  
			String json_str = new String(request.getParameter("message").getBytes("ISO-8859-1"),"UTF-8");
			JSONObject json = new JSONObject(json_str);
			JSONArray ja = json.getJSONArray("message");
			
		            
	        String biRuleId = ja.getJSONObject(0).getString("id");
	      		
			String request_text = "", srcXml="";
			String ProcCode = "0200";
			
			String amount = ja.getJSONObject(0).getString("money");
			String phone = ja.getJSONObject(0).getString("phone");
			String card = ja.getJSONObject(0).getString("card");
			String merchantno = ja.getJSONObject(0).getString("merchantno");
			String terminalNo = ja.getJSONObject(0).getString("terminalno");
			String curcode = ja.getJSONObject(0).getString("curcode");
			String beneficiary = new String(ja.getJSONObject(0).getString("beneficiary").getBytes("ISO-8859-1"),"UTF-8");
			String merchantname = new String(ja.getJSONObject(0).getString("merchantname").getBytes("ISO-8859-1"),"UTF-8");
			String desc = new String(ja.getJSONObject(0).getString("desc").getBytes("ISO-8859-1"),"UTF-8");
			String remark = new String(ja.getJSONObject(0).getString("remark").getBytes("ISO-8859-1"),"UTF-8");
			String idcard = ja.getJSONObject(0).getString("idcard");
			String name = new String(ja.getJSONObject(0).getString("name").getBytes("ISO-8859-1"),"UTF-8");
			String idcardtype = ja.getJSONObject(0).getString("idcardtype");
			String bankAddress = new String(ja.getJSONObject(0).getString("bankAddress").getBytes("ISO-8859-1"),"UTF-8");
			String version = ja.getJSONObject(0).getString("version");
			String processCode = ja.getJSONObject(0).getString("processCode");
			String merchantPwd = ja.getJSONObject(0).getString("merchantPwd");
			String SynAddress = ja.getJSONObject(0).getString("SynAddress");
			String AsynAddress = ja.getJSONObject(0).getString("AsynAddress");
			String TransData = new String(ja.getJSONObject(0).getString("TransData").getBytes("ISO-8859-1"),"UTF-8");
			String BeneficiaryMobileNo = "";//ja.getJSONObject(0).getString("BeneficiaryMobileNo");
			String DeliveryAddress = "";//ja.getJSONObject(0).getString("DeliveryAddress");
			
			String MerchantOrderNo = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
			String AcqSsn = new SimpleDateFormat("HHmmss").format(new Date());
			String TransDatetime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
			String IpAddress = request.getLocalAddr();
			String OrderNo = "";
			String Reference = "Reference";
			String RespCode = "";
			String OrderState = "";
			
			String src = ProcCode
							+ getString(card)
							+ getString(processCode)
							+ getString(amount)
							+ getString(TransDatetime)
							+ getString(AcqSsn)
							+ getString(OrderNo)
							+ getString(TransData)
							+ getString(Reference)
							+ getString(RespCode)
							+ getString(terminalNo)
							+ getString(merchantno)
							+ getString(MerchantOrderNo)
							+ getString(OrderState);
			String macSrc = (src + " " + merchantPwd).toUpperCase();
			String MAC = new MD5().getMD5ofStr(macSrc);
			
			StringBuilder sb = new StringBuilder();
				sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>").append("\n")
				  .append("<x:NetworkRequest xmlns:x=\"http://www.payeco.com\" xmlns:xsi=\"http://www.w3.org\">").append("\n\t")
				  .append("<Version>").append(version).append("</Version>").append("\n\t")
				  .append("<ProcCode>").append(ProcCode).append("</ProcCode>").append("\n\t")
				  .append("<ProcessCode>").append(processCode).append("</ProcessCode>").append("\n\t")
				  .append("<AccountNo>").append(card).append("</AccountNo>").append("\n\t")
				  .append("<AccountType>").append("").append("</AccountType>").append("\n\t")
				  .append("<MobileNo>").append(phone).append("</MobileNo>").append("\n\t")
				  .append("<Amount>").append(amount).append("</Amount>").append("\n\t")
				  .append("<Currency>").append(curcode).append("</Currency>").append("\n\t")
				  .append("<SynAddress>").append(SynAddress).append("</SynAddress>").append("\n\t")
				  .append("<AsynAddress>").append(AsynAddress).append("</AsynAddress>").append("\n\t")
				  .append("<Remark>").append(remark).append("</Remark>").append("\n\t")
				  .append("<TerminalNo>").append(terminalNo).append("</TerminalNo>").append("\n\t")
				  .append("<MerchantNo>").append(merchantno).append("</MerchantNo>").append("\n\t")
				  .append("<MerchantOrderNo>").append(MerchantOrderNo).append("</MerchantOrderNo>").append("\n\t")
				  .append("<OrderNo>").append(OrderNo).append("</OrderNo>").append("\n\t")
				  .append("<OrderFrom>").append("16").append("</OrderFrom>").append("\n\t")
				  .append("<Language>").append("00").append("</Language>").append("\n\t")
				  .append("<Description>").append(desc).append("</Description>").append("\n\t")
				  .append("<OrderType>").append("00").append("</OrderType>").append("\n\t")
				  .append("<AcqSsn>").append(AcqSsn).append("</AcqSsn>").append("\n\t")
				  .append("<Reference>").append(Reference).append("</Reference>").append("\n\t")
				  .append("<TransDatetime>").append(TransDatetime).append("</TransDatetime>").append("\n\t")
				  .append("<MerchantName>").append(merchantname).append("</MerchantName>").append("\n\t")
				  .append("<TransData>").append(TransData).append("</TransData>").append("\n\t")
				  .append("<IDCardName>").append(name).append("</IDCardName>").append("\n\t")
				  .append("<IDCardNo>").append(idcard).append("</IDCardNo>").append("\n\t")
				  .append("<BankAddress>").append(bankAddress).append("</BankAddress>").append("\n\t")
				  .append("<IDCardType>").append(idcardtype).append("</IDCardType>").append("\n\t")
				  .append("<BeneficiaryName>").append(beneficiary).append("</BeneficiaryName>").append("\n\t")
				  .append("<BeneficiaryMobileNo>").append(BeneficiaryMobileNo).append("</BeneficiaryMobileNo>").append("\n\t")
				  .append("<DeliveryAddress>").append(DeliveryAddress).append("</DeliveryAddress>").append("\n\t")
				  .append("<IpAddress>").append(IpAddress).append("</IpAddress>").append("\n\t")
				  .append("<Location>").append("").append("</Location>").append("\n\t")
				  .append("<UserFlag>").append("").append("</UserFlag>").append("\n\t")
			      .append("<MAC>").append(MAC).append("</MAC>").append("\n")
			      .append("</x:NetworkRequest>").append("\n");
				  srcXml = sb.toString();
				  request_text = URLEncoder.encode(new sun.misc.BASE64Encoder().encode(srcXml.getBytes("UTF-8")), "UTF-8");

				  
				//预先插入数据库
				BiRule biRule = engine.rule().get(biRuleId);
				if((biRule.getType()).equals(BiSval.BiType.TIME)){
					biRule = engine.rule().init(biRule);
				}
				BiCollect biCollect = new BiCollect();
				biCollect.setRule(biRule);
				biCollect.setNumber(biRule.getNumber());
				biCollect.setWaringNumber(biRule.getWaringNumber());
				biCollect.setTotalTime(biRule.getTotalTime());
				biCollect.setTotalPrice(biRule.getTotalPrice()*biRule.getRate());
				biCollect.setElement(biRule.getPrice().getElement());
				biCollect.setOrganId(UserUtils.getUser().getCompany().getId());
				biCollect.setStatus(BiSval.BiCollectStatus.SERVER_END);
				engine.collect().save(biCollect);
				 //预先插入已开通服务表，状态为2停止服务。加入支付成功则改状态为1正常使用。
				Global.BIRULE_ID = biCollect.getId();
//				  try {
//					//response.sendRedirect("https://test.payeco.com:9443/DnaOnlineTest/servlet/DnaPayB2C?request_text="+request_text);
//				
//					Desktop desktop = Desktop.getDesktop();
//					if (Desktop.isDesktopSupported() && desktop.isSupported(Desktop.Action.BROWSE)) {
//						URI uri = URI.create("https://test.payeco.com:9443/DnaOnlineTest/servlet/DnaPayB2C?request_text="+request_text);
//						desktop.browse(uri);
//					}
//				  } catch (IOException e) {
//					e.printStackTrace();
//				}
				  System.out.println(request_text);
				   return request_text;
	}
	
	
	
	boolean isNullOrEmpty(String src){
		return src == null || "".equals(src.trim());
	}
	String getString(String src) {
	    return (isNullOrEmpty(src) ? "" : (" " + src.trim()));
	}
	
	
}
/**  
 * @Project fpd 
 * @Title BuilderController.java
 * @Package com.wanfin.fpd.modules.form.builder.web
 * @Description [[_xxx_]]文件 
 * @author Chenh
 * @date 2016年4月26日 下午2:18:34 
 * @version V1.0   
 */ 
package com.wanfin.fpd.modules.form.builder.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.mangofactory.swagger.annotations.ApiIgnore;
import com.wanfin.fpd.common.config.Cons;
import com.wanfin.fpd.common.config.Cons.FModel;
import com.wanfin.fpd.common.config.Global;
import com.wanfin.fpd.common.config.SvalBase;
import com.wanfin.fpd.common.utils.StringUtils;
import com.wanfin.fpd.modules.form.builder.df.DFTool;
import com.wanfin.fpd.modules.form.builder.df.field.DFOption;
import com.wanfin.fpd.modules.form.entity.tpl.DfFormTpl;
import com.wanfin.fpd.modules.form.service.tpl.DfFormTplService;
import com.wanfin.fpd.modules.product.entity.TProduct;
import com.wanfin.fpd.modules.sys.entity.Dict;
import com.wanfin.fpd.modules.sys.entity.User;
import com.wanfin.fpd.modules.sys.service.DictService;
import com.wanfin.fpd.modules.sys.service.SystemService;
import com.wanfin.fpd.modules.sys.utils.UserUtils;

/**
 * @Description [[_xxx_]] BuilderController类
 * @author Chenh
 * @date 2016年4月26日 下午2:18:34 
 */
@ApiIgnore
@Controller
@RequestMapping(value = "${adminPath}/form/builder/builder")
public class BuilderController{
	/**
	 * @Description TPL属性
	 * @Fields String TPL
	 * @author Chenh
	 * @date 2016年5月18日 上午11:42:31 
	 */
	@Autowired
	private SystemService systemService;
	@Autowired
	private DfFormTplService dfFormTplService;
	
	@Autowired
	private DictService dictService;
	
	/**
	 * @Description 获取模块类型的集合
	 * @author zzm 
	 * @date 2016-4-29 上午11:07:37  
	 */
	public static List<Record> getCategoryList(){
		return Db.find("SELECT a.category, MAX(a.categoryName) AS categoryName FROM df_column_define a GROUP BY a.category");
	}
	/**
	 * @Description 获取模块字段的定义
	 * @param ids 字段定义数据的id
	 * @author zzm 
	 * @date 2016-4-29 上午11:09:26  
	 */
	private List<Record> getColDefineListByIds(String ids){
		return Db.find("SELECT * from df_column_define a where a.id in ("+ids+")");
	}
	
	/**
	 * @Description 获取模块的全部字段的定义
	 * @param category	模块标识
	 * @author zzm 
	 * @date 2016-4-29 上午11:09:26  
	 */
	private List<Record> getColDefineList(String category){
		return Db.find("SELECT * from df_column_define a where a.category = '"+category+"'");
	}
	
	@RequiresPermissions("form:builder:builder:view")
	@RequestMapping(value = {"selectModel", ""})
	public String selectModel(HttpServletRequest request, HttpServletResponse response, Model model, String relId, String productId) {
		request.setAttribute("relId", relId);
		request.setAttribute("productId", productId);
		request.setAttribute("list",BuilderController.getCategoryList());
		return "modules/form/builder/selectModel";
	}

	@RequiresPermissions("form:builder:builder:view")
	@RequestMapping(value = {"selectData", ""})
	public String selectData(HttpServletRequest request, HttpServletResponse response, Model model, String category, String modsub, String productId, String categoryId, String relId, String urlData) {
		if(!StringUtils.isBlank(urlData)){
			try {
				JSONObject urlDatas = new JSONObject(urlData);
				category = urlDatas.getString("category");
				modsub = urlDatas.getString("modsub");
				categoryId = urlDatas.getString("categoryId");
				productId = urlDatas.getString("productId");
				relId = urlDatas.getString("relId");
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		if(!StringUtils.isBlank(category)){
			if(!StringUtils.isBlank(relId)){
				DfFormTpl dfFormTpl = new DfFormTpl();
				dfFormTpl.setRelId(null);
				dfFormTpl.setModsub(Cons.FModel.M_TPL.getKey());
				dfFormTpl.setModel(category);
				/**系统模板*/
				List<DfFormTpl> sysTpls = dfFormTplService.findSysTplList(dfFormTpl);
				request.setAttribute("sysTpls", sysTpls);
				
				/**自定义模板*/
				List<DfFormTpl> zdyTpls = dfFormTplService.findTplList(dfFormTpl);
				request.setAttribute("zdyTpls", zdyTpls);
				
				/**关联模板*/
				List<DfFormTpl> relTpls = dfFormTplService.findRelTplList(dfFormTpl);
				request.setAttribute("relTpls", relTpls);
				
				if((relTpls != null) && (relTpls.size() == 1)){
					request.setAttribute("dfFormTplRel",relTpls.get(0));
				}
				if(!StringUtils.isBlank(categoryId)){
					request.setAttribute("categoryId", categoryId);
				}
			}
			request.setAttribute("productId", productId);
			request.setAttribute("category", category);
			request.setAttribute("relId", relId);
			request.setAttribute("list", getColDefineList(category));
			request.setAttribute("modsub", modsub);
			FModel f = Cons.FModel.getFModelByKey(modsub);
			request.setAttribute("modsubKey", f);
			request.setAttribute("modsubs", Cons.FModel.getFModelByKey(category).getModsub());
			return "modules/form/builder/selectData";
		}else{
			return "redirect:"+Global.getAdminPath()+"/form/builder/builder/selectModel?repage";
		}
	}
	
	@RequiresPermissions("form:builder:builder:view")
	@RequestMapping(value = {"toForm", ""})
	public String toForm(HttpServletRequest request, HttpServletResponse response, Model model, String params, String category, String modsub, String ftplId, String relId, String productId, String modelUrl) {
		if(StringUtils.isNotEmpty(ftplId)){
			DfFormTpl dfFormTpl = dfFormTplService.get(ftplId);
			request.setAttribute("dfid", dfFormTpl.getId());
			request.setAttribute("dfjson", dfFormTpl.getJson());
			request.setAttribute("formHtml", dfFormTpl.getOriginalhtml());
			request.setAttribute("category", dfFormTpl.getModel());
			request.setAttribute("formName", dfFormTpl.getSname());
			request.setAttribute("modsub", dfFormTpl.getModsub());
			request.setAttribute("modsubKey", FModel.getFModelByKey(dfFormTpl.getModsub()));
		}else{
			request.setAttribute("category", category);
			if(StringUtils.isNotEmpty(modsub)){
				request.setAttribute("modsub", modsub);
				FModel modsubKey = FModel.getFModelByKey(modsub);
				request.setAttribute("modsubKey", modsubKey);
				request.setAttribute("formName", FModel.getFModelByKey(category).getName()+"-"+modsubKey.getName());
			}else{
				request.setAttribute("formName", FModel.getFModelByKey(category).getName());
			}
		}
		request.setAttribute("modelUrl", modelUrl);
		request.setAttribute("relId", relId);
		request.setAttribute("productId", productId);
		request.setAttribute(SvalBase.JsonKey.KEY_PARAMS, params);
		return "modules/form/builder/builderForm";
	}

	@ResponseBody
	@RequestMapping(value = {"ajaxRecords", ""})
	public Map<String, Object> ajaxRecords(HttpServletRequest request, HttpServletResponse response, Model model, String params, String category) {
		if(params != null){
			List<Record> records = getColDefineListByIds(params);
			return DFTool.convert(records);
		}else if(category != null){
			List<Record> records = getColDefineList(category);
			return DFTool.convert(records);
		}
		return null;
	}
	
	@ResponseBody
	@RequestMapping(value = {"ajaxUrl", ""})
	public List<DFOption> ajaxUrl(HttpServletRequest request, HttpServletResponse response, Model model, String params) {
		List<DFOption> options = new ArrayList<DFOption>();
		Dict dict = new Dict();
		dict.setType(params);//params即为字典表里面的type（key）
		List<Dict> dictlist = dictService.findByCondition(dict);
		for (int i = 0; i < dictlist.size(); i++) {
			Dict dic = dictlist.get(i);
			options.add(new DFOption(dic.getValue(), dic.getLabel(), false));
		}
		return options;
	}
	
	@RequiresPermissions("form:builder:builder:view")
	@RequestMapping(value = {"saveDesign", ""})
	@ResponseBody
	public String saveDesign(HttpServletRequest request, HttpServletResponse response, String id, String relId, String productId, String originalHtml, String formName, String category, String modsub, String json) {
		if(StringUtils.isNotEmpty(category)){
			User currentUser = UserUtils.getUser();//当前登录人
			DfFormTpl dfFormTpl = new DfFormTpl();
			if(StringUtils.isNotEmpty(id)){
				dfFormTpl.setId(id);
			}
			if(StringUtils.isNotEmpty(relId)){
				dfFormTpl.setRelId(relId);
				
				DfFormTpl dfTpl = new DfFormTpl();
				dfTpl.setModel(category);
				dfTpl.setRelId(relId);
				dfTpl.setDelFlag("0");
				DfFormTpl curDfTpl = dfFormTplService.getByModelRelId(dfTpl);
				if(curDfTpl != null){
					dfFormTplService.deleteWL(curDfTpl);
				}
			}
			
			//dfFormTpl.setOffice(currentUser.getOffice());
			dfFormTpl.setOffice(currentUser.getCompany());
			dfFormTpl.setModel(category);
			dfFormTpl.setJson(json);

			dfFormTpl.setOriginalhtml(originalHtml);
			dfFormTpl.setParsehtml(dfFormTpl.getOriginalhtml());
			dfFormTpl.setType("1");
			dfFormTpl.setProduct(new TProduct(productId));
			dfFormTpl.setModsub(modsub);
			dfFormTpl.setSname(formName);
			dfFormTpl.setName(null);
			dfFormTpl.setOrganId(null);
			dfFormTpl.setRemarks(null);
			request.setAttribute("productId", productId);
			dfFormTplService.save(dfFormTpl);
			return "1";
		}
		return "0";
	}
	
	@RequiresPermissions("form:builder:builder:view")
	@RequestMapping(value = {"ajaxSaveTpl", ""})
	@ResponseBody
	public Boolean ajaxSaveTpl(HttpServletRequest request, HttpServletResponse response, String category, String modsub, String id, String relId, String productId) {
		if(StringUtils.isNotEmpty(category) && StringUtils.isNotEmpty(id) && StringUtils.isNotEmpty(relId)){
			DfFormTpl dfTpl = new DfFormTpl();
			dfTpl.setModel(category);
			dfTpl.setRelId(relId);
			dfTpl.setDelFlag("0");
			DfFormTpl dfFormTpl = dfFormTplService.getByModelRelId(dfTpl);
			if(dfFormTpl != null){
				dfFormTplService.deleteWL(dfFormTpl);
			}
			DfFormTpl newDfFormTpl = dfFormTplService.get(id);
			if(newDfFormTpl == null){
				return false;
			}
			DfFormTpl dffTpl = new DfFormTpl();
			dffTpl.setParent(newDfFormTpl);
			dffTpl.setJson(newDfFormTpl.getJson());
			dffTpl.setOffice(newDfFormTpl.getOffice());
			dffTpl.setOriginalhtml(newDfFormTpl.getOriginalhtml());
			dffTpl.setParsehtml(newDfFormTpl.getParsehtml());
//			dffTpl.setRemarks(newDfFormTpl.getSname());
			dffTpl.setSname(newDfFormTpl.getSname());
			dffTpl.setType(newDfFormTpl.getType());
			User curUser = UserUtils.getUser();
			dffTpl.setOrganId(curUser.getCompany().getId());
			dffTpl.setUpdateBy(curUser);
			dffTpl.setCreateBy(curUser);

			dffTpl.setRelId(relId);
			dffTpl.setProduct(new TProduct(productId));
			dffTpl.setModel(category);
			dffTpl.setModsub(modsub);
			
			dffTpl.setName(null);
			dffTpl.setRemarks(null);
			
			request.setAttribute("productId", productId);
			dfFormTplService.save(dffTpl);
			return true;
		}
		return false;
	}
}

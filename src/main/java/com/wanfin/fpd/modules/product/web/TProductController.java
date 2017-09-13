/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.product.web;

import java.util.ArrayList;
import java.util.Date;
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.mangofactory.swagger.annotations.ApiIgnore;
import com.wanfin.fpd.common.config.Cons;
import com.wanfin.fpd.common.config.Cons.FModel;
import com.wanfin.fpd.common.config.Global;
import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.utils.DateUtils;
import com.wanfin.fpd.common.utils.StringUtils;
import com.wanfin.fpd.common.web.BaseController;
import com.wanfin.fpd.modules.dfform.model.Form;
import com.wanfin.fpd.modules.form.entity.tpl.DfFormTpl;
import com.wanfin.fpd.modules.form.service.tpl.DfFormTplService;
import com.wanfin.fpd.modules.product.entity.ProductBizCfg;
import com.wanfin.fpd.modules.product.entity.TProduct;
import com.wanfin.fpd.modules.product.service.ProductBizCfgService;
import com.wanfin.fpd.modules.product.service.TProductService;
import com.wanfin.fpd.modules.product.service.WProductService;
import com.wanfin.fpd.modules.product.vo.WProduct;
import com.wanfin.fpd.modules.productbiz.entity.TProductBiz;
import com.wanfin.fpd.modules.productbiz.service.TProductBizService;
import com.wanfin.fpd.modules.productbiz.vo.ProductBizVo;
import com.wanfin.fpd.modules.sys.entity.Menu;
import com.wanfin.fpd.modules.sys.entity.Office;
import com.wanfin.fpd.modules.sys.entity.Role;
import com.wanfin.fpd.modules.sys.entity.User;
import com.wanfin.fpd.modules.sys.service.OfficeService;
import com.wanfin.fpd.modules.sys.service.SystemService;
import com.wanfin.fpd.modules.sys.utils.UserUtils;
import com.wanfin.fpd.modules.wind.entity.TWindControl;
import com.wanfin.fpd.modules.wind.service.TWindControlService;
import com.wanfin.fpd.modules.wind.vo.TWindControlVo;
import com.wanfin.fpd.modules.windcfg.entity.TWindControlCfg;
import com.wanfin.fpd.modules.windcfg.service.TWindControlCfgService;

/**
 * 产品管理Controller
 * @author lx
 * @version 2016-03-23	
 */
@ApiIgnore
@Controller
@RequestMapping(value = "${adminPath}/product/tProduct")
public class TProductController extends BaseController {

		@Autowired
		private WProductService wProductService;
		@Autowired
		private TProductService tProductService;
		
		@Autowired
		private TProductBizService tProductBizService;
		
		@Autowired
		private ProductBizCfgService productBizCfgService;
		
		@Autowired
		private DfFormTplService dfFormTplService;
		
		@Autowired
		private SystemService systemService;
		
		@Autowired
		private TWindControlService tWindControlService;
		
		@Autowired
		private TWindControlCfgService tWindControlCfgService;
		
		@Autowired
		private OfficeService officeService;
		
		@ModelAttribute
		public TProduct get(@RequestParam(required=false) String id) {
			TProduct entity = null;
			if (StringUtils.isNotBlank(id)){
				entity = tProductService.get(id);
			}
			if (entity == null){
				entity = new TProduct();
			}
			return entity;
		}

		@RequiresPermissions("product:tProduct:view")
		@RequestMapping(value = {"list", ""})
		public String list(TProduct tProduct, HttpServletRequest request, HttpServletResponse response, Model model) {
			Page<TProduct> page = tProductService.findPage(new Page<TProduct>(request, response), tProduct); 
			model.addAttribute("page", page);
			model.addAttribute("tProduct", tProduct);
			return "modules/product/tProductList";
		}

		/**
		 * 获取机构产品JSON数据。
		 * @param organId 机构的ID
		 * @param response
		 * @return
		 */
		//@RequiresPermissions("user")
		@ResponseBody
		@RequestMapping(value = "productTreeData")
		public List<Map<String, Object>> treeData(@RequestParam(required=false) String organId,HttpServletResponse response) {
			TProduct tProduct=new TProduct();
			List<Map<String, Object>> mapList = Lists.newArrayList();
			List<TProduct> list = tProductService.findList(tProduct);
			for (int i=0; i<list.size(); i++){
				TProduct e = list.get(i);
				//if (){
					Map<String, Object> map = Maps.newHashMap();
					map.put("id", e.getId());
				//	map.put("pId", e.getParentId());
				//	map.put("pIds", e.getParentIds());
					map.put("name", e.getName());
				//	if (type != null && "3".equals(type)){
				//		map.put("isParent", true);
				//	}
					mapList.add(map);
				//}
			}
			return mapList;
		}
		
		
		
		
		@RequiresPermissions("product:tProduct:view")
		@RequestMapping(value = "form")
		public String form(TProduct tProduct, Model model) {
		   User currentUser = UserUtils.getUser();//当前登录人
		   List<?> columnlist = tProductService.getColumnList(currentUser);
		   List<?> productbizList = tProductService.getProductbizList(tProduct, currentUser,columnlist);
		   String winds = tProductService.getWindsList(tProduct, currentUser);
		   List<TWindControl>   twindentyList  = tWindControlService.findListByids("'"+winds+"'");
		   
		   model.addAttribute("twindentyList", twindentyList);
		   //存放功能点
		   model.addAttribute("productbizList", productbizList);
			//存放额外字段,页面使用
			model.addAttribute("extendColumns",columnlist);
	        //产品对象
			model.addAttribute("tProduct", tProduct);
			DfFormTpl dfFormTpl = new DfFormTpl();
			dfFormTpl.setModel(Cons.FModel.M_PRODUCT.getKey());
			List<DfFormTpl> list = dfFormTplService.findList(dfFormTpl);
			System.out.println(list);
			model.addAttribute("list",list);
			return "modules/product/tProductForm";
		}
		
		/**
		 * @Description 产品配置界面
		 * @author zzm
		 * @date 2016-5-5 下午4:26:30  
		 */
		@RequiresPermissions("product:tProduct:view")
		@RequestMapping(value = "productConfigIndex")
		public String productConfigIndex(RedirectAttributes redirectAttributes, TProduct tProduct, Model model) {
			if((tProduct != null) && StringUtils.isNotEmpty(tProduct.getId())){
				tProduct = tProductService.get(tProduct.getId());
				DfFormTpl tempDfFormTpl = new DfFormTpl();
				tempDfFormTpl.setModel(Cons.FModel.M_PRODUCT.getKey());
				tempDfFormTpl.setRelId(tProduct.getId());
				tempDfFormTpl.setParent(null);
				DfFormTpl dfFormTpl = dfFormTplService.getByModelRelId(tempDfFormTpl);
				if(dfFormTpl == null){
					addMessage(redirectAttributes, "产品模板未定义");
					return "redirect:"+Global.getAdminPath()+"/product/tProduct/?repage";
				}else{
					model.addAttribute("dfFormTpl", dfFormTpl);
					FModel fm = FModel.getFModelByKey(dfFormTpl.getModel());
					model.addAttribute("action", fm.getAction());
				}
				model.addAttribute("tProduct", tProduct);
				return "modules/product/productConfigIndex";
			}
			addMessage(redirectAttributes, "产品不存在！");
			return "redirect:"+Global.getAdminPath()+"/product/tProduct/?repage";
		}
		
		/**
		 * @Description 业务配置
		 */
		@RequiresPermissions("product:tProduct:view")
		@RequestMapping(value = "productConfigYWPZ")
		public String productConfigYWPZ(TProduct tProduct, Model model) {
			TProductBiz tempTProductBiz = new TProductBiz();
			//公司类型 1：担保 2：贷款
			String ywType = officeService.get(UserUtils.getUser().getOffice().getId()).getPrimaryPerson();
			tempTProductBiz.setType(Cons.TProductBizType.TPBIZ_SQ.getKey());
			List<TProductBiz> sqTProductBizs = tProductBizService.findList(tempTProductBiz);
			tempTProductBiz.setType(Cons.TProductBizType.TPBIZ_SZ.getKey());
			List<TProductBiz> szTProductBizs = tProductBizService.findList(tempTProductBiz);
			tempTProductBiz.setType(Cons.TProductBizType.TPBIZ_SH.getKey());
			List<TProductBiz> shTProductBizs = tProductBizService.findList(tempTProductBiz);
			
			if((tProduct != null) && (StringUtils.isNotEmpty(tProduct.getId()))){
				ProductBizVo tempProductBizVo = new ProductBizVo();
				tempProductBizVo.setProductId(tProduct.getId());
				tempProductBizVo.setType(Cons.TProductBizType.TPBIZ_SQ.getKey());
				List<ProductBizVo> ckdqList = tProductBizService.findByBizCfg(tempProductBizVo);
				tempProductBizVo.setType(Cons.TProductBizType.TPBIZ_SZ.getKey());
				List<ProductBizVo> ckdzList = tProductBizService.findByBizCfg(tempProductBizVo);
				tempProductBizVo.setType(Cons.TProductBizType.TPBIZ_SH.getKey());
				List<ProductBizVo> ckdhList = tProductBizService.findByBizCfg(tempProductBizVo);

				model.addAttribute("ckdqList", ckdqList);
				model.addAttribute("ckdzList", ckdzList);
				model.addAttribute("ckdhList", ckdhList);
			}
			
			List<TProductBiz> dqList = Lists.newArrayList();
			List<TProductBiz> dzList = Lists.newArrayList();
			List<TProductBiz> dhList = Lists.newArrayList();
			TProductBiz.sortList(dqList, sqTProductBizs, TProductBiz.getRootId(), true ,ywType);
			TProductBiz.sortList(dzList, szTProductBizs, TProductBiz.getRootId(), true ,ywType);
			TProductBiz.sortList(dhList, shTProductBizs, TProductBiz.getRootId(), true ,ywType);
			model.addAttribute("dqList", dqList);
			model.addAttribute("dzList", dzList);
			model.addAttribute("dhList", dhList);
			
			
			model.addAttribute("tProduct", tProduct);
			return "modules/product/productConfigYWPZ";
		}

		/**
		 * @Description 业务配置：产品——业务功能配置第二步
		 */
		@RequiresPermissions("product:tProduct:view")
		@RequestMapping(value = "saveProductConfigYWPZ")
		public String saveProductConfigYWPZ(TProduct tProduct, Model model,HttpServletRequest request, String[] ids, String[] dqIds, String[] dzIds, String[] dhIds, String modelType) {
			User currentUser = UserUtils.getUser();//当前登录人
			ProductBizCfg pbCfg = new ProductBizCfg();
			pbCfg.setOrganId(currentUser.getCompany().getId());
			pbCfg.setProduct(tProduct);
			productBizCfgService.deletePLByOrgAndProduct(pbCfg);
			if(dqIds != null){
				for (int i = 0; i < dqIds.length; i++) {
					ProductBizCfg productBizCfg = new ProductBizCfg();
					productBizCfg.setBiz(new TProductBiz(dqIds[i]));
					productBizCfg.setProduct(tProduct);
					productBizCfgService.save(productBizCfg);
				}
			}
			if(dzIds != null){
				for (int i = 0; i < dzIds.length; i++) {
					ProductBizCfg productBizCfg = new ProductBizCfg();
					productBizCfg.setBiz(new TProductBiz(dzIds[i]));
					productBizCfg.setProduct(tProduct);
					productBizCfgService.save(productBizCfg);
				}
			}
			if(dhIds != null){
				for (int i = 0; i < dhIds.length; i++) {
					ProductBizCfg productBizCfg = new ProductBizCfg();
					productBizCfg.setBiz(new TProductBiz(dhIds[i]));
					productBizCfg.setProduct(tProduct);
					productBizCfgService.save(productBizCfg);
				}
			}
			
			//清除旧的产品菜单
			systemService.deleteMenusBySysCode(tProduct.getId());
			//生成新的产品菜单
			String businessRootId = "4973a2e8a4e645c8bee88f1fe89286d7";//业务中心 id
			

			List<String> menuIdsList = tProductBizService.getMenuIdsListByBizIds(StringUtils.listToSql(ids));//配置的业务节点包含的产品菜单
//			List<String> menuIdsList = tProductBizService.getMenuIdsListByBizListIds(StringUtils.listToSqlList(ids));//配置的业务节点包含的产品菜单
			tProductService.saveProdctMeans(businessRootId, null, StringUtils.join(menuIdsList, ","), tProduct.getId());			
			UserUtils.removeCache(UserUtils.CACHE_MODELMENU_ALL_LIST);//班车菜单之后得去除缓存，不然下一个产品无法配置菜单
			
			
			//=============新加东西     产品添加时的菜单加入角色菜单表           start==========20160922       lingxing=========//
			List<String> sysCodeMenuList = tProductBizService.findAllByMenuSysCode(tProduct.getId());
			if(sysCodeMenuList.size()>0){
				for (int i = 0; i < currentUser.getRoleIdList().size(); i++) {//假如客户多个角色，进入for循环。
					String roleid = currentUser.getRoleIdList().get(i);
					systemService.insertInitRoleMenu(roleid, sysCodeMenuList);
				}
			}
			//=============新加东西            end===================//
			
			model.addAttribute("tProduct",tProduct);
			return checkModelType(modelType, tProduct, "redirect:"+Global.getAdminPath()+"/product/tProduct/productConfigYWPZ");
		}
		
		/**
		 * @Description 业务流程
		 */
		@RequiresPermissions("product:tProduct:view")
		@RequestMapping(value = "productConfigYWLC")
		public String productConfigYWLC(TProduct tProduct, Model model) {
			if((tProduct != null) && (StringUtils.isNotEmpty(tProduct.getId()))){
				productConfig(tProduct, model);
				model.addAttribute("tProduct", tProduct);
				return "modules/product/productConfigYWLC";
			}
			return "modules/product/productConfigYWPZ";
		}
		
		/**
		 * @Description 产品配置
		 * @param tProduct
		 * @param model
		 * @author Chenh 
		 * @date 2016年5月26日 上午10:37:48
		 */
		private void productConfig(TProduct tProduct, Model model) {
			pconfigYWLC(tProduct, model);
			pconfigFKPZ(tProduct, model);
			model.addAttribute("actList", tProductService.getActReProcdef());//审批流程集合
		}
		
		/**
		 * @Description 业务流程配置
		 * @param tProduct
		 * @param model
		 * @author Chenh 
		 * @date 2016年5月26日 上午10:37:48
		 */
		private void pconfigYWLC(TProduct tProduct, Model model) {
			ProductBizVo tempProductBizVo = new ProductBizVo();
			tempProductBizVo.setProductId(tProduct.getId());
			tempProductBizVo.setType(Cons.TProductBizType.TPBIZ_SQ.getKey());
			List<ProductBizVo> sqTProductBizs = tProductBizService.findByBizCfg(tempProductBizVo);
			tempProductBizVo.setType(Cons.TProductBizType.TPBIZ_SZ.getKey());
			List<ProductBizVo> szTProductBizs = tProductBizService.findByBizCfg(tempProductBizVo);
			tempProductBizVo.setType(Cons.TProductBizType.TPBIZ_SH.getKey());
			List<ProductBizVo> shTProductBizs = tProductBizService.findByBizCfg(tempProductBizVo);
			List<ProductBizVo> dqList = Lists.newArrayList();
			List<ProductBizVo> dzList = Lists.newArrayList();
			List<ProductBizVo> dhList = Lists.newArrayList();
			ProductBizVo.sortList2(dqList, sqTProductBizs, TProductBiz.getRootId(), true);
			ProductBizVo.sortList2(dzList, szTProductBizs, TProductBiz.getRootId(), true);
			ProductBizVo.sortList2(dhList, shTProductBizs, TProductBiz.getRootId(), true);
			model.addAttribute("dqList", dqList);
			model.addAttribute("dzList", dzList);
			model.addAttribute("dhList", dhList);

			model.addAttribute("tProduct", tProduct);
			List<DfFormTpl> dfFormTpls = dfFormTplService.getByProductHasRelId(tProduct.getId());
			model.addAttribute("dfFormTpls", dfFormTpls);
		}

		/**
		 * @Description 风控配置
		 * @param tProduct
		 * @param model
		 * @author Chenh 
		 * @date 2016年5月26日 上午10:37:48
		 */
		private List<TWindControlCfg> pconfigFKPZ(TProduct tProduct, Model model) {
			User currentUser = UserUtils.getUser();//当前登录人
			TWindControlCfg pWindControlCfg = new TWindControlCfg();
			pWindControlCfg.setProduct(new TProduct(tProduct.getId()));
			pWindControlCfg.setOrganId(currentUser.getOffice().getId()); 
			List<TWindControlCfg> cktwindControls = tWindControlCfgService.findList(pWindControlCfg);
			List<TWindControl> twindControls = tWindControlService.findOrganList();
			
			List<TWindControlVo> windVos = new ArrayList<TWindControlVo>();
			for (TWindControl wind : twindControls) {
				TWindControlVo windVo = new TWindControlVo(wind);
				for (TWindControlCfg windCfg : cktwindControls) {
					if((wind.getId()).equals(windCfg.getWind().getId())){
						windVo.setChecked(true);
						break;
					}
				}
				windVos.add(windVo);
			}
			
			model.addAttribute("winds",windVos);
			
			return cktwindControls;
		}
		
		/**
		 * @Description SPPZ配置
		 * @param tProduct
		 * @param model
		 * @author Chenh 
		 * @date 2016年5月26日 上午10:37:48
		 */
		private void pconfigFKPZView(TProduct tProduct, Model model, List<TWindControlCfg> twindCfgs) {
			if(twindCfgs == null){
				User currentUser = UserUtils.getUser();//当前登录人
				TWindControlCfg pWindControlCfg = new TWindControlCfg();
				pWindControlCfg.setProduct(new TProduct(tProduct.getId()));
				pWindControlCfg.setOrganId(currentUser.getOffice().getId()); 
				twindCfgs =  tWindControlCfgService.findList(pWindControlCfg);
			}
			
			List<TWindControlVo> windVos = new ArrayList<TWindControlVo>();
			for (TWindControlCfg windCfg : twindCfgs) {
				TWindControlVo windVo = new TWindControlVo(windCfg.getWind());
				windVo.setChecked(true);
				windVos.add(windVo);
			}
			model.addAttribute("windVos",windVos);
		}
		
		/**
		 * @Description 权限配置
		 * @param tProduct
		 * @param role
		 * @return
		 * @author zzm 
		 * @date 2016-5-9 下午5:46:37  
		 */
		@RequiresPermissions("product:tProduct:edit")
		@RequestMapping(value = "productConfigQXPZ")
		public String productConfigQXPZ(TProduct tProduct, Model model, Role role) {
			TProduct ptProduct = new TProduct();
			ptProduct.setId(tProduct.getId());
			List<Menu> menus = systemService.getMenuListByProduct(ptProduct);
			StringBuffer productMenus = new StringBuffer();
			for (Menu menu : menus) {
				productMenus.append(menu.getRelId());
				productMenus.append(",");
			}
			model.addAttribute("productMenus", StringUtils.removeEnd(productMenus.toString(), ","));
			role.setMenuIds(StringUtils.removeEnd(productMenus.toString(), ","));
			model.addAttribute("menuList", systemService.findBusinessMenu());
			model.addAttribute("role", role);
			model.addAttribute("tProduct", tProduct);
			return "modules/product/productConfigQXPZ";
		}
		
		/**
		 * @Description 菜单配置
		 * @param tProduct
		 * @param role
		 * @return
		 * @author zzm 
		 * @date 2016-5-9 下午5:46:37  
		 */
		@RequiresPermissions("product:tProduct:edit")
		@RequestMapping(value = "productConfigCDPZ")
		public String productConfigCDPZ(TProduct tProduct, Model model, Role role) {
			List<Menu> list = Lists.newArrayList();
			List<Menu> sourcelist = systemService.getMenuListByProduct(tProduct);
			String ywId = "4973a2e8a4e645c8bee88f1fe89286d7";
			Menu.sortList(list, sourcelist, ywId, true);
//			Menu.sortList(list, sourcelist, Menu.getRootId(), true);
	        model.addAttribute("list", list);
			model.addAttribute("tProduct", tProduct);
			return "modules/product/productConfigCDPZ";
		}
		
		/**
		 * @Description 业务流程配置保存
		 * @param tProduct
		 * @return
		 * @author zzm 
		 * @date 2016-5-9 下午5:46:37  
		 */
		@RequiresPermissions("product:tProduct:edit")
		@RequestMapping(value = "saveBizCfgs")
		public String saveBizCfgs(TProduct tProduct, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request, String modelType) {
			if (!beanValidator(model, tProduct)){
				return form(tProduct, model);
			}
			
			tProductService.saveBizCfgs(tProduct);
			addMessage(redirectAttributes, "业务流程配置成功");
			return checkModelType(modelType, tProduct, "redirect:"+Global.getAdminPath()+"/product/tProduct/?repage");
		}
		
		/**
		 * @Description 风控配置
		 */
		@RequiresPermissions("product:tProduct:view")
		@RequestMapping(value = "productConfigFKPZ")
		public String productConfigFKPZ(TProduct tProduct, Model model) {
			if((tProduct != null) && (StringUtils.isNotEmpty(tProduct.getId()))){
				pconfigYWLC(tProduct, model);
				pconfigFKPZ(tProduct, model);
				model.addAttribute("tProduct", tProduct);
				return "modules/product/productConfigFKPZ";
			}
			return "modules/product/productConfigYWLC";
		}
		
		@RequiresPermissions("product:tProduct:view")
		@RequestMapping(value = "saveProductConfigFKPZ")
		public String saveProductConfigFKPZ(TProduct tProduct, Model model, String modelType) {
			if((tProduct != null) && (StringUtils.isNotEmpty(tProduct.getId()))){
				model.addAttribute("tProduct", tProduct);
				return checkModelType(modelType, tProduct, null);
			}
			return "modules/product/productConfigYWLC";
		}
		
		/**
		 * @Description 审批配置
		 */
		@RequiresPermissions("product:tProduct:view")
		@RequestMapping(value = "productConfigSPPZ")
		public String productConfigSPPZ(TProduct tProduct, Model model) {
			if((tProduct != null) && (StringUtils.isNotEmpty(tProduct.getId()))){
				List<TWindControlCfg> cktwindControls = pconfigFKPZ(tProduct, model);
				pconfigFKPZView(tProduct, model, cktwindControls);
				productConfig(tProduct, model);
				return "modules/product/productConfigSPPZ";
			}
			return "modules/product/productConfigFKPZ";
		}

		
		
		@RequiresPermissions("product:tProduct:edit")
		@RequestMapping(value = "save")
		public String save(TProduct tProduct, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request, String modelType) {
			User currentUser = UserUtils.getUser();//当前登录人
			if (!beanValidator(model, tProduct)){
				return form(tProduct, model);
			}
			tProductService.saveAll(currentUser,tProduct,request);
			addMessage(redirectAttributes, "保存产品成功");
			return checkModelType(modelType, tProduct, "redirect:"+Global.getAdminPath()+"/product/tProduct/?repage");
		}
		
//		/**
//		* @Description:  更新微服务产品表 
//		* @author chenJianpeng
//		* @param entity  
//		* @throws
//		 */
//		public void addProduct(TProduct entity, boolean isNew) {
//			if (entity != null) {
//				User user = UserUtils.getUser();
//				if(user != null){
//					ProProductVo vo = new ProProductVo(entity, user);
////					net.sf.json.JSONObject pjson = net.sf.json.JSONObject.fromObject(new ProProductVo(entity, user));
//					System.out.println("-----------------------------微服务调用开始-----------------------------");
////					System.out.println(pjson.toString());
//					if(!isNew){
//						System.out.println("-----------------------------新增-----------------------------");
//						try {
//							System.out.println(Cons.Ips.WF_PRODUCT_SAVE);
//							
//							 
//
//					    	Entity e = Entity.entity(vo, MediaType.APPLICATION_JSON_TYPE);
//					    	InterfaceUtil.sendPostClient(Cons.Ips.WF_PRODUCT_SAVE, e);
//
//					    	String iii = InterfaceUtil.sendPostJson("http://192.168.1.184:8088/services/pro/psave", "{\"product\":{\"name\":\"陈建鹏\",\"id\":\"142f7468b89b45a88c4851bad1b464b0\",\"type\":\"1\",\"status\":\"new\"},\"user\":{\"organId\":null,\"id\":\"7bdf4d22419147c48cd95a4c5f851e43\"}}");
//					    	System.out.println(iii); 
//					    	
////							String str = InterfaceUtil.sendPostJson("http://192.168.1.184:8088/services/employee/getByEntity", "{\"product\":{\"name\":\"陈建鹏\",\"id\":\"142f7468b89b45a88c4851bad1b464b0\",\"type\":\"1\",\"status\":\"new\"},\"user\":{\"organId\":null,\"id\":\"7bdf4d22419147c48cd95a4c5f851e43\"}}");
////							String str = InterfaceUtil.sendPostJson("http://192.168.1.184:8088/services/employee/getByEntity", "{\"id\":\"09b12a4618e64fb6a27c6fa6b900f549\"}");
////							System.out.println(str);
////							InterfaceUtil.sendPostJson(Cons.Ips.WF_PRODUCT_SAVE, pjson.toString());
//						} catch (Exception e) {
//							System.out.println("-----------------------------微服务-----------------------------");
//							System.out.println("微服务产品新增执行失败！ 地址："+Cons.Ips.WF_PRODUCT_SAVE+"请完善数据！");
//							System.out.println("-----------------------------微服务-----------------------------");
//						}
//					}else{
//						System.out.println("-----------------------------修改-----------------------------");
//						try {
//							System.out.println(Cons.Ips.WF_PRODUCT_UPDATE);
//							
//							Entity e = Entity.entity(vo, MediaType.APPLICATION_JSON);
//					    	InterfaceUtil.sendPostClient(Cons.Ips.WF_PRODUCT_SAVE, e);
////							InterfaceUtil.sendPostJson(Cons.Ips.WF_PRODUCT_UPDATE, pjson.toString());
//						} catch (Exception e) {
//							System.out.println("-----------------------------微服务-----------------------------");
//							System.out.println("微服务产品更新状态执行失败！ 地址："+Cons.Ips.WF_PRODUCT_UPDATE+"请完善数据！");
//							System.out.println("-----------------------------微服务-----------------------------");
//						}
//					}
//					System.out.println("-----------------------------微服务调用结束-----------------------------");
//				}else{
//					System.out.println("-----------------------------当前用户获取失败-----------------------------");
//				}
//			}
//		}
		
		@RequiresPermissions("product:tProduct:edit")
		@RequestMapping(value = "published")
		public String published(TProduct tProduct, Model model, RedirectAttributes redirectAttributes,HttpServletRequest request, String modelType) {
			if(tProduct != null){
				TProduct newTProduct = tProductService.get(tProduct.getId());
				if(StringUtils.isNotEmpty(tProduct.getStatus())){
					newTProduct.setStatus(tProduct.getStatus());
					tProductService.save(newTProduct);
					if((tProduct.getStatus()).equals(Cons.ProductStatus.PUBLISHED)){
						addMessage(redirectAttributes, "产品["+tProduct.getName()+"] 发布成功");
					}else if((tProduct.getStatus()).equals(Cons.ProductStatus.NEW)){
						addMessage(redirectAttributes, "产品["+tProduct.getName()+"] 取消发布成功");
					}
					return checkModelType(modelType, tProduct, "redirect:"+Global.getAdminPath()+"/product/tProduct/?repage");
				}
			}
			addMessage(redirectAttributes, "产品发布失败");
			return checkModelType(modelType, tProduct, "redirect:"+Global.getAdminPath()+"/product/tProduct/?repage");
		}
		
		/**
		 * @Description 根据modelType跳转新页面
		 * @param modelType
		 * @return
		 * @author Chenh 
		 * @date 2016年5月9日 下午2:42:40
		 */
		private String checkModelType(String modelType, TProduct tProduct, String dfUrl) {
			if (StringUtils.isNotEmpty(modelType)) {
				if((modelType).equals("step1")){
					return "redirect:"+Global.getAdminPath()+"/product/tProduct/productConfigIndex?id="+tProduct.getId();
				}else if((modelType).equals("step2")){
					return "redirect:"+Global.getAdminPath()+"/product/tProduct/productConfigYWPZ?id="+tProduct.getId();
				}else if((modelType).equals("step3")){
					return "redirect:"+Global.getAdminPath()+"/product/tProduct/productConfigYWLC?id="+tProduct.getId();
				}else if((modelType).equals("step4")){
					return "redirect:"+Global.getAdminPath()+"/product/tProduct/productConfigQXPZ?id="+tProduct.getId();
				}else if((modelType).equals("step5")){
					return "redirect:"+Global.getAdminPath()+"/product/tProduct/productConfigCDPZ?id="+tProduct.getId();
				}else if((modelType).equals("step6")){
					return "redirect:"+Global.getAdminPath()+"/product/tProduct/productConfigFKPZ?id="+tProduct.getId();
				}else if((modelType).equals("step7")){
					return "redirect:"+Global.getAdminPath()+"/product/tProduct/productConfigSPPZ?id="+tProduct.getId();
				}else if((modelType).equals("step8")){
					return "redirect:"+Global.getAdminPath()+"/product/tProduct/?repage";
				}else{
					return "redirect:"+Global.getAdminPath()+"/product/tProduct/?repage";
				}
			}
			return dfUrl;
		}
	
		@RequiresPermissions("product:tProduct:edit")
		@RequestMapping(value = "delete")
		public String delete(TProduct tProduct, RedirectAttributes redirectAttributes) {
			tProductService.delete(tProduct);
			addMessage(redirectAttributes, "删除产品成功");
			return "redirect:"+Global.getAdminPath()+"/product/tProduct/?repage";
		}
		
		@ResponseBody
		@RequestMapping(value = "getProduct")
		public TProduct getProduct(String id, RedirectAttributes redirectAttributes) {
			return  get(id);
		}

		//产品——业务功能配置第一步
		@RequiresPermissions("product:tProduct:edit")
		@RequestMapping(value = "depLoy")
		public String depLoy(TProduct tProduct, Model model) {
		User currentUser = UserUtils.getUser();//当前登录人
		model.addAttribute("tProduct",get(tProduct.getId()));
		//业务功能点集合
		List<TProductBiz> productbizList = tProductService.getAllProductbizList(currentUser);
		model.addAttribute("productbizList", productbizList);
		return "modules/product/newProductConfig";
		}
		
		@ResponseBody
		@RequestMapping("/ajaxProduct")
		public List<TProductBiz> ajaxProductBizList(String productId,Model model){
			User currentUser = UserUtils.getUser();//当前登录人
			model.addAttribute("tProduct",get(productId));			
			//业务功能点集合 
			System.out.println(currentUser.getOffice().getParent().getId());
			List<TProductBiz> productbizList = tProductService.getAllProductbizList(currentUser);			
			return productbizList;
		}
	
		//产品——业务功能配置第二步
		@RequiresPermissions("product:tProduct:edit")
		@RequestMapping(value = "depLoysecount")
		public String depLoysecount(TProduct tProduct, Model model,HttpServletRequest request, String modelType) {
			User currentUser = UserUtils.getUser();//当前登录人
			
			String ids =tProductService.getAllSelectProductbizIds(request);
			tProduct = get(tProduct.getId());
			model.addAttribute("tProduct",tProduct);
			model.addAttribute("productbizList", tProductService.getAllproductbizListInIds(currentUser,request,ids));//业务功能点集合
			model.addAttribute("actList", tProductService.getActReProcdef());//审批流程集合
			model.addAttribute("biz_id",ids);//业务功能点id
			model.addAttribute("risks",tWindControlService.findList(new TWindControl()));//业务功能点id
			
			return "modules/product/productConfigSecond";
		}
		
		
		//产品——业务功能配置-保存
	    @RequiresPermissions("product:tProduct:edit")
	    @RequestMapping(value = "savedepLoy")
		public String savedepLoy(TProduct tProduct, Model model,HttpServletRequest request) {
		TProduct new_tProduct = get(tProduct.getId());
		String productBizId = request.getParameter("bizId").replaceAll("'","");
		String bizName      = request.getParameter("bizName");
		String actid        = request.getParameter("act_Id");
		String winds        =      request.getParameter("wind_ids");
		
		
		Form.dao.savedepLoy(new_tProduct.getId(),productBizId,actid,bizName,winds);
		return "redirect:"+Global.getAdminPath()+"/product/tProduct/?repage";
		}
	 
	    @RequestMapping(value = "addColumn")
		public void addColumn(HttpServletRequest request,HttpServletResponse response){
			try {
				User currentUser = UserUtils.getUser();//当前登录人
				String chineseName = request.getParameter("chineseName");
				String tableName =request.getParameter("tableName");
				//先查询看是否存在这个字段
				List<?> list = tProductService.findIsexist(currentUser,chineseName,tableName);
				if(list!=null && list.size()>0){
					super.renderString(response, "exist");//存在相同的字段
				}else{
					tProductService.addColumn(chineseName,tableName,currentUser);
				    List<?> columnlist = tProductService.getColumnList(currentUser);
				    super.renderString(response, columnlist);
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
	    
	    
	    @RequestMapping(value = "findalllist")
		public List<TProduct> findalllist() {
			List<TProduct> productList = tProductService.findAllList();
			return productList;
		}
	    
	    @RequiresPermissions("product:tProduct:view")
		@RequestMapping(value = "test")
		public String test(TProduct tProduct,Role role, Model model) {
			model.addAttribute("menuList", systemService.findBusinessMenu());
			model.addAttribute("role", role);
			model.addAttribute("tProduct", tProduct);
			return "modules/product/test";
		}
	    
	    @RequiresPermissions("product:tProduct:view")
		@RequestMapping(value = "savebuness")
		public String savebuness(TProduct tProduct,Role role, Model model, String modelType, String tProductId) {
	    	if(StringUtils.isNotEmpty(tProductId)){
	    		tProduct.setId(tProductId);
	    	}
	    	if((tProduct != null) && (StringUtils.isNotEmpty(tProduct.getId()))){
				String meanids = role.getMenuIds();
				if(StringUtils.isNotEmpty(meanids)){
					String[] str_ = meanids.split(",");
					String topMeanid = str_[0];
					List<Menu> menus = systemService.getMenuListByProduct(tProduct);
					if((menus != null) && (menus.size() > 0)){
						systemService.deletePLWL(menus);
					}
					tProductService.saveProdctMeans(topMeanid, null, meanids, tProduct.getId());
				}
	    	}
			return checkModelType(modelType, tProduct, "redirect:"+Global.getAdminPath()+"/product/tProduct/?repage");
		}
	    
	    /**
	     * @Description 获取业务节点配置 
	     * @param bizCode
	     * @param sysCode
	     * @return
	     * @author zzm 
	     * @date 2016-5-25 下午2:14:54  
	     */
	    @ResponseBody
	    @RequestMapping(value = "getBizCfgByBizCode")
		public ProductBizCfg getBizCfgByBizCode(String bizCode, Model model,HttpServletRequest request) {
	    	ProductBizCfg productBizCfg = productBizCfgService.getByBizCode(bizCode);
	    	return productBizCfg;
		}
	    
	    
	    
	    /**
	     * 检查产品期限
	     * @param bizCode
	     * @param model
	     * @param request
	     * @return
	     */
	    @ResponseBody
	    @RequestMapping(value = "toCheckProductPeriod")
		public String toCheckProductPeriod(String bizCode, Model model,HttpServletRequest request){
	    	String productId = (String) UserUtils.getCache(UserUtils.CACHE_SYSCODE);
	    	TProduct product = tProductService.get(productId);
	    	   String overdueDays = "";
	    	if(product.getPeriod()!=null&&!"".equals(product.getPeriod())){//算出天数，如果为负数，说明过了期限
	    		double overday = DateUtils.getDistanceOfTwoDate(new Date(),product.getPeriod());
	    		overdueDays = String.valueOf(overday).substring(0,String.valueOf(overday).indexOf("."));
	    	}
	    	return overdueDays;
		}
	    
	    
		/**
		 * @Description 获取产品统计分析数据
		 * @param tProduct
		 * @return
		 * @author zzm
		 * @date 2016-6-22 下午3:22:46  
		 */
		@RequiresPermissions("user")
		@RequestMapping(value = "statisticAnalysis")
		public String statisticAnalysis(TProduct tProduct, HttpServletRequest request, HttpServletResponse response, Model model) {
			tProduct.setStatus(Cons.ProductStatus.PUBLISHED);
			Page<TProduct> page = tProductService.findPage(new Page<TProduct>(request, response, 10), tProduct); 
			List<Record> list = Lists.newArrayList();
			List<JSONObject> listJson = Lists.newArrayList();
			if(page.getList() != null){
				for(TProduct p : page.getList()){
					//累计发生金额，最高金额，最低金额， 每笔业务平均金额，
					String sql = "SELECT ROUND(SUM(c.loan_amount),2) AS sumAmount,"+
							" ROUND(MAX(c.loan_amount),2) AS maxAmount,"+
							" ROUND(MIN(c.loan_amount),2) AS minAmount,"+
							" ROUND(AVG(c.loan_amount),2) AS avgAmount,"+
							" COUNT(c.id) AS count"+
							" FROM t_loan_contract c WHERE c.del_flag = '0'"+
							" AND c.status not in(?) AND c.product_id = ?" +
							" GROUP BY c.product_id";
					//放款之前的申请不统计  
					String cStatus="'"+Cons.LoanContractStatus.TO_REVIEW+
							"','"+Cons.LoanContractStatus.TO_APPROVE+
							"','"+Cons.LoanContractStatus.TO_SIGN+
							"','"+Cons.LoanContractStatus.TO_LOAN_APPROVAL+
							"','"+Cons.LoanContractStatus.TO_LOAN;
					Record record1 = Db.findFirst(sql,cStatus,p.getId());
					
					//已结清金额，未结清金额，逾期金额，坏账金额
					sql = "select ROUND(SUM(p.interest_real + p.principal_real),2) AS clearedAmount,"+
							" ROUND(SUM(if(c.status != ?,(p.interest - IFNULL(p.interest_real,0) + p.principal- IFNULL(p.principal_real,0)),0)),2) AS unclearedAmount,"+
							" ROUND(SUM(if(c.status = ?,(p.interest - IFNULL(p.interest_real,0) + p.principal- IFNULL(p.principal_real,0)),0)),2) AS badAmount,"+
							" ROUND(SUM(if(str_to_date(p.account_date,'%Y-%m-%d %H:%i:%s') > NOW(),(p.interest - IFNULL(p.interest_real,0) + p.principal- IFNULL(p.principal_real,0)),0)),2) AS overdueAmount,"+
							" c.product_id"+
							" from t_repay_plan p"+
							" join t_loan_contract c on c.id = p.loan_contract_id"+
							" where p.is_valid = '1' and c.status not in (?) and c.product_id = ?"+
							" GROUP BY c.product_id";
					Record record2 = Db.findFirst(sql,Cons.LoanContractStatus.ENDED,Cons.LoanContractStatus.ENDED,cStatus,p.getId());
					
					//最高利息， 最低利息，平均利息，
					sql = "SELECT SUM(t.sumInterest) AS sumInterest,"+
							" ROUND(MAX(t.sumInterest),2) AS maxInterest,"+
							" ROUND(MIN(t.sumInterest),2) AS minInterest,"+
							" ROUND(AVG(t.sumInterest),2) AS avgInterest"+
							" FROM (SELECT ROUND(SUM(p.interest),2) AS sumInterest,"+
							" p.loan_contract_id,MAX(c.product_id) AS product_id"+
							" FROM t_repay_plan p"+
							" JOIN t_loan_contract c ON c.id = p.loan_contract_id AND c.status != ?"+
							" WHERE p.del_flag = '0' AND p.is_valid = '1' and c.product_id = ? "+
							" GROUP BY p.loan_contract_id"+
							" ) t"+
							" GROUP BY t.product_id";
					Record record3 = Db.findFirst(sql,cStatus,p.getId());
					Record record = new Record();
					record.getColumns().put("name", p.getName());
					if(record1 != null)
						record.setColumns(record1);
					if(record2 != null)
						record.setColumns(record2);
					if(record3 != null)
						record.setColumns(record3);
					list.add(record);
					try {
						listJson.add(new JSONObject(record.toJson()));
					} catch (JSONException e) {
						e.printStackTrace();
						listJson.add(new JSONObject());
					}
				}
			}
			model.addAttribute("list", list);
			model.addAttribute("listJson", listJson);
			model.addAttribute("page", page);
			model.addAttribute("tProduct", tProduct);
			return "modules/product/productAnalysis";
		}


		@RequiresPermissions("product:tProduct:view")
		@RequestMapping(value = {"option"})
		public String option(HttpServletRequest request, HttpServletResponse response, Model model) {
			List<WProduct> wProducts = wProductService.findList();
			List<TProduct> tProducts = tProductService.getAllByOrganId();

			List<TProduct> tRProducts = new ArrayList<TProduct>();
			List<TProduct> tNProducts = new ArrayList<TProduct>();
			for (TProduct tProduct : tProducts) {
				if(StringUtils.isNotEmpty(tProduct.getWtypeId())){
					tRProducts.add(tProduct);
				}else{
					tNProducts.add(tProduct);
				}
			}

			List<WProduct> wRProducts = new ArrayList<WProduct>();
			List<WProduct> wNProducts = new ArrayList<WProduct>();
			for (WProduct wProduct : wProducts) {
				for (TProduct tProduct : tRProducts) {
					if((tProduct.getWtypeId()).equals(wProduct.getId())){
						wProduct.setProduct(tProduct);
						wRProducts.add(wProduct);
					}
				}
				if(wProduct.getProduct() == null){
					wNProducts.add(wProduct);
				}
			}

			model.addAttribute("tProducts", tProducts);
			model.addAttribute("tRProducts", tRProducts);
			model.addAttribute("tNProducts", tNProducts);
			model.addAttribute("wProducts", wProducts);
			model.addAttribute("wRProducts", wRProducts);
			model.addAttribute("wNProducts", wNProducts);
			return "modules/product/productOption";
		}
		
		@ResponseBody
		@RequestMapping("/ajaxOption")
		public Boolean ajaxOption(String id, String wtypeId){
			if(StringUtils.isNotEmpty(id) && StringUtils.isNotEmpty(wtypeId)){
				TProduct product = tProductService.getByWtypeId(wtypeId);
				TProduct newProduct = tProductService.get(id);
				if(newProduct != null){
					newProduct.setWtypeId(wtypeId);
					if(product != null){
						product.setWtypeId(null);
						tProductService.save(product);
					}
					tProductService.save(newProduct);
					return true;
				}
			}			
			return false;
		}
		
		@ResponseBody
		@RequestMapping("/ajaxDelOption")
		public Boolean ajaxDelOption(String id){
			if(StringUtils.isNotEmpty(id)){
				TProduct product = tProductService.get(id);
				if(product != null){
					product.setWtypeId(null);
					tProductService.save(product);
					return true;
				}
			}			
			return false;
		}
		
}
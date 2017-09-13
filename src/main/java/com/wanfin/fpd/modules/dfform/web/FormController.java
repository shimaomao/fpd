/*
 *  Copyright 2014-2015 snakerflow.com
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *     http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */
package com.wanfin.fpd.modules.dfform.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.impl.util.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.snaker.engine.helper.JsonHelper;
import org.springframework.beans.factory.annotation.Autowired;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.wanfin.fpd.common.utils.DateUtils;
import com.wanfin.fpd.modules.act.entity.Act;
import com.wanfin.fpd.modules.dfform.model.Field;
import com.wanfin.fpd.modules.dfform.model.Form;
import com.wanfin.fpd.modules.productbiz.entity.TProductBiz;
import com.wanfin.fpd.modules.productbiz.service.TProductBizService;
import com.wanfin.fpd.modules.sys.entity.User;
import com.wanfin.fpd.modules.sys.utils.PinyinUtil;
import com.wanfin.fpd.modules.sys.utils.UserUtils;

/**
 * 琛ㄥ崟绠＄悊controller
 * @author yuqs
 * @since 0.1
 */
public class FormController extends Controller {
	public static final String PARA_PROCESSID = "processId";
	public static final String PARA_ORDERID = "orderId";
	public static final String PARA_TASKID = "taskId";
	public static final String PARA_TASKNAME = "taskName";
	public static final String PARA_METHOD = "method";
	public static final String PARA_NEXTOPERATOR = "nextOperator";
	public static final String PARA_NODENAME = "nodeName";
	public static final String PARA_CCOPERATOR = "ccOperator";
	public static final String URL_ACTIVETASK = "/snaker/task/active";
	
	private static final Logger log = LoggerFactory.getLogger(FormController.class);
	
	@Autowired
	private TProductBizService tProductBizService;
	
	public void index() {
		String name = getPara("name");
		System.out.println("name :  "+name);
		setAttr("page", Form.dao.paginate(getParaToInt("pageNo", 1), 10, name));
		keepPara();
		render("formList.jsp");
	}
	
	public void add() {
		User currentUser = UserUtils.getUser();//当前登录人
		setAttr("tableName", "t_"+currentUser.getOffice().getUniqueNumber()+"_product");
		log.info("======> add");
		render("formAdd.jsp");
	}
	
	public void view() {
		log.info("======> view");
		setAttr("form", Form.dao.findById(getParaToInt()));
		System.out.println("======>  view :"+Form.dao.findById(1)+" getPara   "+getParaToInt());
		Form model = null;
		model = Form.dao.findById(getParaToInt());
		
		System.out.println(Form.dao.getDataByOrderId(model).toJson());
		render("formView.jsp");
	}
	
	public void edit() {
		log.info("======> edit");
		setAttr("form", Form.dao.findById(getParaToInt()));
		render("formEdit.jsp");
	}
	
	public void designer() {
		log.info("======> designer");
		setAttr("form", Form.dao.findById(getParaToInt()));
		render("formDesigner.jsp");
	}
	
	@SuppressWarnings("static-access")
	public void save() {
		User currentUser = UserUtils.getUser();//当前登录人
		log.info("======> save");
		Form model = getModel(Form.class);
		String name = getPara("form.displayName");
		model.set("name", "t_"+currentUser.getOffice().getUniqueNumber()+"_"+new PinyinUtil().cn2Spell(name)+"_product");
		model.set("office_id", currentUser.getOffice().getParent().getId());
		model.set("create_by", "1");
		model.set("update_by", "1");
		model.set("create_date", DateUtils.getCurrentTime());
		model.set("update_date", DateUtils.getCurrentTime());
		model.set("creator", currentUser.getLoginName());
		model.set("createTime", DateUtils.getCurrentTime());
		model.set("fieldNum", 0);
		model.save();
		redirect("/a/dfform/form");
	}
	
	public void update() {
		log.info("======> update");
		getModel(Form.class).update();
		redirect("/a/dfform/form");
	}
	
	@SuppressWarnings("unchecked")
	public void processor() {
		log.info("======> processor");
		Form model = null;
		try {
			model = Form.dao.findById(getParaToInt("formid"));
			Map<String, Object> map = JsonHelper.fromJson(getPara("parse_form"), Map.class);
			Map<String, Object> datas = (Map<String, Object>)map.get("add_fields");
			Map<String, String> nameMap = Form.dao.process(model, datas);
			String template = (String)map.get("template");
			String parseHtml = (String)map.get("parse");
			if(!nameMap.isEmpty()) {
				for(Map.Entry<String, String> entry : nameMap.entrySet()) {
					template = template.replaceAll(entry.getKey(), entry.getValue());
					parseHtml = parseHtml.replaceAll(entry.getKey(), entry.getValue());
				}
			}
			model.set("create_by", "1");
			model.set("update_by", "1");
			model.set("create_date", DateUtils.getCurrentTime());
			model.set("update_date", DateUtils.getCurrentTime());
			model.set("originalHtml", template);
			model.set("parseHtml", parseHtml);
			model.update();
			renderJson(Boolean.TRUE);
		} catch(Exception e) {
			e.printStackTrace();
			renderJson(Boolean.FALSE);
		}
	}
	
	public void delete() {
		log.info("======> delete");
		Form.dao.deleteById(getParaToInt());
		redirect("/a/dfform/form");
	}
	
	public void use() {
		log.info("======> use");
		Form model = Form.dao.findById(getParaToInt());
		setAttr("form", model);
		keepPara();
		String orderId = getPara(PARA_ORDERID);
		String taskId = getPara(PARA_TASKID);
		if(StringUtils.isEmpty(orderId) || StringUtils.isNotEmpty(taskId)) {
			render("formUse.jsp");
		} else {
			//setAttr("result", Form.dao.getDataByOrderId(model, orderId));
			render("formUseView.jsp");
		}
	}
	
	public void formData() {
	log.info("======> formData");
	//String orderId = getPara(PARA_ORDERID);
	Form model = Form.dao.findById(getParaToInt());//获取表名
	setAttr("form", Form.dao.getDataByOrderId(model));
	List<String> fieldNames = Form.dao.getDataBy(model);
	JSONObject  json = new JSONObject(Form.dao.getDataByOrderId(model).toJson());
	System.out.println(json);
	StringBuilder htmlste = new StringBuilder();
	if(fieldNames != null && fieldNames.size() > 0) {
		for(String fieldName : fieldNames) {
			htmlste.append("<tr><td class='td_table_1'>");
			htmlste.append("<span>"+Form.dao.getDataByFiled(model,fieldName)+"：</span></td><td class='td_table_2' colspan='3'>&nbsp;"+json.getString(fieldName)+"");
			htmlste.append("</td></tr>");
		}
	}
	setAttr("form1", htmlste);
	
	List<?> peizhilist =  Db.query("select * from t_product_peizhi where form_id=?",getParaToInt());
	setAttr("peizhilist", peizhilist);

//	StringBuilder pro_bizstr = new StringBuilder();
//	if(peizhilist != null && peizhilist.size() > 0) {
//		pro_bizstr.append("<tr>");
//		pro_bizstr.append("<td class='td_table_2' style='width: 40%;'>业务功能</td><td class='td_table_2' style='width: 40%;'>流程配置</td>");
//		pro_bizstr.append("</tr>");
//		for (int i = 0; i < peizhilist.size(); i++) {
//			System.out.println(peizhilist.get(i));
//			pro_bizstr.append("<tr>");
//			pro_bizstr.append("<td class='td_table_2'>"+peizhilist.get(i)+"</td>");
//			pro_bizstr.append("<td class='td_table_2'>"+peizhilist.get(i)+"</td>");
//			pro_bizstr.append("</tr>");
//		}
//	}
//	setAttr("form2", pro_bizstr);
	render("formView_detail.jsp");
	}
	
	//产品——业务功能配置
	public void depLoy() {
	User currentUser = UserUtils.getUser();//当前登录人
	Form model = Form.dao.findById(getParaToInt());//获取表名
	setAttr("form_id",model.get("id"));//自定义表单主键id
	setAttr("form_displayName",model.get("displayName"));//自定义产品名称
	List<TProductBiz> productbizList =  Db.query("select * from t_product_biz where office_id='"+currentUser.getOffice().getParent().getId()+"'");
	setAttr("productbizList",productbizList);
	//List<Act> actList =  Db.query("select * from ACT_RE_PROCDEF where office_id='"+currentUser.getOffice().getParent().getId()+"'");
	List<Act> actList =  Db.query("select * from ACT_RE_PROCDEF");
	setAttr("actList",actList);
	render("form_product_buss.jsp");
	}
	
	
	//产品——业务功能配置-保存
	public void savedepLoy() {

	String form_id =	getPara("form_id");
	String productBizId  = getPara("bizId");
	String actid = getPara("act_Id");
	String bizName = getPara("bizName");
	String winds        =      getPara("wind_ids");
	Form.dao.savedepLoy(form_id,productBizId,actid,bizName,winds);

	redirect("/a/dfform/form");
	}
	
	
	@SuppressWarnings("unused")
	@Before(Tx.class)
	public void submit() {
		log.info("======> submit");
		String processId = getPara(PARA_PROCESSID);
		String orderId = getPara(PARA_ORDERID);
		String taskId = getPara(PARA_TASKID);
		int formId = getParaToInt("formId");
		List<Field> fields = Field.dao.find("select * from df_field where formId=?", formId);
		Map<String, Object> params = new HashMap<String, Object>();
		for(Field field : fields) {
			if(Field.FLOW.equals(field.getStr("flow"))) {
				String name = field.getStr("name");
				String type = field.getStr("type");
				String paraValue = getPara(name);
				Object value = null;
				if("text".equalsIgnoreCase(type)) {
					value = paraValue;
				} else if("int".equalsIgnoreCase(type)) {
					value = getParaToInt(name, 0);
				} else if("float".equalsIgnoreCase(type)) {
					if(paraValue == null || "".equals(paraValue)) {
						value = 0.0;
					} else {
						try {
							value = Double.parseDouble(getPara(name));
						} catch(Exception e) {
							value = 0.0;
						}
					}
				} else {
					value = paraValue;
				}
				params.put(name, value);
			}
		}

		

		Form model = Form.dao.findById(formId);
		Map<String, String[]> paraMap = getParaMap();
		Form.dao.submit(model, fields, paraMap, orderId, taskId);
		//redirect(getPara("url"));
		
			redirect("/a/dfform/form");
		
	}
}

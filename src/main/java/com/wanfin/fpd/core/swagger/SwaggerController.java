/**  
 * @Project fpd 
 * @Title SwaggerController.java
 * @Package com.wanfin.fpd.modules.swagger
 * @Description [[_xxx_]]文件 
 * @author Chenh
 * @date 2016年6月13日 下午2:02:15 
 * @version V1.0   
 */
package com.wanfin.fpd.core.swagger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mangofactory.swagger.annotations.ApiIgnore;
import com.wanfin.fpd.core.ApiSval.Gconfig;
import com.wanfin.fpd.core.Link;
import com.wanfin.fpd.modules.gen.entity.GenScheme;
import com.wanfin.fpd.modules.gen.entity.GenTable;
import com.wanfin.fpd.modules.gen.entity.GenTableColumn;
import com.wanfin.fpd.modules.gen.service.GenSchemeService;
import com.wanfin.fpd.modules.gen.service.GenTableColumnService;
import com.wanfin.fpd.modules.gen.service.GenTableService;
import com.wanfin.fpd.modules.gen.util.GenUtils;

/**
 * @Description [[_xxx_]] SwaggerController类
 * @author Chenh
 * @date 2016年6月13日 下午2:02:15
 */
@ApiIgnore
@Controller
public class SwaggerController{
	@Autowired
	private GenTableService genTableService;
	@Autowired
	private GenTableColumnService genTableColumnService;
	@Autowired
	private GenSchemeService genSchemeService;
	/**
	 * @Description Swagger 测试文档界面
	 * @return
	 * @author Chenh
	 * @date 2016年6月13日 下午3:11:02
	 */
	@RequestMapping(value = { Link.SWAGGER_PATH + "/index", Link.SWAGGER_PATH + "" })
	public String index(HttpServletRequest request, HttpServletResponse response, GenScheme entity) {
		initModes(request, entity);
		return "swagger/index";
	}

	/**
	 * @Description Swagger 接口界面
	 * @return
	 * @author Chenh
	 * @date 2016年6月13日 下午3:11:02
	 */
	@RequestMapping(value = { Link.SWAGGER_PATH + "/inter" })
	public String inter() {
		return "swagger/inter";
	}
	
	/**
	 * @Description Swagger 测试界面
	 * @return
	 * @author Chenh
	 * @date 2016年6月13日 下午3:11:02
	 */
	@RequestMapping(value = { Link.SWAGGER_PATH + "/test" })
	public String test(HttpServletRequest request, HttpServletResponse response, GenScheme entity) {
		initModes(request, entity);
		return "swagger/test";
	}

	/**
	 * @Description Swagger API编辑界面
	 * @return
	 * @author Chenh
	 * @date 2016年6月13日 下午3:11:02
	 */
	@RequestMapping(value = { Link.SWAGGER_PATH + "/editor" })
	public String editor() {
		return "swagger/editor";
	}
	
	//初始化Mode列表
	private void initModes(HttpServletRequest request, GenScheme entity) {
		List<Map<String, Object>> models = new ArrayList<Map<String, Object>>();
		List<Map<String, Map<String, Object>>> jsons = new ArrayList<Map<String, Map<String, Object>>>();
		if(entity.getGenTable() == null){
			GenTable newGenTable = new GenTable();
			newGenTable.setIsApi("1");
			entity.setGenTable(newGenTable);
		}else{
			entity.getGenTable().setIsApi("1");
		}
		
		List<GenScheme> genSchemes = genSchemeService.findListByCategory(Gconfig.GcCategory.API_DOC_TEST, entity);
		
		for (GenScheme genScheme : genSchemes) {
			// 查询主表及字段列
			GenTable genTable = genScheme.getGenTable();
			List<GenTableColumn> genTableColumns = genTableColumnService.findList(new GenTableColumn(genTable));
			genTable.setColumnList(genTableColumns);
			Map<String, Object> model = GenUtils.getDataModel(genScheme);
			jsons.add(getGroup(genTableColumns));
			
			models.add(model);
		}
		request.setAttribute("entitys", models);
		request.setAttribute("jsons", jsons);
	}
	
	/**
	 * 讲类属性分组
	 * @param genTableColumns
	 * @return
	 */
	private Map<String, Map<String, Object>> getGroup(List<GenTableColumn> genTableColumns){
		Map<String, Map<String, Object>> map = new HashMap<String, Map<String, Object>>();
		Map<String, Object> isNotNulls = new HashMap<String, Object>();
		Map<String, Object> isApis = new HashMap<String, Object>();
		Map<String, Object> isApiInserts = new HashMap<String, Object>();
		Map<String, Object> isApiQuerys = new HashMap<String, Object>();
		
		for (GenTableColumn entity : genTableColumns) {
			if((entity.getJavaType()).equals("String") || (entity.getJavaType()).equals("Integer") || (entity.getJavaType()).equals("Double") || (entity.getJavaType()).equals("java.util.Date")){
				String fieldVal = "1";
				if((entity.getJavaType()).equals("java.util.Date")){
					fieldVal = "2016-03-09 11:46:20";
				}
				if(("0").equals(entity.getIsNull())){
					isNotNulls.put(entity.getJavaField(), entity.getJavaType());
					isNotNulls.put(entity.getJavaField(), fieldVal);
				}
				if(("1").equals(entity.getIsApi())){
					isApis.put(entity.getJavaField(), fieldVal);
				}
				if(("1").equals(entity.getIsApiInsert())){
					isApiInserts.put(entity.getJavaField(), fieldVal);
				}
				if(("1").equals(entity.getIsApiQuery())){
					isApiQuerys.put(entity.getJavaField(), fieldVal);
				}
			}
		}
		
		map.put("isNotNull", isNotNulls);
		map.put("isApi", isApis);
		map.put("isApiInsert", isApiInserts);
		map.put("isApiQuery", isApiQuerys);
		return map;
	}
}
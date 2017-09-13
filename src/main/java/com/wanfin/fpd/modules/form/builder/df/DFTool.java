/**  
 * @Project fpd 
 * @Title DFTool.java
 * @Package com.wanfin.fpd.modules.form.builder.df
 * @Description [[_自定义表单工具类_]]文件 
 * @author Chenh
 * @date 2016年4月28日 下午1:18:50 
 * @version V1.0   
 */ 
package com.wanfin.fpd.modules.form.builder.df;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.plugin.activerecord.Record;
import com.wanfin.fpd.common.config.SvalBase;
import com.wanfin.fpd.common.utils.StringUtils;
import com.wanfin.fpd.modules.form.builder.df.field.DFFCheckBox;
import com.wanfin.fpd.modules.form.builder.df.field.DFFInput;
import com.wanfin.fpd.modules.form.builder.df.field.DFFSelect;
import com.wanfin.fpd.modules.form.builder.df.field.DFFTextArea;
import com.wanfin.fpd.modules.form.builder.df.field.DFFTextAreaSplit;
import com.wanfin.fpd.modules.form.builder.df.field.DFOption;
import com.wanfin.fpd.modules.form.builder.df.group.DFFGroup;
import com.wanfin.fpd.modules.form.builder.df.group.input.TextArea;
import com.wanfin.fpd.modules.form.builder.df.group.input.TextInput;
import com.wanfin.fpd.modules.form.builder.df.group.input.WdatePicker;
import com.wanfin.fpd.modules.form.builder.df.group.radio.MultipleCheckboxes;
import com.wanfin.fpd.modules.form.builder.df.group.radio.MultipleRadios;
import com.wanfin.fpd.modules.form.builder.df.group.select.Select;
import com.wanfin.fpd.modules.form.builder.df.group.select.TreeSelect;
import com.wanfin.fpd.modules.form.builder.df.plugin.tab.DFTab;
import com.wanfin.fpd.modules.form.builder.df.plugin.tab.DFTabBox;
import com.wanfin.fpd.modules.form.builder.df.plugin.tab.DFTabItem;

/**
 * @Description [[_自定义表单工具类_]] DFTool类
 * @author Chenh
 * @date 2016年4月28日 下午1:18:50 
 */
public class DFTool {
	public static Map<String, Object> convert(List<Record> records){
		Map<String, Object> data = new HashMap<String, Object>();
		data.put(SvalBase.JsonKey.KEY_STATUS, true);
		
		List<DFTab> tabs = new ArrayList<DFTab>();
		DFTab tab = new DFTab();
		List<DFTabItem> tabItems = new ArrayList<DFTabItem>();
		DFTabItem tabIBoxs = new DFTabItem();
//		DFTabItem tabIInputs = new DFTabItem();
//		DFTabItem tabISelects = new DFTabItem();
//		DFTabItem tabIRadios = new DFTabItem();
//		DFTabItem tabIButtons = new DFTabItem();
		
		List<DFTabBox> boxs = new ArrayList<DFTabBox>();
//		List<DFTabBox> boxInputs = new ArrayList<DFTabBox>();
//		List<DFTabBox> boxSelects = new ArrayList<DFTabBox>();
//		List<DFTabBox> boxRadios = new ArrayList<DFTabBox>();
//		List<DFTabBox> boxButtons = new ArrayList<DFTabBox>();
		
		List<DFOption> inputsizeValue = new ArrayList<DFOption>();
		inputsizeValue.add(new DFOption("input-mini", "Mini", false));
		inputsizeValue.add(new DFOption("input-mini", "Small", false));
		inputsizeValue.add(new DFOption("input-medium", "Medium", false));
		inputsizeValue.add(new DFOption("input-large", "Large", false));
		inputsizeValue.add(new DFOption("input-xlarge", "Xlarge", true));
		inputsizeValue.add(new DFOption("input-xxlarge", "Xxlarge", false));
		
		for (Record record : records) {
			Integer r_id = record.getInt("id");
			String r_category = record.getStr("category");
			String r_categoryName = record.getStr("categoryName");
			String r_filed = record.getStr("filed");
			String r_filed2 = record.getStr("filed2");
			String r_type = record.getStr("type");
			String r_formType = record.getStr("formType");
			String r_formUrl = record.getStr("formUrl");
			String r_name = record.getStr("name");
			String dataType = record.getStr("dataType");
			String dataType2 = record.getStr("dataType2");
			String suffix = record.getStr("suffix");
			if(StringUtils.isBlank(suffix)){
				suffix = "";
			}else{
				suffix = "（"+suffix+"）";
			}
			
			if((DFFGroup.DFCtype.INPUT.getType()).equals(r_formType)){
				TextInput input = new TextInput();
				if(rfiledHasDot(r_filed)){
					input.setId(new DFFInput("ID / Name", "input", convertId(r_filed)));
				}else{
					input.setId(new DFFInput("ID / Name", "input", r_filed));
				}
				
				input.setId(new DFFInput("ID / Name", "input", r_filed));
				input.setLabel(new DFFInput("Label", "input", r_name+suffix));
				input.setPlaceholder(new DFFInput("属性提示", "input", r_name));
				input.setHelptext(new DFFInput("属性帮助文本", "input", ""));
				input.setDataType(new DFFInput("数据格式", "hidden", dataType));
				input.setDataType2(new DFFInput("数据格式2", "hidden", dataType2));
				input.setRequired(new DFFCheckBox("必填", "checkbox", false));
				input.setInputsize(new DFFSelect("文本框尺寸", "select", inputsizeValue));
				
				//boxInputs.add(new DFTabBox(r_categoryName, r_formType ,input));
				boxs.add(new DFTabBox(r_categoryName, r_formType ,input));
			}else if((DFFGroup.DFCtype.SELECT.getType()).equals(r_formType)){
				Select select = new Select();
				if(rfiledHasDot(r_filed)){
					select.setId(new DFFInput("ID / Name", "input", convertId(r_filed)));
				}else{
					select.setId(new DFFInput("ID / Name", "input", r_filed));
				}
				
				select.setLabel(new DFFInput("Label", "input", r_name));
				select.setUrl(new DFFInput("下拉数据源URL", "input", r_formUrl));
				
				List<String> optionsValue = new ArrayList<String>();
				optionsValue.add("是");
				optionsValue.add("否");
				select.setOptions(new DFFTextAreaSplit("选项", "textarea-split", optionsValue));
				select.setInputsize(new DFFSelect("文本框尺寸", "select", inputsizeValue));
				//boxSelects.add(new DFTabBox(r_categoryName, r_formType, select));
				boxs.add(new DFTabBox(r_categoryName, r_formType, select));
			}else if((DFFGroup.DFCtype.RADIO.getType()).equals(r_formType)){
				MultipleRadios radio = new MultipleRadios();
				if(rfiledHasDot(r_filed)){
					radio.setId(new DFFInput("ID / Name", "input", convertId(r_filed)));
				}else{
					radio.setId(new DFFInput("ID / Name", "input", r_filed));
				}
				
				radio.setLabel(new DFFInput("Label", "input", r_name));
				radio.setRequired(new DFFCheckBox("必填", "checkbox", false));
				radio.setUrl(new DFFInput("选项数据源URL", "input", r_formUrl));
				
				List<String> radiosValue = new ArrayList<String>();
				radiosValue.add("是");
				radiosValue.add("否");
				radio.setRadios(new DFFTextAreaSplit("选项", "textarea-split", radiosValue));
				//boxRadios.add(new DFTabBox(r_categoryName, r_formType, radio));
				boxs.add(new DFTabBox(r_categoryName, r_formType, radio));
			}else if((DFFGroup.DFCtype.CHECKBOX.getType()).equals(r_formType)){
				MultipleCheckboxes checkbox = new MultipleCheckboxes();
				if(rfiledHasDot(r_filed)){
					checkbox.setId(new DFFInput("ID / Name", "input", convertId(r_filed)));
				}else{
					checkbox.setId(new DFFInput("ID / Name", "input", r_filed));
				}

				checkbox.setLabel(new DFFInput("Label", "input", r_name));
				checkbox.setRequired(new DFFCheckBox("必填", "checkbox", false));
				checkbox.setUrl(new DFFInput("选项数据源URL", "input", r_formUrl));
				
				List<String> checkboxsValue = new ArrayList<String>();
				checkboxsValue.add("是");
				checkboxsValue.add("否");
				checkbox.setCheckboxes(new DFFTextAreaSplit("选项", "textarea-split", checkboxsValue));
				//boxRadios.add(new DFTabBox(r_categoryName, r_formType, checkbox));
				boxs.add(new DFTabBox(r_categoryName, r_formType, checkbox));
			}else if((DFFGroup.DFCtype.TEXTAREA.getType()).equals(r_formType)){
				TextArea textArea = new TextArea();
				if(rfiledHasDot(r_filed)){
					textArea.setId(new DFFInput("ID / Name", "input", convertId(r_filed)));
				}else{
					textArea.setId(new DFFInput("ID / Name", "input", r_filed));
				}
				
				textArea.setLabel(new DFFInput("Label", "input", r_name));
				textArea.setTextarea(new DFFTextArea("", "textarea", ""));
				//boxInputs.add(new DFTabBox(r_categoryName, r_formType, textArea));
				boxs.add(new DFTabBox(r_categoryName, r_formType, textArea));
			}else if((DFFGroup.DFCtype.DATE.getType()).equals(r_formType)){
				WdatePicker wdatePicker = new WdatePicker();
				if(rfiledHasDot(r_filed)){
					wdatePicker.setId(new DFFInput("ID / Name", "input", convertId(r_filed)));
				}else{
					wdatePicker.setId(new DFFInput("ID / Name", "input", r_filed));
				}
				
				wdatePicker.setLabel(new DFFInput("Label", "input", r_name));
				wdatePicker.setRequired(new DFFCheckBox("必填", "checkbox", false));
				wdatePicker.setFormate(new DFFInput("日期格式", "input", "yyyy-MM-dd HH:mm:ss"));
				wdatePicker.setInputsize(new DFFSelect("文本框尺寸", "select", inputsizeValue));
				boxs.add(new DFTabBox(r_categoryName, "WdatePicker", wdatePicker));
			}else if((DFFGroup.DFCtype.TREESELECT.getType()).equals(r_formType)){
				TreeSelect treeSelect = new TreeSelect();
				if(rfiledHasDot(r_filed)){
					treeSelect.setId(new DFFInput("标签id", "input", convertId(r_filed)));
				}else{
					treeSelect.setId(new DFFInput("标签id", "input", r_filed));
				}
				
				treeSelect.setName(new DFFInput("name", "input", r_filed));
				treeSelect.setValue(new DFFInput("value", "input", ""));
				treeSelect.setLabelName(new DFFInput("labelName", "input", r_filed2));
				treeSelect.setLabelValue(new DFFInput("labelValue", "input", ""));
				treeSelect.setLabel(new DFFInput("属性文本", "input", r_name));
				treeSelect.setUrl(new DFFInput("选项数据源URL", "input", r_formUrl));
				treeSelect.setRequired(new DFFCheckBox("必填", "checkbox", false));
				treeSelect.setInputsize(new DFFSelect("文本框尺寸", "select", inputsizeValue));
				boxs.add(new DFTabBox(r_categoryName, r_formType, treeSelect));
			}else{
				data.put(SvalBase.JsonKey.KEY_STATUS, false);
				data.put(SvalBase.JsonKey.KEY_MSG, "formType 未定义!");
			}
		}
//		tabIInputs.setTitle(DFFGroup.DFCtype.INPUT.getName());
//		tabISelects.setTitle(DFFGroup.DFCtype.SELECT.getName());
//		tabIRadios.setTitle(DFFGroup.DFCtype.RADIO.getName()+"-"+DFFGroup.DFCtype.CHECKBOX.getName());
//		tabIButtons.setTitle(DFFGroup.DFCtype.BUTTON.getName());
//		tabIInputs.setBoxs(boxInputs);
//		tabISelects.setBoxs(boxSelects);
//		tabIRadios.setBoxs(boxRadios);
//		tabIButtons.setBoxs(boxButtons);
		tabIBoxs.setTitle("标准属性");
		tabIBoxs.setBoxs(boxs);
//		tabItems.add(tabIInputs);
//		tabItems.add(tabISelects);
//		tabItems.add(tabIRadios);
//		tabItems.add(tabIButtons);
		tabItems.add(tabIBoxs);
		tab.setTabItems(tabItems);
		tabs.add(tab);

//		System.out.println(JSONObject.fromObject(tab).toString());
//		System.out.println(JSONArray.fromObject(tabs).toString());
		data.put(SvalBase.JsonKey.KEY_SIZE, records.size());
		data.put(SvalBase.JsonKey.KEY_DATA, tabs);
		data.put(SvalBase.JsonKey.KEY_PARAMS, "表单");
//		data.put(SvalBase.JsonKey.KEY_DATA, JSONArray.fromObject(tabs).toString());
		return data;
	}
	
	/**
	* @Description: 处理ID中含有点字符的数据 
	* @author Chenh
	* @param r_filed
	* @return  
	* @throws
	 */
	public static String convertId(String r_filed) {
		return r_filed.replaceAll("\\.", "_");
	}
	
	/**
	* @Description: 判断属性标识是否含有. 
	* @author Chenh
	* @param r_filed
	* @return  
	* @throws
	 */
	public static boolean rfiledHasDot(String r_filed) {
		return r_filed.indexOf(".") != -1;
	}
}

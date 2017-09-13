package com.wanfin.fpd.modules.product.entity;
import com.wanfin.fpd.common.persistence.DataEntity;

/**
 * 用于动态添加字段的实体类
 * @author 
 *
 */
public class TExtendColumn extends DataEntity<TExtendColumn>{
	
	private static final long serialVersionUID = 1L;
	private String columnName;//列名
	private String formName;//表单元素名称
	private String chineseName;//中文名称
	private Integer maxNum;//用于产生列名的数字编号(如：column2中的数字2)
	private String tableName;//表名(字段对应的表)
	
	public TExtendColumn(){
		
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getFormName() {
		return formName;
	}

	public void setFormName(String formName) {
		this.formName = formName;
	}

	public String getChineseName() {
		return chineseName;
	}

	public void setChineseName(String chineseName) {
		this.chineseName = chineseName;
	}

	public Integer getMaxNum() {
		return maxNum;
	}

	public void setMaxNum(Integer maxNum) {
		this.maxNum = maxNum;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
}

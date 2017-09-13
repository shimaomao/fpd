/**  
 * @Project fpd 
 * @Title ApiEntity.java
 * @Package com.wanfin.fpd.common.persistence
 * @Description [[_xxx_]]文件 
 * @author Chenh
 * @date 2016年6月7日 下午2:24:26 
 * @version V1.0   
 */ 
package com.wanfin.fpd.common.persistence;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wanfin.fpd.common.config.Global;
import com.wanfin.fpd.common.utils.StringUtils;
import com.wordnik.swagger.annotations.ApiModel;


/**
 * @Description [[_xxx_]] ApiEntity类
 * @author Chenh
 * @date 2016年6月7日 下午2:24:26 
 */
@ApiModel(value="ApiEntity", discriminator = "API基础接口")
public class ApiEntity<T> extends DataEntity<T> {
	private static final long serialVersionUID = 1L;
	public static final Boolean DEFAULT_EXPAND = false;
	public static final Integer DEFAULT_CURPAGE = 1;
	public static final Integer DEFAULT_PERPAGE = Integer.valueOf(Global.getConfig("page.pageSize"));
	public static final Integer DEFAULT_OFFSET = 0;
	public static final Integer DEFAULT_LIMIT = DEFAULT_PERPAGE;

	private Integer offset = DEFAULT_OFFSET; 			//开始位置
	private Integer limit = DEFAULT_LIMIT; 				//返回记录的数量
	private Boolean expand = DEFAULT_EXPAND; 			//是否展开详情
	private Integer curPage = DEFAULT_CURPAGE; 			//第几页
	private Integer perPage = DEFAULT_PERPAGE; 			//每页的记录数
	
	private RequestMethod oper;				//HttpOper:请求操作，设置该属性，将走不通的过滤器显示数据
	private List<String> fields; 		//返回属性列
	private String sorts; 				//按照哪个属性排序name asc
	private String sortby; 				//按照哪个属性排序
	private String orderby; 			//排序顺序
	
	/** 构造方法 ***********************************************************************************/
	public ApiEntity() {
		super();
	}
	public ApiEntity(String id) {
		super(id);
	}

	public List<String> getFields() {
		return fields;
	}
	public String getSorts() {
		if(this.sorts == null){
			this.sorts = StringUtils.sqlSorts(getSortby(), getOrderby());
		}
		return sorts;
	}
	public String getSortby() {
		return sortby;
	}
	public String getOrderby() {
		return orderby;
	}
	public Boolean getExpand() {
		return expand;
	}
	
	public Integer getOffset() {
		return offset;
	}
	public Integer getLimit() {
		return limit;
	}
	public Integer getCurPage() {
		return curPage;
	}
	public Integer getPerPage() {
		return perPage;
	}
	public void setExpand(Boolean expand) {
		this.expand = expand;
	}
	public void setFields(List<String> fields) {
		this.fields = fields;
	}
	public void setSorts(String sorts) {
		this.sorts = sorts;
	}
	public void setSortby(String sortby) {
		this.sortby = sortby;
		this.page.setOrderBy(getSortby());
	}
	public void setOrderby(String orderby) {
		this.orderby = orderby;
		if(this.getPage() != null){
			this.page.setOrderBy(getOrderby());
		}
	}
	public void setOffset(Integer offset) {
		this.offset = offset;
	}
	public void setLimit(Integer limit) {
		this.limit = limit;
	}
	public void setCurPage(Integer curPage) {
		this.curPage = curPage;
		if(this.getPage() != null){
			this.page.setPageNo(getCurPage());
		}
	}
	public void setPerPage(Integer perPage) {
		this.perPage = perPage;
		if(this.getPage() != null){
			this.page.setPageSize(getPerPage());
		}
	}

	@JsonIgnore
	@Transactional
	public RequestMethod getOper() {
		return oper;
	}
	public void setOper(RequestMethod oper) {
		this.oper = oper;
	}
	
	
//	public List<String> getFields() {
//		return fields;
//	}
//	public String getSorts() {
//		if(this.sorts == null){
//			setSorts(StringUtils.sqlSorts(getSortby(), getOrderby()));
//		}
//		return sorts;
//	}
//	
//	
//	public void setExpand(Boolean expand) {
//		this.expand = expand;
//	}
//	public void setSorts(String sorts) {
//		this.sorts = sorts;
//	}
//	public void setSortby(String sortby) {
//		this.sortby = sortby;
//		this.page.setOrderBy(getSortby());
//	}
//	public void setOrderby(String orderby) {
//		this.orderby = orderby;
//		this.page.setOrderBy(getOrderby());
//	}
//	public void setFields(List<String> fields) {
//		this.fields = fields;
//	}
//	public void setOffset(Integer offset) {
//		this.offset = offset;
//		this.page.setPageSize(getPerPage());
//	}
//	public void setLimit(Integer limit) {
//		this.limit = limit;
//	}
//	public void setCurPage(Integer curPage) {
//		this.curPage = curPage;
//		this.page.setPageNo(getCurPage());
//	}
//	public void setPerPage(Integer perPage) {
//		this.perPage = perPage;
//		this.page.setPageSize(getPerPage());
//	}
}

/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.productbiz.entity;

import java.beans.Transient;
import java.util.List;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Lists;
import com.wanfin.fpd.common.config.Global;
import com.wanfin.fpd.common.persistence.DataEntity;
import com.wanfin.fpd.common.utils.StringUtils;
import com.wanfin.fpd.modules.sys.entity.Menu;

/**
 * 业务功能Entity
 * @author lx
 * @version 2016-03-10
 */
public class TProductBiz extends DataEntity<TProductBiz> {
	
	private static final long serialVersionUID = 1L;
	private String category;		//对应自定义表单模块的category
	private String bizCode;		// 业务操作编码（请在字典表维护）
	private String bizName;		// 业务操作名
	private String bizUrl;		// 资源路径
	private String bizParam;		// 资源参数
	private String type;		//业务类型（1贷前；2贷中；3贷后）
	private String sort;		//排序
	private String isShow;		//显示:1、默认（显示）；0、隐藏
	private String isFlow;		//流程节点类型 :1、否；1、主流程；2、子流程
	private String formtpl;		//内置表单ID
	private String status;		//状态:0、默认（无表单）；1、自定义（必填）；2、系统内置
	private String parentIds;		//所有父级编号
	private TProductBiz parent;		//父级
	private List<TProductBiz> childrens;		//子级
	private List<Menu> menuList = Lists.newArrayList(); // 拥有菜单列表
	private String isYwtype;    //0:默认 （不限制）；1：担保机构；2：贷款机构 
	
	/** 构造方法 ***********************************************************************************/
	/**
	 * @Description [[_xxx_]]构造器
	 * @author Chenh
	 * @date 2015-1-15 下午3:39:14  
	 */
	public TProductBiz(String id) {
		super(id);
	}
	public TProductBiz() {
		super();
	}

	@Length(min=0, max=50, message="业务操作名长度必须介于 0 和 50 之间")
	public String getBizName() {
		return bizName;
	}

	public void setBizName(String bizName) {
		this.bizName = bizName;
	}
	
	@Length(min=0, max=64, message="资源路径长度必须介于 0 和 64 之间")
	public String getBizUrl() {
		return bizUrl;
	}

	public void setBizUrl(String bizUrl) {
		this.bizUrl = bizUrl;
	}
	
	@Length(min=0, max=64, message="资源参数长度必须介于 0 和 64 之间")
	public String getBizParam() {
		return bizParam;
	}

	public void setBizParam(String bizParam) {
		this.bizParam = bizParam;
	}
	public String getType() {
		return type;
	}
	public String getFormtpl() {
		return formtpl;
	}
	public void setFormtpl(String formtpl) {
		this.formtpl = formtpl;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getBizCode() {
		return bizCode;
	}
	public void setBizCode(String bizCode) {
		this.bizCode = bizCode;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getParentIds() {
		return parentIds;
	}
	public void setParentIds(String parentIds) {
		this.parentIds = parentIds;
	}
	public TProductBiz getParent() {
		return parent;
	}
	public void setParent(TProductBiz parent) {
		this.parent = parent;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public String getIsShow() {
		return isShow;
	}
	public void setIsShow(String isShow) {
		this.isShow = isShow;
	}
	public String getIsFlow() {
		return isFlow;
	}
	public void setIsFlow(String isFlow) {
		this.isFlow = isFlow;
	}
	
	@Transient
	public List<TProductBiz> getChildrens() {
		return childrens;
	}
	@Transient
	public void setChildrens(List<TProductBiz> childrens) {
		this.childrens = childrens;
	}
	public List<Menu> getMenuList() {
		return menuList;
	}
	public void setMenuList(List<Menu> menuList) {
		this.menuList = menuList;
	}
	public List<String> getMenuIdList() {
		List<String> menuIdList = Lists.newArrayList();
		for (Menu menu : menuList) {
			menuIdList.add(menu.getId());
		}
		return menuIdList;
	}

	public void setMenuIdList(List<String> menuIdList) {
		menuList = Lists.newArrayList();
		for (String menuId : menuIdList) {
			Menu menu = new Menu(menuId);
			menuList.add(menu);
		}
	}

	public String getMenuIds() {
		return StringUtils.join(getMenuIdList(), ",");
	}
	
	public void setMenuIds(String menuIds) {
		menuList = Lists.newArrayList();
		if (menuIds != null){
			String[] ids = StringUtils.split(menuIds, ",");
			setMenuIdList(Lists.newArrayList(ids));
		}
	}
	
	@JsonIgnore
	public static void sortList(List<TProductBiz> list, List<TProductBiz> sourcelist, String parentId, boolean cascade,String ywType){
		for (int i=0; i<sourcelist.size(); i++){
			TProductBiz e = sourcelist.get(i);
			if(ywType.equals("0")||e.getIsYwtype().equals("")||e.getIsYwtype().equals("0")||e.getIsYwtype().equals(ywType)){
			if (e.getParent()!=null && e.getParent().getId()!=null
					&& e.getParent().getId().equals(parentId)){
				list.add(e);
				if (cascade){
					// 判断是否还有子节点, 有则继续获取子节点
					for (int j=0; j<sourcelist.size(); j++){
						TProductBiz child = sourcelist.get(j);
						if (child.getParent()!=null && child.getParent().getId()!=null && child.getParent().getId().equals(e.getId())){
							sortList(list, sourcelist, e.getId(), true,ywType);
							break;
						}
					}
				}
			}
			}
		}
	}

	public String getParentId() {
		return (parent != null && StringUtils.isNotEmpty(parent.getId())) ? parent.getId() : "0";
	}
	/**
	 * @Description TODO
	 * @return
	 * @author Chenh 
	 * @date 2016年5月19日 下午2:50:41  
	 */
	@JsonIgnore
	public static String getRootId(){
		return Global.ROOT_ID;
	}
	
	public String getIsYwtype() {
		return isYwtype;
	}
	public void setIsYwtype(String isYwtype) {
		this.isYwtype = isYwtype;
	}
}
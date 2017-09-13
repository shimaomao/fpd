/**    
* @Project: fpd 
* @Title: YYYYY 
* @Package com.wanfin.fpd.modules.init.vo 
* @Description [[_XXXXX_]]文件
* @author Chenh 
* @date 2016年12月15日 下午3:41:44   
* @version V1.0.0   */ 
package com.wanfin.fpd.modules.init.vo;

import java.util.List;

import com.wanfin.fpd.modules.form.entity.tpl.DfFormTpl;

/**   
 * @author Chenh  
 * @date 2016年12月15日 下午3:41:44 
 * @Description [[_XXXXX_]] YYYYY类
 * TODO 
 *   
 */
public class SysInitFormtplVo extends DfFormTpl{
	private static final long serialVersionUID = 1L;
	List<String> officeIds;
	
	public SysInitFormtplVo() {
		super();
	}
	public SysInitFormtplVo(DfFormTpl tpl, List<String> officeIds) {
		super();
		this.officeIds = officeIds;
		this.id = tpl.getId();
		this.createBy = tpl.getCreateBy();
		this.createDate = tpl.getCreateDate();
		this.delFlag = tpl.getDelFlag();
		this.organId = tpl.getOrganId();
		this.remarks = tpl.getRemarks();
		this.updateBy = tpl.getUpdateBy();
		this.updateDate = tpl.getUpdateDate();
		this.setJson(tpl.getJson());
		this.setModel(tpl.getModel());
		this.setModsub(tpl.getModsub());
		this.setName(tpl.getName());
		this.setOffice(tpl.getOffice());
		this.setOrganId(tpl.getOrganId());
		this.setOriginalhtml(tpl.getOriginalhtml());
		this.setParent(tpl.getParent());
		this.setParsehtml(tpl.getParsehtml());
		this.setProduct(tpl.getProduct());
		this.setRelId(tpl.getRelId());
		this.setSname(tpl.getSname());
		this.setType(tpl.getType());
		this.setUpdateBy(tpl.getUpdateBy());
		this.setUpdateDate(tpl.getUpdateDate());
	}

	public List<String> getOfficeIds() {
		return officeIds;
	}
	public void setOfficeIds(List<String> officeIds) {
		this.officeIds = officeIds;
	}
}

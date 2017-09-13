/**  
 * @Project fpd 
 * @Title WindCfgVo.java
 * @Package com.wanfin.fpd.modules.windcfg
 * @Description [[_xxx_]]文件 
 * @author Chenh
 * @date 2016年5月27日 下午3:02:51 
 * @version V1.0   
 */ 
package com.wanfin.fpd.modules.wind.vo;

import com.wanfin.fpd.modules.wind.entity.TWindControl;

/**
 * @Description [[_xxx_]] WindCfgVo类
 * @author Chenh
 * @date 2016年5月27日 下午3:02:51 
 */
public class TWindControlVo extends TWindControl{
	/**
	 * @Description serialVersionUID属性
	 * @Fields long serialVersionUID
	 * @author Chenh
	 * @date 2016年5月27日 下午3:03:49 
	 */
	private static final long serialVersionUID = 1L;
	private Boolean checked;

	/** 构造方法 ***********************************************************************************/
	/**
	 * @Description [[_xxx_]]构造器
	 * @author Chenh
	 * @date 2015-1-15 下午3:39:14  
	 */
	public TWindControlVo() {
		super();
	}
	public TWindControlVo(TWindControl tWindControl) {
		super();
		if (tWindControl != null) {
			this.createBy = tWindControl.getCreateBy();
			this.createDate = tWindControl.getCreateDate();
			this.currentUser = tWindControl.getCurrentUser();
			this.delFlag = tWindControl.getDelFlag();
			this.id = tWindControl.getId();
			this.organId = tWindControl.getOrganId();
			this.page = tWindControl.getPage();
			this.remarks = tWindControl.getRemarks();
			this.sqlMap = tWindControl.getSqlMap();
			this.updateBy = tWindControl.getUpdateBy();
			this.updateDate = tWindControl.getUpdateDate();
			
			this.name = tWindControl.getName();
			this.url = tWindControl.getUrl();
			this.type = tWindControl.getType();
			this.param = tWindControl.getParam();
			this.status = tWindControl.getStatus();
			this.createTime = tWindControl.getCreateTime();
			this.updateTime = tWindControl.getUpdateTime();
		}
	}

	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}
}

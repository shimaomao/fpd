/**  
 * @Project fpd 
 * @Title DFFSelect.java
 * @Package com.wanfin.fpd.modules.form.builder.df.field
 * @Description [[_xxx_]]文件 
 * @author Chenh
 * @date 2016年4月28日 下午2:20:09 
 * @version V1.0   
 */ 
package com.wanfin.fpd.modules.form.builder.df.field;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.wanfin.fpd.common.utils.StringUtils;
import com.wanfin.fpd.modules.form.builder.df.group.DFFGroup;

/**
 * @Description [[_xxx_]] DFFSelect类
 * @author Chenh
 * @date 2016年4月28日 下午2:20:09 
 */
public class DFFSelect extends DFF<List<DFOption>> implements Serializable{
	private static final long serialVersionUID = 1L;
	private List<DFOption> value;

	/** 构造方法 ***********************************************************************************/
	/**
	 * @Description [[_xxx_]]构造器
	 * @author Chenh
	 * @date 2015-1-15 下午3:39:14  
	 */
	public DFFSelect(String name, String label, String type,
			List<DFOption> value) {
		super(name, label, type);
		this.value = value;
	}
	/**
	 * @Description [[_xxx_]]构造器
	 * @author Chenh
	 * @date 2015-1-15 下午3:39:14  
	 */
	public DFFSelect() {
		super();
	}
	/**
	 * @Description [[_xxx_]]构造器
	 * @author Chenh
	 * @date 2015-1-15 下午3:39:14  
	 */
	public DFFSelect(String name, String label, String type) {
		super(name, label, type);
	}
	/**
	 * @Description [[_xxx_]]构造器
	 * @author Chenh
	 * @date 2015-1-15 下午3:39:14  
	 */
	public DFFSelect(String label, String type, List<DFOption> value) {
		super(label, type);
		this.value = value;
	}
	/** 
	 * @Description TODO
	 * @Title: getValue
	 * @see com.wanfin.fpd.modules.form.builder.df.field.DFF#getValue()
	 * @return
	 * @author Chenh 
	 * @date 2016年4月28日 下午2:20:09  
	 */
	@Override
	public List<DFOption> getValue() {
		if(this.value == null){
			this.value = new ArrayList<DFOption>();
		}
		return this.value;
	}

	/** 
	 * @Description TODO
	 * @Title: setValue
	 * @see com.wanfin.fpd.modules.form.builder.df.field.DFF#setValue(List<DFOption>)
	 * @param value
	 * @author Chenh 
	 * @date 2016年4月28日 下午2:20:09  
	 */
	@Override
	public void setValue(List<DFOption> value) {
		 this.value = value;
	}
	
	@Override
	public String getType() {
		if (!StringUtils.isNotEmpty(super.getType())) {
			return DFFGroup.DFCtype.SELECT.getType();
		}
		return super.getType();
	}
}

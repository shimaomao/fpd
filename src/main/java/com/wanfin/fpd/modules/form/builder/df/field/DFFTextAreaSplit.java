/**  
 * @Project fpd 
 * @Title DFFTextAreaSplit.java
 * @Package com.wanfin.fpd.modules.form.builder.df.field
 * @Description [[_xxx_]]文件 
 * @author Chenh
 * @date 2016年4月28日 下午2:09:34 
 * @version V1.0   
 */ 
package com.wanfin.fpd.modules.form.builder.df.field;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.wanfin.fpd.common.utils.StringUtils;
import com.wanfin.fpd.modules.form.builder.df.group.DFFGroup;

/**
 * @Description [[_xxx_]] DFFTextAreaSplit类
 * @author Chenh
 * @date 2016年4月28日 下午2:09:34 
 */
public class DFFTextAreaSplit extends DFF<List<String>> implements Serializable{
	private static final long serialVersionUID = 1L;
	private List<String> value;

	/** 构造方法 ***********************************************************************************/
	/**
	 * @Description [[_xxx_]]构造器
	 * @author Chenh
	 * @date 2015-1-15 下午3:39:14  
	 */
	public DFFTextAreaSplit(String name, String label, String type,
			List<String> value) {
		super(name, label, type);
		this.value = value;
	}
	/**
	 * @Description [[_xxx_]]构造器
	 * @author Chenh
	 * @date 2015-1-15 下午3:39:14  
	 */
	public DFFTextAreaSplit() {
		super();
	}
	/**
	 * @Description [[_xxx_]]构造器
	 * @author Chenh
	 * @date 2015-1-15 下午3:39:14  
	 */
	public DFFTextAreaSplit(String name, String label, String type) {
		super(name, label, type);
	}
	/**
	 * @Description [[_xxx_]]构造器
	 * @author Chenh
	 * @date 2015-1-15 下午3:39:14  
	 */
	public DFFTextAreaSplit(String label, String type, List<String> value) {
		super(label, type);
		this.value = value;
	}
	
	/** 
	 * @Title: getValue
	 * @see com.wanfin.fpd.modules.form.builder.df.field.DFF#getValue()
	 * @return
	 * @author Chenh 
	 * @date 2016年4月28日 下午2:09:34  
	 */
	@Override
	public List<String> getValue() {
		return this.value;
	}

	/** 
	 * @Title: setValue
	 * @see com.wanfin.fpd.modules.form.builder.df.field.DFF#setValue(List<DFOption>)
	 * @param value
	 * @author Chenh 
	 * @date 2016年4月28日 下午2:09:34  
	 */
	@Override
	public void setValue(List<String> value) {
		if(this.value == null){
			this.value = new ArrayList<String>();
		}
		this.value = value;
	}

	@Override
	public String getType() {
		if (!StringUtils.isNotEmpty(super.getType())) {
			return DFFGroup.DFCtype.TEXTAREASPLIT.getType();
		}
		return super.getType();
	}
}

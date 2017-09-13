/**  
 * @Project fpd 
 * @Title CCheckingAdapter.java
 * @Package com.wanfin.fpd.modules.wind.adapter
 * @Description [[_xxx_]]文件 
 * @author Chenh
 * @date 2016年6月2日 上午10:03:16 
 * @version V1.0   
 */ 
package com.wanfin.fpd.modules.wind.adapter.impl;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

import com.wanfin.fpd.common.utils.StringUtils;
import com.wanfin.fpd.modules.wind.adapter.IAdapter;
import com.wanfin.fpd.modules.wind.adapter.IAdapterUtil;
import com.wanfin.fpd.modules.wind.adapter.IeAdapter;
import com.wanfin.fpd.modules.wind.adapter.IeAdapterDB;
import com.wanfin.fpd.modules.wind.adapter.IeAdapterType;
import com.wanfin.fpd.modules.wind.entity.creditchecking.TCreditChecking;

/**
 * @Description [[_适配器_]] CCheckingAdapter类
 * @author Chenh
 * @date 2016年6月2日 上午10:03:16 
 */
@SuppressWarnings("rawtypes")
public class CCheckingAdapter<T extends IAdapter> extends TCreditChecking{
	private static final long serialVersionUID = 1L;
	
	/** 构造方法 ***********************************************************************************/
	public CCheckingAdapter() {
		super();
	}

	public CCheckingAdapter(String id) {
		super(id);
	}

	public CCheckingAdapter(TCreditChecking cchecking) {
		this.setCompany(cchecking.getCompany());
		this.setData(cchecking.getData());
		this.setEmployee(cchecking.getEmployee());
		this.setSqlMap(cchecking.getSqlMap());
		this.setDb(cchecking.getDb());
		this.setType(cchecking.getType());
		this.setTypeId(cchecking.getTypeId());
		this.setTypeSub(cchecking.getTypeSub());

		this.setId(cchecking.getId());
		this.setCurrentUser(cchecking.getCurrentUser());
		this.setIsNewRecord(cchecking.getIsNewRecord());
		this.setDelFlag(cchecking.getDelFlag());
		this.setOrganId(cchecking.getOrganId());
		this.setPage(cchecking.getPage());
		this.setRemarks(cchecking.getRemarks());
		this.setCreateBy(cchecking.getCreateBy());
		this.setCreateDate(cchecking.getCreateDate());
		this.setUpdateBy(cchecking.getUpdateBy());
		this.setUpdateDate(cchecking.getUpdateDate());
		this.ccdata = getCcdata();
	}

	/**
	 * @Description TCreditChecking to CCheckingAdapter
	 * @author Chenh 
	 * @date 2016年6月2日 下午1:18:17  
	 */
	public static List<CCheckingAdapter> convertToCCheckingAdapter(List<TCreditChecking> ccheckings) {
		List<CCheckingAdapter> ccAdapter = new ArrayList<CCheckingAdapter>();
		for (TCreditChecking cchecking : ccheckings) {
			ccAdapter.add(new CCheckingAdapter(cchecking));
		}
		return ccAdapter;
	}
	
	private List<?> ccdata;		// 个人数据

	public void setCcdata(List<?> ccdata) {
		this.ccdata = ccdata;
	}

	public List<?> getCcdata(){
		if(StringUtils.isNotEmpty(getDb())){
			if(StringUtils.isNotEmpty(getType())){
				if(StringUtils.isNotEmpty(getTypeSub())) {
					IeAdapter curCCAdapter = IeAdapter.getIeAdapterByTypeAndSub(IeAdapterType.getIeAdapterTypeByDBKey(IeAdapterDB.getIeAdapterDBByKey(getDb()), getType()), getTypeSub());
					try {
						if(StringUtils.isNotEmpty(this.getData())){
							this.setCcdata(curCCAdapter.getAdapter().init(IAdapterUtil.toJson(this.getData())));
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}else{
					try {
						throw new Exception(getTypeSub()+"-TypeSub 未定义！");
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}else{
				try {
					throw new Exception("Type 未定义！");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}else{
			try {
				throw new Exception("DB 未定义！");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return this.ccdata;
	}
}
package com.wanfin.fpd.modules.wind.adapter.vo;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.wanfin.fpd.common.utils.StringUtils;
import com.wanfin.fpd.modules.wind.adapter.IeAdapter;
import com.wanfin.fpd.modules.wind.adapter.IeAdapterDB;
import com.wanfin.fpd.modules.wind.adapter.IeAdapterType;
import com.wanfin.fpd.modules.wind.adapter.WindHttp;
import com.wanfin.fpd.modules.wind.adapter.WindVo;
import com.wanfin.fpd.modules.wind.entity.creditchecking.TCreditChecking;

public class IaTypeVo {
	private IeAdapterType type;
	private WindVo vo;
	
	public IaTypeVo() {
		super();
	}
	public IaTypeVo(WindVo vo) {
		super();
		this.vo = vo;
	}

	public IaTypeVo(IeAdapterType type, WindVo vo) {
		super();
		this.type = type;
		this.vo = vo;
	}

	public IeAdapterType getType() {
		return type;
	}
	public void setType(IeAdapterType type) {
		this.type = type;
	}
	public WindVo getVo() {
		return vo;
	}
	public void setVo(WindVo vo) {
		this.vo = vo;
	}

	public static List<TCreditChecking> dealIaTypeVo(IeAdapterDB db, IaTypeVo iaTypeVo) {
		List<TCreditChecking> entitys = new ArrayList<TCreditChecking>();
		WindVo vo = iaTypeVo.getVo();
		IeAdapterType ieAdapterType = iaTypeVo.getType();
		if((vo.getReturnCode()).equals(WindHttp.WHCode.SUCCESS)){
			List<IeAdapter> ieAdapters = IeAdapter.getIeAdapterByType(ieAdapterType);
			for (IeAdapter ieAdapter : ieAdapters) {
				JSONObject voResult = vo.getResult();
				String curData = voResult.optString(ieAdapter.getSub());

				if(StringUtils.isNotEmpty(curData)){
					TCreditChecking tCreditChecking = new TCreditChecking();
					tCreditChecking.preInsert();

					tCreditChecking.setDb(db.getKey());
					tCreditChecking.setType(ieAdapterType.getKey());
					tCreditChecking.setTypeId(vo.getRelId());
					tCreditChecking.setTypeSub(ieAdapter.getSub());
					tCreditChecking.setData(curData);
					tCreditChecking.setRemarks(db.getName()+"-"+ieAdapterType.getName()+"("+ieAdapter.getRemark()+")");
					
					entitys.add(tCreditChecking);
				}
			}
		}
		return entitys;
	}
}
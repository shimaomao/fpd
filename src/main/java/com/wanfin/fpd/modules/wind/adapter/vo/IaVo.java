package com.wanfin.fpd.modules.wind.adapter.vo;

import java.util.ArrayList;
import java.util.List;

import com.wanfin.fpd.modules.wind.adapter.IeAdapterDB;
import com.wanfin.fpd.modules.wind.entity.creditchecking.TCreditChecking;

public class IaVo {
	private IeAdapterDB db;
	private List<IaTypeVo> types;

	public IaVo() {
		super();
	}
	
	public IaVo(IeAdapterDB db, List<IaTypeVo> types) {
		super();
		this.db = db;
		this.types = types;
	}
	public IeAdapterDB getDb() {
		return db;
	}
	public void setDb(IeAdapterDB db) {
		this.db = db;
	}
	public List<IaTypeVo> getTypes() {
		return types;
	}
	public void setTypes(List<IaTypeVo> types) {
		this.types = types;
	}
	
	public static List<TCreditChecking> dealLaVo(List<IaVo> iaVos) {
		List<TCreditChecking> entitys = new ArrayList<TCreditChecking>(); 
		for (IaVo iaVo : iaVos) {
			entitys.addAll(dealIaVo(iaVo));
		}
		return entitys;
	}

	private static List<TCreditChecking> dealIaVo(IaVo iaVo) {
		List<TCreditChecking> entitys = new ArrayList<TCreditChecking>(); 
		IeAdapterDB db = iaVo.getDb();
		List<IaTypeVo> iaTypeVos = iaVo.getTypes();
		for (IaTypeVo iaTypeVo : iaTypeVos) {
			entitys.addAll(IaTypeVo.dealIaTypeVo(db, iaTypeVo));
		}
		return entitys; 
	}
}

/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.wind.adapter.person;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.wanfin.fpd.modules.wind.adapter.CMap;
import com.wanfin.fpd.modules.wind.adapter.IAdapter;

/**
 * 用户的信用卡信息Entity
 * @author srf
 * @version 2016-05-16
 */
public class PCredit implements IAdapter<PCredit>{
	private enum CEnum{
		id("id", "主键"),
		personid("personid", "个人主键"),
		cardbank("cardbank", "发卡银行"),
		amount("amount", "额度"),
		consumecount("consumecount", "刷卡次数"),
		delaycount("delaycount", "逾期次数"); 
		
		private String key;
		private String remark;
		private CEnum(String key, String remark) {
			this.key = key;
			this.remark = remark;
		}
		public String getKey() {
			return key;
		}
		public String getRemark() {
			return remark;
		}
	}
	
	private String id;
	private String personid;		// 个人主键
	private String cardbank;		// 发卡银行
	private String amount;		// 额度
	private String consumecount;		// 刷卡次数
	private String delaycount;		// 逾期次数
	private List<CMap> cmaps;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPersonid() {
		return personid;
	}
	public void setPersonid(String personid) {
		this.personid = personid;
	}
	public String getCardbank() {
		return cardbank;
	}
	public void setCardbank(String cardbank) {
		this.cardbank = cardbank;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getConsumecount() {
		return consumecount;
	}
	public void setConsumecount(String consumecount) {
		this.consumecount = consumecount;
	}
	public String getDelaycount() {
		return delaycount;
	}
	public void setDelaycount(String delaycount) {
		this.delaycount = delaycount;
	}
	public List<CMap> getCmaps() {
		return cmaps;
	}
	public void setCmaps(List<CMap> cmaps) {
		this.cmaps = cmaps;
	}
	@Override
	public List<PCredit> init(JSONArray jarray) throws JSONException {
		List<PCredit> entitys = new ArrayList<PCredit>();
		for (int i = 0; i < jarray.length(); i++) {
			entitys.add(init(jarray.getJSONObject(i)));
			
		}
		return entitys;
	}

	@Override
	public PCredit init(String data) throws JSONException {
		return init(new JSONObject(data));
	}
	
	private PCredit init(JSONObject jo) throws JSONException {
		PCredit entity = new PCredit();
		entity.setAmount(jo.optString(CEnum.amount.getKey()));
		entity.setCardbank(jo.optString(CEnum.cardbank.getKey()));
		entity.setConsumecount(jo.optString(CEnum.consumecount.getKey()));
		entity.setDelaycount(jo.optString(CEnum.delaycount.getKey()));
		
		entity.setPersonid(jo.optString(CEnum.personid.getKey()));
		entity.setId(jo.optString(CEnum.id.getKey()));

		entity.setCmaps(initCmaps(entity));
		return entity;
	}
	
	private List<CMap> initCmaps(PCredit entity) {
		List<CMap> cmaps = new ArrayList<CMap>();
		cmaps.add(new CMap(CEnum.cardbank.getKey(), entity.getCardbank(), CEnum.cardbank.getRemark()));
		cmaps.add(new CMap(CEnum.amount.getKey(), entity.getAmount(), CEnum.amount.getRemark()));
		cmaps.add(new CMap(CEnum.consumecount.getKey(), entity.getConsumecount(), CEnum.consumecount.getRemark()));
		cmaps.add(new CMap(CEnum.delaycount.getKey(), entity.getDelaycount(), CEnum.delaycount.getRemark()));

		cmaps.add(new CMap(CEnum.personid.getKey(), entity.getPersonid(), CEnum.personid.getRemark()));
		cmaps.add(new CMap(CEnum.id.getKey(), entity.getId(), CEnum.id.getRemark()));
		return cmaps;
	}
}
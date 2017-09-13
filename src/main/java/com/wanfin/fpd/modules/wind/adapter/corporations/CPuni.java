/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.wind.adapter.corporations;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.wanfin.fpd.modules.wind.adapter.CMap;
import com.wanfin.fpd.modules.wind.adapter.IAdapter;

/**
 * 公司工商处罚信息Entity
 * @author srf
 * @version 2016-05-13
 */
public class CPuni implements IAdapter<CPuni>{
	private enum CEnum{
//		id, corpid, fileid, judgmentdate, punishno, punishamount, punishorgan, punishType, punishbasis, punishresult; 
		
		id("id", "主键"),
		corpid("corpid", "公司主键"),
		fileid("fileid", "工商信息附件"),
			
		judgmentdate("judgmentdate", "判决书签发日期"),
		punishno("punishno", "处罚编号"),
		punishamount("punishamount", "处罚金额"),
		punishorgan("punishorgan", "处罚机关"),
		punishType("punishType", "处罚类型"),
		punishbasis("punishbasis", "处罚依据"),
		punishresult("punishresult", "处罚结果"); 
		
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
	
	private String id;		// ID
	private String corpid;		// 公司主键
	private Date judgmentdate;		// 判决书签发日期
	private String punishno;		// 处罚编号
	private String punishamount;		// 处罚金额
	private String punishorgan;		// 处罚机关
	private String punishType;		//处罚类型
	private String punishbasis;		// 处罚依据
	private String punishresult;		// 处罚结果
	private String fileid;		// 工商信息附件

	private List<CMap> cmaps;
	
	public String getCorpid() {
		return corpid;
	}
	public void setCorpid(String corpid) {
		this.corpid = corpid;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Date getJudgmentdate() {
		return judgmentdate;
	}
	public void setJudgmentdate(Date judgmentdate) {
		this.judgmentdate = judgmentdate;
	}
	public String getPunishno() {
		return punishno;
	}
	public void setPunishno(String punishno) {
		this.punishno = punishno;
	}
	public String getPunishamount() {
		return punishamount;
	}
	public void setPunishamount(String punishamount) {
		this.punishamount = punishamount;
	}
	public String getPunishorgan() {
		return punishorgan;
	}
	public void setPunishorgan(String punishorgan) {
		this.punishorgan = punishorgan;
	}
	public String getPunishbasis() {
		return punishbasis;
	}
	public void setPunishbasis(String punishbasis) {
		this.punishbasis = punishbasis;
	}
	public String getPunishresult() {
		return punishresult;
	}
	public void setPunishresult(String punishresult) {
		this.punishresult = punishresult;
	}
	public String getFileid() {
		return fileid;
	}
	public void setFileid(String fileid) {
		this.fileid = fileid;
	}
	public String getPunishType() {
		return punishType;
	}
	public void setPunishType(String punishType) {
		this.punishType = punishType;
	}
	public List<CMap> getCmaps() {
		return cmaps;
	}
	public void setCmaps(List<CMap> cmaps) {
		this.cmaps = cmaps;
	}
	@Override
	public List<CPuni> init(JSONArray jarray) throws JSONException {
		List<CPuni> entitys = new ArrayList<CPuni>();
		for (int i = 0; i < jarray.length(); i++) {
			entitys.add(init(jarray.getJSONObject(i)));
			
		}
		return entitys;
	}
	
	@Override
	public CPuni init(String data) throws JSONException {
		return init(new JSONObject(data));
	}
	
	private CPuni init(JSONObject jo) throws JSONException {
		CPuni entity = new CPuni();
		entity.setJudgmentdate(new Date(jo.optLong(CEnum.judgmentdate.getKey())));
		entity.setPunishamount(jo.optString(CEnum.punishamount.getKey()));
		entity.setPunishbasis(jo.optString(CEnum.punishbasis.getKey()));
		entity.setPunishno(jo.optString(CEnum.punishno.getKey()));
		entity.setPunishorgan(jo.optString(CEnum.punishorgan.getKey()));
		entity.setPunishresult(jo.optString(CEnum.punishresult.getKey()));
		entity.setPunishType(jo.optString(CEnum.punishType.getKey()));

		entity.setFileid(jo.optString(CEnum.fileid.getKey()));
		entity.setCorpid(jo.optString(CEnum.corpid.getKey()));
		entity.setId(jo.optString(CEnum.id.getKey()));

		entity.setCmaps(initCmaps(entity));
		
		return entity;
	}

	private List<CMap> initCmaps(CPuni entity) {
		List<CMap> cmaps = new ArrayList<CMap>();
		cmaps.add(new CMap(CEnum.judgmentdate.getKey(), entity.getJudgmentdate(), CEnum.judgmentdate.getRemark()));
		cmaps.add(new CMap(CEnum.punishamount.getKey(), entity.getPunishamount(), CEnum.punishamount.getRemark()));
		cmaps.add(new CMap(CEnum.punishbasis.getKey(), entity.getPunishbasis(), CEnum.punishbasis.getRemark()));
		
		cmaps.add(new CMap(CEnum.punishno.getKey(), entity.getPunishno(), CEnum.punishno.getRemark()));
		cmaps.add(new CMap(CEnum.punishorgan.getKey(), entity.getPunishorgan(), CEnum.punishorgan.getRemark()));
		cmaps.add(new CMap(CEnum.punishresult.getKey(), entity.getPunishresult(), CEnum.punishresult.getRemark()));
		cmaps.add(new CMap(CEnum.punishType.getKey(), entity.getPunishType(), CEnum.punishType.getRemark()));
		
		cmaps.add(new CMap(CEnum.fileid.getKey(), entity.getFileid(), CEnum.fileid.getRemark()));
		cmaps.add(new CMap(CEnum.corpid.getKey(), entity.getCorpid(), CEnum.corpid.getRemark()));
		cmaps.add(new CMap(CEnum.id.getKey(), entity.getId(), CEnum.id.getRemark()));
		return cmaps;
	}
}
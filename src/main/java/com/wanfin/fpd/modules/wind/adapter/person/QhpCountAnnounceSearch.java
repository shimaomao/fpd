/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.wind.adapter.person;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.wanfin.fpd.modules.sys.entity.Office;
import com.wanfin.fpd.modules.wind.adapter.CMap;
import com.wanfin.fpd.modules.wind.adapter.IAdapter;

/**
 * 前海-好信法院通-法院公告信息查询
 * @author srf
 * @version 2016-06-08
 */
public class QhpCountAnnounceSearch implements IAdapter<QhpCountAnnounceSearch>{
	private enum CEnum{
		//id, personid, resId, seqNo, courtNoticeId, noticeDocs, erCode, erMsg, office;
		
		id("id", "主键"),
		personid("personid", "个人主键"),
		resId("resId", "主键"),
		seqNo("seqNo", "序列号"),
		courtNoticeId("courtNoticeId", "法院公告ID"),
		noticeDocs("noticeDocs", "公告"),
		erCode("erCode", "错误代码"),
		erMsg("erMsg", "错误信息"),
		office("office", "部门"); 
		
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
	private String resId;		// qhzc_res_struct主键
	private String courtNoticeId;		// 法院公告ID
	private String seqNo;		// 序列号
	private List<QhpCountAnnosearNotice> noticeDocs;// 公告
	private String erCode;		// 错误代码
	private String erMsg;		// 错误信息
	private Office office;		// 部门ID
	private List<CMap> cmaps;
	
	public QhpCountAnnounceSearch() {
		super();
	}
	
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

	public String getResId() {
		return resId;
	}

	public void setResId(String resId) {
		this.resId = resId;
	}
	
	public String getCourtNoticeId() {
		return courtNoticeId;
	}

	public void setCourtNoticeId(String courtNoticeId) {
		this.courtNoticeId = courtNoticeId;
	}

	public String getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}

	public List<QhpCountAnnosearNotice> getNoticeDocs() {
		return noticeDocs;
	}

	public void setNoticeDocs(List<QhpCountAnnosearNotice> noticeDocs) {
		this.noticeDocs = noticeDocs;
	}

	public String getErCode() {
		return erCode;
	}

	public void setErCode(String erCode) {
		this.erCode = erCode;
	}

	public String getErMsg() {
		return erMsg;
	}

	public void setErMsg(String erMsg) {
		this.erMsg = erMsg;
	}

	public Office getOffice() {
		return office;
	}

	public void setOffice(Office office) {
		this.office = office;
	}

	public List<CMap> getCmaps() {
		return cmaps;
	}

	public void setCmaps(List<CMap> cmaps) {
		this.cmaps = cmaps;
	}

	@Override
	public List<QhpCountAnnounceSearch> init(JSONArray jarray) throws JSONException {
		List<QhpCountAnnounceSearch> entitys = new ArrayList<QhpCountAnnounceSearch>();
		for (int i = 0; i < jarray.length(); i++) {
			entitys.add(init(jarray.getJSONObject(i)));
			
		}
		return entitys;
	}
	
	@Override
	public QhpCountAnnounceSearch init(String data) throws JSONException {
		return init(new JSONObject(data));
	}

	private QhpCountAnnounceSearch init(JSONObject jo) throws JSONException {
		QhpCountAnnounceSearch entity = new QhpCountAnnounceSearch();
		entity.setCourtNoticeId(jo.optString(CEnum.courtNoticeId.getKey()));
		entity.setNoticeDocs(new QhpCountAnnosearNotice().init(jo.optJSONArray(CEnum.noticeDocs.getKey())));
		
		entity.setResId(jo.optString(CEnum.resId.getKey()));
		entity.setSeqNo(jo.optString(CEnum.seqNo.getKey()));
		entity.setErCode(jo.optString(CEnum.erCode.getKey()));
		entity.setErMsg(jo.optString(CEnum.erMsg.getKey()));
		entity.setOffice(new Office(jo.optString(CEnum.office.getKey())));
		
		entity.setPersonid(jo.optString(CEnum.personid.getKey()));
		entity.setId(jo.optString(CEnum.id.getKey()));

		entity.setCmaps(initCmaps(entity));
		return entity;
	}
	
	private List<CMap> initCmaps(QhpCountAnnounceSearch entity) {
		List<CMap> cmaps = new ArrayList<CMap>();
		cmaps.add(new CMap(CEnum.courtNoticeId.getKey(), entity.getCourtNoticeId(), CEnum.courtNoticeId.getRemark()));
		cmaps.add(new CMap(CEnum.noticeDocs.getKey(), entity.getNoticeDocs(), CEnum.noticeDocs.getRemark()));
		
		cmaps.add(new CMap(CEnum.erCode.getKey(), entity.getErCode(), CEnum.erCode.getRemark()));
		cmaps.add(new CMap(CEnum.erMsg.getKey(), entity.getErMsg(), CEnum.erMsg.getRemark()));
		cmaps.add(new CMap(CEnum.resId.getKey(), entity.getResId(), CEnum.resId.getRemark()));
		cmaps.add(new CMap(CEnum.seqNo.getKey(), entity.getSeqNo(), CEnum.seqNo.getRemark()));
		cmaps.add(new CMap(CEnum.office.getKey(), entity.getOffice(), CEnum.office.getRemark()));
		
		cmaps.add(new CMap(CEnum.personid.getKey(), entity.getPersonid(), CEnum.personid.getRemark()));
		cmaps.add(new CMap(CEnum.id.getKey(), entity.getId(), CEnum.id.getRemark()));
		return cmaps;
	}
}
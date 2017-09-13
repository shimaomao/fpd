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
 * 前海-商户银行卡评分数据
 * @author srf
 * @version 2016-06-08
 */
public class QhpBankScore implements IAdapter<QhpBankScore>{
	private enum CEnum{
		//id, personid, resId, seqNo, accountno, dcflag, summaryScore, cstScore, cotScore, cntScore, cnaScore, chvScore, dsiScore, rskScore, wlpScore, cnpScore, cotCluster, dsiCluster, rskCluster, crbScore, crbCluster, ercode, ermsg, office;
		
		id("id", "主键"),
		personid("personid", "个人主键"),
		resId("resId", "主键"),
		seqNo("seqNo", "序列号"),
		accountno("accountno", "银行卡号"),
		dcflag("dcflag", "借贷标记"),
		summaryScore("summaryScore", "银联智策消费综合评分"),
		cstScore("cstScore", "卡状态得分表"),
		cotScore("cotScore", "套现模型得分"),
		cntScore("cntScore", "消费趋势得分"),
		cnaScore("cnaScore", "消费能力得分"),
		chvScore("chvScore", "持卡人价值得分"),
		dsiScore("dsiScore", "消费自由度得分"),
		rskScore("rskScore", "风险得分"),
		wlpScore("wlpScore", "钱包位置得分"),
		cnpScore("cnpScore", "消费偏好得分"),
		cotCluster("cotCluster", "套现模型分类"),
		dsiCluster("dsiCluster", "消费自由度分类"),
		rskCluster("rskCluster", "风险得分分类"),
		crbScore("crbScore", "跨境得分"),
		crbCluster("crbCluster", "跨境得分分类"),
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
	private String seqNo;		// 序列号
	private String accountno;		// 银行卡号
	private String dcflag;		// 借贷标记
	private String summaryScore;		// 银联智策消费综合评分
	private String cstScore;		// 卡状态得分表
	private String cotScore;		// 套现模型得分
	private String cntScore;		// 消费趋势得分
	private String cnaScore;		// 消费能力得分
	private String chvScore;		// 持卡人价值得分
	private String dsiScore;		// 消费自由度得分
	private String rskScore;		// 风险得分
	private String wlpScore;		// 钱包位置得分
	private String cnpScore;		// 消费偏好得分
	private String cotCluster;		// 套现模型分类
	private String dsiCluster;		// 消费自由度分类
	private String rskCluster;		// 风险得分分类
	private String crbScore;		// 跨境得分
	private String crbCluster;		// 跨境得分分类
	private String erCode;		// 错误代码
	private String erMsg;		// 错误信息
	//private String organId;		// 公司ID
	private Office office;		// 部门ID
	private List<CMap> cmaps;
	
	public QhpBankScore() {
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

	public String getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}

	public String getAccountno() {
		return accountno;
	}

	public void setAccountno(String accountno) {
		this.accountno = accountno;
	}

	public String getDcflag() {
		return dcflag;
	}

	public void setDcflag(String dcflag) {
		this.dcflag = dcflag;
	}

	public String getSummaryScore() {
		return summaryScore;
	}

	public void setSummaryScore(String summaryScore) {
		this.summaryScore = summaryScore;
	}

	public String getCstScore() {
		return cstScore;
	}

	public void setCstScore(String cstScore) {
		this.cstScore = cstScore;
	}

	public String getCotScore() {
		return cotScore;
	}

	public void setCotScore(String cotScore) {
		this.cotScore = cotScore;
	}

	public String getCntScore() {
		return cntScore;
	}

	public void setCntScore(String cntScore) {
		this.cntScore = cntScore;
	}

	public String getCnaScore() {
		return cnaScore;
	}

	public void setCnaScore(String cnaScore) {
		this.cnaScore = cnaScore;
	}

	public String getChvScore() {
		return chvScore;
	}

	public void setChvScore(String chvScore) {
		this.chvScore = chvScore;
	}

	public String getDsiScore() {
		return dsiScore;
	}

	public void setDsiScore(String dsiScore) {
		this.dsiScore = dsiScore;
	}

	public String getRskScore() {
		return rskScore;
	}

	public void setRskScore(String rskScore) {
		this.rskScore = rskScore;
	}

	public String getWlpScore() {
		return wlpScore;
	}

	public void setWlpScore(String wlpScore) {
		this.wlpScore = wlpScore;
	}

	public String getCnpScore() {
		return cnpScore;
	}

	public void setCnpScore(String cnpScore) {
		this.cnpScore = cnpScore;
	}

	public String getCotCluster() {
		return cotCluster;
	}

	public void setCotCluster(String cotCluster) {
		this.cotCluster = cotCluster;
	}

	public String getDsiCluster() {
		return dsiCluster;
	}

	public void setDsiCluster(String dsiCluster) {
		this.dsiCluster = dsiCluster;
	}

	public String getRskCluster() {
		return rskCluster;
	}

	public void setRskCluster(String rskCluster) {
		this.rskCluster = rskCluster;
	}

	public String getCrbScore() {
		return crbScore;
	}

	public void setCrbScore(String crbScore) {
		this.crbScore = crbScore;
	}

	public String getCrbCluster() {
		return crbCluster;
	}

	public void setCrbCluster(String crbCluster) {
		this.crbCluster = crbCluster;
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
	public List<QhpBankScore> init(JSONArray jarray) throws JSONException {
		List<QhpBankScore> entitys = new ArrayList<QhpBankScore>();
		for (int i = 0; i < jarray.length(); i++) {
			entitys.add(init(jarray.getJSONObject(i)));
			
		}
		return entitys;
	}
	
	@Override
	public QhpBankScore init(String data) throws JSONException {
		return init(new JSONObject(data));
	}

	private QhpBankScore init(JSONObject jo) throws JSONException {
		QhpBankScore entity = new QhpBankScore();
		entity.setAccountno(jo.optString(CEnum.accountno.getKey()));
		entity.setChvScore(jo.optString(CEnum.chvScore.getKey()));
		entity.setCnaScore(jo.optString(CEnum.cnaScore.getKey()));
		entity.setCnpScore(jo.optString(CEnum.cnpScore.getKey()));
		entity.setCntScore(jo.optString(CEnum.cntScore.getKey()));
		entity.setCotCluster(jo.optString(CEnum.cotCluster.getKey()));
		entity.setCotScore(jo.optString(CEnum.cotScore.getKey()));

		entity.setCrbCluster(jo.optString(CEnum.crbCluster.getKey()));
		entity.setCrbScore(jo.optString(CEnum.cotScore.getKey()));
		entity.setCstScore(jo.optString(CEnum.cotScore.getKey()));
		entity.setDcflag(jo.optString(CEnum.cotScore.getKey()));
		entity.setDsiCluster(jo.optString(CEnum.cotScore.getKey()));
		entity.setDsiScore(jo.optString(CEnum.cotScore.getKey()));
		
		entity.setRskCluster(jo.optString(CEnum.cotScore.getKey()));
		entity.setRskScore(jo.optString(CEnum.cotScore.getKey()));
		entity.setSummaryScore(jo.optString(CEnum.cotScore.getKey()));
		entity.setWlpScore(jo.optString(CEnum.cotScore.getKey()));
		
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
	
	private List<CMap> initCmaps(QhpBankScore entity) {
		List<CMap> cmaps = new ArrayList<CMap>();
		cmaps.add(new CMap(CEnum.accountno.getKey(), entity.getAccountno(), CEnum.accountno.getRemark()));
		cmaps.add(new CMap(CEnum.chvScore.getKey(), entity.getChvScore(), CEnum.chvScore.getRemark()));
		cmaps.add(new CMap(CEnum.cnaScore.getKey(), entity.getCnaScore(), CEnum.cnaScore.getRemark()));
		cmaps.add(new CMap(CEnum.cnpScore.getKey(), entity.getCnpScore(), CEnum.cnpScore.getRemark()));
		cmaps.add(new CMap(CEnum.cntScore.getKey(), entity.getCntScore(), CEnum.cntScore.getRemark()));
		cmaps.add(new CMap(CEnum.cotCluster.getKey(), entity.getCotCluster(), CEnum.cotCluster.getRemark()));
		cmaps.add(new CMap(CEnum.cotScore.getKey(), entity.getCotScore(), CEnum.cotScore.getRemark()));

		cmaps.add(new CMap(CEnum.crbCluster.getKey(), entity.getCotScore(), CEnum.cotScore.getRemark()));
		cmaps.add(new CMap(CEnum.crbScore.getKey(), entity.getCotScore(), CEnum.cotScore.getRemark()));
		cmaps.add(new CMap(CEnum.cstScore.getKey(), entity.getCotScore(), CEnum.cotScore.getRemark()));
		cmaps.add(new CMap(CEnum.dcflag.getKey(), entity.getCotScore(), CEnum.cotScore.getRemark()));
		cmaps.add(new CMap(CEnum.dsiCluster.getKey(), entity.getCotScore(), CEnum.cotScore.getRemark()));
		cmaps.add(new CMap(CEnum.dsiScore.getKey(), entity.getCotScore(), CEnum.dsiScore.getRemark()));
		cmaps.add(new CMap(CEnum.rskCluster.getKey(), entity.getCotScore(), CEnum.rskCluster.getRemark()));
		cmaps.add(new CMap(CEnum.rskScore.getKey(), entity.getCotScore(), CEnum.rskScore.getRemark()));
		cmaps.add(new CMap(CEnum.summaryScore.getKey(), entity.getCotScore(), CEnum.summaryScore.getRemark()));
		cmaps.add(new CMap(CEnum.wlpScore.getKey(), entity.getCotScore(), CEnum.wlpScore.getRemark()));

		cmaps.add(new CMap(CEnum.erCode.getKey(), entity.getCotScore(), CEnum.erCode.getRemark()));
		cmaps.add(new CMap(CEnum.erMsg.getKey(), entity.getCotScore(), CEnum.erMsg.getRemark()));
		cmaps.add(new CMap(CEnum.resId.getKey(), entity.getResId(), CEnum.resId.getRemark()));
		cmaps.add(new CMap(CEnum.seqNo.getKey(), entity.getCotScore(), CEnum.seqNo.getRemark()));
		cmaps.add(new CMap(CEnum.office.getKey(), entity.getOffice(), CEnum.office.getRemark()));
		
		cmaps.add(new CMap(CEnum.personid.getKey(), entity.getPersonid(), CEnum.personid.getRemark()));
		cmaps.add(new CMap(CEnum.id.getKey(), entity.getId(), CEnum.id.getRemark()));
		return cmaps;
	}
}
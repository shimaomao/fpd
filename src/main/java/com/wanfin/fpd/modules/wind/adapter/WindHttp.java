/**  
 * @Project fpd 
 * @Title WindHttpStatus.java
 * @Package com.wanfin.fpd.modules.wind.vo
 * @Description [[_xxx_]]文件 
 * @author Chenh
 * @date 2016年5月27日 下午4:47:05 
 * @version V1.0   
 */
package com.wanfin.fpd.modules.wind.adapter;

import org.json.JSONException;
import org.json.JSONObject;

import com.wanfin.fpd.common.config.SvalBase;
import com.wanfin.fpd.common.utils.StringUtils;

/**
 * @Description [[_xxx_]] WindHttpStatus类
 * @author Chenh
 * @date 2016年5月27日 下午4:47:05
 */
public class WindHttp {
	public static final String WT_ALL = "all";
	public static final String WT_PERSON = "person";
	public static final String WT_CORPORATION = "corporation";
	public static final String WT_MOBILE = "mobile";
	public static final String WT_RISK = "risk";
	public static class WHKey {
		/** 结果状态：true,false */
		public static final String CODE = "returnCode";
		/** 结果状态：true,false */
		public static final String CODE_WZ = "statusCode";
		/** 结果消息 */
		public static final String MESSAGE = SvalBase.JsonKey.KEY_MSG;
		/** 结果内容 */
		public static final String RESULT = SvalBase.JsonKey.KEY_RESULT;
	}

	/**
	 * 接口返回状态码
	 * 
	 * @author user
	 *
	 */
	public static class WHCode {
		/** 成功 */
		public static final String SUCCESS = "200";
		/** 成功 */
		public static final String SUCCESS_WZ = "100";
		/** 没有查询到对应数据 */
		public static final String NO_DATA = "101";
		/** 用户名密码错误 */
		public static final String ERR_ACCOUNT = "102";
		/** token无效 */
		public static final String ERR_TOKEN = "103";
		/** 参数错误 */
		public static final String ERR_PATAMS = "104";
		/** 参数为空 */
		public static final String ERR_NUll = "105";
		/** 服务器忙 */
		public static final String ERR_BUSY = "141";
		/** 异常 */
		public static final String ERR_EXCEP = "142";
		/** 其他 */
		public static final String ERR_OTHER = "144";
	}
	
//	/**
//	 * 征信枚举
//	 */
//	public static enum CreditChecking {
//		C_PERSON_ALL(WindHttp.WT_PERSON, WindHttp.WT_ALL, "个人"),
//		C_CORPORATION_ALL(WindHttp.WT_CORPORATION, WindHttp.WT_ALL, "机构");
//		/**
//		 * @Description C属性
//		 * @Fields String C
//		 * @author Chenh
//		 * @date 2016年6月1日 下午2:47:43 
//		 */
//		
//		public static final String C = "c_";
//		private String type;//类别
//		private String sub;//子分类
//		private String remark;//子分类
//		private CreditChecking(String type, String sub, String remark) {
//			this.type = type;
//			this.sub = sub;
//			this.remark = remark;
//		}
//		
//		public static CreditChecking[] init(String type, List<Map<String, String>> vals) {
//			if(vals != null){
//				for (Map<String, String> val : vals) {
//					Set<String> vkeys = val.keySet();
//					for(String key : vkeys) {
//						if(CreditChecking.getCreditCheckingByTypeAndSub(type, key) == null){
//							EnumUtils.addEnum(CreditChecking.class, C+StringUtils.upperCase(type)+"_"+StringUtils.upperCase(key), new Class[]{String.class, String.class, String.class}, new Object[]{type, key, val.get(key)});
//						}
//					}
//				}
//			}
//			return CreditChecking.values();
//		}
//
//		public static CreditChecking[] initByVo(String type, WindVo vo) {
//			if((vo != null) && (vo.getReturnCode()).equals(WHCode.SUCCESS)){
//				List<Map<String, String>> vals = new ArrayList<Map<String,String>>();
//				JSONObject voResult = vo.getResult();
//				Iterator<?> itkeys = vo.getResult().keys();
//				try {
//			        while(itkeys.hasNext()) {
//			            String key = (String) itkeys.next();
//			            String value = voResult.getString(key);//类型强转失败会异常
//			            
//			            Map<String, String> valMap = new HashMap<String, String>();
//			            valMap.put(key, value);
//			            vals.add(valMap);
//			        }
//				} catch (JSONException e) {
//					e.printStackTrace();
//				}
//				return init(type, vals);
//			}
//			return init(type, null);
//		}
//		
//		public static List<CreditChecking> getCreditCheckingByType(String type) {
//			List<CreditChecking>  creditCheckings = new ArrayList<CreditChecking>();
//			CreditChecking[] credits = CreditChecking.values();
//			for (CreditChecking credit : credits) {
//				if((credit.getType()).equals(type)){
//					creditCheckings.add(credit);
//				}
//			}
//			return creditCheckings;
//		}
//
//		public static CreditChecking getCreditCheckingByTypeAndSub(String type, String sub) {
//			CreditChecking[] credits = CreditChecking.values();
//			for (CreditChecking credit : credits) {
//				if(((credit.getType()).equals(type)) && ((credit.getSub()).equals(sub))){
//					return credit;
//				}
//			}
//			return null;
//		}
//
//		public String getType() {
//			return type;
//		}
//
//		public String getSub() {
//			return sub;
//		}
//
//		public void setType(String type) {
//			this.type = type;
//		}
//
//		public void setSub(String sub) {
//			this.sub = sub;
//		}
//
//		public String getRemark() {
//			return remark;
//		}
//
//		public void setRemark(String remark) {
//			this.remark = remark;
//		}
//	}
	
	/**
	 * @Description 状态处理
	 * @author Chenh 
	 * @date 2016年5月27日 下午4:49:34  
	 */
	public static WindVo dealHttp(IAdapterDb iAdapterDb, String json) {
		WindVo windVo = new WindVo();
		return convertWindVo(iAdapterDb, json, windVo);
	}
	
	/**
	 * @Description 状态处理
	 * @author Chenh 
	 * @date 2016年5月27日 下午4:49:34  
	 */
	public static WindVo dealHttp(IAdapterDb iAdapterDb, String relId, String json) {
		WindVo windVo = new WindVo();
		windVo.setRelId(relId);
		return convertWindVo(iAdapterDb, json, windVo);
	}

	/**
	 * @Description Json转换为WindVo
	 * @param json
	 * @param windVo
	 * @return
	 * @author Chenh 
	 * @date 2016年6月1日 下午12:51:42
	 */
	private static WindVo convertWindVo(IAdapterDb iAdapterDb, String json, WindVo windVo) {
		if(StringUtils.isNotEmpty(json)){
			if(windVo == null){
				windVo = new WindVo();
			}
			if(iAdapterDb != null){
				try {
					JSONObject jo =	new JSONObject(json);
					windVo.setMessage(jo.optString(WindHttp.WHKey.MESSAGE));
					
					/**
					 * WZ：JSON结构中ReturnCode=100标识成功
					 * 为了实现统一，当状态为100时变更其状态为200
					 */
					if((iAdapterDb.getDb()).equals(IeAdapterDB.DB_WZ)){
						windVo.setReturnCode(jo.optString(WindHttp.WHKey.CODE));
						if((windVo.getReturnCode()).equals(WindHttp.WHCode.SUCCESS_WZ)){
							windVo.setReturnCode(WindHttp.WHCode.SUCCESS);
						}
					}else{
						windVo.setReturnCode(jo.optString(WindHttp.WHKey.CODE_WZ));
					}
					
					if((windVo.getReturnCode()).equals(WindHttp.WHCode.SUCCESS)){
						windVo.setResult(jo.getJSONObject(WindHttp.WHKey.RESULT));
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
				return windVo;
			}
		}
		return null;
	}
}

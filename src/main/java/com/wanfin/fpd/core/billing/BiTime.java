package com.wanfin.fpd.core.billing;

import java.util.Date;

import com.wanfin.fpd.common.utils.DateUtils;
import com.wanfin.fpd.modules.billing.entity.rule.BiRule;


/**
 * 及时转换单位枚举
 * @author Chenh
 */
public enum BiTime {
	HOURS(1, "hours", "小时"),
	DAY(2, "day", "天"),
	WEEK(3, "week", "周"),
	MONTH(4, "month", "月"),
	QUARTER(5, "quarter", "季度"),
	YEAR(6, "year", "年");
	private Integer key;
	private String name;
	private String cname;
	private BiTime(Integer key, String name, String cname) {
		this.key = key;
		this.name = name;
		this.cname = cname;
	}
	public Integer getKey() {
		return key;
	}
	public void setKey(Integer key) {
		this.key = key;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	/**
	 * 获取枚举Key
	 * @param db
	 * @return
	 */
	public static BiTime getByKey(Integer unit) {
		BiTime[] biTimes = BiTime.values();
		for (BiTime biTime : biTimes) {
			if((biTime.getKey()).equals(unit)){
				return biTime;
			}
		}
		return null;
	}
	
	/**
	 * 计算小时数
	 * @param biRule
	 * @param curDate
	 * @return
	 */
	public static double calculateHours(BiRule biRule) {
		Date newDate = null;
		Date curDate = new Date();
		BiTime biTime = BiTime.getByKey(biRule.getUnit());
		if((biTime).equals(BiTime.YEAR)){
			newDate = DateUtils.calculateDate(curDate, biRule.getUnitVal(), 0, 0);
		}else if((biTime).equals(BiTime.QUARTER)){
			newDate = DateUtils.calculateDate(curDate, 0, biRule.getUnitVal()*3, 0);
		}else if((biTime).equals(BiTime.MONTH)){
			newDate = DateUtils.calculateDate(curDate, 0, biRule.getUnitVal(), 0);
		}else if((biTime).equals(BiTime.WEEK)){
			newDate = DateUtils.calculateDate(curDate, 0, 0, biRule.getUnitVal()*7);
		}else if((biTime).equals(BiTime.HOURS)){
			newDate = DateUtils.calculateDate(curDate, 0, 0, 0, biRule.getUnitVal(), 0, 0);
		}else if((biTime).equals(BiTime.DAY)){
			newDate = DateUtils.calculateDate(curDate, 0, 0, biRule.getUnitVal());
		}
		return DateUtils.getDistanceHoursOfTwoDate(curDate, newDate);
	}
}

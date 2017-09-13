/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.product.service;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.wanfin.fpd.common.config.Cons.Ips;
import com.wanfin.fpd.common.utils.InterfaceUtil;
import com.wanfin.fpd.common.utils.StringUtils;
import com.wanfin.fpd.modules.product.vo.WProduct;
import com.wanfin.fpd.modules.sys.utils.UserUtils;

/**
 * 产品管理Service
 * @author lx
 * @version 2016-03-23
 */
@Service
public class WProductService{
	public static final String W_IP = Ips.IP_WD;
	public static final String W_PRODUCTS = W_IP+"/Index/Test/loan/wtypeId/";
	 
	public List<WProduct> findList(){  
		try {
			String wproductsJson = InterfaceUtil.sendGet(W_PRODUCTS+"/"+UserUtils.getUser().getCompany().getId(), "");
			if(StringUtils.isNotEmpty(wproductsJson)){
				wproductsJson = new String(wproductsJson.getBytes(), "UTF-8");
//				wproductsJson = "[{\"id\":\"1\",\"agency_id\":\"5288\",\"cat_id\":\"38\",\"summary\":\"放款快、服务好、利息省\",\"loan_name\":\"北京奔驰 C级\",\"loan_img\":\"\\\\\\\\loan\\\\\\\\2016-06-21/2016062115334814452.png\",\"loan_fee\":\"3.50\",\"installment_min\":\"8\",\"installment_max\":\"18\",\"repay_way\":\"1\",\"amount_min\":\"8000.00\",\"amount_max\":\"18000.00\",\"monthly_rate\":\"0.02\",\"monthly_service_name\":\"月保管费\",\"monthly_service_rate\":\"0.01\",\"monthly_total_rate\":\"0.03\",\"prepay\":\"1\",\"prepay_rate\":\"0.01\",\"grace_period\":\"5\",\"grace_period_rate\":\"0.01\",\"overdue_rate\":\"0.01\",\"on_sale\":\"1\",\"add_time\":\"1466494452\",\"update_time\":\"0\",\"sales\":\"0\",\"hot\":\"1\",\"attach\":\"a:2:{s:6:\\\"person\\\";a:3:{i:0;s:1:\\\"2\\\";i:1;s:1:\\\"6\\\";i:2;s:2:\\\"10\\\";}s:7:\\\"company\\\";a:2:{i:0;s:2:\\\"15\\\";i:1;s:2:\\\"19\\\";}}\",\"contract_id\":\"5\",\"operate_uid\":\"16\"},{\"id\":\"2\",\"agency_id\":\"5288\",\"cat_id\":\"39\",\"summary\":\"前瞻的安全思考与成熟的领先技术，创造出更值得信赖的舒心生活。奥迪，国际著名豪华汽车的标志，秉承着进取，尊贵，动感三大核心价值!\",\"loan_name\":\"奥迪A6\",\"loan_img\":\"\\\\\\\\loan\\\\\\\\2016-06-23/2016062317433499494.png\",\"loan_fee\":\"20.00\",\"installment_min\":\"6\",\"installment_max\":\"12\",\"repay_way\":\"3\",\"amount_min\":\"50000.00\",\"amount_max\":\"150000.00\",\"monthly_rate\":\"0.05\",\"monthly_service_name\":\"月保管费\",\"monthly_service_rate\":\"0.03\",\"monthly_total_rate\":\"0.08\",\"prepay\":\"0\",\"prepay_rate\":\"0.02\",\"grace_period\":\"6\",\"grace_period_rate\":\"0.02\",\"overdue_rate\":\"0.03\",\"on_sale\":\"1\",\"add_time\":\"1466675125\",\"update_time\":\"0\",\"sales\":\"0\",\"hot\":\"1\",\"attach\":\"a:3:{s:6:\\\"person\\\";a:2:{i:0;s:1:\\\"5\\\";i:1;s:2:\\\"10\\\";}s:7:\\\"company\\\";a:1:{i:0;s:2:\\\"20\\\";}s:6:\\\"common\\\";a:1:{i:0;s:2:\\\"25\\\";}}\",\"contract_id\":\"5\",\"operate_uid\":\"16\"}]";
	
				wproductsJson = StringEscapeUtils.unescapeEcmaScript(wproductsJson);
				return (ArrayList<WProduct>) JSON.parseArray(wproductsJson, WProduct.class);
			}
		} catch (Exception e) {
			e.printStackTrace(); 
			return new ArrayList<WProduct>();
		}
		return new ArrayList<WProduct>();
	}
}
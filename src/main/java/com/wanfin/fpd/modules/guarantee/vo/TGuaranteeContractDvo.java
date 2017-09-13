/**  
 * @Project jeesite 
 * @Title LoanContractDvo.java
 * @Package com.wanfin.fpd.modules.contract.vo
 * @Description [[_担保合同数据_]]文件 
 * @author Chenh
 * @date 2016年3月31日 下午12:39:27 
 * @version V1.0   
 */ 
package com.wanfin.fpd.modules.guarantee.vo;

import java.util.HashMap;
import java.util.Map;

import com.wanfin.fpd.modules.contract.IContractData;
import com.wanfin.fpd.modules.guarantee.entity.TGuaranteeContract;

/**
 * @Description [[_担保合同数据_]] LoanContractDvo类
 * @author Chenh
 * @date 2016年3月31日 下午12:39:27 
 */
public class TGuaranteeContractDvo implements IContractData<TGuaranteeContract>{
	@Override
	public Map<String, Object> getParams(TGuaranteeContract entity) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("address", entity.getAddress());
		params.put("cardNum", entity.getCardNum());
		return params;
	}
}

/**  
 * @Project jeesite 
 * @Title LoanContractDvo.java
 * @Package com.wanfin.fpd.modules.contract.vo
 * @Description [[_贷款合同数据_]]文件 
 * @author Chenh
 * @date 2016年3月31日 下午12:39:27 
 * @version V1.0   
 */ 
package com.wanfin.fpd.modules.contract.vo;

import java.util.HashMap;
import java.util.Map;

import com.wanfin.fpd.modules.contract.IContractData;
import com.wanfin.fpd.modules.contract.entity.TLoanContract;

/**
 * @Description [[_贷款合同数据_]] LoanContractDvo类
 * @author Chenh
 * @date 2016年3月31日 下午12:39:27 
 */
public class TLoanContractDvo implements IContractData<TLoanContract>{
	@Override
	public Map<String, Object> getParams(TLoanContract entity) {
		Map<String, Object> params = new HashMap<String, Object>();
		if(entity.getApplyDate()!=null){
			params.put("applyDate", entity.getApplyDate().toString());
		}
		if(entity.getArea()!=null){
			params.put("area", entity.getArea().getDbName());
		}
		if(entity.getContractNumber()!=null){
			params.put("contractNumber", entity.getContractNumber());
		}
		return params;
	}
}

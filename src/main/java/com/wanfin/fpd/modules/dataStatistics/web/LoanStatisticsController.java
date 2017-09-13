package com.wanfin.fpd.modules.dataStatistics.web;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.mangofactory.swagger.annotations.ApiIgnore;
import com.wanfin.fpd.common.config.Cons;
import com.wanfin.fpd.common.config.SvalBase;
import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.web.BaseController;
import com.wanfin.fpd.modules.contract.entity.TLoanContract;
import com.wanfin.fpd.modules.contract.service.TLoanContractService;
import com.wanfin.fpd.modules.dataStatistics.entity.LoanStatistics;
import com.wanfin.fpd.modules.dataStatistics.service.LoanStatisticsService;
import com.wanfin.fpd.modules.product.entity.TProduct;
import com.wanfin.fpd.modules.product.service.TProductService;
import com.wanfin.fpd.modules.sys.utils.UserUtils;

@ApiIgnore
@Controller
@RequestMapping(value = "${adminPath}/dataStatistics/loanBalanceStatistics")
public class LoanStatisticsController extends BaseController {
	@Autowired
	private LoanStatisticsService loanStatisticsService;
	
	@Autowired
	private TLoanContractService tLoanContractService;
	

	@RequiresPermissions("dataStatistics:loanBalanceStatistics:view")
	@RequestMapping(value = { "list", "" })
	public String list(HttpServletRequest request, HttpServletResponse response, Model model){
		
		List<LoanStatistics> loanStatisticsList = loanStatisticsService.repaymentPlanAmount();
		
		model.addAttribute("loanStatisticsList", loanStatisticsList);
		
		return "modules/dataStatistics/LoanStatistics";
	}
	
	@RequestMapping(value = "loan")
	@ResponseBody
	public Map<String, Object> loan(HttpServletRequest request, HttpServletResponse response, Model model) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put(SvalBase.JsonKey.KEY_STATUS, true);
		
		List<LoanStatistics> list = loanStatisticsService.repaymentPlanAmount();
		System.out.println("list="+list.size());
		return result;
	}
	
	
	/**
	 * @Description 获取产品统计分析数据
	 * @param tProduct
	 * @return
	 * @author lx
	 * @date 
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "statisticAnalysis")
	public String statisticAnalysis(TLoanContract tLoanContract, HttpServletRequest request, HttpServletResponse response, Model model) {
		TProduct tProduct = new TProduct((String)UserUtils.getCache(UserUtils.CACHE_SYSCODE));
		Calendar cal = Calendar.getInstance();
	    int month = cal.get(Calendar.MONTH) + 1;
	    int year = cal.get(Calendar.YEAR);
		
		if(tLoanContract.getStarttime()==null||tLoanContract.getEndtime()==null){//业务统计分析，如果没有选择起止时间，列表默认当前月份数据
			tLoanContract.setLoanDate(new Date());
			 tLoanContract.setSearchtime(year+"-%"+month+"-%");//（当月份小于10时，用%凑一个位数）
			 model.addAttribute("now_time",year+"年"+month+"月");
		}else{//业务统计分析，选择了起止时间
			cal.setTime(tLoanContract.getEndtime());
			 month = cal.get(Calendar.MONTH)+1;
			 year = cal.get(Calendar.YEAR);
			 tLoanContract.setSearchtime(year+"-%"+month+"-%");
			 
			 //用于LoanStatistics.jsp页面的显示
			 cal.setTime(tLoanContract.getStarttime());
			 model.addAttribute("now_time",cal.get(Calendar.YEAR)+"年"+(cal.get(Calendar.MONTH)+1)+"月"+"-"+year+"年"+month+"月");
		}
		tLoanContract.setProductId(tProduct.getId());
		Page<TLoanContract> page = tLoanContractService.findPage(new Page<TLoanContract>(request, response,20), tLoanContract); 
		
		List<JSONObject> listJson = Lists.newArrayList();
		Record record = new Record();
		
		String str = "";
		
		if(tLoanContract.getLoanAmount()!=null&&!tLoanContract.getLoanAmount().equals("")){
			str = " and loan_amount = "+tLoanContract.getLoanAmount();
		}
		if(tLoanContract.getLoanPeriod()!=null&&!tLoanContract.getLoanPeriod().equals("")){
			str += " and loan_period = "+tLoanContract.getLoanPeriod();		
		}
		if(tLoanContract.getPeriodType()!=null&&!tLoanContract.getPeriodType().equals("")){
			str += " and period_type = "+tLoanContract.getPeriodType();	
		}
		if(tLoanContract.getLoanRate()!=null&&!tLoanContract.getLoanRate().equals("")){
			str += " and loan_rate = "+tLoanContract.getLoanRate();	
		}
		if(tLoanContract.getStatus()!=null&&!tLoanContract.getStatus().equals("")){
			str += " and status = '"+tLoanContract.getStatus()+"'";	
		}
		
		//累计发生金额，最高金额，最低金额 ,业务笔数，
	    String sql = "SELECT "+
	   		    " ROUND(SUM(c.loan_amount),2) AS sumAmount,"+
				" ROUND(MAX(c.loan_amount),2) AS maxAmount,"+
				" ROUND(MIN(c.loan_amount),2) AS minAmount,"+
				" COUNT(c.id) AS count"+
				" FROM t_loan_contract c WHERE c.del_flag = '0'"+
				" AND c.product_id = ?"+
                " AND loan_date like ? "+str+"";
	    System.out.println(sql);
		for (int i = 1; i <= 12; i++) {
				record.setColumns(Db.findFirst(sql,tProduct.getId(),""+year+"-%"+i+"-%"));
			try {
				listJson.add(new JSONObject(record.toJson().replace("null", "0")));
			} catch (JSONException e) {
				e.printStackTrace();
				listJson.add(new JSONObject());
			}
		}
			
		model.addAttribute("listJson", listJson);
		model.addAttribute("page", page);
		model.addAttribute("tLoanContract",tLoanContract);
		 model.addAttribute("year",year);
		return "modules/dataStatistics/LoanStatistics";
	}
	
	public static void main(String[] args) {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date("Wed Jun 01 00:00:00 CST 2016"));
		int month = c.get(Calendar.MONTH)+1;
		 int year = c.get(Calendar.YEAR);
		 int day = c.get(Calendar.DAY_OF_MONTH);
		 System.out.println(year+"-"+month+"-"+day);
	}
}

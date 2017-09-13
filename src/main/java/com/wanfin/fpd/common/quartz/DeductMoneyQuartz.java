package com.wanfin.fpd.common.quartz;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wanfin.fpd.common.utils.StringUtils;
import com.wanfin.fpd.modules.wish.utils.AmountUtil;
import net.sf.json.JSONObject;

import org.apache.commons.collections.map.HashedMap;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONArray;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonArrayFormatVisitor;
import com.jcraft.jsch.ChannelSftp;
import com.wanfin.fpd.common.config.Cons;
import com.wanfin.fpd.common.quartz.ftp.DateUtil;
import com.wanfin.fpd.common.utils.DateUtils;
import com.wanfin.fpd.modules.api.wiss.entity.PayecoBackParams;
import com.wanfin.fpd.modules.api.wiss.service.InteractionService;
import com.wanfin.fpd.modules.wish.contract.vo.RepayRecordVo;
import com.wanfin.fpd.modules.wish.order.entity.ReturnedMoney;
import com.wanfin.fpd.modules.wish.order.service.ReturnedMoneyService;
import com.wanfin.fpd.modules.wish.utils.ExcelUtils;
import com.wanfin.fpd.modules.wish.utils.SftpUtil;

public class DeductMoneyQuartz {

	@Autowired
	public ReturnedMoneyService returnedMoneyService;

	@Autowired
	private InteractionService interactionService;

	/**
	 * 8.2 扣款报表  不是定时器，直接被调用
	 * @throws ParseException 
	 * 
	 */
	public Map<String,String> execute() throws ParseException{

		BigDecimal hundred = new BigDecimal("100");

		Map<String,String> map= new HashMap<>();
		ReturnedMoney entity = new ReturnedMoney();
		entity.setIsDeal("1");
		entity.setStatus("0");
		List<ReturnedMoney> list = returnedMoneyService.findList(entity);
		try {
			if (list.isEmpty()){
				map.put("message", "暂无打款数据");
				return map;
			}
			String filePath = "/logs/files/kk/" + DateUtil.getCurrentYearMonthDay()+".xlsx";

			//准备数据
			//第一行属性
			 //组装数据测试
	        List<String[]> strList = new ArrayList<String[]>();
	        //第一行属性
	        String[] one = {"当次还款人数","当次扣款总金额","当次罚息总金额","回款报表文件名"};
	        strList.add(one);
			//第二行数据
	    	//第二行数据
			Map<String, Object> mapData = new HashedMap();
			ReturnedMoney returnMoney = null;
			BigDecimal repayMoney = new BigDecimal("0");
			BigDecimal sumFee = new BigDecimal("0");
			for(int i =0; i <list.size(); i++){
				returnMoney = list.get(i);
				String loanMoney = returnMoney.getRepayLoanMoney();
				String fee = returnMoney.getFee();
				repayMoney = repayMoney.add(new BigDecimal(loanMoney));
				sumFee = sumFee.add(new BigDecimal(fee));
			}
			String[] two = {String.valueOf(list.size()), AmountUtil.yuanToFen(repayMoney), AmountUtil.yuanToFen(sumFee), list.get(0).getFileName()};
			strList.add(two);
			//第三行属性
			String[] three = {"借款人id","借款人姓名","借款人银行卡号","当次扣款金额","当次罚息金额","代收订单号"};
			strList.add(three);
			//第四行以后的数据
			String[] four = new String[three.length];
			for (int j=0; j<list.size(); j++){
				returnMoney = list.get(j);
				four[0] = returnMoney.getUserId();
				four[1] = returnMoney.getUserName();
				four[2] = returnMoney.getAccountNum();
				four[3] = AmountUtil.yuanToFen(returnMoney.getRepayLoanMoney());
				four[4] = AmountUtil.yuanToFen(returnMoney.getFee());
				four[5] = returnMoney.getOrderId();
				strList.add(four);

			}
			
			OutputStream os = new FileOutputStream(filePath);
			ExcelUtils.exportExcelXTest(strList,null,0,os);


			ChannelSftp sftp = SftpUtil.getSftpConnect(Cons.SftpPayeco.IP, Cons.SftpPayeco.PORT, Cons.SftpPayeco.ACCOUNT, Cons.SftpPayeco.PASSWORD);   
			System.out.println("上传文件开始...");  
			//SftpUtil.uploadFile(filePath, "upload/repay", "YLZF_REPAY_"+DateUtil.getCurrentYearMonthDay()+".xlsx", sftp);
			String uploadFileName = "YLZF_DEDUCTIONS_"+DateUtils.getDate("yyyyMMdd")+".xlsx";
			SftpUtil.uploadFile(filePath, "upload/kk", uploadFileName, sftp);
			System.out.println("上传成功...");  
//			file.delete();  
//			System.out.println("删除完成，开始校验本地文件...");  
			SftpUtil.exit(sftp);  
			
			
			//通知易联
			String tradeId = "kkNotify";
			JSONObject reqBody = new JSONObject();
			//reqBody.put("filePath", "/upload/repay/"+"YLZF_REPAY_"+DateUtil.getCurrentYearMonthDay()+".xlsx");
	        reqBody.put("filePath", uploadFileName);
	  
	        PayecoBackParams payecoBackParams = null;
	        boolean flag = true;
	        
	        int num = 0;
	        while(flag){
	        	payecoBackParams = interactionService.getPayecoRequestByParams(tradeId, reqBody);
	        	if (payecoBackParams != null && payecoBackParams.getHeader() != null && payecoBackParams.getHeader().getResCode() != null && "0000".equals(payecoBackParams.getHeader().getResCode())) {
					flag = false;
				} else {
					flag = true;
				}
	        	num++;
	        	if (num>5) {
	        		map.put("msg", "通知易联失败！");
					return map;
				}
	        }

	        
	        //发送报表后更新信息状态为1
	        
	        for (int z = 0; z < list.size(); z++) {
				returnMoney = list.get(z);
				returnMoney.setStatus("1");
	        	returnedMoneyService.save(returnMoney);
			}

			//通知p2p还款通知

	        map.put("msg", "success");
		} catch (Exception e) {
			map.put("msg", "false");
			e.printStackTrace();
		}
		return map;
	}


	/**
	 * 获取金额转分*100
	 *
	 * @param hundred
	 * @param sumLoanAmount
	 * @return
	 */
	private String getAmount(BigDecimal hundred, String sumLoanAmount) {
		if (StringUtils.isEmpty(sumLoanAmount)) {
			sumLoanAmount = "0";
		} else {
			BigDecimal bigSumLoanAmount = new BigDecimal(sumLoanAmount);
			sumLoanAmount = String.valueOf(bigSumLoanAmount.multiply(hundred));
		}
		return sumLoanAmount;
	}

}

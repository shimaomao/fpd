package com.wanfin.fpd.common.quartz;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.activiti.engine.impl.util.json.HTTP;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;

import com.jcraft.jsch.ChannelSftp;
import com.wanfin.fpd.common.config.Cons;
import com.wanfin.fpd.common.quartz.ftp.DateUtil;
import com.wanfin.fpd.modules.api.wiss.entity.HkInform;
import com.wanfin.fpd.modules.api.wiss.service.HkInformService;
import com.wanfin.fpd.modules.wish.order.entity.ReturnedMoney;
import com.wanfin.fpd.modules.wish.order.service.ReturnedMoneyService;
import com.wanfin.fpd.modules.wish.utils.PoiUtil;
import com.wanfin.fpd.modules.wish.utils.SftpUtil;

public class RepayQuartz {

	@Autowired
	public HkInformService hkInformService;
	@Autowired
	public ReturnedMoneyService returnedMoneyService;

	/**
	 * 8.3 扣款通知
	 */
	public void execute(){

		List<ReturnedMoney> list = returnedMoneyService.findList(new ReturnedMoney());

		ReturnedMoney returnMoney = null;

		try {
			String encoding = "UTF-8" ;

			//连接sftp服务器
			//			File file = new File(path+"//"+fileName);
			//sftp://183.62.252.139:65005   账号：yilian，!12345678
			ChannelSftp sftp = SftpUtil.getSftpConnect(Cons.SftpPayeco.IP, Cons.SftpPayeco.PORT, Cons.SftpPayeco.ACCOUNT, Cons.SftpPayeco.PASSWORD);  
			System.out.println("开始从远程服务器获取...");  

			String path = "/repay/";
			String name = DateUtil.getCurrentYearMonthDay()+".xlsx";
			String fileName = "/logs/files/YLZF_REPAY_" + DateUtil.getCurrentYearMonthDay()+".xlsx";
	
			/**
			 * 1 生成报表
			 * 2 上传到sftp
			 * 3 删除源文件
			 */
			export(list, fileName);
			
			SftpUtil.uploadFile(fileName, "/upload/repay", DateUtil.getCurrentYearMonthDay()+".xlsx", sftp);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	public static void export(List<ReturnedMoney> list, String fileName) throws ParseException{
		 // 第一步，创建一个webbook，对应一个Excel文件  
        HSSFWorkbook wb = new HSSFWorkbook();  
        // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet  
        HSSFSheet sheet = wb.createSheet("学生表一");  
        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short  
        HSSFRow row = sheet.createRow((int) 0);  
        // 第四步，创建单元格，并设置值表头 设置表头居中  
        HSSFCellStyle style = wb.createCellStyle();  
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式  
  
        HSSFCell cell = row.createCell((short) 0);  
        cell.setCellValue("当次还款人数");  
        cell.setCellStyle(style);  
        cell = row.createCell((short) 1);  
        cell.setCellValue("当次扣款总金额");  
        cell.setCellStyle(style);  
        cell = row.createCell((short) 2);  
        cell.setCellValue("当次罚息总金额");  
        cell.setCellStyle(style);  
        cell = row.createCell((short) 3);  
        cell.setCellValue("回款报表文件名");  
        cell.setCellStyle(style);  
        
        ReturnedMoney returnMoney = null;
        BigDecimal repayMoney = new BigDecimal("0.00");
		BigDecimal sumFee = new BigDecimal("0.00");
		for(int i =0; i <list.size(); i++){
			returnMoney = list.get(i);
			String loanMoney = returnMoney.getRepayLoanMoney();
			String fee = returnMoney.getFee();
			repayMoney = repayMoney.add(new BigDecimal(loanMoney));
			sumFee = sumFee.add(new BigDecimal(fee));
		}
        
        SimpleDateFormat df = new SimpleDateFormat("yyyy-mm-dd");  
        row = sheet.createRow((int) 1);  
        // 第四步，创建单元格，并设置值  
        row.createCell((short) 0).setCellValue((double) list.size());  
        row.createCell((short) 1).setCellValue((double) list.size());  
        row.createCell((short) 2).setCellValue((double) list.size());  
        cell = row.createCell((short) 3);  
        cell.setCellValue(new SimpleDateFormat("yyyy-mm-dd").format(df.parse("1997-03-12")));  
        
        HSSFRow row2 = sheet.createRow((int) 2);  
        HSSFCell cell2 = row2.createCell((short) 0);  
        cell2.setCellValue("借款人id");  
        cell2.setCellStyle(style);  
        cell2 = row2.createCell((short) 1);  
        cell2.setCellValue("借款人姓名");  
        cell2.setCellStyle(style);  
        cell2 = row2.createCell((short) 2);  
        cell2.setCellValue("借款人银行卡号");  
        cell2.setCellStyle(style);  
        cell2 = row2.createCell((short) 3);  
        cell2.setCellValue("当次扣款金额");  
        cell2.setCellStyle(style);  
        cell2 = row2.createCell((short) 4);  
        cell2.setCellValue("当次罚息金额");  
        cell2.setCellStyle(style);  
        
        
  
        // 第五步，写入实体数据 实际应用中这些数据从数据库得到，  
//        List list = CreateSimpleExcelToDisk.getStudent();  
        for (int i = 0; i < list.size(); i++)  
        {  
        	returnMoney = list.get(i);
            row = sheet.createRow((int) i + 3);  
             
            // 第四步，创建单元格，并设置值  
            row.createCell((short) 0).setCellValue(returnMoney.getUserId());  
            row.createCell((short) 1).setCellValue(returnMoney.getUserName());  
            row.createCell((short) 2).setCellValue(returnMoney.getAccountNum());  
            row.createCell((short) 3).setCellValue(returnMoney.getRepayLoanMoney());  
            row.createCell((short) 4).setCellValue(returnMoney.getFee());  
        }  
        // 第六步，将文件存到指定位置  
        try  
        {  
            FileOutputStream fout = new FileOutputStream(fileName);  
            wb.write(fout);  
            fout.close();  
        }  
        catch (Exception e)  
        {  
            e.printStackTrace();  
        }  
	}
	public static void main(String[] args) {
		try {
			export(new ArrayList<ReturnedMoney>(), "");
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}

package com.wanfin.fpd.common.quartz.ftp;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.wanfin.fpd.common.utils.MD5Util;
import com.wanfin.fpd.common.utils.sftp.DownLoadFileFromFtp;
import com.wanfin.fpd.modules.catipal.service.TCaiwuService;
import com.wanfin.fpd.modules.company.entity.TCompany;
import com.wanfin.fpd.modules.company.service.TCompanyService;
import com.wanfin.fpd.modules.contract.entity.TLoanContract;
import com.wanfin.fpd.modules.contract.service.TLoanContractService;
import com.wanfin.fpd.modules.employee.entity.TEmployee;
import com.wanfin.fpd.modules.employee.service.TEmployeeService;
import com.wanfin.fpd.modules.guarantee.service.TGuaranteeContractService;
import com.wanfin.fpd.modules.jrj.entity.JrjBalanceSheep;
import com.wanfin.fpd.modules.jrj.entity.JrjBusinessStatus;
import com.wanfin.fpd.modules.jrj.entity.JrjProceeds;
import com.wanfin.fpd.modules.jrj.entity.JrjRiskIndicator;
import com.wanfin.fpd.modules.jrj.service.JrjBalanceSheepService;
import com.wanfin.fpd.modules.jrj.service.JrjBusinessStatusService;
import com.wanfin.fpd.modules.jrj.service.JrjCashFlowService;
import com.wanfin.fpd.modules.jrj.service.JrjInterestsChangService;
import com.wanfin.fpd.modules.jrj.service.JrjProceedsService;
import com.wanfin.fpd.modules.jrj.service.JrjProfitService;
import com.wanfin.fpd.modules.jrj.service.JrjRiskIndicatorService;
import com.wanfin.fpd.modules.mortgage.entity.MortgageContract;
import com.wanfin.fpd.modules.mortgage.service.MortgageContractService;
import com.wanfin.fpd.modules.pledge.entity.PledgeContract;
import com.wanfin.fpd.modules.pledge.service.PledgeContractService;
import com.wanfin.fpd.modules.receivables.service.RepayRecordService;
import com.wanfin.fpd.modules.repayplan.service.TRepayPlanService;

/**
 * 
 * @Description 以sftp的方式上传zip加密包
 * @author xzt
 * @date 2016/11/1
 */
public class UploadFileFromFtpTask {

	private Logger logger = LoggerFactory.getLogger(UploadFileFromFtpTask.class);
	private WebApplicationContext currentWebApplicationContext = null;	
	private JrjBalanceSheepService jrjBalanceSheepService = null;
	private JrjBusinessStatusService jrjBusinessStatusService = null;	
	private JrjProfitService jrjProfitService = null;	
	private JrjCashFlowService jrjCashFlowService = null;	
	private JrjInterestsChangService jrjInterestsChangService = null;
	private JrjProceedsService jrjProceedsService = null;
	private JrjRiskIndicatorService jrjRiskIndicatorService = null;	
	private TCaiwuService tCaiwuService = null;	
	private TRepayPlanService tRepayPlanService = null;	
	private RepayRecordService repayRecordService = null;		
	private TCompanyService tCompanyService = null;	
	private TEmployeeService tEmployeeService = null;	
	private TLoanContractService contractService = null;	
	private MortgageContractService mortgageContractService = null;	
	private TGuaranteeContractService guaranteeContractService = null;	
	private PledgeContractService pledgeContractService = null;
	
	private void initContent() {
		if (currentWebApplicationContext == null) {
			currentWebApplicationContext = ContextLoader.getCurrentWebApplicationContext();			
		}				
		if (jrjBalanceSheepService == null) {
			jrjBalanceSheepService = (JrjBalanceSheepService)currentWebApplicationContext.getBean("jrjBalanceSheepService");			
		}
		if (jrjBusinessStatusService == null) {
			jrjBusinessStatusService = (JrjBusinessStatusService)currentWebApplicationContext.getBean("jrjBusinessStatusService");	
		}
		if (jrjProceedsService == null) {
			jrjProceedsService = (JrjProceedsService)currentWebApplicationContext.getBean("jrjProceedsService");			
		}
		if(jrjProfitService == null){
			jrjProfitService = (JrjProfitService)currentWebApplicationContext.getBean("jrjProfitService");			
		}
		if(jrjCashFlowService == null){
			jrjCashFlowService = (JrjCashFlowService)currentWebApplicationContext.getBean("jrjCashFlowService");			
		}
		if(jrjInterestsChangService == null){
			jrjInterestsChangService = (JrjInterestsChangService)currentWebApplicationContext.getBean("jrjInterestsChangService");			
		}
		if (jrjRiskIndicatorService == null) {
			jrjRiskIndicatorService = (JrjRiskIndicatorService)currentWebApplicationContext.getBean("jrjRiskIndicatorService");						
		}
		if (tCaiwuService == null) {
			tCaiwuService = (TCaiwuService)currentWebApplicationContext.getBean("tCaiwuService");						
		}
		if (tRepayPlanService == null) {
			tRepayPlanService = (TRepayPlanService)currentWebApplicationContext.getBean("tRepayPlanService");				
		}
		if (repayRecordService == null) {
			repayRecordService = (RepayRecordService)currentWebApplicationContext.getBean("repayRecordService");						
		}
		if (tCompanyService == null) {
			tCompanyService = (TCompanyService)currentWebApplicationContext.getBean("tCompanyService");			
		}
		if (tEmployeeService == null) {
			tEmployeeService = (TEmployeeService)currentWebApplicationContext.getBean("tEmployeeService");			
		}
		if (contractService == null) {
			contractService = (TLoanContractService)currentWebApplicationContext.getBean("tLoanContractService");			
		}
		if (mortgageContractService == null) {
			mortgageContractService = (MortgageContractService)currentWebApplicationContext.getBean("mortgageContractService");	
		}	
		if (guaranteeContractService == null) {
			guaranteeContractService = (TGuaranteeContractService)currentWebApplicationContext.getBean("tGuaranteeContractService");	
		}	
		if (pledgeContractService == null) {
			pledgeContractService = (PledgeContractService)currentWebApplicationContext.getBean("pledgeContractService");			
		}
	}
	
	

	/**
	 * 定时任务，执行方法
	 */
	public void execute() {
		initContent();
		// 上传临时文件
		String tempPath = DownLoadFileFromFtp.ftptempdir;
		// 远程SFTP文件夹
		String remoteDir = DownLoadFileFromFtp.ftptodir;
		DownLoadFileFromFtp loadFileFromFtp = new DownLoadFileFromFtp();
		
		//删除 发送回执文件夹里面的所有文件
	    FileUtil.delAllFile(DownLoadFileFromFtp.ftptempdir);
		
		/*************************** 读取扫描的 企业客户的记录 start *******************************/

		// 生成文件名 例如：ERP_440104000569953_LDA_20160908 代号_营业证号_LDA_日期 日期精确到分
		String companyName = "BUS_440104000569953_LDA_" + getCurrentYearMonthHourDay() + ".DAT";

		StringBuffer busData = tCompanyService.updateGetListByscanFlagData(companyName, new Long(1));
		if (busData != null && !busData.toString().equals("")) {
			// 生成数据文件
			loadFileFromFtp.sendUploadFile(remoteDir, companyName, busData);
		}
		
		/*************************** 读取扫描的 客户的记录 end *******************************/
		
		
		/*************************** 读取扫描的 个人客户的记录 start *******************************/

		// 生成文件名 例如：ERP_440104000569953_LDA_20160908 代号_营业证号_LDA_日期 日期精确到分
		String personName = "PER_440104000569953_LDA_" + getCurrentYearMonthHourDay() + ".DAT";

		StringBuffer perData = tEmployeeService.updateGetListByscanFlagData(personName, new Long(1));
		if (perData != null && !perData.toString().equals("")) {
			// 生成数据文件
			loadFileFromFtp.sendUploadFile(remoteDir, personName, perData);
		}		
		/*************************** 读取扫描的个人 客户的记录 end *******************************/
		
		
		
		/*************************** 读取扫描的 业务合同的记录 start *******************************/

		// 生成文件名 例如：ERP_440104000569953_LDA_20160908 代号_营业证号_LDA_日期 日期精确到分
		String contractName = "CONXD_440104000569953_LDA_" + getCurrentYearMonthHourDay() + ".DAT";

		StringBuffer contractData = contractService.updateGetListByscanFlagData(contractName, new Long(1));
		if (contractData != null && !contractData.toString().equals("")) {
			// 生成数据文件
			loadFileFromFtp.sendUploadFile(remoteDir, contractName, contractData);
		}		
		/*************************** 读取扫描的业务合同的记录 end *******************************/
		
		/*************************** 读取扫描的 担保合同的记录 start *******************************/

		// 生成文件名 例如：ERP_440104000569953_LDA_20160908 代号_营业证号_LDA_日期 日期精确到分
		String dbName = "CONDB_440104000569953_LDA_" + getCurrentYearMonthHourDay() + ".DAT";

		StringBuffer dbData = contractService.updateGetListDbByscanFlagData(dbName, new Long(1));
		if (dbData != null && !dbData.toString().equals("")) {
			// 生成数据文件
			loadFileFromFtp.sendUploadFile(remoteDir, dbName, dbData);
		}
		
		/*************************** 读取扫描的业务合同的记录 end *******************************/
		
		
		/*************************** 读取扫描的 抵质押的记录 start *******************************/

		// 生成文件名 例如：ERP_440104000569953_LDA_20160908 代号_营业证号_LDA_日期 日期精确到分
		String diZhiyaName = "PLE_440104000569953_LDA_" + getCurrentYearMonthHourDay() + ".DAT";
		
		/**
		 * 扫描抵押信息
		 */	
		StringBuffer mortData = mortgageContractService.updateGetListByscanFlagData(diZhiyaName, new Long(1));
		             mortData = pledgeContractService.updateGetListByscanFlagData(diZhiyaName, new Long(1),mortData);
		if (mortData != null && !mortData.toString().equals("")) {
			// 生成数据文件
			loadFileFromFtp.sendUploadFile(remoteDir, diZhiyaName, mortData);
		}
		
	   /*************************** 读取扫描 抵质押的记录 end ******************************/
		
		
		/*************************** 读取扫描的 保证的记录 start *******************************/

		// 生成文件名 例如：ERP_440104000569953_LDA_20160908 代号_营业证号_LDA_日期 日期精确到分
		String ensName = "ENS_440104000569953_LDA_" + getCurrentYearMonthHourDay() + ".DAT";
		
		
		StringBuffer ensData = guaranteeContractService.updateGetListByscanFlagData();		            
		if (ensData != null && !ensData.toString().equals("")) {
			// 生成数据文件
			loadFileFromFtp.sendUploadFile(remoteDir, ensName, ensData);
		}
		
	   /*************************** 读取扫描  保证记录 end ******************************/
		
		
		

		/*************************** 读取为扫描的 资产负债的记录 start *******************************/

		// 生成文件名 例如：ERP_440104000569953_LDA_20160908 代号_营业证号_LDA_日期 日期精确到分
		String fileSheepName = "BAL_440104000569953_LDA_" + getCurrentYearMonthHourDay() + ".DAT";

		
		StringBuffer balanceSheepData = jrjBalanceSheepService.updateGetListByscanFlagData(fileSheepName);
		if (balanceSheepData != null && !balanceSheepData.toString().equals("")) {
			// 生成数据文件
			loadFileFromFtp.sendUploadFile(remoteDir, fileSheepName, balanceSheepData);
		}		
		/*************************** 读取为扫描的 资产负债的记录 end *******************************//*
		

		*//*************************** 扫描担保利润表记录 start *******************************/
		// 生成文件名 例如：ERP_440104000569953_LDA_20160908 代号_营业证号_LDA_日期 日期精确到分
		String fileProfitName = "DBLR_440104000569953_LDA_" + getCurrentYearMonthHourDay() + ".DAT";

		StringBuffer dblrSheepData = jrjProfitService.updateGetListByscanFlagData();
		if (dblrSheepData != null && !dblrSheepData.toString().equals("")) {
			// 生成数据文件
			loadFileFromFtp.sendUploadFile(remoteDir, fileProfitName, dblrSheepData);
		}		
		/*************************** 扫描担保利润表记录 end *******************************//*
		
		

		*//***************************  扫描担保现金流量表记录 start *******************************/
		// 生成文件名 例如：ERP_440104000569953_LDA_20160908 代号_营业证号_LDA_日期 日期精确到分
		String fileCashName = "DBXJ_440104000569953_LDA_" + getCurrentYearMonthHourDay() + ".DAT";

		
		StringBuffer cashFlowData = jrjCashFlowService.updateGetListByscanFlagData();
		if (cashFlowData != null && !cashFlowData.toString().equals("")) {
			// 生成数据文件
			loadFileFromFtp.sendUploadFile(remoteDir, fileCashName, cashFlowData);
		}		
		/*************************** 扫描担保现金流量表记录 end *******************************//*
		
		*/

		/*************************** 扫描担保所有者权益变动表记录 start *******************************/
		// 生成文件名 例如：ERP_440104000569953_LDA_20160908 代号_营业证号_LDA_日期 日期精确到分
		String changName = "DBQY_440104000569953_LDA_" + getCurrentYearMonthHourDay() + ".DAT";
		
		StringBuffer changData = jrjInterestsChangService.updateGetListByscanFlagData();
		if (changData != null && !changData.toString().equals("")) {
			// 生成数据文件
			loadFileFromFtp.sendUploadFile(remoteDir, changName, changData);
		}
		
		/*************************** 扫描所有者权益变动表记录 end *******************************//*
		
		*//*************************** 小贷资产负债记录  *******************************/		
		// 1：小货资产负债 2：小货利润表
		String fileCaiwuZcfzName = "ZCFZ_440104000569953_LDA_" + getCurrentYearMonthHourDay() + ".DAT";
		StringBuffer fileCaiwuZcfzData = tCaiwuService.updateGetListByscanFlagData(fileCaiwuZcfzName, new Long(1));
		if (fileCaiwuZcfzData != null && !fileCaiwuZcfzData.toString().equals("")) {
			// 生成数据文件
			loadFileFromFtp.sendUploadFile(remoteDir, fileCaiwuZcfzName, fileCaiwuZcfzData);
		}
	
		
		/*************************** 小贷资产负债记录   *******************************/
		
		/*************************** 小货利润表记录  *******************************/		
		// 1：小货资产负债 2：小货利润表
		String fileCaiwuLrName = "XDLR_440104000569953_LDA_" + getCurrentYearMonthHourDay() + ".DAT";
		StringBuffer fileCaiwuLrData = tCaiwuService.updateGetListByscanFlagData(fileCaiwuLrName, new Long(2));
		if (fileCaiwuLrData != null && !fileCaiwuLrData.toString().equals("")) {
			// 生成数据文件
			loadFileFromFtp.sendUploadFile(remoteDir, fileCaiwuLrName, fileCaiwuLrData);
		}
		
		/*************************** 小货现金流量表记录  *******************************/		
		// 1：小货资产负债 2：小货利润表 3：现金流量
		String fileCaiwuXjllName = "XJLL_440104000569953_LDA_" + getCurrentYearMonthHourDay() + ".DAT";
		StringBuffer fileCaiwuXjllData = tCaiwuService.updateGetListByscanFlagData(fileCaiwuXjllName, new Long(3));
		if (fileCaiwuXjllData != null && !fileCaiwuXjllData.toString().equals("")) {
			// 生成数据文件
			loadFileFromFtp.sendUploadFile(remoteDir, fileCaiwuXjllName, fileCaiwuXjllData);
		}
		
		
		/*************************** 小货现金流量记录   *******************************/
		
		/*************************** 还款计划记录  *******************************/			
		String fileRepayPlanName = "PLA_440104000569953_LDA_" + getCurrentYearMonthHourDay() + ".DAT";
		StringBuffer fileRepayPlanData = tRepayPlanService.updateGetListByscanFlagData(fileRepayPlanName);
		if (fileRepayPlanData != null && !fileRepayPlanData.toString().equals("")) {
			// 生成数据文件
			loadFileFromFtp.sendUploadFile(remoteDir, fileRepayPlanName, fileRepayPlanData);
		}
				
		/*************************** 还款计划记录   *******************************/
		
		/*************************** 实际还款记录  *******************************/			
		String fileRepayRecordName = "REP_440104000569953_LDA_" + getCurrentYearMonthHourDay() + ".DAT";
		StringBuffer fileRepayRecordData = repayRecordService.updateGetListByscanFlagData(fileRepayRecordName);
		
		if (fileRepayRecordData != null && !fileRepayRecordData.toString().equals("")) {
			// 生成数据文件
			loadFileFromFtp.sendUploadFile(remoteDir, fileRepayRecordName, fileRepayRecordData);		
		}
		
		/*************************** 实际还款记录   *******************************/
		
		/*************************** 展期还款计划记录  *******************************/			
		String fileERepayPlanName = "EXT_440104000569953_LDA_" + getCurrentYearMonthHourDay() + ".DAT";
		StringBuffer fileERepayPlanData = tRepayPlanService.updateGetETListByscanFlagData(fileRepayPlanName);
		if (fileERepayPlanData != null && !fileERepayPlanData.toString().equals("")) {
			// 生成数据文件
			loadFileFromFtp.sendUploadFile(remoteDir, fileERepayPlanName, fileERepayPlanData);		
		}
		
		/*************************** 展期还款计划记录   *******************************/
		
		/*************************** 展期实际还款记录  *******************************/			
		String fileERepayRecordName = "ERP_440104000569953_LDA_" + getCurrentYearMonthHourDay() + ".DAT";
		StringBuffer fileERepayRecordData = repayRecordService.updateGetETListByscanFlagData(fileERepayRecordName);
		if (fileERepayRecordData != null && !fileERepayRecordData.toString().equals("")) {
			// 生成数据文件
			loadFileFromFtp.sendUploadFile(remoteDir, fileERepayRecordName, fileERepayRecordData);		
		}
		
		/*************************** 展期实际还款记录   *******************************/
		
	

		// 把数据文件打包加密zip 上传SFTP
		String passwd = getCurrentYearMonthDay();
		loadFileFromFtp.uploadZip(tempPath, getCurrentYearMonthDay(), MD5Util.getMD5(passwd));
		logger.info("-----报表数据已经上传完！-------");
		
		//删除 发送回执文件夹里面的所有文件
		FileUtil.delAllFile(DownLoadFileFromFtp.ftptempdir);

	}

	public static String getCurrentYearMonthHourDay() {
		String FORMAT_YYYYMMDD = "yyyyMMddHHmm";
		SimpleDateFormat formatter = new SimpleDateFormat(FORMAT_YYYYMMDD);
		Date currentTime = new java.util.Date();// 得到当前系统时间
		String yearAndDayString = formatter.format(currentTime); // 将日期时间格式化
		return yearAndDayString;
	}

	public static String getCurrentYearMonthDay() {
		String FORMAT_YYYYMMDD = "yyyyMMdd";
		SimpleDateFormat formatter = new SimpleDateFormat(FORMAT_YYYYMMDD);
		Date currentTime = new java.util.Date();// 得到当前系统时间
		String yearAndDayString = formatter.format(currentTime); // 将日期时间格式化
		return yearAndDayString;
	}

}

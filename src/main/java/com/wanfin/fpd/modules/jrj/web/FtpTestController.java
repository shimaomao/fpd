/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.jrj.web;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mangofactory.swagger.annotations.ApiIgnore;
import com.wanfin.fpd.common.config.Global;
import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.utils.MD5Util;
import com.wanfin.fpd.common.utils.StringUtils;
import com.wanfin.fpd.common.utils.sftp.DownLoadFileFromFtp;
import com.wanfin.fpd.common.web.BaseController;
import com.wanfin.fpd.modules.balancesheep.entity.TBalanceSheep;
import com.wanfin.fpd.modules.balancesheep.service.TBalanceSheepService;
import com.wanfin.fpd.modules.company.entity.TCompany;
import com.wanfin.fpd.modules.company.service.TCompanyService;
import com.wanfin.fpd.modules.contract.entity.TLoanContract;
import com.wanfin.fpd.modules.contract.service.TLoanContractService;
import com.wanfin.fpd.modules.employee.entity.TEmployee;
import com.wanfin.fpd.modules.employee.service.TEmployeeService;
import com.wanfin.fpd.modules.jrj.entity.JrjBalanceSheep;
import com.wanfin.fpd.modules.jrj.entity.JrjBusinessStatus;
import com.wanfin.fpd.modules.jrj.entity.JrjProceeds;
import com.wanfin.fpd.modules.jrj.entity.JrjRiskIndicator;
import com.wanfin.fpd.modules.jrj.service.JrjBalanceSheepService;
import com.wanfin.fpd.modules.jrj.service.JrjBusinessStatusService;
import com.wanfin.fpd.modules.jrj.service.JrjProceedsService;
import com.wanfin.fpd.modules.jrj.service.JrjRiskIndicatorService;
import com.wanfin.fpd.modules.mortgage.entity.MortgageContract;
import com.wanfin.fpd.modules.mortgage.service.MortgageContractService;
import com.wanfin.fpd.modules.pledge.entity.PledgeContract;
import com.wanfin.fpd.modules.pledge.service.PledgeContractService;
import com.wanfin.fpd.modules.sys.entity.Office;
import com.wanfin.fpd.modules.sys.entity.User;
import com.wanfin.fpd.modules.sys.utils.UserUtils;

/**
 * 风险指标Controller
 * @author xzt
 * @version 2016-10-29
 */
@ApiIgnore
@Controller
@RequestMapping(value = "${adminPath}/ftp/test")
public class FtpTestController extends BaseController {

	@Autowired
	private JrjBalanceSheepService jrjBalanceSheepService;
	
	@Autowired 
	private JrjBusinessStatusService jrjBusinessStatusService;
	
	@Autowired
	private JrjProceedsService jrjProceedsService;
	
	@Autowired
	private JrjRiskIndicatorService jrjRiskIndicatorService;
	
	@Autowired
	private TCompanyService tCompanyService;
	
	@Autowired
	private TEmployeeService tEmployeeService;
	
	@Autowired
	private TLoanContractService contractService;
	
	@Autowired
	private MortgageContractService mortgageContractService;
	
	@Autowired
	private PledgeContractService pledgeContractService;
	
	@RequestMapping(value = "form")
	public String form(JrjProceeds jrjProceeds, Model model) {			
		
		// 上传临时文件
		String tempPath = DownLoadFileFromFtp.ftptempdir;
		// 远程SFTP文件夹
		String remoteDir = DownLoadFileFromFtp.ftptodir;
		DownLoadFileFromFtp loadFileFromFtp = new DownLoadFileFromFtp();
		
		/*************************** 读取扫描的 企业客户的记录 start *******************************/

		// 生成文件名 例如：ERP_440104000569953_LDA_20160908 代号_营业证号_LDA_日期 日期精确到分
		String companyName = "BUS_440104000569953_LDA_" + getCurrentYearMonthHourDay() + ".DAT";

		TCompany queryCompanySheep = new TCompany();
		queryCompanySheep.setScanFlag("0");
		List<TCompany> companyList = tCompanyService.findListByScanTime(queryCompanySheep);
		if (companyList != null && companyList.size() > 0) {
			StringBuffer sheepData = new StringBuffer();
			// 获取数据
			for (TCompany temp : companyList) {	
				sheepData.append(temp.senData());
				sheepData.append("\r\n");
			}
			// 生成数据文件
			loadFileFromFtp.sendUploadFile(remoteDir, companyName, sheepData);

			// 更改已经做了扫描处理的标示
			for (TCompany temp : companyList) {
				temp.setScanFlag("1");
				tCompanyService.save(temp);
			}
		}
		/*************************** 读取扫描的 客户的记录 end *******************************/
		
		
		/*************************** 读取扫描的 个人客户的记录 start *******************************/

		// 生成文件名 例如：ERP_440104000569953_LDA_20160908 代号_营业证号_LDA_日期 日期精确到分
		String personName = "PER_440104000569953_LDA_" + getCurrentYearMonthHourDay() + ".DAT";

		TEmployee queryPersonSheep = new TEmployee();
		queryPersonSheep.setScanFlag("0");
		List<TEmployee> personList = tEmployeeService.findListByScanFlag(queryPersonSheep);
		if (personList != null && personList.size() > 0) {
			StringBuffer sheepData = new StringBuffer();
			// 获取数据
			for (TEmployee temp : personList) {
				sheepData.append(temp.senData());
				sheepData.append("\r\n");
			}
			// 生成数据文件
			loadFileFromFtp.sendUploadFile(remoteDir, personName, sheepData);

			// 更改已经做了扫描处理的标示
			for (TEmployee temp : personList) {
				temp.setScanFlag("1");
				tEmployeeService.save(temp);
			}
		}
		/*************************** 读取扫描的个人 客户的记录 end *******************************/
		
		
		
		/*************************** 读取扫描的 业务合同的记录 start *******************************/

		// 生成文件名 例如：ERP_440104000569953_LDA_20160908 代号_营业证号_LDA_日期 日期精确到分
		String contractName = "CON_440104000569953_LDA_" + getCurrentYearMonthHourDay() + ".DAT";

		TLoanContract queryContract = new TLoanContract();
		queryContract.setScanFlag("0");
		List<TLoanContract> contractList = contractService.findListByScanFlag(queryContract);
		if (contractList != null && contractList.size() > 0) {
			StringBuffer sheepData = new StringBuffer();
			// 获取数据
			for (TLoanContract temp : contractList) {
				sheepData.append(contractService.sendData(temp));
				sheepData.append("\r\n");
			}
			// 生成数据文件
			loadFileFromFtp.sendUploadFile(remoteDir, contractName, sheepData);

			// 更改已经做了扫描处理的标示
			for (TLoanContract temp : contractList) {
				temp.setScanFlag("1");
				contractService.save(temp);
			}
		}
		/*************************** 读取扫描的业务合同的记录 end *******************************/
		
		
		/*************************** 读取扫描的 抵质押的记录 start *******************************/

		// 生成文件名 例如：ERP_440104000569953_LDA_20160908 代号_营业证号_LDA_日期 日期精确到分
		String diZhiyaName = "PLE_440104000569953_LDA_" + getCurrentYearMonthHourDay() + ".DAT";
		
		/**
		 * 扫描抵押信息
		 */
		MortgageContract queryMorContract = new MortgageContract();
		PledgeContract pledgeContract = new PledgeContract();
		queryMorContract.setScanFlag("0");
		pledgeContract.setScanFlag("0");
		StringBuffer diZhiData = new StringBuffer();
		List<MortgageContract> morContractList = mortgageContractService.findListByScanFlag(queryMorContract);
		List<PledgeContract> pleContractList = pledgeContractService.findListByScanFlag(pledgeContract);
		
		if (morContractList != null && morContractList.size() > 0) {
			// 获取数据
			for (MortgageContract temp : morContractList) {
				diZhiData.append(temp.sendData() );
				diZhiData.append("\r\n");
			}
			
			// 更改已经做了扫描处理的标示
			for (MortgageContract temp : morContractList) {
				temp.setScanFlag("1");
				mortgageContractService.save(temp);
			}
		}
		
		if (pleContractList != null && pleContractList.size() > 0) {
			// 获取数据
			for (PledgeContract temp : pleContractList) {
				diZhiData.append(temp.sendData());
				diZhiData.append("\r\n");
			}
			
			// 更改已经做了扫描处理的标示
			for (PledgeContract temp : pleContractList) {
				temp.setScanFlag("1");
				pledgeContractService.updateScanFlag(temp);
			}
		}
		// 生成数据文件
	   loadFileFromFtp.sendUploadFile(remoteDir, diZhiyaName, diZhiData);	
	   /*************************** 读取扫描 抵质押的记录 end ******************************/
	   
		

		/*************************** 读取为扫描的 资产负债的记录 start *******************************/

		// 生成文件名 例如：ERP_440104000569953_LDA_20160908 代号_营业证号_LDA_日期 日期精确到分
		String fileSheepName = "BAL_440104000569953_LDA_" + getCurrentYearMonthHourDay() + ".DAT";

		JrjBalanceSheep queryBalanceSheep = new JrjBalanceSheep();
		queryBalanceSheep.setScanFlag("0");
		List<JrjBalanceSheep> sheepList = jrjBalanceSheepService.findListByScanFlag(queryBalanceSheep);
		if (sheepList != null && sheepList.size() > 0) {
			StringBuffer sheepData = new StringBuffer();
			// 获取数据
			for (JrjBalanceSheep temp : sheepList) {
				sheepData.append(temp.toString());
				sheepData.append("\r\n");
			}
			// 生成数据文件
			loadFileFromFtp.sendUploadFile(remoteDir, fileSheepName, sheepData);

			// 更改已经做了扫描处理的标示
			for (JrjBalanceSheep temp : sheepList) {
				temp.setScanFlag("1");
				jrjBalanceSheepService.save(temp);
			}
		}
		/*************************** 读取为扫描的 资产负债的记录 end *******************************/
		

		/*************************** 扫描担保业务状况记录 start *******************************/
		// 生成文件名 例如：ERP_440104000569953_LDA_20160908 代号_营业证号_LDA_日期 日期精确到分
		String fileStatusName = "BUS_440104000569953_LDA_" + getCurrentYearMonthHourDay() + ".DAT";

		JrjBusinessStatus queryStatus = new JrjBusinessStatus();
		queryStatus.setScanFlag("0");
		List<JrjBusinessStatus> statusList = jrjBusinessStatusService.findListByScanFlag(queryStatus);
		if (statusList != null && statusList.size() > 0) {
			StringBuffer data = new StringBuffer();
			for (JrjBusinessStatus status : statusList) {
				data.append(status.toString());
				data.append("\r\n");
			}

			// 生成数据文件
			loadFileFromFtp.sendUploadFile(remoteDir, fileStatusName, data);
			for (JrjBusinessStatus status : statusList) {
				status.setScanFlag("1");
				jrjBusinessStatusService.save(status);
			}

		}
		/*************************** 扫描担保业务状况记录 end *******************************/
		
		

		/***************************  扫描收益情况记录 start *******************************/
		// 生成文件名 例如：ERP_440104000569953_LDA_20160908 代号_营业证号_LDA_日期 日期精确到分
		String fileProceedsName = "PRO_440104000569953_LDA_" + getCurrentYearMonthHourDay() + ".DAT";

		JrjProceeds queryProceeds = new JrjProceeds();
		queryStatus.setScanFlag("0");
		List<JrjProceeds> proceedsList = jrjProceedsService.findListByScanFlag(queryProceeds);
		if (proceedsList != null && proceedsList.size() > 0) {
			StringBuffer data = new StringBuffer();
			for (JrjProceeds proceeds : proceedsList) {
				data.append(proceeds.toString());
				data.append("\r\n");
			}

			// 生成数据文件
			loadFileFromFtp.sendUploadFile(remoteDir, fileProceedsName, data);
			for (JrjProceeds proceeds : proceedsList) {
				proceeds.setScanFlag("1");
				jrjProceedsService.save(proceeds);
			}

		}
		/*************************** 扫描收益情况记录 end *******************************/
		
		

		/*************************** 扫描风险指标记录 start *******************************/
		// 生成文件名 例如：ERP_440104000569953_LDA_20160908 代号_营业证号_LDA_日期 日期精确到分
		String fileRiskName = "RIS_440104000569953_LDA_" + getCurrentYearMonthHourDay() + ".DAT";

		JrjRiskIndicator queryRisk = new JrjRiskIndicator();
		queryRisk.setScanFlag("0");
		List<JrjRiskIndicator> riskList = jrjRiskIndicatorService.findListByscanFlag(queryRisk);
		if (riskList != null && riskList.size() > 0) {
			StringBuffer data = new StringBuffer();
			for (JrjRiskIndicator risk : riskList) {
				data.append(risk.toString());
				data.append("\r\n");
			}

			// 生成数据文件
			loadFileFromFtp.sendUploadFile(remoteDir, fileRiskName, data);
			for (JrjRiskIndicator risk : riskList) {
				risk.setScanFlag("1");
				jrjRiskIndicatorService.save(risk);
			}

		}
		/*************************** 扫描风险指标记录 end *******************************/

		// 把数据文件打包加密zip 上传SFTP
		String passwd = getCurrentYearMonthDay();
		loadFileFromFtp.uploadZip(tempPath, getCurrentYearMonthDay(), MD5Util.getMD5(passwd));
		logger.info("-----报表数据已经上传完！-------");

		return "";
	}
	
	
	

    public static String getCurrentYearMonthHourDay() {
    	String FORMAT_YYYYMMDD = "yyyyMMddhhmm";
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
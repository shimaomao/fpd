package com.wanfin.fpd.common.quartz;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.wanfin.fpd.common.utils.StringUtils;
import com.wanfin.fpd.modules.wish.utils.AmountUtil;
import org.springframework.beans.factory.annotation.Autowired;

import com.jcraft.jsch.ChannelSftp;
import com.wanfin.fpd.common.config.Cons;
import com.wanfin.fpd.common.quartz.ftp.DateUtil;
import com.wanfin.fpd.modules.lending.service.TLendingService;
import com.wanfin.fpd.modules.wish.contract.vo.TLendingVo;
import com.wanfin.fpd.modules.wish.utils.ExcelUtils;
import com.wanfin.fpd.modules.wish.utils.SftpUtil;

public class LoanDayQuartz {


	@Autowired
	private TLendingService tLendingService;

	/**
	 * 9.1  放款日对账()
	 */
	public void execute(){
		System.out.println("-------------放款日对账----------------");
		TLendingVo lendVo = null;
		List<TLendingVo> list = tLendingService.findTLendingVoPage(new TLendingVo());

		try {
			String uploadPath = "upload/dz/day/";
			String name = "YLZF_DZWJ_DAY_" + DateUtil.getCurrentYearMonthDay()+".xlsx";
			String fileName = "/logs/files/dz/day/" + name;
			//生成excel文件，上传到sftp
			//准备数据
			//第一行属性
			 //组装数据测试
	        List<String[]> strList = new ArrayList<String[]>();
	        //第一行属性
	        String[] one = {"业务笔数","借款总金额","手续费金额"};
	        strList.add(one);
			//第二行数据
	    	//第二行数据
			//Map<String, Object> mapData = new HashedMap();
			
			BigDecimal repayMoney = new BigDecimal("0");
			BigDecimal sumFee = new BigDecimal("0");
			for(int i =0; i <list.size(); i++){
				lendVo = list.get(i);
				String loanMoney = lendVo.getSumLoanAmount();
				String fee = lendVo.getSumCharge();
				if (StringUtils.isEmpty(loanMoney)){
					loanMoney = "0";
				}
				if (StringUtils.isEmpty(fee)){
					fee = "0";
				}
				repayMoney = repayMoney.add(new BigDecimal(loanMoney));
				sumFee = sumFee.add(new BigDecimal(fee));
			}
			String[] two = {String.valueOf(list.size()), AmountUtil.yuanToFen(repayMoney), AmountUtil.yuanToFen(sumFee)};
	        strList.add(two);
			//第三行属性
			String[] three = {"借款人id","借款人Payeco Id","借款人业务id","借款人姓名","借款人银行卡号",
					"银行名称","开户省份","账户类型","借款金额","账号类型",
					"借款金额","手续费金额","放款时间","最迟还款时间"};
			strList.add(three);
			//第四行以后的数据

			String[] four = new String[three.length];
			for (int j=0; j<list.size(); j++){
				lendVo = list.get(j);
				four[0] = lendVo.getCustomerId();
				four[1] = lendVo.getCustomerId();
				four[2] = lendVo.getLoanContractId();
				four[3] = lendVo.getCustomerName();
				four[4] = lendVo.getGatheringNumber();

				four[5] = "";   //银行名称
				four[6] = "";
				four[7] = "";

				four[8] = AmountUtil.yuanToFen(lendVo.getSumLoanAmount());
				four[9] = "";

				four[10] = AmountUtil.yuanToFen(lendVo.getSumLoanAmount());

				four[11] = AmountUtil.yuanToFen(lendVo.getSumCharge()); //手续费
				four[12] = lendVo.getLendTime();
				four[13] = lendVo.getPayPrincipalDate();
				strList.add(four);
				four = new String[three.length];
			}
			
			OutputStream os = new FileOutputStream(fileName);
			ExcelUtils.exportExcelXTest(strList,null,0, os);

			ChannelSftp sftp = SftpUtil.getSftpConnect(Cons.SftpPayeco.IP, Cons.SftpPayeco.PORT, Cons.SftpPayeco.ACCOUNT, Cons.SftpPayeco.PASSWORD);   
			SftpUtil.uploadFile(fileName, uploadPath, name, sftp);
			System.out.println("-------------上传放款日对账----------------");
			SftpUtil.exit(sftp);  
		} catch (Exception e) {
			e.printStackTrace();
		}

	}


}

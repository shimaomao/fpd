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
import java.util.List;
import java.util.Map;

import com.wanfin.fpd.common.utils.StringUtils;
import com.wanfin.fpd.modules.wish.utils.AmountUtil;
import org.apache.commons.collections.map.HashedMap;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONArray;
import com.jcraft.jsch.ChannelSftp;
import com.wanfin.fpd.common.config.Cons;
import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.quartz.ftp.DateUtil;
import com.wanfin.fpd.modules.receivables.service.RepayRecordService;
import com.wanfin.fpd.modules.wish.contract.vo.RepayRecordVo;
import com.wanfin.fpd.modules.wish.order.entity.ReturnedMoney;
import com.wanfin.fpd.modules.wish.utils.ExcelUtils;
import com.wanfin.fpd.modules.wish.utils.SftpUtil;

public class LoanMonthQuartz {

    @Autowired
    private RepayRecordService repayRecordService;


    /**
     * 9.2  还款月对账表
     */
    public void execute() {

        System.out.println("-------------执行还款月对账报表定时器----------------");
        RepayRecordVo repayRecordVo = new RepayRecordVo();
        Page<RepayRecordVo> page = repayRecordService.findRepayRecordVoPage(new Page<RepayRecordVo>(), repayRecordVo);
        List<RepayRecordVo> list = page.getList();

        BigDecimal hundred = new BigDecimal("100");
        try {
            String uploadPath = "upload/dz/month/";
            String name = "YLZF_DZWJ_MONTH_" + DateUtil.getCurrentYearMonthDay() + ".xlsx";
            String fileName = "/logs/files/dz/month/" + name;
            //生成excel文件，上传到sftp
            //准备数据
            //第一行属性
            //组装数据测试
            List<String[]> strList = new ArrayList<String[]>();
            //第一行属性
            String[] one = {"还款业务笔数", "借款总金额", "当次还款总金额", "已还款总金额", "当次罚息金额", "剩余未还总金额"};
            strList.add(one);
            //第二行数据
            BigDecimal repayMoney = new BigDecimal("0"); //借款总金额
            BigDecimal thisRepayMoney = new BigDecimal("0"); //当次还款总金额
            BigDecimal alreadyRepayMoney = new BigDecimal("0"); //已还款总金额
            BigDecimal sumFee = new BigDecimal("0"); //当次罚息 总金额
            BigDecimal notRepayMoney = new BigDecimal("0"); //剩余未还总金额

            for (int i = 0; i < list.size(); i++) {
                repayRecordVo = list.get(i);
                if (StringUtils.isBlank(repayRecordVo.getSumLoanAmount())) {
                    repayMoney = repayMoney.add(new BigDecimal("0"));
                } else {
                    repayMoney = repayMoney.add(new BigDecimal(repayRecordVo.getSumLoanAmount()));
                }

                if (StringUtils.isBlank(repayRecordVo.getRepayMoney())) {
                    thisRepayMoney = thisRepayMoney.add(new BigDecimal("0"));
                } else {
                    thisRepayMoney = thisRepayMoney.add(new BigDecimal(repayRecordVo.getRepayMoney()));
                }

                if (StringUtils.isBlank(repayRecordVo.getSumRepayAmount())) {
                    alreadyRepayMoney = alreadyRepayMoney.add(new BigDecimal("0"));
                } else {
                    alreadyRepayMoney = alreadyRepayMoney.add(new BigDecimal(repayRecordVo.getSumRepayAmount()));
                }

                if (StringUtils.isBlank(repayRecordVo.getOverFee())) {
                    sumFee = sumFee.add(new BigDecimal("0"));
                } else {
                    sumFee = sumFee.add(new BigDecimal(repayRecordVo.getOverFee()));
                }

                if (StringUtils.isBlank(repayRecordVo.getDiffAmount())) {
                    notRepayMoney = notRepayMoney.add(new BigDecimal("0"));
                } else {
                    notRepayMoney = notRepayMoney.add(new BigDecimal(repayRecordVo.getDiffAmount()));
                }
            }
            String[] two = {String.valueOf(list.size()), AmountUtil.yuanToFen(repayMoney),
                    AmountUtil.yuanToFen(thisRepayMoney), AmountUtil.yuanToFen(alreadyRepayMoney),
                    AmountUtil.yuanToFen(sumFee), AmountUtil.yuanToFen(notRepayMoney)};
            strList.add(two);
            //第三行属性
            String[] three = {"借款人id", "借款人Payeco Id", "借款业务id", "借款人姓名", "借款人银行卡号",
                    "借款金额", "当次还款金额", "当次罚息金额", "已还款金额", "剩余未还金额",
                    "回款状态"};
            strList.add(three);
            //第四行以后的数据
            String[] four = new String[three.length];
            for (int i = 0; i < list.size(); i++) {
                repayRecordVo = list.get(i);
                //借款人id","借款人Payeco Id","借款业务id","借款人姓名","借款人银行卡号
                four[0] = repayRecordVo.getCustomerId();
                four[1] = repayRecordVo.getCustomerId();
                four[2] = repayRecordVo.getLoanContractId();
                four[3] = repayRecordVo.getCustomerName();
                four[4] = repayRecordVo.getGatheringNumber();

                //借款金额","当次还款金额","当次罚息金额","剩余未还金额","回款状态
                four[5] = AmountUtil.yuanToFen(repayRecordVo.getSumLoanAmount());
                four[6] = AmountUtil.yuanToFen(repayRecordVo.getRepayMoney());
                four[7] = AmountUtil.yuanToFen(repayRecordVo.getOverFee());
                BigDecimal loanAmount = new BigDecimal(repayRecordVo.getSumLoanAmount());
                String sumRepayAmount = repayRecordVo.getSumRepayAmount();
                if (StringUtils.isBlank(sumRepayAmount)) {
                    sumRepayAmount = "0";
                }
                BigDecimal noPaidAmount = (loanAmount.subtract(new BigDecimal(sumRepayAmount))).multiply(hundred);
				/*String remainNoPaidAmount = noPaidAmount.toString();*/
                four[8] = getAmount(hundred, repayRecordVo.getSumRepayAmount());
                BigDecimal sumAmount = new BigDecimal(getAmount(hundred, repayRecordVo.getSumLoanAmount()));
                BigDecimal diffAmount = new BigDecimal(getAmount(hundred, repayRecordVo.getDiffAmount()));
                four[9] = (sumAmount.subtract(diffAmount)).toString() ;



                String status = "0";
                if (new BigDecimal("0").compareTo(sumAmount.subtract(diffAmount)) == 0){
                    status = "2";  //已结清
                } else if (new BigDecimal("0").compareTo(new BigDecimal(getAmount(hundred, repayRecordVo.getSumRepayAmount()))) == 0
                        && new BigDecimal(AmountUtil.yuanToFen(repayRecordVo.getSumLoanAmount())).compareTo(sumAmount.subtract(diffAmount)) == 0
                        ){
                    status = "0";  //未结清
                } else {
                    status = "1"; //部分结清
                }
                four[10] = status;

                strList.add(four);
                four = new String[three.length];
            }

            OutputStream os = new FileOutputStream(fileName);
            ExcelUtils.exportExcelXTest(strList, null, 0, os);

//			export(list, fileName);

            ChannelSftp sftp = SftpUtil.getSftpConnect(Cons.SftpPayeco.IP, Cons.SftpPayeco.PORT, Cons.SftpPayeco.ACCOUNT, Cons.SftpPayeco.PASSWORD);

            System.out.println("上传文件开始...");
            //SftpUtil.uploadFile("/upload/dz/month", sftp);
            SftpUtil.uploadFile(fileName, uploadPath, name, sftp);
            System.out.println("-------------上传还款月对账报表成功！----------------");
            SftpUtil.exit(sftp);
        } catch (Exception e) {
            e.printStackTrace();
        }

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

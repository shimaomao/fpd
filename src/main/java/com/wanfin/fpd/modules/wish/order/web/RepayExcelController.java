package com.wanfin.fpd.modules.wish.order.web;

import com.jcraft.jsch.ChannelSftp;
import com.sun.corba.se.spi.ior.ObjectKey;
import com.wanfin.fpd.common.config.Cons;
import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.quartz.ftp.DateUtil;
import com.wanfin.fpd.common.utils.DateUtils;
import com.wanfin.fpd.common.utils.StringUtils;
import com.wanfin.fpd.modules.lending.service.TLendingService;
import com.wanfin.fpd.modules.receivables.service.RepayRecordService;
import com.wanfin.fpd.modules.wish.contract.vo.RepayRecordVo;
import com.wanfin.fpd.modules.wish.contract.vo.TLendingVo;
import com.wanfin.fpd.modules.wish.utils.AmountUtil;
import com.wanfin.fpd.modules.wish.utils.ExcelUtils;
import com.wanfin.fpd.modules.wish.utils.SftpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by qiao on 2017/8/31.
 */
@Controller
@RequestMapping(value = "${adminPath}/wish/order/wishOrder")
public class RepayExcelController {

    @Autowired
    private RepayRecordService repayRecordService;
    @Autowired
    private TLendingService tLendingService;

    @ResponseBody
    @RequestMapping(value = "/loanMonth")
    public Map<String, Object> loanMonth() {
        Map<String, Object> map = new HashMap<>();

        System.out.println("-------------执行还款月对账报表定时器----------------");
        RepayRecordVo repayRecordVo = new RepayRecordVo();
        System.out.println("fisrt day:"+DateUtils.getMonthFirstDay());
        System.out.println("last day:"+DateUtils.getMonthLastDay());
        repayRecordVo.setStarttime(DateUtils.getMonthFirstDay());
        repayRecordVo.setEndtime(DateUtils.getMonthLastDay());
        Page<RepayRecordVo> page = repayRecordService.findRepayRecordVoPage(new Page<RepayRecordVo>(), repayRecordVo);
        List<RepayRecordVo> list = page.getList();

        BigDecimal hundred = new BigDecimal("100");
        try {
            String uploadPath = "upload/dz/month/";
            String name = "YLZF_DZWJ_MONTH_" + DateUtil.getCurrentYearMonthDay() + ".xlsx";
            String fileName = "/logs/files/dz/month/" + name;
//            String fileName = "D:\\" + name;
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
            BigDecimal sumFee = new BigDecimal("0"); //当次罚息总金额
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
                BigDecimal sumAmount = new BigDecimal(AmountUtil.yuanToFen(repayRecordVo.getSumLoanAmount()));
                BigDecimal diffAmount = new BigDecimal(getAmount(hundred, repayRecordVo.getSumRepayAmount()));
                four[9] = (sumAmount.subtract(diffAmount)).toString();


                String status = "0";
                if (new BigDecimal("0").compareTo(sumAmount.subtract(diffAmount)) == 0) {
                    status = "2";  //已结清
                } else if (new BigDecimal("0").compareTo(new BigDecimal(getAmount(hundred, repayRecordVo.getSumRepayAmount()))) == 0
                        && new BigDecimal(AmountUtil.yuanToFen(repayRecordVo.getSumLoanAmount())).compareTo(sumAmount.subtract(diffAmount)) == 0
                        ) {
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

            map.put("message", "上传报表成功！");
            map.put("flag", "true");
        } catch (Exception e) {
            map.put("message", "上传报表失败，未知错误！");
            map.put("flag", "true");
            e.printStackTrace();
        }
        return map;
    }

    @ResponseBody
    @RequestMapping(value = "/loanDay")
    public Map<String, Object> loanDay(){
        Map<String, Object> map = new HashMap<>();

        System.out.println("-------------放款日对账----------------");
        TLendingVo lendVo = new TLendingVo();
        lendVo.setStarttime(DateUtils.getMorningDate(new Date()));
        lendVo.setEndtime(DateUtils.getEveningDate(new Date()));
        List<TLendingVo> list = tLendingService.findTLendingVoPage(lendVo);

        try {
            if (list.isEmpty()){
                map.put("flag","false");
                map.put("message", "昨天没有放款的数据！");
                return map;
            }

            String uploadPath = "upload/dz/day/";
            String name = "YLZF_DZWJ_DAY_" + DateUtil.getCurrentYearMonthDay()+".xlsx";
//            String fileName = "D:\\" + name;
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
                    "手续费金额","放款时间","最迟还款时间"};
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

//                four[10] = AmountUtil.yuanToFen(lendVo.getSumLoanAmount());

                four[10] = AmountUtil.yuanToFen(lendVo.getSumCharge()); //手续费
                four[11] = lendVo.getLendTime();
                four[12] = lendVo.getPayPrincipalDate();
                strList.add(four);
                four = new String[three.length];
            }

            OutputStream os = new FileOutputStream(fileName);
            ExcelUtils.exportExcelXTest(strList,null,0, os);

            ChannelSftp sftp = SftpUtil.getSftpConnect(Cons.SftpPayeco.IP, Cons.SftpPayeco.PORT, Cons.SftpPayeco.ACCOUNT, Cons.SftpPayeco.PASSWORD);
            SftpUtil.uploadFile(fileName, uploadPath, name, sftp);
            System.out.println("-------------上传放款日对账----------------");
            SftpUtil.exit(sftp);

            map.put("flag", "success");
            map.put("message", "发送放款日对账报表成功！");
        } catch (Exception e) {
            map.put("flag", "false");
            map.put("message", "发送放款日对账报表失败！");
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

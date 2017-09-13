package com.wanfin.fpd.modules.wish.order.web;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.wanfin.fpd.common.utils.StringUtils;
import com.wanfin.fpd.modules.api.wiss.entity.InformOrder;
import com.wanfin.fpd.modules.lending.entity.TLending;
import com.wanfin.fpd.modules.wish.order.entity.WishOrder;
import com.wanfin.fpd.modules.wish.order.service.WishOrderService;
import com.wanfin.fpd.modules.wish.utils.AmountUtil;
import net.sf.json.JSONObject;

import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jcraft.jsch.ChannelSftp;
import com.mangofactory.swagger.annotations.ApiIgnore;
import com.wanfin.fpd.common.config.Cons;
import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.quartz.ftp.DateUtil;
import com.wanfin.fpd.modules.api.wiss.entity.HkInform;
import com.wanfin.fpd.modules.api.wiss.entity.PayecoBackParams;
import com.wanfin.fpd.modules.api.wiss.service.HkInformService;
import com.wanfin.fpd.modules.api.wiss.service.InformOrderService;
import com.wanfin.fpd.modules.api.wiss.service.InteractionService;
import com.wanfin.fpd.modules.lending.service.TLendingService;
import com.wanfin.fpd.modules.receivables.service.RepayRecordService;
import com.wanfin.fpd.modules.wish.contract.vo.RepayRecordVo;
import com.wanfin.fpd.modules.wish.contract.vo.TLendingVo;
import com.wanfin.fpd.modules.wish.order.entity.ReturnedMoney;
import com.wanfin.fpd.modules.wish.order.service.ReturnedMoneyService;
import com.wanfin.fpd.modules.wish.utils.ExcelUtils;
import com.wanfin.fpd.modules.wish.utils.PoiUtil;
import com.wanfin.fpd.modules.wish.utils.SftpUtil;

@ApiIgnore
@Controller
@RequestMapping(value = "/test")
public class TestController {

    @Autowired
    private RepayRecordService repayRecordService;

    @Autowired
    private TLendingService tLendingService;

    @Autowired
    public ReturnedMoneyService returnedMoneyService;

    @Autowired
    private InteractionService interactionService;

    @Autowired
    public InformOrderService informOrderService;

    @Autowired
    public HkInformService hkInformService;

    @Autowired
    public WishOrderService wishOrderService;

    public static void main(String[] args) {
        int[] test = new int[10];
        List<int[]> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            test[0] = i;
            list.add(test);
            test = new int[10];
        }
        for (int j = 0; j < list.size(); j++) {
            int[] aa = list.get(j);
            System.out.println(aa[0]);
        }

    }


    /**
     * 历史订单处理
     */
    @RequestMapping(value = "/wishOrder")
    public void wishOrder() {
        WishOrder wishOrder = null;

        //查询回款订单信息为2
        InformOrder informOrder = new InformOrder();
        informOrder.setDealStatus("2");
        List<InformOrder> list = informOrderService.findList(informOrder);

        try {
            String encoding = "UTF-8";

            if (list == null || list.size() < 1) {
                System.out.println("没有需要处理的历史订单文件...");
                return;
            }

            //连接sftp服务器
            //			File file = new File(path+"//"+fileName);
            //sftp://183.62.252.139:65005   账号：yilian，!12345678
            //ChannelSftp sftp = SftpUtil.getSftpConnect("183.62.252.139", 65005, "yilian", "!12345678");
            ChannelSftp sftp = SftpUtil.getSftpConnect(Cons.SftpPayeco.IP, Cons.SftpPayeco.PORT, Cons.SftpPayeco.ACCOUNT, Cons.SftpPayeco.PASSWORD);
            System.out.println("开始从远程服务器获取历史订单文件...");
            SimpleDateFormat dff = new SimpleDateFormat("yyyy-MM-dd");
            for (int z = 0; z < list.size(); z++) {
                informOrder = list.get(z);
                System.out.println("------------------:" + informOrder.getReqOrdersNo());
                //String path = "D:\\" + informOrder.getReqOrdersNo()+".xlsx";
                String fileDownloadPath = "upload/orders/" + informOrder.getReqOrdersNo();//+".xlsx";
//                String fileSavePath = "/logs/files/orders/" + informOrder.getReqOrdersNo();//+".xlsx";
                String fileSavePath = "D:\\" + informOrder.getReqOrdersNo();//+".xlsx";
                File file = SftpUtil.download(fileDownloadPath, fileSavePath, sftp);
                System.out.println("下载完成:" + file.getAbsolutePath());

                //解析文件
                PoiUtil poi = new PoiUtil();
                //TODO 修改文件路径
                poi.loadExcel(fileSavePath);
                LinkedList[] result = poi.init();

                //批量删除旧数据：四个参数值必须
//				if(StringUtils.isNotBlank(informOrder.getUserId()) && StringUtils.isNotBlank(informOrder.getMerchantId()) && informOrder.getStartDate() !=null && informOrder.getEndDate() != null){
//					wishOrder = new WishOrder();
//					wishOrder.setUserId(informOrder.getUserId());
////					wishOrder.setMerchantId(informOrder.getMerchantId());
//					wishOrder.setBeginDate(informOrder.getStartDate());//删除开始时间
//					wishOrder.setEndDate(informOrder.getEndDate());//删除结束时间
//					wishOrderService.delOldDatas(wishOrder);
//				}

                //插入新数据
                for (int i = 1; i < result.length; i++) {
                    for (int j = 0; j < result[i].size(); j++) {
                        System.out.print(result[i].get(j) + "\t");
                    }
                    wishOrder = new WishOrder();
                    wishOrder.setUserId(informOrder.getUserId());//用于关联订单 add by shirf 2017-08-08
                    wishOrder.setMerchantId(informOrder.getMerchantId()); //商户id

                    wishOrder.setOrderId((String) result[i].get(0)); //订单号
                    String amount = (String) result[i].get(1);
                    wishOrder.setPaymentAmount(amount.substring(1, amount.length()));  //订单金额  单笔订单成交金额


                    String da = toDate(result[i]);
                    wishOrder.setOrderDate(dff.parse(da));  // 订单时间
                    wishOrder.setCurrentExpectedPaymentEligibilityDate((String) result[i].get(3));//订单预计回款日期
                    wishOrder.setIsRefunded((String) result[i].get(4)); //是否存在退货情况.0为否，1为是
                    if ("1".equals((String) (String) result[i].get(4))) {
                        wishOrder.setRefundDate((String) result[i].get(5)); // 退货日期
                        wishOrder.setRefundTimeDiff((String) result[i].get(10)); //退货情况下距下单时间差
                    }
                    wishOrder.setLoanOperation("0");
                    wishOrder.setRefundAmount(AmountUtil.fenToYuan((String) result[i].get(6))); //退款金额
                    wishOrder.setHasBeenDisbursed((String) result[i].get(7)); // 订单是否被支付。0否，1是
                    wishOrder.setIsChargeback((String) result[i].get(8)); //是否被投诉
                    wishOrder.setOrderMonth((String) result[i].get(9));  //订单月份
//                    if (!"N/A".equals((String) result[i].get(10))) {//退货时间差
//                        wishOrder.setDateDiff((String) result[i].get(10));
//                    }
                    wishOrder.setAmount(AmountUtil.fenToYuan((String) result[i].get(11)));

                    wishOrder.setIsStoreCurrentlyTrusted((String) result[i].get(12));
                    try {
                        wishOrder.setId(wishOrder.getOrderId());
                        wishOrderService.updateOrInsert(wishOrder);
                    } catch (Exception e) {
                        e.printStackTrace();
                        // TODO: handle exception
                    }
                }

                //处理完毕
                informOrder.setDealStatus("3");
                informOrderService.save(informOrder);
                //删除文件
                //FileUtil.delAllFile(file.getAbsolutePath());
                //file.delete();
            }
            SftpUtil.exit(sftp);


        } catch (Exception e) {
            System.out.println("处理历史订单文件出错");
            e.printStackTrace();
        }
    }

    private String toDate(LinkedList linkedList) {
        String date = (String) linkedList.get(2);
        return date.replaceAll("/", "-");
    }

    /**
     * 8.1 回款通知
     */
    @RequestMapping(value = "/returnMoney")
    public void returnMoney() {

        System.out.println("-------------回款通知----------------");
        HkInform inform = new HkInform();
        inform.setDealStatus("0");//是否从ftp获取并入库处理由这个状态来决定
        List<HkInform> list = hkInformService.findList(inform);
        ReturnedMoney returnMoney = null;

        try {
            //String encoding = "UTF-8";

            //连接sftp服务器
            //			File file = new File(path+"//"+fileName);
            //sftp://183.62.252.139:65005   账号：yilian，!12345678
            ChannelSftp sftp = SftpUtil.getSftpConnect(Cons.SftpPayeco.IP, Cons.SftpPayeco.PORT, Cons.SftpPayeco.ACCOUNT, Cons.SftpPayeco.PASSWORD);
            System.out.println("开始从远程服务器获取...");

            if (list.isEmpty()) {
                return;
            }

            for (int z = 0; z < list.size(); z++) {

                inform = list.get(z);

                String fileDownloadPath = "upload/hk/" + inform.getFilePath();// + ".xlsx";
                String fileSavePath = "D:\\" + inform.getFilePath();// + ".xlsx";
                System.out.println("path:" + fileDownloadPath);
                System.out.println("filePath:" + fileSavePath);
                SftpUtil.download(fileDownloadPath, fileSavePath, sftp);
                System.out.println("下载完成");

                //解析文件
                PoiUtil poi = new PoiUtil();
                //TODO 修改文件路径
                poi.loadExcel(fileSavePath);
                LinkedList[] result = poi.init();

                for (int i = 1; i < result.length; i++) {
                    for (int j = 0; j < result[i].size(); j++) {
                        System.out.print(result[i].get(j) + "\t");
                    }
                    returnMoney = new ReturnedMoney();
                    returnMoney.setOrderId((String) result[i].get(0));  //代收付订单号
                    returnMoney.setAccountNum((String) result[i].get(1)); //账号
                    returnMoney.setAccountName((String) result[i].get(2)); //开户名
                    returnMoney.setAccountProvice((String) result[i].get(3));//开户省份
                    returnMoney.setAccountCity((String) result[i].get(4)); //开户城市

                    returnMoney.setRealPayMoney(AmountUtil.fenToYuan((String) result[i].get(5))); //实付金额
                    returnMoney.setAccountBank((String) result[i].get(6)); //银行名称
                    returnMoney.setAccountCategory((String) result[i].get(7)); //账户种类
                    returnMoney.setAccountType((String) result[i].get(8)); //账户类型
                    returnMoney.setIdentityType((String) result[i].get(9));  //开户证件类型

                    returnMoney.setIdentityNum((String) result[i].get(10)); //开户证件号
                    returnMoney.setTradeCurrency((String) result[i].get(11)); //交易币种
                    returnMoney.setRemarks((String) result[i].get(12)); //备注
                    returnMoney.setMerchantOrderId((String) result[i].get(13)); //商户订单号
                    returnMoney.setMerchantId((String) result[i].get(14)); //商户流水号

                    returnMoney.setEnterTime((String) result[i].get(15)); //录入时间

                    //returnMoney.setFileName(fileSavePath);
                    System.out.println("-------------------------------" + inform.getFilePath());
                    returnMoney.setFileName(inform.getFilePath());
                    returnMoney.setIsDeal("0");
                    returnMoney.setStatus("0");
                    returnedMoneyService.save(returnMoney);
                }
                //删除下载文件
//				file.delete();
//				if (!file.exists()) {
//					System.out.println("删除下载文件成功！");
//				}

            }
            //更新状态为 已处理
            HkInform inform2 = null;
            for (int i = 0; i < list.size(); i++) {
                inform2 = list.get(i);
                //inform2.setStatus("3");
                inform2.setDealStatus("1");
                hkInformService.save(inform2);
            }

            //回款结束后调用
            returnedMoneyService.repayMoney();


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 8.2 扣款报表
     *
     * @throws ParseException
     */
    @RequestMapping(value = "/deductMoney")
    public void deductMoney() throws ParseException {

        ReturnedMoney entity = new ReturnedMoney();
        entity.setIsDeal("1");
        entity.setStatus("0");
        List<ReturnedMoney> list = returnedMoneyService.findList(new ReturnedMoney());
        try {

            String filePath = "/logs/files/" + DateUtil.getCurrentYearMonthDay() + ".xlsx";


            //准备数据
            //第一行属性
            //组装数据测试
            List<String[]> strList = new ArrayList<String[]>();
            //第一行属性
            String[] one = {"当次还款人数", "当次扣款总金额", "当次罚息总金额", "回款报表文件名"};
            strList.add(one);
            //第二行数据
            //第二行数据
            Map<String, Object> mapData = new HashedMap();
            ReturnedMoney returnMoney = null;
            BigDecimal repayMoney = new BigDecimal("0.00");
            BigDecimal sumFee = new BigDecimal("0.00");
            for (int i = 0; i < list.size(); i++) {
                returnMoney = list.get(i);
                String loanMoney = returnMoney.getRepayLoanMoney();
                String fee = returnMoney.getFee();
                repayMoney = repayMoney.add(new BigDecimal(loanMoney));
                sumFee = sumFee.add(new BigDecimal(fee));
            }
            String[] two = {String.valueOf(list.size()), String.valueOf(repayMoney), String.valueOf(sumFee), list.get(0).getFileName()};
            strList.add(two);
            //第三行属性
            String[] three = {"借款人id", "借款人姓名", "借款人银行卡号", "当次扣款金额", "当次罚息金额", "代收订单号"};
            strList.add(three);
            //第四行以后的数据
            String[] four = new String[6];
            for (int i = 0; i < list.size(); i++) {
                returnMoney = list.get(i);
                four[0] = returnMoney.getUserId();
                four[1] = returnMoney.getUserName();
                four[2] = returnMoney.getAccountNum();
                four[3] = returnMoney.getRepayLoanMoney();
                four[4] = returnMoney.getFee();
                four[5] = returnMoney.getOrderId();
                strList.add(four);
                four = new String[three.length];
            }

            OutputStream os = new FileOutputStream(filePath);
            ExcelUtils.exportExcelXTest(strList, null, 0, os);


            ChannelSftp sftp = SftpUtil.getSftpConnect(Cons.SftpPayeco.IP, Cons.SftpPayeco.PORT, Cons.SftpPayeco.ACCOUNT, Cons.SftpPayeco.PASSWORD);
            System.out.println("上传文件开始...");
            SftpUtil.uploadFile(filePath, "upload/kk", "YLZF_REPAY_" + DateUtil.getCurrentYearMonthDay() + ".xlsx", sftp);
            System.out.println("上传成功...");
//			file.delete();  
//			System.out.println("删除完成，开始校验本地文件...");  
            SftpUtil.exit(sftp);


            //通知易联
            String tradeId = "kkNotify";
            JSONObject reqBody = new JSONObject();
            reqBody.put("filePath", "YLZF_REPAY_" + DateUtil.getCurrentYearMonthDay() + ".xlsx");

            PayecoBackParams payecoBackParams = null;
            boolean flag = true;

            int num = 0;
            while (flag) {
                payecoBackParams = interactionService.getPayecoRequestByParams(tradeId, reqBody);
                if (payecoBackParams != null && payecoBackParams.getHeader() != null && payecoBackParams.getHeader().getResCode() != null && "0000".equals(payecoBackParams.getHeader().getResCode())) {
                    flag = false;
                } else {
                    flag = true;
                }
                num++;
                if (num > 5) {
                    return;
                }
            }
            //发送报表后更新信息状态为1

            for (int i = 0; i < list.size(); i++) {
                returnMoney = list.get(i);
                returnMoney.setStatus("1");
                returnedMoneyService.save(returnMoney);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //打款通知结束后调用
        returnedMoneyService.repayMoney();

    }

    /**
     * 9.1
     */
    @RequestMapping(value = "/loanDay")
    public void loanDay() {
        System.out.println("-------------放款日对账----------------");
        TLendingVo lendVo = null;
        List<TLendingVo> list = tLendingService.findTLendingVoPage(new TLendingVo());

        try {
            String uploadPath = "upload/dz/day/";
            String name = "YLZF_DZWJ_DAY_" + DateUtil.getCurrentYearMonthDay() + ".xlsx";
            String fileName = "D:\\" + name;
            //生成excel文件，上传到sftp
            //准备数据
            //第一行属性
            //组装数据测试
            List<String[]> strList = new ArrayList<String[]>();
            //第一行属性
            String[] one = {"业务笔数", "借款总金额", "手续费金额"};
            strList.add(one);
            //第二行数据
            //第二行数据
            //Map<String, Object> mapData = new HashedMap();

            BigDecimal repayMoney = new BigDecimal("0");
            BigDecimal sumFee = new BigDecimal("0");
            for (int i = 0; i < list.size(); i++) {
                lendVo = list.get(i);
                String loanMoney = lendVo.getSumLoanAmount();
                String fee = lendVo.getSumCharge();
                if (StringUtils.isEmpty(loanMoney)) {
                    loanMoney = "0";
                }
                if (StringUtils.isEmpty(fee)) {
                    fee = "0";
                }
                repayMoney = repayMoney.add(new BigDecimal(loanMoney));
                sumFee = sumFee.add(new BigDecimal(fee));
            }
            String[] two = {String.valueOf(list.size()), AmountUtil.yuanToFen(repayMoney), AmountUtil.yuanToFen(sumFee)};
            strList.add(two);
            //第三行属性
            String[] three = {"借款人id", "借款人Payeco Id", "借款人业务id", "借款人姓名", "借款人银行卡号",
                    "银行名称", "开户省份", "账户类型", "借款金额", "账号类型",
                    "借款金额", "手续费金额", "放款时间", "最迟还款时间"};
            strList.add(three);
            //第四行以后的数据

            TLendingVo tLendingVo = null;
            for (int j = 0; j < list.size(); j++) {
                String[] four = new String[three.length];
                tLendingVo = list.get(j);
                System.out.println("loanContractId:" + tLendingVo.getLoanContractId());
                four[0] = tLendingVo.getCustomerId();
                four[1] = tLendingVo.getCustomerId();
                four[2] = tLendingVo.getLoanContractId();
                four[3] = tLendingVo.getCustomerName();
                four[4] = tLendingVo.getGatheringNumber();

                four[5] = "";   //银行名称
                four[6] = "";
                four[7] = "";

                four[8] = AmountUtil.yuanToFen(tLendingVo.getSumLoanAmount());
                four[9] = "";

                four[10] = AmountUtil.yuanToFen(tLendingVo.getSumLoanAmount());

                four[11] = AmountUtil.yuanToFen(tLendingVo.getSumCharge()); //手续费
                four[12] = tLendingVo.getLendTime();
                four[13] = tLendingVo.getPayPrincipalDate();
                strList.add(four);
                four = new String[three.length];
            }

            OutputStream os = new FileOutputStream(fileName);
            ExcelUtils.exportExcelXTest(strList, null, 0, os);

            ChannelSftp sftp = SftpUtil.getSftpConnect(Cons.SftpPayeco.IP, Cons.SftpPayeco.PORT, Cons.SftpPayeco.ACCOUNT, Cons.SftpPayeco.PASSWORD);
//			SftpUtil.uploadFile(fileName, uploadPath, name, sftp);
            System.out.println("-------------上传放款日对账----------------");
            SftpUtil.exit(sftp);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    /**
     * 9.2  还款月对账表
     */
    @RequestMapping(value = "/loanMonth")
    public void execute() {

        System.out.println("-------------执行还款月对账报表定时器----------------");
        RepayRecordVo repayRecordVo = new RepayRecordVo();
        Page<RepayRecordVo> page = repayRecordService.findRepayRecordVoPage(new Page<RepayRecordVo>(), repayRecordVo);
        List<RepayRecordVo> list = page.getList();

        BigDecimal hundred = new BigDecimal("100");
        try {
            String uploadPath = "upload/dz/month/";
            String name = "YLZF_DZWJ_MONTH_" + DateUtil.getCurrentYearMonthDay() + ".xlsx";
            String fileName = "E:\\" + name;
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
                BigDecimal sumAmount = new BigDecimal(getAmount(hundred, repayRecordVo.getSumLoanAmount()));
                BigDecimal diffAmount = new BigDecimal(getAmount(hundred, repayRecordVo.getDiffAmount()));
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

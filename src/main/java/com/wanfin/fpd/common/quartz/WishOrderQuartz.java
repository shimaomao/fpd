package com.wanfin.fpd.common.quartz;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

import com.sun.org.apache.bcel.internal.generic.RETURN;
import com.wanfin.fpd.modules.wish.utils.AmountUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.jcraft.jsch.ChannelSftp;
import com.wanfin.fpd.common.config.Cons;
import com.wanfin.fpd.modules.api.wiss.entity.InformOrder;
import com.wanfin.fpd.modules.api.wiss.service.InformOrderService;
import com.wanfin.fpd.modules.wish.order.entity.WishOrder;
import com.wanfin.fpd.modules.wish.order.service.WishOrderService;
import com.wanfin.fpd.modules.wish.utils.PoiUtil;
import com.wanfin.fpd.modules.wish.utils.SftpUtil;

/**
 * 商铺历史订单处理
 *
 * @author asus
 */
public class WishOrderQuartz {

    @Autowired
    public WishOrderService wishOrderService;
    @Autowired
    public InformOrderService informOrderService;

    public void execute() {
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
                String fileSavePath = "/logs/files/orders/" + informOrder.getReqOrdersNo();//+".xlsx";
//                String fileSavePath = "D:\\" + informOrder.getReqOrdersNo();//+".xlsx";
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

    public static void main(String[] args) {
        String amount = "$10.20";
        System.out.println(amount.substring(1, amount.length()));
    }

}

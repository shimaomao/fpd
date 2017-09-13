package com.wanfin.fpd.common.quartz;

import java.util.LinkedList;
import java.util.List;

import com.wanfin.fpd.common.utils.SendMail;
import com.wanfin.fpd.modules.wish.utils.AmountUtil;
import org.springframework.beans.factory.annotation.Autowired;

import com.jcraft.jsch.ChannelSftp;
import com.wanfin.fpd.common.config.Cons;
import com.wanfin.fpd.modules.api.wiss.entity.HkInform;
import com.wanfin.fpd.modules.api.wiss.service.HkInformService;
import com.wanfin.fpd.modules.api.wiss.service.InformOrderService;
import com.wanfin.fpd.modules.wish.order.entity.ReturnedMoney;
import com.wanfin.fpd.modules.wish.order.service.ReturnedMoneyService;
import com.wanfin.fpd.modules.wish.utils.PoiUtil;
import com.wanfin.fpd.modules.wish.utils.SftpUtil;

public class ReturnMoneyQuartz {

	@Autowired
	public InformOrderService informOrderService;
	@Autowired
	public ReturnedMoneyService returnedMoneyService;
	@Autowired
	public HkInformService hkInformService;

	/**
	 * 8.1 回款通知
	 * 
	 * 
	 */
	public void execute(){
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

			if (list.isEmpty()){
				return;
			}

			for (int z = 0; z < list.size(); z++) {

				inform = list.get(z);

				String fileDownloadPath = "upload/hk/" + inform.getFilePath();// + ".xlsx";
				String fileSavePath = "/logs/files/hk/" + inform.getFilePath();// + ".xlsx";
				System.out.println("path:"+fileDownloadPath);
				System.out.println("filePath:"+fileSavePath);
				SftpUtil.download(fileDownloadPath, fileSavePath, sftp);  
				System.out.println("下载完成");  

				//解析文件
				PoiUtil poi = new PoiUtil();
				//TODO 修改文件路径
				poi.loadExcel(fileSavePath);  
				LinkedList[] result = poi.init();

				for(int i=1;i<result.length;i++){  
					for(int j=0;j<result[i].size();j++){  
						System.out.print(result[i].get(j) + "\t");  
					}  
					returnMoney = new ReturnedMoney();
					returnMoney.setOrderId((String)result[i].get(0));  //代收付订单号
					returnMoney.setAccountNum((String)result[i].get(1)); //账号
					returnMoney.setAccountName((String)result[i].get(2)); //开户名
					returnMoney.setAccountProvice((String)result[i].get(3));//开户省份
					returnMoney.setAccountCity((String)result[i].get(4)); //开户城市

					returnMoney.setRealPayMoney(AmountUtil.fenToYuan((String)result[i].get(5))); //实付金额
					returnMoney.setAccountBank((String)result[i].get(6)); //银行名称
					returnMoney.setAccountCategory((String)result[i].get(7)); //账户种类
					returnMoney.setAccountType((String)result[i].get(8)); //账户类型
					returnMoney.setIdentityType((String)result[i].get(9));  //开户证件类型

					returnMoney.setIdentityNum((String)result[i].get(10)); //开户证件号
					returnMoney.setTradeCurrency((String)result[i].get(11)); //交易币种
					returnMoney.setRemarks((String)result[i].get(12)); //备注
					returnMoney.setMerchantOrderId((String)result[i].get(13)); //商户订单号
					returnMoney.setMerchantId((String)result[i].get(14)); //商户流水号

					returnMoney.setEnterTime((String)result[i].get(15)); //录入时间
					
					//returnMoney.setFileName(fileSavePath);
					System.out.println("-------------------------------"+inform.getFilePath());
					returnMoney.setFileName(inform.getFilePath());
					returnMoney.setIsDeal("0");
					returnMoney.setStatus("0");
					returnMoney.setFileId(inform.getId());
					returnedMoneyService.save(returnMoney);
				}
				//删除下载文件
//				file.delete();
//				if (!file.exists()) {
//					System.out.println("删除下载文件成功！");
//				}

			}

			SendMail.sendMessage(Cons.EmailParam.EMAIL_HOST_TYPE, Cons.EmailParam.SEND_EMAIL,
					Cons.EmailParam.SEND_EMAIL_PASSWORD, Cons.EmailParam.TARGET_EMAIL, "易联回款报表通知处理",
					"易联回款报表通知处理",
					"text/html;charset=UTF-8");
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

}

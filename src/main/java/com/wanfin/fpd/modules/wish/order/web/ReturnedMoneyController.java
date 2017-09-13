/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.wanfin.fpd.modules.wish.order.web;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.collections.map.HashedMap;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.wanfin.fpd.common.config.Cons;
import com.wanfin.fpd.common.config.Global;
import com.wanfin.fpd.common.persistence.Page;
import com.wanfin.fpd.common.quartz.DeductMoneyQuartz;
import com.wanfin.fpd.common.quartz.WishOrderQuartz;
import com.wanfin.fpd.common.quartz.ftp.DateUtil;
import com.wanfin.fpd.common.web.BaseController;
import com.wanfin.fpd.common.utils.StringUtils;
import com.alibaba.fastjson.JSONArray;
import com.jcraft.jsch.ChannelSftp;
import com.mangofactory.swagger.annotations.ApiIgnore;
import com.wanfin.fpd.modules.api.wiss.entity.HkInform;
import com.wanfin.fpd.modules.api.wiss.entity.PayecoBackParams;
import com.wanfin.fpd.modules.api.wiss.service.HkInformService;
import com.wanfin.fpd.modules.api.wiss.service.InteractionService;
import com.wanfin.fpd.modules.wish.order.entity.ReturnMoneyVo;
import com.wanfin.fpd.modules.wish.order.entity.ReturnedMoney;
import com.wanfin.fpd.modules.wish.order.entity.WishOrder;
import com.wanfin.fpd.modules.wish.order.service.ReturnedMoneyService;
import com.wanfin.fpd.modules.wish.utils.ExcelUtils;
import com.wanfin.fpd.modules.wish.utils.FileWrite;
import com.wanfin.fpd.modules.wish.utils.PoiUtil;
import com.wanfin.fpd.modules.wish.utils.SftpUtil;

/**
 * 回款记录Controller
 * @author cjp
 * @version 2017-07-07
 */
@ApiIgnore
@Controller
@RequestMapping(value = "/wish/order/returnedMoney")
public class ReturnedMoneyController extends BaseController {

	@Autowired
	private ReturnedMoneyService returnedMoneyService;
	@Autowired
	private HkInformService hkInformService;
	@Autowired
	private InteractionService interactionService;

	@ModelAttribute
	public ReturnedMoney get(@RequestParam(required=false) String id) {
		ReturnedMoney entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = returnedMoneyService.get(id);
		}
		if (entity == null){
			entity = new ReturnedMoney();
		}
		return entity;
	}

	@RequiresPermissions("wish.order:returnedMoney:view")
	@RequestMapping(value = {"list", ""})
	public String list(ReturnedMoney returnedMoney, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ReturnedMoney> page = returnedMoneyService.findPage(new Page<ReturnedMoney>(request, response), returnedMoney); 
		model.addAttribute("page", page);
		model.addAttribute("returnedMoney", returnedMoney);
		return "modules/wish.order/returnedMoneyList";
	}

	@RequiresPermissions("wish.order:returnedMoney:view")
	@RequestMapping(value = "form")
	public String form(ReturnedMoney returnedMoney, Model model) {
		model.addAttribute("returnedMoney", returnedMoney);
		return "modules/wish.order/returnedMoneyForm";
	}

	@RequiresPermissions("wish.order:returnedMoney:edit")
	@RequestMapping(value = "save")
	public String save(ReturnedMoney returnedMoney, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, returnedMoney)){
			return form(returnedMoney, model);
		}
		returnedMoneyService.save(returnedMoney);
		addMessage(redirectAttributes, "保存回款记录成功");
		return "redirect:"+Global.getAdminPath()+"/wish.order/returnedMoney/?repage";
	}

	@RequiresPermissions("wish.order:returnedMoney:edit")
	@RequestMapping(value = "delete")
	public String delete(ReturnedMoney returnedMoney, RedirectAttributes redirectAttributes) {
		returnedMoneyService.delete(returnedMoney);
		addMessage(redirectAttributes, "删除回款记录成功");
		return "redirect:"+Global.getAdminPath()+"/wish.order/returnedMoney/?repage";
	}



	@RequestMapping(value = "execute")
	public void execute(){
		HkInform inform = new HkInform();
		//inform.setStatus("2");
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

			for (int z = 0; z < list.size(); z++) {

				inform = list.get(z);

//				String path = inform.getFilePath();
//				String filePath = "/logs/files/" + DateUtil.getCurrentYearMonthDay() + ".xlsx";
				String path = inform.getFilePath();
				String filePath = "E:\\" + DateUtil.getCurrentYearMonthDay() + ".xlsx";
				System.out.println("path:"+path);
				System.out.println("filePath:"+filePath);
				File file = SftpUtil.download(path, filePath, sftp);  
				System.out.println("下载完成");  

				//解析文件
				PoiUtil poi = new PoiUtil();
				//TODO 修改文件路径
				poi.loadExcel(filePath);  
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
					returnMoney.setRealPayMoney((String)result[i].get(5)); //实付金额
					returnMoney.setAccountBank((String)result[i].get(6)); //银行名称
					returnMoney.setAccountCategory((String)result[i].get(7)); //账户种类
					returnMoney.setAccountType((String)result[i].get(8)); //账户类型
					returnMoney.setIdentityType((String)result[i].get(9));  //开户证件类型
					returnMoney.setIdentityNum((String)result[i].get(10)); //开户证件号
					returnMoney.setTradeCurrency((String)result[i].get(11)); //交易币种
					returnMoney.setRemarks((String)result[i].get(12)); //备注
					returnMoney.setOrderId((String)result[i].get(13)); //商户订单号
					returnMoney.setOrderId((String)result[i].get(14)); //商户流水号
					returnMoney.setEnterTime((String)result[i].get(15)); //录入时间
					
					returnMoney.setFileName(path);
					returnMoney.setIsDeal("0");
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
//
//			//回款结束后调用
//			returnedMoneyService.repayMoney();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	@RequestMapping(value = "/dk")
	public void dk(){


//		ReturnedMoney entity = new ReturnedMoney();
//		entity.setIsDeal("1");
//		entity.setStatus("0");
//		List<ReturnedMoney> list = returnedMoneyService.findList(new ReturnedMoney());
//		try {
//
//			String filePath = "E:\\" + DateUtil.getCurrentYearMonthDay()+".xlsx";
//
//			
//			//准备数据
//			//第一行属性
//			Map<String, Object> headMap = new HashedMap();
//			headMap.put("num", "当次还款人数");
//			headMap.put("totalSum", "当次扣款总金额");
//			headMap.put("totalFee", "当次罚息总金额");
//			headMap.put("fileName", "回款报表文件名");
//			//第二行数据
//			Map<String, Object> mapData = new HashedMap();
//			ReturnedMoney returnMoney = null;
//			BigDecimal repayMoney = new BigDecimal("0.00");
//			BigDecimal sumFee = new BigDecimal("0.00");
//			for(int i =0; i <list.size(); i++){
//				returnMoney = list.get(i);
//				String loanMoney = returnMoney.getRepayLoanMoney();
//				String fee = returnMoney.getFee();
//				repayMoney = repayMoney.add(new BigDecimal(loanMoney));
//				sumFee = sumFee.add(new BigDecimal(fee));
//			}
//			mapData.put("num", String.valueOf(list.size()));
//			mapData.put("totalSum", String.valueOf(repayMoney));
//			mapData.put("totalFee", String.valueOf(sumFee));
//			mapData.put("fileName", list.get(0).getFileName());
//			//第三行属性
//			Map<String, Object> secondMap = new HashedMap();
//			secondMap.put("userId", "借款人id");
//			secondMap.put("userName", "借款人姓名");
//			secondMap.put("accountNum", "借款人银行卡号");
//			secondMap.put("repayLoanMoney", "当次扣款金额");
//			secondMap.put("fee", "当次罚息金额");
//			//第四行以后的数据
//			JSONArray jsonArray = new JSONArray();
//			ReturnedMoney returnedMoney = null;
//			for (int i = 0; i < list.size(); i++) {
//				returnedMoney = list.get(i);
//				ReturnMoneyVo returnMoneyVo = new ReturnMoneyVo();
//				returnMoneyVo.setUserId(returnedMoney.getUserId());
//				returnMoneyVo.setUserName(returnedMoney.getUserName());
//				returnMoneyVo.setAccountNum(returnedMoney.getAccountNum());
//				returnMoneyVo.setRepayLoanMoney(returnedMoney.getRepayLoanMoney());
//				returnMoneyVo.setFee(returnedMoney.getFee());
//				jsonArray.add(returnMoneyVo);
//			}
//			
//			OutputStream os = new FileOutputStream(filePath);
//			ExcelUtils.test(headMap, mapData, secondMap, jsonArray, null, 0, os);
//
//
//			ChannelSftp sftp = SftpUtil.getSftpConnect(Cons.SftpPayeco.IP, Cons.SftpPayeco.PORT, Cons.SftpPayeco.ACCOUNT, Cons.SftpPayeco.PASSWORD);   
//			System.out.println("上传文件开始...");  
//			SftpUtil.uploadFile(filePath, "upload/repay", "YLZF_REPAY_"+DateUtil.getCurrentYearMonthDay()+".xlsx", sftp);
//			System.out.println("上传成功...");  
////			file.delete();  
////			System.out.println("删除完成，开始校验本地文件...");  
//			SftpUtil.exit(sftp);  

		ReturnedMoney entity = new ReturnedMoney();
		entity.setIsDeal("1");
		entity.setStatus("0");
		List<ReturnedMoney> list = returnedMoneyService.findList(new ReturnedMoney());
		try {

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
			BigDecimal repayMoney = new BigDecimal("0.00");
			BigDecimal sumFee = new BigDecimal("0.00");
			for(int i =0; i <list.size(); i++){
				returnMoney = list.get(i);
				String loanMoney = returnMoney.getRepayLoanMoney();
				String fee = returnMoney.getFee();
				repayMoney = repayMoney.add(new BigDecimal(loanMoney));
				sumFee = sumFee.add(new BigDecimal(fee));
			}
			String[] two = {String.valueOf(list.size()), String.valueOf(repayMoney), String.valueOf(sumFee), list.get(0).getFileName()};
	        strList.add(two);
			//第三行属性
			String[] three = {"借款人id","借款人姓名","借款人银行卡号","当次扣款金额","当次罚息金额","代收订单号"};
			strList.add(three);
			//第四行以后的数据
			String[] four = new String[6];
			for (int i=0; i<list.size(); i++){
				returnMoney = list.get(i);
				four[0] = returnMoney.getUserId();
				four[1] = returnMoney.getUserName();
				four[2] = returnMoney.getAccountNum();
				four[3] = returnMoney.getRepayLoanMoney();
				four[4] = returnMoney.getFee();
				four[5] = returnMoney.getOrderId();
				strList.add(four);
			}
			
			OutputStream os = new FileOutputStream(filePath);
			ExcelUtils.exportExcelXTest(strList,null,0,os);


			ChannelSftp sftp = SftpUtil.getSftpConnect(Cons.SftpPayeco.IP, Cons.SftpPayeco.PORT, Cons.SftpPayeco.ACCOUNT, Cons.SftpPayeco.PASSWORD);   
			System.out.println("上传文件开始...");  
			SftpUtil.uploadFile(filePath, "upload/kk", "YLZF_REPAY_"+DateUtil.getCurrentYearMonthDay()+".xlsx", sftp);
			System.out.println("上传成功...");  
//			file.delete();  
//			System.out.println("删除完成，开始校验本地文件...");  
			SftpUtil.exit(sftp);  
			
			//通知易联
//			String tradeId = "kkNotify";
//			JSONObject reqBody = new JSONObject();
//	        reqBody.put("filePath", "upload/repay/"+"YLZF_REPAY_"+DateUtil.getCurrentYearMonthDay()+".xlsx");
//	  
//	        PayecoBackParams payecoBackParams = null;
//	        boolean flag = true;
//	        
//	        int num = 0;
//	        while(flag){
//	        	payecoBackParams = interactionService.getPayecoRequestByParams(tradeId, reqBody);
//	        	if (payecoBackParams != null && payecoBackParams.getHeader() != null && payecoBackParams.getHeader().getResCode() != null && "0000".equals(payecoBackParams.getHeader().getResCode())) {
//					flag = false;
//				} else {
//					flag = true;
//				}
//	        	num++;
//	        	if (num>5) {
//					return;
//				}
//	        }
//
//	        
//	        //发送报表后更新信息状态为1
//	        
//	        for (int i = 0; i < list.size(); i++) {
//				returnMoney = list.get(i);
//				returnMoney.setStatus("1");
//	        	returnedMoneyService.save(returnMoney);
//			}
		} catch (Exception e) {
			e.printStackTrace();
		}


		//打款通知结束后调用
		//returnedMoneyService.repayMoney();

	}

	@RequestMapping(value = "/kk")
	public void test(){
		String tradeId = "kkNotify";
		JSONObject reqBody = new JSONObject();
        reqBody.put("filePath", "upload/kk/"+"YLZF_REPAY_"+DateUtil.getCurrentYearMonthDay()+".xlsx");
  
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
				return;
			}
        }
	}
}
package com.wanfin.fpd.common.quartz;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;

import com.wanfin.fpd.common.utils.DateUtils;
import com.wanfin.fpd.modules.api.wiss.entity.PayecoBackParams;
import com.wanfin.fpd.modules.api.wiss.entity.YlExchangeRates;
import com.wanfin.fpd.modules.api.wiss.service.InteractionService;
import com.wanfin.fpd.modules.api.wiss.service.YlExchangeRatesService;

import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

/**
 * 获取汇率定时器
 * @author user
 *
 */
public class ExchangeRatesDeals {
	@Autowired
	private YlExchangeRatesService exchangeRatesService;
	@Autowired
	private InteractionService interactionService;
	/** 
     * 定时任务，执行方法 
     * */  
    public void execute(){  
    	String time = new SimpleDateFormat("MMM d，yyyy KK:mm:ss a",Locale.CHINESE).format(System.currentTimeMillis());  
    	System.out.println("————————————————————————————————————————————————————————————————————————————");
        System.out.println("time:"+time+">>测试获取汇率定");  
        
        //读取数据库中数据，匹配到今天的则不用再获取,系统只需要获取美元汇率
        Date nowDate = new Date();
        YlExchangeRates params = new YlExchangeRates();
        params.setOrigCurrency("CNY");
        params.setExchCurrency("USD");
        params.setDate(DateUtils.formatDate(nowDate,"yyyyMMdd"));
        YlExchangeRates exchangeRateOne = exchangeRatesService.getRate(params);
        params.setOrigCurrency("USD");
        params.setExchCurrency("CNY");
        params.setDate(DateUtils.formatDate(nowDate,"yyyyMMdd"));
        YlExchangeRates exchangeRateTwo = exchangeRatesService.getRate(params);
        if(exchangeRateOne == null || exchangeRateTwo == null){
        	//--组织请求data body
        	String tardeId = "getRates";
            JSONObject reqBody = new JSONObject();
            //测试的时候屏蔽
            PayecoBackParams payecoBackParams = interactionService.getPayecoRequestByParams(tardeId, reqBody);
            //===================================================
            //----------------------测试开始----------------------
            //===================================================
//            //汇率接口返回消息解析
//    		String resStr = "{\"data\":{\"body\":{\"exchs\":[{\"rate\":\"0.872200\",\"origCurrency\":\"HKD\",\"exchCurrency\":\"CNY\",\"date\":\"20170704\"},{\"rate\":\"7.760200\",\"origCurrency\":\"EUR\",\"exchCurrency\":\"CNY\",\"date\":\"20170704\"},{\"rate\":\"8.833200\",\"origCurrency\":\"GBP\",\"exchCurrency\":\"CNY\",\"date\":\"20170704\"},{\"rate\":\"0.060261\",\"origCurrency\":\"JPY\",\"exchCurrency\":\"CNY\",\"date\":\"20170704\"},{\"rate\":\"6.813900\",\"origCurrency\":\"USD\",\"exchCurrency\":\"CNY\",\"date\":\"20170704\"},{\"rate\":\"7.706100\",\"origCurrency\":\"CNY\",\"exchCurrency\":\"EUR\",\"date\":\"20170704\"},{\"rate\":\"8.771600\",\"origCurrency\":\"CNY\",\"exchCurrency\":\"GBP\",\"date\":\"20170704\"},{\"rate\":\"0.868800\",\"origCurrency\":\"CNY\",\"exchCurrency\":\"HKD\",\"date\":\"20170704\"},{\"rate\":\"0.059841\",\"origCurrency\":\"CNY\",\"exchCurrency\":\"JPY\",\"date\":\"20170704\"},{\"rate\":\"6.786700\",\"origCurrency\":\"CNY\",\"exchCurrency\":\"USD\",\"date\":\"20170704\"}]},\"header\":{\"pvdId\":\"wz\",\"reqTime\":\"20160215122334\",\"resCode\":\"0000\",\"resTime\":\"20170705095725\",\"tradeId\":\"getRates\"}},\"sign\":\"M0Un+Gj6elQubszdpK24JSsKSU4vw3BkjHUnAV648nAywaPxk16rUMyYjfz0OKXFNShFlfZiGp7jWt7PvLhMB6i8Ffx9QRTcG9bVkj+Aaqj1KYY3ZcALt0NIprgwotaB+EpP6PhANAM+feme8AT8Agitg1YjQBtnMW6Jqcz6jPI=\"}";
//    		System.out.println("通讯响应数据：" + resStr);
//    		//解析响应数据
//    		JSONObject retPkg = JSONObject.fromObject(resStr);
//    		String retDataStr = retPkg.getString("data");
//    		String retSign = retPkg.getString("sign");
//    		System.out.println("响应data：" + retDataStr);
//    		System.out.println("响应sign：" + retSign);
//          
//    		//解析响应data
//    		JSONObject retData = JSONObject.fromObject(retDataStr);
//          
//    		//解析响应header
//    		JSONObject retHeader = retData.getJSONObject("header");
//          
//    		//解析响应body
//    		//JSONObject retBody = retData.getJSONObject("body");
//          
//    		//解析返回整体结构
//    		//PayecoBackParams payecoBackParams = (PayecoBackParams)JSONObject.toBean(retPkg, PayecoBackParams.class);
//    		PayecoBackParams payecoBackParams = new PayecoBackParams();
//    		payecoBackParams.setData(retPkg.getString("data"));
//    		payecoBackParams.setSign(retPkg.getString("sign"));
//    		JSONObject jsonData = retPkg.getJSONObject("data");
//    		payecoBackParams.setJsonHeader(jsonData.getJSONObject("header"));
//    		payecoBackParams.setJsonBody(jsonData.getJSONObject("body"));
            //===================================================
            //----------------------测试结束----------------------
            //===================================================
            //解析data中的body内容
            if(payecoBackParams != null && payecoBackParams.getJsonBody() != null){
	            JSONArray exchsArray = payecoBackParams.getJsonBody().getJSONArray("exchs");
	    		System.out.println("exchs:"+exchsArray.toString());
	    		//在单独java跑有用，项目里dateFormats无效
	    		//String[] dateFormats = new String[] {"yyyyMMdd" };//"yyyy-MM-dd HH:mm:ss"
	    		String[] dateFormats = new String[] {"yyyyMMddHHmmss","yyyyMMdd","yyyy-MM-dd" };
	            JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher(dateFormats));
	    		@ SuppressWarnings("unchecked")
	    		List<YlExchangeRates> listExchangeRates = (List<YlExchangeRates>)JSONArray.toCollection(exchsArray, YlExchangeRates.class);
	    		for(YlExchangeRates exchangeRate:listExchangeRates){
	    			System.out.println(exchangeRate.toString());
	    			YlExchangeRates tmp = exchangeRatesService.getRate(exchangeRate);
	    			if(tmp == null){//不存在则更新
	    				exchangeRatesService.save(exchangeRate);
	    			}
	    		}
	            System.out.println("获取汇率结束");  
	            System.out.println("————————————————————————————————————————————————————————————————————————————");
            }else{
            	System.out.println("获取汇率失败！");
            }
        }else{
        	System.out.println("==》已经成功获取到需要的数据");
        }
    }
}

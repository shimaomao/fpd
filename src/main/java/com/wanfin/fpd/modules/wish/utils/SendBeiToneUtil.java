package com.wanfin.fpd.modules.wish.utils;

import com.wanfin.fpd.common.config.Cons;
import com.wanfin.fpd.common.utils.JsonOUtils;
import com.wanfin.fpd.modules.api.utils.InterfaceUtil;
import com.wanfin.fpd.modules.contract.entity.TLoanContract;
import com.wanfin.fpd.modules.sys.utils.UserUtils;
import net.sf.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Map;

/**
 * Created by qiao on 2017/8/29.
 */
public class SendBeiToneUtil {

    public static final String SECRET_KEY = Cons.BetToneParam.SECRET_KEY;
    public static final String GET_SIGN = Cons.BetToneParam.GET_SIGN;
    public static final String RELEASE_LOAN = Cons.BetToneParam.RELEASE_LOAN;
    public static final String REPAYMENT = Cons.BetToneParam.REPAYMENT;
    /**
     * 获取验签接口
     *
     * @param loanContract
     * @return
     */
    public static String getSign(TLoanContract loanContract) {
        String userId = loanContract.getCustomerId();

        JSONObject paramJson = new JSONObject();
        paramJson.put("corpId", userId); //易联用户id
        paramJson.put("amount", loanContract.getLoanAmount()); //贷款金额
        paramJson.put("bankowner", loanContract.getGatheringName()); //姓名
        paramJson.put("bankno", loanContract.getGatheringNumber());  //银行卡号
        paramJson.put("idSeq", loanContract.getId());//业务唯一标识
        paramJson.put("yearConversion", loanContract.getLoanRate()); //年化利率
        paramJson.put("limitTime", Integer.parseInt(loanContract.getLoanPeriod())); //周期
        paramJson.put("secretKey", SECRET_KEY);//秘钥

        return sendAndGetSign(paramJson);

    }


    private static String sendAndGetSign(JSONObject paramJson) {
        try{
            String result = getResult(paramJson, GET_SIGN);
            try {
                result = URLDecoder.decode(URLDecoder.decode(result, "utf-8"), "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            System.out.println("接收两次decode后内容:" + result);

            Map map = JsonOUtils.toMap(result);
            String code = (String) map.get("code");
            System.out.println("message:" + map.get("sign"));
            //签名:623D7647E2578614975F2C5666558103
            UserUtils.getSession().setAttribute("sign", map.get("sign"));
            return (String) map.get("sign");

        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }

    public static String getResult(JSONObject paramJson, String url) throws UnsupportedEncodingException {
        String param = paramJson.toString();
        System.out.println("params:"+paramJson.toString());
        System.out.println("发送原内容:"+param);
        param = "data="+ URLEncoder.encode(URLEncoder.encode(param,"utf-8"),"utf-8");
        System.out.println("发送两次encode加密后内容:"+param);


        String backContent = InterfaceUtil.sendPost(url, param);
        System.out.println("接收原内容:"+backContent);
        backContent = URLDecoder.decode(URLDecoder.decode(backContent, "utf-8"), "utf-8");
        System.out.println("接收两次decode后内容:"+backContent);
//        String result = HttpPostJson.sendInfo(url, paramJson.toString());
//        System.out.println("c" + result);
        return backContent;
    }



}

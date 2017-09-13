package com.wanfin.fpd.modules.wish.beitone.web;

import com.wanfin.fpd.common.config.Cons;
import com.wanfin.fpd.common.utils.IdGen;
import com.wanfin.fpd.common.utils.IdcardUtils;
import com.wanfin.fpd.common.utils.JsonOUtils;
import com.wanfin.fpd.common.utils.StringUtils;
import com.wanfin.fpd.common.web.BaseController;
import com.wanfin.fpd.modules.api.utils.InterfaceUtil;
import com.wanfin.fpd.modules.api.utils.test.SignEntity;
import com.wanfin.fpd.modules.contract.entity.TLoanContract;
import com.wanfin.fpd.modules.contract.service.TLoanContractService;
import com.wanfin.fpd.modules.files.entity.TContractFiles;
import com.wanfin.fpd.modules.files.service.TContractFilesService;
import com.wanfin.fpd.modules.sys.utils.UserUtils;
import com.wanfin.fpd.modules.wish.beitone.entity.ProductPush;
import com.wanfin.fpd.modules.wish.beitone.entity.RepayParams;
import com.wanfin.fpd.modules.wish.beitone.entity.StatusInform;
import com.wanfin.fpd.modules.wish.beitone.entity.Test;
import com.wanfin.fpd.modules.wish.beitone.service.ProductPushService;
import com.wanfin.fpd.modules.wish.contract.service.WishContractService;
import com.wanfin.fpd.modules.wish.merchant.entity.Merchant;
import com.wanfin.fpd.modules.wish.merchant.service.MerchantService;
import com.wanfin.fpd.modules.wish.order.entity.TReturnedMsg;
import com.wanfin.fpd.modules.wish.order.service.ReturnedMoneyService;
import com.wanfin.fpd.modules.wish.order.service.TReturnedMsgService;
import com.wanfin.fpd.modules.wish.order.service.WishOverdueService;

import com.wanfin.fpd.modules.wish.utils.SendBeiToneUtil;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping(value = "/push/p2p")
public class PushBeiToneController extends BaseController {


    public static final String SECRET_KEY = Cons.BetToneParam.SECRET_KEY;
    public static final String GET_SIGN = Cons.BetToneParam.GET_SIGN;
    public static final String RELEASE_LOAN = Cons.BetToneParam.RELEASE_LOAN;
    public static final String REPAYMENT = Cons.BetToneParam.REPAYMENT;


    @Autowired
    private MerchantService merchantService;
    @Autowired
    private ProductPushService productPushService;
    @Autowired
    private TLoanContractService loanContractService;
    @Autowired
    private ReturnedMoneyService returnedMoneyService;
    @Autowired
    private WishOverdueService wishOverdueService;
	@Autowired
	private WishContractService wishContractService;
	@Autowired
	private TReturnedMsgService returnedMsgService;
    @Autowired
    private TContractFilesService contractFilesService;
	@Autowired
	private String tLoanContractId = "";

	

    /**
     * 获取验签接口
     *
     * @param loanContract
     * @return
     */
    public String getSign(TLoanContract loanContract) {
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


    @ResponseBody
    @RequestMapping(value = "/test/repayment")
    public Map<String, Object> test(@RequestBody Test test) {
        TReturnedMsg returnedMsg = new TReturnedMsg();
        System.out.println("ids："+test.getIds());
        System.out.println("tastId:"+test.getTaskId());
        returnedMsg.setReturnedMoneyId(test.getIds());
        List<TReturnedMsg> msgList = null;
        Map<String, Object> map = new HashMap<>();


        msgList = returnedMsgService.findListByReturnMoneyIds(returnedMsg);
        if (msgList.isEmpty()) {
            map.put("flag", "false");
            map.put("message", "数据为空！");
            return map;
        }


        try {
            List<TContractFiles> filesList = contractFilesService.findListByTaskIds("'"+test.getTaskId()+"'");

            JSONObject paramJson = new JSONObject();
            String sign = null;
            Map<String, Object> repayInfoMap = new HashMap<>();

            StringBuilder idSeqs = new StringBuilder("");
            for (int i = 0; i < msgList.size(); i++) {
                returnedMsg = msgList.get(i);
                idSeqs.append(returnedMsg.getLoanContractId()).append(",");
                repayInfoMap.put(returnedMsg.getLoanContractId(), returnedMsg.getMoney());
            }
            idSeqs.deleteCharAt(idSeqs.length() - 1);
            paramJson.put("idSeqs", idSeqs.toString());
            paramJson.put("batchid", IdGen.uuid().substring(6, 16)); //存在且唯一
            paramJson.put("repayinfo", repayInfoMap); //手续费
            if (filesList.isEmpty()){
                paramJson.put("imgUrl", ""); //还款凭证
            } else {
                paramJson.put("imgUrl", Cons.BetToneParam.PROJECT_URL + filesList.get(0).getFilePath()); //还款凭证
            }
            paramJson.put("secretKey", SendBeiToneUtil.SECRET_KEY); //key
            String param = paramJson.toString();
            System.out.println("json内容:" + param);


            SignEntity signEntity = (SignEntity) InterfaceUtil.jsonToObject(param, SignEntity.class);
            System.out.println("sign内容:" + signEntity.toString());
            String result = InterfaceUtil.sendRequestByEntity(SendBeiToneUtil.REPAYMENT, paramJson);
            System.out.println("----result:" + result);

            result = URLDecoder.decode(URLDecoder.decode(result, "utf-8"), "utf-8");
            map = JsonOUtils.toMap(result);
            System.out.println("message:" + map.get("message"));
            String code = (String) map.get("code");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    private String sendAndGetSign(JSONObject paramJson) {
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

    private String getResult(JSONObject paramJson, String url) throws UnsupportedEncodingException {
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


    /**
     * 借款发起接口
     *
     * @param loanContract
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/releaseLoan")
    public Map<String, Object> releaseLoan(@RequestBody TLoanContract loanContract) {
        return productPushService.releaseLoan(loanContract.getId());
    }
    
    
    
    @ResponseBody
    @RequestMapping(value = "/notifyStatus")
    public Map<String, Object> getStatus(@RequestBody StatusInform statusInform, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();

        if (statusInform == null) {
            map.put("flag", "false");
            map.put("msg", "参数不能为空");
            return map;
        }

        if (StringUtils.isEmpty(statusInform.getIdSeq())) {
            map.put("flag", "false");
            map.put("msg", "贷款订单id不能为空");
            return map;
        }

        String status = statusInform.getStatus();
        String loanContractId = statusInform.getIdSeq();
        TLoanContract loanContract = loanContractService.get(loanContractId);
        if (loanContract == null) {
            map.put("flag", "false");
            map.put("msg", "贷款订单id信息错误");
            return map;
        }

        //审核结果成功
        if ("2".equals(status)) {
            map = checkInformSuccess(loanContract);

        }

        //审核结果失败
        if ("3".equals(status)) {
            map = checkInformFail(loanContract);

        }

        //放款成功通知  我们不做处理，以易联的放款通知为准
        if ("4".equals(status)) {
            map = getLoanInform(statusInform);
        }


        //确认已还款 periods还款期数
        if ("5".equals(status)) {
            map = getVerifyRepay(statusInform);
        }

        //还款已完成
        if ("6".equals(status)) {
            map = getRepayFinished(statusInform);
        }

        //逾期通知 我们不做处理，
        if ("7".equals(status)) {
            map = getOverdue(loanContract);
        }

        //逾期结清  我们不做处理，
        if ("8".equals(status)) {
            getOverdue(null);
        }

        return map;
    }

    /**
     * 审核通过
     */
    public Map<String, Object> checkInformSuccess(TLoanContract loanContract) {
        Map<String, Object> map = new HashMap<>();

        try {
            System.out.println("审核前的订单状态：" + loanContract.getStatus());
            loanContract.setStatus(Cons.LoanContractStatus.TO_LOAN);
            loanContractService.updateStatus(loanContract);
            new Thread() {
				public void run(){
					wishContractService.singleInform(tLoanContractId);
			    }
			}.start();
            map.put("flag", "true");
            map.put("msg", "贷款订单审核成功已处理");
            return map;
        } catch (Exception e) {
            map.put("flag", "false");
            map.put("msg", "未知错误!");
            e.printStackTrace();
        }
        return map;
    }


    /**
     * 审核失败
     */
    public Map<String, Object> checkInformFail(TLoanContract loanContract) {
        Map<String, Object> map = new HashMap<>();

        try {
            System.out.println("审核前的订单状态：" + loanContract.getStatus());
            loanContract.setStatus(Cons.LoanContractStatus.TO_SUSPENSION);
            loanContractService.updateStatus(loanContract);

            wishContractService.unlockOrder(loanContract.getId());//解锁订单

            map.put("flag", "true");
            map.put("msg", "贷款订单审核失败已处理");
            return map;
        } catch (Exception e) {
            map.put("flag", "false");
            map.put("msg", "未知错误!");
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 获取贝通放款通知信息
     */
    public Map<String, Object> getLoanInform(StatusInform statusInform) {
        Map<String, Object> map = new HashMap<>();
        //1 放款成功  订单状态由待放款5改为未结清6
        try {

//            System.out.println("放款前的订单状态：" + loanContract.getStatus());
//            loanContract.setStatus(Cons.LoanContractStatus.TO_LOAN);
//            loanContractService.save(loanContract);
            map.put("flag", "true");
            map.put("msg", "贷款订单放款已处理");
            return map;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;

    }

    /**
     * 获取贝通确认是否已还款信息
     */
    public Map<String, Object> getVerifyRepay(StatusInform statusInform) {
        Map<String, Object> map = new HashMap<>();
        //确认还款后保存信息到回款记录表
        RepayParams data = statusInform.getData();
        try {
            TReturnedMsg returnedMsg = new TReturnedMsg();
            returnedMsg.setMoney(data.getPrincipal());
            returnedMsg.setEightfee(data.getInterest());
            returnedMsg.setLoanContractId(statusInform.getIdSeq());
            if (StringUtils.isNotBlank(data.getPeriods())){
                returnedMsg.setPeriods(Integer.parseInt(data.getPeriods()));
            }
            returnedMsgService.save(returnedMsg);
            map.put("flag", "true");
            map.put("msg", "还款通知已确认");

        } catch (Exception e) {
            map.put("flag", "false");
            map.put("msg", "还款信息有误");
            e.printStackTrace();
        }
        return map;
    }


    /**
     * 还款已完成
     *
     * @param statusInform
     * @return
     */
    public Map<String, Object> getRepayFinished(StatusInform statusInform) {
        Map<String, Object> map = new HashMap<>();
        //确认还款后保存信息到回款记录表
        RepayParams data = statusInform.getData();
        try {

            map.put("flag", "true");
            map.put("msg", "还款已完成通知已确认");

        } catch (Exception e) {
            map.put("flag", "false");
            map.put("msg", "还款信息有误");
            e.printStackTrace();
        }
        return map;

    }

    /**
     * 获取贝通逾期通知
     */
    public Map<String, Object> getOverdue(TLoanContract loanContract) {
        Map<String, Object> map = new HashMap<>();
        //1 逾期通知
        try {

            map.put("flag", "true");
            map.put("msg", "还款逾期已通知");
        } catch (Exception e) {
            map.put("flag", "false");
            map.put("msg", "未知错误");
            e.printStackTrace();
        }
        return map;
    }



    public String getBirthAddress(String idCard) {
        String address = idCard.substring(6, 14);
        System.out.println("address:" + address);
        address = IdcardUtils.getProvinceByIdCard(idCard);
        return address;
    }

    public String getBirthDay(String idCard) {

        StringBuffer birthday = new StringBuffer();
        birthday.append(IdcardUtils.getYearByIdCard(idCard)).append("-")
                .append(IdcardUtils.getMonthByIdCard(idCard)).append("-")
                .append(IdcardUtils.getDateByIdCard(idCard));
        return birthday.toString();
    }

    public static String getSex(String idCard) {
        String sex = idCard.substring(idCard.length() - 2, idCard.length() - 1);
        int sexNum = Integer.parseInt(sex);

        sex = "男";
        if (sexNum % 2 != 0) {
            sex = "男";
        } else {
            sex = "女";
        }
        return sex;
    }


}
